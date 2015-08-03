package com.lr.service;


import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lr.db.HibernateSessionManager;
import com.lr.exceptions.AuthException;
import com.lr.exceptions.InsufficientDataException;

import com.lr.model.Consigner;
import com.lr.model.LR;
import com.lr.model.LRExpenditure;
import com.lr.model.LRIncome;
import com.lr.model.User;

import com.lr.response.LRExpeditureView;
import com.lr.response.LRExpenditureResponse;
import com.lr.response.LRIncomeResponse;
import com.lr.response.LRIncomeView;


public class LRIncomeService {
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
	
	private LRIncome.DefaultController createControllerFromView(final long lrId,						  
			  													final int freightToBroker,
			  													final int extraPayToBroker,			  											
			  													final int loadingCharges,
			  													final int unloadingCharges,
			  													final int loadingDetBroker,
			  													final int unloadingDetBroker) 
	{
		
		return new LRIncome.DefaultController() {		
			
			public long mLRId()					{	return lrId;				}
			public int  mFreightToBroker()		{	return freightToBroker; 	}			
			public int  mExtraPayToBroker()		{ 	return extraPayToBroker; 	}
			public int  mLoadingCharges()		{ 	return loadingCharges; 		}
			public int  mUnloadingCharges()		{ 	return unloadingCharges; 	}
			public int  mLoadingDetBroker()		{ 	return loadingDetBroker; 	}
			public int  mUnloadingDetBroker()	{ 	return unloadingDetBroker;	}			
		};
	}
	
	private LRIncome.Controller createController(final long lrId,						  
				final int freightToBroker,
				final int extraPayToBroker,			  											
				final int loadingCharges,
				final int unloadingCharges,
				final int loadingDetBroker,
				final int unloadingDetBroker) 
	{
	
		return new LRIncome.DefaultController() {		
		
		public long mLRId()					{	return lrId;				}
		public int  mFreightToBroker()		{	return freightToBroker; 	}			
		public int  mExtraPayToBroker()		{ 	return extraPayToBroker; 	}
		public int  mLoadingCharges()		{ 	return loadingCharges; 		}
		public int  mUnloadingCharges()		{ 	return unloadingCharges; 	}
		public int  mLoadingDetBroker()		{ 	return loadingDetBroker; 	}
		public int  mUnloadingDetBroker()	{ 	return unloadingDetBroker;	}			
		};
	}
	
	public LRIncome newLRIncome(final long lrId,						  
								final int freightToBroker,
								final int extraPayToBroker,					
								final int loadingCharges,
								final int unloadingCharges,
								final int loadingDetBroker,
								final int unloadingDetBroker)
	{

		
		//Get hibernate session manager
		Session session   = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx    = null;
		LRIncome lrIncome = null;
		
		try {
			
			tx = session.beginTransaction();
			
			LRIncome.Controller ctrl = createControllerFromView(lrId,						  
																freightToBroker,
																extraPayToBroker,															
																loadingCharges,
																unloadingCharges,
																loadingDetBroker,
																unloadingDetBroker);
						
			//Create user object using controller
			lrIncome = new LRIncome(ctrl);
			
			session.save(lrIncome);			
			session.flush();
			
			tx.commit();		

		} catch(RuntimeException  ex) {
			lrIncome = null;
			if (tx != null) 	{ tx.rollback(); }
			ex.printStackTrace();			
		} finally {
			if (session.isOpen()) {
        		session.close();
        	} 
		}
		
		return lrIncome;
	}
	
	public LRIncome updateLRIncome(final long lrId,						  
			final int freightToBroker,
			final int extraPayToBroker,					
			final int loadingCharges,
			final int unloadingCharges,
			final int loadingDetBroker,
			final int unloadingDetBroker,
			LRIncome lrIncome)
	{
	
	
		//Get hibernate session manager
		Session session   = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx    = null;
		
		try {
		
		tx = session.beginTransaction();
		
		LRIncome.Controller ctrl = createController(lrId,						  
													freightToBroker,
													extraPayToBroker,															
													loadingCharges,
													unloadingCharges,
													loadingDetBroker,
													unloadingDetBroker);
			
		//Create user object using controller
		lrIncome.changeTo(ctrl);
		
		session.saveOrUpdate(lrIncome);			
		session.flush();
		
		tx.commit();		
		
		} catch(RuntimeException  ex) {
		lrIncome = null;
		if (tx != null) 	{ tx.rollback(); }
		ex.printStackTrace();			
		} finally {
		if (session.isOpen()) {
		session.close();
		} 
		}
		
		return lrIncome;
	}
	
		

	public LRIncomeResponse createLRIncomeResponse(LRIncome lrIncome) 
	{
		
		/** User data visible to UI **/		
		LRIncomeView lrIncomeView = new LRIncomeView();
		lrIncomeView.setId(lrIncome.getId());
		lrIncomeView.setLrId(lrIncome.getLrId());
		lrIncomeView.setFreightToBroker(lrIncome.getFreightToBroker());
		lrIncomeView.setExtraPayToBroker(lrIncome.getExtraPayToBroker());		
		lrIncomeView.setLoadingCharges(lrIncome.getLoadingCharges());
		lrIncomeView.setUnloadingCharges(lrIncome.getUnloadingCharges());	
		lrIncomeView.setLoadingDetBroker(lrIncome.getLoadingDetBroker());	
		lrIncomeView.setUnloadingDetBroker(lrIncome.getUnloadingDetBroker());	
		
		LRIncomeResponse response = new LRIncomeResponse(lrIncomeView);		
		
		return response;
	}
	
}
