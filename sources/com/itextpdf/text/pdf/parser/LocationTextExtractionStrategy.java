package com.itextpdf.text.pdf.parser;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocationTextExtractionStrategy implements TextExtractionStrategy {
    static boolean DUMP_STATE = false;
    private final List<TextChunk> locationalResult = new ArrayList();

    public interface TextChunkFilter {
        boolean accept(TextChunk textChunk);
    }

    public void beginTextBlock() {
    }

    public void endTextBlock() {
    }

    public void renderImage(ImageRenderInfo imageRenderInfo) {
    }

    private boolean startsWithSpace(String str) {
        if (str.length() != 0 && str.charAt(0) == ' ') {
            return true;
        }
        return false;
    }

    private boolean endsWithSpace(String str) {
        if (str.length() != 0 && str.charAt(str.length() - 1) == ' ') {
            return true;
        }
        return false;
    }

    private List<TextChunk> filterTextChunks(List<TextChunk> list, TextChunkFilter textChunkFilter) {
        if (textChunkFilter == null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (TextChunk next : list) {
            if (textChunkFilter.accept(next)) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public boolean isChunkAtWordBoundary(TextChunk textChunk, TextChunk textChunk2) {
        if (textChunk.getCharSpaceWidth() < 0.1f) {
            return false;
        }
        float distanceFromEndOf = textChunk.distanceFromEndOf(textChunk2);
        if (distanceFromEndOf < (-textChunk.getCharSpaceWidth()) || distanceFromEndOf > textChunk.getCharSpaceWidth() / 2.0f) {
            return true;
        }
        return false;
    }

    public String getResultantText(TextChunkFilter textChunkFilter) {
        if (DUMP_STATE) {
            dumpState();
        }
        List<TextChunk> filterTextChunks = filterTextChunks(this.locationalResult, textChunkFilter);
        Collections.sort(filterTextChunks);
        StringBuffer stringBuffer = new StringBuffer();
        TextChunk textChunk = null;
        for (TextChunk next : filterTextChunks) {
            if (textChunk == null) {
                stringBuffer.append(next.text);
            } else if (next.sameLine(textChunk)) {
                if (isChunkAtWordBoundary(next, textChunk) && !startsWithSpace(next.text) && !endsWithSpace(textChunk.text)) {
                    stringBuffer.append(' ');
                }
                stringBuffer.append(next.text);
            } else {
                stringBuffer.append(10);
                stringBuffer.append(next.text);
            }
            textChunk = next;
        }
        return stringBuffer.toString();
    }

    public String getResultantText() {
        return getResultantText((TextChunkFilter) null);
    }

    private void dumpState() {
        for (TextChunk access$100 : this.locationalResult) {
            access$100.printDiagnostics();
            System.out.println();
        }
    }

    public void renderText(TextRenderInfo textRenderInfo) {
        LineSegment baseline = textRenderInfo.getBaseline();
        if (textRenderInfo.getRise() != 0.0f) {
            baseline = baseline.transformBy(new Matrix(0.0f, -textRenderInfo.getRise()));
        }
        this.locationalResult.add(new TextChunk(textRenderInfo.getText(), baseline.getStartPoint(), baseline.getEndPoint(), textRenderInfo.getSingleSpaceWidth()));
    }

    public static class TextChunk implements Comparable<TextChunk> {
        private final float charSpaceWidth;
        private final float distParallelEnd;
        private final float distParallelStart;
        private final int distPerpendicular;
        private final Vector endLocation;
        private final int orientationMagnitude;
        private final Vector orientationVector;
        private final Vector startLocation;
        /* access modifiers changed from: private */
        public final String text;

        private static int compareInts(int i, int i2) {
            if (i == i2) {
                return 0;
            }
            return i < i2 ? -1 : 1;
        }

        public TextChunk(String str, Vector vector, Vector vector2, float f) {
            this.text = str;
            this.startLocation = vector;
            this.endLocation = vector2;
            this.charSpaceWidth = f;
            Vector subtract = vector2.subtract(vector);
            Vector normalize = (subtract.length() == 0.0f ? new Vector(1.0f, 0.0f, 0.0f) : subtract).normalize();
            this.orientationVector = normalize;
            this.orientationMagnitude = (int) (Math.atan2((double) normalize.get(1), (double) normalize.get(0)) * 1000.0d);
            this.distPerpendicular = (int) vector.subtract(new Vector(0.0f, 0.0f, 1.0f)).cross(normalize).get(2);
            this.distParallelStart = normalize.dot(vector);
            this.distParallelEnd = normalize.dot(vector2);
        }

        public Vector getStartLocation() {
            return this.startLocation;
        }

        public Vector getEndLocation() {
            return this.endLocation;
        }

        public String getText() {
            return this.text;
        }

        public float getCharSpaceWidth() {
            return this.charSpaceWidth;
        }

        /* access modifiers changed from: private */
        public void printDiagnostics() {
            PrintStream printStream = System.out;
            printStream.println("Text (@" + this.startLocation + " -> " + this.endLocation + "): " + this.text);
            PrintStream printStream2 = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append("orientationMagnitude: ");
            sb.append(this.orientationMagnitude);
            printStream2.println(sb.toString());
            PrintStream printStream3 = System.out;
            printStream3.println("distPerpendicular: " + this.distPerpendicular);
            PrintStream printStream4 = System.out;
            printStream4.println("distParallel: " + this.distParallelStart);
        }

        public boolean sameLine(TextChunk textChunk) {
            if (this.orientationMagnitude == textChunk.orientationMagnitude && this.distPerpendicular == textChunk.distPerpendicular) {
                return true;
            }
            return false;
        }

        public float distanceFromEndOf(TextChunk textChunk) {
            return this.distParallelStart - textChunk.distParallelEnd;
        }

        public int compareTo(TextChunk textChunk) {
            if (this == textChunk) {
                return 0;
            }
            int compareInts = compareInts(this.orientationMagnitude, textChunk.orientationMagnitude);
            if (compareInts != 0) {
                return compareInts;
            }
            int compareInts2 = compareInts(this.distPerpendicular, textChunk.distPerpendicular);
            if (compareInts2 != 0) {
                return compareInts2;
            }
            return Float.compare(this.distParallelStart, textChunk.distParallelStart);
        }
    }
}
