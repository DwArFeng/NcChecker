package com.dwarfeng.ncc.view.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.dwarfeng.ncc.module.nc.CodeLabel;

/**
 * 代码标签的渲染类。
 * @author DwArFeng
 * @since 1.8
 */
final class CodeLabelRenderImpl extends JPanel implements ListCellRenderer<CodeLabel> {
	
	private static final long serialVersionUID = 4403074299909068326L;
	
	private final JLabel lineNumber;
	
	public CodeLabelRenderImpl() {
		setLayout(new BorderLayout());
		lineNumber = new JLabel();
		lineNumber.setHorizontalAlignment(JLabel.RIGHT);
		add(lineNumber,BorderLayout.CENTER);
	}
	
	/**
	* Overridden for performance reasons.
	* See the <a href="#override">Implementation Note</a>
	* for more information.
	*/
    @Override
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        // Strings get interned...
        if (propertyName == "text"
                || ((propertyName == "font" || propertyName == "foreground")
                    && oldValue != newValue
                    && getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey) != null)) {

            super.firePropertyChange(propertyName, oldValue, newValue);
        }
    }
    
	/**
	* Overridden for performance reasons.
	* See the <a href="#override">Implementation Note</a>
	* for more information.
	*/
	@Override
	public void firePropertyChange(String propertyName, byte oldValue, byte newValue) {}

	/**
	* Overridden for performance reasons.
	* See the <a href="#override">Implementation Note</a>
	* for more information.
	*/
	@Override
	public void firePropertyChange(String propertyName, char oldValue, char newValue) {}
	
	/**
	* Overridden for performance reasons.
	* See the <a href="#override">Implementation Note</a>
	* for more information.
	*/
	@Override
	public void firePropertyChange(String propertyName, short oldValue, short newValue) {}

	/**
	* Overridden for performance reasons.
	* See the <a href="#override">Implementation Note</a>
	* for more information.
	*/
	@Override
	public void firePropertyChange(String propertyName, int oldValue, int newValue) {}

	/**
	* Overridden for performance reasons.
	* See the <a href="#override">Implementation Note</a>
	* for more information.
	*/
	@Override
	public void firePropertyChange(String propertyName, long oldValue, long newValue) {}

	/**
	* Overridden for performance reasons.
	* See the <a href="#override">Implementation Note</a>
	* for more information.
	*/
	@Override
	public void firePropertyChange(String propertyName, float oldValue, float newValue) {}

	/**
	* Overridden for performance reasons.
	* See the <a href="#override">Implementation Note</a>
	* for more information.
	*/
	@Override
	public void firePropertyChange(String propertyName, double oldValue, double newValue) {}

	/**
	* Overridden for performance reasons.
	* See the <a href="#override">Implementation Note</a>
	* for more information.
	*/
	@Override
	public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(
			JList<? extends CodeLabel> list, CodeLabel value, int index,
			boolean isSelected, boolean cellHasFocus) {
		lineNumber.setText(value.getLineIndex() + "");
		return this;
	}
}
