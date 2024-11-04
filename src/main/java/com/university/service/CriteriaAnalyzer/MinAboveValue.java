package com.university.service.CriteriaAnalyzer;

import com.university.model.Evaluations.Evaluation;

public class MinAboveValue implements CriteriaApplier {

    public void apply(Evaluation evaluation, double value, String criteria) {
        evaluation.setEvaluated(true);
        evaluation.setCriteria(criteria);
        evaluation.setCriteriaValue(value);
        if (evaluation.getMin() > value) {
            evaluation.setPassed(true);
        }
    }
}