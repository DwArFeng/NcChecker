package com.dwarfeng.ncc.model.nc;

import com.dwarfeng.dfunc.infs.Buildable;

/**
 * 用不同属性分别实现的代码标签。
 * @author DwArFeng
 * @since 1.8
 */
public final class AttrCodeLabel implements CodeLabel {

	/**
	 * 代码标签的构造器。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Builder implements Buildable<AttrCodeLabel>{
		
		private int lineNumber = 0;
		private boolean markFlag = false;

		/**
		 * 生成默认的代码标签构造器。
		 */
		public Builder(){};
		
		/**
		 * 设置代码标签的行号。
		 * @param val 代码标签的行号。
		 * @return 构造器自身。
		 */
		public Builder lineNumber(int val){
			this.lineNumber = val;
			return this;
		}
		
		/**
		 * 设置代码是否被标记。
		 * @param val 值。
		 * @return 构造器自身。
		 */
		public Builder markFlag(boolean val){
			this.markFlag = val;
			return this;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dfunc.infs.Buildable#build()
		 */
		@Override
		public AttrCodeLabel build() {
			return new AttrCodeLabel(lineNumber, markFlag);
		}
		
	}
	private final int lineIndex;
	private final boolean markFlag;
	
	private AttrCodeLabel(int lineIndex, boolean markFlag) {
		this.lineIndex = lineIndex;
		this.markFlag = markFlag;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.model.nc.CodeLabel#getLineIndex()
	 */
	@Override
	public int getLineIndex() {
		return lineIndex;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.model.nc.CodeLabel#isMarked()
	 */
	@Override
	public boolean isMarked() {
		return markFlag;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder()
				.append("AttrCodeLabel [line = ")
				.append(getLineIndex())
				.append(", markFlag = ")
				.append(markFlag)
				.append("]")
				.toString();
	}

}
