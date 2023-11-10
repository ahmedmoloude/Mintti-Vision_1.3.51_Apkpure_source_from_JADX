package com.itextpdf.text.pdf;

import com.itextpdf.text.AccessibleElementId;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListLabel;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.Version;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.p018io.TempFileCache;
import com.itextpdf.text.pdf.collection.PdfCollection;
import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
import com.itextpdf.text.pdf.internal.PdfAnnotationsImp;
import com.itextpdf.text.pdf.internal.PdfViewerPreferencesImp;
import com.itextpdf.text.xml.xmp.PdfProperties;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

public class PdfDocument extends Document {
    protected static final DecimalFormat SIXTEEN_DIGITS = new DecimalFormat("0000000000000000");
    static final String hangingPunctuation = ".,;:'";
    protected PdfDictionary additionalActions;
    protected int alignment = 0;
    protected PdfAction anchorAction = null;
    PdfAnnotationsImp annotationsImp;
    private PdfBody body;
    protected HashMap<String, PdfRectangle> boxSize = new HashMap<>();
    protected PdfCollection collection;
    protected float currentHeight = 0.0f;
    protected PdfOutline currentOutline;
    protected HashMap<String, PdfObject> documentFileAttachment = new HashMap<>();
    protected HashMap<String, PdfObject> documentLevelJS = new HashMap<>();
    private HashMap<AccessibleElementId, AccessibleElementId> elementsParents = new HashMap<>();
    private TempFileCache externalCache;
    private HashMap<AccessibleElementId, TempFileCache.ObjectPosition> externallyStoredStructElements = new HashMap<>();
    protected boolean firstPageEvent = true;
    private ArrayList<Element> floatingElements = new ArrayList<>();
    protected PdfContentByte graphics;
    protected float imageEnd = -1.0f;
    protected Image imageWait = null;
    protected Indentation indentation = new Indentation();
    protected PdfInfo info = new PdfInfo();
    protected boolean isSectionTitle = false;
    private boolean isToUseExternalCache = false;
    int jsCounter;
    protected PdfString language;
    protected int lastElementType = -1;
    protected float leading = 0.0f;
    private Stack<Float> leadingStack = new Stack<>();
    protected PdfLine line = null;
    protected ArrayList<PdfLine> lines = new ArrayList<>();
    protected TreeMap<String, Destination> localDestinations = new TreeMap<>();
    protected HashMap<Object, Integer> markPoints = new HashMap<>();
    protected float nextMarginBottom;
    protected float nextMarginLeft;
    protected float nextMarginRight;
    protected float nextMarginTop;
    protected Rectangle nextPageSize = null;
    protected PdfAction openActionAction;
    protected String openActionName;
    protected boolean openMCDocument = false;
    protected PdfDictionary pageAA = null;
    private boolean pageEmpty = true;
    protected PdfPageLabels pageLabels;
    protected PageResources pageResources;
    protected PdfOutline rootOutline;
    protected boolean strictImageSequence = false;
    private HashMap<AccessibleElementId, PdfStructureElement> structElements = new HashMap<>();
    protected HashMap<Object, int[]> structParentIndices = new HashMap<>();
    protected TabSettings tabSettings;
    protected PdfContentByte text;
    protected int textEmptySize;
    protected HashMap<String, PdfRectangle> thisBoxSize = new HashMap<>();
    protected PdfViewerPreferencesImp viewerPreferences = new PdfViewerPreferencesImp();
    protected PdfWriter writer;

    public static class Indentation {
        float imageIndentLeft = 0.0f;
        float imageIndentRight = 0.0f;
        float indentBottom = 0.0f;
        float indentLeft = 0.0f;
        float indentRight = 0.0f;
        float indentTop = 0.0f;
        float listIndentLeft = 0.0f;
        float sectionIndentLeft = 0.0f;
        float sectionIndentRight = 0.0f;
    }

    public static class PdfInfo extends PdfDictionary {
        PdfInfo() {
            addProducer();
            addCreationDate();
        }

        PdfInfo(String str, String str2, String str3) {
            this();
            addTitle(str2);
            addSubject(str3);
            addAuthor(str);
        }

        /* access modifiers changed from: package-private */
        public void addTitle(String str) {
            put(PdfName.TITLE, new PdfString(str, PdfObject.TEXT_UNICODE));
        }

        /* access modifiers changed from: package-private */
        public void addSubject(String str) {
            put(PdfName.SUBJECT, new PdfString(str, PdfObject.TEXT_UNICODE));
        }

        /* access modifiers changed from: package-private */
        public void addKeywords(String str) {
            put(PdfName.KEYWORDS, new PdfString(str, PdfObject.TEXT_UNICODE));
        }

        /* access modifiers changed from: package-private */
        public void addAuthor(String str) {
            put(PdfName.AUTHOR, new PdfString(str, PdfObject.TEXT_UNICODE));
        }

        /* access modifiers changed from: package-private */
        public void addCreator(String str) {
            put(PdfName.CREATOR, new PdfString(str, PdfObject.TEXT_UNICODE));
        }

        /* access modifiers changed from: package-private */
        public void addProducer() {
            put(PdfName.PRODUCER, new PdfString(Version.getInstance().getVersion()));
        }

        /* access modifiers changed from: package-private */
        public void addCreationDate() {
            PdfDate pdfDate = new PdfDate();
            put(PdfName.CREATIONDATE, pdfDate);
            put(PdfName.MODDATE, pdfDate);
        }

        /* access modifiers changed from: package-private */
        public void addkey(String str, String str2) {
            if (!str.equals(PdfProperties.PRODUCER) && !str.equals("CreationDate")) {
                put(new PdfName(str), new PdfString(str2, PdfObject.TEXT_UNICODE));
            }
        }
    }

    static class PdfCatalog extends PdfDictionary {
        PdfWriter writer;

        PdfCatalog(PdfIndirectReference pdfIndirectReference, PdfWriter pdfWriter) {
            super(CATALOG);
            this.writer = pdfWriter;
            put(PdfName.PAGES, pdfIndirectReference);
        }

