package com.itextpdf.awt.geom;

import com.itextpdf.awt.geom.misc.HashCode;
import com.itextpdf.awt.geom.misc.Messages;
import java.util.NoSuchElementException;

public abstract class Rectangle2D extends RectangularShape {
    public static final int OUT_BOTTOM = 8;
    public static final int OUT_LEFT = 1;
    public static final int OUT_RIGHT = 4;
    public static final int OUT_TOP = 2;

    public abstract Rectangle2D createIntersection(Rectangle2D rectangle2D);

    public abstract Rectangle2D createUnion(Rectangle2D rectangle2D);

    public abstract int outcode(double d, double d2);

    public abstract void setRect(double d, double d2, double d3, double d4);

    public static class Float extends Rectangle2D {
        public float height;
        public float width;

        /* renamed from: x */
        public float f410x;

        /* renamed from: y */
        public float f411y;

        public Float() {
        }

        public Float(float f, float f2, float f3, float f4) {
            setRect(f, f2, f3, f4);
        }

        public double getX() {
            return (double) this.f410x;
        }

        public double getY() {
            return (double) this.f411y;
        }

        public double getWidth() {
            return (double) this.width;
        }

        public double getHeight() {
            return (double) this.height;
        }

        public boolean isEmpty() {
            return this.width <= 0.0f || this.height <= 0.0f;
        }

        public void setRect(float f, float f2, float f3, float f4) {
            this.f410x = f;
            this.f411y = f2;
            this.width = f3;
            this.height = f4;
        }

        public void setRect(double d, double d2, double d3, double d4) {
            this.f410x = (float) d;
            this.f411y = (float) d2;
            this.width = (float) d3;
            this.height = (float) d4;
        }

        public void setRect(Rectangle2D rectangle2D) {
            this.f410x = (float) rectangle2D.getX();
            this.f411y = (float) rectangle2D.getY();
            this.width = (float) rectangle2D.getWidth();
            this.height = (float) rectangle2D.getHeight();
        }

        public int outcode(double d, double d2) {
            int i;
            float f = this.width;
            if (f <= 0.0f) {
                i = 5;
            } else {
                float f2 = this.f410x;
                i = d < ((double) f2) ? 1 : d > ((double) (f2 + f)) ? 4 : 0;
            }
            float f3 = this.height;
            if (f3 <= 0.0f) {
                return i | 10;
            }
            float f4 = this.f411y;
            if (d2 < ((double) f4)) {
                return i | 2;
            }
            return d2 > ((double) (f4 + f3)) ? i | 8 : i;
        }

        public Rectangle2D getBounds2D() {
            return new Float(this.f410x, this.f411y, this.width, this.height);
        }

        public Rectangle2D createIntersection(Rectangle2D rectangle2D) {
            Rectangle2D rectangle2D2;
            if (rectangle2D instanceof Double) {
                rectangle2D2 = new Double();
            } else {
                rectangle2D2 = new Float();
            }
            Rectangle2D.intersect(this, rectangle2D, rectangle2D2);
            return rectangle2D2;
        }

        public Rectangle2D createUnion(Rectangle2D rectangle2D) {
            Rectangle2D rectangle2D2;
            if (rectangle2D instanceof Double) {
                rectangle2D2 = new Double();
            } else {
                rectangle2D2 = new Float();
            }
            Rectangle2D.union(this, rectangle2D, rectangle2D2);
            return rectangle2D2;
        }

        public String toString() {
            return getClass().getName() + "[x=" + this.f410x + ",y=" + this.f411y + ",width=" + this.width + ",height=" + this.height + "]";
        }
    }

    public static class Double extends Rectangle2D {
        public double height;
        public double width;

        /* renamed from: x */
        public double f408x;

        /* renamed from: y */
        public double f409y;

        public Double() {
        }

        public Double(double d, double d2, double d3, double d4) {
            setRect(d, d2, d3, d4);
        }

        public double getX() {
            return this.f408x;
        }

        public double getY() {
            return this.f409y;
        }

        public double getWidth() {
            return this.width;
        }

        public double getHeight() {
            return this.height;
        }

        public boolean isEmpty() {
            return this.width <= 0.0d || this.height <= 0.0d;
        }

        public void setRect(double d, double d2, double d3, double d4) {
            this.f408x = d;
            this.f409y = d2;
            this.width = d3;
            this.height = d4;
        }

        public void setRect(Rectangle2D rectangle2D) {
            this.f408x = rectangle2D.getX();
            this.f409y = rectangle2D.getY();
            this.width = rectangle2D.getWidth();
            this.height = rectangle2D.getHeight();
        }

