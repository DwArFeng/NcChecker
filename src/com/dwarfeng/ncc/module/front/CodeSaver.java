package com.dwarfeng.ncc.module.front;

/**
 * ���뱣������
 * @author DwArFeng
 * @since 1.8
 */
public interface CodeSaver {
	
	/**
	 * ��ȡ�ܴ�����������������
	 * @return �ܴ�������
	 */
	public int getTotleCode();
	
	/**
	 * �Ƿ�����һ�δ��롣
	 * @return �Ƿ�����һ�δ��롣
	 */
	public boolean hasNext();
	
	/**
	 * ������һ�δ��롣
	 */
	public void saveNext();
	
}
