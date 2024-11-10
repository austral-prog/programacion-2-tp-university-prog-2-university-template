package com.university.app;

import com.university.inOut.*;
import com.university.service.ReportGenerator;
// todo test coverage, cli
import java.util.List;

public class App {

    public static void main(String[] args) {
        App app = new App();
        app.runTasks();
    }

    public void runTasks(){
        task(1, "src/main/resources/input.csv", "src/main/resources/solution.csv");
        task(2, "src/main/resources/input_2.csv", "src/main/resources/solution_2.csv");
        task(3, "src/main/resources/input_3.csv", "src/main/resources/solution_3.csv");
    }

    public void task( int taskNumber, String readFilePath, String writeFilePath) {
        CSVManager csvManager = new CSVManager();
        ReportGenerator manager = new ReportGenerator();

        List<String[]> inputData = csvManager.read(readFilePath, true);

        List<String[]> outputData = switch (taskNumber){
            case 1 -> manager.firstReport(inputData);
            case 2 -> manager.secondReport(inputData);
            case 3 -> manager.thirdReport(inputData);
            default -> throw new IllegalArgumentException("Invalid task number");
        };
        
        csvManager.write(writeFilePath, outputData);
    }
}
