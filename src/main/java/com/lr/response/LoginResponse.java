package com.lr.response;

public class LoginResponse implements Response {
	private int code;
	private UserLoginResponse user;
	
	public LoginResponse() {}
	public LoginResponse(int code, UserLoginResponse user ) {
		this.code = code;
		this.user = user;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public UserLoginResponse getUser() {
		return user;
	}
	public void setUser(UserLoginResponse user) {
		this.user = user;
	}

}
