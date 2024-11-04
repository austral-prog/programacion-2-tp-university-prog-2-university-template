package com.university.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Entity  {
    // Classroom,Subject,Student_Name,Student_Email,Subject_Teacher
    String name;
    List<Subject> subjects;
    List<Student> students;
    List<Classroom> classrooms;

    public Teacher(String name) {
        this.name = name;
        this.subjects = new ArrayList<>();
        this.students = new ArrayList<>();
        this.classrooms = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addClassroom(Classroom classroom) {
        classrooms.add(classroom);
    }

    @Override
    public String classString() {
        return "Teacher";
    }
}