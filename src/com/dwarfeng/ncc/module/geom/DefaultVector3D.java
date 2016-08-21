package com.dwarfeng.ncc.module.geom;

import com.dwarfeng.ncc.module.NccModuleManager;
import com.dwarfeng.ncc.module.NccModuleObject;



/**
 * {@link Vector3D}的默认实现。
 * @author DwArFeng
 * @since 1.8
 */
public final class DefaultVector3D extends NccModuleObject implements Vector3D {
	
	private double x,y,z;
	
	/**
	 * 构造一个向量为<code>(0,0,0)</code>的 {@link Vector3D}类。
	 * @param moduleManager 指定的模型管理器。
	 * @throws NullPointerException <code>moduleManager</code>为 <code>null</code>是抛出此异常。
	 */
	public DefaultVector3D(NccModuleManager moduleManager) {
		this(moduleManager,0,0,0);
	}
	
	/**
	 * 构造一个指定的 {@link Vector3D}类。
	 * @param moduleManager 指定的模型管理器。
	 * @param x 指定的x坐标。
	 * @param y 指定的y坐标。
	 * @param z 指定的z坐标。
	 * @throws NullPointerException <code>moduleManager</code>为<code>null</code>时抛出此异常。
	 */
	public DefaultVector3D(NccModuleManager moduleManager,double x, double y, double z) {
		super(moduleManager);
		this.x = x ;
		this.y = y;
		this.z = z;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.cnc.Vector3D#getX()
	 */
	@Override
	public double getX() {
		return x;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.cnc.Vector3D#getY()
	 */
	@Override
	public double getY() {
		return y;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.cnc.Vector3D#getZ()
	 */
	@Override
	public double getZ() {
		return z;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "X" + getX() + " Y" + getY() + " Z" + getZ();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof DefaultVector3D)) return false;
		DefaultVector3D source = (DefaultVector3D) obj;
		return
				source.getX() == getX() &&
				source.getY() == getY() &&
				source.getZ() == getZ();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (int) (getX() * 17 + getY() * 17 + getZ());
	}

}
