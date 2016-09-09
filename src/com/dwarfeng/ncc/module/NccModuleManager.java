package com.dwarfeng.ncc.module;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

import com.dwarfeng.dfunc.prog.mvc.AbstractModuleManager;
import com.dwarfeng.ncc.module.expl.ExplCp;
import com.dwarfeng.ncc.module.front.CodeLoader;
import com.dwarfeng.ncc.module.front.FrontCp;
import com.dwarfeng.ncc.module.mut.ScannerCodeLoader;
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
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.module.front.FrontModuleControlPort#setFrontCodeSerial(com.dwarfeng.ncc.module.nc.CodeSerial)
			 */
			@Override
			public void setFrontCodeSerial(CodeSerial codeSerial, File file) {
				frontCodeSerial = codeSerial;
				linkedFile = file;
				rollback.clear();
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
				Objects.requireNonNull(config);
				maxRollback = config.getMaxRolBack();
				fitRoolback();
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.module.front.FrontModuleControlPort#getFontConfig()
			 */
			@Override
			public FrontConfig getFontConfig() {
				return new FrontConfig.Builder()
						.maxRollback(maxRollback)
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
				if(!hasFrontCode()) throw new IllegalStateException();
				return frontCodeSerial;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.module.front.FrontCp#overwriteCodeSerial(com.dwarfeng.ncc.module.nc.CodeSerial)
			 */
			@Override
			public void overwriteCodeSerial(CodeSerial codeSerial) {
				if(!hasFrontCode()) throw new IllegalStateException();
				rollback.push(frontCodeSerial);
				frontCodeSerial = codeSerial;
				fitRoolback();
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.module.front.FrontCp#linkFile(java.io.File)
			 */
			@Override
			public void linkFile(File file) {
				if(!hasFrontCode()) throw new IllegalStateException();
				linkedFile = file;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.module.front.FrontCp#getLinkedFile()
			 */
			@Override
			public File getLinkedFile() {
				if(!hasFrontCode()) throw new IllegalStateException();
				return linkedFile;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.module.front.FrontCp#needSave()
			 */
			@Override
			public boolean needSave() {
				if(!hasFrontCode()) throw new IllegalStateException();
				// TODO Auto-generated method stub
				return true;
			}
			
			private void fitRoolback(){
				while(rollback.size() > maxRollback){
					rollback.removeLast();
				}
			}
			
		};
		
		private final Deque<CodeSerial> rollback;
		
		private CodeSerial frontCodeSerial;
		private File linkedFile;
		private int maxRollback;
		
		
		public FrontModule() {
			rollback = new ArrayDeque<CodeSerial>();
		}
		
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
