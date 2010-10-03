package com.dwarfeng.ncc.model.nc;

import java.util.NoSuchElementException;

/**
 * NC代码列表。
 * <p> 实现该接口意味着实现类是一个NC代码列表。
 * @author DwArFeng
 * @since 1.8
 */
public interface CodeSerial extends Iterable<Code>{
	
	/**
	 * 返回指定行号的代码。
	 * @param lineIndex 指定的行号。
	 * @return 指定行号对应的代码。
	 * @throws NoSuchElementException 指定的代码序列中没有指定的元素。
	 */
	public Code getCode(int lineNumber);
	
	/**
	 * 返回该代码序列中的所有代码。
	 * @return Nc代码的总行数。
	 */
	public int getTotle();
	
	/**
	 * 返回代码的数组形式，返回的代码是根据行号进行排序的。
	 * @return 代码的数组形式。
	 */
	public Code[] toArray();
	
	/**
	 * 返回代码的数组形式。
	 * @param start 开始的行号。
	 * @param end 结束的行号。
	 * @return 返回的代码区间。
	 * @throws IndexOutOfBoundsException 起始或结束的行号超界。
	 */
	public Code[] toArray(int start, int end);
	
	/**
	 * 返回最大的代码行数。
	 * @return 最大的代码行数。
	 */
	public int getMaxLineNumber();
	
	/**
	 * 返回最小的代码行数。
	 * @return 最小的代码行数。
	 */
	public int getMinLineNumber();
	
}
