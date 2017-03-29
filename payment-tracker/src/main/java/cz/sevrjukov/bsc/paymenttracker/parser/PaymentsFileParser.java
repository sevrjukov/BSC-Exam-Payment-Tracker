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

import cz.sevrjukov.bsc.paymenttracker.model.Payment;

public class PaymentsFileParser extends InputFileParser<Payment> {

	private static final String TOKENS_SEPARATOR = " ";

	
	
	@Override
	protected Collection<Payment> parseContent(Path fileToParse) throws ParserException {

		try {
			List<String> lines = readFileContent(fileToParse);
			List<Payment> resultList = new ArrayList<>();
			for (String line : lines) {
				Payment p = parseLine(line);
				resultList.add(p);
			}
			return resultList;
		} catch (ParserException ex) {
			// rethrow
			throw ex;
		} catch (Exception ex) {
			//TODO
			return null;
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

	private Payment parseLine(String line) throws ParserException {

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
