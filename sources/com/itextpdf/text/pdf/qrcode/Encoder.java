package com.itextpdf.text.pdf.qrcode;

import com.itextpdf.text.pdf.qrcode.Version;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import kotlin.UByte;

public final class Encoder {
    private static final int[] ALPHANUMERIC_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1};
    static final String DEFAULT_BYTE_MODE_ENCODING = "ISO-8859-1";

    private Encoder() {
    }

    private static int calculateMaskPenalty(ByteMatrix byteMatrix) {
        return MaskUtil.applyMaskPenaltyRule1(byteMatrix) + 0 + MaskUtil.applyMaskPenaltyRule2(byteMatrix) + MaskUtil.applyMaskPenaltyRule3(byteMatrix) + MaskUtil.applyMaskPenaltyRule4(byteMatrix);
    }

    public static void encode(String str, ErrorCorrectionLevel errorCorrectionLevel, QRCode qRCode) throws WriterException {
        encode(str, errorCorrectionLevel, (Map<EncodeHintType, Object>) null, qRCode);
    }

    public static void encode(String str, ErrorCorrectionLevel errorCorrectionLevel, Map<EncodeHintType, Object> map, QRCode qRCode) throws WriterException {
        CharacterSetECI characterSetECIByName;
        String str2 = map == null ? null : (String) map.get(EncodeHintType.CHARACTER_SET);
        if (str2 == null) {
            str2 = DEFAULT_BYTE_MODE_ENCODING;
        }
        Mode chooseMode = chooseMode(str, str2);
        BitVector bitVector = new BitVector();
        appendBytes(str, chooseMode, bitVector, str2);
        initQRCode(bitVector.sizeInBytes(), errorCorrectionLevel, chooseMode, qRCode);
        BitVector bitVector2 = new BitVector();
        if (chooseMode == Mode.BYTE && !DEFAULT_BYTE_MODE_ENCODING.equals(str2) && (characterSetECIByName = CharacterSetECI.getCharacterSetECIByName(str2)) != null) {
            appendECI(characterSetECIByName, bitVector2);
        }
        appendModeInfo(chooseMode, bitVector2);
        appendLengthInfo(chooseMode.equals(Mode.BYTE) ? bitVector.sizeInBytes() : str.length(), qRCode.getVersion(), chooseMode, bitVector2);
        bitVector2.appendBitVector(bitVector);
        terminateBits(qRCode.getNumDataBytes(), bitVector2);
        BitVector bitVector3 = new BitVector();
        interleaveWithECBytes(bitVector2, qRCode.getNumTotalBytes(), qRCode.getNumDataBytes(), qRCode.getNumRSBlocks(), bitVector3);
        ByteMatrix byteMatrix = new ByteMatrix(qRCode.getMatrixWidth(), qRCode.getMatrixWidth());
        qRCode.setMaskPattern(chooseMaskPattern(bitVector3, qRCode.getECLevel(), qRCode.getVersion(), byteMatrix));
        MatrixUtil.buildMatrix(bitVector3, qRCode.getECLevel(), qRCode.getVersion(), qRCode.getMaskPattern(), byteMatrix);
        qRCode.setMatrix(byteMatrix);
        if (!qRCode.isValid()) {
            throw new WriterException("Invalid QR code: " + qRCode.toString());
        }
    }

    static int getAlphanumericCode(int i) {
        int[] iArr = ALPHANUMERIC_TABLE;
        if (i < iArr.length) {
            return iArr[i];
        }
        return -1;
    }

    public static Mode chooseMode(String str) {
        return chooseMode(str, (String) null);
    }

    public static Mode chooseMode(String str, String str2) {
        if ("Shift_JIS".equals(str2)) {
            return isOnlyDoubleByteKanji(str) ? Mode.KANJI : Mode.BYTE;
        }
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt >= '0' && charAt <= '9') {
                z2 = true;
            } else if (getAlphanumericCode(charAt) == -1) {
                return Mode.BYTE;
            } else {
                z = true;
            }
        }
        if (z) {
            return Mode.ALPHANUMERIC;
        }
        if (z2) {
            return Mode.NUMERIC;
        }
        return Mode.BYTE;
    }

    private static boolean isOnlyDoubleByteKanji(String str) {
        try {
            byte[] bytes = str.getBytes("Shift_JIS");
            int length = bytes.length;
            if (length % 2 != 0) {
                return false;
            }
            for (int i = 0; i < length; i += 2) {
                byte b = bytes[i] & UByte.MAX_VALUE;
                if ((b < 129 || b > 159) && (b < 224 || b > 235)) {
                    return false;
                }
            }
            return true;
        } catch (UnsupportedEncodingException unused) {
            return false;
        }
    }

    private static int chooseMaskPattern(BitVector bitVector, ErrorCorrectionLevel errorCorrectionLevel, int i, ByteMatrix byteMatrix) throws WriterException {
        int i2 = Integer.MAX_VALUE;
        int i3 = -1;
        for (int i4 = 0; i4 < 8; i4++) {
            MatrixUtil.buildMatrix(bitVector, errorCorrectionLevel, i, i4, byteMatrix);
            int calculateMaskPenalty = calculateMaskPenalty(byteMatrix);
            if (calculateMaskPenalty < i2) {
                i3 = i4;
                i2 = calculateMaskPenalty;
            }
        }
        return i3;
    }

    private static void initQRCode(int i, ErrorCorrectionLevel errorCorrectionLevel, Mode mode, QRCode qRCode) throws WriterException {
        qRCode.setECLevel(errorCorrectionLevel);
        qRCode.setMode(mode);
        for (int i2 = 1; i2 <= 40; i2++) {
            Version versionForNumber = Version.getVersionForNumber(i2);
            int totalCodewords = versionForNumber.getTotalCodewords();
            Version.ECBlocks eCBlocksForLevel = versionForNumber.getECBlocksForLevel(errorCorrectionLevel);
            int totalECCodewords = eCBlocksForLevel.getTotalECCodewords();
            int numBlocks = eCBlocksForLevel.getNumBlocks();
            int i3 = totalCodewords - totalECCodewords;
            if (i3 >= i + 3) {
                qRCode.setVersion(i2);
                qRCode.setNumTotalBytes(totalCodewords);
                qRCode.setNumDataBytes(i3);
                qRCode.setNumRSBlocks(numBlocks);
                qRCode.setNumECBytes(totalECCodewords);
                qRCode.setMatrixWidth(versionForNumber.getDimensionForVersion());
                return;
            }
        }
        throw new WriterException("Cannot find proper rs block info (input data too big?)");
    }

    static void terminateBits(int i, BitVector bitVector) throws WriterException {
        int i2 = i << 3;
        if (bitVector.size() <= i2) {
            for (int i3 = 0; i3 < 4 && bitVector.size() < i2; i3++) {
                bitVector.appendBit(0);
            }
            int size = bitVector.size() % 8;
            if (size > 0) {
                int i4 = 8 - size;
                for (int i5 = 0; i5 < i4; i5++) {
                    bitVector.appendBit(0);
                }
            }
            if (bitVector.size() % 8 == 0) {
                int sizeInBytes = i - bitVector.sizeInBytes();
                for (int i6 = 0; i6 < sizeInBytes; i6++) {
                    if (i6 % 2 == 0) {
                        bitVector.appendBits(236, 8);
                    } else {
                        bitVector.appendBits(17, 8);
                    }
                }
                if (bitVector.size() != i2) {
                    throw new WriterException("Bits size does not equal capacity");
                }
                return;
            }
            throw new WriterException("Number of bits is not a multiple of 8");
        }
        throw new WriterException("data bits cannot fit in the QR Code" + bitVector.size() + " > " + i2);
    }

    static void getNumDataBytesAndNumECBytesForBlockID(int i, int i2, int i3, int i4, int[] iArr, int[] iArr2) throws WriterException {
        if (i4 < i3) {
            int i5 = i % i3;
            int i6 = i3 - i5;
            int i7 = i / i3;
            int i8 = i7 + 1;
            int i9 = i2 / i3;
            int i10 = i9 + 1;
            int i11 = i7 - i9;
            int i12 = i8 - i10;
            if (i11 != i12) {
                throw new WriterException("EC bytes mismatch");
            } else if (i3 != i6 + i5) {
                throw new WriterException("RS blocks mismatch");
            } else if (i != ((i9 + i11) * i6) + ((i10 + i12) * i5)) {
                throw new WriterException("Total bytes mismatch");
            } else if (i4 < i6) {
                iArr[0] = i9;
                iArr2[0] = i11;
            } else {
                iArr[0] = i10;
                iArr2[0] = i12;
            }
        } else {
            throw new WriterException("Block ID too large");
        }
    }

    static void interleaveWithECBytes(BitVector bitVector, int i, int i2, int i3, BitVector bitVector2) throws WriterException {
        int i4 = i;
        int i5 = i2;
        int i6 = i3;
        BitVector bitVector3 = bitVector2;
        if (bitVector.sizeInBytes() == i5) {
            ArrayList arrayList = new ArrayList(i6);
            int i7 = 0;
            int i8 = 0;
            int i9 = 0;
            for (int i10 = 0; i10 < i6; i10++) {
                int[] iArr = new int[1];
                int[] iArr2 = new int[1];
                int[] iArr3 = iArr;
                getNumDataBytesAndNumECBytesForBlockID(i, i2, i3, i10, iArr, iArr2);
                ByteArray byteArray = new ByteArray();
                byteArray.set(bitVector.getArray(), i7, iArr3[0]);
                ByteArray generateECBytes = generateECBytes(byteArray, iArr2[0]);
                arrayList.add(new BlockPair(byteArray, generateECBytes));
                i8 = Math.max(i8, byteArray.size());
                i9 = Math.max(i9, generateECBytes.size());
                i7 += iArr3[0];
            }
            if (i5 == i7) {
                for (int i11 = 0; i11 < i8; i11++) {
                    for (int i12 = 0; i12 < arrayList.size(); i12++) {
                        ByteArray dataBytes = ((BlockPair) arrayList.get(i12)).getDataBytes();
                        if (i11 < dataBytes.size()) {
                            bitVector3.appendBits(dataBytes.mo23880at(i11), 8);
                        }
                    }
                }
                for (int i13 = 0; i13 < i9; i13++) {
                    for (int i14 = 0; i14 < arrayList.size(); i14++) {
                        ByteArray errorCorrectionBytes = ((BlockPair) arrayList.get(i14)).getErrorCorrectionBytes();
                        if (i13 < errorCorrectionBytes.size()) {
                            bitVector3.appendBits(errorCorrectionBytes.mo23880at(i13), 8);
                        }
                    }
                }
                if (i4 != bitVector2.sizeInBytes()) {
                    throw new WriterException("Interleaving error: " + i4 + " and " + bitVector2.sizeInBytes() + " differ.");
                }
                return;
            }
            throw new WriterException("Data bytes does not match offset");
        }
        throw new WriterException("Number of bits and data bytes does not match");
    }

    static ByteArray generateECBytes(ByteArray byteArray, int i) {
        int size = byteArray.size();
        int[] iArr = new int[(size + i)];
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = byteArray.mo23880at(i2);
        }
        new ReedSolomonEncoder(GF256.QR_CODE_FIELD).encode(iArr, i);
        ByteArray byteArray2 = new ByteArray(i);
        for (int i3 = 0; i3 < i; i3++) {
            byteArray2.set(i3, iArr[size + i3]);
        }
        return byteArray2;
    }

    static void appendModeInfo(Mode mode, BitVector bitVector) {
        bitVector.appendBits(mode.getBits(), 4);
    }

    static void appendLengthInfo(int i, int i2, Mode mode, BitVector bitVector) throws WriterException {
        int characterCountBits = mode.getCharacterCountBits(Version.getVersionForNumber(i2));
        int i3 = (1 << characterCountBits) - 1;
        if (i <= i3) {
            bitVector.appendBits(i, characterCountBits);
            return;
        }
        throw new WriterException(i + "is bigger than" + i3);
    }

    static void appendBytes(String str, Mode mode, BitVector bitVector, String str2) throws WriterException {
        if (mode.equals(Mode.NUMERIC)) {
            appendNumericBytes(str, bitVector);
        } else if (mode.equals(Mode.ALPHANUMERIC)) {
            appendAlphanumericBytes(str, bitVector);
        } else if (mode.equals(Mode.BYTE)) {
            append8BitBytes(str, bitVector, str2);
        } else if (mode.equals(Mode.KANJI)) {
            appendKanjiBytes(str, bitVector);
        } else {
            throw new WriterException("Invalid mode: " + mode);
        }
    }

    static void appendNumericBytes(String str, BitVector bitVector) {
        int length = str.length();
        int i = 0;
        while (i < length) {
            int charAt = str.charAt(i) - '0';
            int i2 = i + 2;
            if (i2 < length) {
                bitVector.appendBits((charAt * 100) + ((str.charAt(i + 1) - '0') * 10) + (str.charAt(i2) - '0'), 10);
                i += 3;
            } else {
                i++;
                if (i < length) {
                    bitVector.appendBits((charAt * 10) + (str.charAt(i) - '0'), 7);
                    i = i2;
                } else {
                    bitVector.appendBits(charAt, 4);
                }
            }
        }
    }

    static void appendAlphanumericBytes(String str, BitVector bitVector) throws WriterException {
        int length = str.length();
        int i = 0;
        while (i < length) {
            int alphanumericCode = getAlphanumericCode(str.charAt(i));
            if (alphanumericCode != -1) {
                int i2 = i + 1;
                if (i2 < length) {
                    int alphanumericCode2 = getAlphanumericCode(str.charAt(i2));
                    if (alphanumericCode2 != -1) {
                        bitVector.appendBits((alphanumericCode * 45) + alphanumericCode2, 11);
                        i += 2;
                    } else {
                        throw new WriterException();
                    }
                } else {
                    bitVector.appendBits(alphanumericCode, 6);
                    i = i2;
                }
            } else {
                throw new WriterException();
            }
        }
    }

    static void append8BitBytes(String str, BitVector bitVector, String str2) throws WriterException {
        try {
            byte[] bytes = str.getBytes(str2);
            for (byte appendBits : bytes) {
                bitVector.appendBits(appendBits, 8);
            }
        } catch (UnsupportedEncodingException e) {
            throw new WriterException(e.toString());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0035 A[LOOP:0: B:4:0x0008->B:17:0x0035, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0044 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void appendKanjiBytes(java.lang.String r6, com.itextpdf.text.pdf.qrcode.BitVector r7) throws com.itextpdf.text.pdf.qrcode.WriterException {
        /*
            java.lang.String r0 = "Shift_JIS"
            byte[] r6 = r6.getBytes(r0)     // Catch:{ UnsupportedEncodingException -> 0x004d }
            int r0 = r6.length
            r1 = 0
        L_0x0008:
            if (r1 >= r0) goto L_0x004c
            byte r2 = r6[r1]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r3 = r1 + 1
            byte r3 = r6[r3]
            r3 = r3 & 255(0xff, float:3.57E-43)
            int r2 = r2 << 8
            r2 = r2 | r3
            r3 = 33088(0x8140, float:4.6366E-41)
            r4 = -1
            if (r2 < r3) goto L_0x0024
            r5 = 40956(0x9ffc, float:5.7392E-41)
            if (r2 > r5) goto L_0x0024
        L_0x0022:
            int r2 = r2 - r3
            goto L_0x0033
        L_0x0024:
            r3 = 57408(0xe040, float:8.0446E-41)
            if (r2 < r3) goto L_0x0032
            r3 = 60351(0xebbf, float:8.457E-41)
            if (r2 > r3) goto L_0x0032
            r3 = 49472(0xc140, float:6.9325E-41)
            goto L_0x0022
        L_0x0032:
            r2 = -1
        L_0x0033:
            if (r2 == r4) goto L_0x0044
            int r3 = r2 >> 8
            int r3 = r3 * 192
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r3 = r3 + r2
            r2 = 13
            r7.appendBits(r3, r2)
            int r1 = r1 + 2
            goto L_0x0008
        L_0x0044:
            com.itextpdf.text.pdf.qrcode.WriterException r6 = new com.itextpdf.text.pdf.qrcode.WriterException
            java.lang.String r7 = "Invalid byte sequence"
            r6.<init>(r7)
            throw r6
        L_0x004c:
            return
        L_0x004d:
            r6 = move-exception
            com.itextpdf.text.pdf.qrcode.WriterException r7 = new com.itextpdf.text.pdf.qrcode.WriterException
            java.lang.String r6 = r6.toString()
            r7.<init>(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.qrcode.Encoder.appendKanjiBytes(java.lang.String, com.itextpdf.text.pdf.qrcode.BitVector):void");
    }

    private static void appendECI(CharacterSetECI characterSetECI, BitVector bitVector) {
        bitVector.appendBits(Mode.ECI.getBits(), 4);
        bitVector.appendBits(characterSetECI.getValue(), 8);
    }
}
