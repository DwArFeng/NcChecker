package com.dwarfeng.ncc.module.nc;

import com.dwarfeng.ncc.module.NccModuleObject;

/**
 * ��ά������
 * @author DwArFeng
 * @since 1.8
 */
public interface Vector3D extends NccModuleObject{
	
	/**
	 * ��ȡx���ꡣ
	 * @return x���ꡣ
	 */
	public double getX();
	
	/**
	 * ��ȡy���ꡣ
	 * @return y���ꡣ
	 */
	public double getY();
	
	/**
	 * ��ȡZ���ꡣ
	 * @return z���ꡣ
	 */
	public double getZ();

}
