package com.p020kl.commonbase.utils;

import android.content.Context;
import android.location.LocationManager;

/* renamed from: com.kl.commonbase.utils.GpsUtils */
public class GpsUtils {
    public static boolean isGpsEnable(Context context) {
        if (context == null) {
            return false;
        }
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        boolean isProviderEnabled = locationManager.isProviderEnabled("gps");
        boolean isProviderEnabled2 = locationManager.isProviderEnabled("network");
        if (isProviderEnabled || isProviderEnabled2) {
            return true;
        }
        return false;
    }
}
