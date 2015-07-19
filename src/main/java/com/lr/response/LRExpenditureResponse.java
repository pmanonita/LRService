package com.lr.response;

public class LRExpenditureResponse implements AppResponse {
	private static final int code = 1; //Success
	private LRExpeditureView lrExpenditure;
	public LRExpenditureResponse() {}
	public LRExpenditureResponse(LRExpeditureView lrExpenditure ) {
		this.lrExpenditure = lrExpenditure;
	}
	
	public int getCode() {
		return code;
	}
	public LRExpeditureView getLrExpenditure() {
		return lrExpenditure;
	}
	public void setLrExpenditure(LRExpeditureView lrExpenditure) {
		this.lrExpenditure = lrExpenditure;
	}
	
	
	
}
