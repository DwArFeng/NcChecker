package com.dwarfeng.ncc.view.gui;

import java.io.File;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 * ���ѿ���վ��
 * @author DwArFeng
 * @since 1.8
 */
public interface NotifyCp{

	/**
	 * ���û�ѯ��һ���ļ���
	 * @param fileFilters ������Ҫ���ļ���������û�еĻ�����ָ��Ϊ <code>null</code>��
	 * @param allFileAllowed �Ƿ�����ѡ�������ļ�(*.*)��
	 * @return ѡ����ļ������ʲôҲû��ѡ��Ļ����򷵻� <code>null</code>��
	 */
	public File askFile(FileFilter[] fileFilters,boolean allFileAllowed);
	
	/**
	 * �û���ѡ��
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum AnswerType{
		/**��*/
		YES,
		/**��*/
		NO,
		/**ȡ��*/
		CANCEL,
	}
	
	/**
	 * ѡ������͡�
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum OptionType{
		/**YES-NOѡ��*/
		YES_NO(JOptionPane.YES_NO_OPTION),
		/**YES-NO-CANCELѡ��*/
		YES_NO_CANCEL(JOptionPane.YES_NO_CANCEL_OPTION),
		/**OK-CANCELѡ��*/
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
	 * ��Ϣ����ö�١�
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum MessageType{
		/**��������*/
		ERROR(JOptionPane.ERROR_MESSAGE),
		/**��Ϣ����*/
		INFOMATION(JOptionPane.INFORMATION_MESSAGE),
		/**��������*/
		WARNING(JOptionPane.WARNING_MESSAGE),
		/**ѯ������*/
		QUESTION(JOptionPane.QUESTION_MESSAGE),
		/**ƽ������*/
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
	 * ���û�����һ��ȷ�Ͽ�
	 * @param message ��Ϣ��
	 * @param title ���⡣
	 * @param optionType ѡ�����͡�
	 * @param messageType ��Ϣ���͡�
	 * @param icon ͼ�ꡣ
	 * @return �û���ѡ�
	 */
	public AnswerType showConfirm(Object message, String title, OptionType optionType, MessageType messageType, Icon icon);
	
}
