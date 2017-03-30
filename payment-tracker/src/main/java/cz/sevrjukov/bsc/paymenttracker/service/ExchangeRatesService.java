package cz.sevrjukov.bsc.paymenttracker.service;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRatesService {

	private static final String CURRENCY_NAMES_SEPARATOR = "/";

	Logger logger = LoggerFactory.getLogger(ExchangeRatesService.class);

	@Value("exchange.rates")
	private Resource exchangeRatesFile;

	/**
	 * The actual map that holds exchange rates
	 */
	private Map<ExchangeRateKey, Double> exchangeRates = new HashMap<>();

	@PostConstruct
	public void loadExchangeRates() {

		logger.debug("Loading exchange rates");

		// loading from resources folder is dirty,
		// but i guess this is sufficient for a demo
		// application. In real world, this would be
		// an external configuration.

		// trick - use standard Properties to do the parsing
		try {
			Properties props = new Properties();
			props.load(exchangeRatesFile.getInputStream());

			for (Enumeration<?> e = props.propertyNames(); e.hasMoreElements();) {
				String key = (String) e.nextElement();
				logger.debug("Parsing " + key);
				// split currencies
				String[] currencies = key.split(CURRENCY_NAMES_SEPARATOR);
				if (currencies.length != 2) {
					// invalid exchange rate specification, continue
					logger.debug("Invalid currency spec %s" + key);
					continue;
				}
				String currency1 = currencies[0].trim();
				String currency2 = currencies[1].trim();
				Double exchangeRate;
				try {
					// parse the rate
					exchangeRate = Double.parseDouble(props.getProperty(key));
					// create composite key and put to map
					ExchangeRateKey mapKey = new ExchangeRateKey(currency1, currency2);
					exchangeRates.put(mapKey, exchangeRate);
				} catch (NumberFormatException ex) {
					logger.debug("Invalid rate spec %s" + props.getProperty(key));
					// bad specification of exchange format
					continue;
				}
			}
		} catch (Exception ex) {
			logger.error("Failed to parse exchange rates file", ex);
		}

	}

	/**
	 * Returns the exchange rate for the given pair of currencies, if specified,
	 * or null.
	 * 
	 * @param currFrom
	 * @param currTo
	 * @return
	 */
	public Double getExchangeRate(String currFrom, String currTo) {
		// no sychronization here, since I assume exchange rates
		// never change after application startup
		ExchangeRateKey mapKey = new ExchangeRateKey(currFrom, currTo);
		Double result = exchangeRates.get(mapKey);
		if (result != null) {
			return result;
		} else {
			// trick - maybe we have the opposite direction in the map?
			// lets try and see
			ExchangeRateKey reversedKey = new ExchangeRateKey(currTo, currFrom);
			result = exchangeRates.get(reversedKey);
			if (result != null) {
				// calculate the inverse, since it's the opposite direction
				double inverse = (1 / result);
				return inverse;
			} else {
				return null;
			}
		}
	}

	/**
	 * Composite hasmap key to store exchange rates
	 *
	 */
	private class ExchangeRateKey {
		String currency1;
		String currency2;

		public ExchangeRateKey(String currency1, String currency2) {
			this.currency1 = currency1;
			this.currency2 = currency2;
		}

		@Override
		public int hashCode() {
			return Objects.hash(currency1, currency2);
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof ExchangeRateKey)) {
				return false;
			}
			return (this.hashCode() == ((ExchangeRateKey) other).hashCode());
		}
	}

}
