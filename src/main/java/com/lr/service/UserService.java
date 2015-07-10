package com.lr.service;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.lr.db.HibernateSessionManager;
import com.lr.model.User;
import com.lr.response.LoginResponse;
import com.lr.response.UserLoginResponse;

public class UserService {
	private final static int successCode = 1;
	private final static int errorCode   = 0;
	
	
	//Validate Signup data (to do : Move it to model)
	private boolean validateSignUpData(String userName, String password) {
		// TODO Auto-generated method stub
		String errorMsg = "";
		if (null == userName && userName.equals("")) {
			errorMsg = "User name can't be null or empty";
			//To -do : throw user defined exception to frontend.
			return false;
		}
		if (null == userName && userName.equals("")) {
			errorMsg = "Password can't be null or empty";
			//To -do : throw user defined exception to frontend.
			return false;
		}		
		return true;
	}
	
	//Signup (to do - Move it to model)
	public boolean signUp(String serviceKey, String userName, String password,
								 String firstName, String lastName, String email,
								 String mobile)
	{
		if (! validateSignUpData(userName, password)) return false; //Remove it once exception are implemented in validate
		
		//Get hibernate session manager		
		try {
			System.out.println("Hibernate stuff ...");
			Session session = HibernateSessionManager.getSessionFactory().openSession();		 
			session.beginTransaction();
			User user = new User();
						
			user.setUserName(userName);
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			try {
			    user.setMobile(Long.parseLong(mobile));
			} catch (NumberFormatException ex) {
				//Set it to some default			
				user.setMobile(0);
			}
			user.setServiceKey(serviceKey);
			user.setAuthKey(""); //Default
			
			user.setCreateDate(new Date());
						 
			session.save(user);
			session.getTransaction().commit();
			
			return true;
			
		} catch(HibernateException ex) {
			System.out.println("Hibernate exception" + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
		
	}
	
	
	
	
	




	public LoginResponse createLoginResponse(AutheticationService authService,
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
