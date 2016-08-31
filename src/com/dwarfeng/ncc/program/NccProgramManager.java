package com.dwarfeng.ncc.program;

import java.util.Locale;
import java.util.ResourceBundle;

import com.dwarfeng.dfunc.prog.mvc.AbstractProgramManager;
import com.dwarfeng.ncc.program.key.ExceptionFieldKeys;
import com.dwarfeng.ncc.program.key.StringFieldKeys;

/**
 * ���ش�����֤����������������ṩ�������Լ��Ϻͳ�����ƶ˿ڡ�
 * @author DwArFeng
 * @since 1.8
 */
public final class NccProgramManager extends AbstractProgramManager<NccProgramControlPort, NccProgramAttrSet> {

	private static final String stringFieldPath = "resource/lang/stringField";
	private static final String exceptionFieldPath = "resource/lang/stringField";
	
	private final NccProgramControlPort programControlPort = new NccProgramControlPort() {
		
		
	};
	
	private final NccProgramAttrSet programAttrSet = new NccProgramAttrSet() {
		
		private ResourceBundle stringField = 
				ResourceBundle.getBundle(stringFieldPath,Locale.getDefault(),NccProgramManager.class.getClassLoader());
		
		private ResourceBundle exceptionField = 
				ResourceBundle.getBundle(exceptionFieldPath,Locale.getDefault(),NccProgramManager.class.getClassLoader());
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.program.NccProgramAttrSet#getStringField(java.lang.String)
		 */
		@Override
		public String getStringField(StringFieldKeys key) {
			return stringField.getString(key.toString());
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.program.NccProgramAttrSet#getExceptionField(com.dwarfeng.ncc.program.ExceptionFieldKeys)
		 */
		@Override
		public String getExceptionField(ExceptionFieldKeys key) {
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
