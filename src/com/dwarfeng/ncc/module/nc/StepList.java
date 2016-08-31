package com.dwarfeng.ncc.module.nc;

import com.dwarfeng.ncc.module.NccModuleObject;

/**
 * 刀具步列表。
 * <p> 由连续的刀具单步组成的刀具步列表。
 * @author DwArFeng
 * @since 1.8
 */
public interface StepList<T extends ToolPoint , M extends Modal> extends Iterable<StepList<T, M>>,
NccModuleObject{

	/**
	 * 返回指定序号处的刀具单步。
	 * @param index 指定的序号。
	 * @return 指定序号处的刀具单步。
	 * 	@throws IndexOutOfBoundsException 行号超过总行数或者小于0时抛出的异常。
	 */
	public Step<T, M> getToolStep(int index);
	
	/**
	 * 返回刀具流中的总共步数。
	 * @return 刀具流中的总步数。
	 */
	public int getTotleStep();
	
}
