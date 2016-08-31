package com.dwarfeng.ncc.module.nc;

import com.dwarfeng.ncc.module.NccModuleObject;


/**
 * 刀具点位。
 * <p> 该点位可以表示刀具在某时刻的的点位和刀轴的情况。
 * @author DwArFeng
 * @since 1.8
 */
public interface ToolPoint extends NccModuleObject{
	
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
