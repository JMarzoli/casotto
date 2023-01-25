package it.unicam.cs.ids.casotto;

import it.unicam.cs.ids.casotto.controller.ActivityManager;
import it.unicam.cs.ids.casotto.model.Activity;
import it.unicam.cs.ids.casotto.repository.ActivityRepository;
import it.unicam.cs.ids.casotto.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@SpringBootApplication
public class CasottoApplication {

	private final Random random = new Random();

	public static void main(String[] args) {
		SpringApplication.run(CasottoApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner demo(ActivityRepository activityRepository, CustomerRepository customerRepository) {
		return args -> ;
	}*/

	private void ssdPrenotaAttività(ActivityRepository activityRepository, CustomerRepository customerRepository) {
		ActivityManager activityManager = new ActivityManager(activityRepository, customerRepository);
		List<Activity> activityList = activityManager.getAllActivities();
		if (activityList.isEmpty()) {
			System.out.println("Nessuna attività presente, ci dispiace");
		} else {
			while (true) {
				System.out.println("Per favore scegli un'attività alla quale iscriverti");
				activityList.forEach(System.out::println);
				Scanner scanner = new Scanner(System.in);
				int IDAttività = scanner.nextInt();
				boolean response = activityManager.addCustomerToActivity(1, IDAttività);
				if (response) {
					System.out.println("Prenotazione effettuata con successo");
					break;
				} else {
					System.out.println("Qualcosa è andato storto, magari l'attività non esiste o non ci sono più posti disponibili");
				}
			}
		}
	}

	private void ssdElaboraPagamento(double prezzoTotale) throws InterruptedException {
		while (true) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Per favore, inserisci il numero della carta");
			scanner.nextLine();
			System.out.println("Per favore, inserisci la data di scadenza");
			scanner.nextLine();
			System.out.println("Per favore, inserisci il CVV");
			scanner.nextInt();
			double disponibilità = this.random.nextDouble(prezzoTotale * 1.75);
			Thread.sleep(2000);
			if (disponibilità < prezzoTotale) {
				System.out.println("Fondi insufficienti, cambia carta");
			} else {
				System.out.println("Pagamento effettuato");
				break;
			}
		}
	}
}
