package com.dwarfeng.ncc.module.nc;

import com.dwarfeng.ncc.module.NccModuleObject;
import com.dwarfeng.ncc.module.expl.ExplState;

/**
 * NC代码列表。
 * <p> 实现该接口意味着实现类是一个NC代码列表。
 * @author DwArFeng
 * @since 1.8
 */
public interface CodeList extends NccModuleObject, Iterable<Code>{
	
	/**
	 * 返回指定行号的文本。
	 * @param lineIndex 指定的行号。
	 * @return 指定行号对应的文本。
	 * @throws IndexOutOfBoundsException 行号超过总行数或者小于0时抛出的异常。
	 */
	public Code getCode(int lineIndex);
	
	/**
	 * 返回程序总共的行数。
	 * @return Nc代码的总行数。
	 */
	public int getTotleLine();
	
	/**
	 * 获取NC代码的解析状态。
	 * <p> 该方法应该与 {@link CodeList#setExpleState(ExplState)}保持同步。
	 * @return 获得NC代码的解析状态。
	 */
	public ExplState getExplState();
	
	/**
	 * 设置NC代码的解析状态。
	 * <p> 该方法应该与 {@link CodeList#getExplState()}保持同步。
	 * @param explState 指定的解析状态。
	 */
	public void setExpleState(ExplState explState);
	
}
