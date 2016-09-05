package com.dwarfeng.ncc.view.gui;

/**
 * ���ȼ�������
 * @author DwArFeng
 * @since 1.8
 */
public interface ProgressMonitor{

	/**
	 * ��ʼ�Խ��Ƚ��м��ӡ�
	 * @throws IllegalStateException �ظ�������
	 */
	public void startMonitor();
	
	/**
	 * �����Խ��ȵļ��ӡ�
	 * @throws IllegalStateException �ظ�ֹͣ��
	 */
	public void endMonitor();
	
	/**
	 * �����ܹ�������
	 * @param val �ܹ�������
	 * @throws IllegalStateException ��ֹͣ���ӵ��ڼ���á�
	 */
	public void setTotleProgress(int val);
	
	/**
	 * ���õ�ǰ��������
	 * @param val ��ǰ��������
	 * @throws IllegalStateException ��ֹͣ���ӵ��ڼ���á�
	 */
	public void setCurrentProgress(int val);
	
	/**
	 * �趨�������Ƿ����ڲ�ȷ��ģʽ�¡�
	 * @param aFlag �Ƿ����ڲ�ȷ��ģʽ�¡�
	 * @throws IllegalStateException ��ֹͣ���ӵ��ڼ���á�
	 */
	public void setIndeterminate(boolean aFlag);
	
	/**
	 * �ù����Ƿ���Ϊ��ֹ��
	 * <p> �÷�����Ҫ�� {@link ProgressMonitor#suspend()}����ͬ����
	 * @return �Ƿ���Ϊ��ֹ��
	 * @throws IllegalStateException ��ֹͣ���ӵ��ڼ���á�
	 */
	public boolean isSuspend();
	
	/**
	 * ��Ϊ��ֹ�ù�����
	 * <p> �÷�����Ҫ�� {@link ProgressMonitor#isSuspend()}����ͬ����
	 * @throws IllegalStateException ��ֹͣ���ӵ��ڼ���á�
	 */
	public void suspend();
	
	/**
	 * ���õ�ǰ��������Ϣ��
	 * @param message ��ǰ�Ĺ�����Ϣ��
	 * @throws IllegalStateException ��ֹͣ���ӵ��ڼ���á�
	 */
	public void setMessage(String message);
}
