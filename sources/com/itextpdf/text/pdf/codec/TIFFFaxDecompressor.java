package com.itextpdf.text.pdf.codec;

import com.itextpdf.text.DocWriter;
import com.itextpdf.text.pdf.BidiOrder;
import com.itextpdf.text.pdf.ByteBuffer;
import com.p020kl.vision_ecg.SmctConstant;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

public class TIFFFaxDecompressor {
    static short[] additionalMakeup = {28679, 28679, 31752, -32759, -31735, -30711, -29687, -28663, 29703, 29703, 30727, 30727, -27639, -26615, -25591, -24567};
    static short[] black = {62, 62, 30, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 3225, 588, 588, 588, 588, 588, 588, 588, 588, 1680, 1680, 20499, 22547, 24595, 26643, 1776, 1776, 1808, 1808, -24557, -22509, -20461, -18413, 1904, 1904, 1936, 1936, -16365, -14317, 782, 782, 782, 782, 814, 814, 814, 814, -12269, -10221, 10257, 10257, 12305, 12305, 14353, 14353, 16403, 18451, 1712, 1712, 1744, 1744, 28691, 30739, -32749, -30701, -28653, -26605, 2061, 2061, 2061, 2061, 2061, 2061, 2061, 2061, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 424, 750, 750, 750, 750, 1616, 1616, 1648, 1648, 1424, 1424, 1456, 1456, 1488, 1488, 1520, 1520, 1840, 1840, 1872, 1872, 1968, 1968, 8209, 8209, 524, 524, 524, 524, 524, 524, 524, 524, 556, 556, 556, 556, 556, 556, 556, 556, 1552, 1552, 1584, 1584, 2000, 2000, 2032, 2032, 976, 976, 1008, 1008, 1040, 1040, 1072, 1072, 1296, 1296, 1328, 1328, 718, 718, 718, 718, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 456, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 326, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 358, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 490, 4113, 4113, 6161, 6161, 848, 848, 880, 880, 912, 912, 944, 944, 622, 622, 622, 622, 654, 654, 654, 654, 1104, 1104, 1136, 1136, 1168, 1168, 1200, 1200, 1232, 1232, 1264, 1264, 686, 686, 686, 686, 1360, 1360, 1392, 1392, 12, 12, 12, 12, 12, 12, 12, 12, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390, 390};
    static byte[] flipTable = {0, ByteCompanionObject.MIN_VALUE, 64, -64, DocWriter.SPACE, -96, 96, -32, BidiOrder.f527S, -112, 80, -48, ByteBuffer.ZERO, -80, 112, -16, 8, -120, 72, -56, 40, -88, 104, -24, 24, -104, 88, -40, 56, -72, 120, -8, 4, -124, 68, -60, 36, -92, 100, -28, 20, -108, 84, -44, 52, -76, 116, -12, BidiOrder.f520CS, -116, 76, -52, 44, -84, 108, -20, 28, -100, 92, -36, DocWriter.f438LT, -68, 124, -4, 2, -126, 66, -62, DocWriter.QUOTE, -94, 98, -30, 18, -110, 82, -46, 50, -78, 114, -14, 10, -118, 74, -54, 42, -86, 106, -22, 26, -102, 90, -38, 58, -70, 122, -6, 6, -122, 70, -58, 38, -90, 102, -26, 22, -106, 86, -42, 54, -74, 118, -10, BidiOrder.f519BN, -114, 78, -50, 46, -82, 110, -18, 30, -98, 94, -34, DocWriter.f437GT, -66, 126, -2, 1, -127, 65, -63, 33, -95, 97, -31, BidiOrder.f528WS, -111, 81, -47, 49, -79, 113, -15, 9, -119, 73, -55, 41, -87, 105, -23, 25, -103, 89, -39, 57, -71, 121, -7, 5, -123, 69, -59, 37, -91, 101, -27, 21, -107, 85, -43, 53, -75, 117, -11, BidiOrder.NSM, -115, 77, -51, 45, -83, 109, -19, 29, -99, 93, -35, DocWriter.EQUALS, -67, 125, -3, 3, -125, 67, -61, 35, -93, 99, -29, 19, -109, 83, -45, 51, -77, 115, -13, BidiOrder.f517AN, -117, 75, -53, 43, -85, 107, -21, 27, -101, 91, -37, 59, -69, 123, -5, 7, -121, 71, -57, 39, -89, 103, -25, 23, -105, 87, -41, 55, -73, 119, -9, BidiOrder.f518B, -113, 79, -49, DocWriter.FORWARD, -81, 111, -17, 31, -97, 95, -33, 63, -65, ByteCompanionObject.MAX_VALUE, -1};
    static short[] initBlack = {3226, 6412, 200, 168, 38, 38, 134, 134, 100, 100, 100, 100, 68, 68, 68, 68};
    static int[] table1 = {0, 1, 3, 7, 15, 31, 63, 127, 255};
    static int[] table2 = {0, 128, 192, 224, 240, 248, 252, 254, 255};
    static short[] twoBitBlack = {292, 260, 226, 226};
    static byte[] twoDCodes = {80, 88, 23, 71, 30, 30, DocWriter.f437GT, DocWriter.f437GT, 4, 4, 4, 4, 4, 4, 4, 4, BidiOrder.f517AN, BidiOrder.f517AN, BidiOrder.f517AN, BidiOrder.f517AN, BidiOrder.f517AN, BidiOrder.f517AN, BidiOrder.f517AN, BidiOrder.f517AN, BidiOrder.f517AN, BidiOrder.f517AN, BidiOrder.f517AN, BidiOrder.f517AN, BidiOrder.f517AN, BidiOrder.f517AN, BidiOrder.f517AN, BidiOrder.f517AN, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 35, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41};
    static short[] white = {6430, 6400, 6400, 6400, 3225, 3225, 3225, 3225, 944, 944, 944, 944, 976, 976, 976, 976, 1456, 1456, 1456, 1456, 1488, 1488, 1488, 1488, 718, 718, 718, 718, 718, 718, 718, 718, 750, 750, 750, 750, 750, 750, 750, 750, 1520, 1520, 1520, 1520, 1552, 1552, 1552, 1552, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 428, 654, 654, 654, 654, 654, 654, 654, 654, 1072, 1072, 1072, 1072, 1104, 1104, 1104, 1104, 1136, 1136, 1136, 1136, 1168, 1168, 1168, 1168, 1200, 1200, 1200, 1200, 1232, 1232, 1232, 1232, 622, 622, 622, 622, 622, 622, 622, 622, 1008, 1008, 1008, 1008, 1040, 1040, 1040, 1040, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 44, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 396, 1712, 1712, 1712, 1712, 1744, 1744, 1744, 1744, 846, 846, 846, 846, 846, 846, 846, 846, 1264, 1264, 1264, 1264, 1296, 1296, 1296, 1296, 1328, 1328, 1328, 1328, 1360, 1360, 1360, 1360, 1392, 1392, 1392, 1392, 1424, 1424, 1424, 1424, 686, 686, 686, 686, 686, 686, 686, 686, 910, 910, 910, 910, 910, 910, 910, 910, 1968, 1968, 1968, 1968, 2000, 2000, 2000, 2000, 2032, 2032, 2032, 2032, 16, 16, 16, 16, 10257, 10257, 10257, 10257, 12305, 12305, 12305, 12305, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 330, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 362, 878, 878, 878, 878, 878, 878, 878, 878, 1904, 1904, 1904, 1904, 1936, 1936, 1936, 1936, -18413, -18413, -16365, -16365, -14317, -14317, -10221, -10221, 590, 590, 590, 590, 590, 590, 590, 590, 782, 782, 782, 782, 782, 782, 782, 782, 1584, 1584, 1584, 1584, 1616, 1616, 1616, 1616, 1648, 1648, 1648, 1648, 1680, 1680, 1680, 1680, 814, 814, 814, 814, 814, 814, 814, 814, 1776, 1776, 1776, 1776, 1808, 1808, 1808, 1808, 1840, 1840, 1840, 1840, 1872, 1872, 1872, 1872, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, 6157, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, -12275, 14353, 14353, 14353, 14353, 16401, 16401, 16401, 16401, 22547, 22547, 24595, 24595, 20497, 20497, 20497, 20497, 18449, 18449, 18449, 18449, 26643, 26643, 28691, 28691, 30739, 30739, -32749, -32749, -30701, -30701, -28653, -28653, -26605, -26605, -24557, -24557, -22509, -22509, -20461, -20461, 8207, 8207, 8207, 8207, 8207, 8207, 8207, 8207, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, 72, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, SmctConstant.KEY_ALGO_RHYTHM, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 4107, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 266, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 298, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 524, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 556, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 136, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 168, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 460, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 492, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 2059, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232, 232};
    private int bitPointer;
    private int bitsPerScanline;
    private byte[] buffer;
    private int bytePointer;
    private int changingElemSize = 0;
    protected int compression;
    private int[] currChangingElems;
    private byte[] data;
    public int fails;
    protected int fillBits = 0;
    protected int fillOrder;

