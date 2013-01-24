package org.cn.pilot.drp.sysmgr.manager;

public class PasswordNotCorrectException extends RuntimeException {

	public PasswordNotCorrectException() {
		super();
	}

	public PasswordNotCorrectException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PasswordNotCorrectException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordNotCorrectException(String message) {
		super(message);
	}

	public PasswordNotCorrectException(Throwable cause) {
		super(cause);
	}

}
