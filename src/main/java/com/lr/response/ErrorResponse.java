package com.lr.response;

public class ErrorResponse {
	private int code;
	private ErrorMessage error;
	
	public ErrorResponse() {}
	public ErrorResponse(int code, ErrorMessage error ) {
		this.code = code;
		this.error = error;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public ErrorMessage getError() {
		return error;
	}
	public void setError(ErrorMessage error) {
		this.error = error;
	}

}
