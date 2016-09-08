package com.dwarfeng.ncc.view.gui;

import com.dwarfeng.ncc.module.nc.CodeSerial;
import com.dwarfeng.ncc.program.conf.MfAppearConfig;

/**
 * 程序框架控制站。
 * <p> 该控制站可以控制程序框架内的方法。
 * @author DwArFeng
 * @since 1.8
 */
public interface FrameCp {
	
	/**
	 * 锁定编辑功能。
	 * <p> 该方法是在对文件进行读取或其它需要场合临时锁定编辑功能，该方法不应该长时间被使用。
	 */
	public void lockEdit();
	
	/**
	 * 解锁编辑功能。
	 */
	public void unlockEdit();
	
	/**
	 * 设置显示模式是否为无文件模式。
	 * <p> 在无文件模式下，诸如保存、另存为等功能将被禁用。
	 * @param aFlag
	 */
	public void noneFileMode(boolean aFlag);
	
	/**
	 * 应用指定设置下的外观。
	 * @param config 指定的外观设置。
	 */
	public void applyAppearanceConfig(MfAppearConfig config);
	
	/**
	 * 获取当前的外观配置。
	 * @return 当前的外观配置。
	 */
	public MfAppearConfig getAppearanceConfig();
	
	/**
	 * 设置状态标签上的文本。
	 * @param message 状态标签上的文本。
	 * @param type 文本的类型。
	 */
	public void setStatusLabelMessage(String message,StatusLabelType type);
	
	/**
	 * 设置主界面是否可见。
	 * @param aFlag 主界面是否可见。
	 * @throws IllegalStateException 当管理器还没有初始化的时候抛出异常。
	 * @throws IllegalStateException 当主界面已经被关闭时抛出异常。
	 */
	public void setVisible(boolean aFlag);
	
	/**
	 * 显示指定的代码序列。
	 * @param codeSerial 显示指定的代码序列。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void showCode(CodeSerial codeSerial);
	
	/**
	 * 设置控制台的输出文本。
	 * @param message 控制台的输出文本。
	 */
	public void traceInConsole(String message);
	
}
