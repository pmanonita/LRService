package com.lr.response;

import com.lr.model.Billingname;
import com.lr.model.Consignee;
import com.lr.model.Consigner;

public class LRView {

	private long    		id;
	private Long    		transid;	
	private String 			vehicleNo;
	private String 			vehicleOwner;
	private Consigner 		consigner;
	private Consignee 		consignee;	
	private String 			billingParty;
	private String 			lrDate;
	private String 			poNo;
	private String 			doNo;
	private Billingname 	billingname;
	private String 			status;
	private String 			multiLoad;
	private String 			userName;
    
    
    public LRView() {}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}	
	
	
	public Long getTransid() {
		return transid;
	}


	public void setTransid(Long transid) {
		this.transid = transid;
	}


	public String getVehicleNo() {
		return vehicleNo;
	}


	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}


	public String getVehicleOwner() {
		return vehicleOwner;
	}


	public void setVehicleOwner(String vehicleOwner) {
		this.vehicleOwner = vehicleOwner;
	}	

	public String getBillingParty() {
		return billingParty;
	}


	public void setBillingParty(String billingParty) {
		this.billingParty = billingParty;
	}


	public Consigner getConsigner() {
		return consigner;
	}


	public void setConsigner(Consigner consigner) {
		this.consigner = consigner;
	}


	public Consignee getConsignee() {
		return consignee;
	}


	public void setConsignee(Consignee consignee) {
		this.consignee = consignee;
	}


	public String getLrDate() {
		return lrDate;
	}


	public void setLrDate(String lrDate) {
		this.lrDate = lrDate;
	}


	public String getPoNo() {
		return poNo;
	}


	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}


	public String getDoNo() {
		return doNo;
	}


	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}


	public Billingname getBillingname() {
		return billingname;
	}


	public void setBillingname(Billingname billingname) {
		this.billingname = billingname;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getMultiLoad() {
		return multiLoad;
	}


	public void setMultiLoad(String multiLoad) {
		this.multiLoad = multiLoad;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	


	
	
	

   

}
