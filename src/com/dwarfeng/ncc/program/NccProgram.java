package com.dwarfeng.ncc.program;

import java.io.FileNotFoundException;
import java.io.IOException;

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
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		NccProgram program = new NccProgram();
		program.controlManager.getControlPort().startProgram();
	}

	/**
	 * �������ش�����֤����
	 */
	public NccProgram() {
		super(new NccModuleManager(), new NccViewManager(), new NccControlManager(), new NccProgramManager());
	}

}
