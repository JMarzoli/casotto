package it.unicam.cs.ids.casotto;

import it.unicam.cs.ids.casotto.controller.BeachManager;
import it.unicam.cs.ids.casotto.model.Beach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
class CasottoApplicationTests {

    private final BeachManager beachManager;

    @Autowired
    public CasottoApplicationTests(BeachManager beachManager) {
        this.beachManager = beachManager;
    }

    @Test
    void beachTest() {
        Beach newBeach = new Beach();
        this.beachManager.saveBeach(newBeach);
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
        this.beachManager.saveBeach(newBeach);
        System.out.println("Perfetto! Queste sono le modifiche che hai effettuato");
        System.out.println(Arrays.toString(changes.toArray()));
    }

}
