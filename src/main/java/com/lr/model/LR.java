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
	private int _transid;	
	private String _vehicleNo;	
	private String _consignor;
	private String   _consignee;
	private String _consignerServtax;
	private String _consigneeServtax;
	private String _vehicleOwner;
	private String   _billingToParty;
	private Date   _lrDate;	
	private String   _multiLoad;
	private String _userid;
	private Date _updatetimestamp;
	
	private LRExpenditure _expId;
	
	
	//For hibernate
	public LR() {
		
	}
	
	public LR (Controller ctrl) {
		createFrom(ctrl);
		
	}
	
	private void validate(Controller ctrl) throws InsufficientDataException {
		//Model level validation
		String errorMsg = "";
		if ((null == ctrl.mVehicleNo() || (null != ctrl.mMultiLoad() && ctrl.mUserid().equals("")))) 
		{
			errorMsg = "User name and password can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}	
		
	}
	
	private void createFrom(Controller ctrl) {

		validate(ctrl);
		
		_transid = ctrl.mTransid();		
		_vehicleNo = ctrl.mVehicleNo();	
		_consignor = ctrl.mConsignor();
		_consignee = ctrl.mConsignee();
		_consignerServtax = ctrl.mConsignerServtax();
		_consigneeServtax = ctrl.mConsigneeServtax();
		_vehicleOwner = ctrl.mVehicleOwner();
		_billingToParty = ctrl.mBillingToParty();
		_lrDate = ctrl.mLrDate();		
		_userid = ctrl.mUserid();		
	}
	
	public void changeTo(Controller ctrl) {
		
		validate(ctrl);
		
		_transid = ctrl.mTransid();		
		_vehicleNo = ctrl.mVehicleNo();	
		_consignor = ctrl.mConsignor();
		_consignee = ctrl.mConsignee();
		_consignerServtax = ctrl.mConsignerServtax();
		_consigneeServtax = ctrl.mConsigneeServtax();
		_vehicleOwner = ctrl.mVehicleOwner();
		_billingToParty = ctrl.mBillingToParty();
		_lrDate = ctrl.mLrDate();		
		_userid = ctrl.mUserid();
		
	}

	public interface Controller {
				
		int mTransid();
		void mTransid(int transid);		

		String mVehicleNo();
		void mVehicleNo(String _vehicleNo);

		String mConsignor();
		void mConsignor(String _consignor);

		String mConsignee();
		void mConsignee(String _consignee);

		String mConsignerServtax();
		void mConsignerServtax(String _consignerServtax);
		 
		String mConsigneeServtax();
		void mConsigneeServtax(String _consigneeServtax);

		String mVehicleOwner();
		void mVehicleOwner(String _vehicleOwner);

		String mBillingToParty();
		void mBillingToParty(String _billingToParty);

		Date mLrDate();
		void mLrDate(Date _lrDate);		

		String mMultiLoad();
		void mMultiLoad(String _multiLoad);
		
		String mUserid();
		void mUserid(String _userid);

		
		
	}
	
	public abstract static class DefaultController implements Controller {
		public void mTransid(int transid) 		{	}
		public void mVehicleNo(String vehicleNo) 		{	}
		public void mConsignor(String consignor)	{	}
		public void mConsignee(String consignee) 		{	}
		public void mConsignerServtax(String consignerServtax) 			{	}
		public void mConsigneeServtax(String consignorServtax)	 		{	}
		public void mVehicleOwner(String vehicleOwner) 	{	}
		public void mBillingToParty(String billingToParty) 		{	}
		public void mLrDate(Date lrDate)	 			{	}
		public void mMultiLoad(String multiLoad) 	{	}
		public void mUserid(String userid) 	{	}		
	}

	
	//gerrter and setter
	
	public long getId() {
		return _id;
	}

	protected void setId(long id) {
		this._id = id;
	}

	public int getTransid() {
		return _transid;
	}

	void setTransid(int transid) {
		this._transid = transid;
	}

	public String getVehicleNo() {
		return _vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this._vehicleNo = vehicleNo;
	}

	public String getConsignor() {
		return _consignor;
	}

	public void setConsignor(String consignor) {
		this._consignor = consignor;
	}

	public String getConsignee() {
		return _consignee;
	}

	public void setConsignee(String consignee) {
		this._consignee = consignee;
	}

	public String getConsignerServtax() {
		return _consignerServtax;
	}

	public void setConsignerServtax(String consignerServtax) {
		this._consignerServtax = consignerServtax;
	}

	public String getConsigneeServtax() {
		return _consigneeServtax;
	}

	public void setConsigneeServtax(String consigneeServtax) {
		this._consigneeServtax = consigneeServtax;
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

	public String getUserid() {
		return _userid;
	}

	public void setUserid(String userid) {
		this._userid = userid;
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
