package cz.sevrjukov.bsc.paymenttracker.model;

public class NetPayments extends Payment {

	private String otherCurrency;
	
	private double amountInOther;

	public String getOtherCurrency() {
		return otherCurrency;
	}

	public void setOtherCurrency(String otherCurrency) {
		this.otherCurrency = otherCurrency;
	}

	public double getAmountInOther() {
		return amountInOther;
	}

	public void setAmountInOther(double amountInOther) {
		this.amountInOther = amountInOther;
	}
	
	
	

}
