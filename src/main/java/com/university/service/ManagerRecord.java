package com.university.service;

import com.university.model.*;
import com.university.model.Evaluations.Evaluation;

import java.util.HashMap;
import java.util.HashSet;

public class ManagerRecord {
    private final HashMap<Integer, Entity> MapIdEntities = new HashMap<>();
    private final HashSet<Entity> globalEntities = new HashSet<>();
    private final EntityManager<Student> StudentManager = new EntityManager<>(Student.class, this);
    private final EntityManager<Teacher> TeacherManager = new EntityManager<>(Teacher.class, this);
    private final EntityManager<Classroom> ClassroomManager = new EntityManager<>(Classroom.class, this);
    private final EntityManager<Subject> SubjectManager = new EntityManager<>(Subject.class, this);
    private final EntityManager<Evaluation> EvaluationManager = new EntityManager<>(Evaluation.class, this);
    private final EntityManager<Exercise> ExerciseManager = new EntityManager<>(Exercise.class, this);

    public HashMap<Integer, Entity> mapIdEntities() {
        return MapIdEntities;
    }
    public HashSet<Entity> globalEntities() {
        return globalEntities;
    }
    public EntityManager<Student> studentManager() {
        return StudentManager;
    }
    public EntityManager<Teacher> teacherManager() {
        return TeacherManager;
    }
    public EntityManager<Classroom> classroomManager() {
        return ClassroomManager;
    }
    public EntityManager<Subject> subjectManager() {
        return SubjectManager;
    }
    public EntityManager<Evaluation> evaluationManager() {
        return EvaluationManager;
    }
    public EntityManager<Exercise> exerciseManager() {
        return ExerciseManager;
    }
}
