package com.dwarfeng.ncc.view.gui;

/**
 * 进度监视器。
 * @author DwArFeng
 * @since 1.8
 */
public interface ProgressMonitor{

	/**
	 * 开始对进度进行监视。
	 * @throws IllegalStateException 重复启动。
	 */
	public void startMonitor();
	
	/**
	 * 结束对进度的监视。
	 * @throws IllegalStateException 重复停止。
	 */
	public void endMonitor();
	
	/**
	 * 设置总工作量。
	 * @param val 总工作量。
	 * @throws IllegalStateException 在停止监视的期间调用。
	 */
	public void setTotleProgress(int val);
	
	/**
	 * 设置当前工作量。
	 * @param val 当前工作量。
	 * @throws IllegalStateException 在停止监视的期间调用。
	 */
	public void setCurrentProgress(int val);
	
	/**
	 * 设定监视器是否工作在不确定模式下。
	 * @param aFlag 是否工作在不确定模式下。
	 * @throws IllegalStateException 在停止监视的期间调用。
	 */
	public void setIndeterminate(boolean aFlag);
	
	/**
	 * 该工作是否被人为终止。
	 * <p> 该方法需要与 {@link ProgressMonitor#suspend()}保持同步。
	 * @return 是否被人为终止。
	 * @throws IllegalStateException 在停止监视的期间调用。
	 */
	public boolean isSuspend();
	
	/**
	 * 人为终止该工作。
	 * <p> 该方法需要与 {@link ProgressMonitor#isSuspend()}保持同步。
	 * @throws IllegalStateException 在停止监视的期间调用。
	 */
	public void suspend();
	
	/**
	 * 设置当前工作的消息。
	 * @param message 当前的工作消息。
	 * @throws IllegalStateException 在停止监视的期间调用。
	 */
	public void setMessage(String message);
}
