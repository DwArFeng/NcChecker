package com.dwarfeng.ncc.module.nc;


/**
 * һ�����س����еĵ�λ��
 * <p> �õ�λ���Ա�ʾ��ǰ�κ����س����еĵ㡣
 * <br> �˵�λ
 * @author DwArFeng
 * @since 1.8
 */
public interface DirectionPoint{
	
	/**
	 * ��ȡ���λ�á�
	 * @return ��λ��λ��������
	 */
	public Vector3D getPosition();
	
	/**
	 * ��ȡ��ķ���Ҳ���ǵ�����������
	 * @return ��ķ���
	 */
	public Vector3D getDirection();
	
}
