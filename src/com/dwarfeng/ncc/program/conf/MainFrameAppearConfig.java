package com.dwarfeng.ncc.program.conf;

import com.dwarfeng.dfunc.infs.Buildable;

/**
 *������á�
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
	 * ������õĹ�s���ࡣ
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
		 * �趨���������չ״̬��
		 * @param val ���������չ״̬��
		 * @return ����������
		 */
		public Builder extendedState(int val){
			this.extendedState = val;
			return this;
		}
		
		/**
		 * �趨������Ŀ�ȡ�
		 * @param val ������Ŀ�ȡ�
		 * @return ����������
		 */
		public Builder frameWidth(int val){
			this.frameWidth = val;
			return this;
		}
		
		/**
		 * �趨������ĸ߶ȡ�
		 * @param val ������ĸ߶ȡ�
		 * @return ����������
		 */
		public Builder frameHeight(int val){
			this.frameHeight = val;
			return this;
		}
		
		/**
		 * �趨�������д������Ŀ�ȡ�
		 * @param val �������Ŀ�ȡ�
		 * @return ����������
		 */
		public Builder codePanelWidth(int val){
			this.codePanelWidth = val;
			return this;
		}
		
		/**
		 * �趨�������п���̨����ĸ߶ȡ�
		 * @param val ����̨����ĸ߶ȡ�
		 * @return ����������
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
	 * ���������չ״̬��
	 * @return the mExtendedState
	 */
	public int getExtendedState() {
		return extendedState;
	}

	/**
	 * ������Ŀ�ȡ�
	 * @return the mFrameWidth
	 */
	public int getFrameWidth() {
		return frameWidth;
	}

	/**
	 * ������ĸ߶ȡ�
	 * @return the mFrameHeight
	 */
	public int getFrameHeight() {
		return frameHeight;
	}

	/**
	 * �������д������Ŀ�ȡ�
	 * @return the mCodePanelWidth
	 */
	public int getCodePanelWidth() {
		return codePanelWidth;
	}

	/**
	 * �������п��Ƶ�ĸ߶ȡ�
	 * @return the mConsolePanelHeight
	 */
	public int getConsolePanelHeight() {
		return consolePanelHeight;
	}

}
