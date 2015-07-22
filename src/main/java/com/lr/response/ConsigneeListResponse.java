package com.lr.response;

import java.util.List;

public class ConsigneeListResponse implements AppResponse {
	private static final int code = 1; //Success
	private List<ConsigneeView> consignees;
	
	public ConsigneeListResponse()	{}
	
	public ConsigneeListResponse(List<ConsigneeView> consignees) {
		this.consignees = consignees;
	}

	public List<ConsigneeView> getConsignees() {
		return consignees;
	}

	public void setConsignees(List<ConsigneeView> consignees) {
		this.consignees = consignees;
	}

	public static int getCode() {
		return code;
	}
	
	

}
