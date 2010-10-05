package com.dwarfeng.ncc.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.dwarfeng.dfunc.DwarfFunction;
import com.dwarfeng.dfunc.dt.CodeTimer;
import com.dwarfeng.dfunc.io.CT;
import com.dwarfeng.dfunc.io.CT.OutputType;
import com.dwarfeng.dfunc.io.FileFunction;
import com.dwarfeng.dfunc.io.StringInputStream;
import com.dwarfeng.dfunc.num.UnitTrans.Time;
import com.dwarfeng.dfunc.prog.mvc.AbstractControlManager;
import com.dwarfeng.ncc.control.back.NewFileRunnable;
import com.dwarfeng.ncc.control.back.OpenFileRunnable;
import com.dwarfeng.ncc.control.back.Toggle2EidtRunnable;
import com.dwarfeng.ncc.control.cps.CodeCp;
import com.dwarfeng.ncc.control.cps.CodeCp.CodeEidtMode;
import com.dwarfeng.ncc.control.cps.FileCp;
import com.dwarfeng.ncc.model.NccModelControlPort;
import com.dwarfeng.ncc.model.front.CodeLoader;
import com.dwarfeng.ncc.model.front.CodePrinter;
import com.dwarfeng.ncc.model.mut.StreamCodeLoader;
import com.dwarfeng.ncc.model.mut.StreamCodePrinter;
import com.dwarfeng.ncc.model.nc.ArrayCodeSerial;
import com.dwarfeng.ncc.model.nc.Code;
import com.dwarfeng.ncc.model.nc.CodeSerial;
import com.dwarfeng.ncc.program.NccProgramAttrSet;
import com.dwarfeng.ncc.program.NccProgramControlPort;
import com.dwarfeng.ncc.program.conf.FrontConfig;
import com.dwarfeng.ncc.program.conf.MfAppearConfig;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.NccViewControlPort;
import com.dwarfeng.ncc.view.gui.DefaultProgressModel;
import com.dwarfeng.ncc.view.gui.NotifyCp.AnswerType;
import com.dwarfeng.ncc.view.gui.NotifyCp.MessageType;
import com.dwarfeng.ncc.view.gui.NotifyCp.OptionType;
import com.dwarfeng.ncc.view.gui.ProgressModel;
import com.dwarfeng.ncc.view.gui.StatusLabelType;

/**
 * ���ش�����֤�����еĿ��ƹ����������ṩ���ƶ˿ڡ�
 * @author DwArFeng
 * @since 1.8
 */
