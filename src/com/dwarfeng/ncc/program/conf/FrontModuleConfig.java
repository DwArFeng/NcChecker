package com.dwarfeng.ncc.program.conf;

import com.dwarfeng.dfunc.infs.Buildable;

/**
 * 前端模型配置。
 * @author DwArFeng
 * @since 1.8
 */
public final class FrontModuleConfig {
	
	/**默认值*/
	public static final FrontModuleConfig DEFAULT = 
			new FrontModuleConfig(1000);
	
	private final int codesInPage;
	
	
	private FrontModuleConfig(int codesInPage) {
		this.codesInPage = codesInPage;
	}
	
	/**
	 * 前端模型配置的构造器。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Builder implements Buildable<FrontModuleConfig>{

		private int codesInPage = DEFAULT.codesInPage; 
		
		/**
		 * 生成默认的前端配置构造器。
		 */
		public Builder() {}
		
		/**
		 * 设置一页中有多少行代码。
		 * @param val 值。
		 * @return 构造器自身。
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
		public FrontModuleConfig build() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	/**
	 * 返回一页中的代码行数。
	 * @return 一页中的代码行数。
	 */
	public int getCodesInPage(){
		return codesInPage;
	}

}
