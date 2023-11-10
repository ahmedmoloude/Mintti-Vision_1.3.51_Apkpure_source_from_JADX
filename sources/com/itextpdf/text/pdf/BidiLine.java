package com.itextpdf.text.pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.TabStop;
import com.itextpdf.text.Utilities;
import java.util.ArrayList;
import java.util.Iterator;

public class BidiLine {
    protected static final IntHashtable mirrorChars;
    protected int arabicOptions;
    protected ArrayList<PdfChunk> chunks = new ArrayList<>();
    protected int currentChar = 0;
    protected PdfChunk[] detailChunks = new PdfChunk[256];
    protected int[] indexChars = new int[256];
    protected int indexChunk = 0;
    protected int indexChunkChar = 0;
    protected boolean isWordSplit = false;
    protected byte[] orderLevels = new byte[256];
    protected int pieceSize = 256;
    protected int runDirection;
    protected boolean shortStore;
    protected int storedCurrentChar = 0;
    protected PdfChunk[] storedDetailChunks = new PdfChunk[0];
    protected int[] storedIndexChars = new int[0];
    protected int storedIndexChunk = 0;
    protected int storedIndexChunkChar = 0;
    protected byte[] storedOrderLevels = new byte[0];
    protected int storedRunDirection;
    protected char[] storedText = new char[0];
    protected int storedTotalTextLength = 0;
    protected char[] text = new char[256];
    protected int totalTextLength = 0;

    public static boolean isWS(char c) {
        return c <= ' ';
    }

