package com.university.service;

import com.university.model.*;
import com.university.model.Evaluations.Evaluation;

public class ManagerHolder {
    public static EntityManager<Student> StudentManager = new EntityManager<>();
    public static EntityManager<Teacher> TeacherManager = new EntityManager<>();
    public static EntityManager<Classroom> ClassroomManager = new EntityManager<>();
    public static EntityManager<Subject> SubjectManager = new EntityManager<>();
    public static EntityManager<Evaluation> EvaluationManager = new EntityManager<>();
    public static EntityManager<Exercise> ExerciseManager = new EntityManager<>();
}
