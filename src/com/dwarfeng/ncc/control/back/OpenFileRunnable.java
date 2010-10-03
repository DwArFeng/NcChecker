package com.dwarfeng.ncc.control.back;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.dwarfeng.dfunc.dt.CodeTimer;
import com.dwarfeng.dfunc.num.UnitTrans.Time;
import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.model.front.CodeLoader;
import com.dwarfeng.ncc.model.nc.ArrayCodeSerial;
import com.dwarfeng.ncc.model.nc.Code;
import com.dwarfeng.ncc.model.nc.CodeSerial;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.gui.DefaultProgressModel;
import com.dwarfeng.ncc.view.gui.ProgressModel;

/**
 * ���ļ�����Ҫ��Runnable��
 * @author DwArFeng
 * @since 1.8
 */
public final class OpenFileRunnable extends AbstractCmr implements Runnable {

	//-----------------------------��������Ҫʹ�õĸ����ֶμ�ֵ------------------------------------
	
	private static final StringFieldKey KEY_LOADING = StringFieldKey.PROGRESS_FILE_NOWLOADING;
	private static final StringFieldKey KEY_START = StringFieldKey.OUT_LOADFILE_START;
	private static final StringFieldKey KEY_STATS= StringFieldKey.OUT_LOADFILE_STATS;
	private static final StringFieldKey KEY_SUSPEND= StringFieldKey.OUT_LOADFILE_SUSPEND;
	private static final StringFieldKey KEY_FAIL= StringFieldKey.OUT_LOADFILE_FAIL;
	
	//------------------------------------------------------------------------------------------------
	
	private final CodeLoader codeLoader;
	private final File file;
	
	/**
	 * ����һ��ָ���Ĵ��ļ������ж���
	 * @param controlManager ָ���ĳ����������
	 * @param codeLoader NC�����ȡ����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public OpenFileRunnable(NccControlManager controlManager, CodeLoader codeLoader, File file) {
		super(controlManager);
		Objects.requireNonNull(codeLoader);
		this.codeLoader = codeLoader;
		this.file = file;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		//����༭������
		viewControlPort.frameCp().lockEdit();
		
		try{
			//���������Ϣ
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_START),
					file.getAbsolutePath()
			);
			
			//���ɽ���ģ��
			ProgressModel progressModel = new DefaultProgressModel();
			progressModel.setIndeterminate(true);
			progressModel.setLabelText(programAttrSet.getStringField(KEY_LOADING));
			
			//���ý���ģ��
			viewControlPort.frameCp().startProgressMonitor(progressModel);
			
			final List<Code> codes = new ArrayList<Code>();
			
			//���������ʱ����
			CodeTimer cti = new CodeTimer();
			cti.start();
			
			//ѭ����ȡ����
			for(;codeLoader.hasNext();){
				
				//����ֶ�ֹͣ������ֹ���̡�
				if(progressModel.isSuspend()){
					viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_SUSPEND));
					return;
				}
				
				//����ѭ����ȡ���롣
				codes.add(codeLoader.loadNext());
				progressModel.setLabelText(programAttrSet.getStringField(KEY_LOADING) + codeLoader.currentValue());
				
			}
			
			//����ѭ����ֹͣ�������ʱ
			progressModel.end();
			cti.stop();
			
			//���ɴ���ʵ��
			CodeSerial codeList = new ArrayCodeSerial(codes.toArray(new Code[0]));
			//����������Ϊǰ�ˡ�
			moduleControlPort.frontCp().setFrontCodeSerial(codeList, file, false);
			//����ͼ����Ⱦ���롣
			viewControlPort.frameCp().showCode(moduleControlPort.frontCp().getCodeSerial());
			viewControlPort.frameCp().noneFileMode(false);
			//���ɱ���
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_STATS),
					codeList.getTotle(),
					cti.getTime(Time.MS)
			);
			
		}catch(Exception e){
			e.printStackTrace();
			viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_FAIL));
			return;
		}finally{
			viewControlPort.frameCp().unlockEdit();
			try {
				codeLoader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
