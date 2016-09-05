package com.dwarfeng.ncc.program.conf;

import java.io.IOException;

/**
 * ���ÿ���վ�㡣
 * @author DwArFeng
 * @since 1.8
 */
public interface ConfigControlPort {
	
	/**
	 * ����ָ����������á�
	 * @param config ָ����������á�
	 * @throws IOException IO�쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void saveMainFrameAppearConfig(MfAppearConfig config)throws IOException;
	
	/**
	 * ��ȡ�����ļ��е�������á�
	 * @return ������á�
	 * @throws IOException IO�쳣��
	 * @throws NumberFormatException ���ָ�ʽ�쳣���������ļ����ⲿ�����ƻ�ʱ���ܻ��׳���
	 */
	public MfAppearConfig loadMainFrameAppearConfig() throws IOException,NumberFormatException;
	
	/**
	 * ����ָ����ǰ��������á�
	 * @param config ָ����ǰ��������á�
	 * @throws IOException IO�쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void saveFrontModuleConfig(FrontModuleConfig config) throws IOException;
	
	/**
	 * ��ȡ�����ļ���ǰ�����á�
	 * @return ǰ��ģ�����á�
	 * @throws IOException IO�쳣��
	 * @throws NumberFormatException ���ָ�ʽ�쳣���������ļ����ⲿ�����ƻ�ʱ���ܻ��׳���
	 */
	public FrontModuleConfig loadFrontModuleConfig()throws IOException, NumberFormatException;

}
