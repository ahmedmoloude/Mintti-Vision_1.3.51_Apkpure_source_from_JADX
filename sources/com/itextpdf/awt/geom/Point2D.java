package com.itextpdf.awt.geom;

import com.itextpdf.awt.geom.misc.HashCode;

public abstract class Point2D implements Cloneable {
    public static double distanceSq(double d, double d2, double d3, double d4) {
        double d5 = d3 - d;
        double d6 = d4 - d2;
        return (d5 * d5) + (d6 * d6);
    }

    public abstract double getX();

    public abstract double getY();

    public abstract void setLocation(double d, double d2);

    public static class Float extends Point2D {

        /* renamed from: x */
        public float f391x;

        /* renamed from: y */
        public float f392y;

        public Float() {
        }

        public Float(float f, float f2) {
            this.f391x = f;
            this.f392y = f2;
        }

        public double getX() {
            return (double) this.f391x;
        }

        public double getY() {
            return (double) this.f392y;
        }

        public void setLocation(float f, float f2) {
            this.f391x = f;
            this.f392y = f2;
        }

        public void setLocation(double d, double d2) {
            this.f391x = (float) d;
            this.f392y = (float) d2;
        }

        public String toString() {
            return getClass().getName() + "[x=" + this.f391x + ",y=" + this.f392y + "]";
        }
    }

    public static class Double extends Point2D {

        /* renamed from: x */
        public double f389x;

        /* renamed from: y */
        public double f390y;

        public Double() {
        }

        public Double(double d, double d2) {
            this.f389x = d;
            this.f390y = d2;
        }

        public double getX() {
            return this.f389x;
        }

        public double getY() {
            return this.f390y;
        }

        public void setLocation(double d, double d2) {
            this.f389x = d;
            this.f390y = d2;
        }

        public String toString() {
            return getClass().getName() + "[x=" + this.f389x + ",y=" + this.f390y + "]";
        }
    }

    protected Point2D() {
    }

    public void setLocation(Point2D point2D) {
        setLocation(point2D.getX(), point2D.getY());
    }

    public double distanceSq(double d, double d2) {
        return distanceSq(getX(), getY(), d, d2);
    }

    public double distanceSq(Point2D point2D) {
        return distanceSq(getX(), getY(), point2D.getX(), point2D.getY());
    }

    public static double distance(double d, double d2, double d3, double d4) {
        return Math.sqrt(distanceSq(d, d2, d3, d4));
    }

    public double distance(double d, double d2) {
        return Math.sqrt(distanceSq(d, d2));
    }

    public double distance(Point2D point2D) {
        return Math.sqrt(distanceSq(point2D));
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            throw new InternalError();
        }
    }

    public int hashCode() {
        HashCode hashCode = new HashCode();
        hashCode.append(getX());
        hashCode.append(getY());
        return hashCode.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Point2D)) {
            return false;
        }
        Point2D point2D = (Point2D) obj;
        if (getX() == point2D.getX() && getY() == point2D.getY()) {
            return true;
        }
        return false;
    }
}
