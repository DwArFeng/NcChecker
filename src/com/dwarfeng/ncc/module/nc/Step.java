package com.dwarfeng.ncc.module.nc;

import com.dwarfeng.ncc.module.NccModuleObject;


/**
 * ���ߵ�����
 * <p> �����ʾ���ߵĵ��������Ա�ʾ������ĳһ���������ĸ����ϣ��Լ���õ��ƶ�ʱ��ģ̬״̬��
 * @author DwArFeng
 * @since 1.8
 */
public interface Step<T extends ToolPoint, M extends Modal> extends NccModuleObject{
	
	/**
	 * ��ȡ�õ�����Ŀ��㡣
	 * @return ��ȡ�õ�����Ŀ��㡣
	 */
	public T getTargetPoint();
	
	/**
	 * ��ȡ�õ�����ģ̬��
	 * @return �õ�����ģ̬��
	 */
	public M getModal();
	
}
