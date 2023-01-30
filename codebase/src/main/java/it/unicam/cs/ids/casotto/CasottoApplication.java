package it.unicam.cs.ids.casotto;

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

	private LocationManager locationManager;

	private ReservationManager reservationManager;

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
	public CommandLineRunner demo(ActivityRepository activityRepository, CustomerRepository customerRepository
			, JavaMailSender javaMailSender, ProductRepository productRepository, OrderRepository orderRepository,
			ReservationRepository reservationRepository, LocationRepository locationRepository, BeachRepository beachRepository) {
		return args -> {
			this.locationManager = new LocationManager(locationRepository);
			this.reservationManager = new ReservationManager(reservationRepository, locationManager);
			this.customerManager = new CustomerManager(customerRepository);
			this.activityManager = new ActivityManager(activityRepository, customerRepository);
			this.barController = new BarController(productRepository, orderRepository);
			this.beachManager = new BeachManager(beachRepository);
			this.ssdVisualizzaStoricoOrdini();
			System.out.println("Benvenuto in Casotto!");
			System.out.println("Seleziona il tipo di account con cui accedere: ");
			System.out.println("1) Account Gestore struttura ");
			System.out.println("2) Account Cliente struttura ");
			System.out.println("3) Servizi riservati al personale della struttura ");
			System.out.println("4) Utente non autenticato");
			int sceltaAccount = scanner.nextInt();
			// Login da Gestore
			if(sceltaAccount == 1){
				System.out.println("Benvenuto Gestore!");
				Manager manager = new Manager();
				System.out.println("Selezionare l'azione che si desidera effettuare: ");
				System.out.println("1 - Modificare i fattori di prezzo di una postazione");
				System.out.println("2 - Modificare la struttura della spiaggia");
				System.out.println("3 - Modificare le attrezzature ludico sportive della spiaggia");
				System.out.println("4 - Modificare le attività in programma");
				System.out.println("5 - Inviare una notifica ai clienti");
				int sceltaGestore;
				do {
					System.out.println("Inserire la scelta (da 1 a 6): ");
					sceltaGestore = scanner.nextInt();
				} while (sceltaGestore < 1 || sceltaGestore > 5);
				switch (sceltaGestore) {
					case 1 :
						this.ssdModificaFattoreDiPrezzo();
						break;
					case 2 :
						this.ssdModificaStrutturaSpiaggia();
						break;
					case 3 :
						// TODO implementare metodo del caso d'uso Modifica Attrezzature Ludico Sportive
						break;
					case 4 :
						this.ssdModificaAttività();
						break;
					case 5 :
						this.ssdNotificaClienti(javaMailSender);
						break;
					default : System.out.println("Scelta non corretta");
				}
			// login da Cliente
			} else if (sceltaAccount == 2) {
				System.out.println("Benvenuto Cliente!");
				Customer customer = new Customer();
				System.out.println("Selezionare l'azione che si desidera effettuare: ");
				System.out.println("1 - Prenotare una postazione");
				System.out.println("2 - Prenotare un'attività");
				System.out.println("3 - Effetture un'ordinazione al bar");
				int sceltaCliente;
				do {
					System.out.println("Inserire la scelta (da 1 a 3): ");
					sceltaCliente = scanner.nextInt();
				} while (sceltaCliente < 1 || sceltaCliente > 3);
				switch (sceltaCliente) {
					case 1:
						this.ssdEffettuaPrenotazione(customer);
						break;
					case 2:
						this.ssdPrenotaAttivita();
						break;
					case 3:
						this.ssdAcquistaProdotti(customer.getId());
						break;
					default: System.out.println("Scelta non corretta");
				}
			// servizi riservati al personale struttura
			} else if (sceltaAccount == 3) {
				System.out.println("Selezionare l'azione che si desidera effettuare: ");
				System.out.println("1 - Visualizzare lo storico degli ordini");
				System.out.println("2 - Prendere in carico un'ordine");
				int sceltaPersonale;
				do {
					System.out.println("Inserire la scelta (da 1 a 2): ");
					sceltaPersonale = scanner.nextInt();
				} while (sceltaPersonale < 1 || sceltaPersonale > 2);
				switch (sceltaPersonale) {
					case 1 :
						this.ssdVisualizzaStoricoOrdini();
						break;
					case 2 :
						this.ssdPrendeInCaricoOrdine();
						break;
				}
			// utente non Loggato
			} else if (sceltaAccount == 4) {
				System.out.println("Selezionare l'azione che si desidera effettuare: ");
				System.out.println("1 - Visualizzare il catologo dei servizi");
				int sceltaUtenteGenerico;
				do {
					System.out.println("Inserire la scelta: ");
					sceltaUtenteGenerico = scanner.nextInt();
				} while (sceltaUtenteGenerico != 1);
				if(sceltaUtenteGenerico == 1) {
					this.ssdVisualizzaCatalogoServizi();
				}
			} else {
				System.out.println("Scelta non valida");
				System.exit(1);
			}
		};
	}

	private void ssdPrenotaAttivita() {
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
	public void ssdEffettuaPrenotazione(Customer customer) throws InterruptedException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		boolean repeat = true;
		while(repeat) {
			System.out.println("Per favore inserisci la data iniziale per la prenotazione: dd/MM/yyyy");
			LocalDate reservationStartDate = LocalDate.parse(scanner.nextLine(), formatter);
			System.out.println("Per favore inserisci la data finale per la prenotazione: dd/MM/yyyy");
			LocalDate reservationEndDate = LocalDate.parse(scanner.nextLine(), formatter);
			int scelta;
			List<Location> availableLocations = this.reservationManager.getAvailableLocationsOnADate(reservationStartDate, reservationEndDate);
			if (!availableLocations.isEmpty()) {
				System.out.println("Ecco le postazioni disponibili per il periodo tra " + reservationStartDate +" e " + reservationEndDate + "\n");
				for(scelta = 0; scelta < availableLocations.size(); ++scelta) {
					System.out.println("" + scelta + ": " + availableLocations.get(scelta));
				}
				System.out.println("Scegli una delle postazioni disponibili\n");
				scelta = Integer.parseInt(scanner.nextLine());
				System.out.println("Il prezzo della postazione " + scelta + " è: " + this.locationManager.getTotalPrice((availableLocations.get(scelta)).getId()) + "\n");
				Location choice = availableLocations.get(scelta);
				double totale = this.locationManager.getTotalPrice(choice.getId());
				System.out.println("Vuoi inserire un codice sconto? Y/n\n");
				if (scanner.nextLine().equalsIgnoreCase("y")) {
					System.out.println("Inserire il codice sconto: ");
					DiscountCode discountCode = new DiscountCode(scanner.nextLine());
					if (this.reservationManager.checkDiscountCode(discountCode)) {
						this.reservationManager.applyDiscountCode(totale,discountCode);
					}
				}
				System.out.println("Inizio procedura di pagamento...\n");
				ssdElaboraPagamento(this.locationManager.getTotalPrice(choice.getId()));
				reservationManager.makeReservation(customer,choice,reservationStartDate,reservationEndDate,totale);
				System.out.println("Prenotazione effettuata con successo!");
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
	public void ssdModificaAttività() {
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
						} default: {
							System.out.println("Hai annullato l'operazione...");
							return;
						}
					}
					System.out.println("Premi 1 se vuoi effettuare un'altra modifica a questa attività: ");
					scelta = scanner.nextLine();
				} while(scelta.equals("1"));
				this.activityManager.updateActivity(activity);
			} default:{
				System.out.println("Hai annullato l'operazione...");
			}
		}
	}

	public void ssdAcquistaProdotti(Long idCustomer) throws InterruptedException {
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
		if(location != null) {
			System.out.println("Inizio procedura di pagamento...\n");
			ssdElaboraPagamento(totale);
			this.barController.createNewOrder(productsInOrder);
		} else {
			System.out.println("La postazione associata al QrCode inquadrato non risulta presente nel sistema...");
		}

	}

	public void ssdRiceveOrdine(){

	}

