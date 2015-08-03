package com.lr.response.generalexpense;

import java.util.List;

import com.lr.response.AppResponse;

public class ExpenseListResponse implements AppResponse {
	private static final int code = 1; //Success
	private List<ExpenseView> expenses;
	
	public ExpenseListResponse()	{}
	
	public static int getCode() {
		return code;
	}
	
	public ExpenseListResponse(List<ExpenseView> expenses) {
		this.expenses = expenses;
	}

	public List<ExpenseView> getExpenses() {
		return expenses;
	}
	public void setExpenses(List<ExpenseView> expenses) {
		this.expenses = expenses;
	}		 

}
