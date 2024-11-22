package com.university.app;

import com.university.inOut.*;
import com.university.service.ManagerRecord;
import com.university.service.Logic.ReportGenerator;
// todo test coverage, cli
import java.util.List;

public class App {

    public static void main(String[] args) {
        App app = new App();
        ManagerRecord managerRecord = new ManagerRecord();
        app.runTasks(managerRecord);
    }

    public ManagerRecord runTasks(ManagerRecord managerRecord) {
        task(1, "src/main/resources/input.csv", "src/main/resources/solution.csv", managerRecord);
        task(2, "src/main/resources/input_2.csv", "src/main/resources/solution_2.csv", managerRecord);
        task(3, "src/main/resources/input_3.csv", "src/main/resources/solution_3.csv", managerRecord);
        return managerRecord;
    }

    public void task(int taskNumber, String readFilePath, String writeFilePath, ManagerRecord managerRecord) {
        CSVManager csvManager = new CSVManager();
        ReportGenerator manager = new ReportGenerator();

        List<String[]> inputData = csvManager.read(readFilePath, true);

        List<String[]> outputData = switch (taskNumber){
            case 1 -> manager.firstReport(inputData, managerRecord);
            case 2 -> manager.secondReport(inputData, managerRecord);
            case 3 -> manager.thirdReport(inputData, managerRecord);
            default -> throw new IllegalArgumentException("Invalid task number");
        };
        
        csvManager.write(writeFilePath, outputData);
    }
}
