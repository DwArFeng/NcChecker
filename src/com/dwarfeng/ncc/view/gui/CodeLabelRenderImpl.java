package com.dwarfeng.ncc.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.dwarfeng.ncc.module.nc.Code;
import com.dwarfeng.ncc.module.nc.CodeLabel;

/**
 * 代码标签的渲染类。
 * @author DwArFeng
 * @since 1.8
 */
public final class CodeLabelRenderImpl extends JPanel implements ListCellRenderer<Code> {
	
	private static final long serialVersionUID = 1163394929476733457L;
	
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
	public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
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
	public void firePropertyChange(String propertyName, double oldValue, double newValue) {}

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
	public void firePropertyChange(String propertyName, short oldValue, short newValue) {}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(
			JList<? extends Code> list, Code value, int index,
			boolean isSelected, boolean cellHasFocus) {
		if(value != null){
			lineNumber.setText(value.getLabel().getLineIndex() + "");
		}else{
			lineNumber.setText("");
		}
		return this;
	}
	
}
