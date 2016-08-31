package com.dwarfeng.ncc.view;

import java.util.Objects;

/**
 * {@link NccViewManager}的抽象实现。
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractNccViewObject implements NccViewObject{
	
	/*
	 * 对象中引用的Ncc视图管理器。
	 */
	protected final NccViewManager viewManager;

	/**
	 * 生成一个具有指定Ncc视图管理器的Ncc视图对象。
	 * @param viewManager 指定的Ncc视图管理器。
	 * @throws NullPointerException 入口参数为<code>null</code>时抛出此异常。
	 */
	public AbstractNccViewObject(NccViewManager viewManager) {
		Objects.requireNonNull(viewManager);
		this.viewManager = viewManager;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.view.NccViewObject#getViewManager()
	 */
	@Override
	public NccViewManager getViewManager() {
		return viewManager;
	}

}
