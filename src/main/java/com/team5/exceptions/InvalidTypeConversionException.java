package com.team5.exceptions;

public class InvalidTypeConversionException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public InvalidTypeConversionException(String msg) {
		super(msg);
	}

}
