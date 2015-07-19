package com.lr.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.lr.exceptions.InsufficientDataException;

public class User implements Serializable {	

	private static final long serialVersionUID = -6779738051490200702L;
	
	private int    _id;
	private String _userName;
	private String _password;
	private String _firstName;
	private String _lastName;	
	private String _email;
	private long   _mobile;
	private String _serviceKey;
	private String _authKey;
	private String _role;
	private Date   _createDate;
	
	//For hibernate
	public User() {
		
	}
	
	public User (Controller ctrl) {
		createFrom(ctrl);
		
	}
	
	private void validate(Controller ctrl) throws InsufficientDataException {
		//Model level validation
		String errorMsg = "";
		if ((null == ctrl.mUserName() || (null != ctrl.mUserName() && ctrl.mUserName().equals("")))
				|| (null == ctrl.mPassword() ||(null != ctrl.mPassword() && ctrl.mPassword().equals("")))) 
		{
			errorMsg = "User name and password can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}	
		
	}
	
	private void createFrom(Controller ctrl) {

		validate(ctrl);
		
		_userName   = ctrl.mUserName();
		_password   = ctrl.mPassword();
		_firstName  = ctrl.mFirstName();
		_lastName   = ctrl.mLastName();
		_email      = ctrl.mEmail();
		_mobile     = ctrl.mMobile();
		_serviceKey = ctrl.mServiceKey();
		_authKey    = ctrl.mAuthKey();
		_createDate = ctrl.mCreateDate();
		_role       = ctrl.mRole();
		
	}
	
	public void changeTo(Controller ctrl) {
		
		validate(ctrl);
		
		_userName   = ctrl.mUserName();
		_password   = ctrl.mPassword();
		_firstName  = ctrl.mFirstName();
		_lastName   = ctrl.mLastName();
		_email      = ctrl.mEmail();
		_mobile     = ctrl.mMobile();
		_serviceKey = ctrl.mServiceKey();
		_authKey    = ctrl.mAuthKey();
		_createDate = ctrl.mCreateDate();
		_role       = ctrl.mRole();
		
	}

	public interface Controller {
		String mUserName();
		void mUserName(String username);
		
		String mPassword();
		void mPassword(String password);
		
		String mFirstName();
		void mFirstName(String firstName);
		
		String mLastName();
		void mLastName(String lastName);
		
		String mEmail();
		void mEmail(String email);
		
		Long mMobile();
		void mMobile(Long mobile);
				
		String mServiceKey();
		void mServiceKey(String serviceKey);
		
		String mAuthKey();
		void mAuthKey(String authKey);
		
		String mRole();
		void mRole(String role);
				
		Date mCreateDate();
		void mCreateDate(Date createDate);
		
	}
	
	public abstract static class DefaultController implements Controller {
		public void mUserName(String username) 		{	}
		public void mPassword(String password) 		{	}
		public void mFirstName(String firstName)	{	}
		public void mLastName(String lastName) 		{	}
		public void mEmail(String email) 			{	}
		public void mMobile(Long mobile)	 		{	}
		public void mServiceKey(String serviceKey) 	{	}
		public void mAuthKey(String authKey) 		{	}
		public void mRole(String role)	 			{	}
		public void mCreateDate(Date createDate) 	{	}		
	}

	//getter and setter
	public int getId()								{ return _id; 					}
	protected void setId(int id) 					{ this._id = id; 				}
	
	public String getUserName() 					{ return _userName; 			}
	private void setUserName(String userName)		{ this._userName = userName; 	}
	
	public String getPassword() 					{ return _password;				}
	private void setPassword(String password) 		{ this._password = password;	}
	
	public String getFirstName() 					{ return _firstName;			}
	private void setFirstName(String firstName) 	{ this._firstName = firstName; 	}

	public String getLastName() 					{ return _lastName;				}
	private void setLastName(String lastName) 		{ this._lastName = lastName;	}

	public String getEmail() 						{ return _email;				}
	private void setEmail(String email) 			{ this._email = email;			}

	public long getMobile() 						{ return _mobile;				}
	private void setMobile(long mobile) 			{ this._mobile = mobile;		}

	public String getServiceKey() 					{ return _serviceKey;			}
	private void setServiceKey(String serviceKey)	{ this._serviceKey = serviceKey;}

	public String getAuthKey() 						{ return _authKey;				}
	private void setAuthKey(String authKey) 		{ this._authKey = authKey;		}

	public String getRole() 						{ return _role;					}
	private void setRole(String role) 				{ this._role = role;			}

	public Date getCreateDate() 					{ return _createDate;			}
	private void setCreateDate(Date createDate) 	{ this._createDate = createDate;}

	public static long getSerialversionuid() 		{ return serialVersionUID;		}
	
	
	// Check if user is present for given service and auth key	
    public static int countByServiceAndAuthKey(Session session, String  serviceKey, String authKey)
        throws HibernateException
    {
    	if(null == serviceKey || null == authKey) {
    		return 0;
    	}
        Query qry = session.getNamedQuery(User.class.getName() + ".countByServiceAndAuthKey");
        qry.setString("serviceKey",  serviceKey);
        qry.setString("authKey",    authKey);
        qry.setMaxResults(1);       
        
        final Long ret = (Long) (qry.uniqueResult());        

        return ret.intValue();
    }
    
    private static final String QUERY_FOR_USER_BY_SKEY_AKEY =
    		User.class.getName() + ".findByServiceAndAuthKey";
    public static User findByServiceAndAuthKey(Session session, String  serviceKey, String authKey)
            throws HibernateException
    {
        	if(null == serviceKey || null == authKey) {
        		return null;
        	}
            Query qry = session.getNamedQuery(QUERY_FOR_USER_BY_SKEY_AKEY);
            qry.setString("serviceKey",  serviceKey);
            qry.setString("authKey",    authKey);
            qry.setMaxResults(1);       
            
            final User user = (User)(qry.uniqueResult());
        	return user;
    }

   
    private static final String QUERY_FOR_USER_BY_NAME_SKEY =
    		User.class.getName() + ".findUserByNameAndServiceKey";
    public static User findByUserNameAndServiceKey(Session session, String userName, String serviceKey)
    		throws HibernateException
    {
    	if (serviceKey == null || userName == null) {
    		return null;
    	}
    	Query qry = session.getNamedQuery(QUERY_FOR_USER_BY_NAME_SKEY);
    	qry.setString("userName",    userName);
    	qry.setString("serviceKey",  serviceKey);
        
        qry.setMaxResults(1);
        
        final User user = (User)(qry.uniqueResult());
    	return user;
    }
     
	
	
}
