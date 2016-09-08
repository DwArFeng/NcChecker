package com.dwarfeng.ncc.control.back;

import java.util.Objects;

import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.module.NccModuleControlPort;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.NccProgramControlPort;
import com.dwarfeng.ncc.view.NccViewControlPort;

/**
 * 抽象的控制管理器持有类。
 * @author DwArFeng
 * @since 1.8
 */
abstract class AbstractCmr implements Runnable{
	
	protected final NccControlManager cm;
	protected final NccViewControlPort viewControlPort;
	protected final NccModuleControlPort moduleControlPort;
	protected final NccProgramControlPort programControlPort;
	protected final NccProgramAttrSet programAttrSet;

	/**
	 * 生成一个拥有指定控制管理器引用的持有类。
	 * @param controlManager 指定的引用
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public AbstractCmr(NccControlManager controlManager) {
		Objects.requireNonNull(controlManager);
		this.cm = controlManager;
		this.viewControlPort = cm.getViewControlPort();
		this.moduleControlPort = cm.getModuleControlPort();
		this.programAttrSet = cm.getProgramAttrSet();
		this.programControlPort = cm.getProgramControlPort();
	}

}
