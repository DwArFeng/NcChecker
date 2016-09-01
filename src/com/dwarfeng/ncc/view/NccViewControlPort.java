package com.dwarfeng.ncc.view;

import com.dwarfeng.dfunc.prog.mvc.ViewControlPort;
import com.dwarfeng.ncc.program.conf.MainFrameAppearConfig;
import com.dwarfeng.ncc.view.gui.NccFrameControlPort;

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

}
