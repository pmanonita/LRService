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
import com.lr.model.LRChalan;
import com.lr.model.LRIncome;
import com.lr.model.LROthers;
import com.lr.model.User;

import com.lr.response.LRChalanView;
import com.lr.response.LRExpeditureView;
import com.lr.response.LRChalanResponse;


public class LRChalanService {
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
	
	private LRChalan.DefaultController createControllerFromView(final String lrIds,	final String chalanDetails)					  
			  											
	{
		
		return new LRChalan.DefaultController() {
			
			
			public String mLRIds()				    {	return lrIds;	}		
			public String mChalanDetails()	        {	return chalanDetails;	}			
			
		};
	}
	
	private LRChalan.Controller createController(final String lrIds,	final String chalanDetails)
	{
	
	return new LRChalan.DefaultController() {
	
	
		public String mLRIds()	{return lrIds;}
		
		
		public String mChalanDetails()	{return chalanDetails; }
		
		
	
	};
	}
	

	public LRChalan newLRChalan(final String lrNos,final String chalanDetails)
	{		
		//Get hibernate session manager
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx  = null;
		LRChalan lrChalan       = null;
		
		try {
			
			tx = session.beginTransaction();			
		
			LRChalan.Controller ctrl = createControllerFromView(lrNos,chalanDetails);
		
			lrChalan = new LRChalan(ctrl);
			
			session.save(lrChalan);			
			session.flush();
			
			tx.commit();		
		
		} catch(RuntimeException  ex) {
			lrChalan = null;
			if (tx != null) 	{ tx.rollback(); }
			ex.printStackTrace();			
		} finally {
			session.close();
		}
		
		return lrChalan;
	}
	
	
	
		

	public LRChalanResponse createLRChalanResponse(LRChalan lrChalan) 
	{
		
		/** User data visible to UI **/		
		LRChalanView lrChalanView = new LRChalanView();
		lrChalanView.setId(lrChalan.getId());
		lrChalanView.setLrIds(lrChalan.getLrIds());
		lrChalanView.setChalanDetails(lrChalan.getChalanDetails());
		String chalanDetails = lrChalan.getChalanDetails();
		//calculation of totalcost
		int totalCost = 0;
		if (chalanDetails != null) {
			String[] expenditureArr = chalanDetails.split(",");
			for (int i=0;i<expenditureArr.length;i++){
				expenditureArr[i] = expenditureArr[i].replaceAll("\"","");
				String[] expeditureColumnArr = expenditureArr[i].split("-");
				if( expeditureColumnArr.length>1 ){
					int amount = 0;
					try{
						amount = Integer.parseInt(expeditureColumnArr[1]);
						totalCost = totalCost+amount;
						
					}catch(Exception e){	}
				}
				
			}
		}
		lrChalanView.setTotalCost(totalCost);
		
				
		LRChalanResponse response = new LRChalanResponse(lrChalanView);		
		
		return response;
	}
	
}