    /* renamed from: h */
    private int f721h;
    private int lastChangingElement = 0;
    private int lineBitNum;
    protected int oneD;
    private int[] prevChangingElems;
    private int t4Options;
    private int t6Options;
    protected int uncompressedMode = 0;

    /* renamed from: w */
    private int f722w;

    public void SetOptions(int i, int i2, int i3, int i4) {
        this.fillOrder = i;
        this.compression = i2;
        this.t4Options = i3;
        this.t6Options = i4;
        this.oneD = i3 & 1;
        this.uncompressedMode = (i3 & 2) >> 1;
        this.fillBits = (i3 & 4) >> 2;
    }

    public void decodeRaw(byte[] bArr, byte[] bArr2, int i, int i2) {
        this.buffer = bArr;
        this.data = bArr2;
        this.f722w = i;
        this.f721h = i2;
        this.bitsPerScanline = i;
        this.lineBitNum = 0;
        this.bitPointer = 0;
        this.bytePointer = 0;
        int i3 = i + 1;
        this.prevChangingElems = new int[i3];
        this.currChangingElems = new int[i3];
        this.fails = 0;
        try {
            int i4 = this.compression;
            if (i4 == 2) {
                decodeRLE();
            } else if (i4 == 3) {
                decodeT4();
            } else if (i4 == 4) {
                this.uncompressedMode = (this.t6Options & 2) >> 1;
                decodeT6();
            } else {
                throw new RuntimeException("Unknown compression type " + this.compression);
            }
        } catch (ArrayIndexOutOfBoundsException unused) {
        }
    }

