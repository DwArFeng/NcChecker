package com.dwarfeng.ncc.model;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

import com.dwarfeng.dfunc.prog.mvc.AbstractModuleManager;
import com.dwarfeng.ncc.model.expl.ExplCp;
import com.dwarfeng.ncc.model.front.CodeLoader;
import com.dwarfeng.ncc.model.front.FrontCp;
import com.dwarfeng.ncc.model.mut.StreamCodeLoader;
import com.dwarfeng.ncc.model.nc.CodeSerial;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.conf.FrontConfig;

/**
 * ���ش�����֤�����е�ģ�Ϳ����������ṩģ�Ϳ��ƶ˿ڡ�
 * @author DwArFeng
 * @since 1.8
 */
public final class NccModelManager extends AbstractModuleManager<NccModelControlPort, NccProgramAttrSet>{
	
	//-----------------------------��������Ҫʹ�õĸ����ֶμ�ֵ------------------------------------
	
	private static final String KEY_NOTINIT = "ģ�͹�������û�г�ʼ��";
	private static final String KEY_INITED = "ģ�͹������Ѿ���ʼ����";
	
	//------------------------------------------------------------------------------------------------

	private final NccModelControlPort moduleControlPort = new NccModelControlPort() {

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
			return new StreamCodeLoader(in);
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
			public void setFrontCodeSerial(CodeSerial codeSerial, File file, boolean isCreate) {
				frontCodeSerial = codeSerial;
				linkedFile = file;
				rollback.clear();
				saveFlag = isCreate;
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
			public void commitCodeSerial(CodeSerial codeSerial) {
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
				return saveFlag;
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
		private boolean saveFlag;
		
		
		public FrontModule() {
			rollback = new ArrayDeque<CodeSerial>();
			saveFlag = false;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.mvc.AbstractModuleManager#getModuleControlPort()
	 */
	@Override
	public NccModelControlPort getModuleControlPort() {
		return moduleControlPort;
	}

}
