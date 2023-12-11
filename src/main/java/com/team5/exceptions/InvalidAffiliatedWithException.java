package com.team5.exceptions;

public class InvalidAffiliatedWithException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidAffiliatedWithException(String msg) {
		super(msg);
	}

}
