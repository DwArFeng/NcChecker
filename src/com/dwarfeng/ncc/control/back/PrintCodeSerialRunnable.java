package com.dwarfeng.ncc.control.back;

import java.io.PrintStream;

import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.model.nc.CodeSerial;
import com.dwarfeng.ncc.program.key.StringFieldKey;

/**
 * 打印代码涉及的Runnable对象。
 * <p> 该类作为中间环节。
 * @author DwArFeng
 * @since 1.8
 */
public class PrintCodeSerialRunnable extends AbstractCmr {
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final StringFieldKey KEY_START = StringFieldKey.OUT_NEWFILE_START;
	private static final StringFieldKey KEY_STATS = StringFieldKey.OUT_NEWFILE_STATS;
	
	//------------------------------------------------------------------------------------------------

	private final CodeSerial codeSerial;
	private final PrintStream printStream;
	
	/**
	 * 生成一个具有指定代码序列，指定打印流的打印代码对象。
	 * @param controlManager 指定的控制管理器。
	 * @param codeSerial 指定的代码序列。
	 * @param printStream 指定的打印流。
	 * @throws NullPointerException 任意入口参数为 <code>null</code>、
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
