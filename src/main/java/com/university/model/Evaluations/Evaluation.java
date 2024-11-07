package com.university.model.Evaluations;

import com.university.model.Entity;
import com.university.model.Exercise;
import com.university.model.Subject;
import com.university.model.Student;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Evaluation extends Entity {

    private final String name;
    private final String evaluationType;
    private final Subject subject;
    private final Student student;
    final ArrayList<Exercise> exercises;

    // grading variables

    private boolean passed;
    boolean evaluated = false;
    private String criteria;
    private double criteriaValue;

    protected Evaluation(String evaluationName, Subject subject, Student student, String evaluationType) {
        this.exercises = new ArrayList<>();
        this.name = evaluationName;
        this.subject = subject;
        this.student = student;
        this.evaluationType = evaluationType;
    }

    // getters for the "main" and final variables

    public String getName() {
        return name;
    }

    public String getEvaluationType() {
        return evaluationType;
    }

    public Subject getSubject() {
        return subject;
    }

    public Student getStudent() {
        return student;
    }

    public String studentName() {
        return this.student.getName();
    } // convenience method

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    // g&s for the grading variables

    public double getCriteriaValue() {
        return criteriaValue;
    }

    public String getCriteria() {
        return criteria;
    }

    public boolean isPassed() {
        return passed;
    }

    public boolean isEvaluated() {
        return this.evaluated;
    }



    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public void setCriteriaValue(double criteriaValue) {
        this.criteriaValue = criteriaValue;
    }



    // grading calculations

    public double getAverage() {
        double sum = 0;
        for (Exercise exercise : exercises) {
            sum += exercise.getGrade();
        }
        return sum / exercises.size();
    }

    public double getMax() {
        double max = 0;
        for (Exercise exercise : exercises) {
            double grade = exercise.getGrade();
            if (grade > max) {
                max = grade;
            }
        }
        return max;
    }

    public double getMin() {
        double min = 10;
        for (Exercise exercise : exercises) {
            double grade = exercise.getGrade();
            if (grade < min) {
                min = grade;
            }
        }
        return min;
    }

    // returns the relevant grade
    public abstract double getGrade();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass() ) return false;
        Evaluation evaluation = (Evaluation) obj;
        return name.equals(evaluation.name)
                && subject.equals(evaluation.subject)
                && student.equals(evaluation.student)
                && evaluationType.equals(evaluation.evaluationType)
                && this.getId() == evaluation.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, subject, student, evaluationType, getId());
    }

    @Override
    public String classString() {
        return "Evaluation";
    }
}