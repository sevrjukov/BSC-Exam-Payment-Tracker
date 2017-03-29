package cz.sevrjukov.bsc.paymenttracker.parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.sevrjukov.bsc.paymenttracker.model.Payment;

@RunWith(SpringJUnit4ClassRunner.class)
public class FileParserTest {

	InputFileParser<Payment> testedParser;

	@Value("good_data.txt")
	private Resource goodDataFile;

	@Value("invalid_amounts.txt")
	private Resource badDataFile;

	@Before
	public void setup() throws IOException {
		testedParser = new PaymentsFileParser();
	}

	@Test
	public void testPathHandling() throws ParserException, IOException {
		try {
			testedParser.parseFile("C://some-non-existing-resource");
			fail("Exception expected");
		} catch (ParserException ex) {
			assertTrue(ex.getMessage().contains("path does not exist"));
			// OK, exception expected
		}

	}

	@Test
	public void testBasicParsing() throws ParserException, IOException {
		
		// file with correct data
		String goodDataFilepath = goodDataFile.getFile().getAbsolutePath();
		testedParser.parseFile(goodDataFilepath);
		
		
		// file with wrong data
		String badDataFilepath = badDataFile.getFile().getAbsolutePath();
		try {
			testedParser.parseFile(badDataFilepath);
			fail("ParserException expected");
		} catch (ParserException ex) {
			// OK, exception expected
		}
	}

}
