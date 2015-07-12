package com.lr.response;

public class ErrorResponse implements AppResponse {
	private static final int code = 0; //Fail
	private ErrorMessage error;
	
	public ErrorResponse() {}
	public ErrorResponse(ErrorMessage error ) {

		this.error = error;
	}
	
	public int getCode() {
		return code;
	}
	
	public ErrorMessage getError() {
		return error;
	}
	public void setError(ErrorMessage error) {
		this.error = error;
	}

}
