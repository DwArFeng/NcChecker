package com.dwarfeng.ncc.model.nc;

import java.util.Comparator;
import java.util.Objects;

/**
 * 代码比较器。
 * <p> 根据代码标签中的行数高低来比较代码。
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
