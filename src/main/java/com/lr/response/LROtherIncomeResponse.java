package com.lr.response;

import java.util.List;

public class LROtherIncomeResponse implements AppResponse {
	private static final int code = 1; //Success
	private List<LROtherIncomeView> lrOtherIncome;
	public LROtherIncomeResponse() {}
	public LROtherIncomeResponse(List<LROtherIncomeView> lrOtherIncome ) {
		this.lrOtherIncome = lrOtherIncome;
	}
	
	public int getCode() {
		return code;
	}
	public List<LROtherIncomeView> getLrOtherIncome() {
		return lrOtherIncome;
	}
	public void setLrOtherIncome(List<LROtherIncomeView> lrOtherIncome) {
		this.lrOtherIncome = lrOtherIncome;
	}
	
	
	
	
	
	
}
