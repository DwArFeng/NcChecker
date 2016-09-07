package com.dwarfeng.ncc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import com.dwarfeng.dfunc.gui.JAdjustableBorderPanel;
import com.dwarfeng.dfunc.gui.JConsole;
import com.dwarfeng.dfunc.gui.JMenuItemAction;
import com.dwarfeng.dfunc.prog.mvc.AbstractViewManager;
import com.dwarfeng.ncc.control.NccControlPort;
import com.dwarfeng.ncc.module.nc.Code;
import com.dwarfeng.ncc.module.nc.CodeSerial;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.conf.MfAppearConfig;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.gui.CodeLabelRender;
import com.dwarfeng.ncc.view.gui.CodeRender;
import com.dwarfeng.ncc.view.gui.FrameCp;
import com.dwarfeng.ncc.view.gui.NotifyCp;
import com.dwarfeng.ncc.view.gui.ProgCp;
import com.dwarfeng.ncc.view.gui.StatusLabelType;

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
			mainFrame = new NccFrame();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.NccViewControlPort#getFrameControlPort()
		 */
		@Override
		public FrameCp frameCp() {
			if(!initFlag) throw new IllegalStateException(KEY_NOTINIT);
			return mainFrame.frameControlPort;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.NccViewControlPort#getNotifyControlPort()
		 */
		@Override
		public NotifyCp notifyCp() {
			if(!initFlag) throw new IllegalStateException(KEY_NOTINIT);
			return notifyControlPort;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.NccViewControlPort#getProgressMonitor()
		 */
		@Override
		public ProgCp progCp() {
			if(!initFlag) throw new IllegalStateException(KEY_NOTINIT);
			return mainFrame.monitor;
		}
		
	};
	
	private final NotifyCp notifyControlPort = new NotifyCp() {
		
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
	
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final StringFieldKey KEY_TITLE = StringFieldKey.MAINFRAME_TITLE;
	private static final StringFieldKey KEY_FILE = StringFieldKey.MENU_FILE;
	private static final StringFieldKey KEY_EDIT = StringFieldKey.MENU_EDIT;
	private static final StringFieldKey KEY_OPENFILE = StringFieldKey.MENU_FILE_OPENFILE;
	private static final StringFieldKey KEY_OPENFILE_DES = StringFieldKey.MENU_FILE_OPENFILE_DES;
	private static final StringFieldKey KEY_NOMISSION = StringFieldKey.MAINFRAME_NOMISSION;
	
	//------------------------------------------------------------------------------------------------
	
	private NccFrame mainFrame;
	
	private class NccFrame extends JFrame{
		
		private final FrameCp frameControlPort = new FrameCp() {
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.NccFrameControlPort#setMainFrameVisible(boolean)
			 */
			@Override
			public void setVisible(boolean aFlag) {
				NccFrame.this.setVisible(aFlag);
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.NccFrameControlPort#applyAppearance(com.dwarfeng.ncc.program.conf.MainFrameAppearConfig)
			 */
			@Override
			public void applyAppearanceConfig(MfAppearConfig config) {
				Objects.requireNonNull(config);
				setExtendedState(config.getExtendedState());
				setSize(config.getFrameWidth(), config.getFrameHeight());
				mainPanel.setWestPreferredValue(config.getCodePanelWidth());
				rightInMain.setSouthPreferredValue(config.getConsolePanelHeight());
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.NccFrameControlPort#getCurrentAppearance()
			 */
			@Override
			public MfAppearConfig getAppearanceConfig() {
				return new MfAppearConfig.Builder()
						.extendedState(getExtendedState())
						.frameWidth(getWidth())
						.frameHeight(getHeight())
						.codePanelWidth(mainPanel.getWestPreferredValue())
						.consolePanelHeight(rightInMain.getSouthPreferredValue())
						.build();
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.NccFrameControlPort#setStatusLabelMessage(java.lang.String, com.dwarfeng.ncc.view.gui.StatusLabelType)
			 */
			@Override
			public void setStatusLabelMessage(String message, StatusLabelType type) {
				statusLabel.setForeground(type.getTextColor());
				statusLabel.setFont(type.getTexFont());
				statusLabel.setText(message);
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.NccFrameControlPort#traceConsole(java.lang.String)
			 */
			@Override
			public void traceInConsole(String message) {
				console.getOut().println(message);
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.NccFrameControlPort#showCode(com.dwarfeng.ncc.module.nc.CodeSerial)
			 */
			@Override
			public void showCode(CodeSerial codeSerial) {
				codeCenter1.codeLabelModel.removeAllElements();
				codeCenter1.codeModel.removeAllElements();
				for(Code code:codeSerial){
					codeCenter1.codeLabelModel.addElement(code);
					codeCenter1.codeModel.addElement(code);
				}
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.NccFrameControlPort#lockEdit()
			 */
			@Override
			public void lockEdit() {
				// TODO 临时的方法。
				modiFlag = true;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.NccFrameControlPort#unlockEdit()
			 */
			@Override
			public void unlockEdit() {
				//TODO 临时的方法。
				modiFlag = false;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.FrameCp#setModiFlag(boolean)
			 */
			@Override
			public void setModiFlag(boolean aFlag) {
				modiFlag = aFlag;
			}
		};
		
		private final ProgCp monitor = new ProgCp(){

			private final Lock lock = new ReentrantLock();
			
			private boolean startFlag = false;
			private boolean suspendFlag = false;
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.ProgressMonitor#startMonitor()
			 */
			@Override
			public void startMonitor() {
				if(startFlag) throw new IllegalStateException();
				startFlag = true;
				progressSuspendButton.setEnabled(true);
				progressBar.setEnabled(true);
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.ProgressMonitor#setindeterminate(boolean)
			 */
			@Override
			public void setIndeterminate(boolean aFlag) {
				if(!startFlag) throw new IllegalStateException();
				progressBar.setIndeterminate(aFlag);
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.ProgressMonitor#setTotleProgress(int)
			 */
			@Override
			public void setTotleProgress(int val) {
				if(!startFlag) throw new IllegalStateException();
				progressBar.setMaximum(val);
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.ProgressMonitor#setCurrentProgress(int)
			 */
			@Override
			public void setCurrentProgress(int val) {
				if(!startFlag) throw new IllegalStateException();
				progressBar.setValue(val);
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.ProgressMonitor#isSuspend()
			 */
			@Override
			public boolean isSuspend() {
				if(!startFlag) throw new IllegalStateException();
				lock.lock();
				try{
					return suspendFlag;
				}finally{
					lock.unlock();
				}
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.ProgressMonitor#endMonitor()
			 */
			@Override
			public void endMonitor() {
				if(!startFlag) throw new IllegalStateException();
				progressBar.setValue(0);
				progressBar.setEnabled(false);
				progressSuspendButton.setEnabled(false);
				progressLabel.setText(programAttrSet.getStringField(KEY_NOMISSION));
				progressBar.setIndeterminate(false);
				startFlag = false;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.ProgressMonitor#setMessage(java.lang.String)
			 */
			@Override
			public void setMessage(String message) {
				if(!startFlag) throw new IllegalStateException();
				progressLabel.setText(message);
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.ProgressMonitor#suspend()
			 */
			@Override
			public void suspend() {
				if(!startFlag) throw new IllegalStateException();
				lock.lock();
				try{
					suspendFlag = true;
				}finally{
					lock.unlock();
				}
			}
			
		};
		
		
		
		

		private final JConsole console;
		private final JLabel statusLabel;
		private final JAdjustableBorderPanel mainPanel;
		private final JAdjustableBorderPanel rightInMain;
		private final JLabel progressLabel;
		private final JButton progressSuspendButton;
		private final JProgressBar progressBar;
		private final NccMenu menu;
		private final CodeCenter1 codeCenter1;
		private final JTabbedPane codeCenterPanel;
		
		private boolean modiFlag = false;

		public NccFrame(){
			this.codeCenter1 = new CodeCenter1();
			
			setSize(new Dimension(800, 600));
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					controlPort.exitProgram();
				}
			});
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setTitle(programAttrSet.getStringField(KEY_TITLE));
			
			mainPanel = new JAdjustableBorderPanel();
			mainPanel.setWestPreferredValue(400);
			mainPanel.setSeperatorThickness(5);
			mainPanel.setWestEnabled(true);
			getContentPane().add(mainPanel, BorderLayout.CENTER);
			
			rightInMain = new JAdjustableBorderPanel();
			rightInMain.setBorder(null);
			rightInMain.setSeperatorThickness(5);
			rightInMain.setSouthEnabled(true);
			mainPanel.add(rightInMain, BorderLayout.CENTER);
			
			console = new JConsole();
			console.setInputFieldVisible(false);
			console.setInputFieldEnabled(false);
			rightInMain.add(console, BorderLayout.SOUTH);
			
			JPanel codePanel = new JPanel();
			mainPanel.add(codePanel, BorderLayout.WEST);
			codePanel.setLayout(new BorderLayout(0, 0));
			
			JPanel codeNorthPanel = new JPanel();
			codePanel.add(codeNorthPanel, BorderLayout.NORTH);
			
			JPanel codeSouthPanel = new JPanel();
			codePanel.add(codeSouthPanel, BorderLayout.SOUTH);
			
			codeCenterPanel = new JTabbedPane(JTabbedPane.LEFT);
			
			codePanel.add(codeCenterPanel, BorderLayout.CENTER);
			
			codeCenterPanel.add(codeCenter1, 0);
			//TODO 改成图标
			codeCenterPanel.setTitleAt(0, "代码功能");

			JPanel statusPanel = new JPanel();
			getContentPane().add(statusPanel, BorderLayout.SOUTH);
			GridBagLayout gbl_statusPanel = new GridBagLayout();
			gbl_statusPanel.columnWidths = new int[]{55, 120, 37, 28, 0};
			gbl_statusPanel.rowHeights = new int[]{19, 0};
			gbl_statusPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_statusPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			statusPanel.setLayout(gbl_statusPanel);
			
			statusLabel = new JLabel();
			GridBagConstraints gbc_statusLabel = new GridBagConstraints();
			gbc_statusLabel.anchor = GridBagConstraints.WEST;
			gbc_statusLabel.fill = GridBagConstraints.VERTICAL;
			gbc_statusLabel.insets = new Insets(0, 5, 0, 5);
			gbc_statusLabel.gridx = 0;
			gbc_statusLabel.gridy = 0;
			statusPanel.add(statusLabel, gbc_statusLabel);
			
			progressBar = new JProgressBar();
			progressBar.setToolTipText("133");
			GridBagConstraints gbc_progressBar = new GridBagConstraints();
			gbc_progressBar.anchor = GridBagConstraints.NORTH;
			gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
			gbc_progressBar.insets = new Insets(0, 0, 0, 5);
			gbc_progressBar.gridx = 1;
			gbc_progressBar.gridy = 0;
			statusPanel.add(progressBar, gbc_progressBar);
			
			progressLabel = new JLabel(programAttrSet.getStringField(KEY_NOMISSION));
			GridBagConstraints gbc_progressLabel = new GridBagConstraints();
			gbc_progressLabel.fill = GridBagConstraints.BOTH;
			gbc_progressLabel.insets = new Insets(0, 0, 0, 5);
			gbc_progressLabel.gridx = 2;
			gbc_progressLabel.gridy = 0;
			statusPanel.add(progressLabel, gbc_progressLabel);
			
			progressSuspendButton = new JButton();
			progressSuspendButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					monitor.suspend();
				}
			});
			progressSuspendButton.setEnabled(false);
			GridBagConstraints gbc_progressSuspendButton = new GridBagConstraints();
			gbc_progressSuspendButton.fill = GridBagConstraints.BOTH;
			gbc_progressSuspendButton.gridx = 3;
			gbc_progressSuspendButton.gridy = 0;
			statusPanel.add(progressSuspendButton, gbc_progressSuspendButton);
			
			menu = new NccMenu();
			setJMenuBar(menu);
			
		}
		
		
		
		
		private class NccMenu extends JMenuBar{
			
			private final JMenu file;
			private final JMenu edit;
			
			
			public NccMenu() {
				super();
				//定义菜单栏
				file = new JMenu(programAttrSet.getStringField(KEY_FILE) + "(F)");
				file.setMnemonic('F');
				add(file);
				edit = new JMenu(programAttrSet.getStringField(KEY_EDIT) + "(E)");
				edit.setMnemonic('E');
				add(edit);
				
				//文件菜单的具体内容
				file.add(new JMenuItemAction.Builder()
						.name(programAttrSet.getStringField(KEY_OPENFILE) + "(O)")
						.description(programAttrSet.getStringField(KEY_OPENFILE_DES))
						.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK))
						.mnemonic(KeyEvent.VK_O)
						.listener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								controlPort.openNcFile();
							}
						})
						.build()
				);
			}
			
		}
	
		private class CodeCenter1 extends JPanel{

			private final JList<Code> codeLabelList;
			private final DefaultListModel<Code> codeLabelModel;
			private final ListCellRenderer<Code> codeLabelRender;
			private final JList<Code> codeList;
			private final DefaultListModel<Code> codeModel;
			private final ListCellRenderer<Code> codeRender;
			private final JScrollPane codeScrollPane;
			
			public CodeCenter1() {
				setBackground(Color.WHITE);
				setLayout(new BorderLayout());
				
				codeLabelList = new JList<Code>();
				codeLabelModel = new DefaultListModel<Code>();
				codeLabelRender = new CodeLabelRender();
				codeLabelList.setModel(codeLabelModel);
				codeLabelList.getScrollableTracksViewportWidth();
				codeLabelList.setCellRenderer(codeLabelRender);
				
				codeScrollPane = new JScrollPane();
				codeScrollPane.setBackground(Color.WHITE);
				codeScrollPane.setBorder(null);
				add(codeScrollPane, BorderLayout.CENTER);
				
				codeList = new JList<Code>();
				codeModel = new DefaultListModel<Code>();
				codeRender = new CodeRender();
				codeList.setModel(codeModel);
				codeList.setCellRenderer(codeRender);
				codeScrollPane.setViewportView(codeList);
				codeScrollPane.setRowHeaderView(codeLabelList);
				
			}
			
		}
	}
	

	
	
	
	
	
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.mvc.AbstractViewManager#getViewControlPort()
	 */
	@Override
	public NccViewControlPort getViewControlPort() {
		return viewControlPort;
	}

}
