package com.dwarfeng.ncc.module.nc;


/**
 * NC������
 * @author DwArFeng
 * @since 1.8
 */
public interface NcStep<P extends DirectionPoint, M extends Modal> extends Previousable<NcStep<P, M>>{
	
	/**
	 * ��ȡ�õ�����Ŀ��㡣
	 * @return ��ȡ�õ�����Ŀ��㡣
	 */
	public P getTargetPoint();
	
	/**
	 * ��ȡ�õ�����ģ̬��
	 * @return �õ�����ģ̬��
	 */
	public M getModal();
	
}
