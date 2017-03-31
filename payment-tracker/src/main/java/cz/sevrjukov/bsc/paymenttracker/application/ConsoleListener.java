package cz.sevrjukov.bsc.paymenttracker.application;

import java.util.Scanner;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.sevrjukov.bsc.paymenttracker.model.Payment;
import cz.sevrjukov.bsc.paymenttracker.parser.ParserException;
import cz.sevrjukov.bsc.paymenttracker.parser.PaymentLineParser;
import cz.sevrjukov.bsc.paymenttracker.service.PaymentsService;


/**
 * Listens for user input via console and processes it.
 * Servers as a mini-controller. Launches a separate listener
 * thread.
 * 
 * @author Alexandr Sevrjukov
 *
 */
@Component
public class ConsoleListener {

	private static final String QUIT_COMMAND = "quit";

	@Autowired
	private PaymentLineParser lineParser;

	@Autowired
	private PaymentsService service;
	
	
	private Thread consoleListeningThread;

	@PostConstruct
	public void init() {
		// start a thread that listens for user inputs
		consoleListeningThread = new Thread(new ConsoleListeningThread());
		consoleListeningThread.start();
	}

	/**
	 * Mini-controller. Processes a single console line input from user.
	 * 
	 * @param input
	 */
	private void processUserInput(String input) {

		if (input.equalsIgnoreCase(QUIT_COMMAND)) {
			// stop the thread
			consoleListeningThread.interrupt();
			// gracefully shut down the application
			ApplicationStarter.stop();
		} else {
			// consider it the currency input
			processPaymentLine(input);
		}

	}

	/**
	 * Attempts to parse payment input line and store it
	 * 
	 * @param paymentLine
	 */
	private void processPaymentLine(String paymentLine) {
		String line = paymentLine.trim();
		try {
			Payment p = lineParser.parseLine(line);
			service.addNewPayment(p);
			System.out.println(String.format("New payment %s added", p.toString()));
		} catch (ParserException pex) {
			// this is parser exception
			System.out.println(String.format("Incorrectly specified payment record, %s", pex.getMessage()));
		} catch (ConstraintViolationException ex) {
			// this is bean validation exception
			Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
			System.out.println("Incorrectly specified payment record. Errors:");
			for (ConstraintViolation<?> violation : violations) {
				System.out.println(violation.getMessage());
			}
		}
	}

	private class ConsoleListeningThread implements Runnable {
		@Override
		public void run() {
			String inputLine = "";
			try (Scanner sc = new Scanner(System.in)) {
				while (true) {
					// check if we should finish this thread
					if (Thread.interrupted()) {
						return;
					}
					inputLine = sc.nextLine();
					processUserInput(inputLine);
				}
			}
		}
	}

}
