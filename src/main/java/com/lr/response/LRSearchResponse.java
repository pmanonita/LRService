package com.lr.response;

import java.util.List;

public class LRSearchResponse implements AppResponse {
	private static final int code = 1; //Success
	private LRView lr;
	private LRExpeditureView lrExpenditure;
	private LRIncomeView lrIncome;
	private List<LROthersView> lrOthers;
	
	public  LRSearchResponse() {}
	
	public int getCode() {
		return code;
	}

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

	public List<LROthersView> getLrOthers() {
		return lrOthers;
	}

	public void setLrOthers(List<LROthersView> lrOthers) {
		this.lrOthers = lrOthers;
	}
	
	

	
	
}
