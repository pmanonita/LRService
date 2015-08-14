package com.lr.response.lrtransaction;

import java.util.List;

import com.lr.response.AppResponse;

public class LRTransOtherIncomeResponse implements AppResponse {
	private static final int code = 1; //Success
	private List<LRTransOtherIncomeView> lrTransOtherIncome;
	public LRTransOtherIncomeResponse() {}
	public LRTransOtherIncomeResponse(List<LRTransOtherIncomeView> lrTransOtherIncome ) {
		this.lrTransOtherIncome = lrTransOtherIncome;
	}
	
	public int getCode() {
		return code;
	}
	public List<LRTransOtherIncomeView> getLrTransOtherIncome() {
		return lrTransOtherIncome;
	}
	public void setLrTransOtherIncome(
			List<LRTransOtherIncomeView> lrTransOtherIncome) {
		this.lrTransOtherIncome = lrTransOtherIncome;
	}
	
	
}
