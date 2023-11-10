package com.itextpdf.awt.geom;

import com.itextpdf.awt.geom.Point2D;
import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.awt.geom.misc.Messages;
import java.util.NoSuchElementException;

public abstract class Line2D implements Shape, Cloneable {
    public static boolean linesIntersect(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        double d9 = d3 - d;
        double d10 = d4 - d2;
        double d11 = d5 - d;
        double d12 = d6 - d2;
        double d13 = d7 - d;
        double d14 = d8 - d2;
        double d15 = (d9 * d12) - (d11 * d10);
        double d16 = (d9 * d14) - (d13 * d10);
        if (d15 == 0.0d && d16 == 0.0d) {
            int i = (d9 > 0.0d ? 1 : (d9 == 0.0d ? 0 : -1));
            if (i == 0) {
                int i2 = (d10 > 0.0d ? 1 : (d10 == 0.0d ? 0 : -1));
                if (i2 == 0) {
                    return false;
                }
                if (d14 * d12 <= 0.0d) {
                    return true;
                }
                if (d12 * d10 >= 0.0d) {
                    if (i2 > 0) {
                        if (d12 <= d10 || d14 <= d10) {
                            return true;
                        }
                    } else if (d12 >= d10 || d14 >= d10) {
                        return true;
                    }
                }
                return false;
            } else if (d13 * d11 <= 0.0d) {
                return true;
            } else {
                if (d11 * d9 >= 0.0d) {
                    if (i > 0) {
                        if (d11 <= d9 || d13 <= d9) {
                            return true;
                        }
                    } else if (d11 >= d9 || d13 >= d9) {
                        return true;
                    }
                }
                return false;
            }
        } else {
            double d17 = (d11 * d14) - (d13 * d12);
            return d15 * d16 <= 0.0d && d17 * ((d15 + d17) - d16) <= 0.0d;
        }
    }

    public static double ptLineDistSq(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d3 - d;
        double d8 = d4 - d2;
        double d9 = ((d5 - d) * d8) - ((d6 - d2) * d7);
        return (d9 * d9) / ((d7 * d7) + (d8 * d8));
    }

    public static double ptSegDistSq(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7;
        double d8 = d3 - d;
        double d9 = d4 - d2;
        double d10 = d5 - d;
        double d11 = d6 - d2;
        if ((d10 * d8) + (d11 * d9) <= 0.0d) {
            d7 = (d10 * d10) + (d11 * d11);
        } else {
            double d12 = d8 - d10;
            double d13 = d9 - d11;
            if ((d12 * d8) + (d13 * d9) <= 0.0d) {
                d7 = (d13 * d13) + (d12 * d12);
            } else {
                double d14 = (d12 * d9) - (d13 * d8);
                d7 = (d14 * d14) / ((d8 * d8) + (d9 * d9));
            }
        }
        if (d7 < 0.0d) {
            return 0.0d;
        }
        return d7;
    }

    public static int relativeCCW(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d3 - d;
        double d8 = d4 - d2;
        double d9 = d5 - d;
        double d10 = d6 - d2;
        double d11 = (d9 * d8) - (d10 * d7);
        if (d11 == 0.0d) {
            d11 = (d9 * d7) + (d10 * d8);
            if (d11 > 0.0d) {
                d11 = ((d9 - d7) * d7) + ((d10 - d8) * d8);
                if (d11 < 0.0d) {
                    d11 = 0.0d;
                }
            }
        }
        if (d11 < 0.0d) {
            return -1;
        }
        return d11 > 0.0d ? 1 : 0;
    }

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

    public abstract Point2D getP1();

    public abstract Point2D getP2();

    public abstract double getX1();

    public abstract double getX2();

    public abstract double getY1();

    public abstract double getY2();

    public abstract void setLine(double d, double d2, double d3, double d4);

    public static class Float extends Line2D {

        /* renamed from: x1 */
        public float f378x1;

        /* renamed from: x2 */
        public float f379x2;

        /* renamed from: y1 */
        public float f380y1;

        /* renamed from: y2 */
        public float f381y2;

        public Float() {
        }

        public Float(float f, float f2, float f3, float f4) {
            setLine(f, f2, f3, f4);
        }

        public Float(Point2D point2D, Point2D point2D2) {
            setLine(point2D, point2D2);
        }

