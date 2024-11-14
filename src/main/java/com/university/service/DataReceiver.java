package com.university.service;

import com.university.inOut.IncompatibleEntity;
import com.university.model.*;
import com.university.model.Evaluations.*;
import com.university.service.CriteriaAnalyzer.CriteriaSorter;

import java.util.HashSet;

import static com.university.service.EntityManager.MapIdEntities;

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
        Evaluation evaluation = sortConstructor(evaluationType, evaluationName, student, subject);
        Exercise exercise = new Exercise(exerciseName, Double.parseDouble(grade), evaluation);

        student = StudentManager.createOrFetchEntity(student);
        subject = SubjectManager.createOrFetchEntity(subject);
        evaluation = EvaluationManager.createOrFetchEntity(evaluation);
        exercise = ExerciseManager.createOrFetchEntity(exercise);

        student.addSubject(subject);

        subject.addStudent(student);

        evaluation.addExercise(exercise);
    }

    public static void thirdDataPoint(String[] thirdInputLine) {

        HashSet<Evaluation> evaluations = EvaluationManager.entities;
        for (Evaluation evaluation : evaluations) {
            if (evaluation.isEvaluated()) {continue;}
            //thirdInputLine; {Subject_Name,Criteria_Type,Criteria_Value,Evaluation_Name_1, Evaluation_Name_2, ...}
            for (int k = 3; k < thirdInputLine.length; k++) { // index 3 to end are evaluation

                String evaluationName = thirdInputLine[k];
                String subjectName = thirdInputLine[0];

                if (evaluationName.equals(evaluation.name())
                        && subjectName.equals(evaluation.getSubject().name())) { // evaluation match condition
                    CriteriaSorter.evaluate(evaluation, thirdInputLine[1], thirdInputLine[2]);
                }
            }

        }
    }

    public static Exercise rawExercise(String exerciseName, double grade, int evaluationID){

        Evaluation evaluation;
        if (MapIdEntities.get(evaluationID) instanceof Evaluation) {
            evaluation = (Evaluation) MapIdEntities.get(evaluationID);
        } else {
            throw new IncompatibleEntity(MapIdEntities.get(evaluationID) + "is not an instance of Evaluation");
        }
        if (evaluation != null) {
            Exercise exercise = new Exercise(exerciseName, grade, evaluation);
            return ExerciseManager.createOrFetchEntity(exercise);
        } else {
            throw new IncompatibleEntity("Evaluation ID is not valid");
        }
    }

    public static Evaluation rawEvaluation(String evaluationName, int subjectID, int studentID, String evaluationType) {
        // Retrieve Subject and Student by ID using MapIdEntities
        if (MapIdEntities.get(subjectID) instanceof Subject subject && MapIdEntities.get(studentID) instanceof Student student) {
            Evaluation evaluation = sortConstructor(evaluationType, evaluationName, student, subject);
            return EvaluationManager.createOrFetchEntity(evaluation);
        } else {
            throw new IncompatibleEntity("Subject or Student ID is not valid");
        }
    }

    public static Subject rawSubject(String subjectName) {
        Subject subject = new Subject(subjectName);
        return SubjectManager.createOrFetchEntity(subject);
    }

    public static Teacher rawTeacher(String teacherName) {
        Teacher teacher = new Teacher(teacherName);
        return TeacherManager.createOrFetchEntity(teacher);
    }

    public static Student rawStudent(String studentName, String studentEmail) {
        Student student = new Student(studentName);
        student.setEmail(studentEmail);
        student = StudentManager.createOrFetchEntity(student);
        return student;
    }

    public static Classroom rawClassroom(int classroomID) {
        Classroom classroom = new Classroom(classroomID);
        ClassroomManager.createOrFetchEntity(classroom);
        return classroom;
    }

    private static Evaluation sortConstructor(String evaluationType, String evaluationName, Student student, Subject subject) {
        Evaluation evaluation = switch (evaluationType) {
            case "WRITTEN_EXAM" -> new WrittenExam(evaluationName, subject, student, evaluationType);
            case "ORAL_EXAM" -> new OralExam(evaluationName, subject, student, evaluationType);
            case "FINAL_PRACTICAL_WORK" -> new FinalPracticalWork(evaluationName, subject, student, evaluationType);
            case "PRACTICAL_WORK" -> new PracticalWork(evaluationName, subject, student, evaluationType);
            default -> throw new IllegalStateException("Unexpected value: " + evaluationType);
        };
        return evaluation;
    }
}
