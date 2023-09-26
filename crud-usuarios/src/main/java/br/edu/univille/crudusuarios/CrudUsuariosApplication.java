package br.edu.univille.crudusuarios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudUsuariosApplication {

	private static final Logger log = LoggerFactory.getLogger(CrudUsuariosApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CrudUsuariosApplication.class);
	}

	@Bean
	public CommandLineRunner loadData(UsuarioRepository repository) {
		return (args) -> {

			repository.save(new Usuario("Lucas Ricardo", "Teste123", "lucas@email.com", "47 1234 5678", "123.456.789-99", "Masculino"));
			repository.save(new Usuario("Guilherme Hirata", "Teste123", "email@email.com", "47 1234 5678", "123.456.789-99", "Feminino"));
			repository.save(new Usuario("Julia", "Teste123", "email@email.com", "47 1234 5678", "123.456.789-99", "Feminino"));
			repository.save(new Usuario("Ronaldo", "Teste123", "email@email.com", "47 1234 5678", "123.456.789-99", "Masculino"));
			repository.save(new Usuario("Matheus", "Teste123", "email@email.com", "47 1234 5678", "123.456.789-99", "Masculino"));
		};
	}

}