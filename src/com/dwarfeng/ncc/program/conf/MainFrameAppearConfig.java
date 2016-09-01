package com.dwarfeng.ncc.program.conf;

import com.dwarfeng.dfunc.infs.Buildable;

/**
 *外观配置。
 * @author DwArFeng
 * @since 1.8
 */
public final class MainFrameAppearConfig {
	
	public static final String SF_extendedState = "mes";
	public static final String SF_frameWidth = "mfw";
	public static final String SF_frameHeitht = "mfh";
	public static final String SF_codePanelWidth = "mdw";
	public static final String SF_consolePanelHeight = "mnh";
	
	public static final MainFrameAppearConfig DEFAULT_CONFIG = 
			new MainFrameAppearConfig(0, 800, 600, 300, 100);
	
	/**
	 * 外观配置的构s造类。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public static class Builder implements Buildable<MainFrameAppearConfig>{

		private int extendedState = DEFAULT_CONFIG.extendedState;
		private int frameWidth = DEFAULT_CONFIG.frameWidth;
		private int frameHeight = DEFAULT_CONFIG.frameHeight;
		private int codePanelWidth = DEFAULT_CONFIG.codePanelWidth;
		private int consolePanelHeight = DEFAULT_CONFIG.consolePanelHeight;
		
		/**
		 * 设定主界面的扩展状态。
		 * @param val 主界面的扩展状态。
		 * @return 构造器自身。
		 */
		public Builder extendedState(int val){
			this.extendedState = val;
			return this;
		}
		
		/**
		 * 设定主界面的宽度。
		 * @param val 主界面的宽度。
		 * @return 构造器自身。
		 */
		public Builder frameWidth(int val){
			this.frameWidth = val;
			return this;
		}
		
		/**
		 * 设定主界面的高度。
		 * @param val 主界面的高度。
		 * @return 构造器自身。
		 */
		public Builder frameHeight(int val){
			this.frameHeight = val;
			return this;
		}
		
		/**
		 * 设定主界面中代码界面的宽度。
		 * @param val 代码界面的宽度。
		 * @return 构造器自身。
		 */
		public Builder codePanelWidth(int val){
			this.codePanelWidth = val;
			return this;
		}
		
		/**
		 * 设定主界面中控制台界面的高度。
		 * @param val 控制台界面的高度。
		 * @return 构造器自身。
		 */
		public Builder consolePanelHeight(int val){
			this.consolePanelHeight = val;
			return this;
		}
		
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.dfunc.infs.Buildable#build()
		 */
		@Override
		public MainFrameAppearConfig build() {
			return new MainFrameAppearConfig(
					extendedState, 
					frameWidth, 
					frameHeight, 
					codePanelWidth, 
					consolePanelHeight
			);
		}
		
	}
	
	private final int extendedState;
	private final int frameWidth;
	private final int frameHeight;
	private final int codePanelWidth;
	private final int consolePanelHeight;

	private MainFrameAppearConfig(
			int mExtendedState,
			int mFrameWidth,
			int mFrameHeight,
			int mCodePanelWidth,
			int mConsolePanelHeight
			) {
		this.extendedState = mExtendedState;
		this.frameHeight = mFrameHeight;
		this.frameWidth = mFrameWidth;
		this.codePanelWidth = mCodePanelWidth;
		this.consolePanelHeight = mConsolePanelHeight;
	}

	/**
	 * 主界面的扩展状态。
	 * @return the mExtendedState
	 */
	public int getExtendedState() {
		return extendedState;
	}

	/**
	 * 主界面的宽度。
	 * @return the mFrameWidth
	 */
	public int getFrameWidth() {
		return frameWidth;
	}

	/**
	 * 主界面的高度。
	 * @return the mFrameHeight
	 */
	public int getFrameHeight() {
		return frameHeight;
	}

	/**
	 * 主界面中代码界面的宽度。
	 * @return the mCodePanelWidth
	 */
	public int getCodePanelWidth() {
		return codePanelWidth;
	}

	/**
	 * 主界面中控制点的高度。
	 * @return the mConsolePanelHeight
	 */
	public int getConsolePanelHeight() {
		return consolePanelHeight;
	}

}
