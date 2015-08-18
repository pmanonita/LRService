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
import com.lr.model.LRExpenditure;
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
	public void validateAuthData(String lrIds)
		throws InsufficientDataException
	{
		String errorMsg = "";
		if (null != lrIds && lrIds.equals("")) 
		{
			errorMsg = "LRNos can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}		
	}
	
	private LRChalan.DefaultController createControllerFromView(final String lrIds,	final String chalanDetails,
																final long totalCost,final Date createDate)								  
			  											
	{
		
		return new LRChalan.DefaultController() {
			
			
			public String mLRIds()				    {	return lrIds;	}		
			public String mChalanDetails()	        {	return chalanDetails;	}		
			public long mTotalCost()	            {	return totalCost;	}
			public Date mCreateDate()	            {	return createDate;	}
			
		};
	}
	
	private LRChalan.Controller createController(final String lrIds,	final String chalanDetails,
												 final long totalCost,final Date createDate)		
	{
	
	return new LRChalan.DefaultController() {
	
	
		public String mLRIds()	{return lrIds;}
		public String mChalanDetails()	{return chalanDetails; }		
		public long   mTotalCost()	{return totalCost; }
		public Date   mCreateDate()	{return createDate; }
		
		
	
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
			
			Date createDate = new Date();
			long totalCost  = calculateTotalCost(chalanDetails);
		
			LRChalan.Controller ctrl = createControllerFromView(lrNos,chalanDetails,totalCost,createDate);
		
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
	
	public long calculateTotalCost(String chalanDetails) {
		//calculation of totalCost
		long totalCost = 0;
		if (chalanDetails != null) {
			chalanDetails = chalanDetails.replaceAll("\"","");
			String[] costArr = chalanDetails.split(",");
			for (int i=0;i<costArr.length;i++){
				costArr[i] = costArr[i].replaceAll("\"","");
				String[] costColumnArr = costArr[i].split("-");
				if( costColumnArr.length>1 ){
					System.out.println("addingcost for "+costColumnArr[0]+" currenttotalbill is "+totalCost);
					int amount = 0;
					if (costColumnArr[0].contains("EXTRA PAY TO BROKER") || costColumnArr[0].contains("ADVANCE") || costColumnArr[0].contains("BALANCE FREIGHT") ) {
						continue;
					} else {
						try{
							amount = Integer.parseInt(costColumnArr[1].trim());
							totalCost = totalCost+amount;
							System.out.println("totl cost is "+totalCost);
							
						}catch(Exception e){	}
						
					}
					
				}
				
			}
		}
		return totalCost;		
	}
	
	public LRChalan updateLRChalan(final String lrNo,final String chalanDetails,LRChalan lrChalan)
	{
		
		//Get hibernate session manager
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx  = null;
		
	
		try {
		
			tx = session.beginTransaction();
			
			Date createDate = lrChalan.getCreateDate();
			long totalCost  = calculateTotalCost(chalanDetails);
			
			LRChalan.Controller ctrl = createController(lrNo,chalanDetails,totalCost,createDate);
					
			//Create user object using controller
			lrChalan.changeTo(ctrl);			
		
			session.saveOrUpdate(lrChalan);			
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
		lrChalanView.setTotalCost(lrChalan.getTotalCost());
		lrChalanView.setCreateDate(lrChalan.getCreateDate());
		
		LRChalanResponse response = new LRChalanResponse(lrChalanView);		
		
		return response;
	}
	
}
