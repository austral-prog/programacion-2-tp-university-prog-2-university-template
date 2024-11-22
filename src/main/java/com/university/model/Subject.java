package com.university.model;

import java.util.HashSet;

public class Subject extends Entity  {
    // Classroom,Subject,Student_Name,Student_Email,Subject_Teacher
    // Student,Subject,Evaluation_Type,Evaluation_Name,Exercise_Name,Grade

    String name;
    HashSet<Teacher> teachers;
    HashSet<Student> students;
    HashSet<Classroom> classrooms;

    public Subject(String name) {
        this.name = name;
        this.teachers = new HashSet<>();
        this.students = new HashSet<>();
        this.classrooms = new HashSet<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void addClassroom(Classroom classroom) {
        classrooms.add(classroom);
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<Student> getStudents() {
        return students;
    }

    public HashSet<Teacher> getTeachers() {
        return teachers;
    }

    public HashSet<Classroom> getClassrooms() {
        return classrooms;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Subject subject = (Subject) obj;
        return name.equals(subject.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String classString() {
        return "Subject";
    }

    public void setSubjectName(String subjectName) {
        this.name = subjectName;
    }
}
