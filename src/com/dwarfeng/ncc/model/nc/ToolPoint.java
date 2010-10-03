package com.dwarfeng.ncc.model.nc;


/**
 * ���ߵ�λ��
 * <p> �õ�λ���Ա�ʾ������ĳʱ�̵ĵĵ�λ�͵���������
 * @author DwArFeng
 * @since 1.8
 */
public interface ToolPoint{
	
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
