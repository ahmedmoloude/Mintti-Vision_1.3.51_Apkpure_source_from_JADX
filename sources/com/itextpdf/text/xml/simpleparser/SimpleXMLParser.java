package com.itextpdf.text.xml.simpleparser;

import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.xml.XMLUtil;
import com.itextpdf.text.xml.simpleparser.handler.HTMLNewLineHandler;
import com.itextpdf.text.xml.simpleparser.handler.NeverNewLineHandler;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Stack;

public final class SimpleXMLParser {
    private static final int ATTRIBUTE_EQUAL = 13;
    private static final int ATTRIBUTE_KEY = 12;
    private static final int ATTRIBUTE_VALUE = 14;
    private static final int CDATA = 7;
    private static final int COMMENT = 8;
    private static final int ENTITY = 10;
    private static final int EXAMIN_TAG = 3;
    private static final int IN_CLOSETAG = 5;

    /* renamed from: PI */
    private static final int f779PI = 9;
    private static final int QUOTE = 11;
    private static final int SINGLE_TAG = 6;
    private static final int TAG_ENCOUNTERED = 2;
    private static final int TAG_EXAMINED = 4;
    private static final int TEXT = 1;
    private static final int UNKNOWN = 0;
    private String attributekey = null;
    private HashMap<String, String> attributes = null;
    private String attributevalue = null;
    private int character = 0;
    private int columns = 0;
    private final SimpleXMLDocHandlerComment comment;
    private final SimpleXMLDocHandler doc;
    private final StringBuffer entity = new StringBuffer();
    private boolean eol = false;
    private final boolean html;
    private int lines = 1;
    private int nested = 0;
    private NewLineHandler newLineHandler;
    private boolean nowhite = false;
    private int previousCharacter = -1;
    private int quoteCharacter = 34;
    private final Stack<Integer> stack;
    private int state;
    private String tag = null;
    private final StringBuffer text = new StringBuffer();

