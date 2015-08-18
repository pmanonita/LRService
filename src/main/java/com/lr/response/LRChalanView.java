package com.lr.response;

import java.util.Date;

public class LRChalanView {

	private long    id;
	private String lrIds;
	private String chalanDetails;
	private long totalCost;
	private Date   createDate;
	
    
    public LRChalanView() {}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getLrIds() {
		return lrIds;
	}


	public void setLrIds(String lrIds) {
		this.lrIds = lrIds;
	}


	public String getChalanDetails() {
		return chalanDetails;
	}


	public void setChalanDetails(String chalanDetails) {
		this.chalanDetails = chalanDetails;
	}


	public long getTotalCost() {
		return totalCost;
	}


	public void setTotalCost(long totalCost) {
		this.totalCost = totalCost;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
