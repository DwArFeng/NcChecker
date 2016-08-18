package com.dwarfeng.ncc.control;

import com.dwarfeng.dfunc.prog.mvc.AbstractControlManager;
import com.dwarfeng.ncc.module.NccModuleControlPort;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.NccProgramControlPort;
import com.dwarfeng.ncc.view.NccViewControlPort;

public final class NccControlManager extends AbstractControlManager<NccProgramControlPort, NccModuleControlPort,
NccViewControlPort, NccControlPort, NccProgramAttrSet> {

	private NccControlPort controlPort = new NccControlPort() {
		
		
	};
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.mvc.AbstractControlManager#getControlPort()
	 */
	@Override
	public NccControlPort getControlPort() {
		return controlPort;
	}

}
