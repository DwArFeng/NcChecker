package com.dwarfeng.ncc.program;

import com.dwarfeng.dfunc.prog.mvc.ProgramControlPort;
import com.dwarfeng.ncc.program.conf.ConfigCp;

/**
 * ���ش�����֤�����еĳ�����Ƽ��ϡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface NccProgramControlPort extends ProgramControlPort{
	
	/**
	 * ��ʼ��
	 * @throws IllegalStateException �ظ���ʼ����
	 */
	public void init();
	
	/**
	 * ���̨�̷߳���һ����������
	 * @param runnable �����ж���
	 * @throws IllegalStateException ���������û��ʼ����
	 */
	public void backInvoke(Runnable runnable);
	
	/**
	 * ������ÿ���վ��
	 * @return ���ÿ���վ��
	 * @throws IllegalStateException ���������û�г�ʼ����
	 */
	public ConfigCp configCp();
	
}
