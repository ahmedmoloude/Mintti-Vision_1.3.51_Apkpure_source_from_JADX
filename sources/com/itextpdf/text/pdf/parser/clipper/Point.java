package com.itextpdf.text.pdf.parser.clipper;

import java.lang.Comparable;
import java.lang.Number;
import java.math.BigInteger;
import java.util.Comparator;

public abstract class Point<T extends Number & Comparable<T>> {
    private static final NumberComparator NUMBER_COMPARATOR = new NumberComparator();

    /* renamed from: x */
    protected T f759x;

    /* renamed from: y */
    protected T f760y;

    /* renamed from: z */
    protected T f761z;

    public static class DoublePoint extends Point<Double> {
        public DoublePoint() {
            this(0.0d, 0.0d);
        }

        public DoublePoint(double d, double d2) {
            this(d, d2, 0.0d);
        }

        public DoublePoint(double d, double d2, double d3) {
            super(Double.valueOf(d), Double.valueOf(d2), Double.valueOf(d3));
        }

        public DoublePoint(DoublePoint doublePoint) {
            super(doublePoint);
        }

        public double getX() {
            return ((Double) this.f759x).doubleValue();
        }

        public double getY() {
            return ((Double) this.f760y).doubleValue();
        }

        public double getZ() {
            return ((Double) this.f761z).doubleValue();
        }
    }

    public static class LongPoint extends Point<Long> {
        public static double getDeltaX(LongPoint longPoint, LongPoint longPoint2) {
            if (longPoint.getY() == longPoint2.getY()) {
                return -3.4E38d;
            }
            return ((double) (longPoint2.getX() - longPoint.getX())) / ((double) (longPoint2.getY() - longPoint.getY()));
        }

        public LongPoint() {
            this(0, 0);
        }

        public LongPoint(long j, long j2) {
            this(j, j2, 0);
        }

        public LongPoint(double d, double d2) {
            this((long) d, (long) d2);
        }

        public LongPoint(long j, long j2, long j3) {
            super(Long.valueOf(j), Long.valueOf(j2), Long.valueOf(j3));
        }

        public LongPoint(LongPoint longPoint) {
            super(longPoint);
        }

        public long getX() {
            return ((Long) this.f759x).longValue();
        }

        public long getY() {
            return ((Long) this.f760y).longValue();
        }

        public long getZ() {
            return ((Long) this.f761z).longValue();
        }
    }

    private static class NumberComparator<T extends Number & Comparable<T>> implements Comparator<T> {
        private NumberComparator() {
        }

        public int compare(T t, T t2) throws ClassCastException {
            return ((Comparable) t).compareTo(t2);
        }
    }

    static boolean arePointsClose(Point<? extends Number> point, Point<? extends Number> point2, double d) {
        double doubleValue = point.f759x.doubleValue() - point2.f759x.doubleValue();
        double doubleValue2 = point.f760y.doubleValue() - point2.f760y.doubleValue();
        return (doubleValue * doubleValue) + (doubleValue2 * doubleValue2) <= d;
    }

    static double distanceFromLineSqrd(Point<? extends Number> point, Point<? extends Number> point2, Point<? extends Number> point3) {
        double doubleValue = point2.f760y.doubleValue() - point3.f760y.doubleValue();
        double doubleValue2 = point3.f759x.doubleValue() - point2.f759x.doubleValue();
        double doubleValue3 = ((point.f759x.doubleValue() * doubleValue) + (point.f760y.doubleValue() * doubleValue2)) - ((point2.f759x.doubleValue() * doubleValue) + (point2.f760y.doubleValue() * doubleValue2));
        return (doubleValue3 * doubleValue3) / ((doubleValue * doubleValue) + (doubleValue2 * doubleValue2));
    }

    static DoublePoint getUnitNormal(LongPoint longPoint, LongPoint longPoint2) {
        double longValue = (double) (((Long) longPoint2.f759x).longValue() - ((Long) longPoint.f759x).longValue());
        double longValue2 = (double) (((Long) longPoint2.f760y).longValue() - ((Long) longPoint.f760y).longValue());
        if (longValue == 0.0d && longValue2 == 0.0d) {
            return new DoublePoint();
        }
        double sqrt = 1.0d / Math.sqrt((longValue * longValue) + (longValue2 * longValue2));
        return new DoublePoint(longValue2 * sqrt, -(longValue * sqrt));
    }

    protected static boolean isPt2BetweenPt1AndPt3(LongPoint longPoint, LongPoint longPoint2, LongPoint longPoint3) {
        if (longPoint.equals(longPoint3) || longPoint.equals(longPoint2) || longPoint3.equals(longPoint2)) {
            return false;
        }
        if (longPoint.f759x != longPoint3.f759x) {
            if ((((Long) longPoint2.f759x).longValue() > ((Long) longPoint.f759x).longValue()) == (((Long) longPoint2.f759x).longValue() < ((Long) longPoint3.f759x).longValue())) {
                return true;
            }
            return false;
        }
        if ((((Long) longPoint2.f760y).longValue() > ((Long) longPoint.f760y).longValue()) == (((Long) longPoint2.f760y).longValue() < ((Long) longPoint3.f760y).longValue())) {
            return true;
        }
        return false;
    }

