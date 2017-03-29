package cz.sevrjukov.bsc.paymenttracker.application;

import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class ConsoleListener {

	private static final String QUIT_COMMAND = "quit";

	@PostConstruct
	public void init() {
		Runnable r = new ConsoleListeningThread();
		new Thread(r).start();
	}

	private void processUserInput(String input) {
		System.out.println("Your input was " + input);
	}

	private class ConsoleListeningThread implements Runnable {
		@Override
		public void run() {
			String s = "";
			try (Scanner sc = new Scanner(System.in)) {
				while (!s.equalsIgnoreCase(QUIT_COMMAND)) {
					s = sc.next();
					processUserInput(s);
				}
			}
			// gracefully shut down the application
			ApplicationStarter.stop();
		}
	}

}
