package it.unicam.cs.ids.casotto;

import it.unicam.cs.ids.casotto.controller.ActivityManager;
import it.unicam.cs.ids.casotto.controller.CustomerManager;
import it.unicam.cs.ids.casotto.controller.LocationManager;
import it.unicam.cs.ids.casotto.controller.ReservationManager;
import it.unicam.cs.ids.casotto.model.Activity;
import it.unicam.cs.ids.casotto.model.DiscountCode;
import it.unicam.cs.ids.casotto.model.Location;
import it.unicam.cs.ids.casotto.repository.ActivityRepository;
import it.unicam.cs.ids.casotto.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@SpringBootApplication
public class CasottoApplication {
	private ReservationManager reservationManager;
	private LocationManager locationManager;
	private CustomerManager customerManager;
	private ActivityManager activityManager;
	private final Random random = new Random();
    private Scanner scanner = new Scanner(System.in);

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

	/**
	 * This method represents the Use Case: Effettua Prenotazione, where the Client can reserve a Location from the beach.
	 * He also can use a discount code to reduce the total price.
	 * @throws InterruptedException
	 */
	public void ssdEffettuaPrenotazione() throws InterruptedException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		boolean repeat = true;

		while(repeat) {
			System.out.println("Per favore inserisci la data per la prenotazione: dd/MM/yyyy");
			LocalDate reservationDate = LocalDate.parse(scanner.nextLine(), formatter);
			int scelta = 0;
			List<Location> availableLocations = this.reservationManager.getAvailableLocationsOnADate(reservationDate);
			if (!availableLocations.isEmpty()) {
				System.out.println("Ecco le postazioni disponibili per la data " + String.valueOf(reservationDate) + "\n");
				for(scelta = 0; scelta < availableLocations.size(); ++scelta) {
					System.out.println("" + scelta + ": " + String.valueOf(availableLocations.get(scelta)));
				}

				System.out.println("Scegli una delle postazioni disponibili\n");
				scelta = Integer.parseInt(scanner.nextLine());
				System.out.println("Il prezzo della postazione " + scelta + " è: " + this.locationManager.getTotalPrice(((Location)availableLocations.get(scelta)).getId()) + "\n");
				Location choice = availableLocations.get(scelta);
				System.out.println("Vuoi inserire un codice sconto? Y/n\n");
				if (scanner.nextLine().equalsIgnoreCase("y")) {
					System.out.println("Inserire il codice sconto: ");
					DiscountCode discountCode = new DiscountCode(scanner.nextLine());
					if (this.reservationManager.checkDiscountCode(discountCode)) {
						this.reservationManager.applyDiscountCode(this.locationManager.getTotalPrice(choice.getId()), discountCode);
					}
				}
				System.out.println("Inizio procedura di pagamento...\n");
				ssdElaboraPagamento(this.locationManager.getTotalPrice(choice.getId()));
			} else {
				System.out.println("Non sono disponibili postazioni in questa data...");
				System.out.println("Vuoi inserire un'altra data? Y/n");
				if (!scanner.nextLine().equalsIgnoreCase("y")) {
					repeat = false;
				}
			}
		}
	}

	/**
	 * This method represents the Use Case: Modifica Attività, where the Manager can add, delete and update an Activity.
	 */
	public void ssdModificaAttività(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		List<Activity> allActivities = activityManager.getAllActivities();
		System.out.println("Ecco tutte le attività in programma: \n");
		for (int i = 0; i < allActivities.size(); i++) {
			System.out.println(i + ": " + allActivities.get(i).getInfo());
		}
		System.out.println("Scegli cosa vuoi fare: \n");
		System.out.println("1: Aggiungi una nuova attività.\n");
		System.out.println("2: Elimina un'attività.\n");
		System.out.println("3: Modifica un'attività.\n");
		System.out.println("0: Annulla operazione.\n");
		String scelta = scanner.nextLine();

		switch (Integer.parseInt(scelta)){
			case 1: {
				System.out.println("Hai scelto di inserire un'attività.");
				System.out.println("Inserisci le info dell'attività: ");
				String info = scanner.nextLine();
				System.out.println("Inserisci la data di inizio dell'attività: (dd/MM/yyyy)");
				String dataInizio = scanner.nextLine();
				System.out.println("Inserisci la data di fine dell'attività: (dd/MM/yyyy)");
				String dataFine = scanner.nextLine();
				System.out.println("Inserisci il numero di partecipanti massimo dell'attività: (-1 se non ci sono limiti)");
				String numPartecipanti = scanner.nextLine();
				if(Integer.parseInt(numPartecipanti)<0) this.activityManager.saveActivity(new Activity(info,LocalDate.parse(dataInizio,formatter),LocalDate.parse(dataFine,formatter)));
				else this.activityManager.saveActivity(new Activity(info,LocalDate.parse(dataInizio,formatter),LocalDate.parse(dataFine,formatter),Integer.parseInt(numPartecipanti)));
				System.out.println("Attività aggiunta con successo!");
			}
			case 2: {
				System.out.println("Seleziona l'attività da eliminare: \n");
				scelta = scanner.nextLine();
				System.out.println("Hai selezionato l'attività numero "+scelta+"\n");
				activityManager.deleteActivity(allActivities.get(Integer.parseInt(scelta)).getId());
			}
			case 3: {
				System.out.println("Seleziona l'attività da modificare: \n");
				scelta = scanner.nextLine();
				System.out.println("Hai selezionato l'attività numero "+scelta+"\n");
				Activity activity = allActivities.get(Integer.parseInt(scelta));
				do{
					System.out.println("Scegli cosa modificare: ");
					System.out.println("1: Modifica le info.\n");
					System.out.println("2: Modifica la data d'inizio.\n");
					System.out.println("3: Modifica la data di fine.\n");
					System.out.println("4: Modifica il numero massimo di partecipanti.\n");
					System.out.println("0: Per annullare l'operazione.\n");
					scelta = scanner.nextLine();
					switch (Integer.parseInt(scelta)) {
						case 1:{
							System.out.println("Inserisci la nuova info: ");
							String info = scanner.nextLine();
							activity.setInfo(info);
							System.out.println("Aggiornata attività scelta con la nuova info!");
						}
						case 2:{
							System.out.println("Inserisci la nuova data d'inizio: (dd/MM/yyyy)");
							String date = scanner.nextLine();
							activity.setActivityBeginDate(LocalDate.parse(date,formatter));
							System.out.println("Aggiornata attività scelta con la nuova data d'inizio!");
						}
						case 3:{
							System.out.println("Inserisci la nuova data di fine: (dd/MM/yyyy)");
							String date = scanner.nextLine();
							activity.setActivityBeginDate(LocalDate.parse(date,formatter));
							System.out.println("Aggiornata attività scelta con la nuova data di fine!");
						}case 4:{
							System.out.println("Inserisci il nuovo numero massimo di partecipanti: ");
							String date = scanner.nextLine();
							activity.setMaxNumberOfPeople(Integer.parseInt(date));
							System.out.println("Aggiornata attività scelta con il vuovo numero massimo di partecipanti!");
						}
					};
					System.out.println("Premi 1 se vuoi effettuare un'altra modifica a questa attività: ");
					scelta = scanner.nextLine();
				} while(scelta.equals("1"));
				activityManager.updateActivity(activity);
			}
		}
	}
}
