package com.lr.response;

import java.util.List;

public class LRListResponse implements AppResponse {
	private static final int code = 1; //Success
	private String message ;
	private List<LRListView> lrs;
	
	public LRListResponse()	{}
	
	public static int getCode() {
		return code;
	}	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
	
	public LRListResponse(List<LRListView> lrs) {
		this.lrs = lrs;
	}

	public List<LRListView> getLrs() {
		return lrs;
	}

	public void setLrs(List<LRListView> lrs) {
		this.lrs = lrs;
	}

	
	
}
