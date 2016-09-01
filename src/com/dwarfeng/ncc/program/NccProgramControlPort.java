package com.dwarfeng.ncc.program;

import java.io.IOException;

import com.dwarfeng.dfunc.prog.mvc.ProgramControlPort;
import com.dwarfeng.ncc.program.conf.MainFrameAppearConfig;

/**
 * ���ش�����֤�����еĳ�����Ƽ��ϡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface NccProgramControlPort extends ProgramControlPort{
	
	/**
	 * ����ָ����������á�
	 * @param config ָ����������á�
	 * @throws IOException IO�쳣��
	 */
	public void saveMainFrameAppearConfig(MainFrameAppearConfig config)throws IOException;
	
	/**
	 * ��ȡ�����ļ��е�������á�
	 * <p> �������ļ���һֱ�������¡�������ȡ�����ļ������쳣ʱ����᷵��Ĭ�ϵ�����ļ���
	 * @return ������á�
	 * @throws IOException IO�쳣��
	 * @throws NumberFormatException ���ָ�ʽ�쳣���������ļ����ⲿ����ʱ���ܻ��׳���
	 */
	public MainFrameAppearConfig loadMainFrameAppearConfig() throws IOException,NumberFormatException;

	
}
