package com.example.Usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class UsuarioApplication {

	public static void main(String[] args) {

		// Cargar .env
		Dotenv dotenv = Dotenv.load();

		// Configurar propiedades del sistema
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		SpringApplication.run(UsuarioApplication.class, args);
	}

}
