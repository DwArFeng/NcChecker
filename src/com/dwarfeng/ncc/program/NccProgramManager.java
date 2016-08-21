package com.dwarfeng.ncc.program;

import java.util.Locale;
import java.util.ResourceBundle;

import com.dwarfeng.dfunc.prog.mvc.AbstractProgramManager;

/**
 * 数控代码验证程序管理器，可以提供程序属性集合和程序控制端口。
 * @author DwArFeng
 * @since 1.8
 */
public final class NccProgramManager extends AbstractProgramManager<NccProgramControlPort, NccProgramAttrSet> {

	private static final String exceptionSfPath = "resource/lang/stringField";
	private static ResourceBundle stringField = 
			ResourceBundle.getBundle(exceptionSfPath,Locale.getDefault(),NccProgramManager.class.getClassLoader());

	
	private final NccProgramControlPort programControlPort = new NccProgramControlPort() {
		
		
	};
	
	private final NccProgramAttrSet programAttrSet = new NccProgramAttrSet() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.program.NccProgramAttrSet#getStringField(java.lang.String)
		 */
		@Override
		public String getStringField(StringFieldKeys key) {
			return stringField.getString(key.toString());
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
