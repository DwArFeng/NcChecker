package com.dwarfeng.ncc.module.front;

import com.dwarfeng.ncc.module.AbstractNccModuleObject;
import com.dwarfeng.ncc.module.NccModuleManager;

/**
 * 前端模型。
 * @author DwArFeng
 * @since 1.8
 */
public final class FrontModule extends AbstractNccModuleObject {

	/**
	 * 生成具有指定模型控制器的前台模型。
	 * @param moduleManager 指定的模型管理器。
	 * @throws NullPointerException 入口参数为<code>null</code>时抛出此异常。
	 */
	public FrontModule(NccModuleManager moduleManager) {
		super(moduleManager);
	}
	
	/**
	 * 返回前端模型对应的控制站。
	 * @return 前台模型控制站。
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
