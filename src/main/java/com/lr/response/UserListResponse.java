package com.lr.response;

import java.util.List;

public class UserListResponse implements AppResponse {
	private static final int code = 1; //Success
	private List<UserView> users;
	
	public UserListResponse()	{}
	
	public static int getCode() {
		return code;
	}
	
	public UserListResponse(List<UserView> users) {
		this.users = users;
	}

	public List<UserView> getUsers() {
		return users;
	}
	public void setUsers(List<UserView> users) {
		this.users = users;
	}		 

}
