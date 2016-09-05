package com.dwarfeng.ncc.view.gui;

import com.dwarfeng.ncc.program.conf.MfAppearConfig;

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
	public void applyAppearance(MfAppearConfig config);
	
	/**
	 * 获取当前的外观配置。
	 * @return 当前的外观配置。
	 */
	public MfAppearConfig getCurrentAppearance();
	
	/**
	 * 设置状态标签上的文本。
	 * @param message 状态标签上的文本。
	 * @param type 文本的类型。
	 */
	public void setStatusLabelMessage(String message,StatusLabelType type);
	
	/**
	 * 设置控制台的输出文本。
	 * @param message 控制台的输出文本。
	 */
	public void traceInConsole(String message);
	
}
