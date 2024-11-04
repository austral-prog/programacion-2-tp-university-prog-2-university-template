// File: src/TpUniversity/service/EvaluationCriteria.java
package com.university.service.CriteriaAnalyzer;

import com.university.model.Evaluations.Evaluation;

public interface CriteriaApplier {
    void apply(Evaluation evaluation, double value, String criteria);
}