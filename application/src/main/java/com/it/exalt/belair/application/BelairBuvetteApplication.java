package com.it.exalt.belair.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application Belair's Buvette.
 */
@SpringBootApplication(scanBasePackages = "com.it.exalt.belair")
public class BelairBuvetteApplication {

    public static void main(String[] args) {
        SpringApplication.run(BelairBuvetteApplication.class, args);
    }
}
