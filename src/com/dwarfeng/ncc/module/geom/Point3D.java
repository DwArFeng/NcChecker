package com.dwarfeng.ncc.module.geom;

/**
 * ��ά�㡣
 * @author DwArFeng
 * @since 1.8
 */
public final class Point3D {
	
	private final double x,y,z;
	
	/**
	 * ����һ����ά��ʵ����
	 * @param x x���ꡣ
	 * @param y y���ꡣ
	 * @param z z���ꡣ
	 */
	public Point3D(double x, double y, double z){
		this.x = x ;
		this.y = y ;
		this.z = z ;
	}

	/**
	 * ����x���ꡣ
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * ����y���ꡣ
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * ����z���ꡣ
	 * @return the z
	 */
	public double getZ() {
		return z;
	}

}
