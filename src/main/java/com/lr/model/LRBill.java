package com.lr.model;

import java.io.Serializable;
import java.util.Date;

import com.lr.exceptions.InsufficientDataException;


public class LRBill implements Serializable {

	private static final long serialVersionUID = -181105947248849943L;
	
	private long _id;
	private String _lrIds;	
	private String  _billDetails;
	private long    _totalBill;	
	private Date	_createDate;
	
	
	public LRBill() {	}
	
	public LRBill (Controller ctrl) {
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
		_billDetails    = ctrl.mBillDetails();
		_totalBill           = ctrl.mTotalBill();
		_createDate          = ctrl.mCreateDate();
		
		
			
	}
	
	public void changeTo(Controller ctrl) {
		
		validate(ctrl);
		
		_lrIds               = ctrl.mLRIds();	
		_billDetails    = ctrl.mBillDetails();
		_totalBill           = ctrl.mTotalBill();
		_createDate          = ctrl.mCreateDate();
		
		
		
	}

	public interface Controller {
				
		String mLRIds();
		void mLRIds(String lrNos);		

		String mBillDetails();
		void mBillDetails(String billDetails);	
		
		long mTotalBill();
		void mTotalBill(long totalBill);	
		
		Date mCreateDate();
		void mCreateDate(Date createDate);
		
		
	}
	
	public abstract static class DefaultController implements Controller {
		public void mLRIds(String lrIds) { }
		public void mBillDetails(String billDetails) { }
		public void mTotalBill(long totalBill) { }
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
	
	public String getBillDetails() {
		
		return _billDetails;
	}
	
	void setBillDetails(String billDetails) {
		this._billDetails =  billDetails;
	}
	
	public long getTotalBill() {
		return _totalBill;
	}

	void setTotalBill(long totalBill) {
		this._totalBill = totalBill;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	void setCreateDate(Date createDate) {
		this._createDate = createDate;
	}

	
	
		
	public static long mSerialversionuid() 		{ return serialVersionUID;		}	

}

