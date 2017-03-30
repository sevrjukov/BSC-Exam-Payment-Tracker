package cz.sevrjukov.bsc.paymenttracker.model;

public class NetPayments {

	private String currency;

	private int amount;

	private String otherCurrency;

	private double amountInOther;

	public NetPayments(String currency, int amount) {
		this.currency = currency;
		this.amount = amount;
	}
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

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
