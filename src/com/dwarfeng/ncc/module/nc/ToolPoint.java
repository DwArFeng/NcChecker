package com.dwarfeng.ncc.module.nc;

import com.dwarfeng.ncc.module.NccModuleObject;


/**
 * ���ߵ�λ��
 * <p> �õ�λ���Ա�ʾ������ĳʱ�̵ĵĵ�λ�͵���������
 * @author DwArFeng
 * @since 1.8
 */
public interface ToolPoint extends NccModuleObject{
	
	/**
	 * ���ߵĵ���λ�á�
	 * @return ��λ��λ��������
	 */
	public Vector3D getPosition();
	
	/**
	 * ��ȡ���ߵķ���
	 * @return ���ߵķ���
	 */
	public Vector3D getDirection();
	
}
