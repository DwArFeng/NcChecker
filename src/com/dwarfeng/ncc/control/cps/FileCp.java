package com.dwarfeng.ncc.control.cps;

import java.io.File;

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
	 * 打开指定的NC文件。
	 * @param file 指定的文件，如果为 <code>null</code>，则询问需要打开的文件。
	 * @throws IllegalStateException 程序未启动时调用此方法。
	 */
	public void openNcFile(File file);
	
	/**
	 * 保存前端的程序。
	 * @throws IllegalStateException 程序未启动时调用此方法。
	 * @throws NullPointerException 前端文件并不存在。
	 */
	public void saveFrontFile();

}
