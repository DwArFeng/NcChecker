package com.dwarfeng.ncc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.border.BevelBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.undo.UndoManager;

import com.dwarfeng.dfunc.gui.swing.JAdjustableBorderPanel;
import com.dwarfeng.dfunc.gui.swing.JConsole;
import com.dwarfeng.dfunc.gui.swing.JMenuItemAction;
import com.dwarfeng.dfunc.gui.swing.MuaListModel;
import com.dwarfeng.dfunc.io.CT;
import com.dwarfeng.dfunc.prog.mvc.AbstractViewManager;
import com.dwarfeng.dfunc.threads.InnerThread;
import com.dwarfeng.ncc.control.NccControlPort;
import com.dwarfeng.ncc.control.cps.CodeCp.CodeEditMode;
import com.dwarfeng.ncc.model.nc.Code;
import com.dwarfeng.ncc.model.nc.CodeSerial;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.conf.MfAppearConfig;
import com.dwarfeng.ncc.program.key.ImageKey;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.gui.CodeLabelRender;
import com.dwarfeng.ncc.view.gui.CodeRender;
import com.dwarfeng.ncc.view.gui.FrameCp;
import com.dwarfeng.ncc.view.gui.MutiStatus;
import com.dwarfeng.ncc.view.gui.NotifyCp;
import com.dwarfeng.ncc.view.gui.ProgressModel;
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
		
	};
	
	private final NotifyCp notifyControlPort = new NotifyCp() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.gui.NotifyControlPort#askFile(javax.swing.filechooser.FileFilter[], boolean)
		 */
		@Override
		public File askOpenFile(FileFilter[] fileFilters, boolean allFileAllowed) {
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

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.gui.NotifyCp#askSaveFile()
		 */
		@Override
		public File askSaveFile() {
			JFileChooser fc = new JFileChooser();
			fc.resetChoosableFileFilters();
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.setAcceptAllFileFilterUsed(true);
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
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.view.gui.NotifyCp#showConfirm(java.lang.Object, java.lang.String, com.dwarfeng.ncc.view.gui.NotifyCp.OptionType, com.dwarfeng.ncc.view.gui.NotifyCp.MessageType, javax.swing.Icon)
		 */
		@Override
		public AnswerType showConfirm(Object message, String title,
				OptionType optionType, MessageType messageType, Icon icon) {
			int i = JOptionPane.showConfirmDialog(
					mainFrame, message, title, optionType.getVal(), messageType.getVal(), icon);
			
			switch (i) {
				case JOptionPane.YES_OPTION:
					return AnswerType.YES;
				case JOptionPane.NO_OPTION:
					return AnswerType.NO;
				default:
					return AnswerType.CANCEL;
				}
		}

	};
	
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final String DIALOG_SUFF = "....";
	private static final StringFieldKey KEY_TITLE = StringFieldKey.MAINFRAME_TITLE;
	
	private static final StringFieldKey KEY_FILE = StringFieldKey.MENU_FILE;
	private static final StringFieldKey KEY_EDIT = StringFieldKey.MENU_EDIT;
	
	private static final StringFieldKey KEY_OPENFILE = StringFieldKey.MENU_FILE_OPENFILE;
	private static final StringFieldKey KEY_OPENFILE_DES = StringFieldKey.MENU_FILE_OPENFILE_DES;
	private static final StringFieldKey KEY_NEW = StringFieldKey.MENU_FILE_NEW;
	private static final StringFieldKey KEY_NEW_DES = StringFieldKey.MENU_FILE_NEW_DES;
	private static final StringFieldKey KEY_CLOSE = StringFieldKey.MENU_FILE_CLOSE;
	private static final StringFieldKey KEY_CLOSE_DES = StringFieldKey.MENU_FILE_CLOSE_DES;
	private static final StringFieldKey KEY_SAVE = StringFieldKey.MENU_FILE_SAVE;
	private static final StringFieldKey KEY_SAVE_DES = StringFieldKey.MENU_FILE_SAVE_DES;
	private static final StringFieldKey KEY_SAVEA = StringFieldKey.MENU_FILE_SAVEA;
	private static final StringFieldKey KEY_SAVEA_DES = StringFieldKey.MENU_FILE_SAVEA_DES;
	
	private static final StringFieldKey KEY_COMMIT = StringFieldKey.CODE_COMMIT;
	private static final StringFieldKey KEY_COMMIT_DES = StringFieldKey.CODE_COMMIT_DES;
	private static final StringFieldKey KEY_COMMITNQ = StringFieldKey.CODE_COMMITNQ;
	private static final StringFieldKey CODE_COMMIT_DES = StringFieldKey.CODE_COMMITNQ_DES;
	private static final StringFieldKey KEY_DISCARD = StringFieldKey.CODE_DISCARD;
	private static final StringFieldKey KEY_DISCARD_DES = StringFieldKey.CODE_DISCARD_DES;
	private static final StringFieldKey KEY_DISCARDNQ = StringFieldKey.CODE_DISCARDNQ;
	private static final StringFieldKey KEY_DISCARDNQ_DES = StringFieldKey.CODE_DISCARDNQ_DES;
	
	private static final StringFieldKey KEY_NOMISSION = StringFieldKey.PROGRESS_NOMISSION;
	
	private static final ImageKey IMG_MNEW = ImageKey.M_NEW;
	private static final ImageKey IMG_MOPEN = ImageKey.M_OPEN;
	private static final ImageKey IMG_MCLOSE = ImageKey.M_CLOSE;
	private static final ImageKey IMG_MSAVE= ImageKey.M_SAVE;
	private static final ImageKey IMG_MSAVEA = ImageKey.M_SAVEA;
	private static final ImageKey IMG_PSUSPEND = ImageKey.P_SUSPEND;
	private static final ImageKey IMG_CINSPECT = ImageKey.C_INSPECT;
	private static final ImageKey IMG_CEDIT = ImageKey.C_EDIT;
	
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
			 * @see com.dwarfeng.ncc.view.gui.NccFrameControlPort#showCode(com.dwarfeng.ncc.model.nc.CodeSerial)
			 */
			@Override
			public void showCode(CodeSerial codeSerial) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						codeCenter1.codeLabelModel.clear();
						codeCenter1.codeModel.clear();
						if(Objects.nonNull(codeSerial)){
							codeCenter1.codeLabelModel.addAll(Arrays.asList(codeSerial.toArray()));
							codeCenter1.codeModel.addAll(Arrays.asList(codeSerial.toArray()));
						}
					}
				});
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.NccFrameControlPort#lockEdit()
			 */
			@Override
			public void lockEdit() {
				menu.lockEdit();
				codeCenter1.lockEdit();
				codeCenter2.lockEdit();
				codeToolBar.lockEdit();
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.NccFrameControlPort#unlockEdit()
			 */
			@Override
			public void unlockEdit() {
				menu.unlockEdit();
				codeCenter1.unlockEdit();
				codeCenter2.unlockEdit();
				codeToolBar.unlockEdit();
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.FrameCp#noneFileMode(boolean)
			 */
			@Override
			public void noneFileMode(boolean aFlag) {
				menu.noneFile(aFlag);
				codeCenter1.noneFile(aFlag);
				codeCenter2.noneFile(aFlag);
				codeToolBar.noneFile(aFlag);
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.FrameCp#traceInConsole(java.lang.String, java.lang.String[])
			 */
			@Override
			public void traceInConsole(String format, Object... args) {
				final Formatter formatter = new Formatter();
				try{
					console.getOut().println(CT.toString(formatter.format(format, args)));
				}finally{
					formatter.close();
				}
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.FrameCp#startProgressMonitor(com.dwarfeng.ncc.view.gui.ProgressModel)
			 */
			@Override
			public void startProgressMonitor(ProgressModel model) {
				Objects.requireNonNull(model);
				progressPanel.setProgressModel(model);
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.FrameCp#toggleMode(com.dwarfeng.ncc.control.NccControlPort.CodePanelMode)
			 */
			@Override
			public void knockForMode(CodeEditMode mode) {
				codeToolBar.setMode(mode);
				switch(mode){
					case EDIT:
						if(Objects.nonNull(codeCenter1.getParent()) && codeCenter1.getParent().equals(codePanel)){
							codePanel.remove(codeCenter1);
							codePanel.add(codeCenter2, BorderLayout.CENTER);
							codePanel.repaint();
						}
						break;
					case INSPECT:
						if(Objects.nonNull(codeCenter2.getParent()) && codeCenter2.getParent().equals(codePanel)){
							codePanel.remove(codeCenter2);
							codePanel.add(codeCenter1, BorderLayout.CENTER);
							codePanel.repaint();
						}
						break;
				}
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.FrameCp#setEditText(java.lang.String)
			 */
			@Override
			public void setEditText(String text) {
				codeCenter2.setText(text == null ? "" : text);
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.FrameCp#getEditFlag()
			 */
			@Override
			public boolean needCommit() {
				return codeCenter2.getEidtFlag();
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.FrameCp#knockForCommit()
			 */
			@Override
			public void knockForCommit() {
				codeCenter2.clearEditFlag();
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.FrameCp#getEditLine()
			 */
			@Override
			public int getEditLine() {
				return codeCenter2.getLineCount();
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.gui.FrameCp#getEditText()
			 */
			@Override
			public String getEditText() {
				return codeCenter2.getText();
			}
		};
		
		
		
		
		
		

		private final JConsole console;
		private final JLabel statusLabel;
		private final JAdjustableBorderPanel mainPanel;
		private final JAdjustableBorderPanel rightInMain;
		private final ProgressPanel progressPanel;
		private final NccMenu menu;
		private final CodeCenter1 codeCenter1;
		private final CodeCenter2 codeCenter2;
		private final CodeToolBar codeToolBar;
		private final JPanel codePanel;
		
		public NccFrame(){
			this.codeCenter1 = new CodeCenter1();
			this.codeCenter2 = new CodeCenter2();
			this.codeToolBar = new CodeToolBar();
			
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
			
			codePanel = new JPanel();
			mainPanel.add(codePanel, BorderLayout.WEST);
			codePanel.setLayout(new BorderLayout(0, 0));
			codePanel.add(codeCenter1, BorderLayout.CENTER);
			codePanel.add(codeToolBar,BorderLayout.WEST);

			JPanel statusPanel = new JPanel();
			getContentPane().add(statusPanel, BorderLayout.SOUTH);
			GridBagLayout gbl_statusPanel = new GridBagLayout();
			gbl_statusPanel.columnWidths = new int[]{60, 440, 0};
			gbl_statusPanel.rowHeights = new int[]{20, 0};
			gbl_statusPanel.columnWeights = new double[]{0.0, 0.0, 1.0};
			gbl_statusPanel.rowWeights = new double[]{0.0};
			statusPanel.setLayout(gbl_statusPanel);
			
			statusLabel = new JLabel();
			GridBagConstraints gbc_statusLabel = new GridBagConstraints();
			gbc_statusLabel.anchor = GridBagConstraints.WEST;
			gbc_statusLabel.fill = GridBagConstraints.VERTICAL;
			gbc_statusLabel.insets = new Insets(0, 5, 0, 5);
			gbc_statusLabel.gridx = 0;
			gbc_statusLabel.gridy = 0;
			statusPanel.add(statusLabel, gbc_statusLabel);
			
			progressPanel = new ProgressPanel();
			GridBagConstraints gbc_progressPanel = new GridBagConstraints();
			gbc_progressPanel.anchor = GridBagConstraints.NORTH;
			gbc_progressPanel.fill = GridBagConstraints.BOTH;
			gbc_progressPanel.insets = new Insets(0, 0, 0, 5);
			gbc_progressPanel.gridx = 1;
			gbc_progressPanel.gridy = 0;
			statusPanel.add(progressPanel, gbc_progressPanel);
			
			menu = new NccMenu();
			setJMenuBar(menu);
			
		}
		
		
		
		
		private class NccMenu extends JMenuBar implements MutiStatus{
			
			private final JMenu file;
			private final JMenuItem file_new;
			private final JMenuItem file_open;
			private final JMenuItem file_close;
			private final JMenuItem file_save;
			private final JMenuItem file_savea;
			
			private final JMenu edit;
			
			private boolean lockEditMask;
			private boolean noneFileMask;
			
			
			public NccMenu() {
				super();
				//定义菜单栏
				file = new JMenu(programAttrSet.getStringField(KEY_FILE));
				file.setMnemonic('F');
				add(file);
				edit = new JMenu(programAttrSet.getStringField(KEY_EDIT));
				edit.setMnemonic('E');
				add(edit);
				
				//文件菜单的具体内容
				file_new = file.add(new JMenuItemAction.Builder()
						.name(programAttrSet.getStringField(KEY_NEW))
						.description(programAttrSet.getStringField(KEY_NEW_DES))
						.icon(new ImageIcon(programAttrSet.getImage(IMG_MNEW)))
						.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK))
						.mnemonic(KeyEvent.VK_N)
						.listener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								controlPort.fileCp().newFrontFile();
							}
						})
						.build()
				);
				
				file_open = file.add(new JMenuItemAction.Builder()
						.name(programAttrSet.getStringField(KEY_OPENFILE) + DIALOG_SUFF)
						.description(programAttrSet.getStringField(KEY_OPENFILE_DES))
						.icon(new ImageIcon(programAttrSet.getImage(IMG_MOPEN)))
						.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK))
						.mnemonic(KeyEvent.VK_O)
						.listener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								controlPort.fileCp().openNcFile();
							}
						})
						.build()
				);
				
				file.addSeparator();
				
				file_close = file.add(new JMenuItemAction.Builder()
						.name(programAttrSet.getStringField(KEY_CLOSE))
						.description(programAttrSet.getStringField(KEY_CLOSE_DES))
						.icon(new ImageIcon(programAttrSet.getImage(IMG_MCLOSE)))
						.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK))
						.mnemonic(KeyEvent.VK_W)
						.listener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								controlPort.fileCp().closeFrontFile();
							}
						})
						.build()
				);
				
				file.addSeparator();
				
				file_save = file.add(new JMenuItemAction.Builder()
						.name(programAttrSet.getStringField(KEY_SAVE))
						.description(programAttrSet.getStringField(KEY_SAVE_DES))
						.icon(new ImageIcon(programAttrSet.getImage(IMG_MSAVE)))
						.keyStorke(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK))
						.mnemonic(KeyEvent.VK_S)
						.listener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								controlPort.fileCp().saveFrontFile();
							}
						})
						.build()
				);
				
				file_savea = file.add(new JMenuItemAction.Builder()
						.name(programAttrSet.getStringField(KEY_SAVEA))
						.description(programAttrSet.getStringField(KEY_SAVEA_DES))
						.icon(new ImageIcon(programAttrSet.getImage(IMG_MSAVEA)))
						.mnemonic(KeyEvent.VK_A)
						.listener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								
							}
						})
						.build()
				);
			}
			
			/* (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.MutiStatus#lockEdit()
			 */
			@Override
			public void lockEdit(){
				lockEditMask = true;
				refresh();
			}
			

			/* (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.MutiStatus#unlockEdit()
			 */
			@Override
			public void unlockEdit(){
				lockEditMask = false;
				refresh();
			}
			
			/* (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.MutiStatus#noneFile(boolean)
			 */
			@Override
			public void noneFile(boolean aFlag){
				noneFileMask = aFlag;
				refresh();
			}
			
			private void refresh() {
				file_new.setEnabled(!lockEditMask);
				file_open.setEnabled(!lockEditMask);
				file_close.setEnabled(!(lockEditMask || noneFileMask));
				file_save.setEnabled(!(lockEditMask || noneFileMask));
				file_savea.setEnabled(!(lockEditMask || noneFileMask));
			}
			
		}
	
		private class CodeCenter1 extends JPanel implements MutiStatus{

			private final JList<Code> codeLabelList;
			private final MuaListModel<Code> codeLabelModel;
			private final ListCellRenderer<Code> codeLabelRender;
			private final JList<Code> codeList;
			private final MuaListModel<Code> codeModel;
			private final ListCellRenderer<Code> codeRender;
			private final JScrollPane codeScrollPane;
			
			private boolean lockEditMask;
			private boolean noneFileMask;
			
			public CodeCenter1() {
				setLayout(new BorderLayout());
				
				codeLabelList = new JList<Code>();
				codeLabelModel = new MuaListModel<Code>();
				codeLabelRender = new CodeLabelRender();
				codeLabelList.setModel(codeLabelModel);
				codeLabelList.getScrollableTracksViewportWidth();
				codeLabelList.setCellRenderer(codeLabelRender);
				
				codeScrollPane = new JScrollPane();
				codeScrollPane.setBackground(Color.WHITE);
				codeScrollPane.setBorder(null);
				add(codeScrollPane, BorderLayout.CENTER);
				
				codeList = new JList<Code>();
				codeModel = new MuaListModel<Code>();
				codeRender = new CodeRender();
				codeList.setModel(codeModel);
				codeList.setCellRenderer(codeRender);
				codeScrollPane.setViewportView(codeList);
				codeScrollPane.setRowHeaderView(codeLabelList);
				
			}

			/* (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.MutiStatus#lockEdit()
			 */
			@Override
			public void lockEdit(){
				lockEditMask = true;
				refresh();
			}
			

			/* (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.MutiStatus#unlockEdit()
			 */
			@Override
			public void unlockEdit(){
				lockEditMask = false;
				refresh();
			}
			
			/* (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.MutiStatus#noneFile(boolean)
			 */
			@Override
			public void noneFile(boolean aFlag){
				noneFileMask = aFlag;
				refresh();
			}
			
			private void refresh() {
				codeLabelList.setEnabled(!(lockEditMask || noneFileMask));
				codeList.setEnabled(!(lockEditMask || noneFileMask));
			}
			
		}
	
		private class CodeCenter2 extends JPanel implements MutiStatus{
			
			private final JTextArea textArea;
			private final JButton commit;
			private final JButton commitNq;
			private final JButton discard;
			private final JButton discardNq;
			
			private final UndoManager undoManager;
			
			private boolean noneFileMask;
			private boolean lockEditMask;
			
			private boolean changeFlag;
			private boolean editFlag;
			
			public CodeCenter2() {
				this.undoManager = new UndoManager();
				this.changeFlag = false;
				this.editFlag = false;
				setLayout(new BorderLayout());
				
				JScrollPane scrollPane = new JScrollPane();
				add(scrollPane,BorderLayout.CENTER);
				
				textArea = new JTextArea();
				textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
				textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
					@Override
					public void undoableEditHappened(UndoableEditEvent e) {
						if(changeFlag) return;
						undoManager.addEdit(e.getEdit());
						editFlag = true;
					}
				});
				
				scrollPane.setViewportView(textArea);
				
				JPanel southPanel = new JPanel();
				GridBagLayout gbl_southPanel = new GridBagLayout();
				gbl_southPanel.columnWidths = new int[]{0,0,0,0,0};
				gbl_southPanel.rowHeights = new int[]{0};
				gbl_southPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0};
				gbl_southPanel.rowWeights = new double[]{0.0};
				southPanel.setLayout(gbl_southPanel);
				add(southPanel, BorderLayout.SOUTH);
				
				discardNq = new JButton();
				discardNq.setText(programAttrSet.getStringField(KEY_DISCARDNQ));
				discardNq.setToolTipText(programAttrSet.getStringField(KEY_DISCARDNQ_DES));
				GridBagConstraints gbc_discardNq = new GridBagConstraints();
				gbc_discardNq.anchor = GridBagConstraints.WEST;
				gbc_discardNq.fill = GridBagConstraints.BOTH;
				gbc_discardNq.insets = new Insets(0, 0, 0, 5);
				gbc_discardNq.gridx = 0;
				gbc_discardNq.gridy = 0;
				southPanel.add(discardNq, gbc_discardNq);
				
				discard = new JButton();
				discard.setText(programAttrSet.getStringField(KEY_DISCARD));
				discard.setToolTipText(programAttrSet.getStringField(KEY_DISCARD_DES));
				GridBagConstraints gbc_discard = new GridBagConstraints();
				gbc_discard.anchor = GridBagConstraints.WEST;
				gbc_discard.fill = GridBagConstraints.BOTH;
				gbc_discard.insets = new Insets(0, 0, 0, 5);
				gbc_discard.gridx = 1;
				gbc_discard.gridy = 0;
				southPanel.add(discard, gbc_discard);
				
				commit = new JButton();
				commit.setText(programAttrSet.getStringField(KEY_COMMIT));
				commit.setToolTipText(programAttrSet.getStringField(KEY_COMMIT_DES));
				GridBagConstraints gbc_commit = new GridBagConstraints();
				gbc_commit.anchor = GridBagConstraints.WEST;
				gbc_commit.fill = GridBagConstraints.BOTH;
				gbc_commit.insets = new Insets(0, 5, 0, 0);
				gbc_commit.gridx = 3;
				gbc_commit.gridy = 0;
				southPanel.add(commit, gbc_commit);
				
				commitNq = new JButton();
				commitNq.setText(programAttrSet.getStringField(KEY_COMMITNQ));
				commitNq.setToolTipText(programAttrSet.getStringField(KEY_COMMIT_DES));
				GridBagConstraints gbc_commitNq = new GridBagConstraints();
				gbc_commitNq.anchor = GridBagConstraints.WEST;
				gbc_commitNq.fill = GridBagConstraints.BOTH;
				gbc_commitNq.insets = new Insets(0, 5, 0, 0);
				gbc_commitNq.gridx = 4;
				gbc_commitNq.gridy = 0;
				southPanel.add(commitNq, gbc_commitNq);
				
			}

			public String getText() {
				return textArea.getText();
			}

			public int getLineCount() {
				return textArea.getLineCount();
			}

			/* (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.MutiStatus#lockEdit()
			 */
			@Override
			public void lockEdit(){
				lockEditMask = true;
				refresh();
			}
			

			/* (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.MutiStatus#unlockEdit()
			 */
			@Override
			public void unlockEdit(){
				lockEditMask = false;
				refresh();
			}
			
			/* (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.MutiStatus#noneFile(boolean)
			 */
			@Override
			public void noneFile(boolean aFlag){
				noneFileMask = aFlag;
				refresh();
			}
			
			public void clearEditFlag(){
				this.editFlag = false;
			}
			
			public void setText(String text){
				changeFlag = true;
				try{
					textArea.setText(text);
					undoManager.discardAllEdits();
					editFlag = false;
					revalidate();
				}finally{
					changeFlag = false;
				}
			}
			
			public boolean getEidtFlag(){
				return this.editFlag;
			}
			
			private void refresh() {
				textArea.setEditable(!(lockEditMask || noneFileMask));
			}
			
		}
		
		private class CodeToolBar extends JToolBar implements MutiStatus{
			
			private final JToggleButton codeFunction;
			private final JToggleButton codeEdit;
			private final ButtonGroup buttonGroup;
			
			private boolean lockEditMask = false;
			private boolean noneFileMask = false;
			
			private boolean modiFlag;
			
			private ButtonModel currentButtonModel;
			
			public CodeToolBar() {
				modiFlag = false;
				
				setFloatable(false);
				setBorder(new BevelBorder(BevelBorder.LOWERED));
				setOrientation(JToolBar.VERTICAL);
				
				final ActionListener actionListener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(modiFlag) return;
						checkButtonGroup();
					}
				};
				
				buttonGroup = new ButtonGroup();
				
				codeFunction = new JToggleButton();
				codeFunction.setIcon(new ImageIcon(programAttrSet.getImage(IMG_CINSPECT)));
				codeFunction.setPreferredSize(new Dimension(25,25));
				codeFunction.addActionListener(actionListener);
				add(codeFunction);
				
				codeEdit = new JToggleButton();
				codeEdit.setIcon(new ImageIcon(programAttrSet.getImage(IMG_CEDIT)));
				codeEdit.setSelected(false);
				codeEdit.setPreferredSize(new Dimension(25,25));
				codeEdit.addActionListener(actionListener);
				add(codeEdit);
				
				buttonGroup.add(codeFunction);
				buttonGroup.add(codeEdit);
				buttonGroup.setSelected(codeFunction.getModel(), true);
				currentButtonModel = codeFunction.getModel();
			}
			
			/* (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.MutiStatus#lockEdit()
			 */
			@Override
			public void lockEdit(){
				lockEditMask = true;
				refresh();
			}
			
			/* (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.MutiStatus#unlockEdit()
			 */
			@Override
			public void unlockEdit(){
				lockEditMask = false;
				refresh();
			}
			
			/* (non-Javadoc)
			 * @see com.dwarfeng.ncc.view.MutiStatus#noneFile(boolean)
			 */
			@Override
			public void noneFile(boolean aFlag){
				noneFileMask = aFlag;
				refresh();
			}
			
			public void setMode(CodeEditMode mode){
				modiFlag = true;
				try{
					buttonGroup.clearSelection();
					switch (mode) {
						case EDIT:
							buttonGroup.setSelected(codeEdit.getModel(), true);
							currentButtonModel = codeEdit.getModel();
							break;
						case INSPECT:
							buttonGroup.setSelected(codeFunction.getModel(), true);
							currentButtonModel = codeFunction.getModel();
							break;
						default:
							break;
					}
				}finally{
					modiFlag = false;
				}
			}
			
			private void refresh(){
				codeEdit.setEnabled(!(lockEditMask || noneFileMask));
				codeFunction.setEnabled(!(lockEditMask || noneFileMask));
			}
			
			private void checkButtonGroup(){
				if(buttonGroup.getSelection() == currentButtonModel) return;
				currentButtonModel = buttonGroup.getSelection();
				if(codeEdit.isSelected()) controlPort.codeCp().attemptToggleMode(CodeEditMode.EDIT);
				if(codeFunction.isSelected()) controlPort.codeCp().attemptToggleMode(CodeEditMode.INSPECT);
			}
			
		}
	
		private final class ProgressPanel extends JPanel{
			
			private static final long serialVersionUID = 222490662241685578L;

			private final class Monitor extends InnerThread{

				private final Lock lock;
				private final Condition condition;
				private ProgressModel model;
				
				public Monitor() {
					super("Progress Monitor", true);
					lock = new ReentrantLock();
					condition = lock.newCondition();
				}

				/*
				 * (non-Javadoc)
				 * @see com.dwarfeng.dfunc.threads.InnerThread#threadRunMethod()
				 */
				@Override
				protected void threadRunMethod() {
					lock.lock();
					try{
						while(Objects.isNull(model)){
							try {
								condition.await();
							} catch (InterruptedException e) {
								//DO NOTHING
							}
						}
						if(model.isEnd() || model.isSuspend()){
							enableFlag = false;
							resetPanel();
							model = null;
							refresh();
						}else{
							try{
								//获取关键值
								int max = model.getMaximum();
								int val = model.getValue();
								String text = model.getLabelText();
								
								//判断异常情况
								if(max < val || max < 1 || val < 0) throw new ArithmeticException();
								Objects.requireNonNull(text);
								
								//设置诸属性
								progressBar.setIndeterminate(model.isIndeterminate());
								progressBar.setMaximum(max);
								progressBar.setValue(val);
								statusLabel.setText(text);
								
							}catch(Exception e){
								e.printStackTrace();
								model.suspend();
								resetPanel();
							}
						}
						
					}finally{
						lock.unlock();
					}
				}

				/*
				 * (non-Javadoc)
				 * @see com.dwarfeng.dfunc.threads.InnerThread#threadStopMethod()
				 */
				@Override
				protected void threadStopMethod() {}
				/*
				 * (non-Javadoc)
				 * @see com.dwarfeng.dfunc.threads.InnerThread#threadStartMethod()
				 */
				@Override
				protected void threadStartMethod() {}
				
				private void setProgressModel(ProgressModel model){
					lock.lock();
					try{
						this.model = model;
						condition.signalAll();
					}finally{
						lock.unlock();
					}
				}
				
			}
			
			private final Monitor monitor;
			
			private final JButton suspendButton;
			private final JProgressBar progressBar;
			private final JLabel statusLabel;
			
			private boolean enableFlag;
			
			public ProgressPanel() {
				this.monitor = new Monitor();
				this.monitor.runThread();
				this.enableFlag = false;
				
				setLayout(new BorderLayout());
				
				suspendButton = new JButton();
				suspendButton.setIcon(new ImageIcon(programAttrSet.getImage(IMG_PSUSPEND)));
				suspendButton.setPreferredSize(new Dimension(20, 20));
				suspendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(Objects.nonNull(monitor.model))
							monitor.model.suspend();
					}
				});
				add(suspendButton, BorderLayout.WEST);
				
				progressBar = new JProgressBar();
				progressBar.setValue(0);
				progressBar.setMaximum(1);
				progressBar.setIndeterminate(false);
				
				add(progressBar, BorderLayout.CENTER);
				
				statusLabel = new JLabel();
				statusLabel.setText(programAttrSet.getStringField(KEY_NOMISSION));
				statusLabel.setPreferredSize(new Dimension(200, 0));
				add(statusLabel, BorderLayout.EAST);
				
				refresh();
			}
			
			public void setProgressModel(ProgressModel model){
				enableFlag = true;
				monitor.setProgressModel(model);
				refresh();
			}
			
			private void resetPanel(){
				progressBar.setIndeterminate(false);
				progressBar.setValue(0);
				progressBar.setMaximum(1);
				statusLabel.setText(programAttrSet.getStringField(KEY_NOMISSION));
				enableFlag = false;
			}
			
			private void refresh(){
				progressBar.setEnabled(enableFlag);
				suspendButton.setEnabled(enableFlag);
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
