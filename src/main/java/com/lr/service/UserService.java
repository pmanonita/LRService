package com.lr.service;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.lr.db.HibernateSessionManager;
import com.lr.exceptions.InsufficientDataException;
import com.lr.model.User;

import com.lr.response.LoginResponse;
import com.lr.response.UserLoginResponse;

public class UserService {
	private final static int successCode = 1;
	//private final static int errorCode   = 0;
	
	//view level validation
	public void validateSignUpData(String userName, String password)
		throws InsufficientDataException
	{
		String errorMsg = "";
		if ((null == userName || (null != userName && userName.equals("")))
					|| (null == password ||(null != password && password.equals("")))) 
		{
			errorMsg = "User name and password can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}		
	}	
	
	public boolean signUp(final String serviceKey, final String userName, final String password,
			 final String firstName, final String lastName, final String email, final Long mobile)
	{
		//Get hibernate session manager		
		try {
			System.out.println("Hibernate stuff ...");
			Session session = HibernateSessionManager.getSessionFactory().openSession();		 
			session.beginTransaction();			
			
			//Populate data into controller
			User.Controller ctrl = new User.DefaultController() {				
				@Override
				public String mUserName() 	{	return userName; 	}
				@Override
				public String mPassword() 	{ 	return password; 	}
				@Override
				public String mFirstName() 	{ 	return firstName; }
				@Override
				public String mLastName() 	{ 	return lastName; 	}
				@Override
				public String mEmail() 		{ 	return email; 	}
				@Override
				public Long mMobile() 		{ 	return mobile; }
				@Override
				public String mServiceKey() { 	return serviceKey; }
				@Override
				public String mAuthKey() 	{ 	return null; }
				@Override
				public Date mCreateDate() 	{ 	return new Date(); }
			};
			
			//Create user using controller
			User user = new User(ctrl);
			
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
