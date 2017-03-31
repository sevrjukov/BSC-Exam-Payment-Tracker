package cz.sevrjukov.bsc.paymenttracker.parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;


/**
 * Base generic class for file parsers.
 * Takes care of file handling, but leaves the actual parsing
 * to implementors.
 * 
 * @author Alexandr Sevrjukov
 *
 * @param <T>
 */
public abstract class AbstractFileParser<T> {

	
	
	public Collection<T> parseFile(String filePath) throws ParserException {
		// check if the input file exists
		Path fileToParse = Paths.get(filePath);
		if (!Files.exists(fileToParse)) {
			throw new ParserException(String.format("The specified file [%s] path does not exist", filePath));
		}
		return parseContent(fileToParse);
	}
	
	/**
	 * Parses the input file and returns a collection of entities
	 * parsed from the file.
	 * @param fileToParse
	 * @return
	 * @throws ParserException
	 */
	abstract protected Collection<T> parseContent(Path fileToParse) throws ParserException;
}
