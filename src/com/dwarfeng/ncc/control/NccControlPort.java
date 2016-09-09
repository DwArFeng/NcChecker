package com.dwarfeng.ncc.control;

import java.io.File;

import com.dwarfeng.dfunc.prog.mvc.ControlPort;

/**
 * ���ش�����֤�����еĿ��ƶ˿ڡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface NccControlPort extends ControlPort {
	
	/**
	 * �رճ���
	 * @throws IllegalStateException ����δ����ʱ���ô˷�����
	 */
	public void exitProgram();
	
	/**
	 * ֪ͨ�����������Ҫ��ĳ��NC�ļ���
	 * @throws IllegalStateException ����δ����ʱ���ô˷�����
	 */
	public void openNcFile();
	
	/**
	 * ��ָ����NC�ļ���
	 * @param file ָ�����ļ���
	 * @throws IllegalStateException ����δ����ʱ���ô˷�����
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void openNcFile(File file);
	
	/**
	 * ��������
	 * @throws IllegalStateException �������Ѿ���������δ�ر�ʱ�ظ����ô˷�����
	 */
	public void startProgram();
	
	/**
	 * �ر�ǰ�˵ĳ���
	 * @throws IllegalStateException ����δ����ʱ���ô˷�����
	 * @throws NullPointerException ǰ�˵��ļ��������ڡ�
	 */
	public void closeFrontFile();
	
	/**
	 * ����ǰ�˵ĳ���
	 * @throws IllegalStateException ����δ����ʱ���ô˷�����
	 * @throws NullPointerException ǰ���ļ��������ڡ�
	 */
	public void saveFrontFile();
}
