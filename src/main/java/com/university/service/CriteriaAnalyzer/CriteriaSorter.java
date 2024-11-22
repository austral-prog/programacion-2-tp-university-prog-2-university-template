package com.university.service.CriteriaAnalyzer;

import com.university.model.Evaluations.Evaluation;

public class CriteriaSorter {
    public static void evaluate(Evaluation evaluation, String criteriaType, String criteriaValue) {
        CriteriaApplier criteria = switch (criteriaType) {
            case "AVERAGE_ABOVE_VALUE" -> new AverageAboveValue();
            case "MIN_ABOVE_VALUE" -> new MinAboveValue();
            case "MAX_ABOVE_VALUE" -> new MaxAboveValue();
            default -> throw new IllegalStateException("Unexpected value: " + criteriaType);
        };
        criteria.apply(evaluation, Double.parseDouble(criteriaValue), criteriaType);
    }
}
