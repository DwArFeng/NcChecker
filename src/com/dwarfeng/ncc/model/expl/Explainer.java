package com.dwarfeng.ncc.model.expl;

import com.dwarfeng.ncc.model.nc.Modal;
import com.dwarfeng.ncc.model.nc.ToolPoint;

/**
 * NC�����������
 * @author DwArFeng
 * @since 1.8
 */
public interface Explainer<T extends ToolPoint , M extends Modal> {
	
	
	/**
	 * �Ƿ�����һ������͵���䡣
	 * @return �Ƿ�����һ���ָʾ����䡣
	 */
	public boolean hasNext();
	
}
