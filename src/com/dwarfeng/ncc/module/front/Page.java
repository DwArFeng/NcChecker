package com.dwarfeng.ncc.module.front;

/**
 * 分页。
 * @author DwArFeng
 * @since 1.8
 */
public final class Page{

	private final int val;
	
	/**
	 * 生成一个分页对象。
	 * @param page 指定的页数。
	 */
	public Page(int page) {
		this.val = page;
	}
	
	/**
	 * 返回当前的分页值。
	 * @return 值。
	 */
	public int getVal(){
		return this.val;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Page)) return false;
		return ((Page)obj).val == this.val;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return val * 17;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "page " + val;
	}

}
