package com.dwarfeng.ncc.view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.dwarfeng.dfunc.prog.mvc.AbstractViewManager;
import com.dwarfeng.ncc.control.NccControlPort;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.view.gui.NccFrame;
import com.dwarfeng.ncc.view.gui.NccFrameControlPort;
import com.dwarfeng.ncc.view.gui.NotifyControlPort;
import com.dwarfeng.ncc.view.gui.ProgressMonitor;

/**
 * 数控代码验证程序中的视图控制器，可以提供视图控制端口。
 * @author DwArFeng
 * @since 1.8
 */
public final class NccViewManager extends AbstractViewManager<NccViewControlPort, NccControlPort, NccProgramAttrSet> {
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final String KEY_NOTINIT = "视图管理器没有初始化。";
	private static final String KEY_INITED = "视图管理器已经初始化了。";
	
	//------------------------------------------------------------------------------------------------
	
	private NccFrame mainFrame;
	
	private NccViewControlPort viewControlPort = new NccViewControlPort() {
		
		private boolean initFlag = false;

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.NccViewControlPort#init()
		 */
		@Override
		public void init() {
			if(initFlag) throw new IllegalStateException(KEY_INITED);
			initFlag = true;
			mainFrame = new NccFrame(programAttrSet,controlPort);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.NccViewControlPort#getFrameControlPort()
		 */
		@Override
		public NccFrameControlPort getMainFrameControlPort() {
			if(!initFlag) throw new IllegalStateException(KEY_NOTINIT);
			return mainFrame.getFrameControlPort();
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.NccViewControlPort#getNotifyControlPort()
		 */
		@Override
		public NotifyControlPort getNotifyControlPort() {
			if(!initFlag) throw new IllegalStateException(KEY_NOTINIT);
			return notifyControlPort;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.NccViewControlPort#getProgressMonitor()
		 */
		@Override
		public ProgressMonitor getProgressMonitor() {
			if(!initFlag) throw new IllegalStateException(KEY_NOTINIT);
			return mainFrame.getProgressMonitor();
		}
		
	};
	
	private final NotifyControlPort notifyControlPort = new NotifyControlPort() {
		
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
			final int res = fc.showOpenDialog(mainFrame);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.mvc.AbstractViewManager#getViewControlPort()
	 */
	@Override
	public NccViewControlPort getViewControlPort() {
		return viewControlPort;
	}

}
