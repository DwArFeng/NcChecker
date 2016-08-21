package com.dwarfeng.ncc.module;

import java.util.Objects;

/**
 * ������Mccģ������Ķ���
 * @author DwArFeng
 * @since 1.8
 */
public abstract class NccModuleObject {
	
	/**
	 * ���������õ�Mccģ�͹�������
	 */
	protected final NccModuleManager moduleManager;
	
	/**
	 * ����һ������ָ��Nccģ�͹�������Nccģ�Ͷ���
	 * @param moduleManager ֪�������Nccģ�͹�������
	 * @throws NullPointerException ��ڲ���Ϊ<code>null</code>ʱ�׳����쳣��
	 */
	public NccModuleObject(NccModuleManager moduleManager) {
		Objects.requireNonNull(moduleManager);
		this.moduleManager = moduleManager;
	}

}
