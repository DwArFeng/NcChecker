package com.dwarfeng.ncc.view;

import com.dwarfeng.dfunc.prog.mvc.AbstractViewManager;
import com.dwarfeng.ncc.control.NccControlPort;
import com.dwarfeng.ncc.program.NccProgramAttrSet;

public final class NccViewManager extends AbstractViewManager<NccViewControlPort, NccControlPort, NccProgramAttrSet> {

	private NccViewControlPort viewControlPort = new NccViewControlPort() {
		
		
	};
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.mvc.AbstractViewManager#getViewControlPort()
	 */
	@Override
	public NccViewControlPort getViewControlPort() {
		return viewControlPort;
	}

}