    private SimpleXMLParser(SimpleXMLDocHandler simpleXMLDocHandler, SimpleXMLDocHandlerComment simpleXMLDocHandlerComment, boolean z) {
        this.doc = simpleXMLDocHandler;
        this.comment = simpleXMLDocHandlerComment;
        this.html = z;
        if (z) {
            this.newLineHandler = new HTMLNewLineHandler();
        } else {
            this.newLineHandler = new NeverNewLineHandler();
        }
        this.stack = new Stack<>();
        this.state = z ? 1 : 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:238:0x0016, code lost:
        continue;
     */
    /* renamed from: go */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m103go(java.io.Reader r17) throws java.io.IOException {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            boolean r2 = r1 instanceof java.io.BufferedReader
            if (r2 == 0) goto L_0x000b
            java.io.BufferedReader r1 = (java.io.BufferedReader) r1
            goto L_0x0011
        L_0x000b:
            java.io.BufferedReader r2 = new java.io.BufferedReader
            r2.<init>(r1)
            r1 = r2
        L_0x0011:
            com.itextpdf.text.xml.simpleparser.SimpleXMLDocHandler r2 = r0.doc
            r2.startDocument()
        L_0x0016:
            int r2 = r0.previousCharacter
            r3 = -1
            if (r2 != r3) goto L_0x0022
            int r2 = r1.read()
            r0.character = r2
            goto L_0x0026
        L_0x0022:
            r0.character = r2
            r0.previousCharacter = r3
        L_0x0026:
            int r2 = r0.character
            r4 = 1
            r5 = 0
            if (r2 != r3) goto L_0x004b
            boolean r1 = r0.html
            if (r1 == 0) goto L_0x003f
            if (r1 == 0) goto L_0x0039
            int r1 = r0.state
            if (r1 != r4) goto L_0x0039
            r16.flush()
        L_0x0039:
            com.itextpdf.text.xml.simpleparser.SimpleXMLDocHandler r1 = r0.doc
            r1.endDocument()
            goto L_0x004a
        L_0x003f:
            java.lang.Object[] r1 = new java.lang.Object[r5]
            java.lang.String r2 = "missing.end.tag"
            java.lang.String r1 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r2, (java.lang.Object[]) r1)
            r0.throwException(r1)
        L_0x004a:
            return
        L_0x004b:
            r3 = 10
            if (r2 != r3) goto L_0x0056
            boolean r6 = r0.eol
            if (r6 == 0) goto L_0x0056
            r0.eol = r5
            goto L_0x0016
        L_0x0056:
            boolean r6 = r0.eol
            r7 = 13
            if (r6 == 0) goto L_0x005f
            r0.eol = r5
            goto L_0x007c
        L_0x005f:
            if (r2 != r3) goto L_0x0069
            int r2 = r0.lines
            int r2 = r2 + r4
            r0.lines = r2
            r0.columns = r5
            goto L_0x007c
        L_0x0069:
            if (r2 != r7) goto L_0x0077
            r0.eol = r4
            r0.character = r3
            int r2 = r0.lines
            int r2 = r2 + r4
            r0.lines = r2
            r0.columns = r5
            goto L_0x007c
        L_0x0077:
            int r2 = r0.columns
            int r2 = r2 + r4
            r0.columns = r2
        L_0x007c:
            int r2 = r0.state
            java.lang.String r6 = "error.in.attribute.processing"
            r8 = 12
            r11 = 14
            r12 = 61
            r14 = 4
            r15 = 6
            r13 = 38
            r10 = 47
            r9 = 32
            r3 = 62
            switch(r2) {
                case 0: goto L_0x048f;
                case 1: goto L_0x0420;
                case 2: goto L_0x03fc;
                case 3: goto L_0x0373;
                case 4: goto L_0x0346;
                case 5: goto L_0x031a;
                case 6: goto L_0x02e5;
                case 7: goto L_0x02b3;
                case 8: goto L_0x0281;
                case 9: goto L_0x0271;
                case 10: goto L_0x01f2;
                case 11: goto L_0x0175;
                case 12: goto L_0x0139;
                case 13: goto L_0x00e4;
                case 14: goto L_0x0094;
                default: goto L_0x0093;
            }
        L_0x0093:
            goto L_0x0016
        L_0x0094:
            int r2 = r0.character
            r7 = 34
            r8 = 11
            if (r2 == r7) goto L_0x00de
            r7 = 39
            if (r2 != r7) goto L_0x00a1
            goto L_0x00de
        L_0x00a1:
            char r2 = (char) r2
            boolean r2 = java.lang.Character.isWhitespace(r2)
            if (r2 == 0) goto L_0x00aa
            goto L_0x0016
        L_0x00aa:
            boolean r2 = r0.html
            if (r2 == 0) goto L_0x00c3
            int r7 = r0.character
            if (r7 != r3) goto L_0x00c3
            r16.flush()
            r0.processTag(r4)
            r16.initTag()
            int r2 = r16.restoreState()
            r0.state = r2
            goto L_0x0016
        L_0x00c3:
            if (r2 == 0) goto L_0x00d3
            java.lang.StringBuffer r2 = r0.text
            int r3 = r0.character
            char r3 = (char) r3
            r2.append(r3)
            r0.quoteCharacter = r9
            r0.state = r8
            goto L_0x0016
        L_0x00d3:
            java.lang.Object[] r2 = new java.lang.Object[r5]
            java.lang.String r2 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r6, (java.lang.Object[]) r2)
            r0.throwException(r2)
            goto L_0x0016
        L_0x00de:
            r0.quoteCharacter = r2
            r0.state = r8
            goto L_0x0016
        L_0x00e4:
            int r2 = r0.character
            if (r2 != r12) goto L_0x00ec
            r0.state = r11
            goto L_0x0016
        L_0x00ec:
            char r2 = (char) r2
            boolean r2 = java.lang.Character.isWhitespace(r2)
            if (r2 == 0) goto L_0x00f5
            goto L_0x0016
        L_0x00f5:
            boolean r2 = r0.html
            if (r2 == 0) goto L_0x0110
            int r7 = r0.character
            if (r7 != r3) goto L_0x0110
            java.lang.StringBuffer r2 = r0.text
            r2.setLength(r5)
            r0.processTag(r4)
            r16.initTag()
            int r2 = r16.restoreState()
            r0.state = r2
            goto L_0x0016
        L_0x0110:
            if (r2 == 0) goto L_0x011d
            int r3 = r0.character
            if (r3 != r10) goto L_0x011d
            r16.flush()
            r0.state = r15
            goto L_0x0016
        L_0x011d:
            if (r2 == 0) goto L_0x012e
            r16.flush()
            java.lang.StringBuffer r2 = r0.text
            int r3 = r0.character
            char r3 = (char) r3
            r2.append(r3)
            r0.state = r8
            goto L_0x0016
        L_0x012e:
            java.lang.Object[] r2 = new java.lang.Object[r5]
            java.lang.String r2 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r6, (java.lang.Object[]) r2)
            r0.throwException(r2)
            goto L_0x0016
        L_0x0139:
            int r2 = r0.character
            char r2 = (char) r2
            boolean r2 = java.lang.Character.isWhitespace(r2)
            if (r2 == 0) goto L_0x0149
            r16.flush()
            r0.state = r7
            goto L_0x0016
        L_0x0149:
            int r2 = r0.character
            if (r2 != r12) goto L_0x0154
            r16.flush()
            r0.state = r11
            goto L_0x0016
        L_0x0154:
            boolean r6 = r0.html
            if (r6 == 0) goto L_0x016d
            if (r2 != r3) goto L_0x016d
            java.lang.StringBuffer r2 = r0.text
            r2.setLength(r5)
            r0.processTag(r4)
            r16.initTag()
            int r2 = r16.restoreState()
            r0.state = r2
            goto L_0x0016
        L_0x016d:
            java.lang.StringBuffer r3 = r0.text
            char r2 = (char) r2
            r3.append(r2)
            goto L_0x0016
        L_0x0175:
            boolean r2 = r0.html
            if (r2 == 0) goto L_0x0192
            int r6 = r0.quoteCharacter
            if (r6 != r9) goto L_0x0192
            int r6 = r0.character
            if (r6 != r3) goto L_0x0192
            r16.flush()
            r0.processTag(r4)
            r16.initTag()
            int r2 = r16.restoreState()
            r0.state = r2
            goto L_0x0016
        L_0x0192:
            if (r2 == 0) goto L_0x01a8
            int r2 = r0.quoteCharacter
            if (r2 != r9) goto L_0x01a8
            int r2 = r0.character
            char r2 = (char) r2
            boolean r2 = java.lang.Character.isWhitespace(r2)
            if (r2 == 0) goto L_0x01a8
            r16.flush()
            r0.state = r14
            goto L_0x0016
        L_0x01a8:
            boolean r2 = r0.html
            if (r2 == 0) goto L_0x01ba
            int r2 = r0.quoteCharacter
            if (r2 != r9) goto L_0x01ba
            java.lang.StringBuffer r2 = r0.text
            int r3 = r0.character
            char r3 = (char) r3
            r2.append(r3)
            goto L_0x0016
        L_0x01ba:
            int r2 = r0.character
            int r3 = r0.quoteCharacter
            if (r2 != r3) goto L_0x01c7
            r16.flush()
            r0.state = r14
            goto L_0x0016
        L_0x01c7:
            java.lang.String r3 = " \r\n\t"
            int r2 = r3.indexOf(r2)
            if (r2 < 0) goto L_0x01d6
            java.lang.StringBuffer r2 = r0.text
            r2.append(r9)
            goto L_0x0016
        L_0x01d6:
            int r2 = r0.character
            if (r2 != r13) goto L_0x01ea
            int r2 = r0.state
            r0.saveState(r2)
            r2 = 10
            r0.state = r2
            java.lang.StringBuffer r2 = r0.entity
            r2.setLength(r5)
            goto L_0x0016
        L_0x01ea:
            java.lang.StringBuffer r3 = r0.text
            char r2 = (char) r2
            r3.append(r2)
            goto L_0x0016
        L_0x01f2:
            int r2 = r0.character
            r3 = 59
            if (r2 != r3) goto L_0x0223
            int r2 = r16.restoreState()
            r0.state = r2
            java.lang.StringBuffer r2 = r0.entity
            java.lang.String r2 = r2.toString()
            java.lang.StringBuffer r4 = r0.entity
            r4.setLength(r5)
            char r4 = com.itextpdf.text.xml.simpleparser.EntitiesToUnicode.decodeEntity(r2)
            if (r4 != 0) goto L_0x021c
            java.lang.StringBuffer r4 = r0.text
            r4.append(r13)
            r4.append(r2)
            r4.append(r3)
            goto L_0x0016
        L_0x021c:
            java.lang.StringBuffer r2 = r0.text
            r2.append(r4)
            goto L_0x0016
        L_0x0223:
            r3 = 35
            if (r2 == r3) goto L_0x023f
            r3 = 48
            if (r2 < r3) goto L_0x022f
            r3 = 57
            if (r2 <= r3) goto L_0x023f
        L_0x022f:
            r3 = 97
            if (r2 < r3) goto L_0x0237
            r3 = 122(0x7a, float:1.71E-43)
            if (r2 <= r3) goto L_0x023f
        L_0x0237:
            r3 = 65
            if (r2 < r3) goto L_0x0248
            r3 = 90
            if (r2 > r3) goto L_0x0248
        L_0x023f:
            java.lang.StringBuffer r2 = r0.entity
            int r2 = r2.length()
            r3 = 7
            if (r2 < r3) goto L_0x0267
        L_0x0248:
            int r2 = r16.restoreState()
            r0.state = r2
            int r2 = r0.character
            r0.previousCharacter = r2
            java.lang.StringBuffer r2 = r0.text
            r2.append(r13)
            java.lang.StringBuffer r3 = r0.entity
            java.lang.String r3 = r3.toString()
            r2.append(r3)
            java.lang.StringBuffer r2 = r0.entity
            r2.setLength(r5)
            goto L_0x0016
        L_0x0267:
            java.lang.StringBuffer r2 = r0.entity
            int r3 = r0.character
            char r3 = (char) r3
            r2.append(r3)
            goto L_0x0016
        L_0x0271:
            int r2 = r0.character
            if (r2 != r3) goto L_0x0016
            int r2 = r16.restoreState()
            r0.state = r2
            if (r2 != r4) goto L_0x0016
            r0.state = r5
            goto L_0x0016
        L_0x0281:
            int r2 = r0.character
            if (r2 != r3) goto L_0x02a9
            java.lang.StringBuffer r2 = r0.text
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "--"
            boolean r2 = r2.endsWith(r3)
            if (r2 == 0) goto L_0x02a9
            java.lang.StringBuffer r2 = r0.text
            int r3 = r2.length()
            r4 = 2
            int r3 = r3 - r4
            r2.setLength(r3)
            r16.flush()
            int r2 = r16.restoreState()
            r0.state = r2
            goto L_0x0016
        L_0x02a9:
            java.lang.StringBuffer r2 = r0.text
            int r3 = r0.character
            char r3 = (char) r3
            r2.append(r3)
            goto L_0x0016
        L_0x02b3:
            int r2 = r0.character
            if (r2 != r3) goto L_0x02db
            java.lang.StringBuffer r2 = r0.text
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "]]"
            boolean r2 = r2.endsWith(r3)
            if (r2 == 0) goto L_0x02db
            java.lang.StringBuffer r2 = r0.text
            int r3 = r2.length()
            r4 = 2
            int r3 = r3 - r4
            r2.setLength(r3)
            r16.flush()
            int r2 = r16.restoreState()
            r0.state = r2
            goto L_0x0016
        L_0x02db:
            java.lang.StringBuffer r2 = r0.text
            int r3 = r0.character
            char r3 = (char) r3
            r2.append(r3)
            goto L_0x0016
        L_0x02e5:
            int r2 = r0.character
            if (r2 == r3) goto L_0x02f8
            java.lang.Object[] r2 = new java.lang.Object[r4]
            java.lang.String r3 = r0.tag
            r2[r5] = r3
            java.lang.String r3 = "expected.gt.for.tag.lt.1.gt"
            java.lang.String r2 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r3, (java.lang.Object[]) r2)
            r0.throwException(r2)
        L_0x02f8:
            r16.doTag()
            r0.processTag(r4)
            r0.processTag(r5)
            r16.initTag()
            boolean r2 = r0.html
            if (r2 != 0) goto L_0x0312
            int r2 = r0.nested
            if (r2 != 0) goto L_0x0312
            com.itextpdf.text.xml.simpleparser.SimpleXMLDocHandler r1 = r0.doc
            r1.endDocument()
            return
        L_0x0312:
            int r2 = r16.restoreState()
            r0.state = r2
            goto L_0x0016
        L_0x031a:
            int r2 = r0.character
            if (r2 != r3) goto L_0x0335
            r16.doTag()
            r0.processTag(r5)
            boolean r2 = r0.html
            if (r2 != 0) goto L_0x032d
            int r2 = r0.nested
            if (r2 != 0) goto L_0x032d
            return
        L_0x032d:
            int r2 = r16.restoreState()
            r0.state = r2
            goto L_0x0016
        L_0x0335:
            char r2 = (char) r2
            boolean r2 = java.lang.Character.isWhitespace(r2)
            if (r2 != 0) goto L_0x0016
            java.lang.StringBuffer r2 = r0.text
            int r3 = r0.character
            char r3 = (char) r3
            r2.append(r3)
            goto L_0x0016
        L_0x0346:
            int r2 = r0.character
            if (r2 != r3) goto L_0x0358
            r0.processTag(r4)
            r16.initTag()
            int r2 = r16.restoreState()
            r0.state = r2
            goto L_0x0016
        L_0x0358:
            if (r2 != r10) goto L_0x035e
            r0.state = r15
            goto L_0x0016
        L_0x035e:
            char r2 = (char) r2
            boolean r2 = java.lang.Character.isWhitespace(r2)
            if (r2 == 0) goto L_0x0367
            goto L_0x0016
        L_0x0367:
            java.lang.StringBuffer r2 = r0.text
            int r3 = r0.character
            char r3 = (char) r3
            r2.append(r3)
            r0.state = r8
            goto L_0x0016
        L_0x0373:
            int r2 = r0.character
            if (r2 != r3) goto L_0x0388
            r16.doTag()
            r0.processTag(r4)
            r16.initTag()
            int r2 = r16.restoreState()
            r0.state = r2
            goto L_0x0016
        L_0x0388:
            if (r2 != r10) goto L_0x038e
            r0.state = r15
            goto L_0x0016
        L_0x038e:
            r3 = 45
            if (r2 != r3) goto L_0x03a9
            java.lang.StringBuffer r2 = r0.text
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "!-"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x03a9
            r16.flush()
            r2 = 8
            r0.state = r2
            goto L_0x0016
        L_0x03a9:
            int r2 = r0.character
            r3 = 91
            if (r2 != r3) goto L_0x03c5
            java.lang.StringBuffer r2 = r0.text
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "![CDATA"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x03c5
            r16.flush()
            r2 = 7
            r0.state = r2
            goto L_0x0016
        L_0x03c5:
            int r2 = r0.character
            r3 = 69
            if (r2 != r3) goto L_0x03e2
            java.lang.StringBuffer r2 = r0.text
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "!DOCTYP"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x03e2
            r16.flush()
            r2 = 9
            r0.state = r2
            goto L_0x0016
        L_0x03e2:
            int r2 = r0.character
            char r2 = (char) r2
            boolean r2 = java.lang.Character.isWhitespace(r2)
            if (r2 == 0) goto L_0x03f2
            r16.doTag()
            r0.state = r14
            goto L_0x0016
        L_0x03f2:
            java.lang.StringBuffer r2 = r0.text
            int r3 = r0.character
            char r3 = (char) r3
            r2.append(r3)
            goto L_0x0016
        L_0x03fc:
            r16.initTag()
            int r2 = r0.character
            if (r2 != r10) goto L_0x0408
            r2 = 5
            r0.state = r2
            goto L_0x0016
        L_0x0408:
            r3 = 63
            if (r2 != r3) goto L_0x0415
            r16.restoreState()
            r2 = 9
            r0.state = r2
            goto L_0x0016
        L_0x0415:
            java.lang.StringBuffer r3 = r0.text
            char r2 = (char) r2
            r3.append(r2)
            r2 = 3
            r0.state = r2
            goto L_0x0016
        L_0x0420:
            int r3 = r0.character
            r6 = 60
            if (r3 != r6) goto L_0x0433
            r16.flush()
            int r2 = r0.state
            r0.saveState(r2)
            r2 = 2
            r0.state = r2
            goto L_0x0016
        L_0x0433:
            if (r3 != r13) goto L_0x0445
            r0.saveState(r2)
            java.lang.StringBuffer r2 = r0.entity
            r2.setLength(r5)
            r2 = 10
            r0.state = r2
            r0.nowhite = r4
            goto L_0x0016
        L_0x0445:
            if (r3 != r9) goto L_0x0466
            boolean r2 = r0.html
            if (r2 == 0) goto L_0x0458
            boolean r2 = r0.nowhite
            if (r2 == 0) goto L_0x0458
            java.lang.StringBuffer r2 = r0.text
            r2.append(r9)
            r0.nowhite = r5
            goto L_0x0016
        L_0x0458:
            boolean r2 = r0.nowhite
            if (r2 == 0) goto L_0x0462
            java.lang.StringBuffer r2 = r0.text
            char r3 = (char) r3
            r2.append(r3)
        L_0x0462:
            r0.nowhite = r5
            goto L_0x0016
        L_0x0466:
            char r2 = (char) r3
            boolean r2 = java.lang.Character.isWhitespace(r2)
            if (r2 == 0) goto L_0x0483
            boolean r2 = r0.html
            if (r2 == 0) goto L_0x0473
            goto L_0x0016
        L_0x0473:
            boolean r2 = r0.nowhite
            if (r2 == 0) goto L_0x047f
            java.lang.StringBuffer r2 = r0.text
            int r3 = r0.character
            char r3 = (char) r3
            r2.append(r3)
        L_0x047f:
            r0.nowhite = r5
            goto L_0x0016
        L_0x0483:
            java.lang.StringBuffer r2 = r0.text
            int r3 = r0.character
            char r3 = (char) r3
            r2.append(r3)
            r0.nowhite = r4
            goto L_0x0016
        L_0x048f:
            int r2 = r0.character
            r3 = 60
            if (r2 != r3) goto L_0x0016
            r0.saveState(r4)
            r2 = 2
            r0.state = r2
            goto L_0x0016
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.xml.simpleparser.SimpleXMLParser.m103go(java.io.Reader):void");
    }

    private int restoreState() {
        if (!this.stack.empty()) {
            return this.stack.pop().intValue();
        }
        return 0;
    }

    private void saveState(int i) {
        this.stack.push(Integer.valueOf(i));
    }

    private void flush() {
        int i = this.state;
        if (i != 1) {
            if (i != 14) {
                if (i != 7) {
                    if (i == 8) {
                        SimpleXMLDocHandlerComment simpleXMLDocHandlerComment = this.comment;
                        if (simpleXMLDocHandlerComment != null) {
                            simpleXMLDocHandlerComment.comment(this.text.toString());
                        }
                    } else if (i != 11) {
                        if (i == 12) {
                            String stringBuffer = this.text.toString();
                            this.attributekey = stringBuffer;
                            if (this.html) {
                                this.attributekey = stringBuffer.toLowerCase();
                            }
                        }
                    }
                    this.text.setLength(0);
                }
            }
            String stringBuffer2 = this.text.toString();
            this.attributevalue = stringBuffer2;
            this.attributes.put(this.attributekey, stringBuffer2);
            this.text.setLength(0);
        }
        if (this.text.length() > 0) {
            this.doc.text(this.text.toString());
        }
        this.text.setLength(0);
    }

    private void initTag() {
        this.tag = null;
        this.attributes = new HashMap<>();
    }

    private void doTag() {
        if (this.tag == null) {
            this.tag = this.text.toString();
        }
        if (this.html) {
            this.tag = this.tag.toLowerCase();
        }
        this.text.setLength(0);
    }

    private void processTag(boolean z) {
        if (z) {
            this.nested++;
            this.doc.startElement(this.tag, this.attributes);
            return;
        }
        if (this.newLineHandler.isNewLineTag(this.tag)) {
            this.nowhite = false;
        }
        this.nested--;
        this.doc.endElement(this.tag);
    }

    private void throwException(String str) throws IOException {
        throw new IOException(MessageLocalization.getComposedMessage("1.near.line.2.column.3", str, String.valueOf(this.lines), String.valueOf(this.columns)));
    }

    public static void parse(SimpleXMLDocHandler simpleXMLDocHandler, SimpleXMLDocHandlerComment simpleXMLDocHandlerComment, Reader reader, boolean z) throws IOException {
        new SimpleXMLParser(simpleXMLDocHandler, simpleXMLDocHandlerComment, z).m103go(reader);
    }

    public static void parse(SimpleXMLDocHandler simpleXMLDocHandler, InputStream inputStream) throws IOException {
        String declaredEncoding;
        byte[] bArr = new byte[4];
        if (inputStream.read(bArr) == 4) {
            String encodingName = XMLUtil.getEncodingName(bArr);
            String str = null;
            if (encodingName.equals("UTF-8")) {
                StringBuffer stringBuffer = new StringBuffer();
                while (true) {
                    int read = inputStream.read();
                    if (read == -1 || read == 62) {
                        str = stringBuffer.toString();
                    } else {
                        stringBuffer.append((char) read);
                    }
                }
                str = stringBuffer.toString();
            } else if (encodingName.equals("CP037")) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    int read2 = inputStream.read();
                    if (read2 == -1 || read2 == 110) {
                        str = new String(byteArrayOutputStream.toByteArray(), "CP037");
                    } else {
                        byteArrayOutputStream.write(read2);
                    }
                }
                str = new String(byteArrayOutputStream.toByteArray(), "CP037");
            }
            if (!(str == null || (declaredEncoding = getDeclaredEncoding(str)) == null)) {
                encodingName = declaredEncoding;
            }
            parse(simpleXMLDocHandler, (Reader) new InputStreamReader(inputStream, IanaEncodings.getJavaEncoding(encodingName)));
            return;
        }
        throw new IOException(MessageLocalization.getComposedMessage("insufficient.length", new Object[0]));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003a, code lost:
        r3 = r3 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getDeclaredEncoding(java.lang.String r5) {
        /*
            r0 = 0
            if (r5 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.String r1 = "encoding"
            int r1 = r5.indexOf(r1)
            if (r1 >= 0) goto L_0x000d
            return r0
        L_0x000d:
            r2 = 34
            int r3 = r5.indexOf(r2, r1)
            r4 = 39
            int r1 = r5.indexOf(r4, r1)
            if (r3 != r1) goto L_0x001c
            return r0
        L_0x001c:
            if (r3 >= 0) goto L_0x0020
            if (r1 > 0) goto L_0x0024
        L_0x0020:
            if (r1 <= 0) goto L_0x0032
            if (r1 >= r3) goto L_0x0032
        L_0x0024:
            int r1 = r1 + 1
            int r2 = r5.indexOf(r4, r1)
            if (r2 >= 0) goto L_0x002d
            return r0
        L_0x002d:
            java.lang.String r5 = r5.substring(r1, r2)
            return r5
        L_0x0032:
            if (r1 >= 0) goto L_0x0036
            if (r3 > 0) goto L_0x003a
        L_0x0036:
            if (r3 <= 0) goto L_0x0048
            if (r3 >= r1) goto L_0x0048
        L_0x003a:
            int r3 = r3 + 1
            int r1 = r5.indexOf(r2, r3)
            if (r1 >= 0) goto L_0x0043
            return r0
        L_0x0043:
            java.lang.String r5 = r5.substring(r3, r1)
            return r5
        L_0x0048:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.xml.simpleparser.SimpleXMLParser.getDeclaredEncoding(java.lang.String):java.lang.String");
    }

    public static void parse(SimpleXMLDocHandler simpleXMLDocHandler, Reader reader) throws IOException {
        parse(simpleXMLDocHandler, (SimpleXMLDocHandlerComment) null, reader, false);
    }

    @Deprecated
    public static String escapeXML(String str, boolean z) {
        return XMLUtil.escapeXML(str, z);
    }
}
