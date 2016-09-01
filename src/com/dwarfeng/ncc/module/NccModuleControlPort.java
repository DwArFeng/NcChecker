package com.dwarfeng.ncc.module;

import java.io.InputStream;

import com.dwarfeng.dfunc.prog.mvc.ModuleControlPort;
import com.dwarfeng.ncc.module.expl.CodeLoader;

/**
 * 数控代码验证程序中的模型控制端口。
 * @author DwArFeng
 * @since 1.8
 */
public interface NccModuleControlPort extends ModuleControlPort {
	
	/**
	 * 初始化。
	 * @throws IllegalStateException 重复初始化。
	 */
	public void init();
	
	/**
	 * 获取一个针对指定输入流的新的 {@link CodeLoader}。
	 * @param in 指定的输入流。
	 * @return 新的 {@link CodeLoader}。
	 */
	public CodeLoader newNcCodeLoader(InputStream in);

}
