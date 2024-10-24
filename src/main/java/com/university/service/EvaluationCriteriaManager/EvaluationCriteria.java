// File: src/TpUniversity/service/EvaluationCriteria.java
package TpUniversity.service.EvaluationCriteriaManager;

import TpUniversity.model.Evaluation;

public interface EvaluationCriteria {
    void apply(Evaluation evaluation, double value, String criteria);
}