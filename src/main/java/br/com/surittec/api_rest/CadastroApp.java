package br.com.surittec.api_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class CadastroApp {

	public static final String VERSAO = "1.0";

    public static void main(String[] args) {
        SpringApplication.run(CadastroApp.class, args);

        System.out.println("\n\n\n\n\n-----------------------------------------------------------------------------------");
        System.out.println("APP Iniciada");
        System.out.println("-----------------------------------------------------------------------------------\n\n");
    }
}
