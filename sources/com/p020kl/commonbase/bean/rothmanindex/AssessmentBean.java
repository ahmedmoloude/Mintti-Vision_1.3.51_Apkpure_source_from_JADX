package com.p020kl.commonbase.bean.rothmanindex;

import com.p020kl.commonbase.bean.BaseEntity;

/* renamed from: com.kl.commonbase.bean.rothmanindex.AssessmentBean */
public class AssessmentBean extends BaseEntity {
    private String AssessedBy;
    private String DateAssessed;
    private boolean IsBowelIncontinenceMet;
    private boolean IsCardiacMet;
    private boolean IsCognitiveMet;
    private boolean IsFoodMet;
    private boolean IsGastrointestinalMet;
    private boolean IsGenitourinaryMet;
    private boolean IsMusculoSkeletalMet;
    private boolean IsNeurologicalMet;
    private boolean IsPeripheralVascularMet;
    private boolean IsPsychosocialMet;
    private boolean IsRespiratoryMet;
    private boolean IsSafetyMet;
    private boolean IsSkinMet;
    private boolean IsUrinaryIncontinenceMet;

    public boolean isCardiacMet() {
        return this.IsCardiacMet;
    }

    public void setCardiacMet(boolean z) {
        this.IsCardiacMet = z;
    }

    public boolean isFoodMet() {
        return this.IsFoodMet;
    }

    public void setFoodMet(boolean z) {
        this.IsFoodMet = z;
    }

    public boolean isGastrointestinalMet() {
        return this.IsGastrointestinalMet;
    }

    public void setGastrointestinalMet(boolean z) {
        this.IsGastrointestinalMet = z;
    }

    public boolean isGenitourinaryMet() {
        return this.IsGenitourinaryMet;
    }

    public void setGenitourinaryMet(boolean z) {
        this.IsGenitourinaryMet = z;
    }

    public boolean isBowelIncontinenceMet() {
        return this.IsBowelIncontinenceMet;
    }

    public void setBowelIncontinenceMet(boolean z) {
        this.IsBowelIncontinenceMet = z;
    }

    public boolean isUrinaryIncontinenceMet() {
        return this.IsUrinaryIncontinenceMet;
    }

    public void setUrinaryIncontinenceMet(boolean z) {
        this.IsUrinaryIncontinenceMet = z;
    }

    public boolean isMusculoSkeletalMet() {
        return this.IsMusculoSkeletalMet;
    }

    public void setMusculoSkeletalMet(boolean z) {
        this.IsMusculoSkeletalMet = z;
    }

    public boolean isNeurologicalMet() {
        return this.IsNeurologicalMet;
    }

    public void setNeurologicalMet(boolean z) {
        this.IsNeurologicalMet = z;
    }

    public boolean isCognitiveMet() {
        return this.IsCognitiveMet;
    }

    public void setCognitiveMet(boolean z) {
        this.IsCognitiveMet = z;
    }

    public boolean isPeripheralVascularMet() {
        return this.IsPeripheralVascularMet;
    }

    public void setPeripheralVascularMet(boolean z) {
        this.IsPeripheralVascularMet = z;
    }

    public boolean isSkinMet() {
        return this.IsSkinMet;
    }

    public void setSkinMet(boolean z) {
        this.IsSkinMet = z;
    }

    public boolean isPsychosocialMet() {
        return this.IsPsychosocialMet;
    }

    public void setPsychosocialMet(boolean z) {
        this.IsPsychosocialMet = z;
    }

    public boolean isRespiratoryMet() {
        return this.IsRespiratoryMet;
    }

    public void setRespiratoryMet(boolean z) {
        this.IsRespiratoryMet = z;
    }

    public boolean isSafetyMet() {
        return this.IsSafetyMet;
    }

    public void setSafetyMet(boolean z) {
        this.IsSafetyMet = z;
    }

    public String getAssessedBy() {
        return this.AssessedBy;
    }

    public void setAssessedBy(String str) {
        this.AssessedBy = str;
    }

    public String getDateAssessed() {
        return this.DateAssessed;
    }

    public void setDateAssessed(String str) {
        this.DateAssessed = str;
    }
}
