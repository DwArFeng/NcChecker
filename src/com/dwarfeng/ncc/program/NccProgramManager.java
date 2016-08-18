package com.dwarfeng.ncc.program;

import com.dwarfeng.dfunc.prog.AbstractProgramManager;

public final class NccProgramManager extends AbstractProgramManager<NccProgramControlPort, NccProgramAttrSet> {

	private final NccProgramControlPort programControlPort = new NccProgramControlPort() {
		
		
	};
	
	private final NccProgramAttrSet programAttrSet = new NccProgramAttrSet() {
		
		
	};
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.AbstractProgramManager#getProgramAttrSet()
	 */
	@Override
	public NccProgramAttrSet getProgramAttrSet() {
		return programAttrSet;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.AbstractProgramManager#getProgramControlPort()
	 */
	@Override
	public NccProgramControlPort getProgramControlPort() {
		return programControlPort;
	}

}
