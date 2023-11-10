package com.itextpdf.text.pdf.fonts.otf;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class OpenTypeFontTableReader {
    protected static final Logger LOG = LoggerFactory.getLogger((Class<?>) OpenTypeFontTableReader.class);

    /* renamed from: rf */
    protected final RandomAccessFileOrArray f733rf;
    private List<String> supportedLanguages;
    protected final int tableLocation;

    /* access modifiers changed from: protected */
    public abstract void readSubTable(int i, int i2) throws IOException;

    public OpenTypeFontTableReader(RandomAccessFileOrArray randomAccessFileOrArray, int i) throws IOException {
        this.f733rf = randomAccessFileOrArray;
        this.tableLocation = i;
    }

    public Language getSupportedLanguage() throws FontReadingException {
        Language[] values = Language.values();
        for (String next : this.supportedLanguages) {
            int length = values.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    Language language = values[i];
                    if (language.isSupported(next)) {
                        return language;
                    }
                    i++;
                }
            }
        }
        throw new FontReadingException("Unsupported languages " + this.supportedLanguages);
    }

    /* access modifiers changed from: protected */
    public final void startReadingTable() throws FontReadingException {
        try {
            TableHeader readHeader = readHeader();
            readScriptListTable(this.tableLocation + readHeader.scriptListOffset);
            readFeatureListTable(this.tableLocation + readHeader.featureListOffset);
            readLookupListTable(this.tableLocation + readHeader.lookupListOffset);
        } catch (IOException e) {
            throw new FontReadingException("Error reading font file", e);
        }
    }

    private void readLookupListTable(int i) throws IOException {
        this.f733rf.seek((long) i);
        short readShort = this.f733rf.readShort();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < readShort; i2++) {
            arrayList.add(Integer.valueOf(this.f733rf.readShort()));
        }
        for (int i3 = 0; i3 < readShort; i3++) {
            readLookupTable(((Integer) arrayList.get(i3)).intValue() + i);
        }
    }

    private void readLookupTable(int i) throws IOException {
        this.f733rf.seek((long) i);
        short readShort = this.f733rf.readShort();
        this.f733rf.skipBytes(2);
        short readShort2 = this.f733rf.readShort();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i2 = 0; i2 < readShort2; i2++) {
            arrayList.add(Integer.valueOf(this.f733rf.readShort()));
        }
        for (Integer intValue : arrayList) {
            readSubTable(readShort, intValue.intValue() + i);
        }
    }

    /* access modifiers changed from: protected */
    public final List<Integer> readCoverageFormat(int i) throws IOException {
        ArrayList arrayList;
        this.f733rf.seek((long) i);
        short readShort = this.f733rf.readShort();
        int i2 = 0;
        if (readShort == 1) {
            short readShort2 = this.f733rf.readShort();
            arrayList = new ArrayList(readShort2);
            while (i2 < readShort2) {
                arrayList.add(Integer.valueOf(this.f733rf.readShort()));
                i2++;
            }
        } else if (readShort == 2) {
            short readShort3 = this.f733rf.readShort();
            arrayList = new ArrayList();
            while (i2 < readShort3) {
                readRangeRecord(arrayList);
                i2++;
            }
        } else {
            throw new UnsupportedOperationException("Invalid coverage format: " + readShort);
        }
        return Collections.unmodifiableList(arrayList);
    }

    private void readRangeRecord(List<Integer> list) throws IOException {
        short readShort = this.f733rf.readShort();
        this.f733rf.readShort();
        for (int readShort2 = this.f733rf.readShort(); readShort2 <= readShort; readShort2++) {
            list.add(Integer.valueOf(readShort2));
        }
    }

    private void readScriptListTable(int i) throws IOException {
        this.f733rf.seek((long) i);
        short readShort = this.f733rf.readShort();
        HashMap hashMap = new HashMap(readShort);
        for (int i2 = 0; i2 < readShort; i2++) {
            readScriptRecord(i, hashMap);
        }
        ArrayList arrayList = new ArrayList(readShort);
        for (String str : hashMap.keySet()) {
            readScriptTable(((Integer) hashMap.get(str)).intValue());
            arrayList.add(str);
        }
        this.supportedLanguages = Collections.unmodifiableList(arrayList);
    }

    private void readScriptRecord(int i, Map<String, Integer> map) throws IOException {
        map.put(this.f733rf.readString(4, "utf-8"), Integer.valueOf(i + this.f733rf.readShort()));
    }

    private void readScriptTable(int i) throws IOException {
        this.f733rf.seek((long) i);
        short readShort = this.f733rf.readShort();
        short readShort2 = this.f733rf.readShort();
        if (readShort2 > 0) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(readShort2);
            for (int i2 = 0; i2 < readShort2; i2++) {
                readLangSysRecord(linkedHashMap);
            }
            for (String str : linkedHashMap.keySet()) {
                readLangSysTable(((Integer) linkedHashMap.get(str)).intValue() + i);
            }
        }
        readLangSysTable(i + readShort);
    }

    private void readLangSysRecord(Map<String, Integer> map) throws IOException {
        map.put(this.f733rf.readString(4, "utf-8"), Integer.valueOf(this.f733rf.readShort()));
    }

    private void readLangSysTable(int i) throws IOException {
        this.f733rf.seek((long) i);
        short readShort = this.f733rf.readShort();
        Logger logger = LOG;
        logger.debug("lookupOrderOffset=" + readShort);
        short readShort2 = this.f733rf.readShort();
        logger.debug("reqFeatureIndex=" + readShort2);
        short readShort3 = this.f733rf.readShort();
        ArrayList arrayList = new ArrayList(readShort3);
        for (int i2 = 0; i2 < readShort3; i2++) {
            arrayList.add(Short.valueOf(this.f733rf.readShort()));
        }
        Logger logger2 = LOG;
        logger2.debug("featureListIndices=" + arrayList);
    }

    private void readFeatureListTable(int i) throws IOException {
        this.f733rf.seek((long) i);
        short readShort = this.f733rf.readShort();
        Logger logger = LOG;
        logger.debug("featureCount=" + readShort);
        LinkedHashMap linkedHashMap = new LinkedHashMap(readShort);
        for (int i2 = 0; i2 < readShort; i2++) {
            linkedHashMap.put(this.f733rf.readString(4, "utf-8"), Short.valueOf(this.f733rf.readShort()));
        }
        for (String str : linkedHashMap.keySet()) {
            Logger logger2 = LOG;
            logger2.debug("*************featureName=" + str);
            readFeatureTable(((Short) linkedHashMap.get(str)).shortValue() + i);
        }
    }

    private void readFeatureTable(int i) throws IOException {
        this.f733rf.seek((long) i);
        short readShort = this.f733rf.readShort();
        Logger logger = LOG;
        logger.debug("featureParamsOffset=" + readShort);
        short readShort2 = this.f733rf.readShort();
        logger.debug("lookupCount=" + readShort2);
        ArrayList arrayList = new ArrayList(readShort2);
        for (int i2 = 0; i2 < readShort2; i2++) {
            arrayList.add(Short.valueOf(this.f733rf.readShort()));
        }
    }

    private TableHeader readHeader() throws IOException {
        this.f733rf.seek((long) this.tableLocation);
        return new TableHeader(this.f733rf.readInt(), this.f733rf.readUnsignedShort(), this.f733rf.readUnsignedShort(), this.f733rf.readUnsignedShort());
    }
}
