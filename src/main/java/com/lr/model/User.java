package com.lr.model;

import javax.xml.bind.annotation.XmlRootElement;

public class User {
	
	private String _username;
	private String _password;
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
	public String get_apiKey() {
		return _apiKey;
	}
	public void set_apiKey(String _apiKey) {
		this._apiKey = _apiKey;
	}

}
