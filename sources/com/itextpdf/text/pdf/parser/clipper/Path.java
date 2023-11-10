package com.itextpdf.text.pdf.parser.clipper;

import com.itextpdf.text.pdf.parser.clipper.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Path extends ArrayList<Point.LongPoint> {
    private static final long serialVersionUID = -7120161578077546673L;

    static class Join {
        private Point.LongPoint offPt;
        OutPt outPt1;
        OutPt outPt2;

        Join() {
        }

        public Point.LongPoint getOffPt() {
            return this.offPt;
        }

        public void setOffPt(Point.LongPoint longPoint) {
            this.offPt = longPoint;
        }
    }

    static class OutPt {
        int idx;
        OutPt next;
        OutPt prev;

        /* renamed from: pt */
        protected Point.LongPoint f757pt;

        OutPt() {
        }

        public static OutRec getLowerMostRec(OutRec outRec, OutRec outRec2) {
            if (outRec.bottomPt == null) {
                outRec.bottomPt = outRec.pts.getBottomPt();
            }
            if (outRec2.bottomPt == null) {
                outRec2.bottomPt = outRec2.pts.getBottomPt();
            }
            OutPt outPt = outRec.bottomPt;
            OutPt outPt2 = outRec2.bottomPt;
            if (outPt.getPt().getY() > outPt2.getPt().getY()) {
                return outRec;
            }
            if (outPt.getPt().getY() < outPt2.getPt().getY()) {
                return outRec2;
            }
            if (outPt.getPt().getX() < outPt2.getPt().getX()) {
                return outRec;
            }
            if (outPt.getPt().getX() <= outPt2.getPt().getX() && outPt.next != outPt) {
                return (outPt2.next != outPt2 && !isFirstBottomPt(outPt, outPt2)) ? outRec2 : outRec;
            }
            return outRec2;
        }

        private static boolean isFirstBottomPt(OutPt outPt, OutPt outPt2) {
            OutPt outPt3 = outPt.prev;
            while (outPt3.getPt().equals(outPt.getPt()) && !outPt3.equals(outPt)) {
                outPt3 = outPt3.prev;
            }
            double abs = Math.abs(Point.LongPoint.getDeltaX(outPt.getPt(), outPt3.getPt()));
            OutPt outPt4 = outPt.next;
            while (outPt4.getPt().equals(outPt.getPt()) && !outPt4.equals(outPt)) {
                outPt4 = outPt4.next;
            }
            double abs2 = Math.abs(Point.LongPoint.getDeltaX(outPt.getPt(), outPt4.getPt()));
            OutPt outPt5 = outPt2.prev;
            while (outPt5.getPt().equals(outPt2.getPt()) && !outPt5.equals(outPt2)) {
                outPt5 = outPt5.prev;
            }
            double abs3 = Math.abs(Point.LongPoint.getDeltaX(outPt2.getPt(), outPt5.getPt()));
            OutPt outPt6 = outPt2.next;
            while (outPt6.getPt().equals(outPt2.getPt()) && outPt6.equals(outPt2)) {
                outPt6 = outPt6.next;
            }
            double abs4 = Math.abs(Point.LongPoint.getDeltaX(outPt2.getPt(), outPt6.getPt()));
            return (abs >= abs3 && abs >= abs4) || (abs2 >= abs3 && abs2 >= abs4);
        }

        public OutPt duplicate(boolean z) {
            OutPt outPt = new OutPt();
            outPt.setPt(new Point.LongPoint(getPt()));
            outPt.idx = this.idx;
            if (z) {
                outPt.next = this.next;
                outPt.prev = this;
                this.next.prev = outPt;
                this.next = outPt;
            } else {
                outPt.prev = this.prev;
                outPt.next = this;
                this.prev.next = outPt;
                this.prev = outPt;
            }
            return outPt;
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0067, code lost:
            if (r3 != null) goto L_0x0069;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0069, code lost:
            if (r3 == r0) goto L_0x0086;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x006f, code lost:
            if (isFirstBottomPt(r0, r3) != false) goto L_0x0072;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0071, code lost:
            r2 = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0072, code lost:
            r3 = r3.next;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0081, code lost:
            if (r3.getPt().equals(r2.getPt()) != false) goto L_0x0069;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0083, code lost:
            r3 = r3.next;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0086, code lost:
            return r2;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.itextpdf.text.pdf.parser.clipper.Path.OutPt getBottomPt() {
            /*
                r9 = this;
                com.itextpdf.text.pdf.parser.clipper.Path$OutPt r0 = r9.next
                r1 = 0
                r2 = r9
                r3 = r1
            L_0x0005:
                if (r0 == r2) goto L_0x0067
                com.itextpdf.text.pdf.parser.clipper.Point$LongPoint r4 = r0.getPt()
                long r4 = r4.getY()
                com.itextpdf.text.pdf.parser.clipper.Point$LongPoint r6 = r2.getPt()
                long r6 = r6.getY()
                int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r8 <= 0) goto L_0x001e
            L_0x001b:
                r2 = r0
                r3 = r1
                goto L_0x0064
            L_0x001e:
                com.itextpdf.text.pdf.parser.clipper.Point$LongPoint r4 = r0.getPt()
                long r4 = r4.getY()
                com.itextpdf.text.pdf.parser.clipper.Point$LongPoint r6 = r2.getPt()
                long r6 = r6.getY()
                int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r8 != 0) goto L_0x0064
                com.itextpdf.text.pdf.parser.clipper.Point$LongPoint r4 = r0.getPt()
                long r4 = r4.getX()
                com.itextpdf.text.pdf.parser.clipper.Point$LongPoint r6 = r2.getPt()
                long r6 = r6.getX()
                int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r8 > 0) goto L_0x0064
                com.itextpdf.text.pdf.parser.clipper.Point$LongPoint r4 = r0.getPt()
                long r4 = r4.getX()
                com.itextpdf.text.pdf.parser.clipper.Point$LongPoint r6 = r2.getPt()
                long r6 = r6.getX()
                int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r8 >= 0) goto L_0x005b
                goto L_0x001b
            L_0x005b:
                com.itextpdf.text.pdf.parser.clipper.Path$OutPt r4 = r0.next
                if (r4 == r2) goto L_0x0064
                com.itextpdf.text.pdf.parser.clipper.Path$OutPt r4 = r0.prev
                if (r4 == r2) goto L_0x0064
                r3 = r0
            L_0x0064:
                com.itextpdf.text.pdf.parser.clipper.Path$OutPt r0 = r0.next
                goto L_0x0005
            L_0x0067:
                if (r3 == 0) goto L_0x0086
            L_0x0069:
                if (r3 == r0) goto L_0x0086
                boolean r1 = isFirstBottomPt(r0, r3)
                if (r1 != 0) goto L_0x0072
                r2 = r3
            L_0x0072:
                com.itextpdf.text.pdf.parser.clipper.Path$OutPt r1 = r3.next
                r3 = r1
            L_0x0075:
                com.itextpdf.text.pdf.parser.clipper.Point$LongPoint r1 = r3.getPt()
                com.itextpdf.text.pdf.parser.clipper.Point$LongPoint r4 = r2.getPt()
                boolean r1 = r1.equals(r4)
                if (r1 != 0) goto L_0x0069
                com.itextpdf.text.pdf.parser.clipper.Path$OutPt r3 = r3.next
                goto L_0x0075
            L_0x0086:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.parser.clipper.Path.OutPt.getBottomPt():com.itextpdf.text.pdf.parser.clipper.Path$OutPt");
        }

        public int getPointCount() {
            int i = 0;
            OutPt outPt = this;
            do {
                i++;
                outPt = outPt.next;
                if (outPt == this) {
                    break;
                }
            } while (outPt != null);
            return i;
        }

        public Point.LongPoint getPt() {
            return this.f757pt;
        }

        public void reversePolyPtLinks() {
            OutPt outPt = this;
            while (true) {
                OutPt outPt2 = outPt.next;
                outPt.next = outPt.prev;
                outPt.prev = outPt2;
                if (outPt2 != this) {
                    outPt = outPt2;
                } else {
                    return;
                }
            }
        }

        public void setPt(Point.LongPoint longPoint) {
            this.f757pt = longPoint;
        }
    }

    protected static class Maxima {
        protected Maxima Next;
        protected Maxima Prev;

        /* renamed from: X */
        protected long f756X;

        protected Maxima() {
        }
    }

    static class OutRec {
        int Idx;
        OutPt bottomPt;
        OutRec firstLeft;
        boolean isHole;
        boolean isOpen;
        PolyNode polyNode;
        protected OutPt pts;

        OutRec() {
        }

        public double area() {
            OutPt outPt = this.pts;
            double d = 0.0d;
            if (outPt == null) {
                return 0.0d;
            }
            do {
                d += ((double) (outPt.prev.getPt().getX() + outPt.getPt().getX())) * ((double) (outPt.prev.getPt().getY() - outPt.getPt().getY()));
                outPt = outPt.next;
            } while (outPt != this.pts);
            return d * 0.5d;
        }

        public void fixHoleLinkage() {
            OutRec outRec = this.firstLeft;
            if (outRec == null) {
                return;
            }
            if (this.isHole == outRec.isHole || outRec.pts == null) {
                while (outRec != null && (outRec.isHole == this.isHole || outRec.pts == null)) {
                    outRec = outRec.firstLeft;
                }
                this.firstLeft = outRec;
            }
        }

        public OutPt getPoints() {
            return this.pts;
        }

        public void setPoints(OutPt outPt) {
            this.pts = outPt;
        }
    }

    private static OutPt excludeOp(OutPt outPt) {
        OutPt outPt2 = outPt.prev;
        outPt2.next = outPt.next;
        outPt.next.prev = outPt2;
        outPt2.idx = 0;
        return outPt2;
    }

    public Path() {
    }

    public Path(Point.LongPoint[] longPointArr) {
        this();
        for (Point.LongPoint add : longPointArr) {
            add(add);
        }
    }

    public Path(int i) {
        super(i);
    }

    public Path(Collection<? extends Point.LongPoint> collection) {
        super(collection);
    }

    public double area() {
        int size = size();
        double d = 0.0d;
        if (size < 3) {
            return 0.0d;
        }
        int i = size - 1;
        for (int i2 = 0; i2 < size; i2++) {
            d += (((double) ((Point.LongPoint) get(i)).getX()) + ((double) ((Point.LongPoint) get(i2)).getX())) * (((double) ((Point.LongPoint) get(i)).getY()) - ((double) ((Point.LongPoint) get(i2)).getY()));
            i = i2;
        }
        return (-d) * 0.5d;
    }

    public Path cleanPolygon() {
        return cleanPolygon(1.415d);
    }

    public Path cleanPolygon(double d) {
        int size = size();
        if (size == 0) {
            return new Path();
        }
        OutPt[] outPtArr = new OutPt[size];
        for (int i = 0; i < size; i++) {
            outPtArr[i] = new OutPt();
        }
        int i2 = 0;
        while (i2 < size) {
            outPtArr[i2].f757pt = (Point.LongPoint) get(i2);
            int i3 = i2 + 1;
            outPtArr[i2].next = outPtArr[i3 % size];
            outPtArr[i2].next.prev = outPtArr[i2];
            outPtArr[i2].idx = 0;
            i2 = i3;
        }
        double d2 = d * d;
        OutPt outPt = outPtArr[0];
        while (outPt.idx == 0 && outPt.next != outPt.prev) {
            if (Point.arePointsClose(outPt.f757pt, outPt.prev.f757pt, d2)) {
                outPt = excludeOp(outPt);
            } else if (Point.arePointsClose(outPt.prev.f757pt, outPt.next.f757pt, d2)) {
                excludeOp(outPt.next);
                outPt = excludeOp(outPt);
                size -= 2;
            } else if (Point.slopesNearCollinear(outPt.prev.f757pt, outPt.f757pt, outPt.next.f757pt, d2)) {
                outPt = excludeOp(outPt);
            } else {
                outPt.idx = 1;
                outPt = outPt.next;
            }
            size--;
        }
        if (size < 3) {
            size = 0;
        }
        Path path = new Path(size);
        for (int i4 = 0; i4 < size; i4++) {
            path.add(outPt.f757pt);
            outPt = outPt.next;
        }
        return path;
    }

    public int isPointInPolygon(Point.LongPoint longPoint) {
        int i;
        int size = size();
        if (size < 3) {
            return 0;
        }
        Point.LongPoint longPoint2 = (Point.LongPoint) get(0);
        int i2 = 1;
        int i3 = 0;
        while (i2 <= size) {
            Point.LongPoint longPoint3 = (Point.LongPoint) (i2 == size ? get(0) : get(i2));
            if (longPoint3.getY() == longPoint.getY()) {
                if (longPoint3.getX() != longPoint.getX()) {
                    if (longPoint2.getY() == longPoint.getY()) {
                        if ((longPoint3.getX() > longPoint.getX()) == (longPoint2.getX() < longPoint.getX())) {
                        }
                    }
                }
                return -1;
            }
            if ((longPoint2.getY() < longPoint.getY()) != (longPoint3.getY() < longPoint.getY())) {
                if (longPoint2.getX() < longPoint.getX()) {
                    i = i2;
                    if (longPoint3.getX() <= longPoint.getX()) {
                        continue;
                    } else {
                        int i4 = (((((double) (longPoint2.getX() - longPoint.getX())) * ((double) (longPoint3.getY() - longPoint.getY()))) - (((double) (longPoint3.getX() - longPoint.getX())) * ((double) (longPoint2.getY() - longPoint.getY())))) > 0.0d ? 1 : (((((double) (longPoint2.getX() - longPoint.getX())) * ((double) (longPoint3.getY() - longPoint.getY()))) - (((double) (longPoint3.getX() - longPoint.getX())) * ((double) (longPoint2.getY() - longPoint.getY())))) == 0.0d ? 0 : -1));
                        if (i4 == 0) {
                            return -1;
                        }
                        if ((i4 > 0) == (longPoint3.getY() > longPoint2.getY())) {
                            i3 = 1 - i3;
                        }
                    }
                } else if (longPoint3.getX() > longPoint.getX()) {
                    i3 = 1 - i3;
                } else {
                    i = i2;
                    int i5 = (((((double) (longPoint2.getX() - longPoint.getX())) * ((double) (longPoint3.getY() - longPoint.getY()))) - (((double) (longPoint3.getX() - longPoint.getX())) * ((double) (longPoint2.getY() - longPoint.getY())))) > 0.0d ? 1 : (((((double) (longPoint2.getX() - longPoint.getX())) * ((double) (longPoint3.getY() - longPoint.getY()))) - (((double) (longPoint3.getX() - longPoint.getX())) * ((double) (longPoint2.getY() - longPoint.getY())))) == 0.0d ? 0 : -1));
                    if (i5 == 0) {
                        return -1;
                    }
                    if ((i5 > 0) == (longPoint3.getY() > longPoint2.getY())) {
                        i3 = 1 - i3;
                    }
                }
                i2 = i + 1;
                longPoint2 = longPoint3;
            }
            i = i2;
            i2 = i + 1;
            longPoint2 = longPoint3;
        }
        return i3;
    }

    public boolean orientation() {
        return area() >= 0.0d;
    }

    public void reverse() {
        Collections.reverse(this);
    }

    public Path TranslatePath(Point.LongPoint longPoint) {
        Path path = new Path(size());
        for (int i = 0; i < size(); i++) {
            path.add(new Point.LongPoint(((Point.LongPoint) get(i)).getX() + longPoint.getX(), ((Point.LongPoint) get(i)).getY() + longPoint.getY()));
        }
        return path;
    }
}
