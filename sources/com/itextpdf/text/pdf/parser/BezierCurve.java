package com.itextpdf.text.pdf.parser;

import com.itextpdf.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class BezierCurve implements Shape {
    public static double curveCollinearityEpsilon = 1.0E-30d;
    public static double distanceToleranceManhattan = 0.4d;
    public static double distanceToleranceSquare = 0.025d;
    private final List<Point2D> controlPoints;

    public BezierCurve(List<Point2D> list) {
        this.controlPoints = new ArrayList(list);
    }

    public List<Point2D> getBasePoints() {
        return this.controlPoints;
    }

    public List<Point2D> getPiecewiseLinearApproximation() {
        ArrayList arrayList = r13;
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(this.controlPoints.get(0));
        double x = this.controlPoints.get(0).getX();
        ArrayList arrayList3 = arrayList2;
        double d = x;
        recursiveApproximation(d, this.controlPoints.get(0).getY(), this.controlPoints.get(1).getX(), this.controlPoints.get(1).getY(), this.controlPoints.get(2).getX(), this.controlPoints.get(2).getY(), this.controlPoints.get(3).getX(), this.controlPoints.get(3).getY(), arrayList);
        List<Point2D> list = this.controlPoints;
        ArrayList arrayList4 = arrayList3;
        arrayList4.add(list.get(list.size() - 1));
        return arrayList4;
    }

    private void recursiveApproximation(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, List<Point2D> list) {
        List<Point2D> list2 = list;
        double d9 = (d + d3) / 2.0d;
        double d10 = (d2 + d4) / 2.0d;
        double d11 = (d3 + d5) / 2.0d;
        double d12 = (d4 + d6) / 2.0d;
        double d13 = (d5 + d7) / 2.0d;
        double d14 = (d6 + d8) / 2.0d;
        double d15 = (d9 + d11) / 2.0d;
        double d16 = (d10 + d12) / 2.0d;
        double d17 = (d11 + d13) / 2.0d;
        double d18 = (d12 + d14) / 2.0d;
        double d19 = (d15 + d17) / 2.0d;
        double d20 = (d16 + d18) / 2.0d;
        double d21 = d7 - d;
        double d22 = d8 - d2;
        double abs = Math.abs(((d3 - d7) * d22) - ((d4 - d8) * d21));
        double abs2 = Math.abs(((d5 - d7) * d22) - ((d6 - d8) * d21));
        double d23 = curveCollinearityEpsilon;
        if (abs > d23 || abs2 > d23) {
            double d24 = abs + abs2;
            if (d24 * d24 <= distanceToleranceSquare * ((d21 * d21) + (d22 * d22))) {
                list2.add(new Point2D.Double(d19, d20));
                return;
            }
        } else if (Math.abs(((d + d5) - d3) - d3) + Math.abs(((d2 + d6) - d4) - d4) + Math.abs(((d3 + d7) - d5) - d5) + Math.abs(((d4 + d8) - d6) - d6) <= distanceToleranceManhattan) {
            list2.add(new Point2D.Double(d19, d20));
            return;
        }
        double d25 = d20;
        double d26 = d19;
        List<Point2D> list3 = list;
        recursiveApproximation(d, d2, d9, d10, d15, d16, d26, d25, list3);
        recursiveApproximation(d26, d25, d17, d18, d13, d14, d7, d8, list3);
    }
}
