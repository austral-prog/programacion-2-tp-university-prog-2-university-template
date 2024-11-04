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
    private boolean evaluated = false;
    private String criteria;
    private double criteriaValue;

    protected Evaluation(String evaluationName, Subject subject, Student student) {
        this.exercises = new ArrayList<>();
        this.evaluationName = evaluationName;
        this.subject = subject;
        this.student = student;
    }

    public String getName(){return this.evaluationName;}

    public String getStudentName(){return this.student.getName();}

    public Subject getSubject(){
        return this.subject;
    }

    public  String getEvaluationName(){return this.evaluationName;}

    public boolean isEvaluated() {return this.evaluated;}

    public void addExercise(Exercise exercise){exercises.add(exercise);}

    public void setEvaluated(boolean evaluated) {this.evaluated = evaluated;}

    public void setCriteria(String criteria) {this.criteria = criteria;}

    public void setPassed(boolean passed){this.passed = passed;}

    public void setCriteriaValue(double criteriaValue) {this.criteriaValue = criteriaValue;}

    public void setEvaluationType(String evaluationType) {this.evaluationType = evaluationType;}

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
        for (Exercise exercise : exercises){
            double grade = exercise.getGrade();
            if (grade > max){
                max = grade;
            }
        }
        return max;
    }

    public double getMin() {
        double min = 10;
        for (Exercise exercise : exercises){
            double grade = exercise.getGrade();
            if (grade < min){
                min = grade;
            }
        }
        return min;
    }

    public abstract double getRelevantGrade();

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

    // PRINTERS ( they return a String[] with the data of the evaluation, ordered as expected)
//todo new class to handle formating (the following methods) Formater
    public String[] getSecondTaskPrintData(){
        //Subject_Name,Evaluation_Name,Student_Name,Grade (rounded to 1 decimal place)
        return new String[]{this.subject.getName(), this.evaluationName, this.student.getName(), String.format("%.1f", getAverage())};
    }
    public String[] getThirdTaskPrintData() {
        // Evaluation_Name,Student_Name,Evaluation_Type,Criteria,Criteria_Value,Grade,Passed,Min,Max,Average,Subject_Name
        return new String[]{this.evaluationName, this.student.getName(), this.evaluationType,
                this.criteria, String.valueOf(getRelevantGrade()), String.valueOf(this.criteriaValue), String.valueOf(this.passed),
                String.format("%.1f", getMin()), String.format("%.1f", getMax()),String.format("%.1f", getAverage()), this.subject.getName()};
    }
}