package com.lr.model;

import java.io.Serializable;
import java.util.Date;

import com.lr.exceptions.InsufficientDataException;

public class User implements Serializable {	

	private static final long serialVersionUID = -6779738051490200702L;
	
	private int    _userId;
	private String _userName;
	private String _password;
	private String _firstName;
	private String _lastName;	
	private String _email;
	private long   _mobile;
	private String _serviceKey;
	private String _authKey;
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
		public void mCreateDate(Date createDate) 	{	}		
	}

	//getter and setter
	public int getUserId()							{ return _userId; 				}
	protected void setUserId(int userId) 			{ this._userId = userId; 		}
	
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

	public Date getCreateDate() 					{ return _createDate;			}
	private void setCreateDate(Date createDate) 	{ this._createDate = createDate;}

	public static long getSerialversionuid() 		{ return serialVersionUID;		}
	
	
	
}
