package com.itextpdf.awt.geom;

import com.itextpdf.awt.geom.Point2D;
import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.awt.geom.misc.Messages;
import com.itextpdf.awt.geom.p017gl.Crossing;
import java.util.NoSuchElementException;

public abstract class CubicCurve2D implements Shape, Cloneable {
    public abstract Point2D getCtrlP1();

    public abstract Point2D getCtrlP2();

    public abstract double getCtrlX1();

    public abstract double getCtrlX2();

    public abstract double getCtrlY1();

    public abstract double getCtrlY2();

    public abstract Point2D getP1();

    public abstract Point2D getP2();

    public abstract double getX1();

    public abstract double getX2();

    public abstract double getY1();

    public abstract double getY2();

    public abstract void setCurve(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8);

    public static class Float extends CubicCurve2D {
        public float ctrlx1;
        public float ctrlx2;
        public float ctrly1;
        public float ctrly2;

        /* renamed from: x1 */
        public float f363x1;

        /* renamed from: x2 */
        public float f364x2;

        /* renamed from: y1 */
        public float f365y1;

        /* renamed from: y2 */
        public float f366y2;

        public Float() {
        }

        public Float(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
            setCurve(f, f2, f3, f4, f5, f6, f7, f8);
        }

        public double getX1() {
            return (double) this.f363x1;
        }

        public double getY1() {
            return (double) this.f365y1;
        }

        public double getCtrlX1() {
            return (double) this.ctrlx1;
        }

        public double getCtrlY1() {
            return (double) this.ctrly1;
        }

        public double getCtrlX2() {
            return (double) this.ctrlx2;
        }

        public double getCtrlY2() {
            return (double) this.ctrly2;
        }

        public double getX2() {
            return (double) this.f364x2;
        }

        public double getY2() {
            return (double) this.f366y2;
        }

        public Point2D getP1() {
            return new Point2D.Float(this.f363x1, this.f365y1);
        }

        public Point2D getCtrlP1() {
            return new Point2D.Float(this.ctrlx1, this.ctrly1);
        }

        public Point2D getCtrlP2() {
            return new Point2D.Float(this.ctrlx2, this.ctrly2);
        }

        public Point2D getP2() {
            return new Point2D.Float(this.f364x2, this.f366y2);
        }

        public void setCurve(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
            this.f363x1 = (float) d;
            this.f365y1 = (float) d2;
            this.ctrlx1 = (float) d3;
            this.ctrly1 = (float) d4;
            this.ctrlx2 = (float) d5;
            this.ctrly2 = (float) d6;
            this.f364x2 = (float) d7;
            this.f366y2 = (float) d8;
        }

        public void setCurve(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
            this.f363x1 = f;
            this.f365y1 = f2;
            this.ctrlx1 = f3;
            this.ctrly1 = f4;
            this.ctrlx2 = f5;
            this.ctrly2 = f6;
            this.f364x2 = f7;
            this.f366y2 = f8;
        }

        public Rectangle2D getBounds2D() {
            float min = Math.min(Math.min(this.f363x1, this.f364x2), Math.min(this.ctrlx1, this.ctrlx2));
            float min2 = Math.min(Math.min(this.f365y1, this.f366y2), Math.min(this.ctrly1, this.ctrly2));
            return new Rectangle2D.Float(min, min2, Math.max(Math.max(this.f363x1, this.f364x2), Math.max(this.ctrlx1, this.ctrlx2)) - min, Math.max(Math.max(this.f365y1, this.f366y2), Math.max(this.ctrly1, this.ctrly2)) - min2);
        }
    }

    public static class Double extends CubicCurve2D {
        public double ctrlx1;
        public double ctrlx2;
        public double ctrly1;
        public double ctrly2;

        /* renamed from: x1 */
        public double f359x1;

        /* renamed from: x2 */
        public double f360x2;

        /* renamed from: y1 */
        public double f361y1;

        /* renamed from: y2 */
        public double f362y2;

        public Double() {
        }

        public Double(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
            setCurve(d, d2, d3, d4, d5, d6, d7, d8);
        }

        public double getX1() {
            return this.f359x1;
        }

