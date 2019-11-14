/*
 * AICare DI - Artificial Intelligence Care (Dynamic Inventory)
 * Todos os direitos reservados.
 */
package br.com.aicare.di.api_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author Paulo Collares
 */
@SpringBootApplication
@EnableScheduling
public class AICareApplication {

    public static final String VERSAO = "1.0";

    public static void main(String[] args) {
        SpringApplication.run(AICareApplication.class, args);

        System.out.println("\n\n\n\n\n-----------------------------------------------------------------------------------");
        System.out.println("API REST AICare DI iniciado");
        System.out.println("-----------------------------------------------------------------------------------\n\n");
    }
}
