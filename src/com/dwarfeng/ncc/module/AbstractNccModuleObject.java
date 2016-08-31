package com.dwarfeng.ncc.module;

import java.util.Objects;

/**
 * {@link NccModuleObject}�ĳ���ʵ�֡�
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractNccModuleObject implements NccModuleObject{
	
	/**
	 * ���������õ�Nccģ�͹�������
	 */
	protected final NccModuleManager moduleManager;
	
	/**
	 * ����һ������ָ��Nccģ�͹�������Nccģ�Ͷ���
	 * @param moduleManager ָ����Nccģ�͹�������
	 * @throws NullPointerException ��ڲ���Ϊ<code>null</code>ʱ�׳����쳣��
	 */
	public AbstractNccModuleObject(NccModuleManager moduleManager) {
		Objects.requireNonNull(moduleManager);
		this.moduleManager = moduleManager;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.NccModuleObject#getModuleManger()
	 */
	@Override
	public NccModuleManager getModuleManger() {
		return moduleManager;
	}

}
