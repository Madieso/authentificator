package com.lma.authentificator.model;

public class ResponseRest {

	public ResponseRest(String code) {
		super();
		this.errorCode = code;
	}

	private String errorCode;

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String code) {
		this.errorCode = code;
	}

}
