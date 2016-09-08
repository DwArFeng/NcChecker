package com.dwarfeng.ncc.view.gui;

/**
 * 表示一个类拥有多种状态。
 * @author DwArFeng
 * @since 1.8
 */
public interface MutiStatus {

	/**
	 * 锁定编辑。
	 */
	public abstract void lockEdit();

	/**
	 * 解锁编辑。
	 */
	public abstract void unlockEdit();

	/**
	 * 更改无文件状态。
	 * @param aFlag 标记。
	 */
	public abstract void noneFile(boolean aFlag);

}