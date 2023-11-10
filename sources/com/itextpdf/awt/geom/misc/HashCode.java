package com.itextpdf.awt.geom.misc;

public final class HashCode {
    public static final int EMPTY_HASH_CODE = 1;
    private int hashCode = 1;

    public static int combine(int i, int i2) {
        return (i * 31) + i2;
    }

    public final int hashCode() {
        return this.hashCode;
    }

    public static int combine(int i, boolean z) {
        return combine(i, z ? 1231 : 1237);
    }

    public static int combine(int i, long j) {
        return combine(i, (int) (j ^ (j >>> 32)));
    }

    public static int combine(int i, float f) {
        return combine(i, Float.floatToIntBits(f));
    }

    public static int combine(int i, double d) {
        return combine(i, Double.doubleToLongBits(d));
    }

    public static int combine(int i, Object obj) {
        return combine(i, obj.hashCode());
    }

    public final HashCode append(int i) {
        this.hashCode = combine(this.hashCode, i);
        return this;
    }

    public final HashCode append(long j) {
        this.hashCode = combine(this.hashCode, j);
        return this;
    }

    public final HashCode append(float f) {
        this.hashCode = combine(this.hashCode, f);
        return this;
    }

    public final HashCode append(double d) {
        this.hashCode = combine(this.hashCode, d);
        return this;
    }

    public final HashCode append(boolean z) {
        this.hashCode = combine(this.hashCode, z);
        return this;
    }

    public final HashCode append(Object obj) {
        this.hashCode = combine(this.hashCode, obj);
        return this;
    }
}
