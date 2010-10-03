package com.dwarfeng.ncc.model.nc;


/**
 * 刀具点位。
 * <p> 该点位可以表示刀具在某时刻的的点位和刀轴的情况。
 * @author DwArFeng
 * @since 1.8
 */
public interface ToolPoint{
	
	/**
	 * 刀具的刀尖位置。
	 * @return 点位的位置向量。
	 */
	public Vector3D getPosition();
	
	/**
	 * 获取刀具的方向。
	 * @return 刀具的方向。
	 */
	public Vector3D getDirection();
	
}
