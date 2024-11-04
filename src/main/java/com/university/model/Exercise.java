package com.university.model;

import com.university.model.Evaluations.Evaluation;

public class Exercise extends Entity {

    String name;
    double grade;
    Evaluation evaluation;

    public Exercise(String name, double grade, Evaluation evaluation) {
        this.name = name;
        this.grade = grade;
        this.evaluation = evaluation;
    }
    public String getName() {
        return name;
    }
    public double getGrade() {
        return grade;
    }
    public Evaluation getEvaluation() {
        return evaluation;
    }

    @Override
    public String classString() {
        return "Exercise";
    }
}
