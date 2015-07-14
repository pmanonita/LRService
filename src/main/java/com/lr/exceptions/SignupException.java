package com.lr.exceptions;

import java.io.Serializable;

public class SignupException extends RuntimeException implements Serializable 
{	

	private static final long serialVersionUID = -3280468180002316264L;

	public SignupException() {
        super();
    }
    
	public SignupException(String msg)   {
        super(msg);
    }
 
    public SignupException(String msg, Exception e)  {
        super(msg, e);
    }	
}