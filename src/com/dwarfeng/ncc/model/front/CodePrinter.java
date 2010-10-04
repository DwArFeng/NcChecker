package com.dwarfeng.ncc.model.front;

import java.io.Closeable;
import java.io.IOException;

/**
 * 代码打印器。
 * @author DwArFeng
 * @since 1.8
 */
public interface CodePrinter extends Closeable{
	
	/**
	 * 获取总代码量（工作量）。
	 * @return 总代码量。
	 */
	public int getTotleCode();
	
	/**
	 * 是否还有下一段代码。
	 * @return 是否还有下一段代码。
	 * @throws IOException IO异常。
	 */
	public boolean hasNext() throws IOException;
	
	/**
	 * 保存下一段代码。
	 * @throws IOException IO异常。
	 */
	public void printNext() throws IOException;
	
	/**
	 * 返回当前的打印进度
	 * @return 当前的进度。
	 */
	public int currentValue();
	
}
