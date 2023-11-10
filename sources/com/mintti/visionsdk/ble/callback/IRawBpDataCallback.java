package com.mintti.visionsdk.ble.callback;

public interface IRawBpDataCallback {
    void onDecompressionData(short s);

    void onPressure(short s);

    void onPressurizationData(short s);
}
