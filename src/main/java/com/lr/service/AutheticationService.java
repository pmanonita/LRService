package com.lr.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lr.db.HibernateSessionManager;
import com.lr.key.ApiKey;
import com.lr.model.User;


public class AutheticationService {

	private static Map<String, String> authToUserNameMap = new HashMap<String, String>();
	
	public static Map<String, String> getAuthToUserNameMap() {
		return authToUserNameMap;
	}
 
    //Check from file rather than checking in db.
    public boolean isServiceKeyValid( String serviceKey ) {
    	
    	if(null == serviceKey || (null != serviceKey)
    							&& serviceKey.equals(""))
    	{
    		return false;
    	}
    	
    	if(serviceKey.equals(ApiKey.getKey())) {
    		return true;
    	}
    	
    	return false;       
    }
    
    public boolean isAuthTokenValid( String serviceKey, String authToken ) {
    	
    	if(null == authToken || (null != authToken
    							&& authToken.equals(""))) 
    	{
    		return false;
    	}
    	
        if ( isServiceKeyValid( serviceKey ) ) {
        	
        	//Check in cache to avoid db calls
        	if (authToUserNameMap.containsKey(authToken)) {
        		return true;
        	}	
        	
        	//Check in db
        	//To-do : Use a single session from a util class. Don't send it to model
        	Session session = HibernateSessionManager.getSessionFactory().openSession();
        	Transaction tx = null;
        	
        	try {
        		tx = session.beginTransaction();        	
        		final int count = User.countByServiceAndAuthKey(session, serviceKey, authToken);        	
        		tx.commit();
        		
        		if (count == 1) {
        			return true;
        		}
        	} catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                e.printStackTrace();
                return false;
             } finally {
                session.close(); 
             }       	
        }
        
        return false;
    }
 
    
 



}
