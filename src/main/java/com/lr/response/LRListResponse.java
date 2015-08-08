package com.lr.response;

import java.util.List;

public class LRListResponse implements AppResponse {
	private static final int code = 1; //Success
	private static String message = "";
	private List<LRListView> lrs;
	
	public LRListResponse()	{}
	
	public static int getCode() {
		return code;
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

	public static String getMessage() {
		return message;
	}

	public static void setMessage(String message) {
		LRListResponse.message = message;
	}
	
	
}
