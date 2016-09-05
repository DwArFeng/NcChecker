package com.dwarfeng.ncc.module.expl;

import java.io.InputStream;

/**
 * ���Ϳ���վ��
 * @author DwArFeng
 * @since 1.8
 */
public interface ExplControlPort{

	/**
	 * ��ȡһ�����ָ�����������µ� {@link CodeLoader}��
	 * @param in ָ������������
	 * @return �µ� {@link CodeLoader}��
	 */
	public CodeLoader newNcCodeLoader(InputStream in);
	
}
