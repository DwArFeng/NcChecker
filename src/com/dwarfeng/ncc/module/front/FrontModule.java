package com.dwarfeng.ncc.module.front;

import com.dwarfeng.ncc.module.AbstractNccModuleObject;
import com.dwarfeng.ncc.module.NccModuleManager;

/**
 * ǰ��ģ�͡�
 * @author DwArFeng
 * @since 1.8
 */
public final class FrontModule extends AbstractNccModuleObject {

	/**
	 * ���ɾ���ָ��ģ�Ϳ�������ǰ̨ģ�͡�
	 * @param moduleManager ָ����ģ�͹�������
	 * @throws NullPointerException ��ڲ���Ϊ<code>null</code>ʱ�׳����쳣��
	 */
	public FrontModule(NccModuleManager moduleManager) {
		super(moduleManager);
	}
	
	/**
	 * ����ǰ��ģ�Ͷ�Ӧ�Ŀ���վ��
	 * @return ǰ̨ģ�Ϳ���վ��
	 */
	public FrontModuleControlPort getFrontModuleControlPort(){
		return controlPort;
	}
	
	private final FrontModuleControlPort controlPort = new FrontModuleControlPort() {
		
		

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.module.NccModuleObject#getModuleManger()
		 */
		@Override
		public NccModuleManager getModuleManger() {
			return moduleManager;
		}
		
		
	};
	

}
