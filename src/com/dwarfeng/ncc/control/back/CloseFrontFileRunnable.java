package com.dwarfeng.ncc.control.back;

import java.io.IOException;

import com.dwarfeng.dfunc.dt.CodeTimer;
import com.dwarfeng.dfunc.io.StringOutputStream;
import com.dwarfeng.dfunc.num.UnitTrans.Time;
import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.control.NccControlPort.Mode;
import com.dwarfeng.ncc.model.front.CodePrinter;
import com.dwarfeng.ncc.model.mut.StreamCodePrinter;
import com.dwarfeng.ncc.model.nc.CodeSerial;
import com.dwarfeng.ncc.view.gui.DefaultProgressModel;
import com.dwarfeng.ncc.view.gui.ProgressModel;

/**
 * 关闭前台文件所需的Runnable。
 * @author DwArFeng
 * @since 1.8
 */
public class CloseFrontFileRunnable extends AbstractCmr{

	private final boolean saveFlag;
	
	/**
	 * 生成一个指定的关闭文件可运行对象。
	 * @param controlManager 指定的程序管理器。
	 * @param 是否需要保存。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public CloseFrontFileRunnable(NccControlManager controlManager, boolean saveFlag) {
		super(controlManager);
		this.saveFlag = saveFlag;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
//		
//		StringOutputStream out = null;
//		CodePrinter codePrinter = null;
//		
//		//界面编辑锁定
//		viewControlPort.frameCp().lockEdit();
//		
//		try{
//			
//			if(saveFlag){
//				//初始必要的变量
//				CodeSerial codeSerial = modelControlPort.frontCp().getFrontCodeSerial();
//				out = new StringOutputStream();
//				
//				codePrinter = new StreamCodePrinter(codeSerial, out);
//				
//				//输出开始信息
//				viewControlPort.frameCp().traceInConsole(
//						programAttrSet.getStringField(KEY_START),
//						codeSerial.getTotle()
//				);
//				
//				//生成进度模型
//				ProgressModel progressModel = new DefaultProgressModel();
//				progressModel.setIndeterminate(false);
//				progressModel.setLabelText(programAttrSet.getStringField(KEY_LOADING));
//				progressModel.setMaximum(codePrinter.getTotleCode());
//				progressModel.setValue(0);
//				
//				//设置进度模型
//				viewControlPort.frameCp().startProgressMonitor(progressModel);
//				
//				//建立代码计时机制
//				CodeTimer cti = new CodeTimer();
//				cti.start();
//				
//				//循环读取程序
//				while(codePrinter.hasNext()){
//					
//					//如果手动停止，则终止进程。
//					if(progressModel.isSuspend()){
//						viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_SUSPEND));
//						return;
//					}
//					
//					//否则循环读取代码。
//					codePrinter.printNext();
//					progressModel.setValue(codePrinter.currentValue());
//					
//				}
//				
//				//结束循环并停止监视与计时
//				progressModel.end();
//				cti.stop();
//				
//				//生成文本
//				out.flush();
//				String text = out.toString();
//				
//				//视图转换为编辑模式
//				viewControlPort.frameCp().knockForMode(Mode.EDIT);
//				//渲染文本
//				viewControlPort.frameCp().setEditText(text);
//				//生成报告
//				viewControlPort.frameCp().traceInConsole(
//						programAttrSet.getStringField(KEY_STATS),
//						cti.getTime(Time.MS)
//				);
//			}
//
//			//从视图中撤下代码段
//			viewControlPort.frameCp().showCode(null);
//			//移除前台模型中的代码段
//			modelControlPort.frontCp().setFrontCodeSerial(null, null, false);
//			//设置视图为无文件模式
//			viewControlPort.frameCp().noneFileMode(true);
//			
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_FAIL));
//			return;
//		}finally{
//			viewControlPort.frameCp().unlockEdit();
//			if(codePrinter != null){
//				try {
//					codePrinter.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}

	}

}
