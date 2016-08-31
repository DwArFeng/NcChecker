package com.dwarfeng.ncc.module;

/**
 * 隶属于Ncc模型子类的对象。
 * @author DwArFeng
 * @since 1.8
 */
public interface NccModuleObject {

	/**
	 * 返回所属的Ncc模型管理器。
	 * @return Ncc模型管理器。
	 */
	public NccModuleManager getModuleManger();
}
