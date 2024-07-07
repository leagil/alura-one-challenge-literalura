package com.leagil.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Persona autor;
    private String idioma;
    private Integer nroDescargas;

    public Libro() {}

    public Libro(DatosLibro d) {
        this.titulo = d.titulo();
        this.autor = new Persona(d.autores().get(0));
        this.idioma = d.idiomas().get(0);
        this.nroDescargas = d.nroDescargas();
    }

    public Persona getAutor() {
        return autor;
    }

    public void setAutor(Persona autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setIdioma(List<String> idiomas) {
        this.idioma = idiomas.get(0);
    }

    public Integer getNroDescargas() {
        return nroDescargas;
    }

    public void setNroDescargas(Integer nroDescargas) {
        this.nroDescargas = nroDescargas;
    }

    @Override
    public String toString() {
        return
                "Título: '" + titulo + "', "
                + "Autor: " + autor + ", "
                + "Idioma: " + idioma + ", "
                + "Nro descargas: " + nroDescargas;
    }

}
