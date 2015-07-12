package com.lr.response;

public class UserResponse implements AppResponse {
	private static final int code = 1; //Success
	private UserView user;
	
	public UserResponse() {}
	public UserResponse(UserView user ) {
		this.user = user;
	}
	
	public int getCode() {
		return code;
	}
	
	public UserView getUser() {
		return user;
	}
	public void setUser(UserView user) {
		this.user = user;
	}

}
