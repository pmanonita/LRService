package com.lr.service;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lr.db.HibernateSessionManager;
import com.lr.exceptions.AuthException;
import com.lr.exceptions.DataNotFoundException;
import com.lr.exceptions.InsufficientDataException;
import com.lr.exceptions.SignupException;
import com.lr.model.User;
import com.lr.response.AppResponse;
import com.lr.response.UserListResponse;
import com.lr.response.UserResponse;
import com.lr.response.UserView;

public class UserService {	
	
	//view level validation
	public void validateAuthData(String userName, String password)
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
	
	private User.DefaultController createControllerFromView(final String serviceKey, 
															 final String userName,
															 final String password,
			 												 final String firstName,
			 												 final String lastName, 
			 												 final String email,
			 												 final Long   mobile,
			 												 final String role) 
	{
		//Create controller object and populate data
		return new User.DefaultController() {
			public String mUserName() 	{	return userName; 	}
			public String mPassword() 	{ 	return password; 	}
			public String mFirstName() 	{ 	return firstName; 	}			
			public String mLastName() 	{ 	return lastName; 	}
			public String mEmail() 		{ 	return email; 		}
			public Long mMobile() 		{ 	return mobile; 		}			
			public String mServiceKey() { 	return serviceKey; 	}
			public String mAuthKey() 	{ 	return null; 		}
			public Date mCreateDate() 	{ 	return new Date(); 	}
			public String mRole() 		{	return role;		}
		};
	}
	
	//Signup
	public User signUp(final String serviceKey,
						  final String userName,
						  final String password,
						  final String firstName,
						  final String lastName,
						  final String email,
						  final Long   mobile,
						  final String role)
	{
		validateAuthData(userName, password);
		
		//Get hibernate session manager
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx  = null;
		User user       = null;
		
		try {
			
			tx = session.beginTransaction();
			
			//check if user already exists
			user = User.findByUserNameAndServiceKey(session, userName, serviceKey);
			if (user != null && user.getUserName().equalsIgnoreCase(userName)) {
				tx.rollback();
    			session.close();    			
    			throw new SignupException("Signup failure. User already exists");				
			}
			

			User.Controller ctrl = createControllerFromView(serviceKey, userName, password,
					  							    		 firstName, lastName, email,
					  										 mobile, role);
						
			//Create user object using controller
			user = new User(ctrl);
			
			session.save(user);			
			session.flush();
			
			tx.commit();		

		} catch(HibernateException  ex) {
			user = null;
			if (tx != null) 	{ tx.rollback(); }
			ex.printStackTrace();			
		} finally {
			if(session.isOpen()) {
        		session.close();
        	}
		}
		
		return user;
	}
	
	private User.Controller CreateContoller(final String userName,
			                                    final String serviceKey,
			                                    final String role,
			                                    final String password,
			                                    final Long   mobile,
			                                    final String lastName,
			                                    final String firstName,
			                                    final String email,
			                                    final Date   cDate,
			                                    final String authKey) 
	{
		return new User.DefaultController() {
			public String mUserName() 	{	return userName;	}
			public String mServiceKey() {	return serviceKey;	}
			public String mRole() 		{	return role;		}
			public String mPassword() 	{	return password;	}							
			public Long mMobile() 		{	return mobile;		}							
			public String mLastName() 	{	return lastName;	}							
			public String mFirstName() 	{	return firstName;	}							
			public String mEmail() 		{	return email;		}							
			public Date mCreateDate() 	{	return cDate;		}							
			public String mAuthKey() 	{	return authKey;		}
		};
	}
	
	//login
	public User login( String serviceKey, String userName, String password )
		throws AuthException
	{
		validateAuthData(userName, password);
		
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
    	Transaction tx   = null;
    	User user        = null;
    	
    	try {
    		tx = session.beginTransaction();        	
    		user = User.findByUserNameAndServiceKey(session, userName, serviceKey);
    		
    		if (null == user) {
    			tx.rollback();
    			session.close();    			
    			throw new AuthException("Authentication failure. User doesn't exist"); 
    		}    		
    		if ( null != user) {
    			//Check password    			
    			//Wrong password
    			if (null == user.getPassword() 
    					|| (null != user.getPassword()
    						&& (!user.getPassword().equals(password)))) 
    			{
    				tx.rollback();
        			session.close();
        			System.err.println("Authentication failure. Username and password doesn't match");
    				throw new AuthException("Authentication failure. Username and password doesn't match");
    			}    			
    			//Correct Password
    			if(null != user.getPassword() 
    					&& (user.getPassword().equals(password)))
    			{
    				//Check if auth key is not present
    				if( null == user.getAuthKey() ||(null != user.getAuthKey() 
    								&& user.getAuthKey().equals("")))
    				{
    					//Create auth key
    					String authToken = UUID.randomUUID().toString();    					
    					
    					//Get existing data    					
    					final String uName  = user.getUserName();
    					final String pass   = user.getPassword();
    					final String fName  = user.getFirstName();
    					final String lName  = user.getLastName();
    					final String email  = user.getEmail();
    					final long   mobile = user.getMobile();    					
    					final String sKey   = user.getServiceKey();	
    					final String role   = user.getRole();
    					final Date   cDate  = user.getCreateDate();	

    					//New changed data
    					final String authKey = authToken;
   					
    					//Create Controller
    					User.Controller ctrl = CreateContoller(uName,sKey,role,pass,mobile,lName,fName,
    														   email,cDate,authKey);
    					//Update Data
						user.changeTo(ctrl);					
						session.save(user);			
						session.flush();
						
						//store in cache
    					Map<String, String> authToUserNameMap = AutheticationService.getAuthToUserNameMap();
    					if (authToUserNameMap != null) {
    						authToUserNameMap.put(user.getAuthKey(), user.getUserName());
    					}   					
    				}   				
    			}    			
    		}
    		
    		tx.commit();    		
    		
    	} catch (HibernateException e) {
    		user = null;
            if (tx != null) tx.rollback();
            e.printStackTrace();
            
        } finally {
        	if (session.isOpen()) {
        		session.close();
        	}
        }
    	
    	return user;        
    }
	
