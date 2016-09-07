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
			new FrontConfig(10000);
	
	private final int codesInPage;
	
	
	private FrontConfig(int codesInPage) {
		this.codesInPage = codesInPage;
	}
	
	/**
	 * ǰ��ģ�����õĹ�������
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Builder implements Buildable<FrontConfig>{

		private int codesInPage = DEFAULT.codesInPage; 
		
		/**
		 * ����Ĭ�ϵ�ǰ�����ù�������
		 */
		public Builder() {}
		
		/**
		 * ����һҳ���ж����д��롣
		 * @param val ֵ��
		 * @return ����������
		 */
		public Builder condesInPage(int val){
			this.codesInPage = val;
			return this;
		}
		
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
	
	/**
	 * ����һҳ�еĴ���������
	 * @return һҳ�еĴ���������
	 */
	public int getCodesInPage(){
		return codesInPage;
	}

}
