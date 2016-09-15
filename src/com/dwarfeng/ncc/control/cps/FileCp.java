package com.dwarfeng.ncc.control.cps;

/**
 * �ļ���������վ��
 * @author DwArFeng
 * @since 1.8
 */
public interface FileCp {
	
	/**
	 * �ر�ǰ�˵��ļ���
	 * @throws IllegalStateException ����δ����ʱ���ô˷�����
	 * @throws NullPointerException ǰ�˵��ļ��������ڡ�
	 */
	public void closeFrontFile();
	
	/**
	 * �½�һ��ǰ�˵��ļ���
	 * @throws IllegalStateException ����δ����ʱ���ô˷�����
	 */
	public void newFrontFile();
	
	/**
	 * ��NC�ļ���
	 * @throws IllegalStateException ����δ����ʱ���ô˷�����
	 */
	public void openNcFile();
	
	/**
	 * ����ǰ�˵ĳ���
	 * @throws IllegalStateException ����δ����ʱ���ô˷�����
	 * @throws NullPointerException ǰ���ļ��������ڡ�
	 */
	public void saveFrontFile();

}