        public double getY1() {
            return this.f361y1;
        }

        public double getCtrlX1() {
            return this.ctrlx1;
        }

        public double getCtrlY1() {
            return this.ctrly1;
        }

        public double getCtrlX2() {
            return this.ctrlx2;
        }

        public double getCtrlY2() {
            return this.ctrly2;
        }

        public double getX2() {
            return this.f360x2;
        }

        public double getY2() {
            return this.f362y2;
        }

        public Point2D getP1() {
            return new Point2D.Double(this.f359x1, this.f361y1);
        }

        public Point2D getCtrlP1() {
            return new Point2D.Double(this.ctrlx1, this.ctrly1);
        }

        public Point2D getCtrlP2() {
            return new Point2D.Double(this.ctrlx2, this.ctrly2);
        }

        public Point2D getP2() {
            return new Point2D.Double(this.f360x2, this.f362y2);
        }

        public void setCurve(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
            this.f359x1 = d;
            this.f361y1 = d2;
            this.ctrlx1 = d3;
            this.ctrly1 = d4;
            this.ctrlx2 = d5;
            this.ctrly2 = d6;
            this.f360x2 = d7;
            this.f362y2 = d8;
        }

        public Rectangle2D getBounds2D() {
            double min = Math.min(Math.min(this.f359x1, this.f360x2), Math.min(this.ctrlx1, this.ctrlx2));
            double min2 = Math.min(Math.min(this.f361y1, this.f362y2), Math.min(this.ctrly1, this.ctrly2));
            return new Rectangle2D.Double(min, min2, Math.max(Math.max(this.f359x1, this.f360x2), Math.max(this.ctrlx1, this.ctrlx2)) - min, Math.max(Math.max(this.f361y1, this.f362y2), Math.max(this.ctrly1, this.ctrly2)) - min2);
        }
    }

    class Iterator implements PathIterator {

        /* renamed from: c */
        CubicCurve2D f367c;
        int index;

        /* renamed from: t */
        AffineTransform f368t;

        public int getWindingRule() {
            return 1;
        }

        Iterator(CubicCurve2D cubicCurve2D, AffineTransform affineTransform) {
            this.f367c = cubicCurve2D;
            this.f368t = affineTransform;
        }

        public boolean isDone() {
            return this.index > 1;
        }

        public void next() {
            this.index++;
        }

        public int currentSegment(double[] dArr) {
            int i;
            if (!isDone()) {
                int i2 = 0;
                if (this.index == 0) {
                    dArr[0] = this.f367c.getX1();
                    dArr[1] = this.f367c.getY1();
                    i = 1;
                } else {
                    dArr[0] = this.f367c.getCtrlX1();
                    dArr[1] = this.f367c.getCtrlY1();
                    dArr[2] = this.f367c.getCtrlX2();
                    dArr[3] = this.f367c.getCtrlY2();
                    dArr[4] = this.f367c.getX2();
                    dArr[5] = this.f367c.getY2();
                    i2 = 3;
                    i = 3;
                }
                AffineTransform affineTransform = this.f368t;
                if (affineTransform != null) {
                    affineTransform.transform(dArr, 0, dArr, 0, i);
                }
                return i2;
            }
            throw new NoSuchElementException(Messages.getString("awt.4B"));
        }

        public int currentSegment(float[] fArr) {
            int i;
            if (!isDone()) {
                int i2 = 0;
                if (this.index == 0) {
                    fArr[0] = (float) this.f367c.getX1();
                    fArr[1] = (float) this.f367c.getY1();
                    i = 1;
                } else {
                    fArr[0] = (float) this.f367c.getCtrlX1();
                    fArr[1] = (float) this.f367c.getCtrlY1();
                    fArr[2] = (float) this.f367c.getCtrlX2();
                    fArr[3] = (float) this.f367c.getCtrlY2();
                    fArr[4] = (float) this.f367c.getX2();
                    fArr[5] = (float) this.f367c.getY2();
                    i2 = 3;
                    i = 3;
                }
                AffineTransform affineTransform = this.f368t;
                if (affineTransform != null) {
                    affineTransform.transform(fArr, 0, fArr, 0, i);
                }
                return i2;
            }
            throw new NoSuchElementException(Messages.getString("awt.4B"));
        }
    }

