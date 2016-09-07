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
			new FrontConfig();
	
	private FrontConfig() {
	}
	
	/**
	 * ǰ��ģ�����õĹ�������
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Builder implements Buildable<FrontConfig>{

		/**
		 * ����Ĭ�ϵ�ǰ�����ù�������
		 */
		public Builder() {}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dfunc.infs.Buildable#build()
		 */
		@Override
		public FrontConfig build() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}
