package com.lr.response;

public class ConsigneeView {
	
	private long   id;
	private String consigneeCode;	
	private String consigneeName;	
	private String address;	
	private String serviceTax;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getConsigneeCode() {
		return consigneeCode;
	}
	public void setConsigneeCode(String consigneeCode) {
		this.consigneeCode = consigneeCode;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(String serviceTax) {
		this.serviceTax = serviceTax;
	}
	
	
   

}
