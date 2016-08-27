package com.dwarfeng.ncc.module.nc;

/**
 * NC代码接口。
 * 
 * @author DwArFeng
 * @since 1.8
 */
public interface NcCode extends Previousable<NcCode>{
	
	/**
	 * 获取NC代码的文本值。
	 * @return 文本值。
	 */
	public String getCode();

}
