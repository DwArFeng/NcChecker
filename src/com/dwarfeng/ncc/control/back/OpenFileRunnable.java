package com.dwarfeng.ncc.control.back;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	
	private static final StringFieldKey KEY_LOADING = StringFieldKey.FILE_NOWLOADING;
	
	//------------------------------------------------------------------------------------------------
	
	private final CodeLoader codeLoader;
	
	/**
	 * 生成一个指定的打开文件可运行对象。
	 * @param controlManager 指定的程序管理器。
	 * @param codeLoader NC代码读取器。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public OpenFileRunnable(NccControlManager controlManager, CodeLoader codeLoader) {
		super(controlManager);
		Objects.requireNonNull(codeLoader);
		this.codeLoader = codeLoader;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try{
			viewControlPort.frameCp().lockEdit();
			viewControlPort.progCp().startMonitor();
			viewControlPort.progCp().setMessage(programAttrSet.getStringField(KEY_LOADING));
			viewControlPort.progCp().setIndeterminate(true);
			int i = 1;
			final List<Code> codes = new ArrayList<Code>();
			while(codeLoader.hasNext()){
				//如果手动停止，则终止程序。
				if(viewControlPort.progCp().isSuspend()){
					return;
				}
				
				//否则循环读取代码。
				codes.add(codeLoader.loadNext());
				viewControlPort.progCp().setMessage(programAttrSet.getStringField(KEY_LOADING) + i);
				i++;
			}
			//循环结束，代码读取完毕。

			CodeSerial codeList = new ArrayCodeList(codes.toArray(new Code[0]));
			//将代码设置为前端。
			moduleControlPort.frontCp().setFrontCodeSerial(codeList);
			
			viewControlPort.frameCp().showCode(moduleControlPort.frontCp().getCodeSerial());
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
