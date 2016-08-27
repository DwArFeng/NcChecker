package com.dwarfeng.ncc.module.nc;

/**
 * ���ṩǰһ��Ԫ�صĽӿڡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface Previousable<T extends Previousable<T>> {
	
	/**
	 * ���ظ�Ԫ���Ƿ���ǰһ��Ԫ�ء�
	 * @return
	 */
	public boolean hasPrevious();
	
	/**
	 * ������һ��Ԫ�ء�
	 * <br> ��� {@code Preable.hasPre() == false}����÷���Ӧ�÷��� <code>null</code>
	 * ������Ӧ�÷��� <code>null</code>��
	 * @return ��һ��Ԫ�ء�
	 */
	public T previous();
	

}
