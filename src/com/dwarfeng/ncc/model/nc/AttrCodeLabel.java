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
		
		private int lineNumber;

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
	 * @see com.dwarfeng.ncc.model.nc.CodeLabel#getLineIndex()
	 */
	@Override
	public int getLineIndex() {
		return lineIndex;
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
				.append("]")
				.toString();
	}
	

}
