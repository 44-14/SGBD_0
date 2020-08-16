package com.main.schoolux.exceptions;

public class PropertyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PropertyException(String exception) {

		super(exception);
	}

	public PropertyException(String exception, Throwable cause) {
		super(exception, cause);
	}

}
