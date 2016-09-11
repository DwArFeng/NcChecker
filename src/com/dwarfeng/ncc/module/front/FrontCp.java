package com.dwarfeng.ncc.module.front;

import java.io.File;

import com.dwarfeng.ncc.module.nc.CodeSerial;
import com.dwarfeng.ncc.program.conf.FrontConfig;

/**
 * ǰ��ģ�Ϳ���վ��
 * @author DwArFeng
 * @since 1.8
 */
public interface FrontCp{
	
	/**
	 * ��ȡǰ̨�Ĵ����б�
	 * @return ǰ̨�Ĵ����б����û�У��򷵻� <code>null</code>��
	 */
	public CodeSerial getFrontCodeSerial();
	
	/**
	 * ����ǰ̨�Ĵ����б�
	 * @param codeSerial ָ���Ĵ����б����Ҫ�Ƴ�ǰ̨���룬��ָ�� <code>null</code>��
	 * @param file �����ӵ��ļ���
	 * @param isCreate �Ƿ��Ǵӳ������½��ġ�
	 */
	public void setFrontCodeSerial(CodeSerial codeSerial, File file, boolean isCreate);
	
	/**
	 * ʹ��ָ���Ĵ������и���ǰ̨���롣
	 * <p> �������µĴ��벻һ�������Ƕ�������ͬ������ύ����ģ����ҿ��Խ������޴����ĳ�����
	 * @param codeSerial ָ���Ĵ��롣
	 * @throws IllegalStateException û��ǰ̨���롣
	 */
	public void overwriteCodeSerial(CodeSerial codeSerial);
	
	/**
	 * Ӧ��ǰ��ģ�͵����á�
	 * @param config ǰ��ģ�����á�
	 */
	public void applyFontConfig(FrontConfig config);
	
	/**
	 * ��ȡǰ��ģ�͵����á�
	 * @return ǰ��ģ�͵����á�
	 */
	public FrontConfig getFontConfig();
	
	/**
	 * �����Ƿ���ǰ̨���롣
	 * @return �Ƿ���ǰ̨���롣
	 */
	public boolean hasFrontCode();
	
	/**
	 * ��ȡ��ǰ�Ĵ��롣
	 * @return ��Ӧ�Ĵ��롣
	 * @throws IllegalStateException ǰ̨���벻���ڡ�
	 */
	public CodeSerial getCodeSerial(); 
	
	/**
	 * ��ǰ̨�������ĳ���ļ������ӡ�
	 * @param file ָ�����ļ�������Ϊ <code>null</code>��
	 * @throws IllegalStateException ǰ̨���벻���ڡ�
	 */
	public void linkFile(File file);
	
	/**
	 * ��ȡ��ǰ̨�����ӵ��ļ���
	 * @return ��ǰ̨�����ӵ��ļ������û�У��򷵻� <code>null</code>��
	 * @throws IllegalStateException ǰ̨���벻���ڡ�
	 */
	public File getLinkedFile();
	
	/**
	 * ����ǰ̨�ļ��Ƿ���Ҫ���档
	 * @return ǰ̨�ļ��Ƿ���Ҫ���档
	 * @throws IllegalStateException ǰ̨���벻���ڡ�
	 */
	public boolean needSave();

}
