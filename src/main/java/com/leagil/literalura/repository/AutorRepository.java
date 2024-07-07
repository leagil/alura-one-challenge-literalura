package com.leagil.literalura.repository;

import com.leagil.literalura.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Persona,Long> {
    Optional<Persona> findByNombreContainsIgnoreCase(String nombre);
    List<Persona> findByAnioNacimientoLessThanEqualAndAnioMuerteGreaterThanEqual(Integer AnioNacimiento, Integer AnioMuerte);


}
