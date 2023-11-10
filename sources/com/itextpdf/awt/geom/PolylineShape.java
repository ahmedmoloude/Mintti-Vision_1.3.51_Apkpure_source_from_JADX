package com.itextpdf.awt.geom;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class PolylineShape implements Shape {

    /* renamed from: np */
    protected int f393np;

    /* renamed from: x */
    protected int[] f394x;

    /* renamed from: y */
    protected int[] f395y;

    public boolean contains(double d, double d2) {
        return false;
    }

    public boolean contains(double d, double d2, double d3, double d4) {
        return false;
    }

    public boolean contains(Point2D point2D) {
        return false;
    }

    public boolean contains(Rectangle2D rectangle2D) {
        return false;
    }

    public PolylineShape(int[] iArr, int[] iArr2, int i) {
        this.f393np = i;
        int[] iArr3 = new int[i];
        this.f394x = iArr3;
        this.f395y = new int[i];
        System.arraycopy(iArr, 0, iArr3, 0, i);
        System.arraycopy(iArr2, 0, this.f395y, 0, this.f393np);
    }

    public Rectangle2D getBounds2D() {
        int[] rect = rect();
        if (rect == null) {
            return null;
        }
        return new Rectangle2D.Double((double) rect[0], (double) rect[1], (double) rect[2], (double) rect[3]);
    }

    public Rectangle getBounds() {
        return getBounds2D().getBounds();
    }

    private int[] rect() {
        if (this.f393np == 0) {
            return null;
        }
        int[] iArr = this.f394x;
        int i = iArr[0];
        int[] iArr2 = this.f395y;
        int i2 = iArr2[0];
        int i3 = iArr[0];
        int i4 = iArr2[0];
        for (int i5 = 1; i5 < this.f393np; i5++) {
            int[] iArr3 = this.f394x;
            if (iArr3[i5] < i) {
                i = iArr3[i5];
            } else if (iArr3[i5] > i3) {
                i3 = iArr3[i5];
            }
            int[] iArr4 = this.f395y;
            if (iArr4[i5] < i2) {
                i2 = iArr4[i5];
            } else if (iArr4[i5] > i4) {
                i4 = iArr4[i5];
            }
        }
        return new int[]{i, i2, i3 - i, i4 - i2};
    }

    public boolean intersects(double d, double d2, double d3, double d4) {
        return intersects(new Rectangle2D.Double(d, d2, d3, d4));
    }

    public boolean intersects(Rectangle2D rectangle2D) {
        if (this.f393np == 0) {
            return false;
        }
        int[] iArr = this.f394x;
        int[] iArr2 = this.f395y;
        Line2D.Double doubleR = new Line2D.Double((double) iArr[0], (double) iArr2[0], (double) iArr[0], (double) iArr2[0]);
        for (int i = 1; i < this.f393np; i++) {
            int[] iArr3 = this.f394x;
            int i2 = i - 1;
            int[] iArr4 = this.f395y;
            doubleR.setLine((double) iArr3[i2], (double) iArr4[i2], (double) iArr3[i], (double) iArr4[i]);
            if (doubleR.intersects(rectangle2D)) {
                return true;
            }
        }
        return false;
    }

    public PathIterator getPathIterator(AffineTransform affineTransform) {
        return new PolylineShapeIterator(this, affineTransform);
    }

    public PathIterator getPathIterator(AffineTransform affineTransform, double d) {
        return new PolylineShapeIterator(this, affineTransform);
    }
}
