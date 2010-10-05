package com.dwarfeng.ncc.view.gui;

import com.dwarfeng.ncc.control.cps.CodeCp.CodeEidtMode;
import com.dwarfeng.ncc.model.nc.CodeSerial;
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
	 * @param aFlag 是否为无文件模式。
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
	 * 显示指定的代码序列，如果需要撤下代码，则入口参数使用 <code>null</code>。
	 * @param codeSerial 显示指定的代码序列。
	 */
	public void showCode(CodeSerial codeSerial);
	
	/**
	 * 在控制台上格式化输出指定的文本。
	 * @param format 指定的格式。
	 * @param args 参数集。
	 */
	public void traceInConsole(String format,Object... args);
	
	/**
	 * 开始进度监视。
	 * @param model 指定的进度模型。
	 * @throws NullPointerException 进度模型为 <code>null</code>。
	 */
	public void startProgressMonitor(ProgressModel model);
	
	/**
	 * 切换代码面板的模式。
	 * @param mode 指定的模式。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void knockForMode(CodeEidtMode mode);
	
	/**
	 * 设置编辑面板的文本。
	 * @param text 指定的文本。
	 */
	public void setEditText(String text);
	
	/**
	 * 返回编辑界面中文本的总行数。
	 * @return 编辑界面中文本的总行数。
	 */
	public int getEditLine();
	
	/**
	 * 获取编辑界面中的文本。
	 * @return 编辑界面中的文本。
	 */
	public String getEditText();
	
	/**
	 * 获取编辑模式是否需要提交的标记。
	 * @return 是否需要提交。
	 */
	public boolean needCommit();
	
	/**
	 * 通知视图模型编辑的代码已经提交。
	 */
	public void knockForCommit();
	
}
