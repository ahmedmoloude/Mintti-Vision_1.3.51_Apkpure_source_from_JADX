package com.p020kl.commonbase.bean.rothmanindex;

/* renamed from: com.kl.commonbase.bean.rothmanindex.VitalSignsBean */
public class VitalSignsBean {
    private BloodPressureBean BloodPressure;
    private BradenScaleBean BradenScale;
    private OximetryBean Oximetry;
    private PulseBean Pulse;
    private RespirationBean Respiration;
    private TemperatureBean Temperature;

    public BloodPressureBean getBloodPressure() {
        return this.BloodPressure;
    }

    public void setBloodPressure(BloodPressureBean bloodPressureBean) {
        this.BloodPressure = bloodPressureBean;
    }

    public BradenScaleBean getBradenScale() {
        return this.BradenScale;
    }

    public void setBradenScale(BradenScaleBean bradenScaleBean) {
        this.BradenScale = bradenScaleBean;
    }

    public OximetryBean getOximetry() {
        return this.Oximetry;
    }

    public void setOximetry(OximetryBean oximetryBean) {
        this.Oximetry = oximetryBean;
    }

    public PulseBean getPulse() {
        return this.Pulse;
    }

    public void setPulse(PulseBean pulseBean) {
        this.Pulse = pulseBean;
    }

    public RespirationBean getRespiration() {
        return this.Respiration;
    }

    public void setRespiration(RespirationBean respirationBean) {
        this.Respiration = respirationBean;
    }

    public TemperatureBean getTemperature() {
        return this.Temperature;
    }

    public void setTemperature(TemperatureBean temperatureBean) {
        this.Temperature = temperatureBean;
    }

    /* renamed from: com.kl.commonbase.bean.rothmanindex.VitalSignsBean$BloodPressureBean */
    public static class BloodPressureBean {
        private String DateMeasured;
        private int Diastolic;
        private String MeasuredBy;
        private int Systolic;

        public int getDiastolic() {
            return this.Diastolic;
        }

        public void setDiastolic(int i) {
            this.Diastolic = i;
        }

        public int getSystolic() {
            return this.Systolic;
        }

        public void setSystolic(int i) {
            this.Systolic = i;
        }

        public String getMeasuredBy() {
            return this.MeasuredBy;
        }

        public void setMeasuredBy(String str) {
            this.MeasuredBy = str;
        }

        public String getDateMeasured() {
            return this.DateMeasured;
        }

        public void setDateMeasured(String str) {
            this.DateMeasured = str;
        }
    }

    /* renamed from: com.kl.commonbase.bean.rothmanindex.VitalSignsBean$BradenScaleBean */
    public static class BradenScaleBean {
        private String DateMeasured;
        private String MeasuredBy;
        private int Measurement;

        public int getMeasurement() {
            return this.Measurement;
        }

        public void setMeasurement(int i) {
            this.Measurement = i;
        }

        public String getMeasuredBy() {
            return this.MeasuredBy;
        }

        public void setMeasuredBy(String str) {
            this.MeasuredBy = str;
        }

        public String getDateMeasured() {
            return this.DateMeasured;
        }

        public void setDateMeasured(String str) {
            this.DateMeasured = str;
        }
    }

    /* renamed from: com.kl.commonbase.bean.rothmanindex.VitalSignsBean$OximetryBean */
    public static class OximetryBean {
        private String DateMeasured;
        private String MeasuredBy;
        private int Measurement;

        public int getMeasurement() {
            return this.Measurement;
        }

        public void setMeasurement(int i) {
            this.Measurement = i;
        }

        public String getMeasuredBy() {
            return this.MeasuredBy;
        }

        public void setMeasuredBy(String str) {
            this.MeasuredBy = str;
        }

        public String getDateMeasured() {
            return this.DateMeasured;
        }

        public void setDateMeasured(String str) {
            this.DateMeasured = str;
        }
    }

    /* renamed from: com.kl.commonbase.bean.rothmanindex.VitalSignsBean$PulseBean */
    public static class PulseBean {
        private String DateMeasured;
        private String MeasuredBy;
        private int Measurement;

        public int getMeasurement() {
            return this.Measurement;
        }

        public void setMeasurement(int i) {
            this.Measurement = i;
        }

        public String getMeasuredBy() {
            return this.MeasuredBy;
        }

        public void setMeasuredBy(String str) {
            this.MeasuredBy = str;
        }

        public String getDateMeasured() {
            return this.DateMeasured;
        }

        public void setDateMeasured(String str) {
            this.DateMeasured = str;
        }
    }

    /* renamed from: com.kl.commonbase.bean.rothmanindex.VitalSignsBean$RespirationBean */
    public static class RespirationBean {
        private String DateMeasured;
        private String MeasuredBy;
        private int Measurement;

        public int getMeasurement() {
            return this.Measurement;
        }

        public void setMeasurement(int i) {
            this.Measurement = i;
        }

        public String getMeasuredBy() {
            return this.MeasuredBy;
        }

        public void setMeasuredBy(String str) {
            this.MeasuredBy = str;
        }

        public String getDateMeasured() {
            return this.DateMeasured;
        }

        public void setDateMeasured(String str) {
            this.DateMeasured = str;
        }
    }

    /* renamed from: com.kl.commonbase.bean.rothmanindex.VitalSignsBean$TemperatureBean */
    public static class TemperatureBean {
        private String DateMeasured;
        private String MeasuredBy;
        private double Measurement;

        public double getMeasurement() {
            return this.Measurement;
        }

        public void setMeasurement(double d) {
            this.Measurement = d;
        }

        public String getMeasuredBy() {
            return this.MeasuredBy;
        }

        public void setMeasuredBy(String str) {
            this.MeasuredBy = str;
        }

        public String getDateMeasured() {
            return this.DateMeasured;
        }

        public void setDateMeasured(String str) {
            this.DateMeasured = str;
        }
    }
}
