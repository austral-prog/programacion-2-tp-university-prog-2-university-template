package com.university;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgregadorEstudiantes {

    public Map<String, Integer> agregarEstudiantesPorCurso(List<Estudiante> estudiantes) {
        Map<String, Integer> conteoCursos = new HashMap<>();

        for (Estudiante estudiante : estudiantes) {
            String nombre = estudiante.getNombre();
            conteoCursos.put(nombre, conteoCursos.getOrDefault(nombre, 0) + 1);
        }

        return conteoCursos;
    }
}
