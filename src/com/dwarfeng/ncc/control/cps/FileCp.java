package com.dwarfeng.ncc.control.cps;

/**
 * �ļ���������վ��
 * @author DwArFeng
 * @since 1.8
 */
public interface FileCp {
	
	/**
	 * �ر�ǰ�˵��ļ���
	 * @throws NullPointerException ǰ�˵��ļ��������ڡ�
	 */
	public void closeFrontFile();
	
	/**
	 * �½�һ��ǰ�˵��ļ���
	 */
	public void newFrontFile();
	
	/**
	 * ��NC�ļ���
	 */
	public void openNcFile();
	
	/**
	 * ����ǰ�˵ĳ���
	 * @throws NullPointerException ǰ���ļ��������ڡ�
	 */
	public void saveFrontFile();
	
	/**
	 * ���Ϊǰ���ļ���
	 * @throws NullPointerException ǰ���ļ��������ڡ�
	 */
	public void saveAsFrontFile();

}
