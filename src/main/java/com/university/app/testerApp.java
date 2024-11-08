package com.university.app;

import com.university.model.Student;
import com.university.service.EntityManager;
import com.university.service.ManagerHolder;

public class testerApp {
    public static void main(String[] args) {
        EntityManager<Student> studentManager = ManagerHolder.StudentManager;
        System.out.println(studentManager.getIdentifier());
    }
}