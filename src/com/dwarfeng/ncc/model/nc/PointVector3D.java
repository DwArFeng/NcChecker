package com.dwarfeng.ncc.model.nc;




/**
 * 利用从零点到指定点实现的{@link Vector3D}。
 * @author DwArFeng
 * @since 1.8
 */
public final class PointVector3D implements Vector3D {
	
	private double x,y,z;
	
	
	/**
	 * 构造一个指定的 {@link Vector3D}类。
	 * @param x 指定的x坐标。
	 * @param y 指定的y坐标。
	 * @param z 指定的z坐标。
	 */
	public PointVector3D(double x, double y, double z) {
		this.x = x ;
		this.y = y;
		this.z = z;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.model.cnc.Vector3D#getX()
	 */
	@Override
	public double getX() {
		return x;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.model.cnc.Vector3D#getY()
	 */
	@Override
	public double getY() {
		return y;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.model.cnc.Vector3D#getZ()
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
		if(!(obj instanceof PointVector3D)) return false;
		PointVector3D source = (PointVector3D) obj;
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
