package com.dwarfeng.ncc.control;

import com.dwarfeng.dfunc.prog.mvc.AbstractControlManager;
import com.dwarfeng.ncc.module.NccModuleControlPort;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.NccProgramControlPort;
import com.dwarfeng.ncc.view.NccViewControlPort;

/**
 * ���ش�����֤�����еĿ��ƹ����������ṩ���ƶ˿ڡ�
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
			//TODO �ĳ�StringField
			if(startFlag) throw new IllegalStateException("�����Ѿ�������");
			startFlag = true;
			viewControlPort.init();
			moduleControlPort.init();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#setMainFrameVisible(boolean)
		 */
		@Override
		public void setMainFrameVisible(boolean aFlag) {
			if(!startFlag) throw new IllegalStateException("����δ����");
			viewControlPort.setMainFrameVisible(aFlag);
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
