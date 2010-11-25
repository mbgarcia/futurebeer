package com.futurebeer.util;


public enum StatusMesa {
	LIVRE(0), OCUPADA(1);
	
	private int status;
	
	private StatusMesa(int s){
		status = s;
	}
	
	public String toString() {
		return String.valueOf(status);
	}

	public int getStatus() {
		return status;
	}
}
