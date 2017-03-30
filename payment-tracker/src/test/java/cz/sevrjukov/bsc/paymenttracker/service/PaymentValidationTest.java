package cz.sevrjukov.bsc.paymenttracker.service;

import static org.junit.Assert.fail;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.sevrjukov.bsc.paymenttracker.SpringAppTestConfig;
import cz.sevrjukov.bsc.paymenttracker.model.Payment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringAppTestConfig.class })
public class PaymentValidationTest {

	
	// autowired to check conainter-managed bean validator
	@Autowired
	private PaymentsService testedService;

	@Test
	public void testPaymentBeanValidation() {

		// OK bean
		Payment okPayment = new Payment();
		okPayment.setCurrency("CZK");
		testedService.addNewPayment(okPayment);

		try {
			testedService.addNewPayment(null);
			fail("Exception expected");
		} catch (ConstraintViolationException ex) {
			// OK
		}

		try {
			// null currency
			Payment nullCurrency = new Payment();
			testedService.addNewPayment(nullCurrency);
			fail("Exception expected");
		} catch (ConstraintViolationException ex) {
			// OK
		}

		try {
			// currency too long
			Payment longCurrency = new Payment();
			longCurrency.setCurrency("ABCD");
			testedService.addNewPayment(longCurrency);
			fail("Exception expected");
		} catch (ConstraintViolationException ex) {
			// OK
		}

		try {
			// currency contains small chars
			Payment wrongCurrency = new Payment();
			wrongCurrency.setCurrency("cZK");
			testedService.addNewPayment(wrongCurrency);
			fail("Exception expected");
		} catch (ConstraintViolationException ex) {
			// OK
		}

		try {
			// enclosing whitespaces
			Payment whitespaces = new Payment();
			whitespaces.setCurrency(" USD ");
			testedService.addNewPayment(whitespaces);
			fail("Exception expected");
		} catch (ConstraintViolationException ex) {
			// OK
		}
	}
	
	
	

}
