package com.dwarfeng.ncc.module.front;

import com.dwarfeng.ncc.module.nc.CodeSerial;
import com.dwarfeng.ncc.program.conf.FrontConfig;

/**
 * 前端模型控制站。
 * @author DwArFeng
 * @since 1.8
 */
public interface FrontCp{
	
	/**
	 * 获取前台的代码列表。
	 * @return 前台的代码列表，如果没有，则返回 <code>null</code>。
	 */
	public CodeSerial getFrontCodeSerial();
	
	/**
	 * 设置前台的代码列表。
	 * @param codeSerial 指定的代码列表，如果要移除前台代码，则指定 <code>null</code>。
	 */
	public void setFrontCodeSerial(CodeSerial codeSerial);
	
	/**
	 * 使用指定的代码序列覆盖前台代码。
	 * <p> 与设置新的代码不一样，覆盖多用于相同代码的提交与更改，并且可以进行有限次数的撤销。
	 * @param codeSerial 指定的代码。
	 * @throws IllegalStateException 没有前台代码。
	 */
	public void overwriteCodeSerial(CodeSerial codeSerial);
	
	/**
	 * 应用前端模型的配置。
	 * @param config 前端模型配置。
	 */
	public void applyFontConfig(FrontConfig config);
	
	/**
	 * 获取前端模型的配置。
	 * @return 前端模型的配置。
	 */
	public FrontConfig getFontConfig();
	
	/**
	 * 返回是否有前台代码。
	 * @return 是否有前台代码。
	 */
	public boolean hasFrontCode();
	
	/**
	 * 获取当前的代码。
	 * @return 对应的代码。
	 * @throws NullPointerException 前台代码不存在。
	 */
	public CodeSerial getCodeSerial(); 

}
