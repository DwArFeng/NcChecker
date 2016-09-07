package com.dwarfeng.ncc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import com.dwarfeng.dfunc.io.CT;
import com.dwarfeng.dfunc.prog.mvc.AbstractViewManager;
import com.dwarfeng.ncc.control.NccControlPort;
import com.dwarfeng.ncc.module.front.Page;
import com.dwarfeng.ncc.module.nc.Code;
import com.dwarfeng.ncc.module.nc.CodeLabel;
import com.dwarfeng.ncc.module.nc.CodeSerial;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.conf.MfAppearConfig;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.gui.CodeLabelRenderImpl;
import com.dwarfeng.ncc.view.gui.CodeRenderImpl;
import com.dwarfeng.ncc.view.gui.FrameCp;
import com.dwarfeng.ncc.view.gui.NotifyCp;
import com.dwarfeng.ncc.view.gui.PageRenderImpl;
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
			 * @see com.dwarfeng.ncc.view.gui.NccFrameControlPort#setCodePages(int)
			 */
			@Override
			public void setCodeTotlePages(int val) {
				codePageModule.removeAllElements();
				for(int i = 1 ; i < val + 1 ; i ++){
					codePageModule.addElement(new Page(i));
				}
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.NccFrameControlPort#showCode(com.dwarfeng.ncc.module.nc.CodeSerial)
			 */
			@Override
			public void showCode(CodeSerial codeSerial, boolean flag) {
				codeCenter1.codeLabelModel.removeAllElements();
				codeCenter1.codeModel.removeAllElements();
				for(Code code:codeSerial){
					codeCenter1.codeLabelModel.addElement(code);
					codeCenter1.codeModel.addElement(code);
				}
				if(flag){
					codeCenter1.backTop();
				}else{
					codeCenter1.backEnd();
				}
				codeCenter1.fitSize();
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.NccFrameControlPort#setCodePage(com.dwarfeng.ncc.module.front.Page)
			 */
			@Override
			public void setCodePage(Page page) {
				if(codePageModule.getIndexOf(page) == -1)
					throw new NoSuchElementException(page.toString());
				codePage.setSelectedItem(page);
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
		private final DefaultComboBoxModel<Page> codePageModule;
		private final ListCellRenderer<Page> codePageRender;
		private final NccMenu menu;
		private final CodeCenter1 codeCenter1;
		private final JComboBox<Page> codePage;
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
			gbc_btnNewButton_3.insets = new Insets(0, 0, 5, 0);
			gbc_btnNewButton_3.fill = GridBagConstraints.BOTH;
			gbc_btnNewButton_3.gridx = 5;
			gbc_btnNewButton_3.gridy = 0;
			codeNorthPanel.add(btnNewButton_3, gbc_btnNewButton_3);
			
			JButton button = new JButton("");
			GridBagConstraints gbc_button = new GridBagConstraints();
			gbc_button.insets = new Insets(0, 0, 0, 5);
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
			gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
			gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
			gbc_btnNewButton_1.gridx = 1;
			gbc_btnNewButton_1.gridy = 1;
			codeNorthPanel.add(btnNewButton_1, gbc_btnNewButton_1);
			
			JButton btnNewButton_2 = new JButton("");
			btnNewButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			
			codePageModule = new DefaultComboBoxModel<Page>();
			codePageRender = new PageRenderImpl();
			
			codePage = new JComboBox<Page>();
			codePage.setModel(codePageModule);
			codePage.setRenderer(codePageRender);
			codePage.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(modiFlag || codePage.getSelectedItem() == null) return;
					controlPort.toggleCodePage((Page)codePage.getSelectedItem(), true);
				}
			});
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 0, 5);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 2;
			gbc_comboBox.gridy = 1;
			codeNorthPanel.add(codePage, gbc_comboBox);
			GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
			gbc_btnNewButton_2.insets = new Insets(0, 0, 0, 5);
			gbc_btnNewButton_2.fill = GridBagConstraints.BOTH;
			gbc_btnNewButton_2.gridx = 3;
			gbc_btnNewButton_2.gridy = 1;
			codeNorthPanel.add(btnNewButton_2, gbc_btnNewButton_2);
			
			JButton button_1 = new JButton("");
			GridBagConstraints gbc_button_1 = new GridBagConstraints();
			gbc_button_1.insets = new Insets(0, 0, 0, 5);
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
			private final JScrollPane codeLabelScrollPane;
			private final JList<Code> codeList;
			private final DefaultListModel<Code> codeModel;
			private final ListCellRenderer<Code> codeRender;
			private final JScrollPane codeScrollPane;
			private final GridBagLayout mgr;
			
			public CodeCenter1() {
				setBackground(Color.WHITE);
				
				mgr = new GridBagLayout();
				mgr.columnWidths = new int[]{0,0};
				mgr.columnWeights = new double[]{0.0,1.0};
				mgr.rowWeights = new double[]{1.0};
				setLayout(mgr);
				
				GridBagConstraints gbc1 = new GridBagConstraints();
				gbc1.fill = GridBagConstraints.BOTH;
				gbc1.gridx = 0;
				gbc1.gridy = 0;
				gbc1.insets = new Insets(0, 0, 0, 0);
				codeLabelScrollPane = new JScrollPane();
				codeLabelScrollPane.setBackground(Color.WHITE);
				codeLabelScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				codeLabelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
				codeLabelScrollPane.setBorder(null);
				add(codeLabelScrollPane, gbc1);
				
				codeLabelList = new JList<Code>();
				codeLabelModel = new DefaultListModel<Code>();
				codeLabelRender = new CodeLabelRenderImpl();
				codeLabelList.setModel(codeLabelModel);
				codeLabelList.getScrollableTracksViewportWidth();
				codeLabelList.setCellRenderer(codeLabelRender);
				codeLabelScrollPane.setViewportView(codeLabelList);
				
				GridBagConstraints gbc2 = new GridBagConstraints();
				gbc2.fill = GridBagConstraints.BOTH;
				gbc2.gridx = 1;
				gbc2.gridy = 0;
				gbc2.insets = new Insets(0, 10, 0, 0);
				codeScrollPane = new JScrollPane();
				codeScrollPane.setBackground(Color.WHITE);
				codeScrollPane.setBorder(null);
				add(codeScrollPane, gbc2);
				
				codeList = new JList<Code>();
				codeModel = new DefaultListModel<Code>();
				codeRender = new CodeRenderImpl();
				codeList.setModel(codeModel);
				codeList.setCellRenderer(codeRender);
				codeScrollPane.setViewportView(codeList);
				
				codeScrollPane.getViewport().addChangeListener(new ChangeListener() {
					
					@Override
					public void stateChanged(ChangeEvent e) {
						if(modiFlag) return;
						codeLabelScrollPane.getViewport().setViewPosition(
								new Point(0, codeScrollPane.getViewport().getViewPosition().y));
					}
				});
				
			}
			
			private void fitSize(){
				int codeLabelScrollPaneWidth = getGraphics().getFontMetrics().stringWidth(
						codeLabelModel.get(codeLabelModel.size() - 1).getLabel().getLineIndex() + "");
				codeLabelScrollPane.setPreferredSize(new Dimension(codeLabelScrollPaneWidth,0));
				mgr.columnWidths = new int[]{codeLabelScrollPaneWidth , 0};
				revalidate();
			}
			
			private void backTop(){
				codeLabelScrollPane.getViewport().setViewPosition(new Point());
				codeScrollPane.getViewport().setViewPosition(new Point());
			}
			
			private void backEnd(){
				int end;
				int viewHeight = codeScrollPane.getViewport().getViewRect().height;
				end = codeScrollPane.getViewport().getView().getPreferredSize().height - viewHeight;
				codeScrollPane.getViewport().setViewPosition(new Point(0, end));
				codeLabelScrollPane.getViewport().setViewPosition(new Point(0, end));
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
