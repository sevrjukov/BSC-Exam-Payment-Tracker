package cz.sevrjukov.bsc.paymenttracker.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import cz.sevrjukov.bsc.paymenttracker.model.Payment;

@Service
@Validated
public class PaymentsService {

	/**
	 * I consider in-memory storage sufficient for this application. Otherwise
	 * I'd use some sort of repository
	 */
	private Set<Payment> paymentsSet = new HashSet<>();

	/**
	 * Validates and adds new payment to the registry.
	 * 
	 * @throws ConstraintViolationException
	 *             in case that Payment object validation failed
	 * 
	 * @param payment
	 */
	public void addNewPayment(@Valid @NotNull Payment payment) {
		// synchronizing to make sure we don't interrupt
		// stats calculation in another thread
		synchronized (paymentsSet) {
			paymentsSet.add(payment);
		}
	}

	/**
	 * Calculates statistics - net amount of all transactions per currency.
	 * 
	 * @return
	 */
	public Map<String, Integer> calculateNetAmounts() {

		// temporary map for calculation results
		Map<String, Integer> resultMap = new HashMap<>();
		// synchronized to make sure the collection
		// is not modified during the calculation
		synchronized (paymentsSet) {
			for (Payment payment : paymentsSet) {
				String currency = payment.getCurrency();
				Integer valForCurrency = resultMap.get(currency);

				if (valForCurrency == null) {
					// first record for this particular currency
					resultMap.put(currency, payment.getAmount());
				} else {
					// record already exists, update it
					int newValue = valForCurrency.intValue() + payment.getAmount();
					resultMap.put(currency, new Integer(newValue));
				}
			}
		}
		return resultMap;
	}

}