/*	*//**
	 * This method represent th Use Case: Prende in carico ordine, where the worker wants to take charge of an order.
	 *//*
	public void ssdPrendeInCaricoOrdine() {
		System.out.println("Ecco la lista degli ordini ancora da soddisfare: ");
		List<Order> orders = this.barController.getAllOrders();
		int scelta;
		for(scelta = 0; scelta < orders.size(); scelta++){
			System.out.println(scelta + ") " + orders.get(scelta));
		}
		System.out.println("Scegli l'ordine da soddisfare: ");
		int x = scanner.nextInt();
		Order order = orders.get(x);
		barController.setOrderAsCompleted(orders.get(x).getId());
		System.out.println("Ordine segnato come completato correttamente!");
		ssdStampaScontrino(order);
	}
	public void UC_NotificaTerminaliPresenzaOrdini(ProductRepository productRepository, OrderRepository orderRepository){
		List<Order> orders = this.barController.getAllOrders();
		if (!orders.isEmpty()){
			System.out.println("Ci sono ancora ordini da soddisfare!");
		}
	}*/ //?????

	public void ssdNotificaClienti(JavaMailSender javaMailSender) {
		NotificationManager notificationManager = new NotificationManager(javaMailSender);
		System.out.println("Scegli la categoria di notifica che vuoi inviare:");
		System.out.println("1: Inviare il programma di un'attività.\n");
		System.out.println("2: Inviare una promozione.\n");
		switch (Integer.parseInt(scanner.nextLine())) {
			case 1 -> {
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
			}
			case 2 -> {
				System.out.println("Inserisci il testo della promozione");
				String promozione = scanner.nextLine();
				customerManager.getAllCustomers().forEach(customer ->
						notificationManager.sendSimpleMessage(customer.getEmail(), "Nuova Promozione!", promozione));
			}
			default -> System.out.println("Scelta non corretta, riprova");
		}
	}

	public void ssdVisualizzaStoricoOrdini() {
		List<Order> orderList = this.barController.getAllOrders();
		if (orderList.isEmpty()) {
			System.out.println("Nessun ordine presente");
		} else {
			orderList.forEach(System.out::println);
		}
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
	 * This method represents the use case Visualizza Attività in Programmag
	 */
	public void ssdVisualizzaPostazioniStruttura() {
		System.out.println("Le postazioni presenti nella struttura sono: ");
		System.out.println(this.locationManager.getAllLocations());
	}

	/**
	 * This method contains the behavior of the Visualizza Attività in Programma usa case
	 */
	public void ssdVisualizzaAttivitàInProgramma() {
		System.out.println(this.activityManager.getAllActivities());
	}

	/**
	 * This method contains the behavior of the Modifica Fattore di Prezzo use case
	 */
	public void ssdModificaFattoreDiPrezzo() {
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

	public void ssdModificaStrutturaSpiaggia() {
		//asking for the sand type
		System.out.println("La spiaggia ha come tipo di sabbia: " + beach.getSandType());
		System.out.println("Inserire il nuovo tipo di sabbia, o premere invio per non modificarlo");
		String newType = scanner.nextLine();
		//asking for the description
		System.out.println("La descrizione della spiaggia è: " + beach.getDescription());
		System.out.println("Inserire la nuova descrizione, o premere invio per non modificarla");
		String newDes = scanner.nextLine();
		//effects the changes
		if(newType == null) { newType = beach.getSandType(); }
		if(newDes == null) { newDes = beach.getDescription(); }
		this.beachManager.updateModifyBeach(beach.getId(), newType, newDes);
		System.out.println("Le modifiche sono state effettuate con successo");
	}

	//TODO wip
	public void ssdPrendeInCaricoOrdine(){
		//retriving the non completed orders
		List<Order> nonCompletedOrders = this.barController.getNonCompletedOrder();
		//if all orders are already satisfied
		if(nonCompletedOrders.isEmpty()) {
			System.out.println("Non ci sono ordini non completati nel sistema");
			return;
		}
		//there are some orders to be completed
		System.out.println("Gli ordini che risultano ancora non completati sono:");
		int i = 1;
		for(Order o : nonCompletedOrders) {
			System.out.println(i + ") id: " + o.getId() + "che contiene i prodotti " + o.getProducts());
			i++;
		}
		System.out.print("Inserire il numero dell'ordine che si vuole soddisfare");
		Integer choice = scanner.nextInt();
		//valid input
		if(choice < i || choice > 1) {
			Order order = nonCompletedOrders.get(choice - 1);
			System.out.println("Si è scelto l'ordine: ");
			this.barController.createRecipt(order);
			System.out.println("Stampa scontino in corso...");
			this.ssdStampaScontrino(order);
			this.barController.setOrderAsCompleted(order.getId());
			System.out.println("Il sistema ha segnato l'ordine come completato correttamente");
		} else {
			//invalid input
			System.out.println("Immesso un indice di ordine sbagliato");
		}
	}

}