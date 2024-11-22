package com.university.service.CriteriaAnalyzer;

import com.university.model.Evaluations.Evaluation;

public class AverageAboveValue implements CriteriaApplier {

    @Override
    public void apply(Evaluation evaluation, double value, String criteria) {
        evaluation.setEvaluated(true);
        evaluation.setCriteria(criteria);
        evaluation.setCriteriaValue(value);
        evaluation.setPassed(evaluation.getAverage() > value);
    }
}