package com.dwarfeng.ncc.model.front;

import java.io.Closeable;
import java.io.IOException;

import com.dwarfeng.ncc.model.nc.Code;

/**
 * NC�����ȡ����
 * <p> �ܹ����������ж�ȡNC������ࡣ
 * @author DwArFeng
 * @since 1.8
 */
public interface CodeLoader extends Closeable {

	/**
	 * ������һ��NC���롣
	 * @return ��һ��NC���롣
	 * @throws IOException IOͨ���쳣�� 
	 */
	public Code loadNext() throws IOException;
	
	/**
	 * �ж��Ƿ�����һ��NC���롣
	 * @return �Ƿ�����һ�д��롣
	 * @throws IOException IO�쳣��
	 */
	public boolean hasNext() throws IOException;
	
	/**
	 * ���ص�ǰ�Ķ�ȡ����
	 * @return ��ǰ�Ľ��ȡ�
	 */
	public int currentValue();
	
}
