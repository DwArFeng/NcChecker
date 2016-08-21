package com.dwarfeng.ncc.module.geom;

import com.dwarfeng.ncc.module.NccModuleManager;
import com.dwarfeng.ncc.module.NccModuleObject;



/**
 * {@link Vector3D}��Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 1.8
 */
public final class DefaultVector3D extends NccModuleObject implements Vector3D {
	
	private double x,y,z;
	
	/**
	 * ����һ������Ϊ<code>(0,0,0)</code>�� {@link Vector3D}�ࡣ
	 * @param moduleManager ָ����ģ�͹�������
	 * @throws NullPointerException <code>moduleManager</code>Ϊ <code>null</code>���׳����쳣��
	 */
	public DefaultVector3D(NccModuleManager moduleManager) {
		this(moduleManager,0,0,0);
	}
	
	/**
	 * ����һ��ָ���� {@link Vector3D}�ࡣ
	 * @param moduleManager ָ����ģ�͹�������
	 * @param x ָ����x���ꡣ
	 * @param y ָ����y���ꡣ
	 * @param z ָ����z���ꡣ
	 * @throws NullPointerException <code>moduleManager</code>Ϊ<code>null</code>ʱ�׳����쳣��
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
