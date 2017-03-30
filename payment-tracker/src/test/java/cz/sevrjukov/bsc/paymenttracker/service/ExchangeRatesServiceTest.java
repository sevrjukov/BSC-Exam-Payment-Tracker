package cz.sevrjukov.bsc.paymenttracker.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.sevrjukov.bsc.paymenttracker.SpringAppTestConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringAppTestConfig.class })
public class ExchangeRatesServiceTest {

	@Autowired
	private ExchangeRatesService testedService;
	
	@Test
	public void testConversionRates() {
		// specified conversion rate
		assertEquals(25.26017, testedService.getExchangeRate("USD", "CZK").doubleValue(),0);
		
		// inversed conversion rate
		assertEquals((1/25.26017), testedService.getExchangeRate("CZK", "USD").doubleValue(), 0.001);
		
		// non existing
		assertNull(testedService.getExchangeRate("ABC", "EFG"));
	}

}
