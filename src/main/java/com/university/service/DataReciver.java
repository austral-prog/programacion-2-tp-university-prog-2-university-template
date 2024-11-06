package com.university.service;

import com.university.model.*;
import com.university.model.Evaluations.Evaluation;
import com.university.service.CriteriaAnalyzer.CriteriaSorter;

import static com.university.service.EntityManager.*;

public class DataReciver {

    public static void firstDataPoint(String classroomID,String subjectName, String studentName,
                                      String studentEmail, String subjectTeacher){
        // instance or get model objects

        Teacher teacher = createOrFetchTeacher(subjectTeacher);
        Student student = createOrFetchStudent(studentName);
        Classroom classroom = createOrFetchClassroom(classroomID);
        Subject subject = createOrFetchSubject(subjectName);

        // associate possible links between objects

        teacher.addClassroom(classroom);
        teacher.addSubject(subject);
        teacher.addStudent(student);

        student.addSubject(subject);
        student.setEmail(studentEmail);

        classroom.addTeacher(teacher);
        classroom.addStudent(student);
        classroom.addSubject(subject);

        subject.addTeacher(teacher);
        subject.addStudent(student);
        subject.addClassroom(classroom);

    }

    public static void secondDataPoint(String studentName, String subjectName, String evaluationType,
                                       String evaluationName, String exerciseName, String grade){

        Student student = createOrFetchStudent(studentName);
        Subject subject = createOrFetchSubject(subjectName);
        Evaluation evaluation = createOrFetchEvaluation(evaluationName, subjectName, studentName, evaluationType);
        Exercise exercise = createOrFetchExercise(exerciseName, Double.parseDouble(grade), evaluation);

        student.addSubject(subject);

        subject.addStudent(student);

        evaluation.addExercise(exercise);
    }

    public static void thirdDataPoint(String[] thirdInputLine) {

        for (Evaluation evaluation : evaluations) {
            if (evaluation.isEvaluated()) {continue;}
            //thirdInputLine; {Subject_Name,Criteria_Type,Criteria_Value,Evaluation_Name_1, Evaluation_Name_2, ...}
            for (int k = 3; k < thirdInputLine.length; k++) { // index 3 to end are evaluation

                String evaluationName = thirdInputLine[k];
                String subjectName = thirdInputLine[0];

                if (evaluationName.equals(evaluation.getName())
                        && subjectName.equals(evaluation.getSubject())) { // evaluation match condition
                    CriteriaSorter.evaluate(evaluation, thirdInputLine[1], thirdInputLine[2]);
                }
            }

        }
    }

}
