package com.dwarfeng.ncc.view.gui;

import java.awt.Component;
import java.io.File;
import java.util.Objects;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.dwarfeng.ncc.view.AbstractNccViewObject;
import com.dwarfeng.ncc.view.NccViewManager;

/**
 * 提醒器。
 * <p> 定义对用户的提醒方法。
 * @author DwArFeng
 * @since 1.8
 */
public final class Notifyer extends AbstractNccViewObject {

	private final Component root;
	
	/**
	 * 创建一个具有指定视图管理器，指定根元素的提醒器。
	 * @param viewManager 指定的视图管理器。
	 * @param root 指定的根元素。
	 * @throws NullPointerException 任意一个入口参数为 <code>null</code>抛出此异常。
	 */
	public Notifyer(NccViewManager viewManager, Component root) {
		super(viewManager);
		Objects.requireNonNull(root);
		this.root = root;
	}
	
	/**
	 * 返回提醒器对应的提醒控制站。
	 * @return 提醒控制站。
	 */
	public NotifyControlPort getNotifyControlPort(){
		return controlPort;
	}
	
	private final NotifyControlPort controlPort = new NotifyControlPort() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.NccViewObject#getViewManager()
		 */
		@Override
		public NccViewManager getViewManager() {
			return viewManager;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.gui.NotifyControlPort#askFile(javax.swing.filechooser.FileFilter[], boolean)
		 */
		@Override
		public File askFile(FileFilter[] fileFilters, boolean allFileAllowed) {
			JFileChooser fc = new JFileChooser();
			fc.resetChoosableFileFilters();
			for(FileFilter ff:fileFilters) fc.setFileFilter(ff);
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.setAcceptAllFileFilterUsed(allFileAllowed);
			fc.setMultiSelectionEnabled(false);
			final int res = fc.showOpenDialog(root);
			switch (res) {
				case JFileChooser.CANCEL_OPTION:
					return null;
				case JFileChooser.ERROR_OPTION:
					return null;
				default:
					return fc.getSelectedFile();
			}
		}
	};

}
