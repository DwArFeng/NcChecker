package com.dwarfeng.ncc.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.dwarfeng.dfunc.DwarfFunction;
import com.dwarfeng.dfunc.prog.mvc.AbstractControlManager;
import com.dwarfeng.ncc.module.NccModuleControlPort;
import com.dwarfeng.ncc.module.expl.CodeLoader;
import com.dwarfeng.ncc.module.expl.ExplCp;
import com.dwarfeng.ncc.module.front.Page;
import com.dwarfeng.ncc.module.nc.ArrayCodeList;
import com.dwarfeng.ncc.module.nc.Code;
import com.dwarfeng.ncc.module.nc.CodeSerial;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.NccProgramControlPort;
import com.dwarfeng.ncc.program.conf.FrontConfig;
import com.dwarfeng.ncc.program.conf.MfAppearConfig;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.NccViewControlPort;
import com.dwarfeng.ncc.view.gui.FrameCp;
import com.dwarfeng.ncc.view.gui.NotifyCp;
import com.dwarfeng.ncc.view.gui.ProgCp;
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
	private static final StringFieldKey KEY_GETREADY = StringFieldKey.OUT_GETREADY;
	private static final StringFieldKey KEY_LOADING = StringFieldKey.FILE_NOWLOADING;
	
	//------------------------------------------------------------------------------------------------
	
	private final NccControlPort controlPort = new NccControlPort() {
		
		private boolean startFlag = false;

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#startProgram()
		 */

		
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
			final NccProgramAttrSet attrSet = programAttrSet;
			final NotifyCp notifyControlPort = getViewControlPort().notifyCp();
			final boolean aFlag = true;
			final FileFilter[] fileFilters = new FileFilter[]{
				new FileNameExtensionFilter(attrSet.getStringField(StringFieldKey.CTRL_TEXTFILE), "txt"),
				new FileNameExtensionFilter(attrSet.getStringField(StringFieldKey.CTRL_NCFILE), "nc", "ptp", "mpf")
			};
			final File file = notifyControlPort.askFile(fileFilters, aFlag);
			if(file == null) return;
			final ProgCp progressMonitor = viewControlPort.progCp();
			final ExplCp explMoudleControlPort = moduleControlPort.explCp();
			InputStream in = null;
			viewControlPort.frameCp().lockEdit();
			try{
				in = new FileInputStream(file);
				final CodeLoader codeLoader = explMoudleControlPort.newNcCodeLoader(in);
				final Runnable runnable = new Runnable() {
					
					@Override
					public void run() {
						try{
							progressMonitor.startMonitor();
							progressMonitor.setMessage(attrSet.getStringField(KEY_LOADING));
							progressMonitor.setIndeterminate(true);
							int i = 1;
							final List<Code> codes = new ArrayList<Code>();
							while(codeLoader.hasNext()){
								if(progressMonitor.isSuspend()){
									return;
								}
								codes.add(codeLoader.loadNext());
								progressMonitor.setMessage(attrSet.getStringField(KEY_LOADING) + i);
								i++;
							}
							
							CodeSerial codeList = new ArrayCodeList(codes.toArray(new Code[0]));
							moduleControlPort.frontCp().setFrontCodeSerial(codeList);
							viewControlPort.frameCp().setCodeTotlePages(moduleControlPort.frontCp().getFrontCodePage());
							
							viewControlPort.frameCp().showCode(moduleControlPort.frontCp().getCodeSerial(Page.PAGE_ONE), true);
							viewControlPort.frameCp().setCodePage(Page.PAGE_ONE);
							
						}catch(Exception e){
							e.printStackTrace();
							return;
						}finally{
							viewControlPort.frameCp().unlockEdit();
							progressMonitor.endMonitor();
							try {
								codeLoader.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
					}
				};
				
				programControlPort.backInvoke(runnable);
				
			}catch(IOException e){
				return;
			}
			
			
		}
		
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
			
			
			//��ʾ����������
			final FrameCp mainFrameControlPort = viewControlPort.frameCp();
			mainFrameControlPort.applyAppearanceConfig(mfac);
			mainFrameControlPort.setVisible(true);
			
			//��������ı�
			mainFrameControlPort.setStatusLabelMessage(programAttrSet.getStringField(KEY_GETREADY), StatusLabelType.NORMAL);
			mainFrameControlPort.traceInConsole(DwarfFunction.getWelcomeString());
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#codePageChange(com.dwarfeng.ncc.module.front.Page)
		 */
		@Override
		public void toggleCodePage(Page page, boolean flag) {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			if(!moduleControlPort.frontCp().hasFrontCode())
				throw new IllegalStateException("Ϊ�λ���û��ǰ�˴��������µ������������");
			viewControlPort.frameCp().setModiFlag(true);
			try{
				viewControlPort.frameCp().showCode(moduleControlPort.frontCp().getCodeSerial(page), flag);
				viewControlPort.frameCp().setCodePage(page);
			}finally{
				viewControlPort.frameCp().setModiFlag(false);
			}
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
