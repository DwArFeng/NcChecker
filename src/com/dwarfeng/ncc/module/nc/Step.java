package com.dwarfeng.ncc.module.nc;


/**
 * 刀具单步。
 * <p> 该类表示刀具的单步，可以表示刀具在某一步走在了哪个点上，以及向该点移动时的模态状态。
 * @author DwArFeng
 * @since 1.8
 */
public interface Step<T extends ToolPoint, M extends Modal>{
	
	/**
	 * 获取该单步的目标点。
	 * @return 获取该单步的目标点。
	 */
	public T getTargetPoint();
	
	/**
	 * 获取该单步的模态。
	 * @return 该单步的模态。
	 */
	public M getModal();
	
}
