package TpUniversity.service;
import TpUniversity.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public List<String[]> secondTaskLogic(List<String[]> inputData) {
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

        List<String[]> output = new ArrayList<>();
        for (Evaluation evaluation : evaluations) {
            String[] line = evaluation.getData();
            output.add(line);
        }

        // Sort by Student_Name, then Evaluation_Name, and finally Subject_Name
        output.sort(Comparator.comparing((String[] array) -> array[2])  // Sort by Student_Name
                .thenComparing(array -> array[1])                       // Then by Evaluation_Name
                .thenComparing(array -> array[0]));                     // Then by Subject_Name


        String[] header = {"Subject_Name","Evaluation_Name","Student_Name", "Grade"};
        output.addFirst(header);

        return output;
    }
    public List<String[]> thirdTaskLogic(List<String[]> inputData) {
        List<String[]> outputData = new ArrayList<>(inputData);
        return outputData;
    }
}
