package com.university;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EscritorCSV {

    public void escribirResultados(String archivoRuta, Map<String, Integer> conteoEstudiantes) {
        // Obtener una lista de los nombres de los estudiantes
        List<String> nombresEstudiantes = new ArrayList<>(conteoEstudiantes.keySet());

        // Ordenar los nombres alfabéticamente
        Collections.sort(nombresEstudiantes);

        try (FileWriter escritor = new FileWriter(archivoRuta)) {
            escritor.append("Nombre_Estudiante,Cantidad_Cursos\n");

            // Escribir las entradas ordenadas alfabéticamente en el CSV
            for (String nombre : nombresEstudiantes) {
                Integer cantidadCursos = conteoEstudiantes.get(nombre);
                escritor.append(nombre).append(",").append(String.valueOf(cantidadCursos)).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo CSV");
        }
    }
}


