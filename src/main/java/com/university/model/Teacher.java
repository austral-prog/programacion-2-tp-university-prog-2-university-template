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

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public boolean equals(Object obj) {
        if (obj instanceof Teacher teacher) {
            return this.name.equals(teacher.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String classString() {
        return "Teacher";
    }

}