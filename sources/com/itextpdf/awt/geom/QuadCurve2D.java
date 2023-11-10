package com.itextpdf.awt.geom;

import com.itextpdf.awt.geom.Point2D;
import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.awt.geom.misc.Messages;
import com.itextpdf.awt.geom.p017gl.Crossing;
import java.util.NoSuchElementException;

public abstract class QuadCurve2D implements Shape, Cloneable {
    public abstract Point2D getCtrlPt();

    public abstract double getCtrlX();

    public abstract double getCtrlY();

    public abstract Point2D getP1();

    public abstract Point2D getP2();

    public abstract double getX1();

    public abstract double getX2();

    public abstract double getY1();

    public abstract double getY2();

    public abstract void setCurve(double d, double d2, double d3, double d4, double d5, double d6);

    public static class Float extends QuadCurve2D {
        public float ctrlx;
        public float ctrly;

        /* renamed from: x1 */
        public float f400x1;

        /* renamed from: x2 */
        public float f401x2;

        /* renamed from: y1 */
        public float f402y1;

        /* renamed from: y2 */
        public float f403y2;

        public Float() {
        }

        public Float(float f, float f2, float f3, float f4, float f5, float f6) {
            setCurve(f, f2, f3, f4, f5, f6);
        }

        public double getX1() {
            return (double) this.f400x1;
        }

        public double getY1() {
            return (double) this.f402y1;
        }

        public double getCtrlX() {
            return (double) this.ctrlx;
        }

        public double getCtrlY() {
            return (double) this.ctrly;
        }

        public double getX2() {
            return (double) this.f401x2;
        }

        public double getY2() {
            return (double) this.f403y2;
        }

        public Point2D getP1() {
            return new Point2D.Float(this.f400x1, this.f402y1);
        }

        public Point2D getCtrlPt() {
            return new Point2D.Float(this.ctrlx, this.ctrly);
        }

        public Point2D getP2() {
            return new Point2D.Float(this.f401x2, this.f403y2);
        }

        public void setCurve(double d, double d2, double d3, double d4, double d5, double d6) {
            this.f400x1 = (float) d;
            this.f402y1 = (float) d2;
            this.ctrlx = (float) d3;
            this.ctrly = (float) d4;
            this.f401x2 = (float) d5;
            this.f403y2 = (float) d6;
        }

        public void setCurve(float f, float f2, float f3, float f4, float f5, float f6) {
            this.f400x1 = f;
            this.f402y1 = f2;
            this.ctrlx = f3;
            this.ctrly = f4;
            this.f401x2 = f5;
            this.f403y2 = f6;
        }

        public Rectangle2D getBounds2D() {
            float min = Math.min(Math.min(this.f400x1, this.f401x2), this.ctrlx);
            float min2 = Math.min(Math.min(this.f402y1, this.f403y2), this.ctrly);
            return new Rectangle2D.Float(min, min2, Math.max(Math.max(this.f400x1, this.f401x2), this.ctrlx) - min, Math.max(Math.max(this.f402y1, this.f403y2), this.ctrly) - min2);
        }
    }

    public static class Double extends QuadCurve2D {
        public double ctrlx;
        public double ctrly;

        /* renamed from: x1 */
        public double f396x1;

        /* renamed from: x2 */
        public double f397x2;

        /* renamed from: y1 */
        public double f398y1;

        /* renamed from: y2 */
        public double f399y2;

        public Double() {
        }

        public Double(double d, double d2, double d3, double d4, double d5, double d6) {
            setCurve(d, d2, d3, d4, d5, d6);
        }

        public double getX1() {
            return this.f396x1;
        }

        public double getY1() {
            return this.f398y1;
        }

        public double getCtrlX() {
            return this.ctrlx;
        }

        public double getCtrlY() {
            return this.ctrly;
        }

        public double getX2() {
            return this.f397x2;
        }

        public double getY2() {
            return this.f399y2;
        }

