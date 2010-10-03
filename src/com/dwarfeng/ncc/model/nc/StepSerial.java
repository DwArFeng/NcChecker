package com.dwarfeng.ncc.model.nc;

/**
 * ���߲��б�
 * <p> �������ĵ��ߵ�����ɵĵ��߲��б�
 * @author DwArFeng
 * @since 1.8
 */
public interface StepSerial<T extends ToolPoint , M extends Modal> extends Iterable<StepSerial<T, M>>{

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
