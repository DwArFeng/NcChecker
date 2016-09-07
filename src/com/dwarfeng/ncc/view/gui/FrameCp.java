package com.dwarfeng.ncc.view.gui;

import java.util.NoSuchElementException;

import com.dwarfeng.ncc.module.front.Page;
import com.dwarfeng.ncc.module.nc.CodeSerial;
import com.dwarfeng.ncc.program.conf.MfAppearConfig;

/**
 * �����ܿ���վ��
 * <p> �ÿ���վ���Կ��Ƴ������ڵķ�����
 * @author DwArFeng
 * @since 1.8
 */
public interface FrameCp {

	/**
	 * ���õ�����ǩ��
	 * <p> ��������ǩΪtrueʱ����������������Σ��������������������ʱ�Ͳ��ᴥ��������
	 * @param aFlag ָ���ı�ǩ��
	 */
	public void setModiFlag(boolean aFlag);
	
	/**
	 * �����༭���ܡ�
	 */
	public void lockEdit();
	
	/**
	 * �����༭���ܡ�
	 */
	public void unlockEdit();
	
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
	 * ���ô���ĵ�ǰҳ����
	 * @param page ����ĵ�ǰҳ����
	 * @throws NoSuchElementException ָ����ҳ�������ڡ�
	 */
	public void setCodePage(Page page);
	
	/**
	 * ���ô������ҳ����
	 * @param val �������ҳ����
	 */
	public void setCodeTotlePages(int val);
	
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
	 * ��ʾָ���Ĵ������С�
	 * @param codeSerial ��ʾָ���Ĵ������С�
	 * @param flag Ϊ <code>true</code>ʱ��ʾ���붥����Ϊ <code>false</code>ʱ���ش���ײ���
	 */
	public void showCode(CodeSerial codeSerial, boolean flag);
	
	/**
	 * ���ÿ���̨������ı���
	 * @param message ����̨������ı���
	 */
	public void traceInConsole(String message);
	
	
}
