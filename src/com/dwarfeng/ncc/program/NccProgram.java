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
 * 数控代码验证程序。
 * <p> 这个程序是DwArFeng在一次编程失败，导致工件过切之后编写的数控代码检测程序。
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
	 * 生成数控代码验证程序。
	 */
	public NccProgram() {
		super(new NccModelManager(), new NccViewManager(), new NccControlManager(), new NccProgramManager());
	}

}
