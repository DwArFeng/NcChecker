package com.dwarfeng.ncc.module.nc;


/**
 * NC单步。
 * @author DwArFeng
 * @since 1.8
 */
public interface NcStep<P extends DirectionPoint, M extends Modal> extends Previousable<NcStep<P, M>>{
	
	/**
	 * 获取该单步的目标点。
	 * @return 获取该单步的目标点。
	 */
	public P getTargetPoint();
	
	/**
	 * 获取该单步的模态。
	 * @return 该单步的模态。
	 */
	public M getModal();
	
}
