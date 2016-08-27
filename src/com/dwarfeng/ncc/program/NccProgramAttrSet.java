package com.dwarfeng.ncc.program;

import com.dwarfeng.dfunc.prog.mvc.ProgramAttrSet;

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
	public String getStringField(StringFieldKeys key);
	
	/**
	 * ��ó����ж�Ӧ��ֵ���쳣�ֶΡ�
	 * @param key ָ���ļ�ֵ��
	 * @return ��Ӧ���쳣�ֶΡ�
	 */
	public String getExceptionField(ExceptionFieldKeys key);

}
