package com.dwarfeng.ncc.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.dwarfeng.dfunc.DwarfFunction;
import com.dwarfeng.dfunc.io.CT;
import com.dwarfeng.dfunc.prog.mvc.AbstractControlManager;
import com.dwarfeng.ncc.control.back.OpenFileRunnable;
import com.dwarfeng.ncc.module.NccModuleControlPort;
import com.dwarfeng.ncc.module.front.CodeLoader;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.NccProgramControlPort;
import com.dwarfeng.ncc.program.conf.FrontConfig;
import com.dwarfeng.ncc.program.conf.MfAppearConfig;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.NccViewControlPort;
import com.dwarfeng.ncc.view.gui.NotifyCp;
import com.dwarfeng.ncc.view.gui.StatusLabelType;

/**
 * 数控代码验证程序中的控制管理器，可提供控制端口。
 * @author DwArFeng
 * @since 1.8
 */
public final class NccControlManager extends AbstractControlManager<NccProgramControlPort, NccModuleControlPort,
NccViewControlPort, NccControlPort, NccProgramAttrSet> {
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final String KEY_NOTINIT = "控制管理器还未初始化。";
	private static final String KEY_INITED = "控制管理器已经初始化了。";
	private static final StringFieldKey KEY_GETREADY = StringFieldKey.OUT_GETREADY;
	private static final StringFieldKey KEY_STARTFIN = StringFieldKey.OUT_STARTFIN;
	
	//------------------------------------------------------------------------------------------------
	
	private final NccControlPort controlPort = new NccControlPort() {
		
		private boolean startFlag = false;
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#startProgram()
		 */
		@Override
		public void startProgram() {
			if(startFlag) throw new IllegalStateException(KEY_INITED);
			startFlag = true;
			
			//由于界面支持该外观，所以不可能抛出异常。
			try {
				UIManager.setLookAndFeel(new NimbusLookAndFeel());
			} catch (UnsupportedLookAndFeelException e) {}
			
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
			viewControlPort.frameCp().applyAppearanceConfig(mfac);
			
			//显示程序主界面
			viewControlPort.frameCp().setVisible(true);
			
			//输出就绪文本
			viewControlPort.frameCp().setStatusLabelMessage(programAttrSet.getStringField(KEY_GETREADY), StatusLabelType.NORMAL);
			viewControlPort.frameCp().traceInConsole(DwarfFunction.getWelcomeString());
			viewControlPort.frameCp().traceInConsole("");
			viewControlPort.frameCp().traceInConsole(CT.toString(programAttrSet.getStringField(KEY_STARTFIN)));
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
			
			final NotifyCp notifyControlPort = getViewControlPort().notifyCp();
			final boolean aFlag = true;
			final FileFilter[] fileFilters = new FileFilter[]{
				new FileNameExtensionFilter(programAttrSet.getStringField(StringFieldKey.CTRL_TEXTFILE), "txt"),
				new FileNameExtensionFilter(programAttrSet.getStringField(StringFieldKey.CTRL_NCFILE), "nc", "ptp", "mpf")
			};
			final File file = notifyControlPort.askFile(fileFilters, aFlag);
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
			
			InputStream in = null;
			CodeLoader codeLoader = null;
			try{
				in = new FileInputStream(file);
				codeLoader = moduleControlPort.explCp().newNcCodeLoader(in);
			}catch(IOException e){
				return;
			}
			
			programControlPort.backInvoke(new OpenFileRunnable(NccControlManager.this, codeLoader));
		}


		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#closeFrontFile()
		 */
		@Override
		public void closeFrontFile() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			if(!moduleControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
			
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
