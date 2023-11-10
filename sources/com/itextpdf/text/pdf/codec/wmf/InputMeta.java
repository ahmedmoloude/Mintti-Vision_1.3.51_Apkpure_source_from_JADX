package com.itextpdf.text.pdf.codec.wmf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Utilities;
import java.io.IOException;
import java.io.InputStream;

public class InputMeta {

    /* renamed from: in */
    InputStream f725in;
    int length;

    public InputMeta(InputStream inputStream) {
        this.f725in = inputStream;
    }

    public int readWord() throws IOException {
        this.length += 2;
        int read = this.f725in.read();
        if (read < 0) {
            return 0;
        }
        return (read + (this.f725in.read() << 8)) & 65535;
    }

    public int readShort() throws IOException {
        int readWord = readWord();
        return readWord > 32767 ? readWord - 65536 : readWord;
    }

    public int readInt() throws IOException {
        this.length += 4;
        int read = this.f725in.read();
        if (read < 0) {
            return 0;
        }
        return read + (this.f725in.read() << 8) + (this.f725in.read() << 16) + (this.f725in.read() << 24);
    }

    public int readByte() throws IOException {
        this.length++;
        return this.f725in.read() & 255;
    }

    public void skip(int i) throws IOException {
        this.length += i;
        Utilities.skip(this.f725in, i);
    }

    public int getLength() {
        return this.length;
    }

    public BaseColor readColor() throws IOException {
        int readByte = readByte();
        int readByte2 = readByte();
        int readByte3 = readByte();
        readByte();
        return new BaseColor(readByte, readByte2, readByte3);
    }
}
