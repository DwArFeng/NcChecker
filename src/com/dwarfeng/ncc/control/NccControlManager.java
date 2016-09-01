package com.dwarfeng.ncc.control;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.dwarfeng.dfunc.prog.mvc.AbstractControlManager;
import com.dwarfeng.ncc.module.NccModuleControlPort;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.NccProgramControlPort;
import com.dwarfeng.ncc.program.conf.MainFrameAppearConfig;
import com.dwarfeng.ncc.view.NccViewControlPort;

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
			
			//读取配置文件
			MainFrameAppearConfig mfac = null;
			try {
				mfac = programControlPort.loadMainFrameAppearConfig();
			} catch (NumberFormatException e) {
				mfac = MainFrameAppearConfig.DEFAULT_CONFIG;
			} catch (IOException e) {
				mfac = MainFrameAppearConfig.DEFAULT_CONFIG;
			}
			
			//各控制站点初始化
			viewControlPort.init();
			moduleControlPort.init();
			
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//调用各个控制器的终结方法。
			
			//退出程序
			System.exit(0);
			
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
