package com.lr.service;


import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lr.db.HibernateSessionManager;
import com.lr.exceptions.InsufficientDataException;

import com.lr.model.LRExpenditure;

import com.lr.response.LRExpeditureView;
import com.lr.response.LRExpenditureResponse;


public class LRExpenditureService {
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
	
	private LRExpenditure.DefaultController createControllerFromView(final long lrId,						  
			  											final int freightToBroker,
			  											final int extraPayToBroker,
			  											final int advance,
			  											final int balanceFreight,
			  											final int loadingCharges,
			  											final int unloadingCharges,
			  											final int loadingDetBroker,
			  											final int unloadingDetBroker) 
	{
		
		return new LRExpenditure.DefaultController() {
			
			
			public long mLRId()	{return lrId;}
			

			public int mFreightToBroker()	{return freightToBroker; }
			

			public int mExtraPayToBroker()	{return extraPayToBroker; }
			

			public int mAdvance()	{ return advance; }
			
			public int mBalanceFreight()	{return balanceFreight; }
			
			 
			public int mLoadingCharges()	{return loadingCharges; }
			

			public int mUnloadingCharges(){ return unloadingCharges; }
			
			public int mLoadingDetBroker(){ return loadingDetBroker; }
		

			public int mUnloadingDetBroker(){ return unloadingDetBroker;}
			
			
			
		};
	}
	
	public LRExpenditure newLRExpenditure(final long lrId,						  
					final int freightToBroker,
					final int extraPayToBroker,
					final int advance,
					final int balanceFreight,
					final int loadingCharges,
					final int unloadingCharges,
					final int loadingDetBroker,
					final int unloadingDetBroker)
	{
		//validateAuthData(userName, password);
		
		//Get hibernate session manager
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx  = null;
		LRExpenditure lrExpenditure       = null;
		
		try {
			
			tx = session.beginTransaction();
			
			//check if user already exists
			/*user = User.findByUserNameAndServiceKey(session, userName, serviceKey);
			if (user != null && user.mUserName().equalsIgnoreCase(userName)) {
				tx.rollback();
    			session.close();    			
    			throw new SignupException("Signup failure. User already exists");				
			}*/
			

			LRExpenditure.Controller ctrl = createControllerFromView(lrId,						  
															freightToBroker,
															extraPayToBroker,
															advance,
															balanceFreight,
															loadingCharges,
															unloadingCharges,
															loadingDetBroker,
															unloadingDetBroker);
						
			//Create user object using controller
			lrExpenditure = new LRExpenditure(ctrl);
			
			session.save(lrExpenditure);			
			session.flush();
			
			tx.commit();		

		} catch(RuntimeException  ex) {
			lrExpenditure = null;
			if (tx != null) 	{ tx.rollback(); }
			ex.printStackTrace();			
		} finally {
			session.close();
		}
		
		return lrExpenditure;
	}
	
	
	
	

	

	public LRExpenditureResponse createLRExpenditureResponse(LRExpenditure lrExpediture) 
	{
		
		/** User data visible to UI **/		
		LRExpeditureView lrExpenditureView = new LRExpeditureView();
		lrExpenditureView.setId(lrExpediture.getId());
		lrExpenditureView.setLrId(lrExpediture.getLrId());
		lrExpenditureView.setFreightToBroker(lrExpediture.getFreightToBroker());
		lrExpenditureView.setExtraPayToBroker(lrExpediture.getExtraPayToBroker());
		lrExpenditureView.setAdvance(lrExpediture.getAdvance());
		lrExpenditureView.setBalanceFreight(lrExpediture.getBalanceFreight());
		lrExpenditureView.setLoadingCharges(lrExpediture.getLoadingCharges());
		lrExpenditureView.setUnloadingCharges(lrExpediture.getUnloadingCharges());	
		lrExpenditureView.setLoadingDetBroker(lrExpediture.getLoadingDetBroker());	
		lrExpenditureView.setUnloadingDetBroker(lrExpediture.getUnloadingDetBroker());	
		
		LRExpenditureResponse response = new LRExpenditureResponse(lrExpenditureView);		
		
		return response;
	}
	
}
