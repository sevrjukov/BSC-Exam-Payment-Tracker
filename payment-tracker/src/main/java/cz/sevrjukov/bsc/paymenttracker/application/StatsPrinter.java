package cz.sevrjukov.bsc.paymenttracker.application;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cz.sevrjukov.bsc.paymenttracker.model.NetPayments;
import cz.sevrjukov.bsc.paymenttracker.service.PaymentsService;

@Component
public class StatsPrinter {

	@Autowired
	private PaymentsService service;

	private static final int ONE_MINUTE = 1000 * 10;

	@Scheduled(fixedRate = ONE_MINUTE)
	public void printStats() {
		
		Map<String, NetPayments> stats = service.calculateNetAmounts();
		
		System.out.println("-------------------------");
		for (Map.Entry<String, NetPayments> entry : stats.entrySet()) {
			NetPayments net = entry.getValue();
			if (net.getAmount() == 0) {
				continue;
			}
			System.out.print(String.format("%s %s  ", net.getCurrency(), net.getAmount()));

			if (net.getAmountInOther() != 0) {
				System.out.print(String.format("(%s %.4f)", net.getOtherCurrency(), net.getAmountInOther()));
			}
			// endline
			System.out.println();
		}
		System.out.println("-------------------------");
	}

}
