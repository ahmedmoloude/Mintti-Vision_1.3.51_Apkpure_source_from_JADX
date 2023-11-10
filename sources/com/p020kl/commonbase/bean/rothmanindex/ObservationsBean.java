package com.p020kl.commonbase.bean.rothmanindex;

/* renamed from: com.kl.commonbase.bean.rothmanindex.ObservationsBean */
public class ObservationsBean {
    private AssessmentBean Assessment;
    private LabsBean Labs;
    private String ObservedBy;
    private String ObservedId;
    private VitalSignsBean VitalSigns;

    public String getObservedBy() {
        return this.ObservedBy;
    }

    public void setObservedBy(String str) {
        this.ObservedBy = str;
    }

    public String getObservedId() {
        return this.ObservedId;
    }

    public void setObservedId(String str) {
        this.ObservedId = str;
    }

    public AssessmentBean getAssessment() {
        return this.Assessment;
    }

    public void setAssessment(AssessmentBean assessmentBean) {
        this.Assessment = assessmentBean;
    }

    public VitalSignsBean getVitalSigns() {
        return this.VitalSigns;
    }

    public void setVitalSigns(VitalSignsBean vitalSignsBean) {
        this.VitalSigns = vitalSignsBean;
    }

    public LabsBean getLabs() {
        return this.Labs;
    }

    public void setLabs(LabsBean labsBean) {
        this.Labs = labsBean;
    }
}
