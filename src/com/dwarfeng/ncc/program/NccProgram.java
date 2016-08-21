package com.dwarfeng.ncc.program;

import com.dwarfeng.dfunc.prog.MvcProgram;
import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.control.NccControlPort;
import com.dwarfeng.ncc.module.NccModuleControlPort;
import com.dwarfeng.ncc.module.NccModuleManager;
import com.dwarfeng.ncc.view.NccViewControlPort;
import com.dwarfeng.ncc.view.NccViewManager;

/**
 * ���ش�����֤����
 * <p> ���������DwArFeng��һ�α��ʧ�ܣ����¹�������֮���д�����ش��������
 * 
 * @author DwArFeng
 * @since 1.8
 */
public final class NccProgram extends MvcProgram<NccProgramControlPort, NccModuleControlPort, 
NccViewControlPort, NccControlPort, NccProgramAttrSet> {
	
	

	public NccProgram() {
		super(new NccModuleManager(), new NccViewManager(), new NccControlManager(), new NccProgramManager());
		// TODO Auto-generated constructor stub
	}

}
