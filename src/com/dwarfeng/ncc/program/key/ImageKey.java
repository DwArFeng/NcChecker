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
	M_CLOSE("/resource/image/m_close.png");
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
