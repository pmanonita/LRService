package com.lr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.lr.model.LR.Controller;

public class LRTransaction implements Serializable {

	private static final long serialVersionUID = 8345028283786868393L;
	
	private long    _id;
	private Set<LR> _lrs;
	private String  _status;
	private Date	_createDate;
	
	
	public LRTransaction() {	}
	
	public LRTransaction (Controller ctrl) {
		createFrom(ctrl);		
	}	

	private void createFrom(Controller ctrl) {		
		_lrs        = ctrl.mLRs();
		_status     = ctrl.mStatus();
		_createDate = ctrl.mCreateDate();		
	}
	
	public void changeTo(Controller ctrl) {		
		_lrs        = ctrl.mLRs();
		_status     = ctrl.mStatus();
		_createDate = ctrl.mCreateDate();		 
	}

	public interface Controller {				
				
		Set<LR> mLRs();
		void mLRs(Set<LR> lrs);	
		
		String mStatus();
		void mStatus(String status);
		
		Date mCreateDate();
		void mCreateDate(Date createDate);
	}
	
	public abstract static class DefaultController implements Controller {
		public void mLRs(Set<LR> lrs)			{	}
		public void mStatus(String status)		{	}
		public void mCreateDate(Date createDate){	}		
	}
	
	
	
	//get and set
	public long getId() {
		return _id;
	}	
	protected void setId(long _id) {
		this._id = _id;
	}
	public Set<LR> getLrs() {
		return _lrs;
	}
	private void setLrs(Set<LR> lrs) {
		this._lrs = lrs;
	}

	public String getStatus() {
		return _status;
	}

	public void setStatus(String status) {
		this._status = status;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		this._createDate = createDate;
	}
	
	public static long mSerialversionuid() 	{ return serialVersionUID;	}
	
	
	
}
