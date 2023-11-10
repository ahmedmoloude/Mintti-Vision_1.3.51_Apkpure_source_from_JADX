package com.p020kl.commonbase.bean.rothmanindex;

import java.util.List;

/* renamed from: com.kl.commonbase.bean.rothmanindex.TrendBean */
public class TrendBean {

    /* renamed from: Id */
    private String f836Id;
    private int Version;
    private List<WeightedScoresBean> WeightedScores;

    public String getId() {
        return this.f836Id;
    }

    public void setId(String str) {
        this.f836Id = str;
    }

    public int getVersion() {
        return this.Version;
    }

    public void setVersion(int i) {
        this.Version = i;
    }

    public List<WeightedScoresBean> getWeightedScores() {
        return this.WeightedScores;
    }

    public void setWeightedScores(List<WeightedScoresBean> list) {
        this.WeightedScores = list;
    }

    /* renamed from: com.kl.commonbase.bean.rothmanindex.TrendBean$WeightedScoresBean */
    public static class WeightedScoresBean {
        private int Point;
        private String Timestamp;

        public int getPoint() {
            return this.Point;
        }

        public void setPoint(int i) {
            this.Point = i;
        }

        public String getTimestamp() {
            return this.Timestamp;
        }

        public void setTimestamp(String str) {
            this.Timestamp = str;
        }
    }
}
