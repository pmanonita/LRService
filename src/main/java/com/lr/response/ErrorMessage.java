package com.lr.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {
	private String message;
	//private int errorCode;
	
	
	public ErrorMessage()	{}
	
	public ErrorMessage(String errorMessage/*,int errorCode*/)
	{
		super();
		this.message   = errorMessage;
		//this.errorCode = errorCode;		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String errorMessage) {
		this.message = errorMessage;
	}
	
	/*

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}*/

}
