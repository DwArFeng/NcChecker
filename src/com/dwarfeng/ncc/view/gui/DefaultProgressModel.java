package com.dwarfeng.ncc.view.gui;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * {@link ProgressModel}的默认实现。
 * @author DwArFeng
 * @since 1.8
 */
public final class DefaultProgressModel implements ProgressModel {

	private final ReadWriteLock lock;
	
	public DefaultProgressModel() {
		lock = new ReentrantReadWriteLock();
		maximum = 1;
		value = 0;
		indeFlag = false;
		labelText = "";
		suspendFlag = false;
		endFlag = false;
	}

	private int maximum;
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.view.gui.ProgressModel#getMaximum()
	 */
	@Override
	public int getMaximum() {
		lock.readLock().lock();
		try{
			return Math.max(maximum, 1);
		}finally{
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.view.gui.ProgressModel#setMaximum(int)
	 */
	@Override
	public void setMaximum(int val) {
		lock.writeLock().lock();
		try{
			this.maximum = val;	
		}finally{
			lock.writeLock().unlock();
		}
	}

	private int value;
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.view.gui.ProgressModel#getValue()
	 */
	@Override
	public int getValue() {
		lock.readLock().lock();
		try{
			return Math.min(value, Math.max(maximum, 1) - 1);
		}finally{
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.view.gui.ProgressModel#setValue(int)
	 */
	@Override
	public void setValue(int val) {
		lock.writeLock().lock();
		try{
			this.value = val;
		}finally{
			lock.writeLock().unlock();
		}
	}

	private boolean indeFlag;
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.view.gui.ProgressModel#isIndeterminate()
	 */
	@Override
	public boolean isIndeterminate() {
		lock.readLock().lock();
		try{
			return indeFlag;
		}finally{
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.view.gui.ProgressModel#setIndeterminate(boolean)
	 */
	@Override
	public void setIndeterminate(boolean aFlag) {
		lock.writeLock().lock();
		try{
			this.indeFlag = aFlag;
		}finally{
			lock.writeLock().unlock();
		}
	}
	
	private String labelText;

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.view.gui.ProgressModel#getLabelText()
	 */
	@Override
	public String getLabelText() {
		lock.readLock().lock();
		try{
			return labelText == null ? "" : labelText;
		}finally{
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.view.gui.ProgressModel#setLabelText(java.lang.String)
	 */
	@Override
	public void setLabelText(String str) {
		lock.writeLock().lock();
		try{
			this.labelText = str;
		}finally{
			lock.writeLock().unlock();
		}
	}

	private boolean suspendFlag;
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.view.gui.ProgressModel#isSuspend()
	 */
	@Override
	public boolean isSuspend() {
		lock.readLock().lock();
		try{
			return suspendFlag;
		}finally{
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.view.gui.ProgressModel#suspend()
	 */
	@Override
	public void suspend() {
		lock.writeLock().lock();
		try{
			suspendFlag = true;
		}finally{
			lock.writeLock().unlock();
		}
	}

	private boolean endFlag;
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.view.gui.ProgressModel#isEnd()
	 */
	@Override
	public boolean isEnd() {
		lock.readLock().lock();
		try{
			return endFlag;
		}finally{
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.view.gui.ProgressModel#end()
	 */
	@Override
	public void end() {
		lock.writeLock().lock();
		try{
			endFlag = true;
		}finally{
			lock.writeLock().unlock();
		}
	}

}
