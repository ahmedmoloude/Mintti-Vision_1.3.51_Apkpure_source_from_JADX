package androidx.core.graphics;

import android.graphics.Rect;

public final class Insets {
    public static final Insets NONE = new Insets(0, 0, 0, 0);
    public final int bottom;
    public final int left;
    public final int right;

    /* renamed from: top  reason: collision with root package name */
    public final int f2161top;

    private Insets(int i, int i2, int i3, int i4) {
        this.left = i;
        this.f2161top = i2;
        this.right = i3;
        this.bottom = i4;
    }

    /* renamed from: of */
    public static Insets m38of(int i, int i2, int i3, int i4) {
        if (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            return NONE;
        }
        return new Insets(i, i2, i3, i4);
    }

    /* renamed from: of */
    public static Insets m39of(Rect rect) {
        return m38of(rect.left, rect.top, rect.right, rect.bottom);
    }

    public static Insets add(Insets insets, Insets insets2) {
        return m38of(insets.left + insets2.left, insets.f2161top + insets2.f2161top, insets.right + insets2.right, insets.bottom + insets2.bottom);
    }

    public static Insets subtract(Insets insets, Insets insets2) {
        return m38of(insets.left - insets2.left, insets.f2161top - insets2.f2161top, insets.right - insets2.right, insets.bottom - insets2.bottom);
    }

    public static Insets max(Insets insets, Insets insets2) {
        return m38of(Math.max(insets.left, insets2.left), Math.max(insets.f2161top, insets2.f2161top), Math.max(insets.right, insets2.right), Math.max(insets.bottom, insets2.bottom));
    }

    public static Insets min(Insets insets, Insets insets2) {
        return m38of(Math.min(insets.left, insets2.left), Math.min(insets.f2161top, insets2.f2161top), Math.min(insets.right, insets2.right), Math.min(insets.bottom, insets2.bottom));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Insets insets = (Insets) obj;
        return this.bottom == insets.bottom && this.left == insets.left && this.right == insets.right && this.f2161top == insets.f2161top;
    }

    public int hashCode() {
        return (((((this.left * 31) + this.f2161top) * 31) + this.right) * 31) + this.bottom;
    }

    public String toString() {
        return "Insets{left=" + this.left + ", top=" + this.f2161top + ", right=" + this.right + ", bottom=" + this.bottom + '}';
    }

    @Deprecated
    public static Insets wrap(android.graphics.Insets insets) {
        return toCompatInsets(insets);
    }

    public static Insets toCompatInsets(android.graphics.Insets insets) {
        return m38of(insets.left, insets.top, insets.right, insets.bottom);
    }

    public android.graphics.Insets toPlatformInsets() {
        return android.graphics.Insets.of(this.left, this.f2161top, this.right, this.bottom);
    }
}
