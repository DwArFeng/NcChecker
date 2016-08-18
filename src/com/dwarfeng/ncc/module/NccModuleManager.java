package com.dwarfeng.ncc.module;

import com.dwarfeng.dfunc.prog.mvc.AbstractModuleManager;
import com.dwarfeng.ncc.program.NccProgramAttrSet;

public final class NccModuleManager extends AbstractModuleManager<NccModuleControlPort, NccProgramAttrSet>{

	private final NccModuleControlPort moduleControlPort = new NccModuleControlPort() {
		
		
	};
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.mvc.AbstractModuleManager#getModuleControlPort()
	 */
	@Override
	public NccModuleControlPort getModuleControlPort() {
		return moduleControlPort;
	}

}
