package com.university.service;

import com.university.model.*;
import com.university.model.Evaluations.Evaluation;

import java.util.HashMap;
import java.util.HashSet;


public class Remover {

    public static boolean disconnection(Entity entity, EntityManager<?> manager, ManagerRecord ManagerRecord) {
        // Check object type of manager
        // Check if entity is a type of object in manager
        // Check if entity is in the manager
        // Cast entity to the object type of manager
        // Divert to respective functions.

        if (manager.entities.contains(entity)) {

            if (entity instanceof Student) {

                    removeStudent((Student) entity, ManagerRecord);
                    return true;
                }
            } else if (entity instanceof Evaluation) {

                    removeEvaluation((Evaluation) entity, ManagerRecord);
                    return true;

            } else if (entity instanceof Exercise) {

                    removeExercise((Exercise) entity, ManagerRecord);
                    return true;

            } else if (entity instanceof Subject) {

                    removeSubject((Subject) entity, ManagerRecord);
                    return true;

            } else if (entity instanceof Teacher) {

                    removeTeacher((Teacher) entity, ManagerRecord);
                    return true;

            } else if (entity instanceof Classroom) {

                    removeClassroom((Classroom) entity, ManagerRecord);
                    return true;

            }
            return false;
        }



    static private void removeClassroom(Classroom classroom, ManagerRecord ManagerRecord) {

    HashSet<Classroom> classes = ManagerRecord.classroomManager().entities;
    HashMap<Integer, Entity> MapIdEntities = ManagerRecord.mapIdEntities();


    for (Teacher teacher : classroom.getTeachers()) {
        teacher.getClassrooms().remove(classroom);
        }
    for (Subject subject : classroom.getSubjects()) {
        subject.getClassrooms().remove(classroom);
        }
    classroom.getStudents().clear();
    classes.remove(classroom);
    MapIdEntities.remove(classroom.getId());
    }

    static private void removeTeacher(Teacher teacher, ManagerRecord ManagerRecord) {

    HashSet<Teacher> teachers = ManagerRecord.teacherManager().entities;
    HashMap<Integer, Entity> MapIdEntities = ManagerRecord.mapIdEntities();

    for (Classroom classroom : teacher.getClassrooms()) {
        classroom.getTeachers().remove(teacher);
    }

    for (Subject subject : teacher.getSubjects()) {
        subject.getTeachers().remove(teacher);
    }

    teachers.remove(teacher);
    MapIdEntities.remove(teacher.getId());
    }

    static private void removeSubject(Subject subject, ManagerRecord ManagerRecord) {

    HashSet<Subject> subjects = ManagerRecord.subjectManager().entities;
    HashMap<Integer, Entity> MapIdEntities = ManagerRecord.mapIdEntities();

    for (Teacher teacher : subject.getTeachers()) {
        teacher.getSubjects().remove(subject);
    }

    for (Classroom classroom : subject.getClassrooms()) {
        classroom.getSubjects().remove(subject);
    }

    subject.getStudents().clear();
    subjects.remove(subject);
    MapIdEntities.remove(subject.getId());
    }

    static private void removeExercise(Exercise exercise, ManagerRecord ManagerRecord) {

    HashSet<Exercise> exercises = ManagerRecord.exerciseManager().entities;
    HashMap<Integer, Entity> MapIdEntities = ManagerRecord.mapIdEntities();

    exercise.getEvaluation().getExercises().remove(exercise);
    MapIdEntities.remove(exercise.getId());
    exercises.remove(exercise);
    }

    static private void removeEvaluation(Evaluation evaluation, ManagerRecord ManagerRecord) {

    HashSet<Evaluation> evaluations = ManagerRecord.evaluationManager().entities;
    HashMap<Integer, Entity> MapIdEntities = ManagerRecord.mapIdEntities();

    for (Exercise exercise : evaluation.getExercises()) {
        removeExercise(exercise, ManagerRecord);
    }
    evaluation.getExercises().clear();
    evaluation.getSubject().getStudents().remove(evaluation.getStudent());
    evaluations.remove(evaluation);
    MapIdEntities.remove(evaluation.getId());
    }

    static private void removeStudent(Student student, ManagerRecord ManagerRecord) {

        HashSet<Student> students = ManagerRecord.studentManager().entities;
        HashSet<Evaluation> evaluations = ManagerRecord.evaluationManager().entities;
        HashSet<Classroom> classes = ManagerRecord.classroomManager().entities;
        HashSet<Subject> subjects = ManagerRecord.subjectManager().entities;
        HashSet<Teacher> teachers = ManagerRecord.teacherManager().entities;

        HashMap<Integer, Entity> MapIdEntities = ManagerRecord.mapIdEntities();

        for (Evaluation evaluation : evaluations) {
            if (evaluation.getStudent().equals(student)) {
                removeEvaluation(evaluation, ManagerRecord);
            }
        }

        for (Classroom classroom : classes) {
            classroom.getStudents().remove(student);
        }

        for (Subject subject : subjects) {
            subject.getStudents().remove(student);
        }

        for (Teacher teacher : teachers) {
            teacher.getStudents().remove(student);
        }

        students.remove(student);
        MapIdEntities.remove(student.getId());
    }
}
