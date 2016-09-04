package com.dwarfeng.ncc.program;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import com.dwarfeng.dfunc.io.FileFunction;
import com.dwarfeng.dfunc.prog.mvc.AbstractProgramManager;
import com.dwarfeng.dfunc.threads.RunnerQueue;
import com.dwarfeng.ncc.program.conf.MainFrameAppearConfig;
import com.dwarfeng.ncc.program.key.ExceptionFieldKey;
import com.dwarfeng.ncc.program.key.StringFieldKey;

/**
 * 数控代码验证程序管理器，可以提供程序属性集合和程序控制端口。
 * @author DwArFeng
 * @since 1.8
 */
public final class NccProgramManager extends AbstractProgramManager<NccProgramControlPort, NccProgramAttrSet> {

	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final String KEY_NOTINIT = "程序管理器还未初始化。";
	private static final ExceptionFieldKey KEY_INITED = ExceptionFieldKey.PROG_INITED;
	
	//------------------------------------------------------------------------------------------------
	
	private static final String STRING_FIELD_PATH = "resource/lang/StringField";
	private static final String EXCEPTION_FIELD_PATH = "resource/lang/ExceptionField";
	private static final String MFAPPEAR_CONFIG_PATH = "config/mfappear.cfg";
	private static final String MFAPPEAR_CONFIG_COMMENT = "Config for main frame's appearance";
	
	private boolean initFlag = false;
	private ResourceBundle stringField;	
	private ResourceBundle exceptionField;

	private RunnerQueue<Runnable> runnerQueue;
	
	private final NccProgramControlPort programControlPort = new NccProgramControlPort() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.program.NccProgramControlPort#init()
		 */
		@Override
		public void init() {
			if(initFlag) throw new IllegalStateException(programAttrSet.getExceptionField(KEY_INITED));
			stringField = ResourceBundle.getBundle(STRING_FIELD_PATH,Locale.getDefault(),NccProgramManager.class.getClassLoader());
			exceptionField = ResourceBundle.getBundle(EXCEPTION_FIELD_PATH,Locale.getDefault(),NccProgramManager.class.getClassLoader());
			runnerQueue = new RunnerQueue<Runnable>();
			runnerQueue.runThread();
			initFlag = true;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.program.NccProgramControlPort#saveAppearConfig(com.dwarfeng.ncc.program.conf.AppearConfig)
		 */
		@Override
		public void saveMainFrameAppearConfig(MainFrameAppearConfig config) throws IOException {
			if(!initFlag) throw new IllegalStateException(KEY_NOTINIT);
			
			Properties properties = new Properties();
			
			properties.put(MainFrameAppearConfig.SF_extendedState, ""+config.getExtendedState());
			properties.put(MainFrameAppearConfig.SF_frameWidth, ""+config.getFrameWidth());
			properties.put(MainFrameAppearConfig.SF_frameHeitht, ""+config.getFrameHeight());
			properties.put(MainFrameAppearConfig.SF_codePanelWidth, ""+config.getCodePanelWidth());
			properties.put(MainFrameAppearConfig.SF_consolePanelHeight, ""+config.getConsolePanelHeight());
			
			File file = new File(MFAPPEAR_CONFIG_PATH);
			OutputStream out = null;
			
			try{
				FileFunction.createFileIfNotExists(file);
				out = new FileOutputStream(file);
				properties.store(out, MFAPPEAR_CONFIG_COMMENT);
			}finally{
				if(out != null) out.close(); 
			}
			
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.program.NccProgramControlPort#loadMainFrameAppearConfig()
		 */
		@Override
		public MainFrameAppearConfig loadMainFrameAppearConfig()throws IOException, NumberFormatException {
			if(!initFlag) throw new IllegalStateException(KEY_NOTINIT);
			
			MainFrameAppearConfig def = MainFrameAppearConfig.DEFAULT_CONFIG;
			
			InputStream in = null;
			
			int mExpandState;
			int mFrameWidth;
			int mFrameHeight;
			int mCodePanelWidth;
			int mConsolePanelHeight;
			
			try {
				in = new FileInputStream(new File(MFAPPEAR_CONFIG_PATH));
				Properties properties = new Properties();
				properties.load(in);
				
				mExpandState = new Integer(properties.getProperty(MainFrameAppearConfig.SF_extendedState,""+def.getExtendedState()));
				mFrameWidth = new Integer(properties.getProperty(MainFrameAppearConfig.SF_frameWidth,""+def.getFrameWidth()));
				mFrameHeight = new Integer(properties.getProperty(MainFrameAppearConfig.SF_frameHeitht,""+def.getFrameHeight()));
				mCodePanelWidth = new Integer(properties.getProperty(MainFrameAppearConfig.SF_codePanelWidth,""+def.getCodePanelWidth()));
				mConsolePanelHeight = new Integer(properties.getProperty(MainFrameAppearConfig.SF_consolePanelHeight,""+def.getConsolePanelHeight()));
				
				return new MainFrameAppearConfig.Builder()
						.extendedState(mExpandState)
						.frameWidth(mFrameWidth)
						.frameHeight(mFrameHeight)
						.codePanelWidth(mCodePanelWidth)
						.consolePanelHeight(mConsolePanelHeight)
						.build();
				
			}finally{
				if(in != null) in.close();
			}
			
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.program.NccProgramControlPort#backInvoke(java.lang.Runnable)
		 */
		@Override
		public void backInvoke(Runnable runnable) {
			if(!initFlag) throw new IllegalStateException(KEY_NOTINIT);
			runnerQueue.invoke(runnable);
		}

		
		
	};
	
	private final NccProgramAttrSet programAttrSet = new NccProgramAttrSet() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.program.NccProgramAttrSet#getStringField(java.lang.String)
		 */
		@Override
		public String getStringField(StringFieldKey key) {
			if(!initFlag) throw new IllegalStateException(KEY_NOTINIT);
			return stringField.getString(key.toString());
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.program.NccProgramAttrSet#getExceptionField(com.dwarfeng.ncc.program.ExceptionFieldKeys)
		 */
		@Override
		public String getExceptionField(ExceptionFieldKey key) {
			if(!initFlag) throw new IllegalStateException(KEY_NOTINIT);
			return exceptionField.getString(key.toString());
		}
		
	};
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.AbstractProgramManager#getProgramAttrSet()
	 */
	@Override
	public NccProgramAttrSet getProgramAttrSet() {
		return programAttrSet;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.AbstractProgramManager#getProgramControlPort()
	 */
	@Override
	public NccProgramControlPort getProgramControlPort() {
		return programControlPort;
	}

}
