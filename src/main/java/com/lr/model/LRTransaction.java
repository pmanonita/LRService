package com.lr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.lr.model.LR.Controller;

public class LRTransaction implements Serializable {

	private static final long serialVersionUID = 8345028283786868393L;
	
	private long    _id;
	private Set<LR> _lrs;	
	private int  	_multiLoadCharge;
	private int  	_freightToBroker;	
	private int  	_extraPayToBroker;
	private int  	_advance;
	private int  	_balanceFreight;
	private int  	_loadingCharges;
	private int  	_unloadingCharges;
	private int  	_loadingDetBroker;
	private int  	_unloadingDetBroker;		
	private int  	_multiLoadChargeBilling;
	private int  	_freightToBrokerBilling;
	private int  	_loadingChargesBilling;
	private int  	_unloadingChargesBilling;
	private int  	_loadingDetBrokerBilling;
	private int  	_unloadingDetBrokerBilling;
	private String  _status;
	private Date	_createDate;

	
	public LRTransaction() {	}
	
	public LRTransaction (Controller ctrl) {
		createFrom(ctrl);		
	}	

	private void createFrom(Controller ctrl) {		
		_lrs        				= ctrl.mLRs();		
		_multiLoadCharge    		= ctrl.mMultiLoadCharge();
		_freightToBroker    		= ctrl.mFreightToBroker();
		_extraPayToBroker   		= ctrl.mExtraPayToBroker();
		_advance            		= ctrl.mAdvance();
		_balanceFreight     		= ctrl.mBalanceFreight();
		_loadingCharges     		= ctrl.mLoadingCharges();
		_unloadingCharges   		= ctrl.mUnloadingCharges();
		_loadingDetBroker   		= ctrl.mLoadingDetBroker();
		_unloadingDetBroker 		= ctrl.mUnloadingDetBroker();		
		_multiLoadChargeBilling    	= ctrl.mMultiLoadChargeBilling();
		_freightToBrokerBilling    	= ctrl.mFreightToBrokerBilling();
		_loadingChargesBilling     	= ctrl.mLoadingChargesBilling();
		_unloadingChargesBilling   	= ctrl.mUnloadingChargesBilling();
		_loadingDetBrokerBilling   	= ctrl.mLoadingDetBrokerBilling();
		_unloadingDetBrokerBilling 	= ctrl.mUnloadingDetBrokerBilling();
		_status     				= ctrl.mStatus();
		_createDate 				= ctrl.mCreateDate();		
	}
	
	public void changeTo(Controller ctrl) {		
		_lrs        				= ctrl.mLRs();		
		_multiLoadCharge    		= ctrl.mMultiLoadCharge();
		_freightToBroker    		= ctrl.mFreightToBroker();
		_extraPayToBroker   		= ctrl.mExtraPayToBroker();
		_advance            		= ctrl.mAdvance();
		_balanceFreight     		= ctrl.mBalanceFreight();
		_loadingCharges     		= ctrl.mLoadingCharges();
		_unloadingCharges   		= ctrl.mUnloadingCharges();
		_loadingDetBroker   		= ctrl.mLoadingDetBroker();
		_unloadingDetBroker 		= ctrl.mUnloadingDetBroker();		
		_multiLoadChargeBilling		= ctrl.mMultiLoadChargeBilling();
		_freightToBrokerBilling    	= ctrl.mFreightToBrokerBilling();
		_loadingChargesBilling     	= ctrl.mLoadingChargesBilling();
		_unloadingChargesBilling   	= ctrl.mUnloadingChargesBilling();
		_loadingDetBrokerBilling   	= ctrl.mLoadingDetBrokerBilling();
		_unloadingDetBrokerBilling	= ctrl.mUnloadingDetBrokerBilling();		
		_status     				= ctrl.mStatus();
		_createDate 				= ctrl.mCreateDate();		 
	}

	public interface Controller {				
				
		Set<LR> mLRs();
		void mLRs(Set<LR> lrs);
		
		int mMultiLoadCharge();
		void mMultiLoadCharge(int multiLoadCharge);
		
		int mFreightToBroker();
		void mFreightToBroker(int freightToBroker);

		int mExtraPayToBroker();
		void mExtraPayToBrokersignor(int extraPayToBroker);

		int mAdvance();
		void mAdvance(int advance);

		int mBalanceFreight();
		void mBalanceFreight(int balanceFreight);
		 
		int mLoadingCharges();
		void mLoadingCharges(int loadingCharges);

		int mUnloadingCharges();
		void mUnloadingCharges(int unloadingCharges);

		int mLoadingDetBroker();
		void mLoadingDetBroker(int loadingDetBroker);			
		
		int mUnloadingDetBroker();
		void mUnloadingDetBroker(int unloadingDetBroker);		
		
		int mMultiLoadChargeBilling();
		void mMultiLoadChargeBilling(int multiLoadChargeBilling);
		
		int mFreightToBrokerBilling();
		void mFreightToBrokerBilling(int freightToBrokerBilling);
				 
		int mLoadingChargesBilling();
		void mLoadingChargesBilling(int loadingChargesBilling);

		int mUnloadingChargesBilling();
		void mUnloadingChargesBilling(int unloadingChargesBilling);

