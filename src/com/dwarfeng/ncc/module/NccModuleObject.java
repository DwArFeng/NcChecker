package com.dwarfeng.ncc.module;

import java.util.Objects;

/**
 * 隶属于Mcc模型子类的对象。
 * @author DwArFeng
 * @since 1.8
 */
public abstract class NccModuleObject {
	
	/**
	 * 对象中引用的Mcc模型管理器。
	 */
	protected final NccModuleManager moduleManager;
	
	/**
	 * 生成一个具有指定Ncc模型管理器的Ncc模型对象。
	 * @param moduleManager 知道你个的Ncc模型管理器。
	 * @throws NullPointerException 入口参数为<code>null</code>时抛出此异常。
	 */
	public NccModuleObject(NccModuleManager moduleManager) {
		Objects.requireNonNull(moduleManager);
		this.moduleManager = moduleManager;
	}

}
