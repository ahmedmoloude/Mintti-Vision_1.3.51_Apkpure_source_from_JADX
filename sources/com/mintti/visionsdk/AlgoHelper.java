package com.mintti.visionsdk;

public class AlgoHelper {
    static {
        System.loadLibrary("vision_algo");
    }

    public static native void calcBloodPressure(double[] dArr, int[] iArr);

    public static native void calcHeartRate(double[] dArr, int i, double[] dArr2, int[] iArr);

    public static native void calcSpo2(int[] iArr, int i, int[] iArr2, double[] dArr);

    public static native int calcStopPressure(double[] dArr, double[] dArr2);

    public static native int filtFiltWrapper(int[] iArr);

    public static native int filtFiltWrapperLowCost(int[] iArr);

    public static native void initFiltFiltWrapper();
}
