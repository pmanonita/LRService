package com.lr.service;


import java.util.Date;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lr.db.HibernateSessionManager;
import com.lr.exceptions.AuthException;
import com.lr.exceptions.InsufficientDataException;

import com.lr.model.Consignee;
import com.lr.model.Consigner;
import com.lr.model.LR;
import com.lr.model.LRBill;
import com.lr.model.LRChalan;
import com.lr.model.LRIncome;
import com.lr.model.LROthers;
import com.lr.model.User;

import com.lr.response.LRBillView;
import com.lr.response.LRExpeditureView;
import com.lr.response.LRBillResponse;


public class LRBillService {
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
	
	private LRBill.DefaultController createControllerFromView(final String lrIds,	final String billDetails)					  
			  											
	{
		
		return new LRBill.DefaultController() {
			
			
			public String mLRIds()				    {	return lrIds;	}		
			public String mBillDetails()	        {	return billDetails;	}			
			
		};
	}
	
	private LRBill.Controller createController(final String lrIds,	final String billDetails)
	{
	
	return new LRBill.DefaultController() {
	
	
		public String mLRIds()	{return lrIds;}
		
		
		public String mBillDetails()	{return billDetails; }
		
		
	
	};
	}
	

	public LRBill newLRBill(final String lrNos,final String billDetails)
	{		
		//Get hibernate session manager
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx  = null;
		LRBill lrBill       = null;
		
		try {
			
			tx = session.beginTransaction();			
		
			LRBill.Controller ctrl = createControllerFromView(lrNos,billDetails);
		
			lrBill = new LRBill(ctrl);
			
			session.save(lrBill);			
			session.flush();
			
			tx.commit();		
		
		} catch(RuntimeException  ex) {
			lrBill = null;
			if (tx != null) 	{ tx.rollback(); }
			ex.printStackTrace();			
		} finally {
			session.close();
		}
		
		return lrBill;
	}
	
	public LRBill updateLRBill(final String lrNo,final String billDetails,LRBill lrBill)
	{
		
		//Get hibernate session manager
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx  = null;
		
	
		try {
		
			tx = session.beginTransaction();
		
			LRBill.Controller ctrl = createController(lrNo,billDetails);
					
			//Create user object using controller
			lrBill.changeTo(ctrl);			
		
			session.saveOrUpdate(lrBill);			
			session.flush();
		
			tx.commit();		
	
		} catch(RuntimeException  ex) {
			lrBill = null;
			if (tx != null) 	{ tx.rollback(); }
			ex.printStackTrace();			
		} finally {
			session.close();
		}
	
		return lrBill;
	}
	
	
	
		

	public LRBillResponse createLRBillResponse(LRBill lrBill) 
	{
		
		/** User data visible to UI **/		
		LRBillView lrBillView = new LRBillView();
		lrBillView.setId(lrBill.getId());
		lrBillView.setLrIds(lrBill.getLrIds());
		lrBillView.setBillDetails(lrBill.getBillDetails());
		String billDetails = lrBill.getBillDetails();		
		lrBillView.setTotalCost();	
				
		LRBillResponse response = new LRBillResponse(lrBillView);		
		
		return response;
	}
	
}
