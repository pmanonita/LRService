package com.lr.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;




import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lr.db.HibernateSessionManager;
import com.lr.exceptions.AuthException;
import com.lr.exceptions.DataNotFoundException;
import com.lr.exceptions.InsufficientDataException;
import com.lr.model.Consignee;
import com.lr.model.Consigner;
import com.lr.model.LR;
import com.lr.model.LRExpenditure;
import com.lr.model.LRIncome;
import com.lr.model.LROthers;
import com.lr.model.User;
import com.lr.response.ConsigneeListResponse;
import com.lr.response.ConsigneeView;
import com.lr.response.ConsignerListResponse;
import com.lr.response.ConsignerView;
import com.lr.response.LRResponse;
import com.lr.response.LRView;


public class LrService {
	
	//view level validation
	public void validateAuthData(String lrNo)
		throws InsufficientDataException
	{
		String errorMsg = "";
		if (null != lrNo && lrNo.equals("")) 
		{
			errorMsg = "LR Number can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}		
	}
	
	private LR.DefaultController createControllerFromView(final String vehileNo,
			  											  final String vehicleOwner,
			  											  final Consigner consigner,
			  											  final Consignee consignee,			  											
			  											  final String billingParty)
	{
		
		return new LR.DefaultController() {
			public long mTransid() 					{	return 0;				}			
			public String mVehicleNo() 				{	return vehileNo;		}
			public Consigner mConsignerId() 		{	return consigner;		}
			public Consignee mConsigneeId() 		{	return consignee;		}
			public String mVehicleOwner() 			{	return vehicleOwner;	}
			public String mBillingToParty()	 		{	return billingParty;	}
			public Date mLrDate() 					{	return new Date();		}
			public String mMultiLoad() 				{	return null;			}
			public String mUserName() 				{ 	return "";				}
			public LRExpenditure mLrexpenditureId()	{ 	return null; 			}
			public LRIncome mLrincomeId()			{ 	return null; 			}
			public Set mOtherExpenditures() 		{ 	return null; 			}			
		};
	}

	private LR.Controller CreateContoller(final String vehileNo,
										  final String vehicleOwner,
										  final Consigner consigner,
										  final Consignee consignee,			  											
										  final String billingParty,
										  final LRExpenditure lrExpenditure,
										  final LRIncome lrIncome,
										  final Set otherexpeditures) 
	{
		return new LR.DefaultController() {		
			public long mTransid() 					{	return 0;				}
			public String mVehicleNo() 				{	return vehileNo;		}		
			public Consigner mConsignerId() 		{	return consigner;		}
			public Consignee mConsigneeId() 		{	return consignee;		}
			public String mVehicleOwner() 			{	return vehicleOwner;	}
			public String mBillingToParty() 		{	return billingParty;	}
			public Date mLrDate() 					{	return new Date();		}
			public String mMultiLoad()				{	return null;			}
			public String mUserName() 				{	return "";				}
			public LRExpenditure mLrexpenditureId()	{ 	return lrExpenditure; 	}
			public LRIncome mLrincomeId()			{ 	return lrIncome; 		}
			public Set mOtherExpenditures() 		{ 	return null; 			}	
		};
	}

	//Create new LR
	public LR newLR(final String serviceKey,						  
					final String vehileNo,
					final String vehicleOwner,
					final Consigner consigner,
					final Consignee consignee,						  
					final String billingParty)
	{		
		//Get hibernate session manager
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx  = null;
		LR lr       = null;
		
		try {
			
			tx = session.beginTransaction();			

			LR.Controller ctrl = createControllerFromView(vehileNo,
														  vehicleOwner,
														  consigner,
														  consignee,															
														  billingParty);

			lr = new LR(ctrl);
			
			session.save(lr);			
			session.flush();
			
			tx.commit();		

		} catch(RuntimeException  ex) {
			lr = null;
			if (tx != null) 	{ tx.rollback(); }
			ex.printStackTrace();			
		} finally {
			session.close();
		}
		
		return lr;
	}
	
	//Get LR from Id
	public LR getLr( String lrNo ) {

		if ( lrNo == null || lrNo.equals("") ) return null;
		
		Long lrId = null;		
		try {
			lrId = Long.parseLong(lrNo);
		}	catch (NumberFormatException ex) { 
			return null;
		}
	
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
		LR lr        = null;
	
		try {
			tx = session.beginTransaction();        	
			lr = LR.findLRById(session, lrId);
		
			if (null == lr) {
				tx.rollback();
				session.close();    			
				throw new AuthException("LR Not found"); 
			}		
			tx.commit();    		
		
		} catch (HibernateException e) {
			lr = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}	
		return lr;        
	}
	
	public LR updateExpenditureToLR(LRExpenditure lrExpenditure, LR lr) {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
	
		try {
			tx = session.beginTransaction();  
			//Create Controller
			LR.Controller ctrl = CreateContoller(lr.getVehicleNo(),
												 lr.getVehicleOwner(),
												 lr.getConsignerId(),
												 lr.getConsigneeId(),
												 lr.getBillingToParty(),
												 lrExpenditure,
												 lr.getLrincomeId(),
												 lr.getOtherExpenditures());
								
			//Update Data
			lr.changeTo(ctrl);			
			
			session.saveOrUpdate(lr);			
			session.flush();
					
			tx.commit();    		
		
		} catch (HibernateException e) {
			lr = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return lr; 
	}
	
	public LR updateLR(final String vehileNo,
			  final String vehicleOwner,
			  final Consigner consigner,
			  final Consignee consignee,						  
			  final String billingParty,
			  LR lr) {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
		
	
		try {
			tx = session.beginTransaction();  
			//Create Controller
			LR.Controller ctrl = CreateContoller(vehileNo,
												 vehicleOwner,
												 consigner,
												 consignee,
												 billingParty,
												lr.getLrexpenditureId(),
												lr.getLrincomeId(),
												lr.getOtherExpenditures());
								
			//Update Data
			lr.changeTo(ctrl);
			
			
			
			session.saveOrUpdate(lr);			
			session.flush();
					
			tx.commit();    		
		
		} catch (HibernateException e) {
			lr = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return lr; 
	}
	
	public LR updateIncomeToLR(LRIncome lrIncome,LR lr) {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
		
	
		try {
			tx = session.beginTransaction();  
			//Create Controller
			LR.Controller ctrl = CreateContoller(lr.getVehicleNo(),
												 lr.getVehicleOwner(),
												 lr.getConsignerId(),
												 lr.getConsigneeId(),
												 lr.getBillingToParty(),
												 lr.getLrexpenditureId(),
												 lrIncome,
												 lr.getOtherExpenditures());
								
			//Update Data
			lr.changeTo(ctrl);		
			
			session.saveOrUpdate(lr);			
			session.flush();
					
			tx.commit();    		
		
		} catch (HibernateException e) {
			lr = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return lr; 
	}
	
	public LR updateOtherExpenditureToLR(LROthers lrOthers,LR lr) {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
		
	
		try {
			tx = session.beginTransaction();  

			Set<LROthers> lrOtherExpeditures = null;
			if (lr.getOtherExpenditures() != null) {
				lrOtherExpeditures = lr.getOtherExpenditures();
			}
			
			lrOtherExpeditures.add(lrOthers);
			
			LR.Controller ctrl = CreateContoller(lr.getVehicleNo(),
												 lr.getVehicleOwner(),
												 lr.getConsignerId(),
												 lr.getConsigneeId(),
												 lr.getBillingToParty(),
												 lr.getLrexpenditureId(),
												 lr.getLrincomeId(),
												 lrOtherExpeditures);
								
			//Update Data
			lr.changeTo(ctrl);			
			session.saveOrUpdate(lr);			
			session.flush();
					
			tx.commit();    		
		
		} catch (HibernateException e) {
			lr = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return lr; 
	}
	
	
	public Consigner getConsigner( String consignerId ) {	
	
		Session session     = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx      = null;
		Consigner consigner = null;
	
		try {
			tx = session.beginTransaction();        	
			consigner = Consigner.findConsignerById(session, consignerId);
		
		if (null == consigner) {
			tx.rollback();
			session.close();    			
			throw new AuthException("Consigner Not found"); 
		}	
		
		tx.commit();    		
		
		} catch (HibernateException e) {
			consigner = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return consigner;        
	}
	
	public Consignee getConsignee( String consigneeId ) {	
	
		Session session     = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx      = null;
		Consignee consignee = null;
	
		try {
			tx = session.beginTransaction();        	
			consignee = Consignee.findConsigneeById(session, consigneeId);
		
		if (null == consignee) {
			tx.rollback();
			session.close();    			
			throw new DataNotFoundException("Consignee Not found");
		}
		
		tx.commit();    		
		
		} catch (HibernateException e) {
			consignee = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return consignee;        
	}
	
	//list all consigners
	public List<Consigner> listConsigner() {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
    	Transaction tx   = null;
    	List<Consigner> consignerList = null;
    	try {
    		tx = session.beginTransaction();        	
    		consignerList = Consigner.findAllConsigners(session);    		
    		
    		if (null == consignerList) {
    			System.err.println("ERROR ERROR : Not able to list consigners");
    			throw new DataNotFoundException("Not able to list consigners" );
    		} 			
    		tx.commit();
    		
    	} catch (HibernateException e) {
    		consignerList = null;
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            
        } finally {
        	if (session.isOpen()) {
        		session.close();
        	} 
        }
		
		return consignerList;
	}
	
	//list all consigners
	public List<Consignee> listConsignee() {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
    	Transaction tx   = null;
    	List<Consignee> consigneeList = null;
    	try {
    		tx = session.beginTransaction();        	
    		consigneeList = Consignee.findAllConsignees(session);
    		
    		if (null == consigneeList) {
    			System.err.println("ERROR ERROR : Not able to list consignere");
    			throw new DataNotFoundException("Not able to list consignere" );
    		} 			
    		tx.commit();
    		
    	} catch (HibernateException e) {
    		consigneeList = null;
            if (tx != null) tx.rollback();
            e.printStackTrace();
            
        } finally {
        	if (session.isOpen()) {
        		session.close();
        	} 
        }
		
		return consigneeList;
	}

	public LRResponse createLRResponse(LR lr) 
	{			
		LRView lrView = new LRView();
		lrView.setId(lr.getId());
		lrView.setVehicleNo(lr.getVehicleNo());
		lrView.setVehicleOwner(lr.getVehicleOwner());
		lrView.setConsigner(lr.getConsignerId());
		lrView.setConsignee(lr.getConsigneeId());		
		lrView.setBillingParty(lr.getBillingToParty());		
		
		LRResponse response = new LRResponse(lrView);		
		
		return response;
	}
	
	public ConsignerListResponse createListConsignerResponse(List<Consigner> consignerList) {				
		List<ConsignerView> lConsignerView  = new ArrayList<ConsignerView>();
		for (Consigner consigner : consignerList) {
			if (null != consigner) {
				ConsignerView consignerView = new ConsignerView();				
				consignerView.setId(consigner.getId());
				consignerView.setConsignerCode(consigner.getConsignerCode());
				consignerView.setConsignerName(consigner.getConsignerName());
				consignerView.setAddress(consigner.getAddress());
				consignerView.setServiceTax(consigner.getServiceTax());
				lConsignerView.add(consignerView);							
			}			
		}
		ConsignerListResponse response = new ConsignerListResponse(lConsignerView);
		return response;
	}
	
	public ConsigneeListResponse createListConsigneeResponse(List<Consignee> consigneeList) {				
		List<ConsigneeView> lConsigneeView  = new ArrayList<ConsigneeView>();
		for (Consignee consignee : consigneeList) {
			if (null != consignee) {
				ConsigneeView consigneeView = new ConsigneeView();				
				consigneeView.setId(consignee.getId());
				consigneeView.setConsigneeCode(consignee.getConsigneeCode());
				consigneeView.setConsigneeName(consignee.getConsigneeName());
				consigneeView.setAddress(consignee.getAddress());
				consigneeView.setServiceTax(consignee.getServiceTax());
				lConsigneeView.add(consigneeView);			
			}			
		}
		ConsigneeListResponse response = new ConsigneeListResponse(lConsigneeView);
		return response;
	}
	
}
