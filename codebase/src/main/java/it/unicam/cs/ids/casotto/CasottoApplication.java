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
	private UmbrellaManager umbrellaManager;
	private BeachChairManager beachChairManager;
	private EquipmentManager equipmentManager;

	private Beach beach;

	private BeachManager beachManager;

	private final Random random = new Random();

    private final Scanner scanner = new Scanner(System.in);

	private int stampeRimaste = 5;

	public CasottoApplication() {
		this.beach = new Beach();
	}

	public static void main(String[] args) {
		SpringApplication.run(CasottoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ActivityRepository activityRepository, CustomerRepository customerRepository
			, JavaMailSender javaMailSender, ProductRepository productRepository, OrderRepository orderRepository,
			ReservationRepository reservationRepository, LocationRepository locationRepository, BeachRepository beachRepository,
								  UmbrellaRepository umbrellaRepository, BeachChairRepository beachChairRepository, EquipmentRepository equipmentRepository) {
		return args -> {
			this.locationManager = new LocationManager(locationRepository);
			this.reservationManager = new ReservationManager(reservationRepository, locationManager,customerManager);
			this.customerManager = new CustomerManager(customerRepository);
			this.activityManager = new ActivityManager(activityRepository, customerRepository);
			this.barController = new BarController(productRepository, orderRepository);
			this.beachManager = new BeachManager(beachRepository);
			this.umbrellaManager = new UmbrellaManager(umbrellaRepository);
			this.beachChairManager = new BeachChairManager(beachChairRepository);
			this.equipmentManager = new EquipmentManager(equipmentRepository);
			databasePopulation();
			this.ssdVisualizzaStoricoOrdini(); //DIMENTICATO?
			boolean loopMain = true;
			while(loopMain) {
				System.out.println("Benvenuto in Casotto!");
				System.out.println("Seleziona il tipo di account con cui accedere: ");
				System.out.println("1) Account Gestore struttura ");
				System.out.println("2) Account Cliente struttura ");
				System.out.println("3) Servizi riservati al personale della struttura ");
				System.out.println("4) Utente non autenticato");
				System.out.println("Altro per terminare l'applicazione.");
				boolean loopUseCase = true;
				int sceltaAccount = scanner.nextInt();
				// Login da Gestore
				if (sceltaAccount == 1) {
					while(loopUseCase) {
						loopUseCase = true;
						System.out.println("Benvenuto Gestore!");
						Manager manager = new Manager();
						System.out.println("Selezionare l'azione che si desidera effettuare: ");
						System.out.println("1 - Modificare i fattori di prezzo di una postazione");
						System.out.println("2 - Modificare la struttura della spiaggia");
						System.out.println("3 - Modificare le attrezzature ludico sportive della spiaggia");
						System.out.println("4 - Modificare le attività in programma");
						System.out.println("5 - Inviare una notifica ai clienti");
						System.out.println("0 - Per tornare indietro.");
						int sceltaGestore;
						do {
							System.out.println("Inserire la scelta (da 1 a 6): ");
							sceltaGestore = scanner.nextInt();
						} while (sceltaGestore < 0 || sceltaGestore > 5);
						switch (sceltaGestore) {
							case 1:
								this.ssdModificaFattoreDiPrezzo();
								break;
							case 2:
								this.ssdModificaStrutturaSpiaggia();
								break;
							case 3:
								ssdModificaAttrezzaturaLudicoSportiva();
								break;
							case 4:
								this.ssdModificaAttivita();
								break;
							case 5:
								this.ssdNotificaClienti(javaMailSender);
								break;
							case 0:
								loopUseCase  =false;
								break;
							default:
								System.out.println("Scelta non corretta");
						}
					}
					// login da Cliente
				} else if (sceltaAccount == 2) {
					while(loopUseCase) {
						loopUseCase = true;
						System.out.println("Benvenuto Cliente!");
						Customer customer = customerManager.getAllCustomers().get(0);
						System.out.println("Selezionare l'azione che si desidera effettuare: ");
						System.out.println("1 - Prenotare una postazione");
						System.out.println("2 - Prenotare un'attività");
						System.out.println("3 - Effetture un'ordinazione al bar");
						System.out.println("0 - Per tornare indietro.");
						int sceltaCliente;
						do {
							System.out.println("Inserire la scelta (da 1 a 3): ");
							sceltaCliente = scanner.nextInt();
						} while (sceltaCliente < 0 || sceltaCliente > 3);
						switch (sceltaCliente) {
							case 1:
								this.ssdEffettuaPrenotazione(customer);
								break;
							case 2:
								this.ssdPrenotaAttivita();
								break;
							case 3:
								this.ssdAcquistaProdotti();
								break;
							case 0:
								loopUseCase=false;
								break;
							default:
								System.out.println("Scelta non corretta");
						}
					}
					// servizi riservati al personale struttura
				} else if (sceltaAccount == 3) {
					while(loopUseCase) {
						loopUseCase = true;
						System.out.println("Selezionare l'azione che si desidera effettuare: ");
						System.out.println("1 - Visualizzare lo storico degli ordini");
						System.out.println("2 - Prendere in carico un'ordine");
						System.out.println("0 - Per tornare indietro.");
						int sceltaPersonale;
						do {
							System.out.println("Inserire la scelta (da 1 a 2): ");
							sceltaPersonale = scanner.nextInt();
						} while (sceltaPersonale < 0 || sceltaPersonale > 2);
						switch (sceltaPersonale) {
							case 1:
								this.ssdVisualizzaStoricoOrdini();
								break;
							case 2:
								this.ssdPrendeInCaricoOrdine();
								break;
							case 0:
								loopUseCase = false;
								break;
						}
					}
					// utente non Loggato
				} else if (sceltaAccount == 4) {
					while(loopUseCase) {
						loopUseCase = true;
						System.out.println("Selezionare l'azione che si desidera effettuare: ");
						System.out.println("1 - Visualizzare il catologo dei servizi");
						System.out.println("0 - Per tornare indietro.");
						int sceltaUtenteGenerico;
						do {
							System.out.println("Inserire la scelta: ");
							sceltaUtenteGenerico = scanner.nextInt();
						} while (sceltaUtenteGenerico<0 || sceltaUtenteGenerico>1);
						if (sceltaUtenteGenerico == 1) {
							this.ssdVisualizzaCatalogoServizi();
						} else {
							loopUseCase = false;
						}
					}
				} else {
					System.out.println("Arrivederci.");
					loopMain  =false;
					System.exit(1);
				}
			}
		};
	}

	public void databasePopulation(){
		beachManager.saveBeach(new Beach("Sabbia fine", "Lido in via IdS"));
		this.beach = beachManager.findAllBeaches().get(0);
		customerManager.saveCustomer(new Customer("LEONARDO", "MAZZOLI", "LEOMAZ@HOTMAIL.IT"));
		activityManager.saveActivity(new Activity("Info attività 1",LocalDate.of(2023,1,1),LocalDate.of(2023,1,3),-1));
		activityManager.saveActivity(new Activity("Info attività 2",LocalDate.of(2023,1,5),LocalDate.of(2023,1,6),50));
		activityManager.saveActivity(new Activity("Info attività 3",LocalDate.of(2023, 1,9),LocalDate.of(2023,1,9),-1));
		umbrellaManager.saveUmbrella(10,"Acciaio",15);
		umbrellaManager.saveUmbrella(10,"Acciaio",15);
		umbrellaManager.saveUmbrella(10,"Acciaio",15);
		umbrellaManager.saveUmbrella(10,"Acciaio",15);
		beachChairManager.saveBeachChair("tessuto", 2,1.5,10);
		beachChairManager.saveBeachChair("tessuto", 2,1.5,10);
		beachChairManager.saveBeachChair("tessuto", 2,1.5,10);
		beachChairManager.saveBeachChair("tessuto", 2,1.5,10);
		List<Umbrella> umbrellas = umbrellaManager.getAllUmbrellas();
		List<BeachChair> beachChairs = beachChairManager.getAllBeachChairs();
		locationManager.saveLocation("Location 1",List.of(umbrellas.get(0)),List.of(beachChairs.get(0)),"qr1");
		locationManager.saveLocation("Location 2",List.of(umbrellas.get(1)),List.of(beachChairs.get(1)),"qr2");
		locationManager.saveLocation("Location 3",List.of(umbrellas.get(2)),List.of(beachChairs.get(2)),"qr3");
		locationManager.saveLocation("Location 4",List.of(umbrellas.get(3)),List.of(beachChairs.get(3)),"qr4");
		barController.createProduct("SUCCO","SUCCO",2.50,10);
		barController.createProduct("PANINO","PANINO",3.50,5);
		barController.createProduct("PIZZA","PIZZA",6.50,5);
		barController.createProduct("COCA COLA","COCA COLA",2.50,15);
		barController.createNewOrder(barController.getProducts());
		equipmentManager.saveEquipment(new Equipment("Attrezzatura 1", "Ludico"));
		equipmentManager.saveEquipment(new Equipment("Attrezzatura 2", "Sportiva"));
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
	 */
	public void ssdEffettuaPrenotazione(Customer customer) throws InterruptedException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		boolean repeat = true;
		while(repeat) {
			System.out.println("Ecco tutte le postazioni disponibili nella struttura: ");
			ssdVisualizzaPostazioniStruttura();
			boolean correct = false;
			LocalDate reservationStartDate = null;
			LocalDate reservationEndDate = null;
			while (!correct) {
				System.out.println("\nPer favore inserisci la data iniziale per la prenotazione: dd/MM/yyyy");
				String date = scanner.next();
				reservationStartDate = LocalDate.parse(date, formatter);
				System.out.println("Per favore inserisci la data finale per la prenotazione: dd/MM/yyyy");
				date = scanner.next();
				reservationEndDate = LocalDate.parse(date, formatter);
				if (checkDateIfCorrect(reservationStartDate, reservationEndDate)) {
					correct = true;
				} else {
					System.out.println("Date non valide, riprova...");
				}
			}
			int scelta;
			List<Location> availableLocations = this.reservationManager.getAvailableLocationsOnADate(reservationStartDate, reservationEndDate);
			if (!availableLocations.isEmpty()) {
				System.out.println("Ecco le postazioni disponibili per il periodo tra " + reservationStartDate + " e " + reservationEndDate + "\n");
				availableLocations.forEach(System.out::println);
				System.out.println("Scegli una delle postazioni disponibili:");
				scelta = Integer.parseInt(scanner.next()) - 1;
				while (scelta < 0 || scelta >= availableLocations.size()) {
					System.out.println("Numero della postazione non valido... \n Inseriscine un'altra: ");
					scelta = Integer.parseInt(scanner.next()) - 1;
				}
				System.out.println("Postazione selezionata: " + availableLocations.get(scelta));
				System.out.println("Il prezzo della postazione scelta è: " + this.locationManager.getTotalPrice((availableLocations.get(scelta)).getId()));
				Location choice = availableLocations.get(scelta);
				double totale = this.locationManager.getTotalPrice(choice.getId());
				System.out.println("Vuoi inserire un codice sconto? (Y/no)");
				String code = scanner.next();
				if (code.equalsIgnoreCase("y")) {
					System.out.println("Inserire il codice sconto: ");
					code = scanner.next();
					DiscountCode discountCode = new DiscountCode(code);
					if (this.reservationManager.checkDiscountCode(discountCode)) {
						totale = this.reservationManager.applyDiscountCode(totale, discountCode);
						System.out.println("Ecco il nuovo prezzo della postazione: " + totale);
					}
				}
				System.out.println("Inizio procedura di pagamento...");
				ssdElaboraPagamento(this.locationManager.getTotalPrice(choice.getId()));

				reservationManager.makeReservation(customer, choice.getId(), reservationStartDate, reservationEndDate, totale);
				System.out.println("Prenotazione effettuata con successo!");
				repeat = false;
			} else {
				System.out.println("Non sono disponibili postazioni in questa data...");
				System.out.println("Vuoi inserire un'altra data? Y/n");
				String s = scanner.next();
				if (!s.equalsIgnoreCase("y")) {
					repeat = false;
				}
			}
		}
	}

	private boolean checkDateIfCorrect(LocalDate reservationStartDate, LocalDate reservationEndDate) {
		return reservationStartDate.isBefore(reservationEndDate);
	}

	/**
	 * This method represents the Use Case: Modifica Attività, where the Manager can add, delete and update an Activity.
	 */
	public void ssdModificaAttivita() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.println("Ecco tutte le attività in programma: \n");
		ssdVisualizzaAttivitaInProgramma();
		System.out.println("Scegli cosa vuoi fare: \n");
		System.out.println("1: Aggiungi una nuova attività.\n");
		System.out.println("2: Elimina un'attività.\n");
		System.out.println("3: Modifica un'attività.\n");
		System.out.println("0: Annulla operazione.\n");
		int scelta = scanner.nextInt();
		switch (scelta){
			case 1: {
				System.out.println("Hai scelto di inserire un'attività.");
				System.out.println("Inserisci le info dell'attività: ");
				String info = scanner.next();
				System.out.println("Inserisci la data di inizio dell'attività: (dd/MM/yyyy)");
				String dataInizio = scanner.next();
				System.out.println("Inserisci la data di fine dell'attività: (dd/MM/yyyy)");
				String dataFine = scanner.next();
				System.out.println("Inserisci il numero di partecipanti massimo dell'attività: (-1 se non ci sono limiti)");
				String numPartecipanti = scanner.next();
				if(Integer.parseInt(numPartecipanti)<0) this.activityManager.saveActivity(new Activity(info,LocalDate.parse(dataInizio,formatter),LocalDate.parse(dataFine,formatter)));
				else this.activityManager.saveActivity(new Activity(info,LocalDate.parse(dataInizio,formatter),LocalDate.parse(dataFine,formatter),Integer.parseInt(numPartecipanti)));
				System.out.println("Attività aggiunta con successo!");
				break;
			}
			case 2: {
				System.out.println("Seleziona l'attività da eliminare: ");
				ssdVisualizzaAttivitaInProgramma();
				scelta = scanner.nextInt()-1;
				System.out.println("scelta: " + scelta + " su "+ activityManager.getAllActivities().size());
				while(scelta<0 || scelta>=activityManager.getAllActivities().size()){
					System.out.println("Numero dell'attività non valido... \n Inseriscine un'altra: ");
					scelta = scanner.nextInt()-1;
				}
				this.activityManager.deleteActivity(this.activityManager.getAllActivities().get(scelta).getId());
				System.out.println("Hai eliminato con successo l'attività selezionata!");
				System.out.println(activityManager.getAllActivities());
				break;
			}
			case 3: {
				ssdVisualizzaAttivitaInProgramma();
				System.out.println("Seleziona l'attività da modificare: \n");
				scelta = scanner.nextInt()-1;
				while(scelta<0 || scelta>=activityManager.getAllActivities().size()){
					System.out.println("Numero dell'attività non valido... \n Inseriscine un'altra: ");
					scelta = scanner.nextInt()-1;
				}
				String again;
				Activity activity = this.activityManager.getAllActivities().get(scelta);
				do {
					System.out.println("Scegli cosa modificare: ");
					System.out.println("1: Modifica le info.\n");
					System.out.println("2: Modifica la data d'inizio.\n");
					System.out.println("3: Modifica la data di fine.\n");
					System.out.println("4: Modifica il numero massimo di partecipanti.\n");
					System.out.println("0: Per annullare l'operazione.\n");
					scelta = scanner.nextInt();
					switch (scelta) {
						case 1:{
							System.out.println("Inserisci la nuova info: ");
							String info = scanner.next();
							activity.setInfo(info);
							System.out.println("Aggiornata attività scelta con la nuova info!");
							break;
						}
						case 2:{
							System.out.println("Inserisci la nuova data d'inizio: (dd/MM/yyyy)");
							String date = scanner.next();
							activity.setActivityBeginDate(LocalDate.parse(date,formatter));
							System.out.println("Aggiornata attività scelta con la nuova data d'inizio!");
							break;
						}
						case 3:{
							System.out.println("Inserisci la nuova data di fine: (dd/MM/yyyy)");
							String date = scanner.next();
							activity.setActivityBeginDate(LocalDate.parse(date,formatter));
							System.out.println("Aggiornata attività scelta con la nuova data di fine!");
							break;
						}case 4:{
							System.out.println("Inserisci il nuovo numero massimo di partecipanti: ");
							String max = scanner.next();
							activity.setMaxNumberOfPeople(Integer.parseInt(max));
							System.out.println("Aggiornata attività scelta con il nuovo numero massimo di partecipanti!");
							break;
						} default: {
							System.out.println("Hai annullato l'operazione...");
							return;
						}
					}
					System.out.println("Vuoi effettuare un'altra modifica a questa attività? (Y/no)");
					again = scanner.next();
				} while(again.equalsIgnoreCase("y"));
				this.activityManager.updateActivity(activity);
				System.out.println("Modifiche all'attività salvate correttamente!");
				return;
			} default:{
				System.out.println("Hai annullato l'operazione...");
			}
		}
	}

	public void ssdAcquistaProdotti() throws InterruptedException {
		System.out.println("Benvenuto al servizio Bar della struttura!");
		System.out.println("Seleziona il prodotto che vuoi acquistare: \n");
		List<Product> products = this.barController.getProducts();
		if(products.isEmpty()) {
			System.out.println("Al momento non sono disponibili prodotti...");
			return;
		}
		List<Product> productsInOrder = new LinkedList<>();
		int n;
		boolean loop =true;
		for (n = 0; n < products.size(); n++) {
			System.out.println(n +") " + products.get(n).getName());
		}
		while(loop){
			System.out.println("\n Seleziona il prodotto che vuoi acquistare: ");
			int scelta = scanner.nextInt();
			System.out.println("scelta: " + scelta + " su "+ barController.getProducts().size());
			while(scelta<0 || scelta>=barController.getProducts().size()){
				System.out.println("Numero del prodotto non valido... \n Inseriscine un'altro: ");
				scelta = scanner.nextInt();
			}
			productsInOrder.add(products.get(scelta));
			System.out.println(products.get(scelta).getName() + " è stato aggiunto al carrello.");
			System.out.println("Vuoi aggiungere altri prodotti? (Y/n)");
			if(!scanner.next().equalsIgnoreCase("y")){
				loop=false;
			}
		}
		System.out.println("Ecco il tuo carrello: ");
		productsInOrder.forEach(System.out::println);
		double totale = productsInOrder.stream().mapToDouble(Product::getPrice).sum();
		System.out.println("\nQuesto il tuo totale: " +totale);
		System.out.println("Inquadra il QrCode associato alla tua postazione: ");
		boolean again = true;
		while(again) {
			String qrcode = scanner.next();
			Location location = locationManager.findByQrCode(qrcode);
			if (location != null) {
				System.out.println("Postazione trovata correttamente!\n");
				System.out.println("Inizio procedura di pagamento...");
				ssdElaboraPagamento(totale);
				this.barController.createNewOrder(productsInOrder);
				ssdNotificaTerminaliPresenzaOrdini();
				System.out.println("Il tuo ordine ti verrà consegnato direttamente alla tua postazione!");
				again = false;
			} else {
				System.out.println("La postazione associata al QrCode inquadrato non risulta presente nel sistema...");
				System.out.println("Inserisci un'altro codice: ");
			}
		}

	}
	public void ssdModificaAttrezzaturaLudicoSportiva(){
		System.out.println("Ecco tutte le attrezzature ludico/sportive attualmente presenti in struttura: ");
		List<Equipment> equipments = equipmentManager.getAllEquipments();
		if(!equipments.isEmpty()) {
			equipments.forEach(e-> System.out.println(e.toString()));
			System.out.println("Seleziona l'attrezzatura da modificare: ");
			int scelta = scanner.nextInt()-1;
			while(scelta<0 || scelta>=equipmentManager.getAllEquipments().size()){
				System.out.println("Numero del prodotto non valido... \n Inseriscine un'altro: ");
				scelta = scanner.nextInt()-1;
			}
			System.out.println("Inserisci la nuova descrizione: ");
			String desc = scanner.next();
			Equipment newEq = equipments.get(scelta);
			newEq.setDescription(desc);
			equipmentManager.saveEquipment(newEq);
			System.out.println("modifica effettuata con successo!");
			System.out.println(equipmentManager.getAllEquipments().get(0).getDescription());
		} else System.out.println("Non sono presenti attrezzature in struttura...");
	}

	public void ssdNotificaTerminaliPresenzaOrdini(){
		List<Order> orders = this.barController.getAllOrders();
		if (!orders.isEmpty()){
			System.out.println("Ordine ricevuto con successo!");
		}
	}
	public void ssdNotificaClienti(JavaMailSender javaMailSender) {
		NotificationManager notificationManager = new NotificationManager(javaMailSender);
		System.out.println("Scegli la categoria di notifica che vuoi inviare:");
		System.out.println("1: Inviare il programma di un'attività.\n");
		System.out.println("2: Inviare una promozione.\n");
		switch (Integer.parseInt(scanner.next())) {
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
				String promozione = scanner.next();
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
		System.out.println("Lo stabilimento offre i seguenti servizi:");
		System.out.println("- Prenotazione di postazioni tramite il sistema");
		System.out.println("- Prenotazione delle attività ludiche che si tengono quotidinamente nella spiaggia");
		System.out.println("- Possibilità di effettuare ordinazione nel bar della struttura comodamente dal proprio ombrellone");
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
	public void ssdVisualizzaAttivitaInProgramma() {
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
			System.out.println("Postazione numero: " + i++ + ") " + l.getId());
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
		String newType = scanner.next();
		//asking for the description
		System.out.println("La descrizione della spiaggia è: " + beach.getDescription());
		System.out.println("Inserire la nuova descrizione, o premere invio per non modificarla");
		String newDes = scanner.next();
		//effects the changes
		if(newType == null) { newType = beach.getSandType(); }
		if(newDes == null) { newDes = beach.getDescription(); }
		this.beachManager.updateModifyBeach(beach.getId(), newType, newDes); //TODO il metodo updateModifyBeach dà errore
		System.out.println("Le modifiche sono state effettuate con successo");
	}

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
			System.out.println(i + ") id: " + o.getId() + " |che contiene i prodotti: " + o.getProducts());
			i++;
		}
		System.out.println("Inserire il numero dell'ordine che si vuole soddisfare");
		int choice = scanner.nextInt();
		//valid input
		if(choice < i || choice > 1) {
			Order order = nonCompletedOrders.get(choice - 1);
			System.out.println("Si è scelto l'ordine: ");
			this.barController.createRecipt(order);
			System.out.println("Stampa scontino in corso...");
			this.ssdStampaScontrino(order);
			this.barController.setOrderAsCompleted(order.getId()); //TODO il metodo setOrderAsCompleted dà errore
			System.out.println("Il sistema ha segnato l'ordine come completato correttamente");
		} else {
			//invalid input
			System.out.println("Immesso un indice di ordine sbagliato");
		}
	}

}