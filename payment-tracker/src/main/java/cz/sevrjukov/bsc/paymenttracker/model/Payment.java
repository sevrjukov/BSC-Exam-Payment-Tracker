package cz.sevrjukov.bsc.paymenttracker.model;

import java.util.Objects;

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

	/**
	 * Default no-params constructor
	 */
	public Payment() {

	}

	public Payment(String currency, int amount) {
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

	@Override
	public String toString() {
		return String.format("Payment [%s][%s]", currency, amount);
	}

	@Override
	public int hashCode() {
		return Objects.hash(currency, amount);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Payment)) {
			return false;
		} else {
			return (this.hashCode() == ((Payment) other).hashCode());
		}
	}

}
