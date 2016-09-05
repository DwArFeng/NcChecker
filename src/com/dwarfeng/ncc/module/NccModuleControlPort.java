package com.dwarfeng.ncc.module;

import com.dwarfeng.dfunc.prog.mvc.ModuleControlPort;
import com.dwarfeng.ncc.module.expl.ExplControlPort;
import com.dwarfeng.ncc.module.front.FrontModuleControlPort;

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
	public FrontModuleControlPort getFrontModuleControlPort();
	
	/**
	 * ��ȡ����ģ�Ϳ���վ��
	 * @return ����ģ�Ϳ���վ��
	 * @throws IllegalStateException ģ�͹�������δ��ʼ����
	 */
	public ExplControlPort getExplMoudleControlPort();

}
