package com.dwarfeng.ncc.module.geom;

/**
 * 三维点。
 * @author DwArFeng
 * @since 1.8
 */
public final class Point3D {
	
	private final double x,y,z;
	
	/**
	 * 建立一个三维点实例。
	 * @param x x坐标。
	 * @param y y坐标。
	 * @param z z坐标。
	 */
	public Point3D(double x, double y, double z){
		this.x = x ;
		this.y = y ;
		this.z = z ;
	}

	/**
	 * 返回x坐标。
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * 返回y坐标。
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * 返回z坐标。
	 * @return the z
	 */
	public double getZ() {
		return z;
	}

}
