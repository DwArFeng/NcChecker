package com.dwarfeng.ncc.view;

import com.dwarfeng.dfunc.prog.mvc.ViewControlPort;

/**
 * ���ش�����֤�������ͼ���ƶ˿ڡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface NccViewControlPort extends ViewControlPort {
	
	/**
	 * ��ʼ����
	 * @throws IllegalStateException �ظ���ʼ��ʱ�׳��쳣��
	 */
	public void init();
	
	/**
	 * �����������Ƿ�ɼ���
	 * @param aFlag �������Ƿ�ɼ���
	 * @throws IllegalStateException ����������û�г�ʼ����ʱ���׳��쳣��
	 */
	public void setMainFrameVisible(boolean aFlag);

}
