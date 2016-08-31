package com.dwarfeng.ncc.view;

import com.dwarfeng.dfunc.prog.mvc.AbstractViewManager;
import com.dwarfeng.ncc.control.NccControlPort;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.view.gui.NccFrame;

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
		 * @see com.dwarfeng.ncc.view.NccViewControlPort#setMainFrameVisible(boolean)
		 */
		@Override
		public void setMainFrameVisible(boolean aFlag) {
			//TODO �ĳ�StringField
			if(!initFlag) throw new IllegalStateException("��ͼ��������û�г�ʼ��");
			mainFrame.setVisible(aFlag);
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
