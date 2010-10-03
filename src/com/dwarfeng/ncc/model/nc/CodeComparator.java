package com.dwarfeng.ncc.model.nc;

import java.util.Comparator;
import java.util.Objects;

/**
 * ����Ƚ�����
 * <p> ���ݴ����ǩ�е������ߵ����Ƚϴ��롣
 * @author DwArFeng
 * @since 1.8
 */
public final class CodeComparator implements Comparator<Code> {

	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Code o1, Code o2) {
		Objects.requireNonNull(o1);
		Objects.requireNonNull(o2);
		return new CodeLabelComparator().compare(o1.getLabel(), o2.getLabel());
	}

}
