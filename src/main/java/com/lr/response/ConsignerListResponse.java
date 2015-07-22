package com.lr.response;

import java.util.List;

public class ConsignerListResponse implements AppResponse {
	private static final int code = 1; //Success
	private List<ConsignerView> consigners;
	
	public ConsignerListResponse()	{}
	
	public ConsignerListResponse(List<ConsignerView> consigners) {
		this.consigners = consigners;
	}

	public List<ConsignerView> getConsigners() {
		return consigners;
	}

	public void setConsigners(List<ConsignerView> consigners) {
		this.consigners = consigners;
	}

	public static int getCode() {
		return code;
	}
	
	

}
