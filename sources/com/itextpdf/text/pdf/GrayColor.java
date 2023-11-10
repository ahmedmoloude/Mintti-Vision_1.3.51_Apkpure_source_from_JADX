package com.itextpdf.text.pdf;

public class GrayColor extends ExtendedColor {
    public static final GrayColor GRAYBLACK = new GrayColor(0.0f);
    public static final GrayColor GRAYWHITE = new GrayColor(1.0f);
    private static final long serialVersionUID = -6571835680819282746L;
    private float gray;

    public GrayColor(int i) {
        this(((float) i) / 255.0f);
    }

    public GrayColor(float f) {
        super(1, f, f, f);
        this.gray = normalize(f);
    }

    public float getGray() {
        return this.gray;
    }

    public boolean equals(Object obj) {
        return (obj instanceof GrayColor) && ((GrayColor) obj).gray == this.gray;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.gray);
    }
}
