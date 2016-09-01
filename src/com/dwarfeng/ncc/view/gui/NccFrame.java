package com.dwarfeng.ncc.view.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.dwarfeng.dfunc.gui.JAdjustableBorderPanel;
import com.dwarfeng.ncc.control.NccControlPort;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.conf.MainFrameAppearConfig;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.NccViewManager;
import com.dwarfeng.ncc.view.NccViewObject;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JButton;

import com.dwarfeng.dfunc.gui.JConsole;
import com.dwarfeng.dfunc.io.CT;

import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.JMenu;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JTree;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 * Ncc程序主框架。
 * @author DwArFeng
 * @since 1.8
 */
public class NccFrame extends JFrame implements NccViewObject{
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final StringFieldKey KEY_TITLE = StringFieldKey.MAINFRAME_TITLE;
	
	//------------------------------------------------------------------------------------------------

	private final NccViewManager viewManager;
	private JAdjustableBorderPanel mainPanel;
	private JAdjustableBorderPanel rightInMain;
	
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
		codeNorthPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		codePanel.add(codeNorthPanel, BorderLayout.NORTH);
		GridBagLayout gbl_codeNorthPanel = new GridBagLayout();
		gbl_codeNorthPanel.columnWidths = new int[]{20, 0, 0, 0, 0, 0};
		gbl_codeNorthPanel.rowHeights = new int[]{0, 25, 0};
		gbl_codeNorthPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_codeNorthPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		codeNorthPanel.setLayout(gbl_codeNorthPanel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridwidth = 5;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		codeNorthPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
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
		
		JComboBox comboBox = new JComboBox();
		comboBox.setEditable(true);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		codeNorthPanel.add(comboBox, gbc_comboBox);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 0, 5);
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
		
		JPanel codeSouthPanel = new JPanel();
		codePanel.add(codeSouthPanel, BorderLayout.SOUTH);
		
		JPanel codeCenterPanel = new JPanel();
		codePanel.add(codeCenterPanel, BorderLayout.CENTER);
		codeCenterPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		codeCenterPanel.add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_1.setPreferredSize(new Dimension(100, 2));
		codeCenterPanel.add(scrollPane_1, BorderLayout.WEST);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
		JPanel statusPanel = new JPanel();
		getContentPane().add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel("New label");
		statusPanel.add(lblNewLabel, BorderLayout.WEST);
		
		JProgressBar progressBar = new JProgressBar();
		statusPanel.add(progressBar, BorderLayout.CENTER);
		
		JButton btnNewButton = new JButton("");
		statusPanel.add(btnNewButton, BorderLayout.EAST);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
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
	private JConsole console;
	private JLabel lblNewLabel;
	

}
