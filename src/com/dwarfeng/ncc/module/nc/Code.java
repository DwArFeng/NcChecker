package com.dwarfeng.ncc.module.nc;

public interface Code{
	
	/**
	 * ���ظô�����ı���ʽ��
	 * @return �ô�����ı���ʽ��
	 */
	public String content();
	
	/**
	 * ���ش���ı�ǩ��
	 * @return ����ı�ǩ��
	 */
	public CodeLabel getLabel();
	
	/**
	 * ���ô���ı�ǩ��
	 * @param label ����ı�ǩ��
	 */
	public void setLabel(CodeLabel label);
	
}
