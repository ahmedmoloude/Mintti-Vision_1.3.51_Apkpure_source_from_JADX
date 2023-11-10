package com.itextpdf.text.pdf.parser.clipper;

import com.itextpdf.text.pdf.parser.clipper.Clipper;
import com.itextpdf.text.pdf.parser.clipper.Edge;
import com.itextpdf.text.pdf.parser.clipper.Path;
import com.itextpdf.text.pdf.parser.clipper.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class ClipperBase implements Clipper {
    private static final long HI_RANGE = 4611686018427387903L;
    private static final Logger LOGGER = Logger.getLogger(Clipper.class.getName());
    private static final long LOW_RANGE = 1073741823;
    protected LocalMinima currentLM = null;
    private final List<List<Edge>> edges = new ArrayList();
    protected boolean hasOpenPaths = false;
    protected LocalMinima minimaList = null;
    protected final boolean preserveCollinear;
    protected boolean useFullRange;

    protected class LocalMinima {
        Edge leftBound;
        LocalMinima next;
        Edge rightBound;

        /* renamed from: y */
        long f751y;

        protected LocalMinima() {
        }
    }

    protected class Scanbeam {
        Scanbeam next;

        /* renamed from: y */
        long f752y;

        protected Scanbeam() {
        }
    }

    private static void initEdge(Edge edge, Edge edge2, Edge edge3, Point.LongPoint longPoint) {
        edge.next = edge2;
        edge.prev = edge3;
        edge.setCurrent(new Point.LongPoint(longPoint));
        edge.outIdx = -1;
    }

    private static void initEdge2(Edge edge, Clipper.PolyType polyType) {
        if (edge.getCurrent().getY() >= edge.next.getCurrent().getY()) {
            edge.setBot(new Point.LongPoint(edge.getCurrent()));
            edge.setTop(new Point.LongPoint(edge.next.getCurrent()));
        } else {
            edge.setTop(new Point.LongPoint(edge.getCurrent()));
            edge.setBot(new Point.LongPoint(edge.next.getCurrent()));
        }
        edge.updateDeltaX();
        edge.polyTyp = polyType;
    }

    private static boolean rangeTest(Point.LongPoint longPoint, boolean z) {
        if (z) {
            if (longPoint.getX() > 4611686018427387903L || longPoint.getY() > 4611686018427387903L || (-longPoint.getX()) > 4611686018427387903L || (-longPoint.getY()) > 4611686018427387903L) {
                throw new IllegalStateException("Coordinate outside allowed range");
            }
        } else if (longPoint.getX() > LOW_RANGE || longPoint.getY() > LOW_RANGE || (-longPoint.getX()) > LOW_RANGE || (-longPoint.getY()) > LOW_RANGE) {
            return rangeTest(longPoint, true);
        }
        return z;
    }

    private static Edge removeEdge(Edge edge) {
        edge.prev.next = edge.next;
        edge.next.prev = edge.prev;
        Edge edge2 = edge.next;
        edge.prev = null;
        return edge2;
    }

    protected ClipperBase(boolean z) {
        this.preserveCollinear = z;
    }

    public boolean addPath(Path path, Clipper.PolyType polyType, boolean z) {
        Edge edge;
        boolean z2;
        if (z || polyType != Clipper.PolyType.CLIP) {
            int size = path.size() - 1;
            if (z) {
                while (size > 0 && ((Point.LongPoint) path.get(size)).equals(path.get(0))) {
                    size--;
                }
            }
            while (size > 0 && ((Point.LongPoint) path.get(size)).equals(path.get(size - 1))) {
                size--;
            }
            if ((z && size < 2) || (!z && size < 1)) {
                return false;
            }
            ArrayList arrayList = new ArrayList(size + 1);
            for (int i = 0; i <= size; i++) {
                arrayList.add(new Edge());
            }
            ((Edge) arrayList.get(1)).setCurrent(new Point.LongPoint((Point.LongPoint) path.get(1)));
            this.useFullRange = rangeTest((Point.LongPoint) path.get(0), this.useFullRange);
            this.useFullRange = rangeTest((Point.LongPoint) path.get(size), this.useFullRange);
            initEdge((Edge) arrayList.get(0), (Edge) arrayList.get(1), (Edge) arrayList.get(size), (Point.LongPoint) path.get(0));
            int i2 = size - 1;
            initEdge((Edge) arrayList.get(size), (Edge) arrayList.get(0), (Edge) arrayList.get(i2), (Point.LongPoint) path.get(size));
            while (i2 >= 1) {
                this.useFullRange = rangeTest((Point.LongPoint) path.get(i2), this.useFullRange);
                initEdge((Edge) arrayList.get(i2), (Edge) arrayList.get(i2 + 1), (Edge) arrayList.get(i2 - 1), (Point.LongPoint) path.get(i2));
                i2--;
            }
            Edge edge2 = (Edge) arrayList.get(0);
            Edge edge3 = edge2;
            Edge edge4 = edge3;
            while (true) {
                if (!edge2.getCurrent().equals(edge2.next.getCurrent()) || (!z && edge2.next.equals(edge3))) {
                    if (edge2.prev == edge2.next) {
                        break;
                    } else if (!z || !Point.slopesEqual(edge2.prev.getCurrent(), edge2.getCurrent(), edge2.next.getCurrent(), this.useFullRange) || (isPreserveCollinear() && Point.isPt2BetweenPt1AndPt3(edge2.prev.getCurrent(), edge2.getCurrent(), edge2.next.getCurrent()))) {
                        edge2 = edge2.next;
                        if (edge2 != edge4) {
                            if (!z && edge2.next == edge3) {
                                break;
                            }
                        } else {
                            break;
                        }
                    } else {
                        if (edge2 == edge3) {
                            edge3 = edge2.next;
                        }
                        edge4 = removeEdge(edge2).prev;
                    }
                } else if (edge2 == edge2.next) {
                    break;
                } else {
                    if (edge2 == edge3) {
                        edge3 = edge2.next;
                    }
                    edge4 = removeEdge(edge2);
                }
                edge2 = edge4;
            }
            if ((!z && edge2 == edge2.next) || (z && edge2.prev == edge2.next)) {
                return false;
            }
            if (!z) {
                this.hasOpenPaths = true;
                edge3.prev.outIdx = -2;
            }
            Edge edge5 = edge3;
            boolean z3 = true;
            do {
                initEdge2(edge5, polyType);
                edge5 = edge5.next;
                if (z3 && edge5.getCurrent().getY() != edge3.getCurrent().getY()) {
                    z3 = false;
                    continue;
                }
            } while (edge5 != edge3);
            if (!z3) {
                this.edges.add(arrayList);
                if (edge5.prev.getBot().equals(edge5.prev.getTop())) {
                    edge5 = edge5.next;
                }
                Edge edge6 = null;
                while (true) {
                    Edge findNextLocMin = edge.findNextLocMin();
                    if (findNextLocMin == edge6) {
                        return true;
                    }
                    if (edge6 == null) {
                        edge6 = findNextLocMin;
                    }
                    LocalMinima localMinima = new LocalMinima();
                    localMinima.next = null;
                    localMinima.f751y = findNextLocMin.getBot().getY();
                    if (findNextLocMin.deltaX < findNextLocMin.prev.deltaX) {
                        localMinima.leftBound = findNextLocMin.prev;
                        localMinima.rightBound = findNextLocMin;
                        z2 = false;
                    } else {
                        localMinima.leftBound = findNextLocMin;
                        localMinima.rightBound = findNextLocMin.prev;
                        z2 = true;
                    }
                    localMinima.leftBound.side = Edge.Side.LEFT;
                    localMinima.rightBound.side = Edge.Side.RIGHT;
                    if (!z) {
                        localMinima.leftBound.windDelta = 0;
                    } else if (localMinima.leftBound.next == localMinima.rightBound) {
                        localMinima.leftBound.windDelta = -1;
                    } else {
                        localMinima.leftBound.windDelta = 1;
                    }
                    localMinima.rightBound.windDelta = -localMinima.leftBound.windDelta;
                    edge = processBound(localMinima.leftBound, z2);
                    if (edge.outIdx == -2) {
                        edge = processBound(edge, z2);
                    }
                    Edge processBound = processBound(localMinima.rightBound, !z2);
                    if (processBound.outIdx == -2) {
                        processBound = processBound(processBound, !z2);
                    }
                    if (localMinima.leftBound.outIdx == -2) {
                        localMinima.leftBound = null;
                    } else if (localMinima.rightBound.outIdx == -2) {
                        localMinima.rightBound = null;
                    }
                    insertLocalMinima(localMinima);
                    if (!z2) {
                        edge = processBound;
                    }
                }
            } else if (z) {
                return false;
            } else {
                edge5.prev.outIdx = -2;
                LocalMinima localMinima2 = new LocalMinima();
                localMinima2.next = null;
                localMinima2.f751y = edge5.getBot().getY();
                localMinima2.leftBound = null;
                localMinima2.rightBound = edge5;
                localMinima2.rightBound.side = Edge.Side.RIGHT;
                localMinima2.rightBound.windDelta = 0;
                while (true) {
                    if (edge5.getBot().getX() != edge5.prev.getTop().getX()) {
                        edge5.reverseHorizontal();
                    }
                    if (edge5.next.outIdx == -2) {
                        insertLocalMinima(localMinima2);
                        this.edges.add(arrayList);
                        return true;
                    }
                    edge5.nextInLML = edge5.next;
                    edge5 = edge5.next;
                }
            }
        } else {
            throw new IllegalStateException("AddPath: Open paths must be subject.");
        }
    }

    public boolean addPaths(Paths paths, Clipper.PolyType polyType, boolean z) {
        boolean z2 = false;
        for (int i = 0; i < paths.size(); i++) {
            if (addPath((Path) paths.get(i), polyType, z)) {
                z2 = true;
            }
        }
        return z2;
    }

    public void clear() {
        disposeLocalMinimaList();
        this.edges.clear();
        this.useFullRange = false;
        this.hasOpenPaths = false;
    }

    private void disposeLocalMinimaList() {
        while (true) {
            LocalMinima localMinima = this.minimaList;
            if (localMinima != null) {
                LocalMinima localMinima2 = localMinima.next;
                this.minimaList = null;
                this.minimaList = localMinima2;
            } else {
                this.currentLM = null;
                return;
            }
        }
    }

    private void insertLocalMinima(LocalMinima localMinima) {
        if (this.minimaList == null) {
            this.minimaList = localMinima;
        } else if (localMinima.f751y >= this.minimaList.f751y) {
            localMinima.next = this.minimaList;
            this.minimaList = localMinima;
        } else {
            LocalMinima localMinima2 = this.minimaList;
            while (localMinima2.next != null && localMinima.f751y < localMinima2.next.f751y) {
                localMinima2 = localMinima2.next;
            }
            localMinima.next = localMinima2.next;
            localMinima2.next = localMinima;
        }
    }

    public boolean isPreserveCollinear() {
        return this.preserveCollinear;
    }

    /* access modifiers changed from: protected */
    public void popLocalMinima() {
        LOGGER.entering(ClipperBase.class.getName(), "popLocalMinima");
        LocalMinima localMinima = this.currentLM;
        if (localMinima != null) {
            this.currentLM = localMinima.next;
        }
    }

    private Edge processBound(Edge edge, boolean z) {
        Edge edge2;
        Edge edge3;
        if (edge.outIdx == -2) {
            Edge edge4 = edge;
            if (z) {
                while (edge4.getTop().getY() == edge4.next.getBot().getY()) {
                    edge4 = edge4.next;
                }
                while (edge4 != edge && edge4.deltaX == -3.4E38d) {
                    edge4 = edge4.prev;
                }
            } else {
                while (edge4.getTop().getY() == edge4.prev.getBot().getY()) {
                    edge4 = edge4.prev;
                }
                while (edge4 != edge && edge4.deltaX == -3.4E38d) {
                    edge4 = edge4.next;
                }
            }
            if (edge4 != edge) {
                if (z) {
                    edge3 = edge.next;
                } else {
                    edge3 = edge.prev;
                }
                LocalMinima localMinima = new LocalMinima();
                localMinima.next = null;
                localMinima.f751y = edge3.getBot().getY();
                localMinima.leftBound = null;
                localMinima.rightBound = edge3;
                edge3.windDelta = 0;
                Edge processBound = processBound(edge3, z);
                insertLocalMinima(localMinima);
                return processBound;
            } else if (z) {
                return edge4.next;
            } else {
                return edge4.prev;
            }
        } else {
            if (edge.deltaX == -3.4E38d) {
                if (z) {
                    edge2 = edge.prev;
                } else {
                    edge2 = edge.next;
                }
                if (edge2.deltaX == -3.4E38d) {
                    if (!(edge2.getBot().getX() == edge.getBot().getX() || edge2.getTop().getX() == edge.getBot().getX())) {
                        edge.reverseHorizontal();
                    }
                } else if (edge2.getBot().getX() != edge.getBot().getX()) {
                    edge.reverseHorizontal();
                }
            }
            if (z) {
                Edge edge5 = edge;
                while (edge5.getTop().getY() == edge5.next.getBot().getY() && edge5.next.outIdx != -2) {
                    edge5 = edge5.next;
                }
                if (edge5.deltaX == -3.4E38d && edge5.next.outIdx != -2) {
                    Edge edge6 = edge5;
                    while (edge6.prev.deltaX == -3.4E38d) {
                        edge6 = edge6.prev;
                    }
                    if (edge6.prev.getTop().getX() > edge5.next.getTop().getX()) {
                        edge5 = edge6.prev;
                    }
                }
                Edge edge7 = edge;
                while (edge7 != edge5) {
                    edge7.nextInLML = edge7.next;
                    if (!(edge7.deltaX != -3.4E38d || edge7 == edge || edge7.getBot().getX() == edge7.prev.getTop().getX())) {
                        edge7.reverseHorizontal();
                    }
                    edge7 = edge7.next;
                }
                if (!(edge7.deltaX != -3.4E38d || edge7 == edge || edge7.getBot().getX() == edge7.prev.getTop().getX())) {
                    edge7.reverseHorizontal();
                }
                return edge5.next;
            }
            Edge edge8 = edge;
            while (edge8.getTop().getY() == edge8.prev.getBot().getY() && edge8.prev.outIdx != -2) {
                edge8 = edge8.prev;
            }
            if (edge8.deltaX == -3.4E38d && edge8.prev.outIdx != -2) {
                Edge edge9 = edge8;
                while (edge9.next.deltaX == -3.4E38d) {
                    edge9 = edge9.next;
                }
                if (edge9.next.getTop().getX() == edge8.prev.getTop().getX() || edge9.next.getTop().getX() > edge8.prev.getTop().getX()) {
                    edge8 = edge9.next;
                }
            }
            Edge edge10 = edge;
            while (edge10 != edge8) {
                edge10.nextInLML = edge10.prev;
                if (!(edge10.deltaX != -3.4E38d || edge10 == edge || edge10.getBot().getX() == edge10.next.getTop().getX())) {
                    edge10.reverseHorizontal();
                }
                edge10 = edge10.prev;
            }
            if (!(edge10.deltaX != -3.4E38d || edge10 == edge || edge10.getBot().getX() == edge10.next.getTop().getX())) {
                edge10.reverseHorizontal();
            }
            return edge8.prev;
        }
    }

    protected static Path.OutRec parseFirstLeft(Path.OutRec outRec) {
        while (outRec != null && outRec.getPoints() == null) {
            outRec = outRec.firstLeft;
        }
        return outRec;
    }

    /* access modifiers changed from: protected */
    public void reset() {
        LocalMinima localMinima = this.minimaList;
        this.currentLM = localMinima;
        if (localMinima != null) {
            while (localMinima != null) {
                Edge edge = localMinima.leftBound;
                if (edge != null) {
                    edge.setCurrent(new Point.LongPoint(edge.getBot()));
                    edge.side = Edge.Side.LEFT;
                    edge.outIdx = -1;
                }
                Edge edge2 = localMinima.rightBound;
                if (edge2 != null) {
                    edge2.setCurrent(new Point.LongPoint(edge2.getBot()));
                    edge2.side = Edge.Side.RIGHT;
                    edge2.outIdx = -1;
                }
                localMinima = localMinima.next;
            }
        }
    }
}
