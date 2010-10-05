package com.dwarfeng.ncc.control;

import java.io.File;

import com.dwarfeng.dfunc.prog.mvc.ControlPort;
import com.dwarfeng.ncc.control.cps.CodeCp;
import com.dwarfeng.ncc.control.cps.FileCp;

/**
 * 数控代码验证程序中的控制端口。
 * @author DwArFeng
 * @since 1.8
 */
public interface NccControlPort extends ControlPort {
	
	/**
	 * 关闭程序。
	 * @throws IllegalStateException 程序未启动时调用此方法。
	 */
	public void exitProgram();
	
	/**
	 * 启动程序。
	 * @throws IllegalStateException 当程序已经启动，还未关闭时重复调用此方法。
	 */
	public void startProgram();
	
	/**
	 * 提交指定文本的代码。
	 * @param code 指定的代码。
	 */
	public void commitCode(String code);
	
//	/**
//	 * 将代码序列打印在某个输出流上。
//	 * @param codeSerial 指定的代码序列。
//	 * @param out 指定的输出流。
//	 * @throws IllegalStateException 程序未启动时调用此方法。
//	 * @throws NullPointerException 入口参数为 <code>null</code>。
//	 */
//	public void printCodeSerial(CodeSerial codeSerial, OutputStream out);
	
	/**
	 * 获取文件控制站。
	 * @return 文件控制站。
	 * @throws IllegalStateException 程序未启动时调用此方法。
	 */
	public FileCp fileCp();
	
	/**
	 * 获取代码控制站。
	 * @return 代码控制站。
	 * @throws IllegalStateException 程序未启动时调用此方法。
	 */
	public CodeCp codeCp();
	
}
