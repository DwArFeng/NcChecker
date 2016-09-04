package com.dwarfeng.ncc.view.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.dwarfeng.dfunc.gui.JAdjustableBorderPanel;
import com.dwarfeng.dfunc.gui.JMenuItemAction;
import com.dwarfeng.ncc.control.NccControlPort;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.conf.MainFrameAppearConfig;
import com.dwarfeng.ncc.program.key.ExceptionFieldKey;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.NccViewManager;
import com.dwarfeng.ncc.view.NccViewObject;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.KeyStroke;

import com.dwarfeng.dfunc.gui.JConsole;
import com.sun.glass.events.KeyEvent;

import javax.swing.JMenuBar;
import java.awt.Component;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JMenu;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Ncc程序主框架。
 * @author DwArFeng
 * @since 1.8
 */
public class NccFrame extends JFrame implements NccViewObject{
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final StringFieldKey KEY_TITLE = StringFieldKey.MAINFRAME_TITLE;
	private static final StringFieldKey KEY_FILE = StringFieldKey.MENU_FILE;
	private static final StringFieldKey KEY_EDIT = StringFieldKey.MENU_EDIT;
	private static final StringFieldKey KEY_OPENFILE = StringFieldKey.MENU_FILE_OPENFILE;
	private static final StringFieldKey KEY_OPENFILE_DES = StringFieldKey.MENU_FILE_OPENFILE_DES;
	private static final StringFieldKey KEY_NOMISSION = StringFieldKey.MAINFRAME_NOMISSION;
	
	private static final ExceptionFieldKey KEY_PROGNOTSTART = ExceptionFieldKey.PGES_NOTSTART;
	private static final ExceptionFieldKey KEY_PROGSTARTED = ExceptionFieldKey.PGES_STARTED;
	
	//------------------------------------------------------------------------------------------------

	private final NccViewManager viewManager;
	private final JAdjustableBorderPanel mainPanel;
	private final JAdjustableBorderPanel rightInMain;
	private final NccMenu menu;
	private final JConsole console;
	private final JLabel statusLabel;
	private final JLabel progressLabel;
	private final JButton progressSuspendButton;
	private final JProgressBar progressBar;
	
