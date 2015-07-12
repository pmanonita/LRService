package com.lr.response;

public class Result {
	private AppResponse result;
	
	public Result(AppResponse result) {
		this.result = result; 
	}

	public AppResponse getResult() {
		return result;
	}

	public void setResult(AppResponse result) {
		this.result = result;
	}

}
