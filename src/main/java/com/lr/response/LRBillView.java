package com.lr.response;

import java.util.Date;

public class LRBillView {

	private long    id;
	private String lrIds;
	private String billDetails;
	private long   totalBill;
	private Date   createDate;
	
    
    public LRBillView() {}


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


	public String getBillDetails() {
		return billDetails;
	}


	public void setBillDetails(String billDetails) {
		this.billDetails = billDetails;
	}


	public long getTotalBill() {
		return totalBill;
	}


	public void setTotalBill(long totalBill) {
		this.totalBill = totalBill;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	

	
   

}
