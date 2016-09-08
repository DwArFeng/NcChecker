package com.dwarfeng.ncc.control.back;

import java.util.Objects;

import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.module.NccModuleControlPort;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.NccProgramControlPort;
import com.dwarfeng.ncc.view.NccViewControlPort;

/**
 * ����Ŀ��ƹ����������ࡣ
 * @author DwArFeng
 * @since 1.8
 */
abstract class AbstractCmr implements Runnable{
	
	protected final NccControlManager cm;
	protected final NccViewControlPort viewControlPort;
	protected final NccModuleControlPort moduleControlPort;
	protected final NccProgramControlPort programControlPort;
	protected final NccProgramAttrSet programAttrSet;

	/**
	 * ����һ��ӵ��ָ�����ƹ��������õĳ����ࡣ
	 * @param controlManager ָ��������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public AbstractCmr(NccControlManager controlManager) {
		Objects.requireNonNull(controlManager);
		this.cm = controlManager;
		this.viewControlPort = cm.getViewControlPort();
		this.moduleControlPort = cm.getModuleControlPort();
		this.programAttrSet = cm.getProgramAttrSet();
		this.programControlPort = cm.getProgramControlPort();
	}

}
