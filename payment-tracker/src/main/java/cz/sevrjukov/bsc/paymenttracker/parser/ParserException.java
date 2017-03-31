package cz.sevrjukov.bsc.paymenttracker.parser;

/**
 * General exception which happens during file parsing, for various reasons.
 * 
 * @author Alexandr Sevrjukov
 *
 */
public class ParserException extends Exception {

	private static final long serialVersionUID = 2344834341781381923L;

	public ParserException() {
	}

	public ParserException(String message) {
		super(message);
	}

	public ParserException(Throwable cause) {
		super(cause);
	}

	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
