package com.lr.model;

import java.util.Date;

import com.lr.exceptions.InsufficientDataException;
import com.lr.model.LR.Controller;

public class LRExpenditure {
	
	private static final long serialVersionUID = -6779738051490200702L;
	
	private long   _id;
	private long _lrNo;	
	private int _freightToBroker;	
	private int _extraPayToBroker;
	private int   _advance;
	private int _balanceFreight;
	private int _loadingCharges;
	private int _unloadingCharges;
	private int   _loadingDetBroker;
	private int   _unloadingDetBroker;
	
	//For hibernate
	public LRExpenditure() {
		// TODO Auto-generated constructor stub
	}
	
	public LRExpenditure (Controller ctrl) {
		createFrom(ctrl);
		
	}
	
	private void validate(Controller ctrl) throws InsufficientDataException {
		//Model level validation
		String errorMsg = "";
		if (ctrl.mLRNo() == 0 ) 
		{
			errorMsg = "LR No can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}	
		
	}
	
	private void createFrom(Controller ctrl) {
		

		validate(ctrl);
		
		_lrNo = ctrl.mLRNo();	
		_freightToBroker = ctrl.mFreightToBroker();
		_extraPayToBroker = ctrl.mExtraPayToBroker();
		_advance = ctrl.mAdvance();
		_balanceFreight = ctrl.mBalanceFreight();
		_loadingCharges = ctrl.mLoadingCharges();
		_unloadingCharges = ctrl.mUnloadingCharges();
		_loadingDetBroker = ctrl.mLoadingDetBroker();
		_unloadingDetBroker = ctrl.mUnloadingDetBroker();		
			
	}
	
	public void changeTo(Controller ctrl) {
		
		validate(ctrl);
		
		_lrNo = ctrl.mLRNo();	
		_freightToBroker = ctrl.mFreightToBroker();
		_extraPayToBroker = ctrl.mExtraPayToBroker();
		_advance = ctrl.mAdvance();
		_balanceFreight = ctrl.mBalanceFreight();
		_loadingCharges = ctrl.mLoadingCharges();
		_unloadingCharges = ctrl.mUnloadingCharges();
		_loadingDetBroker = ctrl.mLoadingDetBroker();
		_unloadingDetBroker = ctrl.mUnloadingDetBroker();
		
	}

	public interface Controller {
				
		long mLRNo();
		void mLRNo(long lrNo);		

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

		

		
		
	}
	
	public abstract static class DefaultController implements Controller {

		
		@Override
		public void mLRNo(long lrNo) { }
		
		@Override
		public void mFreightToBroker(int freightToBroker) { }

		@Override
		public void mExtraPayToBrokersignor(int extraPayToBroker) { }
		
		
		@Override
		public void mAdvance(int advance) { }

		@Override
		public void mBalanceFreight(int balanceFreight) { }
		

		@Override
		public void mLoadingCharges(int loadingCharges) { }
		
		@Override
		public void mUnloadingCharges(int unloadingCharges) { }
		
		@Override
		public void mLoadingDetBroker(int loadingDetBroker) { }
		
		@Override
		public void mUnloadingDetBroker(int unloadingDetBroker) { }
				
	}

	//getter and setter
	
	
	public long getId() {
		return _id;
	}

	public void setId(long id) {
		this._id = id;
	}

	public long getLrNo() {
		return _lrNo;
	}

	public void setLrNo(long lrNo) {
		this._lrNo = lrNo;
	}

	public int getFreightToBroker() {
		return _freightToBroker;
	}

	public void setFreightToBroker(int freightToBroker) {
		this._freightToBroker = freightToBroker;
	}

	public int getExtraPayToBroker() {
		return _extraPayToBroker;
	}

	public void setExtraPayToBroker(int extraPayToBroker) {
		this._extraPayToBroker = extraPayToBroker;
	}

	public int getAdvance() {
		return _advance;
	}

	public void setAdvance(int advance) {
		this._advance = advance;
	}

	public int getBalanceFreight() {
		return _balanceFreight;
	}

	public void setBalanceFreight(int balanceFreight) {
		this._balanceFreight = balanceFreight;
	}

	public int getLoadingCharges() {
		return _loadingCharges;
	}

	public void setLoadingCharges(int loadingCharges) {
		this._loadingCharges = loadingCharges;
	}

	public int getUnloadingCharges() {
		return _unloadingCharges;
	}

	public void setUnloadingCharges(int unloadingCharges) {
		this._unloadingCharges = unloadingCharges;
	}

	public int getLoadingDetBroker() {
		return _loadingDetBroker;
	}

	public void setLoadingDetBroker(int loadingDetBroker) {
		this._loadingDetBroker = loadingDetBroker;
	}

	public int getUnloadingDetBroker() {
		return _unloadingDetBroker;
	}

	public void setUnloadingDetBroker(int unloadingDetBroker) {
		this._unloadingDetBroker = unloadingDetBroker;
	}

	
	
	public static long mSerialversionuid() 		{ return serialVersionUID;		}
	
	
	

}
