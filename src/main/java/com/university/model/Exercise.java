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
    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }
    public Evaluation getEvaluation() {
        return evaluation;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Exercise exercise = (Exercise) obj;
        return name.equals(exercise.name)
                && grade == exercise.grade
                && evaluation.equals(exercise.evaluation);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + Double.hashCode(grade) + evaluation.hashCode();
    }

    @Override
    public String classString() {
        return "Exercise";
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public void setExerciseName(String exerciseName) {
        this.name = exerciseName;
    }
}
