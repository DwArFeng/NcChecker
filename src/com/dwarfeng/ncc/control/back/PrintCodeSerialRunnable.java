package com.dwarfeng.ncc.control.back;

import java.io.PrintStream;

import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.model.nc.CodeSerial;
import com.dwarfeng.ncc.program.key.StringFieldKey;

/**
 * ��ӡ�����漰��Runnable����
 * <p> ������Ϊ�м价�ڡ�
 * @author DwArFeng
 * @since 1.8
 */
public class PrintCodeSerialRunnable extends AbstractCmr {
	
	//-----------------------------��������Ҫʹ�õĸ����ֶμ�ֵ------------------------------------
	
	private static final StringFieldKey KEY_START = StringFieldKey.OUT_NEWFILE_START;
	private static final StringFieldKey KEY_STATS = StringFieldKey.OUT_NEWFILE_STATS;
	
	//------------------------------------------------------------------------------------------------

	private final CodeSerial codeSerial;
	private final PrintStream printStream;
	
	/**
	 * ����һ������ָ���������У�ָ����ӡ���Ĵ�ӡ�������
	 * @param controlManager ָ���Ŀ��ƹ�������
	 * @param codeSerial ָ���Ĵ������С�
	 * @param printStream ָ���Ĵ�ӡ����
	 * @throws NullPointerException ������ڲ���Ϊ <code>null</code>��
	 */
	public PrintCodeSerialRunnable(NccControlManager controlManager, CodeSerial codeSerial,
			PrintStream printStream) {
		super(controlManager);
		this.codeSerial = codeSerial;
		this.printStream = printStream;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
	}

}
