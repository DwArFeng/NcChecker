package com.dwarfeng.ncc.module.mut;

import com.dwarfeng.dfunc.num.UnitTrans;
import com.dwarfeng.dfunc.num.UnitTrans.ANGLE;
import com.dwarfeng.ncc.module.NccModuleManager;
import com.dwarfeng.ncc.module.AbstractNccModuleObject;
import com.dwarfeng.ncc.module.nc.PointVector3D;
import com.dwarfeng.ncc.module.nc.ToolPoint;
import com.dwarfeng.ncc.module.nc.Vector3D;

/**
 * 通过XYZ与AC轴来构造点位的AC双转头五轴点点。
 * @author DwArFeng
 * @since 1.8
 */
public final class AcDoubleRotorPoint extends AbstractNccModuleObject implements ToolPoint{
	
	private final double x,y,z,a,c;
	
	/**
	 * 构造一个默认的，所有参数都是0的AC双转头五轴点。
	 * @param moduleManager 指定的模型管理器。
	 * @throws NullPointerException 当<code>moduleManager</code>为 <code>null</code>时抛出该异常。
	 */
	public AcDoubleRotorPoint(NccModuleManager moduleManager) {
		this(moduleManager,0,0,0,0,0);
	}
	
	/**
	 * 构造一个AC双转头五轴点。
	 * @param moduleManager 指定的模型管理器。
	 * @param x x坐标。
	 * @param y y坐标。
	 * @param z z坐标。
	 * @param a a轴转角。
	 * @param c c轴转角。
	 * @throws NullPointerException 当<code>moduleManager</code>为 <code>null</code>时抛出该异常。
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
	 * 返回该点的A轴转角（角度制）
	 * @return the a
	 */
	public double getA() {
		return a;
	}
	
	/**
	 * 返回该点的C轴转角（角度制）
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
