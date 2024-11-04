package com.university.app;

import com.university.inOut.*;
import com.university.service.ReportGenerator;

import java.util.List;

public class App {

    public static void main(String[] args) {
        App app = new App();
        app.task1();
        app.task2();
        app.task3();
    }

    public void task1() {
        CSVManager csvManager = new CSVManager();
        ReportGenerator manager = new ReportGenerator();

        List<String[]> inputData = csvManager.read("src/main/resources/input.csv", true);
        List<String[]> outputData = manager.firstReport(inputData);
        csvManager.write("src/main/resources/solution.csv", outputData);
    }

    public void task2() {
        CSVManager csvManager = new CSVManager();
        ReportGenerator manager = new ReportGenerator();

        List<String[]> inputData = csvManager.read("src/main/resources/input_2.csv", true);
        List<String[]> outputData = manager.secondReport(inputData);
        csvManager.write("src/main/resources/solution_2.csv", outputData);

    }

    public void task3() {
        CSVManager csvManager = new CSVManager();
        ReportGenerator manager = new ReportGenerator();

        List<String[]> inputData = csvManager.read("src/main/resources/input_3.csv", true);
        List<String[]> outputData = manager.thirdReport(inputData);
        csvManager.write("src/main/resources/solution_3.csv", outputData);
    }
}
