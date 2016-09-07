package com.dwarfeng.ncc.module;

import java.io.InputStream;
import java.util.Objects;

import com.dwarfeng.dfunc.prog.mvc.AbstractModuleManager;
import com.dwarfeng.ncc.module.expl.CodeLoader;
import com.dwarfeng.ncc.module.expl.ExplCp;
import com.dwarfeng.ncc.module.expl.ScannerCodeLoader;
import com.dwarfeng.ncc.module.front.FrontCp;
import com.dwarfeng.ncc.module.nc.CodeSerial;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.conf.FrontConfig;

/**
 * 数控代码验证程序中的模型控制器，可提供模型控制端口。
 * @author DwArFeng
 * @since 1.8
 */
public final class NccModuleManager extends AbstractModuleManager<NccModuleControlPort, NccProgramAttrSet>{
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final String KEY_NOTINIT = "模型管理器还没有初始化";
	private static final String KEY_INITED = "模型管理器已经初始化了";
	
	//------------------------------------------------------------------------------------------------

	private final NccModuleControlPort moduleControlPort = new NccModuleControlPort() {

		private boolean initFlag = false;
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.module.NccModuleControlPort#init()
		 */
		@Override
		public void init() {
			if(initFlag) throw new IllegalStateException(KEY_INITED);
			initFlag = true;
			frontModule = new FrontModule();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.module.NccModuleControlPort#getFrontModuleControlPort()
		 */
		@Override
		public FrontCp frontCp() {
			if(!initFlag) throw new IllegalStateException(KEY_NOTINIT);
			return frontModule.frontModuleControlPort;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.module.NccModuleControlPort#getExplMoudleControlPort()
		 */
		@Override
		public ExplCp explCp() {
			if(!initFlag) throw new IllegalStateException(KEY_NOTINIT);
			return explControlPort;
		}
		
		
	};
	
	private final ExplCp explControlPort = new ExplCp() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.module.expl.ExplControlPort#newNcCodeLoader(java.io.InputStream)
		 */
		@Override
		public CodeLoader newNcCodeLoader(InputStream in) {
			return new ScannerCodeLoader(in);
		}
	};
	
	private FrontModule frontModule;
	
	private class FrontModule{
		
		private final FrontCp frontModuleControlPort = new FrontCp() {
			
			private CodeSerial frontCodeSerial;
			private int codesInPage;
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.module.front.FrontModuleControlPort#setFrontCodeSerial(com.dwarfeng.ncc.module.nc.CodeSerial)
			 */
			@Override
			public void setFrontCodeSerial(CodeSerial codeSerial) {
				frontCodeSerial = codeSerial;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.module.front.FrontModuleControlPort#getFrontCodeSerial()
			 */
			@Override
			public CodeSerial getFrontCodeSerial() {
				return frontCodeSerial;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.module.front.FrontModuleControlPort#applyFontConfig(com.dwarfeng.ncc.program.conf.FrontConfig)
			 */
			@Override
			public void applyFontConfig(FrontConfig config) {
				
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.module.front.FrontModuleControlPort#getFontConfig()
			 */
			@Override
			public FrontConfig getFontConfig() {
				return new FrontConfig.Builder()
						.build();
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.module.front.FrontModuleControlPort#hasFrontCode()
			 */
			@Override
			public boolean hasFrontCode() {
				return frontCodeSerial != null;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.module.front.FrontModuleControlPort#getCodeSerial(com.dwarfeng.ncc.module.front.Page)
			 */
			@Override
			public CodeSerial getCodeSerial() {
				return frontCodeSerial;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.module.front.FrontCp#overwriteCodeSerial(com.dwarfeng.ncc.module.nc.CodeSerial)
			 */
			@Override
			public void overwriteCodeSerial(CodeSerial codeSerial) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		public FrontModule() {}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.mvc.AbstractModuleManager#getModuleControlPort()
	 */
	@Override
	public NccModuleControlPort getModuleControlPort() {
		return moduleControlPort;
	}

}
