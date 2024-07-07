package com.leagil.literalura.repository;

import com.leagil.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface LibroRepository extends JpaRepository<Libro,Long> {
    List<Libro> findAll();
    List<Libro> findByIdioma(String idioma);
}
