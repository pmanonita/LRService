package com.lr.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lr.db.HibernateSessionManager;
import com.lr.exceptions.AuthException;
import com.lr.exceptions.DataNotFoundException;
import com.lr.model.AccountSource;
import com.lr.model.Consignee;
import com.lr.model.User;
import com.lr.response.AppResponse;
import com.lr.response.UserListResponse;
import com.lr.response.UserView;

public class AccountService {	
	
	
	public AccountSource findAccount( String strAccountId ) {
		
		if (null == strAccountId ||
				(null != strAccountId && strAccountId.equals("")))
		{
			return null;
		}
		
		Integer iaccountId = null;		
		try {
			iaccountId = Integer.parseInt(strAccountId);
		}	catch (NumberFormatException ex) { 
			return null;
		}
		
		Session session       = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx        = null;
		AccountSource account = null;		
	
		try {
			tx = session.beginTransaction();        	
			account = AccountSource.findById(session, iaccountId);
		
			if (null == account) {
				tx.rollback();
				session.close();    			
				throw new DataNotFoundException("Account Not found"); 
			}
		
			tx.commit();    		
		
		} catch (HibernateException e) {
			account = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return account;        
	}

	//list accounts
	public List<AccountSource> listAccount() {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
    	Transaction tx   = null;    	
    	List<AccountSource> accountList = null;
    	try {
    		tx = session.beginTransaction();        	
    		accountList = AccountSource.findAllAccounts(session);
    		
    		if (null == accountList) {
    			System.err.println("ERROR ERROR : Not able to list Accounts");
    			throw new DataNotFoundException("Not able to list Accounts" );
    		} 			
    		tx.commit();
    		
    	} catch (HibernateException e) {
    		accountList = null;
            if (tx != null) tx.rollback();
            e.printStackTrace();
            
        } finally {
        	if (session.isOpen()) {
        		session.close();
        	} 
        }
		
		return accountList;
	}		
			
}
