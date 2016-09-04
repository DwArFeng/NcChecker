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

import com.dwarfeng.dfunc.io.CT;
import com.dwarfeng.dfunc.prog.mvc.AbstractControlManager;
import com.dwarfeng.ncc.module.NccModuleControlPort;
import com.dwarfeng.ncc.module.expl.CodeLoader;
import com.dwarfeng.ncc.module.expl.ExplMoudleControlPort;
import com.dwarfeng.ncc.module.nc.Code;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.NccProgramControlPort;
import com.dwarfeng.ncc.program.conf.MainFrameAppearConfig;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.NccViewControlPort;
import com.dwarfeng.ncc.view.gui.NotifyControlPort;
import com.dwarfeng.ncc.view.gui.ProgressMonitor;

/**
 * 数控代码验证程序中的控制管理器，可提供控制端口。
 * @author DwArFeng
 * @since 1.8
 */
public final class NccControlManager extends AbstractControlManager<NccProgramControlPort, NccModuleControlPort,
NccViewControlPort, NccControlPort, NccProgramAttrSet> {
	
	

	private NccControlPort controlPort = new NccControlPort() {
		
		private boolean startFlag = false;

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#startProgram()
		 */
		@Override
		public void startProgram() {
			//TODO 改成StringField
			if(startFlag) throw new IllegalStateException("程序已经启动了");
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
			MainFrameAppearConfig mfac = null;
			try {
				mfac = programControlPort.loadMainFrameAppearConfig();
			} catch (NumberFormatException e) {
				mfac = MainFrameAppearConfig.DEFAULT_CONFIG;
			} catch (IOException e) {
				mfac = MainFrameAppearConfig.DEFAULT_CONFIG;
			}
			
			//显示程序主界面
			viewControlPort.getMainFrameControlPort().applyAppearance(mfac);
			viewControlPort.getMainFrameControlPort().setVisible(true);
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#exitProgram()
		 */
		@Override
		public void exitProgram() {
			//TODO 使用StringField
			if(!startFlag) throw new IllegalStateException("程序还未启动");
			
			//隐藏界面
			viewControlPort.getMainFrameControlPort().setVisible(false);
			
			//保存各种配置
			MainFrameAppearConfig config = viewControlPort.getMainFrameControlPort().getCurrentAppearance();
			try {
				programControlPort.saveMainFrameAppearConfig(config);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//调用各个控制器的终结方法。
			
			//退出程序
			System.exit(0);
			
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#openFile()
		 */
		@Override
		public void openNcFile() {
			final NccProgramAttrSet attrSet = getProgramAttrSet();
			final NotifyControlPort notifyControlPort = getViewControlPort().getNotifyControlPort();
			final boolean aFlag = true;
			final FileFilter[] fileFilters = new FileFilter[]{
				new FileNameExtensionFilter(attrSet.getStringField(StringFieldKey.CTRL_TEXTFILE), "txt"),
				new FileNameExtensionFilter(attrSet.getStringField(StringFieldKey.CTRL_NCFILE), "nc", "ptp", "mpf")
			};
			final File file = notifyControlPort.askFile(fileFilters, aFlag);
			if(file == null) return;
			final ProgressMonitor progressMonitor = viewControlPort.getProgressMonitor();
			final ExplMoudleControlPort explMoudleControlPort = moduleControlPort.getExplMoudleControlPort();
			InputStream in = null;
			try{
				in = new FileInputStream(file);
				final CodeLoader codeLoader = explMoudleControlPort.newNcCodeLoader(in);
				final Runnable runnable = new Runnable() {
					
					@Override
					public void run() {
						try{
							progressMonitor.startMonitor();
							//TODO 使用StringField
							progressMonitor.setMessage("开始读取文件...");
							progressMonitor.setIndeterminate(true);
							int i = 1;
							final List<Code> codeList = new ArrayList<Code>();
							while(codeLoader.hasNext()){
								if(progressMonitor.isSuspend()){
									return;
								}
								codeList.add(codeLoader.loadNext());
								progressMonitor.setMessage("开始读取文件..." + i);
								i++;
							}
							
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
