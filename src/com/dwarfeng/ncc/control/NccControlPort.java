package com.dwarfeng.ncc.control;

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
	 * 启动程序。
	 * @throws IllegalStateException 当程序已经启动，还未关闭时重复调用此方法。
	 */
	public void startProgram();
	
}
