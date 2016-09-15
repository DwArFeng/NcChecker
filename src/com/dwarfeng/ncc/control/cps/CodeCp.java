package com.dwarfeng.ncc.control.cps;

/**
 * �����վ��
 * @author DwArFeng
 * @since 1.8
 */
public interface CodeCp {

	/**
	 * �����еļ���ģʽ����Դ�����壩
	 * @author DwArFeng
	 * @since 1.8
	 */
	public enum CodeEditMode{
		/**�鿴ģʽ*/
		INSPECT,
		/**�༭ģʽ*/
		EDIT,
	}
	
	/**
	 * �����л�ģʽ��
	 * @param mode ָ����ģʽ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void attemptToggleMode(CodeEditMode mode);
	
	
}
