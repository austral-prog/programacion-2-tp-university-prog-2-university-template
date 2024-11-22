package com.university.service.Logic;

import com.university.model.*;
import com.university.model.Evaluations.*;
import com.university.service.ManagerRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;


public class ReportGenerator {

    public List<String[]> firstReport(List<String[]> inputData, ManagerRecord ManagerRecord) {
        // se recorre el csv y se agregan todos los estudiantes con sus cursos
        for (String[] firstInputLine : inputData) {
            DataMapper.firstDataPoint(firstInputLine, ManagerRecord);
        }
        List<String[]> outputData = new ArrayList<>();
        // se recorre la lista de estudiantes y se construye un arreglo para cada elemento de la lista
        // cada par de datos tiene nombre y cantidad de cursos, como indica el header.
        HashSet<Student> students = ManagerRecord.studentManager().entities;
        for (Student student : students) {
            outputData.add(ModelFormater.format(student, "first"));
        }
        // se ordena en orden alphabetic, compara el elemento 0 de los arreglos de la lista
        outputData.sort(Comparator.comparing(array -> array[0]));
        String[] header = {"Student_Name","Course_Count"};
        outputData.add(0, header);
        return outputData;
    }

    public List<String[]> secondReport(List<String[]> inputData, ManagerRecord ManagerRecord) {

        for (String[] secondInputLine : inputData) {
            DataMapper.secondDataPoint(secondInputLine, ManagerRecord);
        }

        HashSet<Evaluation> evaluations = ManagerRecord.evaluationManager().entities;

        List<String[]> output = new ArrayList<>();
        for (Evaluation evaluation : evaluations) {
            String[] line = ModelFormater.format(evaluation, "second");
            output.add(line);
        }

        output.sort(Comparator.comparing((String[] array) -> array[0])  // Sort by Subject_Name
                .thenComparing(array -> array[1])                       // Then by Evaluation_Name
                .thenComparing(array -> array[2]));                     // Then by Student_Name

        String[] header = {"Subject_Name","Evaluation_Name","Student_Name", "Grade"};
        output.add(0, header);

        return output;
    }
    public List<String[]> thirdReport(List<String[]> input_3csv, ManagerRecord ManagerRecord) {

        HashSet<Evaluation>  evaluations = ManagerRecord.evaluationManager().entities;
        for (String[] thirdInputLine : input_3csv) {
            DataMapper.thirdDataPoint(thirdInputLine, ManagerRecord);
        }

        List<String[]> outputData = new ArrayList<>();

        for (Evaluation evaluation : evaluations) {
            String[] line = ModelFormater.format(evaluation, "third");
            outputData.add(line);
        }

        String[] header = {"Evaluation_Name","Relevant_Grade","Evaluation_Type","Criteria","Criteria_Value","Passed/Failed"};
        outputData.add(0, header);

        return outputData;
    }
}

