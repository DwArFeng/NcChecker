package com.dwarfeng.ncc.view;

import com.dwarfeng.dfunc.prog.mvc.ViewControlPort;
import com.dwarfeng.ncc.view.gui.FrameCp;
import com.dwarfeng.ncc.view.gui.NotifyCp;
import com.dwarfeng.ncc.view.gui.ProgCp;

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
	 * 返回程序框架的控制站。
	 * @return 程序框架控制站。
	 * @throws IllegalStateException 管理器还没有初始化的时候抛出异常。
	 */
	public FrameCp frameCp();
	
	/**
	 * 获取视图的提醒控制站。
	 * @return 提醒控制站
	 * @throws IllegalStateException 管理器还没有初始化的时候抛出此异常。
	 */
	public NotifyCp notifyCp();
	
	/**
	 * 获取视图模型的进度控制站。
	 * @return 进度控制站。
	 * @throws IllegalStateException 管理器还没有初始化的时候抛出此异常。
	 */
	public ProgCp progCp();

}
