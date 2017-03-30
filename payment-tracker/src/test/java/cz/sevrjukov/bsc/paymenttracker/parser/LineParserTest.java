package cz.sevrjukov.bsc.paymenttracker.parser;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.sevrjukov.bsc.paymenttracker.SpringAppTestConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringAppTestConfig.class })
public class LineParserTest {

	@Autowired
	private PaymentLineParser parser;
	
	@Test
	public void test() throws ParserException {

		
		// positive scenario,valid input
		parser.parseLine("CZK -898");
		
		
		// another valid input
		parser.parseLine("CZK +898");
		
		
		try {
			// null input
			parser.parseLine(null);
			fail("ParserException expected");
		} catch (ParserException ex) {
			//OK
		}
		
		try {
			// three tokens
			parser.parseLine("one two three");
			fail("ParserException expected");
		} catch (ParserException ex) {
			//OK
		}
		
		try {
			// invalid number
			parser.parseLine("CZK -89s8");
			fail("ParserException expected");
		} catch (ParserException ex) {
			//OK
		}
		
		
		try {
			// decimals should not be allowed
			parser.parseLine("CZK -898.5");
			fail("ParserException expected");
		} catch (ParserException ex) {
			//OK
		}
	}

}
