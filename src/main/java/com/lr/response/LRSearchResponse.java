package com.lr.response;

public class LRSearchResponse implements AppResponse {
	private static final int code = 1; //Success
	private LRView lr;
	private LRExpeditureView lrExpenditure;
	private LRIncomeView lrIncome;	
	
	public  LRSearchResponse() {}

	public LRView getLr() {
		return lr;
	}

	public void setLr(LRView lr) {
		this.lr = lr;
	}

	public LRExpeditureView getLrExpenditure() {
		return lrExpenditure;
	}

	public void setLrExpenditure(LRExpeditureView lrExpenditure) {
		this.lrExpenditure = lrExpenditure;
	}

	public LRIncomeView getLrIncome() {
		return lrIncome;
	}

	public void setLrIncome(LRIncomeView lrIncome) {
		this.lrIncome = lrIncome;
	}

	public static int getCode() {
		return code;
	}
	
}
