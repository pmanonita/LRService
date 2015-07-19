package com.lr.response;

public class LROthersResponse implements AppResponse {
	private static final int code = 1; //Success
	private LROthersView lrOthers;
	public LROthersResponse() {}
	public LROthersResponse(LROthersView lrOthers ) {
		this.lrOthers = lrOthers;
	}
	
	public int getCode() {
		return code;
	}
	public LROthersView getLrOthers() {
		return lrOthers;
	}
	public void setLrOthers(LROthersView lrOthers) {
		this.lrOthers = lrOthers;
	}
	
	
	
	
}
