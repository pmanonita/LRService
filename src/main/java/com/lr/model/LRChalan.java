package com.lr.model;

import java.io.Serializable;
import java.util.Date;

import com.lr.exceptions.InsufficientDataException;


public class LRChalan implements Serializable {

	private static final long serialVersionUID = -181105947248849943L;
	
	private long    _id;
	private String  _lrIds;	
	private String  _chalanDetails;	
	private long    _totalCost;	
	private Date	_createDate;
	
	
	public LRChalan() {	}
	
	public LRChalan (Controller ctrl) {
		createFrom(ctrl);
		
	}
	
	private void validate(Controller ctrl) throws InsufficientDataException {
		//Model level validation
		String errorMsg = "";
		if (ctrl.mLRIds() == null || ctrl.mLRIds().equals("")) {
			errorMsg = "LR No can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}		
	}
	
	private void createFrom(Controller ctrl) {		

		validate(ctrl);
		
		_lrIds               = ctrl.mLRIds();	
		_chalanDetails       = ctrl.mChalanDetails();
		_totalCost           = ctrl.mTotalCost();
		_createDate          = ctrl.mCreateDate();
			
	}
	
	public void changeTo(Controller ctrl) {
		
		validate(ctrl);
		
		_lrIds               = ctrl.mLRIds();	
		_chalanDetails    = ctrl.mChalanDetails();
		_totalCost           = ctrl.mTotalCost();
		_createDate          = ctrl.mCreateDate();
		
		
		
	}

	public interface Controller {
				
		String mLRIds();
		void mLRIds(String lrNos);		

		String mChalanDetails();
		void mChalanDetails(String chalanDetails);	
		
		long mTotalCost();
		void mTotalCost(long totalCost);	
		
		Date mCreateDate();
		void mCreateDate(Date createDate);
		
		
	}
	
	public abstract static class DefaultController implements Controller {
		public void mLRIds(String lrIds) { }
		public void mChalanDetails(String chalanDetails) { }
		public void mTotalCost(long totalCost) { }
		public void mCreateDate(Date createDate) { }
		
		
	}

	//getter and setter
	
	
	public long getId() {
		return _id;
	}

	protected void setId(long id) {
		this._id = id;
	}

	public String getLrIds() {
		return _lrIds;
	}

	void setLrIds(String lrIds) {
		this._lrIds = lrIds;
	}
	
	public String getChalanDetails() {
		
		return _chalanDetails;
	}
	
	void setChalanDetails(String chalanDetails) {
		this._chalanDetails =  chalanDetails;
	}
	
	public long getTotalCost() {
		return _totalCost;
	}

	void setTotalCost(long totalCost) {
		this._totalCost = totalCost;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	void setCreateDate(Date createDate) {
		this._createDate = createDate;
	}
	
		
	public static long mSerialversionuid() 		{ return serialVersionUID;		}	

}
