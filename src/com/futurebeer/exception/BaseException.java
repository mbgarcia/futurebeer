package com.futurebeer.exception;

public class BaseException extends Exception{
	private static final long serialVersionUID = -7903726824852757751L;

	public BaseException(Exception e) {
		super(e);
	}

	public BaseException(String string, Exception e) {
		super(string, e);
	}

	public BaseException(String string) {
		super(string);
	}
}
