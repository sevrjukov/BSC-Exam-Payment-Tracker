package cz.sevrjukov.bsc.paymenttracker.parser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.sevrjukov.bsc.paymenttracker.SpringAppTestConfig;
import cz.sevrjukov.bsc.paymenttracker.model.Payment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringAppTestConfig.class })
public class FileParserTest {

	@Autowired
	private PaymentsFileParser testedParser;

	@Value("good_data.txt")
	private Resource goodDataFile;

	@Value("invalid_amounts.txt")
	private Resource invalidAmountsFile;
	
	@Value("invalid_currency.txt")
	private Resource invalidCurrencyFile;
	
	@Value("corrupted_lines.txt")
	private Resource corruptedLinesFile;

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
	public void testParsing() throws ParserException, IOException {

		// file with correct data
		String goodDataFilepath = goodDataFile.getFile().getAbsolutePath();
		Collection<Payment> res1 = testedParser.parseFile(goodDataFilepath);
		assertTrue(res1.size() == 5);

		// file with wrong currency - all lines should parse ok,
		// currency format validation is done on service level
		String invalidCurrencyPath = invalidCurrencyFile.getFile().getAbsolutePath();
		Collection<Payment> res2 = testedParser.parseFile(invalidCurrencyPath);
		assertTrue(res2.size() == 4);
		
		// file with wrong data - parsing should continue, but results
		// should not include bad lines
		String invalidAmountsPath = invalidAmountsFile.getFile().getAbsolutePath();
		Collection<Payment> res3 = testedParser.parseFile(invalidAmountsPath);
		assertTrue(res3.size() == 3);
		
		// several corrupted, non-parsable lines which are ignored
		// and 2 good lines
		String corruptedLinesfile = corruptedLinesFile.getFile().getAbsolutePath();
		Collection<Payment> res4 = testedParser.parseFile(corruptedLinesfile);
		assertTrue(res4.size() == 2);
		
	}

}
