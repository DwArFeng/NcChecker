package com.dwarfeng.ncc.module.mut;

import com.dwarfeng.dfunc.num.UnitTrans;
import com.dwarfeng.dfunc.num.UnitTrans.ANGLE;
import com.dwarfeng.ncc.module.NccModuleManager;
import com.dwarfeng.ncc.module.AbstractNccModuleObject;
import com.dwarfeng.ncc.module.nc.PointVector3D;
import com.dwarfeng.ncc.module.nc.ToolPoint;
import com.dwarfeng.ncc.module.nc.Vector3D;

/**
 * ͨ��XYZ��AC���������λ��AC˫תͷ�����㡣
 * @author DwArFeng
 * @since 1.8
 */
public final class AcDoubleRotorPoint extends AbstractNccModuleObject implements ToolPoint{
	
	private final double x,y,z,a,c;
	
	/**
	 * ����һ��Ĭ�ϵģ����в�������0��AC˫תͷ����㡣
	 * @param moduleManager ָ����ģ�͹�������
	 * @throws NullPointerException ��<code>moduleManager</code>Ϊ <code>null</code>ʱ�׳����쳣��
	 */
	public AcDoubleRotorPoint(NccModuleManager moduleManager) {
		this(moduleManager,0,0,0,0,0);
	}
	
	/**
	 * ����һ��AC˫תͷ����㡣
	 * @param moduleManager ָ����ģ�͹�������
	 * @param x x���ꡣ
	 * @param y y���ꡣ
	 * @param z z���ꡣ
	 * @param a a��ת�ǡ�
	 * @param c c��ת�ǡ�
	 * @throws NullPointerException ��<code>moduleManager</code>Ϊ <code>null</code>ʱ�׳����쳣��
	 */
	public AcDoubleRotorPoint(NccModuleManager moduleManager, double x, double y, double z, double a, double c) {
		super(moduleManager);
		this.x = x;
		this.y = y;
		this.z = z;
		this.a = a;
		this.c = c;
	}

	/**
	 * ���ظõ��A��ת�ǣ��Ƕ��ƣ�
	 * @return the a
	 */
	public double getA() {
		return a;
	}
	
	/**
	 * ���ظõ��C��ת�ǣ��Ƕ��ƣ�
	 * @return the c
	 */
	public double getC() {
		return c;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.cnc.NCPoint#getPoint()
	 */
	@Override
	public Vector3D getPosition() {
		return new PointVector3D(moduleManager,x, y, z);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.cnc.NCPoint#getNorVec()
	 */
	@Override
	public Vector3D getDirection() {
		return new PointVector3D(
				moduleManager,
				Math.sin(UnitTrans.trans(a, ANGLE.DEG, ANGLE.RAD).doubleValue())
				* Math.sin(UnitTrans.trans(c, ANGLE.DEG, ANGLE.RAD).doubleValue()), 
				-1 * Math.sin(UnitTrans.trans(a, ANGLE.DEG, ANGLE.RAD).doubleValue())
				* Math.cos(UnitTrans.trans(c, ANGLE.DEG, ANGLE.RAD).doubleValue()),
				Math.cos(UnitTrans.trans(a, ANGLE.DEG, ANGLE.RAD).doubleValue())
		);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof AcDoubleRotorPoint)) return false;
		AcDoubleRotorPoint source = (AcDoubleRotorPoint) obj;
		return 
				source.getPosition().equals(getPosition()) &&
				source.a == a &&
				source.c == c;
	};
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (int) (x * 17 + y * 17 + z * 17 + a * 17 + c);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "X" + x + " Y" + y + " Z" + z + " A" + a + " C" + c;
	}

}