    public void decodeRLE() {
        for (int i = 0; i < this.f721h; i++) {
            decodeNextScanline();
            if (this.bitPointer != 0) {
                this.bytePointer++;
                this.bitPointer = 0;
            }
            this.lineBitNum += this.bitsPerScanline;
        }
    }

    public void decodeNextScanline() {
        this.changingElemSize = 0;
        int i = 0;
        boolean z = true;
        while (true) {
            if (i >= this.f722w) {
                break;
            }
            int i2 = i;
            while (z && i2 < this.f722w) {
                int nextNBits = nextNBits(10);
                short s = white[nextNBits];
                short s2 = s & 1;
                int i3 = (s >>> 1) & 15;
                if (i3 == 12) {
                    short s3 = additionalMakeup[(12 & (nextNBits << 2)) | nextLesserThan8Bits(2)];
                    i2 += (s3 >>> 4) & 4095;
                    updatePointer(4 - ((s3 >>> 1) & 7));
                } else if (i3 == 0) {
                    this.fails++;
                } else if (i3 == 15) {
                    this.fails++;
                    return;
                } else {
                    i2 += (s >>> 5) & 2047;
                    updatePointer(10 - i3);
                    if (s2 == 0) {
                        int[] iArr = this.currChangingElems;
                        int i4 = this.changingElemSize;
                        this.changingElemSize = i4 + 1;
                        iArr[i4] = i2;
                        z = false;
                    }
                }
            }
            if (i2 == this.f722w) {
                int i5 = i2 - i;
                if (z && i5 != 0 && i5 % 64 == 0 && nextNBits(8) != 53) {
                    this.fails++;
                    updatePointer(8);
                }
                i = i2;
            } else {
                i = i2;
                while (!z && i < this.f722w) {
                    short s4 = initBlack[nextLesserThan8Bits(4)];
                    int i6 = (s4 >>> 1) & 15;
                    int i7 = (s4 >>> 5) & 2047;
                    if (i7 == 100) {
                        short s5 = black[nextNBits(9)];
                        short s6 = s5 & 1;
                        int i8 = (s5 >>> 1) & 15;
                        int i9 = (s5 >>> 5) & 2047;
                        if (i8 == 12) {
                            updatePointer(5);
                            short s7 = additionalMakeup[nextLesserThan8Bits(4)];
                            int i10 = (s7 >>> 4) & 4095;
                            setToBlack(i, i10);
                            i += i10;
                            updatePointer(4 - ((s7 >>> 1) & 7));
                        } else if (i8 == 15) {
                            this.fails++;
                            return;
                        } else {
                            setToBlack(i, i9);
                            i += i9;
                            updatePointer(9 - i8);
                            if (s6 == 0) {
                                int[] iArr2 = this.currChangingElems;
                                int i11 = this.changingElemSize;
                                this.changingElemSize = i11 + 1;
                                iArr2[i11] = i;
                            }
                        }
                    } else if (i7 == 200) {
                        short s8 = twoBitBlack[nextLesserThan8Bits(2)];
                        int i12 = (s8 >>> 5) & 2047;
                        setToBlack(i, i12);
                        i += i12;
                        updatePointer(2 - ((s8 >>> 1) & 15));
                        int[] iArr3 = this.currChangingElems;
                        int i13 = this.changingElemSize;
                        this.changingElemSize = i13 + 1;
                        iArr3[i13] = i;
                    } else {
                        setToBlack(i, i7);
                        i += i7;
                        updatePointer(4 - i6);
                        int[] iArr4 = this.currChangingElems;
                        int i14 = this.changingElemSize;
                        this.changingElemSize = i14 + 1;
                        iArr4[i14] = i;
                    }
                    z = true;
                }
                if (i == this.f722w) {
                    int i15 = i - i2;
                    if (!z && i15 != 0 && i15 % 64 == 0 && nextNBits(10) != 55) {
                        this.fails++;
                        updatePointer(10);
                    }
                }
            }
        }
        int[] iArr5 = this.currChangingElems;
        int i16 = this.changingElemSize;
        this.changingElemSize = i16 + 1;
        iArr5[i16] = i;
    }

