package com.dwarfeng.ncc.view.gui;

import com.dwarfeng.ncc.program.conf.MainFrameAppearConfig;

/**
 * �����ܿ���վ��
 * <p> �ÿ���վ���Կ��Ƴ������ڵķ�����
 * @author DwArFeng
 * @since 1.8
 */
public interface NccFrameControlPort {

	/**
	 * �����������Ƿ�ɼ���
	 * @param aFlag �������Ƿ�ɼ���
	 * @throws IllegalStateException ����������û�г�ʼ����ʱ���׳��쳣��
	 * @throws IllegalStateException ���������Ѿ����ر�ʱ�׳��쳣��
	 */
	public void setVisible(boolean aFlag);
	
	/**
	 * Ӧ��ָ�������µ���ۡ�
	 * @param config ָ����������á�
	 */
	public void applyAppearance(MainFrameAppearConfig config);
	
	/**
	 * ��ȡ��ǰ��������á�
	 * @return ��ǰ��������á�
	 */
	public MainFrameAppearConfig getCurrentAppearance();
	
}
