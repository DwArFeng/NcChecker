package com.dwarfeng.ncc.program;

import com.dwarfeng.dfunc.prog.mvc.ProgramAttrSet;
import com.dwarfeng.ncc.program.key.ExceptionFieldKey;
import com.dwarfeng.ncc.program.key.StringFieldKey;

/**
 * ���ش�����֤�����еĳ������Լ��ϡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface NccProgramAttrSet extends ProgramAttrSet {
	
	/**
	 * ��ó����ж�Ӧ��ֵ���ֶΡ�
	 * @param key ָ���ļ�ֵ��
	 * @return ��Ӧ���ֶΡ�
	 */
	public String getStringField(StringFieldKey key);
	
	/**
	 * ��ó����ж�Ӧ��ֵ���쳣�ֶΡ�
	 * @param key ָ���ļ�ֵ��
	 * @return ��Ӧ���쳣�ֶΡ�
	 */
	public String getExceptionField(ExceptionFieldKey key);
	
}
