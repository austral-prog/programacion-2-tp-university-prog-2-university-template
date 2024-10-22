package TpUniversity.model;

import java.util.HashSet;

public class Student {

    HashSet<String> courses;
    String name;

    public Student(String name) {
        this.courses = new HashSet<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCourse(String course) {
        courses.add(course);
    }

    public int getCourseAmount() {
        return courses.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}