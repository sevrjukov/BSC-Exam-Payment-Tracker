package cz.sevrjukov.bsc.paymenttracker.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import cz.sevrjukov.bsc.paymenttracker.model.Payment;

@Service
@Validated
public class PaymentsService {

	
	
	
	public void addNewPayment(@Valid @NotNull Payment payment) {

	}

}
