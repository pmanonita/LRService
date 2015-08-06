package com.lr.response;

import java.util.List;

import com.lr.model.LRBill;
import com.lr.model.LRChalan;

public class LRSearchResponse implements AppResponse {
	private static final int code = 1; //Success
	private LRView lr;
	private LRExpeditureView lrExpenditure;
	private LRChalan lrChalan;
	private LRBill lrBill;
	private LRIncomeView lrIncome;
	private List<LROthersView> lrOthers;
	private List<LROtherIncomeView> lrOtherIncome;
	
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

	public List<LROtherIncomeView> getLrOtherIncome() {
		return lrOtherIncome;
	}

	public void setLrOtherIncome(List<LROtherIncomeView> lrOtherIncome) {
		this.lrOtherIncome = lrOtherIncome;
	}

	public LRChalan getLrChalan() {
		return lrChalan;
	}

	public void setLrChalan(LRChalan lrChalan) {
		this.lrChalan = lrChalan;
	}

	public LRBill getLrBill() {
		return lrBill;
	}

	public void setLrBill(LRBill lrBill) {
		this.lrBill = lrBill;
	}
	
	
	
	
	
	

	
	
}
