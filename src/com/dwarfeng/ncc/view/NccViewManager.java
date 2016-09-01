package com.dwarfeng.ncc.view;

import com.dwarfeng.dfunc.prog.mvc.AbstractViewManager;
import com.dwarfeng.ncc.control.NccControlPort;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.conf.MainFrameAppearConfig;
import com.dwarfeng.ncc.view.gui.NccFrame;
import com.dwarfeng.ncc.view.gui.NccFrameControlPort;

/**
 * ���ش�����֤�����е���ͼ�������������ṩ��ͼ���ƶ˿ڡ�
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
			//TODO �ĳ�StringField
			if(initFlag) throw new IllegalStateException("��ͼ�������Ѿ���ʼ����");
			initFlag = true;
			mainFrame = new NccFrame(NccViewManager.this);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.NccViewControlPort#getFrameControlPort()
		 */
		@Override
		public NccFrameControlPort getMainFrameControlPort() {
			//TODO �ĳ�StringField
			if(!initFlag) throw new IllegalStateException("��ͼ��������δ��ʼ��");
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
