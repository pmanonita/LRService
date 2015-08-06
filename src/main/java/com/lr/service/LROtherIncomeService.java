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
import com.lr.model.LROtherIncome;
import com.lr.response.LROtherIncomeView;
import com.lr.response.LROtherIncomeResponse;


public class LROtherIncomeService {
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
	
	private LROtherIncome.DefaultController createControllerFromView(final long lrId,						  
			  											final int amount,
			  											final String remarks) 
	{
		
		return new LROtherIncome.DefaultController() {			
			public long mLRId()			{	return lrId;	}
			public int mAmount()		{	return amount; 	}
			public String mRemarks()	{	return remarks; }
			
		};
	}
	
	public LROtherIncome newLROtherIncome(final long lrId, final int amount, final String remarks) {		
		//Get hibernate session manager
		Session session   = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx    = null;
		LROtherIncome lrOtherIncomes = null;
		
		try {
			
			tx = session.beginTransaction();			

			LROtherIncome.Controller ctrl = createControllerFromView(lrId, amount, remarks);
						
			//Create user object using controller
			lrOtherIncomes = new LROtherIncome(ctrl);
			
			session.save(lrOtherIncomes);			
			session.flush();
			
			tx.commit();		

		} catch(HibernateException  ex) {
			lrOtherIncomes = null;
			if (tx != null) 	{ tx.rollback(); }
			ex.printStackTrace();			
		} finally {
			if (session.isOpen()) {
        		session.close();
        	} 
		}
		
		return lrOtherIncomes;
	}
	
	
	
	//Get LROtherIncome from Id
	public LROtherIncome findLROtherIncome( long lrOtherIncomeId ) {
		
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
		LROtherIncome lrOtherIncome            = null;
	
		try {
			tx      = session.beginTransaction();        	
			lrOtherIncome = LROtherIncome.findLROtherIncomeById(session, lrOtherIncomeId);
		
			if (null == lrOtherIncome) {
				tx.rollback();
				session.close();    			
				throw new AuthException("LROtherIncome Not found"); 
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
	
	//Remove LROtherIncome
	public boolean removeLROtherIncome( LROtherIncome lrOtherIncomes ) {
		
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

	public LROtherIncomeResponse createLROtherIncomeResponse(Set<LROtherIncome> lrOtherIncomeExps) {
		LROtherIncomeView lrOtherIncomesView          = null;
		
		List<LROtherIncomeView> lrOtherIncomes = new ArrayList<LROtherIncomeView>();
		if (null != lrOtherIncomeExps && lrOtherIncomeExps.size() > 0) {
			for (LROtherIncome lrOtherIncomeExp : lrOtherIncomeExps) {
				if(lrOtherIncomeExp != null) {
					lrOtherIncomesView = new LROtherIncomeView();
					lrOtherIncomesView.setId(lrOtherIncomeExp.getId());
					lrOtherIncomesView.setLrId(lrOtherIncomeExp.getLrId());
					lrOtherIncomesView.setAmount(lrOtherIncomeExp.getAmount());
					lrOtherIncomesView.setRemarks(lrOtherIncomeExp.getRemarks());
					lrOtherIncomes.add(lrOtherIncomesView);
				}
			}
		}
				
		LROtherIncomeResponse response = new LROtherIncomeResponse(lrOtherIncomes);		
		
		return response;
	}
	
}
