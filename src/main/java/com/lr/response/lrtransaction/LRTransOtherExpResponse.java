package com.lr.response.lrtransaction;

import java.util.List;

import com.lr.response.AppResponse;

public class LRTransOtherExpResponse implements AppResponse {
	private static final int code = 1; //Success
	private List<LRTransOtherExpView> lrTransOtherExp;
	public LRTransOtherExpResponse() {}
	public LRTransOtherExpResponse(List<LRTransOtherExpView> lrTransOtherExp ) {
		this.lrTransOtherExp = lrTransOtherExp;
	}
	
	public int getCode() {
		return code;
	}
	public List<LRTransOtherExpView> getLrTransOtherExp() {
		return lrTransOtherExp;
	}
	public void setLrTransOtherExp(List<LRTransOtherExpView> lrTransOtherExp) {
		this.lrTransOtherExp = lrTransOtherExp;
	}
	
	
	
	
	
	
}
