package com.lr.model;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.lr.exceptions.InsufficientDataException;
import com.lr.model.LR.Controller;

public class Consignee {
	
	private static final long serialVersionUID = -6779738051490200702L;
	
	private long   _id;
	private String _consigneeCode;	
	private String _consigneeName;	
	private String _address;	
	private String _serviceTax;
	private String _toPlace;
	
	
	//For hibernate
	public Consignee() {
		// TODO Auto-generated constructor stub
	}
	
	public Consignee (Controller ctrl) {
		createFrom(ctrl);
		
	}
	
	private void validate(Controller ctrl) throws InsufficientDataException {
		//Model level validation
		String errorMsg = "";
		if (ctrl.mConsigneeCode() == null && !ctrl.mConsigneeCode().equals("") ) 
		{
			errorMsg = "Consignee Code can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}	
		
	}
	
	private void createFrom(Controller ctrl) {
		

		validate(ctrl);
		
		_consigneeCode = ctrl.mConsigneeCode();
		_consigneeName = ctrl.mConsigneeName();
		_address = ctrl.mAddress();
		_serviceTax = ctrl.mServiceTax();
		_toPlace = ctrl.mToPlace();
			
			
	}
	
	public void changeTo(Controller ctrl) {
		
		validate(ctrl);
		
		_consigneeCode = ctrl.mConsigneeCode();
		_consigneeName = ctrl.mConsigneeName();
		_address = ctrl.mAddress();
		_serviceTax = ctrl.mServiceTax();
		_toPlace = ctrl.mToPlace();
			
		
	}

	public interface Controller {
				
		String mConsigneeCode();
		void mConsigneeCode(String consigneeCode);	
		
		String mConsigneeName();
		void mConsigneeName(String consigneeName);	

		String mAddress();
		void mAddress(String address);

		String mServiceTax();
		void mServiceTax(String serviceTax);
		
		String mToPlace();
		void mToPlace(String toPlace);

				
	}
	
	public abstract static class DefaultController implements Controller {

		
		@Override
		public void mConsigneeCode(String consigneeCode) { }
		
		@Override
		public void mConsigneeName(String consigneeName) { }
		
		@Override
		public void mAddress(String address) { }

		@Override
		public void mServiceTax(String serviceTax) { }
		
		@Override
		public void mToPlace(String toPlace) { }
		
		
				
	}

	//getter and setter
	
	public long getId() {
		return _id;
	}

	protected void setId(long id) {
		this._id = id;
	}
	
	
	public String getConsigneeCode() {
		return _consigneeCode;
	}

	void setConsigneeCode(String consigneeCode) {
		this._consigneeCode = consigneeCode;
	}
	
	public String getConsigneeName(){ return _consigneeName;}
	void setConsigneeName(String consigneeName){
		this._consigneeName = consigneeName;
	}


	public String getAddress(){ return _address;}
	void setAddress(String address){
		this._address = address;
	}
	
	public String getServiceTax(){ return _serviceTax;}
	void setServiceTax(String serviceTax){
		this._serviceTax = serviceTax;
	}
	
	public String getToPlace(){ return _toPlace;}
	void setToPlace(String toPlace){
		this._toPlace = toPlace;
	}


	

	
	public static long mSerialversionuid() 		{ return serialVersionUID;		}
	
	private static final String QUERY_FOR_LIST_CONSIGNER =
		Consignee.class.getName() + ".findAllConsignees";
	public static List<Consignee> findAllConsignees(Session session) {
	
		Query qry = session.getNamedQuery(QUERY_FOR_LIST_CONSIGNER);	
    
		@SuppressWarnings("unchecked")
		final List<Consignee> consigneelist = qry.list();
		return consigneelist;
	} 
	
	private static final String QUERY_FOR_CONSIGNEE_BY_ID_SKEY =
		Consigner.class.getName() + ".findConsigneeById";
	 public static Consignee findConsigneeById(Session session, String consigneeId)
		throws HibernateException
		{
		 	if (consigneeId == null || consigneeId == null) {
			 return null;
		 	}
		 	Query qry = session.getNamedQuery(QUERY_FOR_CONSIGNEE_BY_ID_SKEY);
		 	qry.setString("consigneeId",    consigneeId);		 	
 
		 	qry.setMaxResults(1);
 
		 	final Consignee consignee = (Consignee)(qry.uniqueResult());
		 	return consignee;
		}
	
	
	

}
