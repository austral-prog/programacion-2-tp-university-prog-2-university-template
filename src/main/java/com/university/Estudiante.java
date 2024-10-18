package com.university;

public class Estudiante {
    private String nombre;
    private String cursos;

    public Estudiante(String nombre, String cursos) {
        this.nombre = nombre;
        this.cursos = cursos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCursos() {
        return cursos;
    }
}

