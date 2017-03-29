package cz.sevrjukov.bsc.paymenttracker.validator;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

import cz.sevrjukov.bsc.paymenttracker.model.Payment;


/**
 * Tests Payment bean validator
 * 
 * @author Alexandr Sevrjukov
 *
 */
public class PaymentValidatorTest {
	
	PaymentValidator testedValidator;

	@Before
	public void setUp() {
		testedValidator = new PaymentValidator();
	}

	@Test
	public void testSupports() {
		assertTrue(testedValidator.supports(Payment.class));
	}

	@Test
	public void currencyCodeValidationTests() {
		
		// OK bean
		Payment okPayment = new Payment();
		okPayment.setCurrency("CZK");
		
		BindException errors = new BindException(okPayment, "currency");
		ValidationUtils.invokeValidator(testedValidator, okPayment, errors);
		assertTrue(!errors.hasErrors());
		
		// null entity
		ValidationUtils.invokeValidator(testedValidator, null, errors);
		assertTrue(errors.hasErrors());
		
		// null currency
		Payment nullCurrency = new Payment();
		nullCurrency.setCurrency(null);
		errors = new BindException(okPayment, "currency");
		ValidationUtils.invokeValidator(testedValidator, nullCurrency, errors);
		assertTrue(errors.hasErrors());
		
		// currency too long
		Payment longCurrency = new Payment();
		longCurrency.setCurrency("ABCD");
		errors = new BindException(okPayment, "currency");
		ValidationUtils.invokeValidator(testedValidator, longCurrency, errors);
		assertTrue(errors.hasErrors());
		
		// currency contains small chars
		Payment wrongCurrency = new Payment();
		wrongCurrency.setCurrency("cZK");
		errors = new BindException(okPayment, "currency");
		ValidationUtils.invokeValidator(testedValidator, wrongCurrency, errors);
		assertTrue(errors.hasErrors());
		
		// entrailing whitespaces
		Payment whitespaces = new Payment();
		whitespaces.setCurrency(" USD ");
		errors = new BindException(okPayment, "currency");
		ValidationUtils.invokeValidator(testedValidator, whitespaces, errors);
		assertTrue(errors.hasErrors());
		
	}

}
