package com.university.model.Evaluations;

import com.university.model.Entity;
import com.university.model.Exercise;
import com.university.model.Subject;
import com.university.model.Student;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Evaluation extends Entity {

    final ArrayList<Exercise> exercises;
    private final String evaluationName;
    private final Subject subject;
    private final Student student;
    private String evaluationType;
    private boolean passed;
    boolean evaluated = false;
    private String criteria;
    private double criteriaValue;

    protected Evaluation(String evaluationName, Subject subject, Student student) {
        this.exercises = new ArrayList<>();
        this.evaluationName = evaluationName;
        this.subject = subject;
        this.student = student;
    }

    public String getName() {
        return evaluationName;
    }

    public String getStudentName() {
        return this.student.getName();
    }

    public Student getStudent() {
        return student;
    }

    public Subject getSubject() {
        return subject;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public double getCriteriaValue() {
        return criteriaValue;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getEvaluationType() {
        return evaluationType;
    }

    public boolean isPassed() {
        return passed;
    }

    public boolean isEvaluated() {
        return this.evaluated;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
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

    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
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
        if (obj == null || getClass() != obj.getClass()) return false;
        Evaluation evaluation = (Evaluation) obj;
        return evaluationName.equals(evaluation.evaluationName)
                && subject.equals(evaluation.subject)
                && student.equals(evaluation.student)
                && evaluationType.equals(evaluation.evaluationType)
                && this.getId() == evaluation.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(evaluationName, subject, student, evaluationType, getId());
    }

    @Override
    public String classString() {
        return "Evaluation";
    }
}