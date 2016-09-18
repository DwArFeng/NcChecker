package com.dwarfeng.ncc.model.nc;

/**
 * 代码标签。
 * @author DwArFeng
 * @since 1.8
 */
public interface CodeLabel {

	/**
	 * 返回代码的行数。
	 * @return 代码的行数。
	 */
	public int getLineIndex();
	
	/**
	 * 返回该代码是否被标记。
	 * @return 是否被标记。
	 */
	public boolean isMarked();
	
}
