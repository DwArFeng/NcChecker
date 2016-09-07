package com.dwarfeng.ncc.control;

import com.dwarfeng.dfunc.prog.mvc.ControlPort;
import com.dwarfeng.ncc.module.front.Page;

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
	
	/**
	 * 改变代码页面。
	 * @param page 代码页面。
	 * @throws IllegalStateException 程序未启动时调用此方法。
	 * @param flag 为 <code>true</code>时显示代码顶部，为 <code>false</code>时返回代码底部。
	 */
	public void toggleCodePage(Page page,boolean flag);
	
}
