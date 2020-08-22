package com.main.schoolux.exceptions;







public class PropertyException extends Exception {

	public PropertyException(String exception) {

		super(exception);
	}


	public PropertyException(String exception, Throwable cause) {
		super(exception, cause);
	}

}
