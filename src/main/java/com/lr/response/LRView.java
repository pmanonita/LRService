package com.lr.response;

import com.lr.model.Consignee;
import com.lr.model.Consigner;

public class LRView {

	private long    id;
	private String vehicleNo;
	private String vehicleOwner;
	private Consigner consigner;
	private Consignee consignee;
	private String   servTaxConsigner;
	private String servTaxConsignee;
	private String billingParty;
    
    
    public LRView() {}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
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

	public String getServTaxConsigner() {
		return servTaxConsigner;
	}


	public void setServTaxConsigner(String servTaxConsigner) {
		this.servTaxConsigner = servTaxConsigner;
	}


	public String getServTaxConsignee() {
		return servTaxConsignee;
	}


	public void setServTaxConsignee(String servTaxConsignee) {
		this.servTaxConsignee = servTaxConsignee;
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

   

}
