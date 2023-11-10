package androidx.constraintlayout.core.motion.utils;

public class FloatRect {
    public float bottom;
    public float left;
    public float right;

    /* renamed from: top  reason: collision with root package name */
    public float f2153top;

    public final float centerX() {
        return (this.left + this.right) * 0.5f;
    }

    public final float centerY() {
        return (this.f2153top + this.bottom) * 0.5f;
    }
}
