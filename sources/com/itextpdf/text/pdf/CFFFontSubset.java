package com.itextpdf.text.pdf;

import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.pdf.CFFFont;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import kotlin.text.Typography;

public class CFFFontSubset extends CFFFont {
    static final byte ENDCHAR_OP = 14;
    static final byte RETURN_OP = 11;
    static final String[] SubrsEscapeFuncs = {"RESERVED_0", "RESERVED_1", "RESERVED_2", "and", "or", "not", "RESERVED_6", "RESERVED_7", "RESERVED_8", "abs", "add", "sub", HtmlTags.DIV, "RESERVED_13", "neg", "eq", "RESERVED_16", "RESERVED_17", "drop", "RESERVED_19", "put", "get", "ifelse", "random", "mul", "RESERVED_25", "sqrt", "dup", "exch", "index", "roll", "RESERVED_31", "RESERVED_32", "RESERVED_33", "hflex", "flex", "hflex1", "flex1", "RESERVED_REST"};
    static final String[] SubrsFunctions = {"RESERVED_0", "hstem", "RESERVED_2", "vstem", "vmoveto", "rlineto", "hlineto", "vlineto", "rrcurveto", "RESERVED_9", "callsubr", "return", "escape", "RESERVED_13", "endchar", "RESERVED_15", "RESERVED_16", "RESERVED_17", "hstemhm", "hintmask", "cntrmask", "rmoveto", "hmoveto", "vstemhm", "rcurveline", "rlinecurve", "vvcurveto", "hhcurveto", "shortint", "callgsubr", "vhcurveto", "hvcurveto"};
    HashSet<Integer> FDArrayUsed = new HashSet<>();
    int GBias;
    HashMap<Integer, int[]> GlyphsUsed;
    byte[] NewCharStringsIndex;
    byte[] NewGSubrsIndex;
    byte[][] NewLSubrsIndex;
    byte[] NewSubrsIndexNonCID;
    int NumOfHints;
    LinkedList<CFFFont.Item> OutputList;
    ArrayList<Integer> glyphsInList;
    HashMap<Integer, int[]> hGSubrsUsed = new HashMap<>();
    HashMap<Integer, int[]>[] hSubrsUsed;
    HashMap<Integer, int[]> hSubrsUsedNonCID = new HashMap<>();
    ArrayList<Integer> lGSubrsUsed = new ArrayList<>();
    ArrayList<Integer>[] lSubrsUsed;
    ArrayList<Integer> lSubrsUsedNonCID = new ArrayList<>();

    public CFFFontSubset(RandomAccessFileOrArray randomAccessFileOrArray, HashMap<Integer, int[]> hashMap) {
        super(randomAccessFileOrArray);
        this.GBias = 0;
        this.NumOfHints = 0;
        this.GlyphsUsed = hashMap;
        this.glyphsInList = new ArrayList<>(hashMap.keySet());
        for (int i = 0; i < this.fonts.length; i++) {
            seek(this.fonts[i].charstringsOffset);
            this.fonts[i].nglyphs = getCard16();
            seek(this.stringIndexOffset);
            this.fonts[i].nstrings = getCard16() + standardStrings.length;
            this.fonts[i].charstringsOffsets = getIndex(this.fonts[i].charstringsOffset);
            if (this.fonts[i].fdselectOffset >= 0) {
                readFDSelect(i);
                BuildFDArrayUsed(i);
            }
            if (this.fonts[i].isCID) {
                ReadFDArray(i);
            }
            this.fonts[i].CharsetLength = CountCharset(this.fonts[i].charsetOffset, this.fonts[i].nglyphs);
        }
    }

    /* access modifiers changed from: package-private */
    public int CountCharset(int i, int i2) {
        int i3;
        seek(i);
        char card8 = getCard8();
        if (card8 == 0) {
            return (i2 * 2) + 1;
        }
        if (card8 == 1) {
            i3 = CountRange(i2, 1) * 3;
        } else if (card8 != 2) {
            return 0;
        } else {
            i3 = CountRange(i2, 2) * 4;
        }
        return i3 + 1;
    }

    /* access modifiers changed from: package-private */
    public int CountRange(int i, int i2) {
        char c;
        int i3 = 0;
        int i4 = 1;
        while (i4 < i) {
            i3++;
            getCard16();
            if (i2 == 1) {
                c = getCard8();
            } else {
                c = getCard16();
            }
            i4 += c + 1;
        }
        return i3;
    }

    /* access modifiers changed from: protected */
    public void readFDSelect(int i) {
        int i2 = this.fonts[i].nglyphs;
        int[] iArr = new int[i2];
        seek(this.fonts[i].fdselectOffset);
        this.fonts[i].FDSelectFormat = getCard8();
        int i3 = this.fonts[i].FDSelectFormat;
        if (i3 == 0) {
            for (int i4 = 0; i4 < i2; i4++) {
                iArr[i4] = getCard8();
            }
            this.fonts[i].FDSelectLength = this.fonts[i].nglyphs + 1;
        } else if (i3 == 3) {
            char card16 = getCard16();
            char card162 = getCard16();
            int i5 = 0;
            int i6 = 0;
            while (i5 < card16) {
                int card8 = getCard8();
                char card163 = getCard16();
                int i7 = card163 - card162;
                for (int i8 = 0; i8 < i7; i8++) {
                    iArr[i6] = card8;
                    i6++;
                }
                i5++;
                card162 = card163;
            }
            this.fonts[i].FDSelectLength = (card16 * 3) + 3 + 2;
        }
        this.fonts[i].FDSelect = iArr;
    }

    /* access modifiers changed from: protected */
    public void BuildFDArrayUsed(int i) {
        int[] iArr = this.fonts[i].FDSelect;
        for (int i2 = 0; i2 < this.glyphsInList.size(); i2++) {
            this.FDArrayUsed.add(Integer.valueOf(iArr[this.glyphsInList.get(i2).intValue()]));
        }
    }

