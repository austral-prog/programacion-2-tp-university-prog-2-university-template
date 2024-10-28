package com.university;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorCSV {

    public List<Estudiante> leerEstudiantes(String archivoRuta) {
        List<Estudiante> estudiantes = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(archivoRuta))) {
            String linea;
            lector.readLine();

            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    String nombre = datos[2];
                    String curso = datos[1];
                    estudiantes.add(new Estudiante(nombre, curso));
                }
            }
        } catch (IOException e) {
            System.err.println("No se encontró archivo en el CSV");
        }

        return estudiantes;
    }
}
