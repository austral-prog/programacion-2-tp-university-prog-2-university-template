package com.university.model.Evaluations;

import com.university.model.Exercise;
import com.university.model.Student;
import com.university.model.Subject;

public class WrittenExam extends Evaluation {
    public WrittenExam(String evaluationName, Subject subject, Student studentName, String evaluationType) {
        super(evaluationName, subject, studentName, evaluationType);
    }

    @Override
    public double getGrade() { // returns the average of the grades of all exercises
        double sum = 0;
        for (Exercise exercise : exercises) {
            sum += exercise.getGrade();
        }
        return sum / exercises.size();
    }
}
