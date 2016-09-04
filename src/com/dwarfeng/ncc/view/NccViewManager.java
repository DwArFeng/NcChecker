package com.dwarfeng.ncc.view;

import com.dwarfeng.dfunc.prog.mvc.AbstractViewManager;
import com.dwarfeng.ncc.control.NccControlPort;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.key.ExceptionFieldKey;
import com.dwarfeng.ncc.view.gui.NccFrame;
import com.dwarfeng.ncc.view.gui.NccFrameControlPort;
import com.dwarfeng.ncc.view.gui.NotifyControlPort;
import com.dwarfeng.ncc.view.gui.Notifyer;
import com.dwarfeng.ncc.view.gui.ProgressMonitor;

/**
 * 数控代码验证程序中的视图控制器，可以提供视图控制端口。
 * @author DwArFeng
 * @since 1.8
 */
public final class NccViewManager extends AbstractViewManager<NccViewControlPort, NccControlPort, NccProgramAttrSet> {
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final ExceptionFieldKey KEY_NOTINIT = ExceptionFieldKey.VIEW_NOTINIT;
	private static final ExceptionFieldKey KEY_INITED = ExceptionFieldKey.VIEW_INITED;
	
	//------------------------------------------------------------------------------------------------
	
	private NccFrame mainFrame;
	private Notifyer notifyer;
	
	private NccViewControlPort viewControlPort = new NccViewControlPort() {
		
		private boolean initFlag = false;

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.NccViewControlPort#init()
		 */
		@Override
		public void init() {
			if(initFlag) throw new IllegalStateException(getProgramAttrSet().getExceptionField(KEY_INITED));
			initFlag = true;
			mainFrame = new NccFrame(NccViewManager.this);
			notifyer = new Notifyer(NccViewManager.this, mainFrame);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.NccViewControlPort#getFrameControlPort()
		 */
		@Override
		public NccFrameControlPort getMainFrameControlPort() {
			if(!initFlag) throw new IllegalStateException(getProgramAttrSet().getExceptionField(KEY_NOTINIT));
			return mainFrame.getControlPort();
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.NccViewControlPort#getNotifyControlPort()
		 */
		@Override
		public NotifyControlPort getNotifyControlPort() {
			if(!initFlag) throw new IllegalStateException(getProgramAttrSet().getExceptionField(KEY_NOTINIT));
			return notifyer.getNotifyControlPort();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.NccViewControlPort#getProgressMonitor()
		 */
		@Override
		public ProgressMonitor getProgressMonitor() {
			if(!initFlag) throw new IllegalStateException(getProgramAttrSet().getExceptionField(KEY_NOTINIT));
			return mainFrame.getProgressMonitor();
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
