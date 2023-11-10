package com.itextpdf.text.pdf.parser;

import com.itextpdf.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Subpath {
    private boolean closed;
    private List<Shape> segments;
    private Point2D startPoint;

    public Subpath() {
        this.segments = new ArrayList();
    }

    public Subpath(Subpath subpath) {
        ArrayList arrayList = new ArrayList();
        this.segments = arrayList;
        this.startPoint = subpath.startPoint;
        arrayList.addAll(subpath.getSegments());
        this.closed = subpath.closed;
    }

    public Subpath(Point2D point2D) {
        this((float) point2D.getX(), (float) point2D.getY());
    }

    public Subpath(float f, float f2) {
        this.segments = new ArrayList();
        this.startPoint = new Point2D.Float(f, f2);
    }

    public void setStartPoint(Point2D point2D) {
        setStartPoint((float) point2D.getX(), (float) point2D.getY());
    }

    public void setStartPoint(float f, float f2) {
        this.startPoint = new Point2D.Float(f, f2);
    }

    public Point2D getStartPoint() {
        return this.startPoint;
    }

    public Point2D getLastPoint() {
        Point2D point2D = this.startPoint;
        if (this.segments.size() <= 0 || this.closed) {
            return point2D;
        }
        List<Shape> list = this.segments;
        Shape shape = list.get(list.size() - 1);
        return shape.getBasePoints().get(shape.getBasePoints().size() - 1);
    }

    public void addSegment(Shape shape) {
        if (!this.closed) {
            if (isSinglePointOpen()) {
                this.startPoint = shape.getBasePoints().get(0);
            }
            this.segments.add(shape);
        }
    }

    public List<Shape> getSegments() {
        return this.segments;
    }

    public boolean isEmpty() {
        return this.startPoint == null;
    }

    public boolean isSinglePointOpen() {
        return this.segments.size() == 0 && !this.closed;
    }

    public boolean isSinglePointClosed() {
        return this.segments.size() == 0 && this.closed;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void setClosed(boolean z) {
        this.closed = z;
    }

    public boolean isDegenerate() {
        if (this.segments.size() > 0 && this.closed) {
            return false;
        }
        for (Shape basePoints : this.segments) {
            if (new HashSet(basePoints.getBasePoints()).size() != 1) {
                return false;
            }
        }
        if (this.segments.size() > 0 || this.closed) {
            return true;
        }
        return false;
    }

    public List<Point2D> getPiecewiseLinearApproximation() {
        List<Point2D> list;
        ArrayList arrayList = new ArrayList();
        if (this.segments.size() == 0) {
            return arrayList;
        }
        if (this.segments.get(0) instanceof BezierCurve) {
            arrayList.addAll(((BezierCurve) this.segments.get(0)).getPiecewiseLinearApproximation());
        } else {
            arrayList.addAll(this.segments.get(0).getBasePoints());
        }
        for (int i = 1; i < this.segments.size(); i++) {
            if (this.segments.get(i) instanceof BezierCurve) {
                List<Point2D> piecewiseLinearApproximation = ((BezierCurve) this.segments.get(i)).getPiecewiseLinearApproximation();
                list = piecewiseLinearApproximation.subList(1, piecewiseLinearApproximation.size());
            } else {
                List<Point2D> basePoints = this.segments.get(i).getBasePoints();
                list = basePoints.subList(1, basePoints.size());
            }
            arrayList.addAll(list);
        }
        return arrayList;
    }
}
