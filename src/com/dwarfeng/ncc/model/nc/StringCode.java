package com.dwarfeng.ncc.model.nc;

import java.util.Objects;

/**
 * 利用 {@link String}来实现的 {@link Code}接口
 * @author DwArFeng
 * @since 1.8
 */
public final class StringCode implements Code{
	
	private final String code;
	private CodeLabel codeLabel;
	
	/**
	 * 生成一个字符串代码对象。
	 * @param code 指定的文本代码。
	 * @throws NullPointerException 入口参数为 <code>null</code>时抛出异常。
	 */
	public StringCode(String code, CodeLabel codeLabel) {
		Objects.requireNonNull(code);
		Objects.requireNonNull(codeLabel);
		this.code = code;
		this.codeLabel = codeLabel;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.nc.Code#content()
	 */
	@Override
	public String content() {
		return code;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.nc.Code#getLabel()
	 */
	@Override
	public CodeLabel getLabel() {
		return codeLabel;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.module.nc.Code#setLabel(com.dwarfeng.ncc.module.nc.CodeLabel)
	 */
	@Override
	public void setLabel(CodeLabel label) {
		this.codeLabel = label;
	}
	

}
