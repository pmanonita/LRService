package com.lr.response;

import java.util.List;

import com.lr.model.Billingname;
import com.lr.model.Consignee;
import com.lr.model.Consigner;
import com.lr.model.LRTransaction;

public class LRListView {

	private long    		id;
	private LRTransaction  	transaction;	
	private String 			vehicleNo;
	private String 			vehicleOwner;
	private Consigner 		consigner;
	private Consignee 		consignee;	
	private String 			billingParty;
	private String 			lrDate;
	private String 			poNo;
	private String 			doNo;
	private Billingname 	billingname;
	private String 			status;
	private String 			multiLoad;
	private String 			userName;
	
	//Exp
	private long    lrExpenditureId;
	private long 	lrId;
	private int 	freightToBroker;
	private int 	extraPayToBroker;
	private int 	advance;
	private int   	balanceFreight;
	private int 	loadingCharges;
	private int 	unloadingCharges;
	private int 	loadingDetBroker;
	private int 	unloadingDetBroker;
	
	//List
	private List<LROthersView> 		lrOthers;
	private List<LROtherIncomeView> lrOtherIncome;
	
	//LRIncome
	private long    incomeId;
	private long 	lrIdBilling;
	private int 	freightToBrokerBilling;
	private int 	extraPayToBrokerBilling;	
	private int 	loadingChargesBilling;
	private int 	unloadingChargesBilling;
	private int 	loadingDetBrokerBilling;
	private int 	unloadingDetBrokerBilling;
	
	//LRChalan
	private LRChalanView chalan;
	
	//LRBill
	private LRBillView bill;
	
    public LRListView() {}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getVehicleNo() {
		return vehicleNo;
	}


	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}


	public String getVehicleOwner() {
		return vehicleOwner;
	}


	public void setVehicleOwner(String vehicleOwner) {
		this.vehicleOwner = vehicleOwner;
	}	

	
	public String getBillingParty() {
		return billingParty;
	}


	public void setBillingParty(String billingParty) {
		this.billingParty = billingParty;
	}


	public String getPoNo() {
		return poNo;
	}


	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}


	public String getDoNo() {
		return doNo;
	}


	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}


	public Billingname getBillingname() {
		return billingname;
	}


	public void setBillingname(Billingname billingname) {
		this.billingname = billingname;
	}
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Consigner getConsigner() {
		return consigner;
	}


	public void setConsigner(Consigner consigner) {
		this.consigner = consigner;
	}


	public Consignee getConsignee() {
		return consignee;
	}


	public void setConsignee(Consignee consignee) {
		this.consignee = consignee;
	}	
	
	
	public long getLrExpenditureId() {
		return lrExpenditureId;
	}


	public void setLrExpenditureId(long lrExpenditureId) {
		this.lrExpenditureId = lrExpenditureId;
	}


	public long getLrId() {
		return lrId;
	}


	public void setLrId(long lrId) {
		this.lrId = lrId;
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


	public List<LROthersView> getLrOthers() {
		return lrOthers;
	}


	public void setLrOthers(List<LROthersView> lrOthers) {
		this.lrOthers = lrOthers;
	}


	public long getIncomeId() {
		return incomeId;
	}


	public void setIncomeId(long incomeId) {
		this.incomeId = incomeId;
	}


	public long getLrIdBilling() {
		return lrIdBilling;
	}


	public void setLrIdBilling(long lrIdBilling) {
		this.lrIdBilling = lrIdBilling;
	}


	public int getFreightToBrokerBilling() {
		return freightToBrokerBilling;
	}


	public void setFreightToBrokerBilling(int freightToBrokerBilling) {
		this.freightToBrokerBilling = freightToBrokerBilling;
	}


	public int getExtraPayToBrokerBilling() {
		return extraPayToBrokerBilling;
	}


	public void setExtraPayToBrokerBilling(int extraPayToBrokerBilling) {
		this.extraPayToBrokerBilling = extraPayToBrokerBilling;
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


	public LRTransaction getTransaction() {
		return transaction;
	}


	public void setTransaction(LRTransaction transaction) {
		this.transaction = transaction;
	}


	public String getLrDate() {
		return lrDate;
	}


	public void setLrDate(String lrDate) {
		this.lrDate = lrDate;
	}


	public String getMultiLoad() {
		return multiLoad;
	}


	public void setMultiLoad(String multiLoad) {
		this.multiLoad = multiLoad;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public List<LROtherIncomeView> getLrOtherIncome() {
		return lrOtherIncome;
	}


	public void setLrOtherIncome(List<LROtherIncomeView> lrOtherIncome) {
		this.lrOtherIncome = lrOtherIncome;
	}


	public LRChalanView getChalan() {
		return chalan;
	}


	public void setChalan(LRChalanView chalan) {
		this.chalan = chalan;
	}


	public LRBillView getBill() {
		return bill;
	}


	public void setBill(LRBillView bill) {
		this.bill = bill;
	}  

}
