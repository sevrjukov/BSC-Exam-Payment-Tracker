package cz.sevrjukov.bsc.paymenttracker.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.sevrjukov.bsc.paymenttracker.SpringAppTestConfig;
import cz.sevrjukov.bsc.paymenttracker.model.NetPayments;
import cz.sevrjukov.bsc.paymenttracker.model.Payment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringAppTestConfig.class })
public class PaymentServiceTest {

	// autowired to check conainter-managed bean validator
	@Autowired
	private PaymentsService testedService;
	

	@Test
	public void statsCalculationTest() {

		testedService.addNewPayment(new Payment("CZK", 100));
		testedService.addNewPayment(new Payment("AUD", -700));
		testedService.addNewPayment(new Payment("CZK", 200));
		testedService.addNewPayment(new Payment("AUD", 700));
		testedService.addNewPayment(new Payment("CZK", -80));
		testedService.addNewPayment(new Payment("USD", 222));

		Map<String, NetPayments> statResults = testedService.calculateNetAmounts();

		// check calculated results
		NetPayments czkResult = statResults.get("CZK");
		assertNotNull(czkResult);
		assertEquals(220, czkResult.getAmount());

		NetPayments audResult = statResults.get("AUD");
		assertNotNull(audResult);
		assertEquals(0, audResult.getAmount());

		NetPayments usdResult = statResults.get("USD");
		assertNotNull(usdResult);
		assertEquals(222, usdResult.getAmount());

	}
}