    protected CubicCurve2D() {
    }

    public void setCurve(Point2D point2D, Point2D point2D2, Point2D point2D3, Point2D point2D4) {
        setCurve(point2D.getX(), point2D.getY(), point2D2.getX(), point2D2.getY(), point2D3.getX(), point2D3.getY(), point2D4.getX(), point2D4.getY());
    }

    public void setCurve(double[] dArr, int i) {
        setCurve(dArr[i + 0], dArr[i + 1], dArr[i + 2], dArr[i + 3], dArr[i + 4], dArr[i + 5], dArr[i + 6], dArr[i + 7]);
    }

    public void setCurve(Point2D[] point2DArr, int i) {
        int i2 = i + 0;
        int i3 = i + 1;
        int i4 = i + 2;
        int i5 = i + 3;
        setCurve(point2DArr[i2].getX(), point2DArr[i2].getY(), point2DArr[i3].getX(), point2DArr[i3].getY(), point2DArr[i4].getX(), point2DArr[i4].getY(), point2DArr[i5].getX(), point2DArr[i5].getY());
    }

    public void setCurve(CubicCurve2D cubicCurve2D) {
        setCurve(cubicCurve2D.getX1(), cubicCurve2D.getY1(), cubicCurve2D.getCtrlX1(), cubicCurve2D.getCtrlY1(), cubicCurve2D.getCtrlX2(), cubicCurve2D.getCtrlY2(), cubicCurve2D.getX2(), cubicCurve2D.getY2());
    }

    public double getFlatnessSq() {
        return getFlatnessSq(getX1(), getY1(), getCtrlX1(), getCtrlY1(), getCtrlX2(), getCtrlY2(), getX2(), getY2());
    }

    public static double getFlatnessSq(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        return Math.max(Line2D.ptSegDistSq(d, d2, d7, d8, d3, d4), Line2D.ptSegDistSq(d, d2, d7, d8, d5, d6));
    }

    public static double getFlatnessSq(double[] dArr, int i) {
        return getFlatnessSq(dArr[i + 0], dArr[i + 1], dArr[i + 2], dArr[i + 3], dArr[i + 4], dArr[i + 5], dArr[i + 6], dArr[i + 7]);
    }

    public double getFlatness() {
        return getFlatness(getX1(), getY1(), getCtrlX1(), getCtrlY1(), getCtrlX2(), getCtrlY2(), getX2(), getY2());
    }

    public static double getFlatness(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        return Math.sqrt(getFlatnessSq(d, d2, d3, d4, d5, d6, d7, d8));
    }

    public static double getFlatness(double[] dArr, int i) {
        return getFlatness(dArr[i + 0], dArr[i + 1], dArr[i + 2], dArr[i + 3], dArr[i + 4], dArr[i + 5], dArr[i + 6], dArr[i + 7]);
    }

    public void subdivide(CubicCurve2D cubicCurve2D, CubicCurve2D cubicCurve2D2) {
        subdivide(this, cubicCurve2D, cubicCurve2D2);
    }

    public static void subdivide(CubicCurve2D cubicCurve2D, CubicCurve2D cubicCurve2D2, CubicCurve2D cubicCurve2D3) {
        double x1 = cubicCurve2D.getX1();
        double y1 = cubicCurve2D.getY1();
        double ctrlX1 = cubicCurve2D.getCtrlX1();
        double ctrlY1 = cubicCurve2D.getCtrlY1();
        double ctrlX2 = cubicCurve2D.getCtrlX2();
        double ctrlY2 = cubicCurve2D.getCtrlY2();
        double x2 = cubicCurve2D.getX2();
        double y2 = cubicCurve2D.getY2();
        double d = (ctrlX1 + ctrlX2) / 2.0d;
        double d2 = (ctrlY1 + ctrlY2) / 2.0d;
        double d3 = (ctrlX1 + x1) / 2.0d;
        double d4 = (ctrlY1 + y1) / 2.0d;
        double d5 = (x2 + ctrlX2) / 2.0d;
        double d6 = (y2 + ctrlY2) / 2.0d;
        double d7 = (d3 + d) / 2.0d;
        double d8 = (d4 + d2) / 2.0d;
        double d9 = (d5 + d) / 2.0d;
        double d10 = (d6 + d2) / 2.0d;
        double d11 = (d7 + d9) / 2.0d;
        double d12 = (d8 + d10) / 2.0d;
        if (cubicCurve2D2 != null) {
            cubicCurve2D2.setCurve(x1, y1, d3, d4, d7, d8, d11, d12);
        }
        if (cubicCurve2D3 != null) {
            cubicCurve2D3.setCurve(d11, d12, d9, d10, d5, d6, x2, y2);
        }
    }

