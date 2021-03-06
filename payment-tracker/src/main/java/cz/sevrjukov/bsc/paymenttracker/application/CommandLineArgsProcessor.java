package cz.sevrjukov.bsc.paymenttracker.application;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import cz.sevrjukov.bsc.paymenttracker.model.Payment;
import cz.sevrjukov.bsc.paymenttracker.parser.ParserException;
import cz.sevrjukov.bsc.paymenttracker.parser.PaymentsFileParser;
import cz.sevrjukov.bsc.paymenttracker.service.PaymentsService;


/**
 * Processes command line arguments with which
 * the program was launched. 
 * 
 * @author Alexandr Sevrjukov
 *
 */
@Component
public class CommandLineArgsProcessor {

	Logger logger = LoggerFactory.getLogger(CommandLineArgsProcessor.class);

	@Autowired
	private PaymentsService paymentsService;

	@Autowired
	private PaymentsFileParser parser;

	private ApplicationArguments args;

	/**
	 * Automatically injects command line arguments
	 * @param args
	 */
	@Autowired
	public CommandLineArgsProcessor(ApplicationArguments args) {
		this.args = args;
	}

	
	/**
	 * Processes the input file with payments
	 * specified as the first argument
	 */
	@PostConstruct
	public void processInputFile() {

		String[] argStr = args.getSourceArgs();
		if (argStr.length == 0) {
			// no file specified, ok
			logger.info("No input file specified");
			return;
		}

		// this should be input file path
		String filePath = argStr[0];

		try {
			Collection<Payment> records = parser.parseFile(filePath);
			int recordsAdded = 0;
			for (Payment payment : records) {
				try {
					// try to add new record to the registry
					// this throws ConstraintViolationException if
					// validation failed
					paymentsService.addNewPayment(payment);
					recordsAdded++;
					logger.debug(payment.toString());
				} catch (ConstraintViolationException ex) {
					logger.warn("Ignoring invalid Payment record " + payment);
				}
			}
			logger.info(String.format("Input file parsed, added %s payment records", recordsAdded));
		} catch (ParserException ex) {
			// file parsing error (file missing, inaccessible etc)
			logger.error(String.format("Failed to parse input file %s", filePath), ex);
		}
	}

}
