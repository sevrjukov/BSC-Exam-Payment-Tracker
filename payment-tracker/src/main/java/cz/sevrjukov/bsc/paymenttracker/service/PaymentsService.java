package cz.sevrjukov.bsc.paymenttracker.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import cz.sevrjukov.bsc.paymenttracker.model.NetPayments;
import cz.sevrjukov.bsc.paymenttracker.model.Payment;

/**
 * Stores and processes Payment records.
 * 
 * @author Alexandr Sevrjukov
 *
 */
@Service
@Validated
public class PaymentsService {

	private static final String USD_CURRENCY_CODE = "USD";

	@Autowired
	private ExchangeRatesService ratesService;

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
	 * Calculates statistics - net amount of all transactions per currency,
	 * including amount in USD, if the exchange rate to this currency is
	 * specified.
	 * 
	 * @return
	 */
	public Map<String, NetPayments> calculateNetAmounts() {

		// temporary map for calculation results
		Map<String, NetPayments> resultMap = new HashMap<>();
		// synchronized to make sure the collection
		// is not modified during the calculation
		synchronized (paymentsSet) {
			for (Payment payment : paymentsSet) {
				String currency = payment.getCurrency();
				NetPayments netForCurrency = resultMap.get(currency);

				if (netForCurrency == null) {
					// first record for this particular currency
					resultMap.put(currency, new NetPayments(currency, payment.getAmount()));
				} else {
					// record already exists, update it
					int newValue = netForCurrency.getAmount() + payment.getAmount();
					netForCurrency.setAmount(newValue);
				}
			}
		}

		// now, calculate the value in USD
		for (Map.Entry<String, NetPayments> entry : resultMap.entrySet()) {

			NetPayments net = entry.getValue();
			// for non-zero amounts calculate also value in USD
			if (net.getAmount() != 0) {
				Double exchangeRate = ratesService.getExchangeRate(USD_CURRENCY_CODE, net.getCurrency());
				if (exchangeRate != null) {
					double amountInOther = net.getAmount() / exchangeRate;
					net.setAmountInOther(amountInOther);
					net.setOtherCurrency(USD_CURRENCY_CODE);
				}
			}
		}
		return resultMap;

	}

}
