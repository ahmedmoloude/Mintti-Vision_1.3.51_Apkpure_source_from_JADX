package com.itextpdf.text.pdf;

import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.error_messages.MessageLocalization;
import java.io.IOException;
import java.io.OutputStream;
import kotlin.UByte;

public class LZWDecoder {
    int[] andTable = {511, 1023, 2047, 4095};
    int bitPointer;
    int bitsToGet = 9;
    int bytePointer;
    byte[] data = null;
    int nextBits = 0;
    int nextData = 0;
    byte[][] stringTable;
    int tableIndex;
    OutputStream uncompData;

    public void decode(byte[] bArr, OutputStream outputStream) {
        if (bArr[0] == 0 && bArr[1] == 1) {
            throw new RuntimeException(MessageLocalization.getComposedMessage("lzw.flavour.not.supported", new Object[0]));
        }
        initializeStringTable();
        this.data = bArr;
        this.uncompData = outputStream;
        this.bytePointer = 0;
        this.bitPointer = 0;
        this.nextData = 0;
        this.nextBits = 0;
        int i = 0;
        while (true) {
            int nextCode = getNextCode();
            if (nextCode == 257) {
                return;
            }
            if (nextCode == 256) {
                initializeStringTable();
                i = getNextCode();
                if (i != 257) {
                    writeString(this.stringTable[i]);
                } else {
                    return;
                }
            } else {
                if (nextCode < this.tableIndex) {
                    byte[] bArr2 = this.stringTable[nextCode];
                    writeString(bArr2);
                    addStringToTable(this.stringTable[i], bArr2[0]);
                } else {
                    byte[] bArr3 = this.stringTable[i];
                    byte[] composeString = composeString(bArr3, bArr3[0]);
                    writeString(composeString);
                    addStringToTable(composeString);
                }
                i = nextCode;
            }
        }
    }

    public void initializeStringTable() {
        this.stringTable = new byte[8192][];
        for (int i = 0; i < 256; i++) {
            byte[][] bArr = this.stringTable;
            bArr[i] = new byte[1];
            bArr[i][0] = (byte) i;
        }
        this.tableIndex = 258;
        this.bitsToGet = 9;
    }

    public void writeString(byte[] bArr) {
        try {
            this.uncompData.write(bArr);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }

    public void addStringToTable(byte[] bArr, byte b) {
        int length = bArr.length;
        byte[] bArr2 = new byte[(length + 1)];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        bArr2[length] = b;
        byte[][] bArr3 = this.stringTable;
        int i = this.tableIndex;
        int i2 = i + 1;
        this.tableIndex = i2;
        bArr3[i] = bArr2;
        if (i2 == 511) {
            this.bitsToGet = 10;
        } else if (i2 == 1023) {
            this.bitsToGet = 11;
        } else if (i2 == 2047) {
            this.bitsToGet = 12;
        }
    }

    public void addStringToTable(byte[] bArr) {
        byte[][] bArr2 = this.stringTable;
        int i = this.tableIndex;
        int i2 = i + 1;
        this.tableIndex = i2;
        bArr2[i] = bArr;
        if (i2 == 511) {
            this.bitsToGet = 10;
        } else if (i2 == 1023) {
            this.bitsToGet = 11;
        } else if (i2 == 2047) {
            this.bitsToGet = 12;
        }
    }

    public byte[] composeString(byte[] bArr, byte b) {
        int length = bArr.length;
        byte[] bArr2 = new byte[(length + 1)];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        bArr2[length] = b;
        return bArr2;
    }

    public int getNextCode() {
        try {
            byte[] bArr = this.data;
            int i = this.bytePointer;
            int i2 = i + 1;
            this.bytePointer = i2;
            byte b = (this.nextData << 8) | (bArr[i] & UByte.MAX_VALUE);
            this.nextData = b;
            int i3 = this.nextBits + 8;
            this.nextBits = i3;
            int i4 = this.bitsToGet;
            if (i3 < i4) {
                this.bytePointer = i2 + 1;
                this.nextData = (b << 8) | (bArr[i2] & UByte.MAX_VALUE);
                this.nextBits = i3 + 8;
            }
            int i5 = this.nextData;
            int i6 = this.nextBits;
            int i7 = (i5 >> (i6 - i4)) & this.andTable[i4 - 9];
            this.nextBits = i6 - i4;
            return i7;
        } catch (ArrayIndexOutOfBoundsException unused) {
            return 257;
        }
    }
}
