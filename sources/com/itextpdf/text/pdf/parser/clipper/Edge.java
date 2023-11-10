package com.itextpdf.text.pdf.parser.clipper;

import com.itextpdf.text.pdf.parser.clipper.Clipper;
import com.itextpdf.text.pdf.parser.clipper.Point;
import java.math.BigInteger;
import java.util.logging.Logger;

class Edge {
    protected static final double HORIZONTAL = -3.4E38d;
    private static final Logger LOGGER = Logger.getLogger(Edge.class.getName());
    protected static final int SKIP = -2;
    protected static final int UNASSIGNED = -1;
    private final Point.LongPoint bot = new Point.LongPoint();
    private final Point.LongPoint current = new Point.LongPoint();
    private final Point.LongPoint delta = new Point.LongPoint();
    double deltaX;
    Edge next;
    Edge nextInAEL;
    Edge nextInLML;
    Edge nextInSEL;
    int outIdx;
    Clipper.PolyType polyTyp;
    Edge prev;
    Edge prevInAEL;
    Edge prevInSEL;
    Side side;

    /* renamed from: top  reason: collision with root package name */
    private final Point.LongPoint f2173top = new Point.LongPoint();
    int windCnt;
    int windCnt2;
    int windDelta;

    enum Side {
        LEFT,
        RIGHT
    }

    static boolean doesE2InsertBeforeE1(Edge edge, Edge edge2) {
        if (edge2.current.getX() == edge.current.getX()) {
            if (edge2.f2173top.getY() > edge.f2173top.getY()) {
                if (edge2.f2173top.getX() < topX(edge, edge2.f2173top.getY())) {
                    return true;
                }
                return false;
            } else if (edge.f2173top.getX() > topX(edge2, edge.f2173top.getY())) {
                return true;
            } else {
                return false;
            }
        } else if (edge2.current.getX() < edge.current.getX()) {
            return true;
        } else {
            return false;
        }
    }

    static boolean slopesEqual(Edge edge, Edge edge2, boolean z) {
        if (z) {
            return BigInteger.valueOf(edge.getDelta().getY()).multiply(BigInteger.valueOf(edge2.getDelta().getX())).equals(BigInteger.valueOf(edge.getDelta().getX()).multiply(BigInteger.valueOf(edge2.getDelta().getY())));
        }
        return edge.getDelta().getY() * edge2.getDelta().getX() == edge.getDelta().getX() * edge2.getDelta().getY();
    }

    static void swapPolyIndexes(Edge edge, Edge edge2) {
        int i = edge.outIdx;
        edge.outIdx = edge2.outIdx;
        edge2.outIdx = i;
    }

    static void swapSides(Edge edge, Edge edge2) {
        Side side2 = edge.side;
        edge.side = edge2.side;
        edge2.side = side2;
    }

    static long topX(Edge edge, long j) {
        if (j == edge.getTop().getY()) {
            return edge.getTop().getX();
        }
        return edge.getBot().getX() + Math.round(edge.deltaX * ((double) (j - edge.getBot().getY())));
    }

    public Edge findNextLocMin() {
        Edge edge = this;
        while (true) {
            if (!edge.bot.equals(edge.prev.bot) || edge.current.equals(edge.f2173top)) {
                edge = edge.next;
            } else if (edge.deltaX != HORIZONTAL && edge.prev.deltaX != HORIZONTAL) {
                return edge;
            } else {
                while (true) {
                    Edge edge2 = edge.prev;
                    if (edge2.deltaX != HORIZONTAL) {
                        break;
                    }
                    edge = edge2;
                }
                Edge edge3 = edge;
                while (edge3.deltaX == HORIZONTAL) {
                    edge3 = edge3.next;
                }
                if (edge3.f2173top.getY() != edge3.prev.bot.getY()) {
                    return edge.prev.bot.getX() < edge3.bot.getX() ? edge : edge3;
                }
                edge = edge3;
            }
        }
    }

    public Point.LongPoint getBot() {
        return this.bot;
    }

    public Point.LongPoint getCurrent() {
        return this.current;
    }

