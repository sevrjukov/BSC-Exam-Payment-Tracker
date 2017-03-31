package cz.sevrjukov.bsc.paymenttracker.parser;

/**
 * Base generic interface for line parsers.
 * Implementing classes should take one line of input and parse to
 * to an entity class.
 * @author Alexandr Sevrjukov
 *
 * @param <T>
 */
public interface LineParser<T> {

	/**
	 * Parses one input line to an entity object.
	 * @param line - input line
	 * @return
	 * @throws ParserException in case that the line is invalid and cannot be parsed.
	 */
	public T parseLine(String line) throws ParserException;

}
