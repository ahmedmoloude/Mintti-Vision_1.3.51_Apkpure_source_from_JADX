package com.p020kl.commonbase.bean.rothmanindex;

import java.util.List;

/* renamed from: com.kl.commonbase.bean.rothmanindex.PayloadBean */
public class PayloadBean {
    private List<String> canAssess;
    private List<String> canMonitor;
    private String clientId;
    private String exp;
    private String individualId;
    private String role;

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String str) {
        this.clientId = str;
    }

    public String getIndividualId() {
        return this.individualId;
    }

    public void setIndividualId(String str) {
        this.individualId = str;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String str) {
        this.role = str;
    }

    public String getExp() {
        return this.exp;
    }

    public void setExp(String str) {
        this.exp = str;
    }

    public List<String> getCanAssess() {
        return this.canAssess;
    }

    public void setCanAssess(List<String> list) {
        this.canAssess = list;
    }

    public List<String> getCanMonitor() {
        return this.canMonitor;
    }

    public void setCanMonitor(List<String> list) {
        this.canMonitor = list;
    }
}
