package com.university.model.Evaluations;

import com.university.model.Exercise;
import com.university.model.Student;
import com.university.model.Subject;

public class FinalPracticalWork extends Evaluation {

    public FinalPracticalWork(String evaluationName, Subject subject, Student studentName, String evaluationType) {
        super(evaluationName, subject, studentName, evaluationType);
    }

    @Override
    public double getGrade() { // returns the sum of the grades of all exercises
        double sum = 0;
        for (Exercise exercise : this.exercises) {
            sum += exercise.getGrade();
        }
        return sum;
    }
}