public final class NccControlManager extends AbstractControlManager<NccProgramControlPort, NccModelControlPort,
NccViewControlPort, NccControlPort, NccProgramAttrSet> {
	
	//-----------------------------��������Ҫʹ�õĸ����ֶμ�ֵ------------------------------------

	private static final String KEY_NOTINIT = "���ƹ�������δ��ʼ����";
	private static final String KEY_INITED = "���ƹ������Ѿ���ʼ���ˡ�";
	
	private static final StringFieldKey KEY_GETREADY = StringFieldKey.LABEL_GETREADY;
	private static final StringFieldKey KEY_STARTFIN = StringFieldKey.OUT_STARTFIN;
	
	private static final StringFieldKey KEY_COMMIT_TITLE = StringFieldKey.MSG_COMMIT_TITLE;
	private static final StringFieldKey KEY_COMMIT_MESSAGE = StringFieldKey.MSG_COMMIT_MESSAGE;
	
	//------------------------------------------------------------------------------------------------
	
	private static  class SaveCheckResult {
		
		public static final SaveCheckResult DEFAULT = new SaveCheckResult(false, false);
		
		public final boolean saveFlag;
		public final boolean suspendFlag;
		
		public SaveCheckResult(boolean saveFlag, boolean suspendFlag){
			this.saveFlag = saveFlag;
			this.suspendFlag = suspendFlag;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if(obj == null) return false;
			if(!(obj instanceof SaveCheckResult)) return false;
			SaveCheckResult r = (SaveCheckResult) obj;
			return r.saveFlag == saveFlag && r.suspendFlag == suspendFlag;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			int i = 0;
			i += saveFlag ? 1 : 0;
			i *= 2;
			i += suspendFlag ? 1 : 0;
			return i;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return new StringBuilder()
					.append("SaveCheckResult [saveFlag = ")
					.append(saveFlag)
					.append(", suspendFlag = ")
					.append(saveFlag)
					.append("]")
					.toString();
		}
		
	}
	
	
	
	
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
			
//			//���ڽ���֧�ָ���ۣ����Բ������׳��쳣��
//			try {
//				UIManager.setLookAndFeel(new NimbusLookAndFeel());
//			} catch (UnsupportedLookAndFeelException e) {}
			
			//�����ʼ��
			fileControl = new FileControl();
			codeControl = new CodeControl();
			
			//������վ���ʼ��
			programControlPort.init();
			modelControlPort.init();
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
			modelControlPort.frontCp().applyFontConfig(fc);
			modelControlPort.frontCp().setFrontCodeSerial(null,null,false);
			viewControlPort.frameCp().applyAppearanceConfig(mfac);
			viewControlPort.frameCp().noneFileMode(true);
			
			//��ʾ����������
			viewControlPort.frameCp().setVisible(true);
			
			//��������ı�
			CT.setOutputType(OutputType.NO_DATE);
			viewControlPort.frameCp().setStatusLabelMessage(programAttrSet.getStringField(KEY_GETREADY), StatusLabelType.NORMAL);
			viewControlPort.frameCp().traceInConsole(DwarfFunction.getWelcomeString());
			viewControlPort.frameCp().traceInConsole("");
			CT.setOutputType(OutputType.HALF_DATE);
			viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_STARTFIN));
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

		private void toggle2Inspect() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			
			//���ȼ���Ƿ���δ�ύ�ĸ���
			if(viewControlPort.frameCp().needCommit()){
				AnswerType type = viewControlPort.notifyCp().showConfirm(
						programAttrSet.getStringField(KEY_COMMIT_MESSAGE), 
						programAttrSet.getStringField(KEY_COMMIT_TITLE), 
						OptionType.YES_NO_CANCEL, 
						MessageType.QUESTION, null
				);
				
				programControlPort.backInvoke(
						new Toggle2InspectRunnable(NccControlManager.this, type));
			}else{
				
				programControlPort.backInvoke(
						new Toggle2InspectRunnable(NccControlManager.this, AnswerType.NO));
			}
			
		}

		private void toggle2Edit() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			
			
			programControlPort.backInvoke(new Toggle2EidtRunnable(NccControlManager.this));
		}


		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#commitCode(java.lang.String)
		 */
		@Override
		public void commitCode(String str) {
			// TODO Auto-generated method stub
			
		}


		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#fileCp()
		 */
		@Override
		public FileCp fileCp() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			return fileControl.fileCp;
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.ncc.control.NccControlPort#codeCp()
		 */
		@Override
		public CodeCp codeCp() {
			if(!startFlag) throw new IllegalStateException(KEY_NOTINIT);
			return codeControl.codeCp;
		}
		
		
	};
	
	private FileControl fileControl;
	
	
	//-----------------------------��������Ҫʹ�õĸ����ֶμ�ֵ------------------------------------

	private static final StringFieldKey KEY_SAVE_TITLE = StringFieldKey.MSG_SAVE_TITLE;
	private static final StringFieldKey KEY_SAVE_MESSAGE = StringFieldKey.MSG_SAVE_MESSAGE;
	
	private static final StringFieldKey KEY_COMMIT_PROG = StringFieldKey.PROGRESS_COMMIT;
	private static final StringFieldKey KEY_COMMIT_START = StringFieldKey.OUT_COMMIT_START;
	private static final StringFieldKey KEY_COMMIT_STATS = StringFieldKey.OUT_COMMIT_STATS;
	private static final StringFieldKey KEY_COMMIT_FAIL = StringFieldKey.OUT_COMMIT_FAIL;
	private static final StringFieldKey KEY_COMMIT_SUSPEND= StringFieldKey.OUT_COMMIT_SUSPEND;
	
	private static final StringFieldKey KEY_SAVE_PROG = StringFieldKey.PROGRESS_SAVE;
	private static final StringFieldKey KEY_SAVE_START = StringFieldKey.OUT_SAVE_START;
	private static final StringFieldKey KEY_SAVE_STATS = StringFieldKey.OUT_SAVE_STATS;
	private static final StringFieldKey KEY_SAVE_FAIL = StringFieldKey.OUT_SAVE_FAIL;
	private static final StringFieldKey KEY_SAVE_SUSPEND= StringFieldKey.OUT_SAVE_SUSPEND;
	
	//------------------------------------------------------------------------------------------------
	
	private class FileControl{
		
		private final FileCp fileCp = new FileCp() {

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.control.cps.FileCp#closeFrontFile()
			 */
			@Override
			public void closeFrontFile() {
				if(!modelControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
				//��̨������ָ������
				programControlPort.backInvoke(CloseFrontFileRunnable);
			}
			
			private final Runnable CloseFrontFileRunnable = new Runnable() {
				
				/*
				 * (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					try{
						viewControlPort.frameCp().lockEdit();
						//ִ���ڲ�������
						inner();
						return;
					}catch(Exception e){
						e.printStackTrace();
						return;
					}finally{
						viewControlPort.frameCp().unlockEdit();
					}
				}
				
				private void inner(){
					//�ж��Ƿ����ǰ̨�ļ� ��û��ǰ̨�ļ��Ļ�����ʽ�׳��쳣��
					if(!modelControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
					
					
					//�жϳ����Ƿ��ڴ���༭ģʽ��
					if(codeControl.getCodeEidtMode() == CodeEidtMode.EDIT){
						//ѯ�ʴ����Ƿ���Ҫ�ύ
						SaveCheckResult commitResult = checkCommitCode();
						//�ж��Ƿ���ֹ����
						if(commitResult.suspendFlag) return;
						//�ж��Ƿ�Ҫ�ύ�ļ���
						if(commitResult.saveFlag){
							//ִ���ύ�ļ��ķ��������Ҳ鿴�����Ƿ���Ϊ��ֹ
							if(inner_commit()) return;
						}
						//�л�������鿴ģʽ
						inner_toggleInspect();
					}
					
					//ѯ���ļ��Ƿ�Ҫ����
					SaveCheckResult saveResult = checkSaveFrontFile();
					//�����ж��Ƿ���ֹ����
					if(saveResult.suspendFlag) return;
					//�ж��Ƿ�Ҫ�����ļ�
					if(saveResult.saveFlag){
						//ִ�б����ļ��ķ��������Ҳ鿴�����Ƿ���Ϊ��ֹ��
						if(inner_save()) return;
					}
					//�ر��ļ�����
					inner_close();
				}

				private void inner_close() {
					// TODO Auto-generated method stub
					
				}

				private void inner_toggleInspect() {
					//��ͼת��Ϊ�༭ģʽ
					codeControl.setCodeEditMode(CodeEidtMode.INSPECT);
					//��Ⱦ��������
					viewControlPort.frameCp().showCode(modelControlPort.frontCp().getFrontCodeSerial());
				}

				private boolean inner_commit() {
					//��ȡ��Ҫ�ı���
					InputStream in = new StringInputStream(viewControlPort.frameCp().getEditText());
					CodeLoader codeLoader = null;
					
					try{
						//��ʼ��Ҫ�ı���
						codeLoader = new StreamCodeLoader(in);
						final List<Code> codes = new ArrayList<Code>();
						
						//�����ʼ��Ϣ
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_COMMIT_START),
								viewControlPort.frameCp().getEditLine()
						);
						
						//���ɽ���ģ��
						ProgressModel progressModel = new DefaultProgressModel();
						progressModel.setIndeterminate(false);
						progressModel.setLabelText(programAttrSet.getStringField(KEY_COMMIT_PROG));
						progressModel.setMaximum(viewControlPort.frameCp().getEditLine());
						progressModel.setValue(0);
						
						//���ý���ģ��
						viewControlPort.frameCp().startProgressMonitor(progressModel);
						
						//���������ʱ����
						CodeTimer cti = new CodeTimer();
						cti.start();
						
						//ѭ����ȡ����
						while(codeLoader.hasNext()){
							
							//����ֶ�ֹͣ������ֹ���̡�
							if(progressModel.isSuspend()){
								viewControlPort.frameCp().traceInConsole(
										programAttrSet.getStringField(KEY_COMMIT_SUSPEND));
								codeControl.setCodeEditMode(CodeEidtMode.EDIT);
								return true;
							}
							
							//����ѭ����ȡ���롣
							codes.add(codeLoader.loadNext());
							progressModel.setValue(codeLoader.currentValue());
							
						}
						
						//����ѭ����ֹͣ�������ʱ
						progressModel.end();
						cti.stop();
						
						//���ɴ�������
						final CodeSerial codeSerial = new ArrayCodeSerial(codes.toArray(new Code[0]));
						//�ύ����
						modelControlPort.frontCp().commitCodeSerial(codeSerial);
						viewControlPort.frameCp().knockForCommit();
						//���ɱ���
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_COMMIT_STATS),
								cti.getTime(Time.MS)
						);
						
						return true;
						
					}catch(Exception e){
						//TODO Ӧ���ø�����ķ�ʽ֪ͨ�û�
						e.printStackTrace();
						viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_COMMIT_FAIL));
						viewControlPort.frameCp().knockForMode(CodeEidtMode.EDIT);
						return true;
					}finally{
						viewControlPort.frameCp().unlockEdit();
						if(codeLoader != null){
							try {
								codeLoader.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}

				private boolean inner_save() {
					
					//�����ļ���ʼ����ȡǰ��ģ���е�����ļ���
					File srcFile = modelControlPort.frontCp().getLinkedFile();
					
					//�ж��ļ��Ƿ�Ϊnull������ǣ������û�ѯ���ļ���
					if(Objects.isNull(srcFile)){
						srcFile = viewControlPort.notifyCp().askSaveFile();
					}
					
					//�ٴ��ж�����ļ��������ȻΪnull�������û�ȡ����ѡ����Ӧ��ֱ�ӷ��ء�
					if(Objects.isNull(srcFile)) return true;
					
					//������ʱ�ļ����������ȱ�д����ʱ�ļ��У��������ʱ�ļ��滻Դ�ļ���
					File tarFile = new File(srcFile.getAbsolutePath() + ".temp");
					
					//��ȡ��Ҫ�ı���
					FileOutputStream out = null;
					CodePrinter codePrinter = null;
					CodeTimer cti = new CodeTimer();
					
					try{
						//������ʱ�ļ�
						FileFunction.createFileIfNotExists(tarFile);
						//��ȡ�����
						out = new FileOutputStream(tarFile);
						
						codePrinter = new StreamCodePrinter(
								modelControlPort.frontCp().getCodeSerial(), 
								out
						);
						
						//�����ʼ��Ϣ
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_SAVE_START),
								codePrinter.getTotleCode()
						);
						
						//���ɽ���ģ��
						ProgressModel progressModel = new DefaultProgressModel();
						progressModel.setIndeterminate(false);
						progressModel.setLabelText(programAttrSet.getStringField(KEY_SAVE_PROG));
						progressModel.setMaximum(codePrinter.getTotleCode());
						progressModel.setValue(0);
						
						//���ý���ģ��
						viewControlPort.frameCp().startProgressMonitor(progressModel);
						
						//���������ʱ����
						cti.start();
						
						//ѭ����ȡ����
						while(codePrinter.hasNext()){
							
							//����ֶ�ֹͣ������ֹ���̡�
							if(progressModel.isSuspend()){
								viewControlPort.frameCp().traceInConsole(
										programAttrSet.getStringField(KEY_SAVE_SUSPEND));
								return true;
							}
							
							//����ѭ����ȡ���롣
							codePrinter.printNext();
							progressModel.setValue(codePrinter.currentValue());
							
						}
						
						//����ѭ����ֹͣ�������ʱ
						progressModel.end();
						cti.stop();
						
					}catch(IOException e){
						//TODO Ӧ���ø�����ķ�ʽ֪ͨ�û���
						e.printStackTrace();
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_SAVE_FAIL));
						return true;
					}finally{
						if(Objects.nonNull(codePrinter)){
							try {
								codePrinter.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					
					try {
						//��Ŀ���ļ����Ƶ�Դ�ļ���
						FileFunction.FileCopy(srcFile, srcFile);
						//���ɱ���
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_SAVE_STATS),
								cti.getTime(Time.MS)
						);
						return false;
					} catch (IOException e) {
						//TODO Ӧ���ø�����ķ�ʽ֪ͨ�û���
						e.printStackTrace();
						return true;
					}
					
				}
			};

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.control.cps.FileCp#newFrontFile()
			 */
			@Override
			public void newFrontFile() {
				
				//������Ҫ�漰�ر��ļ�
				perhapsCloseFrontFile();
				
				//��̨������ָ������
				programControlPort.backInvoke(new NewFileRunnable(NccControlManager.this));
				
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.control.cps.FileCp#openNcFile(java.io.File)
			 */
			@Override
			public void openNcFile(File file) {
				Objects.requireNonNull(file);
				
				//�п���Ҫ�ر��ļ�
				perhapsCloseFrontFile();
				
				InputStream in = null;
				CodeLoader codeLoader = null;
				try{
					in = new FileInputStream(file);
					codeLoader = modelControlPort.explCp().newNcCodeLoader(in);
				}catch(IOException e){
					return;
				}
				
				//��̨������ָ������
				programControlPort.backInvoke(new OpenFileRunnable(NccControlManager.this, codeLoader, file));
			}

			@Override
			public void saveFrontFile() {
				// TODO Auto-generated method stub
				
			}
			
			public void openNcFile() {
				final boolean aFlag = true;
				final FileFilter[] fileFilters = new FileFilter[]{
					new FileNameExtensionFilter(programAttrSet.getStringField(StringFieldKey.CTRL_TEXTFILE), "txt"),
					new FileNameExtensionFilter(programAttrSet.getStringField(StringFieldKey.CTRL_NCFILE), "nc", "ptp", "mpf")
				};
				final File file = viewControlPort.notifyCp().askOpenFile(fileFilters, aFlag);
				if(file == null) return;
				openNcFile(file);
			}
			
		};

		private SaveCheckResult checkCommitCode(){
			if(!modelControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
			if(codeControl.getCodeEidtMode() != CodeEidtMode.EDIT) throw new IllegalStateException();
			
			if(!viewControlPort.frameCp().needCommit()){
				return SaveCheckResult.DEFAULT;
			}else{
				AnswerType type = viewControlPort.notifyCp().showConfirm(
						programAttrSet.getStringField(KEY_COMMIT_MESSAGE), 
						programAttrSet.getStringField(KEY_COMMIT_TITLE), 
						OptionType.YES_NO_CANCEL, 
						MessageType.QUESTION, null
				);
				
				return toResult(type);
			}
		}
		
		private SaveCheckResult checkSaveFrontFile(){
			if(!modelControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
			
			if(!modelControlPort.frontCp().needSave()){
				return SaveCheckResult.DEFAULT;
			}else{
				AnswerType type = viewControlPort.notifyCp().showConfirm(
						programAttrSet.getStringField(KEY_SAVE_MESSAGE), 
						programAttrSet.getStringField(KEY_SAVE_TITLE), 
						OptionType.YES_NO_CANCEL, 
						MessageType.QUESTION, null
				);
				
				return toResult(type);
			}
		}
		
		private SaveCheckResult toResult(AnswerType type){
			Objects.requireNonNull(type);
			
			switch (type) {
			case CANCEL:
				return new SaveCheckResult(false, true);
			case NO:
				return new SaveCheckResult(false, false);
			case YES:
				return new SaveCheckResult(true, false);
			default:
				return new SaveCheckResult(false, false);
		}
		}
		
	}
	
	private CodeControl codeControl;
	
	private class CodeControl{
		
		private final CodeCp codeCp = new CodeCp() {

			@Override
			public void attemptToggleMode(CodeEidtMode mode) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		private CodeEidtMode mode;
		
		public CodeControl() {
			mode = CodeEidtMode.INSPECT;
		}
		
		public CodeEidtMode getCodeEidtMode(){
			return this.mode;
		}
		
		public void setCodeEditMode(CodeEidtMode mode){
			this.mode = mode;
			viewControlPort.frameCp().knockForMode(mode);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.mvc.AbstractControlManager#getControlPort()
	 */
	@Override
	public NccControlPort getControlPort() {
		return controlPort;
	}

}
