package com.dwarfeng.ncc.program;

/**
 * 拥有属性集合引用的对象。
 * @author DwArFeng
 * @since 1.8
 */
public interface AttRefable {

	/**
	 * 获取该对象中的属性集合。
	 * @return 属性集合。
	 */
	public NccProgramAttrSet getAttrSet();
	
}
