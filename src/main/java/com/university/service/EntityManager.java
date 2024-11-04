package com.university.service;

import com.university.CRUDRepository;
import com.university.model.*;
import com.university.model.Evaluations.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// This class is responsible for managing the entities of the system,
// it provides methods to create new instances of these entities, ensure they are unique,
// and store them in collections for easy access. Each entity has a unique ID.


public class EntityManager implements CRUDRepository<Entity> {

    public static Map<Integer, Entity> entities = new HashMap<>();

    public static ArrayList<Student> students = new ArrayList<>();
    public static ArrayList<Evaluation> evaluations = new ArrayList<>();
    public static ArrayList<Exercise> exercises = new ArrayList<>();
    public static ArrayList<Subject> subjects = new ArrayList<>();
    public static ArrayList<Teacher> teachers = new ArrayList<>();
    public static ArrayList<Classroom> classes = new ArrayList<>();

    // Alternative ID method (mas prolijo):

    // private int newId() {
    //     return entities.size() + 1;
    // }

    // Map<Int; uniqueID, Entity; Entity>
    public static void registerEntity(Entity entity) {
        entities.put(entity.getId(), entity);
    }

    // generates a unique ID for each entity
    public static int newId() {
        int newId = (int) (Math.random() * 100000000);
        while (entities.containsKey(newId)) {
            newId = (int) (Math.random() * 100000000);
        }
        return newId;
    }

    public static Student newStudent(String studentName) {

        for (Student student : students) {
            if (student.getName().equals(studentName)) {
                return student;
            }
        }

        Student newStudent = new Student(studentName);
        students.add(newStudent);
        newStudent.setId(newId());
        registerEntity(newStudent);
        return newStudent;

    }

    public static Evaluation newEvaluation(String evaluationName, String subjectName, String studentName, String evaluationType) {

        for (Evaluation evaluation : evaluations) {
            if (evaluation.getName().equals(evaluationName) && evaluation.getSubject().equals(subjectName) && evaluation.getStudentName().equals(studentName)) {
                return evaluation;
            }
        }

        Subject subject = null;
        boolean subjectFound = false;

        for (Subject searchSubject : subjects) {
            if (searchSubject.getName().equals(subjectName)) {
                subject = searchSubject;
                subjectFound = true;
                break;
            }
        }

        if(!subjectFound) {
            subject = newSubject(subjectName);
        }

        Evaluation newEvaluation = switch (evaluationType) {
            case "WRITTEN_EXAM" -> new WrittenExam(evaluationName, subject, studentName);
            case "PRACTICAL_WORK" ->  new PracticalWork(evaluationName, subject, studentName);
            case "FINAL_PRACTICAL_WORK" -> new FinalPracticalWork(evaluationName, subject, studentName);
            case "ORAL_EXAM" -> new OralExam(evaluationName, subject, studentName);
            default -> throw new IllegalStateException("Unexpected value: " + evaluationType);
        };

        evaluations.add(newEvaluation);
        newEvaluation.setId(newId());
        registerEntity(newEvaluation);
        return newEvaluation;
    }

    public static Exercise newExercise(String name, double grade, Evaluation evaluation) {

        for (Exercise exercise : exercises) {
            if (exercise.getName().equals(name) && exercise.getGrade() == grade && exercise.getEvaluation().equals(evaluation)) {
                return exercise;
            }
        }

        Exercise newExercise = new Exercise(name, grade, evaluation);
        exercises.add(newExercise);
        newExercise.setId(newId());
        registerEntity(newExercise);
        return newExercise;
    }

    public static Subject newSubject(String subjectName) {

        for (Subject subject : subjects) {
            if (subject.getName().equals(subjectName)) {
                return subject;
            }
        }

        Subject newSubject = new Subject(subjectName);
        subjects.add(newSubject);
        newSubject.setId(newId());
        registerEntity(newSubject);
        return newSubject;
    }

    public static Teacher newTeacher(String teacherName) {

        for (Teacher teacher : teachers) {
            if (teacher.getName().equals(teacherName)) {
                return teacher;
            }
        }

        Teacher newTeacher = new Teacher(teacherName);
        teachers.add(newTeacher);
        newTeacher.setId(newId());
        registerEntity(newTeacher);
        return newTeacher;
    }

    public static Classroom newClassroom(String classroomId) {

        for (Classroom classroom : classes) {
            if (classroom.getClassroomId() == Integer.parseInt(classroomId)) {
                return classroom;
            }
        }

        Classroom newClassroom = new Classroom(Integer.parseInt(classroomId));
        classes.add(newClassroom);
        newClassroom.setId(newId());
        registerEntity(newClassroom);
        return newClassroom;
    }

    private void removeClassroom(Classroom classroom) {
        for (Teacher teacher : classroom.getTeachers()) {
            teacher.getClassrooms().remove(classroom);
        }
        for (Subject subject : classroom.getSubjects()) {
            subject.getClassrooms().remove(classroom);
        }
        classroom.getStudents().clear();
        classes.remove(classroom);
        entities.remove(classroom.getId());
    }

    private void removeTeacher(Teacher teacher) {
        for (Classroom classroom : teacher.getClassrooms()) {
            classroom.getTeachers().remove(teacher);
        }

        for (Subject subject : teacher.getSubjects()) {
            subject.getTeachers().remove(teacher);
        }

        teachers.remove(teacher);
        entities.remove(teacher.getId());
    }

    private void removeSubject(Subject subject) {

        for (Teacher teacher : subject.getTeachers()) {
            teacher.getSubjects().remove(subject);
        }

        for (Classroom classroom : subject.getClassrooms()) {
            classroom.getSubjects().remove(subject);
        }

        subject.getStudents().clear();
        subjects.remove(subject);
        entities.remove(subject.getId());
    }

    private void removeExercise(Exercise exercise) {

    }

    private void removeEvaluation(Evaluation evaluation) {

    }

    private void removeStudent(Student student) {
    }

    @Override
    public void create(Entity entity) {
        registerEntity(entity);
    }

    @Override
    public Entity read(int id) {
        return entities.get(id);
    }

    @Override
    public void update(int id, Entity entity) {
        entity.setId(id);
        entities.put(id, entity);
    }
    //todo delete method

    @Override
    public void delete(int id) {
        Entity entity = entities.get(id);
        switch (entity.classString()) {
            case "Student" ->
                    removeStudent((Student) entity);
            case "Evaluation" ->
                    removeEvaluation((Evaluation) entity);
            case "Exercise" ->
                    removeExercise((Exercise) entity);
            case "Subject" ->
                    removeSubject((Subject) entity);
            case "Teacher" ->
                    removeTeacher((Teacher) entity);
            case "Classroom" ->
                    removeClassroom((Classroom) entity);
        }

    }

    @Override
    public String getIdentifier() {
        return "";
    }

    @Override
    public Class getEntityClass() {
        return Entity.class;
    }
}
