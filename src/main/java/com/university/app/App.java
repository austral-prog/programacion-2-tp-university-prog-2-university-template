package TpUniversity.app;

import TpUniversity.inOut.*;
import TpUniversity.model.Evaluation;
import TpUniversity.model.Box;
import TpUniversity.service.UniversityManager;

import java.util.List;

public class App {
    // Object used on task2 to return multiple return variables
    Box<List<String[]>, List<Evaluation>> processedData;

    public static void main(String[] args) {
        App app = new App();
        app.task1();
        app.task2();
        app.task3();
    }

    public void task1() {
        CSVReader reader = new CSVReader();
        CSVWriter writer = new CSVWriter();
        UniversityManager manager = new UniversityManager();

        List<String[]> inputData = reader.readCSV("src/main/resources/input.csv");
        List<String[]> outputData = manager.firstTaskLogic(inputData);
        writer.writeCSV("src/main/resources/solution.csv", outputData);
    }

    public void task2() {
        CSVReader reader = new CSVReader();
        CSVWriter writer = new CSVWriter();
        UniversityManager manager = new UniversityManager();

<<<<<<< HEAD
        List<String[]> inputData = reader.readCSV("src/TpUniversity/input_2.csv");
        this.processedData = manager.secondTaskLogic(inputData);
        writer.writeCSV("src/TpUniversity/solution_2.csv", processedData.getFirst());

    }

    public void task3() {
        CSVReader reader = new CSVReader();
        CSVWriter writer = new CSVWriter();
        UniversityManager manager = new UniversityManager();

        List<String[]> inputData = reader.readCSV("src/TpUniversity/input_3.csv");
        List<String[]> outputData = manager.thirdTaskLogic(inputData, processedData);
        writer.writeCSV("src/TpUniversity/solution_3.csv", outputData);
=======
        List<String[]> inputData = reader.readCSV("src/main/resources/input_2.csv");
        List<String[]> outputData = manager.secondTaskLogic(inputData);
        writer.writeCSV("src/main/resources/solution_2.csv", outputData);
>>>>>>> 2166ae0580f33517d68f924cdf1f6e6f891fc7fa
    }
}