        public double getX1() {
            return (double) this.f378x1;
        }

        public double getY1() {
            return (double) this.f380y1;
        }

        public double getX2() {
            return (double) this.f379x2;
        }

        public double getY2() {
            return (double) this.f381y2;
        }

        public Point2D getP1() {
            return new Point2D.Float(this.f378x1, this.f380y1);
        }

        public Point2D getP2() {
            return new Point2D.Float(this.f379x2, this.f381y2);
        }

        public void setLine(double d, double d2, double d3, double d4) {
            this.f378x1 = (float) d;
            this.f380y1 = (float) d2;
            this.f379x2 = (float) d3;
            this.f381y2 = (float) d4;
        }

        public void setLine(float f, float f2, float f3, float f4) {
            this.f378x1 = f;
            this.f380y1 = f2;
            this.f379x2 = f3;
            this.f381y2 = f4;
        }

        public Rectangle2D getBounds2D() {
            float f;
            float f2;
            float f3 = this.f378x1;
            float f4 = this.f379x2;
            if (f3 < f4) {
                f = f4 - f3;
            } else {
                float f5 = f4;
                f = f3 - f4;
                f3 = f5;
            }
            float f6 = this.f380y1;
            float f7 = this.f381y2;
            if (f6 < f7) {
                f2 = f7 - f6;
            } else {
                float f8 = f7;
                f2 = f6 - f7;
                f6 = f8;
            }
            return new Rectangle2D.Float(f3, f6, f, f2);
        }
    }

    public static class Double extends Line2D {

        /* renamed from: x1 */
        public double f374x1;

        /* renamed from: x2 */
        public double f375x2;

        /* renamed from: y1 */
        public double f376y1;

        /* renamed from: y2 */
        public double f377y2;

        public Double() {
        }

        public Double(double d, double d2, double d3, double d4) {
            setLine(d, d2, d3, d4);
        }

        public Double(Point2D point2D, Point2D point2D2) {
            setLine(point2D, point2D2);
        }

        public double getX1() {
            return this.f374x1;
        }

        public double getY1() {
            return this.f376y1;
        }

        public double getX2() {
            return this.f375x2;
        }

        public double getY2() {
            return this.f377y2;
        }

        public Point2D getP1() {
            return new Point2D.Double(this.f374x1, this.f376y1);
        }

        public Point2D getP2() {
            return new Point2D.Double(this.f375x2, this.f377y2);
        }

        public void setLine(double d, double d2, double d3, double d4) {
            this.f374x1 = d;
            this.f376y1 = d2;
            this.f375x2 = d3;
            this.f377y2 = d4;
        }

        public Rectangle2D getBounds2D() {
            double d;
            double d2;
            double d3;
            double d4;
            double d5 = this.f374x1;
            double d6 = this.f375x2;
            if (d5 < d6) {
                d2 = d5;
                d = d6 - d5;
            } else {
                d = d5 - d6;
                d2 = d6;
            }
            double d7 = this.f376y1;
            double d8 = this.f377y2;
            if (d7 < d8) {
                d4 = d7;
                d3 = d8 - d7;
            } else {
                d3 = d7 - d8;
                d4 = d8;
            }
            return new Rectangle2D.Double(d2, d4, d, d3);
        }
    }

    class Iterator implements PathIterator {
        int index;

        /* renamed from: t */
        AffineTransform f382t;

        /* renamed from: x1 */
        double f383x1;

        /* renamed from: x2 */
        double f384x2;

        /* renamed from: y1 */
        double f385y1;

        /* renamed from: y2 */
        double f386y2;

        public int getWindingRule() {
            return 1;
        }

        Iterator(Line2D line2D, AffineTransform affineTransform) {
            this.f383x1 = line2D.getX1();
            this.f385y1 = line2D.getY1();
            this.f384x2 = line2D.getX2();
            this.f386y2 = line2D.getY2();
            this.f382t = affineTransform;
        }

        public boolean isDone() {
            return this.index > 1;
        }

        public void next() {
            this.index++;
        }

        public int currentSegment(double[] dArr) {
            if (!isDone()) {
                int i = 0;
                if (this.index == 0) {
                    dArr[0] = this.f383x1;
                    dArr[1] = this.f385y1;
                } else {
                    dArr[0] = this.f384x2;
                    dArr[1] = this.f386y2;
                    i = 1;
                }
                AffineTransform affineTransform = this.f382t;
                if (affineTransform != null) {
                    affineTransform.transform(dArr, 0, dArr, 0, 1);
                }
                return i;
            }
            throw new NoSuchElementException(Messages.getString("awt.4B"));
        }

