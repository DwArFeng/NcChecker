package com.dwarfeng.ncc.module.nc;

import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.ncc.module.AbstractNccModuleObject;
import com.dwarfeng.ncc.module.NccModuleManager;
import com.dwarfeng.ncc.module.expl.ExplState;

/**
 * 利用数组实现的NC代码列表。
 * @author DwArFeng
 * @since 1.8
 */
public final class ArrayCodeList extends AbstractNccModuleObject implements CodeList {

	private final String[] codeArray;
	
	public ArrayCodeList(NccModuleManager moduleManager,String[] codeArray) {
		super(moduleManager);
		Objects.requireNonNull(codeArray);
		this.codeArray = codeArray;
		explState = ExplState.NOTEXPL;
	}

	private void ensureIndex(int lineIndex){
		if(lineIndex < 0 || lineIndex >= codeArray.length)
			//TODO 使用StringField
			throw new IndexOutOfBoundsException("超界");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.nc.NcCode#getCode(int)
	 */
	@Override
	public String getCode(int lineIndex) {
		ensureIndex(lineIndex);
		return codeArray[lineIndex];
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.nc.NcCode#getTotleLine()
	 */
	@Override
	public int getTotleLine() {
		return codeArray.length;
	}
	
	private ExplState explState;
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.nc.NcCode#getExplState()
	 */
	@Override
	public ExplState getExplState() {
		lock.readLock().lock();
		try{
			return explState;
		}finally{
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.nc.NcCode#setExpleState(com.dwarfeng.ncc.module.nc.NcCode.ExplState)
	 */
	@Override
	public void setExpleState(ExplState explState) {
		lock.writeLock().lock();
		try{
			this.explState = explState;
		}finally{
			lock.writeLock().unlock();
		}
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb	.append("ArrayNcCode [totleLine = ")
			.append(getTotleLine())
			.append(" explainState = ")
			.append(explState)
			.append("]");
		return sb.toString();
	}
}
