package com.dwarfeng.ncc.view.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import com.dwarfeng.ncc.view.NccViewObject;

/**
 * ���ѿ���վ��
 * @author DwArFeng
 * @since 1.8
 */
public interface NotifyControlPort extends NccViewObject{

	/**
	 * ���û�ѯ��һ���ļ���
	 * @param fileFilters ������Ҫ���ļ���������û�еĻ�����ָ��Ϊ <code>null</code>��
	 * @param allFileAllowed �Ƿ�����ѡ�������ļ�(*.*)��
	 * @return ѡ����ļ������ʲôҲû��ѡ��Ļ����򷵻� <code>null</code>��
	 */
	public File askFile(FileFilter[] fileFilters,boolean allFileAllowed);
	
}
