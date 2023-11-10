package com.itextpdf.text.pdf.parser;

import com.itextpdf.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Path {
    private static final String START_PATH_ERR_MSG = "Path shall start with \"re\" or \"m\" operator";
    private Point2D currentPoint;
    private List<Subpath> subpaths = new ArrayList();

    public Path() {
    }

    public Path(List<? extends Subpath> list) {
        addSubpaths(list);
    }

    public List<Subpath> getSubpaths() {
        return this.subpaths;
    }

    public void addSubpath(Subpath subpath) {
        this.subpaths.add(subpath);
        this.currentPoint = subpath.getLastPoint();
    }

    public void addSubpaths(List<? extends Subpath> list) {
        if (list.size() > 0) {
            this.subpaths.addAll(list);
            this.currentPoint = this.subpaths.get(list.size() - 1).getLastPoint();
        }
    }

    public Point2D getCurrentPoint() {
        return this.currentPoint;
    }

    public void moveTo(float f, float f2) {
        this.currentPoint = new Point2D.Float(f, f2);
        Subpath lastSubpath = getLastSubpath();
        if (lastSubpath == null || !lastSubpath.isSinglePointOpen()) {
            this.subpaths.add(new Subpath(this.currentPoint));
        } else {
            lastSubpath.setStartPoint(this.currentPoint);
        }
    }

    public void lineTo(float f, float f2) {
        if (this.currentPoint != null) {
            Point2D.Float floatR = new Point2D.Float(f, f2);
            getLastSubpath().addSegment(new Line(this.currentPoint, floatR));
            this.currentPoint = floatR;
            return;
        }
        throw new RuntimeException(START_PATH_ERR_MSG);
    }

    public void curveTo(float f, float f2, float f3, float f4, float f5, float f6) {
        if (this.currentPoint != null) {
            Point2D.Float floatR = new Point2D.Float(f, f2);
            Point2D.Float floatR2 = new Point2D.Float(f3, f4);
            Point2D.Float floatR3 = new Point2D.Float(f5, f6);
            getLastSubpath().addSegment(new BezierCurve(new ArrayList(Arrays.asList(new Point2D[]{this.currentPoint, floatR, floatR2, floatR3}))));
            this.currentPoint = floatR3;
            return;
        }
        throw new RuntimeException(START_PATH_ERR_MSG);
    }

    public void curveTo(float f, float f2, float f3, float f4) {
        Point2D point2D = this.currentPoint;
        if (point2D != null) {
            curveTo((float) point2D.getX(), (float) this.currentPoint.getY(), f, f2, f3, f4);
            return;
        }
        throw new RuntimeException(START_PATH_ERR_MSG);
    }

    public void curveFromTo(float f, float f2, float f3, float f4) {
        if (this.currentPoint != null) {
            curveTo(f, f2, f3, f4, f3, f4);
            return;
        }
        throw new RuntimeException(START_PATH_ERR_MSG);
    }

    public void rectangle(float f, float f2, float f3, float f4) {
        moveTo(f, f2);
        float f5 = f3 + f;
        lineTo(f5, f2);
        float f6 = f2 + f4;
        lineTo(f5, f6);
        lineTo(f, f6);
        closeSubpath();
    }

    public void closeSubpath() {
        Subpath lastSubpath = getLastSubpath();
        lastSubpath.setClosed(true);
        Point2D startPoint = lastSubpath.getStartPoint();
        moveTo((float) startPoint.getX(), (float) startPoint.getY());
    }

    public void closeAllSubpaths() {
        for (Subpath closed : this.subpaths) {
            closed.setClosed(true);
        }
    }

    public List<Integer> replaceCloseWithLine() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (Subpath next : this.subpaths) {
            if (next.isClosed()) {
                next.setClosed(false);
                next.addSegment(new Line(next.getLastPoint(), next.getStartPoint()));
                arrayList.add(Integer.valueOf(i));
            }
            i++;
        }
        return arrayList;
    }

    public boolean isEmpty() {
        return this.subpaths.size() == 0;
    }

    private Subpath getLastSubpath() {
        if (this.subpaths.size() <= 0) {
            return null;
        }
        List<Subpath> list = this.subpaths;
        return list.get(list.size() - 1);
    }
}
