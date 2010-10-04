package com.dwarfeng.ncc.control.back;

import com.dwarfeng.dfunc.dt.CodeTimer;
import com.dwarfeng.dfunc.num.UnitTrans.Time;
import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.model.nc.ArrayCodeSerial;
import com.dwarfeng.ncc.model.nc.Code;
import com.dwarfeng.ncc.model.nc.CodeSerial;
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
		viewControlPort.frameCp().lockEdit();
		try{
			//�����Ϣ
			viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_START));
			
			CodeTimer timer = new CodeTimer();
			timer.start();
			//�½�һ���յĴ�������
			CodeSerial codeSerial = new ArrayCodeSerial(new Code[0]);
			timer.stop();
			
			//��ǰ��ģ�������������������
			modelControlPort.frontCp().setFrontCodeSerial(codeSerial, null, true);
			
			//����ͼ����Ⱦ����
			viewControlPort.frameCp().showCode(modelControlPort.frontCp().getCodeSerial());
			viewControlPort.frameCp().noneFileMode(false);
			
			//����ͳ�Ʊ���
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_STATS), timer.getTime(Time.MS));
			
		}catch(Exception e){
			
			e.printStackTrace();
			return;
			
		}finally{
			viewControlPort.frameCp().unlockEdit();
		}
		
	}

}