		int mLoadingDetBrokerBilling();
		void mLoadingDetBrokerBilling(int loadingDetBrokerBilling);			
		
		int mUnloadingDetBrokerBilling();
		void mUnloadingDetBrokerBilling(int unloadingDetBrokerBilling);		
		
		String mStatus();
		void mStatus(String status);
		
		Date mCreateDate();
		void mCreateDate(Date createDate);
	}
	
	public abstract static class DefaultController implements Controller {
		public void mLRs(Set<LR> lrs)											{ }
		public void mMultiLoadCharge(int multiLoadCharge) 						{ }
		public void mFreightToBroker(int freightToBroker) 						{ }
		public void mExtraPayToBrokersignor(int extraPayToBroker) 				{ }
		public void mAdvance(int advance) 										{ }
		public void mBalanceFreight(int balanceFreight) 						{ }
		public void mLoadingCharges(int loadingCharges) 						{ }
		public void mUnloadingCharges(int unloadingCharges) 					{ }
		public void mLoadingDetBroker(int loadingDetBroker) 					{ }
		public void mUnloadingDetBroker(int unloadingDetBroker) 				{ }		
		public void mMultiLoadChargeBilling(int multiLoadChargeBilling) 		{ }
		public void mFreightToBrokerBilling(int freightToBrokerBilling) 		{ }
		public void mLoadingChargesBilling(int loadingChargesBilling) 			{ }
		public void mUnloadingChargesBilling(int unloadingChargesBilling) 		{ }
		public void mLoadingDetBrokerBilling(int loadingDetBrokerBilling) 		{ }
		public void mUnloadingDetBrokerBilling(int unloadingDetBrokerBilling) 	{ }		
		public void mStatus(String status)										{ }
		public void mCreateDate(Date createDate)								{ }		
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

	public int getMultiLoadCharge() {
		return _multiLoadCharge;
	}

	private void setMultiLoadCharge(int multiLoadCharge) {
		this._multiLoadCharge = multiLoadCharge;
	}

	public int getFreightToBroker() {
		return _freightToBroker;
	}

	private void setFreightToBroker(int freightToBroker) {
		this._freightToBroker = freightToBroker;
	}

	public int getExtraPayToBroker() {
		return _extraPayToBroker;
	}

	private void setExtraPayToBroker(int extraPayToBroker) {
		this._extraPayToBroker = extraPayToBroker;
	}

	public int getAdvance() {
		return _advance;
	}

	private void setAdvance(int advance) {
		this._advance = advance;
	}

	public int getBalanceFreight() {
		return _balanceFreight;
	}

	private void setBalanceFreight(int balanceFreight) {
		this._balanceFreight = balanceFreight;
	}

	public int getLoadingCharges() {
		return _loadingCharges;
	}

	private void setLoadingCharges(int loadingCharges) {
		this._loadingCharges = loadingCharges;
	}

	public int getUnloadingCharges() {
		return _unloadingCharges;
	}

	private void setUnloadingCharges(int unloadingCharges) {
		this._unloadingCharges = unloadingCharges;
	}

	public int getLoadingDetBroker() {
		return _loadingDetBroker;
	}

	private void setLoadingDetBroker(int loadingDetBroker) {
		this._loadingDetBroker = loadingDetBroker;
	}

	public int getUnloadingDetBroker() {
		return _unloadingDetBroker;
	}

	private void setUnloadingDetBroker(int unloadingDetBroker) {
		this._unloadingDetBroker = unloadingDetBroker;
	}

	public int getMultiLoadChargeBilling() {
		return _multiLoadChargeBilling;
	}

	private void setMultiLoadChargeBilling(int multiLoadChargeBilling) {
		this._multiLoadChargeBilling = multiLoadChargeBilling;
	}

	public int getFreightToBrokerBilling() {
		return _freightToBrokerBilling;
	}

	private void setFreightToBrokerBilling(int freightToBrokerBilling) {
		this._freightToBrokerBilling = freightToBrokerBilling;
	}

	public int getLoadingChargesBilling() {
		return _loadingChargesBilling;
	}

	private void setLoadingChargesBilling(int loadingChargesBilling) {
		this._loadingChargesBilling = loadingChargesBilling;
	}

	public int getUnloadingChargesBilling() {
		return _unloadingChargesBilling;
	}

	private void setUnloadingChargesBilling(int unloadingChargesBilling) {
		this._unloadingChargesBilling = unloadingChargesBilling;
	}

	public int getLoadingDetBrokerBilling() {
		return _loadingDetBrokerBilling;
	}

	private void setLoadingDetBrokerBilling(int loadingDetBrokerBilling) {
		this._loadingDetBrokerBilling = loadingDetBrokerBilling;
	}

	public int getUnloadingDetBrokerBilling() {
		return _unloadingDetBrokerBilling;
	}

	private void setUnloadingDetBrokerBilling(int unloadingDetBrokerBilling) {
		this._unloadingDetBrokerBilling = unloadingDetBrokerBilling;
	}

	public String getStatus() {
		return _status;
	}

	private void setStatus(String status) {
		this._status = status;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	private void setCreateDate(Date createDate) {
		this._createDate = createDate;
	}
	
	public static long mSerialversionuid() 	{ return serialVersionUID;	}
	
	
	
}
