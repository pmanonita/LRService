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
import com.lr.model.LROtherIncome;
import com.lr.model.LRTransOtherExp;
import com.lr.response.lrtransaction.LRTransOtherExpResponse;
import com.lr.response.lrtransaction.LRTransOtherExpView;


public class LRTransOtherExpService {
	private final static int successCode = 1;
	//private final static int errorCode   = 0;
	
	//view level validation
	public void validateAuthData(String transid)
		throws InsufficientDataException
	{
		String errorMsg = "";
		if (null != transid && transid.equals("")) 
		{
			errorMsg = "Transid can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}		
	}
	
	private LRTransOtherExp.DefaultController createControllerFromView(final long lrId,						  
			  											final int amount,
			  											final String remarks) 
	{
		
		return new LRTransOtherExp.DefaultController() {			
			public long mTransid()			{	return lrId;	}
			public int mAmount()		{	return amount; 	}
			public String mRemarks()	{	return remarks; }
			
		};
	}
	
	public LRTransOtherExp newLRTransOtherExp(final long transid, final int amount, final String remarks) {		
		//Get hibernate session manager
		Session session   = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx    = null;
		LRTransOtherExp lrOthers = null;
		
		try {
			
			tx = session.beginTransaction();			

			LRTransOtherExp.Controller ctrl = createControllerFromView(transid, amount, remarks);
						
			//Create user object using controller
			lrOthers = new LRTransOtherExp(ctrl);
			
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
	public LRTransOtherExp findLROtherExpenditure( long lrOtherExpenditureId ) {
		
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
		LRTransOtherExp lrTransOtherExp            = null;
	
		try {
			tx      = session.beginTransaction();        	
			lrTransOtherExp = LRTransOtherExp.findLRTransOtherExpenditureById(session, lrOtherExpenditureId);
		
			if (null == lrTransOtherExp) {
				tx.rollback();
				session.close();    			
				throw new AuthException("LRTransOtherExpenditure Not found"); 
			}		
			tx.commit();    		
		
		} catch (HibernateException e) {
			lrTransOtherExp = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}	
		return lrTransOtherExp;        
	}
	
	//Remove LRTransOtherExpenditure
	public boolean removeLRTransOtherExpenditure( LRTransOtherExp lrTransOtherExp ) {
		
		boolean isRemoved = true;
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;		
	
		try {
			tx      = session.beginTransaction(); 
			
			session.delete(lrTransOtherExp);			
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

	public LRTransOtherExpResponse createLRTransOtherExpResponse(Set<LRTransOtherExp> lrTransOtherExps) {
		LRTransOtherExpView lrOthersView          = null;
		
		List<LRTransOtherExpView> lrTransOtherExpViews = new ArrayList<LRTransOtherExpView>();
		if (null != lrTransOtherExps && lrTransOtherExps.size() > 0) {
			for (LRTransOtherExp lrOtherExp : lrTransOtherExps) {
				if(lrOtherExp != null) {
					lrOthersView = new LRTransOtherExpView();
					lrOthersView.setId(lrOtherExp.getId());
					lrOthersView.setTransid(lrOtherExp.getTransid());
					lrOthersView.setAmount(lrOtherExp.getAmount());
					lrOthersView.setRemarks(lrOtherExp.getRemarks());
					lrTransOtherExpViews.add(lrOthersView);
				}
			}
		}
				
		LRTransOtherExpResponse response = new LRTransOtherExpResponse(lrTransOtherExpViews);		
		
		return response;
	}
	
}
