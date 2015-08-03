package com.lr.response;

import java.util.List;

public class BillingnameListResponse implements AppResponse {
	private static final int code = 1; //Success
	private List<BillingnameView> billingnames;
	
	public BillingnameListResponse()	{}
	
	public BillingnameListResponse(List<BillingnameView> billingnames) {
		this.billingnames = billingnames;
	}
	

	public static int getCode() {
		return code;
	}

	public List<BillingnameView> getBillingnames() {
		return billingnames;
	}

	public void setBillingnames(List<BillingnameView> billingnames) {
		this.billingnames = billingnames;
	}
	
	
	
	

}
