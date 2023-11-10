package com.itextpdf.xmp.impl;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

public class FixASCIIControlsReader extends PushbackReader {
    private static final int BUFFER_SIZE = 8;
    private static final int STATE_AMP = 1;
    private static final int STATE_DIG1 = 4;
    private static final int STATE_ERROR = 5;
    private static final int STATE_HASH = 2;
    private static final int STATE_HEX = 3;
    private static final int STATE_START = 0;
    private int control = 0;
    private int digits = 0;
    private int state = 0;

    public FixASCIIControlsReader(Reader reader) {
        super(reader, 8);
    }

    public int read(char[] cArr, int i, int i2) throws IOException {
        char[] cArr2 = new char[8];
        boolean z = true;
        int i3 = 0;
        loop0:
        while (true) {
            int i4 = 0;
            while (z && i3 < i2) {
                z = super.read(cArr2, i4, 1) == 1;
                if (z) {
                    char processChar = processChar(cArr2[i4]);
                    int i5 = this.state;
                    if (i5 == 0) {
                        if (Utils.isControlChar(processChar)) {
                            processChar = ' ';
                        }
                        cArr[i] = processChar;
                        i3++;
                        i++;
                    } else if (i5 == 5) {
                        unread(cArr2, 0, i4 + 1);
                    } else {
                        i4++;
                    }
                } else if (i4 > 0) {
                    unread(cArr2, 0, i4);
                    this.state = 5;
                    z = true;
                }
            }
        }
        if (i3 > 0 || z) {
            return i3;
        }
        return -1;
    }

    private char processChar(char c) {
        int i;
        int i2 = this.state;
        if (i2 == 0) {
            if (c == '&') {
                this.state = 1;
            }
            return c;
        } else if (i2 == 1) {
            if (c == '#') {
                this.state = 2;
            } else {
                this.state = 5;
            }
            return c;
        } else if (i2 != 2) {
            if (i2 == 3) {
                if (('0' <= c && c <= '9') || (('a' <= c && c <= 'f') || ('A' <= c && c <= 'F'))) {
                    this.control = (this.control * 16) + Character.digit(c, 16);
                    int i3 = this.digits + 1;
                    this.digits = i3;
                    if (i3 <= 4) {
                        this.state = 3;
                    } else {
                        this.state = 5;
                    }
                } else if (c != ';' || !Utils.isControlChar((char) this.control)) {
                    this.state = 5;
                } else {
                    this.state = 0;
                    i = this.control;
                }
                return c;
            } else if (i2 == 4) {
                if ('0' <= c && c <= '9') {
                    this.control = (this.control * 10) + Character.digit(c, 10);
                    int i4 = this.digits + 1;
                    this.digits = i4;
                    if (i4 <= 5) {
                        this.state = 4;
                    } else {
                        this.state = 5;
                    }
                } else if (c != ';' || !Utils.isControlChar((char) this.control)) {
                    this.state = 5;
                } else {
                    this.state = 0;
                    i = this.control;
                }
                return c;
            } else if (i2 != 5) {
                return c;
            } else {
                this.state = 0;
                return c;
            }
            return (char) i;
        } else {
            if (c == 'x') {
                this.control = 0;
                this.digits = 0;
                this.state = 3;
            } else if ('0' > c || c > '9') {
                this.state = 5;
            } else {
                this.control = Character.digit(c, 10);
                this.digits = 1;
                this.state = 4;
            }
            return c;
        }
    }
}
