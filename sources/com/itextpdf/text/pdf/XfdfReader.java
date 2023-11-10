package com.itextpdf.text.pdf;

import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.xml.simpleparser.SimpleXMLDocHandler;
import com.itextpdf.text.xml.simpleparser.SimpleXMLParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class XfdfReader implements SimpleXMLDocHandler {
    private final Stack<String> fieldNames;
    private final Stack<String> fieldValues;
    HashMap<String, String> fields;
    String fileSpec;
    private boolean foundRoot;
    protected HashMap<String, List<String>> listFields;

    public void endDocument() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0027 A[SYNTHETIC, Splitter:B:12:0x0027] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public XfdfReader(java.lang.String r3) throws java.io.IOException {
        /*
            r2 = this;
            r2.<init>()
            r0 = 0
            r2.foundRoot = r0
            java.util.Stack r0 = new java.util.Stack
            r0.<init>()
            r2.fieldNames = r0
            java.util.Stack r0 = new java.util.Stack
            r0.<init>()
            r2.fieldValues = r0
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ all -> 0x0024 }
            r1.<init>(r3)     // Catch:{ all -> 0x0024 }
            com.itextpdf.text.xml.simpleparser.SimpleXMLParser.parse((com.itextpdf.text.xml.simpleparser.SimpleXMLDocHandler) r2, (java.io.InputStream) r1)     // Catch:{ all -> 0x0021 }
            r1.close()     // Catch:{ Exception -> 0x0020 }
        L_0x0020:
            return
        L_0x0021:
            r3 = move-exception
            r0 = r1
            goto L_0x0025
        L_0x0024:
            r3 = move-exception
        L_0x0025:
            if (r0 == 0) goto L_0x002a
            r0.close()     // Catch:{ Exception -> 0x002a }
        L_0x002a:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.XfdfReader.<init>(java.lang.String):void");
    }

    public XfdfReader(byte[] bArr) throws IOException {
        this((InputStream) new ByteArrayInputStream(bArr));
    }

    public XfdfReader(InputStream inputStream) throws IOException {
        this.foundRoot = false;
        this.fieldNames = new Stack<>();
        this.fieldValues = new Stack<>();
        SimpleXMLParser.parse((SimpleXMLDocHandler) this, inputStream);
    }

    public HashMap<String, String> getFields() {
        return this.fields;
    }

    public String getField(String str) {
        return this.fields.get(str);
    }

    public String getFieldValue(String str) {
        String str2 = this.fields.get(str);
        if (str2 == null) {
            return null;
        }
        return str2;
    }

    public List<String> getListValues(String str) {
        return this.listFields.get(str);
    }

    public String getFileSpec() {
        return this.fileSpec;
    }

    public void startElement(String str, Map<String, String> map) {
        if (!this.foundRoot) {
            if (str.equals("xfdf")) {
                this.foundRoot = true;
            } else {
                throw new RuntimeException(MessageLocalization.getComposedMessage("root.element.is.not.xfdf.1", str));
            }
        }
        if (!str.equals("xfdf")) {
            if (str.equals("f")) {
                this.fileSpec = map.get(HtmlTags.HREF);
            } else if (str.equals("fields")) {
                this.fields = new HashMap<>();
                this.listFields = new HashMap<>();
            } else if (str.equals("field")) {
                this.fieldNames.push(map.get("name"));
            } else if (str.equals("value")) {
                this.fieldValues.push("");
            }
        }
    }

    public void endElement(String str) {
        if (str.equals("value")) {
            String str2 = "";
            for (int i = 0; i < this.fieldNames.size(); i++) {
                str2 = str2 + "." + ((String) this.fieldNames.elementAt(i));
            }
            if (str2.startsWith(".")) {
                str2 = str2.substring(1);
            }
            String pop = this.fieldValues.pop();
            String put = this.fields.put(str2, pop);
            if (put != null) {
                List list = this.listFields.get(str2);
                if (list == null) {
                    list = new ArrayList();
                    list.add(put);
                }
                list.add(pop);
                this.listFields.put(str2, list);
            }
        } else if (str.equals("field") && !this.fieldNames.isEmpty()) {
            this.fieldNames.pop();
        }
    }

    public void startDocument() {
        this.fileSpec = "";
    }

    public void text(String str) {
        if (!this.fieldNames.isEmpty() && !this.fieldValues.isEmpty()) {
            this.fieldValues.push(this.fieldValues.pop() + str);
        }
    }
}
