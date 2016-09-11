package com.dwarfeng.ncc.module.front;

import java.io.File;

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
	 * @param file 相连接的文件。
	 * @param isCreate 是否是从程序中新建的。
	 */
	public void setFrontCodeSerial(CodeSerial codeSerial, File file, boolean isCreate);
	
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
	 * @throws IllegalStateException 前台代码不存在。
	 */
	public CodeSerial getCodeSerial(); 
	
	/**
	 * 将前台代码段与某个文件相连接。
	 * @param file 指定的文件，可以为 <code>null</code>。
	 * @throws IllegalStateException 前台代码不存在。
	 */
	public void linkFile(File file);
	
	/**
	 * 获取与前台相连接的文件。
	 * @return 与前台向连接的文件，如果没有，则返回 <code>null</code>。
	 * @throws IllegalStateException 前台代码不存在。
	 */
	public File getLinkedFile();
	
	/**
	 * 返回前台文件是否需要保存。
	 * @return 前台文件是否需要保存。
	 * @throws IllegalStateException 前台代码不存在。
	 */
	public boolean needSave();

}
