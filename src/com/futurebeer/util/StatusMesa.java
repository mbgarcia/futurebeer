package com.futurebeer.util;

public enum StatusMesa {
	OCUPADA(1), LIVRE(2);
	
	private int status;
	
	private StatusMesa(int s){
		status = s;
	}
	
	public String toString() {
		return String.valueOf(status);
	}
}
