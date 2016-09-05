package com.dwarfeng.ncc.module.front;

import com.dwarfeng.ncc.module.nc.CodeSerial;

/**
 * ǰ��ģ�Ϳ���վ��
 * @author DwArFeng
 * @since 1.8
 */
public interface FrontModuleControlPort{
	
	/**
	 * ��ȡǰ̨�Ĵ����б�
	 * @return ǰ̨�Ĵ����б����û�У��򷵻� <code>null</code>��
	 */
	public CodeSerial getFrontCodeSerial();
	
	/**
	 * ����ǰ̨�Ĵ����б�
	 * @param codeSerial ָ���Ĵ����б����Ҫ�Ƴ�ǰ̨���룬��ָ�� <code>null</code>��
	 */
	public void setFrontCodeSerial(CodeSerial codeSerial);

}
