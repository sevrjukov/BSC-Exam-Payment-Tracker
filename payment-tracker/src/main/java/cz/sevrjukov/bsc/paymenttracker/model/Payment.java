package cz.sevrjukov.bsc.paymenttracker.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Payment {

	@NotNull(message = "Currency must be specified")
	@Pattern(regexp = "^[A-Z]{3}$", message = "Currency wrongly specified, expected 3 capital characters")
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
