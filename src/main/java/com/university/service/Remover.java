package com.university.service;

import com.university.model.*;
import com.university.model.Evaluations.Evaluation;

import java.util.HashSet;

import static com.university.service.EntityManager.MapIdEntities;

public class Remover {

    public static boolean disconnection(Entity entity, EntityManager<?> manager) {
        // Check object type of manager
        // Check if entity is a type of object in manager
        // Check if entity is in the manager
        // Cast entity to the object type of manager
        // Divert to respective functions.

        if (manager.entities.contains(entity)) {

            if (entity instanceof Student) {

                    removeStudent((Student) entity);
                    return true;
                }
            } else if (entity instanceof Evaluation) {

                    removeEvaluation((Evaluation) entity);
                    return true;

            } else if (entity instanceof Exercise) {

                    removeExercise((Exercise) entity);
                    return true;

            } else if (entity instanceof Subject) {

                    removeSubject((Subject) entity);
                    return true;

            } else if (entity instanceof Teacher) {

                    removeTeacher((Teacher) entity);
                    return true;

            } else if (entity instanceof Classroom) {

                    removeClassroom((Classroom) entity);
                    return true;

            }
            return false;
        }



    static private void removeClassroom(Classroom classroom) {

    HashSet<Classroom> classes = ManagerHolder.ClassroomManager.entities;

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

    static private void removeTeacher(Teacher teacher) {

    HashSet<Teacher> teachers = ManagerHolder.TeacherManager.entities;

    for (Classroom classroom : teacher.getClassrooms()) {
        classroom.getTeachers().remove(teacher);
    }

    for (Subject subject : teacher.getSubjects()) {
        subject.getTeachers().remove(teacher);
    }

    teachers.remove(teacher);
    MapIdEntities.remove(teacher.getId());
    }

    static private void removeSubject(Subject subject) {

    HashSet<Subject> subjects = ManagerHolder.SubjectManager.entities;

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

    static private void removeExercise(Exercise exercise) {

    HashSet<Exercise> exercises = ManagerHolder.ExerciseManager.entities;

    exercise.getEvaluation().getExercises().remove(exercise);
    MapIdEntities.remove(exercise.getId());
    exercises.remove(exercise);
    }

    static private void removeEvaluation(Evaluation evaluation) {

    HashSet<Evaluation> evaluations = ManagerHolder.EvaluationManager.entities;

    for (Exercise exercise : evaluation.getExercises()) {
        removeExercise(exercise);
    }
    evaluation.getExercises().clear();
    evaluation.getSubject().getStudents().remove(evaluation.getStudent());
    evaluations.remove(evaluation);
    MapIdEntities.remove(evaluation.getId());
    }

    static private void removeStudent(Student student) {

        HashSet<Student> students = ManagerHolder.StudentManager.entities;
        HashSet<Evaluation> evaluations = ManagerHolder.EvaluationManager.entities;
        HashSet<Classroom> classes = ManagerHolder.ClassroomManager.entities;
        HashSet<Subject> subjects = ManagerHolder.SubjectManager.entities;
        HashSet<Teacher> teachers = ManagerHolder.TeacherManager.entities;

        for (Evaluation evaluation : evaluations) {
            if (evaluation.getStudent().equals(student)) {
                removeEvaluation(evaluation);
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
