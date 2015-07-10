package com.lr.exceptions;

import java.io.Serializable;

public class InsufficientDataException extends RuntimeException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 944438211313055225L;
	
	public InsufficientDataException() {
        super();
    }
    
	public InsufficientDataException(String msg)   {
        super(msg);
    }
 
    public InsufficientDataException(String msg, Exception e)  {
        super(msg, e);
    }	

}
