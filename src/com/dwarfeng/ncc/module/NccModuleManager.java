package com.dwarfeng.ncc.module;

import java.io.InputStream;

import com.dwarfeng.dfunc.prog.mvc.AbstractModuleManager;
import com.dwarfeng.ncc.module.expl.CodeLoader;
import com.dwarfeng.ncc.module.expl.ExplControlPort;
import com.dwarfeng.ncc.module.expl.ScannerCodeLoader;
import com.dwarfeng.ncc.module.front.FrontModuleControlPort;
import com.dwarfeng.ncc.module.nc.CodeSerial;
import com.dwarfeng.ncc.program.NccProgramAttrSet;

/**
 * ���ش�����֤�����е�ģ�Ϳ����������ṩģ�Ϳ��ƶ˿ڡ�
 * @author DwArFeng
 * @since 1.8
 */
public final class NccModuleManager extends AbstractModuleManager<NccModuleControlPort, NccProgramAttrSet>{
	
	//-----------------------------��������Ҫʹ�õĸ����ֶμ�ֵ------------------------------------
	
	private static final String KEY_NOTINIT = "ģ�͹�������û�г�ʼ��";
	private static final String KEY_INITED = "ģ�͹������Ѿ���ʼ����";
	
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
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.module.NccModuleControlPort#getFrontModuleControlPort()
		 */
		@Override
		public FrontModuleControlPort getFrontModuleControlPort() {
			if(!initFlag) throw new IllegalStateException(KEY_NOTINIT);
			return frontModuleControlPort;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.module.NccModuleControlPort#getExplMoudleControlPort()
		 */
		@Override
		public ExplControlPort getExplMoudleControlPort() {
			if(!initFlag) throw new IllegalStateException(KEY_NOTINIT);
			return explControlPort;
		}
		
		
	};
	
	private final ExplControlPort explControlPort = new ExplControlPort() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.module.expl.ExplControlPort#newNcCodeLoader(java.io.InputStream)
		 */
		@Override
		public CodeLoader newNcCodeLoader(InputStream in) {
			return new ScannerCodeLoader(in);
		}
	};
	
	private CodeSerial frontCodeSerial;
	
	private final FrontModuleControlPort frontModuleControlPort = new FrontModuleControlPort() {
		
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
