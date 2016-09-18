package com.dwarfeng.ncc.control.cps;

/**
 * 文件操作控制站。
 * @author DwArFeng
 * @since 1.8
 */
public interface FileCp {
	
	/**
	 * 关闭前端的文件。
	 * @throws NullPointerException 前端的文件并不存在。
	 */
	public void closeFrontFile();
	
	/**
	 * 新建一个前端的文件。
	 */
	public void newFrontFile();
	
	/**
	 * 打开NC文件。
	 */
	public void openNcFile();
	
	/**
	 * 保存前端的程序。
	 * @throws NullPointerException 前端文件并不存在。
	 */
	public void saveFrontFile();
	
	/**
	 * 另存为前端文件。
	 * @throws NullPointerException 前端文件并不存在。
	 */
	public void saveAsFrontFile();

}
