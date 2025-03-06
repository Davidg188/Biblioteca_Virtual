/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.biblioteca_virtual;

/**
 *
 * @author David Gomez
 */

class Libro {
    private final String titulo;
    private final String autor;
    private final int anioPublicacion;
    private final String genero;
    private boolean disponible;
    private double calificacion;
    private int numCalificaciones;

    public Libro(String titulo, String autor, int anioPublicacion, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.genero = genero;
        this.disponible = true;
        this.calificacion = 0;
        this.numCalificaciones = 0;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void prestar() {
        disponible = false;
    }

    public void devolver() {
        disponible = true;
    }

    public void calificarLibro(double nota) {
        if (nota >= 0 && nota <= 5) {
            calificacion = ((calificacion * numCalificaciones) + nota) / (++numCalificaciones);
            System.out.println("Calificacion anadida con exito.");
        } else {
            System.out.println("Calificacion invalida. Debe estar entre 0 y 5.");
        }
    }

    public String getInfo() {
        return String.format("Titulo: %s | Autor: %s | Ano: %d | Genero: %s | Disponible: %s | Calificacion: %.1f",
                titulo, autor, anioPublicacion, genero, (disponible ? "Si" : "No"), calificacion);
    }
}

class Biblioteca {
    private final java.util.List<Libro> libros = new java.util.ArrayList<>();

    public void agregarLibro(Libro libro) {
        libros.add(libro);
        System.out.println("Libro agregado correctamente.");
    }

    public void buscarLibro(String termino) {
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(termino) || libro.getAutor().equalsIgnoreCase(termino)) {
                System.out.println(libro.getInfo());
                return;
            }
        }
        System.out.println("Libro no encontrado.");
    }

    public void prestarLibro(String titulo) {
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo) && libro.isDisponible()) {
                libro.prestar();
                System.out.println("Libro prestado.");
                return;
            }
        }
        System.out.println("No se puede prestar el libro.");
    }

    public void devolverLibro(String titulo) {
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo) && !libro.isDisponible()) {
                libro.devolver();
                System.out.println("Libro devuelto.");
                return;
            }
        }
        System.out.println("El libro no estaba prestado.");
    }

    public void mostrarLibrosDisponibles() {
        boolean hayLibros = false;
        for (Libro libro : libros) {
            if (libro.isDisponible()) {
                System.out.println(libro.getInfo());
                hayLibros = true;
            }
        }
        if (!hayLibros) System.out.println("No hay libros disponibles.");
    }

    public java.util.List<Libro> getLibros() {
        return libros;
    }

    public void mostrarGeneroMasPopular() {
        java.util.Map<String, Integer> conteoGeneros = new java.util.HashMap<>();
        for (Libro libro : libros) {
            conteoGeneros.put(libro.getGenero(), conteoGeneros.getOrDefault(libro.getGenero(), 0) + 1);
        }
        String generoPopular = conteoGeneros.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey)
                .orElse("N/A");
        System.out.println("Genero mas popular: " + generoPopular);
    }
}

public class Biblioteca_Virtual {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            System.out.println("\n--- Biblioteca Virtual ---");
            System.out.println("1. Agregar un libro");
            System.out.println("2. Buscar un libro");
            System.out.println("3. Prestar un libro");
            System.out.println("4. Devolver un libro");
            System.out.println("5. Mostrar libros disponibles");
            System.out.println("6. Calificar un libro");
            System.out.println("7. Mostrar genero mas popular");
            System.out.println("8. Salir");
            System.out.print("Elige una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> {
                    System.out.print("Titulo: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Autor: ");
                    String autor = scanner.nextLine();
                    System.out.print("Ano de publicacion: ");
                    int anio = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Genero: ");
                    String genero = scanner.nextLine();
                    biblioteca.agregarLibro(new Libro(titulo, autor, anio, genero));
                }
                case 2 -> {
                    System.out.print("Introduce el titulo o autor: ");
                    biblioteca.buscarLibro(scanner.nextLine());
                }
                case 3 -> {
                    System.out.print("Titulo del libro a prestar: ");
                    biblioteca.prestarLibro(scanner.nextLine());
                }
                case 4 -> {
                    System.out.print("Titulo del libro a devolver: ");
                    biblioteca.devolverLibro(scanner.nextLine());
                }
                case 5 -> biblioteca.mostrarLibrosDisponibles();
                case 6 -> {
                    System.out.print("Titulo del libro a calificar: ");
                    String tituloCalificar = scanner.nextLine();
                    System.out.print("Calificacion (0-5): ");
                    double calificacion = scanner.nextDouble();
                    for (Libro libro : biblioteca.getLibros()) {
                        if (libro.getTitulo().equalsIgnoreCase(tituloCalificar)) {
                            libro.calificarLibro(calificacion);
                            break;
                        }
                    }
                }
                case 7 -> biblioteca.mostrarGeneroMasPopular();
                case 8 -> {
                    System.out.println("Saliendo del sistema...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opcion invalida, intenta de nuevo.");
            }
        }
    }
}
