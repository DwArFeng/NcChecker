package com.dwarfeng.ncc.control;

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
	 * ��������
	 * @throws IllegalStateException �������Ѿ���������δ�ر�ʱ�ظ����ô˷�����
	 */
	public void startProgram();
	
}
