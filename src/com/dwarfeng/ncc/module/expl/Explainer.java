package com.dwarfeng.ncc.module.expl;

import com.dwarfeng.ncc.module.nc.Modal;
import com.dwarfeng.ncc.module.nc.ToolPoint;

/**
 * NC代码解释器。
 * @author DwArFeng
 * @since 1.8
 */
public interface Explainer<T extends ToolPoint , M extends Modal> {
	
	
	/**
	 * 是否有下一句待解释的语句。
	 * @return 是否有下一句待指示的语句。
	 */
	public boolean hasNext();
	
}
