package TpUniversity.app;

import TpUniversity.inOut.*;
import TpUniversity.service.UniversityManager;

import java.util.List;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.task1();
        app.task2();
    }

    public void task1() {
        CSVReader reader = new CSVReader();
        CSVWriter writer = new CSVWriter();
        UniversityManager manager = new UniversityManager();

        List<String[]> inputData = reader.readCSV("src/TpUniversity/input.csv");
        List<String[]> outputData = manager.firstTaskLogic(inputData);
        writer.writeCSV("src/TpUniversity/solution.csv", outputData);
    }

    public void task2() {
        CSVReader reader = new CSVReader();
        CSVWriter writer = new CSVWriter();
        UniversityManager manager = new UniversityManager();

        List<String[]> inputData = reader.readCSV("src/TpUniversity/input_2.csv");
        List<String[]> outputData = manager.secondTaskLogic(inputData);
        writer.writeCSV("src/TpUniversity/solution_2.csv", outputData);
    }
}
