package com.university.model.Evaluations;

import com.university.model.Subject;

public class PracticalWork extends Evaluation {
    public PracticalWork(String evaluationName, Subject subject, String studentName) {
        super(evaluationName, subject, studentName);
        this.setEvaluationType("PRACTICAL_WORK");
    }

    @Override
    public double getRelevantGrade() { // returns the last grade
        return exercises.getLast().getGrade();
    }
}
//PRACTICAL_WORK