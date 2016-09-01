package com.dwarfeng.ncc.view.gui;

import com.dwarfeng.ncc.program.conf.MainFrameAppearConfig;

/**
 * 程序框架控制站。
 * <p> 该控制站可以控制程序框架内的方法。
 * @author DwArFeng
 * @since 1.8
 */
public interface NccFrameControlPort {

	/**
	 * 设置主界面是否可见。
	 * @param aFlag 主界面是否可见。
	 * @throws IllegalStateException 当管理器还没有初始化的时候抛出异常。
	 * @throws IllegalStateException 当主界面已经被关闭时抛出异常。
	 */
	public void setVisible(boolean aFlag);
	
	/**
	 * 应用指定设置下的外观。
	 * @param config 指定的外观设置。
	 */
	public void applyAppearance(MainFrameAppearConfig config);
	
	/**
	 * 获取当前的外观配置。
	 * @return 当前的外观配置。
	 */
	public MainFrameAppearConfig getCurrentAppearance();
	
}
