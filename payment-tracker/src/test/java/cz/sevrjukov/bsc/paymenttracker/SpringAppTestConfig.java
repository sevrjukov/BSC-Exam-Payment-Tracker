package cz.sevrjukov.bsc.paymenttracker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@ComponentScan(basePackages = { "cz.sevrjukov.bsc.paymenttracker.model", "cz.sevrjukov.bsc.paymenttracker.parser", "cz.sevrjukov.bsc.paymenttracker.service" })
public class SpringAppTestConfig {

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	@Bean
	public LocalValidatorFactoryBean validatorFactory() {
		return new LocalValidatorFactoryBean();
	}

}