        /* access modifiers changed from: package-private */
        public void addNames(TreeMap<String, Destination> treeMap, HashMap<String, PdfObject> hashMap, HashMap<String, PdfObject> hashMap2, PdfWriter pdfWriter) {
            if (!treeMap.isEmpty() || !hashMap.isEmpty() || !hashMap2.isEmpty()) {
                try {
                    PdfDictionary pdfDictionary = new PdfDictionary();
                    if (!treeMap.isEmpty()) {
                        HashMap hashMap3 = new HashMap();
                        for (Map.Entry next : treeMap.entrySet()) {
                            String str = (String) next.getKey();
                            Destination destination = (Destination) next.getValue();
                            if (destination.destination != null) {
                                hashMap3.put(str, destination.reference);
                            }
                        }
                        if (hashMap3.size() > 0) {
                            pdfDictionary.put(PdfName.DESTS, pdfWriter.addToBody(PdfNameTree.writeTree(hashMap3, pdfWriter)).getIndirectReference());
                        }
                    }
                    if (!hashMap.isEmpty()) {
                        pdfDictionary.put(PdfName.JAVASCRIPT, pdfWriter.addToBody(PdfNameTree.writeTree(hashMap, pdfWriter)).getIndirectReference());
                    }
                    if (!hashMap2.isEmpty()) {
                        pdfDictionary.put(PdfName.EMBEDDEDFILES, pdfWriter.addToBody(PdfNameTree.writeTree(hashMap2, pdfWriter)).getIndirectReference());
                    }
                    if (pdfDictionary.size() > 0) {
                        put(PdfName.NAMES, pdfWriter.addToBody(pdfDictionary).getIndirectReference());
                    }
                } catch (IOException e) {
                    throw new ExceptionConverter(e);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void setOpenAction(PdfAction pdfAction) {
            put(PdfName.OPENACTION, pdfAction);
        }

        /* access modifiers changed from: package-private */
        public void setAdditionalActions(PdfDictionary pdfDictionary) {
            try {
                put(PdfName.f546AA, this.writer.addToBody(pdfDictionary).getIndirectReference());
            } catch (Exception e) {
                throw new ExceptionConverter(e);
            }
        }
    }

    public PdfDocument() {
        addProducer();
        addCreationDate();
    }

    public void addWriter(PdfWriter pdfWriter) throws DocumentException {
        if (this.writer == null) {
            this.writer = pdfWriter;
            this.annotationsImp = new PdfAnnotationsImp(pdfWriter);
            return;
        }
        throw new DocumentException(MessageLocalization.getComposedMessage("you.can.only.add.a.writer.to.a.pdfdocument.once", new Object[0]));
    }

    public float getLeading() {
        return this.leading;
    }

    /* access modifiers changed from: package-private */
    public void setLeading(float f) {
        this.leading = f;
    }

    /* access modifiers changed from: protected */
    public void pushLeading() {
        this.leadingStack.push(Float.valueOf(this.leading));
    }

    /* access modifiers changed from: protected */
    public void popLeading() {
        this.leading = this.leadingStack.pop().floatValue();
        if (this.leadingStack.size() > 0) {
            this.leading = this.leadingStack.peek().floatValue();
        }
    }

    public TabSettings getTabSettings() {
        return this.tabSettings;
    }

    public void setTabSettings(TabSettings tabSettings2) {
        this.tabSettings = tabSettings2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0048, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean add(com.itextpdf.text.Element r13) throws com.itextpdf.text.DocumentException {
        /*
            r12 = this;
            com.itextpdf.text.pdf.PdfWriter r0 = r12.writer
            r1 = 0
            if (r0 == 0) goto L_0x000c
            boolean r0 = r0.isPaused()
            if (r0 == 0) goto L_0x000c
            return r1
        L_0x000c:
            int r0 = r13.type()     // Catch:{ Exception -> 0x05ef }
            r2 = 37
            if (r0 == r2) goto L_0x0017
            r12.flushFloatingElements()     // Catch:{ Exception -> 0x05ef }
        L_0x0017:
            int r0 = r13.type()     // Catch:{ Exception -> 0x05ef }
            r2 = 23
            r3 = 1
            if (r0 == r2) goto L_0x05cc
            r2 = 50
            if (r0 == r2) goto L_0x05b5
            r2 = 55
            r4 = 0
            if (r0 == r2) goto L_0x0586
            r2 = 666(0x29a, float:9.33E-43)
            if (r0 == r2) goto L_0x057b
            r2 = 29
            if (r0 == r2) goto L_0x051d
            r2 = 30
            if (r0 == r2) goto L_0x0511
            switch(r0) {
                case 0: goto L_0x04fc;
                case 1: goto L_0x04ee;
                case 2: goto L_0x04e0;
                case 3: goto L_0x04d2;
                case 4: goto L_0x04c4;
                case 5: goto L_0x04bd;
                case 6: goto L_0x04b6;
                case 7: goto L_0x04a8;
                case 8: goto L_0x049c;
                default: goto L_0x0038;
            }     // Catch:{ Exception -> 0x05ef }
        L_0x0038:
            switch(r0) {
                case 10: goto L_0x0462;
                case 11: goto L_0x0438;
                case 12: goto L_0x02e0;
                case 13: goto L_0x01c0;
                case 14: goto L_0x0160;
                case 15: goto L_0x00bd;
                case 16: goto L_0x01c0;
                case 17: goto L_0x0099;
                default: goto L_0x003b;
            }     // Catch:{ Exception -> 0x05ef }
        L_0x003b:
            switch(r0) {
                case 32: goto L_0x0059;
                case 33: goto L_0x0059;
                case 34: goto L_0x0059;
                case 35: goto L_0x0059;
                case 36: goto L_0x0059;
                case 37: goto L_0x0049;
                case 38: goto L_0x003f;
                default: goto L_0x003e;
            }     // Catch:{ Exception -> 0x05ef }
        L_0x003e:
            goto L_0x0048
        L_0x003f:
            com.itextpdf.text.pdf.PdfBody r13 = (com.itextpdf.text.pdf.PdfBody) r13     // Catch:{ Exception -> 0x05ef }
            r12.body = r13     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfContentByte r0 = r12.graphics     // Catch:{ Exception -> 0x05ef }
            r0.rectangle(r13)     // Catch:{ Exception -> 0x05ef }
        L_0x0048:
            return r1
        L_0x0049:
            r12.ensureNewLine()     // Catch:{ Exception -> 0x05ef }
            r12.flushLines()     // Catch:{ Exception -> 0x05ef }
            r0 = r13
            com.itextpdf.text.pdf.PdfDiv r0 = (com.itextpdf.text.pdf.PdfDiv) r0     // Catch:{ Exception -> 0x05ef }
            r12.addDiv(r0)     // Catch:{ Exception -> 0x05ef }
            r12.pageEmpty = r1     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x0059:
            com.itextpdf.text.pdf.PdfWriter r0 = r12.writer     // Catch:{ Exception -> 0x05ef }
            boolean r0 = isTagged(r0)     // Catch:{ Exception -> 0x05ef }
            if (r0 == 0) goto L_0x0075
            r0 = r13
            com.itextpdf.text.Image r0 = (com.itextpdf.text.Image) r0     // Catch:{ Exception -> 0x05ef }
            boolean r0 = r0.isImgTemplate()     // Catch:{ Exception -> 0x05ef }
            if (r0 != 0) goto L_0x0075
            r12.flushLines()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfContentByte r0 = r12.text     // Catch:{ Exception -> 0x05ef }
            r1 = r13
            com.itextpdf.text.Image r1 = (com.itextpdf.text.Image) r1     // Catch:{ Exception -> 0x05ef }
            r0.openMCBlock(r1)     // Catch:{ Exception -> 0x05ef }
        L_0x0075:
            r0 = r13
            com.itextpdf.text.Image r0 = (com.itextpdf.text.Image) r0     // Catch:{ Exception -> 0x05ef }
            r12.add((com.itextpdf.text.Image) r0)     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfWriter r0 = r12.writer     // Catch:{ Exception -> 0x05ef }
            boolean r0 = isTagged(r0)     // Catch:{ Exception -> 0x05ef }
            if (r0 == 0) goto L_0x05e8
            r0 = r13
            com.itextpdf.text.Image r0 = (com.itextpdf.text.Image) r0     // Catch:{ Exception -> 0x05ef }
            boolean r0 = r0.isImgTemplate()     // Catch:{ Exception -> 0x05ef }
            if (r0 != 0) goto L_0x05e8
            r12.flushLines()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfContentByte r0 = r12.text     // Catch:{ Exception -> 0x05ef }
            r1 = r13
            com.itextpdf.text.Image r1 = (com.itextpdf.text.Image) r1     // Catch:{ Exception -> 0x05ef }
            r0.closeMCBlock(r1)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x0099:
            r0 = r13
            com.itextpdf.text.Anchor r0 = (com.itextpdf.text.Anchor) r0     // Catch:{ Exception -> 0x05ef }
            java.lang.String r1 = r0.getReference()     // Catch:{ Exception -> 0x05ef }
            float r0 = r0.getLeading()     // Catch:{ Exception -> 0x05ef }
            r12.leading = r0     // Catch:{ Exception -> 0x05ef }
            r12.pushLeading()     // Catch:{ Exception -> 0x05ef }
            if (r1 == 0) goto L_0x00b2
            com.itextpdf.text.pdf.PdfAction r0 = new com.itextpdf.text.pdf.PdfAction     // Catch:{ Exception -> 0x05ef }
            r0.<init>((java.lang.String) r1)     // Catch:{ Exception -> 0x05ef }
            r12.anchorAction = r0     // Catch:{ Exception -> 0x05ef }
        L_0x00b2:
            r13.process(r12)     // Catch:{ Exception -> 0x05ef }
            r0 = 0
            r12.anchorAction = r0     // Catch:{ Exception -> 0x05ef }
            r12.popLeading()     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x00bd:
            r0 = r13
            com.itextpdf.text.ListItem r0 = (com.itextpdf.text.ListItem) r0     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfWriter r1 = r12.writer     // Catch:{ Exception -> 0x05ef }
            boolean r1 = isTagged(r1)     // Catch:{ Exception -> 0x05ef }
            if (r1 == 0) goto L_0x00d0
            r12.flushLines()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfContentByte r1 = r12.text     // Catch:{ Exception -> 0x05ef }
            r1.openMCBlock(r0)     // Catch:{ Exception -> 0x05ef }
        L_0x00d0:
            float r1 = r0.getSpacingBefore()     // Catch:{ Exception -> 0x05ef }
            float r2 = r12.leading     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.Font r4 = r0.getFont()     // Catch:{ Exception -> 0x05ef }
            r12.addSpacing(r1, r2, r4)     // Catch:{ Exception -> 0x05ef }
            int r1 = r0.getAlignment()     // Catch:{ Exception -> 0x05ef }
            r12.alignment = r1     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r1 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r2 = r1.listIndentLeft     // Catch:{ Exception -> 0x05ef }
            float r4 = r0.getIndentationLeft()     // Catch:{ Exception -> 0x05ef }
            float r2 = r2 + r4
            r1.listIndentLeft = r2     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r1 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r2 = r1.indentRight     // Catch:{ Exception -> 0x05ef }
            float r4 = r0.getIndentationRight()     // Catch:{ Exception -> 0x05ef }
            float r2 = r2 + r4
            r1.indentRight = r2     // Catch:{ Exception -> 0x05ef }
            float r1 = r0.getTotalLeading()     // Catch:{ Exception -> 0x05ef }
            r12.leading = r1     // Catch:{ Exception -> 0x05ef }
            r12.pushLeading()     // Catch:{ Exception -> 0x05ef }
            r12.carriageReturn()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfLine r1 = r12.line     // Catch:{ Exception -> 0x05ef }
            r1.setListItem(r0)     // Catch:{ Exception -> 0x05ef }
            r13.process(r12)     // Catch:{ Exception -> 0x05ef }
            float r1 = r0.getSpacingAfter()     // Catch:{ Exception -> 0x05ef }
            float r2 = r0.getTotalLeading()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.Font r4 = r0.getFont()     // Catch:{ Exception -> 0x05ef }
            r12.addSpacing(r1, r2, r4, r3)     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfLine r1 = r12.line     // Catch:{ Exception -> 0x05ef }
            boolean r1 = r1.hasToBeJustified()     // Catch:{ Exception -> 0x05ef }
            if (r1 == 0) goto L_0x0129
            com.itextpdf.text.pdf.PdfLine r1 = r12.line     // Catch:{ Exception -> 0x05ef }
            r1.resetAlignment()     // Catch:{ Exception -> 0x05ef }
        L_0x0129:
            r12.carriageReturn()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r1 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r2 = r1.listIndentLeft     // Catch:{ Exception -> 0x05ef }
            float r4 = r0.getIndentationLeft()     // Catch:{ Exception -> 0x05ef }
            float r2 = r2 - r4
            r1.listIndentLeft = r2     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r1 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r2 = r1.indentRight     // Catch:{ Exception -> 0x05ef }
            float r4 = r0.getIndentationRight()     // Catch:{ Exception -> 0x05ef }
            float r2 = r2 - r4
            r1.indentRight = r2     // Catch:{ Exception -> 0x05ef }
            r12.popLeading()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfWriter r1 = r12.writer     // Catch:{ Exception -> 0x05ef }
            boolean r1 = isTagged(r1)     // Catch:{ Exception -> 0x05ef }
            if (r1 == 0) goto L_0x05e8
            r12.flushLines()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfContentByte r1 = r12.text     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.ListBody r2 = r0.getListBody()     // Catch:{ Exception -> 0x05ef }
            r1.closeMCBlock(r2)     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfContentByte r1 = r12.text     // Catch:{ Exception -> 0x05ef }
            r1.closeMCBlock(r0)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x0160:
            r0 = r13
            com.itextpdf.text.List r0 = (com.itextpdf.text.List) r0     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfWriter r1 = r12.writer     // Catch:{ Exception -> 0x05ef }
            boolean r1 = isTagged(r1)     // Catch:{ Exception -> 0x05ef }
            if (r1 == 0) goto L_0x0173
            r12.flushLines()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfContentByte r1 = r12.text     // Catch:{ Exception -> 0x05ef }
            r1.openMCBlock(r0)     // Catch:{ Exception -> 0x05ef }
        L_0x0173:
            boolean r1 = r0.isAlignindent()     // Catch:{ Exception -> 0x05ef }
            if (r1 == 0) goto L_0x017c
            r0.normalizeIndentation()     // Catch:{ Exception -> 0x05ef }
        L_0x017c:
            com.itextpdf.text.pdf.PdfDocument$Indentation r1 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r2 = r1.listIndentLeft     // Catch:{ Exception -> 0x05ef }
            float r4 = r0.getIndentationLeft()     // Catch:{ Exception -> 0x05ef }
            float r2 = r2 + r4
            r1.listIndentLeft = r2     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r1 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r2 = r1.indentRight     // Catch:{ Exception -> 0x05ef }
            float r4 = r0.getIndentationRight()     // Catch:{ Exception -> 0x05ef }
            float r2 = r2 + r4
            r1.indentRight = r2     // Catch:{ Exception -> 0x05ef }
            r13.process(r12)     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r1 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r2 = r1.listIndentLeft     // Catch:{ Exception -> 0x05ef }
            float r4 = r0.getIndentationLeft()     // Catch:{ Exception -> 0x05ef }
            float r2 = r2 - r4
            r1.listIndentLeft = r2     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r1 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r2 = r1.indentRight     // Catch:{ Exception -> 0x05ef }
            float r4 = r0.getIndentationRight()     // Catch:{ Exception -> 0x05ef }
            float r2 = r2 - r4
            r1.indentRight = r2     // Catch:{ Exception -> 0x05ef }
            r12.carriageReturn()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfWriter r1 = r12.writer     // Catch:{ Exception -> 0x05ef }
            boolean r1 = isTagged(r1)     // Catch:{ Exception -> 0x05ef }
            if (r1 == 0) goto L_0x05e8
            r12.flushLines()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfContentByte r1 = r12.text     // Catch:{ Exception -> 0x05ef }
            r1.closeMCBlock(r0)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x01c0:
            r0 = r13
            com.itextpdf.text.Section r0 = (com.itextpdf.text.Section) r0     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfWriter r2 = r12.writer     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfPageEvent r2 = r2.getPageEvent()     // Catch:{ Exception -> 0x05ef }
            boolean r4 = r0.isNotAddedYet()     // Catch:{ Exception -> 0x05ef }
            if (r4 == 0) goto L_0x01d7
            com.itextpdf.text.Paragraph r4 = r0.getTitle()     // Catch:{ Exception -> 0x05ef }
            if (r4 == 0) goto L_0x01d7
            r10 = 1
            goto L_0x01d8
        L_0x01d7:
            r10 = 0
        L_0x01d8:
            boolean r4 = r0.isTriggerNewPage()     // Catch:{ Exception -> 0x05ef }
            if (r4 == 0) goto L_0x01e1
            r12.newPage()     // Catch:{ Exception -> 0x05ef }
        L_0x01e1:
            if (r10 == 0) goto L_0x022c
            float r4 = r12.indentTop()     // Catch:{ Exception -> 0x05ef }
            float r5 = r12.currentHeight     // Catch:{ Exception -> 0x05ef }
            float r4 = r4 - r5
            com.itextpdf.text.Rectangle r5 = r12.pageSize     // Catch:{ Exception -> 0x05ef }
            int r5 = r5.getRotation()     // Catch:{ Exception -> 0x05ef }
            r6 = 90
            if (r5 == r6) goto L_0x01f8
            r6 = 180(0xb4, float:2.52E-43)
            if (r5 != r6) goto L_0x0200
        L_0x01f8:
            com.itextpdf.text.Rectangle r5 = r12.pageSize     // Catch:{ Exception -> 0x05ef }
            float r5 = r5.getHeight()     // Catch:{ Exception -> 0x05ef }
            float r4 = r5 - r4
        L_0x0200:
            com.itextpdf.text.pdf.PdfDestination r5 = new com.itextpdf.text.pdf.PdfDestination     // Catch:{ Exception -> 0x05ef }
            r6 = 2
            r5.<init>(r6, r4)     // Catch:{ Exception -> 0x05ef }
        L_0x0206:
            com.itextpdf.text.pdf.PdfOutline r4 = r12.currentOutline     // Catch:{ Exception -> 0x05ef }
            int r4 = r4.level()     // Catch:{ Exception -> 0x05ef }
            int r6 = r0.getDepth()     // Catch:{ Exception -> 0x05ef }
            if (r4 < r6) goto L_0x021b
            com.itextpdf.text.pdf.PdfOutline r4 = r12.currentOutline     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfOutline r4 = r4.parent()     // Catch:{ Exception -> 0x05ef }
            r12.currentOutline = r4     // Catch:{ Exception -> 0x05ef }
            goto L_0x0206
        L_0x021b:
            com.itextpdf.text.pdf.PdfOutline r4 = new com.itextpdf.text.pdf.PdfOutline     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfOutline r6 = r12.currentOutline     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.Paragraph r7 = r0.getBookmarkTitle()     // Catch:{ Exception -> 0x05ef }
            boolean r8 = r0.isBookmarkOpen()     // Catch:{ Exception -> 0x05ef }
            r4.<init>((com.itextpdf.text.pdf.PdfOutline) r6, (com.itextpdf.text.pdf.PdfDestination) r5, (com.itextpdf.text.Paragraph) r7, (boolean) r8)     // Catch:{ Exception -> 0x05ef }
            r12.currentOutline = r4     // Catch:{ Exception -> 0x05ef }
        L_0x022c:
            r12.carriageReturn()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r4 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r5 = r4.sectionIndentLeft     // Catch:{ Exception -> 0x05ef }
            float r6 = r0.getIndentationLeft()     // Catch:{ Exception -> 0x05ef }
            float r5 = r5 + r6
            r4.sectionIndentLeft = r5     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r4 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r5 = r4.sectionIndentRight     // Catch:{ Exception -> 0x05ef }
            float r6 = r0.getIndentationRight()     // Catch:{ Exception -> 0x05ef }
            float r5 = r5 + r6
            r4.sectionIndentRight = r5     // Catch:{ Exception -> 0x05ef }
            boolean r4 = r0.isNotAddedYet()     // Catch:{ Exception -> 0x05ef }
            r11 = 16
            if (r4 == 0) goto L_0x027d
            if (r2 == 0) goto L_0x027d
            int r4 = r13.type()     // Catch:{ Exception -> 0x05ef }
            if (r4 != r11) goto L_0x0266
            com.itextpdf.text.pdf.PdfWriter r4 = r12.writer     // Catch:{ Exception -> 0x05ef }
            float r5 = r12.indentTop()     // Catch:{ Exception -> 0x05ef }
            float r6 = r12.currentHeight     // Catch:{ Exception -> 0x05ef }
            float r5 = r5 - r6
            com.itextpdf.text.Paragraph r6 = r0.getTitle()     // Catch:{ Exception -> 0x05ef }
            r2.onChapter(r4, r12, r5, r6)     // Catch:{ Exception -> 0x05ef }
            goto L_0x027d
        L_0x0266:
            com.itextpdf.text.pdf.PdfWriter r5 = r12.writer     // Catch:{ Exception -> 0x05ef }
            float r4 = r12.indentTop()     // Catch:{ Exception -> 0x05ef }
            float r6 = r12.currentHeight     // Catch:{ Exception -> 0x05ef }
            float r7 = r4 - r6
            int r8 = r0.getDepth()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.Paragraph r9 = r0.getTitle()     // Catch:{ Exception -> 0x05ef }
            r4 = r2
            r6 = r12
            r4.onSection(r5, r6, r7, r8, r9)     // Catch:{ Exception -> 0x05ef }
        L_0x027d:
            if (r10 == 0) goto L_0x028a
            r12.isSectionTitle = r3     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.Paragraph r4 = r0.getTitle()     // Catch:{ Exception -> 0x05ef }
            r12.add((com.itextpdf.text.Element) r4)     // Catch:{ Exception -> 0x05ef }
            r12.isSectionTitle = r1     // Catch:{ Exception -> 0x05ef }
        L_0x028a:
            com.itextpdf.text.pdf.PdfDocument$Indentation r1 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r4 = r1.sectionIndentLeft     // Catch:{ Exception -> 0x05ef }
            float r5 = r0.getIndentation()     // Catch:{ Exception -> 0x05ef }
            float r4 = r4 + r5
            r1.sectionIndentLeft = r4     // Catch:{ Exception -> 0x05ef }
            r13.process(r12)     // Catch:{ Exception -> 0x05ef }
            r12.flushLines()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r1 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r4 = r1.sectionIndentLeft     // Catch:{ Exception -> 0x05ef }
            float r5 = r0.getIndentationLeft()     // Catch:{ Exception -> 0x05ef }
            float r6 = r0.getIndentation()     // Catch:{ Exception -> 0x05ef }
            float r5 = r5 + r6
            float r4 = r4 - r5
            r1.sectionIndentLeft = r4     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r1 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r4 = r1.sectionIndentRight     // Catch:{ Exception -> 0x05ef }
            float r5 = r0.getIndentationRight()     // Catch:{ Exception -> 0x05ef }
            float r4 = r4 - r5
            r1.sectionIndentRight = r4     // Catch:{ Exception -> 0x05ef }
            boolean r0 = r0.isComplete()     // Catch:{ Exception -> 0x05ef }
            if (r0 == 0) goto L_0x05e8
            if (r2 == 0) goto L_0x05e8
            int r0 = r13.type()     // Catch:{ Exception -> 0x05ef }
            if (r0 != r11) goto L_0x02d2
            com.itextpdf.text.pdf.PdfWriter r0 = r12.writer     // Catch:{ Exception -> 0x05ef }
            float r1 = r12.indentTop()     // Catch:{ Exception -> 0x05ef }
            float r4 = r12.currentHeight     // Catch:{ Exception -> 0x05ef }
            float r1 = r1 - r4
            r2.onChapterEnd(r0, r12, r1)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x02d2:
            com.itextpdf.text.pdf.PdfWriter r0 = r12.writer     // Catch:{ Exception -> 0x05ef }
            float r1 = r12.indentTop()     // Catch:{ Exception -> 0x05ef }
            float r4 = r12.currentHeight     // Catch:{ Exception -> 0x05ef }
            float r1 = r1 - r4
            r2.onSectionEnd(r0, r12, r1)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x02e0:
            com.itextpdf.text.TabSettings r0 = r12.tabSettings     // Catch:{ Exception -> 0x05ef }
            r2 = r13
            com.itextpdf.text.Phrase r2 = (com.itextpdf.text.Phrase) r2     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.TabSettings r2 = r2.getTabSettings()     // Catch:{ Exception -> 0x05ef }
            if (r2 == 0) goto L_0x02f4
            r2 = r13
            com.itextpdf.text.Phrase r2 = (com.itextpdf.text.Phrase) r2     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.TabSettings r2 = r2.getTabSettings()     // Catch:{ Exception -> 0x05ef }
            r12.tabSettings = r2     // Catch:{ Exception -> 0x05ef }
        L_0x02f4:
            r2 = r13
            com.itextpdf.text.Paragraph r2 = (com.itextpdf.text.Paragraph) r2     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfWriter r5 = r12.writer     // Catch:{ Exception -> 0x05ef }
            boolean r5 = isTagged(r5)     // Catch:{ Exception -> 0x05ef }
            if (r5 == 0) goto L_0x0307
            r12.flushLines()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfContentByte r5 = r12.text     // Catch:{ Exception -> 0x05ef }
            r5.openMCBlock(r2)     // Catch:{ Exception -> 0x05ef }
        L_0x0307:
            float r5 = r2.getSpacingBefore()     // Catch:{ Exception -> 0x05ef }
            float r6 = r12.leading     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.Font r7 = r2.getFont()     // Catch:{ Exception -> 0x05ef }
            r12.addSpacing(r5, r6, r7)     // Catch:{ Exception -> 0x05ef }
            int r5 = r2.getAlignment()     // Catch:{ Exception -> 0x05ef }
            r12.alignment = r5     // Catch:{ Exception -> 0x05ef }
            float r5 = r2.getTotalLeading()     // Catch:{ Exception -> 0x05ef }
            r12.leading = r5     // Catch:{ Exception -> 0x05ef }
            r12.pushLeading()     // Catch:{ Exception -> 0x05ef }
            r12.carriageReturn()     // Catch:{ Exception -> 0x05ef }
            float r5 = r12.currentHeight     // Catch:{ Exception -> 0x05ef }
            float r6 = r12.calculateLineHeight()     // Catch:{ Exception -> 0x05ef }
            float r5 = r5 + r6
            float r6 = r12.indentTop()     // Catch:{ Exception -> 0x05ef }
            float r7 = r12.indentBottom()     // Catch:{ Exception -> 0x05ef }
            float r6 = r6 - r7
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 <= 0) goto L_0x033d
            r12.newPage()     // Catch:{ Exception -> 0x05ef }
        L_0x033d:
            com.itextpdf.text.pdf.PdfDocument$Indentation r5 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r6 = r5.indentLeft     // Catch:{ Exception -> 0x05ef }
            float r7 = r2.getIndentationLeft()     // Catch:{ Exception -> 0x05ef }
            float r6 = r6 + r7
            r5.indentLeft = r6     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r5 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r6 = r5.indentRight     // Catch:{ Exception -> 0x05ef }
            float r7 = r2.getIndentationRight()     // Catch:{ Exception -> 0x05ef }
            float r6 = r6 + r7
            r5.indentRight = r6     // Catch:{ Exception -> 0x05ef }
            r12.carriageReturn()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfWriter r5 = r12.writer     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfPageEvent r5 = r5.getPageEvent()     // Catch:{ Exception -> 0x05ef }
            if (r5 == 0) goto L_0x036e
            boolean r6 = r12.isSectionTitle     // Catch:{ Exception -> 0x05ef }
            if (r6 != 0) goto L_0x036e
            com.itextpdf.text.pdf.PdfWriter r6 = r12.writer     // Catch:{ Exception -> 0x05ef }
            float r7 = r12.indentTop()     // Catch:{ Exception -> 0x05ef }
            float r8 = r12.currentHeight     // Catch:{ Exception -> 0x05ef }
            float r7 = r7 - r8
            r5.onParagraph(r6, r12, r7)     // Catch:{ Exception -> 0x05ef }
        L_0x036e:
            boolean r6 = r2.getKeepTogether()     // Catch:{ Exception -> 0x05ef }
            if (r6 == 0) goto L_0x03c9
            r12.carriageReturn()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfPTable r6 = new com.itextpdf.text.pdf.PdfPTable     // Catch:{ Exception -> 0x05ef }
            r6.<init>((int) r3)     // Catch:{ Exception -> 0x05ef }
            boolean r7 = r2.getKeepTogether()     // Catch:{ Exception -> 0x05ef }
            r6.setKeepTogether(r7)     // Catch:{ Exception -> 0x05ef }
            r7 = 1120403456(0x42c80000, float:100.0)
            r6.setWidthPercentage(r7)     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfPCell r7 = new com.itextpdf.text.pdf.PdfPCell     // Catch:{ Exception -> 0x05ef }
            r7.<init>()     // Catch:{ Exception -> 0x05ef }
            r7.addElement(r2)     // Catch:{ Exception -> 0x05ef }
            r7.setBorder(r1)     // Catch:{ Exception -> 0x05ef }
            r7.setPadding(r4)     // Catch:{ Exception -> 0x05ef }
            r6.addCell((com.itextpdf.text.pdf.PdfPCell) r7)     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r4 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r7 = r4.indentLeft     // Catch:{ Exception -> 0x05ef }
            float r8 = r2.getIndentationLeft()     // Catch:{ Exception -> 0x05ef }
            float r7 = r7 - r8
            r4.indentLeft = r7     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r4 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r7 = r4.indentRight     // Catch:{ Exception -> 0x05ef }
            float r8 = r2.getIndentationRight()     // Catch:{ Exception -> 0x05ef }
            float r7 = r7 - r8
            r4.indentRight = r7     // Catch:{ Exception -> 0x05ef }
            r12.add((com.itextpdf.text.Element) r6)     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r4 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r6 = r4.indentLeft     // Catch:{ Exception -> 0x05ef }
            float r7 = r2.getIndentationLeft()     // Catch:{ Exception -> 0x05ef }
            float r6 = r6 + r7
            r4.indentLeft = r6     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r4 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r6 = r4.indentRight     // Catch:{ Exception -> 0x05ef }
            float r7 = r2.getIndentationRight()     // Catch:{ Exception -> 0x05ef }
            float r6 = r6 + r7
            r4.indentRight = r6     // Catch:{ Exception -> 0x05ef }
            goto L_0x03e7
        L_0x03c9:
            com.itextpdf.text.pdf.PdfLine r4 = r12.line     // Catch:{ Exception -> 0x05ef }
            float r6 = r2.getFirstLineIndent()     // Catch:{ Exception -> 0x05ef }
            r4.setExtraIndent(r6)     // Catch:{ Exception -> 0x05ef }
            r13.process(r12)     // Catch:{ Exception -> 0x05ef }
            r12.carriageReturn()     // Catch:{ Exception -> 0x05ef }
            float r4 = r2.getSpacingAfter()     // Catch:{ Exception -> 0x05ef }
            float r6 = r2.getTotalLeading()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.Font r7 = r2.getFont()     // Catch:{ Exception -> 0x05ef }
            r12.addSpacing(r4, r6, r7, r3)     // Catch:{ Exception -> 0x05ef }
        L_0x03e7:
            if (r5 == 0) goto L_0x03f9
            boolean r4 = r12.isSectionTitle     // Catch:{ Exception -> 0x05ef }
            if (r4 != 0) goto L_0x03f9
            com.itextpdf.text.pdf.PdfWriter r4 = r12.writer     // Catch:{ Exception -> 0x05ef }
            float r6 = r12.indentTop()     // Catch:{ Exception -> 0x05ef }
            float r7 = r12.currentHeight     // Catch:{ Exception -> 0x05ef }
            float r6 = r6 - r7
            r5.onParagraphEnd(r4, r12, r6)     // Catch:{ Exception -> 0x05ef }
        L_0x03f9:
            r12.alignment = r1     // Catch:{ Exception -> 0x05ef }
            java.util.ArrayList<com.itextpdf.text.Element> r1 = r12.floatingElements     // Catch:{ Exception -> 0x05ef }
            if (r1 == 0) goto L_0x0408
            int r1 = r1.size()     // Catch:{ Exception -> 0x05ef }
            if (r1 == 0) goto L_0x0408
            r12.flushFloatingElements()     // Catch:{ Exception -> 0x05ef }
        L_0x0408:
            com.itextpdf.text.pdf.PdfDocument$Indentation r1 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r4 = r1.indentLeft     // Catch:{ Exception -> 0x05ef }
            float r5 = r2.getIndentationLeft()     // Catch:{ Exception -> 0x05ef }
            float r4 = r4 - r5
            r1.indentLeft = r4     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfDocument$Indentation r1 = r12.indentation     // Catch:{ Exception -> 0x05ef }
            float r4 = r1.indentRight     // Catch:{ Exception -> 0x05ef }
            float r5 = r2.getIndentationRight()     // Catch:{ Exception -> 0x05ef }
            float r4 = r4 - r5
            r1.indentRight = r4     // Catch:{ Exception -> 0x05ef }
            r12.carriageReturn()     // Catch:{ Exception -> 0x05ef }
            r12.tabSettings = r0     // Catch:{ Exception -> 0x05ef }
            r12.popLeading()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfWriter r0 = r12.writer     // Catch:{ Exception -> 0x05ef }
            boolean r0 = isTagged(r0)     // Catch:{ Exception -> 0x05ef }
            if (r0 == 0) goto L_0x05e8
            r12.flushLines()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfContentByte r0 = r12.text     // Catch:{ Exception -> 0x05ef }
            r0.closeMCBlock(r2)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x0438:
            com.itextpdf.text.TabSettings r0 = r12.tabSettings     // Catch:{ Exception -> 0x05ef }
            r1 = r13
            com.itextpdf.text.Phrase r1 = (com.itextpdf.text.Phrase) r1     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.TabSettings r1 = r1.getTabSettings()     // Catch:{ Exception -> 0x05ef }
            if (r1 == 0) goto L_0x044c
            r1 = r13
            com.itextpdf.text.Phrase r1 = (com.itextpdf.text.Phrase) r1     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.TabSettings r1 = r1.getTabSettings()     // Catch:{ Exception -> 0x05ef }
            r12.tabSettings = r1     // Catch:{ Exception -> 0x05ef }
        L_0x044c:
            r1 = r13
            com.itextpdf.text.Phrase r1 = (com.itextpdf.text.Phrase) r1     // Catch:{ Exception -> 0x05ef }
            float r1 = r1.getTotalLeading()     // Catch:{ Exception -> 0x05ef }
            r12.leading = r1     // Catch:{ Exception -> 0x05ef }
            r12.pushLeading()     // Catch:{ Exception -> 0x05ef }
            r13.process(r12)     // Catch:{ Exception -> 0x05ef }
            r12.tabSettings = r0     // Catch:{ Exception -> 0x05ef }
            r12.popLeading()     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x0462:
            com.itextpdf.text.pdf.PdfLine r0 = r12.line     // Catch:{ Exception -> 0x05ef }
            if (r0 != 0) goto L_0x0469
            r12.carriageReturn()     // Catch:{ Exception -> 0x05ef }
        L_0x0469:
            com.itextpdf.text.pdf.PdfChunk r0 = new com.itextpdf.text.pdf.PdfChunk     // Catch:{ Exception -> 0x05ef }
            r2 = r13
            com.itextpdf.text.Chunk r2 = (com.itextpdf.text.Chunk) r2     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfAction r4 = r12.anchorAction     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.TabSettings r5 = r12.tabSettings     // Catch:{ Exception -> 0x05ef }
            r0.<init>(r2, r4, r5)     // Catch:{ Exception -> 0x05ef }
        L_0x0475:
            com.itextpdf.text.pdf.PdfLine r2 = r12.line     // Catch:{ Exception -> 0x05ef }
            float r4 = r12.leading     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfChunk r2 = r2.add(r0, r4)     // Catch:{ Exception -> 0x05ef }
            if (r2 == 0) goto L_0x048d
            r12.carriageReturn()     // Catch:{ Exception -> 0x05ef }
            boolean r0 = r0.isNewlineSplit()     // Catch:{ Exception -> 0x05ef }
            if (r0 != 0) goto L_0x048b
            r2.trimFirstSpace()     // Catch:{ Exception -> 0x05ef }
        L_0x048b:
            r0 = r2
            goto L_0x0475
        L_0x048d:
            r12.pageEmpty = r1     // Catch:{ Exception -> 0x05ef }
            java.lang.String r1 = "NEWPAGE"
            boolean r0 = r0.isAttribute(r1)     // Catch:{ Exception -> 0x05ef }
            if (r0 == 0) goto L_0x05e8
            r12.newPage()     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x049c:
            r0 = r13
            com.itextpdf.text.Meta r0 = (com.itextpdf.text.Meta) r0     // Catch:{ Exception -> 0x05ef }
            java.lang.String r0 = r0.getContent()     // Catch:{ Exception -> 0x05ef }
            r12.setLanguage(r0)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x04a8:
            com.itextpdf.text.pdf.PdfDocument$PdfInfo r0 = r12.info     // Catch:{ Exception -> 0x05ef }
            r1 = r13
            com.itextpdf.text.Meta r1 = (com.itextpdf.text.Meta) r1     // Catch:{ Exception -> 0x05ef }
            java.lang.String r1 = r1.getContent()     // Catch:{ Exception -> 0x05ef }
            r0.addCreator(r1)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x04b6:
            com.itextpdf.text.pdf.PdfDocument$PdfInfo r0 = r12.info     // Catch:{ Exception -> 0x05ef }
            r0.addCreationDate()     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x04bd:
            com.itextpdf.text.pdf.PdfDocument$PdfInfo r0 = r12.info     // Catch:{ Exception -> 0x05ef }
            r0.addProducer()     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x04c4:
            com.itextpdf.text.pdf.PdfDocument$PdfInfo r0 = r12.info     // Catch:{ Exception -> 0x05ef }
            r1 = r13
            com.itextpdf.text.Meta r1 = (com.itextpdf.text.Meta) r1     // Catch:{ Exception -> 0x05ef }
            java.lang.String r1 = r1.getContent()     // Catch:{ Exception -> 0x05ef }
            r0.addAuthor(r1)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x04d2:
            com.itextpdf.text.pdf.PdfDocument$PdfInfo r0 = r12.info     // Catch:{ Exception -> 0x05ef }
            r1 = r13
            com.itextpdf.text.Meta r1 = (com.itextpdf.text.Meta) r1     // Catch:{ Exception -> 0x05ef }
            java.lang.String r1 = r1.getContent()     // Catch:{ Exception -> 0x05ef }
            r0.addKeywords(r1)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x04e0:
            com.itextpdf.text.pdf.PdfDocument$PdfInfo r0 = r12.info     // Catch:{ Exception -> 0x05ef }
            r1 = r13
            com.itextpdf.text.Meta r1 = (com.itextpdf.text.Meta) r1     // Catch:{ Exception -> 0x05ef }
            java.lang.String r1 = r1.getContent()     // Catch:{ Exception -> 0x05ef }
            r0.addSubject(r1)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x04ee:
            com.itextpdf.text.pdf.PdfDocument$PdfInfo r0 = r12.info     // Catch:{ Exception -> 0x05ef }
            r1 = r13
            com.itextpdf.text.Meta r1 = (com.itextpdf.text.Meta) r1     // Catch:{ Exception -> 0x05ef }
            java.lang.String r1 = r1.getContent()     // Catch:{ Exception -> 0x05ef }
            r0.addTitle(r1)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x04fc:
            com.itextpdf.text.pdf.PdfDocument$PdfInfo r0 = r12.info     // Catch:{ Exception -> 0x05ef }
            r1 = r13
            com.itextpdf.text.Meta r1 = (com.itextpdf.text.Meta) r1     // Catch:{ Exception -> 0x05ef }
            java.lang.String r1 = r1.getName()     // Catch:{ Exception -> 0x05ef }
            r2 = r13
            com.itextpdf.text.Meta r2 = (com.itextpdf.text.Meta) r2     // Catch:{ Exception -> 0x05ef }
            java.lang.String r2 = r2.getContent()     // Catch:{ Exception -> 0x05ef }
            r0.addkey(r1, r2)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x0511:
            r0 = r13
            com.itextpdf.text.Rectangle r0 = (com.itextpdf.text.Rectangle) r0     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfContentByte r2 = r12.graphics     // Catch:{ Exception -> 0x05ef }
            r2.rectangle(r0)     // Catch:{ Exception -> 0x05ef }
            r12.pageEmpty = r1     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x051d:
            com.itextpdf.text.pdf.PdfLine r0 = r12.line     // Catch:{ Exception -> 0x05ef }
            if (r0 != 0) goto L_0x0524
            r12.carriageReturn()     // Catch:{ Exception -> 0x05ef }
        L_0x0524:
            r0 = r13
            com.itextpdf.text.Annotation r0 = (com.itextpdf.text.Annotation) r0     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.Rectangle r2 = new com.itextpdf.text.Rectangle     // Catch:{ Exception -> 0x05ef }
            r2.<init>(r4, r4)     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfLine r4 = r12.line     // Catch:{ Exception -> 0x05ef }
            if (r4 == 0) goto L_0x056d
            com.itextpdf.text.Rectangle r2 = new com.itextpdf.text.Rectangle     // Catch:{ Exception -> 0x05ef }
            float r4 = r12.indentRight()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfLine r5 = r12.line     // Catch:{ Exception -> 0x05ef }
            float r5 = r5.widthLeft()     // Catch:{ Exception -> 0x05ef }
            float r4 = r4 - r5
            float r4 = r0.llx(r4)     // Catch:{ Exception -> 0x05ef }
            float r5 = r12.indentTop()     // Catch:{ Exception -> 0x05ef }
            float r6 = r12.currentHeight     // Catch:{ Exception -> 0x05ef }
            float r5 = r5 - r6
            r6 = 1101004800(0x41a00000, float:20.0)
            float r5 = r5 - r6
            float r5 = r0.ury(r5)     // Catch:{ Exception -> 0x05ef }
            float r7 = r12.indentRight()     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfLine r8 = r12.line     // Catch:{ Exception -> 0x05ef }
            float r8 = r8.widthLeft()     // Catch:{ Exception -> 0x05ef }
            float r7 = r7 - r8
            float r7 = r7 + r6
            float r6 = r0.urx(r7)     // Catch:{ Exception -> 0x05ef }
            float r7 = r12.indentTop()     // Catch:{ Exception -> 0x05ef }
            float r8 = r12.currentHeight     // Catch:{ Exception -> 0x05ef }
            float r7 = r7 - r8
            float r7 = r0.lly(r7)     // Catch:{ Exception -> 0x05ef }
            r2.<init>(r4, r5, r6, r7)     // Catch:{ Exception -> 0x05ef }
        L_0x056d:
            com.itextpdf.text.pdf.PdfWriter r4 = r12.writer     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfAnnotation r0 = com.itextpdf.text.pdf.internal.PdfAnnotationsImp.convertAnnotation(r4, r0, r2)     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.internal.PdfAnnotationsImp r2 = r12.annotationsImp     // Catch:{ Exception -> 0x05ef }
            r2.addPlainAnnotation(r0)     // Catch:{ Exception -> 0x05ef }
            r12.pageEmpty = r1     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x057b:
            com.itextpdf.text.pdf.PdfWriter r0 = r12.writer     // Catch:{ Exception -> 0x05ef }
            if (r0 == 0) goto L_0x05e8
            r1 = r13
            com.itextpdf.text.api.WriterOperation r1 = (com.itextpdf.text.api.WriterOperation) r1     // Catch:{ Exception -> 0x05ef }
            r1.write(r0, r12)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x0586:
            r0 = r13
            com.itextpdf.text.pdf.draw.DrawInterface r0 = (com.itextpdf.text.pdf.draw.DrawInterface) r0     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.pdf.PdfContentByte r5 = r12.graphics     // Catch:{ Exception -> 0x05ef }
            float r6 = r12.indentLeft()     // Catch:{ Exception -> 0x05ef }
            float r7 = r12.indentBottom()     // Catch:{ Exception -> 0x05ef }
            float r8 = r12.indentRight()     // Catch:{ Exception -> 0x05ef }
            float r9 = r12.indentTop()     // Catch:{ Exception -> 0x05ef }
            float r2 = r12.indentTop()     // Catch:{ Exception -> 0x05ef }
            float r10 = r12.currentHeight     // Catch:{ Exception -> 0x05ef }
            float r2 = r2 - r10
            java.util.Stack<java.lang.Float> r10 = r12.leadingStack     // Catch:{ Exception -> 0x05ef }
            int r10 = r10.size()     // Catch:{ Exception -> 0x05ef }
            if (r10 <= 0) goto L_0x05ac
            float r4 = r12.leading     // Catch:{ Exception -> 0x05ef }
        L_0x05ac:
            float r10 = r2 - r4
            r4 = r0
            r4.draw(r5, r6, r7, r8, r9, r10)     // Catch:{ Exception -> 0x05ef }
            r12.pageEmpty = r1     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x05b5:
            boolean r0 = r13 instanceof com.itextpdf.text.MarkedSection     // Catch:{ Exception -> 0x05ef }
            if (r0 == 0) goto L_0x05c5
            r0 = r13
            com.itextpdf.text.MarkedSection r0 = (com.itextpdf.text.MarkedSection) r0     // Catch:{ Exception -> 0x05ef }
            com.itextpdf.text.MarkedObject r0 = r0.getTitle()     // Catch:{ Exception -> 0x05ef }
            if (r0 == 0) goto L_0x05c5
            r0.process(r12)     // Catch:{ Exception -> 0x05ef }
        L_0x05c5:
            r0 = r13
            com.itextpdf.text.MarkedObject r0 = (com.itextpdf.text.MarkedObject) r0     // Catch:{ Exception -> 0x05ef }
            r0.process(r12)     // Catch:{ Exception -> 0x05ef }
            goto L_0x05e8
        L_0x05cc:
            r0 = r13
            com.itextpdf.text.pdf.PdfPTable r0 = (com.itextpdf.text.pdf.PdfPTable) r0     // Catch:{ Exception -> 0x05ef }
            int r2 = r0.size()     // Catch:{ Exception -> 0x05ef }
            int r4 = r0.getHeaderRows()     // Catch:{ Exception -> 0x05ef }
            if (r2 > r4) goto L_0x05da
            goto L_0x05e8
        L_0x05da:
            r12.ensureNewLine()     // Catch:{ Exception -> 0x05ef }
            r12.flushLines()     // Catch:{ Exception -> 0x05ef }
            r12.addPTable(r0)     // Catch:{ Exception -> 0x05ef }
            r12.pageEmpty = r1     // Catch:{ Exception -> 0x05ef }
            r12.newLine()     // Catch:{ Exception -> 0x05ef }
        L_0x05e8:
            int r13 = r13.type()     // Catch:{ Exception -> 0x05ef }
            r12.lastElementType = r13     // Catch:{ Exception -> 0x05ef }
            return r3
        L_0x05ef:
            r13 = move-exception
            com.itextpdf.text.DocumentException r0 = new com.itextpdf.text.DocumentException
            r0.<init>((java.lang.Exception) r13)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfDocument.add(com.itextpdf.text.Element):boolean");
    }

    public void open() {
        if (!this.open) {
            super.open();
            this.writer.open();
            PdfOutline pdfOutline = new PdfOutline(this.writer);
            this.rootOutline = pdfOutline;
            this.currentOutline = pdfOutline;
        }
        try {
            if (isTagged(this.writer)) {
                this.openMCDocument = true;
            }
            initPage();
        } catch (DocumentException e) {
            throw new ExceptionConverter(e);
        }
    }

    public void close() {
        int size;
        if (!this.close) {
            try {
                if (isTagged(this.writer)) {
                    flushFloatingElements();
                    flushLines();
                    this.writer.flushAcroFields();
                    this.writer.flushTaggedObjects();
                    if (isPageEmpty() && (size = this.writer.pageReferences.size()) > 0 && this.writer.currentPageNumber == size) {
                        this.writer.pageReferences.remove(size - 1);
                    }
                } else {
                    this.writer.flushAcroFields();
                }
                if (this.imageWait != null) {
                    newPage();
                }
                endPage();
                if (isTagged(this.writer)) {
                    this.writer.getDirectContent().closeMCBlock(this);
                }
                if (!this.annotationsImp.hasUnusedAnnotations()) {
                    PdfPageEvent pageEvent = this.writer.getPageEvent();
                    if (pageEvent != null) {
                        pageEvent.onCloseDocument(this.writer, this);
                    }
                    super.close();
                    this.writer.addLocalDestinations(this.localDestinations);
                    calculateOutlineCount();
                    writeOutlines();
                    this.writer.close();
                    return;
                }
                throw new RuntimeException(MessageLocalization.getComposedMessage("not.all.annotations.could.be.added.to.the.document.the.document.doesn.t.have.enough.pages", new Object[0]));
            } catch (Exception e) {
                throw ExceptionConverter.convertException(e);
            }
        }
    }

    public void setXmpMetadata(byte[] bArr) throws IOException {
        PdfStream pdfStream = new PdfStream(bArr);
        pdfStream.put(PdfName.TYPE, PdfName.METADATA);
        pdfStream.put(PdfName.SUBTYPE, PdfName.XML);
        PdfEncryption encryption = this.writer.getEncryption();
        if (encryption != null && !encryption.isMetadataEncrypted()) {
            PdfArray pdfArray = new PdfArray();
            pdfArray.add((PdfObject) PdfName.CRYPT);
            pdfStream.put(PdfName.FILTER, pdfArray);
        }
        this.writer.addPageDictEntry(PdfName.METADATA, this.writer.addToBody(pdfStream).getIndirectReference());
    }

    public boolean newPage() {
        if (isPageEmpty()) {
            setNewPageSizeAndMargins();
            return false;
        } else if (!this.open || this.close) {
            throw new RuntimeException(MessageLocalization.getComposedMessage("the.document.is.not.open", new Object[0]));
        } else {
            ArrayList<IAccessibleElement> endPage = endPage();
            super.newPage();
            this.indentation.imageIndentLeft = 0.0f;
            this.indentation.imageIndentRight = 0.0f;
            try {
                if (isTagged(this.writer)) {
                    flushStructureElementsOnNewPage();
                    this.writer.getDirectContentUnder().restoreMCBlocks(endPage);
                }
                initPage();
                PdfBody pdfBody = this.body;
                if (pdfBody == null || pdfBody.getBackgroundColor() == null) {
                    return true;
                }
                this.graphics.rectangle(this.body);
                return true;
            } catch (DocumentException e) {
                throw new ExceptionConverter(e);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x015b A[Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0166 A[Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x017b A[Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.ArrayList<com.itextpdf.text.pdf.interfaces.IAccessibleElement> endPage() {
        /*
            r11 = this;
            java.lang.String r0 = "crop"
            java.lang.String r1 = "art"
            boolean r2 = r11.isPageEmpty()
            r3 = 0
            if (r2 == 0) goto L_0x000c
            return r3
        L_0x000c:
            r11.flushFloatingElements()     // Catch:{ DocumentException -> 0x01a6 }
            r2 = -1
            r11.lastElementType = r2
            com.itextpdf.text.pdf.PdfWriter r2 = r11.writer
            com.itextpdf.text.pdf.PdfPageEvent r2 = r2.getPageEvent()
            if (r2 == 0) goto L_0x001f
            com.itextpdf.text.pdf.PdfWriter r4 = r11.writer
            r2.onEndPage(r4, r11)
        L_0x001f:
            r11.flushLines()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.Rectangle r2 = r11.pageSize     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            int r2 = r2.getRotation()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfWriter r4 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            boolean r4 = r4.isPdfIso()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            if (r4 == 0) goto L_0x0087
            java.util.HashMap<java.lang.String, com.itextpdf.text.pdf.PdfRectangle> r4 = r11.thisBoxSize     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            boolean r4 = r4.containsKey(r1)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            java.lang.String r5 = "trim"
            if (r4 == 0) goto L_0x0053
            java.util.HashMap<java.lang.String, com.itextpdf.text.pdf.PdfRectangle> r4 = r11.thisBoxSize     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            boolean r4 = r4.containsKey(r5)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            if (r4 != 0) goto L_0x0044
            goto L_0x0053
        L_0x0044:
            com.itextpdf.text.pdf.PdfXConformanceException r0 = new com.itextpdf.text.pdf.PdfXConformanceException     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            java.lang.String r1 = "only.one.of.artbox.or.trimbox.can.exist.in.the.page"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            java.lang.String r1 = com.itextpdf.text.error_messages.MessageLocalization.getComposedMessage((java.lang.String) r1, (java.lang.Object[]) r2)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r0.<init>(r1)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            throw r0     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
        L_0x0053:
            java.util.HashMap<java.lang.String, com.itextpdf.text.pdf.PdfRectangle> r4 = r11.thisBoxSize     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            boolean r1 = r4.containsKey(r1)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            if (r1 != 0) goto L_0x0087
            java.util.HashMap<java.lang.String, com.itextpdf.text.pdf.PdfRectangle> r1 = r11.thisBoxSize     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            boolean r1 = r1.containsKey(r5)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            if (r1 != 0) goto L_0x0087
            java.util.HashMap<java.lang.String, com.itextpdf.text.pdf.PdfRectangle> r1 = r11.thisBoxSize     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            boolean r1 = r1.containsKey(r0)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            if (r1 == 0) goto L_0x0075
            java.util.HashMap<java.lang.String, com.itextpdf.text.pdf.PdfRectangle> r1 = r11.thisBoxSize     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            java.lang.Object r0 = r1.get(r0)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r1.put(r5, r0)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            goto L_0x0087
        L_0x0075:
            java.util.HashMap<java.lang.String, com.itextpdf.text.pdf.PdfRectangle> r0 = r11.thisBoxSize     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfRectangle r1 = new com.itextpdf.text.pdf.PdfRectangle     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.Rectangle r4 = r11.pageSize     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.Rectangle r6 = r11.pageSize     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            int r6 = r6.getRotation()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r1.<init>((com.itextpdf.text.Rectangle) r4, (int) r6)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r0.put(r5, r1)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
        L_0x0087:
            com.itextpdf.text.pdf.PageResources r0 = r11.pageResources     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfWriter r1 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfDictionary r1 = r1.getDefaultColorspace()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r0.addDefaultColorDiff(r1)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfWriter r0 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            boolean r0 = r0.isRgbTransparencyBlending()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            if (r0 == 0) goto L_0x00ab
            com.itextpdf.text.pdf.PdfDictionary r0 = new com.itextpdf.text.pdf.PdfDictionary     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r0.<init>()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.f566CS     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.DEVICERGB     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r0.put(r1, r4)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PageResources r1 = r11.pageResources     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r1.addDefaultColorDiff(r0)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
        L_0x00ab:
            com.itextpdf.text.pdf.PageResources r0 = r11.pageResources     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfDictionary r0 = r0.getResources()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfPage r1 = new com.itextpdf.text.pdf.PdfPage     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfRectangle r4 = new com.itextpdf.text.pdf.PdfRectangle     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.Rectangle r5 = r11.pageSize     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r4.<init>((com.itextpdf.text.Rectangle) r5, (int) r2)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            java.util.HashMap<java.lang.String, com.itextpdf.text.pdf.PdfRectangle> r5 = r11.thisBoxSize     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r1.<init>(r4, r5, r0, r2)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfWriter r0 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            boolean r0 = isTagged(r0)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            if (r0 == 0) goto L_0x00cf
            com.itextpdf.text.pdf.PdfName r0 = com.itextpdf.text.pdf.PdfName.TABS     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.f642S     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r1.put(r0, r2)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            goto L_0x00da
        L_0x00cf:
            com.itextpdf.text.pdf.PdfName r0 = com.itextpdf.text.pdf.PdfName.TABS     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfWriter r2 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfName r2 = r2.getTabs()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r1.put(r0, r2)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
        L_0x00da:
            com.itextpdf.text.pdf.PdfWriter r0 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfDictionary r0 = r0.getPageDictEntries()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r1.putAll(r0)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfWriter r0 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r0.resetPageDictEntries()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfDictionary r0 = r11.pageAA     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            if (r0 == 0) goto L_0x00ff
            com.itextpdf.text.pdf.PdfName r0 = com.itextpdf.text.pdf.PdfName.f546AA     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfWriter r2 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfDictionary r4 = r11.pageAA     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfIndirectObject r2 = r2.addToBody(r4)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfIndirectReference r2 = r2.getIndirectReference()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r1.put(r0, r2)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r11.pageAA = r3     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
        L_0x00ff:
            com.itextpdf.text.pdf.internal.PdfAnnotationsImp r0 = r11.annotationsImp     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            boolean r0 = r0.hasUnusedAnnotations()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            if (r0 == 0) goto L_0x011c
            com.itextpdf.text.pdf.internal.PdfAnnotationsImp r0 = r11.annotationsImp     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfWriter r2 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.Rectangle r4 = r11.pageSize     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfArray r0 = r0.rotateAnnotations(r2, r4)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            int r2 = r0.size()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            if (r2 == 0) goto L_0x011c
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.ANNOTS     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r1.put(r2, r0)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
        L_0x011c:
            com.itextpdf.text.pdf.PdfWriter r0 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            boolean r0 = isTagged(r0)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            if (r0 == 0) goto L_0x0138
            com.itextpdf.text.pdf.PdfName r0 = com.itextpdf.text.pdf.PdfName.STRUCTPARENTS     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfNumber r2 = new com.itextpdf.text.pdf.PdfNumber     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfWriter r4 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfIndirectReference r4 = r4.getCurrentPage()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            int r4 = r11.getStructParentIndex(r4)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r2.<init>((int) r4)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r1.put(r0, r2)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
        L_0x0138:
            com.itextpdf.text.pdf.PdfContentByte r0 = r11.text     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            int r0 = r0.size()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            int r2 = r11.textEmptySize     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            if (r0 > r2) goto L_0x014e
            com.itextpdf.text.pdf.PdfWriter r0 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            boolean r0 = isTagged(r0)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            if (r0 == 0) goto L_0x014b
            goto L_0x014e
        L_0x014b:
            r11.text = r3     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            goto L_0x0153
        L_0x014e:
            com.itextpdf.text.pdf.PdfContentByte r0 = r11.text     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r0.endText()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
        L_0x0153:
            com.itextpdf.text.pdf.PdfWriter r0 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            boolean r0 = isTagged(r0)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            if (r0 == 0) goto L_0x0166
            com.itextpdf.text.pdf.PdfWriter r0 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfContentByte r0 = r0.getDirectContent()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            java.util.ArrayList r0 = r0.saveMCBlocks()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            goto L_0x0167
        L_0x0166:
            r0 = r3
        L_0x0167:
            com.itextpdf.text.pdf.PdfWriter r2 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfContents r10 = new com.itextpdf.text.pdf.PdfContents     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfWriter r4 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfContentByte r5 = r4.getDirectContentUnder()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfContentByte r6 = r11.graphics     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfWriter r4 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            boolean r4 = isTagged(r4)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            if (r4 != 0) goto L_0x017d
            com.itextpdf.text.pdf.PdfContentByte r3 = r11.text     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
        L_0x017d:
            r7 = r3
            com.itextpdf.text.pdf.PdfWriter r3 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfContentByte r8 = r3.getDirectContent()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.Rectangle r9 = r11.pageSize     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r4 = r10
            r4.<init>(r5, r6, r7, r8, r9)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r2.add((com.itextpdf.text.pdf.PdfPage) r1, (com.itextpdf.text.pdf.PdfContents) r10)     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.internal.PdfAnnotationsImp r1 = r11.annotationsImp     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r1.resetAnnotations()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            com.itextpdf.text.pdf.PdfWriter r1 = r11.writer     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            r1.resetContent()     // Catch:{ DocumentException -> 0x019f, IOException -> 0x0198 }
            return r0
        L_0x0198:
            r0 = move-exception
            com.itextpdf.text.ExceptionConverter r1 = new com.itextpdf.text.ExceptionConverter
            r1.<init>(r0)
            throw r1
        L_0x019f:
            r0 = move-exception
            com.itextpdf.text.ExceptionConverter r1 = new com.itextpdf.text.ExceptionConverter
            r1.<init>(r0)
            throw r1
        L_0x01a6:
            r0 = move-exception
            com.itextpdf.text.ExceptionConverter r1 = new com.itextpdf.text.ExceptionConverter
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfDocument.endPage():java.util.ArrayList");
    }

    public boolean setPageSize(Rectangle rectangle) {
        PdfWriter pdfWriter = this.writer;
        if (pdfWriter != null && pdfWriter.isPaused()) {
            return false;
        }
        this.nextPageSize = new Rectangle(rectangle);
        return true;
    }

    public boolean setMargins(float f, float f2, float f3, float f4) {
        PdfWriter pdfWriter = this.writer;
        if (pdfWriter != null && pdfWriter.isPaused()) {
            return false;
        }
        this.nextMarginLeft = f;
        this.nextMarginRight = f2;
        this.nextMarginTop = f3;
        this.nextMarginBottom = f4;
        return true;
    }

    public boolean setMarginMirroring(boolean z) {
        PdfWriter pdfWriter = this.writer;
        if (pdfWriter == null || !pdfWriter.isPaused()) {
            return super.setMarginMirroring(z);
        }
        return false;
    }

    public boolean setMarginMirroringTopBottom(boolean z) {
        PdfWriter pdfWriter = this.writer;
        if (pdfWriter == null || !pdfWriter.isPaused()) {
            return super.setMarginMirroringTopBottom(z);
        }
        return false;
    }

    public void setPageCount(int i) {
        PdfWriter pdfWriter = this.writer;
        if (pdfWriter == null || !pdfWriter.isPaused()) {
            super.setPageCount(i);
        }
    }

    public void resetPageCount() {
        PdfWriter pdfWriter = this.writer;
        if (pdfWriter == null || !pdfWriter.isPaused()) {
            super.resetPageCount();
        }
    }

    /* access modifiers changed from: protected */
    public void initPage() throws DocumentException {
        this.pageN++;
        this.pageResources = new PageResources();
        if (isTagged(this.writer)) {
            this.graphics = this.writer.getDirectContentUnder().getDuplicate();
            this.writer.getDirectContent().duplicatedFrom = this.graphics;
        } else {
            this.graphics = new PdfContentByte(this.writer);
        }
        setNewPageSizeAndMargins();
        this.imageEnd = -1.0f;
        this.indentation.imageIndentRight = 0.0f;
        this.indentation.imageIndentLeft = 0.0f;
        this.indentation.indentBottom = 0.0f;
        this.indentation.indentTop = 0.0f;
        this.currentHeight = 0.0f;
        this.thisBoxSize = new HashMap<>(this.boxSize);
        if (!(this.pageSize.getBackgroundColor() == null && !this.pageSize.hasBorders() && this.pageSize.getBorderColor() == null)) {
            add((Element) this.pageSize);
        }
        float f = this.leading;
        int i = this.alignment;
        this.pageEmpty = true;
        try {
            Image image = this.imageWait;
            if (image != null) {
                add(image);
                this.imageWait = null;
            }
            this.leading = f;
            this.alignment = i;
            carriageReturn();
            PdfPageEvent pageEvent = this.writer.getPageEvent();
            if (pageEvent != null) {
                if (this.firstPageEvent) {
                    pageEvent.onOpenDocument(this.writer, this);
                }
                pageEvent.onStartPage(this.writer, this);
            }
            this.firstPageEvent = false;
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    /* access modifiers changed from: protected */
    public void newLine() throws DocumentException {
        this.lastElementType = -1;
        carriageReturn();
        ArrayList<PdfLine> arrayList = this.lines;
        if (arrayList != null && !arrayList.isEmpty()) {
            this.lines.add(this.line);
            this.currentHeight += this.line.height();
        }
        this.line = new PdfLine(indentLeft(), indentRight(), this.alignment, this.leading);
    }

    /* access modifiers changed from: protected */
    public float calculateLineHeight() {
        float height = this.line.height();
        float f = this.leading;
        return height != f ? height + f : height;
    }

    /* access modifiers changed from: protected */
    public void carriageReturn() {
        if (this.lines == null) {
            this.lines = new ArrayList<>();
        }
        PdfLine pdfLine = this.line;
        if (pdfLine != null && pdfLine.size() > 0) {
            if (this.currentHeight + calculateLineHeight() > indentTop() - indentBottom() && this.currentHeight != 0.0f) {
                PdfLine pdfLine2 = this.line;
                this.line = null;
                newPage();
                this.line = pdfLine2;
                pdfLine2.left = indentLeft();
            }
            this.currentHeight += this.line.height();
            this.lines.add(this.line);
            this.pageEmpty = false;
        }
        float f = this.imageEnd;
        if (f > -1.0f && this.currentHeight > f) {
            this.imageEnd = -1.0f;
            this.indentation.imageIndentRight = 0.0f;
            this.indentation.imageIndentLeft = 0.0f;
        }
        this.line = new PdfLine(indentLeft(), indentRight(), this.alignment, this.leading);
    }

    public float getVerticalPosition(boolean z) {
        if (z) {
            ensureNewLine();
        }
        return (top() - this.currentHeight) - this.indentation.indentTop;
    }

    /* access modifiers changed from: protected */
    public void ensureNewLine() {
        try {
            int i = this.lastElementType;
            if (i == 11 || i == 10) {
                newLine();
                flushLines();
            }
        } catch (DocumentException e) {
            throw new ExceptionConverter(e);
        }
    }

    /* access modifiers changed from: protected */
    public float flushLines() throws DocumentException {
        ListLabel listLabel;
        if (this.lines == null) {
            return 0.0f;
        }
        PdfLine pdfLine = this.line;
        if (pdfLine != null && pdfLine.size() > 0) {
            this.lines.add(this.line);
            this.line = new PdfLine(indentLeft(), indentRight(), this.alignment, this.leading);
        }
        if (this.lines.isEmpty()) {
            return 0.0f;
        }
        Object[] objArr = new Object[2];
        objArr[1] = new Float(0.0f);
        Iterator<PdfLine> it = this.lines.iterator();
        PdfFont pdfFont = null;
        float f = 0.0f;
        while (it.hasNext()) {
            PdfLine next = it.next();
            float indentLeft = (next.indentLeft() - indentLeft()) + this.indentation.indentLeft + this.indentation.listIndentLeft + this.indentation.sectionIndentLeft;
            this.text.moveText(indentLeft, -next.height());
            next.flush();
            if (next.listSymbol() != null) {
                Chunk listSymbol = next.listSymbol();
                if (isTagged(this.writer)) {
                    listLabel = next.listItem().getListLabel();
                    this.graphics.openMCBlock(listLabel);
                    Chunk chunk = new Chunk(listSymbol);
                    chunk.setRole((PdfName) null);
                    listSymbol = chunk;
                } else {
                    listLabel = null;
                }
                ColumnText.showTextAligned(this.graphics, 0, new Phrase(listSymbol), this.text.getXTLM() - next.listIndent(), this.text.getYTLM(), 0.0f);
                if (listLabel != null) {
                    this.graphics.closeMCBlock(listLabel);
                }
            }
            objArr[0] = pdfFont;
            if (isTagged(this.writer) && next.listItem() != null) {
                this.text.openMCBlock(next.listItem().getListBody());
            }
            writeLineToContent(next, this.text, this.graphics, objArr, this.writer.getSpaceCharRatio());
            pdfFont = (PdfFont) objArr[0];
            f += next.height();
            this.text.moveText(-indentLeft, 0.0f);
        }
        this.lines = new ArrayList<>();
        return f;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x0331  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x03da  */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x03e8  */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x049a  */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x04a4  */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x0515  */
    /* JADX WARNING: Removed duplicated region for block: B:186:0x0542  */
    /* JADX WARNING: Removed duplicated region for block: B:187:0x0555  */
    /* JADX WARNING: Removed duplicated region for block: B:190:0x055e  */
    /* JADX WARNING: Removed duplicated region for block: B:202:0x0593  */
    /* JADX WARNING: Removed duplicated region for block: B:215:0x05e0  */
    /* JADX WARNING: Removed duplicated region for block: B:216:0x05e9  */
    /* JADX WARNING: Removed duplicated region for block: B:218:0x05ed  */
    /* JADX WARNING: Removed duplicated region for block: B:219:0x05f3  */
    /* JADX WARNING: Removed duplicated region for block: B:223:0x0606  */
    /* JADX WARNING: Removed duplicated region for block: B:229:0x062f  */
    /* JADX WARNING: Removed duplicated region for block: B:232:0x0637  */
    /* JADX WARNING: Removed duplicated region for block: B:233:0x06be  */
    /* JADX WARNING: Removed duplicated region for block: B:247:0x0765  */
    /* JADX WARNING: Removed duplicated region for block: B:263:0x07a0  */
    /* JADX WARNING: Removed duplicated region for block: B:266:0x07a8  */
    /* JADX WARNING: Removed duplicated region for block: B:267:0x07af  */
    /* JADX WARNING: Removed duplicated region for block: B:269:0x07b4  */
    /* JADX WARNING: Removed duplicated region for block: B:272:0x07bb  */
    /* JADX WARNING: Removed duplicated region for block: B:275:0x07c4  */
    /* JADX WARNING: Removed duplicated region for block: B:276:0x07d0  */
    /* JADX WARNING: Removed duplicated region for block: B:309:0x08c8  */
    /* JADX WARNING: Removed duplicated region for block: B:311:0x08ce  */
    /* JADX WARNING: Removed duplicated region for block: B:313:0x08d3  */
    /* JADX WARNING: Removed duplicated region for block: B:315:0x08d9  */
    /* JADX WARNING: Removed duplicated region for block: B:318:0x08e2  */
    /* JADX WARNING: Removed duplicated region for block: B:323:0x08f4  */
    /* JADX WARNING: Removed duplicated region for block: B:324:0x08f9  */
    /* JADX WARNING: Removed duplicated region for block: B:326:0x0902  */
    /* JADX WARNING: Removed duplicated region for block: B:340:0x094d  */
    /* JADX WARNING: Removed duplicated region for block: B:345:0x095f  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x010f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public float writeLineToContent(com.itextpdf.text.pdf.PdfLine r62, com.itextpdf.text.pdf.PdfContentByte r63, com.itextpdf.text.pdf.PdfContentByte r64, java.lang.Object[] r65, float r66) throws com.itextpdf.text.DocumentException {
        /*
            r61 = this;
            r7 = r61
            r8 = r62
            r9 = r63
            r14 = r64
            r15 = 0
            r0 = r65[r15]
            com.itextpdf.text.pdf.PdfFont r0 = (com.itextpdf.text.pdf.PdfFont) r0
            r12 = 1
            r1 = r65[r12]
            java.lang.Float r1 = (java.lang.Float) r1
            float r1 = r1.floatValue()
            float r2 = r63.getXTLM()
            float r3 = r62.getOriginalWidth()
            float r2 = r2 + r3
            int r13 = r62.numberOfSpaces()
            int r3 = r62.getLineLengthUtf32()
            boolean r4 = r62.hasToBeJustified()
            if (r4 == 0) goto L_0x0034
            if (r13 != 0) goto L_0x0031
            if (r3 <= r12) goto L_0x0034
        L_0x0031:
            r26 = 1
            goto L_0x0036
        L_0x0034:
            r26 = 0
        L_0x0036:
            int r4 = r62.getSeparatorCount()
            r11 = 1065353216(0x3f800000, float:1.0)
            r10 = 0
            if (r4 <= 0) goto L_0x0050
            float r3 = r62.widthLeft()
            float r4 = (float) r4
            float r3 = r3 / r4
            r27 = r1
            r28 = r2
            r4 = r3
        L_0x004a:
            r5 = 0
            r6 = 0
        L_0x004c:
            r29 = 0
            goto L_0x00eb
        L_0x0050:
            if (r26 == 0) goto L_0x00d6
            if (r4 != 0) goto L_0x00d6
            boolean r4 = r62.isNewlineSplit()
            if (r4 == 0) goto L_0x0082
            float r4 = r62.widthLeft()
            float r5 = (float) r13
            float r5 = r5 * r66
            float r6 = (float) r3
            float r5 = r5 + r6
            float r5 = r5 - r11
            float r5 = r5 * r1
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 < 0) goto L_0x0082
            boolean r3 = r62.isRTL()
            if (r3 == 0) goto L_0x0078
            float r3 = r62.widthLeft()
            float r3 = r3 - r5
            r9.moveText(r3, r10)
        L_0x0078:
            float r3 = r66 * r1
            r6 = r1
            r27 = r6
            r28 = r2
            r5 = r3
            r4 = 0
            goto L_0x004c
        L_0x0082:
            float r1 = r62.widthLeft()
            int r4 = r62.size()
            int r4 = r4 - r12
            com.itextpdf.text.pdf.PdfChunk r4 = r8.getChunk(r4)
            if (r4 == 0) goto L_0x00c2
            java.lang.String r5 = r4.toString()
            int r6 = r5.length()
            if (r6 <= 0) goto L_0x00c2
            int r6 = r5.length()
            int r6 = r6 - r12
            char r5 = r5.charAt(r6)
            java.lang.String r6 = ".,;:'"
            int r6 = r6.indexOf(r5)
            if (r6 < 0) goto L_0x00c2
            com.itextpdf.text.pdf.PdfFont r4 = r4.font()
            float r4 = r4.width((int) r5)
            r5 = 1053609165(0x3ecccccd, float:0.4)
            float r4 = r4 * r5
            float r4 = r4 + r1
            float r1 = r4 - r1
            r60 = r4
            r4 = r1
            r1 = r60
            goto L_0x00c3
        L_0x00c2:
            r4 = 0
        L_0x00c3:
            float r5 = (float) r13
            float r5 = r5 * r66
            float r3 = (float) r3
            float r5 = r5 + r3
            float r5 = r5 - r11
            float r1 = r1 / r5
            float r3 = r66 * r1
            r6 = r1
            r27 = r6
            r28 = r2
            r5 = r3
            r29 = r4
            r4 = 0
            goto L_0x00eb
        L_0x00d6:
            int r3 = r8.alignment
            if (r3 == 0) goto L_0x00df
            int r3 = r8.alignment
            r4 = -1
            if (r3 != r4) goto L_0x00e4
        L_0x00df:
            float r3 = r62.widthLeft()
            float r2 = r2 - r3
        L_0x00e4:
            r27 = r1
            r28 = r2
            r4 = 0
            goto L_0x004a
        L_0x00eb:
            int r3 = r62.getLastStrokeChunk()
            float r30 = r63.getXTLM()
            float r2 = r63.getYTLM()
            java.util.Iterator r31 = r62.iterator()
            r1 = 2143289344(0x7fc00000, float:NaN)
            r1 = r0
            r16 = r30
            r0 = 0
            r17 = 0
            r18 = 0
            r32 = 2143289344(0x7fc00000, float:NaN)
            r33 = 0
        L_0x0109:
            boolean r19 = r31.hasNext()
            if (r19 == 0) goto L_0x0948
            java.lang.Object r19 = r31.next()
            r11 = r19
            com.itextpdf.text.pdf.PdfChunk r11 = (com.itextpdf.text.pdf.PdfChunk) r11
            com.itextpdf.text.pdf.PdfWriter r10 = r7.writer
            boolean r10 = isTagged(r10)
            if (r10 == 0) goto L_0x012b
            com.itextpdf.text.pdf.interfaces.IAccessibleElement r10 = r11.accessibleElement
            if (r10 == 0) goto L_0x012b
            com.itextpdf.text.pdf.interfaces.IAccessibleElement r10 = r11.accessibleElement
            r9.openMCBlock(r10)
            r34 = 1
            goto L_0x012d
        L_0x012b:
            r34 = r18
        L_0x012d:
            com.itextpdf.text.BaseColor r10 = r11.color()
            com.itextpdf.text.pdf.PdfFont r18 = r11.font()
            float r15 = r18.size()
            boolean r18 = r11.isImage()
            if (r18 == 0) goto L_0x014b
            float r18 = r11.height()
            r23 = r4
            r24 = r18
            r12 = 0
            r18 = r1
            goto L_0x016c
        L_0x014b:
            com.itextpdf.text.pdf.PdfFont r18 = r11.font()
            com.itextpdf.text.pdf.BaseFont r12 = r18.getFont()
            r18 = r1
            r1 = 1
            float r12 = r12.getFontDescriptor(r1, r15)
            com.itextpdf.text.pdf.PdfFont r1 = r11.font()
            com.itextpdf.text.pdf.BaseFont r1 = r1.getFont()
            r23 = r4
            r4 = 3
            float r1 = r1.getFontDescriptor(r4, r15)
            r24 = r12
            r12 = r1
        L_0x016c:
            java.lang.String r4 = "HSCALE"
            java.lang.String r1 = "SKEW"
            r25 = r13
            java.lang.String r13 = "WORD_SPACING"
            r35 = r10
            java.lang.String r10 = "CHAR_SPACING"
            r36 = r10
            if (r0 > r3) goto L_0x0706
            if (r26 == 0) goto L_0x0183
            float r37 = r11.getWidthCorrected(r6, r5)
            goto L_0x0187
        L_0x0183:
            float r37 = r11.width()
        L_0x0187:
            boolean r38 = r11.isStroked()
            if (r38 == 0) goto L_0x06de
            int r10 = r0 + 1
            com.itextpdf.text.pdf.PdfChunk r10 = r8.getChunk(r10)
            boolean r39 = r11.isSeparator()
            if (r39 == 0) goto L_0x0208
            r39 = r0
            java.lang.String r0 = "SEPARATOR"
            java.lang.Object r0 = r11.getAttribute(r0)
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            r21 = 0
            r37 = r0[r21]
            com.itextpdf.text.pdf.draw.DrawInterface r37 = (com.itextpdf.text.pdf.draw.DrawInterface) r37
            r22 = 1
            r0 = r0[r22]
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x01e4
            float r40 = r2 + r12
            float r0 = r62.getOriginalWidth()
            float r41 = r30 + r0
            float r42 = r24 - r12
            r0 = r37
            r8 = r1
            r43 = r18
            r1 = r64
            r44 = r2
            r2 = r30
            r45 = r3
            r3 = r40
            r46 = r4
            r18 = r13
            r13 = r23
            r4 = r41
            r47 = r5
            r5 = r42
            r48 = r6
            r6 = r44
            r0.draw(r1, r2, r3, r4, r5, r6)
            goto L_0x0205
        L_0x01e4:
            r8 = r1
            r45 = r3
            r46 = r4
            r47 = r5
            r48 = r6
            r43 = r18
            r6 = r2
            r18 = r13
            r13 = r23
            float r3 = r6 + r12
            float r4 = r16 + r13
            float r5 = r24 - r12
            r0 = r37
            r1 = r64
            r2 = r16
            r44 = r6
            r0.draw(r1, r2, r3, r4, r5, r6)
        L_0x0205:
            r37 = r13
            goto L_0x021b
        L_0x0208:
            r39 = r0
            r8 = r1
            r44 = r2
            r45 = r3
            r46 = r4
            r47 = r5
            r48 = r6
            r43 = r18
            r18 = r13
            r13 = r23
        L_0x021b:
            boolean r0 = r11.isTab()
            if (r0 == 0) goto L_0x028f
            java.lang.String r0 = "TABSETTINGS"
            boolean r0 = r11.isAttribute(r0)
            if (r0 == 0) goto L_0x0252
            com.itextpdf.text.TabStop r0 = r11.getTabStop()
            if (r0 == 0) goto L_0x024f
            float r1 = r0.getPosition()
            float r17 = r1 + r30
            com.itextpdf.text.pdf.draw.DrawInterface r1 = r0.getLeader()
            if (r1 == 0) goto L_0x028a
            com.itextpdf.text.pdf.draw.DrawInterface r0 = r0.getLeader()
            r6 = r44
            float r3 = r6 + r12
            float r5 = r24 - r12
            r1 = r64
            r2 = r16
            r4 = r17
            r0.draw(r1, r2, r3, r4, r5, r6)
            goto L_0x028a
        L_0x024f:
            r17 = r16
            goto L_0x028a
        L_0x0252:
            java.lang.String r0 = "TAB"
            java.lang.Object r0 = r11.getAttribute(r0)
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            r1 = 0
            r2 = r0[r1]
            r1 = r2
            com.itextpdf.text.pdf.draw.DrawInterface r1 = (com.itextpdf.text.pdf.draw.DrawInterface) r1
            r2 = 1
            r3 = r0[r2]
            java.lang.Float r3 = (java.lang.Float) r3
            float r2 = r3.floatValue()
            r3 = 3
            r0 = r0[r3]
            java.lang.Float r0 = (java.lang.Float) r0
            float r0 = r0.floatValue()
            float r17 = r2 + r0
            int r0 = (r17 > r16 ? 1 : (r17 == r16 ? 0 : -1))
            if (r0 <= 0) goto L_0x028a
            r6 = r44
            float r3 = r6 + r12
            float r5 = r24 - r12
            r0 = r1
            r1 = r64
            r2 = r16
            r4 = r17
            r0.draw(r1, r2, r3, r4, r5, r6)
        L_0x028a:
            r40 = r16
            r6 = r17
            goto L_0x0293
        L_0x028f:
            r6 = r16
            r40 = r17
        L_0x0293:
            java.lang.String r0 = "BACKGROUND"
            boolean r1 = r11.isAttribute(r0)
            if (r1 == 0) goto L_0x0325
            java.lang.Object r1 = r11.getAttribute(r0)
            java.lang.Object[] r1 = (java.lang.Object[]) r1
            java.lang.Object[] r1 = (java.lang.Object[]) r1
            r2 = 0
            r3 = r1[r2]
            if (r3 == 0) goto L_0x0325
            boolean r2 = r64.getInText()
            if (r2 == 0) goto L_0x02b9
            com.itextpdf.text.pdf.PdfWriter r3 = r7.writer
            boolean r3 = isTagged(r3)
            if (r3 == 0) goto L_0x02b9
            r64.endText()
        L_0x02b9:
            r64.saveState()
            if (r10 == 0) goto L_0x02c6
            boolean r0 = r10.isAttribute(r0)
            if (r0 == 0) goto L_0x02c6
            r0 = 0
            goto L_0x02c8
        L_0x02c6:
            r0 = r27
        L_0x02c8:
            if (r10 != 0) goto L_0x02cc
            float r0 = r0 + r29
        L_0x02cc:
            r3 = 0
            r4 = r1[r3]
            com.itextpdf.text.BaseColor r4 = (com.itextpdf.text.BaseColor) r4
            r14.setColorFill(r4)
            r4 = 1
            r1 = r1[r4]
            float[] r1 = (float[]) r1
            float[] r1 = (float[]) r1
            r5 = r1[r3]
            float r5 = r6 - r5
            r3 = r44
            float r16 = r3 + r12
            r17 = r1[r4]
            float r16 = r16 - r17
            float r17 = r11.getTextRise()
            float r4 = r16 + r17
            float r0 = r37 - r0
            r16 = 0
            r17 = r1[r16]
            float r0 = r0 + r17
            r16 = 2
            r17 = r1[r16]
            float r0 = r0 + r17
            float r16 = r24 - r12
            r17 = 1
            r23 = r1[r17]
            float r16 = r16 + r23
            r17 = 3
            r1 = r1[r17]
            float r1 = r16 + r1
            r14.rectangle((float) r5, (float) r4, (float) r0, (float) r1)
            r64.fill()
            r0 = 0
            r14.setGrayFill(r0)
            r64.restoreState()
            if (r2 == 0) goto L_0x0327
            com.itextpdf.text.pdf.PdfWriter r0 = r7.writer
            boolean r0 = isTagged(r0)
            if (r0 == 0) goto L_0x0327
            r0 = 1
            r14.beginText(r0)
            goto L_0x0327
        L_0x0325:
            r3 = r44
        L_0x0327:
            java.lang.String r0 = "UNDERLINE"
            boolean r1 = r11.isAttribute(r0)
            r16 = 4
            if (r1 == 0) goto L_0x03da
            boolean r1 = r64.getInText()
            if (r1 == 0) goto L_0x0342
            com.itextpdf.text.pdf.PdfWriter r2 = r7.writer
            boolean r2 = isTagged(r2)
            if (r2 == 0) goto L_0x0342
            r64.endText()
        L_0x0342:
            if (r10 == 0) goto L_0x034c
            boolean r2 = r10.isAttribute(r0)
            if (r2 == 0) goto L_0x034c
            r2 = 0
            goto L_0x034e
        L_0x034c:
            r2 = r27
        L_0x034e:
            if (r10 != 0) goto L_0x0352
            float r2 = r2 + r29
        L_0x0352:
            java.lang.Object r0 = r11.getAttribute(r0)
            java.lang.Object[][] r0 = (java.lang.Object[][]) r0
            java.lang.Object[][] r0 = (java.lang.Object[][]) r0
            r4 = 0
        L_0x035b:
            int r5 = r0.length
            if (r4 >= r5) goto L_0x03c2
            r5 = r0[r4]
            r17 = 0
            r23 = r5[r17]
            com.itextpdf.text.BaseColor r23 = (com.itextpdf.text.BaseColor) r23
            r17 = 1
            r5 = r5[r17]
            float[] r5 = (float[]) r5
            float[] r5 = (float[]) r5
            r41 = r0
            if (r23 != 0) goto L_0x0375
            r0 = r35
            goto L_0x0377
        L_0x0375:
            r0 = r23
        L_0x0377:
            if (r0 == 0) goto L_0x037c
            r14.setColorStroke(r0)
        L_0x037c:
            r21 = 0
            r23 = r5[r21]
            r42 = r5[r17]
            float r42 = r42 * r15
            r44 = r13
            float r13 = r23 + r42
            r14.setLineWidth((float) r13)
            r13 = 2
            r17 = r5[r13]
            r13 = 3
            r23 = r5[r13]
            float r23 = r23 * r15
            float r17 = r17 + r23
            r5 = r5[r16]
            int r5 = (int) r5
            if (r5 == 0) goto L_0x039d
            r14.setLineCap(r5)
        L_0x039d:
            float r13 = r3 + r17
            r14.moveTo((float) r6, (float) r13)
            float r17 = r6 + r37
            r42 = r8
            float r8 = r17 - r2
            r14.lineTo((float) r8, (float) r13)
            r64.stroke()
            if (r0 == 0) goto L_0x03b3
            r64.resetGrayStroke()
        L_0x03b3:
            if (r5 == 0) goto L_0x03b9
            r0 = 0
            r14.setLineCap(r0)
        L_0x03b9:
            int r4 = r4 + 1
            r0 = r41
            r8 = r42
            r13 = r44
            goto L_0x035b
        L_0x03c2:
            r42 = r8
            r44 = r13
            r8 = 1065353216(0x3f800000, float:1.0)
            r14.setLineWidth((float) r8)
            if (r1 == 0) goto L_0x03e0
            com.itextpdf.text.pdf.PdfWriter r0 = r7.writer
            boolean r0 = isTagged(r0)
            if (r0 == 0) goto L_0x03e0
            r0 = 1
            r14.beginText(r0)
            goto L_0x03e0
        L_0x03da:
            r42 = r8
            r44 = r13
            r8 = 1065353216(0x3f800000, float:1.0)
        L_0x03e0:
            java.lang.String r0 = "ACTION"
            boolean r1 = r11.isAttribute(r0)
            if (r1 == 0) goto L_0x049a
            if (r10 == 0) goto L_0x03f2
            boolean r1 = r10.isAttribute(r0)
            if (r1 == 0) goto L_0x03f2
            r1 = 0
            goto L_0x03f4
        L_0x03f2:
            r1 = r27
        L_0x03f4:
            if (r10 != 0) goto L_0x03f8
            float r1 = r1 + r29
        L_0x03f8:
            boolean r2 = r11.isImage()
            if (r2 == 0) goto L_0x042e
            com.itextpdf.text.pdf.PdfWriter r2 = r7.writer
            float r4 = r11.getImageOffsetY()
            float r4 = r4 + r3
            float r5 = r6 + r37
            float r5 = r5 - r1
            float r1 = r11.getImageHeight()
            float r1 = r1 + r3
            float r13 = r11.getImageOffsetY()
            float r13 = r13 + r1
            java.lang.Object r0 = r11.getAttribute(r0)
            r17 = r0
            com.itextpdf.text.pdf.PdfAction r17 = (com.itextpdf.text.pdf.PdfAction) r17
            r20 = 0
            r0 = r2
            r1 = r6
            r2 = r4
            r4 = r3
            r3 = r5
            r5 = r4
            r4 = r13
            r13 = r5
            r5 = r17
            r8 = r6
            r6 = r20
            com.itextpdf.text.pdf.PdfAnnotation r0 = r0.createAnnotation(r1, r2, r3, r4, r5, r6)
            goto L_0x0459
        L_0x042e:
            r13 = r3
            r8 = r6
            com.itextpdf.text.pdf.PdfWriter r2 = r7.writer
            float r3 = r13 + r12
            float r4 = r11.getTextRise()
            float r3 = r3 + r4
            float r6 = r8 + r37
            float r4 = r6 - r1
            float r1 = r13 + r24
            float r5 = r11.getTextRise()
            float r5 = r5 + r1
            java.lang.Object r0 = r11.getAttribute(r0)
            r6 = r0
            com.itextpdf.text.pdf.PdfAction r6 = (com.itextpdf.text.pdf.PdfAction) r6
            r20 = 0
            r0 = r2
            r1 = r8
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r20
            com.itextpdf.text.pdf.PdfAnnotation r0 = r0.createAnnotation(r1, r2, r3, r4, r5, r6)
        L_0x0459:
            r1 = 1
            r9.addAnnotation(r0, r1)
            com.itextpdf.text.pdf.PdfWriter r1 = r7.writer
            boolean r1 = isTagged(r1)
            if (r1 == 0) goto L_0x049c
            com.itextpdf.text.pdf.interfaces.IAccessibleElement r1 = r11.accessibleElement
            if (r1 == 0) goto L_0x049c
            com.itextpdf.text.pdf.interfaces.IAccessibleElement r1 = r11.accessibleElement
            com.itextpdf.text.AccessibleElementId r1 = r1.getId()
            com.itextpdf.text.pdf.PdfStructureElement r1 = r7.getStructElement(r1)
            if (r1 == 0) goto L_0x049c
            int r2 = r7.getStructParentIndex(r0)
            com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.STRUCTPARENT
            com.itextpdf.text.pdf.PdfNumber r4 = new com.itextpdf.text.pdf.PdfNumber
            r4.<init>((int) r2)
            r0.put(r3, r4)
            com.itextpdf.text.pdf.PdfWriter r3 = r7.writer
            com.itextpdf.text.pdf.PdfIndirectReference r3 = r3.getCurrentPage()
            r1.setAnnotation(r0, r3)
            com.itextpdf.text.pdf.PdfWriter r0 = r7.writer
            com.itextpdf.text.pdf.PdfStructureTreeRoot r0 = r0.getStructureTreeRoot()
            com.itextpdf.text.pdf.PdfIndirectReference r1 = r1.getReference()
            r0.setAnnotationMark(r2, r1)
            goto L_0x049c
        L_0x049a:
            r13 = r3
            r8 = r6
        L_0x049c:
            java.lang.String r0 = "REMOTEGOTO"
            boolean r1 = r11.isAttribute(r0)
            if (r1 == 0) goto L_0x050d
            if (r10 == 0) goto L_0x04ae
            boolean r1 = r10.isAttribute(r0)
            if (r1 == 0) goto L_0x04ae
            r1 = 0
            goto L_0x04b0
        L_0x04ae:
            r1 = r27
        L_0x04b0:
            if (r10 != 0) goto L_0x04b4
            float r1 = r1 + r29
        L_0x04b4:
            java.lang.Object r0 = r11.getAttribute(r0)
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            r2 = 0
            r3 = r0[r2]
            r2 = r3
            java.lang.String r2 = (java.lang.String) r2
            r3 = 1
            r4 = r0[r3]
            boolean r4 = r4 instanceof java.lang.String
            if (r4 == 0) goto L_0x04ea
            r0 = r0[r3]
            r3 = r0
            java.lang.String r3 = (java.lang.String) r3
            float r0 = r13 + r12
            float r4 = r11.getTextRise()
            float r4 = r4 + r0
            float r6 = r8 + r37
            float r5 = r6 - r1
            float r0 = r13 + r24
            float r1 = r11.getTextRise()
            float r6 = r0 + r1
            r0 = r61
            r1 = r2
            r2 = r3
            r3 = r8
            r0.remoteGoto((java.lang.String) r1, (java.lang.String) r2, (float) r3, (float) r4, (float) r5, (float) r6)
            goto L_0x050d
        L_0x04ea:
            r0 = r0[r3]
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r3 = r0.intValue()
            float r0 = r13 + r12
            float r4 = r11.getTextRise()
            float r4 = r4 + r0
            float r6 = r8 + r37
            float r5 = r6 - r1
            float r0 = r13 + r24
            float r1 = r11.getTextRise()
            float r6 = r0 + r1
            r0 = r61
            r1 = r2
            r2 = r3
            r3 = r8
            r0.remoteGoto((java.lang.String) r1, (int) r2, (float) r3, (float) r4, (float) r5, (float) r6)
        L_0x050d:
            java.lang.String r0 = "LOCALGOTO"
            boolean r1 = r11.isAttribute(r0)
            if (r1 == 0) goto L_0x053a
            if (r10 == 0) goto L_0x051f
            boolean r1 = r10.isAttribute(r0)
            if (r1 == 0) goto L_0x051f
            r1 = 0
            goto L_0x0521
        L_0x051f:
            r1 = r27
        L_0x0521:
            if (r10 != 0) goto L_0x0525
            float r1 = r1 + r29
        L_0x0525:
            java.lang.Object r0 = r11.getAttribute(r0)
            r2 = r0
            java.lang.String r2 = (java.lang.String) r2
            float r6 = r8 + r37
            float r4 = r6 - r1
            float r5 = r13 + r15
            r0 = r61
            r1 = r2
            r2 = r8
            r3 = r13
            r0.localGoto(r1, r2, r3, r4, r5)
        L_0x053a:
            java.lang.String r0 = "LOCALDESTINATION"
            boolean r1 = r11.isAttribute(r0)
            if (r1 == 0) goto L_0x0555
            java.lang.Object r0 = r11.getAttribute(r0)
            java.lang.String r0 = (java.lang.String) r0
            com.itextpdf.text.pdf.PdfDestination r1 = new com.itextpdf.text.pdf.PdfDestination
            float r2 = r13 + r15
            r3 = 0
            r6 = 0
            r1.<init>(r3, r8, r2, r6)
            r7.localDestination(r0, r1)
            goto L_0x0556
        L_0x0555:
            r6 = 0
        L_0x0556:
            java.lang.String r0 = "GENERICTAG"
            boolean r1 = r11.isAttribute(r0)
            if (r1 == 0) goto L_0x058b
            if (r10 == 0) goto L_0x0568
            boolean r1 = r10.isAttribute(r0)
            if (r1 == 0) goto L_0x0568
            r1 = 0
            goto L_0x056a
        L_0x0568:
            r1 = r27
        L_0x056a:
            if (r10 != 0) goto L_0x056e
            float r1 = r1 + r29
        L_0x056e:
            com.itextpdf.text.Rectangle r2 = new com.itextpdf.text.Rectangle
            float r3 = r8 + r37
            float r3 = r3 - r1
            float r1 = r13 + r15
            r2.<init>(r8, r13, r3, r1)
            com.itextpdf.text.pdf.PdfWriter r1 = r7.writer
            com.itextpdf.text.pdf.PdfPageEvent r1 = r1.getPageEvent()
            if (r1 == 0) goto L_0x058b
            com.itextpdf.text.pdf.PdfWriter r3 = r7.writer
            java.lang.Object r0 = r11.getAttribute(r0)
            java.lang.String r0 = (java.lang.String) r0
            r1.onGenericTag(r3, r7, r2, r0)
        L_0x058b:
            java.lang.String r0 = "PDFANNOTATION"
            boolean r1 = r11.isAttribute(r0)
            if (r1 == 0) goto L_0x05c2
            if (r10 == 0) goto L_0x059d
            boolean r1 = r10.isAttribute(r0)
            if (r1 == 0) goto L_0x059d
            r1 = 0
            goto L_0x059f
        L_0x059d:
            r1 = r27
        L_0x059f:
            if (r10 != 0) goto L_0x05a3
            float r1 = r1 + r29
        L_0x05a3:
            java.lang.Object r0 = r11.getAttribute(r0)
            com.itextpdf.text.pdf.PdfAnnotation r0 = (com.itextpdf.text.pdf.PdfAnnotation) r0
            com.itextpdf.text.pdf.PdfAnnotation r0 = com.itextpdf.text.pdf.PdfFormField.shallowDuplicate(r0)
            com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.RECT
            com.itextpdf.text.pdf.PdfRectangle r3 = new com.itextpdf.text.pdf.PdfRectangle
            float r4 = r13 + r12
            float r5 = r8 + r37
            float r5 = r5 - r1
            float r1 = r13 + r24
            r3.<init>(r8, r4, r5, r1)
            r0.put(r2, r3)
            r1 = 1
            r9.addAnnotation(r0, r1)
        L_0x05c2:
            r15 = r42
            java.lang.Object r0 = r11.getAttribute(r15)
            float[] r0 = (float[]) r0
            float[] r0 = (float[]) r0
            r12 = r46
            java.lang.Object r1 = r11.getAttribute(r12)
            java.lang.Float r1 = (java.lang.Float) r1
            if (r0 != 0) goto L_0x05de
            if (r1 == 0) goto L_0x05d9
            goto L_0x05de
        L_0x05d9:
            r0 = 1065353216(0x3f800000, float:1.0)
            r19 = 0
            goto L_0x0602
        L_0x05de:
            if (r0 == 0) goto L_0x05e9
            r2 = 0
            r3 = r0[r2]
            r2 = 1
            r0 = r0[r2]
            r2 = r3
            r3 = r0
            goto L_0x05eb
        L_0x05e9:
            r2 = 0
            r3 = 0
        L_0x05eb:
            if (r1 == 0) goto L_0x05f3
            float r0 = r1.floatValue()
            r10 = r0
            goto L_0x05f5
        L_0x05f3:
            r10 = 1065353216(0x3f800000, float:1.0)
        L_0x05f5:
            r4 = 1065353216(0x3f800000, float:1.0)
            r0 = r63
            r1 = r10
            r5 = r8
            r19 = 0
            r6 = r13
            r0.setTextMatrix(r1, r2, r3, r4, r5, r6)
            r0 = r10
        L_0x0602:
            r1 = r18
            if (r26 != 0) goto L_0x062f
            boolean r2 = r11.isAttribute(r1)
            if (r2 == 0) goto L_0x0619
            java.lang.Object r2 = r11.getAttribute(r1)
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            r9.setWordSpacing(r2)
        L_0x0619:
            r2 = r36
            boolean r3 = r11.isAttribute(r2)
            if (r3 == 0) goto L_0x0631
            java.lang.Object r3 = r11.getAttribute(r2)
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
            r9.setCharacterSpacing(r3)
            goto L_0x0631
        L_0x062f:
            r2 = r36
        L_0x0631:
            boolean r3 = r11.isImage()
            if (r3 == 0) goto L_0x06be
            com.itextpdf.text.Image r3 = r11.getImage()
            float r37 = r11.getImageWidth()
            float r4 = r11.getImageScalePercentage()
            float[] r4 = r3.matrix(r4)
            float r5 = r11.getImageOffsetX()
            float r6 = r8 + r5
            r5 = r4[r16]
            float r6 = r6 - r5
            r4[r16] = r6
            float r5 = r11.getImageOffsetY()
            float r5 = r5 + r13
            r6 = 5
            r10 = r4[r6]
            float r5 = r5 - r10
            r4[r6] = r5
            r5 = 0
            r10 = r4[r5]
            double r5 = (double) r10
            r20 = 1
            r10 = r4[r20]
            r36 = r0
            r22 = r1
            double r0 = (double) r10
            r23 = r2
            r10 = 2
            r2 = r4[r10]
            r38 = r8
            double r7 = (double) r2
            r2 = 3
            r10 = r4[r2]
            r24 = r3
            double r2 = (double) r10
            r10 = r4[r16]
            r49 = r2
            double r2 = (double) r10
            r10 = 5
            r4 = r4[r10]
            r51 = r2
            double r2 = (double) r4
            r4 = 0
            r54 = r23
            r53 = r35
            r10 = r64
            r66 = r11
            r11 = r24
            r58 = r12
            r56 = r13
            r59 = r22
            r35 = r25
            r57 = r44
            r41 = 3
            r12 = r5
            r6 = r15
            r5 = 0
            r14 = r0
            r16 = r7
            r18 = r49
            r20 = r51
            r22 = r2
            r24 = r4
            r25 = r34
            r10.addImage(r11, r12, r14, r16, r18, r20, r22, r24, r25)
            float r0 = r38 + r27
            float r1 = r66.getImageWidth()
            float r0 = r0 + r1
            float r1 = r63.getXTLM()
            float r0 = r0 - r1
            r1 = 0
            r9.moveText(r0, r1)
            goto L_0x06d7
        L_0x06be:
            r36 = r0
            r59 = r1
            r54 = r2
            r38 = r8
            r66 = r11
            r58 = r12
            r56 = r13
            r6 = r15
            r53 = r35
            r57 = r44
            r1 = 0
            r5 = 0
            r41 = 3
            r35 = r25
        L_0x06d7:
            r11 = r36
            r16 = r38
            r17 = r40
            goto L_0x06ff
        L_0x06de:
            r39 = r0
            r56 = r2
            r45 = r3
            r58 = r4
            r47 = r5
            r48 = r6
            r66 = r11
            r59 = r13
            r43 = r18
            r57 = r23
            r53 = r35
            r54 = r36
            r5 = 0
            r41 = 3
            r6 = r1
            r35 = r25
            r1 = 0
            r11 = 1065353216(0x3f800000, float:1.0)
        L_0x06ff:
            float r16 = r16 + r37
            int r0 = r39 + 1
            r2 = r16
            goto L_0x0729
        L_0x0706:
            r39 = r0
            r56 = r2
            r45 = r3
            r58 = r4
            r47 = r5
            r48 = r6
            r66 = r11
            r59 = r13
            r43 = r18
            r57 = r23
            r53 = r35
            r54 = r36
            r5 = 0
            r41 = 3
            r6 = r1
            r35 = r25
            r1 = 0
            r2 = r16
            r11 = 1065353216(0x3f800000, float:1.0)
        L_0x0729:
            boolean r3 = r66.isImage()
            if (r3 != 0) goto L_0x074b
            com.itextpdf.text.pdf.PdfFont r3 = r66.font()
            r4 = r43
            int r3 = r3.compareTo((com.itextpdf.text.pdf.PdfFont) r4)
            if (r3 == 0) goto L_0x074d
            com.itextpdf.text.pdf.PdfFont r3 = r66.font()
            com.itextpdf.text.pdf.BaseFont r4 = r3.getFont()
            float r7 = r3.size()
            r9.setFontAndSize(r4, r7)
            goto L_0x074e
        L_0x074b:
            r4 = r43
        L_0x074d:
            r3 = r4
        L_0x074e:
            java.lang.String r4 = "TEXTRENDERMODE"
            r7 = r66
            java.lang.Object r4 = r7.getAttribute(r4)
            java.lang.Object[] r4 = (java.lang.Object[]) r4
            java.lang.Object[] r4 = (java.lang.Object[]) r4
            r8 = 0
            java.lang.String r10 = "SUBSUPSCRIPT"
            java.lang.Object r10 = r7.getAttribute(r10)
            java.lang.Float r10 = (java.lang.Float) r10
            if (r4 == 0) goto L_0x07a0
            r12 = r4[r5]
            java.lang.Integer r12 = (java.lang.Integer) r12
            int r12 = r12.intValue()
            r15 = r12 & 3
            if (r15 == 0) goto L_0x0774
            r9.setTextRenderingMode(r15)
        L_0x0774:
            r12 = 1
            r13 = 2
            if (r15 == r12) goto L_0x077e
            if (r15 != r13) goto L_0x077b
            goto L_0x077e
        L_0x077b:
            r14 = 1065353216(0x3f800000, float:1.0)
            goto L_0x07a4
        L_0x077e:
            r8 = r4[r12]
            java.lang.Float r8 = (java.lang.Float) r8
            float r8 = r8.floatValue()
            r14 = 1065353216(0x3f800000, float:1.0)
            int r16 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1))
            if (r16 == 0) goto L_0x078f
            r9.setLineWidth((float) r8)
        L_0x078f:
            r4 = r4[r13]
            com.itextpdf.text.BaseColor r4 = (com.itextpdf.text.BaseColor) r4
            if (r4 != 0) goto L_0x0797
            r4 = r53
        L_0x0797:
            if (r4 == 0) goto L_0x079c
            r9.setColorStroke(r4)
        L_0x079c:
            r55 = r8
            r8 = r4
            goto L_0x07a6
        L_0x07a0:
            r12 = 1
            r14 = 1065353216(0x3f800000, float:1.0)
            r15 = 0
        L_0x07a4:
            r55 = 1065353216(0x3f800000, float:1.0)
        L_0x07a6:
            if (r10 == 0) goto L_0x07af
            float r10 = r10.floatValue()
            r4 = r53
            goto L_0x07b2
        L_0x07af:
            r4 = r53
            r10 = 0
        L_0x07b2:
            if (r4 == 0) goto L_0x07b7
            r9.setColorFill(r4)
        L_0x07b7:
            int r13 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r13 == 0) goto L_0x07be
            r9.setTextRise((float) r10)
        L_0x07be:
            boolean r10 = r7.isImage()
            if (r10 == 0) goto L_0x07d0
            r66 = r0
            r16 = r3
            r10 = r47
            r1 = r48
            r23 = r57
            goto L_0x08c6
        L_0x07d0:
            boolean r10 = r7.isHorizontalSeparator()
            r16 = 1148846080(0x447a0000, float:1000.0)
            if (r10 == 0) goto L_0x07f1
            com.itextpdf.text.pdf.PdfTextArray r10 = new com.itextpdf.text.pdf.PdfTextArray
            r10.<init>()
            r12 = r57
            float r14 = -r12
            float r14 = r14 * r16
            com.itextpdf.text.pdf.PdfFont r1 = r7.font
            float r1 = r1.size()
            float r14 = r14 / r1
            float r14 = r14 / r11
            r10.add((float) r14)
            r9.showText((com.itextpdf.text.pdf.PdfTextArray) r10)
            goto L_0x0814
        L_0x07f1:
            r12 = r57
            boolean r1 = r7.isTab()
            if (r1 == 0) goto L_0x0822
            int r1 = (r17 > r2 ? 1 : (r17 == r2 ? 0 : -1))
            if (r1 == 0) goto L_0x0822
            com.itextpdf.text.pdf.PdfTextArray r1 = new com.itextpdf.text.pdf.PdfTextArray
            r1.<init>()
            float r10 = r17 - r2
            float r10 = r10 * r16
            com.itextpdf.text.pdf.PdfFont r14 = r7.font
            float r14 = r14.size()
            float r10 = r10 / r14
            float r10 = r10 / r11
            r1.add((float) r10)
            r9.showText((com.itextpdf.text.pdf.PdfTextArray) r1)
        L_0x0814:
            r66 = r0
            r16 = r3
            r23 = r12
            r12 = r33
            r10 = r47
            r1 = r48
            goto L_0x08c6
        L_0x0822:
            if (r26 == 0) goto L_0x089c
            if (r35 <= 0) goto L_0x089c
            boolean r1 = r7.isSpecialEncoding()
            if (r1 == 0) goto L_0x089c
            int r1 = (r11 > r32 ? 1 : (r11 == r32 ? 0 : -1))
            r10 = r47
            if (r1 == 0) goto L_0x0847
            float r1 = r10 / r11
            r9.setWordSpacing(r1)
            r1 = r48
            float r14 = r1 / r11
            float r18 = r63.getCharacterSpacing()
            float r14 = r14 + r18
            r9.setCharacterSpacing(r14)
            r32 = r11
            goto L_0x0849
        L_0x0847:
            r1 = r48
        L_0x0849:
            java.lang.String r14 = r7.toString()
            r5 = 32
            r66 = r0
            int r0 = r14.indexOf(r5)
            if (r0 >= 0) goto L_0x085f
            r9.showText((java.lang.String) r14)
            r16 = r3
            r23 = r12
            goto L_0x08c4
        L_0x085f:
            float r5 = -r10
            float r5 = r5 * r16
            r16 = r3
            com.itextpdf.text.pdf.PdfFont r3 = r7.font
            float r3 = r3.size()
            float r5 = r5 / r3
            float r5 = r5 / r11
            com.itextpdf.text.pdf.PdfTextArray r3 = new com.itextpdf.text.pdf.PdfTextArray
            r23 = r12
            r11 = 0
            java.lang.String r12 = r14.substring(r11, r0)
            r3.<init>(r12)
        L_0x0878:
            int r11 = r0 + 1
            r12 = 32
            int r11 = r14.indexOf(r12, r11)
            if (r11 < 0) goto L_0x088e
            r3.add((float) r5)
            java.lang.String r0 = r14.substring(r0, r11)
            r3.add((java.lang.String) r0)
            r0 = r11
            goto L_0x0878
        L_0x088e:
            r3.add((float) r5)
            java.lang.String r0 = r14.substring(r0)
            r3.add((java.lang.String) r0)
            r9.showText((com.itextpdf.text.pdf.PdfTextArray) r3)
            goto L_0x08c4
        L_0x089c:
            r66 = r0
            r16 = r3
            r23 = r12
            r10 = r47
            r1 = r48
            if (r26 == 0) goto L_0x08bd
            int r0 = (r11 > r32 ? 1 : (r11 == r32 ? 0 : -1))
            if (r0 == 0) goto L_0x08bd
            float r5 = r10 / r11
            r9.setWordSpacing(r5)
            float r0 = r1 / r11
            float r3 = r63.getCharacterSpacing()
            float r0 = r0 + r3
            r9.setCharacterSpacing(r0)
            r32 = r11
        L_0x08bd:
            java.lang.String r0 = r7.toString()
            r9.showText((java.lang.String) r0)
        L_0x08c4:
            r12 = r33
        L_0x08c6:
            if (r13 == 0) goto L_0x08cc
            r0 = 0
            r9.setTextRise((float) r0)
        L_0x08cc:
            if (r4 == 0) goto L_0x08d1
            r63.resetRGBColorFill()
        L_0x08d1:
            if (r15 == 0) goto L_0x08d7
            r0 = 0
            r9.setTextRenderingMode(r0)
        L_0x08d7:
            if (r8 == 0) goto L_0x08dc
            r63.resetRGBColorStroke()
        L_0x08dc:
            r0 = 1065353216(0x3f800000, float:1.0)
            int r3 = (r55 > r0 ? 1 : (r55 == r0 ? 0 : -1))
            if (r3 == 0) goto L_0x08e5
            r9.setLineWidth((float) r0)
        L_0x08e5:
            boolean r3 = r7.isAttribute(r6)
            if (r3 != 0) goto L_0x08f9
            r3 = r58
            boolean r3 = r7.isAttribute(r3)
            if (r3 == 0) goto L_0x08f4
            goto L_0x08f9
        L_0x08f4:
            r33 = r12
            r3 = r56
            goto L_0x0900
        L_0x08f9:
            r3 = r56
            r9.setTextMatrix(r2, r3)
            r33 = 1
        L_0x0900:
            if (r26 != 0) goto L_0x0918
            r4 = r54
            boolean r4 = r7.isAttribute(r4)
            if (r4 == 0) goto L_0x090d
            r9.setCharacterSpacing(r1)
        L_0x090d:
            r4 = r59
            boolean r4 = r7.isAttribute(r4)
            if (r4 == 0) goto L_0x0918
            r9.setWordSpacing(r10)
        L_0x0918:
            r5 = r61
            com.itextpdf.text.pdf.PdfWriter r4 = r5.writer
            boolean r4 = isTagged(r4)
            if (r4 == 0) goto L_0x092b
            com.itextpdf.text.pdf.interfaces.IAccessibleElement r4 = r7.accessibleElement
            if (r4 == 0) goto L_0x092b
            com.itextpdf.text.pdf.interfaces.IAccessibleElement r4 = r7.accessibleElement
            r9.closeMCBlock(r4)
        L_0x092b:
            r8 = r62
            r14 = r64
            r0 = r66
            r6 = r1
            r7 = r5
            r5 = r10
            r1 = r16
            r4 = r23
            r18 = r34
            r13 = r35
            r10 = 0
            r11 = 1065353216(0x3f800000, float:1.0)
            r12 = 1
            r15 = 0
            r16 = r2
            r2 = r3
            r3 = r45
            goto L_0x0109
        L_0x0948:
            r4 = r1
            r5 = r7
            r0 = 0
            if (r26 == 0) goto L_0x095b
            r9.setWordSpacing(r0)
            r9.setCharacterSpacing(r0)
            boolean r1 = r62.isNewlineSplit()
            if (r1 == 0) goto L_0x095b
            r10 = 0
            goto L_0x095d
        L_0x095b:
            r10 = r27
        L_0x095d:
            if (r33 == 0) goto L_0x0968
            float r1 = r63.getXTLM()
            float r1 = r30 - r1
            r9.moveText(r1, r0)
        L_0x0968:
            r0 = 0
            r65[r0] = r4
            java.lang.Float r0 = new java.lang.Float
            r0.<init>(r10)
            r1 = 1
            r65[r1] = r0
            return r28
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfDocument.writeLineToContent(com.itextpdf.text.pdf.PdfLine, com.itextpdf.text.pdf.PdfContentByte, com.itextpdf.text.pdf.PdfContentByte, java.lang.Object[], float):float");
    }

    /* access modifiers changed from: protected */
    public float indentLeft() {
        return left(this.indentation.indentLeft + this.indentation.listIndentLeft + this.indentation.imageIndentLeft + this.indentation.sectionIndentLeft);
    }

    /* access modifiers changed from: protected */
    public float indentRight() {
        return right(this.indentation.indentRight + this.indentation.sectionIndentRight + this.indentation.imageIndentRight);
    }

    /* access modifiers changed from: protected */
    public float indentTop() {
        return top(this.indentation.indentTop);
    }

    /* access modifiers changed from: package-private */
    public float indentBottom() {
        return bottom(this.indentation.indentBottom);
    }

    /* access modifiers changed from: protected */
    public void addSpacing(float f, float f2, Font font) {
        addSpacing(f, f2, font, false);
    }

    /* access modifiers changed from: protected */
    public void addSpacing(float f, float f2, Font font, boolean z) {
        boolean z2;
        float f3;
        if (f == 0.0f || (z2 = this.pageEmpty)) {
            return;
        }
        if (!z || z2 || this.lines.size() != 0 || this.line.size() != 0) {
            if (z) {
                f3 = f;
            } else {
                f3 = calculateLineHeight();
            }
            if (this.currentHeight + f3 > indentTop() - indentBottom()) {
                newPage();
                return;
            }
            this.leading = f;
            carriageReturn();
            if (font.isUnderlined() || font.isStrikethru()) {
                Font font2 = new Font(font);
                font2.setStyle(font2.getStyle() & -5 & -9);
                font = font2;
            }
            Chunk chunk = new Chunk(" ", font);
            if (z && this.pageEmpty) {
                chunk = new Chunk("", font);
            }
            chunk.process(this);
            carriageReturn();
            this.leading = f2;
        }
    }

    /* access modifiers changed from: package-private */
    public PdfInfo getInfo() {
        return this.info;
    }

    /* access modifiers changed from: package-private */
    public PdfCatalog getCatalog(PdfIndirectReference pdfIndirectReference) {
        PdfCatalog pdfCatalog = new PdfCatalog(pdfIndirectReference, this.writer);
        if (this.rootOutline.getKids().size() > 0) {
            pdfCatalog.put(PdfName.PAGEMODE, PdfName.USEOUTLINES);
            pdfCatalog.put(PdfName.OUTLINES, this.rootOutline.indirectReference());
        }
        this.writer.getPdfVersion().addToCatalog(pdfCatalog);
        this.viewerPreferences.addToCatalog(pdfCatalog);
        if (this.pageLabels != null) {
            pdfCatalog.put(PdfName.PAGELABELS, this.pageLabels.getDictionary(this.writer));
        }
        pdfCatalog.addNames(this.localDestinations, getDocumentLevelJS(), this.documentFileAttachment, this.writer);
        String str = this.openActionName;
        if (str != null) {
            pdfCatalog.setOpenAction(getLocalGotoAction(str));
        } else {
            PdfAction pdfAction = this.openActionAction;
            if (pdfAction != null) {
                pdfCatalog.setOpenAction(pdfAction);
            }
        }
        PdfDictionary pdfDictionary = this.additionalActions;
        if (pdfDictionary != null) {
            pdfCatalog.setAdditionalActions(pdfDictionary);
        }
        if (this.collection != null) {
            pdfCatalog.put(PdfName.COLLECTION, this.collection);
        }
        if (this.annotationsImp.hasValidAcroForm()) {
            try {
                pdfCatalog.put(PdfName.ACROFORM, this.writer.addToBody(this.annotationsImp.getAcroForm()).getIndirectReference());
            } catch (IOException e) {
                throw new ExceptionConverter(e);
            }
        }
        if (this.language != null) {
            pdfCatalog.put(PdfName.LANG, this.language);
        }
        return pdfCatalog;
    }

    /* access modifiers changed from: package-private */
    public void addOutline(PdfOutline pdfOutline, String str) {
        localDestination(str, pdfOutline.getPdfDestination());
    }

    public PdfOutline getRootOutline() {
        return this.rootOutline;
    }

    /* access modifiers changed from: package-private */
    public void calculateOutlineCount() {
        if (this.rootOutline.getKids().size() != 0) {
            traverseOutlineCount(this.rootOutline);
        }
    }

    /* access modifiers changed from: package-private */
    public void traverseOutlineCount(PdfOutline pdfOutline) {
        ArrayList<PdfOutline> kids = pdfOutline.getKids();
        PdfOutline parent = pdfOutline.parent();
        if (!kids.isEmpty()) {
            for (int i = 0; i < kids.size(); i++) {
                traverseOutlineCount(kids.get(i));
            }
            if (parent == null) {
                return;
            }
            if (pdfOutline.isOpen()) {
                parent.setCount(pdfOutline.getCount() + parent.getCount() + 1);
                return;
            }
            parent.setCount(parent.getCount() + 1);
            pdfOutline.setCount(-pdfOutline.getCount());
        } else if (parent != null) {
            parent.setCount(parent.getCount() + 1);
        }
    }

    /* access modifiers changed from: package-private */
    public void writeOutlines() throws IOException {
        if (this.rootOutline.getKids().size() != 0) {
            outlineTree(this.rootOutline);
            PdfWriter pdfWriter = this.writer;
            PdfOutline pdfOutline = this.rootOutline;
            pdfWriter.addToBody((PdfObject) pdfOutline, pdfOutline.indirectReference());
        }
    }

    /* access modifiers changed from: package-private */
    public void outlineTree(PdfOutline pdfOutline) throws IOException {
        pdfOutline.setIndirectReference(this.writer.getPdfIndirectReference());
        if (pdfOutline.parent() != null) {
            pdfOutline.put(PdfName.PARENT, pdfOutline.parent().indirectReference());
        }
        ArrayList<PdfOutline> kids = pdfOutline.getKids();
        int size = kids.size();
        for (int i = 0; i < size; i++) {
            outlineTree(kids.get(i));
        }
        for (int i2 = 0; i2 < size; i2++) {
            if (i2 > 0) {
                kids.get(i2).put(PdfName.PREV, kids.get(i2 - 1).indirectReference());
            }
            if (i2 < size - 1) {
                kids.get(i2).put(PdfName.NEXT, kids.get(i2 + 1).indirectReference());
            }
        }
        if (size > 0) {
            pdfOutline.put(PdfName.FIRST, kids.get(0).indirectReference());
            pdfOutline.put(PdfName.LAST, kids.get(size - 1).indirectReference());
        }
        for (int i3 = 0; i3 < size; i3++) {
            PdfOutline pdfOutline2 = kids.get(i3);
            this.writer.addToBody((PdfObject) pdfOutline2, pdfOutline2.indirectReference());
        }
    }

    /* access modifiers changed from: package-private */
    public void setViewerPreferences(int i) {
        this.viewerPreferences.setViewerPreferences(i);
    }

    /* access modifiers changed from: package-private */
    public void addViewerPreference(PdfName pdfName, PdfObject pdfObject) {
        this.viewerPreferences.addViewerPreference(pdfName, pdfObject);
    }

    /* access modifiers changed from: package-private */
    public void setPageLabels(PdfPageLabels pdfPageLabels) {
        this.pageLabels = pdfPageLabels;
    }

    public PdfPageLabels getPageLabels() {
        return this.pageLabels;
    }

    /* access modifiers changed from: package-private */
    public void localGoto(String str, float f, float f2, float f3, float f4) {
        this.annotationsImp.addPlainAnnotation(this.writer.createAnnotation(f, f2, f3, f4, getLocalGotoAction(str), (PdfName) null));
    }

    /* access modifiers changed from: package-private */
    public void remoteGoto(String str, String str2, float f, float f2, float f3, float f4) {
        this.annotationsImp.addPlainAnnotation(this.writer.createAnnotation(f, f2, f3, f4, new PdfAction(str, str2), (PdfName) null));
    }

    /* access modifiers changed from: package-private */
    public void remoteGoto(String str, int i, float f, float f2, float f3, float f4) {
        addAnnotation(this.writer.createAnnotation(f, f2, f3, f4, new PdfAction(str, i), (PdfName) null));
    }

    /* access modifiers changed from: package-private */
    public void setAction(PdfAction pdfAction, float f, float f2, float f3, float f4) {
        addAnnotation(this.writer.createAnnotation(f, f2, f3, f4, pdfAction, (PdfName) null));
    }

    /* access modifiers changed from: package-private */
    public PdfAction getLocalGotoAction(String str) {
        Destination destination = this.localDestinations.get(str);
        if (destination == null) {
            destination = new Destination();
        }
        if (destination.action != null) {
            return destination.action;
        }
        if (destination.reference == null) {
            destination.reference = this.writer.getPdfIndirectReference();
        }
        PdfAction pdfAction = new PdfAction(destination.reference);
        destination.action = pdfAction;
        this.localDestinations.put(str, destination);
        return pdfAction;
    }

    /* access modifiers changed from: package-private */
    public boolean localDestination(String str, PdfDestination pdfDestination) {
        Destination destination = this.localDestinations.get(str);
        if (destination == null) {
            destination = new Destination();
        }
        if (destination.destination != null) {
            return false;
        }
        destination.destination = pdfDestination;
        this.localDestinations.put(str, destination);
        if (pdfDestination.hasPage()) {
            return true;
        }
        pdfDestination.addPage(this.writer.getCurrentPage());
        return true;
    }

    /* access modifiers changed from: package-private */
    public void addJavaScript(PdfAction pdfAction) {
        if (pdfAction.get(PdfName.f604JS) != null) {
            try {
                HashMap<String, PdfObject> hashMap = this.documentLevelJS;
                DecimalFormat decimalFormat = SIXTEEN_DIGITS;
                int i = this.jsCounter;
                this.jsCounter = i + 1;
                hashMap.put(decimalFormat.format((long) i), this.writer.addToBody(pdfAction).getIndirectReference());
            } catch (IOException e) {
                throw new ExceptionConverter(e);
            }
        } else {
            throw new RuntimeException(MessageLocalization.getComposedMessage("only.javascript.actions.are.allowed", new Object[0]));
        }
    }

    /* access modifiers changed from: package-private */
    public void addJavaScript(String str, PdfAction pdfAction) {
        if (pdfAction.get(PdfName.f604JS) != null) {
            try {
                this.documentLevelJS.put(str, this.writer.addToBody(pdfAction).getIndirectReference());
            } catch (IOException e) {
                throw new ExceptionConverter(e);
            }
        } else {
            throw new RuntimeException(MessageLocalization.getComposedMessage("only.javascript.actions.are.allowed", new Object[0]));
        }
    }

    /* access modifiers changed from: package-private */
    public HashMap<String, PdfObject> getDocumentLevelJS() {
        return this.documentLevelJS;
    }

    /* access modifiers changed from: package-private */
    public void addFileAttachment(String str, PdfFileSpecification pdfFileSpecification) throws IOException {
        if (str == null) {
            PdfString pdfString = (PdfString) pdfFileSpecification.get(PdfName.DESC);
            if (pdfString == null) {
                str = "";
            } else {
                str = PdfEncodings.convertToString(pdfString.getBytes(), (String) null);
            }
        }
        pdfFileSpecification.addDescription(str, true);
        if (str.length() == 0) {
            str = "Unnamed";
        }
        String convertToString = PdfEncodings.convertToString(new PdfString(str, PdfObject.TEXT_UNICODE).getBytes(), (String) null);
        int i = 0;
        while (this.documentFileAttachment.containsKey(convertToString)) {
            i++;
            convertToString = PdfEncodings.convertToString(new PdfString(str + " " + i, PdfObject.TEXT_UNICODE).getBytes(), (String) null);
        }
        this.documentFileAttachment.put(convertToString, pdfFileSpecification.getReference());
    }

    /* access modifiers changed from: package-private */
    public HashMap<String, PdfObject> getDocumentFileAttachment() {
        return this.documentFileAttachment;
    }

    /* access modifiers changed from: package-private */
    public void setOpenAction(String str) {
        this.openActionName = str;
        this.openActionAction = null;
    }

    /* access modifiers changed from: package-private */
    public void setOpenAction(PdfAction pdfAction) {
        this.openActionAction = pdfAction;
        this.openActionName = null;
    }

    /* access modifiers changed from: package-private */
    public void addAdditionalAction(PdfName pdfName, PdfAction pdfAction) {
        if (this.additionalActions == null) {
            this.additionalActions = new PdfDictionary();
        }
        if (pdfAction == null) {
            this.additionalActions.remove(pdfName);
        } else {
            this.additionalActions.put(pdfName, pdfAction);
        }
        if (this.additionalActions.size() == 0) {
            this.additionalActions = null;
        }
    }

    public void setCollection(PdfCollection pdfCollection) {
        this.collection = pdfCollection;
    }

    /* access modifiers changed from: package-private */
    public PdfAcroForm getAcroForm() {
        return this.annotationsImp.getAcroForm();
    }

    /* access modifiers changed from: package-private */
    public void setSigFlags(int i) {
        this.annotationsImp.setSigFlags(i);
    }

    /* access modifiers changed from: package-private */
    public void addCalculationOrder(PdfFormField pdfFormField) {
        this.annotationsImp.addCalculationOrder(pdfFormField);
    }

    /* access modifiers changed from: package-private */
    public void addAnnotation(PdfAnnotation pdfAnnotation) {
        this.pageEmpty = false;
        this.annotationsImp.addAnnotation(pdfAnnotation);
    }

    /* access modifiers changed from: package-private */
    public void setLanguage(String str) {
        this.language = new PdfString(str);
    }

    /* access modifiers changed from: package-private */
    public void setCropBoxSize(Rectangle rectangle) {
        setBoxSize("crop", rectangle);
    }

    /* access modifiers changed from: package-private */
    public void setBoxSize(String str, Rectangle rectangle) {
        if (rectangle == null) {
            this.boxSize.remove(str);
        } else {
            this.boxSize.put(str, new PdfRectangle(rectangle));
        }
    }

    /* access modifiers changed from: protected */
    public void setNewPageSizeAndMargins() {
        this.pageSize = this.nextPageSize;
        if (!this.marginMirroring || (getPageNumber() & 1) != 0) {
            this.marginLeft = this.nextMarginLeft;
            this.marginRight = this.nextMarginRight;
        } else {
            this.marginRight = this.nextMarginLeft;
            this.marginLeft = this.nextMarginRight;
        }
        if (!this.marginMirroringTopBottom || (getPageNumber() & 1) != 0) {
            this.marginTop = this.nextMarginTop;
            this.marginBottom = this.nextMarginBottom;
        } else {
            this.marginTop = this.nextMarginBottom;
            this.marginBottom = this.nextMarginTop;
        }
        if (!isTagged(this.writer)) {
            PdfContentByte pdfContentByte = new PdfContentByte(this.writer);
            this.text = pdfContentByte;
            pdfContentByte.reset();
        } else {
            this.text = this.graphics;
        }
        this.text.beginText();
        this.text.moveText(left(), top());
        if (isTagged(this.writer)) {
            this.textEmptySize = this.text.size();
        }
    }

    /* access modifiers changed from: package-private */
    public Rectangle getBoxSize(String str) {
        PdfRectangle pdfRectangle = this.thisBoxSize.get(str);
        if (pdfRectangle != null) {
            return pdfRectangle.getRectangle();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setPageEmpty(boolean z) {
        this.pageEmpty = z;
    }

    /* access modifiers changed from: package-private */
    public boolean isPageEmpty() {
        if (isTagged(this.writer)) {
            PdfWriter pdfWriter = this.writer;
            if (pdfWriter == null) {
                return true;
            }
            if (pdfWriter.getDirectContent().size(false) == 0 && this.writer.getDirectContentUnder().size(false) == 0 && this.text.size(false) - this.textEmptySize == 0 && (this.pageEmpty || this.writer.isPaused())) {
                return true;
            }
            return false;
        }
        PdfWriter pdfWriter2 = this.writer;
        if (pdfWriter2 == null) {
            return true;
        }
        if (pdfWriter2.getDirectContent().size() == 0 && this.writer.getDirectContentUnder().size() == 0 && (this.pageEmpty || this.writer.isPaused())) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void setDuration(int i) {
        if (i > 0) {
            this.writer.addPageDictEntry(PdfName.DUR, new PdfNumber(i));
        }
    }

    /* access modifiers changed from: package-private */
    public void setTransition(PdfTransition pdfTransition) {
        this.writer.addPageDictEntry(PdfName.TRANS, pdfTransition.getTransitionDictionary());
    }

    /* access modifiers changed from: package-private */
    public void setPageAction(PdfName pdfName, PdfAction pdfAction) {
        if (this.pageAA == null) {
            this.pageAA = new PdfDictionary();
        }
        this.pageAA.put(pdfName, pdfAction);
    }

    /* access modifiers changed from: package-private */
    public void setThumbnail(Image image) throws PdfException, DocumentException {
        PdfWriter pdfWriter = this.writer;
        PdfName pdfName = PdfName.THUMB;
        PdfWriter pdfWriter2 = this.writer;
        pdfWriter.addPageDictEntry(pdfName, pdfWriter2.getImageReference(pdfWriter2.addDirectImageSimple(image)));
    }

    /* access modifiers changed from: package-private */
    public PageResources getPageResources() {
        return this.pageResources;
    }

    /* access modifiers changed from: package-private */
    public boolean isStrictImageSequence() {
        return this.strictImageSequence;
    }

    /* access modifiers changed from: package-private */
    public void setStrictImageSequence(boolean z) {
        this.strictImageSequence = z;
    }

    public void clearTextWrap() {
        float f = this.imageEnd - this.currentHeight;
        PdfLine pdfLine = this.line;
        if (pdfLine != null) {
            f += pdfLine.height();
        }
        if (this.imageEnd > -1.0f && f > 0.0f) {
            carriageReturn();
            this.currentHeight += f;
        }
    }

    public int getStructParentIndex(Object obj) {
        int[] iArr = this.structParentIndices.get(obj);
        if (iArr == null) {
            iArr = new int[]{this.structParentIndices.size(), 0};
            this.structParentIndices.put(obj, iArr);
        }
        return iArr[0];
    }

    public int getNextMarkPoint(Object obj) {
        int[] iArr = this.structParentIndices.get(obj);
        if (iArr == null) {
            iArr = new int[]{this.structParentIndices.size(), 0};
            this.structParentIndices.put(obj, iArr);
        }
        int i = iArr[1];
        iArr[1] = iArr[1] + 1;
        return i;
    }

    public int[] getStructParentIndexAndNextMarkPoint(Object obj) {
        int[] iArr = this.structParentIndices.get(obj);
        if (iArr == null) {
            iArr = new int[]{this.structParentIndices.size(), 0};
            this.structParentIndices.put(obj, iArr);
        }
        int i = iArr[1];
        iArr[1] = iArr[1] + 1;
        return new int[]{iArr[0], i};
    }

    /* access modifiers changed from: protected */
    public void add(Image image) throws PdfException, DocumentException {
        float f;
        Image image2 = image;
        if (image.hasAbsoluteY()) {
            this.graphics.addImage(image2);
            this.pageEmpty = false;
            return;
        }
        if (this.currentHeight != 0.0f && (indentTop() - this.currentHeight) - image.getScaledHeight() < indentBottom()) {
            if (this.strictImageSequence || this.imageWait != null) {
                newPage();
                if (this.currentHeight != 0.0f && (indentTop() - this.currentHeight) - image.getScaledHeight() < indentBottom()) {
                    this.imageWait = image2;
                    return;
                }
            } else {
                this.imageWait = image2;
                return;
            }
        }
        this.pageEmpty = false;
        if (image2 == this.imageWait) {
            this.imageWait = null;
        }
        boolean z = (image.getAlignment() & 4) == 4 && (image.getAlignment() & 1) != 1;
        boolean z2 = (image.getAlignment() & 8) == 8;
        float f2 = this.leading;
        float f3 = f2 / 2.0f;
        if (z) {
            f3 += f2;
        }
        float f4 = f3;
        float indentTop = ((indentTop() - this.currentHeight) - image.getScaledHeight()) - f4;
        float[] matrix = image.matrix();
        float indentLeft = indentLeft() - matrix[4];
        if ((image.getAlignment() & 2) == 2) {
            indentLeft = (indentRight() - image.getScaledWidth()) - matrix[4];
        }
        if ((image.getAlignment() & 1) == 1) {
            indentLeft = (indentLeft() + (((indentRight() - indentLeft()) - image.getScaledWidth()) / 2.0f)) - matrix[4];
        }
        if (image.hasAbsoluteX()) {
            indentLeft = image.getAbsoluteX();
        }
        if (z) {
            float f5 = this.imageEnd;
            if (f5 < 0.0f || f5 < this.currentHeight + image.getScaledHeight() + f4) {
                this.imageEnd = this.currentHeight + image.getScaledHeight() + f4;
            }
            if ((image.getAlignment() & 2) == 2) {
                this.indentation.imageIndentRight += image.getScaledWidth() + image.getIndentationLeft();
            } else {
                this.indentation.imageIndentLeft += image.getScaledWidth() + image.getIndentationRight();
            }
        } else if ((image.getAlignment() & 2) == 2) {
            indentLeft -= image.getIndentationRight();
        } else {
            if ((image.getAlignment() & 1) == 1) {
                f = image.getIndentationLeft() - image.getIndentationRight();
            } else {
                f = image.getIndentationLeft();
            }
            indentLeft += f;
        }
        this.graphics.addImage(image, matrix[0], matrix[1], matrix[2], matrix[3], indentLeft, indentTop - matrix[5]);
        if (!z && !z2) {
            this.currentHeight += image.getScaledHeight() + f4;
            flushLines();
            this.text.moveText(0.0f, -(image.getScaledHeight() + f4));
            newLine();
        }
    }

    /* access modifiers changed from: package-private */
    public void addPTable(PdfPTable pdfPTable) throws DocumentException {
        ColumnText columnText = new ColumnText(isTagged(this.writer) ? this.text : this.writer.getDirectContent());
        columnText.setRunDirection(pdfPTable.getRunDirection());
        if (pdfPTable.getKeepTogether() && !fitsPage(pdfPTable, 0.0f) && this.currentHeight > 0.0f) {
            newPage();
            if (isTagged(this.writer)) {
                columnText.setCanvas(this.text);
            }
        }
        if (this.currentHeight == 0.0f) {
            columnText.setAdjustFirstLine(false);
        }
        columnText.addElement(pdfPTable);
        boolean isHeadersInEvent = pdfPTable.isHeadersInEvent();
        pdfPTable.setHeadersInEvent(true);
        int i = 0;
        while (true) {
            columnText.setSimpleColumn(indentLeft(), indentBottom(), indentRight(), indentTop() - this.currentHeight);
            if ((columnText.mo20965go() & 1) != 0) {
                if (isTagged(this.writer)) {
                    this.text.setTextMatrix(indentLeft(), columnText.getYLine());
                } else {
                    this.text.moveText(0.0f, (columnText.getYLine() - indentTop()) + this.currentHeight);
                }
                this.currentHeight = indentTop() - columnText.getYLine();
                pdfPTable.setHeadersInEvent(isHeadersInEvent);
                return;
            }
            i = indentTop() - this.currentHeight == columnText.getYLine() ? i + 1 : 0;
            if (i != 3) {
                this.currentHeight = indentTop() - columnText.getYLine();
                newPage();
                if (isTagged(this.writer)) {
                    columnText.setCanvas(this.text);
                }
            } else {
                throw new DocumentException(MessageLocalization.getComposedMessage("infinite.table.loop", new Object[0]));
            }
        }
    }

    private void addDiv(PdfDiv pdfDiv) throws DocumentException {
        if (this.floatingElements == null) {
            this.floatingElements = new ArrayList<>();
        }
        this.floatingElements.add(pdfDiv);
    }

    private void flushFloatingElements() throws DocumentException {
        ArrayList<Element> arrayList = this.floatingElements;
        if (arrayList != null && !arrayList.isEmpty()) {
            ArrayList<Element> arrayList2 = this.floatingElements;
            this.floatingElements = null;
            FloatLayout floatLayout = new FloatLayout(arrayList2, false);
            int i = 0;
            while (true) {
                indentLeft();
                floatLayout.setSimpleColumn(indentLeft(), indentBottom(), indentRight(), indentTop() - this.currentHeight);
                try {
                    if ((floatLayout.layout(isTagged(this.writer) ? this.text : this.writer.getDirectContent(), false) & 1) != 0) {
                        if (isTagged(this.writer)) {
                            this.text.setTextMatrix(indentLeft(), floatLayout.getYLine());
                        } else {
                            this.text.moveText(0.0f, (floatLayout.getYLine() - indentTop()) + this.currentHeight);
                        }
                        this.currentHeight = indentTop() - floatLayout.getYLine();
                        return;
                    }
                    i = (indentTop() - this.currentHeight == floatLayout.getYLine() || isPageEmpty()) ? i + 1 : 0;
                    if (i != 2) {
                        newPage();
                    } else {
                        return;
                    }
                } catch (Exception unused) {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean fitsPage(PdfPTable pdfPTable, float f) {
        if (!pdfPTable.isLockedWidth()) {
            pdfPTable.setTotalWidth(((indentRight() - indentLeft()) * pdfPTable.getWidthPercentage()) / 100.0f);
        }
        ensureNewLine();
        float floatValue = Float.valueOf(pdfPTable.isSkipFirstHeader() ? pdfPTable.getTotalHeight() - pdfPTable.getHeaderHeight() : pdfPTable.getTotalHeight()).floatValue();
        float f2 = 0.0f;
        if (this.currentHeight > 0.0f) {
            f2 = pdfPTable.spacingBefore();
        }
        return floatValue + f2 <= ((indentTop() - this.currentHeight) - indentBottom()) - f;
    }

    private static boolean isTagged(PdfWriter pdfWriter) {
        return pdfWriter != null && pdfWriter.isTagged();
    }

    private PdfLine getLastLine() {
        if (this.lines.size() <= 0) {
            return null;
        }
        ArrayList<PdfLine> arrayList = this.lines;
        return arrayList.get(arrayList.size() - 1);
    }

    public class Destination {
        public PdfAction action;
        public PdfDestination destination;
        public PdfIndirectReference reference;

        public Destination() {
        }
    }

    /* access modifiers changed from: protected */
    public void useExternalCache(TempFileCache tempFileCache) {
        this.isToUseExternalCache = true;
        this.externalCache = tempFileCache;
    }

    /* access modifiers changed from: protected */
    public void saveStructElement(AccessibleElementId accessibleElementId, PdfStructureElement pdfStructureElement) {
        this.structElements.put(accessibleElementId, pdfStructureElement);
    }

    /* access modifiers changed from: protected */
    public PdfStructureElement getStructElement(AccessibleElementId accessibleElementId) {
        return getStructElement(accessibleElementId, true);
    }

    /* access modifiers changed from: protected */
    public PdfStructureElement getStructElement(AccessibleElementId accessibleElementId, boolean z) {
        TempFileCache.ObjectPosition objectPosition;
        PdfStructureElement pdfStructureElement = this.structElements.get(accessibleElementId);
        if (this.isToUseExternalCache && pdfStructureElement == null && (objectPosition = this.externallyStoredStructElements.get(accessibleElementId)) != null) {
            try {
                pdfStructureElement = (PdfStructureElement) this.externalCache.get(objectPosition);
                pdfStructureElement.setStructureTreeRoot(this.writer.getStructureTreeRoot());
                pdfStructureElement.setStructureElementParent(getStructElement(this.elementsParents.get(pdfStructureElement.getElementId()), z));
                if (z) {
                    this.externallyStoredStructElements.remove(accessibleElementId);
                    this.structElements.put(accessibleElementId, pdfStructureElement);
                }
            } catch (IOException e) {
                throw new ExceptionConverter(e);
            } catch (ClassNotFoundException e2) {
                throw new ExceptionConverter(e2);
            }
        }
        return pdfStructureElement;
    }

    /* access modifiers changed from: protected */
    public void flushStructureElementsOnNewPage() {
        if (this.isToUseExternalCache) {
            Iterator<Map.Entry<AccessibleElementId, PdfStructureElement>> it = this.structElements.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry next = it.next();
                if (!((PdfStructureElement) next.getValue()).getStructureType().equals(PdfName.DOCUMENT)) {
                    try {
                        PdfStructureElement pdfStructureElement = (PdfStructureElement) next.getValue();
                        PdfDictionary parent = pdfStructureElement.getParent();
                        PdfStructureElement pdfStructureElement2 = null;
                        if (parent instanceof PdfStructureElement) {
                            pdfStructureElement2 = (PdfStructureElement) parent;
                        }
                        if (pdfStructureElement2 != null) {
                            this.elementsParents.put(next.getKey(), pdfStructureElement2.getElementId());
                        }
                        this.externallyStoredStructElements.put(next.getKey(), this.externalCache.put(pdfStructureElement));
                        it.remove();
                    } catch (IOException e) {
                        throw new ExceptionConverter(e);
                    }
                }
            }
        }
    }

    public Set<AccessibleElementId> getStructElements() {
        HashSet hashSet = new HashSet();
        hashSet.addAll(this.externallyStoredStructElements.keySet());
        hashSet.addAll(this.structElements.keySet());
        return hashSet;
    }
}
