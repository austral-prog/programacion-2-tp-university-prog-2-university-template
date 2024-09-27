package com.university;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private String email;
    private List<Course> courses;

    public Student(String name, String email) {
        super(name);
        this.email = email;
        this.courses = new ArrayList<Course>();
    }

    public Student(String[] studentData) {
        super(studentData[2]);
        this.email = studentData[3];
        this.courses = new ArrayList<Course>();
    }

    // Getters
    public Integer getCourseCount() {
        int i = 0;
        List<String> subjects = new ArrayList<String>();

        for (Course c : this.courses) {
            if (subjects.contains(c.getSubject())) continue;
            subjects.add(c.getSubject());
            i += 1;
        }
        return i;
    }
    public List<Course> getCourses() { return this.courses; }
    public String getEmail() { return email; }

    // Setters
    public void addCourse(Course course) { this.courses.add(course); }
    public void setEmail(String email) { this.email = email; }

    // Other

    @Override
    public String toString() {
        return String.format("%s,%d", super.getName(), this.getCourseCount());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (!(o instanceof Student cStudent)) { return false; }
        return this.getName().equals(cStudent.getName());
    }
}
