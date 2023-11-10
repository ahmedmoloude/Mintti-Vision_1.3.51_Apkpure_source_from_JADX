package com.itextpdf.text.pdf.codec;

import java.io.IOException;
import java.io.OutputStream;

public class LZWCompressor {
    BitFile bf_;
    int clearCode_;
    int codeSize_;
    int endOfInfo_;
    int limit_;
    LZWStringTable lzss_;
    int numBits_;
    short prefix_;
    boolean tiffFudge_;

    public LZWCompressor(OutputStream outputStream, int i, boolean z) throws IOException {
        this.bf_ = new BitFile(outputStream, !z);
        this.codeSize_ = i;
        this.tiffFudge_ = z;
        int i2 = 1 << i;
        this.clearCode_ = i2;
        this.endOfInfo_ = i2 + 1;
        int i3 = i + 1;
        this.numBits_ = i3;
        int i4 = (1 << i3) - 1;
        this.limit_ = i4;
        if (z) {
            this.limit_ = i4 - 1;
        }
        this.prefix_ = -1;
        LZWStringTable lZWStringTable = new LZWStringTable();
        this.lzss_ = lZWStringTable;
        lZWStringTable.ClearTable(this.codeSize_);
        this.bf_.writeBits(this.clearCode_, this.numBits_);
    }

    public void compress(byte[] bArr, int i, int i2) throws IOException {
        int i3 = i2 + i;
        while (i < i3) {
            byte b = bArr[i];
            short FindCharString = this.lzss_.FindCharString(this.prefix_, b);
            if (FindCharString != -1) {
                this.prefix_ = FindCharString;
            } else {
                this.bf_.writeBits(this.prefix_, this.numBits_);
                if (this.lzss_.AddCharString(this.prefix_, b) > this.limit_) {
                    int i4 = this.numBits_;
                    if (i4 == 12) {
                        this.bf_.writeBits(this.clearCode_, i4);
                        this.lzss_.ClearTable(this.codeSize_);
                        this.numBits_ = this.codeSize_ + 1;
                    } else {
                        this.numBits_ = i4 + 1;
                    }
                    int i5 = (1 << this.numBits_) - 1;
                    this.limit_ = i5;
                    if (this.tiffFudge_) {
                        this.limit_ = i5 - 1;
                    }
                }
                this.prefix_ = (short) (((short) b) & 255);
            }
            i++;
        }
    }

    public void flush() throws IOException {
        short s = this.prefix_;
        if (s != -1) {
            this.bf_.writeBits(s, this.numBits_);
        }
        this.bf_.writeBits(this.endOfInfo_, this.numBits_);
        this.bf_.flush();
    }
}
