package com.dwarfeng.ncc.control.back;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.dwarfeng.dfunc.dt.CodeTimer;
import com.dwarfeng.dfunc.io.StringInputStream;
import com.dwarfeng.dfunc.num.UnitTrans.Time;
import com.dwarfeng.ncc.control.NccControlManager;
import com.dwarfeng.ncc.control.NccControlPort.Mode;
import com.dwarfeng.ncc.model.front.CodeLoader;
import com.dwarfeng.ncc.model.mut.StreamCodeLoader;
import com.dwarfeng.ncc.model.nc.ArrayCodeSerial;
import com.dwarfeng.ncc.model.nc.Code;
import com.dwarfeng.ncc.model.nc.CodeSerial;
import com.dwarfeng.ncc.program.key.StringFieldKey;
import com.dwarfeng.ncc.view.gui.DefaultProgressModel;
import com.dwarfeng.ncc.view.gui.NotifyCp.AnswerType;
import com.dwarfeng.ncc.view.gui.ProgressModel;

/**
 * 切换到查看模式的Runnable。
 * @author DwArFeng
 * @since 1.8
 */
public class Toggle2InspectRunnable extends AbstractCmr {
	
	//-----------------------------以下是需要使用的各种字段键值------------------------------------
	
	private static final StringFieldKey KEY_LOADING = StringFieldKey.PROGRESS_COMMIT;
	private static final StringFieldKey KEY_START = StringFieldKey.OUT_COMMIT_START;
	private static final StringFieldKey KEY_STATS= StringFieldKey.OUT_COMMIT_STATS;
	private static final StringFieldKey KEY_SUSPEND= StringFieldKey.OUT_COMMIT_SUSPEND;
	private static final StringFieldKey KEY_FAIL= StringFieldKey.OUT_COMMIT_FAIL;
	
	//------------------------------------------------------------------------------------------------

	private final AnswerType answerType;
	
	/**
	 * 生成一个指定的查看模式的可运行对象。
	 * @param controlManager 指定的控制管理器。
	 * @param ignoreAnswer 是否忽略回答。
	 * @param type 回答的类型
	 * @throws NullPointerException 不忽略回答的情况下回答为 <code>null</code>。
	 */
	public Toggle2InspectRunnable(
			NccControlManager controlManager,AnswerType type) {
		super(controlManager);
		Objects.requireNonNull(type);
		this.answerType = type;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		switch (answerType) {
			case CANCEL:
				directEdit();
				return;
			case NO:
				directInspect();
				return;
			case YES:
				commitInspect();
				return;
		}
	}

	private void commitInspect() {
		InputStream in = new StringInputStream(viewControlPort.frameCp().getEditText());
		CodeLoader codeLoader = null;
		
		//界面编辑锁定
		viewControlPort.frameCp().lockEdit();
		
		try{
			//初始必要的变量
			codeLoader = new StreamCodeLoader(in);
			final List<Code> codes = new ArrayList<Code>();
			
			//输出开始信息
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_START),
					viewControlPort.frameCp().getEditLine()
			);
			
			//生成进度模型
			ProgressModel progressModel = new DefaultProgressModel();
			progressModel.setIndeterminate(false);
			progressModel.setLabelText(programAttrSet.getStringField(KEY_LOADING));
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
					viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_SUSPEND));
					viewControlPort.frameCp().knockForMode(Mode.EDIT);
					return;
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
			//视图转换为编辑模式
			viewControlPort.frameCp().knockForMode(Mode.INSPECT);
			//渲染代码序列
			viewControlPort.frameCp().showCode(modelControlPort.frontCp().getFrontCodeSerial());
			//生成报告
			viewControlPort.frameCp().traceInConsole(
					programAttrSet.getStringField(KEY_STATS),
					cti.getTime(Time.MS)
			);
			
		}catch(Exception e){
			e.printStackTrace();
			viewControlPort.frameCp().traceInConsole(programAttrSet.getStringField(KEY_FAIL));
			viewControlPort.frameCp().knockForMode(Mode.EDIT);
			return;
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

	private void directEdit() {
		viewControlPort.frameCp().knockForMode(Mode.EDIT);
	}

	private void directInspect() {
		viewControlPort.frameCp().setEditText("");
		viewControlPort.frameCp().knockForMode(Mode.INSPECT);
	}

	
}
