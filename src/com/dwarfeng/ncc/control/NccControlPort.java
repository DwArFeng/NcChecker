package com.dwarfeng.ncc.control;

import com.dwarfeng.dfunc.prog.mvc.ControlPort;

/**
 * 数控代码验证程序中的控制端口。
 * @author DwArFeng
 * @since 1.8
 */
public interface NccControlPort extends ControlPort {
	
	/**
	 * 启动程序。
	 * @throws IllegalStateException 当程序已经启动，还未关闭时重复调用此方法。
	 */
	public void startProgram();
	
	/**
	 * 设置主界面是否可见。
	 * @param aFlag 主界面是否可见。
	 * @throws IllegalStateException 当程序还未启动时调用此方法。
	 */
	public void setMainFrameVisible(boolean aFlag);

}
