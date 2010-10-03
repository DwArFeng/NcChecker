package com.dwarfeng.ncc.model;

import com.dwarfeng.dfunc.prog.mvc.ModuleControlPort;
import com.dwarfeng.ncc.model.expl.ExplCp;
import com.dwarfeng.ncc.model.front.FrontCp;

/**
 * 数控代码验证程序中的模型控制端口。
 * @author DwArFeng
 * @since 1.8
 */
public interface NccModelControlPort extends ModuleControlPort {
	
	/**
	 * 初始化。
	 * @throws IllegalStateException 重复初始化。
	 */
	public void init();
	
	/**
	 * 获取前台的模型控制站。
	 * @return 前台模型控制站。
	 * @throws IllegalStateException 模型管理器还未初始化。
	 */
	public FrontCp frontCp();
	
	/**
	 * 获取解释模型控制站。
	 * @return 解释模型控制站。
	 * @throws IllegalStateException 模型管理器还未初始化。
	 */
	public ExplCp explCp();

}
