package TpUniversity.inOut;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {

    // Toma un archivo CSV y escribe cada elemento de la lista como una línea, tomando cada dato del array
    // de strings y separandolos con una coma

    public void writeCSV(String filePath, List<String[]> writeData) {
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
