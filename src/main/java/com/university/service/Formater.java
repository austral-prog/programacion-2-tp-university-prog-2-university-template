package com.university.service;

import com.university.model.Entity;
import com.university.model.Evaluations.Evaluation;
import com.university.model.Student;

public class Formater {
    public static String[] format(Entity entity, String flag) {
        switch (flag) {

            case "first":
                if (entity.classString().equals("Student")) {
                    Student student = (Student) entity;
                    return new String[]{student.getName(), String.valueOf(student.getSubjectAmount())};

                } else {throw new IllegalArgumentException("Invalid entity type");}

            case "second":
                if (entity.classString().equals("Evaluation")) {
                    Evaluation eval = (Evaluation) entity;
                    return new String[]{eval.getSubject().getName(), eval.getName(),
                            eval.getStudentName(), String.format("%.1f", eval.getAverage())};

                } else {throw new IllegalArgumentException("Invalid entity type");}

            case "third":
                if (entity.classString().equals("Evaluation")) {
                    Evaluation eval = (Evaluation) entity;
                    return new String[]{eval.getName(), String.format("%.1f", eval.getGrade())
                    , eval.getEvaluationType(), eval.getCriteria(), String.format("%.1f", eval.getCriteriaValue())
                    , eval.isPassed() ? "Passed" : "Failed"};

                } else {throw new IllegalArgumentException("Invalid entity type");}
            default:
                throw new IllegalArgumentException("Invalid flag");
        }
    }

}
