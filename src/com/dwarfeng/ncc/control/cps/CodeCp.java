package com.dwarfeng.ncc.control.cps;

/**
 * 代码控站。
 * @author DwArFeng
 * @since 1.8
 */
public interface CodeCp {

	/**
	 * 程序中的几种模式（针对代码面板）
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum CodeEditMode{
		/**查看模式*/
		INSPECT,
		/**编辑模式*/
		EDIT,
	}
	
	/**
	 * 尝试切换模式。
	 * @param mode 指定的模式。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void attemptToggleMode(CodeEditMode mode);
	
	
}
