package com.dwarfeng.ncc.module.nc;

import java.util.Comparator;
import java.util.Objects;

public final class CodeLabelComparator implements Comparator<CodeLabel> {

	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(CodeLabel o1, CodeLabel o2) {
		Objects.requireNonNull(o1);
		Objects.requireNonNull(o2);
		if(o1.getLineIndex() == o2.getLineIndex()) return 0;
		return o1.getLineIndex() > o2.getLineIndex() ? 1 : -1;
	}
	
}
