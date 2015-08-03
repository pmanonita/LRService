package com.lr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.lr.exceptions.InsufficientDataException;
import com.lr.model.LR.Controller;

public class Billingname implements Serializable {
	
	private static final long serialVersionUID = -6779738051490200702L;
	
	private int   _id;
	private String _name;	
	private String _address;	
	
	
	public Billingname() {	}
	
	public Billingname (Controller ctrl) {
		createFrom(ctrl);		
	}
	
	private void validate(Controller ctrl) throws InsufficientDataException {
		//Model level validation
		String errorMsg = "";
		if (ctrl.mName() == null && !ctrl.mName().equals("") ) 
		{
			errorMsg = "Consigner Code can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}	
		
	}
	
	private void createFrom(Controller ctrl) {
		validate(ctrl);
		
		_name 	= ctrl.mName();
		_address 	= ctrl.mAddress();
					
	}
	
	public void changeTo(Controller ctrl) {
		
		validate(ctrl);
		
		_name 	= ctrl.mName();
		_address 	= ctrl.mAddress();	
	}

	public interface Controller {
				
		String mName();
		void mName(String name);	
		
		String mAddress();
		void mAddress(String address);	
			
	}
	
	public abstract static class DefaultController implements Controller {
		public void mName(String name) { }
		public void mAddress(String address) { }
	}
		

	//getter and setter	
	public int getId() {
		return _id;
	}

	protected void setId(int id) {
		this._id = id;
	}
	
	
	public String getName() {
		return _name;
	}

	void setName(String name) {
		this._name = name;
	}

	
	public String getAddress(){ return _address;}
	void setAddress(String address){
		this._address = address;
	}
	
	public static long mSerialversionuid() 		{ return serialVersionUID;		}
	
	private static final String QUERY_FOR_LIST_BILLINGNAME =
		Billingname.class.getName() + ".findAllBillingname";
	public static List<Billingname> findAllBillingname(Session session) {
	
		Query qry = session.getNamedQuery(QUERY_FOR_LIST_BILLINGNAME);	
    
		@SuppressWarnings("unchecked")
		final List<Billingname> billingnamelist = qry.list();
		return billingnamelist;
	} 
	
	private static final String QUERY_FOR_BILLINGNAME_BY_ID_SKEY =
		Billingname.class.getName() + ".findBillingnameById";
	 public static Billingname findBillingnameById(Session session, Integer id)
		throws HibernateException
	{
	 	if (id == null) {
		 return null;
	 	}
		 	
		Query qry = session.getNamedQuery(QUERY_FOR_BILLINGNAME_BY_ID_SKEY);
		qry.setInteger("id", id);		 	
 
		qry.setMaxResults(1);
 
		final Billingname billingname = (Billingname)(qry.uniqueResult());
		return billingname;
	}
}
