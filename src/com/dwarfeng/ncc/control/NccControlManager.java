package com.dwarfeng.ncc.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.dwarfeng.dfunc.DwarfFunction;
import com.dwarfeng.dfunc.io.CT;
import com.dwarfeng.dfunc.io.CT.OutputType;
import com.dwarfeng.dfunc.prog.mvc.AbstractControlManager;
import com.dwarfeng.ncc.control.back.NewFileRunnable;
import com.dwarfeng.ncc.control.back.OpenFileRunnable;
import com.dwarfeng.ncc.control.back.Toggle2EidtRunnable;
import com.dwarfeng.ncc.model.NccModelControlPort;
import com.dwarfeng.ncc.model.front.CodeLoader;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.NccProgramControlPort;
import com.dwarfeng.ncc.program.conf.FrontConfig;
import com.dwarfeng.ncc.program.conf.MfAppearConfig;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.NccViewControlPort;
import com.dwarfeng.ncc.view.gui.StatusLabelType;

/**
 * 数控代码验证程序中的控制管理器，可提供控制端口。
 * @author DwArFeng
 * @since 1.8
 */
@SuppressWarnings("unused")
public final class NccControlManager extends AbstractControlManager<NccProgramControlPort, NccModelControlPort,
NccViewControlPort, NccControlPort, NccProgramAttrSet> {
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final String KEY_NOTINIT = "控制管理器还未初始化。";
	private static final String KEY_INITED = "控制管理器已经初始化了。";
	
	private static final StringFieldKey KEY_GETREADY = StringFieldKey.LABEL_GETREADY;
	private static final StringFieldKey KEY_STARTFIN = StringFieldKey.OUT_STARTFIN;
	
	//------------------------------------------------------------------------------------------------
	
	private final NccControlPort controlPort = new NccControlPort() {
		
		private boolean startFlag = false;
		private Mode currentMode;
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#startProgram()
		 */
		@Override
		public void startProgram() {
			if(startFlag) throw new IllegalStateException(KEY_INITED);
			startFlag = true;
			currentMode = Mode.INSPECT;
			
//			//由于界面支持该外观，所以不可能抛出异常。
//			try {
//				UIManager.setLookAndFeel(new NimbusLookAndFeel());
//			} catch (UnsupportedLookAndFeelException e) {}
			
			//各控制站点初始化
			programControlPort.init();
			moduleControlPort.init();
			viewControlPort.init();
			
			//读取配置文件
			MfAppearConfig mfac = null;
			try {
				mfac = programControlPort.configCp().loadMainFrameAppearConfig();
			} catch (NumberFormatException e) {
				mfac = MfAppearConfig.DEFAULT_CONFIG;
			} catch (IOException e) {
				mfac = MfAppearConfig.DEFAULT_CONFIG;
			}
			//TODO 设置为读取式。
			FrontConfig fc = FrontConfig.DEFAULT;
			
			//各个管理器初始化参数
			moduleControlPort.frontCp().applyFontConfig(fc);
			moduleControlPort.frontCp().setFrontCodeSerial(null,null,false);
			viewControlPort.frameCp().applyAppearanceConfig(mfac);
			viewControlPort.frameCp().noneFileMode(true);
			
			//显示程序主界面
			viewControlPort.frameCp().setVisible(true);
			
			//输出就绪文本
			CT.setOutputType(OutputType.NO_DATE);
			viewControlPort.frameCp().setStatusLabelMessage(programAttrSet.getStringField(KEY_GETREADY), StatusLabelType.NORMAL);
			viewControlPort.frameCp().traceInConsole(DwarfFunction.getWelcomeString());
			viewControlPort.frameCp().traceInConsole("");
			CT.setOutputType(OutputType.HALF_DATE);
			viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_STARTFIN));
		}

		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#exitProgram()
		 */
		@Override
		public void exitProgram() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			
			//隐藏界面
			viewControlPort.frameCp().setVisible(false);
			
			//保存各种配置
			MfAppearConfig config = viewControlPort.frameCp().getAppearanceConfig();
			try {
				programControlPort.configCp().saveMainFrameAppearConfig(config);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//调用各个控制器的终结方法。
			
			//退出程序
			System.exit(0);
			
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#openNcFile()
		 */
		@Override
		public void openNcFile() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			
			final boolean aFlag = true;
			final FileFilter[] fileFilters = new FileFilter[]{
				new FileNameExtensionFilter(programAttrSet.getStringField(StringFieldKey.CTRL_TEXTFILE), "txt"),
				new FileNameExtensionFilter(programAttrSet.getStringField(StringFieldKey.CTRL_NCFILE), "nc", "ptp", "mpf")
			};
			final File file = viewControlPort.notifyCp().askFile(fileFilters, aFlag);
			if(file == null) return;
			openNcFile(file);
		}


		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#openNcFile(java.io.File)
		 */
		@Override
		public void openNcFile(File file) {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			Objects.requireNonNull(file);
			
			//有可能要关闭文件
			perhapsCloseFrontFile();
			
			InputStream in = null;
			CodeLoader codeLoader = null;
			try{
				in = new FileInputStream(file);
				codeLoader = moduleControlPort.explCp().newNcCodeLoader(in);
			}catch(IOException e){
				return;
			}
			
			//后台中运行指定方法
			programControlPort.backInvoke(new OpenFileRunnable(NccControlManager.this, codeLoader, file));
		}


		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#closeFrontFile()
		 */
		@Override
		public void closeFrontFile() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			if(!moduleControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
			
			//可能需要保存文件
			perhapseSaveFrontFile();
			
			try{
				viewControlPort.frameCp().lockEdit();
				//从视图中撤下代码段
				viewControlPort.frameCp().showCode(null);
				//移除前台模型中的代码段
				moduleControlPort.frontCp().setFrontCodeSerial(null, null, false);
				//设置视图为无文件模式
				viewControlPort.frameCp().noneFileMode(true);
				
			}finally{
				viewControlPort.frameCp().unlockEdit();
			}
			// TODO Auto-generated method stub
			
		}
		
		/**
		 * 如果某个操作可能会涉及到关闭文件，则调用该方法。
		 * 该方法会检车是否存在前台文件，如果有前台文件且前台文件需要保存的话，则将其关闭。
		 */
		private void perhapsCloseFrontFile(){
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			if(	moduleControlPort.frontCp().hasFrontCode()) closeFrontFile();
		}


		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#saveFrontFile()
		 */
		@Override
		public void saveFrontFile() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			if(!moduleControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
			
			// TODO Auto-generated method stub
		}
		
		/**
		 * 如果某个操作可能会涉及到保存文件，则调用该方法。
		 * 该方法会检车是否存在前台文件，如果有前台文件的话，则将其保存。
		 */
		private void perhapseSaveFrontFile(){
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			if(moduleControlPort.frontCp().hasFrontCode()) saveFrontFile();
		}


		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#newFrontFile()
		 */
		@Override
		public void newFrontFile() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			
			//可能需要涉及关闭文件
			perhapsCloseFrontFile();
			
			//后台中运行指定方法
			programControlPort.backInvoke(new NewFileRunnable(NccControlManager.this));
			
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#toggleMode(com.dwarfeng.ncc.control.NccControlPort.CodePanelMode)
		 */
		@Override
		public void toggleMode(Mode mode) {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			Objects.requireNonNull(mode);
			
			switch(mode){
				case EDIT:
					toggle2Edit();
					break;
				case INSPECT:
					toggle2Inspect();
					break;
			}
		}


		private void toggle2Inspect() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			// TODO Auto-generated method stub
			//FOO
			
			viewControlPort.frameCp().toggleMode(Mode.INSPECT);
		}

		private void toggle2Edit() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			
			programControlPort.backInvoke(new Toggle2EidtRunnable(NccControlManager.this));
		}


		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#commitCode(java.lang.String)
		 */
		@Override
		public void commitCode(String str) {
			// TODO Auto-generated method stub
			
		}
		
		
	};
	
	
	
	
	
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.mvc.AbstractControlManager#getControlPort()
	 */
	@Override
	public NccControlPort getControlPort() {
		return controlPort;
	}

}
