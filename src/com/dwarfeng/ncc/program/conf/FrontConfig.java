package com.dwarfeng.ncc.program.conf;

import com.dwarfeng.dfunc.infs.Buildable;

/**
 * ǰ��ģ�����á�
 * @author DwArFeng
 * @since 1.8
 */
public final class FrontConfig {
	
	/**Ĭ��ֵ*/
	public static final FrontConfig DEFAULT = 
			new FrontConfig(20);
	
	private final int maxRollback;
	
	private FrontConfig(int maxRollback) {
		this.maxRollback = maxRollback;
	}
	
	/**
	 * ǰ��ģ�����õĹ�������
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Builder implements Buildable<FrontConfig>{
		
		private int maxRollback = DEFAULT.maxRollback;

		/**
		 * ����Ĭ�ϵ�ǰ�����ù�������
		 */
		public Builder() {}
		
		/**
		 * �������ع�����
		 * @param val ֵ��
		 * @return ����������
		 */
		public Builder maxRollback(int val){
			maxRollback = Math.max(0, val);
			return this;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dfunc.infs.Buildable#build()
		 */
		@Override
		public FrontConfig build() {
			return new FrontConfig(maxRollback);
		}
		
	}
	
	/**
	 * ��ȡ���ع�ֵ��
	 * @return ���ع�ֵ��
	 */
	public int getMaxRolBack(){
		return maxRollback;
	}

}
