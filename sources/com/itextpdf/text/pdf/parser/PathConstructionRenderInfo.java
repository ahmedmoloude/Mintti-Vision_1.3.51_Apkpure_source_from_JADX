package com.itextpdf.text.pdf.parser;

import java.util.List;

public class PathConstructionRenderInfo {
    public static final int CLOSE = 6;
    public static final int CURVE_123 = 3;
    public static final int CURVE_13 = 5;
    public static final int CURVE_23 = 4;
    public static final int LINETO = 2;
    public static final int MOVETO = 1;
    public static final int RECT = 7;
    private Matrix ctm;
    private int operation;
    private List<Float> segmentData;

    public PathConstructionRenderInfo(int i, List<Float> list, Matrix matrix) {
        this.operation = i;
        this.segmentData = list;
        this.ctm = matrix;
    }

    public PathConstructionRenderInfo(int i, Matrix matrix) {
        this(i, (List<Float>) null, matrix);
    }

    public int getOperation() {
        return this.operation;
    }

    public List<Float> getSegmentData() {
        return this.segmentData;
    }

    public Matrix getCtm() {
        return this.ctm;
    }
}