    static {
        IntHashtable intHashtable = new IntHashtable();
        mirrorChars = intHashtable;
        intHashtable.put(40, 41);
        intHashtable.put(41, 40);
        intHashtable.put(60, 62);
        intHashtable.put(62, 60);
        intHashtable.put(91, 93);
        intHashtable.put(93, 91);
        intHashtable.put(123, 125);
        intHashtable.put(125, 123);
        intHashtable.put(171, 187);
        intHashtable.put(187, 171);
        intHashtable.put(8249, 8250);
        intHashtable.put(8250, 8249);
        intHashtable.put(8261, 8262);
        intHashtable.put(8262, 8261);
        intHashtable.put(8317, 8318);
        intHashtable.put(8318, 8317);
        intHashtable.put(8333, 8334);
        intHashtable.put(8334, 8333);
        intHashtable.put(8712, 8715);
        intHashtable.put(8713, 8716);
        intHashtable.put(8714, 8717);
        intHashtable.put(8715, 8712);
        intHashtable.put(8716, 8713);
        intHashtable.put(8717, 8714);
        intHashtable.put(8725, 10741);
        intHashtable.put(8764, 8765);
        intHashtable.put(8765, 8764);
        intHashtable.put(8771, 8909);
        intHashtable.put(8786, 8787);
        intHashtable.put(8787, 8786);
        intHashtable.put(8788, 8789);
        intHashtable.put(8789, 8788);
        intHashtable.put(8804, 8805);
        intHashtable.put(8805, 8804);
        intHashtable.put(8806, 8807);
        intHashtable.put(8807, 8806);
        intHashtable.put(8808, 8809);
        intHashtable.put(8809, 8808);
        intHashtable.put(8810, 8811);
        intHashtable.put(8811, 8810);
        intHashtable.put(8814, 8815);
        intHashtable.put(8815, 8814);
        intHashtable.put(8816, 8817);
        intHashtable.put(8817, 8816);
        intHashtable.put(8818, 8819);
        intHashtable.put(8819, 8818);
        intHashtable.put(8820, 8821);
        intHashtable.put(8821, 8820);
        intHashtable.put(8822, 8823);
        intHashtable.put(8823, 8822);
        intHashtable.put(8824, 8825);
        intHashtable.put(8825, 8824);
        intHashtable.put(8826, 8827);
        intHashtable.put(8827, 8826);
        intHashtable.put(8828, 8829);
        intHashtable.put(8829, 8828);
        intHashtable.put(8830, 8831);
        intHashtable.put(8831, 8830);
        intHashtable.put(8832, 8833);
        intHashtable.put(8833, 8832);
        intHashtable.put(8834, 8835);
        intHashtable.put(8835, 8834);
        intHashtable.put(8836, 8837);
        intHashtable.put(8837, 8836);
        intHashtable.put(8838, 8839);
        intHashtable.put(8839, 8838);
        intHashtable.put(8840, 8841);
        intHashtable.put(8841, 8840);
        intHashtable.put(8842, 8843);
        intHashtable.put(8843, 8842);
        intHashtable.put(8847, 8848);
        intHashtable.put(8848, 8847);
        intHashtable.put(8849, 8850);
        intHashtable.put(8850, 8849);
        intHashtable.put(8856, 10680);
        intHashtable.put(8866, 8867);
        intHashtable.put(8867, 8866);
        intHashtable.put(8870, 10974);
        intHashtable.put(8872, 10980);
        intHashtable.put(8873, 10979);
        intHashtable.put(8875, 10981);
        intHashtable.put(8880, 8881);
        intHashtable.put(8881, 8880);
        intHashtable.put(8882, 8883);
        intHashtable.put(8883, 8882);
        intHashtable.put(8884, 8885);
        intHashtable.put(8885, 8884);
        intHashtable.put(8886, 8887);
        intHashtable.put(8887, 8886);
        intHashtable.put(8905, 8906);
        intHashtable.put(8906, 8905);
        intHashtable.put(8907, 8908);
        intHashtable.put(8908, 8907);
        intHashtable.put(8909, 8771);
        intHashtable.put(8912, 8913);
        intHashtable.put(8913, 8912);
        intHashtable.put(8918, 8919);
        intHashtable.put(8919, 8918);
        intHashtable.put(8920, 8921);
        intHashtable.put(8921, 8920);
        intHashtable.put(8922, 8923);
        intHashtable.put(8923, 8922);
        intHashtable.put(8924, 8925);
        intHashtable.put(8925, 8924);
        intHashtable.put(8926, 8927);
        intHashtable.put(8927, 8926);
        intHashtable.put(8928, 8929);
        intHashtable.put(8929, 8928);
        intHashtable.put(8930, 8931);
        intHashtable.put(8931, 8930);
        intHashtable.put(8932, 8933);
        intHashtable.put(8933, 8932);
        intHashtable.put(8934, 8935);
        intHashtable.put(8935, 8934);
        intHashtable.put(8936, 8937);
        intHashtable.put(8937, 8936);
        intHashtable.put(8938, 8939);
        intHashtable.put(8939, 8938);
        intHashtable.put(8940, 8941);
        intHashtable.put(8941, 8940);
        intHashtable.put(8944, 8945);
        intHashtable.put(8945, 8944);
        intHashtable.put(8946, 8954);
        intHashtable.put(8947, 8955);
        intHashtable.put(8948, 8956);
        intHashtable.put(8950, 8957);
        intHashtable.put(8951, 8958);
        intHashtable.put(8954, 8946);
        intHashtable.put(8955, 8947);
        intHashtable.put(8956, 8948);
        intHashtable.put(8957, 8950);
        intHashtable.put(8958, 8951);
        intHashtable.put(8968, 8969);
        intHashtable.put(8969, 8968);
        intHashtable.put(8970, 8971);
        intHashtable.put(8971, 8970);
        intHashtable.put(9001, 9002);
        intHashtable.put(9002, 9001);
        intHashtable.put(10088, 10089);
        intHashtable.put(10089, 10088);
        intHashtable.put(10090, 10091);
        intHashtable.put(10091, 10090);
        intHashtable.put(10092, 10093);
        intHashtable.put(10093, 10092);
        intHashtable.put(10094, 10095);
        intHashtable.put(10095, 10094);
        intHashtable.put(10096, 10097);
        intHashtable.put(10097, 10096);
        intHashtable.put(10098, 10099);
        intHashtable.put(10099, 10098);
        intHashtable.put(10100, 10101);
        intHashtable.put(10101, 10100);
        intHashtable.put(10197, 10198);
        intHashtable.put(10198, 10197);
        intHashtable.put(10205, 10206);
        intHashtable.put(10206, 10205);
        intHashtable.put(10210, 10211);
        intHashtable.put(10211, 10210);
        intHashtable.put(10212, 10213);
        intHashtable.put(10213, 10212);
        intHashtable.put(10214, 10215);
        intHashtable.put(10215, 10214);
        intHashtable.put(10216, 10217);
        intHashtable.put(10217, 10216);
        intHashtable.put(10218, 10219);
        intHashtable.put(10219, 10218);
        intHashtable.put(10627, 10628);
        intHashtable.put(10628, 10627);
        intHashtable.put(10629, 10630);
        intHashtable.put(10630, 10629);
        intHashtable.put(10631, 10632);
        intHashtable.put(10632, 10631);
        intHashtable.put(10633, 10634);
        intHashtable.put(10634, 10633);
        intHashtable.put(10635, 10636);
        intHashtable.put(10636, 10635);
        intHashtable.put(10637, 10640);
        intHashtable.put(10638, 10639);
        intHashtable.put(10639, 10638);
        intHashtable.put(10640, 10637);
        intHashtable.put(10641, 10642);
        intHashtable.put(10642, 10641);
        intHashtable.put(10643, 10644);
        intHashtable.put(10644, 10643);
        intHashtable.put(10645, 10646);
        intHashtable.put(10646, 10645);
        intHashtable.put(10647, 10648);
        intHashtable.put(10648, 10647);
        intHashtable.put(10680, 8856);
        intHashtable.put(10688, 10689);
        intHashtable.put(10689, 10688);
        intHashtable.put(10692, 10693);
        intHashtable.put(10693, 10692);
        intHashtable.put(10703, 10704);
        intHashtable.put(10704, 10703);
        intHashtable.put(10705, 10706);
        intHashtable.put(10706, 10705);
        intHashtable.put(10708, 10709);
        intHashtable.put(10709, 10708);
        intHashtable.put(10712, 10713);
        intHashtable.put(10713, 10712);
        intHashtable.put(10714, 10715);
        intHashtable.put(10715, 10714);
        intHashtable.put(10741, 8725);
        intHashtable.put(10744, 10745);
        intHashtable.put(10745, 10744);
        intHashtable.put(10748, 10749);
        intHashtable.put(10749, 10748);
        intHashtable.put(10795, 10796);
        intHashtable.put(10796, 10795);
        intHashtable.put(10797, 10796);
        intHashtable.put(10798, 10797);
        intHashtable.put(10804, 10805);
        intHashtable.put(10805, 10804);
        intHashtable.put(10812, 10813);
        intHashtable.put(10813, 10812);
        intHashtable.put(10852, 10853);
        intHashtable.put(10853, 10852);
        intHashtable.put(10873, 10874);
        intHashtable.put(10874, 10873);
        intHashtable.put(10877, 10878);
        intHashtable.put(10878, 10877);
        intHashtable.put(10879, 10880);
        intHashtable.put(10880, 10879);
        intHashtable.put(10881, 10882);
        intHashtable.put(10882, 10881);
        intHashtable.put(10883, 10884);
        intHashtable.put(10884, 10883);
        intHashtable.put(10891, 10892);
        intHashtable.put(10892, 10891);
        intHashtable.put(10897, 10898);
        intHashtable.put(10898, 10897);
        intHashtable.put(10899, 10900);
        intHashtable.put(10900, 10899);
        intHashtable.put(10901, 10902);
        intHashtable.put(10902, 10901);
        intHashtable.put(10903, 10904);
        intHashtable.put(10904, 10903);
        intHashtable.put(10905, 10906);
        intHashtable.put(10906, 10905);
        intHashtable.put(10907, 10908);
        intHashtable.put(10908, 10907);
        intHashtable.put(10913, 10914);
        intHashtable.put(10914, 10913);
        intHashtable.put(10918, 10919);
        intHashtable.put(10919, 10918);
        intHashtable.put(10920, 10921);
        intHashtable.put(10921, 10920);
        intHashtable.put(10922, 10923);
        intHashtable.put(10923, 10922);
        intHashtable.put(10924, 10925);
        intHashtable.put(10925, 10924);
        intHashtable.put(10927, 10928);
        intHashtable.put(10928, 10927);
        intHashtable.put(10931, 10932);
        intHashtable.put(10932, 10931);
        intHashtable.put(10939, 10940);
        intHashtable.put(10940, 10939);
        intHashtable.put(10941, 10942);
        intHashtable.put(10942, 10941);
        intHashtable.put(10943, 10944);
        intHashtable.put(10944, 10943);
        intHashtable.put(10945, 10946);
        intHashtable.put(10946, 10945);
        intHashtable.put(10947, 10948);
        intHashtable.put(10948, 10947);
        intHashtable.put(10949, 10950);
        intHashtable.put(10950, 10949);
        intHashtable.put(10957, 10958);
        intHashtable.put(10958, 10957);
        intHashtable.put(10959, 10960);
        intHashtable.put(10960, 10959);
        intHashtable.put(10961, 10962);
        intHashtable.put(10962, 10961);
        intHashtable.put(10963, 10964);
        intHashtable.put(10964, 10963);
        intHashtable.put(10965, 10966);
        intHashtable.put(10966, 10965);
        intHashtable.put(10974, 8870);
        intHashtable.put(10979, 8873);
        intHashtable.put(10980, 8872);
        intHashtable.put(10981, 8875);
        intHashtable.put(10988, 10989);
        intHashtable.put(10989, 10988);
        intHashtable.put(10999, 11000);
        intHashtable.put(11000, 10999);
        intHashtable.put(11001, 11002);
        intHashtable.put(11002, 11001);
        intHashtable.put(12296, 12297);
        intHashtable.put(12297, 12296);
        intHashtable.put(12298, 12299);
        intHashtable.put(12299, 12298);
        intHashtable.put(12300, 12301);
        intHashtable.put(12301, 12300);
        intHashtable.put(12302, 12303);
        intHashtable.put(12303, 12302);
        intHashtable.put(12304, 12305);
        intHashtable.put(12305, 12304);
        intHashtable.put(12308, 12309);
        intHashtable.put(12309, 12308);
        intHashtable.put(12310, 12311);
        intHashtable.put(12311, 12310);
        intHashtable.put(12312, 12313);
        intHashtable.put(12313, 12312);
        intHashtable.put(12314, 12315);
        intHashtable.put(12315, 12314);
        intHashtable.put(65288, 65289);
        intHashtable.put(65289, 65288);
        intHashtable.put(65308, 65310);
        intHashtable.put(65310, 65308);
        intHashtable.put(65339, 65341);
        intHashtable.put(65341, 65339);
        intHashtable.put(65371, 65373);
        intHashtable.put(65373, 65371);
        intHashtable.put(65375, 65376);
        intHashtable.put(65376, 65375);
        intHashtable.put(65378, 65379);
        intHashtable.put(65379, 65378);
    }

