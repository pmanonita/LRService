package com.lr.model;

import java.util.Date;
import java.util.List;

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
			
			
	}
	
	public void changeTo(Controller ctrl) {
		
		validate(ctrl);
		
		_consigneeCode = ctrl.mConsigneeCode();
		_consigneeName = ctrl.mConsigneeName();
		_address = ctrl.mAddress();
		_serviceTax = ctrl.mServiceTax();
			
		
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

	

	
	public static long mSerialversionuid() 		{ return serialVersionUID;		}
	
	private static final String QUERY_FOR_LIST_CONSIGNER =
		Consignee.class.getName() + ".findAllConsignees";
	public static List<Consignee> findAllConsignees(Session session) {
	
		Query qry = session.getNamedQuery(QUERY_FOR_LIST_CONSIGNER);	
    
		@SuppressWarnings("unchecked")
		final List<Consignee> consigneelist = qry.list();
		return consigneelist;
	} 
	
	
	

}
