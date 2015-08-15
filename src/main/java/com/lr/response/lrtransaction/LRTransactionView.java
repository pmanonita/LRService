package com.lr.response.lrtransaction;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.lr.model.LR.Controller;
import com.lr.model.LRBill;
import com.lr.model.LRChalan;
import com.lr.model.LRTransOtherExp;
import com.lr.model.LRTransOtherIncome;

public class LRTransactionView {
	
	private long    id;
	private String 	lrs;	
	private int  	multiLoadCharge;
	private int  	freightToBroker;	
	private int  	extraPayToBroker;
	private int  	advance;
	private int  	balanceFreight;
	private int  	loadingCharges;
	private int  	unloadingCharges;
	private int  	loadingDetBroker;
	private int  	unloadingDetBroker;		
	private int  	multiLoadChargeBilling;
	private int  	freightToBrokerBilling;
	private int  	loadingChargesBilling;
	private int  	unloadingChargesBilling;
	private int  	loadingDetBrokerBilling;
	private int  	unloadingDetBrokerBilling;
	private String  status;
	private Date	createDate;
	

	
	public LRTransactionView() {	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getLrs() {
		return lrs;
	}



	public void setLrs(String lrs) {
		this.lrs = lrs;
	}



	public int getMultiLoadCharge() {
		return multiLoadCharge;
	}



	public void setMultiLoadCharge(int multiLoadCharge) {
		this.multiLoadCharge = multiLoadCharge;
	}



	public int getFreightToBroker() {
		return freightToBroker;
	}



	public void setFreightToBroker(int freightToBroker) {
		this.freightToBroker = freightToBroker;
	}



	public int getExtraPayToBroker() {
		return extraPayToBroker;
	}



	public void setExtraPayToBroker(int extraPayToBroker) {
		this.extraPayToBroker = extraPayToBroker;
	}



	public int getAdvance() {
		return advance;
	}



	public void setAdvance(int advance) {
		this.advance = advance;
	}



	public int getBalanceFreight() {
		return balanceFreight;
	}



	public void setBalanceFreight(int balanceFreight) {
		this.balanceFreight = balanceFreight;
	}



	public int getLoadingCharges() {
		return loadingCharges;
	}



	public void setLoadingCharges(int loadingCharges) {
		this.loadingCharges = loadingCharges;
	}



	public int getUnloadingCharges() {
		return unloadingCharges;
	}



	public void setUnloadingCharges(int unloadingCharges) {
		this.unloadingCharges = unloadingCharges;
	}



	public int getLoadingDetBroker() {
		return loadingDetBroker;
	}



	public void setLoadingDetBroker(int loadingDetBroker) {
		this.loadingDetBroker = loadingDetBroker;
	}



	public int getUnloadingDetBroker() {
		return unloadingDetBroker;
	}



	public void setUnloadingDetBroker(int unloadingDetBroker) {
		this.unloadingDetBroker = unloadingDetBroker;
	}



	public int getMultiLoadChargeBilling() {
		return multiLoadChargeBilling;
	}



	public void setMultiLoadChargeBilling(int multiLoadChargeBilling) {
		this.multiLoadChargeBilling = multiLoadChargeBilling;
	}



	public int getFreightToBrokerBilling() {
		return freightToBrokerBilling;
	}



	public void setFreightToBrokerBilling(int freightToBrokerBilling) {
		this.freightToBrokerBilling = freightToBrokerBilling;
	}



	public int getLoadingChargesBilling() {
		return loadingChargesBilling;
	}



	public void setLoadingChargesBilling(int loadingChargesBilling) {
		this.loadingChargesBilling = loadingChargesBilling;
	}



	public int getUnloadingChargesBilling() {
		return unloadingChargesBilling;
	}



	public void setUnloadingChargesBilling(int unloadingChargesBilling) {
		this.unloadingChargesBilling = unloadingChargesBilling;
	}



	public int getLoadingDetBrokerBilling() {
		return loadingDetBrokerBilling;
	}



	public void setLoadingDetBrokerBilling(int loadingDetBrokerBilling) {
		this.loadingDetBrokerBilling = loadingDetBrokerBilling;
	}



	public int getUnloadingDetBrokerBilling() {
		return unloadingDetBrokerBilling;
	}



	public void setUnloadingDetBrokerBilling(int unloadingDetBrokerBilling) {
		this.unloadingDetBrokerBilling = unloadingDetBrokerBilling;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public Date getCreateDate() {
		return createDate;
	}



	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
