package com.dwarfeng.ncc.module.expl;

import java.io.InputStream;

import com.dwarfeng.ncc.module.AbstractNccModuleObject;
import com.dwarfeng.ncc.module.NccModuleManager;

/**
 * 解释模型。
 * @author DwArFeng
 * @since 1.8
 */
public final class ExplMoudle extends AbstractNccModuleObject{

	/**
	 * 生成具有指定模型控制器的解释模型。
	 * @param moduleManager 指定的模型管理器。
	 * @throws NullPointerException 入口参数为<code>null</code>时抛出此异常。
	 */
	public ExplMoudle(NccModuleManager moduleManager) {
		super(moduleManager);
	}

	/**
	 * 获取解释模型的控制器。
	 * @return 解释模型控制器。
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
