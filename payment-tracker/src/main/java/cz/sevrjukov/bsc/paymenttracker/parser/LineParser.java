package cz.sevrjukov.bsc.paymenttracker.parser;

public interface LineParser<T> {

	public T parseLine(String line) throws ParserException;

}
