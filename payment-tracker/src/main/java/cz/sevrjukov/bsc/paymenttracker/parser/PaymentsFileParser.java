package cz.sevrjukov.bsc.paymenttracker.parser;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.sevrjukov.bsc.paymenttracker.model.Payment;

/**
 * Input payments file parser. Parses files which contain
 * payment records in format  &lt;STRING&gt; &lt;INTEGER&gt;.
 * Invalid lines are skipped and a warning is logged.
 * 
 * @author Alexandr Sevrjukov
 *
 */
@Component
public class PaymentsFileParser extends AbstractFileParser<Payment> {

	Logger logger = LoggerFactory.getLogger(PaymentsFileParser.class);
	
	@Autowired
	private PaymentLineParser lineParser;

	
	
	/**
	 * Parses the file. Tries to obtain file lock prior to parsing to avoid
	 * issues with concurrent file modification.
	 */
	@Override
	protected Collection<Payment> parseContent(Path fileToParse) throws ParserException {
		logger.debug(String.format("Parsing file %s", fileToParse.toString()));
		try {
			List<String> lines = readFileContent(fileToParse);
			List<Payment> resultList = new ArrayList<>();
			for (String line : lines) {
				try {
					Payment p = parseLine(line);
					resultList.add(p);
				} catch (ParserException ex) {
					logger.warn(String.format("Skipping invalid line, reason: %s", ex.getMessage()));
				}
				
			}
			return resultList;
		} catch (Exception ex) {
			logger.error("Failed to parse input file", ex);
			throw new ParserException("Unexpected exception during input file parsing");
		}
	}
	
	

	/**
	 * Safely reads file content, locking the file prior to reading
	 * 
	 * @return
	 * @throws IOException
	 */
	private List<String> readFileContent(Path fileToParse) throws IOException {

		// acquire file and lock it for reading
		try (FileChannel fileChannel = FileChannel.open(fileToParse, StandardOpenOption.READ);
				FileLock lock = fileChannel.lock(0, Long.MAX_VALUE, true);) {

			// read all lines from the file
			List<String> lines = Files.readAllLines(fileToParse);

			lock.release();
			return lines;
		}
	}

	/**
	 * Delegates the actual parsing to the LineParser
	 * @param line
	 * @return
	 * @throws ParserException
	 */
	private Payment parseLine(String line) throws ParserException {
		return lineParser.parseLine(line);
	}

}
