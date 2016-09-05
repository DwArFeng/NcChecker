package com.dwarfeng.ncc.module.nc;

import java.util.NoSuchElementException;

/**
 * NC�����б�
 * <p> ʵ�ָýӿ���ζ��ʵ������һ��NC�����б�
 * @author DwArFeng
 * @since 1.8
 */
public interface CodeSerial extends Iterable<Code>{
	
	/**
	 * ����ָ���кŵĴ��롣
	 * @param lineIndex ָ�����кš�
	 * @return ָ���кŶ�Ӧ�Ĵ��롣
	 * @throws NoSuchElementException ָ���Ĵ���������û��ָ����Ԫ�ء�
	 */
	public Code getCode(int lineNumber);
	
	/**
	 * ���ظô��������е����д��롣
	 * @return Nc�������������
	 */
	public int getTotle();
	
	/**
	 * ���ش����������ʽ�����صĴ����Ǹ����кŽ�������ġ�
	 * @return �����������ʽ��
	 */
	public Code[] toArray();
	
	/**
	 * �������Ĵ���������
	 * @return ���Ĵ���������
	 */
	public int getMaxLineNumber();
	
	/**
	 * ������С�Ĵ���������
	 * @return ��С�Ĵ���������
	 */
	public int getMinLineNumber();
	
}
