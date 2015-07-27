package com.lr.response;

public class LRIncomeView {

	private long    id;
	private long lrId;
	private int freightToBroker;
	private int extraPayToBroker;	
	private int loadingCharges;
	private int unloadingCharges;
	private int loadingDetBroker;
	private int unloadingDetBroker;
    
    
    public LRIncomeView() {}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getLrId() {
		return lrId;
	}


	public void setLrId(long lrId) {
		this.lrId = lrId;
	}


	public int getFreightToBroker() {
		return freightToBroker;
	}


	public void setFreightToBroker(int freightToBroker) {
		this.freightToBroker = freightToBroker;
	}


	public int getExtraPayToBroker() {
		return extraPayToBroker;
	}


	public void setExtraPayToBroker(int extraPayToBroker) {
		this.extraPayToBroker = extraPayToBroker;
	}

	public int getLoadingCharges() {
		return loadingCharges;
	}


	public void setLoadingCharges(int loadingCharges) {
		this.loadingCharges = loadingCharges;
	}


	public int getUnloadingCharges() {
		return unloadingCharges;
	}


	public void setUnloadingCharges(int unloadingCharges) {
		this.unloadingCharges = unloadingCharges;
	}


	public int getLoadingDetBroker() {
		return loadingDetBroker;
	}


	public void setLoadingDetBroker(int loadingDetBroker) {
		this.loadingDetBroker = loadingDetBroker;
	}


	public int getUnloadingDetBroker() {
		return unloadingDetBroker;
	}


	public void setUnloadingDetBroker(int unloadingDetBroker) {
		this.unloadingDetBroker = unloadingDetBroker;
	}


	
   

}
