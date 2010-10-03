package com.dwarfeng.ncc.control;

import java.io.File;
import com.dwarfeng.dfunc.prog.mvc.ControlPort;

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
	 * 通知程序管理器需要打开某个NC文件。
	 * @throws IllegalStateException 程序未启动时调用此方法。
	 */
	public void openNcFile();
	
	/**
	 * 打开指定的NC文件。
	 * @param file 指定的文件。
	 * @throws IllegalStateException 程序未启动时调用此方法。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void openNcFile(File file);
	
	/**
	 * 启动程序。
	 * @throws IllegalStateException 当程序已经启动，还未关闭时重复调用此方法。
	 */
	public void startProgram();
	
	/**
	 * 关闭前端的程序。
	 * @throws IllegalStateException 程序未启动时调用此方法。
	 * @throws NullPointerException 前端的文件并不存在。
	 */
	public void closeFrontFile();
	
	/**
	 * 保存前端的程序。
	 * @throws IllegalStateException 程序未启动时调用此方法。
	 * @throws NullPointerException 前端文件并不存在。
	 */
	public void saveFrontFile();
	
	/**
	 * 新建一个前端的文件。
	 * @throws IllegalStateException 程序未启动时调用此方法。
	 */
	public void newFrontFile();
	
	/**
	 * 程序中的几种模式（针对代码面板）
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum Mode{
		
		INSPECT,
		EDIT,
	}
	
	/**
	 * 切换代码面板的模式
	 * @param mode 代码面板的模式。
	 * @throws IllegalStateException 程序未启动时调用此方法。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void toggleMode(Mode mode);
	
//	/**
//	 * 将代码序列打印在某个输出流上。
//	 * @param codeSerial 指定的代码序列。
//	 * @param out 指定的输出流。
//	 * @throws IllegalStateException 程序未启动时调用此方法。
//	 * @throws NullPointerException 入口参数为 <code>null</code>。
//	 */
//	public void printCodeSerial(CodeSerial codeSerial, OutputStream out);
	
}
