package com.itextpdf.text.pdf.parser;

import com.itextpdf.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Line implements Shape {

    /* renamed from: p1 */
    private final Point2D f744p1;

    /* renamed from: p2 */
    private final Point2D f745p2;

    public Line() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Line(float f, float f2, float f3, float f4) {
        this.f744p1 = new Point2D.Float(f, f2);
        this.f745p2 = new Point2D.Float(f3, f4);
    }

    public Line(Point2D point2D, Point2D point2D2) {
        this((float) point2D.getX(), (float) point2D.getY(), (float) point2D2.getX(), (float) point2D2.getY());
    }

    public List<Point2D> getBasePoints() {
        ArrayList arrayList = new ArrayList(2);
        arrayList.add(this.f744p1);
        arrayList.add(this.f745p2);
        return arrayList;
    }
}
