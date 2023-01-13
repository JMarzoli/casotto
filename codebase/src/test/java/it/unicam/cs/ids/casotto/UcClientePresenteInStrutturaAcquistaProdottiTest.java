package it.unicam.cs.ids.casotto;

import it.unicam.cs.ids.casotto.controller.BarController;
import it.unicam.cs.ids.casotto.repository.OrderRepository;
import it.unicam.cs.ids.casotto.repository.ProductRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Logger;

@SpringBootTest
class UcClientePresenteInStrutturaAcquistaProdottiTest {

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private BarController barController;

    private static final Logger logger = Logger.getLogger(UcClientePresenteInStrutturaAcquistaProdottiTest.class.getName());

    @Autowired
    public UcClientePresenteInStrutturaAcquistaProdottiTest(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @BeforeEach
    void init() {
        logger.info("Creo il Bar");
        barController = new BarController(productRepository, orderRepository);
        logger.info("Bar creato correttamente!");
    }

    @Test
    void UCTest() {
        logger.info("Buongiorno, questi sono i prodotti disponibili: \n" + barController.getProducts());
        logger.info("Fai la tua scelta e invieremo l'ordine al terminale!");

    }
}
