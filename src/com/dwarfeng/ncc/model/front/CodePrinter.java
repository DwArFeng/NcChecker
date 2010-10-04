package com.dwarfeng.ncc.model.front;

import java.io.Closeable;
import java.io.IOException;

/**
 * �����ӡ����
 * @author DwArFeng
 * @since 1.8
 */
public interface CodePrinter extends Closeable{
	
	/**
	 * ��ȡ�ܴ�����������������
	 * @return �ܴ�������
	 */
	public int getTotleCode();
	
	/**
	 * �Ƿ�����һ�δ��롣
	 * @return �Ƿ�����һ�δ��롣
	 * @throws IOException IO�쳣��
	 */
	public boolean hasNext() throws IOException;
	
	/**
	 * ������һ�δ��롣
	 * @throws IOException IO�쳣��
	 */
	public void printNext() throws IOException;
	
	/**
	 * ���ص�ǰ�Ĵ�ӡ����
	 * @return ��ǰ�Ľ��ȡ�
	 */
	public int currentValue();
	
}
