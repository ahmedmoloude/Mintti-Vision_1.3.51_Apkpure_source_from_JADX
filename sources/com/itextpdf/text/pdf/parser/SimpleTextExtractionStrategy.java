package com.itextpdf.text.pdf.parser;

public class SimpleTextExtractionStrategy implements TextExtractionStrategy {
    private Vector lastEnd;
    private Vector lastStart;
    private final StringBuffer result = new StringBuffer();

    public void beginTextBlock() {
    }

    public void endTextBlock() {
    }

    public void renderImage(ImageRenderInfo imageRenderInfo) {
    }

    public String getResultantText() {
        return this.result.toString();
    }

    /* access modifiers changed from: protected */
    public final void appendTextChunk(CharSequence charSequence) {
        this.result.append(charSequence);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0049  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void renderText(com.itextpdf.text.pdf.parser.TextRenderInfo r10) {
        /*
            r9 = this;
            java.lang.StringBuffer r0 = r9.result
            int r0 = r0.length()
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x000c
            r0 = 1
            goto L_0x000d
        L_0x000c:
            r0 = 0
        L_0x000d:
            com.itextpdf.text.pdf.parser.LineSegment r3 = r10.getBaseline()
            com.itextpdf.text.pdf.parser.Vector r4 = r3.getStartPoint()
            com.itextpdf.text.pdf.parser.Vector r3 = r3.getEndPoint()
            if (r0 != 0) goto L_0x0040
            com.itextpdf.text.pdf.parser.Vector r5 = r9.lastStart
            com.itextpdf.text.pdf.parser.Vector r6 = r9.lastEnd
            com.itextpdf.text.pdf.parser.Vector r7 = r6.subtract(r5)
            com.itextpdf.text.pdf.parser.Vector r8 = r5.subtract(r4)
            com.itextpdf.text.pdf.parser.Vector r7 = r7.cross((com.itextpdf.text.pdf.parser.Vector) r8)
            float r7 = r7.lengthSquared()
            com.itextpdf.text.pdf.parser.Vector r5 = r6.subtract(r5)
            float r5 = r5.lengthSquared()
            float r7 = r7 / r5
            r5 = 1065353216(0x3f800000, float:1.0)
            int r5 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r5 <= 0) goto L_0x0040
            r5 = 1
            goto L_0x0041
        L_0x0040:
            r5 = 0
        L_0x0041:
            if (r5 == 0) goto L_0x0049
            java.lang.String r0 = "\n"
            r9.appendTextChunk(r0)
            goto L_0x0088
        L_0x0049:
            if (r0 != 0) goto L_0x0088
            java.lang.StringBuffer r0 = r9.result
            int r5 = r0.length()
            int r5 = r5 - r1
            char r0 = r0.charAt(r5)
            r1 = 32
            if (r0 == r1) goto L_0x0088
            java.lang.String r0 = r10.getText()
            int r0 = r0.length()
            if (r0 <= 0) goto L_0x0088
            java.lang.String r0 = r10.getText()
            char r0 = r0.charAt(r2)
            if (r0 == r1) goto L_0x0088
            com.itextpdf.text.pdf.parser.Vector r0 = r9.lastEnd
            com.itextpdf.text.pdf.parser.Vector r0 = r0.subtract(r4)
            float r0 = r0.length()
            float r1 = r10.getSingleSpaceWidth()
            r2 = 1073741824(0x40000000, float:2.0)
            float r1 = r1 / r2
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L_0x0088
            java.lang.String r0 = " "
            r9.appendTextChunk(r0)
        L_0x0088:
            java.lang.String r10 = r10.getText()
            r9.appendTextChunk(r10)
            r9.lastStart = r4
            r9.lastEnd = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy.renderText(com.itextpdf.text.pdf.parser.TextRenderInfo):void");
    }
}
