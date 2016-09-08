package com.dwarfeng.ncc.control.back;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	
	private static final StringFieldKey KEY_LOADING = StringFieldKey.FILE_NOWLOADING;
	
	//------------------------------------------------------------------------------------------------
	
	private final CodeLoader codeLoader;
	
	/**
	 * ����һ��ָ���Ĵ��ļ������ж���
	 * @param controlManager ָ���ĳ����������
	 * @param codeLoader NC�����ȡ����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public OpenFileRunnable(NccControlManager controlManager, CodeLoader codeLoader) {
		super(controlManager);
		Objects.requireNonNull(codeLoader);
		this.codeLoader = codeLoader;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try{
			viewControlPort.frameCp().lockEdit();
			viewControlPort.progCp().startMonitor();
			viewControlPort.progCp().setMessage(programAttrSet.getStringField(KEY_LOADING));
			viewControlPort.progCp().setIndeterminate(true);
			int i = 1;
			final List<Code> codes = new ArrayList<Code>();
			while(codeLoader.hasNext()){
				//����ֶ�ֹͣ������ֹ����
				if(viewControlPort.progCp().isSuspend()){
					return;
				}
				
				//����ѭ����ȡ���롣
				codes.add(codeLoader.loadNext());
				viewControlPort.progCp().setMessage(programAttrSet.getStringField(KEY_LOADING) + i);
				i++;
			}
			//ѭ�������������ȡ��ϡ�

			CodeSerial codeList = new ArrayCodeList(codes.toArray(new Code[0]));
			//����������Ϊǰ�ˡ�
			moduleControlPort.frontCp().setFrontCodeSerial(codeList);
			
			viewControlPort.frameCp().showCode(moduleControlPort.frontCp().getCodeSerial());
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
