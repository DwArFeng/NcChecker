package com.dwarfeng.ncc.view;

import com.dwarfeng.dfunc.prog.mvc.ViewControlPort;
import com.dwarfeng.ncc.view.gui.NccFrameControlPort;
import com.dwarfeng.ncc.view.gui.NotifyControlPort;
import com.dwarfeng.ncc.view.gui.ProgressMonitor;

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
	 * ���س����ܵĿ���վ��
	 * @return �����ܿ���վ��
	 * @throws IllegalStateException ��������û�г�ʼ����ʱ���׳��쳣��
	 */
	public NccFrameControlPort getMainFrameControlPort();
	
	/**
	 * ��ȡ��ͼ�����ѿ���վ��
	 * @return ���ѿ���վ
	 * @throws IllegalStateException ��������û�г�ʼ����ʱ���׳����쳣��
	 */
	public NotifyControlPort getNotifyControlPort();
	
	/**
	 * ��ȡ��ͼģ�͵Ľ��ȿ���վ��
	 * @return ���ȿ���վ��
	 * @throws IllegalStateException ��������û�г�ʼ����ʱ���׳����쳣��
	 */
	public ProgressMonitor getProgressMonitor();

}
