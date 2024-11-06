package com.university.model.Evaluations;

import com.university.model.Exercise;
import com.university.model.Student;
import com.university.model.Subject;

public class OralExam extends Evaluation {
    public OralExam(String evaluationName, Subject subject, Student studentName) {
        super(evaluationName, subject, studentName);
        this.setEvaluationType("ORAL_EXAM");
    }

    @Override
    public double getGrade() { // returns the average of the grades of all exercises //todo return the ony grade
        double sum = 0;
        for (Exercise exercise : exercises) {
            sum += exercise.getGrade();
        }
        return sum / exercises.size();
    }
}
