package com.dwarfeng.ncc.module;

import java.util.Objects;

/**
 * {@link NccModuleObject}的抽象实现。
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractNccModuleObject implements NccModuleObject{
	
	/**
	 * 对象中引用的Ncc模型管理器。
	 */
	protected final NccModuleManager moduleManager;
	
	/**
	 * 生成一个具有指定Ncc模型管理器的Ncc模型对象。
	 * @param moduleManager 指定的Ncc模型管理器。
	 * @throws NullPointerException 入口参数为<code>null</code>时抛出此异常。
	 */
	public AbstractNccModuleObject(NccModuleManager moduleManager) {
		Objects.requireNonNull(moduleManager);
		this.moduleManager = moduleManager;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.NccModuleObject#getModuleManger()
	 */
	@Override
	public NccModuleManager getModuleManger() {
		return moduleManager;
	}

}
