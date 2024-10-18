package com.university;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

public class LectorCSVTest {

    @Test
    public void pruebaLeerEstudiantes() {
        LectorCSV lector = new LectorCSV();
        List<Estudiante> estudiantes = lector.leerEstudiantes("src/main/resources/estudiantes.csv");


        assertFalse(estudiantes.isEmpty());

        Estudiante primerEstudiante = estudiantes.get(0);
        assertEquals("Olivia Red", primerEstudiante.getNombre());
        assertEquals("Political Science", primerEstudiante.getCurso());
    }
}
