package com.p020kl.commonbase.bean.rothmanindex;

import java.util.List;

/* renamed from: com.kl.commonbase.bean.rothmanindex.QuiltTrendsBean */
public class QuiltTrendsBean {

    /* renamed from: Id */
    private String f835Id;
    private List<RawScoresBean> RawScores;
    private int Version;

    public String getId() {
        return this.f835Id;
    }

    public void setId(String str) {
        this.f835Id = str;
    }

    public int getVersion() {
        return this.Version;
    }

    public void setVersion(int i) {
        this.Version = i;
    }

    public List<RawScoresBean> getRawScores() {
        return this.RawScores;
    }

    public void setRawScores(List<RawScoresBean> list) {
        this.RawScores = list;
    }

    /* renamed from: com.kl.commonbase.bean.rothmanindex.QuiltTrendsBean$RawScoresBean */
    public static class RawScoresBean {
        private int Point;
        private String TimeStamp;

        public int getPoint() {
            return this.Point;
        }

        public void setPoint(int i) {
            this.Point = i;
        }

        public String getTimeStamp() {
            return this.TimeStamp;
        }

        public void setTimeStamp(String str) {
            this.TimeStamp = str;
        }
    }
}
