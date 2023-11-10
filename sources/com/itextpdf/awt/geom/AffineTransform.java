package com.itextpdf.awt.geom;

import com.itextpdf.awt.geom.Point2D;
import com.itextpdf.awt.geom.misc.HashCode;
import com.itextpdf.awt.geom.misc.Messages;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AffineTransform implements Cloneable, Serializable {
    public static final int TYPE_FLIP = 64;
    public static final int TYPE_GENERAL_ROTATION = 16;
    public static final int TYPE_GENERAL_SCALE = 4;
    public static final int TYPE_GENERAL_TRANSFORM = 32;
    public static final int TYPE_IDENTITY = 0;
    public static final int TYPE_MASK_ROTATION = 24;
    public static final int TYPE_MASK_SCALE = 6;
    public static final int TYPE_QUADRANT_ROTATION = 8;
    public static final int TYPE_TRANSLATION = 1;
    public static final int TYPE_UNIFORM_SCALE = 2;
    static final int TYPE_UNKNOWN = -1;
    static final double ZERO = 1.0E-10d;
    private static final long serialVersionUID = 1330973210523860834L;
    double m00;
    double m01;
    double m02;
    double m10;
    double m11;
    double m12;
    transient int type;

    public AffineTransform() {
        this.type = 0;
        this.m11 = 1.0d;
        this.m00 = 1.0d;
        this.m12 = 0.0d;
        this.m02 = 0.0d;
        this.m01 = 0.0d;
        this.m10 = 0.0d;
    }

    public AffineTransform(AffineTransform affineTransform) {
        this.type = affineTransform.type;
        this.m00 = affineTransform.m00;
        this.m10 = affineTransform.m10;
        this.m01 = affineTransform.m01;
        this.m11 = affineTransform.m11;
        this.m02 = affineTransform.m02;
        this.m12 = affineTransform.m12;
    }

    public AffineTransform(float f, float f2, float f3, float f4, float f5, float f6) {
        this.type = -1;
        this.m00 = (double) f;
        this.m10 = (double) f2;
        this.m01 = (double) f3;
        this.m11 = (double) f4;
        this.m02 = (double) f5;
        this.m12 = (double) f6;
    }

    public AffineTransform(double d, double d2, double d3, double d4, double d5, double d6) {
        this.type = -1;
        this.m00 = d;
        this.m10 = d2;
        this.m01 = d3;
        this.m11 = d4;
        this.m02 = d5;
        this.m12 = d6;
    }

    public AffineTransform(float[] fArr) {
        this.type = -1;
        this.m00 = (double) fArr[0];
        this.m10 = (double) fArr[1];
        this.m01 = (double) fArr[2];
        this.m11 = (double) fArr[3];
        if (fArr.length > 4) {
            this.m02 = (double) fArr[4];
            this.m12 = (double) fArr[5];
        }
    }

    public AffineTransform(double[] dArr) {
        this.type = -1;
        this.m00 = dArr[0];
        this.m10 = dArr[1];
        this.m01 = dArr[2];
        this.m11 = dArr[3];
        if (dArr.length > 4) {
            this.m02 = dArr[4];
            this.m12 = dArr[5];
        }
    }

    public int getType() {
        int i = this.type;
        if (i != -1) {
            return i;
        }
        double d = this.m00;
        double d2 = this.m01;
        double d3 = this.m10;
        double d4 = this.m11;
        if ((d * d2) + (d3 * d4) != 0.0d) {
            return 32;
        }
        int i2 = 0;
        if (this.m02 != 0.0d || this.m12 != 0.0d) {
            i2 = 1;
        } else if (d == 1.0d && d4 == 1.0d && d2 == 0.0d && d3 == 0.0d) {
            return 0;
        }
        if ((d * d4) - (d2 * d3) < 0.0d) {
            i2 |= 64;
        }
        double d5 = (d * d) + (d3 * d3);
        if (d5 != (d2 * d2) + (d4 * d4)) {
            i2 |= 4;
        } else if (d5 != 1.0d) {
            i2 |= 2;
        }
        if ((d == 0.0d && d4 == 0.0d) || (d3 == 0.0d && d2 == 0.0d && (d < 0.0d || d4 < 0.0d))) {
            return i2 | 8;
        }
        return (d2 == 0.0d && d3 == 0.0d) ? i2 : i2 | 16;
    }

    public double getScaleX() {
        return this.m00;
    }

    public double getScaleY() {
        return this.m11;
    }

    public double getShearX() {
        return this.m01;
    }

    public double getShearY() {
        return this.m10;
    }

    public double getTranslateX() {
        return this.m02;
    }

    public double getTranslateY() {
        return this.m12;
    }

    public boolean isIdentity() {
        return getType() == 0;
    }

    public void getMatrix(double[] dArr) {
        dArr[0] = this.m00;
        dArr[1] = this.m10;
        dArr[2] = this.m01;
        dArr[3] = this.m11;
        if (dArr.length > 4) {
            dArr[4] = this.m02;
            dArr[5] = this.m12;
        }
    }

    public double getDeterminant() {
        return (this.m00 * this.m11) - (this.m01 * this.m10);
    }

    public void setTransform(double d, double d2, double d3, double d4, double d5, double d6) {
        this.type = -1;
        this.m00 = d;
        this.m10 = d2;
        this.m01 = d3;
        this.m11 = d4;
        this.m02 = d5;
        this.m12 = d6;
    }

    public void setTransform(AffineTransform affineTransform) {
        this.type = affineTransform.type;
        setTransform(affineTransform.m00, affineTransform.m10, affineTransform.m01, affineTransform.m11, affineTransform.m02, affineTransform.m12);
    }

    public void setToIdentity() {
        this.type = 0;
        this.m11 = 1.0d;
        this.m00 = 1.0d;
        this.m12 = 0.0d;
        this.m02 = 0.0d;
        this.m01 = 0.0d;
        this.m10 = 0.0d;
    }

    public void setToTranslation(double d, double d2) {
        this.m11 = 1.0d;
        this.m00 = 1.0d;
        this.m10 = 0.0d;
        this.m01 = 0.0d;
        this.m02 = d;
        this.m12 = d2;
        if (d == 0.0d && d2 == 0.0d) {
            this.type = 0;
        } else {
            this.type = 1;
        }
    }

    public void setToScale(double d, double d2) {
        this.m00 = d;
        this.m11 = d2;
        this.m12 = 0.0d;
        this.m02 = 0.0d;
        this.m01 = 0.0d;
        this.m10 = 0.0d;
        if (d == 1.0d && d2 == 1.0d) {
            this.type = 0;
        } else {
            this.type = -1;
        }
    }

    public void setToShear(double d, double d2) {
        this.m11 = 1.0d;
        this.m00 = 1.0d;
        this.m12 = 0.0d;
        this.m02 = 0.0d;
        this.m01 = d;
        this.m10 = d2;
        if (d == 0.0d && d2 == 0.0d) {
            this.type = 0;
        } else {
            this.type = -1;
        }
    }

    public void setToRotation(double d) {
        double sin = Math.sin(d);
        double cos = Math.cos(d);
        if (Math.abs(cos) < ZERO) {
            sin = sin > 0.0d ? 1.0d : -1.0d;
            cos = 0.0d;
        } else if (Math.abs(sin) < ZERO) {
            cos = cos > 0.0d ? 1.0d : -1.0d;
            sin = 0.0d;
        }
        this.m11 = cos;
        this.m00 = cos;
        this.m01 = -sin;
        this.m10 = sin;
        this.m12 = 0.0d;
        this.m02 = 0.0d;
        this.type = -1;
    }

    public void setToRotation(double d, double d2, double d3) {
        setToRotation(d);
        double d4 = this.m00;
        double d5 = this.m10;
        this.m02 = ((1.0d - d4) * d2) + (d3 * d5);
        this.m12 = (d3 * (1.0d - d4)) - (d2 * d5);
        this.type = -1;
    }

    public static AffineTransform getTranslateInstance(double d, double d2) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToTranslation(d, d2);
        return affineTransform;
    }

    public static AffineTransform getScaleInstance(double d, double d2) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToScale(d, d2);
        return affineTransform;
    }

    public static AffineTransform getShearInstance(double d, double d2) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToShear(d, d2);
        return affineTransform;
    }

    public static AffineTransform getRotateInstance(double d) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToRotation(d);
        return affineTransform;
    }

    public static AffineTransform getRotateInstance(double d, double d2, double d3) {
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToRotation(d, d2, d3);
        return affineTransform;
    }

    public void translate(double d, double d2) {
        concatenate(getTranslateInstance(d, d2));
    }

    public void scale(double d, double d2) {
        concatenate(getScaleInstance(d, d2));
    }

    public void shear(double d, double d2) {
        concatenate(getShearInstance(d, d2));
    }

    public void rotate(double d) {
        concatenate(getRotateInstance(d));
    }

    public void rotate(double d, double d2, double d3) {
        concatenate(getRotateInstance(d, d2, d3));
    }

    /* access modifiers changed from: package-private */
    public AffineTransform multiply(AffineTransform affineTransform, AffineTransform affineTransform2) {
        AffineTransform affineTransform3 = affineTransform;
        AffineTransform affineTransform4 = affineTransform2;
        double d = affineTransform3.m00;
        double d2 = affineTransform4.m00;
        double d3 = affineTransform3.m10;
        double d4 = affineTransform4.m01;
        double d5 = affineTransform4.m10;
        double d6 = (d * d2) + (d3 * d4);
        double d7 = affineTransform4.m11;
        double d8 = (d3 * d7) + (d * d5);
        double d9 = affineTransform3.m01;
        double d10 = d8;
        double d11 = affineTransform3.m11;
        double d12 = (d9 * d2) + (d11 * d4);
        double d13 = (d11 * d7) + (d9 * d5);
        double d14 = affineTransform3.m02;
        double d15 = d13;
        double d16 = affineTransform3.m12;
        return new AffineTransform(d6, d10, d12, d15, affineTransform4.m02 + (d2 * d14) + (d4 * d16), (d14 * d5) + (d16 * d7) + affineTransform4.m12);
    }

    public void concatenate(AffineTransform affineTransform) {
        setTransform(multiply(affineTransform, this));
    }

    public void preConcatenate(AffineTransform affineTransform) {
        setTransform(multiply(this, affineTransform));
    }

    public AffineTransform createInverse() throws NoninvertibleTransformException {
        double determinant = getDeterminant();
        if (Math.abs(determinant) >= ZERO) {
            double d = this.m11;
            double d2 = this.m10;
            double d3 = this.m01;
            double d4 = (-d2) / determinant;
            double d5 = (-d3) / determinant;
            double d6 = this.m00;
            double d7 = d6 / determinant;
            double d8 = this.m12;
            double d9 = d3 * d8;
            double d10 = d8;
            double d11 = this.m02;
            return new AffineTransform(d / determinant, d4, d5, d7, (d9 - (d * d11)) / determinant, ((d2 * d11) - (d6 * d10)) / determinant);
        }
        throw new NoninvertibleTransformException(Messages.getString("awt.204"));
    }

    public Point2D transform(Point2D point2D, Point2D point2D2) {
        if (point2D2 == null) {
            if (point2D instanceof Point2D.Double) {
                point2D2 = new Point2D.Double();
            } else {
                point2D2 = new Point2D.Float();
            }
        }
        double x = point2D.getX();
        double y = point2D.getY();
        point2D2.setLocation((this.m00 * x) + (this.m01 * y) + this.m02, (x * this.m10) + (y * this.m11) + this.m12);
        return point2D2;
    }

    public void transform(Point2D[] point2DArr, int i, Point2D[] point2DArr2, int i2, int i3) {
        while (true) {
            i3--;
            if (i3 >= 0) {
                int i4 = i + 1;
                Point2D point2D = point2DArr[i];
                double x = point2D.getX();
                double y = point2D.getY();
                Point2D point2D2 = point2DArr2[i2];
                if (point2D2 == null) {
                    if (point2D instanceof Point2D.Double) {
                        point2D2 = new Point2D.Double();
                    } else {
                        point2D2 = new Point2D.Float();
                    }
                }
                point2D2.setLocation((this.m00 * x) + (this.m01 * y) + this.m02, (x * this.m10) + (y * this.m11) + this.m12);
                point2DArr2[i2] = point2D2;
                i2++;
                i = i4;
            } else {
                return;
            }
        }
    }

    public void transform(double[] dArr, int i, double[] dArr2, int i2, int i3) {
        int i4;
        int i5;
        int i6 = 2;
        if (dArr == dArr2 && i < i2 && i2 < (i5 = i + i4)) {
            i = i5 - 2;
            i2 = (i2 + (i4 = i3 * 2)) - 2;
            i6 = -2;
        }
        while (true) {
            i3--;
            if (i3 >= 0) {
                double d = dArr[i + 0];
                double d2 = dArr[i + 1];
                dArr2[i2 + 0] = (this.m00 * d) + (this.m01 * d2) + this.m02;
                dArr2[i2 + 1] = (d * this.m10) + (d2 * this.m11) + this.m12;
                i += i6;
                i2 += i6;
            } else {
                return;
            }
        }
    }

    public void transform(float[] fArr, int i, float[] fArr2, int i2, int i3) {
        int i4;
        int i5;
        int i6 = 2;
        if (fArr == fArr2 && i < i2 && i2 < (i5 = i + i4)) {
            i = i5 - 2;
            i2 = (i2 + (i4 = i3 * 2)) - 2;
            i6 = -2;
        }
        while (true) {
            i3--;
            if (i3 >= 0) {
                double d = (double) fArr[i + 0];
                double d2 = (double) fArr[i + 1];
                fArr2[i2 + 0] = (float) ((this.m00 * d) + (this.m01 * d2) + this.m02);
                fArr2[i2 + 1] = (float) ((d * this.m10) + (d2 * this.m11) + this.m12);
                i += i6;
                i2 += i6;
            } else {
                return;
            }
        }
    }

    public void transform(float[] fArr, int i, double[] dArr, int i2, int i3) {
        int i4 = i;
        int i5 = i2;
        int i6 = i3;
        while (true) {
            i6--;
            if (i6 >= 0) {
                int i7 = i4 + 1;
                int i8 = i5 + 1;
                double d = (double) fArr[i4];
                double d2 = (double) fArr[i7];
                dArr[i5] = (this.m00 * d) + (this.m01 * d2) + this.m02;
                i5 = i8 + 1;
                dArr[i8] = (d * this.m10) + (d2 * this.m11) + this.m12;
                i4 = i7 + 1;
            } else {
                return;
            }
        }
    }

    public void transform(double[] dArr, int i, float[] fArr, int i2, int i3) {
        while (true) {
            i3--;
            if (i3 >= 0) {
                int i4 = i + 1;
                double d = dArr[i];
                i = i4 + 1;
                double d2 = dArr[i4];
                int i5 = i2 + 1;
                fArr[i2] = (float) ((this.m00 * d) + (this.m01 * d2) + this.m02);
                i2 = i5 + 1;
                fArr[i5] = (float) ((d * this.m10) + (d2 * this.m11) + this.m12);
            } else {
                return;
            }
        }
    }

    public Point2D deltaTransform(Point2D point2D, Point2D point2D2) {
        if (point2D2 == null) {
            if (point2D instanceof Point2D.Double) {
                point2D2 = new Point2D.Double();
            } else {
                point2D2 = new Point2D.Float();
            }
        }
        double x = point2D.getX();
        double y = point2D.getY();
        point2D2.setLocation((this.m00 * x) + (this.m01 * y), (x * this.m10) + (y * this.m11));
        return point2D2;
    }

    public void deltaTransform(double[] dArr, int i, double[] dArr2, int i2, int i3) {
        while (true) {
            i3--;
            if (i3 >= 0) {
                int i4 = i + 1;
                double d = dArr[i];
                i = i4 + 1;
                double d2 = dArr[i4];
                int i5 = i2 + 1;
                dArr2[i2] = (this.m00 * d) + (this.m01 * d2);
                i2 = i5 + 1;
                dArr2[i5] = (d * this.m10) + (d2 * this.m11);
            } else {
                return;
            }
        }
    }

    public Point2D inverseTransform(Point2D point2D, Point2D point2D2) throws NoninvertibleTransformException {
        double determinant = getDeterminant();
        if (Math.abs(determinant) >= ZERO) {
            if (point2D2 == null) {
                if (point2D instanceof Point2D.Double) {
                    point2D2 = new Point2D.Double();
                } else {
                    point2D2 = new Point2D.Float();
                }
            }
            double x = point2D.getX() - this.m02;
            double y = point2D.getY() - this.m12;
            point2D2.setLocation(((this.m11 * x) - (this.m01 * y)) / determinant, ((y * this.m00) - (x * this.m10)) / determinant);
            return point2D2;
        }
        throw new NoninvertibleTransformException(Messages.getString("awt.204"));
    }

    public void inverseTransform(double[] dArr, int i, double[] dArr2, int i2, int i3) throws NoninvertibleTransformException {
        double determinant = getDeterminant();
        if (Math.abs(determinant) >= ZERO) {
            int i4 = i;
            int i5 = i2;
            int i6 = i3;
            while (true) {
                i6--;
                if (i6 >= 0) {
                    int i7 = i4 + 1;
                    double d = dArr[i4] - this.m02;
                    i4 = i7 + 1;
                    double d2 = dArr[i7] - this.m12;
                    int i8 = i5 + 1;
                    dArr2[i5] = ((this.m11 * d) - (this.m01 * d2)) / determinant;
                    i5 = i8 + 1;
                    dArr2[i8] = ((d2 * this.m00) - (d * this.m10)) / determinant;
                } else {
                    return;
                }
            }
        } else {
            throw new NoninvertibleTransformException(Messages.getString("awt.204"));
        }
    }

    public void inverseTransform(float[] fArr, int i, float[] fArr2, int i2, int i3) throws NoninvertibleTransformException {
        float determinant = (float) getDeterminant();
        if (((double) Math.abs(determinant)) >= ZERO) {
            while (true) {
                i3--;
                if (i3 >= 0) {
                    int i4 = i + 1;
                    float f = fArr[i] - ((float) this.m02);
                    int i5 = i4 + 1;
                    float f2 = fArr[i4] - ((float) this.m12);
                    int i6 = i2 + 1;
                    fArr2[i2] = ((((float) this.m11) * f) - (((float) this.m01) * f2)) / determinant;
                    i2 = i6 + 1;
                    fArr2[i6] = ((f2 * ((float) this.m00)) - (f * ((float) this.m10))) / determinant;
                    i = i5;
                } else {
                    return;
                }
            }
        } else {
            throw new NoninvertibleTransformException(Messages.getString("awt.204"));
        }
    }

    public Shape createTransformedShape(Shape shape) {
        if (shape == null) {
            return null;
        }
        if (shape instanceof GeneralPath) {
            return ((GeneralPath) shape).createTransformedShape(this);
        }
        PathIterator pathIterator = shape.getPathIterator(this);
        GeneralPath generalPath = new GeneralPath(pathIterator.getWindingRule());
        generalPath.append(pathIterator, false);
        return generalPath;
    }

    public String toString() {
        return getClass().getName() + "[[" + this.m00 + ", " + this.m01 + ", " + this.m02 + "], [" + this.m10 + ", " + this.m11 + ", " + this.m12 + "]]";
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
        hashCode.append(this.m00);
        hashCode.append(this.m01);
        hashCode.append(this.m02);
        hashCode.append(this.m10);
        hashCode.append(this.m11);
        hashCode.append(this.m12);
        return hashCode.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AffineTransform)) {
            return false;
        }
        AffineTransform affineTransform = (AffineTransform) obj;
        if (this.m00 == affineTransform.m00 && this.m01 == affineTransform.m01 && this.m02 == affineTransform.m02 && this.m10 == affineTransform.m10 && this.m11 == affineTransform.m11 && this.m12 == affineTransform.m12) {
            return true;
        }
        return false;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.type = -1;
    }
}
