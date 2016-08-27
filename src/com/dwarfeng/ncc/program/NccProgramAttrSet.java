package com.dwarfeng.ncc.program;

import com.dwarfeng.dfunc.prog.mvc.ProgramAttrSet;

/**
 * 数控代码验证程序中的程序属性集合。
 * @author DwArFeng
 * @since 1.8
 */
public interface NccProgramAttrSet extends ProgramAttrSet {
	
	/**
	 * 获得程序中对应键值的字段。
	 * @param key 指定的键值。
	 * @return 对应的字段。
	 */
	public String getStringField(StringFieldKeys key);
	
	/**
	 * 获得程序中对应键值的异常字段。
	 * @param key 指定的键值。
	 * @return 对应的异常字段。
	 */
	public String getExceptionField(ExceptionFieldKeys key);

}
