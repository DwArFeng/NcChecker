package com.dwarfeng.ncc.program;

import com.dwarfeng.dfunc.prog.mvc.ProgramControlPort;
import com.dwarfeng.ncc.program.conf.ConfigCp;

/**
 * 数控代码验证程序中的程序控制集合。
 * @author DwArFeng
 * @since 1.8
 */
public interface NccProgramControlPort extends ProgramControlPort{
	
	/**
	 * 初始化
	 * @throws IllegalStateException 重复初始化。
	 */
	public void init();
	
	/**
	 * 向后台线程发起一个运行请求。
	 * @param runnable 可运行对象。
	 * @throws IllegalStateException 程序管理器没初始化。
	 */
	public void backInvoke(Runnable runnable);
	
	/**
	 * 获得配置控制站。
	 * @return 配置控制站。
	 * @throws IllegalStateException 程序管理器没有初始化。
	 */
	public ConfigCp configCp();
	
}
