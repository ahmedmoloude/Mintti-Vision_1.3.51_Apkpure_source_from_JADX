package com.p020kl.commonbase.bean.rothmanindex;

import java.util.List;

/* renamed from: com.kl.commonbase.bean.rothmanindex.HealthTrendBean */
public class HealthTrendBean {

    /* renamed from: Id */
    private String f834Id;
    private List<RiScoresBean> RiScores;
    private int Version;
    private List<WeightedScoresBean> WeightedScores;

    public String getId() {
        return this.f834Id;
    }

    public void setId(String str) {
        this.f834Id = str;
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

    public List<RiScoresBean> getRiScores() {
        return this.RiScores;
    }

    public void setRiScores(List<RiScoresBean> list) {
        this.RiScores = list;
    }

    /* renamed from: com.kl.commonbase.bean.rothmanindex.HealthTrendBean$WeightedScoresBean */
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

    /* renamed from: com.kl.commonbase.bean.rothmanindex.HealthTrendBean$RiScoresBean */
    public static class RiScoresBean {
        private DrilldownBean Drilldown;
        private int Point;
        private String Timestamp;
        private WeightedPointBean WeightedPoint;

        public int getPoint() {
            return this.Point;
        }

        public void setPoint(int i) {
            this.Point = i;
        }

        public DrilldownBean getDrilldown() {
            return this.Drilldown;
        }

        public void setDrilldown(DrilldownBean drilldownBean) {
            this.Drilldown = drilldownBean;
        }

        public String getTimestamp() {
            return this.Timestamp;
        }

        public void setTimestamp(String str) {
            this.Timestamp = str;
        }

        public WeightedPointBean getWeightedPoint() {
            return this.WeightedPoint;
        }

        public void setWeightedPoint(WeightedPointBean weightedPointBean) {
            this.WeightedPoint = weightedPointBean;
        }

        /* renamed from: com.kl.commonbase.bean.rothmanindex.HealthTrendBean$RiScoresBean$DrilldownBean */
        public static class DrilldownBean {
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

        /* renamed from: com.kl.commonbase.bean.rothmanindex.HealthTrendBean$RiScoresBean$WeightedPointBean */
        public static class WeightedPointBean {
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
}
