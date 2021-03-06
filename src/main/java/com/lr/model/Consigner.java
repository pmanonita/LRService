package com.lr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.lr.exceptions.InsufficientDataException;
import com.lr.model.LR.Controller;

public class Consigner implements Serializable {
	
	private static final long serialVersionUID = -6779738051490200702L;
	
	private long   _id;
	private String _consignerCode;	
	private String _consignerName;	
	private String _address;	
	private String _serviceTax;
	private String _fromPlace;
	
	public Consigner() {	}
	
	public Consigner (Controller ctrl) {
		createFrom(ctrl);		
	}
	
	private void validate(Controller ctrl) throws InsufficientDataException {
		//Model level validation
		String errorMsg = "";
		if (ctrl.mConsignerCode() == null && !ctrl.mConsignerCode().equals("") ) 
		{
			errorMsg = "Consigner Code can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}	
		
	}
	
	private void createFrom(Controller ctrl) {
		validate(ctrl);
		
		_consignerCode 	= ctrl.mConsignerCode();
		_consignerName 	= ctrl.mConsignerName();
		_address 		= ctrl.mAddress();
		_serviceTax 	= ctrl.mServiceTax();
		_fromPlace 		= ctrl.mFromPlace();			
	}
	
	public void changeTo(Controller ctrl) {
		
		validate(ctrl);
		
		_consignerCode 	= ctrl.mConsignerCode();
		_consignerName 	= ctrl.mConsignerName();
		_address 		= ctrl.mAddress();
		_serviceTax 	= ctrl.mServiceTax();
		_fromPlace 		= ctrl.mFromPlace();		
	}

	public interface Controller {
				
		String mConsignerCode();
		void mConsignerCode(String consignerCode);	
		
		String mConsignerName();
		void mConsignerName(String consignerName);	

		String mAddress();
		void mAddress(String address);

		String mServiceTax();
		void mServiceTax(String serviceTax);
		
		String mFromPlace();
		void mFromPlace(String fromPlace);				
	}
	
	public abstract static class DefaultController implements Controller {
		public void mConsignerCode(String consignerCode) { }
		public void mConsignerName(String consignerName) { }
		public void mAddress(String address) { }
		public void mServiceTax(String serviceTax) { }
		public void mFromPlace(String fromPlace) { }
	}

	//getter and setter	
	public long getId() {
		return _id;
	}

	protected void setId(long id) {
		this._id = id;
	}
	
	
	public String getConsignerCode() {
		return _consignerCode;
	}

	void setConsignerCode(String consignerCode) {
		this._consignerCode = consignerCode;
	}
	
	public String getConsignerName(){ return _consignerName;}
	void setConsignerName(String consignerName){
		this._consignerName = consignerName;
	}


	public String getAddress(){ return _address;}
	void setAddress(String address){
		this._address = address;
	}
	
	public String getServiceTax(){ return _serviceTax;}
	void setServiceTax(String serviceTax){
		this._serviceTax = serviceTax;
	}
	
	public String getFromPlace(){ return _fromPlace;}
	void setFromPlace(String fromPlace){
		this._fromPlace = fromPlace;
	}
	public static long mSerialversionuid() 		{ return serialVersionUID;		}
	
	private static final String QUERY_FOR_LIST_CONSIGNER =
		Consigner.class.getName() + ".findAllConsigners";
	public static List<Consigner> findAllConsigners(Session session) {
	
		Query qry = session.getNamedQuery(QUERY_FOR_LIST_CONSIGNER);	
    
		@SuppressWarnings("unchecked")
		final List<Consigner> consignerlist = qry.list();
		return consignerlist;
	} 
	
	private static final String QUERY_FOR_CONSIGNER_BY_ID_SKEY =
		Consigner.class.getName() + ".findConsignerById";
	 public static Consigner findConsignerById(Session session, Integer id)
		throws HibernateException
	{
	 	if (id == null) {
		 return null;
	 	}
		 	
		Query qry = session.getNamedQuery(QUERY_FOR_CONSIGNER_BY_ID_SKEY);
		qry.setInteger("id", id);		 	
 
		qry.setMaxResults(1);
 
		final Consigner consigner = (Consigner)(qry.uniqueResult());
		return consigner;
	}
}
