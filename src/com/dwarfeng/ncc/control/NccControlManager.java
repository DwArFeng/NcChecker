package com.dwarfeng.ncc.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.dwarfeng.dfunc.DwarfFunction;
import com.dwarfeng.dfunc.prog.mvc.AbstractControlManager;
import com.dwarfeng.ncc.module.NccModuleControlPort;
import com.dwarfeng.ncc.module.expl.CodeLoader;
import com.dwarfeng.ncc.module.expl.ExplControlPort;
import com.dwarfeng.ncc.module.nc.ArrayCodeList;
import com.dwarfeng.ncc.module.nc.Code;
import com.dwarfeng.ncc.module.nc.CodeSerial;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.NccProgramControlPort;
import com.dwarfeng.ncc.program.conf.MfAppearConfig;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.NccViewControlPort;
import com.dwarfeng.ncc.view.gui.NccFrameControlPort;
import com.dwarfeng.ncc.view.gui.NotifyControlPort;
import com.dwarfeng.ncc.view.gui.ProgressMonitor;
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
	private static final StringFieldKey KEY_LOADING = StringFieldKey.FILE_NOWLOADING;
	
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
				mfac = programControlPort.getConfigControlPort().loadMainFrameAppearConfig();
			} catch (NumberFormatException e) {
				mfac = MfAppearConfig.DEFAULT_CONFIG;
			} catch (IOException e) {
				mfac = MfAppearConfig.DEFAULT_CONFIG;
			}
			
			//显示程序主界面
			final NccFrameControlPort mainFrameControlPort = viewControlPort.getMainFrameControlPort();
			mainFrameControlPort.applyAppearance(mfac);
			mainFrameControlPort.setVisible(true);
			
			//输出就绪文本
			mainFrameControlPort.setStatusLabelMessage(programAttrSet.getStringField(KEY_GETREADY), StatusLabelType.NORMAL);
			mainFrameControlPort.traceInConsole(DwarfFunction.getWelcomeString());
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#exitProgram()
		 */
		@Override
		public void exitProgram() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			
			//隐藏界面
			viewControlPort.getMainFrameControlPort().setVisible(false);
			
			//保存各种配置
			MfAppearConfig config = viewControlPort.getMainFrameControlPort().getCurrentAppearance();
			try {
				programControlPort.getConfigControlPort().saveMainFrameAppearConfig(config);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//调用各个控制器的终结方法。
			
			//退出程序
			System.exit(0);
			
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#getFileControlPort()
		 */
		@Override
		public FileControlPort getFileControlPort() {
			return fileControlPort;
		}
		
	};
	
	private final FileControlPort fileControlPort = new FileControlPort() {
		
		@Override
		public void openNcFile() {
			final NccProgramAttrSet attrSet = programAttrSet;
			final NotifyControlPort notifyControlPort = getViewControlPort().getNotifyControlPort();
			final boolean aFlag = true;
			final FileFilter[] fileFilters = new FileFilter[]{
				new FileNameExtensionFilter(attrSet.getStringField(StringFieldKey.CTRL_TEXTFILE), "txt"),
				new FileNameExtensionFilter(attrSet.getStringField(StringFieldKey.CTRL_NCFILE), "nc", "ptp", "mpf")
			};
			final File file = notifyControlPort.askFile(fileFilters, aFlag);
			if(file == null) return;
			final ProgressMonitor progressMonitor = viewControlPort.getProgressMonitor();
			final ExplControlPort explMoudleControlPort = moduleControlPort.getExplMoudleControlPort();
			InputStream in = null;
			try{
				in = new FileInputStream(file);
				final CodeLoader codeLoader = explMoudleControlPort.newNcCodeLoader(in);
				final Runnable runnable = new Runnable() {
					
					@Override
					public void run() {
						try{
							progressMonitor.startMonitor();
							progressMonitor.setMessage(attrSet.getStringField(KEY_LOADING));
							progressMonitor.setIndeterminate(true);
							int i = 1;
							final List<Code> codes = new ArrayList<Code>();
							while(codeLoader.hasNext()){
								if(progressMonitor.isSuspend()){
									return;
								}
								codes.add(codeLoader.loadNext());
								progressMonitor.setMessage(attrSet.getStringField(KEY_LOADING) + i);
								i++;
							}
							
							CodeSerial codeList = new ArrayCodeList(codes.toArray(new Code[0]));
							moduleControlPort.getFrontModuleControlPort()
									.setFrontCodeSerial(codeList);
							//TODO 读取之后的方法
							
						}catch(Exception e){
							e.printStackTrace();
							return;
						}finally{
							progressMonitor.endMonitor();
							try {
								codeLoader.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
					}
				};
				
				programControlPort.backInvoke(runnable);
				
			}catch(IOException e){
				return;
			}
			
			
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
