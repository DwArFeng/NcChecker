package com.dwarfeng.ncc.module.front;

import java.io.Closeable;
import java.io.IOException;

import com.dwarfeng.ncc.module.nc.Code;

/**
 * NC代码读取器。
 * <p> 能够从输入流中读取NC代码的类。
 * @author DwArFeng
 * @since 1.8
 */
public interface CodeLoader extends Closeable {

	/**
	 * 返回下一行NC代码。
	 * @return 下一行NC代码。
	 * @throws IOException IO通信异常。 
	 */
	public Code loadNext() throws IOException;
	
	/**
	 * 判断是否有下一行NC代码。
	 * @return 是否有下一行代码。
	 * @throws IOException IO异常。
	 */
	public boolean hasNext() throws IOException;
	
}
