package com.dwarfeng.ncc.control.back;

import com.dwarfeng.dfunc.dt.CodeTimer;
import com.dwarfeng.dfunc.num.UnitTrans.Time;
import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.model.nc.ArrayCodeSerial;
import com.dwarfeng.ncc.model.nc.Code;
import com.dwarfeng.ncc.model.nc.CodeSerial;
import com.dwarfeng.ncc.program.key.StringFieldKey;

/**
 * 新建文件所需要的Runnable。
 * @author DwArFeng
 * @since 1.8
 */
public class NewFileRunnable extends AbstractCmr {
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final StringFieldKey KEY_START = StringFieldKey.OUT_NEWFILE_START;
	private static final StringFieldKey KEY_STATS = StringFieldKey.OUT_NEWFILE_STATS;
	
	//------------------------------------------------------------------------------------------------

	/**
	 * 生成一个指定的新建文件可运行对象。
	 * @param controlManager 指定的程序管理器。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public NewFileRunnable(NccControlManager controlManager) {
		super(controlManager);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {			
		viewControlPort.frameCp().lockEdit();
		try{
			//输出信息
			viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_START));
			
			CodeTimer timer = new CodeTimer();
			timer.start();
			//新建一个空的代码序列
			CodeSerial codeSerial = new ArrayCodeSerial(new Code[0]);
			timer.stop();
			
			//向前端模型中增加这个代码序列
			modelControlPort.frontCp().setFrontCodeSerial(codeSerial, null, true);
			
			//在视图中渲染代码
			viewControlPort.frameCp().showCode(modelControlPort.frontCp().getCodeSerial());
			viewControlPort.frameCp().noneFileMode(false);
			
			//生成统计报告
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_STATS), timer.getTime(Time.MS));
			
		}catch(Exception e){
			
			e.printStackTrace();
			return;
			
		}finally{
			viewControlPort.frameCp().unlockEdit();
		}
		
	}

}
