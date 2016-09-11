package com.dwarfeng.ncc.program.key;

import java.net.URL;

/**
 * ͼ��ö�١�
 * @author DwArFeng
 * @since 1.8
 */
public enum ImageKey{
	
	/**�½��ļ���ͼ��*/
	M_NEW("/resource/image/m_new.png"),
	/**���ļ���ͼ��*/
	M_OPEN("/resource/image/m_open.png"),
	/**�ر��ļ���ͼ��*/
	M_CLOSE("/resource/image/m_close.png");
	;
	
	private URL url;
	
	private ImageKey(String str) {
		url = ImageKey.class.getResource(str);
	}
	
	/**
	 * ����ͼ��ö��������Ӧ��URI��
	 * @return ��Ӧ��URI��
	 */
	public URL getUrl(){
		return this.url;
	}
}
