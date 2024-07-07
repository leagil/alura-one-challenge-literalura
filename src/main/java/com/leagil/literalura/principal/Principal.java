package com.leagil.literalura.principal;

import com.leagil.literalura.model.DatosLibro;
import com.leagil.literalura.model.Libro;
import com.leagil.literalura.model.LibrosEnUnaPagina;
import com.leagil.literalura.model.Persona;
import com.leagil.literalura.repository.AutorRepository;
import com.leagil.literalura.repository.LibroRepository;
import com.leagil.literalura.service.ConsumoAPI;
import com.leagil.literalura.service.ConvierteDatos;
import org.postgresql.util.PSQLException;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorioLibros;
    private AutorRepository repositorioAutores;

    public Principal(LibroRepository repositoryLibros, AutorRepository repositoryAutores) {
        this.repositorioLibros = repositoryLibros;
        this.repositorioAutores = repositoryAutores;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ----------------------------------------------
                    1 - Buscar libro por título.
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    listarLibrosEnDB();
                    break;
                case 3:
                    listarAutoresEnDB();
                    break;
                case 4:
                    listarAutoresVivosEnDB();
                    break;
                case 5:
                    listarLibrosEnUnIdiomaEnDB();
                    break;
                case 0:
                    System.out.println("Finalizando la aplicación...");
                    break;
                default:
                    System.out.println("Opción no existente");
            }
        }

    }

    private void listarLibrosEnUnIdiomaEnDB() {
        System.out.println("Ingrese el idioma buscado: ");
        String idiomaBuscado = teclado.nextLine();
        List<Libro> librosEncontrados = repositorioLibros.findByIdioma(idiomaBuscado);
        if(librosEncontrados.isEmpty()) {
            System.out.println("La base de datos local no incluye libros en el idioma buscado");
        } else {
            librosEncontrados.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEnDB() {
        System.out.println("Ingrese el año en el que el autor debe haber estado vivo: ");
        Integer anioVivo = teclado.nextInt();
        teclado.nextLine();
        List<Persona> autoresEncontrados =  repositorioAutores.findByAnioNacimientoLessThanEqualAndAnioMuerteGreaterThanEqual(anioVivo, anioVivo);
        if(autoresEncontrados.isEmpty()) {
            System.out.println("La base de datos local no incluye autores vivos en ese rango de años");
        } else {
            autoresEncontrados.forEach(System.out::println);
        }
    }

    private void listarAutoresEnDB() {
        List<Persona> autoresEncontrados = repositorioAutores.findAll();
        if(autoresEncontrados.isEmpty()) {
            System.out.println("No hay autores en la base de datos local");
        } else {
            autoresEncontrados.forEach(System.out::println);
        }
    }

    private void listarLibrosEnDB() {
        List<Libro> librosEncontrados = repositorioLibros.findAll();
        if(librosEncontrados.isEmpty()) {
            System.out.println("No hay libros en la base de datos local");
        } else {
            librosEncontrados.forEach(System.out::println);
        }
    }

    private void buscarLibroWeb() {
        Optional<DatosLibro> datosPrimerLibro = getDatosLibro();
        if (datosPrimerLibro.isPresent()) {
            DatosLibro primerLibro = datosPrimerLibro.get();
            Libro libro = new Libro(primerLibro);
            System.out.println("Libro encontrado: " + libro);
            Persona autor = libro.getAutor();
            System.out.println("el nombre del autor es "+autor.getNombre());
            Optional<Persona> autorEncontrado = repositorioAutores.findByNombreContainsIgnoreCase(autor.getNombre());
            if(!autorEncontrado.isPresent()){
                Persona autorGuardado = repositorioAutores.saveAndFlush(autor);
                libro.setAutor(autorGuardado);
            } else {
                System.out.println("El autor ya está presente en la base de datos local");
                libro.setAutor(autorEncontrado.get());
            }
            try {
                repositorioLibros.save(libro);
            } catch (Exception e) {
                System.out.println("El libro ya está presente en la base de datos local");
                System.out.println(e.getMessage());
            }
        } else System.out.println("Libro no encontrado");
    }

    private Optional<DatosLibro> getDatosLibro() {
        System.out.println("Ingrese el título del libro a buscar: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        System.out.println(json);
        LibrosEnUnaPagina librosEnUnaPagina = conversor.obtenerDatos(json, LibrosEnUnaPagina.class);
        Optional<DatosLibro> primerLibro = librosEnUnaPagina.resultados().stream().findFirst();
        return primerLibro;
    }


}
