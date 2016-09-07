package com.dwarfeng.ncc.control;

import com.dwarfeng.dfunc.prog.mvc.ControlPort;
import com.dwarfeng.ncc.module.front.Page;

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
	
	/**
	 * �ı����ҳ�档
	 * @param page ����ҳ�档
	 * @throws IllegalStateException ����δ����ʱ���ô˷�����
	 * @param flag Ϊ <code>true</code>ʱ��ʾ���붥����Ϊ <code>false</code>ʱ���ش���ײ���
	 */
	public void toggleCodePage(Page page,boolean flag);
	
}
