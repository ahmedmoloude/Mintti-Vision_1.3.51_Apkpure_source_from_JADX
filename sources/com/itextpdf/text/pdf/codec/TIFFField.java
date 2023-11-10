package com.itextpdf.text.pdf.codec;

import java.io.Serializable;
import kotlin.UByte;
import kotlin.jvm.internal.CharCompanionObject;

public class TIFFField implements Comparable<TIFFField>, Serializable {
    public static final int TIFF_ASCII = 2;
    public static final int TIFF_BYTE = 1;
    public static final int TIFF_DOUBLE = 12;
    public static final int TIFF_FLOAT = 11;
    public static final int TIFF_LONG = 4;
    public static final int TIFF_RATIONAL = 5;
    public static final int TIFF_SBYTE = 6;
    public static final int TIFF_SHORT = 3;
    public static final int TIFF_SLONG = 9;
    public static final int TIFF_SRATIONAL = 10;
    public static final int TIFF_SSHORT = 8;
    public static final int TIFF_UNDEFINED = 7;
    private static final long serialVersionUID = 9088332901412823834L;
    int count;
    Object data;
    int tag;
    int type;

    TIFFField() {
    }

    public TIFFField(int i, int i2, int i3, Object obj) {
        this.tag = i;
        this.type = i2;
        this.count = i3;
        this.data = obj;
    }

    public int getTag() {
        return this.tag;
    }

    public int getType() {
        return this.type;
    }

    public int getCount() {
        return this.count;
    }

    public byte[] getAsBytes() {
        return (byte[]) this.data;
    }

    public char[] getAsChars() {
        return (char[]) this.data;
    }

    public short[] getAsShorts() {
        return (short[]) this.data;
    }

    public int[] getAsInts() {
        return (int[]) this.data;
    }

    public long[] getAsLongs() {
        return (long[]) this.data;
    }

    public float[] getAsFloats() {
        return (float[]) this.data;
    }

    public double[] getAsDoubles() {
        return (double[]) this.data;
    }

    public int[][] getAsSRationals() {
        return (int[][]) this.data;
    }

    public long[][] getAsRationals() {
        return (long[][]) this.data;
    }

    public int getAsInt(int i) {
        int i2 = this.type;
        if (i2 != 1) {
            if (i2 == 3) {
                return ((char[]) this.data)[i] & CharCompanionObject.MAX_VALUE;
            }
            switch (i2) {
                case 6:
                    return ((byte[]) this.data)[i];
                case 7:
                    break;
                case 8:
                    return ((short[]) this.data)[i];
                case 9:
                    return ((int[]) this.data)[i];
                default:
                    throw new ClassCastException();
            }
        }
        return ((byte[]) this.data)[i] & UByte.MAX_VALUE;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: InitCodeVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [byte, short], vars: [r4v2 ?, r4v7 ?, r4v4 ?, r4v5 ?, r4v6 ?, r4v8 ?]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:51)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:32)
        */
    public long getAsLong(int r4) {
        /*
            r3 = this;
            int r0 = r3.type
            switch(r0) {
                case 1: goto L_0x003d;
                case 2: goto L_0x0005;
                case 3: goto L_0x0030;
                case 4: goto L_0x0027;
                case 5: goto L_0x0005;
                case 6: goto L_0x001e;
                case 7: goto L_0x003d;
                case 8: goto L_0x0015;
                case 9: goto L_0x000b;
                default: goto L_0x0005;
            }
        L_0x0005:
            java.lang.ClassCastException r4 = new java.lang.ClassCastException
            r4.<init>()
            throw r4
        L_0x000b:
            java.lang.Object r0 = r3.data
            int[] r0 = (int[]) r0
            int[] r0 = (int[]) r0
            r4 = r0[r4]
        L_0x0013:
            long r0 = (long) r4
            return r0
        L_0x0015:
            java.lang.Object r0 = r3.data
            short[] r0 = (short[]) r0
            short[] r0 = (short[]) r0
            short r4 = r0[r4]
            goto L_0x0013
        L_0x001e:
            java.lang.Object r0 = r3.data
            byte[] r0 = (byte[]) r0
            byte[] r0 = (byte[]) r0
            byte r4 = r0[r4]
            goto L_0x0013
        L_0x0027:
            java.lang.Object r0 = r3.data
            long[] r0 = (long[]) r0
            long[] r0 = (long[]) r0
            r1 = r0[r4]
            return r1
        L_0x0030:
            java.lang.Object r0 = r3.data
            char[] r0 = (char[]) r0
            char[] r0 = (char[]) r0
            char r4 = r0[r4]
            r0 = 65535(0xffff, float:9.1834E-41)
            r4 = r4 & r0
            goto L_0x0013
        L_0x003d:
            java.lang.Object r0 = r3.data
            byte[] r0 = (byte[]) r0
            byte[] r0 = (byte[]) r0
            byte r4 = r0[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            goto L_0x0013
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.codec.TIFFField.getAsLong(int):long");
    }

    public float getAsFloat(int i) {
        switch (this.type) {
            case 1:
                return (float) (((byte[]) this.data)[i] & UByte.MAX_VALUE);
            case 3:
                return (float) (((char[]) this.data)[i] & CharCompanionObject.MAX_VALUE);
            case 4:
                return (float) ((long[]) this.data)[i];
            case 5:
                long[] asRational = getAsRational(i);
                return (float) (((double) asRational[0]) / ((double) asRational[1]));
            case 6:
                return (float) ((byte[]) this.data)[i];
            case 8:
                return (float) ((short[]) this.data)[i];
            case 9:
                return (float) ((int[]) this.data)[i];
            case 10:
                int[] asSRational = getAsSRational(i);
                return (float) (((double) asSRational[0]) / ((double) asSRational[1]));
            case 11:
                return ((float[]) this.data)[i];
            case 12:
                return (float) ((double[]) this.data)[i];
            default:
                throw new ClassCastException();
        }
    }

    public double getAsDouble(int i) {
        double d;
        double d2;
        switch (this.type) {
            case 1:
                return (double) (((byte[]) this.data)[i] & UByte.MAX_VALUE);
            case 3:
                return (double) (((char[]) this.data)[i] & CharCompanionObject.MAX_VALUE);
            case 4:
                return (double) ((long[]) this.data)[i];
            case 5:
                long[] asRational = getAsRational(i);
                d = (double) asRational[0];
                d2 = (double) asRational[1];
                break;
            case 6:
                return (double) ((byte[]) this.data)[i];
            case 8:
                return (double) ((short[]) this.data)[i];
            case 9:
                return (double) ((int[]) this.data)[i];
            case 10:
                int[] asSRational = getAsSRational(i);
                d = (double) asSRational[0];
                d2 = (double) asSRational[1];
                break;
            case 11:
                return (double) ((float[]) this.data)[i];
            case 12:
                return ((double[]) this.data)[i];
            default:
                throw new ClassCastException();
        }
        return d / d2;
    }

    public String getAsString(int i) {
        return ((String[]) this.data)[i];
    }

    public int[] getAsSRational(int i) {
        return ((int[][]) this.data)[i];
    }

    public long[] getAsRational(int i) {
        if (this.type == 4) {
            return getAsLongs();
        }
        return ((long[][]) this.data)[i];
    }

    public int compareTo(TIFFField tIFFField) {
        if (tIFFField != null) {
            int tag2 = tIFFField.getTag();
            int i = this.tag;
            if (i < tag2) {
                return -1;
            }
            return i > tag2 ? 1 : 0;
        }
        throw new IllegalArgumentException();
    }
}
