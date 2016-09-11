package com.dwarfeng.ncc.program;

import java.awt.Image;

import com.dwarfeng.dfunc.prog.mvc.ProgramAttrSet;
import com.dwarfeng.ncc.program.key.ExceptionFieldKey;
import com.dwarfeng.ncc.program.key.ImageKey;
import com.dwarfeng.ncc.program.key.StringFieldKey;

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
	public String getStringField(StringFieldKey key);
	
	/**
	 * 获得程序中对应键值的异常字段。
	 * @param key 指定的键值。
	 * @return 对应的异常字段。
	 */
	public String getExceptionField(ExceptionFieldKey key);
	
	/**
	 * 获得程序中对应键值的图片。
	 * @param key 图片键值。
	 * @return 图片。
	 */
	public Image getImage(ImageKey key);
	
}
