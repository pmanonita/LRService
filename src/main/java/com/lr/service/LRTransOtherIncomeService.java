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
import com.lr.model.LRTransOtherIncome;
import com.lr.response.lrtransaction.LRTransOtherIncomeResponse;
import com.lr.response.lrtransaction.LRTransOtherIncomeView;



public class LRTransOtherIncomeService {
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
	
	private LRTransOtherIncome.DefaultController createControllerFromView(final long transid,						  
			  											final int amount,
			  											final String remarks) 
	{
		
		return new LRTransOtherIncome.DefaultController() {			
			public long mTransid()		{	return transid;	}
			public int mAmount()		{	return amount; 	}
			public String mRemarks()	{	return remarks; }
			
		};
	}
	
	public LRTransOtherIncome newLRTransOtherIncome(final long transid, final int amount, final String remarks) {		
		//Get hibernate session manager
		Session session   = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx    = null;
		LRTransOtherIncome lrTransOtherIncomes = null;
		
		try {
			
			tx = session.beginTransaction();			

			LRTransOtherIncome.Controller ctrl = createControllerFromView(transid, amount, remarks);
						
			//Create user object using controller
			lrTransOtherIncomes = new LRTransOtherIncome(ctrl);
			
			session.save(lrTransOtherIncomes);			
			session.flush();
			
			tx.commit();		

		} catch(HibernateException  ex) {
			lrTransOtherIncomes = null;
			if (tx != null) 	{ tx.rollback(); }
			ex.printStackTrace();			
		} finally {
			if (session.isOpen()) {
        		session.close();
        	} 
		}
		
		return lrTransOtherIncomes;
	}
	
	
	
	//Get LRTransOtherIncome from Id
	public LRTransOtherIncome findLRTransOtherIncome( long lrOtherIncomeId ) {
		
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
		LRTransOtherIncome lrOtherIncome            = null;
	
		try {
			tx      = session.beginTransaction();        	
			lrOtherIncome = LRTransOtherIncome.findLRTransOtherIncomeById(session, lrOtherIncomeId);
		
			if (null == lrOtherIncome) {
				tx.rollback();
				session.close();    			
				throw new AuthException("LRTransOtherIncome Not found"); 
			}		
			tx.commit();    		
		
		} catch (HibernateException e) {
			lrOtherIncome = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}	
		return lrOtherIncome;        
	}
	
	//Remove LRTransOtherIncome
	public boolean removeLRTransOtherIncome( LRTransOtherIncome lrOtherIncomes ) {
		
		boolean isRemoved = true;
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;		
	
		try {
			tx      = session.beginTransaction(); 
			
			session.delete(lrOtherIncomes);			
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

	public LRTransOtherIncomeResponse createLRTransOtherIncomeResponse(Set<LRTransOtherIncome> lrOtherIncomeExps) {
		LRTransOtherIncomeView lrTransOtherIncomesView          = null;
		
		List<LRTransOtherIncomeView> lrTransOtherIncomeViews = new ArrayList<LRTransOtherIncomeView>();
		if (null != lrOtherIncomeExps && lrOtherIncomeExps.size() > 0) {
			for (LRTransOtherIncome lrOtherIncomeExp : lrOtherIncomeExps) {
				if(lrOtherIncomeExp != null) {
					lrTransOtherIncomesView = new LRTransOtherIncomeView();
					lrTransOtherIncomesView.setId(lrOtherIncomeExp.getId());
					lrTransOtherIncomesView.setTransid(lrOtherIncomeExp.getTransid());
					lrTransOtherIncomesView.setAmount(lrOtherIncomeExp.getAmount());
					lrTransOtherIncomesView.setRemarks(lrOtherIncomeExp.getRemarks());
					lrTransOtherIncomeViews.add(lrTransOtherIncomesView);
				}
			}
		}
				
		LRTransOtherIncomeResponse response = new LRTransOtherIncomeResponse(lrTransOtherIncomeViews);		
		
		return response;
	}
	
}
