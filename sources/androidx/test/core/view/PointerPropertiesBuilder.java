package androidx.test.core.view;

import android.view.MotionEvent;

public class PointerPropertiesBuilder {

    /* renamed from: id */
    private int f192id;
    private int toolType;

    private PointerPropertiesBuilder() {
    }

    public PointerPropertiesBuilder setId(int i) {
        this.f192id = i;
        return this;
    }

    public PointerPropertiesBuilder setToolType(int i) {
        this.toolType = i;
        return this;
    }

    public MotionEvent.PointerProperties build() {
        MotionEvent.PointerProperties pointerProperties = new MotionEvent.PointerProperties();
        pointerProperties.id = this.f192id;
        pointerProperties.toolType = this.toolType;
        return pointerProperties;
    }

    public static PointerPropertiesBuilder newBuilder() {
        return new PointerPropertiesBuilder();
    }
}
