package com.university.service;

import com.university.model.*;
import com.university.model.Evaluations.*;
import com.university.service.CriteriaAnalyzer.CriteriaSorter;

import static com.university.service.EntityManager.*;

public class DataReceiver {

    private final static EntityManager<Student> StudentManager = ManagerHolder.StudentManager;
    private static final EntityManager<Teacher> TeacherManager = ManagerHolder.TeacherManager;
    private final static EntityManager<Classroom> ClassroomManager = ManagerHolder.ClassroomManager;
    private static final EntityManager<Subject> SubjectManager = ManagerHolder.SubjectManager;
    private final static EntityManager<Evaluation> EvaluationManager = ManagerHolder.EvaluationManager;
    private static final EntityManager<Exercise> ExerciseManager = ManagerHolder.ExerciseManager;

    public static void firstDataPoint(String classroomID,String subjectName, String studentName,
                                      String studentEmail, String subjectTeacher){

        // instance or get model objects

        Teacher teacher = new Teacher(subjectTeacher);
        Student student = new Student(studentName);
        Classroom classroom = new Classroom(Integer.parseInt(classroomID));
        Subject subject = new Subject(subjectName);

        teacher = TeacherManager.createOrFetchEntity(teacher);
        student = StudentManager.createOrFetchEntity(student);
        classroom = ClassroomManager.createOrFetchEntity(classroom);
        subject = SubjectManager.createOrFetchEntity(subject);

/*
        Teacher teacher = createOrFetchTeacher(subjectTeacher);
        Student student = createOrFetchStudent(studentName);
        Classroom classroom = createOrFetchClassroom(classroomID);
        Subject subject = createOrFetchSubject(subjectName);
*/

        // associate possible relationships between objects

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

        Student student = new Student(studentName);
        Subject subject = new Subject(subjectName);
        Evaluation evaluation = switch (evaluationType) {
            case "WRITTEN_EXAM" -> new WrittenExam(evaluationName, subject, student, evaluationType);
            case "ORAL_EXAM" -> new OralExam(evaluationName, subject, student, evaluationType);
            case "FINAL_PRACTICAL_WORK" -> new FinalPracticalWork(evaluationName, subject, student, evaluationType);
            case "PRACTICAL_WORK" -> new PracticalWork(evaluationName, subject, student, evaluationType);
            default -> throw new IllegalStateException("Unexpected value: " + evaluationType);
        };
        Exercise exercise = new Exercise(exerciseName, Double.parseDouble(grade), evaluation);

        student = StudentManager.createOrFetchEntity(student);
        subject = SubjectManager.createOrFetchEntity(subject);
        evaluation = EvaluationManager.createOrFetchEntity(evaluation);
        exercise = ExerciseManager.createOrFetchEntity(exercise);

/*
        Student student = createOrFetchStudent(studentName);
        Subject subject = createOrFetchSubject(subjectName);
        Evaluation evaluation = createOrFetchEvaluation(evaluationName, subjectName, studentName, evaluationType);
        Exercise exercise = createOrFetchExercise(exerciseName, Double.parseDouble(grade), evaluation);
 */

        student.addSubject(subject);

        subject.addStudent(student);

        evaluation.addExercise(exercise);
    }

    public static void thirdDataPoint(String[] thirdInputLine) {
//todo adapt to new structure
        for (Evaluation evaluation : evaluations) {
            if (evaluation.isEvaluated()) {continue;}
            //thirdInputLine; {Subject_Name,Criteria_Type,Criteria_Value,Evaluation_Name_1, Evaluation_Name_2, ...}
            for (int k = 3; k < thirdInputLine.length; k++) { // index 3 to end are evaluation

                String evaluationName = thirdInputLine[k];
                String subjectName = thirdInputLine[0];

                if (evaluationName.equals(evaluation.getName())
                        && subjectName.equals(evaluation.getSubject().getName())) { // evaluation match condition
                    CriteriaSorter.evaluate(evaluation, thirdInputLine[1], thirdInputLine[2]);
                }
            }

        }
    }

}
