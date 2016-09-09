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
			new FrontConfig(20);
	
	private final int maxRollback;
	
	private FrontConfig(int maxRollback) {
		this.maxRollback = maxRollback;
	}
	
	/**
	 * 前端模型配置的构造器。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Builder implements Buildable<FrontConfig>{
		
		private int maxRollback = DEFAULT.maxRollback;

		/**
		 * 生成默认的前端配置构造器。
		 */
		public Builder() {}
		
		/**
		 * 调整最大回滚数。
		 * @param val 值。
		 * @return 构造器自身。
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
	 * 获取最大回滚值。
	 * @return 最大回滚值。
	 */
	public int getMaxRolBack(){
		return maxRollback;
	}

}
