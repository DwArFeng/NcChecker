package com.dwarfeng.ncc.control.back;

import java.util.Objects;

import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.model.NccModelControlPort;
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
	protected final NccModelControlPort modelControlPort;
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
		this.modelControlPort = cm.getModelControlPort();
		this.programAttrSet = cm.getProgramAttrSet();
		this.programControlPort = cm.getProgramControlPort();
	}

}
