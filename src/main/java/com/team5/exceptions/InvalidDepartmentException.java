package com.team5.exceptions;

public class InvalidDepartmentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

public InvalidDepartmentException(String message) {
	super(message);
}
}
