package com.lr.response.lrtransaction;

import java.util.List;

import com.lr.response.AppResponse;

public class LRTransactioListResponse implements AppResponse {
	private static final int code = 1; //Success
	private String message ;
	private List<LRTransactionListView> lrTransactions;
	
	public LRTransactioListResponse()	{}
	
	public LRTransactioListResponse(List<LRTransactionListView> lrs) {
		this.lrTransactions = lrTransactions;
	}
	
	public static int getCode() {
		return code;
	}	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<LRTransactionListView> getLrTransactions() {
		return lrTransactions;
	}

	public void setLrTransactions(List<LRTransactionListView> lrTransactions) {
		this.lrTransactions = lrTransactions;
	}
	
}
