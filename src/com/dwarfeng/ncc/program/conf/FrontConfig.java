package com.dwarfeng.ncc.program.conf;

import com.dwarfeng.dfunc.infs.Buildable;

/**
 * 前端模型配置。
 * @author DwArFeng
 * @since 1.8
 */
public final class FrontConfig {
	
	/**默认值*/
	public static final FrontConfig DEFAULT = 
			new FrontConfig();
	
	private FrontConfig() {
	}
	
	/**
	 * 前端模型配置的构造器。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Builder implements Buildable<FrontConfig>{

		/**
		 * 生成默认的前端配置构造器。
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
