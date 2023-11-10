package com.itextpdf.awt.geom;

import com.itextpdf.text.error_messages.MessageLocalization;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.NoSuchElementException;

public class PolylineShapeIterator implements PathIterator {
    protected AffineTransform affine;
    protected int index;
    protected PolylineShape poly;

    public int getWindingRule() {
        return 1;
    }

    PolylineShapeIterator(PolylineShape polylineShape, AffineTransform affineTransform) {
        this.poly = polylineShape;
        this.affine = affineTransform;
    }

    public int currentSegment(double[] dArr) {
        if (!isDone()) {
            int i = this.index == 0 ? 0 : 1;
            dArr[0] = (double) this.poly.f394x[this.index];
            dArr[1] = (double) this.poly.f395y[this.index];
            AffineTransform affineTransform = this.affine;
            if (affineTransform != null) {
                affineTransform.transform(dArr, 0, dArr, 0, 1);
            }
            return i;
        }
        throw new NoSuchElementException(MessageLocalization.getComposedMessage("line.iterator.out.of.bounds", new Object[0]));
    }

    public int currentSegment(float[] fArr) {
        if (!isDone()) {
            int i = this.index == 0 ? 0 : 1;
            fArr[0] = (float) this.poly.f394x[this.index];
            fArr[1] = (float) this.poly.f395y[this.index];
            AffineTransform affineTransform = this.affine;
            if (affineTransform != null) {
                affineTransform.transform(fArr, 0, fArr, 0, 1);
            }
            return i;
        }
        throw new NoSuchElementException(MessageLocalization.getComposedMessage("line.iterator.out.of.bounds", new Object[0]));
    }

    public boolean isDone() {
        return this.index >= this.poly.f393np;
    }

    public void next() {
        this.index++;
    }
}
