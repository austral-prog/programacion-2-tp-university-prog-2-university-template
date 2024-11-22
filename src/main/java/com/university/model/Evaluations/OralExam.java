package com.university.model.Evaluations;

import com.university.model.Student;
import com.university.model.Subject;

public class OralExam extends Evaluation {
    public OralExam(String evaluationName, Subject subject, Student studentName, String evaluationType) {
        super(evaluationName, subject, studentName, evaluationType);
    }

    @Override
    public double getGrade() {
        return exercises.get(0).getGrade();
    }
}
