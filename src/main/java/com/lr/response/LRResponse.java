package com.lr.response;

public class LRResponse implements AppResponse {
	private static final int code = 1; //Success
	private LRView lr;
	public LRResponse() {}
	public LRResponse(LRView lr ) {
		this.lr = lr;
	}
	
	public int getCode() {
		return code;
	}
	
	public LRView getLr() {
		return lr;
	}
	public void setLr(LRView lr) {
		this.lr = lr;
	}
	
	
}
