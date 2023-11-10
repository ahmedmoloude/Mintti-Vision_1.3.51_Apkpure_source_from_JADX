package com.linktop.infs;

import com.linktop.constant.ResultData;

public interface OnBpDataListener {
    void onFIRAvgFilter(ResultData resultData, boolean z);

    void onRcvPressure(int i);
}
