package com.dwarfeng.ncc.module;

import java.io.InputStream;

import com.dwarfeng.dfunc.prog.mvc.ModuleControlPort;
import com.dwarfeng.ncc.module.expl.CodeLoader;

/**
 * ���ش�����֤�����е�ģ�Ϳ��ƶ˿ڡ�
 * @author DwArFeng
 * @since 1.8
 */
public interface NccModuleControlPort extends ModuleControlPort {
	
	/**
	 * ��ʼ����
	 * @throws IllegalStateException �ظ���ʼ����
	 */
	public void init();
	
	/**
	 * ��ȡһ�����ָ�����������µ� {@link CodeLoader}��
	 * @param in ָ������������
	 * @return �µ� {@link CodeLoader}��
	 */
	public CodeLoader newNcCodeLoader(InputStream in);

}
