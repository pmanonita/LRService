package com.lr.response;

public class LRBillResponse implements AppResponse {
	private static final int code = 1; //Success
	private LRBillView lrBill;
	public LRBillResponse() {}
	public LRBillResponse(LRBillView lrBill ) {
		this.lrBill = lrBill;
	}
	
	public int getCode() {
		return code;
	}
	public LRBillView getLrBill() {
		return lrBill;
	}
	public void setLrBill(LRBillView lrBill) {
		this.lrBill = lrBill;
	}
	
	
	
}
