package com.dwarfeng.ncc.program;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.dwarfeng.dfunc.prog.MvcProgram;
import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.control.NccControlPort;
import com.dwarfeng.ncc.model.NccModelControlPort;
import com.dwarfeng.ncc.model.NccModelManager;
import com.dwarfeng.ncc.view.NccViewControlPort;
import com.dwarfeng.ncc.view.NccViewManager;

/**
 * ���ش�����֤����
 * <p> ���������DwArFeng��һ�α��ʧ�ܣ����¹�������֮���д�����ش��������
 * 
 * @author DwArFeng
 * @since 1.8
 */
public final class NccProgram extends MvcProgram<NccProgramControlPort, NccModelControlPort, 
NccViewControlPort, NccControlPort, NccProgramAttrSet> {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		NccProgram program = new NccProgram();
		program.controlManager.getControlPort().startProgram();
	}

	/**
	 * �������ش�����֤����
	 */
	public NccProgram() {
		super(new NccModelManager(), new NccViewManager(), new NccControlManager(), new NccProgramManager());
	}

}
