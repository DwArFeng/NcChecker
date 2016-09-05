package com.dwarfeng.ncc.control;


/**
 * 文件控制站。
 * @author DwArFeng
 * @since 1.8
 */
public interface FileControlPort{
	
	/**
	 * 通知程序管理器需要打开某个NC文件。
	 */
	public void openNcFile();
	
}
