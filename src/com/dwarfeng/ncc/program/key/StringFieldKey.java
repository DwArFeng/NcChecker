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
	/**正在进入编辑模式*/
	PROGRESS_CODE_EDIT,
	
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
	/**关闭文件*/
	MENU_FILE_CLOSE,
	/**关闭文件的描述*/
	MENU_FILE_CLOSE_DES,
	/**保存文件*/
	MENU_FILE_SAVE,
	/**保存文件的描述*/
	MENU_FILE_SAVE_DES,
	/**另存为文件*/
	MENU_FILE_SAVEA,
	/**另存为文件的描述*/
	MENU_FILE_SAVEA_DES,
	
	/**提交更改*/
	CODE_COMMIT,
	/**提交更改的描述*/
	CODE_COMMIT_DES,
	/**提交更改并退出*/
	CODE_COMMITNQ,
	/**提交更改并退出的描述*/
	CODE_COMMITNQ_DES,
	/**放弃更改*/
	CODE_DISCARD,
	/**放弃更改的描述*/
	CODE_DISCARD_DES,
	/**放弃更改并退出*/
	CODE_DISCARDNQ,
	/**放弃更改并退出的描述*/
	CODE_DISCARDNQ_DES,
	
	/**启动完毕*/
	OUT_STARTFIN,
	/**正在读取NC文件*/
	OUT_LOADFILE_START,
	/**读取完毕后输出的统计*/
	OUT_LOADFILE_STATS,
	/**读取失败*/
	OUT_LOADFILE_FAIL,
	/**读取人为中止*/
	OUT_LOADFILE_SUSPEND,
	/**新建文件开始*/
	OUT_NEWFILE_START,
	/**新建文件统计*/
	OUT_NEWFILE_STATS,
	/**开始进入编辑模式*/
	OUT_EDIT_START,
	/**进入编辑模式统计*/
	OUT_EDIT_STATS,
	/**进入编辑模式失败*/
	OUT_EDIT_FAIL,
	/**进入编辑模式中止*/
	OUT_EDIT_SUSPEND,
	
	/**询问提交的标题*/
	MSG_COMMIT_TITLE,
	/**询问提交的信息*/
	MSG_COMMIT_MESSAGE
}
