package com.dwarfeng.ncc.control.back;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.dwarfeng.dfunc.dt.CodeTimer;
import com.dwarfeng.dfunc.num.UnitTrans.Time;
import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.module.front.CodeLoader;
import com.dwarfeng.ncc.module.nc.ArrayCodeList;
import com.dwarfeng.ncc.module.nc.Code;
import com.dwarfeng.ncc.module.nc.CodeSerial;
import com.dwarfeng.ncc.program.key.StringFieldKey;

/**
 * ���ļ�����Ҫ��Runnable��
 * @author DwArFeng
 * @since 1.8
 */
public final class OpenFileRunnable extends AbstractCmr implements Runnable {

	//-----------------------------��������Ҫʹ�õĸ����ֶμ�ֵ------------------------------------
	
	private static final StringFieldKey KEY_LOADING = StringFieldKey.PROGRESS_FILE_NOWLOADING;
	private static final StringFieldKey KEY_LOADSTART = StringFieldKey.OUT_LOADFILE_START;
	private static final StringFieldKey KEY_LOADSTATS= StringFieldKey.OUT_LOADFILE_STATS;
	
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
		try{
			//����༭������
			viewControlPort.frameCp().lockEdit();
			//���������Ϣ
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_LOADSTART),
					file.getAbsolutePath()
			);
			//��ʼ����
			viewControlPort.progCp().startMonitor();
			//���ü��ӱ�ǩ
			viewControlPort.progCp().setMessage(programAttrSet.getStringField(KEY_LOADING));
			//����ģʽΪ����ģʽ
			viewControlPort.progCp().setIndeterminate(true);
			
			final List<Code> codes = new ArrayList<Code>();
			
			//���������ʱ����
			CodeTimer cti = new CodeTimer();
			cti.start();
			//ѭ����ȡ����
			for(int i = 1 ; codeLoader.hasNext() ; i++){
				
				//����ֶ�ֹͣ������ֹ����
				if(viewControlPort.progCp().isSuspend()){
					return;
				}
				
				//����ѭ����ȡ���롣
				codes.add(codeLoader.loadNext());
				viewControlPort.progCp().setMessage(programAttrSet.getStringField(KEY_LOADING) + i);
				
			}
			cti.stop();
			
			//ѭ�������������ȡ��ϡ�
			//���ɴ���ʵ��
			CodeSerial codeList = new ArrayCodeList(codes.toArray(new Code[0]));
			//����������Ϊǰ�ˡ�
			moduleControlPort.frontCp().setFrontCodeSerial(codeList, file);
			//����ͼ����Ⱦ���롣
			viewControlPort.frameCp().showCode(moduleControlPort.frontCp().getCodeSerial());
			viewControlPort.frameCp().noneFileMode(false);
			//���ɱ���
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_LOADSTATS),
					codeList.getTotle(),
					cti.getTime(Time.MS)
			);
			
		}catch(Exception e){
			e.printStackTrace();
			return;
		}finally{
			viewControlPort.frameCp().unlockEdit();
			viewControlPort.progCp().endMonitor();
			try {
				codeLoader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
