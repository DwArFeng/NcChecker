package com.dwarfeng.ncc.control;

import java.io.File;

import com.dwarfeng.dfunc.prog.mvc.ControlPort;
import com.dwarfeng.ncc.control.cps.CodeCp;
import com.dwarfeng.ncc.control.cps.FileCp;

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
	 * ��������
	 * @throws IllegalStateException �������Ѿ���������δ�ر�ʱ�ظ����ô˷�����
	 */
	public void startProgram();
	
	/**
	 * �ύָ���ı��Ĵ��롣
	 * @param code ָ���Ĵ��롣
	 */
	public void commitCode(String code);
	
//	/**
//	 * ���������д�ӡ��ĳ��������ϡ�
//	 * @param codeSerial ָ���Ĵ������С�
//	 * @param out ָ�����������
//	 * @throws IllegalStateException ����δ����ʱ���ô˷�����
//	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
//	 */
//	public void printCodeSerial(CodeSerial codeSerial, OutputStream out);
	
	/**
	 * ��ȡ�ļ�����վ��
	 * @return �ļ�����վ��
	 * @throws IllegalStateException ����δ����ʱ���ô˷�����
	 */
	public FileCp fileCp();
	
	/**
	 * ��ȡ�������վ��
	 * @return �������վ��
	 * @throws IllegalStateException ����δ����ʱ���ô˷�����
	 */
	public CodeCp codeCp();
	
}
