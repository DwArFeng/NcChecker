package com.dwarfeng.ncc.module.expl;

import java.io.InputStream;

import com.dwarfeng.ncc.module.AbstractNccModuleObject;
import com.dwarfeng.ncc.module.NccModuleManager;

/**
 * ����ģ�͡�
 * @author DwArFeng
 * @since 1.8
 */
public final class ExplMoudle extends AbstractNccModuleObject{

	/**
	 * ���ɾ���ָ��ģ�Ϳ������Ľ���ģ�͡�
	 * @param moduleManager ָ����ģ�͹�������
	 * @throws NullPointerException ��ڲ���Ϊ<code>null</code>ʱ�׳����쳣��
	 */
	public ExplMoudle(NccModuleManager moduleManager) {
		super(moduleManager);
	}

	/**
	 * ��ȡ����ģ�͵Ŀ�������
	 * @return ����ģ�Ϳ�������
	 */
	public ExplMoudleControlPort getExplMoudleControlPort(){
		return controlPort;
	}
	
	private final ExplMoudleControlPort controlPort = new ExplMoudleControlPort() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.module.NccModuleObject#getModuleManger()
		 */
		@Override
		public NccModuleManager getModuleManger() {
			return moduleManager;
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.module.expl.ExplMoudleControlPort#newNcCodeLoader(java.io.InputStream)
		 */
		@Override
		public CodeLoader newNcCodeLoader(InputStream in) {
			return new ScannerCodeLoader(moduleManager, in);
		}
	};
	
	
}
