package com.lr.response;

import java.util.List;
import com.lr.model.AccountSource;

public class AccountListResponse implements AppResponse {
	private static final int code = 1; //Success
	private List<AccountSource> accounts;
	
	public AccountListResponse()	{}
	
	public AccountListResponse(List<AccountSource> accounts) {
		this.accounts = accounts;
	}
	
	public static int getCode() {
		return code;
	}

	public List<AccountSource> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountSource> accounts) {
		this.accounts = accounts;
	} 

}
