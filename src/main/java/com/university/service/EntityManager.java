package com.university.service;

import com.university.CRUDRepository;
import com.university.model.*;
import com.university.model.Evaluations.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

// This class is responsible for managing the entities of the system,
// it provides methods to create new instances of these entities, ensure they are unique,
// and store them in collections for easy access. Each entity has a unique ID.


public class EntityManager<E extends Entity> implements CRUDRepository<Entity> {

    public static Map<Integer, Entity> globalEntities = new HashMap<>();

    private HashSet<E> entities = new HashSet<>();

    public static ArrayList<Student> students = new ArrayList<>();
    public static ArrayList<Evaluation> evaluations = new ArrayList<>();
    public static ArrayList<Exercise> exercises = new ArrayList<>();
    public static ArrayList<Subject> subjects = new ArrayList<>();
    public static ArrayList<Teacher> teachers = new ArrayList<>();
    public static ArrayList<Classroom> classes = new ArrayList<>();

    // Registers an entity in the entities map
    public static void registerEntity(Entity entity) {
        globalEntities.put(entity.getId(), entity);
    }

    // generates a new unique ID for an entity
    public static int newId() {
        return globalEntities.size() + 1;
    }

    // Create or fetch methods



    public static Student createOrFetchStudent(String studentName) {

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

    // Create or fetch methods

    public static Evaluation createOrFetchEvaluation(String evaluationName, String subjectName, String studentName, String evaluationType) {

        for (Evaluation evaluation : evaluations) {
            if (evaluation.getName().equals(evaluationName) && evaluation.getSubject().getName().equals(subjectName) && evaluation.studentName().equals(studentName)) {
                return evaluation;
            }
        }

        Subject subject = createOrFetchSubject(subjectName);
        Student student = createOrFetchStudent(studentName);

        Evaluation newEvaluation = switch (evaluationType) {
            case "WRITTEN_EXAM" -> new WrittenExam(evaluationName, subject, student, evaluationType);
            case "PRACTICAL_WORK" ->  new PracticalWork(evaluationName, subject, student, evaluationType);
            case "FINAL_PRACTICAL_WORK" -> new FinalPracticalWork(evaluationName, subject, student, evaluationType);
            case "ORAL_EXAM" -> new OralExam(evaluationName, subject, student, evaluationType);
            default -> throw new IllegalStateException("Unexpected value: " + evaluationType);
        };

        evaluations.add(newEvaluation);
        newEvaluation.setId(newId());
        registerEntity(newEvaluation);
        return newEvaluation;
    }

    public static Exercise createOrFetchExercise(String name, double grade, Evaluation evaluation) {

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

    public static Subject createOrFetchSubject(String subjectName) {

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

    public static Teacher createOrFetchTeacher(String teacherName) {

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

    public static Classroom createOrFetchClassroom(String classroomId) {

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

    // Remove methods

    private void removeClassroom(Classroom classroom) {
        for (Teacher teacher : classroom.getTeachers()) {
            teacher.getClassrooms().remove(classroom);
        }
        for (Subject subject : classroom.getSubjects()) {
            subject.getClassrooms().remove(classroom);
        }
        classroom.getStudents().clear();
        classes.remove(classroom);
        globalEntities.remove(classroom.getId());
    }

    private void removeTeacher(Teacher teacher) {
        for (Classroom classroom : teacher.getClassrooms()) {
            classroom.getTeachers().remove(teacher);
        }

        for (Subject subject : teacher.getSubjects()) {
            subject.getTeachers().remove(teacher);
        }

        teachers.remove(teacher);
        globalEntities.remove(teacher.getId());
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
        globalEntities.remove(subject.getId());
    }
// todo deleters
    private void removeExercise(Exercise exercise) {
        exercise.getEvaluation().getExercises().remove(exercise);
        globalEntities.remove(exercise.getId());
        exercises.remove(exercise);
    }

    private void removeEvaluation(Evaluation evaluation) {
        for (Exercise exercise : evaluation.getExercises()) {
            removeExercise(exercise);
        }
        evaluation.getExercises();
        evaluation.getSubject().getStudents().remove(evaluation.getStudent());
        evaluations.remove(evaluation);
        globalEntities.remove(evaluation.getId());
    }

    private void removeStudent(Student student) {
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
        globalEntities.remove(student.getId());
    }

    @Override
    public void create(Entity entity) {
        registerEntity(entity);
    }

    @Override
    public Entity read(int id) {
        return globalEntities.get(id);
    }

    @Override
    public void update(int id, Entity entity) {
        entity.setId(id);
        globalEntities.put(id, entity);
    }

    @Override
    public void delete(int id) {
        Entity entity = globalEntities.get(id);
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
    public Class<Entity> getEntityClass() {
        return Entity.class;
    }
}
