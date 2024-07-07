package com.leagil.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
//    @Column(name = "anio_nacimiento")
    private Integer anioNacimiento;
//    @Column(name = "anio_muerte")
    private Integer anioMuerte;

    public Persona() {}

    public Persona(DatosPersona d) {
        this.nombre = d.nombre();
        this.anioNacimiento = d.anioNacimiento();
        this.anioMuerte = d.anioMuerte();
    }

    public Integer getAnioMuerte() {
        return anioMuerte;
    }

    public void setAnioMuerte(Integer anioMuerte) {
        this.anioMuerte = anioMuerte;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return
                "{ Nombre: '" + nombre + "' - "
                        + "Año de nac.: " + anioNacimiento + " - "
                        + "Año de muerte: " + anioMuerte + " } ";
    }

}
