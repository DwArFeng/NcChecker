package com.dwarfeng.ncc.program.key;

/**
 * 程序字段键值枚举。
 * @author DwArFeng
 * @since 1.8
 */
public enum StringFieldKey {

	/**文本文件字段*/
	CTRL_TEXTFILE,
	/**NC文件字段*/
	CTRL_NCFILE,
	
	/**程序主界面的标题字段*/
	MAINFRAME_TITLE,
	
	/**程序主界面没有任务的标题字段*/
	PROGRESS_NOMISSION,
	/**正在读取文件*/
	PROGRESS_FILE_NOWLOADING,
	
	/**就绪*/
	LABEL_GETREADY,
	
	/**文件菜单*/
	MENU_FILE,
	/**编辑菜单*/
	MENU_EDIT,

	/**打开文本*/
	MENU_FILE_OPENFILE,
	/**打开文本的描述*/
	MENU_FILE_OPENFILE_DES,
	/**新建文本*/
	MENU_FILE_NEW,
	/**新建文本的描述*/
	MENU_FILE_NEW_DES,
	
	/**启动完毕*/
	OUT_STARTFIN,
	/**正在读取NC文件*/
	OUT_LOADFILE_START,
	/**读取完毕后输出的统计*/
	OUT_LOADFILE_STATS,
	/**读取失败*/
	OUT_LOADFILE_FAIL,
	
}
