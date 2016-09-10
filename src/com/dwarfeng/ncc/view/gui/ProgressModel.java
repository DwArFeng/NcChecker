package com.dwarfeng.ncc.view.gui;

/**
 * ����ģ�͡�
 * <p> ����ģ���ǽ���������ڼ��ӽ��ȣ�ά����������ά����һ��ģ�ͣ���ģ�͹����ڶ��߳�ģʽ�¡�
 * ά����ͨ���ڲ��ϵ��޸Ľ���ģ�͵����ԣ������ȼ������򲻶ϵĶ�ȡ����ģ�͵ĸ������ԣ��������ý�����
 * ���ǩ����ʽ��ʾ���������С�
 * <br> ��ģ����ƶ��̹߳��������еķ��������ڲ�ͬ����
 * @author DwArFeng
 * @since 1.8
 */
public interface ProgressModel {
	
	/**
	 * ����ģ�͵��ܹ�������
	 * <p> ��ֵ���κ�����²���С�ڵ�ǰ���Ȼ�1��
	 * @return ģ�͵��ܹ�������
	 */
	public int getMaximum();
	
	/**
	 * ����ģ�͵��ܹ�������
	 * @param val ָ��ֵ��
	 */
	public void setMaximum(int val);
	
	/**
	 * ����ģ�͵ĵ�ǰ���ȡ�
	 * <p> ��ֵ���κ�����²���С��0����ڵ�ǰ���ȡ�
	 * @return ģ�͵Ľ��ȡ�
	 */
	public int getValue();
	
	/**
	 * ����ģ�͵ĵ�ǰ���ȡ�
	 * @param val ָ��ֵ��
	 */
	public void setValue(int val);
	
	/**
	 * ��ģ���Ƿ��ǽ���δ֪�͵ġ�
	 * @return �Ƿ��ǽ���δ֪�͡�
	 */
	public boolean isIndeterminate();
	
	/**
	 * ���ø�ģ���Ƿ�Ϊ����δ֪�͵ġ�
	 * @param aFlag ��ǡ�
	 */
	public void setIndeterminate(boolean aFlag);
	
	/**
	 * ��������˵�����ı���
	 * <p> ���ı����κ�ʱ�̲���Ϊnull��
	 * @return �ı���
	 */
	public String getLabelText();
	
	/**
	 * ��������˵�����ı���
	 * @param str ����˵�����ı���
	 */
	public void setLabelText(String str);
	
	/**
	 * ���ز����Ƿ���ֹ��
	 * @return �����Ƿ���ֹ��
	 */
	public boolean isSuspend();
	
	/**
	 * ��ֹ��ǰ������
	 */
	public void suspend();
	
	/**
	 * ���ص�ǰ�����Ƿ���Ȼ������
	 * @return �Ƿ���Ȼ������
	 */
	public boolean isEnd();
	
	/**
	 * ��Ȼ������ǰ������
	 */
	public void end();

}
