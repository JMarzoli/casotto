package it.unicam.cs.ids.casotto;

import it.unicam.cs.ids.casotto.controller.*;
import it.unicam.cs.ids.casotto.model.Order;
import it.unicam.cs.ids.casotto.model.Product;
import it.unicam.cs.ids.casotto.repository.OrderRepository;
import it.unicam.cs.ids.casotto.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;


@SpringBootApplication
public class CasottoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasottoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BeachManager beachManager, EquipmentManager equipmentManager
			, UmbrellaManager umbrellaManager, BeachChairManager beachChairManager, CustomerManager customerManager, ActivityManager activityManager, ProductRepository productRepository, OrderRepository orderRepository) {
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
//			CREAZIONE ORDINE
			BarController barController = new BarController(productRepository, orderRepository);
			Product product = new Product("name", 10, "description", 10);
			Product product1 = new Product("name1", 10, "description1", 10);
			barController.addNewProduct(product);
			barController.addNewProduct(product1);
			List<Product> products = List.of(product, product1);
			System.out.println("Questo ?? il tuo ordine: " + products);
			barController.createNewOrder(products);
			System.out.println("Questo ?? il suo totale" + products.stream().mapToDouble(product2 -> product1.getPrice()).sum());
//			VISIONE DEGLI ORDINI NON COMPLETATI
			WorkersController workersController = new WorkersController(orderRepository);
			System.out.println("---------------------------------------");
			List<Order> orders = workersController.getOrdersNonCompleted();
			for (Order order: orders) {
				System.out.println(order.getProducts());
			}
//			IMPOSTO ORDINE COMPLETATO
			workersController.setOrderCompleted(orders.get(0).getId());
		};
	}
}
