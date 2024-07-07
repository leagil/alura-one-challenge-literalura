package com.leagil.literalura;

import com.leagil.literalura.principal.Principal;
import com.leagil.literalura.repository.AutorRepository;
import com.leagil.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repositorioLibros;
	@Autowired
	private AutorRepository repositorioAutores;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorioLibros, repositorioAutores);
		principal.muestraElMenu();
	}
}
