package com.dwarfeng.ncc.module.nc;

import com.dwarfeng.ncc.module.nc.Modals.G0;
import com.dwarfeng.ncc.module.nc.Modals.G90;


/**
 * 模态量接口。
 * <p> 模态接口标记所有非位置性的模态量（由于位置模态量的特殊性，单独用有向点来封装）。
 * <br> 该模态量接口标定了所有数控系统中共用的模态量，
 * 其它的系统的模态量接口可以覆盖此接口，在这个基础上拓展自己的模态量。
 * @author DwArFeng
 * @since 1.8
 */
public interface Modal {
	
	/**
	 * 获取G0组模态。
	 * @return G0组模态。
	 */
	public G0 getG0();
	
	/**
	 * 获取G90组模态。
	 * @return G90组模态。
	 */
	public G90 getG90();
	
	/**
	 * 获取主轴转速。
	 * @return 主轴转速，单位是RPM。
	 */
	public double getSpindle();
	
	/**
	 * 获取进给速度。
	 * @return 进给速度，但是是MMPM。
	 */
	public double getFeed();

}
