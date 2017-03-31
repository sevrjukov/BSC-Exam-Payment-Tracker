package cz.sevrjukov.bsc.paymenttracker.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * Spring Boot Application entry point.
 * 
 * @author Alexandr Sevrjukov
 *
 */
@SpringBootApplication(scanBasePackages="cz.sevrjukov")
@EnableScheduling
public class ApplicationStarter {

	private static ApplicationContext context;

	/**
	 * Application start entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		context = SpringApplication.run(ApplicationStarter.class, args);
	}

	/**
	 * Stops the application programmatically
	 */
	public static void stop() {
		SpringApplication.exit(context);
	}

	
	/**
	 * Configures bean validation processor.
	 * (Used for bean validation).
	 * @return
	 */
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	/**
	 * Configures bean validator factory.
	 * (Used for bean validation).
	 * @return
	 */
	@Bean
	public LocalValidatorFactoryBean validatorFactory() {
		return new LocalValidatorFactoryBean();
	}
}
