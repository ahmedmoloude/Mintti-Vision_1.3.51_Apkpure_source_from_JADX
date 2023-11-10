package com.itextpdf.text.pdf.parser.clipper;

import com.itextpdf.text.pdf.parser.clipper.Clipper;
import com.itextpdf.text.pdf.parser.clipper.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ClipperOffset {
    private static final double DEFAULT_ARC_TOLERANCE = 0.25d;
    private static final double TOLERANCE = 1.0E-20d;
    private static final double TWO_PI = 6.283185307179586d;
    private final double arcTolerance;
    private double cos;
    private double delta;
    private Path destPoly;
    private Paths destPolys;
    private double inA;
    private Point.LongPoint lowest;
    private double miterLim;
    private final double miterLimit;
    private final List<Point.DoublePoint> normals;
    private final PolyNode polyNodes;
    private double sin;
    private Path srcPoly;
    private double stepsPerRad;

    private static boolean nearZero(double d) {
        return d > -1.0E-20d && d < TOLERANCE;
    }

    public ClipperOffset() {
        this(2.0d, DEFAULT_ARC_TOLERANCE);
    }

    public ClipperOffset(double d) {
        this(d, DEFAULT_ARC_TOLERANCE);
    }

    public ClipperOffset(double d, double d2) {
        this.miterLimit = d;
        this.arcTolerance = d2;
        Point.LongPoint longPoint = new Point.LongPoint();
        this.lowest = longPoint;
        longPoint.setX(-1L);
        this.polyNodes = new PolyNode();
        this.normals = new ArrayList();
    }

    public void addPath(Path path, Clipper.JoinType joinType, Clipper.EndType endType) {
        int size = path.size() - 1;
        if (size >= 0) {
            PolyNode polyNode = new PolyNode();
            polyNode.setJoinType(joinType);
            polyNode.setEndType(endType);
            int i = 0;
            if (endType == Clipper.EndType.CLOSED_LINE || endType == Clipper.EndType.CLOSED_POLYGON) {
                while (size > 0 && path.get(0) == path.get(size)) {
                    size--;
                }
            }
            polyNode.getPolygon().add(path.get(0));
            int i2 = 0;
            for (int i3 = 1; i3 <= size; i3++) {
                if (polyNode.getPolygon().get(i) != path.get(i3)) {
                    i++;
                    polyNode.getPolygon().add(path.get(i3));
                    if (((Point.LongPoint) path.get(i3)).getY() > ((Point.LongPoint) polyNode.getPolygon().get(i2)).getY() || (((Point.LongPoint) path.get(i3)).getY() == ((Point.LongPoint) polyNode.getPolygon().get(i2)).getY() && ((Point.LongPoint) path.get(i3)).getX() < ((Point.LongPoint) polyNode.getPolygon().get(i2)).getX())) {
                        i2 = i;
                    }
                }
            }
            if (endType != Clipper.EndType.CLOSED_POLYGON || i >= 2) {
                this.polyNodes.addChild(polyNode);
                if (endType == Clipper.EndType.CLOSED_POLYGON) {
                    if (this.lowest.getX() < 0) {
                        this.lowest = new Point.LongPoint((long) (this.polyNodes.getChildCount() - 1), (long) i2);
                        return;
                    }
                    Point.LongPoint longPoint = (Point.LongPoint) this.polyNodes.getChilds().get((int) this.lowest.getX()).getPolygon().get((int) this.lowest.getY());
                    if (((Point.LongPoint) polyNode.getPolygon().get(i2)).getY() > longPoint.getY() || (((Point.LongPoint) polyNode.getPolygon().get(i2)).getY() == longPoint.getY() && ((Point.LongPoint) polyNode.getPolygon().get(i2)).getX() < longPoint.getX())) {
                        this.lowest = new Point.LongPoint((long) (this.polyNodes.getChildCount() - 1), (long) i2);
                    }
                }
            }
        }
    }

    public void addPaths(Paths paths, Clipper.JoinType joinType, Clipper.EndType endType) {
        Iterator it = paths.iterator();
        while (it.hasNext()) {
            addPath((Path) it.next(), joinType, endType);
        }
    }

    public void clear() {
        this.polyNodes.getChilds().clear();
        this.lowest.setX(-1L);
    }

    private void doMiter(int i, int i2, double d) {
        double d2 = this.delta / d;
        this.destPoly.add(new Point.LongPoint(Math.round(((double) ((Point.LongPoint) this.srcPoly.get(i)).getX()) + ((this.normals.get(i2).getX() + this.normals.get(i).getX()) * d2)), Math.round(((double) ((Point.LongPoint) this.srcPoly.get(i)).getY()) + ((this.normals.get(i2).getY() + this.normals.get(i).getY()) * d2))));
    }

    private void doOffset(double d) {
        double d2;
        int i;
        char c;
        double d3 = d;
        this.destPolys = new Paths();
        this.delta = d3;
        int i2 = 0;
        if (nearZero(d)) {
            while (i2 < this.polyNodes.getChildCount()) {
                PolyNode polyNode = this.polyNodes.getChilds().get(i2);
                if (polyNode.getEndType() == Clipper.EndType.CLOSED_POLYGON) {
                    this.destPolys.add(polyNode.getPolygon());
                }
                i2++;
            }
            return;
        }
        double d4 = this.miterLimit;
        if (d4 > 2.0d) {
            this.miterLim = 2.0d / (d4 * d4);
        } else {
            this.miterLim = 0.5d;
        }
        double d5 = this.arcTolerance;
        double d6 = DEFAULT_ARC_TOLERANCE;
        double d7 = 0.0d;
        if (d5 > 0.0d) {
            if (d5 > Math.abs(d) * DEFAULT_ARC_TOLERANCE) {
                d6 = DEFAULT_ARC_TOLERANCE * Math.abs(d);
            } else {
                d6 = this.arcTolerance;
            }
        }
        double acos = 3.141592653589793d / Math.acos(1.0d - (d6 / Math.abs(d)));
        double d8 = TWO_PI / acos;
        this.sin = Math.sin(d8);
        this.cos = Math.cos(d8);
        this.stepsPerRad = acos / TWO_PI;
        int i3 = (d3 > 0.0d ? 1 : (d3 == 0.0d ? 0 : -1));
        if (i3 < 0) {
            this.sin = -this.sin;
        }
        int i4 = 0;
        while (i4 < this.polyNodes.getChildCount()) {
            PolyNode polyNode2 = this.polyNodes.getChilds().get(i4);
            Path polygon = polyNode2.getPolygon();
            this.srcPoly = polygon;
            int size = polygon.size();
            if (size == 0 || (i3 <= 0 && (size < 3 || polyNode2.getEndType() != Clipper.EndType.CLOSED_POLYGON))) {
                d2 = acos;
            } else {
                this.destPoly = new Path();
                int i5 = 1;
                if (size == 1) {
                    if (polyNode2.getJoinType() == Clipper.JoinType.ROUND) {
                        double d9 = d7;
                        double d10 = 1.0d;
                        while (((double) i5) <= acos) {
                            this.destPoly.add(new Point.LongPoint(Math.round(((double) ((Point.LongPoint) this.srcPoly.get(i2)).getX()) + (d10 * d3)), Math.round(((double) ((Point.LongPoint) this.srcPoly.get(i2)).getY()) + (d9 * d3))));
                            double d11 = this.cos;
                            double d12 = this.sin;
                            d9 = (d10 * d12) + (d9 * d11);
                            i5++;
                            d10 = (d10 * d11) - (d12 * d9);
                            acos = acos;
                            i2 = 0;
                        }
                        d2 = acos;
                    } else {
                        d2 = acos;
                        double d13 = -1.0d;
                        double d14 = -1.0d;
                        for (int i6 = 0; i6 < 4; i6++) {
                            this.destPoly.add(new Point.LongPoint(Math.round(((double) ((Point.LongPoint) this.srcPoly.get(0)).getX()) + (d13 * d3)), Math.round(((double) ((Point.LongPoint) this.srcPoly.get(0)).getY()) + (d14 * d3))));
                            if (d13 < 0.0d) {
                                d13 = 1.0d;
                            } else if (d14 < 0.0d) {
                                d14 = 1.0d;
                            } else {
                                d13 = -1.0d;
                            }
                        }
                    }
                    this.destPolys.add(this.destPoly);
                } else {
                    d2 = acos;
                    this.normals.clear();
                    int i7 = 0;
                    while (true) {
                        i = size - 1;
                        if (i7 >= i) {
                            break;
                        }
                        i7++;
                        this.normals.add(Point.getUnitNormal((Point.LongPoint) this.srcPoly.get(i7), (Point.LongPoint) this.srcPoly.get(i7)));
                    }
                    if (polyNode2.getEndType() == Clipper.EndType.CLOSED_LINE || polyNode2.getEndType() == Clipper.EndType.CLOSED_POLYGON) {
                        c = 0;
                        this.normals.add(Point.getUnitNormal((Point.LongPoint) this.srcPoly.get(i), (Point.LongPoint) this.srcPoly.get(0)));
                    } else {
                        this.normals.add(new Point.DoublePoint(this.normals.get(size - 2)));
                        c = 0;
                    }
                    if (polyNode2.getEndType() == Clipper.EndType.CLOSED_POLYGON) {
                        int[] iArr = new int[1];
                        iArr[c] = i;
                        for (int i8 = 0; i8 < size; i8++) {
                            offsetPoint(i8, iArr, polyNode2.getJoinType());
                        }
                        this.destPolys.add(this.destPoly);
                    } else if (polyNode2.getEndType() == Clipper.EndType.CLOSED_LINE) {
                        int[] iArr2 = {i};
                        for (int i9 = 0; i9 < size; i9++) {
                            offsetPoint(i9, iArr2, polyNode2.getJoinType());
                        }
                        this.destPolys.add(this.destPoly);
                        this.destPoly = new Path();
                        Point.DoublePoint doublePoint = this.normals.get(i);
                        for (int i10 = i; i10 > 0; i10--) {
                            int i11 = i10 - 1;
                            this.normals.set(i10, new Point.DoublePoint(-this.normals.get(i11).getX(), -this.normals.get(i11).getY()));
                        }
                        this.normals.set(0, new Point.DoublePoint(-doublePoint.getX(), -doublePoint.getY(), 0.0d));
                        iArr2[0] = 0;
                        while (i >= 0) {
                            offsetPoint(i, iArr2, polyNode2.getJoinType());
                            i--;
                        }
                        this.destPolys.add(this.destPoly);
                    } else {
                        int[] iArr3 = new int[1];
                        for (int i12 = 1; i12 < i; i12++) {
                            offsetPoint(i12, iArr3, polyNode2.getJoinType());
                        }
                        if (polyNode2.getEndType() == Clipper.EndType.OPEN_BUTT) {
                            this.destPoly.add(new Point.LongPoint(Math.round(((double) ((Point.LongPoint) this.srcPoly.get(i)).getX()) + (this.normals.get(i).getX() * d3)), Math.round(((double) ((Point.LongPoint) this.srcPoly.get(i)).getY()) + (this.normals.get(i).getY() * d3)), 0));
                            this.destPoly.add(new Point.LongPoint(Math.round(((double) ((Point.LongPoint) this.srcPoly.get(i)).getX()) - (this.normals.get(i).getX() * d3)), Math.round(((double) ((Point.LongPoint) this.srcPoly.get(i)).getY()) - (this.normals.get(i).getY() * d3)), 0));
                        } else {
                            iArr3[0] = size - 2;
                            this.inA = 0.0d;
                            this.normals.set(i, new Point.DoublePoint(-this.normals.get(i).getX(), -this.normals.get(i).getY()));
                            if (polyNode2.getEndType() == Clipper.EndType.OPEN_SQUARE) {
                                doSquare(i, iArr3[0], true);
                            } else {
                                doRound(i, iArr3[0]);
                            }
                        }
                        for (int i13 = i; i13 > 0; i13--) {
                            int i14 = i13 - 1;
                            this.normals.set(i13, new Point.DoublePoint(-this.normals.get(i14).getX(), -this.normals.get(i14).getY()));
                        }
                        this.normals.set(0, new Point.DoublePoint(-this.normals.get(1).getX(), -this.normals.get(1).getY()));
                        iArr3[0] = i;
                        for (int i15 = iArr3[0] - 1; i15 > 0; i15--) {
                            offsetPoint(i15, iArr3, polyNode2.getJoinType());
                        }
                        if (polyNode2.getEndType() == Clipper.EndType.OPEN_BUTT) {
                            this.destPoly.add(new Point.LongPoint(Math.round(((double) ((Point.LongPoint) this.srcPoly.get(0)).getX()) - (this.normals.get(0).getX() * d3)), Math.round(((double) ((Point.LongPoint) this.srcPoly.get(0)).getY()) - (this.normals.get(0).getY() * d3))));
                            this.destPoly.add(new Point.LongPoint(Math.round(((double) ((Point.LongPoint) this.srcPoly.get(0)).getX()) + (this.normals.get(0).getX() * d3)), Math.round(((double) ((Point.LongPoint) this.srcPoly.get(0)).getY()) + (this.normals.get(0).getY() * d3))));
                            d7 = 0.0d;
                        } else {
                            iArr3[0] = 1;
                            d7 = 0.0d;
                            this.inA = 0.0d;
                            if (polyNode2.getEndType() == Clipper.EndType.OPEN_SQUARE) {
                                doSquare(0, 1, true);
                            } else {
                                doRound(0, 1);
                            }
                        }
                        this.destPolys.add(this.destPoly);
                    }
                }
                d7 = 0.0d;
            }
            i4++;
            acos = d2;
            i2 = 0;
        }
    }

    private void doRound(int i, int i2) {
        int max = Math.max((int) Math.round(this.stepsPerRad * Math.abs(Math.atan2(this.inA, (this.normals.get(i2).getX() * this.normals.get(i).getX()) + (this.normals.get(i2).getY() * this.normals.get(i).getY())))), 1);
        double x = this.normals.get(i2).getX();
        double y = this.normals.get(i2).getY();
        int i3 = 0;
        while (i3 < max) {
            this.destPoly.add(new Point.LongPoint(Math.round(((double) ((Point.LongPoint) this.srcPoly.get(i)).getX()) + (this.delta * x)), Math.round(((double) ((Point.LongPoint) this.srcPoly.get(i)).getY()) + (this.delta * y))));
            double d = this.cos;
            double d2 = this.sin;
            y = (y * d) + (x * d2);
            i3++;
            x = (x * d) - (d2 * y);
        }
        this.destPoly.add(new Point.LongPoint(Math.round(((double) ((Point.LongPoint) this.srcPoly.get(i)).getX()) + (this.normals.get(i).getX() * this.delta)), Math.round(((double) ((Point.LongPoint) this.srcPoly.get(i)).getY()) + (this.normals.get(i).getY() * this.delta))));
    }

    private void doSquare(int i, int i2, boolean z) {
        int i3 = i;
        int i4 = i2;
        double x = this.normals.get(i4).getX();
        double y = this.normals.get(i4).getY();
        double x2 = this.normals.get(i3).getX();
        double y2 = this.normals.get(i3).getY();
        double x3 = (double) ((Point.LongPoint) this.srcPoly.get(i3)).getX();
        double y3 = (double) ((Point.LongPoint) this.srcPoly.get(i3)).getY();
        double d = x2;
        double tan = Math.tan(Math.atan2(this.inA, (x * x2) + (y * y2)) / 4.0d);
        double d2 = y2;
        double d3 = 0.0d;
        this.destPoly.add(new Point.LongPoint(Math.round((this.delta * (x - (z ? y * tan : 0.0d))) + x3), Math.round((this.delta * (y + (z ? x * tan : 0.0d))) + y3), 0));
        Path path = this.destPoly;
        long round = Math.round(x3 + (this.delta * (d + (z ? d2 * tan : 0.0d))));
        double d4 = this.delta;
        if (z) {
            d3 = d * tan;
        }
        path.add(new Point.LongPoint(round, Math.round(y3 + (d4 * (d2 - d3))), 0));
    }

    public void execute(Paths paths, double d) {
        Paths paths2 = paths;
        double d2 = d;
        paths.clear();
        fixOrientations();
        doOffset(d2);
        DefaultClipper defaultClipper = new DefaultClipper(1);
        defaultClipper.addPaths(this.destPolys, Clipper.PolyType.SUBJECT, true);
        if (d2 > 0.0d) {
            defaultClipper.execute(Clipper.ClipType.UNION, paths2, Clipper.PolyFillType.POSITIVE, Clipper.PolyFillType.POSITIVE);
            return;
        }
        LongRect bounds = this.destPolys.getBounds();
        Path path = new Path(4);
        path.add(new Point.LongPoint(bounds.left - 10, bounds.bottom + 10, 0));
        path.add(new Point.LongPoint(bounds.right + 10, bounds.bottom + 10, 0));
        path.add(new Point.LongPoint(bounds.right + 10, bounds.f2174top - 10, 0));
        path.add(new Point.LongPoint(bounds.left - 10, bounds.f2174top - 10, 0));
        defaultClipper.addPath(path, Clipper.PolyType.SUBJECT, true);
        defaultClipper.execute(Clipper.ClipType.UNION, paths2, Clipper.PolyFillType.NEGATIVE, Clipper.PolyFillType.NEGATIVE);
        if (paths.size() > 0) {
            paths2.remove(0);
        }
    }

    public void execute(PolyTree polyTree, double d) {
        PolyTree polyTree2 = polyTree;
        double d2 = d;
        polyTree.Clear();
        fixOrientations();
        doOffset(d2);
        DefaultClipper defaultClipper = new DefaultClipper(1);
        defaultClipper.addPaths(this.destPolys, Clipper.PolyType.SUBJECT, true);
        if (d2 > 0.0d) {
            defaultClipper.execute(Clipper.ClipType.UNION, polyTree2, Clipper.PolyFillType.POSITIVE, Clipper.PolyFillType.POSITIVE);
            return;
        }
        LongRect bounds = this.destPolys.getBounds();
        Path path = new Path(4);
        path.add(new Point.LongPoint(bounds.left - 10, bounds.bottom + 10, 0));
        path.add(new Point.LongPoint(bounds.right + 10, bounds.bottom + 10, 0));
        path.add(new Point.LongPoint(bounds.right + 10, bounds.f2174top - 10, 0));
        path.add(new Point.LongPoint(bounds.left - 10, bounds.f2174top - 10, 0));
        defaultClipper.addPath(path, Clipper.PolyType.SUBJECT, true);
        defaultClipper.execute(Clipper.ClipType.UNION, polyTree2, Clipper.PolyFillType.NEGATIVE, Clipper.PolyFillType.NEGATIVE);
        if (polyTree.getChildCount() != 1 || polyTree.getChilds().get(0).getChildCount() <= 0) {
            polyTree.Clear();
            return;
        }
        PolyNode polyNode = polyTree.getChilds().get(0);
        polyTree.getChilds().set(0, polyNode.getChilds().get(0));
        polyTree.getChilds().get(0).setParent(polyTree2);
        for (int i = 1; i < polyNode.getChildCount(); i++) {
            polyTree2.addChild(polyNode.getChilds().get(i));
        }
    }

    private void fixOrientations() {
        int i = 0;
        if (this.lowest.getX() < 0 || this.polyNodes.childs.get((int) this.lowest.getX()).getPolygon().orientation()) {
            while (i < this.polyNodes.getChildCount()) {
                PolyNode polyNode = this.polyNodes.childs.get(i);
                if (polyNode.getEndType() == Clipper.EndType.CLOSED_LINE && !polyNode.getPolygon().orientation()) {
                    Collections.reverse(polyNode.getPolygon());
                }
                i++;
            }
            return;
        }
        while (i < this.polyNodes.getChildCount()) {
            PolyNode polyNode2 = this.polyNodes.childs.get(i);
            if (polyNode2.getEndType() == Clipper.EndType.CLOSED_POLYGON || (polyNode2.getEndType() == Clipper.EndType.CLOSED_LINE && polyNode2.getPolygon().orientation())) {
                Collections.reverse(polyNode2.getPolygon());
            }
            i++;
        }
    }

    private void offsetPoint(int i, int[] iArr, Clipper.JoinType joinType) {
        double d;
        long j;
        char c;
        int i2 = i;
        int i3 = iArr[0];
        double x = this.normals.get(i3).getX();
        double y = this.normals.get(i3).getY();
        double y2 = this.normals.get(i2).getY();
        double x2 = this.normals.get(i2).getX();
        long x3 = ((Point.LongPoint) this.srcPoly.get(i2)).getX();
        long y3 = ((Point.LongPoint) this.srcPoly.get(i2)).getY();
        int i4 = i3;
        double d2 = (x * y2) - (x2 * y);
        this.inA = d2;
        long j2 = y3;
        if (Math.abs(d2 * this.delta) >= 1.0d) {
            j = j2;
            d = y2;
            double d3 = this.inA;
            if (d3 > 1.0d) {
                this.inA = 1.0d;
            } else if (d3 < -1.0d) {
                this.inA = -1.0d;
            }
        } else if ((x * x2) + (y2 * y) > 0.0d) {
            this.destPoly.add(new Point.LongPoint(Math.round(((double) x3) + (x * this.delta)), Math.round(((double) j2) + (y * this.delta)), 0));
            return;
        } else {
            j = j2;
            d = y2;
        }
        if (this.inA * this.delta < 0.0d) {
            double d4 = (double) x3;
            double d5 = (double) j;
            this.destPoly.add(new Point.LongPoint(Math.round((x * this.delta) + d4), Math.round((y * this.delta) + d5)));
            this.destPoly.add(this.srcPoly.get(i2));
            this.destPoly.add(new Point.LongPoint(Math.round(d4 + (x2 * this.delta)), Math.round(d5 + (this.delta * d))));
        } else {
            int i5 = C15291.$SwitchMap$com$itextpdf$text$pdf$parser$clipper$Clipper$JoinType[joinType.ordinal()];
            if (i5 == 1) {
                int i6 = i4;
                c = 0;
                double d6 = (x2 * x) + 1.0d + (d * y);
                if (d6 >= this.miterLim) {
                    doMiter(i2, i6, d6);
                } else {
                    doSquare(i2, i6, false);
                }
            } else if (i5 == 2) {
                c = 0;
                doSquare(i2, i4, false);
            } else if (i5 == 3) {
                doRound(i2, i4);
            }
            iArr[c] = i2;
        }
        c = 0;
        iArr[c] = i2;
    }

    /* renamed from: com.itextpdf.text.pdf.parser.clipper.ClipperOffset$1 */
    static /* synthetic */ class C15291 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$text$pdf$parser$clipper$Clipper$JoinType;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                com.itextpdf.text.pdf.parser.clipper.Clipper$JoinType[] r0 = com.itextpdf.text.pdf.parser.clipper.Clipper.JoinType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$itextpdf$text$pdf$parser$clipper$Clipper$JoinType = r0
                com.itextpdf.text.pdf.parser.clipper.Clipper$JoinType r1 = com.itextpdf.text.pdf.parser.clipper.Clipper.JoinType.MITER     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$parser$clipper$Clipper$JoinType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.itextpdf.text.pdf.parser.clipper.Clipper$JoinType r1 = com.itextpdf.text.pdf.parser.clipper.Clipper.JoinType.BEVEL     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$itextpdf$text$pdf$parser$clipper$Clipper$JoinType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.itextpdf.text.pdf.parser.clipper.Clipper$JoinType r1 = com.itextpdf.text.pdf.parser.clipper.Clipper.JoinType.ROUND     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.parser.clipper.ClipperOffset.C15291.<clinit>():void");
        }
    }
}
