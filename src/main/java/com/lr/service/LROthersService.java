package com.lr.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lr.db.HibernateSessionManager;
import com.lr.exceptions.AuthException;
import com.lr.exceptions.InsufficientDataException;
import com.lr.model.LR;
import com.lr.model.LROthers;
import com.lr.response.LROthersView;
import com.lr.response.LROthersResponse;


public class LROthersService {
	private final static int successCode = 1;
	//private final static int errorCode   = 0;
	
	//view level validation
	public void validateAuthData(String lrId)
		throws InsufficientDataException
	{
		String errorMsg = "";
		if (null != lrId && lrId.equals("")) 
		{
			errorMsg = "LRNo can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}		
	}
	
	private LROthers.DefaultController createControllerFromView(final long lrId,						  
			  											final int amount,
			  											final String remarks) 
	{
		
		return new LROthers.DefaultController() {			
			public long mLRId()			{	return lrId;	}
			public int mAmount()		{	return amount; 	}
			public String mRemarks()	{	return remarks; }
			
		};
	}
	
	public LROthers newLROthers(final long lrId, final int amount, final String remarks) {		
		//Get hibernate session manager
		Session session   = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx    = null;
		LROthers lrOthers = null;
		
		try {
			
			tx = session.beginTransaction();			

			LROthers.Controller ctrl = createControllerFromView(lrId, amount, remarks);
						
			//Create user object using controller
			lrOthers = new LROthers(ctrl);
			
			session.save(lrOthers);			
			session.flush();
			
			tx.commit();		

		} catch(HibernateException  ex) {
			lrOthers = null;
			if (tx != null) 	{ tx.rollback(); }
			ex.printStackTrace();			
		} finally {
			if (session.isOpen()) {
        		session.close();
        	} 
		}
		
		return lrOthers;
	}
	
	//Get LROtherExpenditure from Id
	public LROthers findLROtherExpenditure( long lrOtherExpenditureId ) {
		
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
		LROthers lrOther            = null;
	
		try {
			tx      = session.beginTransaction();        	
			lrOther = LROthers.findLROtherExpenditureById(session, lrOtherExpenditureId);
		
			if (null == lrOther) {
				tx.rollback();
				session.close();    			
				throw new AuthException("LROtherExpenditure Not found"); 
			}		
			tx.commit();    		
		
		} catch (HibernateException e) {
			lrOther = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}	
		return lrOther;        
	}
	
	//Remove LROtherExpenditure
	public boolean removeLROtherExpenditure( LROthers lrOthers ) {
		
		boolean isRemoved = true;
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;		
	
		try {
			tx      = session.beginTransaction(); 
			
			session.delete(lrOthers);			
			session.flush();
			
			tx.commit();	  		
		
		} catch (HibernateException e) {
			isRemoved = false;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}	
		return isRemoved;       
	}

	public LROthersResponse createLROthersResponse(Set<LROthers> lrOtherExps) {
		LROthersView lrOthersView          = null;
		
		List<LROthersView> lrOthers = new ArrayList<LROthersView>();
		if (null != lrOtherExps && lrOtherExps.size() > 0) {
			for (LROthers lrOtherExp : lrOtherExps) {
				if(lrOtherExp != null) {
					lrOthersView = new LROthersView();
					lrOthersView.setId(lrOtherExp.getId());
					lrOthersView.setLrId(lrOtherExp.getLrId());
					lrOthersView.setAmount(lrOtherExp.getAmount());
					lrOthersView.setRemarks(lrOtherExp.getRemarks());
					lrOthers.add(lrOthersView);
				}
			}
		}
				
		LROthersResponse response = new LROthersResponse(lrOthers);		
		
		return response;
	}
	
}
