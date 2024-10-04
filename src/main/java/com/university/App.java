package com.university;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

class Student {
    HashSet<String> courses;
    String name;

    public Student(String name) {
        this.courses = new HashSet<>();
        this.name = name;
    }

    String getName() {
        return name;
    }

    void addCourse(String course) {
        courses.add(course);
    }

    private int courseAmount() {
        return courses.size();
    }

    String[] getData() {
        return new String[]{this.getName(), String.valueOf(this.courseAmount())};
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
    public class App {
        public static void main(String[] args) {
            String filePath = "E:\\Desktop\\Prog\\Prog-2-Java\\src\\TpUniverity\\input.csv";
            String line;
            HashSet<Student> students = new HashSet<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");  // Splitting by comma
                    for (Student student : students) {
                        if (student.getName().equals(values[2])) {
                            student.addCourse(values[1]);
                        }
                    }
                    Student student = new Student(values[2]);
                    if (!students.contains(student)) {
                        students.add(student);
                        student.addCourse(values[1]);
                    } else {
                        student = null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String newFilePath = "E:\\Desktop\\Prog\\Prog-2-Java\\src\\TpUniverity\\solution.csv";
            ArrayList<Student> studentsList = new ArrayList<>(students);
            studentsList.sort(Comparator.comparing(Student::getName));

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(newFilePath))) {
                String[] header = {"Student_Name", "Course_Count"};
                bw.write(String.join(",", header));
                bw.newLine();
                for (Student student : studentsList) {
                    String[] data = student.getData();
                    if (!data[0].equals("Student_Name")) {
                        bw.write(String.join(",", student.getData()));
                        bw.newLine();
                    }
                }
                bw.newLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
