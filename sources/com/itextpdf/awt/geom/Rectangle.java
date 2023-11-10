package com.itextpdf.awt.geom;

import com.itextpdf.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Rectangle extends Rectangle2D implements Shape, Serializable {
    private static final long serialVersionUID = -4345857070255674764L;
    public double height;
    public double width;

    /* renamed from: x */
    public double f406x;

    /* renamed from: y */
    public double f407y;

    public Rectangle() {
        setBounds(0, 0, 0, 0);
    }

    public Rectangle(Point point) {
        setBounds(point.f387x, point.f388y, 0.0d, 0.0d);
    }

    public Rectangle(Point point, Dimension dimension) {
        setBounds(point.f387x, point.f388y, dimension.width, dimension.height);
    }

    public Rectangle(double d, double d2, double d3, double d4) {
        setBounds(d, d2, d3, d4);
    }

    public Rectangle(int i, int i2) {
        setBounds(0, 0, i, i2);
    }

    public Rectangle(Rectangle rectangle) {
        setBounds(rectangle.f406x, rectangle.f407y, rectangle.width, rectangle.height);
    }

    public Rectangle(com.itextpdf.text.Rectangle rectangle) {
        rectangle.normalize();
        setBounds((double) rectangle.getLeft(), (double) rectangle.getBottom(), (double) rectangle.getWidth(), (double) rectangle.getHeight());
    }

    public Rectangle(Dimension dimension) {
        setBounds(0.0d, 0.0d, dimension.width, dimension.height);
    }

    public double getX() {
        return this.f406x;
    }

    public double getY() {
        return this.f407y;
    }

    public double getHeight() {
        return this.height;
    }

    public double getWidth() {
        return this.width;
    }

    public boolean isEmpty() {
        return this.width <= 0.0d || this.height <= 0.0d;
    }

    public Dimension getSize() {
        return new Dimension(this.width, this.height);
    }

    public void setSize(int i, int i2) {
        setSize((double) i, (double) i2);
    }

    public void setSize(double d, double d2) {
        this.width = d;
        this.height = d2;
    }

    public void setSize(Dimension dimension) {
        setSize(dimension.width, dimension.height);
    }

    public Point getLocation() {
        return new Point(this.f406x, this.f407y);
    }

    public void setLocation(int i, int i2) {
        setLocation((double) i, (double) i2);
    }

    public void setLocation(double d, double d2) {
        this.f406x = d;
        this.f407y = d2;
    }

    public void setLocation(Point point) {
        setLocation(point.f387x, point.f388y);
    }

    public void setRect(double d, double d2, double d3, double d4) {
        int floor = (int) Math.floor(d);
        int floor2 = (int) Math.floor(d2);
        setBounds(floor, floor2, ((int) Math.ceil(d + d3)) - floor, ((int) Math.ceil(d2 + d4)) - floor2);
    }

    public Rectangle getBounds() {
        return new Rectangle(this.f406x, this.f407y, this.width, this.height);
    }

    public Rectangle2D getBounds2D() {
        return getBounds();
    }

    public void setBounds(int i, int i2, int i3, int i4) {
        setBounds((double) i, (double) i2, (double) i3, (double) i4);
    }

    public void setBounds(double d, double d2, double d3, double d4) {
        this.f406x = d;
        this.f407y = d2;
        this.height = d4;
        this.width = d3;
    }

    public void setBounds(Rectangle rectangle) {
        setBounds(rectangle.f406x, rectangle.f407y, rectangle.width, rectangle.height);
    }

    public void grow(int i, int i2) {
        translate((double) i, (double) i2);
    }

    public void grow(double d, double d2) {
        this.f406x -= d;
        this.f407y -= d2;
        this.width += d + d;
        this.height += d2 + d2;
    }

    public void translate(int i, int i2) {
        translate((double) i, (double) i2);
    }

    public void translate(double d, double d2) {
        this.f406x += d;
        this.f407y += d2;
    }

    public void add(int i, int i2) {
        add((double) i, (double) i2);
    }

    public void add(double d, double d2) {
        double min = Math.min(this.f406x, d);
        double max = Math.max(this.f406x + this.width, d);
        double min2 = Math.min(this.f407y, d2);
        setBounds(min, min2, max - min, Math.max(this.f407y + this.height, d2) - min2);
    }

    public void add(Point point) {
        add(point.f387x, point.f388y);
    }

    public void add(Rectangle rectangle) {
        double min = Math.min(this.f406x, rectangle.f406x);
        double max = Math.max(this.f406x + this.width, rectangle.f406x + rectangle.width);
        double min2 = Math.min(this.f407y, rectangle.f407y);
        setBounds(min, min2, max - min, Math.max(this.f407y + this.height, rectangle.f407y + rectangle.height) - min2);
    }

    public boolean contains(int i, int i2) {
        return contains((double) i, (double) i2);
    }

    public boolean contains(double d, double d2) {
        if (isEmpty()) {
            return false;
        }
        double d3 = this.f406x;
        if (d < d3) {
            return false;
        }
        double d4 = this.f407y;
        if (d2 < d4) {
            return false;
        }
        double d5 = d2 - d4;
        if (d - d3 >= this.width || d5 >= this.height) {
            return false;
        }
        return true;
    }

    public boolean contains(Point point) {
        return contains(point.f387x, point.f388y);
    }

    public boolean contains(int i, int i2, int i3, int i4) {
        return contains(i, i2) && contains((i + i3) - 1, (i2 + i4) - 1);
    }

    public boolean contains(double d, double d2, double d3, double d4) {
        return contains(d, d2) && contains((d + d3) - 0.01d, (d2 + d4) - 0.01d);
    }

    public boolean contains(Rectangle rectangle) {
        return contains(rectangle.f406x, rectangle.f407y, rectangle.width, rectangle.height);
    }

    public Rectangle2D createIntersection(Rectangle2D rectangle2D) {
        if (rectangle2D instanceof Rectangle) {
            return intersection((Rectangle) rectangle2D);
        }
        Rectangle2D.Double doubleR = new Rectangle2D.Double();
        Rectangle2D.intersect(this, rectangle2D, doubleR);
        return doubleR;
    }

    public Rectangle intersection(Rectangle rectangle) {
        double max = Math.max(this.f406x, rectangle.f406x);
        double max2 = Math.max(this.f407y, rectangle.f407y);
        return new Rectangle(max, max2, Math.min(this.f406x + this.width, rectangle.f406x + rectangle.width) - max, Math.min(this.f407y + this.height, rectangle.f407y + rectangle.height) - max2);
    }

    public boolean intersects(Rectangle rectangle) {
        return !intersection(rectangle).isEmpty();
    }

    public int outcode(double d, double d2) {
        int i;
        double d3 = this.width;
        if (d3 <= 0.0d) {
            i = 5;
        } else {
            double d4 = this.f406x;
            i = d < d4 ? 1 : d > d4 + d3 ? 4 : 0;
        }
        double d5 = this.height;
        if (d5 <= 0.0d) {
            return i | 10;
        }
        double d6 = this.f407y;
        if (d2 < d6) {
            return i | 2;
        }
        return d2 > d6 + d5 ? i | 8 : i;
    }

    public Rectangle2D createUnion(Rectangle2D rectangle2D) {
        if (rectangle2D instanceof Rectangle) {
            return union((Rectangle) rectangle2D);
        }
        Rectangle2D.Double doubleR = new Rectangle2D.Double();
        Rectangle2D.union(this, rectangle2D, doubleR);
        return doubleR;
    }

    public Rectangle union(Rectangle rectangle) {
        Rectangle rectangle2 = new Rectangle(this);
        rectangle2.add(rectangle);
        return rectangle2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Rectangle)) {
            return false;
        }
        Rectangle rectangle = (Rectangle) obj;
        if (rectangle.f406x == this.f406x && rectangle.f407y == this.f407y && rectangle.width == this.width && rectangle.height == this.height) {
            return true;
        }
        return false;
    }

    public String toString() {
        return getClass().getName() + "[x=" + this.f406x + ",y=" + this.f407y + ",width=" + this.width + ",height=" + this.height + "]";
    }
}
