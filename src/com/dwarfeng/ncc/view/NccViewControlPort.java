package com.dwarfeng.ncc.view;

import com.dwarfeng.dfunc.prog.mvc.ViewControlPort;

/**
 * 数控代码验证程序的视图控制端口。
 * @author DwArFeng
 * @since 1.8
 */
public interface NccViewControlPort extends ViewControlPort {
	
	/**
	 * 初始化。
	 * @throws IllegalStateException 重复初始化时抛出异常。
	 */
	public void init();
	
	/**
	 * 设置主界面是否可见。
	 * @param aFlag 主界面是否可见。
	 * @throws IllegalStateException 当管理器还没有初始化的时候抛出异常。
	 */
	public void setMainFrameVisible(boolean aFlag);

}
