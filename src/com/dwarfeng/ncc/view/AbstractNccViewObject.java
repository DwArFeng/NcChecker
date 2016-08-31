package com.dwarfeng.ncc.view;

import java.util.Objects;

/**
 * {@link NccViewManager}�ĳ���ʵ�֡�
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractNccViewObject implements NccViewObject{
	
	/*
	 * ���������õ�Ncc��ͼ��������
	 */
	protected final NccViewManager viewManager;

	/**
	 * ����һ������ָ��Ncc��ͼ��������Ncc��ͼ����
	 * @param viewManager ָ����Ncc��ͼ��������
	 * @throws NullPointerException ��ڲ���Ϊ<code>null</code>ʱ�׳����쳣��
	 */
	public AbstractNccViewObject(NccViewManager viewManager) {
		Objects.requireNonNull(viewManager);
		this.viewManager = viewManager;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.ncc.view.NccViewObject#getViewManager()
	 */
	@Override
	public NccViewManager getViewManager() {
		return viewManager;
	}

}
