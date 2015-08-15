package com.lr.model;

import java.io.Serializable;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.lr.exceptions.InsufficientDataException;


public class LRTransOtherIncome implements Serializable {
	
	private static final long serialVersionUID = -530903930212664583L;
	
	private long   _id;
	private long   _transid;	
	private int    _amount;	
	private String _remarks;
	
		
	public LRTransOtherIncome() {	}
	
	public LRTransOtherIncome (Controller ctrl) {
		createFrom(ctrl);		
	}
	
	private void validate(Controller ctrl) throws InsufficientDataException {
		//Model level validation
		String errorMsg = "";
		if (ctrl.mTransid() == 0 ) {
			errorMsg = "LR No can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}		
	}
	
	private void createFrom(Controller ctrl) {
		validate(ctrl);
		
		_transid    = ctrl.mTransid();
		_amount  = ctrl.mAmount();
		_remarks = ctrl.mRemarks();	
		
	}
	
	public void changeTo(Controller ctrl) {		
		validate(ctrl);
		
		_transid    = ctrl.mTransid();	
		_amount  = ctrl.mAmount();
		_remarks = ctrl.mRemarks();		
		
	}

	public interface Controller {
				
		long mTransid();
		void mTransid(long transid);		

		int mAmount();
		void mAmount(int amount);

		String mRemarks();
		void mRemarks(String remarks);
		
		
	}
	
	public abstract static class DefaultController implements Controller {
		public void mTransid(long transid) { }
		public void mAmount(int freightToBroker) { }
		public void mRemarks(String remarks) { }
		
	}

	
	//getter and setter	
	public long getId() {
		return _id;
	}

	protected void setId(long id) {
		this._id = id;
	}
	
	
	public long getTransid() {
		return _transid;
	}

	void setTransid(long transid) {
		this._transid = transid;
	}

	public int getAmount(){ return _amount; }
	void setAmount(int amount){
		this._amount = amount;
	}

	public String getRemarks(){ return _remarks; }
	void setRemarks(String remarks){
		this._remarks = remarks;
	}
	
		
	public static long mSerialversionuid() 		{ return serialVersionUID;		}
	
	private static final String QUERY_FOR_LRTRANSOTHERINCOME_BY_ID_SKEY =
		LRTransOtherIncome.class.getName() + ".findLRTransOtherIncomeById";	
	public static LRTransOtherIncome findLRTransOtherIncomeById(Session session, Long id)
		throws HibernateException
	{
	 	if (id == null) {
	 		return null;
	 	}
	 	
	 	Query qry = session.getNamedQuery(QUERY_FOR_LRTRANSOTHERINCOME_BY_ID_SKEY);
	 	qry.setLong("id", id);		 	
 
	 	qry.setMaxResults(1);
 
	 	final LRTransOtherIncome lrOther = (LRTransOtherIncome)(qry.uniqueResult());
	 	return lrOther;
	}

}
