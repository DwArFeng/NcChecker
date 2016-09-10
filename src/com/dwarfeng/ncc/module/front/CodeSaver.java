package com.dwarfeng.ncc.module.front;

/**
 * 代码保存器。
 * @author DwArFeng
 * @since 1.8
 */
public interface CodeSaver {
	
	/**
	 * 获取总代码量（工作量）。
	 * @return 总代码量。
	 */
	public int getTotleCode();
	
	/**
	 * 是否还有下一段代码。
	 * @return 是否还有下一段代码。
	 */
	public boolean hasNext();
	
	/**
	 * 保存下一段代码。
	 */
	public void saveNext();
	
}
