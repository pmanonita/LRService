package com.lr.response;

public class LRBillView {

	private long    id;
	private String lrIds;
	private String billDetails;
	private int totalCost;
	
    
    public LRBillView() {}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getLrIds() {
		return lrIds;
	}


	public void setLrIds(String lrIds) {
		this.lrIds = lrIds;
	}


	public String getBillDetails() {
		return billDetails;
	}


	public void setBillDetails(String billDetails) {
		this.billDetails = billDetails;
	}


	public int getTotalCost() {
		return totalCost;
	}


	public void setTotalCost() {
		//calculation of totalcost
		int totalIncome = 0;
		if (billDetails != null) {
			String[] incomeArr = billDetails.split(",");
			for (int i=0;i<incomeArr.length;i++){
				incomeArr[i] = incomeArr[i].replaceAll("\"","");
				String[] incomeColumnArr = incomeArr[i].split("-");
				if( incomeColumnArr.length>1 ){
					int amount = 0;
					if (incomeColumnArr[0].contains("EXTRA PAY TO BROKER") || incomeColumnArr[0].contains("ADVANCE") || incomeColumnArr[0].contains("BALANCE FREIGHT") ) {
						continue;
					} else {
						try{
							amount = Integer.parseInt(incomeColumnArr[1]);
							totalIncome = totalIncome+amount;
							
						}catch(Exception e){	}
						
					}
					
				}
				
			}
		}
		this.totalCost = totalIncome;		
	}
	
	


	

	
   

}