    public static void subdivide(double[] dArr, int i, double[] dArr2, int i2, double[] dArr3, int i3) {
        double d = dArr[i + 0];
        double d2 = dArr[i + 1];
        double d3 = dArr[i + 2];
        double d4 = dArr[i + 3];
        double d5 = dArr[i + 4];
        double d6 = dArr[i + 5];
        double d7 = dArr[i + 6];
        double d8 = dArr[i + 7];
        double d9 = (d3 + d5) / 2.0d;
        double d10 = (d4 + d6) / 2.0d;
        double d11 = (d3 + d) / 2.0d;
        double d12 = (d4 + d2) / 2.0d;
        double d13 = (d5 + d7) / 2.0d;
        double d14 = (d6 + d8) / 2.0d;
        double d15 = (d11 + d9) / 2.0d;
        double d16 = (d12 + d10) / 2.0d;
        double d17 = (d13 + d9) / 2.0d;
        double d18 = (d14 + d10) / 2.0d;
        double d19 = (d15 + d17) / 2.0d;
        double d20 = (d16 + d18) / 2.0d;
        if (dArr2 != null) {
            dArr2[i2 + 0] = d;
            dArr2[i2 + 1] = d2;
            dArr2[i2 + 2] = d11;
            dArr2[i2 + 3] = d12;
            dArr2[i2 + 4] = d15;
            dArr2[i2 + 5] = d16;
            dArr2[i2 + 6] = d19;
            dArr2[i2 + 7] = d20;
        }
        if (dArr3 != null) {
            dArr3[i3 + 0] = d19;
            dArr3[i3 + 1] = d20;
            dArr3[i3 + 2] = d17;
            dArr3[i3 + 3] = d18;
            dArr3[i3 + 4] = d13;
            dArr3[i3 + 5] = d14;
            dArr3[i3 + 6] = d7;
            dArr3[i3 + 7] = d8;
        }
    }

    public static int solveCubic(double[] dArr) {
        return solveCubic(dArr, dArr);
    }

    public static int solveCubic(double[] dArr, double[] dArr2) {
        return Crossing.solveCubic(dArr, dArr2);
    }

    public boolean contains(double d, double d2) {
        return Crossing.isInsideEvenOdd(Crossing.crossShape(this, d, d2));
    }

    public boolean contains(double d, double d2, double d3, double d4) {
        int intersectShape = Crossing.intersectShape(this, d, d2, d3, d4);
        return intersectShape != 255 && Crossing.isInsideEvenOdd(intersectShape);
    }

    public boolean intersects(double d, double d2, double d3, double d4) {
        int intersectShape = Crossing.intersectShape(this, d, d2, d3, d4);
        return intersectShape == 255 || Crossing.isInsideEvenOdd(intersectShape);
    }

    public boolean contains(Point2D point2D) {
        return contains(point2D.getX(), point2D.getY());
    }

    public boolean intersects(Rectangle2D rectangle2D) {
        return intersects(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    public boolean contains(Rectangle2D rectangle2D) {
        return contains(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    public Rectangle getBounds() {
        return getBounds2D().getBounds();
    }

    public PathIterator getPathIterator(AffineTransform affineTransform) {
        return new Iterator(this, affineTransform);
    }

    public PathIterator getPathIterator(AffineTransform affineTransform, double d) {
        return new FlatteningPathIterator(getPathIterator(affineTransform), d);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }
}
