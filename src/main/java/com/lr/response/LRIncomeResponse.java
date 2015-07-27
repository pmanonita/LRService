package com.lr.response;

public class LRIncomeResponse implements AppResponse {
	private static final int code = 1; //Success
	private LRIncomeView lrIncome;
	public LRIncomeResponse() {}
	public LRIncomeResponse(LRIncomeView lrIncome ) {
		this.lrIncome = lrIncome;
	}
	
	public int getCode() {
		return code;
	}
	public LRIncomeView getLrIncome() {
		return lrIncome;
	}
	public void setLrIncome(LRIncomeView lrIncome) {
		this.lrIncome = lrIncome;
	}
	
	
	
}
