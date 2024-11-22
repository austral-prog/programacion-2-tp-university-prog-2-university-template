package com.university.service.Logic;

import com.university.inOut.IncompatibleEntity;
import com.university.model.*;
import com.university.model.Evaluations.*;
import com.university.service.CriteriaAnalyzer.CriteriaSorter;
import com.university.service.EntityManager;
import com.university.service.ManagerRecord;

import java.util.HashMap;
import java.util.HashSet;


public class DataMapper {

    public static void firstDataPoint(String[] firstInputLine, ManagerRecord ManagerRecord) {

        String classroomID = firstInputLine[0];
        String subjectName = firstInputLine[1];
        String studentName = firstInputLine[2];
        String studentEmail = firstInputLine[3];
        String subjectTeacher = firstInputLine[4];

        // instance or get model objects

        Teacher teacher = new Teacher(subjectTeacher);
        Student student = new Student(studentName);
        Classroom classroom = new Classroom(Integer.parseInt(classroomID));
        Subject subject = new Subject(subjectName);

        teacher = ManagerRecord.teacherManager().createOrFetchEntity(teacher);
        student = ManagerRecord.studentManager().createOrFetchEntity(student);
        classroom = ManagerRecord.classroomManager().createOrFetchEntity(classroom);
        subject = ManagerRecord.subjectManager().createOrFetchEntity(subject);

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

    public static void secondDataPoint(String[] secondInputLine, ManagerRecord ManagerRecord) {

        String studentName = secondInputLine[0];
        String subjectName = secondInputLine[1];
        String evaluationType = secondInputLine[2];
        String evaluationName = secondInputLine[3];
        String exerciseName = secondInputLine[4];
        String grade = secondInputLine[5];

        Student student = new Student(studentName);
        Subject subject = new Subject(subjectName);
        Evaluation evaluation = sortConstructor(evaluationType, evaluationName, student, subject);
        Exercise exercise = new Exercise(exerciseName, Double.parseDouble(grade), evaluation);

        student = ManagerRecord.studentManager().createOrFetchEntity(student);
        subject = ManagerRecord.subjectManager().createOrFetchEntity(subject);
        evaluation = ManagerRecord.evaluationManager().createOrFetchEntity(evaluation);
        exercise = ManagerRecord.exerciseManager().createOrFetchEntity(exercise);

        student.addSubject(subject);

        subject.addStudent(student);

        evaluation.addExercise(exercise);

    }

    public static void thirdDataPoint(String[] thirdInputLine, ManagerRecord ManagerRecord) {

        HashSet<Evaluation> evaluations = ManagerRecord.evaluationManager().entities;
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

    public static Exercise rawExercise(String exerciseName, double grade, int evaluationID, ManagerRecord ManagerRecord) {

        HashMap<Integer, Entity> MapIdEntities = ManagerRecord.mapIdEntities();
        EntityManager<Exercise> ExerciseManager = ManagerRecord.exerciseManager();

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

    public static Evaluation rawEvaluation(String evaluationName, int subjectID, int studentID, String evaluationType, ManagerRecord ManagerRecord) {

        HashMap<Integer, Entity> MapIdEntities = ManagerRecord.mapIdEntities();
        EntityManager<Evaluation> EvaluationManager = ManagerRecord.evaluationManager();

        // Retrieve Subject and Student by ID using MapIdEntities
        if (MapIdEntities.get(subjectID) instanceof Subject subject && MapIdEntities.get(studentID) instanceof Student student) {
            Evaluation evaluation = sortConstructor(evaluationType, evaluationName, student, subject);
            return EvaluationManager.createOrFetchEntity(evaluation);
        } else {
            throw new IncompatibleEntity("Subject or Student ID is not valid");
        }
    }

    public static Subject rawSubject(String subjectName, ManagerRecord ManagerRecord) {
        EntityManager<Subject> SubjectManager = ManagerRecord.subjectManager();
        Subject subject = new Subject(subjectName);
        return SubjectManager.createOrFetchEntity(subject);
    }

    public static Teacher rawTeacher(String teacherName, ManagerRecord ManagerRecord) {
        EntityManager<Teacher> TeacherManager = ManagerRecord.teacherManager();
        Teacher teacher = new Teacher(teacherName);
        return TeacherManager.createOrFetchEntity(teacher);
    }

    public static Student rawStudent(String studentName, String studentEmail, ManagerRecord ManagerRecord) {
        EntityManager<Student> StudentManager = ManagerRecord.studentManager();
        Student student = new Student(studentName);
        student.setEmail(studentEmail);
        student = StudentManager.createOrFetchEntity(student);
        return student;
    }

    public static Classroom rawClassroom(int classroomID, ManagerRecord ManagerRecord) {
        EntityManager<Classroom> ClassroomManager = ManagerRecord.classroomManager();
        Classroom classroom = new Classroom(classroomID);
        ClassroomManager.createOrFetchEntity(classroom);
        return classroom;
    }

    private static Evaluation sortConstructor(String evaluationType, String evaluationName, Student student, Subject subject) {
        return switch (evaluationType) {
            case "WRITTEN_EXAM" -> new WrittenExam(evaluationName, subject, student, evaluationType);
            case "ORAL_EXAM" -> new OralExam(evaluationName, subject, student, evaluationType);
            case "FINAL_PRACTICAL_WORK" -> new FinalPracticalWork(evaluationName, subject, student, evaluationType);
            case "PRACTICAL_WORK" -> new PracticalWork(evaluationName, subject, student, evaluationType);
            default -> throw new IllegalStateException("Unexpected value: " + evaluationType);
        };
    }
}
