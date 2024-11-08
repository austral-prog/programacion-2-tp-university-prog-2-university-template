package com.university.service;

import com.university.model.*;
import com.university.model.Evaluations.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;


public class ReportGenerator {

    public List<String[]> firstReport(List<String[]> inputData) {
        // se recorre el csv y se agregan todos los estudiantes con sus cursos
        for (String[] strings : inputData) {
            DataReceiver.firstDataPoint(strings[0], strings[1], strings[2], strings[3], strings[4]);
        }
        List<String[]> outputData = new ArrayList<>();
        // se recorre la lista de estudiantes y se construye un arreglo para cada elemento de la lista
        // cada par de datos tiene nombre y cantidad de cursos, como indica el header.
        HashSet<Student> students = ManagerHolder.StudentManager.entities;
        for (Student student : students) {
            outputData.add(Formater.format(student, "first"));
        }
        // se ordena en orden alphabetic, compara el elemento 0 de los arreglos de la lista
        outputData.sort(Comparator.comparing(array -> array[0]));
        String[] header = {"Student_Name","Course_Count"};
        outputData.addFirst(header);
        return outputData;
    }

    public List<String[]> secondReport(List<String[]> inputData) {

        for (String[] strings : inputData) {
            DataReceiver.secondDataPoint(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5]);
        }

        HashSet<Evaluation> evaluations = ManagerHolder.EvaluationManager.entities;

        List<String[]> output = new ArrayList<>();
        for (Evaluation evaluation : evaluations) {
            String[] line = Formater.format(evaluation, "second");
            output.add(line);
        }

        output.sort(Comparator.comparing((String[] array) -> array[0])  // Sort by Subject_Name
                .thenComparing(array -> array[1])                       // Then by Evaluation_Name
                .thenComparing(array -> array[2]));                     // Then by Student_Name

        String[] header = {"Subject_Name","Evaluation_Name","Student_Name", "Grade"};
        output.addFirst(header);

        return output;
    }
    public List<String[]> thirdReport(List<String[]> input_3csv) {

        HashSet<Evaluation>  evaluations = ManagerHolder.EvaluationManager.entities;
        for (String[] thirdInputLine : input_3csv) {
            DataReceiver.thirdDataPoint(thirdInputLine);
        }

        List<String[]> outputData = new ArrayList<>();

        for (Evaluation evaluation : evaluations) {
            String[] line = Formater.format(evaluation, "third");
            outputData.add(line);
        }

        String[] header = {"Evaluation_Name","Relevant_Grade","Evaluation_Type","Criteria","Criteria_Value","Passed/Failed"};
        outputData.addFirst(header);

        return outputData;
    }
}

