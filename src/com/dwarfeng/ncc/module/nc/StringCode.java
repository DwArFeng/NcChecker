package com.dwarfeng.ncc.module.nc;

import java.util.Objects;

import com.dwarfeng.ncc.module.AbstractNccModuleObject;
import com.dwarfeng.ncc.module.NccModuleManager;

/**
 * 利用 {@link String}来实现的 {@link Code}接口
 * @author DwArFeng
 * @since 1.8
 */
public final class StringCode extends AbstractNccModuleObject implements Code{
	
	private final String code;
	
	/**
	 * 生成一个字符串代码对象。
	 * @param moduleManager 指定的模型管理器。
	 * @param code 指定的文本代码。
	 * @throws NullPointerException <code>moduleManager</code>为 <code>null</code>时抛出此异常。
	 * @throws NullPointerException <code>code</code>为 <code>null</code>时抛出异常。
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
