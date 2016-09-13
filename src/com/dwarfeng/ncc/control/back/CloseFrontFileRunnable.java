package com.dwarfeng.ncc.control.back;

import java.io.IOException;

import com.dwarfeng.dfunc.dt.CodeTimer;
import com.dwarfeng.dfunc.io.StringOutputStream;
import com.dwarfeng.dfunc.num.UnitTrans.Time;
import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.control.NccControlPort.Mode;
import com.dwarfeng.ncc.model.front.CodePrinter;
import com.dwarfeng.ncc.model.mut.StreamCodePrinter;
import com.dwarfeng.ncc.model.nc.CodeSerial;
import com.dwarfeng.ncc.view.gui.DefaultProgressModel;
import com.dwarfeng.ncc.view.gui.ProgressModel;

/**
 * �ر�ǰ̨�ļ������Runnable��
 * @author DwArFeng
 * @since 1.8
 */
public class CloseFrontFileRunnable extends AbstractCmr{

	private final boolean saveFlag;
	
	/**
	 * ����һ��ָ���Ĺر��ļ������ж���
	 * @param controlManager ָ���ĳ����������
	 * @param �Ƿ���Ҫ���档
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public CloseFrontFileRunnable(NccControlManager controlManager, boolean saveFlag) {
		super(controlManager);
		this.saveFlag = saveFlag;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
//		
//		StringOutputStream out = null;
//		CodePrinter codePrinter = null;
//		
//		//����༭����
//		viewControlPort.frameCp().lockEdit();
//		
//		try{
//			
//			if(saveFlag){
//				//��ʼ��Ҫ�ı���
//				CodeSerial codeSerial = modelControlPort.frontCp().getFrontCodeSerial();
//				out = new StringOutputStream();
//				
//				codePrinter = new StreamCodePrinter(codeSerial, out);
//				
//				//�����ʼ��Ϣ
//				viewControlPort.frameCp().traceInConsole(
//						programAttrSet.getStringField(KEY_START),
//						codeSerial.getTotle()
//				);
//				
//				//���ɽ���ģ��
//				ProgressModel progressModel = new DefaultProgressModel();
//				progressModel.setIndeterminate(false);
//				progressModel.setLabelText(programAttrSet.getStringField(KEY_LOADING));
//				progressModel.setMaximum(codePrinter.getTotleCode());
//				progressModel.setValue(0);
//				
//				//���ý���ģ��
//				viewControlPort.frameCp().startProgressMonitor(progressModel);
//				
//				//���������ʱ����
//				CodeTimer cti = new CodeTimer();
//				cti.start();
//				
//				//ѭ����ȡ����
//				while(codePrinter.hasNext()){
//					
//					//����ֶ�ֹͣ������ֹ���̡�
//					if(progressModel.isSuspend()){
//						viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_SUSPEND));
//						return;
//					}
//					
//					//����ѭ����ȡ���롣
//					codePrinter.printNext();
//					progressModel.setValue(codePrinter.currentValue());
//					
//				}
//				
//				//����ѭ����ֹͣ�������ʱ
//				progressModel.end();
//				cti.stop();
//				
//				//�����ı�
//				out.flush();
//				String text = out.toString();
//				
//				//��ͼת��Ϊ�༭ģʽ
//				viewControlPort.frameCp().knockForMode(Mode.EDIT);
//				//��Ⱦ�ı�
//				viewControlPort.frameCp().setEditText(text);
//				//���ɱ���
//				viewControlPort.frameCp().traceInConsole(
//						programAttrSet.getStringField(KEY_STATS),
//						cti.getTime(Time.MS)
//				);
//			}
//
//			//����ͼ�г��´����
//			viewControlPort.frameCp().showCode(null);
//			//�Ƴ�ǰ̨ģ���еĴ����
//			modelControlPort.frontCp().setFrontCodeSerial(null, null, false);
//			//������ͼΪ���ļ�ģʽ
//			viewControlPort.frameCp().noneFileMode(true);
//			
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_FAIL));
//			return;
//		}finally{
//			viewControlPort.frameCp().unlockEdit();
//			if(codePrinter != null){
//				try {
//					codePrinter.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}

	}

}
