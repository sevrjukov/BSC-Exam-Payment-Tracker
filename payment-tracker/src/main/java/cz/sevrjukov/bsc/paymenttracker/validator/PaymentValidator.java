package cz.sevrjukov.bsc.paymenttracker.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.sevrjukov.bsc.paymenttracker.model.Payment;

public class PaymentValidator implements Validator {

	/**
	 * Regular expression used to validate currency code. Rules: Exactly 3
	 * capital characters (A-Z)
	 */
	private Pattern currencyRegexp = Pattern.compile("^[A-Z]{3}$");

	@Override
	public boolean supports(Class<?> clazz) {
		return Payment.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		if (target == null) {
			errors.reject("Validated object should not be null");
			return;
		}
		
		Payment payment = (Payment) target;
		if (StringUtils.isEmpty(payment.getCurrency())) {
			errors.reject("currency", "Currency is empty");
			return;
		}
		
		Matcher m = currencyRegexp.matcher(payment.getCurrency());

		if (!m.matches()) {
			errors.reject("currency", String.format("Currency [%s] wrongly specified, expected 3 capital characters",
					payment.getCurrency()));
		}
	}

}
