package com.dwarfeng.ncc.model.nc;

/**
 * �����ǩ��
 * @author DwArFeng
 * @since 1.8
 */
public interface CodeLabel {

	/**
	 * ���ش����������
	 * @return �����������
	 */
	public int getLineIndex();
	
	/**
	 * ���ظô����Ƿ񱻱�ǡ�
	 * @return �Ƿ񱻱�ǡ�
	 */
	public boolean isMarked();
	
}
