package com.dwarfeng.ncc.module.des;

public enum Des {
	A(String.class),
	B(Character.class);
	
	Class<? extends Object> cls;
	
	private <T extends Object> Des(Class<T> cls) {
		this.cls = cls;
	}
	public<T> Class<T> getCl(){
		return cls;
	}
}
