package com.lr.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.lr.exceptions.InsufficientDataException;


public class LR implements Serializable {	

	private static final long serialVersionUID = -6779738051490200702L;
	
	private long    _id;
	private long _transid;	
	private String _vehicleNo;
	private String _vehicleOwner;
	private String   _billingToParty;
	private Date   _lrDate;	
	private String   _multiLoad;
	private String _userName;
	private Date _updatetimestamp;
	
	private LRExpenditure _expId;
	private Consigner _consignerId;
	private Consignee _consigneeId;
	
	
	
	//For hibernate
	public LR() {
		
	}
	
	public LR (Controller ctrl) {
		createFrom(ctrl);
		
	}
	
	private void validate(Controller ctrl) throws InsufficientDataException {
		//Model level validation
		String errorMsg = "";
		if ((null == ctrl.mVehicleNo() || (null != ctrl.mMultiLoad() && ctrl.mUserName().equals("")))) 
		{
			errorMsg = "User name and password can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}	
		
	}
	
	private void createFrom(Controller ctrl) {

		validate(ctrl);
		
		_transid = ctrl.mTransid();		
		_vehicleNo = ctrl.mVehicleNo();	
		_consignerId = ctrl.mConsignerId();
		_consigneeId = ctrl.mConsigneeId();		
		_vehicleOwner = ctrl.mVehicleOwner();
		_billingToParty = ctrl.mBillingToParty();
		_lrDate = ctrl.mLrDate();		
		_userName = ctrl.mUserName();		
	}
	
	public void changeTo(Controller ctrl) {
		
		validate(ctrl);
		
		_transid = ctrl.mTransid();		
		_vehicleNo = ctrl.mVehicleNo();	
		_consignerId = ctrl.mConsignerId();
		_consigneeId = ctrl.mConsigneeId();		
		_vehicleOwner = ctrl.mVehicleOwner();
		_billingToParty = ctrl.mBillingToParty();
		_lrDate = ctrl.mLrDate();		
		_userName = ctrl.mUserName();
		
	}

	public interface Controller {
				
		long mTransid();
		void mTransid(long transid);		

		String mVehicleNo();
		void mVehicleNo(String _vehicleNo);

		Consigner mConsignerId();
		void mConsignerId(Consigner _consignerId);

		Consignee mConsigneeId();
		void mConsigneeId(Consignee _consigneeId);
		

		String mVehicleOwner();
		void mVehicleOwner(String _vehicleOwner);

		String mBillingToParty();
		void mBillingToParty(String _billingToParty);

		Date mLrDate();
		void mLrDate(Date _lrDate);		

		String mMultiLoad();
		void mMultiLoad(String _multiLoad);
		
		String mUserName();
		void mUserName(String _userName);

		
		
	}
	
	public abstract static class DefaultController implements Controller {
		public void mTransid(long transid) 		{	}
		public void mVehicleNo(String vehicleNo) 		{	}
		public void mConsignerId(Consigner consignerId)	{	}
		public void mConsigneeId(Consignee consigneeId) 		{	}		
		public void mVehicleOwner(String vehicleOwner) 	{	}
		public void mBillingToParty(String billingToParty) 		{	}
		public void mLrDate(Date lrDate)	 			{	}
		public void mMultiLoad(String multiLoad) 	{	}
		public void mUserName(String userName) 	{	}		
	}

	
	//gerrter and setter
	
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

	public String getVehicleNo() {
		return _vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this._vehicleNo = vehicleNo;
	}

	public Consigner getConsignerId() {
		return _consignerId;
	}

	public void setConsignerId(Consigner consignerId) {
		this._consignerId = consignerId;
	}

	public Consignee getConsigneeId() {
		return _consigneeId;
	}

	public void setConsigneeId(Consignee consigneeId) {
		this._consigneeId = consigneeId;
	}	

	public String getVehicleOwner() {
		return _vehicleOwner;
	}

	public void setVehicleOwner(String vehicleOwner) {
		this._vehicleOwner = vehicleOwner;
	}

	public String getBillingToParty() {
		return _billingToParty;
	}

	public void setBillingToParty(String billingToParty) {
		this._billingToParty = billingToParty;
	}

	public Date getLrDate() {
		return _lrDate;
	}

	public void setLrDate(Date lrDate) {
		this._lrDate = lrDate;
	}

	public String getMultiLoad() {
		return _multiLoad;
	}

	public void setMultiLoad(String multiLoad) {
		this._multiLoad = multiLoad;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		this._userName = userName;
	}
	
	

	public Date getUpdatetimestamp() {
		return _updatetimestamp;
	}

	public void setUpdatetimestamp(Date updatetimestamp) {
		this._updatetimestamp = updatetimestamp;
	}

	public LRExpenditure getExpId() {
		return _expId;
	}

	public void setExpId(LRExpenditure expId) {
		this._expId = expId;
	}

	

	public static long mSerialversionuid() 		{ return serialVersionUID;		}
	
	
	
	
	
	
	
}
