package com.dwarfeng.ncc.view.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import com.dwarfeng.ncc.view.NccViewObject;

/**
 * 提醒控制站。
 * @author DwArFeng
 * @since 1.8
 */
public interface NotifyControlPort extends NccViewObject{

	/**
	 * 向用户询问一个文件。
	 * @param fileFilters 所有需要的文件过滤器，没有的话可以指定为 <code>null</code>。
	 * @param allFileAllowed 是否允许选择所有文件(*.*)。
	 * @return 选择的文件，如果什么也没有选择的话，则返回 <code>null</code>。
	 */
	public File askFile(FileFilter[] fileFilters,boolean allFileAllowed);
	
}