    protected static boolean slopesEqual(LongPoint longPoint, LongPoint longPoint2, LongPoint longPoint3, boolean z) {
        if (z) {
            return BigInteger.valueOf(longPoint.getY() - longPoint2.getY()).multiply(BigInteger.valueOf(longPoint2.getX() - longPoint3.getX())).equals(BigInteger.valueOf(longPoint.getX() - longPoint2.getX()).multiply(BigInteger.valueOf(longPoint2.getY() - longPoint3.getY())));
        }
        return ((longPoint.getY() - longPoint2.getY()) * (longPoint2.getX() - longPoint3.getX())) - ((longPoint.getX() - longPoint2.getX()) * (longPoint2.getY() - longPoint3.getY())) == 0;
    }

    protected static boolean slopesEqual(LongPoint longPoint, LongPoint longPoint2, LongPoint longPoint3, LongPoint longPoint4, boolean z) {
        if (z) {
            return BigInteger.valueOf(longPoint.getY() - longPoint2.getY()).multiply(BigInteger.valueOf(longPoint3.getX() - longPoint4.getX())).equals(BigInteger.valueOf(longPoint.getX() - longPoint2.getX()).multiply(BigInteger.valueOf(longPoint3.getY() - longPoint4.getY())));
        }
        return ((longPoint.getY() - longPoint2.getY()) * (longPoint3.getX() - longPoint4.getX())) - ((longPoint.getX() - longPoint2.getX()) * (longPoint3.getY() - longPoint4.getY())) == 0;
    }

    static boolean slopesNearCollinear(LongPoint longPoint, LongPoint longPoint2, LongPoint longPoint3, double d) {
        if (Math.abs(((Long) longPoint.f759x).longValue() - ((Long) longPoint2.f759x).longValue()) > Math.abs(((Long) longPoint.f760y).longValue() - ((Long) longPoint2.f760y).longValue())) {
            if ((((Long) longPoint.f759x).longValue() > ((Long) longPoint2.f759x).longValue()) != (((Long) longPoint.f759x).longValue() < ((Long) longPoint3.f759x).longValue())) {
                if ((((Long) longPoint2.f759x).longValue() > ((Long) longPoint.f759x).longValue()) == (((Long) longPoint2.f759x).longValue() < ((Long) longPoint3.f759x).longValue())) {
                    if (distanceFromLineSqrd(longPoint2, longPoint, longPoint3) < d) {
                        return true;
                    }
                    return false;
                } else if (distanceFromLineSqrd(longPoint3, longPoint, longPoint2) < d) {
                    return true;
                } else {
                    return false;
                }
            } else if (distanceFromLineSqrd(longPoint, longPoint2, longPoint3) < d) {
                return true;
            } else {
                return false;
            }
        } else {
            if ((((Long) longPoint.f760y).longValue() > ((Long) longPoint2.f760y).longValue()) != (((Long) longPoint.f760y).longValue() < ((Long) longPoint3.f760y).longValue())) {
                if ((((Long) longPoint2.f760y).longValue() > ((Long) longPoint.f760y).longValue()) == (((Long) longPoint2.f760y).longValue() < ((Long) longPoint3.f760y).longValue())) {
                    if (distanceFromLineSqrd(longPoint2, longPoint, longPoint3) < d) {
                        return true;
                    }
                    return false;
                } else if (distanceFromLineSqrd(longPoint3, longPoint, longPoint2) < d) {
                    return true;
                } else {
                    return false;
                }
            } else if (distanceFromLineSqrd(longPoint, longPoint2, longPoint3) < d) {
                return true;
            } else {
                return false;
            }
        }
    }

    protected Point(Point<T> point) {
        this(point.f759x, point.f760y, point.f761z);
    }

    protected Point(T t, T t2, T t3) {
        this.f759x = t;
        this.f760y = t2;
        this.f761z = t3;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Point)) {
            return false;
        }
        Point point = (Point) obj;
        NumberComparator numberComparator = NUMBER_COMPARATOR;
        if (numberComparator.compare(this.f759x, point.f759x) == 0 && numberComparator.compare(this.f760y, point.f760y) == 0) {
            return true;
        }
        return false;
    }

    public void set(Point<T> point) {
        this.f759x = point.f759x;
        this.f760y = point.f760y;
        this.f761z = point.f761z;
    }

    public void setX(T t) {
        this.f759x = t;
    }

    public void setY(T t) {
        this.f760y = t;
    }

    public void setZ(T t) {
        this.f761z = t;
    }

    public String toString() {
        return "Point [x=" + this.f759x + ", y=" + this.f760y + ", z=" + this.f761z + "]";
    }
}
