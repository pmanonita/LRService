package com.lr.response;

public class UserLoginResponse {

	private String username;
	private String email;
	private long mobile;
	private String authToken;      
    private String emailVerified;
    private String mobileVerified;
    
    public UserLoginResponse() {}
    
    public UserLoginResponse(String username,
    						 String email,
    						 long mobile,
    						 String authToken,
    						 String emailVerified,
    						 String mobileVerified)
    {
    	this.username       = username;
    	this.email          = email;
    	this.mobile         = mobile;
    	this.authToken      = authToken;
    	this.emailVerified  = emailVerified;
    	this.mobileVerified = mobileVerified;
    }    

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(String emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getMobileVerified() {
		return mobileVerified;
	}

	public void setMobileVerified(String mobileVerified) {
		this.mobileVerified = mobileVerified;
	}
    

}
