package com.neurosky.AlgoSdk;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NskAlgoSdk {
    private static final String TAG = "NskAlgoSdkTag Wrapper";
    private static OnECGAlgoIndexListener mOnECGAlgoIndexListener;
    private static OnECGHRVFDAlgoIndexListener mOnECGHRVFDAlgoIndexListener;
    private static OnECGHRVTDAlgoIndexListener mOnECGHRVTDAlgoIndexListener;
    private static OnSignalQualityListener mOnSignalQualityListener;
    private static OnStateChangeListener mOnStateChangeListener;

    public interface OnECGAlgoIndexListener {
        void onECGAlgoIndex(int i, int i2);
    }

    public interface OnECGHRVFDAlgoIndexListener {
        void onECGHRVFDAlgoIndexListener(float f, float f2, float f3, float f4);
    }

    public interface OnECGHRVTDAlgoIndexListener {
        void onECGHRVTDAlgoIndexListener(float f, float f2, float f3, float f4, float f5);
    }

    public interface OnSignalQualityListener {
        void onOverallSignalQuality(int i);

        void onSignalQuality(int i);
    }

    public interface OnStateChangeListener {
        void onStateChange(int i, int i2);
    }

    static {
        try {
            System.loadLibrary("NskAlgo");
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Load lib error: ");
            sb.append(e.toString());
        }
    }

    public static void NskAlgECGHRVFDAlgoIndexListener(float f, float f2, float f3, float f4) {
        OnECGHRVFDAlgoIndexListener onECGHRVFDAlgoIndexListener = mOnECGHRVFDAlgoIndexListener;
        if (onECGHRVFDAlgoIndexListener != null) {
            onECGHRVFDAlgoIndexListener.onECGHRVFDAlgoIndexListener(f, f2, f3, f4);
        }
    }

    public static void NskAlgECGHRVTDAlgoIndexListener(float f, float f2, float f3, float f4, float f5) {
        OnECGHRVTDAlgoIndexListener onECGHRVTDAlgoIndexListener = mOnECGHRVTDAlgoIndexListener;
        if (onECGHRVTDAlgoIndexListener != null) {
            onECGHRVTDAlgoIndexListener.onECGHRVTDAlgoIndexListener(f, f2, f3, f4, f5);
        }
    }

    public static native String NskAlgoAlgoVersion(int i);

    public static native int NskAlgoDataStream(int i, short[] sArr, int i2);

    public static void NskAlgoECGAlgoIndexListener(int i, int i2) {
        OnECGAlgoIndexListener onECGAlgoIndexListener = mOnECGAlgoIndexListener;
        if (onECGAlgoIndexListener != null) {
            int i3 = 255;
            switch (i) {
                case 1:
                    i3 = 1;
                    break;
                case 2:
                    i3 = 2;
                    break;
                case 3:
                    i3 = 3;
                    break;
                case 4:
                    i3 = 4;
                    break;
                case 5:
                    i3 = 5;
                    break;
                case 6:
                    i3 = 6;
                    break;
                case 7:
                    i3 = 7;
                    break;
                case 8:
                    i3 = 8;
                    break;
                case 9:
                    i3 = 9;
                    break;
                case 10:
                    i3 = 10;
                    break;
                case 11:
                    i3 = 11;
                    break;
                case 12:
                    i3 = 12;
                    break;
                case 13:
                    i3 = 13;
                    break;
            }
            onECGAlgoIndexListener.onECGAlgoIndex(i3, i2);
        }
    }

    public static native int NskAlgoInit(int i, String str, String str2);

    public static void NskAlgoOverallSignalQualityListener(int i) {
        OnSignalQualityListener onSignalQualityListener = mOnSignalQualityListener;
        if (onSignalQualityListener != null) {
            onSignalQualityListener.onOverallSignalQuality(i);
        }
    }

    public static native int NskAlgoPause();

    public static native boolean NskAlgoProfileActive(int i);

    public static native boolean NskAlgoProfileDelete(int i);

    public static native byte[] NskAlgoProfileGetBaseline(int i, int i2);

    public static native boolean NskAlgoProfileSetBaseline(int i, int i2, byte[] bArr);

    public static native boolean NskAlgoProfileUpdate(NskAlgoProfile nskAlgoProfile);

    public static native NskAlgoProfile[] NskAlgoProfiles();

    public static native boolean NskAlgoQueryOverallQuality(int i);

    public static void NskAlgoSdkStateChangeListener(int i, int i2) {
        OnStateChangeListener onStateChangeListener = mOnStateChangeListener;
        if (onStateChangeListener != null) {
            onStateChangeListener.onStateChange(i, i2);
        }
    }

    public static native String NskAlgoSdkVersion();

    private static native boolean NskAlgoSetBaudRate(int i, int i2);

    public static native boolean NskAlgoSetECGConfigAfib(float f);

    public static native boolean NskAlgoSetECGConfigHRV(int i);

    public static native boolean NskAlgoSetECGConfigHRVFD(int i, int i2);

    public static native boolean NskAlgoSetECGConfigHRVTD(int i, int i2);

    public static native boolean NskAlgoSetECGConfigHeartage(int i);

    public static native boolean NskAlgoSetECGConfigStress(int i, int i2);

    private static native boolean NskAlgoSetSignalQualityWatchDog(short s, short s2);

    public static void NskAlgoSignalQualityListener(int i) {
        OnSignalQualityListener onSignalQualityListener = mOnSignalQualityListener;
        if (onSignalQualityListener != null) {
            onSignalQualityListener.onSignalQuality(i);
        }
    }

    public static native int NskAlgoStart(boolean z);

    public static native int NskAlgoStop();

    public static native int NskAlgoUninit();

    public static String dateToStr(Date date) {
        return new SimpleDateFormat("yyyy/MM/dd").format(date);
    }

    public static Date strToDate(String str) {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean setBaudRate(int i, int i2) {
        int i3;
        if (i != 6) {
            return false;
        }
        if (i2 == 1) {
            i3 = 256;
        } else if (i2 == 2) {
            i3 = 300;
        } else if (i2 == 3) {
            i3 = 512;
        } else if (i2 != 4) {
            return false;
        } else {
            i3 = 600;
        }
        return NskAlgoSetBaudRate(6, i3);
    }

    public void setOnECGAlgoIndexListener(OnECGAlgoIndexListener onECGAlgoIndexListener) {
        mOnECGAlgoIndexListener = onECGAlgoIndexListener;
    }

    public void setOnECGHRVFDAlgoIndexListener(OnECGHRVFDAlgoIndexListener onECGHRVFDAlgoIndexListener) {
        mOnECGHRVFDAlgoIndexListener = onECGHRVFDAlgoIndexListener;
    }

    public void setOnECGHRVTDAlgoIndexListener(OnECGHRVTDAlgoIndexListener onECGHRVTDAlgoIndexListener) {
        mOnECGHRVTDAlgoIndexListener = onECGHRVTDAlgoIndexListener;
    }

    public void setOnSignalQualityListener(OnSignalQualityListener onSignalQualityListener) {
        mOnSignalQualityListener = onSignalQualityListener;
    }

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        mOnStateChangeListener = onStateChangeListener;
    }

    public boolean setSignalQualityWatchDog(short s, short s2) {
        return NskAlgoSetSignalQualityWatchDog(s, s2);
    }
}
