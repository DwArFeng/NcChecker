package com.dwarfeng.ncc.program;

import java.io.IOException;

import com.dwarfeng.dfunc.prog.mvc.ProgramControlPort;
import com.dwarfeng.ncc.program.conf.MainFrameAppearConfig;

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
	 * 保存指定的外观设置。
	 * @param config 指定的外观设置。
	 * @throws IOException IO异常。
	 * @throws IllegalStateException 程序管理器没初始化。
	 */
	public void saveMainFrameAppearConfig(MainFrameAppearConfig config)throws IOException;
	
	/**
	 * 获取配置文件中的外观配置。
	 * <p> 该配置文件会一直保持最新。但当读取配置文件发生异常时，则会返回默认的外观文件。
	 * @return 外观配置。
	 * @throws IOException IO异常。
	 * @throws NumberFormatException 数字格式异常：在配置文件被外部更改时可能会抛出。
	 * @throws IllegalStateException 程序管理器没初始化。
	 */
	public MainFrameAppearConfig loadMainFrameAppearConfig() throws IOException,NumberFormatException;

	/**
	 * 向后台线程发起一个运行请求。
	 * @param runnable 可运行对象。
	 * @throws IllegalStateException 程序管理器没初始化。
	 */
	public void backInvoke(Runnable runnable);
	
}