        public int outcode(double d, double d2) {
            int i;
            double d3 = this.width;
            if (d3 <= 0.0d) {
                i = 5;
            } else {
                double d4 = this.f408x;
                i = d < d4 ? 1 : d > d4 + d3 ? 4 : 0;
            }
            double d5 = this.height;
            if (d5 <= 0.0d) {
                return i | 10;
            }
            double d6 = this.f409y;
            if (d2 < d6) {
                return i | 2;
            }
            return d2 > d6 + d5 ? i | 8 : i;
        }

        public Rectangle2D getBounds2D() {
            return new Double(this.f408x, this.f409y, this.width, this.height);
        }

        public Rectangle2D createIntersection(Rectangle2D rectangle2D) {
            Double doubleR = new Double();
            Rectangle2D.intersect(this, rectangle2D, doubleR);
            return doubleR;
        }

        public Rectangle2D createUnion(Rectangle2D rectangle2D) {
            Double doubleR = new Double();
            Rectangle2D.union(this, rectangle2D, doubleR);
            return doubleR;
        }

        public String toString() {
            return getClass().getName() + "[x=" + this.f408x + ",y=" + this.f409y + ",width=" + this.width + ",height=" + this.height + "]";
        }
    }

    class Iterator implements PathIterator {
        double height;
        int index;

        /* renamed from: t */
        AffineTransform f412t;
        double width;

        /* renamed from: x */
        double f413x;

        /* renamed from: y */
        double f414y;

        public int getWindingRule() {
            return 1;
        }

        Iterator(Rectangle2D rectangle2D, AffineTransform affineTransform) {
            this.f413x = rectangle2D.getX();
            this.f414y = rectangle2D.getY();
            this.width = rectangle2D.getWidth();
            double height2 = rectangle2D.getHeight();
            this.height = height2;
            this.f412t = affineTransform;
            if (this.width < 0.0d || height2 < 0.0d) {
                this.index = 6;
            }
        }

        public boolean isDone() {
            return this.index > 5;
        }

        public void next() {
            this.index++;
        }

        public int currentSegment(double[] dArr) {
            if (!isDone()) {
                int i = this.index;
                if (i == 5) {
                    return 4;
                }
                int i2 = 0;
                if (i == 0) {
                    dArr[0] = this.f413x;
                    dArr[1] = this.f414y;
                } else {
                    if (i == 1) {
                        dArr[0] = this.f413x + this.width;
                        dArr[1] = this.f414y;
                    } else if (i == 2) {
                        dArr[0] = this.f413x + this.width;
                        dArr[1] = this.f414y + this.height;
                    } else if (i == 3) {
                        dArr[0] = this.f413x;
                        dArr[1] = this.f414y + this.height;
                    } else if (i == 4) {
                        dArr[0] = this.f413x;
                        dArr[1] = this.f414y;
                    }
                    i2 = 1;
                }
                AffineTransform affineTransform = this.f412t;
                if (affineTransform != null) {
                    affineTransform.transform(dArr, 0, dArr, 0, 1);
                }
                return i2;
            }
            throw new NoSuchElementException(Messages.getString("awt.4B"));
        }

        public int currentSegment(float[] fArr) {
            if (!isDone()) {
                int i = this.index;
                if (i == 5) {
                    return 4;
                }
                int i2 = 0;
                if (i == 0) {
                    fArr[0] = (float) this.f413x;
                    fArr[1] = (float) this.f414y;
                } else {
                    if (i == 1) {
                        fArr[0] = (float) (this.f413x + this.width);
                        fArr[1] = (float) this.f414y;
                    } else if (i == 2) {
                        fArr[0] = (float) (this.f413x + this.width);
                        fArr[1] = (float) (this.f414y + this.height);
                    } else if (i == 3) {
                        fArr[0] = (float) this.f413x;
                        fArr[1] = (float) (this.f414y + this.height);
                    } else if (i == 4) {
                        fArr[0] = (float) this.f413x;
                        fArr[1] = (float) this.f414y;
                    }
                    i2 = 1;
                }
                AffineTransform affineTransform = this.f412t;
                if (affineTransform != null) {
                    affineTransform.transform(fArr, 0, fArr, 0, 1);
                }
                return i2;
            }
            throw new NoSuchElementException(Messages.getString("awt.4B"));
        }
    }

    protected Rectangle2D() {
    }

    public void setRect(Rectangle2D rectangle2D) {
        setRect(rectangle2D.getX(), rectangle2D.getY(), rectangle2D.getWidth(), rectangle2D.getHeight());
    }

