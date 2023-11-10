package androidx.test.core.view;

import android.view.MotionEvent;

public class PointerCoordsBuilder {
    private float orientation;
    private float pressure = 1.0f;
    private float size = 1.0f;
    private float toolMajor;
    private float toolMinor;
    private float touchMajor;
    private float touchMinor;

    /* renamed from: x */
    private float f190x = 0.0f;

    /* renamed from: y */
    private float f191y = 0.0f;

    private PointerCoordsBuilder() {
    }

    public static PointerCoordsBuilder newBuilder() {
        return new PointerCoordsBuilder();
    }

    public PointerCoordsBuilder setCoords(float f, float f2) {
        this.f190x = f;
        this.f191y = f2;
        return this;
    }

    public PointerCoordsBuilder setPressure(float f) {
        this.pressure = f;
        return this;
    }

    public PointerCoordsBuilder setSize(float f) {
        this.size = f;
        return this;
    }

    public PointerCoordsBuilder setTouch(float f, float f2) {
        this.touchMajor = f;
        this.touchMinor = f2;
        return this;
    }

    public PointerCoordsBuilder setTool(float f, float f2) {
        this.toolMajor = f;
        this.toolMinor = f2;
        return this;
    }

    public PointerCoordsBuilder setOrientation(float f) {
        this.orientation = f;
        return this;
    }

    public MotionEvent.PointerCoords build() {
        MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
        pointerCoords.x = this.f190x;
        pointerCoords.y = this.f191y;
        pointerCoords.pressure = this.pressure;
        pointerCoords.size = this.size;
        pointerCoords.touchMajor = this.touchMajor;
        pointerCoords.touchMinor = this.touchMinor;
        pointerCoords.toolMajor = this.toolMajor;
        pointerCoords.toolMinor = this.toolMinor;
        pointerCoords.orientation = this.orientation;
        return pointerCoords;
    }
}
