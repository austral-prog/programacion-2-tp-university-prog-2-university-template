package com.university.model;

import java.util.ArrayList;
import java.util.List;

public class Classroom extends Entity {
    // Classroom,Subject,Student_Name,Student_Email,Subject_Teacher
    int ClassroomId;
    List<Subject> Subjects;
    List<Student> Students;
    List<Teacher> Teachers;

    public Classroom(int ClassroomId) {
        this.ClassroomId = ClassroomId;
        Subjects = new ArrayList<>();
        Students = new ArrayList<>();
        Teachers = new ArrayList<>();
    }

    public int getClassroomId() {
        return ClassroomId;
    }

    public List<Subject> getSubjects() {
        return Subjects;
    }

    public List<Student> getStudents() {
        return Students;
    }

    public List<Teacher> getTeachers() {
        return Teachers;
    }

    public void addSubject(Subject subject) {
        Subjects.add(subject);
    }

    public void addStudent(Student student) {
        Students.add(student);
    }

    public void addTeacher(Teacher teacher) {
        Teachers.add(teacher);
    }

    @Override
    public String classString() {
        return "Classroom";
    }
}
