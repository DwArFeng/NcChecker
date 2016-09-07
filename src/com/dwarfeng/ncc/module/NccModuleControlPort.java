package com.dwarfeng.ncc.module;

import com.dwarfeng.dfunc.prog.mvc.ModuleControlPort;
import com.dwarfeng.ncc.module.expl.ExplCp;
import com.dwarfeng.ncc.module.front.FrontCp;

/**
 * ���ش�����֤�����е�ģ�Ϳ��ƶ˿ڡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface NccModuleControlPort extends ModuleControlPort {
	
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
