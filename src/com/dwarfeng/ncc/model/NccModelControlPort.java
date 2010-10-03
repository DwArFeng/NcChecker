package com.dwarfeng.ncc.model;

import com.dwarfeng.dfunc.prog.mvc.ModuleControlPort;
import com.dwarfeng.ncc.model.expl.ExplCp;
import com.dwarfeng.ncc.model.front.FrontCp;

/**
 * ���ش�����֤�����е�ģ�Ϳ��ƶ˿ڡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface NccModelControlPort extends ModuleControlPort {
	
	/**
	 * ��ʼ����
	 * @throws IllegalStateException �ظ���ʼ����
	 */
	public void init();
	
	/**
	 * ��ȡǰ̨��ģ�Ϳ���վ��
	 * @return ǰ̨ģ�Ϳ���վ��
	 * @throws IllegalStateException ģ�͹�������δ��ʼ����
	 */
	public FrontCp frontCp();
	
	/**
	 * ��ȡ����ģ�Ϳ���վ��
	 * @return ����ģ�Ϳ���վ��
	 * @throws IllegalStateException ģ�͹�������δ��ʼ����
	 */
	public ExplCp explCp();

}
