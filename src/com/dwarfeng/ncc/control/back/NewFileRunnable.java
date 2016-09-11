package com.dwarfeng.ncc.control.back;

import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.module.nc.ArrayCodeSerial;
import com.dwarfeng.ncc.module.nc.Code;
import com.dwarfeng.ncc.module.nc.CodeSerial;
import com.dwarfeng.ncc.program.key.StringFieldKey;

/**
 * �½��ļ�����Ҫ��Runnable��
 * @author DwArFeng
 * @since 1.8
 */
public class NewFileRunnable extends AbstractCmr {
	
	//-----------------------------��������Ҫʹ�õĸ����ֶμ�ֵ------------------------------------
	
	private static final StringFieldKey KEY_START = StringFieldKey.OUT_NEWFILE_START;
	private static final StringFieldKey KEY_STATS = StringFieldKey.OUT_NEWFILE_STATS;
	
	//------------------------------------------------------------------------------------------------

	/**
	 * ����һ��ָ�����½��ļ������ж���
	 * @param controlManager ָ���ĳ����������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public NewFileRunnable(NccControlManager controlManager) {
		super(controlManager);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {			
		
		//�½�һ���յĴ�������
		CodeSerial codeSerial = new ArrayCodeSerial(new Code[0]);
		
		//��ǰ��ģ�������������������
		moduleControlPort.frontCp().setFrontCodeSerial(codeSerial, null, true);
		
		//����ͼ����Ⱦ����
		viewControlPort.frameCp().showCode(moduleControlPort.frontCp().getCodeSerial());
		viewControlPort.frameCp().noneFileMode(false);
		
		//����ͳ�Ʊ���
	
	}

}