    public BidiLine() {
    }

    public BidiLine(BidiLine bidiLine) {
        this.runDirection = bidiLine.runDirection;
        this.pieceSize = bidiLine.pieceSize;
        this.text = (char[]) bidiLine.text.clone();
        this.detailChunks = (PdfChunk[]) bidiLine.detailChunks.clone();
        this.totalTextLength = bidiLine.totalTextLength;
        this.orderLevels = (byte[]) bidiLine.orderLevels.clone();
        this.indexChars = (int[]) bidiLine.indexChars.clone();
        this.chunks = new ArrayList<>(bidiLine.chunks);
        this.indexChunk = bidiLine.indexChunk;
        this.indexChunkChar = bidiLine.indexChunkChar;
        this.currentChar = bidiLine.currentChar;
        this.storedRunDirection = bidiLine.storedRunDirection;
        this.storedText = (char[]) bidiLine.storedText.clone();
        this.storedDetailChunks = (PdfChunk[]) bidiLine.storedDetailChunks.clone();
        this.storedTotalTextLength = bidiLine.storedTotalTextLength;
        this.storedOrderLevels = (byte[]) bidiLine.storedOrderLevels.clone();
        this.storedIndexChars = (int[]) bidiLine.storedIndexChars.clone();
        this.storedIndexChunk = bidiLine.storedIndexChunk;
        this.storedIndexChunkChar = bidiLine.storedIndexChunkChar;
        this.storedCurrentChar = bidiLine.storedCurrentChar;
        this.shortStore = bidiLine.shortStore;
        this.arabicOptions = bidiLine.arabicOptions;
    }

    public boolean isEmpty() {
        return this.currentChar >= this.totalTextLength && this.indexChunk >= this.chunks.size();
    }

