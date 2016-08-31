package com.dwarfeng.ncc.module.nc;

import com.dwarfeng.ncc.module.NccModuleObject;

/**
 * 三维向量。
 * @author DwArFeng
 * @since 1.8
 */
public interface Vector3D extends NccModuleObject{
	
	/**
	 * 获取x坐标。
	 * @return x坐标。
	 */
	public double getX();
	
	/**
	 * 获取y坐标。
	 * @return y坐标。
	 */
	public double getY();
	
	/**
	 * 获取Z坐标。
	 * @return z坐标。
	 */
	public double getZ();

}
