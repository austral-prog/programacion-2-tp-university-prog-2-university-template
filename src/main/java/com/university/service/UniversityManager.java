package TpUniversity.service;
import TpUniversity.model.*;
import TpUniversity.service.EvaluationCriteriaManager.AverageAboveValue;
import TpUniversity.service.EvaluationCriteriaManager.EvaluationCriteria;
import TpUniversity.service.EvaluationCriteriaManager.MaxAboveValue;
import TpUniversity.service.EvaluationCriteriaManager.MinAboveValue;

import java.util.*;

public class UniversityManager {

    public List<String[]> firstTaskLogic(List<String[]> inputData) {
        // se crea una lista de students
        ArrayList<Student> students = new ArrayList<>();
        // se recorre el csv y se agregan todos los estudiantes con sus cursos
        for (String[] strings : inputData) {
            String studentName = strings[2];
            String course = strings[1];
            boolean studentFound = false;
            for (Student student : students) {
                if (student.getName().equals(studentName)) {
                    student.addCourse(course);
                    studentFound = true;
                    break;
                }
            }
            if (!studentFound) {
                Student newStudent = new Student(studentName);
                newStudent.addCourse(course);
                students.add(newStudent);
            }
        }

        List<String[]> outputData = new ArrayList<>();

        // se recorre la lista de estudiantes y se construye un arreglo para cada elemento de la lista
        // cada par de datos tiene nombre y cantidad de cursos

        for (Student student : students) {
            String[] line = {student.getName(), String.valueOf(student.getCourseAmount())};
            outputData.add(line);
        }

        // se ordena en orden aflabetico, compara el elemento 0 de los arreglos de la lista
        outputData.sort(Comparator.comparing(array -> array[0]));
        // se agrega el header
        String[] header = {"Student_Name","Course_Count"};
        outputData.addFirst(header);
        return outputData;
    }

    public Box<List<String[]>, List<Evaluation>> secondTaskLogic(List<String[]> inputData) {
        ArrayList<Evaluation> evaluations = new ArrayList<>();
        boolean isFirstLine = true; // flag para ignorar la primer linea (header)
        for (String[] strings : inputData) {
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }
            String studentName = strings[0];
            String subject = strings[1];
            String evaluationType = strings[2];
            String evaluationName = strings[3];
            String exerciseName = strings[4];
            String grade = strings[5];
            boolean evaluationFound = false;
            for (Evaluation evaluation : evaluations) {
                if (evaluation.getName().equals(evaluationName)
                && evaluation.getSubject().equals(subject)
                && evaluation.getStudentName().equals(studentName)
                && evaluation.getEvaluationType().equals(evaluationType)) { // Si encuentra la evaluacion, agrega la nota
                    evaluation.addGrade(Double.parseDouble(grade));
                    evaluation.addExercise(exerciseName);
                    evaluationFound = true;
                    break;
                }
            }
            if (!evaluationFound) {
                Evaluation newEvaluation = new Evaluation(evaluationName, subject, studentName, evaluationType);
                newEvaluation.addGrade(Double.parseDouble(grade));
                newEvaluation.addExercise(exerciseName);
                evaluations.add(newEvaluation);
            }

        }

        evaluations.sort(Comparator.comparing(Evaluation::getName)
                .thenComparing(Evaluation::getEvaluationName)
                .thenComparing(Evaluation::getSubject));

        List<String[]> output = new ArrayList<>();
        for (Evaluation evaluation : evaluations) {
            String[] line = evaluation.getData();
            output.add(line);
        }

// Sort by Subject_Name, then Evaluation_Name, and finally Student_Name
        output.sort(Comparator.comparing((String[] array) -> array[0])  // Sort by Subject_Name
                .thenComparing(array -> array[1])                       // Then by Evaluation_Name
                .thenComparing(array -> array[2]));                     // Then by Student_Name
        // Sort the list for later use
        evaluations.sort(Comparator.comparing(Evaluation::getSubject)
                .thenComparing(Evaluation::getEvaluationName)
                .thenComparing(Evaluation::getStudentName));

        String[] header = {"Subject_Name","Evaluation_Name","Student_Name", "Grade"};
        output.addFirst(header);
        // Box is a class to return two variables
        return new Box<>(output, evaluations);
    }
    public List<String[]> thirdTaskLogic(List<String[]> inputOf3List, Box<List<String[]>, List<Evaluation>> processedData) {

        List<String[]> outputData = new ArrayList<>();                // objective return
        List<Evaluation> evaluationsList = processedData.getSecond(); // retriving variables from Box object
        String[] inputOf3Row;                                         // declaration of array variavbles
        boolean isFirstLine = true;

        Map<String, EvaluationCriteria> criteriaMap = new HashMap<>();
        criteriaMap.put("AVERAGE_ABOVE_VALUE", new AverageAboveValue());
        criteriaMap.put("MAX_ABOVE_VALUE", new MaxAboveValue());
        criteriaMap.put("MIN_ABOVE_VALUE", new MinAboveValue());// flag to ignore the first line

        for (String[] strings : inputOf3List) { // i es el index de el input, con los datos para evaluar

            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }

            inputOf3Row = strings;

            for (Evaluation evaluation : evaluationsList) { // j es el index de las evaluaciones
                if (evaluation.isEvaluated()) {
                    continue;
                }
                for (int k = 3; k < inputOf3Row.length; k++) { // k es el index de los tipos de evaluaciones

                    //items in array; Subject_Name,Criteria_Type,Criteria_Value,Evaluation_Name

                    if (inputOf3Row[k].equals(evaluation.getEvaluationName())) { // found evaluation

                        String criteriaType = inputOf3Row[1];
                        String criteriaValue = inputOf3Row[2];

                        EvaluationCriteria criteria = criteriaMap.get(criteriaType);

                        criteria.apply(evaluation, Double.parseDouble(criteriaValue), criteriaType);
                    }
                }

            }
        }
        for (Evaluation evaluation : evaluationsList) {
            String[] line = evaluation.getAltData();
            outputData.add(line);
        }
        // Sort by Subject_Name, then Evaluation_Name, and finally Student_Name
        outputData.sort(Comparator.comparing((String[] array) -> array[0])  // Sort by Subject_Name
                .thenComparing(array -> array[1])                           // Then by Evaluation_Name
                .thenComparing(array -> array[2]));                         // Then by Student_Name

        String[] header = {"Subject_Name","Evaluation_Name","Student_Name","Evaluation_Type","Grade","Criteria","Criteria_Value","Passed","Min","Max"};
        outputData.addFirst(header);
        return outputData;
    }
    void AVERAGE_ABOVE_VALUE(Evaluation evaluation, double value) {
        evaluation.setEvaluated(true);
        evaluation.setCriteria("AVERAGE_ABOVE_VALUE");
        if (evaluation.getAverage() > value) {
            evaluation.setPassed(true);
        }
    }
    void MAX_ABOVE_VALUE(Evaluation evaluation, double value) {
        evaluation.setEvaluated(true);
        evaluation.setCriteria("MAX_ABOVE_VALUE");
        if (evaluation.getMax() > value) {
            evaluation.setPassed(true);
        }
    }
    void MIN_ABOVE_VALUE(Evaluation evaluation, double value) {
        evaluation.setEvaluated(true);
        evaluation.setCriteria("MIN_ABOVE_VALUE");
        if (evaluation.getMin() > value) {
            evaluation.setPassed(true);
        }
    }
}
