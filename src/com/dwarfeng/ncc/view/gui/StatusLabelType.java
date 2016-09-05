package com.dwarfeng.ncc.view.gui;

import java.awt.Color;
import java.awt.Font;

/**
 * 状态标签的种类。
 * @author DwArFeng
 * @since 1.8
 */
public enum StatusLabelType {

	/**正常状态*/
	NORMAL(Color.BLACK,new Font(Font.SANS_SERIF,Font.PLAIN,12));
	;
	private final Color textColor;
	private final Font textFont;
	
	private StatusLabelType(Color textColor,Font textFont) {
		this.textColor = textColor;
		this.textFont = textFont;
	}
	
	/**
	 * 获取这种类型的字体颜色。
	 * @return 字体颜色。
	 */
	public Color getTextColor(){
		return this.textColor;
	}
	
	/**
	 * 返回这种类型的字体。
	 * @return 字体。
	 */
	public Font getTexFont(){
		return this.textFont;
	}
	
}
