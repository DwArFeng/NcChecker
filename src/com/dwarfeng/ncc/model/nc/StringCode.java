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
	 * @param codeLabel 指定的代码标签。
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
	 * @see com.dwarfeng.ncc.model.nc.Code#content()
	 */
	@Override
	public String content() {
		return code;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.model.nc.Code#getLabel()
	 */
	@Override
	public CodeLabel getLabel() {
		return codeLabel;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.model.nc.Code#setLabel(com.dwarfeng.ncc.model.nc.CodeLabel)
	 */
	@Override
	public void setLabel(CodeLabel label) {
		this.codeLabel = label;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder()
				.append("StringCode [content = ")
				.append(content())
				.append(" label = ")
				.append(getLabel())
				.append("]")
				.toString();
	}

}
