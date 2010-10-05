package com.dwarfeng.ncc.view.gui;

import com.dwarfeng.ncc.control.cps.CodeCp.CodeEidtMode;
import com.dwarfeng.ncc.model.nc.CodeSerial;
import com.dwarfeng.ncc.program.conf.MfAppearConfig;

/**
 * �����ܿ���վ��
 * <p> �ÿ���վ���Կ��Ƴ������ڵķ�����
 * @author DwArFeng
 * @since 1.8
 */
public interface FrameCp {
	
	/**
	 * �����༭���ܡ�
	 * <p> �÷������ڶ��ļ����ж�ȡ��������Ҫ������ʱ�����༭���ܣ��÷�����Ӧ�ó�ʱ�䱻ʹ�á�
	 */
	public void lockEdit();
	
	/**
	 * �����༭���ܡ�
	 */
	public void unlockEdit();
	
	/**
	 * ������ʾģʽ�Ƿ�Ϊ���ļ�ģʽ��
	 * <p> �����ļ�ģʽ�£����籣�桢���Ϊ�ȹ��ܽ������á�
	 * @param aFlag �Ƿ�Ϊ���ļ�ģʽ��
	 */
	public void noneFileMode(boolean aFlag);
	
	/**
	 * Ӧ��ָ�������µ���ۡ�
	 * @param config ָ����������á�
	 */
	public void applyAppearanceConfig(MfAppearConfig config);
	
	/**
	 * ��ȡ��ǰ��������á�
	 * @return ��ǰ��������á�
	 */
	public MfAppearConfig getAppearanceConfig();
	
	/**
	 * ����״̬��ǩ�ϵ��ı���
	 * @param message ״̬��ǩ�ϵ��ı���
	 * @param type �ı������͡�
	 */
	public void setStatusLabelMessage(String message,StatusLabelType type);
	
	/**
	 * �����������Ƿ�ɼ���
	 * @param aFlag �������Ƿ�ɼ���
	 * @throws IllegalStateException ����������û�г�ʼ����ʱ���׳��쳣��
	 * @throws IllegalStateException ���������Ѿ����ر�ʱ�׳��쳣��
	 */
	public void setVisible(boolean aFlag);
	
	/**
	 * ��ʾָ���Ĵ������У������Ҫ���´��룬����ڲ���ʹ�� <code>null</code>��
	 * @param codeSerial ��ʾָ���Ĵ������С�
	 */
	public void showCode(CodeSerial codeSerial);
	
	/**
	 * �ڿ���̨�ϸ�ʽ�����ָ�����ı���
	 * @param format ָ���ĸ�ʽ��
	 * @param args ��������
	 */
	public void traceInConsole(String format,Object... args);
	
	/**
	 * ��ʼ���ȼ��ӡ�
	 * @param model ָ���Ľ���ģ�͡�
	 * @throws NullPointerException ����ģ��Ϊ <code>null</code>��
	 */
	public void startProgressMonitor(ProgressModel model);
	
	/**
	 * �л���������ģʽ��
	 * @param mode ָ����ģʽ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void knockForMode(CodeEidtMode mode);
	
	/**
	 * ���ñ༭�����ı���
	 * @param text ָ�����ı���
	 */
	public void setEditText(String text);
	
	/**
	 * ���ر༭�������ı�����������
	 * @return �༭�������ı�����������
	 */
	public int getEditLine();
	
	/**
	 * ��ȡ�༭�����е��ı���
	 * @return �༭�����е��ı���
	 */
	public String getEditText();
	
	/**
	 * ��ȡ�༭ģʽ�Ƿ���Ҫ�ύ�ı�ǡ�
	 * @return �Ƿ���Ҫ�ύ��
	 */
	public boolean needCommit();
	
	/**
	 * ֪ͨ��ͼģ�ͱ༭�Ĵ����Ѿ��ύ��
	 */
	public void knockForCommit();
	
}
