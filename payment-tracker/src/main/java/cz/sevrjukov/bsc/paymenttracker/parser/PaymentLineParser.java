package cz.sevrjukov.bsc.paymenttracker.parser;

import org.springframework.stereotype.Component;

import cz.sevrjukov.bsc.paymenttracker.model.Payment;

@Component
public class PaymentLineParser implements LineParser<Payment> {

	private static final String TOKENS_SEPARATOR = " ";
	
	@Override
	public Payment parseLine(String line) throws ParserException {
		
		if (line == null) {
			throw new ParserException("Input line should not be null");
		}
		
		String[] tokens = line.split(TOKENS_SEPARATOR);
		if (tokens.length != 2) {
			throw new ParserException(String.format("Invalid input line '%s'", line));
		}

		Payment p = new Payment();
		p.setCurrency(tokens[0]);

		try {
			int amount = Integer.parseInt(tokens[1]);
			p.setAmount(amount);
		} catch (NumberFormatException ex) {
			throw new ParserException(
					String.format("Cannot parse payment line, '%s' is not a valid integer", tokens[1]));
		}
		return p;
	}


}
