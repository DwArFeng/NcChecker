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
import com.dwarfeng.dfunc.io.StringOutputStream;
import com.dwarfeng.dfunc.num.UnitTrans.Time;
import com.dwarfeng.dfunc.prog.mvc.AbstractControlManager;
import com.dwarfeng.ncc.control.cps.CodeCp;
import com.dwarfeng.ncc.control.cps.CodeCp.CodeEditMode;
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
 * 数控代码验证程序中的控制管理器，可提供控制端口。
 * @author DwArFeng
 * @since 1.8
 */
public final class NccControlManager extends AbstractControlManager<NccProgramControlPort, NccModelControlPort,
NccViewControlPort, NccControlPort, NccProgramAttrSet> {
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------

	private static final String KEY_NOTINIT = "控制管理器还未初始化。";
	private static final String KEY_INITED = "控制管理器已经初始化了。";
	
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
			if(obj == this) return true;
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
			
//			//由于界面支持该外观，所以不可能抛出异常。
//			try {
//				UIManager.setLookAndFeel(new NimbusLookAndFeel());
//			} catch (UnsupportedLookAndFeelException e) {}
			
			//自身初始化
			fileControl = new FileControl();
			codeControl = new CodeControl();
			
			//各控制站点初始化
			programControlPort.init();
			modelControlPort.init();
			viewControlPort.init();
			
			//读取配置文件
			MfAppearConfig mfac = null;
			try {
				mfac = programControlPort.configCp().loadMainFrameAppearConfig();
			} catch (NumberFormatException e) {
				mfac = MfAppearConfig.DEFAULT_CONFIG;
			} catch (IOException e) {
				mfac = MfAppearConfig.DEFAULT_CONFIG;
			}
			//TODO 设置为读取式。
			FrontConfig fc = FrontConfig.DEFAULT;
			
			//各个管理器初始化参数
			modelControlPort.frontCp().applyFontConfig(fc);
			modelControlPort.frontCp().setFrontCodeSerial(null,null,false);
			viewControlPort.frameCp().applyAppearanceConfig(mfac);
			viewControlPort.frameCp().noneFileMode(true);
			
			//显示程序主界面
			viewControlPort.frameCp().setVisible(true);
			
			//输出就绪文本
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
			
			programControlPort.backInvoke(exitProgramRunnable);
		}
		
		private final Runnable exitProgramRunnable = new Runnable() {
			
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				try{
					viewControlPort.frameCp().lockEdit();
					//执行内部方法。
					if(inner()) return;
				}catch(Exception e){
					e.printStackTrace();
					return;
				}finally{
					viewControlPort.frameCp().unlockEdit();
				}
				
				
				try{
					
					//隐藏界面
					viewControlPort.frameCp().setVisible(false);
					
					//保存各种配置
					MfAppearConfig config = viewControlPort.frameCp().getAppearanceConfig();
					try {
						programControlPort.configCp().saveMainFrameAppearConfig(config);
					} catch (IOException e) {
						e.printStackTrace();
					}
					//调用各个控制器的终结方法。
					
					//退出程序
					System.exit(0);
					return;
				}catch(Exception e){
					e.printStackTrace();
					return;
				}
				
			}

			private boolean inner() {
				//判断是否拥有前台文件，有的话需要保存等操作
				if(modelControlPort.frontCp().hasFrontCode()){
					//判断程序是否在代码编辑模式下
					if(codeControl.getCodeEidtMode() == CodeEditMode.EDIT){
						//询问代码是否需要提交
						SaveCheckResult commitResult = checkCommitCode();
						//判断是否中止操作
						if(commitResult.suspendFlag) return true;
						//判断是否要提交文件。
						if(commitResult.saveFlag){
							//执行提交文件的方法，并且查看过程是否被人为中止
							if(inner_commit()) return true;
						}
						
						inner_toggleInspect();
					}
					
					//询问文件是否要保存
					SaveCheckResult saveResult = checkSaveFrontFile();
					//首先判断是否终止操作
					if(saveResult.suspendFlag) return true;
					//判断是否要保存文件
					if(saveResult.saveFlag){
						//执行保存文件的方法，并且查看过程是否被人为中止。
						if(inner_save()) return true;
					}
					//关闭文件调度
					inner_close();
				}
				
				return false;
			}

			private void inner_toggleInspect() {
				//视图转换为编辑模式
				codeControl.setCodeEditMode(CodeEditMode.INSPECT);
				//渲染代码序列
				viewControlPort.frameCp().setCode(modelControlPort.frontCp().getFrontCodeSerial());
			}

			private void inner_close() {
				//进入查看状态
				codeControl.setCodeEditMode(CodeEditMode.INSPECT);
				//输出开始文本
				viewControlPort.frameCp().traceInConsole(
						programAttrSet.getStringField(KEY_CLOSE_START));
				//开始计时
				CodeTimer timer = new CodeTimer();
				timer.start();
				//清除前台
				modelControlPort.frontCp().setFrontCodeSerial(null, null, false);
				//撤下代码
				viewControlPort.frameCp().setCode(null);
				//进入无文件模式
				viewControlPort.frameCp().noneFileMode(true);
				//统计时间
				timer.stop();
				//输出统计文本
				viewControlPort.frameCp().traceInConsole(
						programAttrSet.getStringField(KEY_CLOSE_STATS), timer.getTime(Time.MS));
			}

			private boolean inner_save() {
				
				//保存文件开始，拉取前端模型中的相关文件。
				File srcFile = modelControlPort.frontCp().getLinkedFile();
				
				//判断文件是否为null，如果是，则向用户询问文件。
				if(Objects.isNull(srcFile)){
					srcFile = viewControlPort.notifyCp().askSaveFile();
				}
				
				//再次判断相关文件，如果仍然为null，代表用户取消了选择，则应该直接返回。
				if(Objects.isNull(srcFile)) return true;
				
				//设置临时文件，数据首先被写到临时文件中，随后用临时文件替换源文件。
				File tarFile = new File(srcFile.getAbsolutePath() + ".temp");
				
				//拉取必要的变量
				FileOutputStream out = null;
				CodePrinter codePrinter = null;
				CodeTimer cti = new CodeTimer();
				
				try{
					//创建临时文件
					FileFunction.createFileIfNotExists(tarFile);
					//拉取输出流
					out = new FileOutputStream(tarFile);
					
					codePrinter = new StreamCodePrinter(
							modelControlPort.frontCp().getCodeSerial(), 
							out
					);
					
					//输出开始信息
					viewControlPort.frameCp().traceInConsole(
							programAttrSet.getStringField(KEY_SAVE_START),
							codePrinter.getTotleCode()
					);
					
					//生成进度模型
					ProgressModel progressModel = new DefaultProgressModel();
					progressModel.setIndeterminate(false);
					progressModel.setLabelText(programAttrSet.getStringField(KEY_SAVE_PROG));
					progressModel.setMaximum(codePrinter.getTotleCode());
					progressModel.setValue(0);
					
					//设置进度模型
					viewControlPort.frameCp().startProgressMonitor(progressModel);
					
					//建立代码计时机制
					cti.start();
					
					//循环读取程序
					while(codePrinter.hasNext()){
						
						//如果手动停止，则终止进程。
						if(progressModel.isSuspend()){
							viewControlPort.frameCp().traceInConsole(
									programAttrSet.getStringField(KEY_SAVE_SUSPEND));
							return true;
						}
						
						//否则循环读取代码。
						codePrinter.printNext();
						progressModel.setValue(codePrinter.currentValue());
						
					}
					
					//结束循环并停止监视与计时
					progressModel.end();
					cti.stop();
					
				}catch(IOException e){
					//TODO 应该用更合理的方式通知用户。
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
					//将目标文件复制到源文件上
					FileFunction.FileCopy(tarFile, srcFile);
					tarFile.delete();
					//生成报告
					viewControlPort.frameCp().traceInConsole(
							programAttrSet.getStringField(KEY_SAVE_STATS),
							cti.getTime(Time.MS)
					);
					return false;
				} catch (IOException e) {
					//TODO 应该用更合理的方式通知用户。
					e.printStackTrace();
					return true;
				}
				
			}

			private SaveCheckResult checkSaveFrontFile() {
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

			private boolean inner_commit() {
				//拉取必要的变量
				InputStream in = new StringInputStream(viewControlPort.frameCp().getEditText());
				CodeLoader codeLoader = null;
				
				try{
					//初始必要的变量
					codeLoader = new StreamCodeLoader(in);
					final List<Code> codes = new ArrayList<Code>();
					
					//输出开始信息
					viewControlPort.frameCp().traceInConsole(
							programAttrSet.getStringField(KEY_COMMIT_START),
							viewControlPort.frameCp().getEditLine()
					);
					
					//生成进度模型
					ProgressModel progressModel = new DefaultProgressModel();
					progressModel.setIndeterminate(false);
					progressModel.setLabelText(programAttrSet.getStringField(KEY_COMMIT_PROG));
					progressModel.setMaximum(viewControlPort.frameCp().getEditLine());
					progressModel.setValue(0);
					
					//设置进度模型
					viewControlPort.frameCp().startProgressMonitor(progressModel);
					
					//建立代码计时机制
					CodeTimer cti = new CodeTimer();
					cti.start();
					
					//循环读取程序
					while(codeLoader.hasNext()){
						
						//如果手动停止，则终止进程。
						if(progressModel.isSuspend()){
							viewControlPort.frameCp().traceInConsole(
									programAttrSet.getStringField(KEY_COMMIT_SUSPEND));
							codeControl.setCodeEditMode(CodeEditMode.EDIT);
							return true;
						}
						
						//否则循环读取代码。
						codes.add(codeLoader.loadNext());
						progressModel.setValue(codeLoader.currentValue());
						
					}
					
					//结束循环并停止监视与计时
					progressModel.end();
					cti.stop();
					
					//生成代码序列
					final CodeSerial codeSerial = new ArrayCodeSerial(codes.toArray(new Code[0]));
					//提交代码
					modelControlPort.frontCp().commitCodeSerial(codeSerial);
					viewControlPort.frameCp().knockForCommit();
					//生成报告
					viewControlPort.frameCp().traceInConsole(
							programAttrSet.getStringField(KEY_COMMIT_STATS),
							cti.getTime(Time.MS)
					);
					
					return false;
					
				}catch(Exception e){
					//TODO 应该用更合理的方式通知用户
					e.printStackTrace();
					viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_COMMIT_FAIL));
					viewControlPort.frameCp().knockForMode(CodeEditMode.EDIT);
					return true;
				}finally{
					if(codeLoader != null){
						try {
							codeLoader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}

			private SaveCheckResult checkCommitCode() {
				if(!modelControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
				if(codeControl.getCodeEidtMode() != CodeEditMode.EDIT) throw new IllegalStateException();
				
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

			private SaveCheckResult toResult(AnswerType type) {
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
			
		};

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
	
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------

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
	
	private static final StringFieldKey KEY_NEWFILE_START = StringFieldKey.OUT_NEWFILE_START;
	private static final StringFieldKey KEY_NEWFILE_STATS = StringFieldKey.OUT_NEWFILE_STATS;
	
	private static final StringFieldKey KEY_OPEN_PROG = StringFieldKey.PROGRESS_FILE_NOWLOADING;
	private static final StringFieldKey KEY_OPEN_START = StringFieldKey.OUT_LOADFILE_START;
	private static final StringFieldKey KEY_OPEN_STATS= StringFieldKey.OUT_LOADFILE_STATS;
	private static final StringFieldKey KEY_OPEN_SUSPEND= StringFieldKey.OUT_LOADFILE_SUSPEND;
	private static final StringFieldKey KEY_OPEN_FAIL= StringFieldKey.OUT_LOADFILE_FAIL;
	
	private static final StringFieldKey KEY_CLOSE_START = StringFieldKey.OUT_CLOSE_START;
	private static final StringFieldKey KEY_CLOSE_STATS= StringFieldKey.OUT_CLOSE_STATS;
	
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
				
				//后台中运行指定方法
				programControlPort.backInvoke(closeFrontFileRunnable);
			}
			
			private final Runnable closeFrontFileRunnable = new Runnable() {
				
				/*
				 * (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					try{
						viewControlPort.frameCp().lockEdit();
						//执行内部方法。
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
					//判断是否具有前台文件 ，没有前台文件的话，显式抛出异常。
					if(!modelControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
					
					
					//判断程序是否在代码编辑模式下
					if(codeControl.getCodeEidtMode() == CodeEditMode.EDIT){
						//询问代码是否需要提交
						SaveCheckResult commitResult = checkCommitCode();
						//判断是否中止操作
						if(commitResult.suspendFlag) return;
						//判断是否要提交文件。
						if(commitResult.saveFlag){
							//执行提交文件的方法，并且查看过程是否被人为中止
							if(inner_commit()) return;
						}
					}
					
					//询问文件是否要保存
					SaveCheckResult saveResult = checkSaveFrontFile();
					//首先判断是否终止操作
					if(saveResult.suspendFlag) return;
					//判断是否要保存文件
					if(saveResult.saveFlag){
						//执行保存文件的方法，并且查看过程是否被人为中止。
						if(inner_save()) return;
					}
					//关闭文件调度
					inner_close();
				}

				private void inner_close() {
					//进入查看状态
					codeControl.setCodeEditMode(CodeEditMode.INSPECT);
					//输出开始文本
					viewControlPort.frameCp().traceInConsole(
							programAttrSet.getStringField(KEY_CLOSE_START));
					//开始计时
					CodeTimer timer = new CodeTimer();
					timer.start();
					//清除前台
					modelControlPort.frontCp().setFrontCodeSerial(null, null, false);
					//撤下代码
					viewControlPort.frameCp().setCode(null);
					//进入无文件模式
					viewControlPort.frameCp().noneFileMode(true);
					//统计时间
					timer.stop();
					//输出统计文本
					viewControlPort.frameCp().traceInConsole(
							programAttrSet.getStringField(KEY_CLOSE_STATS), timer.getTime(Time.MS));
				}

				private boolean inner_commit() {
					//拉取必要的变量
					InputStream in = new StringInputStream(viewControlPort.frameCp().getEditText());
					CodeLoader codeLoader = null;
					
					try{
						//初始必要的变量
						codeLoader = new StreamCodeLoader(in);
						final List<Code> codes = new ArrayList<Code>();
						
						//输出开始信息
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_COMMIT_START),
								viewControlPort.frameCp().getEditLine()
						);
						
						//生成进度模型
						ProgressModel progressModel = new DefaultProgressModel();
						progressModel.setIndeterminate(false);
						progressModel.setLabelText(programAttrSet.getStringField(KEY_COMMIT_PROG));
						progressModel.setMaximum(viewControlPort.frameCp().getEditLine());
						progressModel.setValue(0);
						
						//设置进度模型
						viewControlPort.frameCp().startProgressMonitor(progressModel);
						
						//建立代码计时机制
						CodeTimer cti = new CodeTimer();
						cti.start();
						
						//循环读取程序
						while(codeLoader.hasNext()){
							
							//如果手动停止，则终止进程。
							if(progressModel.isSuspend()){
								viewControlPort.frameCp().traceInConsole(
										programAttrSet.getStringField(KEY_COMMIT_SUSPEND));
								codeControl.setCodeEditMode(CodeEditMode.EDIT);
								return true;
							}
							
							//否则循环读取代码。
							codes.add(codeLoader.loadNext());
							progressModel.setValue(codeLoader.currentValue());
							
						}
						
						//结束循环并停止监视与计时
						progressModel.end();
						cti.stop();
						
						//生成代码序列
						final CodeSerial codeSerial = new ArrayCodeSerial(codes.toArray(new Code[0]));
						//提交代码
						modelControlPort.frontCp().commitCodeSerial(codeSerial);
						viewControlPort.frameCp().knockForCommit();
						//生成报告
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_COMMIT_STATS),
								cti.getTime(Time.MS)
						);
						
						return false;
						
					}catch(Exception e){
						//TODO 应该用更合理的方式通知用户
						e.printStackTrace();
						viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_COMMIT_FAIL));
						viewControlPort.frameCp().knockForMode(CodeEditMode.EDIT);
						return true;
					}finally{
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
					
					//保存文件开始，拉取前端模型中的相关文件。
					File srcFile = modelControlPort.frontCp().getLinkedFile();
					
					//判断文件是否为null，如果是，则向用户询问文件。
					if(Objects.isNull(srcFile)){
						srcFile = viewControlPort.notifyCp().askSaveFile();
					}
					
					//再次判断相关文件，如果仍然为null，代表用户取消了选择，则应该直接返回。
					if(Objects.isNull(srcFile)) return true;
					
					//设置临时文件，数据首先被写到临时文件中，随后用临时文件替换源文件。
					File tarFile = new File(srcFile.getAbsolutePath() + ".temp");
					
					//拉取必要的变量
					FileOutputStream out = null;
					CodePrinter codePrinter = null;
					CodeTimer cti = new CodeTimer();
					
					try{
						//创建临时文件
						FileFunction.createFileIfNotExists(tarFile);
						//拉取输出流
						out = new FileOutputStream(tarFile);
						
						codePrinter = new StreamCodePrinter(
								modelControlPort.frontCp().getCodeSerial(), 
								out
						);
						
						//输出开始信息
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_SAVE_START),
								codePrinter.getTotleCode()
						);
						
						//生成进度模型
						ProgressModel progressModel = new DefaultProgressModel();
						progressModel.setIndeterminate(false);
						progressModel.setLabelText(programAttrSet.getStringField(KEY_SAVE_PROG));
						progressModel.setMaximum(codePrinter.getTotleCode());
						progressModel.setValue(0);
						
						//设置进度模型
						viewControlPort.frameCp().startProgressMonitor(progressModel);
						
						//建立代码计时机制
						cti.start();
						
						//循环读取程序
						while(codePrinter.hasNext()){
							
							//如果手动停止，则终止进程。
							if(progressModel.isSuspend()){
								viewControlPort.frameCp().traceInConsole(
										programAttrSet.getStringField(KEY_SAVE_SUSPEND));
								return true;
							}
							
							//否则循环读取代码。
							codePrinter.printNext();
							progressModel.setValue(codePrinter.currentValue());
							
						}
						
						//结束循环并停止监视与计时
						progressModel.end();
						cti.stop();
						
					}catch(IOException e){
						//TODO 应该用更合理的方式通知用户。
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
						//将目标文件复制到源文件上
						FileFunction.FileCopy(tarFile, srcFile);
						tarFile.delete();
						//生成报告
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_SAVE_STATS),
								cti.getTime(Time.MS)
						);
						return false;
					} catch (IOException e) {
						//TODO 应该用更合理的方式通知用户。
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
				//后台中运行指定方法
				programControlPort.backInvoke(newFrontFileRunnable);
			}
			
			private final Runnable newFrontFileRunnable =  new Runnable() {
				
				/*
				 * (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					try{
						viewControlPort.frameCp().lockEdit();
						//执行内部方法。
						inner();
						return;
					}catch(Exception e){
						e.printStackTrace();
						return;
					}finally{
						viewControlPort.frameCp().unlockEdit();
					}
				}

				private void inner() {
					//判断是是否具有前台文件，如果有的话，需要关闭文件。
					if(modelControlPort.frontCp().hasFrontCode()){
						if(inner_close()) return;
					}
					inner_new();
				}

				private boolean inner_close() {
					//判断程序是否在代码编辑模式下
					if(codeControl.getCodeEidtMode() == CodeEditMode.EDIT){
						//询问代码是否需要提交
						SaveCheckResult commitResult = checkCommitCode();
						//判断是否中止操作
						if(commitResult.suspendFlag) return true;
						//判断是否要提交文件。
						if(commitResult.saveFlag){
							//执行提交文件的方法，并且查看过程是否被人为中止
							if(inner_commit()) return true;
						}
					}
					
					//询问文件是否要保存
					SaveCheckResult saveResult = checkSaveFrontFile();
					//首先判断是否终止操作
					if(saveResult.suspendFlag) return true;
					//判断是否要保存文件
					if(saveResult.saveFlag){
						//执行保存文件的方法，并且查看过程是否被人为中止。
						if(inner_save()) return true;
					}
					return false;
				}
				
				private boolean inner_save() {
					
					//保存文件开始，拉取前端模型中的相关文件。
					File srcFile = modelControlPort.frontCp().getLinkedFile();
					
					//判断文件是否为null，如果是，则向用户询问文件。
					if(Objects.isNull(srcFile)){
						srcFile = viewControlPort.notifyCp().askSaveFile();
					}
					
					//再次判断相关文件，如果仍然为null，代表用户取消了选择，则应该直接返回。
					if(Objects.isNull(srcFile)) return true;
					
					//设置临时文件，数据首先被写到临时文件中，随后用临时文件替换源文件。
					File tarFile = new File(srcFile.getAbsolutePath() + ".temp");
					
					//拉取必要的变量
					FileOutputStream out = null;
					CodePrinter codePrinter = null;
					CodeTimer cti = new CodeTimer();
					
					try{
						//创建临时文件
						FileFunction.createFileIfNotExists(tarFile);
						//拉取输出流
						out = new FileOutputStream(tarFile);
						
						codePrinter = new StreamCodePrinter(
								modelControlPort.frontCp().getCodeSerial(), 
								out
						);
						
						//输出开始信息
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_SAVE_START),
								codePrinter.getTotleCode()
						);
						
						//生成进度模型
						ProgressModel progressModel = new DefaultProgressModel();
						progressModel.setIndeterminate(false);
						progressModel.setLabelText(programAttrSet.getStringField(KEY_SAVE_PROG));
						progressModel.setMaximum(codePrinter.getTotleCode());
						progressModel.setValue(0);
						
						//设置进度模型
						viewControlPort.frameCp().startProgressMonitor(progressModel);
						
						//建立代码计时机制
						cti.start();
						
						//循环读取程序
						while(codePrinter.hasNext()){
							
							//如果手动停止，则终止进程。
							if(progressModel.isSuspend()){
								viewControlPort.frameCp().traceInConsole(
										programAttrSet.getStringField(KEY_SAVE_SUSPEND));
								return true;
							}
							
							//否则循环读取代码。
							codePrinter.printNext();
							progressModel.setValue(codePrinter.currentValue());
							
						}
						
						//结束循环并停止监视与计时
						progressModel.end();
						cti.stop();
						
					}catch(IOException e){
						//TODO 应该用更合理的方式通知用户。
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
						//将目标文件复制到源文件上
						FileFunction.FileCopy(tarFile, srcFile);
						tarFile.delete();
						//生成报告
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_SAVE_STATS),
								cti.getTime(Time.MS)
						);
						return false;
					} catch (IOException e) {
						//TODO 应该用更合理的方式通知用户。
						e.printStackTrace();
						return true;
					}
					
				}

				private void inner_new() {
					//输出信息
					viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_NEWFILE_START));
					
					CodeTimer timer = new CodeTimer();
					timer.start();
					//新建一个空的代码序列
					CodeSerial codeSerial = new ArrayCodeSerial(new Code[0]);
					timer.stop();
					
					codeControl.setCodeEditMode(CodeEditMode.INSPECT);
					
					//向前端模型中增加这个代码序列
					modelControlPort.frontCp().setFrontCodeSerial(codeSerial, null, true);
					
					//在视图中渲染代码
					viewControlPort.frameCp().setCode(modelControlPort.frontCp().getCodeSerial());
					viewControlPort.frameCp().noneFileMode(false);
					
					//生成统计报告
					viewControlPort.frameCp().traceInConsole(
							programAttrSet.getStringField(KEY_NEWFILE_STATS), timer.getTime(Time.MS));
				}
				
				private boolean inner_commit() {
					//拉取必要的变量
					InputStream in = new StringInputStream(viewControlPort.frameCp().getEditText());
					CodeLoader codeLoader = null;
					
					try{
						//初始必要的变量
						codeLoader = new StreamCodeLoader(in);
						final List<Code> codes = new ArrayList<Code>();
						
						//输出开始信息
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_COMMIT_START),
								viewControlPort.frameCp().getEditLine()
						);
						
						//生成进度模型
						ProgressModel progressModel = new DefaultProgressModel();
						progressModel.setIndeterminate(false);
						progressModel.setLabelText(programAttrSet.getStringField(KEY_COMMIT_PROG));
						progressModel.setMaximum(viewControlPort.frameCp().getEditLine());
						progressModel.setValue(0);
						
						//设置进度模型
						viewControlPort.frameCp().startProgressMonitor(progressModel);
						
						//建立代码计时机制
						CodeTimer cti = new CodeTimer();
						cti.start();
						
						//循环读取程序
						while(codeLoader.hasNext()){
							
							//如果手动停止，则终止进程。
							if(progressModel.isSuspend()){
								viewControlPort.frameCp().traceInConsole(
										programAttrSet.getStringField(KEY_COMMIT_SUSPEND));
								codeControl.setCodeEditMode(CodeEditMode.EDIT);
								return true;
							}
							
							//否则循环读取代码。
							codes.add(codeLoader.loadNext());
							progressModel.setValue(codeLoader.currentValue());
							
						}
						
						//结束循环并停止监视与计时
						progressModel.end();
						cti.stop();
						
						//生成代码序列
						final CodeSerial codeSerial = new ArrayCodeSerial(codes.toArray(new Code[0]));
						//提交代码
						modelControlPort.frontCp().commitCodeSerial(codeSerial);
						viewControlPort.frameCp().knockForCommit();
						//生成报告
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_COMMIT_STATS),
								cti.getTime(Time.MS)
						);
						
						return false;
						
					}catch(Exception e){
						//TODO 应该用更合理的方式通知用户
						e.printStackTrace();
						viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_COMMIT_FAIL));
						viewControlPort.frameCp().knockForMode(CodeEditMode.EDIT);
						return true;
					}finally{
						if(codeLoader != null){
							try {
								codeLoader.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
				
			};

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.control.cps.FileCp#openNcFile(java.io.File)
			 */
			@Override
			public void openNcFile() {
				//后台中运行指定方法
				programControlPort.backInvoke(openNcFileRunnable);
			}
			
			private final Runnable openNcFileRunnable = new Runnable() {
				
				/*
				 * (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					try{
						viewControlPort.frameCp().lockEdit();
						//执行内部方法。
						inner();
						return;
					}catch(Exception e){
						e.printStackTrace();
						return;
					}finally{
						viewControlPort.frameCp().unlockEdit();
					}
				}

				private void inner() {
					//判断是是否具有前台文件，如果有的话，需要关闭文件。
					if(modelControlPort.frontCp().hasFrontCode()){
						if(inner_close()) return;
					}
					//打开文件
					if(inner_open()) return;
					//在视图中渲染代码。
					viewControlPort.frameCp().setCode(modelControlPort.frontCp().getCodeSerial());
					viewControlPort.frameCp().noneFileMode(false);
				}

				private boolean inner_open() {
					
					//向用户询问文件。
					FileFilter[] fileFilters = new FileFilter[]{
							new FileNameExtensionFilter(programAttrSet.getStringField(StringFieldKey.CTRL_TEXTFILE), "txt"),
							new FileNameExtensionFilter(programAttrSet.getStringField(StringFieldKey.CTRL_NCFILE), "nc", "ptp", "mpf")
						};
					File file = viewControlPort.notifyCp().askOpenFile(fileFilters, true);
					
					//如果文件为null，则表示用户选择了取消
					if(Objects.isNull(file)) return true;
					
					//拉取必要变量
					FileInputStream in = null;
					CodeLoader codeLoader = null;
					
					try{
						
						in = new FileInputStream(file);
						codeLoader = new StreamCodeLoader(in);
						
						//输出基本信息
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_OPEN_START),
								file.getAbsolutePath()
						);
						
						//生成进度模型
						ProgressModel progressModel = new DefaultProgressModel();
						progressModel.setIndeterminate(true);
						progressModel.setLabelText(programAttrSet.getStringField(KEY_OPEN_PROG));
						
						//设置进度模型
						viewControlPort.frameCp().startProgressMonitor(progressModel);
						
						final List<Code> codes = new ArrayList<Code>();
						
						//建立代码计时机制
						CodeTimer cti = new CodeTimer();
						cti.start();
						
						//循环读取程序
						while(codeLoader.hasNext()){
							
							//如果手动停止，则终止进程。
							if(progressModel.isSuspend()){
								viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_OPEN_SUSPEND));
								return true;
							}
							
							//否则循环读取代码。
							codes.add(codeLoader.loadNext());
							progressModel.setLabelText(programAttrSet.getStringField(KEY_OPEN_PROG) + codeLoader.currentValue());
							
						}
						
						//结束循环并停止监视与计时
						progressModel.end();
						cti.stop();
						
						//生成代码实例
						CodeSerial codeList = new ArrayCodeSerial(codes.toArray(new Code[0]));
						//将代码设置为前端。
						modelControlPort.frontCp().setFrontCodeSerial(codeList, file, false);
						//生成报告
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_OPEN_STATS),
								codeList.getTotle(),
								cti.getTime(Time.MS)
						);
						
						return false;
						
					}catch(IOException e){
						//TODO 应该用更合理的方式通知用户。
						e.printStackTrace();
						viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_OPEN_FAIL));
						return true;
					}finally{
						if (Objects.nonNull(codeLoader)) {
							try{
								codeLoader.close();
							}catch(IOException e){
								e.printStackTrace();
							}
						}
					}
				}

				private boolean inner_close() {
					//判断程序是否在代码编辑模式下
					if(codeControl.getCodeEidtMode() == CodeEditMode.EDIT){
						//询问代码是否需要提交
						SaveCheckResult commitResult = checkCommitCode();
						//判断是否中止操作
						if(commitResult.suspendFlag) return true;
						//判断是否要提交文件。
						if(commitResult.saveFlag){
							//执行提交文件的方法，并且查看过程是否被人为中止
							if(inner_commit()) return true;
						}
						//切换到代码查看模式
						inner_toggleInspect();
					}
					
					//询问文件是否要保存
					SaveCheckResult saveResult = checkSaveFrontFile();
					//首先判断是否终止操作
					if(saveResult.suspendFlag) return true;
					//判断是否要保存文件
					if(saveResult.saveFlag){
						//执行保存文件的方法，并且查看过程是否被人为中止。
						if(inner_save()) return true;
					}
					return false;
				}

				private void inner_toggleInspect() {
					//视图转换为编辑模式
					codeControl.setCodeEditMode(CodeEditMode.INSPECT);
					//渲染代码序列
					viewControlPort.frameCp().setCode(modelControlPort.frontCp().getFrontCodeSerial());
				}

				private boolean inner_commit() {
					//拉取必要的变量
					InputStream in = new StringInputStream(viewControlPort.frameCp().getEditText());
					CodeLoader codeLoader = null;
					
					try{
						//初始必要的变量
						codeLoader = new StreamCodeLoader(in);
						final List<Code> codes = new ArrayList<Code>();
						
						//输出开始信息
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_COMMIT_START),
								viewControlPort.frameCp().getEditLine()
						);
						
						//生成进度模型
						ProgressModel progressModel = new DefaultProgressModel();
						progressModel.setIndeterminate(false);
						progressModel.setLabelText(programAttrSet.getStringField(KEY_COMMIT_PROG));
						progressModel.setMaximum(viewControlPort.frameCp().getEditLine());
						progressModel.setValue(0);
						
						//设置进度模型
						viewControlPort.frameCp().startProgressMonitor(progressModel);
						
						//建立代码计时机制
						CodeTimer cti = new CodeTimer();
						cti.start();
						
						//循环读取程序
						while(codeLoader.hasNext()){
							
							//如果手动停止，则终止进程。
							if(progressModel.isSuspend()){
								viewControlPort.frameCp().traceInConsole(
										programAttrSet.getStringField(KEY_COMMIT_SUSPEND));
								codeControl.setCodeEditMode(CodeEditMode.EDIT);
								return true;
							}
							
							//否则循环读取代码。
							codes.add(codeLoader.loadNext());
							progressModel.setValue(codeLoader.currentValue());
							
						}
						
						//结束循环并停止监视与计时
						progressModel.end();
						cti.stop();
						
						//生成代码序列
						final CodeSerial codeSerial = new ArrayCodeSerial(codes.toArray(new Code[0]));
						//提交代码
						modelControlPort.frontCp().commitCodeSerial(codeSerial);
						viewControlPort.frameCp().knockForCommit();
						//生成报告
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_COMMIT_STATS),
								cti.getTime(Time.MS)
						);
						
						return false;
						
					}catch(Exception e){
						//TODO 应该用更合理的方式通知用户
						e.printStackTrace();
						viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_COMMIT_FAIL));
						viewControlPort.frameCp().knockForMode(CodeEditMode.EDIT);
						return true;
					}finally{
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
					
					//保存文件开始，拉取前端模型中的相关文件。
					File srcFile = modelControlPort.frontCp().getLinkedFile();
					
					//判断文件是否为null，如果是，则向用户询问文件。
					if(Objects.isNull(srcFile)){
						srcFile = viewControlPort.notifyCp().askSaveFile();
					}
					
					//再次判断相关文件，如果仍然为null，代表用户取消了选择，则应该直接返回。
					if(Objects.isNull(srcFile)) return true;
					
					//设置临时文件，数据首先被写到临时文件中，随后用临时文件替换源文件。
					File tarFile = new File(srcFile.getAbsolutePath() + ".temp");
					
					//拉取必要的变量
					FileOutputStream out = null;
					CodePrinter codePrinter = null;
					CodeTimer cti = new CodeTimer();
					
					try{
						//创建临时文件
						FileFunction.createFileIfNotExists(tarFile);
						//拉取输出流
						out = new FileOutputStream(tarFile);
						
						codePrinter = new StreamCodePrinter(
								modelControlPort.frontCp().getCodeSerial(), 
								out
						);
						
						//输出开始信息
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_SAVE_START),
								codePrinter.getTotleCode()
						);
						
						//生成进度模型
						ProgressModel progressModel = new DefaultProgressModel();
						progressModel.setIndeterminate(false);
						progressModel.setLabelText(programAttrSet.getStringField(KEY_SAVE_PROG));
						progressModel.setMaximum(codePrinter.getTotleCode());
						progressModel.setValue(0);
						
						//设置进度模型
						viewControlPort.frameCp().startProgressMonitor(progressModel);
						
						//建立代码计时机制
						cti.start();
						
						//循环读取程序
						while(codePrinter.hasNext()){
							
							//如果手动停止，则终止进程。
							if(progressModel.isSuspend()){
								viewControlPort.frameCp().traceInConsole(
										programAttrSet.getStringField(KEY_SAVE_SUSPEND));
								return true;
							}
							
							//否则循环读取代码。
							codePrinter.printNext();
							progressModel.setValue(codePrinter.currentValue());
							
						}
						
						//结束循环并停止监视与计时
						progressModel.end();
						cti.stop();
						
					}catch(IOException e){
						//TODO 应该用更合理的方式通知用户。
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
						//将目标文件复制到源文件上
						FileFunction.FileCopy(tarFile, srcFile);
						tarFile.delete();
						//生成报告
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_SAVE_STATS),
								cti.getTime(Time.MS)
						);
						return false;
					} catch (IOException e) {
						//TODO 应该用更合理的方式通知用户。
						e.printStackTrace();
						return true;
					}
					
				}
			};

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.control.cps.FileCp#saveFrontFile()
			 */
			@Override
			public void saveFrontFile() {
				if(!modelControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
				
				//后台中运行指定方法
				programControlPort.backInvoke(saveFrontFileRunnable);
			}
			
			private Runnable saveFrontFileRunnable = new Runnable() {
				
				/*
				 * (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					try{
						viewControlPort.frameCp().lockEdit();
						//执行内部方法。
						inner();
						return;
					}catch(Exception e){
						e.printStackTrace();
						return;
					}finally{
						viewControlPort.frameCp().unlockEdit();
					}
				}

				private void inner() {
					//判断程序是否在代码编辑模式下
					if(codeControl.getCodeEidtMode() == CodeEditMode.EDIT){
						//询问代码是否需要提交
						SaveCheckResult commitResult = checkCommitCode();
						//判断是否中止操作
						if(commitResult.suspendFlag) return;
						//判断是否要提交文件。
						if(commitResult.saveFlag){
							//执行提交文件的方法，并且查看过程是否被人为中止
							if(inner_commit()) return;
						}
						//切换到代码查看模式
						inner_toggleInspect();
					}
					
					//执行保存文件的方法，并且查看过程是否被人为中止。
					if(inner_save()) return;
					
					//通知前台模型文件已经保存
					modelControlPort.frontCp().knockForSave();
				}

				private boolean inner_save() {
					
					//保存文件开始，拉取前端模型中的相关文件。
					File srcFile = modelControlPort.frontCp().getLinkedFile();
					
					//判断文件是否为null，如果是，则向用户询问文件。
					if(Objects.isNull(srcFile)){
						srcFile = viewControlPort.notifyCp().askSaveFile();
					}
					
					//再次判断相关文件，如果仍然为null，代表用户取消了选择，则应该直接返回。
					if(Objects.isNull(srcFile)) return true;
					
					//链接文件与前台模型
					modelControlPort.frontCp().linkFile(srcFile);
					
					//设置临时文件，数据首先被写到临时文件中，随后用临时文件替换源文件。
					File tarFile = new File(srcFile.getAbsolutePath() + ".temp");
					
					//拉取必要的变量
					FileOutputStream out = null;
					CodePrinter codePrinter = null;
					CodeTimer cti = new CodeTimer();
					
					try{
						//创建临时文件
						FileFunction.createFileIfNotExists(tarFile);
						//拉取输出流
						out = new FileOutputStream(tarFile);
						
						codePrinter = new StreamCodePrinter(
								modelControlPort.frontCp().getCodeSerial(), 
								out
						);
						
						//输出开始信息
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_SAVE_START),
								codePrinter.getTotleCode()
						);
						
						//生成进度模型
						ProgressModel progressModel = new DefaultProgressModel();
						progressModel.setIndeterminate(false);
						progressModel.setLabelText(programAttrSet.getStringField(KEY_SAVE_PROG));
						progressModel.setMaximum(codePrinter.getTotleCode());
						progressModel.setValue(0);
						
						//设置进度模型
						viewControlPort.frameCp().startProgressMonitor(progressModel);
						
						//建立代码计时机制
						cti.start();
						
						//循环读取程序
						while(codePrinter.hasNext()){
							
							//如果手动停止，则终止进程。
							if(progressModel.isSuspend()){
								viewControlPort.frameCp().traceInConsole(
										programAttrSet.getStringField(KEY_SAVE_SUSPEND));
								return true;
							}
							
							//否则循环读取代码。
							codePrinter.printNext();
							progressModel.setValue(codePrinter.currentValue());
							
						}
						
						//结束循环并停止监视与计时
						progressModel.end();
						cti.stop();
						
					}catch(IOException e){
						//TODO 应该用更合理的方式通知用户。
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
						//将目标文件复制到源文件上
						FileFunction.FileCopy(tarFile, srcFile);
						tarFile.delete();
						//生成报告
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_SAVE_STATS),
								cti.getTime(Time.MS)
						);
						return false;
					} catch (IOException e) {
						//TODO 应该用更合理的方式通知用户。
						e.printStackTrace();
						return true;
					}
					
				}

				private void inner_toggleInspect() {
					//视图转换为编辑模式
					codeControl.setCodeEditMode(CodeEditMode.INSPECT);
					//渲染代码序列
					viewControlPort.frameCp().setCode(modelControlPort.frontCp().getFrontCodeSerial());
				}

				private boolean inner_commit() {
					//拉取必要的变量
					InputStream in = new StringInputStream(viewControlPort.frameCp().getEditText());
					CodeLoader codeLoader = null;
					
					try{
						//初始必要的变量
						codeLoader = new StreamCodeLoader(in);
						final List<Code> codes = new ArrayList<Code>();
						
						//输出开始信息
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_COMMIT_START),
								viewControlPort.frameCp().getEditLine()
						);
						
						//生成进度模型
						ProgressModel progressModel = new DefaultProgressModel();
						progressModel.setIndeterminate(false);
						progressModel.setLabelText(programAttrSet.getStringField(KEY_COMMIT_PROG));
						progressModel.setMaximum(viewControlPort.frameCp().getEditLine());
						progressModel.setValue(0);
						
						//设置进度模型
						viewControlPort.frameCp().startProgressMonitor(progressModel);
						
						//建立代码计时机制
						CodeTimer cti = new CodeTimer();
						cti.start();
						
						//循环读取程序
						while(codeLoader.hasNext()){
							
							//如果手动停止，则终止进程。
							if(progressModel.isSuspend()){
								viewControlPort.frameCp().traceInConsole(
										programAttrSet.getStringField(KEY_COMMIT_SUSPEND));
								codeControl.setCodeEditMode(CodeEditMode.EDIT);
								return true;
							}
							
							//否则循环读取代码。
							codes.add(codeLoader.loadNext());
							progressModel.setValue(codeLoader.currentValue());
							
						}
						
						//结束循环并停止监视与计时
						progressModel.end();
						cti.stop();
						
						//生成代码序列
						final CodeSerial codeSerial = new ArrayCodeSerial(codes.toArray(new Code[0]));
						//提交代码
						modelControlPort.frontCp().commitCodeSerial(codeSerial);
						viewControlPort.frameCp().knockForCommit();
						//生成报告
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_COMMIT_STATS),
								cti.getTime(Time.MS)
						);
						
						return false;
						
					}catch(Exception e){
						//TODO 应该用更合理的方式通知用户
						e.printStackTrace();
						viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_COMMIT_FAIL));
						viewControlPort.frameCp().knockForMode(CodeEditMode.EDIT);
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
			};

			@Override
			public void saveAsFrontFile() {
				if(!modelControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
				
				//后台中运行指定方法
				programControlPort.backInvoke(saveAsFrontFileRunnable);
			}
			
			private final Runnable saveAsFrontFileRunnable = new Runnable() {
				
				/*
				 * (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					try{
						viewControlPort.frameCp().lockEdit();
						//执行内部方法。
						inner();
						return;
					}catch(Exception e){
						e.printStackTrace();
						return;
					}finally{
						viewControlPort.frameCp().unlockEdit();
					}
				}
				
				private void inner() {
					//判断程序是否在代码编辑模式下
					if(codeControl.getCodeEidtMode() == CodeEditMode.EDIT){
						//询问代码是否需要提交
						SaveCheckResult commitResult = checkCommitCode();
						//判断是否中止操作
						if(commitResult.suspendFlag) return;
						//判断是否要提交文件。
						if(commitResult.saveFlag){
							//执行提交文件的方法，并且查看过程是否被人为中止
							if(inner_commit()) return;
						}
						//切换到代码查看模式
						inner_toggleInspect();
					}
					
					//执行保存文件的方法，并且查看过程是否被人为中止。
					if(inner_save()) return;
					
					//通知前台模型文件已经保存
					modelControlPort.frontCp().knockForSave();
				}

				private boolean inner_save() {
					
					//询问用户文件
					File srcFile = viewControlPort.notifyCp().askSaveFile();
					
					//如果文件为null，代表用户取消了选择，则应该直接返回。
					if(Objects.isNull(srcFile)) return true;
					
					//链接文件与前台模型
					modelControlPort.frontCp().linkFile(srcFile);
					
					//设置临时文件，数据首先被写到临时文件中，随后用临时文件替换源文件。
					File tarFile = new File(srcFile.getAbsolutePath() + ".temp");
					
					//拉取必要的变量
					FileOutputStream out = null;
					CodePrinter codePrinter = null;
					CodeTimer cti = new CodeTimer();
					
					try{
						//创建临时文件
						FileFunction.createFileIfNotExists(tarFile);
						//拉取输出流
						out = new FileOutputStream(tarFile);
						
						codePrinter = new StreamCodePrinter(
								modelControlPort.frontCp().getCodeSerial(), 
								out
						);
						
						//输出开始信息
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_SAVE_START),
								codePrinter.getTotleCode()
						);
						
						//生成进度模型
						ProgressModel progressModel = new DefaultProgressModel();
						progressModel.setIndeterminate(false);
						progressModel.setLabelText(programAttrSet.getStringField(KEY_SAVE_PROG));
						progressModel.setMaximum(codePrinter.getTotleCode());
						progressModel.setValue(0);
						
						//设置进度模型
						viewControlPort.frameCp().startProgressMonitor(progressModel);
						
						//建立代码计时机制
						cti.start();
						
						//循环读取程序
						while(codePrinter.hasNext()){
							
							//如果手动停止，则终止进程。
							if(progressModel.isSuspend()){
								viewControlPort.frameCp().traceInConsole(
										programAttrSet.getStringField(KEY_SAVE_SUSPEND));
								return true;
							}
							
							//否则循环读取代码。
							codePrinter.printNext();
							progressModel.setValue(codePrinter.currentValue());
							
						}
						
						//结束循环并停止监视与计时
						progressModel.end();
						cti.stop();
						
					}catch(IOException e){
						//TODO 应该用更合理的方式通知用户。
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
						//将目标文件复制到源文件上
						FileFunction.FileCopy(tarFile, srcFile);
						tarFile.delete();
						//生成报告
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_SAVE_STATS),
								cti.getTime(Time.MS)
						);
						return false;
					} catch (IOException e) {
						//TODO 应该用更合理的方式通知用户。
						e.printStackTrace();
						return true;
					}
					
				}

				private void inner_toggleInspect() {
					//视图转换为编辑模式
					codeControl.setCodeEditMode(CodeEditMode.INSPECT);
					//渲染代码序列
					viewControlPort.frameCp().setCode(modelControlPort.frontCp().getFrontCodeSerial());
				}

				private boolean inner_commit() {
					//拉取必要的变量
					InputStream in = new StringInputStream(viewControlPort.frameCp().getEditText());
					CodeLoader codeLoader = null;
					
					try{
						//初始必要的变量
						codeLoader = new StreamCodeLoader(in);
						final List<Code> codes = new ArrayList<Code>();
						
						//输出开始信息
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_COMMIT_START),
								viewControlPort.frameCp().getEditLine()
						);
						
						//生成进度模型
						ProgressModel progressModel = new DefaultProgressModel();
						progressModel.setIndeterminate(false);
						progressModel.setLabelText(programAttrSet.getStringField(KEY_COMMIT_PROG));
						progressModel.setMaximum(viewControlPort.frameCp().getEditLine());
						progressModel.setValue(0);
						
						//设置进度模型
						viewControlPort.frameCp().startProgressMonitor(progressModel);
						
						//建立代码计时机制
						CodeTimer cti = new CodeTimer();
						cti.start();
						
						//循环读取程序
						while(codeLoader.hasNext()){
							
							//如果手动停止，则终止进程。
							if(progressModel.isSuspend()){
								viewControlPort.frameCp().traceInConsole(
										programAttrSet.getStringField(KEY_COMMIT_SUSPEND));
								codeControl.setCodeEditMode(CodeEditMode.EDIT);
								return true;
							}
							
							//否则循环读取代码。
							codes.add(codeLoader.loadNext());
							progressModel.setValue(codeLoader.currentValue());
							
						}
						
						//结束循环并停止监视与计时
						progressModel.end();
						cti.stop();
						
						//生成代码序列
						final CodeSerial codeSerial = new ArrayCodeSerial(codes.toArray(new Code[0]));
						//提交代码
						modelControlPort.frontCp().commitCodeSerial(codeSerial);
						viewControlPort.frameCp().knockForCommit();
						//生成报告
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_COMMIT_STATS),
								cti.getTime(Time.MS)
						);
						
						return false;
						
					}catch(Exception e){
						//TODO 应该用更合理的方式通知用户
						e.printStackTrace();
						viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_COMMIT_FAIL));
						viewControlPort.frameCp().knockForMode(CodeEditMode.EDIT);
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
			};
			
		};

		private SaveCheckResult checkCommitCode(){
			if(!modelControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
			if(codeControl.getCodeEidtMode() != CodeEditMode.EDIT) throw new IllegalStateException();
			
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
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------

	private static final StringFieldKey KEY_EDIT_PROG = StringFieldKey.PROGRESS_CODE_EDIT;
	private static final StringFieldKey KEY_EDIT_START = StringFieldKey.OUT_EDIT_START;
	private static final StringFieldKey KEY_EDIT_STATS= StringFieldKey.OUT_EDIT_STATS;
	private static final StringFieldKey KEY_EDIT_SUSPEND= StringFieldKey.OUT_EDIT_SUSPEND;
	private static final StringFieldKey KEY_EDIT_FAIL= StringFieldKey.OUT_EDIT_FAIL;
	
	//------------------------------------------------------------------------------------------------
	
	private CodeControl codeControl;
	
	private class CodeControl{
		
		private final CodeCp codeCp = new CodeCp() {

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.control.cps.CodeCp#attemptToggleMode(com.dwarfeng.ncc.control.cps.CodeCp.CodeEidtMode)
			 */
			@Override
			public void attemptToggleMode(CodeEditMode mode) {
				Objects.requireNonNull(mode);
				
				switch (mode) {
					case EDIT:
						attemptToggleEdit();
						break;
					case INSPECT:
						attemptToggleInspect();
						break;
				}
			}

			private void attemptToggleInspect() {
				if(getCodeEidtMode() == CodeEditMode.INSPECT) return;
				
				programControlPort.backInvoke(attemptTogleInspectRunnable);
			}
			
			private final Runnable attemptTogleInspectRunnable = new Runnable() {
				
				/*
				 * (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					try{
						viewControlPort.frameCp().lockEdit();
						//执行内部方法。
						inner();
						return;
					}catch(Exception e){
						e.printStackTrace();
						return;
					}finally{
						viewControlPort.frameCp().unlockEdit();
					}
				}

				private void inner() {
					//如果没有前台文件，显式的抛出异常。
					if(!modelControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
					
					//询问代码是否需要提交
					SaveCheckResult commitResult = checkCommitCode();
					
					//判断是否中止操作
					if(commitResult.suspendFlag) return;
					//判断是否要提交文件。
					if(commitResult.saveFlag){
						//执行提交文件的方法，并且查看过程是否被人为中止
						if(inner_commit()) return;
					}
					//切换到代码查看模式
					inner_toggleInspect();
				}

				private void inner_toggleInspect() {
					//视图转换为编辑模式
					codeControl.setCodeEditMode(CodeEditMode.INSPECT);
					//渲染代码序列
					viewControlPort.frameCp().setCode(modelControlPort.frontCp().getFrontCodeSerial());
				}

				private boolean inner_commit() {
					//拉取必要的变量
					InputStream in = new StringInputStream(viewControlPort.frameCp().getEditText());
					CodeLoader codeLoader = null;
					
					try{
						//初始必要的变量
						codeLoader = new StreamCodeLoader(in);
						final List<Code> codes = new ArrayList<Code>();
						
						//输出开始信息
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_COMMIT_START),
								viewControlPort.frameCp().getEditLine()
						);
						
						//生成进度模型
						ProgressModel progressModel = new DefaultProgressModel();
						progressModel.setIndeterminate(false);
						progressModel.setLabelText(programAttrSet.getStringField(KEY_COMMIT_PROG));
						progressModel.setMaximum(viewControlPort.frameCp().getEditLine());
						progressModel.setValue(0);
						
						//设置进度模型
						viewControlPort.frameCp().startProgressMonitor(progressModel);
						
						//建立代码计时机制
						CodeTimer cti = new CodeTimer();
						cti.start();
						
						//循环读取程序
						while(codeLoader.hasNext()){
							
							//如果手动停止，则终止进程。
							if(progressModel.isSuspend()){
								viewControlPort.frameCp().traceInConsole(
										programAttrSet.getStringField(KEY_COMMIT_SUSPEND));
								codeControl.setCodeEditMode(CodeEditMode.EDIT);
								return true;
							}
							
							//否则循环读取代码。
							codes.add(codeLoader.loadNext());
							progressModel.setValue(codeLoader.currentValue());
							
						}
						
						//结束循环并停止监视与计时
						progressModel.end();
						cti.stop();
						
						//生成代码序列
						final CodeSerial codeSerial = new ArrayCodeSerial(codes.toArray(new Code[0]));
						//提交代码
						modelControlPort.frontCp().commitCodeSerial(codeSerial);
						viewControlPort.frameCp().knockForCommit();
						//生成报告
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_COMMIT_STATS),
								cti.getTime(Time.MS)
						);
						
						return false;
						
					}catch(Exception e){
						//TODO 应该用更合理的方式通知用户
						e.printStackTrace();
						viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_COMMIT_FAIL));
						setCodeEditMode(CodeEditMode.EDIT);
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
			};

			private void attemptToggleEdit() {
				if(getCodeEidtMode() == CodeEditMode.EDIT) return;
				
				programControlPort.backInvoke(attemptToggleEditRunnable);
			}
			
			private final Runnable attemptToggleEditRunnable = new Runnable() {
				
				/*
				 * (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					try{
						viewControlPort.frameCp().lockEdit();
						//执行内部方法。
						inner();
						return;
					}catch(Exception e){
						e.printStackTrace();
						return;
					}finally{
						viewControlPort.frameCp().unlockEdit();
					}
				}

				private void inner() {
					//如果没有前台文件，显式的抛出异常。
					if(!modelControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
					
					StringOutputStream out = null;
					CodePrinter codePrinter = null;
					
					try{
						//初始必要的变量
						CodeSerial codeSerial = modelControlPort.frontCp().getFrontCodeSerial();
						out = new StringOutputStream();
						
						codePrinter = new StreamCodePrinter(codeSerial, out);
						
						//输出开始信息
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_EDIT_START),
								codeSerial.getTotle()
						);
						
						//生成进度模型
						ProgressModel progressModel = new DefaultProgressModel();
						progressModel.setIndeterminate(false);
						progressModel.setLabelText(programAttrSet.getStringField(KEY_EDIT_PROG));
						progressModel.setMaximum(codePrinter.getTotleCode());
						progressModel.setValue(0);
						
						//设置进度模型
						viewControlPort.frameCp().startProgressMonitor(progressModel);
						
						//建立代码计时机制
						CodeTimer cti = new CodeTimer();
						cti.start();
						
						//循环读取程序
						while(codePrinter.hasNext()){
							
							//如果手动停止，则终止进程。
							if(progressModel.isSuspend()){
								viewControlPort.frameCp().traceInConsole(
										programAttrSet.getStringField(KEY_EDIT_SUSPEND));
								viewControlPort.frameCp().knockForMode(CodeEditMode.INSPECT);
								return;
							}
							
							//否则循环读取代码。
							codePrinter.printNext();
							progressModel.setValue(codePrinter.currentValue());
							
						}
						
						//结束循环并停止监视与计时
						progressModel.end();
						cti.stop();
						
						//生成文本
						out.flush();
						String text = out.toString();
						
						//视图转换为编辑模式
						setCodeEditMode(CodeEditMode.EDIT);
						//渲染文本
						viewControlPort.frameCp().setEditText(text);
						//生成报告
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_EDIT_STATS),
								cti.getTime(Time.MS)
						);
						
					}catch(Exception e){
						e.printStackTrace();
						viewControlPort.frameCp().traceInConsole(
								programAttrSet.getStringField(KEY_EDIT_FAIL));
						setCodeEditMode(CodeEditMode.INSPECT);
						return;
					}finally{
						viewControlPort.frameCp().unlockEdit();
						if(codePrinter != null){
							try {
								codePrinter.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			};

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.control.cps.CodeCp#undo()
			 */
			@Override
			public void undo() {
				if(mode != CodeEditMode.EDIT) throw new IllegalStateException();
				viewControlPort.frameCp().getUndoManager().undo();
				viewControlPort.frameCp().knockForUndoOrRedo();
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.ncc.control.cps.CodeCp#redo()
			 */
			@Override
			public void redo() {
				if(mode != CodeEditMode.EDIT) throw new IllegalStateException();
				viewControlPort.frameCp().getUndoManager().redo();
				viewControlPort.frameCp().knockForUndoOrRedo();
			}

			@Override
			public void commit() {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		private CodeEditMode mode;
		
		public CodeControl() {
			mode = CodeEditMode.INSPECT;
		}
		
		public CodeEditMode getCodeEidtMode(){
			return this.mode;
		}
		
		public void setCodeEditMode(CodeEditMode mode){
			this.mode = mode;
			viewControlPort.frameCp().knockForMode(mode);
		}
		
		private SaveCheckResult checkCommitCode(){
			if(!modelControlPort.frontCp().hasFrontCode()) throw new NullPointerException();
			if(codeControl.getCodeEidtMode() != CodeEditMode.EDIT) throw new IllegalStateException();
			
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dfunc.prog.mvc.AbstractControlManager#getControlPort()
	 */
	@Override
	public NccControlPort getControlPort() {
		return controlPort;
	}

}
