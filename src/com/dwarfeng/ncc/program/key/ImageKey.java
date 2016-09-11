package com.dwarfeng.ncc.program.key;

import java.net.URL;

/**
 * 图像枚举。
 * @author DwArFeng
 * @since 1.8
 */
public enum ImageKey{
	
	/**新建文件的图标*/
	M_NEW("/resource/image/m_new.png"),
	/**打开文件的图标*/
	M_OPEN("/resource/image/m_open.png"),
	/**关闭文件的图标*/
	M_CLOSE("/resource/image/m_close.png"),
	/**保存文件的图标*/
	M_SAVE("/resource/image/m_save.png"),
	/**另存为文件的图标*/
	M_SAVEA("/resource/image/m_savea.png"),
	/**进度监视器的终止图标*/
	P_SUSPEND("/resource/image/p_suspend.png"),
	/**代码查看图标*/
	C_INSPECT("/resource/image/c_inspect.png"),
	C_EDIT("/resource/image/c_edit.png")
	;
	
	private URL url;
	
	private ImageKey(String str) {
		url = ImageKey.class.getResource(str);
	}
	
	/**
	 * 返回图像枚举主键对应的URI。
	 * @return 对应的URI。
	 */
	public URL getUrl(){
		return this.url;
	}
}
