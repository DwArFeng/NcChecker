package com.dwarfeng.ncc.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.dwarfeng.dfunc.DwarfFunction;
import com.dwarfeng.dfunc.io.CT;
import com.dwarfeng.dfunc.prog.mvc.AbstractControlManager;
import com.dwarfeng.ncc.control.back.OpenFileRunnable;
import com.dwarfeng.ncc.module.NccModuleControlPort;
import com.dwarfeng.ncc.module.front.CodeLoader;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.NccProgramControlPort;
import com.dwarfeng.ncc.program.conf.FrontConfig;
import com.dwarfeng.ncc.program.conf.MfAppearConfig;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.NccViewControlPort;
import com.dwarfeng.ncc.view.gui.NotifyCp;
import com.dwarfeng.ncc.view.gui.StatusLabelType;

/**
 * ���ش�����֤�����еĿ��ƹ����������ṩ���ƶ˿ڡ�
 * @author DwArFeng
 * @since 1.8
 */
public final class NccControlManager extends AbstractControlManager<NccProgramControlPort, NccModuleControlPort,
NccViewControlPort, NccControlPort, NccProgramAttrSet> {
	
	//-----------------------------��������Ҫʹ�õĸ����ֶμ�ֵ------------------------------------
	
	private static final String KEY_NOTINIT = "���ƹ�������δ��ʼ����";
	private static final String KEY_INITED = "���ƹ������Ѿ���ʼ���ˡ�";
	private static final StringFieldKey KEY_GETREADY = StringFieldKey.LABEL_GETREADY;
	private static final StringFieldKey KEY_STARTFIN = StringFieldKey.OUT_STARTFIN;
	
	//------------------------------------------------------------------------------------------------
	
	private final NccControlPort controlPort = new NccControlPort() {
		
		private boolean startFlag = false;
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#startProgram()
		 */
		@Override
		public void startProgram() {
			if(startFlag) throw new IllegalStateException(KEY_INITED);
			startFlag = true;
			
			//���ڽ���֧�ָ���ۣ����Բ������׳��쳣��
			try {
				UIManager.setLookAndFeel(new NimbusLookAndFeel());
			} catch (UnsupportedLookAndFeelException e) {}
			
			//������վ���ʼ��
			programControlPort.init();
			moduleControlPort.init();
			viewControlPort.init();
			
			//��ȡ�����ļ�
			MfAppearConfig mfac = null;
			try {
				mfac = programControlPort.configCp().loadMainFrameAppearConfig();
			} catch (NumberFormatException e) {
				mfac = MfAppearConfig.DEFAULT_CONFIG;
			} catch (IOException e) {
				mfac = MfAppearConfig.DEFAULT_CONFIG;
			}
			//TODO ����Ϊ��ȡʽ��
			FrontConfig fc = FrontConfig.DEFAULT;
			
			//������������ʼ������
			moduleControlPort.frontCp().applyFontConfig(fc);
			moduleControlPort.frontCp().setFrontCodeSerial(null,null);
			viewControlPort.frameCp().applyAppearanceConfig(mfac);
			viewControlPort.frameCp().noneFileMode(true);
			
			//��ʾ����������
			viewControlPort.frameCp().setVisible(true);
			
			//��������ı�
			viewControlPort.frameCp().setStatusLabelMessage(programAttrSet.getStringField(KEY_GETREADY), StatusLabelType.NORMAL);
			viewControlPort.frameCp().traceInConsole(DwarfFunction.getWelcomeString());
			viewControlPort.frameCp().traceInConsole("");
			viewControlPort.frameCp().traceInConsole(CT.toString(programAttrSet.getStringField(KEY_STARTFIN)));
		}

		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#exitProgram()
		 */
		@Override
		public void exitProgram() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			
			//���ؽ���
			viewControlPort.frameCp().setVisible(false);
			
			//�����������
			MfAppearConfig config = viewControlPort.frameCp().getAppearanceConfig();
			try {
				programControlPort.configCp().saveMainFrameAppearConfig(config);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//���ø������������ս᷽����
			
			//�˳�����
			System.exit(0);
			
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#openNcFile()
		 */
		@Override
		public void openNcFile() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			
			final boolean aFlag = true;
			final FileFilter[] fileFilters = new FileFilter[]{
				new FileNameExtensionFilter(programAttrSet.getStringField(StringFieldKey.CTRL_TEXTFILE), "txt"),
				new FileNameExtensionFilter(programAttrSet.getStringField(StringFieldKey.CTRL_NCFILE), "nc", "ptp", "mpf")
			};
			final File file = viewControlPort.notifyCp().askFile(fileFilters, aFlag);
			if(file == null) return;
			openNcFile(file);
		}


		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#openNcFile(java.io.File)
		 */
		@Override
		public void openNcFile(File file) {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			Objects.requireNonNull(file);
			
			//�п���Ҫ�ر��ļ�
			perhapsCloseFrontFile();
			
			InputStream in = null;
			CodeLoader codeLoader = null;
			try{
				in = new FileInputStream(file);
				codeLoader = moduleControlPort.explCp().newNcCodeLoader(in);
			}catch(IOException e){
				return;
			}
			
			programControlPort.backInvoke(new OpenFileRunnable(NccControlManager.this, codeLoader, file));
		}


		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#closeFrontFile()
		 */
		@Override
		public void closeFrontFile() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			if(!moduleControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
			
			//������Ҫ�����ļ�
			perhapseSaveFrontFile();
			
			try{
				viewControlPort.frameCp().lockEdit();
				//����ͼ�г��´����
				viewControlPort.frameCp().showCode(null);
				//�Ƴ�ǰ̨ģ���еĴ����
				moduleControlPort.frontCp().setFrontCodeSerial(null, null);
				
			}finally{
				viewControlPort.frameCp().unlockEdit();
			}
			// TODO Auto-generated method stub
			
		}
		
		/**
		 * ���ĳ���������ܻ��漰���ر��ļ�������ø÷�����
		 * �÷�����쳵�Ƿ����ǰ̨�ļ��������ǰ̨�ļ���ǰ̨�ļ���Ҫ����Ļ�������رա�
		 */
		private void perhapsCloseFrontFile(){
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			if(		moduleControlPort.frontCp().hasFrontCode() &&
					moduleControlPort.frontCp().needSave()) closeFrontFile();
		}


		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#saveFrontFile()
		 */
		@Override
		public void saveFrontFile() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			if(!moduleControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
			
			// TODO Auto-generated method stub
		}
		
		/**
		 * ���ĳ���������ܻ��漰�������ļ�������ø÷�����
		 * �÷�����쳵�Ƿ����ǰ̨�ļ��������ǰ̨�ļ��Ļ������䱣�档
		 */
		private void perhapseSaveFrontFile(){
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			if(moduleControlPort.frontCp().hasFrontCode()) saveFrontFile();
		}
		
		
	};
	
	
	
	
	
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.mvc.AbstractControlManager#getControlPort()
	 */
	@Override
	public NccControlPort getControlPort() {
		return controlPort;
	}

}
