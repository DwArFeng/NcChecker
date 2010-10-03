package com.dwarfeng.ncc.model.nc;

import java.util.Objects;

/**
 * ���� {@link String}��ʵ�ֵ� {@link Code}�ӿ�
 * @author DwArFeng
 * @since 1.8
 */
public final class StringCode implements Code{
	
	private final String code;
	private CodeLabel codeLabel;
	
	/**
	 * ����һ���ַ����������
	 * @param code ָ�����ı����롣
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>ʱ�׳��쳣��
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
