package com.dwarfeng.ncc.view.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.dwarfeng.dfunc.gui.JAdjustableBorderPanel;
import com.dwarfeng.ncc.view.NccViewManager;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import com.dwarfeng.dfunc.gui.JConsole;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import java.awt.Component;

/**
 * Ncc³ÌÐòÖ÷¿ò¼Ü¡£
 * @author DwArFeng
 * @since 1.8
 */
public class NccFrame extends JFrame{

	private final NccViewManager viewManager;
	
	public NccFrame(NccViewManager viewManager) {
		
		this.viewManager = viewManager;
		
		JAdjustableBorderPanel mainPanel = new JAdjustableBorderPanel();
		mainPanel.setSeperatorThickness(5);
		mainPanel.setWestEnabled(true);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		JAdjustableBorderPanel rightInMain = new JAdjustableBorderPanel();
		rightInMain.setBorder(null);
		rightInMain.setSeperatorThickness(5);
		rightInMain.setSouthEnabled(true);
		mainPanel.add(rightInMain, BorderLayout.CENTER);
		
		JConsole console = new JConsole();
		console.setInputFieldVisible(false);
		console.setInputFieldEnabled(false);
		rightInMain.add(console, BorderLayout.SOUTH);
		
		JPanel codePanel = new JPanel();
		mainPanel.add(codePanel, BorderLayout.WEST);
		codePanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		codePanel.add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		textArea.setBorder(null);
		scrollPane.setViewportView(textArea);
		
		JPanel panel = new JPanel();
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		codePanel.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel statusPanel = new JPanel();
		getContentPane().add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("New label");
		statusPanel.add(lblNewLabel, BorderLayout.WEST);
		
		JProgressBar progressBar = new JProgressBar();
		statusPanel.add(progressBar, BorderLayout.CENTER);
		
		JButton btnNewButton = new JButton("");
		statusPanel.add(btnNewButton, BorderLayout.EAST);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
	}

}