    /* access modifiers changed from: protected */
    public void ReadFDArray(int i) {
        seek(this.fonts[i].fdarrayOffset);
        this.fonts[i].FDArrayCount = getCard16();
        this.fonts[i].FDArrayOffsize = getCard8();
        if (this.fonts[i].FDArrayOffsize < 4) {
            this.fonts[i].FDArrayOffsize++;
        }
        this.fonts[i].FDArrayOffsets = getIndex(this.fonts[i].fdarrayOffset);
    }

    public byte[] Process(String str) throws IOException {
        try {
            this.buf.reOpen();
            int i = 0;
            while (true) {
                if (i >= this.fonts.length) {
                    break;
                } else if (str.equals(this.fonts[i].name)) {
                    break;
                } else {
                    i++;
                }
            }
            if (i == this.fonts.length) {
                return null;
            }
            if (this.gsubrIndexOffset >= 0) {
                this.GBias = CalcBias(this.gsubrIndexOffset, i);
            }
            BuildNewCharString(i);
            BuildNewLGSubrs(i);
            byte[] BuildNewFile = BuildNewFile(i);
            try {
                this.buf.close();
            } catch (Exception unused) {
            }
            return BuildNewFile;
        } finally {
            try {
                this.buf.close();
            } catch (Exception unused2) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public int CalcBias(int i, int i2) {
        seek(i);
        char card16 = getCard16();
        if (this.fonts[i2].CharstringType == 1) {
            return 0;
        }
        if (card16 < 1240) {
            return 107;
        }
        return card16 < 33900 ? 1131 : 32768;
    }

    /* access modifiers changed from: protected */
    public void BuildNewCharString(int i) throws IOException {
        this.NewCharStringsIndex = BuildNewIndex(this.fonts[i].charstringsOffsets, this.GlyphsUsed, (byte) 14);
    }

    /* access modifiers changed from: protected */
    public void BuildNewLGSubrs(int i) throws IOException {
        if (this.fonts[i].isCID) {
            this.hSubrsUsed = new HashMap[this.fonts[i].fdprivateOffsets.length];
            this.lSubrsUsed = new ArrayList[this.fonts[i].fdprivateOffsets.length];
            this.NewLSubrsIndex = new byte[this.fonts[i].fdprivateOffsets.length][];
            this.fonts[i].PrivateSubrsOffset = new int[this.fonts[i].fdprivateOffsets.length];
            this.fonts[i].PrivateSubrsOffsetsArray = new int[this.fonts[i].fdprivateOffsets.length][];
            ArrayList arrayList = new ArrayList(this.FDArrayUsed);
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                int intValue = ((Integer) arrayList.get(i2)).intValue();
                this.hSubrsUsed[intValue] = new HashMap<>();
                this.lSubrsUsed[intValue] = new ArrayList<>();
                BuildFDSubrsOffsets(i, intValue);
                if (this.fonts[i].PrivateSubrsOffset[intValue] >= 0) {
                    BuildSubrUsed(i, intValue, this.fonts[i].PrivateSubrsOffset[intValue], this.fonts[i].PrivateSubrsOffsetsArray[intValue], this.hSubrsUsed[intValue], this.lSubrsUsed[intValue]);
                    this.NewLSubrsIndex[intValue] = BuildNewIndex(this.fonts[i].PrivateSubrsOffsetsArray[intValue], this.hSubrsUsed[intValue], (byte) 11);
                }
            }
        } else if (this.fonts[i].privateSubrs >= 0) {
            this.fonts[i].SubrsOffsets = getIndex(this.fonts[i].privateSubrs);
            BuildSubrUsed(i, -1, this.fonts[i].privateSubrs, this.fonts[i].SubrsOffsets, this.hSubrsUsedNonCID, this.lSubrsUsedNonCID);
        }
        BuildGSubrsUsed(i);
        if (this.fonts[i].privateSubrs >= 0) {
            this.NewSubrsIndexNonCID = BuildNewIndex(this.fonts[i].SubrsOffsets, this.hSubrsUsedNonCID, (byte) 11);
        }
        this.NewGSubrsIndex = BuildNewIndex(this.gsubrOffsets, this.hGSubrsUsed, (byte) 11);
    }

    /* access modifiers changed from: protected */
    public void BuildFDSubrsOffsets(int i, int i2) {
        this.fonts[i].PrivateSubrsOffset[i2] = -1;
        seek(this.fonts[i].fdprivateOffsets[i2]);
        while (getPosition() < this.fonts[i].fdprivateOffsets[i2] + this.fonts[i].fdprivateLengths[i2]) {
            getDictItem();
            if (this.key == "Subrs") {
                this.fonts[i].PrivateSubrsOffset[i2] = ((Integer) this.args[0]).intValue() + this.fonts[i].fdprivateOffsets[i2];
            }
        }
        if (this.fonts[i].PrivateSubrsOffset[i2] >= 0) {
            this.fonts[i].PrivateSubrsOffsetsArray[i2] = getIndex(this.fonts[i].PrivateSubrsOffset[i2]);
        }
    }

    /* access modifiers changed from: protected */
    public void BuildSubrUsed(int i, int i2, int i3, int[] iArr, HashMap<Integer, int[]> hashMap, ArrayList<Integer> arrayList) {
        int i4 = i;
        int i5 = i2;
        int[] iArr2 = iArr;
        int CalcBias = CalcBias(i3, i4);
        for (int i6 = 0; i6 < this.glyphsInList.size(); i6++) {
            int intValue = this.glyphsInList.get(i6).intValue();
            int i7 = this.fonts[i4].charstringsOffsets[intValue];
            int i8 = this.fonts[i4].charstringsOffsets[intValue + 1];
            if (i5 >= 0) {
                EmptyStack();
                this.NumOfHints = 0;
                if (this.fonts[i4].FDSelect[intValue] == i5) {
                    ReadASubr(i7, i8, this.GBias, CalcBias, hashMap, arrayList, iArr);
                }
            } else {
                ReadASubr(i7, i8, this.GBias, CalcBias, hashMap, arrayList, iArr);
            }
        }
        for (int i9 = 0; i9 < arrayList.size(); i9++) {
            int intValue2 = arrayList.get(i9).intValue();
            if (intValue2 < iArr2.length - 1 && intValue2 >= 0) {
                ReadASubr(iArr2[intValue2], iArr2[intValue2 + 1], this.GBias, CalcBias, hashMap, arrayList, iArr);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void BuildGSubrsUsed(int i) {
        int i2;
        int i3;
        if (this.fonts[i].privateSubrs >= 0) {
            i3 = CalcBias(this.fonts[i].privateSubrs, i);
            i2 = this.lSubrsUsedNonCID.size();
        } else {
            i3 = 0;
            i2 = 0;
        }
        for (int i4 = 0; i4 < this.lGSubrsUsed.size(); i4++) {
            int intValue = this.lGSubrsUsed.get(i4).intValue();
            if (intValue < this.gsubrOffsets.length - 1 && intValue >= 0) {
                int i5 = this.gsubrOffsets[intValue];
                int i6 = this.gsubrOffsets[intValue + 1];
                if (this.fonts[i].isCID) {
                    ReadASubr(i5, i6, this.GBias, 0, this.hGSubrsUsed, this.lGSubrsUsed, (int[]) null);
                } else {
                    ReadASubr(i5, i6, this.GBias, i3, this.hSubrsUsedNonCID, this.lSubrsUsedNonCID, this.fonts[i].SubrsOffsets);
                    if (i2 < this.lSubrsUsedNonCID.size()) {
                        while (i2 < this.lSubrsUsedNonCID.size()) {
                            int intValue2 = this.lSubrsUsedNonCID.get(i2).intValue();
                            if (intValue2 < this.fonts[i].SubrsOffsets.length - 1 && intValue2 >= 0) {
                                ReadASubr(this.fonts[i].SubrsOffsets[intValue2], this.fonts[i].SubrsOffsets[intValue2 + 1], this.GBias, i3, this.hSubrsUsedNonCID, this.lSubrsUsedNonCID, this.fonts[i].SubrsOffsets);
                            }
                            i2++;
                        }
                        i2 = this.lSubrsUsedNonCID.size();
                    }
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v30, resolved type: java.lang.Integer} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void ReadASubr(int r13, int r14, int r15, int r16, java.util.HashMap<java.lang.Integer, int[]> r17, java.util.ArrayList<java.lang.Integer> r18, int[] r19) {
        /*
            r12 = this;
            r6 = r12
            r7 = r17
            r12.EmptyStack()
            r8 = 0
            r6.NumOfHints = r8
            r12.seek(r13)
        L_0x000c:
            int r0 = r12.getPosition()
            r9 = r14
            if (r0 >= r9) goto L_0x0102
            r12.ReadCommand()
            int r10 = r12.getPosition()
            int r0 = r6.arg_count
            r1 = 0
            if (r0 <= 0) goto L_0x0028
            java.lang.Object[] r0 = r6.args
            int r2 = r6.arg_count
            int r2 = r2 + -1
            r0 = r0[r2]
            goto L_0x0029
        L_0x0028:
            r0 = r1
        L_0x0029:
            int r2 = r6.arg_count
            r12.HandelStack()
            java.lang.String r3 = r6.key
            java.lang.String r4 = "callsubr"
            if (r3 != r4) goto L_0x0071
            if (r2 <= 0) goto L_0x006e
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            int r0 = r0 + r16
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
            boolean r2 = r7.containsKey(r2)
            if (r2 != 0) goto L_0x0059
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
            r7.put(r2, r1)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r0)
            r11 = r18
            r11.add(r1)
            goto L_0x005b
        L_0x0059:
            r11 = r18
        L_0x005b:
            r1 = r19[r0]
            int r0 = r0 + 1
            r2 = r19[r0]
            r0 = r12
            r3 = r16
            r4 = r15
            r5 = r19
            r0.CalcHints(r1, r2, r3, r4, r5)
            r12.seek(r10)
            goto L_0x000c
        L_0x006e:
            r11 = r18
            goto L_0x000c
        L_0x0071:
            r11 = r18
            java.lang.String r3 = r6.key
            java.lang.String r4 = "callgsubr"
            if (r3 != r4) goto L_0x00b8
            if (r2 <= 0) goto L_0x000c
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            int r0 = r0 + r15
            java.util.HashMap<java.lang.Integer, int[]> r2 = r6.hGSubrsUsed
            java.lang.Integer r3 = java.lang.Integer.valueOf(r0)
            boolean r2 = r2.containsKey(r3)
            if (r2 != 0) goto L_0x00a0
            java.util.HashMap<java.lang.Integer, int[]> r2 = r6.hGSubrsUsed
            java.lang.Integer r3 = java.lang.Integer.valueOf(r0)
            r2.put(r3, r1)
            java.util.ArrayList<java.lang.Integer> r1 = r6.lGSubrsUsed
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
            r1.add(r2)
        L_0x00a0:
            int[] r1 = r6.gsubrOffsets
            r1 = r1[r0]
            int[] r2 = r6.gsubrOffsets
            int r0 = r0 + 1
            r2 = r2[r0]
            r0 = r12
            r3 = r16
            r4 = r15
            r5 = r19
            r0.CalcHints(r1, r2, r3, r4, r5)
            r12.seek(r10)
            goto L_0x000c
        L_0x00b8:
            java.lang.String r0 = r6.key
            java.lang.String r1 = "hstem"
            if (r0 == r1) goto L_0x00f9
            java.lang.String r0 = r6.key
            java.lang.String r1 = "vstem"
            if (r0 == r1) goto L_0x00f9
            java.lang.String r0 = r6.key
            java.lang.String r1 = "hstemhm"
            if (r0 == r1) goto L_0x00f9
            java.lang.String r0 = r6.key
            java.lang.String r1 = "vstemhm"
            if (r0 != r1) goto L_0x00d3
            goto L_0x00f9
        L_0x00d3:
            java.lang.String r0 = r6.key
            java.lang.String r1 = "hintmask"
            if (r0 == r1) goto L_0x00df
            java.lang.String r0 = r6.key
            java.lang.String r1 = "cntrmask"
            if (r0 != r1) goto L_0x000c
        L_0x00df:
            int r0 = r6.NumOfHints
            int r2 = r2 / 2
            int r0 = r0 + r2
            r6.NumOfHints = r0
            int r1 = r0 / 8
            int r0 = r0 % 8
            if (r0 != 0) goto L_0x00ee
            if (r1 != 0) goto L_0x00f0
        L_0x00ee:
            int r1 = r1 + 1
        L_0x00f0:
            r0 = 0
        L_0x00f1:
            if (r0 >= r1) goto L_0x000c
            r12.getCard8()
            int r0 = r0 + 1
            goto L_0x00f1
        L_0x00f9:
            int r0 = r6.NumOfHints
            int r2 = r2 / 2
            int r0 = r0 + r2
            r6.NumOfHints = r0
            goto L_0x000c
        L_0x0102:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.CFFFontSubset.ReadASubr(int, int, int, int, java.util.HashMap, java.util.ArrayList, int[]):void");
    }

    /* access modifiers changed from: protected */
    public void HandelStack() {
        int StackOpp = StackOpp();
        if (StackOpp >= 2) {
            EmptyStack();
        } else if (StackOpp == 1) {
            PushStack();
        } else {
            int i = StackOpp * -1;
            for (int i2 = 0; i2 < i; i2++) {
                PopStack();
            }
        }
    }

    /* access modifiers changed from: protected */
    public int StackOpp() {
        if (this.key == "ifelse") {
            return -3;
        }
        if (this.key == "roll" || this.key == "put") {
            return -2;
        }
        if (this.key == "callsubr" || this.key == "callgsubr" || this.key == "add" || this.key == "sub" || this.key == HtmlTags.DIV || this.key == "mul" || this.key == "drop" || this.key == "and" || this.key == "or" || this.key == "eq") {
            return -1;
        }
        if (this.key == "abs" || this.key == "neg" || this.key == "sqrt" || this.key == "exch" || this.key == "index" || this.key == "get" || this.key == "not" || this.key == "return") {
            return 0;
        }
        return (this.key == "random" || this.key == "dup") ? 1 : 2;
    }

    /* access modifiers changed from: protected */
    public void EmptyStack() {
        for (int i = 0; i < this.arg_count; i++) {
            this.args[i] = null;
        }
        this.arg_count = 0;
    }

    /* access modifiers changed from: protected */
    public void PopStack() {
        if (this.arg_count > 0) {
            this.args[this.arg_count - 1] = null;
            this.arg_count--;
        }
    }

    /* access modifiers changed from: protected */
    public void PushStack() {
        this.arg_count++;
    }

    /* access modifiers changed from: protected */
    public void ReadCommand() {
        this.key = null;
        boolean z = false;
        while (!z) {
            char card8 = getCard8();
            if (card8 == 28) {
                this.args[this.arg_count] = Integer.valueOf((getCard8() << 8) | getCard8());
                this.arg_count++;
            } else if (card8 >= ' ' && card8 <= 246) {
                this.args[this.arg_count] = Integer.valueOf(card8 - 139);
                this.arg_count++;
            } else if (card8 >= 247 && card8 <= 250) {
                this.args[this.arg_count] = Integer.valueOf(((card8 - 247) * 256) + getCard8() + 108);
                this.arg_count++;
            } else if (card8 >= 251 && card8 <= 254) {
                this.args[this.arg_count] = Integer.valueOf((((-(card8 - 251)) * 256) - getCard8()) - 108);
                this.arg_count++;
            } else if (card8 == 255) {
                this.args[this.arg_count] = Integer.valueOf((getCard8() << 24) | (getCard8() << 16) | (getCard8() << 8) | getCard8());
                this.arg_count++;
            } else if (card8 <= 31 && card8 != 28) {
                if (card8 == 12) {
                    int card82 = getCard8();
                    String[] strArr = SubrsEscapeFuncs;
                    if (card82 > strArr.length - 1) {
                        card82 = strArr.length - 1;
                    }
                    this.key = strArr[card82];
                } else {
                    this.key = SubrsFunctions[card8];
                }
                z = true;
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: java.lang.Integer} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int CalcHints(int r9, int r10, int r11, int r12, int[] r13) {
        /*
            r8 = this;
            r8.seek(r9)
        L_0x0003:
            int r9 = r8.getPosition()
            if (r9 >= r10) goto L_0x00ab
            r8.ReadCommand()
            int r9 = r8.getPosition()
            r0 = 0
            int r1 = r8.arg_count
            if (r1 <= 0) goto L_0x001d
            java.lang.Object[] r0 = r8.args
            int r1 = r8.arg_count
            int r1 = r1 + -1
            r0 = r0[r1]
        L_0x001d:
            int r1 = r8.arg_count
            r8.HandelStack()
            java.lang.String r2 = r8.key
            java.lang.String r3 = "callsubr"
            if (r2 != r3) goto L_0x0042
            if (r1 <= 0) goto L_0x0003
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            int r0 = r0 + r11
            r2 = r13[r0]
            int r0 = r0 + 1
            r3 = r13[r0]
            r1 = r8
            r4 = r11
            r5 = r12
            r6 = r13
            r1.CalcHints(r2, r3, r4, r5, r6)
            r8.seek(r9)
            goto L_0x0003
        L_0x0042:
            java.lang.String r2 = r8.key
            java.lang.String r3 = "callgsubr"
            if (r2 != r3) goto L_0x0066
            if (r1 <= 0) goto L_0x0003
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            int r0 = r0 + r12
            int[] r1 = r8.gsubrOffsets
            r3 = r1[r0]
            int[] r1 = r8.gsubrOffsets
            int r0 = r0 + 1
            r4 = r1[r0]
            r2 = r8
            r5 = r11
            r6 = r12
            r7 = r13
            r2.CalcHints(r3, r4, r5, r6, r7)
            r8.seek(r9)
            goto L_0x0003
        L_0x0066:
            java.lang.String r9 = r8.key
            java.lang.String r0 = "hstem"
            if (r9 == r0) goto L_0x00a2
            java.lang.String r9 = r8.key
            java.lang.String r0 = "vstem"
            if (r9 == r0) goto L_0x00a2
            java.lang.String r9 = r8.key
            java.lang.String r0 = "hstemhm"
            if (r9 == r0) goto L_0x00a2
            java.lang.String r9 = r8.key
            java.lang.String r0 = "vstemhm"
            if (r9 != r0) goto L_0x0081
            goto L_0x00a2
        L_0x0081:
            java.lang.String r9 = r8.key
            java.lang.String r0 = "hintmask"
            if (r9 == r0) goto L_0x008d
            java.lang.String r9 = r8.key
            java.lang.String r0 = "cntrmask"
            if (r9 != r0) goto L_0x0003
        L_0x008d:
            int r9 = r8.NumOfHints
            int r0 = r9 / 8
            int r9 = r9 % 8
            if (r9 != 0) goto L_0x0097
            if (r0 != 0) goto L_0x0099
        L_0x0097:
            int r0 = r0 + 1
        L_0x0099:
            r9 = 0
        L_0x009a:
            if (r9 >= r0) goto L_0x0003
            r8.getCard8()
            int r9 = r9 + 1
            goto L_0x009a
        L_0x00a2:
            int r9 = r8.NumOfHints
            int r1 = r1 / 2
            int r9 = r9 + r1
            r8.NumOfHints = r9
            goto L_0x0003
        L_0x00ab:
            int r9 = r8.NumOfHints
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.CFFFontSubset.CalcHints(int, int, int, int, int[]):int");
    }

    /* access modifiers changed from: protected */
    public byte[] BuildNewIndex(int[] iArr, HashMap<Integer, int[]> hashMap, byte b) throws IOException {
        int[] iArr2 = new int[iArr.length];
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < iArr.length; i4++) {
            iArr2[i4] = i2;
            if (hashMap.containsKey(Integer.valueOf(i4))) {
                i2 += iArr[i4 + 1] - iArr[i4];
            } else {
                i3++;
            }
        }
        byte[] bArr = new byte[(i2 + i3)];
        int i5 = 0;
        while (i < iArr.length - 1) {
            int i6 = iArr2[i];
            int i7 = i + 1;
            int i8 = iArr2[i7];
            int i9 = i6 + i5;
            iArr2[i] = i9;
            if (i6 != i8) {
                this.buf.seek((long) iArr[i]);
                this.buf.readFully(bArr, i9, i8 - i6);
            } else {
                bArr[i9] = b;
                i5++;
            }
            i = i7;
        }
        int length = iArr.length - 1;
        iArr2[length] = iArr2[length] + i5;
        return AssembleIndex(iArr2, bArr);
    }

    /* access modifiers changed from: protected */
    public byte[] AssembleIndex(int[] iArr, byte[] bArr) {
        int[] iArr2 = iArr;
        byte[] bArr2 = bArr;
        char length = (char) (iArr2.length - 1);
        int i = iArr2[iArr2.length - 1];
        byte b = i <= 255 ? 1 : i <= 65535 ? 2 : i <= 16777215 ? (byte) 3 : 4;
        byte[] bArr3 = new byte[(((length + 1) * b) + 3 + bArr2.length)];
        int i2 = 0;
        bArr3[0] = (byte) ((length >>> 8) & 255);
        bArr3[1] = (byte) ((length >>> 0) & 255);
        bArr3[2] = b;
        int i3 = 3;
        for (int i4 : iArr2) {
            int i5 = (i4 - iArr2[0]) + 1;
            if (b != 1) {
                if (b != 2) {
                    if (b != 3) {
                        if (b != 4) {
                        } else {
                            bArr3[i3] = (byte) ((i5 >>> 24) & 255);
                            i3++;
                        }
                    }
                    bArr3[i3] = (byte) ((i5 >>> 16) & 255);
                    i3++;
                }
                bArr3[i3] = (byte) ((i5 >>> 8) & 255);
                i3++;
            }
            bArr3[i3] = (byte) ((i5 >>> 0) & 255);
            i3++;
        }
        int length2 = bArr2.length;
        while (i2 < length2) {
            bArr3[i3] = bArr2[i2];
            i2++;
            i3++;
        }
        return bArr3;
    }

    /* access modifiers changed from: protected */
    public byte[] BuildNewFile(int i) {
        this.OutputList = new LinkedList<>();
        CopyHeader();
        BuildIndexHeader(1, 1, 1);
        this.OutputList.addLast(new CFFFont.UInt8Item((char) (this.fonts[i].name.length() + 1)));
        this.OutputList.addLast(new CFFFont.StringItem(this.fonts[i].name));
        BuildIndexHeader(1, 2, 1);
        CFFFont.IndexOffsetItem indexOffsetItem = new CFFFont.IndexOffsetItem(2);
        this.OutputList.addLast(indexOffsetItem);
        CFFFont.IndexBaseItem indexBaseItem = new CFFFont.IndexBaseItem();
        this.OutputList.addLast(indexBaseItem);
        CFFFont.DictOffsetItem dictOffsetItem = new CFFFont.DictOffsetItem();
        CFFFont.DictOffsetItem dictOffsetItem2 = new CFFFont.DictOffsetItem();
        CFFFont.DictOffsetItem dictOffsetItem3 = new CFFFont.DictOffsetItem();
        CFFFont.DictOffsetItem dictOffsetItem4 = new CFFFont.DictOffsetItem();
        CFFFont.DictOffsetItem dictOffsetItem5 = new CFFFont.DictOffsetItem();
        if (!this.fonts[i].isCID) {
            this.OutputList.addLast(new CFFFont.DictNumberItem(this.fonts[i].nstrings));
            this.OutputList.addLast(new CFFFont.DictNumberItem(this.fonts[i].nstrings + 1));
            this.OutputList.addLast(new CFFFont.DictNumberItem(0));
            this.OutputList.addLast(new CFFFont.UInt8Item(12));
            this.OutputList.addLast(new CFFFont.UInt8Item(30));
            this.OutputList.addLast(new CFFFont.DictNumberItem(this.fonts[i].nglyphs));
            this.OutputList.addLast(new CFFFont.UInt8Item(12));
            this.OutputList.addLast(new CFFFont.UInt8Item(Typography.quote));
        }
        seek(this.topdictOffsets[i]);
        while (getPosition() < this.topdictOffsets[i + 1]) {
            int position = getPosition();
            getDictItem();
            int position2 = getPosition();
            if (!(this.key == "Encoding" || this.key == "Private" || this.key == "FDSelect" || this.key == "FDArray" || this.key == "charset" || this.key == "CharStrings")) {
                this.OutputList.add(new CFFFont.RangeItem(this.buf, position, position2 - position));
            }
        }
        CreateKeys(dictOffsetItem3, dictOffsetItem4, dictOffsetItem, dictOffsetItem2);
        this.OutputList.addLast(new CFFFont.IndexMarkerItem(indexOffsetItem, indexBaseItem));
        if (this.fonts[i].isCID) {
            this.OutputList.addLast(getEntireIndexRange(this.stringIndexOffset));
        } else {
            CreateNewStringIndex(i);
        }
        this.OutputList.addLast(new CFFFont.RangeItem(new RandomAccessFileOrArray(this.NewGSubrsIndex), 0, this.NewGSubrsIndex.length));
        if (this.fonts[i].isCID) {
            this.OutputList.addLast(new CFFFont.MarkerItem(dictOffsetItem4));
            if (this.fonts[i].fdselectOffset >= 0) {
                this.OutputList.addLast(new CFFFont.RangeItem(this.buf, this.fonts[i].fdselectOffset, this.fonts[i].FDSelectLength));
            } else {
                CreateFDSelect(dictOffsetItem4, this.fonts[i].nglyphs);
            }
            this.OutputList.addLast(new CFFFont.MarkerItem(dictOffsetItem));
            this.OutputList.addLast(new CFFFont.RangeItem(this.buf, this.fonts[i].charsetOffset, this.fonts[i].CharsetLength));
            if (this.fonts[i].fdarrayOffset >= 0) {
                this.OutputList.addLast(new CFFFont.MarkerItem(dictOffsetItem3));
                Reconstruct(i);
            } else {
                CreateFDArray(dictOffsetItem3, dictOffsetItem5, i);
            }
        } else {
            CreateFDSelect(dictOffsetItem4, this.fonts[i].nglyphs);
            CreateCharset(dictOffsetItem, this.fonts[i].nglyphs);
            CreateFDArray(dictOffsetItem3, dictOffsetItem5, i);
        }
        if (this.fonts[i].privateOffset >= 0) {
            CFFFont.IndexBaseItem indexBaseItem2 = new CFFFont.IndexBaseItem();
            this.OutputList.addLast(indexBaseItem2);
            this.OutputList.addLast(new CFFFont.MarkerItem(dictOffsetItem5));
            CFFFont.DictOffsetItem dictOffsetItem6 = new CFFFont.DictOffsetItem();
            CreateNonCIDPrivate(i, dictOffsetItem6);
            CreateNonCIDSubrs(i, indexBaseItem2, dictOffsetItem6);
        }
        this.OutputList.addLast(new CFFFont.MarkerItem(dictOffsetItem2));
        this.OutputList.addLast(new CFFFont.RangeItem(new RandomAccessFileOrArray(this.NewCharStringsIndex), 0, this.NewCharStringsIndex.length));
        int[] iArr = {0};
        Iterator it = this.OutputList.iterator();
        while (it.hasNext()) {
            ((CFFFont.Item) it.next()).increment(iArr);
        }
        Iterator it2 = this.OutputList.iterator();
        while (it2.hasNext()) {
            ((CFFFont.Item) it2.next()).xref();
        }
        byte[] bArr = new byte[iArr[0]];
        Iterator it3 = this.OutputList.iterator();
        while (it3.hasNext()) {
            ((CFFFont.Item) it3.next()).emit(bArr);
        }
        return bArr;
    }

    /* access modifiers changed from: protected */
    public void CopyHeader() {
        seek(0);
        getCard8();
        getCard8();
        char card8 = getCard8();
        getCard8();
        this.nextIndexOffset = card8;
        this.OutputList.addLast(new CFFFont.RangeItem(this.buf, 0, card8));
    }

    /* access modifiers changed from: protected */
    public void BuildIndexHeader(int i, int i2, int i3) {
        this.OutputList.addLast(new CFFFont.UInt16Item((char) i));
        this.OutputList.addLast(new CFFFont.UInt8Item((char) i2));
        if (i2 == 1) {
            this.OutputList.addLast(new CFFFont.UInt8Item((char) i3));
        } else if (i2 == 2) {
            this.OutputList.addLast(new CFFFont.UInt16Item((char) i3));
        } else if (i2 == 3) {
            this.OutputList.addLast(new CFFFont.UInt24Item((char) i3));
        } else if (i2 == 4) {
            this.OutputList.addLast(new CFFFont.UInt32Item((char) i3));
        }
    }

    /* access modifiers changed from: protected */
    public void CreateKeys(CFFFont.OffsetItem offsetItem, CFFFont.OffsetItem offsetItem2, CFFFont.OffsetItem offsetItem3, CFFFont.OffsetItem offsetItem4) {
        this.OutputList.addLast(offsetItem);
        this.OutputList.addLast(new CFFFont.UInt8Item(12));
        this.OutputList.addLast(new CFFFont.UInt8Item(Typography.dollar));
        this.OutputList.addLast(offsetItem2);
        this.OutputList.addLast(new CFFFont.UInt8Item(12));
        this.OutputList.addLast(new CFFFont.UInt8Item('%'));
        this.OutputList.addLast(offsetItem3);
        this.OutputList.addLast(new CFFFont.UInt8Item(15));
        this.OutputList.addLast(offsetItem4);
        this.OutputList.addLast(new CFFFont.UInt8Item(17));
    }

    /* access modifiers changed from: protected */
    public void CreateNewStringIndex(int i) {
        int i2;
        String str = this.fonts[i].name + "-OneRange";
        if (str.length() > 127) {
            str = str.substring(0, 127);
        }
        String str2 = "AdobeIdentity" + str;
        int i3 = this.stringOffsets[this.stringOffsets.length - 1] - this.stringOffsets[0];
        int i4 = this.stringOffsets[0] - 1;
        if (str2.length() + i3 <= 255) {
            i2 = 1;
        } else if (str2.length() + i3 <= 65535) {
            i2 = 2;
        } else {
            i2 = str2.length() + i3 <= 16777215 ? 3 : 4;
        }
        this.OutputList.addLast(new CFFFont.UInt16Item((char) ((this.stringOffsets.length - 1) + 3)));
        this.OutputList.addLast(new CFFFont.UInt8Item((char) i2));
        for (int i5 : this.stringOffsets) {
            this.OutputList.addLast(new CFFFont.IndexOffsetItem(i2, i5 - i4));
        }
        int i6 = (this.stringOffsets[this.stringOffsets.length - 1] - i4) + 5;
        this.OutputList.addLast(new CFFFont.IndexOffsetItem(i2, i6));
        int i7 = i6 + 8;
        this.OutputList.addLast(new CFFFont.IndexOffsetItem(i2, i7));
        this.OutputList.addLast(new CFFFont.IndexOffsetItem(i2, i7 + str.length()));
        this.OutputList.addLast(new CFFFont.RangeItem(this.buf, this.stringOffsets[0], i3));
        this.OutputList.addLast(new CFFFont.StringItem(str2));
    }

    /* access modifiers changed from: protected */
    public void CreateFDSelect(CFFFont.OffsetItem offsetItem, int i) {
        this.OutputList.addLast(new CFFFont.MarkerItem(offsetItem));
        this.OutputList.addLast(new CFFFont.UInt8Item(3));
        this.OutputList.addLast(new CFFFont.UInt16Item(1));
        this.OutputList.addLast(new CFFFont.UInt16Item(0));
        this.OutputList.addLast(new CFFFont.UInt8Item(0));
        this.OutputList.addLast(new CFFFont.UInt16Item((char) i));
    }

    /* access modifiers changed from: protected */
    public void CreateCharset(CFFFont.OffsetItem offsetItem, int i) {
        this.OutputList.addLast(new CFFFont.MarkerItem(offsetItem));
        this.OutputList.addLast(new CFFFont.UInt8Item(2));
        this.OutputList.addLast(new CFFFont.UInt16Item(1));
        this.OutputList.addLast(new CFFFont.UInt16Item((char) (i - 1)));
    }

    /* access modifiers changed from: protected */
    public void CreateFDArray(CFFFont.OffsetItem offsetItem, CFFFont.OffsetItem offsetItem2, int i) {
        this.OutputList.addLast(new CFFFont.MarkerItem(offsetItem));
        BuildIndexHeader(1, 1, 1);
        CFFFont.IndexOffsetItem indexOffsetItem = new CFFFont.IndexOffsetItem(1);
        this.OutputList.addLast(indexOffsetItem);
        CFFFont.IndexBaseItem indexBaseItem = new CFFFont.IndexBaseItem();
        this.OutputList.addLast(indexBaseItem);
        int i2 = this.fonts[i].privateLength;
        int CalcSubrOffsetSize = CalcSubrOffsetSize(this.fonts[i].privateOffset, this.fonts[i].privateLength);
        if (CalcSubrOffsetSize != 0) {
            i2 += 5 - CalcSubrOffsetSize;
        }
        this.OutputList.addLast(new CFFFont.DictNumberItem(i2));
        this.OutputList.addLast(offsetItem2);
        this.OutputList.addLast(new CFFFont.UInt8Item(18));
        this.OutputList.addLast(new CFFFont.IndexMarkerItem(indexOffsetItem, indexBaseItem));
    }

    /* access modifiers changed from: package-private */
    public void Reconstruct(int i) {
        CFFFont.DictOffsetItem[] dictOffsetItemArr = new CFFFont.DictOffsetItem[(this.fonts[i].FDArrayOffsets.length - 1)];
        CFFFont.IndexBaseItem[] indexBaseItemArr = new CFFFont.IndexBaseItem[this.fonts[i].fdprivateOffsets.length];
        CFFFont.DictOffsetItem[] dictOffsetItemArr2 = new CFFFont.DictOffsetItem[this.fonts[i].fdprivateOffsets.length];
        ReconstructFDArray(i, dictOffsetItemArr);
        ReconstructPrivateDict(i, dictOffsetItemArr, indexBaseItemArr, dictOffsetItemArr2);
        ReconstructPrivateSubrs(i, indexBaseItemArr, dictOffsetItemArr2);
    }

    /* access modifiers changed from: package-private */
    public void ReconstructFDArray(int i, CFFFont.OffsetItem[] offsetItemArr) {
        int i2;
        BuildIndexHeader(this.fonts[i].FDArrayCount, this.fonts[i].FDArrayOffsize, 1);
        CFFFont.IndexOffsetItem[] indexOffsetItemArr = new CFFFont.IndexOffsetItem[(this.fonts[i].FDArrayOffsets.length - 1)];
        for (int i3 = 0; i3 < this.fonts[i].FDArrayOffsets.length - 1; i3++) {
            indexOffsetItemArr[i3] = new CFFFont.IndexOffsetItem(this.fonts[i].FDArrayOffsize);
            this.OutputList.addLast(indexOffsetItemArr[i3]);
        }
        CFFFont.IndexBaseItem indexBaseItem = new CFFFont.IndexBaseItem();
        this.OutputList.addLast(indexBaseItem);
        int i4 = 0;
        while (i4 < this.fonts[i].FDArrayOffsets.length - 1) {
            seek(this.fonts[i].FDArrayOffsets[i4]);
            while (true) {
                i2 = i4 + 1;
                if (getPosition() >= this.fonts[i].FDArrayOffsets[i2]) {
                    break;
                }
                int position = getPosition();
                getDictItem();
                int position2 = getPosition();
                if (this.key == "Private") {
                    int intValue = ((Integer) this.args[0]).intValue();
                    int CalcSubrOffsetSize = CalcSubrOffsetSize(this.fonts[i].fdprivateOffsets[i4], this.fonts[i].fdprivateLengths[i4]);
                    if (CalcSubrOffsetSize != 0) {
                        intValue += 5 - CalcSubrOffsetSize;
                    }
                    this.OutputList.addLast(new CFFFont.DictNumberItem(intValue));
                    offsetItemArr[i4] = new CFFFont.DictOffsetItem();
                    this.OutputList.addLast(offsetItemArr[i4]);
                    this.OutputList.addLast(new CFFFont.UInt8Item(18));
                    seek(position2);
                } else {
                    this.OutputList.addLast(new CFFFont.RangeItem(this.buf, position, position2 - position));
                }
            }
            this.OutputList.addLast(new CFFFont.IndexMarkerItem(indexOffsetItemArr[i4], indexBaseItem));
            i4 = i2;
        }
    }

    /* access modifiers changed from: package-private */
    public void ReconstructPrivateDict(int i, CFFFont.OffsetItem[] offsetItemArr, CFFFont.IndexBaseItem[] indexBaseItemArr, CFFFont.OffsetItem[] offsetItemArr2) {
        for (int i2 = 0; i2 < this.fonts[i].fdprivateOffsets.length; i2++) {
            this.OutputList.addLast(new CFFFont.MarkerItem(offsetItemArr[i2]));
            indexBaseItemArr[i2] = new CFFFont.IndexBaseItem();
            this.OutputList.addLast(indexBaseItemArr[i2]);
            seek(this.fonts[i].fdprivateOffsets[i2]);
            while (getPosition() < this.fonts[i].fdprivateOffsets[i2] + this.fonts[i].fdprivateLengths[i2]) {
                int position = getPosition();
                getDictItem();
                int position2 = getPosition();
                if (this.key == "Subrs") {
                    offsetItemArr2[i2] = new CFFFont.DictOffsetItem();
                    this.OutputList.addLast(offsetItemArr2[i2]);
                    this.OutputList.addLast(new CFFFont.UInt8Item(19));
                } else {
                    this.OutputList.addLast(new CFFFont.RangeItem(this.buf, position, position2 - position));
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void ReconstructPrivateSubrs(int i, CFFFont.IndexBaseItem[] indexBaseItemArr, CFFFont.OffsetItem[] offsetItemArr) {
        for (int i2 = 0; i2 < this.fonts[i].fdprivateLengths.length; i2++) {
            if (offsetItemArr[i2] != null && this.fonts[i].PrivateSubrsOffset[i2] >= 0) {
                this.OutputList.addLast(new CFFFont.SubrMarkerItem(offsetItemArr[i2], indexBaseItemArr[i2]));
                byte[][] bArr = this.NewLSubrsIndex;
                if (bArr[i2] != null) {
                    this.OutputList.addLast(new CFFFont.RangeItem(new RandomAccessFileOrArray(bArr[i2]), 0, this.NewLSubrsIndex[i2].length));
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int CalcSubrOffsetSize(int i, int i2) {
        seek(i);
        int i3 = 0;
        while (getPosition() < i + i2) {
            int position = getPosition();
            getDictItem();
            int position2 = getPosition();
            if (this.key == "Subrs") {
                i3 = (position2 - position) - 1;
            }
        }
        return i3;
    }

    /* access modifiers changed from: protected */
    public int countEntireIndexRange(int i) {
        seek(i);
        char card16 = getCard16();
        if (card16 == 0) {
            return 2;
        }
        char card8 = getCard8();
        seek(i + 2 + 1 + (card16 * card8));
        return ((card16 + 1) * card8) + 3 + (getOffset(card8) - 1);
    }

    /* access modifiers changed from: package-private */
    public void CreateNonCIDPrivate(int i, CFFFont.OffsetItem offsetItem) {
        seek(this.fonts[i].privateOffset);
        while (getPosition() < this.fonts[i].privateOffset + this.fonts[i].privateLength) {
            int position = getPosition();
            getDictItem();
            int position2 = getPosition();
            if (this.key == "Subrs") {
                this.OutputList.addLast(offsetItem);
                this.OutputList.addLast(new CFFFont.UInt8Item(19));
            } else {
                this.OutputList.addLast(new CFFFont.RangeItem(this.buf, position, position2 - position));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void CreateNonCIDSubrs(int i, CFFFont.IndexBaseItem indexBaseItem, CFFFont.OffsetItem offsetItem) {
        this.OutputList.addLast(new CFFFont.SubrMarkerItem(offsetItem, indexBaseItem));
        byte[] bArr = this.NewSubrsIndexNonCID;
        if (bArr != null) {
            this.OutputList.addLast(new CFFFont.RangeItem(new RandomAccessFileOrArray(bArr), 0, this.NewSubrsIndexNonCID.length));
        }
    }
}
