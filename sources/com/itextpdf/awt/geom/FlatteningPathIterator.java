package com.itextpdf.awt.geom;

import com.itextpdf.awt.geom.misc.Messages;
import java.util.NoSuchElementException;

public class FlatteningPathIterator implements PathIterator {
    private static final int BUFFER_CAPACITY = 16;
    private static final int BUFFER_LIMIT = 16;
    private static final int BUFFER_SIZE = 16;
    double[] buf;
    boolean bufEmpty;
    int bufIndex;
    int bufLimit;
    int bufSize;
    int bufSubdiv;
    int bufType;
    double[] coords;
    double flatness;
    double flatness2;

    /* renamed from: p */
    PathIterator f369p;

    /* renamed from: px */
    double f370px;

    /* renamed from: py */
    double f371py;

    public FlatteningPathIterator(PathIterator pathIterator, double d) {
        this(pathIterator, d, 16);
    }

    public FlatteningPathIterator(PathIterator pathIterator, double d, int i) {
        this.bufEmpty = true;
        this.coords = new double[6];
        if (d < 0.0d) {
            throw new IllegalArgumentException(Messages.getString("awt.206"));
        } else if (i < 0) {
            throw new IllegalArgumentException(Messages.getString("awt.207"));
        } else if (pathIterator != null) {
            this.f369p = pathIterator;
            this.flatness = d;
            this.flatness2 = d * d;
            this.bufLimit = i;
            int min = Math.min(i, 16);
            this.bufSize = min;
            this.buf = new double[min];
            this.bufIndex = min;
        } else {
            throw new NullPointerException(Messages.getString("awt.208"));
        }
    }

    public double getFlatness() {
        return this.flatness;
    }

    public int getRecursionLimit() {
        return this.bufLimit;
    }

    public int getWindingRule() {
        return this.f369p.getWindingRule();
    }

    public boolean isDone() {
        return this.bufEmpty && this.f369p.isDone();
    }

    /* access modifiers changed from: package-private */
    public void evaluate() {
        if (this.bufEmpty) {
            this.bufType = this.f369p.currentSegment(this.coords);
        }
        int i = this.bufType;
        boolean z = false;
        if (i == 0 || i == 1) {
            double[] dArr = this.coords;
            this.f370px = dArr[0];
            this.f371py = dArr[1];
        } else if (i == 2) {
            if (this.bufEmpty) {
                int i2 = this.bufIndex - 6;
                this.bufIndex = i2;
                double[] dArr2 = this.buf;
                dArr2[i2 + 0] = this.f370px;
                dArr2[i2 + 1] = this.f371py;
                System.arraycopy(this.coords, 0, dArr2, i2 + 2, 4);
                this.bufSubdiv = 0;
            }
            while (this.bufSubdiv < this.bufLimit && QuadCurve2D.getFlatnessSq(this.buf, this.bufIndex) >= this.flatness2) {
                int i3 = this.bufIndex;
                if (i3 <= 4) {
                    int i4 = this.bufSize;
                    double[] dArr3 = new double[(i4 + 16)];
                    System.arraycopy(this.buf, i3, dArr3, i3 + 16, i4 - i3);
                    this.buf = dArr3;
                    this.bufSize += 16;
                    this.bufIndex += 16;
                }
                double[] dArr4 = this.buf;
                int i5 = this.bufIndex;
                QuadCurve2D.subdivide(dArr4, i5, dArr4, i5 - 4, dArr4, i5);
                this.bufIndex -= 4;
                this.bufSubdiv++;
            }
            int i6 = this.bufIndex + 4;
            this.bufIndex = i6;
            double[] dArr5 = this.buf;
            this.f370px = dArr5[i6];
            this.f371py = dArr5[i6 + 1];
            int i7 = this.bufSize;
            if (i6 == i7 - 2) {
                z = true;
            }
            this.bufEmpty = z;
            if (z) {
                this.bufIndex = i7;
                this.bufType = 1;
            }
        } else if (i == 3) {
            if (this.bufEmpty) {
                int i8 = this.bufIndex - 8;
                this.bufIndex = i8;
                double[] dArr6 = this.buf;
                dArr6[i8 + 0] = this.f370px;
                dArr6[i8 + 1] = this.f371py;
                System.arraycopy(this.coords, 0, dArr6, i8 + 2, 6);
                this.bufSubdiv = 0;
            }
            while (this.bufSubdiv < this.bufLimit && CubicCurve2D.getFlatnessSq(this.buf, this.bufIndex) >= this.flatness2) {
                int i9 = this.bufIndex;
                if (i9 <= 6) {
                    int i10 = this.bufSize;
                    double[] dArr7 = new double[(i10 + 16)];
                    System.arraycopy(this.buf, i9, dArr7, i9 + 16, i10 - i9);
                    this.buf = dArr7;
                    this.bufSize += 16;
                    this.bufIndex += 16;
                }
                double[] dArr8 = this.buf;
                int i11 = this.bufIndex;
                CubicCurve2D.subdivide(dArr8, i11, dArr8, i11 - 6, dArr8, i11);
                this.bufIndex -= 6;
                this.bufSubdiv++;
            }
            int i12 = this.bufIndex + 6;
            this.bufIndex = i12;
            double[] dArr9 = this.buf;
            this.f370px = dArr9[i12];
            this.f371py = dArr9[i12 + 1];
            int i13 = this.bufSize;
            if (i12 == i13 - 2) {
                z = true;
            }
            this.bufEmpty = z;
            if (z) {
                this.bufIndex = i13;
                this.bufType = 1;
            }
        }
    }

    public void next() {
        if (this.bufEmpty) {
            this.f369p.next();
        }
    }

    public int currentSegment(float[] fArr) {
        if (!isDone()) {
            evaluate();
            int i = this.bufType;
            if (i == 4) {
                return i;
            }
            fArr[0] = (float) this.f370px;
            fArr[1] = (float) this.f371py;
            if (i != 0) {
                return 1;
            }
            return i;
        }
        throw new NoSuchElementException(Messages.getString("awt.4Bx"));
    }

    public int currentSegment(double[] dArr) {
        if (!isDone()) {
            evaluate();
            int i = this.bufType;
            if (i == 4) {
                return i;
            }
            dArr[0] = this.f370px;
            dArr[1] = this.f371py;
            if (i != 0) {
                return 1;
            }
            return i;
        }
        throw new NoSuchElementException(Messages.getString("awt.4B"));
    }
}
