package com.dwarfeng.ncc.module.expl;

import java.io.InputStream;

/**
 * 解释控制站。
 * @author DwArFeng
 * @since 1.8
 */
public interface ExplControlPort{

	/**
	 * 获取一个针对指定输入流的新的 {@link CodeLoader}。
	 * @param in 指定的输入流。
	 * @return 新的 {@link CodeLoader}。
	 */
	public CodeLoader newNcCodeLoader(InputStream in);
	
}
