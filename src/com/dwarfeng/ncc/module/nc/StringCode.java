package com.dwarfeng.ncc.module.nc;

import java.util.Objects;

import com.dwarfeng.ncc.module.AbstractNccModuleObject;
import com.dwarfeng.ncc.module.NccModuleManager;

/**
 * ���� {@link String}��ʵ�ֵ� {@link Code}�ӿ�
 * @author DwArFeng
 * @since 1.8
 */
public final class StringCode extends AbstractNccModuleObject implements Code{
	
	private final String code;
	
	/**
	 * ����һ���ַ����������
	 * @param moduleManager ָ����ģ�͹�������
	 * @param code ָ�����ı����롣
	 * @throws NullPointerException <code>moduleManager</code>Ϊ <code>null</code>ʱ�׳����쳣��
	 * @throws NullPointerException <code>code</code>Ϊ <code>null</code>ʱ�׳��쳣��
	 */
	public StringCode(NccModuleManager moduleManager, String code) {
		super(moduleManager);
		Objects.requireNonNull(code);
		this.code = code;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return code;
	}
	

}
