package com.gyf.immersionbar;

import android.view.View;
import androidx.core.view.ViewCompat;
import java.util.HashMap;
import java.util.Map;

public class BarParams implements Cloneable {
    public float autoNavigationBarDarkModeAlpha = 0.0f;
    public boolean autoNavigationBarDarkModeEnable = false;
    public float autoStatusBarDarkModeAlpha = 0.0f;
    public boolean autoStatusBarDarkModeEnable = false;
    public boolean barEnable = true;
    public BarHide barHide = BarHide.FLAG_SHOW_BAR;
    public float contentAlpha = 0.0f;
    public int contentColor = 0;
    public int contentColorTransform = ViewCompat.MEASURED_STATE_MASK;
    public int defaultNavigationBarColor = ViewCompat.MEASURED_STATE_MASK;
    public boolean fits = false;
    public boolean fitsLayoutOverlapEnable = true;
    public int flymeOSStatusBarFontColor;
    public int flymeOSStatusBarFontTempColor;
    public boolean fullScreen = false;
    public boolean hideNavigationBar = false;
    public boolean isSupportActionBar = false;
    public boolean keyboardEnable = false;
    public int keyboardMode = 18;
    public float navigationBarAlpha = 0.0f;
    public int navigationBarColor = ViewCompat.MEASURED_STATE_MASK;
    public int navigationBarColorTransform = ViewCompat.MEASURED_STATE_MASK;
    public boolean navigationBarDarkIcon = false;
    public boolean navigationBarEnable = true;
    public float navigationBarTempAlpha = 0.0f;
    public boolean navigationBarWithEMUI3Enable = true;
    public boolean navigationBarWithKitkatEnable = true;
    OnBarListener onBarListener;
    OnKeyboardListener onKeyboardListener;
    OnNavigationBarListener onNavigationBarListener;
    public float statusBarAlpha = 0.0f;
    public int statusBarColor = 0;
    public boolean statusBarColorEnabled = true;
    public int statusBarColorTransform = ViewCompat.MEASURED_STATE_MASK;
    public boolean statusBarDarkFont = false;
    public float statusBarTempAlpha = 0.0f;
    public View statusBarView;
    public View titleBarView;
    public float viewAlpha = 0.0f;
    Map<View, Map<Integer, Integer>> viewMap = new HashMap();

    /* access modifiers changed from: protected */
    public BarParams clone() {
        try {
            return (BarParams) super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