    public Point.LongPoint getDelta() {
        return this.delta;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0011, code lost:
        if (r0.nextInLML == null) goto L_0x0028;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0024, code lost:
        if (r0.nextInLML == null) goto L_0x0028;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.pdf.parser.clipper.Edge getMaximaPair() {
        /*
            r4 = this;
            com.itextpdf.text.pdf.parser.clipper.Edge r0 = r4.next
            com.itextpdf.text.pdf.parser.clipper.Point$LongPoint r0 = r0.f2173top
            com.itextpdf.text.pdf.parser.clipper.Point$LongPoint r1 = r4.f2173top
            boolean r0 = r0.equals(r1)
            r1 = 0
            if (r0 == 0) goto L_0x0014
            com.itextpdf.text.pdf.parser.clipper.Edge r0 = r4.next
            com.itextpdf.text.pdf.parser.clipper.Edge r2 = r0.nextInLML
            if (r2 != 0) goto L_0x0014
            goto L_0x0028
        L_0x0014:
            com.itextpdf.text.pdf.parser.clipper.Edge r0 = r4.prev
            com.itextpdf.text.pdf.parser.clipper.Point$LongPoint r0 = r0.f2173top
            com.itextpdf.text.pdf.parser.clipper.Point$LongPoint r2 = r4.f2173top
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0027
            com.itextpdf.text.pdf.parser.clipper.Edge r0 = r4.prev
            com.itextpdf.text.pdf.parser.clipper.Edge r2 = r0.nextInLML
            if (r2 != 0) goto L_0x0027
            goto L_0x0028
        L_0x0027:
            r0 = r1
        L_0x0028:
            if (r0 == 0) goto L_0x003c
            int r2 = r0.outIdx
            r3 = -2
            if (r2 == r3) goto L_0x003b
            com.itextpdf.text.pdf.parser.clipper.Edge r2 = r0.nextInAEL
            com.itextpdf.text.pdf.parser.clipper.Edge r3 = r0.prevInAEL
            if (r2 != r3) goto L_0x003c
            boolean r2 = r0.isHorizontal()
            if (r2 != 0) goto L_0x003c
        L_0x003b:
            return r1
        L_0x003c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.parser.clipper.Edge.getMaximaPair():com.itextpdf.text.pdf.parser.clipper.Edge");
    }

    public Edge getNextInAEL(Clipper.Direction direction) {
        return direction == Clipper.Direction.LEFT_TO_RIGHT ? this.nextInAEL : this.prevInAEL;
    }

    public Point.LongPoint getTop() {
        return this.f2173top;
    }

    public boolean isContributing(Clipper.PolyFillType polyFillType, Clipper.PolyFillType polyFillType2, Clipper.ClipType clipType) {
        LOGGER.entering(Edge.class.getName(), "isContributing");
        if (this.polyTyp == Clipper.PolyType.SUBJECT) {
            Clipper.PolyFillType polyFillType3 = polyFillType2;
            polyFillType2 = polyFillType;
            polyFillType = polyFillType3;
        }
        int i = C15321.f755xe9aea078[polyFillType.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (this.windCnt != -1) {
                        return false;
                    }
                } else if (this.windCnt != 1) {
                    return false;
                }
            } else if (Math.abs(this.windCnt) != 1) {
                return false;
            }
        } else if (this.windDelta == 0 && this.windCnt != 1) {
            return false;
        }
        int i2 = C15321.$SwitchMap$com$itextpdf$text$pdf$parser$clipper$Clipper$ClipType[clipType.ordinal()];
        if (i2 == 1) {
            int i3 = C15321.f755xe9aea078[polyFillType2.ordinal()];
            if (i3 == 1 || i3 == 2) {
                if (this.windCnt2 != 0) {
                    return true;
                }
                return false;
            } else if (i3 != 3) {
                if (this.windCnt2 < 0) {
                    return true;
                }
                return false;
            } else if (this.windCnt2 > 0) {
                return true;
            } else {
                return false;
            }
        } else if (i2 == 2) {
            int i4 = C15321.f755xe9aea078[polyFillType2.ordinal()];
            if (i4 == 1 || i4 == 2) {
                if (this.windCnt2 == 0) {
                    return true;
                }
                return false;
            } else if (i4 != 3) {
                if (this.windCnt2 >= 0) {
                    return true;
                }
                return false;
            } else if (this.windCnt2 <= 0) {
                return true;
            } else {
                return false;
            }
        } else if (i2 != 3) {
            if (i2 != 4 || this.windDelta != 0) {
                return true;
            }
            int i5 = C15321.f755xe9aea078[polyFillType2.ordinal()];
            if (i5 == 1 || i5 == 2) {
                if (this.windCnt2 == 0) {
                    return true;
                }
                return false;
            } else if (i5 != 3) {
                if (this.windCnt2 >= 0) {
                    return true;
                }
                return false;
            } else if (this.windCnt2 <= 0) {
                return true;
            } else {
                return false;
            }
        } else if (this.polyTyp == Clipper.PolyType.SUBJECT) {
            int i6 = C15321.f755xe9aea078[polyFillType2.ordinal()];
            if (i6 == 1 || i6 == 2) {
                if (this.windCnt2 == 0) {
                    return true;
                }
                return false;
            } else if (i6 != 3) {
                if (this.windCnt2 >= 0) {
                    return true;
                }
                return false;
            } else if (this.windCnt2 <= 0) {
                return true;
            } else {
                return false;
            }
        } else {
            int i7 = C15321.f755xe9aea078[polyFillType2.ordinal()];
            if (i7 == 1 || i7 == 2) {
                if (this.windCnt2 != 0) {
                    return true;
                }
                return false;
            } else if (i7 != 3) {
                if (this.windCnt2 < 0) {
                    return true;
                }
                return false;
            } else if (this.windCnt2 > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    /* renamed from: com.itextpdf.text.pdf.parser.clipper.Edge$1 */
    static /* synthetic */ class C15321 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$text$pdf$parser$clipper$Clipper$ClipType;

        /* renamed from: $SwitchMap$com$itextpdf$text$pdf$parser$clipper$Clipper$PolyFillType */
        static final /* synthetic */ int[] f755xe9aea078;

        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|(2:1|2)|3|(2:5|6)|7|9|10|(2:11|12)|13|15|16|17|18|19|20|22) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0044 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x004e */
        static {
            /*
                com.itextpdf.text.pdf.parser.clipper.Clipper$ClipType[] r0 = com.itextpdf.text.pdf.parser.clipper.Clipper.ClipType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$itextpdf$text$pdf$parser$clipper$Clipper$ClipType = r0
                r1 = 1
                com.itextpdf.text.pdf.parser.clipper.Clipper$ClipType r2 = com.itextpdf.text.pdf.parser.clipper.Clipper.ClipType.INTERSECTION     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                r0 = 2
                int[] r2 = $SwitchMap$com$itextpdf$text$pdf$parser$clipper$Clipper$ClipType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.itextpdf.text.pdf.parser.clipper.Clipper$ClipType r3 = com.itextpdf.text.pdf.parser.clipper.Clipper.ClipType.UNION     // Catch:{ NoSuchFieldError -> 0x001d }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                r2 = 3
                int[] r3 = $SwitchMap$com$itextpdf$text$pdf$parser$clipper$Clipper$ClipType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.itextpdf.text.pdf.parser.clipper.Clipper$ClipType r4 = com.itextpdf.text.pdf.parser.clipper.Clipper.ClipType.DIFFERENCE     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r3 = $SwitchMap$com$itextpdf$text$pdf$parser$clipper$Clipper$ClipType     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.itextpdf.text.pdf.parser.clipper.Clipper$ClipType r4 = com.itextpdf.text.pdf.parser.clipper.Clipper.ClipType.XOR     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r5 = 4
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                com.itextpdf.text.pdf.parser.clipper.Clipper$PolyFillType[] r3 = com.itextpdf.text.pdf.parser.clipper.Clipper.PolyFillType.values()
                int r3 = r3.length
                int[] r3 = new int[r3]
                f755xe9aea078 = r3
                com.itextpdf.text.pdf.parser.clipper.Clipper$PolyFillType r4 = com.itextpdf.text.pdf.parser.clipper.Clipper.PolyFillType.EVEN_ODD     // Catch:{ NoSuchFieldError -> 0x0044 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0044 }
                r3[r4] = r1     // Catch:{ NoSuchFieldError -> 0x0044 }
            L_0x0044:
                int[] r1 = f755xe9aea078     // Catch:{ NoSuchFieldError -> 0x004e }
                com.itextpdf.text.pdf.parser.clipper.Clipper$PolyFillType r3 = com.itextpdf.text.pdf.parser.clipper.Clipper.PolyFillType.NON_ZERO     // Catch:{ NoSuchFieldError -> 0x004e }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x004e }
                r1[r3] = r0     // Catch:{ NoSuchFieldError -> 0x004e }
            L_0x004e:
                int[] r0 = f755xe9aea078     // Catch:{ NoSuchFieldError -> 0x0058 }
                com.itextpdf.text.pdf.parser.clipper.Clipper$PolyFillType r1 = com.itextpdf.text.pdf.parser.clipper.Clipper.PolyFillType.POSITIVE     // Catch:{ NoSuchFieldError -> 0x0058 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0058 }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0058 }
            L_0x0058:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.parser.clipper.Edge.C15321.<clinit>():void");
        }
    }

    public boolean isEvenOddAltFillType(Clipper.PolyFillType polyFillType, Clipper.PolyFillType polyFillType2) {
        if (this.polyTyp == Clipper.PolyType.SUBJECT) {
            if (polyFillType == Clipper.PolyFillType.EVEN_ODD) {
                return true;
            }
            return false;
        } else if (polyFillType2 == Clipper.PolyFillType.EVEN_ODD) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEvenOddFillType(Clipper.PolyFillType polyFillType, Clipper.PolyFillType polyFillType2) {
        if (this.polyTyp == Clipper.PolyType.SUBJECT) {
            if (polyFillType2 == Clipper.PolyFillType.EVEN_ODD) {
                return true;
            }
            return false;
        } else if (polyFillType == Clipper.PolyFillType.EVEN_ODD) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isHorizontal() {
        return this.delta.getY() == 0;
    }

    public boolean isIntermediate(double d) {
        return ((double) this.f2173top.getY()) == d && this.nextInLML != null;
    }

    public boolean isMaxima(double d) {
        return ((double) this.f2173top.getY()) == d && this.nextInLML == null;
    }

    public void reverseHorizontal() {
        long x = this.f2173top.getX();
        this.f2173top.setX(Long.valueOf(this.bot.getX()));
        this.bot.setX(Long.valueOf(x));
        long z = this.f2173top.getZ();
        this.f2173top.setZ(Long.valueOf(this.bot.getZ()));
        this.bot.setZ(Long.valueOf(z));
    }

    public void setBot(Point.LongPoint longPoint) {
        this.bot.set(longPoint);
    }

    public void setCurrent(Point.LongPoint longPoint) {
        this.current.set(longPoint);
    }

    public void setTop(Point.LongPoint longPoint) {
        this.f2173top.set(longPoint);
    }

    public String toString() {
        return "TEdge [Bot=" + this.bot + ", Curr=" + this.current + ", Top=" + this.f2173top + ", Delta=" + this.delta + ", Dx=" + this.deltaX + ", PolyTyp=" + this.polyTyp + ", Side=" + this.side + ", WindDelta=" + this.windDelta + ", WindCnt=" + this.windCnt + ", WindCnt2=" + this.windCnt2 + ", OutIdx=" + this.outIdx + ", Next=" + this.next + ", Prev=" + this.prev + ", NextInLML=" + this.nextInLML + ", NextInAEL=" + this.nextInAEL + ", PrevInAEL=" + this.prevInAEL + ", NextInSEL=" + this.nextInSEL + ", PrevInSEL=" + this.prevInSEL + "]";
    }

    public void updateDeltaX() {
        this.delta.setX(Long.valueOf(this.f2173top.getX() - this.bot.getX()));
        this.delta.setY(Long.valueOf(this.f2173top.getY() - this.bot.getY()));
        if (this.delta.getY() == 0) {
            this.deltaX = HORIZONTAL;
        } else {
            this.deltaX = ((double) this.delta.getX()) / ((double) this.delta.getY());
        }
    }
}
