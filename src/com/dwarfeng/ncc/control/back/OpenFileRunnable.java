package com.dwarfeng.ncc.control.back;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.dwarfeng.dfunc.dt.CodeTimer;
import com.dwarfeng.dfunc.num.UnitTrans.Time;
import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.module.front.CodeLoader;
import com.dwarfeng.ncc.module.nc.ArrayCodeList;
import com.dwarfeng.ncc.module.nc.Code;
import com.dwarfeng.ncc.module.nc.CodeSerial;
import com.dwarfeng.ncc.program.key.StringFieldKey;

/**
 * 打开文件所需要的Runnable。
 * @author DwArFeng
 * @since 1.8
 */
public final class OpenFileRunnable extends AbstractCmr implements Runnable {

	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final StringFieldKey KEY_LOADING = StringFieldKey.PROGRESS_FILE_NOWLOADING;
	private static final StringFieldKey KEY_LOADSTART = StringFieldKey.OUT_LOADFILE_START;
	private static final StringFieldKey KEY_LOADSTATS= StringFieldKey.OUT_LOADFILE_STATS;
	
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
		try{
			//界面编辑锁定。
			viewControlPort.frameCp().lockEdit();
			//输出基本信息
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_LOADSTART),
					file.getAbsolutePath()
			);
			//开始监视
			viewControlPort.progCp().startMonitor();
			//设置监视标签
			viewControlPort.progCp().setMessage(programAttrSet.getStringField(KEY_LOADING));
			//监视模式为不定模式
			viewControlPort.progCp().setIndeterminate(true);
			
			final List<Code> codes = new ArrayList<Code>();
			
			//建立代码计时机制
			CodeTimer cti = new CodeTimer();
			cti.start();
			//循环读取程序
			for(int i = 1 ; codeLoader.hasNext() ; i++){
				
				//如果手动停止，则终止程序。
				if(viewControlPort.progCp().isSuspend()){
					return;
				}
				
				//否则循环读取代码。
				codes.add(codeLoader.loadNext());
				viewControlPort.progCp().setMessage(programAttrSet.getStringField(KEY_LOADING) + i);
				
			}
			cti.stop();
			
			//循环结束，代码读取完毕。
			//生成代码实例
			CodeSerial codeList = new ArrayCodeList(codes.toArray(new Code[0]));
			//将代码设置为前端。
			moduleControlPort.frontCp().setFrontCodeSerial(codeList, file);
			//在视图中渲染代码。
			viewControlPort.frameCp().showCode(moduleControlPort.frontCp().getCodeSerial());
			viewControlPort.frameCp().noneFileMode(false);
			//生成报告
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_LOADSTATS),
					codeList.getTotle(),
					cti.getTime(Time.MS)
			);
			
		}catch(Exception e){
			e.printStackTrace();
			return;
		}finally{
			viewControlPort.frameCp().unlockEdit();
			viewControlPort.progCp().endMonitor();
			try {
				codeLoader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
