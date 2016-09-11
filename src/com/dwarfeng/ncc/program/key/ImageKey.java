package com.dwarfeng.ncc.program.key;

import java.net.URL;

/**
 * 图像枚举。
 * @author DwArFeng
 * @since 1.8
 */
public enum ImageKey{
	
	M_NEW("/resource/image/m_new.png");
	
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
