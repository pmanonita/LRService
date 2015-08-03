package com.lr.response.generalexpense;

import com.lr.response.AppResponse;
import com.lr.response.UserView;

public class ExpenseResponse implements AppResponse {
	private static final int code = 1; //Success
	private ExpenseView expense;
	
	public ExpenseResponse() {}
	public ExpenseResponse(ExpenseView expense) {
		this.expense = expense;
	}
	
	public int getCode() {
		return code;
	}
	
	public ExpenseView getExpense() {
		return expense;
	}
	public void setExpense(ExpenseView expense) {
		this.expense = expense;
	}
	


}
