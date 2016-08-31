package com.dwarfeng.ncc.module.expl;

/**
 * Nc代码的解析状态。
 * @author DwArFeng
 * @since 1.8
 */
public enum ExplState{
	/**没有被解析*/
	NOTEXPL,
	/**正在被解析*/
	EXPLING,
	/**已经完成解析*/
	EXPLED;
}