package com.itextpdf.text.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.error_messages.MessageLocalization;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import kotlin.UByte;

class TrueTypeFontSubSet {
    static final int ARG_1_AND_2_ARE_WORDS = 1;
    static final int HEAD_LOCA_FORMAT_OFFSET = 51;
    static final int MORE_COMPONENTS = 32;
    static final int TABLE_CHECKSUM = 0;
    static final int TABLE_LENGTH = 2;
    static final int TABLE_OFFSET = 1;
    static final int WE_HAVE_AN_X_AND_Y_SCALE = 64;
    static final int WE_HAVE_A_SCALE = 8;
    static final int WE_HAVE_A_TWO_BY_TWO = 128;
    static final int[] entrySelectors = {0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4};
    static final String[] tableNamesCmap = {"cmap", "cvt ", "fpgm", "glyf", "head", "hhea", "hmtx", "loca", "maxp", "prep"};
    static final String[] tableNamesExtra = {"OS/2", "cmap", "cvt ", "fpgm", "glyf", "head", "hhea", "hmtx", "loca", "maxp", "name, prep"};
    static final String[] tableNamesSimple = {"cvt ", "fpgm", "glyf", "head", "hhea", "hmtx", "loca", "maxp", "prep"};
    protected int directoryOffset;
    protected String fileName;
    protected int fontPtr;
    protected int glyfTableRealSize;
    protected ArrayList<Integer> glyphsInList;
    protected HashSet<Integer> glyphsUsed;
    protected boolean includeCmap;
    protected boolean includeExtras;
    protected boolean locaShortTable;
    protected int[] locaTable;
    protected int locaTableRealSize;
    protected byte[] newGlyfTable;
    protected int[] newLocaTable;
    protected byte[] newLocaTableOut;
    protected byte[] outFont;

    /* renamed from: rf */
    protected RandomAccessFileOrArray f699rf;
    protected HashMap<String, int[]> tableDirectory;
    protected int tableGlyphOffset;

    TrueTypeFontSubSet(String str, RandomAccessFileOrArray randomAccessFileOrArray, HashSet<Integer> hashSet, int i, boolean z, boolean z2) {
        this.fileName = str;
        this.f699rf = randomAccessFileOrArray;
        this.glyphsUsed = hashSet;
        this.includeCmap = z;
        this.includeExtras = z2;
        this.directoryOffset = i;
        this.glyphsInList = new ArrayList<>(hashSet);
    }

