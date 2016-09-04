package com.dwarfeng.ncc.module;

import com.dwarfeng.dfunc.prog.mvc.AbstractModuleManager;
import com.dwarfeng.ncc.module.expl.ExplMoudle;
import com.dwarfeng.ncc.module.expl.ExplMoudleControlPort;
import com.dwarfeng.ncc.module.front.FrontModule;
import com.dwarfeng.ncc.module.front.FrontModuleControlPort;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.key.ExceptionFieldKey;

/**
 * 数控代码验证程序中的模型控制器，可提供模型控制端口。
 * @author DwArFeng
 * @since 1.8
 */
public final class NccModuleManager extends AbstractModuleManager<NccModuleControlPort, NccProgramAttrSet>{
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final ExceptionFieldKey KEY_NOTINIT = ExceptionFieldKey.MODL_NOTINIT;
	private static final ExceptionFieldKey KEY_INITED = ExceptionFieldKey.MODL_INITED;
	
	//------------------------------------------------------------------------------------------------
	
	private FrontModule frontModule;
	private ExplMoudle explMoudle;

	private final NccModuleControlPort moduleControlPort = new NccModuleControlPort() {

		private boolean initFlag = false;
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.module.NccModuleControlPort#init()
		 */
		@Override
		public void init() {
			if(initFlag) throw new IllegalStateException(getProgramAttrSet().getExceptionField(KEY_INITED));
			frontModule = new FrontModule(NccModuleManager.this);
			explMoudle = new ExplMoudle(NccModuleManager.this);
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.module.NccModuleControlPort#getFrontModuleControlPort()
		 */
		@Override
		public FrontModuleControlPort getFrontModuleControlPort() {
			if(!initFlag) throw new IllegalStateException(getProgramAttrSet().getExceptionField(KEY_NOTINIT));
			return frontModule.getFrontModuleControlPort();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.module.NccModuleControlPort#getExplMoudleControlPort()
		 */
		@Override
		public ExplMoudleControlPort getExplMoudleControlPort() {
			return explMoudle.getExplMoudleControlPort();
		}
		
		
	};
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.mvc.AbstractModuleManager#getModuleControlPort()
	 */
	@Override
	public NccModuleControlPort getModuleControlPort() {
		return moduleControlPort;
	}

}
