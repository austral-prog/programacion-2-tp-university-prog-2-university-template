package com.university.model;

import java.util.HashSet;

public class Student extends Entity  {

    HashSet<Subject> subjects;
    String name;
    String  email;

    public Student(String name) {
        this.subjects = new HashSet<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public int getSubjectAmount() {
        return subjects.size();
    }

    public String[] getFirstTaskPrintData() {
        return new String[]{this.getName(), String.valueOf(this.getSubjectAmount())};
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return name.equals(student.name)
                && getId() == student.getId();
    }

    @Override
    public int hashCode() {
        return name.hashCode() + Integer.hashCode(this.getId()) + email.hashCode();
    }

    @Override
    public String classString() {
        return "Student";
    }

    /*  // Unused code
        public HashSet<Subject> getSubjects() {
        return subjects;
    }
     */
}