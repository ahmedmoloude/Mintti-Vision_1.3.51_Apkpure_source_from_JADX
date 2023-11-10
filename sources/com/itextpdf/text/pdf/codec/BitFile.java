package com.itextpdf.text.pdf.codec;

import java.io.IOException;
import java.io.OutputStream;

public class BitFile {
    int bitsLeft_;
    boolean blocks_ = false;
    byte[] buffer_;
    int index_;
    OutputStream output_;

    public BitFile(OutputStream outputStream, boolean z) {
        this.output_ = outputStream;
        this.blocks_ = z;
        this.buffer_ = new byte[256];
        this.index_ = 0;
        this.bitsLeft_ = 8;
    }

    public void flush() throws IOException {
        int i = this.index_ + (this.bitsLeft_ == 8 ? 0 : 1);
        if (i > 0) {
            if (this.blocks_) {
                this.output_.write(i);
            }
            this.output_.write(this.buffer_, 0, i);
            this.buffer_[0] = 0;
            this.index_ = 0;
            this.bitsLeft_ = 8;
        }
    }

    public void writeBits(int i, int i2) throws IOException {
        do {
            int i3 = this.index_;
            if ((i3 == 254 && this.bitsLeft_ == 0) || i3 > 254) {
                if (this.blocks_) {
                    this.output_.write(255);
                }
                this.output_.write(this.buffer_, 0, 255);
                this.buffer_[0] = 0;
                this.index_ = 0;
                this.bitsLeft_ = 8;
            }
            int i4 = this.bitsLeft_;
            if (i2 <= i4) {
                if (this.blocks_) {
                    byte[] bArr = this.buffer_;
                    int i5 = this.index_;
                    bArr[i5] = (byte) (((i & ((1 << i2) - 1)) << (8 - i4)) | bArr[i5]);
                    this.bitsLeft_ = i4 - i2;
                } else {
                    byte[] bArr2 = this.buffer_;
                    int i6 = this.index_;
                    bArr2[i6] = (byte) (((i & ((1 << i2) - 1)) << (i4 - i2)) | bArr2[i6]);
                    this.bitsLeft_ = i4 - i2;
                }
                i2 = 0;
                continue;
            } else if (this.blocks_) {
                byte[] bArr3 = this.buffer_;
                int i7 = this.index_;
                bArr3[i7] = (byte) (bArr3[i7] | ((((1 << i4) - 1) & i) << (8 - i4)));
                i >>= i4;
                i2 -= i4;
                int i8 = i7 + 1;
                this.index_ = i8;
                bArr3[i8] = 0;
                this.bitsLeft_ = 8;
                continue;
            } else {
                byte[] bArr4 = this.buffer_;
                int i9 = this.index_;
                bArr4[i9] = (byte) (((i >>> (i2 - i4)) & ((1 << i4) - 1)) | bArr4[i9]);
                i2 -= i4;
                int i10 = i9 + 1;
                this.index_ = i10;
                bArr4[i10] = 0;
                this.bitsLeft_ = 8;
                continue;
            }
        } while (i2 != 0);
    }
}
