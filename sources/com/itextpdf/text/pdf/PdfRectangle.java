package com.itextpdf.text.pdf;

import com.itextpdf.awt.geom.AffineTransform;
import com.itextpdf.text.Rectangle;

public class PdfRectangle extends NumberArray {
    private float llx;
    private float lly;
    private float urx;
    private float ury;

    public boolean add(PdfObject pdfObject) {
        return false;
    }

    public boolean add(float[] fArr) {
        return false;
    }

    public boolean add(int[] iArr) {
        return false;
    }

    public void addFirst(PdfObject pdfObject) {
    }

    public PdfRectangle(float f, float f2, float f3, float f4, int i) {
        super(new float[0]);
        this.llx = 0.0f;
        this.lly = 0.0f;
        this.urx = 0.0f;
        this.ury = 0.0f;
        if (i == 90 || i == 270) {
            this.llx = f2;
            this.lly = f;
            this.urx = f4;
            this.ury = f3;
        } else {
            this.llx = f;
            this.lly = f2;
            this.urx = f3;
            this.ury = f4;
        }
        super.add((PdfObject) new PdfNumber(this.llx));
        super.add((PdfObject) new PdfNumber(this.lly));
        super.add((PdfObject) new PdfNumber(this.urx));
        super.add((PdfObject) new PdfNumber(this.ury));
    }

    public PdfRectangle(float f, float f2, float f3, float f4) {
        this(f, f2, f3, f4, 0);
    }

    public PdfRectangle(float f, float f2, int i) {
        this(0.0f, 0.0f, f, f2, i);
    }

    public PdfRectangle(float f, float f2) {
        this(0.0f, 0.0f, f, f2, 0);
    }

    public PdfRectangle(Rectangle rectangle, int i) {
        this(rectangle.getLeft(), rectangle.getBottom(), rectangle.getRight(), rectangle.getTop(), i);
    }

    public PdfRectangle(Rectangle rectangle) {
        this(rectangle.getLeft(), rectangle.getBottom(), rectangle.getRight(), rectangle.getTop(), 0);
    }

    public Rectangle getRectangle() {
        return new Rectangle(left(), bottom(), right(), top());
    }

    public float left() {
        return this.llx;
    }

    public float right() {
        return this.urx;
    }

    public float top() {
        return this.ury;
    }

    public float bottom() {
        return this.lly;
    }

    public float left(int i) {
        return this.llx + ((float) i);
    }

    public float right(int i) {
        return this.urx - ((float) i);
    }

    public float top(int i) {
        return this.ury - ((float) i);
    }

    public float bottom(int i) {
        return this.lly + ((float) i);
    }

    public float width() {
        return this.urx - this.llx;
    }

    public float height() {
        return this.ury - this.lly;
    }

    public PdfRectangle rotate() {
        return new PdfRectangle(this.lly, this.llx, this.ury, this.urx, 0);
    }

    public PdfRectangle transform(AffineTransform affineTransform) {
        float[] fArr = {this.llx, this.lly, this.urx, this.ury};
        affineTransform.transform(fArr, 0, fArr, 0, 2);
        float[] fArr2 = {fArr[0], fArr[1], fArr[2], fArr[3]};
        if (fArr[0] > fArr[2]) {
            fArr2[0] = fArr[2];
            fArr2[2] = fArr[0];
        }
        if (fArr[1] > fArr[3]) {
            fArr2[1] = fArr[3];
            fArr2[3] = fArr[1];
        }
        return new PdfRectangle(fArr2[0], fArr2[1], fArr2[2], fArr2[3]);
    }
}
