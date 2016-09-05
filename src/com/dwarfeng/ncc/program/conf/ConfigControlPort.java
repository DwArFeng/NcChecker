package com.dwarfeng.ncc.program.conf;

import java.io.IOException;

/**
 * 设置控制站点。
 * @author DwArFeng
 * @since 1.8
 */
public interface ConfigControlPort {
	
	/**
	 * 保存指定的外观设置。
	 * @param config 指定的外观设置。
	 * @throws IOException IO异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void saveMainFrameAppearConfig(MfAppearConfig config)throws IOException;
	
	/**
	 * 获取配置文件中的外观配置。
	 * @return 外观配置。
	 * @throws IOException IO异常。
	 * @throws NumberFormatException 数字格式异常：在配置文件被外部更改破坏时可能会抛出。
	 */
	public MfAppearConfig loadMainFrameAppearConfig() throws IOException,NumberFormatException;
	
	/**
	 * 保存指定的前端外观配置。
	 * @param config 指定的前端外观配置。
	 * @throws IOException IO异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public void saveFrontModuleConfig(FrontModuleConfig config) throws IOException;
	
	/**
	 * 获取配置文件的前端配置。
	 * @return 前端模型配置。
	 * @throws IOException IO异常。
	 * @throws NumberFormatException 数字格式异常：在配置文件被外部更改破坏时可能会抛出。
	 */
	public FrontModuleConfig loadFrontModuleConfig()throws IOException, NumberFormatException;

}