    /* access modifiers changed from: package-private */
    public byte[] process() throws IOException, DocumentException {
        try {
            this.f699rf.reOpen();
            createTableDirectory();
            readLoca();
            flatGlyphs();
            createNewGlyphTables();
            locaTobytes();
            assembleFont();
            return this.outFont;
        } finally {
            try {
                this.f699rf.close();
            } catch (Exception unused) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public void assembleFont() throws IOException {
        String[] strArr;
        int i;
        int[] iArr;
        if (this.includeExtras) {
            strArr = tableNamesExtra;
        } else if (this.includeCmap) {
            strArr = tableNamesCmap;
        } else {
            strArr = tableNamesSimple;
        }
        int i2 = 0;
        int i3 = 2;
        for (String str : strArr) {
            if (!str.equals("glyf") && !str.equals("loca") && (iArr = this.tableDirectory.get(str)) != null) {
                i3++;
                i2 += (iArr[2] + 3) & -4;
            }
        }
        int i4 = (i3 * 16) + 12;
        this.outFont = new byte[(i2 + this.newLocaTableOut.length + this.newGlyfTable.length + i4)];
        this.fontPtr = 0;
        writeFontInt(65536);
        writeFontShort(i3);
        int i5 = entrySelectors[i3];
        int i6 = 1 << i5;
        writeFontShort(i6 * 16);
        writeFontShort(i5);
        writeFontShort((i3 - i6) * 16);
        for (String str2 : strArr) {
            int[] iArr2 = this.tableDirectory.get(str2);
            if (iArr2 != null) {
                writeFontString(str2);
                if (str2.equals("glyf")) {
                    writeFontInt(calculateChecksum(this.newGlyfTable));
                    i = this.glyfTableRealSize;
                } else if (str2.equals("loca")) {
                    writeFontInt(calculateChecksum(this.newLocaTableOut));
                    i = this.locaTableRealSize;
                } else {
                    writeFontInt(iArr2[0]);
                    i = iArr2[2];
                }
                writeFontInt(i4);
                writeFontInt(i);
                i4 += (i + 3) & -4;
            }
        }
        for (String str3 : strArr) {
            int[] iArr3 = this.tableDirectory.get(str3);
            if (iArr3 != null) {
                if (str3.equals("glyf")) {
                    byte[] bArr = this.newGlyfTable;
                    System.arraycopy(bArr, 0, this.outFont, this.fontPtr, bArr.length);
                    this.fontPtr += this.newGlyfTable.length;
                    this.newGlyfTable = null;
                } else if (str3.equals("loca")) {
                    byte[] bArr2 = this.newLocaTableOut;
                    System.arraycopy(bArr2, 0, this.outFont, this.fontPtr, bArr2.length);
                    this.fontPtr += this.newLocaTableOut.length;
                    this.newLocaTableOut = null;
                } else {
                    this.f699rf.seek((long) iArr3[1]);
                    this.f699rf.readFully(this.outFont, this.fontPtr, iArr3[2]);
                    this.fontPtr += (iArr3[2] + 3) & -4;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void createTableDirectory() throws IOException, DocumentException {
        this.tableDirectory = new HashMap<>();
        this.f699rf.seek((long) this.directoryOffset);
        if (this.f699rf.readInt() == 65536) {
            int readUnsignedShort = this.f699rf.readUnsignedShort();
            this.f699rf.skipBytes(6);
            for (int i = 0; i < readUnsignedShort; i++) {
                this.tableDirectory.put(readStandardString(4), new int[]{this.f699rf.readInt(), this.f699rf.readInt(), this.f699rf.readInt()});
            }
            return;
        }
        throw new DocumentException(MessageLocalization.getComposedMessage("1.is.not.a.true.type.file", this.fileName));
    }

    /* access modifiers changed from: protected */
    public void readLoca() throws IOException, DocumentException {
        int[] iArr = this.tableDirectory.get("head");
        int i = 0;
        if (iArr != null) {
            this.f699rf.seek((long) (iArr[1] + 51));
            this.locaShortTable = this.f699rf.readUnsignedShort() == 0;
            int[] iArr2 = this.tableDirectory.get("loca");
            if (iArr2 != null) {
                this.f699rf.seek((long) iArr2[1]);
                if (this.locaShortTable) {
                    int i2 = iArr2[2] / 2;
                    this.locaTable = new int[i2];
                    while (i < i2) {
                        this.locaTable[i] = this.f699rf.readUnsignedShort() * 2;
                        i++;
                    }
                    return;
                }
                int i3 = iArr2[2] / 4;
                this.locaTable = new int[i3];
                while (i < i3) {
                    this.locaTable[i] = this.f699rf.readInt();
                    i++;
                }
                return;
            }
            throw new DocumentException(MessageLocalization.getComposedMessage("table.1.does.not.exist.in.2", "loca", this.fileName));
        }
        throw new DocumentException(MessageLocalization.getComposedMessage("table.1.does.not.exist.in.2", "head", this.fileName));
    }

    /* access modifiers changed from: protected */
    public void createNewGlyphTables() throws IOException {
        this.newLocaTable = new int[this.locaTable.length];
        int size = this.glyphsInList.size();
        int[] iArr = new int[size];
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = this.glyphsInList.get(i2).intValue();
        }
        Arrays.sort(iArr);
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            int i5 = iArr[i4];
            int[] iArr2 = this.locaTable;
            i3 += iArr2[i5 + 1] - iArr2[i5];
        }
        this.glyfTableRealSize = i3;
        this.newGlyfTable = new byte[((i3 + 3) & -4)];
        int i6 = 0;
        int i7 = 0;
        while (true) {
            int[] iArr3 = this.newLocaTable;
            if (i < iArr3.length) {
                iArr3[i] = i6;
                if (i7 < size && iArr[i7] == i) {
                    i7++;
                    iArr3[i] = i6;
                    int[] iArr4 = this.locaTable;
                    int i8 = iArr4[i];
                    int i9 = iArr4[i + 1] - i8;
                    if (i9 > 0) {
                        this.f699rf.seek((long) (this.tableGlyphOffset + i8));
                        this.f699rf.readFully(this.newGlyfTable, i6, i9);
                        i6 += i9;
                    }
                }
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void locaTobytes() {
        if (this.locaShortTable) {
            this.locaTableRealSize = this.newLocaTable.length * 2;
        } else {
            this.locaTableRealSize = this.newLocaTable.length * 4;
        }
        byte[] bArr = new byte[((this.locaTableRealSize + 3) & -4)];
        this.newLocaTableOut = bArr;
        this.outFont = bArr;
        int i = 0;
        this.fontPtr = 0;
        while (true) {
            int[] iArr = this.newLocaTable;
            if (i < iArr.length) {
                if (this.locaShortTable) {
                    writeFontShort(iArr[i] / 2);
                } else {
                    writeFontInt(iArr[i]);
                }
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void flatGlyphs() throws IOException, DocumentException {
        int[] iArr = this.tableDirectory.get("glyf");
        if (iArr != null) {
            if (!this.glyphsUsed.contains(0)) {
                this.glyphsUsed.add(0);
                this.glyphsInList.add(0);
            }
            this.tableGlyphOffset = iArr[1];
            for (int i = 0; i < this.glyphsInList.size(); i++) {
                checkGlyphComposite(this.glyphsInList.get(i).intValue());
            }
            return;
        }
        throw new DocumentException(MessageLocalization.getComposedMessage("table.1.does.not.exist.in.2", "glyf", this.fileName));
    }

    /* access modifiers changed from: protected */
    public void checkGlyphComposite(int i) throws IOException {
        int[] iArr = this.locaTable;
        int i2 = iArr[i];
        if (i2 != iArr[i + 1]) {
            this.f699rf.seek((long) (this.tableGlyphOffset + i2));
            if (this.f699rf.readShort() < 0) {
                this.f699rf.skipBytes(8);
                while (true) {
                    int readUnsignedShort = this.f699rf.readUnsignedShort();
                    Integer valueOf = Integer.valueOf(this.f699rf.readUnsignedShort());
                    if (!this.glyphsUsed.contains(valueOf)) {
                        this.glyphsUsed.add(valueOf);
                        this.glyphsInList.add(valueOf);
                    }
                    if ((readUnsignedShort & 32) != 0) {
                        int i3 = (readUnsignedShort & 1) != 0 ? 4 : 2;
                        if ((readUnsignedShort & 8) != 0) {
                            i3 += 2;
                        } else if ((readUnsignedShort & 64) != 0) {
                            i3 += 4;
                        }
                        if ((readUnsignedShort & 128) != 0) {
                            i3 += 8;
                        }
                        this.f699rf.skipBytes(i3);
                    } else {
                        return;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public String readStandardString(int i) throws IOException {
        byte[] bArr = new byte[i];
        this.f699rf.readFully(bArr);
        try {
            return new String(bArr, "Cp1252");
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    /* access modifiers changed from: protected */
    public void writeFontShort(int i) {
        byte[] bArr = this.outFont;
        int i2 = this.fontPtr;
        int i3 = i2 + 1;
        this.fontPtr = i3;
        bArr[i2] = (byte) (i >> 8);
        this.fontPtr = i3 + 1;
        bArr[i3] = (byte) i;
    }

    /* access modifiers changed from: protected */
    public void writeFontInt(int i) {
        byte[] bArr = this.outFont;
        int i2 = this.fontPtr;
        int i3 = i2 + 1;
        this.fontPtr = i3;
        bArr[i2] = (byte) (i >> 24);
        int i4 = i3 + 1;
        this.fontPtr = i4;
        bArr[i3] = (byte) (i >> 16);
        int i5 = i4 + 1;
        this.fontPtr = i5;
        bArr[i4] = (byte) (i >> 8);
        this.fontPtr = i5 + 1;
        bArr[i5] = (byte) i;
    }

    /* access modifiers changed from: protected */
    public void writeFontString(String str) {
        byte[] convertToBytes = PdfEncodings.convertToBytes(str, "Cp1252");
        System.arraycopy(convertToBytes, 0, this.outFont, this.fontPtr, convertToBytes.length);
        this.fontPtr += convertToBytes.length;
    }

    /* access modifiers changed from: protected */
    public int calculateChecksum(byte[] bArr) {
        int length = bArr.length / 4;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < length; i6++) {
            int i7 = i5 + 1;
            i4 += bArr[i5] & UByte.MAX_VALUE;
            int i8 = i7 + 1;
            i3 += bArr[i7] & UByte.MAX_VALUE;
            int i9 = i8 + 1;
            i2 += bArr[i8] & UByte.MAX_VALUE;
            i5 = i9 + 1;
            i += bArr[i9] & UByte.MAX_VALUE;
        }
        return i + (i2 << 8) + (i3 << 16) + (i4 << 24);
    }
}
