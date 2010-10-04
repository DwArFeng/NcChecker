package com.dwarfeng.ncc.view.gui;

import java.io.File;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 * 提醒控制站。
 * @author DwArFeng
 * @since 1.8
 */
public interface NotifyCp{

	/**
	 * 向用户询问一个文件。
	 * @param fileFilters 所有需要的文件过滤器，没有的话可以指定为 <code>null</code>。
	 * @param allFileAllowed 是否允许选择所有文件(*.*)。
	 * @return 选择的文件，如果什么也没有选择的话，则返回 <code>null</code>。
	 */
	public File askFile(FileFilter[] fileFilters,boolean allFileAllowed);
	
	/**
	 * 用户的选择。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum AnswerType{
		/**是*/
		YES,
		/**否*/
		NO,
		/**取消*/
		CANCEL,
	}
	
	/**
	 * 选项的类型。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum OptionType{
		/**YES-NO选项*/
		YES_NO(JOptionPane.YES_NO_OPTION),
		/**YES-NO-CANCEL选项*/
		YES_NO_CANCEL(JOptionPane.YES_NO_CANCEL_OPTION),
		/**OK-CANCEL选项*/
		OK_CANCEL(JOptionPane.OK_CANCEL_OPTION),
		;
		final int val;
		
		private OptionType(int val) {
			this.val = val;
		}
		
		public int getVal(){
			return this.val;
		}
	}
	
	/**
	 * 信息类型枚举。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum MessageType{
		/**错误类型*/
		ERROR(JOptionPane.ERROR_MESSAGE),
		/**信息类型*/
		INFOMATION(JOptionPane.INFORMATION_MESSAGE),
		/**警告类型*/
		WARNING(JOptionPane.WARNING_MESSAGE),
		/**询问类型*/
		QUESTION(JOptionPane.QUESTION_MESSAGE),
		/**平常类型*/
		PLAIN(JOptionPane.PLAIN_MESSAGE),
		;
		final int val;
		
		private MessageType(int val) {
			this.val = val;
		}
		
		public int getVal(){
			return this.val;
		}
	}
	
	/**
	 * 给用户发送一个确认框。
	 * @param message 信息。
	 * @param title 标题。
	 * @param optionType 选项类型。
	 * @param messageType 信息类型。
	 * @param icon 图标。
	 * @return 用户的选项。
	 */
	public AnswerType showConfirm(Object message, String title, OptionType optionType, MessageType messageType, Icon icon);
	
}