    public void decodeT4() {
        int i;
        int i2 = this.f721h;
        int[] iArr = new int[2];
        if (this.data.length >= 2) {
            if (nextNBits(12) != 1) {
                this.fails++;
            }
            updatePointer(12);
            int i3 = 0;
            int i4 = -1;
            while (i3 != 1) {
                try {
                    i3 = findNextLine();
                    i4++;
                } catch (Exception unused) {
                    throw new RuntimeException("No reference line present.");
                }
            }
            decodeNextScanline();
            int i5 = i4 + 1;
            this.lineBitNum += this.bitsPerScanline;
            while (i5 < i2) {
                try {
                    int findNextLine = findNextLine();
                    if (findNextLine == 0) {
                        int[] iArr2 = this.prevChangingElems;
                        this.prevChangingElems = this.currChangingElems;
                        this.currChangingElems = iArr2;
                        this.lastChangingElement = 0;
                        int i6 = 0;
                        int i7 = -1;
                        boolean z = true;
                        int i8 = 0;
                        while (true) {
                            if (i6 >= this.f722w) {
                                break;
                            }
                            getNextChangingElement(i7, z, iArr);
                            int i9 = iArr[0];
                            int i10 = iArr[1];
                            byte b = twoDCodes[nextLesserThan8Bits(7)] & UByte.MAX_VALUE;
                            int i11 = (b & 120) >>> 3;
                            byte b2 = 7 & b;
                            if (i11 == 0) {
                                if (!z) {
                                    setToBlack(i6, i10 - i6);
                                }
                                updatePointer(7 - b2);
                                i6 = i10;
                                i7 = i6;
                            } else if (i11 == 1) {
                                updatePointer(7 - b2);
                                if (z) {
                                    int decodeWhiteCodeWord = i6 + decodeWhiteCodeWord();
                                    int i12 = i8 + 1;
                                    this.currChangingElems[i8] = decodeWhiteCodeWord;
                                    int decodeBlackCodeWord = decodeBlackCodeWord();
                                    setToBlack(decodeWhiteCodeWord, decodeBlackCodeWord);
                                    i = decodeWhiteCodeWord + decodeBlackCodeWord;
                                    i8 = i12 + 1;
                                    this.currChangingElems[i12] = i;
                                } else {
                                    int decodeBlackCodeWord2 = decodeBlackCodeWord();
                                    setToBlack(i6, decodeBlackCodeWord2);
                                    int i13 = i6 + decodeBlackCodeWord2;
                                    int i14 = i8 + 1;
                                    this.currChangingElems[i8] = i13;
                                    i = i13 + decodeWhiteCodeWord();
                                    i8 = i14 + 1;
                                    this.currChangingElems[i14] = i;
                                }
                                i7 = i;
                                i6 = i7;
                            } else if (i11 <= 8) {
                                i7 = i9 + (i11 - 5);
                                int i15 = i8 + 1;
                                this.currChangingElems[i8] = i7;
                                if (!z) {
                                    setToBlack(i6, i7 - i6);
                                }
                                z = !z;
                                updatePointer(7 - b2);
                                i6 = i7;
                                i8 = i15;
                            } else {
                                this.fails++;
                                int i16 = 0;
                                while (findNextLine != 1) {
                                    try {
                                        findNextLine = findNextLine();
                                        i16++;
                                    } catch (Exception unused2) {
                                        return;
                                    }
                                }
                                i5 += i16 - 1;
                                updatePointer(13);
                            }
                        }
                        this.currChangingElems[i8] = i6;
                        this.changingElemSize = i8 + 1;
                    } else {
                        decodeNextScanline();
                    }
                    this.lineBitNum += this.bitsPerScanline;
                    i5++;
                } catch (Exception unused3) {
                    this.fails++;
                    return;
                }
            }
            return;
        }
        throw new RuntimeException("Insufficient data to read initial EOL.");
    }

