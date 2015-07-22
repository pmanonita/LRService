package com.lr.service;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lr.db.HibernateSessionManager;
import com.lr.exceptions.AuthException;
import com.lr.exceptions.DataNotFoundException;
import com.lr.exceptions.InsufficientDataException;
import com.lr.exceptions.SignupException;
import com.lr.model.Consignee;
import com.lr.model.Consigner;
import com.lr.model.LR;
import com.lr.model.User;
import com.lr.model.LR.Controller;
import com.lr.response.ConsigneeListResponse;
import com.lr.response.ConsigneeView;
import com.lr.response.ConsignerListResponse;
import com.lr.response.ConsignerView;
import com.lr.response.LRResponse;
import com.lr.response.LRView;
import com.lr.response.UserListResponse;
import com.lr.response.UserResponse;
import com.lr.response.UserView;

public class LrService {
	private final static int successCode = 1;
	//private final static int errorCode   = 0;
	
	//view level validation
	public void validateAuthData(String vehicleNo)
		throws InsufficientDataException
	{
		String errorMsg = "";
		if (null != vehicleNo && vehicleNo.equals("")) 
		{
			errorMsg = "Vehicle Number can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}		
	}
	
	private LR.DefaultController createControllerFromView(final String serviceKey,						  
			  											final String vehileNo,
			  											final String vehicleOwner,
			  											final String consignor,
			  											final String consignee,
			  											final String servTaxConsigner,
			  											final String servTaxConsignee,
			  											final String billingParty) 
	{
		
		return new LR.DefaultController() {
			
			@Override
			public int mTransid() {	return 0;	}
			
			@Override
			public String mVehicleNo() {	return vehileNo;	}
			
			@Override
			public String mConsignor() {	return consignor;	}
			
			@Override
			public String mConsignee() {	return consignee;	}
			
			@Override
			public String mConsignerServtax() {		return servTaxConsigner;	}
			
			@Override
			public String mConsigneeServtax() {		return servTaxConsignee;	}
			
			@Override
			public String mVehicleOwner() {		return vehicleOwner;	}
			
			@Override
			public String mBillingToParty() {	return billingParty;	}
			
			@Override
			public Date mLrDate() {		return new Date();	}
			
			@Override
			public String mMultiLoad() {	return null;	}
			
			@Override
			public String mUserid() {	return "";	}
			
			
		};
	}
	
	
	
	public LR newLR(final String serviceKey,						  
						  final String vehileNo,
						  final String vehicleOwner,
						  final String consignor,
						  final String consignee,
						  final String servTaxConsigner,
						  final String servTaxConsignee,
						  final String billingParty)
	{
		//validateAuthData(userName, password);
		
		//Get hibernate session manager
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx  = null;
		LR lr       = null;
		
		try {
			
			tx = session.beginTransaction();
			
			//check if user already exists
			/*user = User.findByUserNameAndServiceKey(session, userName, serviceKey);
			if (user != null && user.mUserName().equalsIgnoreCase(userName)) {
				tx.rollback();
    			session.close();    			
    			throw new SignupException("Signup failure. User already exists");				
			}*/
			

			LR.Controller ctrl = createControllerFromView(serviceKey,
															vehileNo,
															vehicleOwner,
															consignor,
															consignee,
															servTaxConsigner,
															servTaxConsignee,
															billingParty);
						
			//Create user object using controller
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
            if (tx!=null) tx.rollback();
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
		
		/** User data visible to UI **/		
		LRView lrView = new LRView();
		lrView.setId(lr.getId());
		lrView.setVehicleNo(lr.getVehicleNo());
		lrView.setVehicleOwner(lr.getVehicleOwner());
		lrView.setConsignor(lr.getConsignor());
		lrView.setConsignee(lr.getConsignee());
		lrView.setServTaxConsigner(lr.getConsignerServtax());
		lrView.setServTaxConsignee(lr.getConsigneeServtax());
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
