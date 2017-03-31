package cz.sevrjukov.bsc.paymenttracker.application;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cz.sevrjukov.bsc.paymenttracker.model.NetPayments;
import cz.sevrjukov.bsc.paymenttracker.service.PaymentsService;


/**
 * Periodically displays statistics - net amounts
 * for each currency, including conversion to USD,
 * if the exchange rate is specified.
 * 
 * @author Alexandr Sevrjukov
 *
 */
@Component
public class StatsPrinter {

	@Autowired
	private PaymentsService service;

	/**
	 * Timer periodicity
	 */
	private static final int TEN_SECONDS = 1000 * 10;

	
	/**
	 * Scheduled method, outputs statistics to console.
	 */
	@Scheduled(fixedRate = TEN_SECONDS)
	public void printStats() {
		
		// the map containts net amounts for each currency, the map
		// key is the currency itself (for conveniency)
		Map<String, NetPayments> stats = service.calculateNetAmounts();
		
		System.out.println("-------------------------");
		for (Map.Entry<String, NetPayments> entry : stats.entrySet()) {
			// net payments per currency
			NetPayments net = entry.getValue();
			// avoid priting zeros
			if (net.getAmount() == 0) {
				continue;
			}
			System.out.print(String.format("%s %s  ", net.getCurrency(), net.getAmount()));

			// if amount in other currency specified, print it also
			if (net.getAmountInOther() != 0) {
				System.out.print(String.format("(%s %.4f)", net.getOtherCurrency(), net.getAmountInOther()));
			}
			// endline
			System.out.println();
		}
		System.out.println("-------------------------");
	}

}