    //logout	
    public void logout( String serviceKey, String authToken ) 
    	throws GeneralSecurityException 
    {
    	Session session  = HibernateSessionManager.getSessionFactory().openSession();
    	Transaction tx   = null;
    	
    	try {
    		tx = session.beginTransaction();        	
    		final User user = User.findByServiceAndAuthKey(session, serviceKey, authToken);
    		
    		if (null == user) {
    			System.err.println("ERROR ERROR : User not found");
    			throw new GeneralSecurityException( "Invalid service key and authorization token match." );
    		} else {
    			//Get existing data    					
				final String uName  = user.getUserName();
				final String pass   = user.getPassword();
				final String fName  = user.getFirstName();
				final String lName  = user.getLastName();
				final String email  = user.getEmail();
				final long   mobile = user.getMobile();    					
				final String sKey   = user.getServiceKey();	
				final String role   = user.getRole();
				final Date   cDate  = user.getCreateDate();	

				//New changed data
				final String authKey = null;
				
				//Create Controller
				User.Controller ctrl = CreateContoller(uName,sKey,role,pass,mobile,lName,fName,
													   email,cDate,authKey);
				user.changeTo(ctrl);    			
    			session.save(user);			
    			session.flush();
    			
    			//Delete it from cache if present
    			Map<String, String> authToUserNameMap = AutheticationService.getAuthToUserNameMap();
				if (authToUserNameMap != null) {
					if(authToUserNameMap.containsKey(authToken)) {
						authToUserNameMap.remove(authToken);
					}
				}    			
    		}    		
			
    		tx.commit();
    		
    	} catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            
        } finally {
        	if (session.isOpen()) {
        		session.close();
        	} 
        }       
    }	

	public UserResponse createUserResponse(User user) 
	{
		
		/** User data visible to UI **/		
		UserView userView = new UserView();
		userView.setId(user.getId());
		userView.setUserName(user.getUserName());
		userView.setPassword(user.getPassword());
		userView.setFirstName(user.getFirstName());
		userView.setLastName(user.getLastName());
		userView.setEmail(user.getEmail());
		userView.setMobile(user.getMobile());
		userView.setAuthToken(user.getAuthKey());
		userView.setRole(user.getRole());
		
		UserResponse response = new UserResponse(userView);		
		
		return response;
	}

	//list all user
	public List<User> listUser() {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
    	Transaction tx   = null;
    	List<User> userList = null;
    	try {
    		tx = session.beginTransaction();        	
    		userList = User.findAllUsers(session);
    		
    		if (null == userList) {
    			System.err.println("ERROR ERROR : Not able to list users");
    			throw new DataNotFoundException("Not able to list users" );
    		} 			
    		tx.commit();
    		
    	} catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            
        } finally {
        	if (session.isOpen()) {
        		session.close();
        	} 
        }
		
		return userList;		
	}

	public UserListResponse createListUserResponse(List<User> userList) {				
		List<UserView> lUserView  = new ArrayList<UserView>();
		for (User user : userList) {
			if (null != user) {
				UserView userView = new UserView();
				userView.setId(user.getId());
				userView.setUserName(user.getUserName());
				userView.setPassword(user.getPassword());
				userView.setFirstName(user.getFirstName());
				userView.setLastName(user.getLastName());
				userView.setEmail(user.getEmail());
				userView.setMobile(user.getMobile());				
				userView.setRole(user.getRole());				
				lUserView.add(userView);			
			}			
		}
		UserListResponse response = new UserListResponse(lUserView);
		return response;
	}
	
	//Edit user
	public User editUser(final String userId, final String password,
						 final String firstName, final String lastName, final String email,
						 final Long mobile, final String role )
	{	
		if ( userId == null || userId.equals("") ) return null;
		
		Integer uId = null;		
		try {	uId = Integer.parseInt(userId);	}
		catch (NumberFormatException ex) { return null;}
		
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
    	Transaction tx   = null;
    	User user        = null;
    	
    	try {
    		tx = session.beginTransaction();        	
    		user = User.findById(session, uId);    		
    		if (null == user) {
    			tx.rollback();
    			session.close();    			
    			return null; 
    		}			
			//Get existing data that can't be changed from edit screen
			final String uName  = user.getUserName();			    					
			final String sKey   = user.getServiceKey();			
			final Date   cDate  = user.getCreateDate();			
		
			//Create Controller
			User.Controller ctrl = CreateContoller(uName,sKey,role,password,mobile,lastName,firstName,
												   email,cDate,null);
			//Update Data
			user.changeTo(ctrl);			
			session.save(user);			
			session.flush();    		
    		tx.commit();    		
    		
    	} catch (HibernateException e) {
    		user = null;
            if (tx != null) tx.rollback();
            e.printStackTrace();            
        } finally {
        	if (session.isOpen()) {
        		session.close();
        	}
        }
    	
    	return user;        
    }
	
}
