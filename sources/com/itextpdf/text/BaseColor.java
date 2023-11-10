package com.itextpdf.text;

import com.itextpdf.text.error_messages.MessageLocalization;

public class BaseColor {
    public static final BaseColor BLACK = new BaseColor(0, 0, 0);
    public static final BaseColor BLUE = new BaseColor(0, 0, 255);
    public static final BaseColor CYAN = new BaseColor(0, 255, 255);
    public static final BaseColor DARK_GRAY = new BaseColor(64, 64, 64);
    private static final double FACTOR = 0.7d;
    public static final BaseColor GRAY = new BaseColor(128, 128, 128);
    public static final BaseColor GREEN = new BaseColor(0, 255, 0);
    public static final BaseColor LIGHT_GRAY = new BaseColor(192, 192, 192);
    public static final BaseColor MAGENTA = new BaseColor(255, 0, 255);
    public static final BaseColor ORANGE = new BaseColor(255, 200, 0);
    public static final BaseColor PINK = new BaseColor(255, 175, 175);
    public static final BaseColor RED = new BaseColor(255, 0, 0);
    public static final BaseColor WHITE = new BaseColor(255, 255, 255);
    public static final BaseColor YELLOW = new BaseColor(255, 255, 0);
    private int value;

    public BaseColor(int i, int i2, int i3, int i4) {
        setValue(i, i2, i3, i4);
    }

    public BaseColor(int i, int i2, int i3) {
        this(i, i2, i3, 255);
    }

    public BaseColor(float f, float f2, float f3, float f4) {
        this((int) (((double) (f * 255.0f)) + 0.5d), (int) (((double) (f2 * 255.0f)) + 0.5d), (int) (((double) (f3 * 255.0f)) + 0.5d), (int) (((double) (f4 * 255.0f)) + 0.5d));
    }

    public BaseColor(float f, float f2, float f3) {
        this(f, f2, f3, 1.0f);
    }

    public BaseColor(int i) {
        this.value = i;
    }

    public int getRGB() {
        return this.value;
    }

    public int getRed() {
        return (getRGB() >> 16) & 255;
    }

    public int getGreen() {
        return (getRGB() >> 8) & 255;
    }

    public int getBlue() {
        return (getRGB() >> 0) & 255;
    }

    public int getAlpha() {
        return (getRGB() >> 24) & 255;
    }

    public BaseColor brighter() {
        int red = getRed();
        int green = getGreen();
        int blue = getBlue();
        if (red == 0 && green == 0 && blue == 0) {
            return new BaseColor(3, 3, 3);
        }
        if (red > 0 && red < 3) {
            red = 3;
        }
        if (green > 0 && green < 3) {
            green = 3;
        }
        if (blue > 0 && blue < 3) {
            blue = 3;
        }
        return new BaseColor(Math.min((int) (((double) red) / FACTOR), 255), Math.min((int) (((double) green) / FACTOR), 255), Math.min((int) (((double) blue) / FACTOR), 255));
    }

    public BaseColor darker() {
        return new BaseColor(Math.max((int) (((double) getRed()) * FACTOR), 0), Math.max((int) (((double) getGreen()) * FACTOR), 0), Math.max((int) (((double) getBlue()) * FACTOR), 0));
    }

    public boolean equals(Object obj) {
        return (obj instanceof BaseColor) && ((BaseColor) obj).value == this.value;
    }

    public int hashCode() {
        return this.value;
    }

    /* access modifiers changed from: protected */
    public void setValue(int i, int i2, int i3, int i4) {
        validate(i);
        validate(i2);
        validate(i3);
        validate(i4);
        this.value = ((i & 255) << 16) | ((i4 & 255) << 24) | ((i2 & 255) << 8) | ((i3 & 255) << 0);
    }

    private static void validate(int i) {
        if (i < 0 || i > 255) {
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("color.value.outside.range.0.255", new Object[0]));
        }
    }

    public String toString() {
        return "Color value[" + Integer.toString(this.value, 16) + "]";
    }
}
