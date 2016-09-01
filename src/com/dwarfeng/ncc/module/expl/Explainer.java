package com.dwarfeng.ncc.module.expl;

import com.dwarfeng.ncc.module.nc.Modal;
import com.dwarfeng.ncc.module.nc.ToolPoint;

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
