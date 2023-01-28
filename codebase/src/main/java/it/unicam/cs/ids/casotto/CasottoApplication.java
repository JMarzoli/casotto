package it.unicam.cs.ids.casotto;

import ch.qos.logback.core.net.SyslogOutputStream;
import it.unicam.cs.ids.casotto.controller.*;
import it.unicam.cs.ids.casotto.model.*;
import it.unicam.cs.ids.casotto.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootApplication
@EnableScheduling
public class CasottoApplication {

	private ReservationManager reservationManager;

	private LocationManager locationManager;

	private CustomerManager customerManager;

	private ActivityManager activityManager;

	private BarController barController;

	private Beach beach;

	private BeachManager beachManager;

	private final Random random = new Random();

    private final Scanner scanner = new Scanner(System.in);

	private int stampeRimaste = 5;

	public static void main(String[] args) {
		SpringApplication.run(CasottoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ActivityRepository activityRepository, CustomerRepository customerRepository, JavaMailSender javaMailSender, ProductRepository productRepository, OrderRepository orderRepository) {
		return args -> {
		};
	}

	private void ssdPrenotaAttività(ActivityRepository activityRepository, CustomerRepository customerRepository) {
		this.activityManager = new ActivityManager(activityRepository, customerRepository);
		List<Activity> activityList = this.activityManager.getAllActivities();
		if (activityList.isEmpty()) {
			System.out.println("Nessuna attività presente, ci dispiace");
		} else {
			while (true) {
				System.out.println("Per favore scegli un'attività alla quale iscriverti");
				activityList.forEach(System.out::println);
				int IDAttività = scanner.nextInt();
				boolean response = this.activityManager.addCustomerToActivity(1, IDAttività);
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
	public void ssdEffettuaPrenotazione(ReservationRepository reservationRepository, LocationRepository locationRepository) throws InterruptedException {
		this.locationManager = new LocationManager(locationRepository);
		this.reservationManager = new ReservationManager(reservationRepository, this.locationManager);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		boolean repeat = true;
		while(repeat) {
			System.out.println("Per favore inserisci la data per la prenotazione: dd/MM/yyyy");
			LocalDate reservationDate = LocalDate.parse(scanner.nextLine(), formatter);
			int scelta;
			List<Location> availableLocations = this.reservationManager.getAvailableLocationsOnADate(reservationDate);
			if (!availableLocations.isEmpty()) {
				System.out.println("Ecco le postazioni disponibili per la data " + reservationDate + "\n");
				for(scelta = 0; scelta < availableLocations.size(); ++scelta) {
					System.out.println("" + scelta + ": " + availableLocations.get(scelta));
				}

				System.out.println("Scegli una delle postazioni disponibili\n");
				scelta = Integer.parseInt(scanner.nextLine());
				System.out.println("Il prezzo della postazione " + scelta + " è: " + this.locationManager.getTotalPrice((availableLocations.get(scelta)).getId()) + "\n");
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
	public void ssdModificaAttività(ActivityRepository activityRepository, CustomerRepository customerRepository) {
		this.activityManager = new ActivityManager(activityRepository, customerRepository);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		List<Activity> allActivities = this.activityManager.getAllActivities();
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
				break;
			}
			case 2: {
				System.out.println("Seleziona l'attività da eliminare: \n");
				scelta = scanner.nextLine();
				System.out.println("Hai selezionato l'attività numero "+scelta+"\n");
				this.activityManager.deleteActivity(allActivities.get(Integer.parseInt(scelta)).getId());
				break;
			}
			case 3: {
				System.out.println("Seleziona l'attività da modificare: \n");
				scelta = scanner.nextLine();
				System.out.println("Hai selezionato l'attività numero "+scelta+"\n");
				Activity activity = allActivities.get(Integer.parseInt(scelta));
				do {
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
							break;
						}
						case 2:{
							System.out.println("Inserisci la nuova data d'inizio: (dd/MM/yyyy)");
							String date = scanner.nextLine();
							activity.setActivityBeginDate(LocalDate.parse(date,formatter));
							System.out.println("Aggiornata attività scelta con la nuova data d'inizio!");
							break;
						}
						case 3:{
							System.out.println("Inserisci la nuova data di fine: (dd/MM/yyyy)");
							String date = scanner.nextLine();
							activity.setActivityBeginDate(LocalDate.parse(date,formatter));
							System.out.println("Aggiornata attività scelta con la nuova data di fine!");
							break;
						}case 4:{
							System.out.println("Inserisci il nuovo numero massimo di partecipanti: ");
							String date = scanner.nextLine();
							activity.setMaxNumberOfPeople(Integer.parseInt(date));
							System.out.println("Aggiornata attività scelta con il nuovo numero massimo di partecipanti!");
						}
					}
					System.out.println("Premi 1 se vuoi effettuare un'altra modifica a questa attività: ");
					scelta = scanner.nextLine();
				} while(scelta.equals("1"));
				this.activityManager.updateActivity(activity);
			}
		}
	}
/*viualizzo
scelgo
ancora?
ecco totale
inquadra pls
inquadro
controlla con postazioni
pago o inseirsci ancora
creo ordine
invio*/
	public void ssdAcquistaProdotti(ProductRepository productRepository, OrderRepository orderRepository) throws InterruptedException {
		this.barController = new BarController(productRepository, orderRepository);
		System.out.println("Benvenuto al servizio Bar della struttura!");
		System.out.println("Seleziona il prodotto che vuoi acquistare: \n");
		List<Product> products = this.barController.getProducts();
		List<Product> productsInOrder = new LinkedList<>();
		int n;
		boolean loop =true;
		for (n = 0; n < products.size(); n++) {
			System.out.println(n +") " + products.get(n).getName());
		}
		while(loop){
			System.out.println("\n Seleziona il prodotto che vuoi acquistare: ");
			int scelta = scanner.nextInt();
			productsInOrder.add(products.get(scelta));
			System.out.println(products.get(scelta).getName() + " è stato aggiunto al carrello.");
			System.out.println("Vuoi aggiungere altri prodotti? (Y/n)");
			if(!scanner.nextLine().equalsIgnoreCase("y")){
				loop=false;
			}
		}
		System.out.println("Ecco il tuo carrello: ");
		productsInOrder.forEach(System.out::println);
		double totale = productsInOrder.stream().mapToDouble(Product::getPrice).sum();
		System.out.println("\nQuesto il tuo totale: " +totale);
		System.out.println("Inquadra il QrCode associato alla tua postazione: ");
		String qrcode = scanner.nextLine();
		Location location = locationManager.findByQrCode(qrcode);
		//Simulo la presenza di una postazione associata a quel QrCode. Altrimenti sarebbe location!=null.
		if(location == null) {
			System.out.println("Inizio procedura di pagamento...\n");
			ssdElaboraPagamento(totale);
			this.barController.createNewOrder(productsInOrder);
		} else {
			System.out.println("La postazione associata al QrCode inquadrato non risulta presente nel sistema...");
		}

	}

	public void ssdRiceveOrdine(){

	}

	/**
	 * This method represent th Use Case: Prende in carico ordine, where the worker wants to take charge of an order.
	 */
	public void ssdPrendeInCaricoOrdine(ProductRepository productRepository, OrderRepository orderRepository){
		this.barController = new BarController(productRepository, orderRepository);
		System.out.println("Ecco la lista degli ordini ancora da soddisfare: ");
		List<Order> orders = this.barController.getAllOrders();
		int scelta;
		for(scelta = 0; scelta < orders.size(); scelta++){
			System.out.println(scelta + ") " + orders.get(scelta));
		}
		System.out.println("Scegli l'ordine da soddisfare: ");
		int x = scanner.nextInt();
		barController.setOrderAsCompleted(orders.get(x).getId());
		System.out.println("Ordine segnato come completato correttamente!");
		//Todo: Manca il metodo stampa scontrino.
	}
	public void UC_NotificaTerminaliPresenzaOrdini(ProductRepository productRepository, OrderRepository orderRepository){
		this.barController = new BarController(productRepository, orderRepository);
		List<Order> orders = this.barController.getAllOrders();
		if (!orders.isEmpty()){
			System.out.println("Ci sono ancora ordini da soddisfare!");
		}
	}

	public void ssdNotificaClienti(CustomerRepository customerRepository, ActivityRepository activityRepository, JavaMailSender javaMailSender) {
		NotificationManager notificationManager = new NotificationManager(javaMailSender);
		ActivityManager activityManager = new ActivityManager(activityRepository, customerRepository);
		CustomerManager customerManager = new CustomerManager(customerRepository);
		System.out.println("Scegli la categoria di notifica che vuoi inviare:");
		System.out.println("1: Inviare il programma di un'attività.\n");
		System.out.println("2: Inviare una promozione.\n");
		switch (Integer.parseInt(scanner.nextLine())) {
			case 1:
				List<Activity> activityList = activityManager.getAllActivities();
				System.out.println("Scegli l'attività per inviare il programma, usando il numero");
				activityList.forEach(activity -> System.out.println(activity.getId() + ": " + activity.getInfo() + "\n"));
				try {
					int scelta = scanner.nextInt();
					Activity activity = activityList.get(--scelta);
					activity.getCustomersInThisActivity().forEach(customer ->
							notificationManager.sendSimpleMessage(customer.getEmail(), "Programma dell'attività!", activity.getInfo()));
				} catch (Exception ex) {
					System.out.println("Attività non presente, riprova");
				}
				break;
			case 2:
				System.out.println("Inserisci il testo della promozione");
				String promozione = scanner.nextLine();
				customerManager.getAllCustomers().forEach(customer ->
						notificationManager.sendSimpleMessage(customer.getEmail(), "Nuova Promozione!", promozione));
				break;
			default:
				System.out.println("Scelta non corretta, riprova");
		}
	}

	public void ssdPersonaleStrutturaVisualizzaStoricoOrdini(ProductRepository productRepository, OrderRepository orderRepository) {
		BarController barController = new BarController(productRepository, orderRepository);
		barController.getAllOrders().forEach(System.out::println);
	}

	public void ssdStampaScontrino(Order order) {
		if (stampeRimaste <= 0) {
			System.out.println("Per favore cambia lo scontrino, è finito");
		} else {
			System.out.println(new Receipt(LocalDateTime.now(), order, order.getPrice()));
			stampeRimaste--;
		}
	}


	/**
	 * This method represents the use case Visualizza Catalogo Servizi
	 */
	public void ssdVisualizzaCatalogoServizi(){
		System.out.println("SERVIZI DISPONIBILI IN QUESTO STABILIMENTO" + List.of("SERVIZIO BAR", "SERVIZIO BALNEARE CON PRENOTAZIONE DI OMBRELLONI E LETTINI", "ATTIVITA' ALL'APERTO"));
	}

	/**
	 * This method represents the use case Visualizza Attività in Programma
	 * @return
	 */
	public void ssdVisualizzaPostazioniStruttura(LocationRepository locationRepository) {
		this.locationManager = new LocationManager(locationRepository);
		System.out.println(this.locationManager.getAllLocations());
	}

	/**
	 * This method contains the behavior of the Visualizza Attività in Programma usa case
	 */
	public void ssdVisualizzaAttivitàInProgramma(ActivityRepository activityRepository, CustomerRepository customerRepository) {
		this.activityManager = new ActivityManager(activityRepository, customerRepository);
		System.out.println(activityManager.getAllActivities());
	}

	/**
	 * This method contains the behavior of the Modifica Fattore di Prezzo use case
	 */
	public void sssModificaFattoreDiPrezzo(LocationRepository locationRepository) {
		this.locationManager = new LocationManager(locationRepository);
		System.out.println("Selezionare il numero della postazione di cui si vuole modificare il fattore di prezzo");
		int i = 1;
		//asking for the location that should be modified
		for (Location l: this.locationManager.getAllLocations()) {
			System.out.println("Postazione numero: " + i++ + l.getId());
		}
		Scanner in = new Scanner(System.in);
		int locNumber = in.nextInt() - 1;
		Location locToModify = this.locationManager.getAllLocations().get(locNumber);
		//eliciting for the new factors
		System.out.println("The actual price factor of the chairs in this location is " + locToModify.getBeachChairs().get(0).getPrice());
		System.out.println("Insert the new factor for chairs: ");
		double newChairFactor = in.nextDouble();
		System.out.println("The actual price factor of the umbrellas in this location is " + locToModify.getUmbrellas().get(0).getPrice());
		System.out.println("Insert the new factor for umbrellas: ");
		double newUmbrellasFactor = in.nextDouble();
		//effecting the changes
		for (BeachChair c : locToModify.getBeachChairs()) {
			c.setPrice(newChairFactor);
		}
		for(Umbrella u : locToModify.getUmbrellas()){
			u.setPrice(newUmbrellasFactor);
		}
		System.out.println("Modifica avvenuta con successo");
	}

	public void ssdModificaStrutturaSpiaggia(BeachRepository beachRepository) {
		this.beachManager = new BeachManager(beachRepository);
		//asking for the sand type
		System.out.println("La spiaggia ha come tipo di sabbia: " + beach.getSandType());
		System.out.println("Inserire il nuovo tipo di sabbia, o premere invio per non modificarlo");
		String newType = scanner.nextLine();
		//asking for the description
		System.out.println("La descrizione della spiaggia è: " + beach.getDescription());
		System.out.println("Inserire la nuova descrizione, o premere invio per non modificarla");
		String newDes = scanner.nextLine();
		//effects the changes
		if(newType == null) { newType = beach.getSandType();}
		if(newDes == null) {newDes = beach.getDescription();}
		this.beachManager.updateModifyBeach(beach.getId(), newType, newDes);
		System.out.println("Le modifiche sono state effettuate con successo");
	}

	//TODO wip
	public void ssdPrendeInCaricoOrdine(){}
}