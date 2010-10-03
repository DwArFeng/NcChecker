package com.dwarfeng.ncc.control.back;

import java.io.IOException;

import com.dwarfeng.dfunc.dt.CodeTimer;
import com.dwarfeng.dfunc.io.StringBuilderOutputStream;
import com.dwarfeng.dfunc.num.UnitTrans.Time;
import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.control.NccControlPort.Mode;
import com.dwarfeng.ncc.model.mut.StreamCodePrinter;
import com.dwarfeng.ncc.model.nc.CodeSerial;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.gui.DefaultProgressModel;
import com.dwarfeng.ncc.view.gui.ProgressModel;

/**
 * 切换到编辑模式的Runnable。
 * @author DwArFeng
 * @since 1.8
 */
public class Toggle2EidtRunnable extends AbstractCmr{

	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final StringFieldKey KEY_LOADING = StringFieldKey.PROGRESS_CODE_EDIT;
	private static final StringFieldKey KEY_START = StringFieldKey.OUT_EDIT_START;
	private static final StringFieldKey KEY_STATS= StringFieldKey.OUT_EDIT_STATS;
	private static final StringFieldKey KEY_SUSPEND= StringFieldKey.OUT_EDIT_SUSPEND;
	private static final StringFieldKey KEY_FAIL= StringFieldKey.OUT_EDIT_FAIL;
	
	//------------------------------------------------------------------------------------------------
	
	/**
	 * 生成一个指定的编辑模式的可运行对象。
	 * @param controlManager 指定的控制管理器。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public Toggle2EidtRunnable(NccControlManager controlManager) {
		super(controlManager);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		StringBuilderOutputStream out = null;
		
		//界面编辑锁定
		viewControlPort.frameCp().lockEdit();
		
		try{
			//初始必要的变量
			CodeSerial codeSerial = moduleControlPort.frontCp().getFrontCodeSerial();
			out = new StringBuilderOutputStream();
			final StreamCodePrinter codePrinter = new StreamCodePrinter(codeSerial, out);
			
			//输出开始信息
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_START),
					codeSerial.getTotle()
			);
			
			//生成进度模型
			ProgressModel progressModel = new DefaultProgressModel();
			progressModel.setIndeterminate(false);
			progressModel.setLabelText(programAttrSet.getStringField(KEY_LOADING));
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
					viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_SUSPEND));
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
			viewControlPort.frameCp().toggleMode(Mode.EDIT);
			//渲染文本
			viewControlPort.frameCp().setEditText(text);
			//生成报告
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_STATS),
					cti.getTime(Time.MS)
			);
			
		}catch(Exception e){
			e.printStackTrace();
			viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_FAIL));
			return;
		}finally{
			viewControlPort.frameCp().unlockEdit();
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}


}