    public void setFrame(double d, double d2, double d3, double d4) {
        setRect(d, d2, d3, d4);
    }

    public Rectangle2D getBounds2D() {
        return (Rectangle2D) clone();
    }

    public boolean intersectsLine(double d, double d2, double d3, double d4) {
        double x = getX();
        double y = getY();
        double width = x + getWidth();
        double height = y + getHeight();
        return (x <= d && d <= width && y <= d2 && d2 <= height) || (x <= d3 && d3 <= width && y <= d4 && d4 <= height) || Line2D.linesIntersect(x, y, width, height, d, d2, d3, d4) || Line2D.linesIntersect(width, y, x, height, d, d2, d3, d4);
    }

    public boolean intersectsLine(Line2D line2D) {
        return intersectsLine(line2D.getX1(), line2D.getY1(), line2D.getX2(), line2D.getY2());
    }

    public int outcode(Point2D point2D) {
        return outcode(point2D.getX(), point2D.getY());
    }

    public boolean contains(double d, double d2) {
        if (isEmpty()) {
            return false;
        }
        double x = getX();
        double y = getY();
        double width = getWidth() + x;
        double height = getHeight() + y;
        if (x > d || d >= width || y > d2 || d2 >= height) {
            return false;
        }
        return true;
    }

    public boolean intersects(double d, double d2, double d3, double d4) {
        if (isEmpty() || d3 <= 0.0d || d4 <= 0.0d) {
            return false;
        }
        double x = getX();
        double y = getY();
        double width = getWidth() + x;
        double height = getHeight() + y;
        if (d + d3 <= x || d >= width || d2 + d4 <= y || d2 >= height) {
            return false;
        }
        return true;
    }

    public boolean contains(double d, double d2, double d3, double d4) {
        if (isEmpty() || d3 <= 0.0d || d4 <= 0.0d) {
            return false;
        }
        double x = getX();
        double y = getY();
        double width = getWidth() + x;
        double height = getHeight() + y;
        if (x > d || d + d3 > width || y > d2 || d2 + d4 > height) {
            return false;
        }
        return true;
    }

    public static void intersect(Rectangle2D rectangle2D, Rectangle2D rectangle2D2, Rectangle2D rectangle2D3) {
        double max = Math.max(rectangle2D.getMinX(), rectangle2D2.getMinX());
        double max2 = Math.max(rectangle2D.getMinY(), rectangle2D2.getMinY());
        Rectangle2D rectangle2D4 = rectangle2D3;
        rectangle2D4.setFrame(max, max2, Math.min(rectangle2D.getMaxX(), rectangle2D2.getMaxX()) - max, Math.min(rectangle2D.getMaxY(), rectangle2D2.getMaxY()) - max2);
    }

    public static void union(Rectangle2D rectangle2D, Rectangle2D rectangle2D2, Rectangle2D rectangle2D3) {
        double min = Math.min(rectangle2D.getMinX(), rectangle2D2.getMinX());
        double min2 = Math.min(rectangle2D.getMinY(), rectangle2D2.getMinY());
        Rectangle2D rectangle2D4 = rectangle2D3;
        rectangle2D4.setFrame(min, min2, Math.max(rectangle2D.getMaxX(), rectangle2D2.getMaxX()) - min, Math.max(rectangle2D.getMaxY(), rectangle2D2.getMaxY()) - min2);
    }

    public void add(double d, double d2) {
        double min = Math.min(getMinX(), d);
        double min2 = Math.min(getMinY(), d2);
        setRect(min, min2, Math.max(getMaxX(), d) - min, Math.max(getMaxY(), d2) - min2);
    }

    public void add(Point2D point2D) {
        add(point2D.getX(), point2D.getY());
    }

    public void add(Rectangle2D rectangle2D) {
        union(this, rectangle2D, this);
    }

    public PathIterator getPathIterator(AffineTransform affineTransform) {
        return new Iterator(this, affineTransform);
    }

    public PathIterator getPathIterator(AffineTransform affineTransform, double d) {
        return new Iterator(this, affineTransform);
    }

    public int hashCode() {
        HashCode hashCode = new HashCode();
        hashCode.append(getX());
        hashCode.append(getY());
        hashCode.append(getWidth());
        hashCode.append(getHeight());
        return hashCode.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Rectangle2D)) {
            return false;
        }
        Rectangle2D rectangle2D = (Rectangle2D) obj;
        if (getX() == rectangle2D.getX() && getY() == rectangle2D.getY() && getWidth() == rectangle2D.getWidth() && getHeight() == rectangle2D.getHeight()) {
            return true;
        }
        return false;
    }
}
