package com.dwarfeng.ncc.control.cps;

/**
 * 文件操作控制站。
 * @author DwArFeng
 * @since 1.8
 */
public interface FileCp {
	
	/**
	 * 关闭前端的文件。
	 * @throws IllegalStateException 程序未启动时调用此方法。
	 * @throws NullPointerException 前端的文件并不存在。
	 */
	public void closeFrontFile();
	
	/**
	 * 新建一个前端的文件。
	 * @throws IllegalStateException 程序未启动时调用此方法。
	 */
	public void newFrontFile();
	
	/**
	 * 打开NC文件。
	 * @throws IllegalStateException 程序未启动时调用此方法。
	 */
	public void openNcFile();
	
	/**
	 * 保存前端的程序。
	 * @throws IllegalStateException 程序未启动时调用此方法。
	 * @throws NullPointerException 前端文件并不存在。
	 */
	public void saveFrontFile();

}
