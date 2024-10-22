package TpUniversity.inOut;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    // Lee un archivo CSV y retorna una lista de arrays de Strings, cada elemento de la lista es una línea del CSV
    // y cada elemento del array de strings es una de las columnas del CSV

    public List<String[]> readCSV(String filePath) {
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

        return data;  // Retorna la lista con los datos del CSV
    }
}

