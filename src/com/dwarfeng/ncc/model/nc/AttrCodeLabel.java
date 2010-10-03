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
		
		private int lineNumber;

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
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dfunc.infs.Buildable#build()
		 */
		@Override
		public AttrCodeLabel build() {
			return new AttrCodeLabel(lineNumber);
		}
		
	}
	private final int lineIndex;
	
	private AttrCodeLabel(int lineIndex) {
		this.lineIndex = lineIndex;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.nc.CodeLabel#getLineIndex()
	 */
	@Override
	public int getLineIndex() {
		return lineIndex;
	}

}
