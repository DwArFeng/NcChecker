package com.dwarfeng.ncc.module.front;

import com.dwarfeng.ncc.module.nc.CodeSerial;

/**
 * 前端模型控制站。
 * @author DwArFeng
 * @since 1.8
 */
public interface FrontModuleControlPort{
	
	/**
	 * 获取前台的代码列表。
	 * @return 前台的代码列表，如果没有，则返回 <code>null</code>。
	 */
	public CodeSerial getFrontCodeSerial();
	
	/**
	 * 设置前台的代码列表。
	 * @param codeSerial 指定的代码列表，如果要移除前台代码，则指定 <code>null</code>。
	 */
	public void setFrontCodeSerial(CodeSerial codeSerial);

}
