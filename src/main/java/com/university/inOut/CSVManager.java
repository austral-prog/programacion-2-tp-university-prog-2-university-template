package com.university.inOut;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVManager {

    // Lee un archivo CSV y retorna una lista de arrays de Strings, cada elemento de la lista es una línea del CSV
    // y cada elemento del array de strings es una de las columnas del CSV

    public List<String[]> read(String filePath, boolean ignoreHeader) {
        List<String[]> data = new ArrayList<>();  // Lista para almacenar los datos del archivo CSV
        String line;  // Variable para almacenar cada línea entera sin separar

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");     // Divide la línea en un String array separado por comas
                data.add(values);                      // Agrega el arreglo de Strings a la lista
            }
        } catch (Exception e) {
            e.printStackTrace();  // Imprime cualquier error que ocurra
        }

        if (ignoreHeader) {
            data.remove(0);  // Elimina el header si se le pasa true como argumento
        }

        return data;  // Retorna la lista con los datos del CSV
    }

    // Toma un archivo CSV y escribe cada elemento de la lista como una línea, tomando cada dato del array
    // de strings y separándolos con una coma

    public void write(String filePath, List<String[]> writeData) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            int index = 1;
            for (String[] lineData : writeData) {
                // Recorre los arrays y los escribe en el CSV, uniendo con ","
                bw.write(String.join(",", lineData));
                if (index < writeData.size()) {
                    bw.newLine();
                }
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();  // Imprime el error en caso de que ocurra alguno
        }
    }
}

