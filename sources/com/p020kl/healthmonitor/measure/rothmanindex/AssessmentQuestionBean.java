package com.p020kl.healthmonitor.measure.rothmanindex;

/* renamed from: com.kl.healthmonitor.measure.rothmanindex.AssessmentQuestionBean */
public class AssessmentQuestionBean {
    private boolean isSelected;
    private String questionIndex;
    private String questionText;

    public String getQuestionIndex() {
        return this.questionIndex;
    }

    public void setQuestionIndex(String str) {
        this.questionIndex = str;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public void setQuestionText(String str) {
        this.questionText = str;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }
}
