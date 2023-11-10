package com.p020kl.commonbase.bean;

import com.p020kl.commonbase.constants.Constants;

/* renamed from: com.kl.commonbase.bean.FeedbackEntity */
public class FeedbackEntity {
    private String algoVersion = "";
    private String contactWay = "";
    private String feedbackImg1 = "";
    private String feedbackImg2 = "";
    private String feedbackImg3 = "";
    private String feedbackText;
    private String firmwareVersion;

    /* renamed from: os */
    private String f829os = Constants.f840OS;
    private String softwareVersion = "";

    public String getSoftwareVersion() {
        return this.softwareVersion;
    }

    public void setSoftwareVersion(String str) {
        this.softwareVersion = str;
    }

    public String getFirmwareVersion() {
        return this.firmwareVersion;
    }

    public void setFirmwareVersion(String str) {
        this.firmwareVersion = str;
    }

    public String getAlgoVersion() {
        return this.algoVersion;
    }

    public void setAlgoVersion(String str) {
        this.algoVersion = str;
    }

    public String getOs() {
        return this.f829os;
    }

    public void setOs(String str) {
        this.f829os = str;
    }

    public String getFeedbackText() {
        return this.feedbackText;
    }

    public void setFeedbackText(String str) {
        this.feedbackText = str;
    }

    public String getContactWay() {
        return this.contactWay;
    }

    public void setContactWay(String str) {
        this.contactWay = str;
    }

    public String getFeedbackImg1() {
        return this.feedbackImg1;
    }

    public void setFeedbackImg1(String str) {
        this.feedbackImg1 = str;
    }

    public String getFeedbackImg2() {
        return this.feedbackImg2;
    }

    public void setFeedbackImg2(String str) {
        this.feedbackImg2 = str;
    }

    public String getFeedbackImg3() {
        return this.feedbackImg3;
    }

    public void setFeedbackImg3(String str) {
        this.feedbackImg3 = str;
    }
}