    public synchronized void decodeT6() {
        int i;
        int i2;
        boolean z;
        boolean z2;
        int i3;
        synchronized (this) {
            int i4 = this.f721h;
            int[] iArr = new int[2];
            int[] iArr2 = this.currChangingElems;
            int i5 = 0;
            this.changingElemSize = 0;
            int i6 = 0 + 1;
            this.changingElemSize = i6;
            int i7 = this.f722w;
            iArr2[0] = i7;
            this.changingElemSize = i6 + 1;
            iArr2[i6] = i7;
            int i8 = 0;
            while (i8 < i4) {
                int i9 = -1;
                int[] iArr3 = this.prevChangingElems;
                this.prevChangingElems = this.currChangingElems;
                this.currChangingElems = iArr3;
                this.lastChangingElement = i5;
                int i10 = 0;
                int i11 = 0;
                boolean z3 = true;
                while (true) {
                    i = this.f722w;
                    if (i10 >= i) {
                        break;
                    }
                    getNextChangingElement(i9, z3, iArr);
                    int i12 = iArr[i5];
                    int i13 = iArr[1];
                    byte b = twoDCodes[nextLesserThan8Bits(7)] & UByte.MAX_VALUE;
                    int i14 = (b & 120) >>> 3;
                    byte b2 = 7 & b;
                    if (i14 == 0) {
                        if (!z3) {
                            int i15 = this.f722w;
                            if (i13 > i15) {
                                i13 = i15;
                            }
                            setToBlack(i10, i13 - i10);
                        }
                        i10 = i13;
                        updatePointer(7 - b2);
                    } else if (i14 == 1) {
                        updatePointer(7 - b2);
                        if (z3) {
                            int decodeWhiteCodeWord = i10 + decodeWhiteCodeWord();
                            int i16 = i11 + 1;
                            iArr3[i11] = decodeWhiteCodeWord;
                            int decodeBlackCodeWord = decodeBlackCodeWord();
                            int i17 = this.f722w;
                            if (decodeBlackCodeWord > i17 - decodeWhiteCodeWord) {
                                decodeBlackCodeWord = i17 - decodeWhiteCodeWord;
                            }
                            setToBlack(decodeWhiteCodeWord, decodeBlackCodeWord);
                            i10 = decodeWhiteCodeWord + decodeBlackCodeWord;
                            i3 = i16 + 1;
                            iArr3[i16] = i10;
                        } else {
                            int decodeBlackCodeWord2 = decodeBlackCodeWord();
                            int i18 = this.f722w;
                            if (decodeBlackCodeWord2 > i18 - i10) {
                                decodeBlackCodeWord2 = i18 - i10;
                            }
                            setToBlack(i10, decodeBlackCodeWord2);
                            int i19 = i10 + decodeBlackCodeWord2;
                            int i20 = i11 + 1;
                            iArr3[i11] = i19;
                            i10 = i19 + decodeWhiteCodeWord();
                            i3 = i20 + 1;
                            iArr3[i20] = i10;
                        }
                        i11 = i3;
                    } else if (i14 <= 8) {
                        int i21 = i12 + (i14 - 5);
                        int i22 = i11 + 1;
                        iArr3[i11] = i21;
                        if (!z3) {
                            int i23 = this.f722w;
                            if (i21 > i23) {
                                i21 = i23;
                            }
                            setToBlack(i10, i21 - i10);
                        }
                        i10 = i21;
                        z3 = !z3;
                        updatePointer(7 - b2);
                        i11 = i22;
                    } else {
                        if (i14 == 11) {
                            nextLesserThan8Bits(3);
                            boolean z4 = false;
                            int i24 = 0;
                            while (!z4) {
                                while (nextLesserThan8Bits(1) != 1) {
                                    i24++;
                                }
                                if (i24 > 5) {
                                    i24 -= 6;
                                    if (!z3 && i24 > 0) {
                                        iArr3[i11] = i10;
                                        i11++;
                                    }
                                    i10 += i24;
                                    if (i24 > 0) {
                                        z3 = true;
                                    }
                                    if (nextLesserThan8Bits(1) == 0) {
                                        if (!z3) {
                                            iArr3[i11] = i10;
                                            i11++;
                                        }
                                        z2 = true;
                                    } else {
                                        if (z3) {
                                            iArr3[i11] = i10;
                                            i11++;
                                        }
                                        z2 = false;
                                    }
                                    z4 = true;
                                }
                                if (i24 == 5) {
                                    if (!z3) {
                                        iArr3[i11] = i10;
                                        i11++;
                                    }
                                    i2 = i10 + i24;
                                    z = true;
                                } else {
                                    int i25 = i10 + i24;
                                    iArr3[i11] = i25;
                                    setToBlack(i25, 1);
                                    i2 = i25 + 1;
                                    i11++;
                                    z = false;
                                }
                            }
                        }
                        i5 = 0;
                    }
                    i9 = i10;
                    i5 = 0;
                }
                if (i11 <= i) {
                    iArr3[i11] = i10;
                    i11++;
                }
                this.changingElemSize = i11;
                this.lineBitNum += this.bitsPerScanline;
                i8++;
                i5 = 0;
            }
        }
    }