        public int currentSegment(float[] fArr) {
            if (!isDone()) {
                int i = 0;
                if (this.index == 0) {
                    fArr[0] = (float) this.f383x1;
                    fArr[1] = (float) this.f385y1;
                } else {
                    fArr[0] = (float) this.f384x2;
                    fArr[1] = (float) this.f386y2;
                    i = 1;
                }
                AffineTransform affineTransform = this.f382t;
                if (affineTransform != null) {
                    affineTransform.transform(fArr, 0, fArr, 0, 1);
                }
                return i;
            }
            throw new NoSuchElementException(Messages.getString("awt.4B"));
        }
    }

    protected Line2D() {
    }

    public void setLine(Point2D point2D, Point2D point2D2) {
        setLine(point2D.getX(), point2D.getY(), point2D2.getX(), point2D2.getY());
    }

    public void setLine(Line2D line2D) {
        setLine(line2D.getX1(), line2D.getY1(), line2D.getX2(), line2D.getY2());
    }

    public Rectangle getBounds() {
        return getBounds2D().getBounds();
    }

    public int relativeCCW(double d, double d2) {
        return relativeCCW(getX1(), getY1(), getX2(), getY2(), d, d2);
    }

    public int relativeCCW(Point2D point2D) {
        return relativeCCW(getX1(), getY1(), getX2(), getY2(), point2D.getX(), point2D.getY());
    }

    public boolean intersectsLine(double d, double d2, double d3, double d4) {
        return linesIntersect(d, d2, d3, d4, getX1(), getY1(), getX2(), getY2());
    }

    public boolean intersectsLine(Line2D line2D) {
        return linesIntersect(line2D.getX1(), line2D.getY1(), line2D.getX2(), line2D.getY2(), getX1(), getY1(), getX2(), getY2());
    }

    public static double ptSegDist(double d, double d2, double d3, double d4, double d5, double d6) {
        return Math.sqrt(ptSegDistSq(d, d2, d3, d4, d5, d6));
    }

    public double ptSegDistSq(double d, double d2) {
        return ptSegDistSq(getX1(), getY1(), getX2(), getY2(), d, d2);
    }

    public double ptSegDistSq(Point2D point2D) {
        return ptSegDistSq(getX1(), getY1(), getX2(), getY2(), point2D.getX(), point2D.getY());
    }

    public double ptSegDist(double d, double d2) {
        return ptSegDist(getX1(), getY1(), getX2(), getY2(), d, d2);
    }

    public double ptSegDist(Point2D point2D) {
        return ptSegDist(getX1(), getY1(), getX2(), getY2(), point2D.getX(), point2D.getY());
    }

    public static double ptLineDist(double d, double d2, double d3, double d4, double d5, double d6) {
        return Math.sqrt(ptLineDistSq(d, d2, d3, d4, d5, d6));
    }

    public double ptLineDistSq(double d, double d2) {
        return ptLineDistSq(getX1(), getY1(), getX2(), getY2(), d, d2);
    }

    public double ptLineDistSq(Point2D point2D) {
        return ptLineDistSq(getX1(), getY1(), getX2(), getY2(), point2D.getX(), point2D.getY());
    }

    public double ptLineDist(double d, double d2) {
        return ptLineDist(getX1(), getY1(), getX2(), getY2(), d, d2);
    }

    public double ptLineDist(Point2D point2D) {
        return ptLineDist(getX1(), getY1(), getX2(), getY2(), point2D.getX(), point2D.getY());
    }

    public boolean intersects(double d, double d2, double d3, double d4) {
        return intersects(new Rectangle2D.Double(d, d2, d3, d4));
    }

    public boolean intersects(Rectangle2D rectangle2D) {
        return rectangle2D.intersectsLine(getX1(), getY1(), getX2(), getY2());
    }

    public PathIterator getPathIterator(AffineTransform affineTransform) {
        return new Iterator(this, affineTransform);
    }

    public PathIterator getPathIterator(AffineTransform affineTransform, double d) {
        return new Iterator(this, affineTransform);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }
}
