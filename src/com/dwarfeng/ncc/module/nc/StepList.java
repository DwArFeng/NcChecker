package com.dwarfeng.ncc.module.nc;

import com.dwarfeng.ncc.module.NccModuleObject;

/**
 * ���߲��б�
 * <p> �������ĵ��ߵ�����ɵĵ��߲��б�
 * @author DwArFeng
 * @since 1.8
 */
public interface StepList<T extends ToolPoint , M extends Modal> extends Iterable<StepList<T, M>>,
NccModuleObject{

	/**
	 * ����ָ����Ŵ��ĵ��ߵ�����
	 * @param index ָ������š�
	 * @return ָ����Ŵ��ĵ��ߵ�����
	 * 	@throws IndexOutOfBoundsException �кų�������������С��0ʱ�׳����쳣��
	 */
	public Step<T, M> getToolStep(int index);
	
	/**
	 * ���ص������е��ܹ�������
	 * @return �������е��ܲ�����
	 */
	public int getTotleStep();
	
}
