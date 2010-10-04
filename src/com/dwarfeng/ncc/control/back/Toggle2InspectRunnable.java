package com.dwarfeng.ncc.control.back;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.dwarfeng.dfunc.dt.CodeTimer;
import com.dwarfeng.dfunc.io.StringInputStream;
import com.dwarfeng.dfunc.num.UnitTrans.Time;
import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.control.NccControlPort.Mode;
import com.dwarfeng.ncc.model.front.CodeLoader;
import com.dwarfeng.ncc.model.mut.StreamCodeLoader;
import com.dwarfeng.ncc.model.nc.ArrayCodeSerial;
import com.dwarfeng.ncc.model.nc.Code;
import com.dwarfeng.ncc.model.nc.CodeSerial;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.gui.DefaultProgressModel;
import com.dwarfeng.ncc.view.gui.NotifyCp.AnswerType;
import com.dwarfeng.ncc.view.gui.ProgressModel;

/**
 * �л����鿴ģʽ��Runnable��
 * @author DwArFeng
 * @since 1.8
 */
public class Toggle2InspectRunnable extends AbstractCmr {
	
	//-----------------------------��������Ҫʹ�õĸ����ֶμ�ֵ------------------------------------
	
	private static final StringFieldKey KEY_LOADING = StringFieldKey.PROGRESS_COMMIT;
	private static final StringFieldKey KEY_START = StringFieldKey.OUT_COMMIT_START;
	private static final StringFieldKey KEY_STATS= StringFieldKey.OUT_COMMIT_STATS;
	private static final StringFieldKey KEY_SUSPEND= StringFieldKey.OUT_COMMIT_SUSPEND;
	private static final StringFieldKey KEY_FAIL= StringFieldKey.OUT_COMMIT_FAIL;
	
	//------------------------------------------------------------------------------------------------

	private final AnswerType answerType;
	
	/**
	 * ����һ��ָ���Ĳ鿴ģʽ�Ŀ����ж���
	 * @param controlManager ָ���Ŀ��ƹ�������
	 * @param ignoreAnswer �Ƿ���Իش�
	 * @param type �ش������
	 * @throws NullPointerException �����Իش������»ش�Ϊ <code>null</code>��
	 */
	public Toggle2InspectRunnable(
			NccControlManager controlManager,AnswerType type) {
		super(controlManager);
		Objects.requireNonNull(type);
		this.answerType = type;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		switch (answerType) {
			case CANCEL:
				directEdit();
				return;
			case NO:
				directInspect();
				return;
			case YES:
				commitInspect();
				return;
		}
	}

	private void commitInspect() {
		InputStream in = new StringInputStream(viewControlPort.frameCp().getEditText());
		CodeLoader codeLoader = null;
		
		//����༭����
		viewControlPort.frameCp().lockEdit();
		
		try{
			//��ʼ��Ҫ�ı���
			codeLoader = new StreamCodeLoader(in);
			final List<Code> codes = new ArrayList<Code>();
			
			//�����ʼ��Ϣ
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_START),
					viewControlPort.frameCp().getEditLine()
			);
			
			//���ɽ���ģ��
			ProgressModel progressModel = new DefaultProgressModel();
			progressModel.setIndeterminate(false);
			progressModel.setLabelText(programAttrSet.getStringField(KEY_LOADING));
			progressModel.setMaximum(viewControlPort.frameCp().getEditLine());
			progressModel.setValue(0);
			
			//���ý���ģ��
			viewControlPort.frameCp().startProgressMonitor(progressModel);
			
			//���������ʱ����
			CodeTimer cti = new CodeTimer();
			cti.start();
			
			//ѭ����ȡ����
			while(codeLoader.hasNext()){
				
				//����ֶ�ֹͣ������ֹ���̡�
				if(progressModel.isSuspend()){
					viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_SUSPEND));
					viewControlPort.frameCp().knockForMode(Mode.EDIT);
					return;
				}
				
				//����ѭ����ȡ���롣
				codes.add(codeLoader.loadNext());
				progressModel.setValue(codeLoader.currentValue());
				
			}
			
			//����ѭ����ֹͣ�������ʱ
			progressModel.end();
			cti.stop();
			
			//���ɴ�������
			final CodeSerial codeSerial = new ArrayCodeSerial(codes.toArray(new Code[0]));
			//�ύ����
			modelControlPort.frontCp().commitCodeSerial(codeSerial);
			//��ͼת��Ϊ�༭ģʽ
			viewControlPort.frameCp().knockForMode(Mode.INSPECT);
			//��Ⱦ��������
			viewControlPort.frameCp().showCode(modelControlPort.frontCp().getFrontCodeSerial());
			//���ɱ���
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_STATS),
					cti.getTime(Time.MS)
			);
			
		}catch(Exception e){
			e.printStackTrace();
			viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_FAIL));
			viewControlPort.frameCp().knockForMode(Mode.EDIT);
			return;
		}finally{
			viewControlPort.frameCp().unlockEdit();
			if(codeLoader != null){
				try {
					codeLoader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void directEdit() {
		viewControlPort.frameCp().knockForMode(Mode.EDIT);
	}

	private void directInspect() {
		viewControlPort.frameCp().setEditText("");
		viewControlPort.frameCp().knockForMode(Mode.INSPECT);
	}

	
}
