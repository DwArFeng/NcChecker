package com.dwarfeng.ncc.program;

import java.util.Objects;

public class AbstractAttRefable implements AttRefable {

	protected final NccProgramAttrSet aSet;
	
	public AbstractAttRefable(NccProgramAttrSet aSet) {
		Objects.requireNonNull(aSet);
		this.aSet = aSet;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.program.AttRefable#getAttrSet()
	 */
	@Override
	public NccProgramAttrSet getAttrSet() {
		return this.aSet;
	}

}
