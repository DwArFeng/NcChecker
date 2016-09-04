package com.dwarfeng.ncc.module.expl;

import java.io.InputStream;

import com.dwarfeng.ncc.module.NccModuleObject;

/**
 * ���Ϳ���վ��
 * @author DwArFeng
 * @since 1.8
 */
public interface ExplMoudleControlPort extends NccModuleObject{

	/**
	 * ��ȡһ�����ָ�����������µ� {@link CodeLoader}��
	 * @param in ָ������������
	 * @return �µ� {@link CodeLoader}��
	 */
	public CodeLoader newNcCodeLoader(InputStream in);
	
}
