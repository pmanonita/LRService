package com.lr.service;


import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lr.db.HibernateSessionManager;
import com.lr.exceptions.InsufficientDataException;

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
			
			
			public long mLRId()	{return lrId;}
			

			public int mAmount()	{return amount; }
			

			public String mRemarks()	{return remarks; }
			
			
			
		};
	}
	
	public LROthers newLROthers(final long lrId,						  
					final int amount,
					final String remarks)
	{
		//validateAuthData(userName, password);
		
		//Get hibernate session manager
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx  = null;
		LROthers lrOthers       = null;
		
		try {
			
			tx = session.beginTransaction();
			
			

			LROthers.Controller ctrl = createControllerFromView(lrId,						  
															amount,
															remarks);
						
			//Create user object using controller
			lrOthers = new LROthers(ctrl);
			
			session.save(lrOthers);			
			session.flush();
			
			tx.commit();		

		} catch(RuntimeException  ex) {
			lrOthers = null;
			if (tx != null) 	{ tx.rollback(); }
			ex.printStackTrace();			
		} finally {
			session.close();
		}
		
		return lrOthers;
	}
	
	
	
	

	

	public LROthersResponse createLROthersResponse(LROthers lrOthers) 
	{
		
		/** User data visible to UI **/		
		LROthersView lrOthersView = new LROthersView();
		lrOthersView.setId(lrOthers.getId());
		lrOthersView.setLrId(lrOthers.getLrId());
		lrOthersView.setAmount(lrOthers.getAmount());
		lrOthersView.setRemarks(lrOthers.getRemarks());
		
		LROthersResponse response = new LROthersResponse(lrOthersView);		
		
		return response;
	}
	
}
