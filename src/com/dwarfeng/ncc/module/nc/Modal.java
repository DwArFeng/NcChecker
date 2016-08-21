package com.dwarfeng.ncc.module.nc;

import com.dwarfeng.ncc.module.nc.Modals.G0;
import com.dwarfeng.ncc.module.nc.Modals.G90;


/**
 * ģ̬���ӿڡ�
 * <p> ģ̬�ӿڱ�����з�λ���Ե�ģ̬��������λ��ģ̬���������ԣ����������������װ����
 * <br> ��ģ̬���ӿڱ궨����������ϵͳ�й��õ�ģ̬����
 * ������ϵͳ��ģ̬���ӿڿ��Ը��Ǵ˽ӿڣ��������������չ�Լ���ģ̬����
 * @author DwArFeng
 * @since 1.8
 */
public interface Modal {
	
	/**
	 * ��ȡG0��ģ̬��
	 * @return G0��ģ̬��
	 */
	public G0 getG0();
	
	/**
	 * ��ȡG90��ģ̬��
	 * @return G90��ģ̬��
	 */
	public G90 getG90();
	
	/**
	 * ��ȡ����ת�١�
	 * @return ����ת�٣���λ��RPM��
	 */
	public double getSpindle();
	
	/**
	 * ��ȡ�����ٶȡ�
	 * @return �����ٶȣ�������MMPM��
	 */
	public double getFeed();

}
