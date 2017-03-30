package cz.sevrjukov.bsc.paymenttracker.application;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cz.sevrjukov.bsc.paymenttracker.service.PaymentsService;

@Component
public class StatsPrinter {

	@Autowired
	private PaymentsService service;

	private static final int ONE_MINUTE = 1000 * 10;

	@Scheduled(fixedRate = ONE_MINUTE)
	public void printStats() {
		System.out.println("-------------------------");

		Map<String, Integer> stats = service.calculateNetAmounts();
		for (Map.Entry<String, Integer> entry : stats.entrySet()) {
			
			// omit priting zeros
			if (entry.getValue() != 0) {
				System.out.println(String.format("%s \t %s", entry.getKey(), entry.getValue()));
			}
		}
		System.out.println("-------------------------");
	}

}
