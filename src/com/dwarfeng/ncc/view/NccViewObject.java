package com.dwarfeng.ncc.view;

/**
 * 隶属于Ncc模型子类的对象。
 * @author DwArFeng
 * @since 1.8
 */
public interface NccViewObject {
	
	/**
	 * 返回所属的NCC视图管理器。
	 * @return NCC视图管理器。
	 */
	public NccViewManager getViewManager();

}
