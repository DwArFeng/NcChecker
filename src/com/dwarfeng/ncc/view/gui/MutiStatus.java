package com.dwarfeng.ncc.view.gui;

/**
 * ��ʾһ����ӵ�ж���״̬��
 * @author DwArFeng
 * @since 1.8
 */
public interface MutiStatus {

	/**
	 * �����༭��
	 */
	public abstract void lockEdit();

	/**
	 * �����༭��
	 */
	public abstract void unlockEdit();

	/**
	 * �������ļ�״̬��
	 * @param aFlag ��ǡ�
	 */
	public abstract void noneFile(boolean aFlag);

}