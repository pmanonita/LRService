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
	public void validateAuthData(String lrNo)
		throws InsufficientDataException
	{
		String errorMsg = "";
		if (null != lrNo && lrNo.equals("")) 
		{
			errorMsg = "LRNo can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}		
	}
	
	private LROthers.DefaultController createControllerFromView(final long lrNo,						  
			  											final int amount,
			  											final String remarks) 
	{
		
		return new LROthers.DefaultController() {
			
			
			public long mLRNo()	{return lrNo;}
			

			public int mAmount()	{return amount; }
			

			public String mRemarks()	{return remarks; }
			
			
			
		};
	}
	
	public LROthers newLROthers(final long lrNo,						  
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
			
			//check if user already exists
			/*user = User.findByUserNameAndServiceKey(session, userName, serviceKey);
			if (user != null && user.mUserName().equalsIgnoreCase(userName)) {
				tx.rollback();
    			session.close();    			
    			throw new SignupException("Signup failure. User already exists");				
			}*/
			

			LROthers.Controller ctrl = createControllerFromView(lrNo,						  
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
		lrOthersView.setId(lrOthers.mId());
		lrOthersView.setLrNo(lrOthers.mLRNo());
		lrOthersView.setAmount(lrOthers.mAmount());
		lrOthersView.setRemarks(lrOthers.mRemarks());
		
		LROthersResponse response = new LROthersResponse(lrOthersView);		
		
		return response;
	}
	
}
