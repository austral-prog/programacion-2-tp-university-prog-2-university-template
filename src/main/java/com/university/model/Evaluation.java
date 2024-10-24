package TpUniversity.model;

import java.util.ArrayList;

public class Evaluation {

    private ArrayList<Double> grades;
    ArrayList<String> exercises;
    String evaluationName;
    String subject;
    String studentName;
    String evaluationType;
    boolean passed;
    boolean evaluated = false;
    String criteria;
    double criteriaValue;

    public Evaluation(String evaluationName, String subject, String studentName, String evaluationType) {
        this.grades = new ArrayList<>();
        this.exercises = new ArrayList<>();
        this.evaluationName = evaluationName;
        this.subject = subject;
        this.studentName = studentName;
        this.evaluationType = evaluationType;
    }

    public void addGrade(double grade){
        grades.add(grade);
    }

    public void addExercise(String exercise){
        exercises.add(exercise);
    }

    public double getAverage(){
        double sum = 0;
        for (Double num : grades){
            sum += num;
        }
        return sum / grades.size();
    }

    public String getName(){
        return this.evaluationName;
    }

    public String getStudentName(){
        return this.studentName;
    }

    public String getSubject(){
        return this.subject;
    }

    public String getEvaluationType(){
        return this.evaluationType;
    }

    public  String getEvaluationName(){
        return this.evaluationName;
    }

    public void setPassed(boolean passed){
        this.passed = passed;
    }

    public String[] getData(){
        //Subject_Name,Evaluation_Name,Student_Name,Grade (rounded to 1 decimal place)
        return new String[]{this.subject, this.evaluationName, this.studentName, String.format("%.1f", getAverage())};
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Evaluation evaluation = (Evaluation) obj;
        return evaluationName.equals(evaluation.evaluationName);
    }

    @Override
    public int hashCode() {
        return evaluationName.hashCode();
    }

    public boolean isEvaluated() {
        return this.evaluated;
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public double getMax() {
        double max = 0;
        for (Double num : grades){
            if (num > max){
                max = num;
            }
        }
        return max;
    }

    public double getMin() {
        double min = 10;
        for (Double num : grades){
            if (num < min){
                min = num;
            }
        }
        return min;
    }

    public void setCriteriaValue(double criteriaValue) {
        this.criteriaValue = criteriaValue;
    }

    public String[] getAltData() {
        //Subject_Name,Evaluation_Name,Student_Name,Evaluation_Type,Grade (rounded to 1 decimal place),Criteria,Criteria_Value,Passed,Min,Max
        return new String[]{this.subject, this.evaluationName, this.studentName, this.evaluationType,
                String.format("%.1f", getAverage()), this.criteria, String.valueOf(this.criteriaValue), String.valueOf(this.passed),
                String.format("%.1f", getMin()), String.format("%.1f", getMax())};
    }
}