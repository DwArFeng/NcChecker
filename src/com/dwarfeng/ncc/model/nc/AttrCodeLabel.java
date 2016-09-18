package com.dwarfeng.ncc.model.nc;

import com.dwarfeng.dfunc.infs.Buildable;

/**
 * �ò�ͬ���Էֱ�ʵ�ֵĴ����ǩ��
 * @author DwArFeng
 * @since 1.8
 */
public final class AttrCodeLabel implements CodeLabel {

	/**
	 * �����ǩ�Ĺ�������
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Builder implements Buildable<AttrCodeLabel>{
		
		private int lineNumber = 0;
		private boolean markFlag = false;

		/**
		 * ����Ĭ�ϵĴ����ǩ��������
		 */
		public Builder(){};
		
		/**
		 * ���ô����ǩ���кš�
		 * @param val �����ǩ���кš�
		 * @return ����������
		 */
		public Builder lineNumber(int val){
			this.lineNumber = val;
			return this;
		}
		
		/**
		 * ���ô����Ƿ񱻱�ǡ�
		 * @param val ֵ��
		 * @return ����������
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