    private void setToBlack(int i, int i2) {
        int i3 = i + this.lineBitNum;
        int i4 = i2 + i3;
        int i5 = i3 >> 3;
        int i6 = i3 & 7;
        if (i6 > 0) {
            int i7 = 1 << (7 - i6);
            byte b = this.buffer[i5];
            while (i7 > 0 && i3 < i4) {
                b = (byte) (b | i7);
                i7 >>= 1;
                i3++;
            }
            this.buffer[i5] = b;
        }
        int i8 = i3 >> 3;
        while (i3 < i4 - 7) {
            this.buffer[i8] = -1;
            i3 += 8;
            i8++;
        }
        while (i3 < i4) {
            int i9 = i3 >> 3;
            byte[] bArr = this.buffer;
            bArr[i9] = (byte) (bArr[i9] | (1 << (7 - (i3 & 7))));
            i3++;
        }
    }

    private int decodeWhiteCodeWord() {
        boolean z = true;
        int i = 0;
        while (z) {
            int nextNBits = nextNBits(10);
            short s = white[nextNBits];
            short s2 = s & 1;
            int i2 = (s >>> 1) & 15;
            if (i2 == 12) {
                short s3 = additionalMakeup[((nextNBits << 2) & 12) | nextLesserThan8Bits(2)];
                i += (s3 >>> 4) & 4095;
                updatePointer(4 - ((s3 >>> 1) & 7));
            } else if (i2 == 0) {
                throw new RuntimeException("Error 0");
            } else if (i2 != 15) {
                i += (s >>> 5) & 2047;
                updatePointer(10 - i2);
                if (s2 == 0) {
                    z = false;
                }
            } else {
                throw new RuntimeException("Error 1");
            }
        }
        return i;
    }