    public void clearChunks() {
        this.chunks.clear();
        this.totalTextLength = 0;
        this.currentChar = 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0061, code lost:
        r1 = r11.indexChunkChar + 1;
        r11.indexChunkChar = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0066, code lost:
        if (r1 < r6) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0068, code lost:
        r11.indexChunkChar = 0;
        r11.indexChunk++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0071, code lost:
        if (r11.totalTextLength != 0) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0073, code lost:
        r11.detailChunks[0] = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0077, code lost:
        r1 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean getParagraph(int r12) {
        /*
            r11 = this;
            r11.runDirection = r12
            r0 = 0
            r11.currentChar = r0
            r11.totalTextLength = r0
            r1 = 0
        L_0x0008:
            int r2 = r11.indexChunk
            java.util.ArrayList<com.itextpdf.text.pdf.PdfChunk> r3 = r11.chunks
            int r3 = r3.size()
            r4 = 1
            if (r2 >= r3) goto L_0x0083
            java.util.ArrayList<com.itextpdf.text.pdf.PdfChunk> r2 = r11.chunks
            int r3 = r11.indexChunk
            java.lang.Object r2 = r2.get(r3)
            com.itextpdf.text.pdf.PdfChunk r2 = (com.itextpdf.text.pdf.PdfChunk) r2
            com.itextpdf.text.pdf.PdfFont r3 = r2.font()
            com.itextpdf.text.pdf.BaseFont r3 = r3.getFont()
            java.lang.String r5 = r2.toString()
            int r6 = r5.length()
        L_0x002d:
            int r7 = r11.indexChunkChar
            if (r7 >= r6) goto L_0x0078
            char r7 = r5.charAt(r7)
            int r8 = r3.getUnicodeEquivalent(r7)
            char r8 = (char) r8
            r9 = 10
            r10 = 13
            if (r8 == r10) goto L_0x004c
            if (r8 != r9) goto L_0x0043
            goto L_0x004c
        L_0x0043:
            r11.addPiece(r7, r2)
            int r7 = r11.indexChunkChar
            int r7 = r7 + r4
            r11.indexChunkChar = r7
            goto L_0x002d
        L_0x004c:
            if (r8 != r10) goto L_0x0061
            int r1 = r11.indexChunkChar
            int r3 = r1 + 1
            if (r3 >= r6) goto L_0x0061
            int r1 = r1 + 1
            char r1 = r5.charAt(r1)
            if (r1 != r9) goto L_0x0061
            int r1 = r11.indexChunkChar
            int r1 = r1 + r4
            r11.indexChunkChar = r1
        L_0x0061:
            int r1 = r11.indexChunkChar
            int r1 = r1 + r4
            r11.indexChunkChar = r1
            if (r1 < r6) goto L_0x006f
            r11.indexChunkChar = r0
            int r1 = r11.indexChunk
            int r1 = r1 + r4
            r11.indexChunk = r1
        L_0x006f:
            int r1 = r11.totalTextLength
            if (r1 != 0) goto L_0x0077
            com.itextpdf.text.pdf.PdfChunk[] r1 = r11.detailChunks
            r1[r0] = r2
        L_0x0077:
            r1 = 1
        L_0x0078:
            if (r1 == 0) goto L_0x007b
            goto L_0x0083
        L_0x007b:
            r11.indexChunkChar = r0
            int r2 = r11.indexChunk
            int r2 = r2 + r4
            r11.indexChunk = r2
            goto L_0x0008
        L_0x0083:
            int r2 = r11.totalTextLength
            if (r2 != 0) goto L_0x0088
            return r1
        L_0x0088:
            int r2 = r2 - r4
            int r1 = r11.trimRight(r0, r2)
            int r1 = r1 + r4
            r11.totalTextLength = r1
            if (r1 != 0) goto L_0x0093
            return r4
        L_0x0093:
            r2 = 2
            r3 = 3
            if (r12 == r2) goto L_0x0099
            if (r12 != r3) goto L_0x00da
        L_0x0099:
            byte[] r2 = r11.orderLevels
            int r2 = r2.length
            if (r2 >= r1) goto L_0x00a8
            int r2 = r11.pieceSize
            byte[] r5 = new byte[r2]
            r11.orderLevels = r5
            int[] r2 = new int[r2]
            r11.indexChars = r2
        L_0x00a8:
            char[] r2 = r11.text
            int r5 = r11.arabicOptions
            com.itextpdf.text.pdf.languages.ArabicLigaturizer.processNumbers(r2, r0, r1, r5)
            com.itextpdf.text.pdf.BidiOrder r1 = new com.itextpdf.text.pdf.BidiOrder
            char[] r2 = r11.text
            int r5 = r11.totalTextLength
            if (r12 != r3) goto L_0x00b9
            r12 = 1
            goto L_0x00ba
        L_0x00b9:
            r12 = 0
        L_0x00ba:
            byte r12 = (byte) r12
            r1.<init>(r2, r0, r5, r12)
            byte[] r12 = r1.getLevels()
            r1 = 0
        L_0x00c3:
            int r2 = r11.totalTextLength
            if (r1 >= r2) goto L_0x00d4
            byte[] r2 = r11.orderLevels
            byte r3 = r12[r1]
            r2[r1] = r3
            int[] r2 = r11.indexChars
            r2[r1] = r1
            int r1 = r1 + 1
            goto L_0x00c3
        L_0x00d4:
            r11.doArabicShapping()
            r11.mirrorGlyphs()
        L_0x00da:
            int r12 = r11.totalTextLength
            int r12 = r12 - r4
            int r12 = r11.trimRightEx(r0, r12)
            int r12 = r12 + r4
            r11.totalTextLength = r12
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.BidiLine.getParagraph(int):boolean");
    }

    public void addChunk(PdfChunk pdfChunk) {
        this.chunks.add(pdfChunk);
    }

    public void addChunks(ArrayList<PdfChunk> arrayList) {
        this.chunks.addAll(arrayList);
    }

    public void addPiece(char c, PdfChunk pdfChunk) {
        int i = this.totalTextLength;
        int i2 = this.pieceSize;
        if (i >= i2) {
            char[] cArr = this.text;
            PdfChunk[] pdfChunkArr = this.detailChunks;
            int i3 = i2 * 2;
            this.pieceSize = i3;
            char[] cArr2 = new char[i3];
            this.text = cArr2;
            this.detailChunks = new PdfChunk[i3];
            System.arraycopy(cArr, 0, cArr2, 0, i);
            System.arraycopy(pdfChunkArr, 0, this.detailChunks, 0, this.totalTextLength);
        }
        char[] cArr3 = this.text;
        int i4 = this.totalTextLength;
        cArr3[i4] = c;
        PdfChunk[] pdfChunkArr2 = this.detailChunks;
        this.totalTextLength = i4 + 1;
        pdfChunkArr2[i4] = pdfChunk;
    }

    public void save() {
        int i = this.indexChunk;
        boolean z = true;
        if (i > 0) {
            if (i < this.chunks.size()) {
                while (true) {
                    this.indexChunk--;
                    int i2 = this.indexChunk;
                    if (i2 < 0) {
                        break;
                    }
                    this.chunks.remove(i2);
                }
            } else {
                this.chunks.clear();
            }
            this.indexChunk = 0;
        }
        this.storedRunDirection = this.runDirection;
        int i3 = this.totalTextLength;
        this.storedTotalTextLength = i3;
        this.storedIndexChunk = this.indexChunk;
        this.storedIndexChunkChar = this.indexChunkChar;
        int i4 = this.currentChar;
        this.storedCurrentChar = i4;
        if (i4 >= i3) {
            z = false;
        }
        this.shortStore = z;
        if (!z) {
            if (this.storedText.length < i3) {
                this.storedText = new char[i3];
                this.storedDetailChunks = new PdfChunk[i3];
            }
            System.arraycopy(this.text, 0, this.storedText, 0, i3);
            System.arraycopy(this.detailChunks, 0, this.storedDetailChunks, 0, this.totalTextLength);
        }
        int i5 = this.runDirection;
        if (i5 == 2 || i5 == 3) {
            int length = this.storedOrderLevels.length;
            int i6 = this.totalTextLength;
            if (length < i6) {
                this.storedOrderLevels = new byte[i6];
                this.storedIndexChars = new int[i6];
            }
            byte[] bArr = this.orderLevels;
            int i7 = this.currentChar;
            System.arraycopy(bArr, i7, this.storedOrderLevels, i7, i6 - i7);
            int[] iArr = this.indexChars;
            int i8 = this.currentChar;
            System.arraycopy(iArr, i8, this.storedIndexChars, i8, this.totalTextLength - i8);
        }
    }

    public void restore() {
        this.runDirection = this.storedRunDirection;
        int i = this.storedTotalTextLength;
        this.totalTextLength = i;
        this.indexChunk = this.storedIndexChunk;
        this.indexChunkChar = this.storedIndexChunkChar;
        this.currentChar = this.storedCurrentChar;
        if (!this.shortStore) {
            System.arraycopy(this.storedText, 0, this.text, 0, i);
            System.arraycopy(this.storedDetailChunks, 0, this.detailChunks, 0, this.totalTextLength);
        }
        int i2 = this.runDirection;
        if (i2 == 2 || i2 == 3) {
            byte[] bArr = this.storedOrderLevels;
            int i3 = this.currentChar;
            System.arraycopy(bArr, i3, this.orderLevels, i3, this.totalTextLength - i3);
            int[] iArr = this.storedIndexChars;
            int i4 = this.currentChar;
            System.arraycopy(iArr, i4, this.indexChars, i4, this.totalTextLength - i4);
        }
    }

    public void mirrorGlyphs() {
        int i;
        for (int i2 = 0; i2 < this.totalTextLength; i2++) {
            if ((this.orderLevels[i2] & 1) == 1 && (i = mirrorChars.get(this.text[i2])) != 0) {
                this.text[i2] = (char) i;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000b, code lost:
        r4 = r11.text;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doArabicShapping() {
        /*
            r11 = this;
            r0 = 0
            r8 = 0
            r9 = 0
        L_0x0003:
            int r1 = r11.totalTextLength
            r2 = 1791(0x6ff, float:2.51E-42)
            r3 = 1536(0x600, float:2.152E-42)
            if (r8 >= r1) goto L_0x002b
            char[] r4 = r11.text
            char r5 = r4[r8]
            if (r5 < r3) goto L_0x0014
            if (r5 > r2) goto L_0x0014
            goto L_0x002b
        L_0x0014:
            if (r8 == r9) goto L_0x0026
            char r1 = r4[r8]
            r4[r9] = r1
            com.itextpdf.text.pdf.PdfChunk[] r1 = r11.detailChunks
            r2 = r1[r8]
            r1[r9] = r2
            byte[] r1 = r11.orderLevels
            byte r2 = r1[r8]
            r1[r9] = r2
        L_0x0026:
            int r8 = r8 + 1
            int r9 = r9 + 1
            goto L_0x0003
        L_0x002b:
            if (r8 < r1) goto L_0x0030
            r11.totalTextLength = r9
            return
        L_0x0030:
            int r1 = r8 + 1
            r10 = r1
        L_0x0033:
            int r1 = r11.totalTextLength
            if (r10 >= r1) goto L_0x0043
            char[] r1 = r11.text
            char r1 = r1[r10]
            if (r1 < r3) goto L_0x0043
            if (r1 <= r2) goto L_0x0040
            goto L_0x0043
        L_0x0040:
            int r10 = r10 + 1
            goto L_0x0033
        L_0x0043:
            int r6 = r10 - r8
            char[] r4 = r11.text
            int r7 = r11.arabicOptions
            r1 = r4
            r2 = r8
            r3 = r6
            r5 = r9
            int r1 = com.itextpdf.text.pdf.languages.ArabicLigaturizer.arabic_shape(r1, r2, r3, r4, r5, r6, r7)
            if (r8 == r9) goto L_0x006b
            r2 = 0
        L_0x0054:
            if (r2 >= r1) goto L_0x006c
            com.itextpdf.text.pdf.PdfChunk[] r3 = r11.detailChunks
            r4 = r3[r8]
            r3[r9] = r4
            byte[] r3 = r11.orderLevels
            int r4 = r9 + 1
            int r5 = r8 + 1
            byte r6 = r3[r8]
            r3[r9] = r6
            int r2 = r2 + 1
            r9 = r4
            r8 = r5
            goto L_0x0054
        L_0x006b:
            int r9 = r9 + r1
        L_0x006c:
            r8 = r10
            goto L_0x0003
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.BidiLine.doArabicShapping():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:109:0x0276  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x027d  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0127  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x01da  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.itextpdf.text.pdf.PdfLine processLine(float r24, float r25, int r26, int r27, int r28, float r29, float r30, float r31) {
        /*
            r23 = this;
            r0 = r23
            r4 = r25
            r1 = r27
            r2 = 0
            r0.isWordSplit = r2
            r3 = r28
            r0.arabicOptions = r3
            r23.save()
            r3 = 1
            r5 = 3
            if (r1 != r5) goto L_0x0016
            r8 = 1
            goto L_0x0017
        L_0x0016:
            r8 = 0
        L_0x0017:
            int r5 = r0.currentChar
            int r6 = r0.totalTextLength
            r7 = 0
            if (r5 < r6) goto L_0x004a
            boolean r1 = r0.getParagraph(r1)
            if (r1 != 0) goto L_0x0025
            return r7
        L_0x0025:
            int r1 = r0.totalTextLength
            if (r1 != 0) goto L_0x004a
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            com.itextpdf.text.pdf.PdfChunk r1 = new com.itextpdf.text.pdf.PdfChunk
            com.itextpdf.text.pdf.PdfChunk[] r3 = r0.detailChunks
            r2 = r3[r2]
            java.lang.String r3 = ""
            r1.<init>((java.lang.String) r3, (com.itextpdf.text.pdf.PdfChunk) r2)
            r7.add(r1)
            com.itextpdf.text.pdf.PdfLine r9 = new com.itextpdf.text.pdf.PdfLine
            r2 = 0
            r3 = 0
            r6 = 1
            r1 = r9
            r4 = r25
            r5 = r26
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            return r9
        L_0x004a:
            int r1 = r0.currentChar
            if (r1 == 0) goto L_0x0057
            int r5 = r0.totalTextLength
            int r5 = r5 - r3
            int r1 = r0.trimLeftEx(r1, r5)
            r0.currentChar = r1
        L_0x0057:
            int r1 = r0.currentChar
            r15 = r4
            r13 = r7
            r14 = r13
            r9 = 0
            r11 = 2143289344(0x7fc00000, float:NaN)
            r12 = 2143289344(0x7fc00000, float:NaN)
            r16 = -1
        L_0x0063:
            int r10 = r0.currentChar
            int r6 = r0.totalTextLength
            r17 = 0
            if (r10 >= r6) goto L_0x028d
            com.itextpdf.text.pdf.PdfChunk[] r6 = r0.detailChunks
            r6 = r6[r10]
            boolean r9 = r6.isImage()
            if (r9 == 0) goto L_0x00b5
            int r9 = (r29 > r30 ? 1 : (r29 == r30 ? 0 : -1))
            if (r9 >= 0) goto L_0x00b5
            com.itextpdf.text.Image r9 = r6.getImage()
            boolean r10 = r9.isScaleToFitHeight()
            if (r10 == 0) goto L_0x00b5
            r10 = 1073741824(0x40000000, float:2.0)
            float r10 = r10 * r31
            float r10 = r30 + r10
            float r18 = r9.getScaledHeight()
            float r18 = r10 - r18
            float r19 = r6.getImageOffsetY()
            float r18 = r18 - r19
            float r19 = r9.getSpacingBefore()
            float r18 = r18 - r19
            int r18 = (r18 > r29 ? 1 : (r18 == r29 ? 0 : -1))
            if (r18 >= 0) goto L_0x00b5
            float r18 = r6.getImageOffsetY()
            float r10 = r10 - r18
            float r18 = r9.getSpacingBefore()
            float r10 = r10 - r18
            float r10 = r10 - r29
            float r9 = r9.getScaledHeight()
            float r10 = r10 / r9
            r6.setImageScalePercentage(r10)
        L_0x00b5:
            char[] r9 = r0.text
            int r10 = r0.currentChar
            boolean r18 = com.itextpdf.text.Utilities.isSurrogatePair((char[]) r9, (int) r10)
            if (r18 == 0) goto L_0x00cc
            char[] r9 = r0.text
            int r10 = r0.currentChar
            int r9 = com.itextpdf.text.Utilities.convertToUtf32((char[]) r9, (int) r10)
            int r9 = r6.getUnicodeEquivalent(r9)
            goto L_0x00d6
        L_0x00cc:
            char[] r9 = r0.text
            int r10 = r0.currentChar
            char r9 = r9[r10]
            int r9 = r6.getUnicodeEquivalent(r9)
        L_0x00d6:
            r10 = r9
            boolean r9 = com.itextpdf.text.pdf.PdfChunk.noPrint(r10)
            if (r9 == 0) goto L_0x00e1
            r22 = r1
            goto L_0x0280
        L_0x00e1:
            if (r18 == 0) goto L_0x00e8
            float r9 = r6.getCharWidth(r10)
            goto L_0x00fd
        L_0x00e8:
            boolean r9 = r6.isImage()
            if (r9 == 0) goto L_0x00f3
            float r9 = r6.getImageWidth()
            goto L_0x00fd
        L_0x00f3:
            char[] r9 = r0.text
            int r7 = r0.currentChar
            char r7 = r9[r7]
            float r9 = r6.getCharWidth(r7)
        L_0x00fd:
            float r7 = r15 - r9
            int r7 = (r7 > r17 ? 1 : (r7 == r17 ? 0 : -1))
            if (r7 >= 0) goto L_0x0120
            if (r14 != 0) goto L_0x0120
            boolean r7 = r6.isImage()
            if (r7 == 0) goto L_0x0120
            com.itextpdf.text.Image r7 = r6.getImage()
            boolean r19 = r7.isScaleToFitLineWhenOverflow()
            if (r19 == 0) goto L_0x0120
            float r7 = r7.getWidth()
            float r7 = r15 / r7
            r6.setImageScalePercentage(r7)
            r7 = r15
            goto L_0x0121
        L_0x0120:
            r7 = r9
        L_0x0121:
            boolean r9 = r6.isTab()
            if (r9 == 0) goto L_0x01da
            java.lang.String r7 = "TABSETTINGS"
            boolean r7 = r6.isAttribute(r7)
            if (r7 == 0) goto L_0x018e
            int r7 = r0.currentChar
            if (r13 == 0) goto L_0x0147
            float r9 = r4 - r15
            float r10 = r13.getPosition(r11, r9, r12)
            float r9 = r9 - r11
            float r9 = r9 + r10
            float r9 = r4 - r9
            int r15 = (r9 > r17 ? 1 : (r9 == r17 ? 0 : -1))
            if (r15 >= 0) goto L_0x0143
            float r10 = r10 + r9
            r9 = 0
        L_0x0143:
            r13.setPosition(r10)
            r15 = r9
        L_0x0147:
            float r9 = r4 - r15
            com.itextpdf.text.TabStop r10 = com.itextpdf.text.pdf.PdfChunk.getTabStop(r6, r9)
            float r13 = r10.getPosition()
            int r13 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r13 <= 0) goto L_0x0161
            r22 = r1
            r5 = r7
            r1 = r11
            r20 = r12
            r2 = r14
            r9 = r18
            r7 = 0
            goto L_0x0297
        L_0x0161:
            r6.setTabStop(r10)
            if (r8 != 0) goto L_0x0182
            com.itextpdf.text.TabStop$Alignment r11 = r10.getAlignment()
            com.itextpdf.text.TabStop$Alignment r12 = com.itextpdf.text.TabStop.Alignment.LEFT
            if (r11 != r12) goto L_0x0182
            float r9 = r10.getPosition()
            float r9 = r4 - r9
            r22 = r1
            r21 = r6
            r16 = r7
            r15 = r9
            r11 = 2143289344(0x7fc00000, float:NaN)
            r12 = 2143289344(0x7fc00000, float:NaN)
            r13 = 0
            goto L_0x0274
        L_0x0182:
            r22 = r1
            r21 = r6
            r16 = r7
            r11 = r9
            r13 = r10
            r12 = 2143289344(0x7fc00000, float:NaN)
            goto L_0x0274
        L_0x018e:
            java.lang.String r7 = "TAB"
            java.lang.Object r7 = r6.getAttribute(r7)
            java.lang.Object[] r7 = (java.lang.Object[]) r7
            java.lang.Object[] r7 = (java.lang.Object[]) r7
            r9 = r7[r3]
            java.lang.Float r9 = (java.lang.Float) r9
            float r9 = r9.floatValue()
            r10 = 2
            r7 = r7[r10]
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L_0x01c6
            float r7 = r4 - r15
            int r7 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
            if (r7 >= 0) goto L_0x01c6
            com.itextpdf.text.pdf.PdfLine r9 = new com.itextpdf.text.pdf.PdfLine
            r2 = 0
            r6 = 1
            int r5 = r0.currentChar
            int r5 = r5 - r3
            java.util.ArrayList r7 = r0.createArrayOfPdfChunks(r1, r5)
            r1 = r9
            r3 = r25
            r4 = r15
            r5 = r26
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            return r9
        L_0x01c6:
            com.itextpdf.text.pdf.PdfChunk[] r7 = r0.detailChunks
            int r10 = r0.currentChar
            r7 = r7[r10]
            r10 = r24
            r7.adjustLeft(r10)
            float r7 = r4 - r9
            r22 = r1
            r21 = r6
        L_0x01d7:
            r15 = r7
            goto L_0x0274
        L_0x01da:
            boolean r9 = r6.isSeparator()
            if (r9 == 0) goto L_0x0213
            java.lang.String r7 = "SEPARATOR"
            java.lang.Object r7 = r6.getAttribute(r7)
            java.lang.Object[] r7 = (java.lang.Object[]) r7
            java.lang.Object[] r7 = (java.lang.Object[]) r7
            r9 = r7[r2]
            com.itextpdf.text.pdf.draw.DrawInterface r9 = (com.itextpdf.text.pdf.draw.DrawInterface) r9
            r7 = r7[r3]
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L_0x020d
            boolean r7 = r9 instanceof com.itextpdf.text.pdf.draw.LineSeparator
            if (r7 == 0) goto L_0x020d
            com.itextpdf.text.pdf.draw.LineSeparator r9 = (com.itextpdf.text.pdf.draw.LineSeparator) r9
            float r7 = r9.getPercentage()
            float r7 = r7 * r4
            r9 = 1120403456(0x42c80000, float:100.0)
            float r7 = r7 / r9
            float r15 = r15 - r7
            int r7 = (r15 > r17 ? 1 : (r15 == r17 ? 0 : -1))
            if (r7 >= 0) goto L_0x020d
            r15 = 0
        L_0x020d:
            r22 = r1
            r21 = r6
            goto L_0x0274
        L_0x0213:
            int r9 = r0.currentChar
            int r5 = r0.totalTextLength
            char[] r2 = r0.text
            com.itextpdf.text.pdf.PdfChunk[] r3 = r0.detailChunks
            r20 = r9
            r9 = r6
            r21 = r6
            r6 = r10
            r10 = r1
            r22 = r1
            r1 = r11
            r11 = r20
            r20 = r12
            r12 = r5
            r5 = r13
            r13 = r2
            r2 = r14
            r14 = r3
            boolean r3 = r9.isExtSplitCharacter(r10, r11, r12, r13, r14)
            if (r3 == 0) goto L_0x023f
            char r9 = (char) r6
            boolean r9 = java.lang.Character.isWhitespace(r9)
            if (r9 == 0) goto L_0x023f
            int r9 = r0.currentChar
            r16 = r9
        L_0x023f:
            float r7 = r15 - r7
            int r9 = (r7 > r17 ? 1 : (r7 == r17 ? 0 : -1))
            if (r9 >= 0) goto L_0x024c
            r7 = r5
            r5 = r16
            r9 = r18
            r3 = 1
            goto L_0x0297
        L_0x024c:
            if (r5 == 0) goto L_0x0266
            com.itextpdf.text.TabStop$Alignment r2 = r5.getAlignment()
            com.itextpdf.text.TabStop$Alignment r9 = com.itextpdf.text.TabStop.Alignment.ANCHOR
            if (r2 != r9) goto L_0x0266
            boolean r2 = java.lang.Float.isNaN(r20)
            if (r2 == 0) goto L_0x0266
            char r2 = r5.getAnchorChar()
            char r6 = (char) r6
            if (r2 != r6) goto L_0x0266
            float r12 = r4 - r15
            goto L_0x0268
        L_0x0266:
            r12 = r20
        L_0x0268:
            if (r3 == 0) goto L_0x0270
            int r2 = r0.currentChar
            r11 = r1
            r16 = r2
            goto L_0x0271
        L_0x0270:
            r11 = r1
        L_0x0271:
            r13 = r5
            goto L_0x01d7
        L_0x0274:
            if (r18 == 0) goto L_0x027d
            int r1 = r0.currentChar
            r3 = 1
            int r1 = r1 + r3
            r0.currentChar = r1
            goto L_0x027e
        L_0x027d:
            r3 = 1
        L_0x027e:
            r14 = r21
        L_0x0280:
            int r1 = r0.currentChar
            int r1 = r1 + r3
            r0.currentChar = r1
            r9 = r18
            r1 = r22
            r2 = 0
            r7 = 0
            goto L_0x0063
        L_0x028d:
            r22 = r1
            r1 = r11
            r20 = r12
            r5 = r13
            r2 = r14
            r7 = r5
            r5 = r16
        L_0x0297:
            if (r2 != 0) goto L_0x02bb
            int r1 = r0.currentChar
            int r1 = r1 + r3
            r0.currentChar = r1
            if (r9 == 0) goto L_0x02a3
            int r1 = r1 + r3
            r0.currentChar = r1
        L_0x02a3:
            com.itextpdf.text.pdf.PdfLine r9 = new com.itextpdf.text.pdf.PdfLine
            r2 = 0
            r5 = 0
            r6 = 0
            int r1 = r0.currentChar
            int r7 = r1 + -1
            int r1 = r1 - r3
            java.util.ArrayList r7 = r0.createArrayOfPdfChunks(r7, r1)
            r1 = r9
            r3 = r25
            r4 = r5
            r5 = r26
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            return r9
        L_0x02bb:
            if (r7 == 0) goto L_0x02de
            float r3 = r4 - r15
            r12 = r20
            float r3 = r7.getPosition(r1, r3, r12)
            float r6 = r3 - r1
            float r15 = r15 - r6
            int r6 = (r15 > r17 ? 1 : (r15 == r17 ? 0 : -1))
            if (r6 >= 0) goto L_0x02ce
            float r3 = r3 + r15
            goto L_0x02d0
        L_0x02ce:
            r17 = r15
        L_0x02d0:
            if (r8 != 0) goto L_0x02d6
            r7.setPosition(r3)
            goto L_0x02dc
        L_0x02d6:
            float r3 = r4 - r17
            float r3 = r3 - r1
            r7.setPosition(r3)
        L_0x02dc:
            r15 = r17
        L_0x02de:
            int r1 = r0.currentChar
            int r3 = r0.totalTextLength
            if (r1 < r3) goto L_0x02fa
            com.itextpdf.text.pdf.PdfLine r9 = new com.itextpdf.text.pdf.PdfLine
            r2 = 0
            r6 = 1
            r1 = 1
            int r3 = r3 - r1
            r7 = r22
            java.util.ArrayList r7 = r0.createArrayOfPdfChunks(r7, r3)
            r1 = r9
            r3 = r25
            r4 = r15
            r5 = r26
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            return r9
        L_0x02fa:
            r7 = r22
            r3 = 1
            int r1 = r1 - r3
            int r1 = r0.trimRightEx(r7, r1)
            if (r1 >= r7) goto L_0x0319
            com.itextpdf.text.pdf.PdfLine r9 = new com.itextpdf.text.pdf.PdfLine
            r2 = 0
            r6 = 0
            int r1 = r0.currentChar
            int r1 = r1 - r3
            java.util.ArrayList r7 = r0.createArrayOfPdfChunks(r7, r1)
            r1 = r9
            r3 = r25
            r4 = r15
            r5 = r26
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            return r9
        L_0x0319:
            int r3 = r0.currentChar
            r6 = 1
            int r3 = r3 - r6
            if (r1 != r3) goto L_0x0394
            java.lang.String r3 = "HYPHENATION"
            java.lang.Object r3 = r2.getAttribute(r3)
            com.itextpdf.text.pdf.HyphenationEvent r3 = (com.itextpdf.text.pdf.HyphenationEvent) r3
            if (r3 == 0) goto L_0x0394
            int[] r9 = r0.getWord(r7, r1)
            if (r9 == 0) goto L_0x0394
            r10 = 0
            r11 = r9[r10]
            int r12 = r0.currentChar
            int r12 = r12 - r6
            float r11 = r0.getWidth(r11, r12)
            float r11 = r11 + r15
            java.lang.String r12 = new java.lang.String
            char[] r13 = r0.text
            r14 = r9[r10]
            r16 = r9[r6]
            r6 = r9[r10]
            int r6 = r16 - r6
            r12.<init>(r13, r14, r6)
            com.itextpdf.text.pdf.PdfFont r6 = r2.font()
            com.itextpdf.text.pdf.BaseFont r6 = r6.getFont()
            com.itextpdf.text.pdf.PdfFont r10 = r2.font()
            float r10 = r10.size()
            java.lang.String r6 = r3.getHyphenatedWordPre(r12, r6, r10, r11)
            java.lang.String r3 = r3.getHyphenatedWordPost()
            int r10 = r6.length()
            if (r10 <= 0) goto L_0x0394
            com.itextpdf.text.pdf.PdfChunk r1 = new com.itextpdf.text.pdf.PdfChunk
            r1.<init>((java.lang.String) r6, (com.itextpdf.text.pdf.PdfChunk) r2)
            r5 = 1
            r10 = r9[r5]
            int r3 = r3.length()
            int r10 = r10 - r3
            r0.currentChar = r10
            com.itextpdf.text.pdf.PdfLine r10 = new com.itextpdf.text.pdf.PdfLine
            r3 = 0
            float r2 = r2.width(r6)
            float r6 = r11 - r2
            r11 = 0
            r2 = 0
            r2 = r9[r2]
            int r2 = r2 - r5
            java.util.ArrayList r7 = r0.createArrayOfPdfChunks(r7, r2, r1)
            r1 = r10
            r2 = r3
            r3 = r25
            r4 = r6
            r5 = r26
            r6 = r11
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            return r10
        L_0x0394:
            r2 = -1
            if (r5 != r2) goto L_0x039a
            r3 = 1
            r0.isWordSplit = r3
        L_0x039a:
            if (r5 == r2) goto L_0x03c5
            if (r5 < r1) goto L_0x039f
            goto L_0x03c5
        L_0x039f:
            int r1 = r5 + 1
            r0.currentChar = r1
            int r1 = r0.trimRightEx(r7, r5)
            if (r1 >= r7) goto L_0x03ad
            int r1 = r0.currentChar
            r2 = 1
            int r1 = r1 - r2
        L_0x03ad:
            com.itextpdf.text.pdf.PdfLine r9 = new com.itextpdf.text.pdf.PdfLine
            r2 = 0
            float r3 = r0.getWidth(r7, r1, r4)
            float r5 = r4 - r3
            r6 = 0
            java.util.ArrayList r7 = r0.createArrayOfPdfChunks(r7, r1)
            r1 = r9
            r3 = r25
            r4 = r5
            r5 = r26
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            return r9
        L_0x03c5:
            com.itextpdf.text.pdf.PdfLine r9 = new com.itextpdf.text.pdf.PdfLine
            r2 = 0
            int r3 = r1 + 1
            int r5 = r0.currentChar
            r6 = 1
            int r5 = r5 - r6
            float r3 = r0.getWidth(r3, r5, r4)
            float r5 = r15 + r3
            r6 = 0
            java.util.ArrayList r7 = r0.createArrayOfPdfChunks(r7, r1)
            r1 = r9
            r3 = r25
            r4 = r5
            r5 = r26
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.BidiLine.processLine(float, float, int, int, int, float, float, float):com.itextpdf.text.pdf.PdfLine");
    }

    public boolean isWordSplit() {
        return this.isWordSplit;
    }

    public float getWidth(int i, int i2) {
        return getWidth(i, i2, 0.0f);
    }

    public float getWidth(int i, int i2, float f) {
        float f2 = 0.0f;
        TabStop tabStop = null;
        float f3 = Float.NaN;
        float f4 = Float.NaN;
        while (i <= i2) {
            boolean isSurrogatePair = Utilities.isSurrogatePair(this.text, i);
            if (this.detailChunks[i].isTab() && this.detailChunks[i].isAttribute(Chunk.TABSETTINGS)) {
                if (tabStop != null) {
                    float position = tabStop.getPosition(f3, f2, f4);
                    f2 = (f2 - f3) + position;
                    tabStop.setPosition(position);
                }
                TabStop tabStop2 = this.detailChunks[i].getTabStop();
                if (tabStop2 == null) {
                    tabStop = PdfChunk.getTabStop(this.detailChunks[i], f2);
                    f3 = f2;
                } else {
                    if (this.runDirection == 3) {
                        f2 = f - tabStop2.getPosition();
                    } else {
                        f2 = tabStop2.getPosition();
                    }
                    tabStop = null;
                    f3 = Float.NaN;
                }
                f4 = Float.NaN;
            } else if (isSurrogatePair) {
                f2 += this.detailChunks[i].getCharWidth(Utilities.convertToUtf32(this.text, i));
                i++;
            } else {
                char c = this.text[i];
                PdfChunk pdfChunk = this.detailChunks[i];
                if (!PdfChunk.noPrint(pdfChunk.getUnicodeEquivalent(c))) {
                    if (tabStop != null && tabStop.getAlignment() != TabStop.Alignment.ANCHOR && Float.isNaN(f4) && tabStop.getAnchorChar() == ((char) pdfChunk.getUnicodeEquivalent(c))) {
                        f4 = f2;
                    }
                    f2 += this.detailChunks[i].getCharWidth(c);
                }
            }
            i++;
        }
        if (tabStop == null) {
            return f2;
        }
        float position2 = tabStop.getPosition(f3, f2, f4);
        float f5 = (f2 - f3) + position2;
        tabStop.setPosition(position2);
        return f5;
    }

    public ArrayList<PdfChunk> createArrayOfPdfChunks(int i, int i2) {
        return createArrayOfPdfChunks(i, i2, (PdfChunk) null);
    }

    public ArrayList<PdfChunk> createArrayOfPdfChunks(int i, int i2, PdfChunk pdfChunk) {
        int i3 = this.runDirection;
        boolean z = i3 == 2 || i3 == 3;
        if (z) {
            reorder(i, i2);
        }
        ArrayList<PdfChunk> arrayList = new ArrayList<>();
        PdfChunk pdfChunk2 = this.detailChunks[i];
        StringBuffer stringBuffer = new StringBuffer();
        while (i <= i2) {
            int i4 = z ? this.indexChars[i] : i;
            char c = this.text[i4];
            PdfChunk pdfChunk3 = this.detailChunks[i4];
            if (!PdfChunk.noPrint(pdfChunk3.getUnicodeEquivalent(c))) {
                if (pdfChunk3.isImage() || pdfChunk3.isSeparator() || pdfChunk3.isTab()) {
                    if (stringBuffer.length() > 0) {
                        arrayList.add(new PdfChunk(stringBuffer.toString(), pdfChunk2));
                        stringBuffer = new StringBuffer();
                    }
                    arrayList.add(pdfChunk3);
                } else if (pdfChunk3 == pdfChunk2) {
                    stringBuffer.append(c);
                } else {
                    if (stringBuffer.length() > 0) {
                        arrayList.add(new PdfChunk(stringBuffer.toString(), pdfChunk2));
                        stringBuffer = new StringBuffer();
                    }
                    if (!pdfChunk3.isImage() && !pdfChunk3.isSeparator() && !pdfChunk3.isTab()) {
                        stringBuffer.append(c);
                    }
                    pdfChunk2 = pdfChunk3;
                }
            }
            i++;
        }
        if (stringBuffer.length() > 0) {
            arrayList.add(new PdfChunk(stringBuffer.toString(), pdfChunk2));
        }
        if (pdfChunk != null) {
            arrayList.add(pdfChunk);
        }
        return arrayList;
    }

    public int[] getWord(int i, int i2) {
        int i3 = i2;
        while (i3 < this.totalTextLength && (Character.isLetter(this.text[i3]) || Character.isDigit(this.text[i3]))) {
            i3++;
        }
        if (i3 == i2) {
            return null;
        }
        while (i2 >= i && (Character.isLetter(this.text[i2]) || Character.isDigit(this.text[i2]))) {
            i2--;
        }
        return new int[]{i2 + 1, i3};
    }

    public int trimRight(int i, int i2) {
        while (i2 >= i && isWS((char) this.detailChunks[i2].getUnicodeEquivalent(this.text[i2]))) {
            i2--;
        }
        return i2;
    }

    public int trimLeft(int i, int i2) {
        while (i <= i2 && isWS((char) this.detailChunks[i].getUnicodeEquivalent(this.text[i]))) {
            i++;
        }
        return i;
    }

    public int trimRightEx(int i, int i2) {
        while (i2 >= i) {
            char unicodeEquivalent = (char) this.detailChunks[i2].getUnicodeEquivalent(this.text[i2]);
            if (!isWS(unicodeEquivalent) && !PdfChunk.noPrint(unicodeEquivalent) && (!this.detailChunks[i2].isTab() || !this.detailChunks[i2].isAttribute(Chunk.TABSETTINGS) || !((Boolean) ((Object[]) this.detailChunks[i2].getAttribute(Chunk.TAB))[1]).booleanValue())) {
                break;
            }
            i2--;
        }
        return i2;
    }

    public int trimLeftEx(int i, int i2) {
        while (i <= i2) {
            char unicodeEquivalent = (char) this.detailChunks[i].getUnicodeEquivalent(this.text[i]);
            if (!isWS(unicodeEquivalent) && !PdfChunk.noPrint(unicodeEquivalent) && (!this.detailChunks[i].isTab() || !this.detailChunks[i].isAttribute(Chunk.TABSETTINGS) || !((Boolean) ((Object[]) this.detailChunks[i].getAttribute(Chunk.TAB))[1]).booleanValue())) {
                break;
            }
            i++;
        }
        return i;
    }

    public void reorder(int i, int i2) {
        byte b = this.orderLevels[i];
        byte b2 = b;
        byte b3 = b2;
        byte b4 = b3;
        for (int i3 = i + 1; i3 <= i2; i3++) {
            byte b5 = this.orderLevels[i3];
            if (b5 > b4) {
                b4 = b5;
            } else if (b5 < b3) {
                b3 = b5;
            }
            b2 = (byte) (b2 & b5);
            b = (byte) (b | b5);
        }
        if ((b & 1) != 0) {
            if ((b2 & 1) == 1) {
                flip(i, i2 + 1);
                return;
            }
            byte b6 = (byte) (b3 | 1);
            while (b4 >= b6) {
                int i4 = i;
                while (true) {
                    if (i4 <= i2 && this.orderLevels[i4] < b4) {
                        i4++;
                    } else if (i4 > i2) {
                        break;
                    } else {
                        int i5 = i4 + 1;
                        while (i5 <= i2 && this.orderLevels[i5] >= b4) {
                            i5++;
                        }
                        flip(i4, i5);
                        i4 = i5 + 1;
                    }
                }
                b4 = (byte) (b4 - 1);
            }
        }
    }

    public void flip(int i, int i2) {
        int i3 = (i + i2) / 2;
        while (true) {
            i2--;
            if (i < i3) {
                int[] iArr = this.indexChars;
                int i4 = iArr[i];
                iArr[i] = iArr[i2];
                iArr[i2] = i4;
                i++;
            } else {
                return;
            }
        }
    }

    public static String processLTR(String str, int i, int i2) {
        BidiLine bidiLine = new BidiLine();
        bidiLine.addChunk(new PdfChunk(new Chunk(str), (PdfAction) null));
        bidiLine.arabicOptions = i2;
        bidiLine.getParagraph(i);
        ArrayList<PdfChunk> createArrayOfPdfChunks = bidiLine.createArrayOfPdfChunks(0, bidiLine.totalTextLength - 1);
        StringBuilder sb = new StringBuilder();
        Iterator<PdfChunk> it = createArrayOfPdfChunks.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
        }
        return sb.toString();
    }
}
