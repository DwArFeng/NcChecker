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
			
			//���ڽ���֧�ָ���ۣ����Բ������׳��쳣��
			try {
				UIManager.setLookAndFeel(new NimbusLookAndFeel());
			} catch (UnsupportedLookAndFeelException e) {}
			
			//��ȡ�����ļ�
			MainFrameAppearConfig mfac = null;
			try {
				mfac = programControlPort.loadMainFrameAppearConfig();
			} catch (NumberFormatException e) {
				mfac = MainFrameAppearConfig.DEFAULT_CONFIG;
			} catch (IOException e) {
				mfac = MainFrameAppearConfig.DEFAULT_CONFIG;
			}
			
			//������վ���ʼ��
			viewControlPort.init();
			moduleControlPort.init();
			
			//��ʾ����������
			viewControlPort.getMainFrameControlPort().applyAppearance(mfac);
			viewControlPort.getMainFrameControlPort().setVisible(true);
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#exitProgram()
		 */
		@Override
		public void exitProgram() {
			//TODO ʹ��StringField
			if(!startFlag) throw new IllegalStateException("����δ����");
			
			//���ؽ���
			viewControlPort.getMainFrameControlPort().setVisible(false);
			
			//�����������
			MainFrameAppearConfig config = viewControlPort.getMainFrameControlPort().getCurrentAppearance();
			try {
				programControlPort.saveMainFrameAppearConfig(config);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//���ø������������ս᷽����
			
			//�˳�����
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