    private int decodeBlackCodeWord() {
        boolean z = false;
        int i = 0;
        while (!z) {
            short s = initBlack[nextLesserThan8Bits(4)];
            int i2 = (s >>> 1) & 15;
            int i3 = (s >>> 5) & 2047;
            if (i3 == 100) {
                short s2 = black[nextNBits(9)];
                short s3 = s2 & 1;
                int i4 = (s2 >>> 1) & 15;
                int i5 = (s2 >>> 5) & 2047;
                if (i4 == 12) {
                    updatePointer(5);
                    short s4 = additionalMakeup[nextLesserThan8Bits(4)];
                    i += (s4 >>> 4) & 4095;
                    updatePointer(4 - ((s4 >>> 1) & 7));
                } else if (i4 != 15) {
                    i += i5;
                    updatePointer(9 - i4);
                    if (s3 != 0) {
                    }
                } else {
                    throw new RuntimeException("Error 2");
                }
            } else if (i3 == 200) {
                short s5 = twoBitBlack[nextLesserThan8Bits(2)];
                i += (s5 >>> 5) & 2047;
                updatePointer(2 - ((s5 >>> 1) & 15));
            } else {
                i += i3;
                updatePointer(4 - i2);
            }
            z = true;
        }
        return i;
    }

    private int findNextLine() {
        int length = (this.data.length * 8) - 1;
        int i = length - 12;
        int i2 = (this.bytePointer * 8) + this.bitPointer;
        while (i2 <= i) {
            int nextNBits = nextNBits(12);
            i2 += 12;
            while (nextNBits != 1 && i2 < length) {
                nextNBits = ((nextNBits & 2047) << 1) | (nextLesserThan8Bits(1) & 1);
                i2++;
            }
            if (nextNBits == 1) {
                if (this.oneD != 1) {
                    return 1;
                }
                if (i2 < length) {
                    return nextLesserThan8Bits(1);
                }
            }
        }
        throw new RuntimeException();
    }

