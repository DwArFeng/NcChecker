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

import com.dwarfeng.dfunc.io.CT;
import com.dwarfeng.dfunc.prog.mvc.AbstractControlManager;
import com.dwarfeng.ncc.module.NccModuleControlPort;
import com.dwarfeng.ncc.module.expl.CodeLoader;
import com.dwarfeng.ncc.module.expl.ExplMoudleControlPort;
import com.dwarfeng.ncc.module.nc.Code;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.NccProgramControlPort;
import com.dwarfeng.ncc.program.conf.MainFrameAppearConfig;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.NccViewControlPort;
import com.dwarfeng.ncc.view.gui.NotifyControlPort;
import com.dwarfeng.ncc.view.gui.ProgressMonitor;

/**
 * ���ش�����֤�����еĿ��ƹ����������ṩ���ƶ˿ڡ�
 * @author DwArFeng
 * @since 1.8
 */
public final class NccControlManager extends AbstractControlManager<NccProgramControlPort, NccModuleControlPort,
NccViewControlPort, NccControlPort, NccProgramAttrSet> {
	
	

	private NccControlPort controlPort = new NccControlPort() {
		
		private boolean startFlag = false;

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#startProgram()
		 */
		@Override
		public void startProgram() {
			//TODO �ĳ�StringField
			if(startFlag) throw new IllegalStateException("�����Ѿ�������");
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
			MainFrameAppearConfig mfac = null;
			try {
				mfac = programControlPort.loadMainFrameAppearConfig();
			} catch (NumberFormatException e) {
				mfac = MainFrameAppearConfig.DEFAULT_CONFIG;
			} catch (IOException e) {
				mfac = MainFrameAppearConfig.DEFAULT_CONFIG;
			}
			
			//��ʾ����������
			viewControlPort.getMainFrameControlPort().applyAppearance(mfac);
			viewControlPort.getMainFrameControlPort().setVisible(true);
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#exitProgram()
		 */
		@Override
		public void exitProgram() {
			//TODO ʹ��StringField
			if(!startFlag) throw new IllegalStateException("����δ����");
			
			//���ؽ���
			viewControlPort.getMainFrameControlPort().setVisible(false);
			
			//�����������
			MainFrameAppearConfig config = viewControlPort.getMainFrameControlPort().getCurrentAppearance();
			try {
				programControlPort.saveMainFrameAppearConfig(config);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//���ø������������ս᷽����
			
			//�˳�����
			System.exit(0);
			
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#openFile()
		 */
		@Override
		public void openNcFile() {
			final NccProgramAttrSet attrSet = getProgramAttrSet();
			final NotifyControlPort notifyControlPort = getViewControlPort().getNotifyControlPort();
			final boolean aFlag = true;
			final FileFilter[] fileFilters = new FileFilter[]{
				new FileNameExtensionFilter(attrSet.getStringField(StringFieldKey.CTRL_TEXTFILE), "txt"),
				new FileNameExtensionFilter(attrSet.getStringField(StringFieldKey.CTRL_NCFILE), "nc", "ptp", "mpf")
			};
			final File file = notifyControlPort.askFile(fileFilters, aFlag);
			if(file == null) return;
			final ProgressMonitor progressMonitor = viewControlPort.getProgressMonitor();
			final ExplMoudleControlPort explMoudleControlPort = moduleControlPort.getExplMoudleControlPort();
			InputStream in = null;
			try{
				in = new FileInputStream(file);
				final CodeLoader codeLoader = explMoudleControlPort.newNcCodeLoader(in);
				final Runnable runnable = new Runnable() {
					
					@Override
					public void run() {
						try{
							progressMonitor.startMonitor();
							//TODO ʹ��StringField
							progressMonitor.setMessage("��ʼ��ȡ�ļ�...");
							progressMonitor.setIndeterminate(true);
							int i = 1;
							final List<Code> codeList = new ArrayList<Code>();
							while(codeLoader.hasNext()){
								if(progressMonitor.isSuspend()){
									return;
								}
								codeList.add(codeLoader.loadNext());
								progressMonitor.setMessage("��ʼ��ȡ�ļ�..." + i);
								i++;
							}
							
						}catch(Exception e){
							e.printStackTrace();
							return;
						}finally{
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
