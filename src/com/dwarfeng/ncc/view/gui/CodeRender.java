package com.dwarfeng.ncc.view.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import sun.swing.DefaultLookup;

import com.dwarfeng.ncc.module.nc.Code;

/**
 * 代码渲染类。
 * @author DwArFeng
 * @since 1.8
 */
public class CodeRender extends JLabel implements ListCellRenderer<Code> {

	private static final long serialVersionUID = -1460053000339852911L;

	/**
	 * 生成一个代码渲染类。
	 */
	public CodeRender() {
		setOpaque(true);
		setBorder(new EmptyBorder(new Insets(0, 5, 0, 0)));
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
		
        Color bg = null;
        Color fg = null;

        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

            bg = DefaultLookup.getColor(this, ui, "List.dropCellBackground");
            fg = DefaultLookup.getColor(this, ui, "List.dropCellForeground");

            isSelected = true;
        }

        if (isSelected) {
            setBackground(bg == null ? list.getSelectionBackground() : bg);
            setForeground(fg == null ? list.getSelectionForeground() : fg);
        }
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

		setText(value.content());
		return this;
	}
	
	/**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     *
     * @since 1.5
     */
     @Override
     public void invalidate() {}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#isOpaque()
	 */
    @Override
    public boolean isOpaque() {
        Color back = getBackground();
        Component p = getParent();
        if (p != null) {
            p = p.getParent();
        }
        // p should now be the JList.
        boolean colorMatch = (back != null) && (p != null) &&
            back.equals(p.getBackground()) &&
                        p.isOpaque();
        return !colorMatch && super.isOpaque();
    }

	/**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     *
     * @since 1.5
     */
     @Override
     public void repaint() {}

	/**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
     @Override
     public void repaint(long tm, int x, int y, int width, int height) {}

	/**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
     @Override
     public void repaint(Rectangle r) {}

	/**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
     @Override
     public void revalidate() {}

	/**
     * Overridden for performance reasons.
     * See the <a href="#override">Implementation Note</a>
     * for more information.
     */
     @Override
     public void validate() {}
}
