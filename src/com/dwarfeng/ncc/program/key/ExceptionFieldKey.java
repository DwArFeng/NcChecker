package com.dwarfeng.ncc.program.key;

/**
 * 程序异常字段的键值枚举。
 * @author DwArFeng
 * @since 1.8
 */
public enum ExceptionFieldKey {

	/**视图管理器还未初始化异常字段*/
	VIEW_NOTINIT,
	/**视图已经初始化异常字段*/
	VIEW_INITED,
	
	/**模型未初始化异常字段*/
	MODL_NOTINIT,
	/**模型已经初始化异常字段*/
	MODL_INITED,
	
	/**程序管理器未初始化异常字段*/
	PROG_NOTINIT,
	/**程序管理器已经初始化异常字段*/
	PROG_INITED,
	
	/**进度监视器还未启动*/
	PGES_NOTSTART,
	/**进度监视器已经启动*/
	PGES_STARTED,
	
}