	public NccFrame(NccViewManager viewManager) {
		setSize(new Dimension(800, 600));
		
		this.viewManager = viewManager;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controlPort().exitProgram();
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle(attrSet().getStringField(KEY_TITLE));
		
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
		codeNorthPanel.setPreferredSize(new Dimension(10, 100));
		codeNorthPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		codePanel.add(codeNorthPanel, BorderLayout.NORTH);
		GridBagLayout gbl_codeNorthPanel = new GridBagLayout();
		gbl_codeNorthPanel.columnWidths = new int[]{20, 0, 0, 0, 0, 0, 0};
		gbl_codeNorthPanel.rowHeights = new int[]{32, 32, 0};
		gbl_codeNorthPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_codeNorthPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		codeNorthPanel.setLayout(gbl_codeNorthPanel);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_3.gridx = 5;
		gbc_btnNewButton_3.gridy = 0;
		codeNorthPanel.add(btnNewButton_3, gbc_btnNewButton_3);
		
		JButton button = new JButton("");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.BOTH;
		gbc_button.gridx = 0;
		gbc_button.gridy = 1;
		codeNorthPanel.add(button, gbc_button);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 1;
		codeNorthPanel.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		codeNorthPanel.add(comboBox, gbc_comboBox);
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_2.gridx = 3;
		gbc_btnNewButton_2.gridy = 1;
		codeNorthPanel.add(btnNewButton_2, gbc_btnNewButton_2);
		
		JButton button_1 = new JButton("");
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.fill = GridBagConstraints.BOTH;
		gbc_button_1.gridx = 4;
		gbc_button_1.gridy = 1;
		codeNorthPanel.add(button_1, gbc_button_1);
		
		JButton btnNewButton_4 = new JButton("");
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_4.gridx = 5;
		gbc_btnNewButton_4.gridy = 1;
		codeNorthPanel.add(btnNewButton_4, gbc_btnNewButton_4);
		
		JPanel codeSouthPanel = new JPanel();
		codePanel.add(codeSouthPanel, BorderLayout.SOUTH);
		
		JPanel codeCenterPanel = new JPanel();
		codePanel.add(codeCenterPanel, BorderLayout.CENTER);
		codeCenterPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel statusPanel = new JPanel();
		getContentPane().add(statusPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_statusPanel = new GridBagLayout();
		gbl_statusPanel.columnWidths = new int[]{55, 120, 37, 28, 0};
		gbl_statusPanel.rowHeights = new int[]{19, 0};
		gbl_statusPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_statusPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		statusPanel.setLayout(gbl_statusPanel);
		
		statusLabel = new JLabel("New label");
		GridBagConstraints gbc_statusLabel = new GridBagConstraints();
		gbc_statusLabel.anchor = GridBagConstraints.WEST;
		gbc_statusLabel.fill = GridBagConstraints.VERTICAL;
		gbc_statusLabel.insets = new Insets(0, 0, 0, 5);
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
		
		progressLabel = new JLabel(attrSet().getStringField(KEY_NOMISSION));
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
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.view.NccViewObject#getViewManager()
	 */
	@Override
	public NccViewManager getViewManager() {
		return viewManager;
	}
	
	private NccProgramAttrSet attrSet(){
		return getViewManager().getProgramAttrSet();
	}
	
	private NccControlPort controlPort(){
		return getViewManager().getControlPort();
	}
	
	/**
	 * 返回该框架的控制站。
	 * @return 该框架的控制站。
	 */
	public NccFrameControlPort getControlPort(){
		return controlPort;
	}

	private final NccFrameControlPort controlPort = new NccFrameControlPort() {
		
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
		public void applyAppearance(MainFrameAppearConfig config) {
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
		public MainFrameAppearConfig getCurrentAppearance() {
			return new MainFrameAppearConfig.Builder()
					.extendedState(getExtendedState())
					.frameWidth(getWidth())
					.frameHeight(getHeight())
					.codePanelWidth(mainPanel.getWestPreferredValue())
					.consolePanelHeight(rightInMain.getSouthPreferredValue())
					.build();
		}
	};
	
	/**
	 * 获取界面的监视器。
	 * @return 监视器。
	 */
	public ProgressMonitor getProgressMonitor(){
		return this.monitor;
	}
	
	private final ProgressMonitor monitor = new ProgressMonitor(){

		private final Lock lock = new ReentrantLock();
		
		private boolean startFlag = false;
		private boolean suspendFlag = false;
		
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
		 * @see com.dwarfeng.ncc.view.gui.ProgressMonitor#startMonitor()
		 */
		@Override
		public void startMonitor() {
			if(startFlag) throw new IllegalStateException(attrSet().getExceptionField(KEY_PROGSTARTED));
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
			if(!startFlag) throw new IllegalStateException(attrSet().getExceptionField(KEY_PROGNOTSTART));
			progressBar.setIndeterminate(aFlag);
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.gui.ProgressMonitor#setTotleProgress(int)
		 */
		@Override
		public void setTotleProgress(int val) {
			if(!startFlag) throw new IllegalStateException(attrSet().getExceptionField(KEY_PROGNOTSTART));
			progressBar.setMaximum(val);
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.gui.ProgressMonitor#setCurrentProgress(int)
		 */
		@Override
		public void setCurrentProgress(int val) {
			if(!startFlag) throw new IllegalStateException(attrSet().getExceptionField(KEY_PROGNOTSTART));
			progressBar.setValue(val);
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.gui.ProgressMonitor#isSuspend()
		 */
		@Override
		public boolean isSuspend() {
			if(!startFlag) throw new IllegalStateException(attrSet().getExceptionField(KEY_PROGNOTSTART));
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
			if(!startFlag) throw new IllegalStateException(attrSet().getExceptionField(KEY_PROGNOTSTART));
			progressBar.setValue(0);
			progressBar.setEnabled(false);
			progressSuspendButton.setEnabled(false);
			progressLabel.setText(attrSet().getStringField(KEY_NOMISSION));
			progressBar.setIndeterminate(false);
			startFlag = false;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.gui.ProgressMonitor#setMessage(java.lang.String)
		 */
		@Override
		public void setMessage(String message) {
			if(!startFlag) throw new IllegalStateException(attrSet().getExceptionField(KEY_PROGNOTSTART));
			progressLabel.setText(message);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.gui.ProgressMonitor#suspend()
		 */
		@Override
		public void suspend() {
			if(!startFlag) throw new IllegalStateException(attrSet().getExceptionField(KEY_PROGNOTSTART));
			lock.lock();
			try{
				suspendFlag = true;
			}finally{
				lock.unlock();
			}
		}
		
	};
	
	
	
	private class NccMenu extends JMenuBar implements NccViewObject{
		
		private final JMenu file;
		private final JMenu edit;
		
		
		public NccMenu() {
			super();
			//定义菜单栏
			file = new JMenu(attrSet().getStringField(KEY_FILE) + "(F)");
			file.setMnemonic('F');
			add(file);
			edit = new JMenu(attrSet().getStringField(KEY_EDIT) + "(E)");
			edit.setMnemonic('E');
			add(edit);
			
			//文件菜单的具体内容
			file.add(new JMenuItemAction.Builder()
					.name(attrSet().getStringField(KEY_OPENFILE) + "(O)")
					.description(attrSet().getStringField(KEY_OPENFILE_DES))
					.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK))
					.mnemonic(KeyEvent.VK_O)
					.listener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							controlPort().openNcFile();
						}
					})
					.build()
			);
			
			
			
			
			
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.NccViewObject#getViewManager()
		 */
		@Override
		public NccViewManager getViewManager() {
			return viewManager;
		}
		
	}

}
