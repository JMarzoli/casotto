package it.unicam.cs.ids.casotto;

import it.unicam.cs.ids.casotto.controller.*;
import it.unicam.cs.ids.casotto.model.*;
import it.unicam.cs.ids.casotto.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication
public class CasottoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasottoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BeachManager beachManager, EquipmentManager equipmentManager
			, UmbrellaManager umbrellaManager, BeachChairManager beachChairManager
			, CustomerManager customerManager, ActivityManager activityManager
			, ProductRepository productRepository, OrderRepository orderRepository
			, LocationRepository locationRepository, UmbrellaRepository umbrellaRepository
			, BeachChairRepository beachChairRepository, MailSender2 mailSender2) {
		return args -> {
/*			GESTIONE SPIAGGIA
			Beach newBeach = new Beach();
			beachManager.saveBeach(newBeach);
			List<String> changes = new ArrayList<>();
			System.out.println("Per favore inserisci il tipo di spiaggia");
			Scanner in = new Scanner(System.in);
			String sandType = in.nextLine();
			newBeach.setSandType(sandType);
			changes.add(sandType);
			System.out.println("Per favore inserisci una descrizione della spiaggia");
			String description = in.nextLine();
			newBeach.setDescription(description);
			changes.add(description);
			beachManager.saveBeach(newBeach);
			System.out.println("Perfetto! Queste sono le modifiche che hai effettuato");
			System.out.println(Arrays.toString(changes.toArray()));*/
/*			GESTIONE ATTREZZATURA LUDICA
			Equipment newEquipment = new Equipment();
			equipmentManager.saveEquipment(newEquipment);
			List<String> changes = new ArrayList<>();
			System.out.println("Per favore inserisci il nome dell'attrezzatura ludica");
			Scanner in = new Scanner(System.in);
			String name = in.nextLine();
			newEquipment.setName(name);
			changes.add(name);
			System.out.println("Per favore inserisci una descrizione dell'attrezzatura ludica'");
			String description = in.nextLine();
			newEquipment.setDescription(description);
			changes.add(description);
			equipmentManager.saveEquipment(newEquipment);
			System.out.println("Perfetto! Queste sono le modifiche che hai effettuato");
			System.out.println(Arrays.toString(changes.toArray()));*/
/*			MODIFICA PREZZO
			BeachChair newBeachChair = new BeachChair();
			Umbrella newUmbrella = new Umbrella();
			beachChairManager.saveBeachChair(newBeachChair);
			umbrellaManager.saveUmbrella(newUmbrella);
			List<Double> changes = new ArrayList<>();
			System.out.println("Per favore inserisci il prezzo della sdraio");
			Scanner in = new Scanner(System.in);
			double price = in.nextDouble();
			newBeachChair.setPrice(price);
			changes.add(price);
			System.out.println("Per favore inserisci il prezzo dell'ombrellone");
			double price2 = in.nextDouble();
			newUmbrella.setPrice(price2);
			changes.add(price2);
			beachChairManager.saveBeachChair(newBeachChair);
			umbrellaManager.saveUmbrella(newUmbrella);
			System.out.println("Perfetto! Queste sono le modifiche che hai effettuato");
			System.out.println(Arrays.toString(changes.toArray()));*/
/*			EFFETTUO PAGAMENTO
			System.out.println("Per favore inserisci il titolare della carta");
			Scanner in = new Scanner(System.in);
			String cardOwner = in.nextLine();
			System.out.println("Per favore inserisci il numero della carta");
			String cardNumber = in.nextLine();
			System.out.println("Per favore inserisci la data di scadenza");
			String expiryDate = in.nextLine();
			System.out.println("Per favore inserisci il cvv");
			int cvv = in.nextInt();
			System.out.println("Perfetto, effettuo il pagamento...");
			Thread.sleep(4000);
			System.out.println("Pagamento effettuato con successo!");*/
/*			Customer customer = new Customer();
			customerManager.saveCustomer(customer);
			Activity activity = new Activity();
			activityManager.saveActivity(activity);
			activityManager.addCustomerToActivity(customer.getId(), activity.getId());*/
/*//			CREAZIONE ORDINE
			BarController barController = new BarController(productRepository, orderRepository);
			Product product = new Product("name", 10, "description", 10);
			Product product1 = new Product("name1", 10, "description1", 10);
			barController.addNewProduct(product);
			barController.addNewProduct(product1);
			List<Product> products = List.of(product, product1);
			System.out.println("Questo è il tuo ordine: " + products);
			barController.createNewOrder(products);
			System.out.println("Questo è il suo totale" + products.stream().mapToDouble(product2 -> product1.getPrice()).sum());
//			VISIONE DEGLI ORDINI NON COMPLETATI
			WorkersController workersController = new WorkersController(orderRepository);
			System.out.println("---------------------------------------");
			List<Order> orders = workersController.getOrdersNonCompleted();
			for (Order order: orders) {
				System.out.println(order.getProducts());
			}
//			IMPOSTO ORDINE COMPLETATO
			workersController.setOrderCompleted(orders.get(0).getId());*/
//			CREO LOCATION CON DESCRIZIONE
/*			LocationManager locationManager = new LocationManager(locationRepository);
			Location location = locationManager.createLocation("Location");
			UmbrellaManager umbrellaManager1 = new UmbrellaManager(umbrellaRepository);
			Umbrella umbrella = new Umbrella();
			umbrella.setLocation(location);
			umbrellaManager1.saveUmbrella(umbrella);
			locationManager.addUmbrellaToLocation(location.getId(), umbrella);
			BeachChairManager beachChairManager1 = new BeachChairManager(beachChairRepository);
			BeachChair beachChair = new BeachChair();
			beachChair.setLocation(location);
			beachChairManager1.saveBeachChair(beachChair);
			locationManager.addBeachChairToLocation(location.getId(), beachChair);
			System.out.println(locationManager.getAllLocations());*/
//			VISUALIZZA ATTIVITA' IN PROGRAMMA
/*			Customer customer1 = new Customer();
			customerManager.saveCustomer(customer1);
			Activity activity1 = new Activity();
			activityManager.saveActivity(activity1);
			activityManager.addCustomerToActivity(customer1.getId(), activity1.getId());
			System.out.println(activityManager.getAllActivities());*/
//			VISUALIZZA STORICO ORDINI
/*			BarController barController1 = new BarController(productRepository, orderRepository);
			Product product1 = productRepository.findById(1L).orElse(null);
			Product product2 = productRepository.findById(2L).orElse(null);
			barController1.addNewProduct(product1);
			barController1.addNewProduct(product1);
			List<Product> products = List.of(product1, product2);
			barController1.createNewOrder(products);
			System.out.println(barController1.getAllOrders());*/
			//MODIFICA ATTIVITA'
//			List<Activity> allActivities = activityManager.getAllActivities();
//			System.out.println("Ecco tutte le attività in programma: \n");
//			for (int i = 0; i < allActivities.size(); i++) {
//				System.out.println(i + ": " + allActivities.get(i).getInfo());
//			}
//			System.out.println("Scegli cosa vuoi fare: \n");
//			System.out.println("1: Aggiungi una nuova attività.\n");
//			System.out.println("2: Elimina un'attività.\n");
//			System.out.println("3: Modifica un'attività.\n");
//			Scanner in = new Scanner(System.in);
//			String a = in.nextLine();
//
//			switch (Integer.parseInt(a)){
//				case 1: {
//					System.out.println("Inserisci le info dell'attività: ");
//					String info = in.nextLine();
//					System.out.println("Inserisci la data di inizio dell'attività: ");
//					String dataInizio = in.nextLine();
//					System.out.println("Inserisci la data di fine dell'attività: ");
//					String dataFine = in.nextLine();
//					System.out.println("Inserisci il numero di partecipanti massimo dell'attività: ");
//					String numPartecipanti = in.nextLine();
//
//				}
//				case 2: {
//					System.out.println("Seleziona l'attività da eliminare: \n");
//					a = in.nextLine();
//					System.out.println("Hai selezionato l'attività numero "+a+"\n");
//					activityManager.deleteActivity(allActivities.get(Integer.parseInt(a)).getId());
//				}
//				case 3: {
//					System.out.println("Seleziona l'attività da modificare: \n");
//					a = in.nextLine();
//					System.out.println("Hai selezionato l'attività numero "+a+"\n");
//					Activity activity = allActivities.get(Integer.parseInt(a));
//					System.out.println("Scegli cosa modificare: ");
//					System.out.println("1: Modifica le info.\n");
//					System.out.println("2: Modifica la data d'inizio.\n");
//					System.out.println("3: Modifica la data di fine.\n");
//					System.out.println("4: Modifica il numero massimo di partecipanti.\n");
//					a = in.nextLine();
//					do{
//						switch (Integer.parseInt(a)) {
//							case 1:{
//								System.out.println("Inserisci la nuova info: ");
//								String info = in.nextLine();
//								activity.setInfo(info);
//							}
//							case 2:{
//								System.out.println("Inserisci la nuova data d'inizio: ");
//								String date = in.nextLine();
//								activity.setActivityBeginDate(LocalDate.parse(date));
//							}
//							case 3:{
//								System.out.println("Inserisci la nuova data di fine: ");
//								String date = in.nextLine();
//								activity.setActivityBeginDate(LocalDate.parse(date));
//							}case 4:{
//								System.out.println("Inserisci il nuovo numero di iscrizioni: ");
//								String date = in.nextLine();
//								activity.setMaxNumberOfPeople(Integer.parseInt(date));
//							}
//						};
//						System.out.println("Premi 0 se vuoi effettuare un'altra modifica: ");
//						a = in.nextLine();
//					} while(a.equals("0"));
//					activityManager.updateActivity(activity);
//				}
//			}
			//NOTIFICA TERMINALI PRESENZA ORDINI NON COMPLETATI
//			BarController barController1 = new BarController(productRepository, orderRepository);
//			List<Order> orders = barController1.getAllOrders().stream().filter(o -> !o.isHasBeenCompleted()).toList();
//			if(!orders.isEmpty()){
//				System.out.println("Ci sono ancora ordini da completare!");
//			}
			//STAMPA SCONTRINO
//			BarController barController1 = new BarController(productRepository, orderRepository);
//			Product product1 = productRepository.findById(1L).orElse(null);
//			Product product2 = productRepository.findById(2L).orElse(null);
//			barController1.addNewProduct(product1);
//			barController1.addNewProduct(product1);
//			List<Product> products = List.of(product1, product2);
//			barController1.createNewOrder(products);
//			System.out.println("SCONTRINO \n"+"Prodotti acquistati:\n");
//			double tot = 0;
//			for (Product product: products) {
//				System.out.println(product.getName() + " "+ product.getPrice());
//				tot += product.getPrice();
//			}
//			System.out.println("\nTotale pagato: "+ tot);
//			INVIA NOTIFICA CLIENTI
			/*mailSender2.sendSimpleMessage("email", "oggetto", "testo");*/
//			INVIA REMINDER ATTIVITA' GIORNARLIERA
/*			activityManager.getAllActivities().forEach(activity -> {
				if (activity.getActivityBeginDate().isEqual(LocalDate.now())) {
					activity.getCustomersInThisActivity().forEach(customer -> {
						mailSender2.sendSimpleMessage(customer.getEmail(), "Inizio attività", activity.getInfo());
					});
				}
			});*/
//			VISUALIZZA CATOLOGO SERVIZI
/*			System.out.println("SERVIZI DISPONIBILI IN QUESTO STABILIMENTO" + List.of("SERVIZIO BAR", "SERVIZIO BALNEARE CON PRENOTAZIONE DI OMBRELLONI E LETTINI", "ATTIVITA' ALL'APERTO"));*/
		};
	}
}
