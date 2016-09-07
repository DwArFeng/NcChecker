package com.dwarfeng.ncc.view;

import com.dwarfeng.dfunc.prog.mvc.ViewControlPort;
import com.dwarfeng.ncc.view.gui.FrameCp;
import com.dwarfeng.ncc.view.gui.NotifyCp;
import com.dwarfeng.ncc.view.gui.ProgCp;

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
	public FrameCp frameCp();
	
	/**
	 * ��ȡ��ͼ�����ѿ���վ��
	 * @return ���ѿ���վ
	 * @throws IllegalStateException ��������û�г�ʼ����ʱ���׳����쳣��
	 */
	public NotifyCp notifyCp();
	
	/**
	 * ��ȡ��ͼģ�͵Ľ��ȿ���վ��
	 * @return ���ȿ���վ��
	 * @throws IllegalStateException ��������û�г�ʼ����ʱ���׳����쳣��
	 */
	public ProgCp progCp();

}
