package cz.sevrjukov.bsc.paymenttracker.model;

public class Payment {

	
	private String currency;

	/**
	 * It is assumed that amounts are always integers
	 */
	private int amount;

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

}
