package com.lr.response.lrtransaction;

import com.lr.model.LRTransaction;
import com.lr.response.AppResponse;
import com.lr.response.UserView;

public class LRTrasactionResponse implements AppResponse {
	private static final int code = 1; //Success
	private LRTransaction transaction;
	
	public LRTrasactionResponse() {}
	
	public static int getCode() {
		return code;
	}

	public LRTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(LRTransaction transaction) {
		this.transaction = transaction;
	}

	

}
