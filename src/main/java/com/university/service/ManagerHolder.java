package com.university.service;

import com.university.model.*;
import com.university.model.Evaluations.Evaluation;

public class ManagerHolder {
    public static EntityManager<Student> StudentManager = new EntityManager<>(Student.class);
    public static EntityManager<Teacher> TeacherManager = new EntityManager<>(Teacher.class);
    public static EntityManager<Classroom> ClassroomManager = new EntityManager<>(Classroom.class);
    public static EntityManager<Subject> SubjectManager = new EntityManager<>(Subject.class);
    public static EntityManager<Evaluation> EvaluationManager = new EntityManager<>(Evaluation.class);
    public static EntityManager<Exercise> ExerciseManager = new EntityManager<>(Exercise.class);
}
