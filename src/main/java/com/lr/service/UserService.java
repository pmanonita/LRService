package com.lr.service;

import com.lr.response.LoginResponse;
import com.lr.response.UserLoginResponse;

public class UserService {
	private final static int successCode = 1;
	private final static int errorCode   = 0;
	
	
	public static LoginResponse createLoginResponse(AutheticationService authService,
													String authToken,
													String serviceKey) 
	{

		//To-do: pull data from db for the user and construct response
		
		/** Code **/
		int code = successCode;
		
		/** User data visible to UI **/		
		//Hard coded as the data not avilable in db
		UserLoginResponse user = new UserLoginResponse(authService.getUserName(serviceKey),
													   "test@gmail.com",
													   123456789,
													   authToken,
													   "no",
													   "no");
		LoginResponse response = new LoginResponse(code, user);		
		
		return response;
	}	
	
}
