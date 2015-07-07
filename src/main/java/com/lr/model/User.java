package com.lr.model;

public class User {	

	private String _username;
	private String _password;
	private String _firstName;
	private String _LastName;
	private String _initials;
	private String _email;
	private long  _mobile;
	private String _apiKey;
	
	public User() {	}	
	
	public String get_username() {
		return _username;
	}
	public void set_username(String _username) {
		this._username = _username;
	}
	public String get_password() {
		return _password;
	}
	public void set_password(String _password) {
		this._password = _password;
	}
	public String get_firstName() {
		return _firstName;
	}

	public void set_firstName(String _firstName) {
		this._firstName = _firstName;
	}

	public String get_LastName() {
		return _LastName;
	}

	public void set_LastName(String _LastName) {
		this._LastName = _LastName;
	}

	public String get_initials() {
		return _initials;
	}

	public void set_initials(String _initials) {
		this._initials = _initials;
	}

	public String get_email() {
		return _email;
	}

	public void set_email(String _email) {
		this._email = _email;
	}

	public long get_mobile() {
		return _mobile;
	}

	public void set_mobile(long _mobile) {
		this._mobile = _mobile;
	}

	public String get_apiKey() {
		return _apiKey;
	}
	public void set_apiKey(String _apiKey) {
		this._apiKey = _apiKey;
	}

}
