package com.dwarfeng.ncc.view.gui;

import java.awt.Component;
import java.io.File;
import java.util.Objects;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.dwarfeng.ncc.view.AbstractNccViewObject;
import com.dwarfeng.ncc.view.NccViewManager;

/**
 * ��������
 * <p> ������û������ѷ�����
 * @author DwArFeng
 * @since 1.8
 */
public final class Notifyer extends AbstractNccViewObject {

	private final Component root;
	
	/**
	 * ����һ������ָ����ͼ��������ָ����Ԫ�ص���������
	 * @param viewManager ָ������ͼ��������
	 * @param root ָ���ĸ�Ԫ�ء�
	 * @throws NullPointerException ����һ����ڲ���Ϊ <code>null</code>�׳����쳣��
	 */
	public Notifyer(NccViewManager viewManager, Component root) {
		super(viewManager);
		Objects.requireNonNull(root);
		this.root = root;
	}
	
	/**
	 * ������������Ӧ�����ѿ���վ��
	 * @return ���ѿ���վ��
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
