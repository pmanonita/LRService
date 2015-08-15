package com.lr.response.lrtransaction;

import com.lr.model.LRTransaction;
import com.lr.response.AppResponse;
import com.lr.response.UserView;

public class LRTrasactionResponse implements AppResponse {
	private static final int code = 1; //Success
	private LRTransactionView transaction;
	
	public LRTrasactionResponse() {}
	
	public static int getCode() {
		return code;
	}

	public LRTransactionView getTransaction() {
		return transaction;
	}

	public void setTransaction(LRTransactionView transaction) {
		this.transaction = transaction;
	}

	
	

}
