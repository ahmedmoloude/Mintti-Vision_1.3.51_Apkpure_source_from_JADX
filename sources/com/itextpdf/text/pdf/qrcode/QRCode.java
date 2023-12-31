package com.itextpdf.text.pdf.qrcode;

public final class QRCode {
    public static final int NUM_MASK_PATTERNS = 8;
    private ErrorCorrectionLevel ecLevel = null;
    private int maskPattern = -1;
    private ByteMatrix matrix = null;
    private int matrixWidth = -1;
    private Mode mode = null;
    private int numDataBytes = -1;
    private int numECBytes = -1;
    private int numRSBlocks = -1;
    private int numTotalBytes = -1;
    private int version = -1;

    public static boolean isValidMaskPattern(int i) {
        return i >= 0 && i < 8;
    }

    public Mode getMode() {
        return this.mode;
    }

    public ErrorCorrectionLevel getECLevel() {
        return this.ecLevel;
    }

    public int getVersion() {
        return this.version;
    }

    public int getMatrixWidth() {
        return this.matrixWidth;
    }

    public int getMaskPattern() {
        return this.maskPattern;
    }

    public int getNumTotalBytes() {
        return this.numTotalBytes;
    }

    public int getNumDataBytes() {
        return this.numDataBytes;
    }

    public int getNumECBytes() {
        return this.numECBytes;
    }

    public int getNumRSBlocks() {
        return this.numRSBlocks;
    }

    public ByteMatrix getMatrix() {
        return this.matrix;
    }

    /* renamed from: at */
    public int mo23926at(int i, int i2) {
        byte b = this.matrix.get(i, i2);
        if (b == 0 || b == 1) {
            return b;
        }
        throw new RuntimeException("Bad value");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0034, code lost:
        r0 = r3.matrix;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0011, code lost:
        r0 = r3.maskPattern;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isValid() {
        /*
            r3 = this;
            com.itextpdf.text.pdf.qrcode.Mode r0 = r3.mode
            if (r0 == 0) goto L_0x0050
            com.itextpdf.text.pdf.qrcode.ErrorCorrectionLevel r0 = r3.ecLevel
            if (r0 == 0) goto L_0x0050
            int r0 = r3.version
            r1 = -1
            if (r0 == r1) goto L_0x0050
            int r0 = r3.matrixWidth
            if (r0 == r1) goto L_0x0050
            int r0 = r3.maskPattern
            if (r0 == r1) goto L_0x0050
            int r2 = r3.numTotalBytes
            if (r2 == r1) goto L_0x0050
            int r2 = r3.numDataBytes
            if (r2 == r1) goto L_0x0050
            int r2 = r3.numECBytes
            if (r2 == r1) goto L_0x0050
            int r2 = r3.numRSBlocks
            if (r2 == r1) goto L_0x0050
            boolean r0 = isValidMaskPattern(r0)
            if (r0 == 0) goto L_0x0050
            int r0 = r3.numTotalBytes
            int r1 = r3.numDataBytes
            int r2 = r3.numECBytes
            int r1 = r1 + r2
            if (r0 != r1) goto L_0x0050
            com.itextpdf.text.pdf.qrcode.ByteMatrix r0 = r3.matrix
            if (r0 == 0) goto L_0x0050
            int r1 = r3.matrixWidth
            int r0 = r0.getWidth()
            if (r1 != r0) goto L_0x0050
            com.itextpdf.text.pdf.qrcode.ByteMatrix r0 = r3.matrix
            int r0 = r0.getWidth()
            com.itextpdf.text.pdf.qrcode.ByteMatrix r1 = r3.matrix
            int r1 = r1.getHeight()
            if (r0 != r1) goto L_0x0050
            r0 = 1
            goto L_0x0051
        L_0x0050:
            r0 = 0
        L_0x0051:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.qrcode.QRCode.isValid():boolean");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(200);
        stringBuffer.append("<<\n");
        stringBuffer.append(" mode: ");
        stringBuffer.append(this.mode);
        stringBuffer.append("\n ecLevel: ");
        stringBuffer.append(this.ecLevel);
        stringBuffer.append("\n version: ");
        stringBuffer.append(this.version);
        stringBuffer.append("\n matrixWidth: ");
        stringBuffer.append(this.matrixWidth);
        stringBuffer.append("\n maskPattern: ");
        stringBuffer.append(this.maskPattern);
        stringBuffer.append("\n numTotalBytes: ");
        stringBuffer.append(this.numTotalBytes);
        stringBuffer.append("\n numDataBytes: ");
        stringBuffer.append(this.numDataBytes);
        stringBuffer.append("\n numECBytes: ");
        stringBuffer.append(this.numECBytes);
        stringBuffer.append("\n numRSBlocks: ");
        stringBuffer.append(this.numRSBlocks);
        if (this.matrix == null) {
            stringBuffer.append("\n matrix: null\n");
        } else {
            stringBuffer.append("\n matrix:\n");
            stringBuffer.append(this.matrix.toString());
        }
        stringBuffer.append(">>\n");
        return stringBuffer.toString();
    }

    public void setMode(Mode mode2) {
        this.mode = mode2;
    }

    public void setECLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        this.ecLevel = errorCorrectionLevel;
    }

    public void setVersion(int i) {
        this.version = i;
    }

    public void setMatrixWidth(int i) {
        this.matrixWidth = i;
    }

    public void setMaskPattern(int i) {
        this.maskPattern = i;
    }

    public void setNumTotalBytes(int i) {
        this.numTotalBytes = i;
    }

    public void setNumDataBytes(int i) {
        this.numDataBytes = i;
    }

    public void setNumECBytes(int i) {
        this.numECBytes = i;
    }

    public void setNumRSBlocks(int i) {
        this.numRSBlocks = i;
    }

    public void setMatrix(ByteMatrix byteMatrix) {
        this.matrix = byteMatrix;
    }
}
