package com.lr.response;

import java.util.List;

public class LROthersResponse implements AppResponse {
	private static final int code = 1; //Success
	private List<LROthersView> lrOthers;
	public LROthersResponse() {}
	public LROthersResponse(List<LROthersView> lrOthers ) {
		this.lrOthers = lrOthers;
	}
	
	public int getCode() {
		return code;
	}
	public List<LROthersView> getLrOthers() {
		return lrOthers;
	}
	public void setLrOthers(List<LROthersView> lrOthers) {
		this.lrOthers = lrOthers;
	}
	
	
	
	
}
