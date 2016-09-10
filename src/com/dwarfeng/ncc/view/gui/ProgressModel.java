package com.dwarfeng.ncc.view.gui;

/**
 * 进度模型。
 * <p> 进度模型是进度面板用于监视进度，维护方法进行维护的一个模型，该模型工作在多线程模式下。
 * 维护者通常在不断的修改进度模型的属性，而进度监视器则不断的读取进度模型的各种属性，并将其用进度条
 * 与标签的形式显示在主界面中。
 * <br> 该模型设计多线程工作，所有的方法必须内部同步。
 * @author DwArFeng
 * @since 1.8
 */
public interface ProgressModel {
	
	/**
	 * 返回模型的总工作量。
	 * <p> 该值在任何情况下不能小于当前进度或1。
	 * @return 模型的总工作量。
	 */
	public int getMaximum();
	
	/**
	 * 设置模型的总工作量。
	 * @param val 指定值。
	 */
	public void setMaximum(int val);
	
	/**
	 * 返回模型的当前进度。
	 * <p> 该值在任何情况下不能小于0或大于当前进度。
	 * @return 模型的进度。
	 */
	public int getValue();
	
	/**
	 * 设置模型的当前进度。
	 * @param val 指定值。
	 */
	public void setValue(int val);
	
	/**
	 * 该模型是否是进度未知型的。
	 * @return 是否是进度未知型。
	 */
	public boolean isIndeterminate();
	
	/**
	 * 设置该模型是否为进度未知型的。
	 * @param aFlag 标记。
	 */
	public void setIndeterminate(boolean aFlag);
	
	/**
	 * 返回用于说明的文本。
	 * <p> 该文本在任何时刻不能为null。
	 * @return 文本。
	 */
	public String getLabelText();
	
	/**
	 * 设置用于说明的文本。
	 * @param str 用于说明的文本。
	 */
	public void setLabelText(String str);
	
	/**
	 * 返回操作是否被中止。
	 * @return 操作是否被终止。
	 */
	public boolean isSuspend();
	
	/**
	 * 中止当前操作。
	 */
	public void suspend();
	
	/**
	 * 返回当前操作是否自然结束。
	 * @return 是否自然结束。
	 */
	public boolean isEnd();
	
	/**
	 * 自然结束当前操作。
	 */
	public void end();

}
