package com.dwarfeng.ncc.module.nc;

public interface Code{
	
	/**
	 * 返回该代码的文本形式。
	 * @return 该代码的文本形式。
	 */
	public String content();
	
	/**
	 * 返回代码的标签。
	 * @return 代码的标签。
	 */
	public CodeLabel getLabel();
	
	/**
	 * 设置代码的标签。
	 * @param label 代码的标签。
	 */
	public void setLabel(CodeLabel label);
	
}
