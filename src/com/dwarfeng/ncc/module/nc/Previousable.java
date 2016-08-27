package com.dwarfeng.ncc.module.nc;

/**
 * 可提供前一个元素的接口。
 * @author DwArFeng
 * @since 1.8
 */
public interface Previousable<T extends Previousable<T>> {
	
	/**
	 * 返回该元素是否有前一个元素。
	 * @return
	 */
	public boolean hasPrevious();
	
	/**
	 * 返回上一个元素。
	 * <br> 如果 {@code Preable.hasPre() == false}，则该方法应该返回 <code>null</code>
	 * ，否则不应该返回 <code>null</code>。
	 * @return 上一个元素。
	 */
	public T previous();
	

}
