package com.lr.response;

public class LRChalanResponse implements AppResponse {
	private static final int code = 1; //Success
	private LRChalanView lrChalan;
	public LRChalanResponse() {}
	public LRChalanResponse(LRChalanView lrChalan ) {
		this.lrChalan = lrChalan;
	}
	
	public int getCode() {
		return code;
	}
	public LRChalanView getLrChalan() {
		return lrChalan;
	}
	public void setLrChalan(LRChalanView lrChalan) {
		this.lrChalan = lrChalan;
	}
	
	
	
}
