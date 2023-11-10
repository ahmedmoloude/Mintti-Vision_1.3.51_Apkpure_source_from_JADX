package com.itextpdf.text.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.error_messages.MessageLocalization;
import java.io.IOException;
import java.util.HashMap;

class EnumerateTTC extends TrueTypeFont {
    protected String[] names;

    EnumerateTTC(String str) throws DocumentException, IOException {
        this.fileName = str;
        this.f698rf = new RandomAccessFileOrArray(str);
        findNames();
    }

    EnumerateTTC(byte[] bArr) throws DocumentException, IOException {
        this.fileName = "Byte array TTC";
        this.f698rf = new RandomAccessFileOrArray(bArr);
        findNames();
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public void findNames() throws DocumentException, IOException {
        this.tables = new HashMap();
        try {
            if (readStandardString(4).equals("ttcf")) {
                this.f698rf.skipBytes(4);
                int readInt = this.f698rf.readInt();
                this.names = new String[readInt];
                int filePointer = (int) this.f698rf.getFilePointer();
                int i = 0;
                while (i < readInt) {
                    this.tables.clear();
                    this.f698rf.seek((long) filePointer);
                    this.f698rf.skipBytes(i * 4);
                    this.directoryOffset = this.f698rf.readInt();
                    this.f698rf.seek((long) this.directoryOffset);
                    if (this.f698rf.readInt() == 65536) {
                        int readUnsignedShort = this.f698rf.readUnsignedShort();
                        this.f698rf.skipBytes(6);
                        for (int i2 = 0; i2 < readUnsignedShort; i2++) {
                            String readStandardString = readStandardString(4);
                            this.f698rf.skipBytes(4);
                            this.tables.put(readStandardString, new int[]{this.f698rf.readInt(), this.f698rf.readInt()});
                        }
                        this.names[i] = getBaseFont();
                        i++;
                    } else {
                        throw new DocumentException(MessageLocalization.getComposedMessage("1.is.not.a.valid.ttf.file", this.fileName));
                    }
                }
                if (this.f698rf != null) {
                    this.f698rf.close();
                    return;
                }
                return;
            }
            throw new DocumentException(MessageLocalization.getComposedMessage("1.is.not.a.valid.ttc.file", this.fileName));
        } catch (Throwable th) {
            if (this.f698rf != null) {
                this.f698rf.close();
            }
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public String[] getNames() {
        return this.names;
    }
}