    private void getNextChangingElement(int i, boolean z, int[] iArr) {
        int[] iArr2 = this.prevChangingElems;
        int i2 = this.changingElemSize;
        int i3 = this.lastChangingElement;
        int i4 = i3 > 0 ? i3 - 1 : 0;
        int i5 = z ? i4 & -2 : i4 | 1;
        while (true) {
            if (i5 >= i2) {
                break;
            }
            int i6 = iArr2[i5];
            if (i6 > i) {
                this.lastChangingElement = i5;
                iArr[0] = i6;
                break;
            }
            i5 += 2;
        }
        int i7 = i5 + 1;
        if (i7 < i2) {
            iArr[1] = iArr2[i7];
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0081  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int nextNBits(int r13) {
        /*
            r12 = this;
            byte[] r0 = r12.data
            int r1 = r0.length
            r2 = 1
            int r1 = r1 - r2
            int r3 = r12.bytePointer
            int r4 = r12.fillOrder
            r5 = 0
            if (r4 != r2) goto L_0x0024
            byte r4 = r0[r3]
            if (r3 != r1) goto L_0x0013
        L_0x0010:
            r0 = 0
        L_0x0011:
            r1 = 0
            goto L_0x004f
        L_0x0013:
            int r6 = r3 + 1
            if (r6 != r1) goto L_0x001a
            byte r0 = r0[r6]
            goto L_0x0011
        L_0x001a:
            byte r1 = r0[r6]
            int r6 = r3 + 2
            byte r0 = r0[r6]
        L_0x0020:
            r11 = r1
            r1 = r0
            r0 = r11
            goto L_0x004f
        L_0x0024:
            r6 = 2
            if (r4 != r6) goto L_0x008d
            byte[] r4 = flipTable
            byte r6 = r0[r3]
            r6 = r6 & 255(0xff, float:3.57E-43)
            byte r6 = r4[r6]
            if (r3 != r1) goto L_0x0033
            r4 = r6
            goto L_0x0010
        L_0x0033:
            int r7 = r3 + 1
            if (r7 != r1) goto L_0x003f
            byte r0 = r0[r7]
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r0 = r4[r0]
            r4 = r6
            goto L_0x0011
        L_0x003f:
            byte r1 = r0[r7]
            r1 = r1 & 255(0xff, float:3.57E-43)
            byte r1 = r4[r1]
            int r7 = r3 + 2
            byte r0 = r0[r7]
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r0 = r4[r0]
            r4 = r6
            goto L_0x0020
        L_0x004f:
            int r6 = r12.bitPointer
            r7 = 8
            int r6 = 8 - r6
            int r13 = r13 - r6
            if (r13 <= r7) goto L_0x005d
            int r8 = r13 + -8
            r9 = 8
            goto L_0x005f
        L_0x005d:
            r9 = r13
            r8 = 0
        L_0x005f:
            int r3 = r3 + r2
            r12.bytePointer = r3
            int[] r10 = table1
            r6 = r10[r6]
            r4 = r4 & r6
            int r13 = r4 << r13
            int[] r4 = table2
            r6 = r4[r9]
            r0 = r0 & r6
            int r6 = 8 - r9
            int r0 = r0 >>> r6
            if (r8 == 0) goto L_0x0081
            int r0 = r0 << r8
            r4 = r4[r8]
            r1 = r1 & r4
            int r4 = 8 - r8
            int r1 = r1 >>> r4
            r0 = r0 | r1
            int r3 = r3 + r2
            r12.bytePointer = r3
            r12.bitPointer = r8
            goto L_0x008b
        L_0x0081:
            if (r9 != r7) goto L_0x0089
            r12.bitPointer = r5
            int r3 = r3 + r2
            r12.bytePointer = r3
            goto L_0x008b
        L_0x0089:
            r12.bitPointer = r9
        L_0x008b:
            r13 = r13 | r0
            return r13
        L_0x008d:
            java.lang.RuntimeException r13 = new java.lang.RuntimeException
            java.lang.String r0 = "Invalid FillOrder"
            r13.<init>(r0)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.codec.TIFFFaxDecompressor.nextNBits(int):int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x004c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int nextLesserThan8Bits(int r10) {
        /*
            r9 = this;
            byte[] r0 = r9.data
            int r1 = r0.length
            r2 = 1
            int r1 = r1 - r2
            int r3 = r9.bytePointer
            int r4 = r9.fillOrder
            r5 = 0
            if (r4 != r2) goto L_0x0017
            byte r4 = r0[r3]
            if (r3 != r1) goto L_0x0012
        L_0x0010:
            r0 = 0
            goto L_0x002f
        L_0x0012:
            int r1 = r3 + 1
            byte r0 = r0[r1]
            goto L_0x002f
        L_0x0017:
            r6 = 2
            if (r4 != r6) goto L_0x0062
            byte[] r4 = flipTable
            byte r6 = r0[r3]
            r6 = r6 & 255(0xff, float:3.57E-43)
            byte r6 = r4[r6]
            if (r3 != r1) goto L_0x0026
            r4 = r6
            goto L_0x0010
        L_0x0026:
            int r1 = r3 + 1
            byte r0 = r0[r1]
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r0 = r4[r0]
            r4 = r6
        L_0x002f:
            int r1 = r9.bitPointer
            int r6 = 8 - r1
            int r7 = r10 - r6
            int r8 = r6 - r10
            if (r8 < 0) goto L_0x004c
            int[] r0 = table1
            r0 = r0[r6]
            r0 = r0 & r4
            int r0 = r0 >>> r8
            int r1 = r1 + r10
            r9.bitPointer = r1
            r10 = 8
            if (r1 != r10) goto L_0x0061
            r9.bitPointer = r5
            int r3 = r3 + r2
            r9.bytePointer = r3
            goto L_0x0061
        L_0x004c:
            int[] r10 = table1
            r10 = r10[r6]
            r10 = r10 & r4
            int r1 = -r8
            int r10 = r10 << r1
            int[] r1 = table2
            r1 = r1[r7]
            r0 = r0 & r1
            int r1 = 8 - r7
            int r0 = r0 >>> r1
            r0 = r0 | r10
            int r3 = r3 + r2
            r9.bytePointer = r3
            r9.bitPointer = r7
        L_0x0061:
            return r0
        L_0x0062:
            java.lang.RuntimeException r10 = new java.lang.RuntimeException
            java.lang.String r0 = "Invalid FillOrder"
            r10.<init>(r0)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.codec.TIFFFaxDecompressor.nextLesserThan8Bits(int):int");
    }

    private void updatePointer(int i) {
        if (i > 8) {
            this.bytePointer -= i / 8;
            i %= 8;
        }
        int i2 = this.bitPointer - i;
        if (i2 < 0) {
            this.bytePointer--;
            this.bitPointer = i2 + 8;
            return;
        }
        this.bitPointer = i2;
    }
}
