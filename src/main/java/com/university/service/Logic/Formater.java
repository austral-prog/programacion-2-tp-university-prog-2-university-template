package com.university.service.Logic;

import com.university.model.Entity;
import com.university.model.Evaluations.Evaluation;
import com.university.model.Student;

public class Formater {

    public static String[] format(Entity entity, String flag) {
        return switch (flag) {
            case "first" -> formatFirstFormat(entity);
            case "second" -> formatSecondFormat(entity);
            case "third" -> formatThirdFormat(entity);
            default -> throw new IllegalArgumentException("Invalid flag");
        };
    }

        private static String[] formatFirstFormat(Entity entity){
            if (entity instanceof Student student) {
                return new String[]{student.name(), String.valueOf(student.getSubjectAmount())};
            } else {
                throw new IllegalArgumentException("Invalid entity type");
            }
        }

        private static String[] formatSecondFormat(Entity entity){
            if (entity instanceof Evaluation eval) {
                return new String[]{eval.getSubject().name(), eval.name(), eval.studentName(), String.format("%.1f", eval.getGrade())};
            } else {
                throw new IllegalArgumentException("Invalid entity type");
            }
        }

        private static String[] formatThirdFormat(Entity entity){
            if (entity instanceof Evaluation eval) {
                return new String[]{eval.name(), String.format("%.1f", eval.getGrade()), eval.getEvaluationType(), eval.getCriteria(), String.format("%.1f", eval.getCriteriaValue()), eval.isPassed() ? "Passed" : "Failed"};
            } else {
                throw new IllegalArgumentException("Invalid entity type");
            }
        }
    }
