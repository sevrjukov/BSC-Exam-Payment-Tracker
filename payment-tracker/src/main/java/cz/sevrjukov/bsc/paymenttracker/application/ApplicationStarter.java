package cz.sevrjukov.bsc.paymenttracker.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;


/**
 * Application starter entry point
 * 
 * @author Alexandr Sevrjukov
 *
 */
@EnableAutoConfiguration
public class ApplicationStarter {

	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationStarter.class, args);
	}
}
