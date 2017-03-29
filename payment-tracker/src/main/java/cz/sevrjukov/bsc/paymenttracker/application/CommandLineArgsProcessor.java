package cz.sevrjukov.bsc.paymenttracker.application;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import cz.sevrjukov.bsc.paymenttracker.service.PaymentsService;

@Component
public class CommandLineArgsProcessor {

	@Autowired
	private PaymentsService paymentsService;
	
	private ApplicationArguments args;
	
	@Autowired
	public CommandLineArgsProcessor(ApplicationArguments args) {
		this.args = args;
	}
	
	@PostConstruct
	public void init() {	
		//TODO process command line arguments
	}

}
