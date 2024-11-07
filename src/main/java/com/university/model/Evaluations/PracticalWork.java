package com.university.model.Evaluations;

import com.university.model.Student;
import com.university.model.Subject;

public class PracticalWork extends Evaluation {
    public PracticalWork(String evaluationName, Subject subject, Student studentName, String evaluationType) {
        super(evaluationName, subject, studentName, evaluationType);
    }

    @Override
    public double getGrade() { // returns the last grade
        return exercises.getLast().getGrade();
    }
}
//PRACTICAL_WORK