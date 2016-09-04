package com.dwarfeng.ncc.module.nc;

import com.dwarfeng.ncc.module.NccModuleObject;
import com.dwarfeng.ncc.module.expl.ExplState;

/**
 * NC�����б�
 * <p> ʵ�ָýӿ���ζ��ʵ������һ��NC�����б�
 * @author DwArFeng
 * @since 1.8
 */
public interface CodeList extends NccModuleObject, Iterable<Code>{
	
	/**
	 * ����ָ���кŵ��ı���
	 * @param lineIndex ָ�����кš�
	 * @return ָ���кŶ�Ӧ���ı���
	 * @throws IndexOutOfBoundsException �кų�������������С��0ʱ�׳����쳣��
	 */
	public Code getCode(int lineIndex);
	
	/**
	 * ���س����ܹ���������
	 * @return Nc�������������
	 */
	public int getTotleLine();
	
	/**
	 * ��ȡNC����Ľ���״̬��
	 * <p> �÷���Ӧ���� {@link CodeList#setExpleState(ExplState)}����ͬ����
	 * @return ���NC����Ľ���״̬��
	 */
	public ExplState getExplState();
	
	/**
	 * ����NC����Ľ���״̬��
	 * <p> �÷���Ӧ���� {@link CodeList#getExplState()}����ͬ����
	 * @param explState ָ���Ľ���״̬��
	 */
	public void setExpleState(ExplState explState);
	
}