        public Point2D getP1() {
            return new Point2D.Double(this.f396x1, this.f398y1);
        }

        public Point2D getCtrlPt() {
            return new Point2D.Double(this.ctrlx, this.ctrly);
        }

        public Point2D getP2() {
            return new Point2D.Double(this.f397x2, this.f399y2);
        }

        public void setCurve(double d, double d2, double d3, double d4, double d5, double d6) {
            this.f396x1 = d;
            this.f398y1 = d2;
            this.ctrlx = d3;
            this.ctrly = d4;
            this.f397x2 = d5;
            this.f399y2 = d6;
        }

        public Rectangle2D getBounds2D() {
            double min = Math.min(Math.min(this.f396x1, this.f397x2), this.ctrlx);
            double min2 = Math.min(Math.min(this.f398y1, this.f399y2), this.ctrly);
            return new Rectangle2D.Double(min, min2, Math.max(Math.max(this.f396x1, this.f397x2), this.ctrlx) - min, Math.max(Math.max(this.f398y1, this.f399y2), this.ctrly) - min2);
        }
    }

    class Iterator implements PathIterator {

        /* renamed from: c */
        QuadCurve2D f404c;
        int index;

        /* renamed from: t */
        AffineTransform f405t;

        public int getWindingRule() {
            return 1;
        }

