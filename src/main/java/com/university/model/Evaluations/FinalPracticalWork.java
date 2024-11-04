package com.university.model.Evaluations;

import com.university.model.Exercise;
import com.university.model.Subject;

public class FinalPracticalWork extends Evaluation {

    public FinalPracticalWork(String evaluationName, Subject subject, String studentName) {
        super(evaluationName, subject, studentName);
        this.setEvaluationType("FINAL_PRACTICAL_WORK");
    }

    @Override
    public double getRelevantGrade() { // returns the sum of the grades of all exercises
        double sum = 0;
        for (Exercise exercise : this.exercises) {
            sum += exercise.getGrade();
        }
        return sum;
    }
}
