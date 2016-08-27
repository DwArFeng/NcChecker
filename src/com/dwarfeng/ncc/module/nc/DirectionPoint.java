package com.dwarfeng.ncc.module.nc;


/**
 * 一个数控程序中的点位。
 * <p> 该点位可以表示当前任何数控程序中的点。
 * <br> 此点位
 * @author DwArFeng
 * @since 1.8
 */
public interface DirectionPoint{
	
	/**
	 * 获取点的位置。
	 * @return 点位的位置向量。
	 */
	public Vector3D getPosition();
	
	/**
	 * 获取点的方向（也就是刀轴向量）。
	 * @return 点的方向。
	 */
	public Vector3D getDirection();
	
}
