package com.dwarfeng.ncc.control.back;

import java.io.IOException;

import com.dwarfeng.dfunc.dt.CodeTimer;
import com.dwarfeng.dfunc.io.StringBuilderOutputStream;
import com.dwarfeng.dfunc.num.UnitTrans.Time;
import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.control.NccControlPort.Mode;
import com.dwarfeng.ncc.model.mut.StreamCodePrinter;
import com.dwarfeng.ncc.model.nc.CodeSerial;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.gui.DefaultProgressModel;
import com.dwarfeng.ncc.view.gui.ProgressModel;

/**
 * �л����༭ģʽ��Runnable��
 * @author DwArFeng
 * @since 1.8
 */
public class Toggle2EidtRunnable extends AbstractCmr{

	//-----------------------------��������Ҫʹ�õĸ����ֶμ�ֵ------------------------------------
	
	private static final StringFieldKey KEY_LOADING = StringFieldKey.PROGRESS_CODE_EDIT;
	private static final StringFieldKey KEY_START = StringFieldKey.OUT_EDIT_START;
	private static final StringFieldKey KEY_STATS= StringFieldKey.OUT_EDIT_STATS;
	private static final StringFieldKey KEY_SUSPEND= StringFieldKey.OUT_EDIT_SUSPEND;
	private static final StringFieldKey KEY_FAIL= StringFieldKey.OUT_EDIT_FAIL;
	
	//------------------------------------------------------------------------------------------------
	
	/**
	 * ����һ��ָ���ı༭ģʽ�Ŀ����ж���
	 * @param controlManager ָ���Ŀ��ƹ�������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public Toggle2EidtRunnable(NccControlManager controlManager) {
		super(controlManager);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		StringBuilderOutputStream out = null;
		
		//����༭����
		viewControlPort.frameCp().lockEdit();
		
		try{
			//��ʼ��Ҫ�ı���
			CodeSerial codeSerial = moduleControlPort.frontCp().getFrontCodeSerial();
			out = new StringBuilderOutputStream();
			final StreamCodePrinter codePrinter = new StreamCodePrinter(codeSerial, out);
			
			//�����ʼ��Ϣ
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_START),
					codeSerial.getTotle()
			);
			
			//���ɽ���ģ��
			ProgressModel progressModel = new DefaultProgressModel();
			progressModel.setIndeterminate(false);
			progressModel.setLabelText(programAttrSet.getStringField(KEY_LOADING));
			progressModel.setMaximum(codePrinter.getTotleCode());
			progressModel.setValue(0);
			
			//���ý���ģ��
			viewControlPort.frameCp().startProgressMonitor(progressModel);
			
			//���������ʱ����
			CodeTimer cti = new CodeTimer();
			cti.start();
			
			//ѭ����ȡ����
			while(codePrinter.hasNext()){
				
				//����ֶ�ֹͣ������ֹ���̡�
				if(progressModel.isSuspend()){
					viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_SUSPEND));
					return;
				}
				
				//����ѭ����ȡ���롣
				codePrinter.printNext();
				progressModel.setValue(codePrinter.currentValue());
				
			}
			
			//����ѭ����ֹͣ�������ʱ
			progressModel.end();
			cti.stop();
			
			//�����ı�
			out.flush();
			String text = out.toString();
			
			//��ͼת��Ϊ�༭ģʽ
			viewControlPort.frameCp().toggleMode(Mode.EDIT);
			//��Ⱦ�ı�
			viewControlPort.frameCp().setEditText(text);
			//���ɱ���
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_STATS),
					cti.getTime(Time.MS)
			);
			
		}catch(Exception e){
			e.printStackTrace();
			viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_FAIL));
			return;
		}finally{
			viewControlPort.frameCp().unlockEdit();
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}


}
