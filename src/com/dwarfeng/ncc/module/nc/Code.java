package com.dwarfeng.ncc.module.nc;

import com.dwarfeng.ncc.module.NccModuleObject;

public interface Code extends NccModuleObject{
	
	/**
	 * 返回该代码的文本形式。
	 * @return 该代码的文本形式。
	 */
	@Override
	public String toString();
}
