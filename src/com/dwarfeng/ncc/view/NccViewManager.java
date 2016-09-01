package com.dwarfeng.ncc.view;

import com.dwarfeng.dfunc.prog.mvc.AbstractViewManager;
import com.dwarfeng.ncc.control.NccControlPort;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.conf.MainFrameAppearConfig;
import com.dwarfeng.ncc.view.gui.NccFrame;
import com.dwarfeng.ncc.view.gui.NccFrameControlPort;

/**
 * 数控代码验证程序中的视图控制器，可以提供视图控制端口。
 * @author DwArFeng
 * @since 1.8
 */
public final class NccViewManager extends AbstractViewManager<NccViewControlPort, NccControlPort, NccProgramAttrSet> {
	
	private NccFrame mainFrame;
	
	private NccViewControlPort viewControlPort = new NccViewControlPort() {
		
		private boolean initFlag = false;

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.NccViewControlPort#init()
		 */
		@Override
		public void init() {
			//TODO 改成StringField
			if(initFlag) throw new IllegalStateException("视图管理器已经初始化了");
			initFlag = true;
			mainFrame = new NccFrame(NccViewManager.this);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.NccViewControlPort#getFrameControlPort()
		 */
		@Override
		public NccFrameControlPort getMainFrameControlPort() {
			//TODO 改成StringField
			if(!initFlag) throw new IllegalStateException("视图管理器还未初始化");
			return mainFrame.getControlPort();
		}
		
		
	};
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.mvc.AbstractViewManager#getViewControlPort()
	 */
	@Override
	public NccViewControlPort getViewControlPort() {
		return viewControlPort;
	}

}