        Iterator(QuadCurve2D quadCurve2D, AffineTransform affineTransform) {
            this.f404c = quadCurve2D;
            this.f405t = affineTransform;
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
                    dArr[0] = this.f404c.getX1();
                    dArr[1] = this.f404c.getY1();
                    i = 1;
                } else {
                    dArr[0] = this.f404c.getCtrlX();
                    dArr[1] = this.f404c.getCtrlY();
                    dArr[2] = this.f404c.getX2();
                    dArr[3] = this.f404c.getY2();
                    i2 = 2;
                    i = 2;
                }
                AffineTransform affineTransform = this.f405t;
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
                    fArr[0] = (float) this.f404c.getX1();
                    fArr[1] = (float) this.f404c.getY1();
                    i = 1;
                } else {
                    fArr[0] = (float) this.f404c.getCtrlX();
                    fArr[1] = (float) this.f404c.getCtrlY();
                    fArr[2] = (float) this.f404c.getX2();
                    fArr[3] = (float) this.f404c.getY2();
                    i2 = 2;
                    i = 2;
                }
                AffineTransform affineTransform = this.f405t;
                if (affineTransform != null) {
                    affineTransform.transform(fArr, 0, fArr, 0, i);
                }
                return i2;
            }
            throw new NoSuchElementException(Messages.getString("awt.4B"));
        }
    }

    protected QuadCurve2D() {
    }

    public void setCurve(Point2D point2D, Point2D point2D2, Point2D point2D3) {
        setCurve(point2D.getX(), point2D.getY(), point2D2.getX(), point2D2.getY(), point2D3.getX(), point2D3.getY());
    }

    public void setCurve(double[] dArr, int i) {
        setCurve(dArr[i + 0], dArr[i + 1], dArr[i + 2], dArr[i + 3], dArr[i + 4], dArr[i + 5]);
    }

    public void setCurve(Point2D[] point2DArr, int i) {
        int i2 = i + 0;
        double x = point2DArr[i2].getX();
        double y = point2DArr[i2].getY();
        int i3 = i + 1;
        double x2 = point2DArr[i3].getX();
        double y2 = point2DArr[i3].getY();
        int i4 = i + 2;
        setCurve(x, y, x2, y2, point2DArr[i4].getX(), point2DArr[i4].getY());
    }

    public void setCurve(QuadCurve2D quadCurve2D) {
        setCurve(quadCurve2D.getX1(), quadCurve2D.getY1(), quadCurve2D.getCtrlX(), quadCurve2D.getCtrlY(), quadCurve2D.getX2(), quadCurve2D.getY2());
    }

    public double getFlatnessSq() {
        return Line2D.ptSegDistSq(getX1(), getY1(), getX2(), getY2(), getCtrlX(), getCtrlY());
    }

    public static double getFlatnessSq(double d, double d2, double d3, double d4, double d5, double d6) {
        return Line2D.ptSegDistSq(d, d2, d5, d6, d3, d4);
    }

    public static double getFlatnessSq(double[] dArr, int i) {
        return Line2D.ptSegDistSq(dArr[i + 0], dArr[i + 1], dArr[i + 4], dArr[i + 5], dArr[i + 2], dArr[i + 3]);
    }

    public double getFlatness() {
        return Line2D.ptSegDist(getX1(), getY1(), getX2(), getY2(), getCtrlX(), getCtrlY());
    }

    public static double getFlatness(double d, double d2, double d3, double d4, double d5, double d6) {
        return Line2D.ptSegDist(d, d2, d5, d6, d3, d4);
    }

    public static double getFlatness(double[] dArr, int i) {
        return Line2D.ptSegDist(dArr[i + 0], dArr[i + 1], dArr[i + 4], dArr[i + 5], dArr[i + 2], dArr[i + 3]);
    }

    public void subdivide(QuadCurve2D quadCurve2D, QuadCurve2D quadCurve2D2) {
        subdivide(this, quadCurve2D, quadCurve2D2);
    }

    public static void subdivide(QuadCurve2D quadCurve2D, QuadCurve2D quadCurve2D2, QuadCurve2D quadCurve2D3) {
        double x1 = quadCurve2D.getX1();
        double y1 = quadCurve2D.getY1();
        double ctrlX = quadCurve2D.getCtrlX();
        double ctrlY = quadCurve2D.getCtrlY();
        double x2 = quadCurve2D.getX2();
        double y2 = quadCurve2D.getY2();
        double d = (x1 + ctrlX) / 2.0d;
        double d2 = (y1 + ctrlY) / 2.0d;
        double d3 = (x2 + ctrlX) / 2.0d;
        double d4 = (y2 + ctrlY) / 2.0d;
        double d5 = (d + d3) / 2.0d;
        double d6 = (d2 + d4) / 2.0d;
        if (quadCurve2D2 != null) {
            quadCurve2D2.setCurve(x1, y1, d, d2, d5, d6);
        }
        if (quadCurve2D3 != null) {
            quadCurve2D3.setCurve(d5, d6, d3, d4, x2, y2);
        }
    }

    public static void subdivide(double[] dArr, int i, double[] dArr2, int i2, double[] dArr3, int i3) {
        double d = dArr[i + 0];
        double d2 = dArr[i + 1];
        double d3 = dArr[i + 2];
        double d4 = dArr[i + 3];
        double d5 = dArr[i + 4];
        double d6 = dArr[i + 5];
        double d7 = (d + d3) / 2.0d;
        double d8 = (d2 + d4) / 2.0d;
        double d9 = (d3 + d5) / 2.0d;
        double d10 = (d4 + d6) / 2.0d;
        double d11 = (d7 + d9) / 2.0d;
        double d12 = (d8 + d10) / 2.0d;
        if (dArr2 != null) {
            dArr2[i2 + 0] = d;
            dArr2[i2 + 1] = d2;
            dArr2[i2 + 2] = d7;
            dArr2[i2 + 3] = d8;
            dArr2[i2 + 4] = d11;
            dArr2[i2 + 5] = d12;
        }
        if (dArr3 != null) {
            dArr3[i3 + 0] = d11;
            dArr3[i3 + 1] = d12;
            dArr3[i3 + 2] = d9;
            dArr3[i3 + 3] = d10;
            dArr3[i3 + 4] = d5;
            dArr3[i3 + 5] = d6;
        }
    }

    public static int solveQuadratic(double[] dArr) {
        return solveQuadratic(dArr, dArr);
    }

    public static int solveQuadratic(double[] dArr, double[] dArr2) {
        return Crossing.solveQuad(dArr, dArr2);
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
