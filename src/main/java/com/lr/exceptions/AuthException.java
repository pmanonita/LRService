package com.lr.exceptions;

import java.io.Serializable;

public class AuthException extends RuntimeException implements Serializable 
{	

	private static final long serialVersionUID = -3280468180002316264L;

	public AuthException() {
        super();
    }
    
	public AuthException(String msg)   {
        super(msg);
    }
 
    public AuthException(String msg, Exception e)  {
        super(msg, e);
    }	
}