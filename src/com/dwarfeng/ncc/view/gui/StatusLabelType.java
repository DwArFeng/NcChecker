package com.dwarfeng.ncc.view.gui;

import java.awt.Color;
import java.awt.Font;

/**
 * ״̬��ǩ�����ࡣ
 * @author DwArFeng
 * @since 1.8
 */
public enum StatusLabelType {

	/**����״̬*/
	NORMAL(Color.BLACK,new Font(Font.SANS_SERIF,Font.PLAIN,12));
	;
	private final Color textColor;
	private final Font textFont;
	
	private StatusLabelType(Color textColor,Font textFont) {
		this.textColor = textColor;
		this.textFont = textFont;
	}
	
	/**
	 * ��ȡ�������͵�������ɫ��
	 * @return ������ɫ��
	 */
	public Color getTextColor(){
		return this.textColor;
	}
	
	/**
	 * �����������͵����塣
	 * @return ���塣
	 */
	public Font getTexFont(){
		return this.textFont;
	}
	
}
