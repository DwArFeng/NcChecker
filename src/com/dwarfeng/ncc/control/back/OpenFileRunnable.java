package com.dwarfeng.ncc.control.back;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.dwarfeng.dfunc.dt.CodeTimer;
import com.dwarfeng.dfunc.num.UnitTrans.Time;
import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.model.front.CodeLoader;
import com.dwarfeng.ncc.model.nc.ArrayCodeSerial;
import com.dwarfeng.ncc.model.nc.Code;
import com.dwarfeng.ncc.model.nc.CodeSerial;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.gui.DefaultProgressModel;
import com.dwarfeng.ncc.view.gui.ProgressModel;

/**
 * 打开文件所需要的Runnable。
 * @author DwArFeng
 * @since 1.8
 */
public final class OpenFileRunnable extends AbstractCmr implements Runnable {

	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final StringFieldKey KEY_LOADING = StringFieldKey.PROGRESS_FILE_NOWLOADING;
	private static final StringFieldKey KEY_START = StringFieldKey.OUT_LOADFILE_START;
	private static final StringFieldKey KEY_STATS= StringFieldKey.OUT_LOADFILE_STATS;
	private static final StringFieldKey KEY_SUSPEND= StringFieldKey.OUT_LOADFILE_SUSPEND;
	private static final StringFieldKey KEY_FAIL= StringFieldKey.OUT_LOADFILE_FAIL;
	
	//------------------------------------------------------------------------------------------------
	
	private final CodeLoader codeLoader;
	private final File file;
	
	/**
	 * 生成一个指定的打开文件可运行对象。
	 * @param controlManager 指定的程序管理器。
	 * @param codeLoader NC代码读取器。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public OpenFileRunnable(NccControlManager controlManager, CodeLoader codeLoader, File file) {
		super(controlManager);
		Objects.requireNonNull(codeLoader);
		this.codeLoader = codeLoader;
		this.file = file;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		//界面编辑锁定。
		viewControlPort.frameCp().lockEdit();
		
		try{
			//输出基本信息
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_START),
					file.getAbsolutePath()
			);
			
			//生成进度模型
			ProgressModel progressModel = new DefaultProgressModel();
			progressModel.setIndeterminate(true);
			progressModel.setLabelText(programAttrSet.getStringField(KEY_LOADING));
			
			//设置进度模型
			viewControlPort.frameCp().startProgressMonitor(progressModel);
			
			final List<Code> codes = new ArrayList<Code>();
			
			//建立代码计时机制
			CodeTimer cti = new CodeTimer();
			cti.start();
			
			//循环读取程序
			for(;codeLoader.hasNext();){
				
				//如果手动停止，则终止进程。
				if(progressModel.isSuspend()){
					viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_SUSPEND));
					return;
				}
				
				//否则循环读取代码。
				codes.add(codeLoader.loadNext());
				progressModel.setLabelText(programAttrSet.getStringField(KEY_LOADING) + codeLoader.currentValue());
				
			}
			
			//结束循环并停止监视与计时
			progressModel.end();
			cti.stop();
			
			//生成代码实例
			CodeSerial codeList = new ArrayCodeSerial(codes.toArray(new Code[0]));
			//将代码设置为前端。
			moduleControlPort.frontCp().setFrontCodeSerial(codeList, file, false);
			//在视图中渲染代码。
			viewControlPort.frameCp().showCode(moduleControlPort.frontCp().getCodeSerial());
			viewControlPort.frameCp().noneFileMode(false);
			//生成报告
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_STATS),
					codeList.getTotle(),
					cti.getTime(Time.MS)
			);
			
		}catch(Exception e){
			e.printStackTrace();
			viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_FAIL));
			return;
		}finally{
			viewControlPort.frameCp().unlockEdit();
			try {
				codeLoader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
