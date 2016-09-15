package com.dwarfeng.ncc.model;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

import com.dwarfeng.dfunc.prog.mvc.AbstractModelManager;
import com.dwarfeng.ncc.model.expl.ExplCp;
import com.dwarfeng.ncc.model.front.CodeLoader;
import com.dwarfeng.ncc.model.front.FrontCp;
import com.dwarfeng.ncc.model.mut.StreamCodeLoader;
import com.dwarfeng.ncc.model.nc.CodeSerial;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.conf.FrontConfig;

/**
 * 数控代码验证程序中的模型控制器，可提供模型控制端口。
 * @author DwArFeng
 * @since 1.8
 */
public final class NccModelManager extends AbstractModelManager<NccModelControlPort, NccProgramAttrSet>{
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final String KEY_NOTINIT = "模型管理器还没有初始化";
	private static final String KEY_INITED = "模型管理器已经初始化了";
	
	//------------------------------------------------------------------------------------------------

	private final NccModelControlPort modelControlPort = new NccModelControlPort() {

		private boolean initFlag = false;
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.model.NccModelControlPort#init()
		 */
		@Override
		public void init() {
			if(initFlag) throw new IllegalStateException(KEY_INITED);
			initFlag = true;
			frontModel = new FrontModel();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.model.NccModelControlPort#getFrontModelControlPort()
		 */
		@Override
		public FrontCp frontCp() {
			if(!initFlag) throw new IllegalStateException(KEY_NOTINIT);
			return frontModel.frontModelControlPort;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.model.NccModelControlPort#getExplMoudleControlPort()
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
		 * @see com.dwarfeng.ncc.model.expl.ExplControlPort#newNcCodeLoader(java.io.InputStream)
		 */
		@Override
		public CodeLoader newNcCodeLoader(InputStream in) {
			return new StreamCodeLoader(in);
		}
	};
	
	private FrontModel frontModel;
	
	private class FrontModel{
		
		private final FrontCp frontModelControlPort = new FrontCp() {
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.model.front.FrontModelControlPort#setFrontCodeSerial(com.dwarfeng.ncc.model.nc.CodeSerial)
			 */
			@Override
			public void setFrontCodeSerial(CodeSerial codeSerial, File file, boolean isCreate) {
				frontCodeSerial = codeSerial;
				linkedFile = file;
				rollback.clear();
				saveFlag = isCreate;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.model.front.FrontModelControlPort#getFrontCodeSerial()
			 */
			@Override
			public CodeSerial getFrontCodeSerial() {
				return frontCodeSerial;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.model.front.FrontModelControlPort#applyFontConfig(com.dwarfeng.ncc.program.conf.FrontConfig)
			 */
			@Override
			public void applyFontConfig(FrontConfig config) {
				Objects.requireNonNull(config);
				maxRollback = config.getMaxRolBack();
				fitRoolback();
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.model.front.FrontModelControlPort#getFontConfig()
			 */
			@Override
			public FrontConfig getFontConfig() {
				return new FrontConfig.Builder()
						.maxRollback(maxRollback)
						.build();
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.model.front.FrontModelControlPort#hasFrontCode()
			 */
			@Override
			public boolean hasFrontCode() {
				return frontCodeSerial != null;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.model.front.FrontModelControlPort#getCodeSerial(com.dwarfeng.ncc.model.front.Page)
			 */
			@Override
			public CodeSerial getCodeSerial() {
				if(!hasFrontCode()) throw new IllegalStateException();
				return frontCodeSerial;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.model.front.FrontCp#overwriteCodeSerial(com.dwarfeng.ncc.model.nc.CodeSerial)
			 */
			@Override
			public void commitCodeSerial(CodeSerial codeSerial) {
				if(!hasFrontCode()) throw new IllegalStateException();
				rollback.push(frontCodeSerial);
				frontCodeSerial = codeSerial;
				saveFlag = true;
				fitRoolback();
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.model.front.FrontCp#linkFile(java.io.File)
			 */
			@Override
			public void linkFile(File file) {
				if(!hasFrontCode()) throw new IllegalStateException();
				linkedFile = file;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.model.front.FrontCp#getLinkedFile()
			 */
			@Override
			public File getLinkedFile() {
				if(!hasFrontCode()) throw new IllegalStateException();
				return linkedFile;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.model.front.FrontCp#needSave()
			 */
			@Override
			public boolean needSave() {
				if(!hasFrontCode()) throw new IllegalStateException();
				return saveFlag;
			}
			
			private void fitRoolback(){
				while(rollback.size() > maxRollback){
					rollback.removeLast();
				}
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.model.front.FrontCp#knockForSave()
			 */
			@Override
			public void knockForSave() {
				saveFlag = false;
			}
			
		};
		
		private final Deque<CodeSerial> rollback;
		
		private CodeSerial frontCodeSerial;
		private File linkedFile;
		private int maxRollback;
		private boolean saveFlag;
		
		
		public FrontModel() {
			rollback = new ArrayDeque<CodeSerial>();
			saveFlag = false;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.mvc.AbstractModelManager#getModelControlPort()
	 */
	@Override
	public NccModelControlPort getModelControlPort() {
		return modelControlPort;
	}

}
