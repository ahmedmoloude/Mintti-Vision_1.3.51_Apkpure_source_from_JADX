package com.itextpdf.text.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.exceptions.BadPasswordException;
import com.itextpdf.text.log.Counter;
import com.itextpdf.text.log.CounterFactory;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.StringTokenizer;

public class PdfCopy extends PdfWriter {
    protected static Counter COUNTER = CounterFactory.getCounter(PdfCopy.class);
    private static final PdfName annotId;
    private static int annotIdCnt = 0;
    protected static final HashSet<PdfName> fieldKeys;
    private static final PdfName iTextTag = new PdfName("_iTextTag_");
    protected static final HashSet<PdfName> widgetKeys;
    private static final Integer zero = 0;
    private PdfIndirectReference acroForm;
    private ArrayList<String> calculationOrder;
    private ArrayList<Object> calculationOrderRefs;
    private int currentStructArrayNumber = 0;
    protected HashSet<PdfObject> disableIndirects;
    protected PdfArray fieldArray;
    protected HashSet<PdfTemplate> fieldTemplates;
    private HashMap<String, Object> fieldTree;
    protected ArrayList<AcroFields> fields;
    private boolean hasSignature;
    protected ArrayList<ImportedPage> importedPages;
    protected HashMap<PdfReader, HashMap<RefKey, IndirectReferences>> indirectMap;
    protected HashMap<RefKey, PdfIndirectObject> indirectObjects;
    protected HashMap<RefKey, IndirectReferences> indirects;
    protected boolean mergeFields = false;
    private boolean mergeFieldsInternalCall = false;
    private HashMap<Integer, PdfIndirectObject> mergedMap;
    private HashSet<Object> mergedRadioButtons = new HashSet<>();
    private HashSet<PdfIndirectObject> mergedSet;
    private HashMap<Object, PdfString> mergedTextFields = new HashMap<>();
    protected int[] namePtr = {0};
    private boolean needAppearances = false;
    protected HashMap<PdfObject, PdfObject> parentObjects;
    protected PdfReader reader;
    private HashSet<PdfReader> readersWithImportedStructureTreeRootKids = new HashSet<>();
    private PdfDictionary resources;
    /* access modifiers changed from: private */
    public boolean rotateContents = true;
    protected ArrayList<PdfIndirectObject> savedObjects;
    private PdfStructTreeController structTreeController = null;
    protected PRIndirectReference structTreeRootReference;
    private HashMap<PdfArray, ArrayList<Integer>> tabOrder;
    private HashMap<RefKey, PdfIndirectObject> unmergedIndirectRefsMap;
    private HashMap<Integer, PdfIndirectObject> unmergedMap;
    protected boolean updateRootKids = false;

    public PdfIndirectReference add(PdfOutline pdfOutline) {
        return null;
    }

    /* access modifiers changed from: package-private */
    public PdfIndirectReference add(PdfPage pdfPage, PdfContents pdfContents) throws PdfException {
        return null;
    }

    public void addAnnotation(PdfAnnotation pdfAnnotation) {
    }

    static class IndirectReferences {
        boolean hasCopied = false;
        PdfIndirectReference theRef;

        IndirectReferences(PdfIndirectReference pdfIndirectReference) {
            this.theRef = pdfIndirectReference;
        }

        /* access modifiers changed from: package-private */
        public void setCopied() {
            this.hasCopied = true;
        }

        /* access modifiers changed from: package-private */
        public void setNotCopied() {
            this.hasCopied = false;
        }

        /* access modifiers changed from: package-private */
        public boolean getCopied() {
            return this.hasCopied;
        }

        /* access modifiers changed from: package-private */
        public PdfIndirectReference getRef() {
            return this.theRef;
        }

        public String toString() {
            String str = this.hasCopied ? " Copied" : "";
            return getRef() + str;
        }
    }

    static {
        PdfName pdfName = new PdfName("iTextAnnotId");
        annotId = pdfName;
        HashSet<PdfName> hashSet = new HashSet<>();
        widgetKeys = hashSet;
        HashSet<PdfName> hashSet2 = new HashSet<>();
        fieldKeys = hashSet2;
        hashSet.add(PdfName.SUBTYPE);
        hashSet.add(PdfName.CONTENTS);
        hashSet.add(PdfName.RECT);
        hashSet.add(PdfName.f616NM);
        hashSet.add(PdfName.f608M);
        hashSet.add(PdfName.f580F);
        hashSet.add(PdfName.f556BS);
        hashSet.add(PdfName.BORDER);
        hashSet.add(PdfName.f549AP);
        hashSet.add(PdfName.f550AS);
        hashSet.add(PdfName.f557C);
        hashSet.add(PdfName.f545A);
        hashSet.add(PdfName.STRUCTPARENT);
        hashSet.add(PdfName.f618OC);
        hashSet.add(PdfName.f589H);
        hashSet.add(PdfName.f609MK);
        hashSet.add(PdfName.f568DA);
        hashSet.add(PdfName.f631Q);
        hashSet.add(PdfName.f623P);
        hashSet.add(PdfName.TYPE);
        hashSet.add(pdfName);
        hashSet2.add(PdfName.f546AA);
        hashSet2.add(PdfName.f588FT);
        hashSet2.add(PdfName.f657TU);
        hashSet2.add(PdfName.f653TM);
        hashSet2.add(PdfName.f583FF);
        hashSet2.add(PdfName.f664V);
        hashSet2.add(PdfName.f576DV);
        hashSet2.add(PdfName.f575DS);
        hashSet2.add(PdfName.f641RV);
        hashSet2.add(PdfName.OPT);
        hashSet2.add(PdfName.MAXLEN);
        hashSet2.add(PdfName.f651TI);
        hashSet2.add(PdfName.f598I);
        hashSet2.add(PdfName.LOCK);
        hashSet2.add(PdfName.f645SV);
    }

    /* access modifiers changed from: protected */
    public Counter getCounter() {
        return COUNTER;
    }

    protected static class ImportedPage {
        PdfIndirectReference annotsIndirectReference;
        PdfArray mergedFields;
        int pageNumber;
        PdfReader reader;

        ImportedPage(PdfReader pdfReader, int i, boolean z) {
            this.pageNumber = i;
            this.reader = pdfReader;
            if (z) {
                this.mergedFields = new PdfArray();
            }
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ImportedPage)) {
                return false;
            }
            ImportedPage importedPage = (ImportedPage) obj;
            if (this.pageNumber != importedPage.pageNumber || !this.reader.equals(importedPage.reader)) {
                return false;
            }
            return true;
        }

        public String toString() {
            return Integer.toString(this.pageNumber);
        }
    }

    public PdfCopy(Document document, OutputStream outputStream) throws DocumentException {
        super(new PdfDocument(), outputStream);
        document.addDocListener(this.pdf);
        this.pdf.addWriter(this);
        this.indirectMap = new HashMap<>();
        this.parentObjects = new HashMap<>();
        this.disableIndirects = new HashSet<>();
        this.indirectObjects = new HashMap<>();
        this.savedObjects = new ArrayList<>();
        this.importedPages = new ArrayList<>();
    }

    public void setPageEvent(PdfPageEvent pdfPageEvent) {
        throw new UnsupportedOperationException();
    }

    public boolean isRotateContents() {
        return this.rotateContents;
    }

    public void setRotateContents(boolean z) {
        this.rotateContents = z;
    }

    public void setMergeFields() {
        this.mergeFields = true;
        this.resources = new PdfDictionary();
        this.fields = new ArrayList<>();
        this.calculationOrder = new ArrayList<>();
        this.fieldTree = new LinkedHashMap();
        this.unmergedMap = new HashMap<>();
        this.unmergedIndirectRefsMap = new HashMap<>();
        this.mergedMap = new HashMap<>();
        this.mergedSet = new HashSet<>();
    }

    public PdfImportedPage getImportedPage(PdfReader pdfReader, int i) {
        boolean z = this.mergeFields;
        if (!z || this.mergeFieldsInternalCall) {
            if (z) {
                this.importedPages.add(new ImportedPage(pdfReader, i, z));
            }
            PdfStructTreeController pdfStructTreeController = this.structTreeController;
            if (pdfStructTreeController != null) {
                pdfStructTreeController.reader = null;
            }
            this.disableIndirects.clear();
            this.parentObjects.clear();
            return getImportedPageImpl(pdfReader, i);
        }
        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("1.method.cannot.be.used.in.mergeFields.mode.please.use.addDocument", "getImportedPage"));
    }

    public PdfImportedPage getImportedPage(PdfReader pdfReader, int i, boolean z) throws BadPdfFormatException {
        boolean z2 = this.mergeFields;
        if (!z2 || this.mergeFieldsInternalCall) {
            this.updateRootKids = false;
            if (!z) {
                if (z2) {
                    this.importedPages.add(new ImportedPage(pdfReader, i, z2));
                }
                return getImportedPageImpl(pdfReader, i);
            }
            PdfStructTreeController pdfStructTreeController = this.structTreeController;
            if (pdfStructTreeController == null) {
                this.structTreeController = new PdfStructTreeController(pdfReader, this);
            } else if (pdfReader != pdfStructTreeController.reader) {
                this.structTreeController.setReader(pdfReader);
            }
            ImportedPage importedPage = new ImportedPage(pdfReader, i, this.mergeFields);
            int checkStructureTreeRootKids = checkStructureTreeRootKids(importedPage);
            if (checkStructureTreeRootKids == -1) {
                clearIndirects(pdfReader);
                this.updateRootKids = true;
            } else if (checkStructureTreeRootKids == 0) {
                this.updateRootKids = false;
            } else if (checkStructureTreeRootKids == 1) {
                this.updateRootKids = true;
            }
            this.importedPages.add(importedPage);
            this.disableIndirects.clear();
            this.parentObjects.clear();
            return getImportedPageImpl(pdfReader, i);
        }
        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("1.method.cannot.be.used.in.mergeFields.mode.please.use.addDocument", "getImportedPage"));
    }

    private void clearIndirects(PdfReader pdfReader) {
        HashMap hashMap = this.indirectMap.get(pdfReader);
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : hashMap.entrySet()) {
            PdfIndirectObject pdfIndirectObject = this.indirectObjects.get(new RefKey(((IndirectReferences) entry.getValue()).theRef));
            if (pdfIndirectObject == null) {
                arrayList.add(entry.getKey());
            } else if (pdfIndirectObject.object.isArray() || pdfIndirectObject.object.isDictionary() || pdfIndirectObject.object.isStream()) {
                arrayList.add(entry.getKey());
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            hashMap.remove((RefKey) it.next());
        }
    }

    private int checkStructureTreeRootKids(ImportedPage importedPage) {
        boolean z;
        if (this.importedPages.size() == 0) {
            return 1;
        }
        Iterator<ImportedPage> it = this.importedPages.iterator();
        while (true) {
            if (it.hasNext()) {
                if (it.next().reader.equals(importedPage.reader)) {
                    z = true;
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        if (!z) {
            return 1;
        }
        ArrayList<ImportedPage> arrayList = this.importedPages;
        ImportedPage importedPage2 = arrayList.get(arrayList.size() - 1);
        if (!importedPage2.reader.equals(importedPage.reader) || importedPage.pageNumber <= importedPage2.pageNumber) {
            return -1;
        }
        if (this.readersWithImportedStructureTreeRootKids.contains(importedPage.reader)) {
            return 0;
        }
        return 1;
    }

    /* access modifiers changed from: protected */
    public void structureTreeRootKidsForReaderImported(PdfReader pdfReader) {
        this.readersWithImportedStructureTreeRootKids.add(pdfReader);
    }

    /* access modifiers changed from: protected */
    public void fixStructureTreeRoot(HashSet<RefKey> hashSet, HashSet<PdfName> hashSet2) {
        HashMap<PdfName, PdfObject> hashMap = new HashMap<>(hashSet2.size());
        Iterator<PdfName> it = hashSet2.iterator();
        while (it.hasNext()) {
            PdfName next = it.next();
            PdfObject pdfObject = this.structureTreeRoot.classes.get(next);
            if (pdfObject != null) {
                hashMap.put(next, pdfObject);
            }
        }
        this.structureTreeRoot.classes = hashMap;
        PdfArray asArray = this.structureTreeRoot.getAsArray(PdfName.f605K);
        if (asArray != null) {
            int i = 0;
            while (i < asArray.size()) {
                if (!hashSet.contains(new RefKey((PdfIndirectReference) asArray.getPdfObject(i)))) {
                    asArray.remove(i);
                    i--;
                }
                i++;
            }
        }
    }

    /* access modifiers changed from: protected */
    public PdfImportedPage getImportedPageImpl(PdfReader pdfReader, int i) {
        if (this.currentPdfReaderInstance == null) {
            this.currentPdfReaderInstance = super.getPdfReaderInstance(pdfReader);
        } else if (this.currentPdfReaderInstance.getReader() != pdfReader) {
            this.currentPdfReaderInstance = super.getPdfReaderInstance(pdfReader);
        }
        return this.currentPdfReaderInstance.getImportedPage(i);
    }

    /* access modifiers changed from: protected */
    public PdfIndirectReference copyIndirect(PRIndirectReference pRIndirectReference, boolean z, boolean z2) throws IOException, BadPdfFormatException {
        PdfIndirectReference pdfIndirectReference;
        PdfObject pdfObjectRelease;
        RefKey refKey = new RefKey(pRIndirectReference);
        IndirectReferences indirectReferences = this.indirects.get(refKey);
        PdfObject pdfObjectRelease2 = PdfReader.getPdfObjectRelease((PdfObject) pRIndirectReference);
        if (z && z2 && (pdfObjectRelease2 instanceof PdfDictionary) && ((PdfDictionary) pdfObjectRelease2).contains(PdfName.f626PG)) {
            return null;
        }
        if (indirectReferences != null) {
            pdfIndirectReference = indirectReferences.getRef();
            if (indirectReferences.getCopied()) {
                return pdfIndirectReference;
            }
        } else {
            pdfIndirectReference = this.body.getPdfIndirectReference();
            indirectReferences = new IndirectReferences(pdfIndirectReference);
            this.indirects.put(refKey, indirectReferences);
        }
        if (pdfObjectRelease2 != null && pdfObjectRelease2.isDictionary() && (pdfObjectRelease = PdfReader.getPdfObjectRelease(((PdfDictionary) pdfObjectRelease2).get(PdfName.TYPE))) != null && PdfName.PAGE.equals(pdfObjectRelease)) {
            return pdfIndirectReference;
        }
        indirectReferences.setCopied();
        if (pdfObjectRelease2 != null) {
            this.parentObjects.put(pdfObjectRelease2, pRIndirectReference);
        }
        PdfObject copyObject = copyObject(pdfObjectRelease2, z, z2);
        if (this.disableIndirects.contains(pdfObjectRelease2)) {
            indirectReferences.setNotCopied();
        }
        if (copyObject != null) {
            addToBody(copyObject, pdfIndirectReference);
            return pdfIndirectReference;
        }
        this.indirects.remove(refKey);
        return null;
    }

    /* access modifiers changed from: protected */
    public PdfIndirectReference copyIndirect(PRIndirectReference pRIndirectReference) throws IOException, BadPdfFormatException {
        return copyIndirect(pRIndirectReference, false, false);
    }

    /* access modifiers changed from: protected */
    public PdfDictionary copyDictionary(PdfDictionary pdfDictionary, boolean z, boolean z2) throws IOException, BadPdfFormatException {
        PdfObject pdfObject;
        PdfDictionary pdfDictionary2 = new PdfDictionary(pdfDictionary.size());
        PdfObject pdfObjectRelease = PdfReader.getPdfObjectRelease(pdfDictionary.get(PdfName.TYPE));
        if (z) {
            if (!z2 || !pdfDictionary.contains(PdfName.f626PG)) {
                this.structTreeController.addRole(pdfDictionary.getAsName(PdfName.f642S));
                this.structTreeController.addClass(pdfDictionary);
            } else {
                this.disableIndirects.add(pdfDictionary);
                Object obj = pdfDictionary;
                while (this.parentObjects.containsKey(obj) && !this.disableIndirects.contains(obj)) {
                    Object obj2 = (PdfObject) this.parentObjects.get(obj);
                    this.disableIndirects.add(obj2);
                    obj = obj2;
                }
                return null;
            }
        }
        PdfStructTreeController pdfStructTreeController = this.structTreeController;
        if (!(pdfStructTreeController == null || pdfStructTreeController.reader == null || (!pdfDictionary.contains(PdfName.STRUCTPARENTS) && !pdfDictionary.contains(PdfName.STRUCTPARENT)))) {
            PdfName pdfName = PdfName.STRUCTPARENT;
            if (pdfDictionary.contains(PdfName.STRUCTPARENTS)) {
                pdfName = PdfName.STRUCTPARENTS;
            }
            PdfObject pdfObject2 = pdfDictionary.get(pdfName);
            pdfDictionary2.put(pdfName, new PdfNumber(this.currentStructArrayNumber));
            int i = this.currentStructArrayNumber;
            this.currentStructArrayNumber = i + 1;
            this.structTreeController.copyStructTreeForPage((PdfNumber) pdfObject2, i);
        }
        for (PdfName next : pdfDictionary.getKeys()) {
            PdfObject pdfObject3 = pdfDictionary.get(next);
            PdfStructTreeController pdfStructTreeController2 = this.structTreeController;
            if (pdfStructTreeController2 == null || pdfStructTreeController2.reader == null || (!next.equals(PdfName.STRUCTPARENTS) && !next.equals(PdfName.STRUCTPARENT))) {
                if (!PdfName.PAGE.equals(pdfObjectRelease)) {
                    if (!this.tagged || !pdfObject3.isIndirect() || !isStructTreeRootReference((PRIndirectReference) pdfObject3)) {
                        pdfObject = copyObject(pdfObject3, z, z2);
                    } else {
                        pdfObject = this.structureTreeRoot.getReference();
                    }
                    if (pdfObject != null) {
                        pdfDictionary2.put(next, pdfObject);
                    }
                } else if (!next.equals(PdfName.f551B) && !next.equals(PdfName.PARENT)) {
                    this.parentObjects.put(pdfObject3, pdfDictionary);
                    PdfObject copyObject = copyObject(pdfObject3, z, z2);
                    if (copyObject != null) {
                        pdfDictionary2.put(next, copyObject);
                    }
                }
            }
        }
        return pdfDictionary2;
    }

    /* access modifiers changed from: protected */
    public PdfDictionary copyDictionary(PdfDictionary pdfDictionary) throws IOException, BadPdfFormatException {
        return copyDictionary(pdfDictionary, false, false);
    }

    /* access modifiers changed from: protected */
    public PdfStream copyStream(PRStream pRStream) throws IOException, BadPdfFormatException {
        PRStream pRStream2 = new PRStream(pRStream, (PdfDictionary) null);
        for (PdfName next : pRStream.getKeys()) {
            PdfObject pdfObject = pRStream.get(next);
            this.parentObjects.put(pdfObject, pRStream);
            PdfObject copyObject = copyObject(pdfObject);
            if (copyObject != null) {
                pRStream2.put(next, copyObject);
            }
        }
        return pRStream2;
    }

    /* access modifiers changed from: protected */
    public PdfArray copyArray(PdfArray pdfArray, boolean z, boolean z2) throws IOException, BadPdfFormatException {
        PdfArray pdfArray2 = new PdfArray(pdfArray.size());
        ListIterator<PdfObject> listIterator = pdfArray.listIterator();
        while (listIterator.hasNext()) {
            PdfObject next = listIterator.next();
            this.parentObjects.put(next, pdfArray);
            PdfObject copyObject = copyObject(next, z, z2);
            if (copyObject != null) {
                pdfArray2.add(copyObject);
            }
        }
        return pdfArray2;
    }

    /* access modifiers changed from: protected */
    public PdfArray copyArray(PdfArray pdfArray) throws IOException, BadPdfFormatException {
        return copyArray(pdfArray, false, false);
    }

    /* access modifiers changed from: protected */
    public PdfObject copyObject(PdfObject pdfObject, boolean z, boolean z2) throws IOException, BadPdfFormatException {
        if (pdfObject == null) {
            return PdfNull.PDFNULL;
        }
        switch (pdfObject.type) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 8:
                return pdfObject;
            case 5:
                return copyArray((PdfArray) pdfObject, z, z2);
            case 6:
                return copyDictionary((PdfDictionary) pdfObject, z, z2);
            case 7:
                return copyStream((PRStream) pdfObject);
            case 10:
                if (z || z2) {
                    return copyIndirect((PRIndirectReference) pdfObject, z, z2);
                }
                return copyIndirect((PRIndirectReference) pdfObject);
            default:
                if (pdfObject.type < 0) {
                    String pdfLiteral = ((PdfLiteral) pdfObject).toString();
                    if (pdfLiteral.equals(PdfBoolean.TRUE) || pdfLiteral.equals(PdfBoolean.FALSE)) {
                        return new PdfBoolean(pdfLiteral);
                    }
                    return new PdfLiteral(pdfLiteral);
                }
                PrintStream printStream = System.out;
                printStream.println("CANNOT COPY type " + pdfObject.type);
                return null;
        }
    }

    /* access modifiers changed from: protected */
    public PdfObject copyObject(PdfObject pdfObject) throws IOException, BadPdfFormatException {
        return copyObject(pdfObject, false, false);
    }

    /* access modifiers changed from: protected */
    public int setFromIPage(PdfImportedPage pdfImportedPage) {
        int pageNumber = pdfImportedPage.getPageNumber();
        PdfReaderInstance pdfReaderInstance = pdfImportedPage.getPdfReaderInstance();
        this.currentPdfReaderInstance = pdfReaderInstance;
        PdfReader reader2 = pdfReaderInstance.getReader();
        this.reader = reader2;
        setFromReader(reader2);
        return pageNumber;
    }

    /* access modifiers changed from: protected */
    public void setFromReader(PdfReader pdfReader) {
        this.reader = pdfReader;
        HashMap<RefKey, IndirectReferences> hashMap = this.indirectMap.get(pdfReader);
        this.indirects = hashMap;
        if (hashMap == null) {
            HashMap<RefKey, IndirectReferences> hashMap2 = new HashMap<>();
            this.indirects = hashMap2;
            this.indirectMap.put(pdfReader, hashMap2);
        }
    }

    public void addPage(PdfImportedPage pdfImportedPage) throws IOException, BadPdfFormatException {
        if (!this.mergeFields || this.mergeFieldsInternalCall) {
            int fromIPage = setFromIPage(pdfImportedPage);
            PdfDictionary pageN = this.reader.getPageN(fromIPage);
            PRIndirectReference pageOrigRef = this.reader.getPageOrigRef(fromIPage);
            this.reader.releasePage(fromIPage);
            RefKey refKey = new RefKey(pageOrigRef);
            IndirectReferences indirectReferences = this.indirects.get(refKey);
            if (indirectReferences != null && !indirectReferences.getCopied()) {
                this.pageReferences.add(indirectReferences.getRef());
                indirectReferences.setCopied();
            }
            PdfIndirectReference currentPage = getCurrentPage();
            if (indirectReferences == null) {
                indirectReferences = new IndirectReferences(currentPage);
                this.indirects.put(refKey, indirectReferences);
            }
            indirectReferences.setCopied();
            if (this.tagged) {
                this.structTreeRootReference = (PRIndirectReference) this.reader.getCatalog().get(PdfName.STRUCTTREEROOT);
            }
            PdfDictionary copyDictionary = copyDictionary(pageN);
            if (this.mergeFields) {
                ArrayList<ImportedPage> arrayList = this.importedPages;
                ImportedPage importedPage = arrayList.get(arrayList.size() - 1);
                importedPage.annotsIndirectReference = this.body.getPdfIndirectReference();
                copyDictionary.put(PdfName.ANNOTS, importedPage.annotsIndirectReference);
            }
            this.root.addPage(copyDictionary);
            pdfImportedPage.setCopied();
            this.currentPageNumber++;
            this.pdf.setPageCount(this.currentPageNumber);
            this.structTreeRootReference = null;
            return;
        }
        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("1.method.cannot.be.used.in.mergeFields.mode.please.use.addDocument", "addPage"));
    }

    public void addPage(Rectangle rectangle, int i) throws DocumentException {
        if (!this.mergeFields || this.mergeFieldsInternalCall) {
            PdfPage pdfPage = new PdfPage(new PdfRectangle(rectangle, i), new HashMap(), new PageResources().getResources(), 0);
            pdfPage.put(PdfName.TABS, getTabs());
            this.root.addPage((PdfDictionary) pdfPage);
            this.currentPageNumber++;
            this.pdf.setPageCount(this.currentPageNumber);
            return;
        }
        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("1.method.cannot.be.used.in.mergeFields.mode.please.use.addDocument", "addPage"));
    }

    public void addDocument(PdfReader pdfReader, List<Integer> list) throws DocumentException, IOException {
        if (!this.indirectMap.containsKey(pdfReader)) {
            pdfReader.selectPages(list, false);
            addDocument(pdfReader);
            return;
        }
        throw new IllegalArgumentException(MessageLocalization.getComposedMessage("document.1.has.already.been.added", pdfReader.toString()));
    }

    public void copyDocumentFields(PdfReader pdfReader) throws DocumentException, IOException {
        PdfStructTreeController pdfStructTreeController;
        PdfArray asArray;
        if (!this.document.isOpen()) {
            throw new DocumentException(MessageLocalization.getComposedMessage("the.document.is.not.open.yet.you.can.only.add.meta.information", new Object[0]));
        } else if (this.indirectMap.containsKey(pdfReader)) {
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("document.1.has.already.been.added", pdfReader.toString()));
        } else if (!pdfReader.isOpenedWithFullPermissions()) {
            throw new BadPasswordException(MessageLocalization.getComposedMessage("pdfreader.not.opened.with.owner.password", new Object[0]));
        } else if (this.mergeFields) {
            HashMap<RefKey, IndirectReferences> hashMap = new HashMap<>();
            this.indirects = hashMap;
            this.indirectMap.put(pdfReader, hashMap);
            pdfReader.consolidateNamedDestinations();
            pdfReader.shuffleSubsetNames();
            if (this.tagged && PdfStructTreeController.checkTagged(pdfReader)) {
                this.structTreeRootReference = (PRIndirectReference) pdfReader.getCatalog().get(PdfName.STRUCTTREEROOT);
                PdfStructTreeController pdfStructTreeController2 = this.structTreeController;
                if (pdfStructTreeController2 == null) {
                    this.structTreeController = new PdfStructTreeController(pdfReader, this);
                } else if (pdfReader != pdfStructTreeController2.reader) {
                    this.structTreeController.setReader(pdfReader);
                }
            }
            ArrayList<PdfObject> arrayList = new ArrayList<>();
            for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
                PdfDictionary pageNRelease = pdfReader.getPageNRelease(i);
                if (pageNRelease != null && pageNRelease.contains(PdfName.ANNOTS) && (asArray = pageNRelease.getAsArray(PdfName.ANNOTS)) != null && asArray.size() > 0) {
                    if (this.importedPages.size() >= i) {
                        this.indirectMap.get(pdfReader).put(new RefKey(pdfReader.pageRefs.getPageOrigRef(i)), new IndirectReferences((PdfIndirectReference) this.pageReferences.get(i - 1)));
                        for (int i2 = 0; i2 < asArray.size(); i2++) {
                            PdfDictionary asDict = asArray.getAsDict(i2);
                            if (asDict != null) {
                                PdfName pdfName = annotId;
                                int i3 = annotIdCnt + 1;
                                annotIdCnt = i3;
                                asDict.put(pdfName, new PdfNumber(i3));
                                arrayList.add(asArray.getPdfObject(i2));
                            }
                        }
                    } else {
                        throw new DocumentException(MessageLocalization.getComposedMessage("there.are.not.enough.imported.pages.for.copied.fields", new Object[0]));
                    }
                }
            }
            for (PdfObject copyObject : arrayList) {
                copyObject(copyObject);
            }
            if (this.tagged && (pdfStructTreeController = this.structTreeController) != null) {
                pdfStructTreeController.attachStructTreeRootKids((PdfObject) null);
            }
            AcroFields acroFields = pdfReader.getAcroFields();
            if (!acroFields.isGenerateAppearances()) {
                this.needAppearances = true;
            }
            this.fields.add(acroFields);
            updateCalculationOrder(pdfReader);
            this.structTreeRootReference = null;
        } else {
            throw new IllegalArgumentException(MessageLocalization.getComposedMessage("1.method.can.be.only.used.in.mergeFields.mode.please.use.addDocument", "copyDocumentFields"));
        }
    }

    public void addDocument(PdfReader pdfReader) throws DocumentException, IOException {
        PdfArray asArray;
        if (this.document.isOpen()) {
            if (this.indirectMap.containsKey(pdfReader)) {
                throw new IllegalArgumentException(MessageLocalization.getComposedMessage("document.1.has.already.been.added", pdfReader.toString()));
            } else if (pdfReader.isOpenedWithFullPermissions()) {
                if (this.mergeFields) {
                    pdfReader.consolidateNamedDestinations();
                    pdfReader.shuffleSubsetNames();
                    for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
                        PdfDictionary pageNRelease = pdfReader.getPageNRelease(i);
                        if (!(pageNRelease == null || !pageNRelease.contains(PdfName.ANNOTS) || (asArray = pageNRelease.getAsArray(PdfName.ANNOTS)) == null)) {
                            for (int i2 = 0; i2 < asArray.size(); i2++) {
                                PdfDictionary asDict = asArray.getAsDict(i2);
                                if (asDict != null) {
                                    PdfName pdfName = annotId;
                                    int i3 = annotIdCnt + 1;
                                    annotIdCnt = i3;
                                    asDict.put(pdfName, new PdfNumber(i3));
                                }
                            }
                        }
                    }
                    AcroFields acroFields = pdfReader.getAcroFields();
                    if (!acroFields.isGenerateAppearances()) {
                        this.needAppearances = true;
                    }
                    this.fields.add(acroFields);
                    updateCalculationOrder(pdfReader);
                }
                boolean z = this.tagged && PdfStructTreeController.checkTagged(pdfReader);
                this.mergeFieldsInternalCall = true;
                for (int i4 = 1; i4 <= pdfReader.getNumberOfPages(); i4++) {
                    addPage(getImportedPage(pdfReader, i4, z));
                }
                this.mergeFieldsInternalCall = false;
            } else {
                throw new BadPasswordException(MessageLocalization.getComposedMessage("pdfreader.not.opened.with.owner.password", new Object[0]));
            }
        } else {
            throw new DocumentException(MessageLocalization.getComposedMessage("the.document.is.not.open.yet.you.can.only.add.meta.information", new Object[0]));
        }
    }

    public PdfIndirectObject addToBody(PdfObject pdfObject, PdfIndirectReference pdfIndirectReference) throws IOException {
        return addToBody(pdfObject, pdfIndirectReference, false);
    }

    public PdfIndirectObject addToBody(PdfObject pdfObject, PdfIndirectReference pdfIndirectReference, boolean z) throws IOException {
        PdfIndirectObject pdfIndirectObject;
        PdfNumber asNumber;
        if (z) {
            updateReferences(pdfObject);
        }
        if ((this.tagged || this.mergeFields) && this.indirectObjects != null && (pdfObject.isArray() || pdfObject.isDictionary() || pdfObject.isStream() || pdfObject.isNull())) {
            RefKey refKey = new RefKey(pdfIndirectReference);
            pdfIndirectObject = this.indirectObjects.get(refKey);
            if (pdfIndirectObject == null) {
                pdfIndirectObject = new PdfIndirectObject(pdfIndirectReference, pdfObject, (PdfWriter) this);
                this.indirectObjects.put(refKey, pdfIndirectObject);
            }
        } else {
            pdfIndirectObject = super.addToBody(pdfObject, pdfIndirectReference);
        }
        if (this.mergeFields && pdfObject.isDictionary() && (asNumber = ((PdfDictionary) pdfObject).getAsNumber(annotId)) != null) {
            if (z) {
                this.mergedMap.put(Integer.valueOf(asNumber.intValue()), pdfIndirectObject);
                this.mergedSet.add(pdfIndirectObject);
            } else {
                this.unmergedMap.put(Integer.valueOf(asNumber.intValue()), pdfIndirectObject);
                this.unmergedIndirectRefsMap.put(new RefKey(pdfIndirectObject.number, pdfIndirectObject.generation), pdfIndirectObject);
            }
        }
        return pdfIndirectObject;
    }

    /* access modifiers changed from: protected */
    public void cacheObject(PdfIndirectObject pdfIndirectObject) {
        if ((this.tagged || this.mergeFields) && this.indirectObjects != null) {
            this.savedObjects.add(pdfIndirectObject);
            RefKey refKey = new RefKey(pdfIndirectObject.number, pdfIndirectObject.generation);
            if (!this.indirectObjects.containsKey(refKey)) {
                this.indirectObjects.put(refKey, pdfIndirectObject);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void flushTaggedObjects() throws IOException {
        try {
            fixTaggedStructure();
        } catch (ClassCastException unused) {
        } catch (Throwable th) {
            flushIndirectObjects();
            throw th;
        }
        flushIndirectObjects();
    }

    /* access modifiers changed from: protected */
    public void flushAcroFields() throws IOException, BadPdfFormatException {
        PdfArray asArray;
        if (this.mergeFields) {
            try {
                Iterator<ImportedPage> it = this.importedPages.iterator();
                while (it.hasNext()) {
                    ImportedPage next = it.next();
                    PdfDictionary pageN = next.reader.getPageN(next.pageNumber);
                    if (!(pageN == null || (asArray = pageN.getAsArray(PdfName.ANNOTS)) == null)) {
                        if (asArray.size() != 0) {
                            for (AcroFields.Item item : next.reader.getAcroFields().getFields().values()) {
                                Iterator<PdfIndirectReference> it2 = item.widget_refs.iterator();
                                while (it2.hasNext()) {
                                    asArray.arrayList.remove(it2.next());
                                }
                            }
                            this.indirects = this.indirectMap.get(next.reader);
                            Iterator<PdfObject> it3 = asArray.arrayList.iterator();
                            while (it3.hasNext()) {
                                next.mergedFields.add(copyObject(it3.next()));
                            }
                        }
                    }
                }
                for (PdfReader removeFields : this.indirectMap.keySet()) {
                    removeFields.removeFields();
                }
                mergeFields();
                createAcroForms();
                if (this.tagged) {
                    return;
                }
            } catch (ClassCastException unused) {
                if (this.tagged) {
                    return;
                }
            } catch (Throwable th) {
                if (!this.tagged) {
                    flushIndirectObjects();
                }
                throw th;
            }
            flushIndirectObjects();
        }
    }

    /* access modifiers changed from: protected */
    public void fixTaggedStructure() throws IOException {
        PdfObject pdfObject;
        boolean z;
        PdfDictionary kDict;
        PdfIndirectReference pdfIndirectReference;
        HashMap<Integer, PdfIndirectReference> numTree = this.structureTreeRoot.getNumTree();
        HashSet hashSet = new HashSet();
        ArrayList arrayList = new ArrayList();
        if (this.mergeFields && (pdfIndirectReference = this.acroForm) != null) {
            arrayList.add(pdfIndirectReference);
            hashSet.add(new RefKey(this.acroForm));
        }
        Iterator it = this.pageReferences.iterator();
        while (it.hasNext()) {
            PdfIndirectReference pdfIndirectReference2 = (PdfIndirectReference) it.next();
            arrayList.add(pdfIndirectReference2);
            hashSet.add(new RefKey(pdfIndirectReference2));
        }
        int size = numTree.size() - 1;
        int i = 0;
        while (true) {
            PdfIndirectReference pdfIndirectReference3 = null;
            if (size < 0) {
                break;
            }
            PdfIndirectReference pdfIndirectReference4 = numTree.get(Integer.valueOf(size));
            if (pdfIndirectReference4 != null) {
                RefKey refKey = new RefKey(pdfIndirectReference4);
                PdfObject pdfObject2 = this.indirectObjects.get(refKey).object;
                if (pdfObject2.isDictionary()) {
                    PdfDictionary pdfDictionary = (PdfDictionary) pdfObject2;
                    if (!this.pageReferences.contains(pdfDictionary.get(PdfName.f626PG)) && ((kDict = PdfStructTreeController.getKDict(pdfDictionary)) == null || !this.pageReferences.contains(kDict.get(PdfName.f626PG)))) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (z) {
                        hashSet.add(refKey);
                        arrayList.add(pdfIndirectReference4);
                    } else {
                        numTree.remove(Integer.valueOf(size));
                    }
                } else if (pdfObject2.isArray()) {
                    hashSet.add(refKey);
                    arrayList.add(pdfIndirectReference4);
                    PdfArray pdfArray = (PdfArray) pdfObject2;
                    int i2 = i + 1;
                    PdfIndirectReference pdfIndirectReference5 = (PdfIndirectReference) this.pageReferences.get(i);
                    arrayList.add(pdfIndirectReference5);
                    hashSet.add(new RefKey(pdfIndirectReference5));
                    for (int i3 = 0; i3 < pdfArray.size(); i3++) {
                        PdfIndirectReference pdfIndirectReference6 = (PdfIndirectReference) pdfArray.getDirectObject(i3);
                        if (!pdfIndirectReference6.equals(pdfIndirectReference3)) {
                            RefKey refKey2 = new RefKey(pdfIndirectReference6);
                            hashSet.add(refKey2);
                            arrayList.add(pdfIndirectReference6);
                            PdfIndirectObject pdfIndirectObject = this.indirectObjects.get(refKey2);
                            if (pdfIndirectObject.object.isDictionary()) {
                                PdfDictionary pdfDictionary2 = (PdfDictionary) pdfIndirectObject.object;
                                PdfIndirectReference pdfIndirectReference7 = (PdfIndirectReference) pdfDictionary2.get(PdfName.f626PG);
                                if (pdfIndirectReference7 != null && !this.pageReferences.contains(pdfIndirectReference7) && !pdfIndirectReference7.equals(pdfIndirectReference5)) {
                                    pdfDictionary2.put(PdfName.f626PG, pdfIndirectReference5);
                                    PdfArray asArray = pdfDictionary2.getAsArray(PdfName.f605K);
                                    if (asArray != null && asArray.getDirectObject(0).isNumber()) {
                                        asArray.remove(0);
                                    }
                                }
                            }
                            pdfIndirectReference3 = pdfIndirectReference6;
                        }
                    }
                    i = i2;
                }
            }
            size--;
        }
        HashSet hashSet2 = new HashSet();
        findActives(arrayList, hashSet, hashSet2);
        fixPgKey(findActiveParents(hashSet), hashSet);
        fixStructureTreeRoot(hashSet, hashSet2);
        for (Map.Entry next : this.indirectObjects.entrySet()) {
            if (!hashSet.contains(next.getKey())) {
                next.setValue((Object) null);
            } else if (((PdfIndirectObject) next.getValue()).object.isArray()) {
                removeInactiveReferences((PdfArray) ((PdfIndirectObject) next.getValue()).object, hashSet);
            } else if (((PdfIndirectObject) next.getValue()).object.isDictionary() && (pdfObject = ((PdfDictionary) ((PdfIndirectObject) next.getValue()).object).get(PdfName.f605K)) != null && pdfObject.isArray()) {
                removeInactiveReferences((PdfArray) pdfObject, hashSet);
            }
        }
    }

    private void removeInactiveReferences(PdfArray pdfArray, HashSet<RefKey> hashSet) {
        int i = 0;
        while (i < pdfArray.size()) {
            PdfObject pdfObject = pdfArray.getPdfObject(i);
            if ((pdfObject.type() == 0 && !hashSet.contains(new RefKey((PdfIndirectReference) pdfObject))) || (pdfObject.isDictionary() && containsInactivePg((PdfDictionary) pdfObject, hashSet))) {
                pdfArray.remove(i);
                i--;
            }
            i++;
        }
    }

    private boolean containsInactivePg(PdfDictionary pdfDictionary, HashSet<RefKey> hashSet) {
        PdfObject pdfObject = pdfDictionary.get(PdfName.f626PG);
        return pdfObject != null && !hashSet.contains(new RefKey((PdfIndirectReference) pdfObject));
    }

    private ArrayList<PdfIndirectReference> findActiveParents(HashSet<RefKey> hashSet) {
        PdfObject pdfObject;
        ArrayList<PdfIndirectReference> arrayList = new ArrayList<>();
        ArrayList arrayList2 = new ArrayList(hashSet);
        for (int i = 0; i < arrayList2.size(); i++) {
            PdfIndirectObject pdfIndirectObject = this.indirectObjects.get(arrayList2.get(i));
            if (pdfIndirectObject != null && pdfIndirectObject.object.isDictionary() && (pdfObject = ((PdfDictionary) pdfIndirectObject.object).get(PdfName.f623P)) != null && pdfObject.type() == 0) {
                PdfIndirectReference pdfIndirectReference = (PdfIndirectReference) pdfObject;
                RefKey refKey = new RefKey(pdfIndirectReference);
                if (!hashSet.contains(refKey)) {
                    hashSet.add(refKey);
                    arrayList2.add(refKey);
                    arrayList.add(pdfIndirectReference);
                }
            }
        }
        return arrayList;
    }

    private void fixPgKey(ArrayList<PdfIndirectReference> arrayList, HashSet<RefKey> hashSet) {
        PdfDictionary pdfDictionary;
        PdfObject pdfObject;
        PdfArray asArray;
        PdfObject pdfObject2;
        Iterator<PdfIndirectReference> it = arrayList.iterator();
        while (it.hasNext()) {
            PdfIndirectObject pdfIndirectObject = this.indirectObjects.get(new RefKey(it.next()));
            if (pdfIndirectObject != null && pdfIndirectObject.object.isDictionary() && (pdfObject = pdfDictionary.get(PdfName.f626PG)) != null && !hashSet.contains(new RefKey((PdfIndirectReference) pdfObject)) && (asArray = pdfDictionary.getAsArray(PdfName.f605K)) != null) {
                int i = 0;
                while (true) {
                    if (i >= asArray.size()) {
                        break;
                    }
                    PdfObject pdfObject3 = asArray.getPdfObject(i);
                    if (pdfObject3.type() == 0) {
                        PdfIndirectObject pdfIndirectObject2 = this.indirectObjects.get(new RefKey((PdfIndirectReference) pdfObject3));
                        if (pdfIndirectObject2 != null && pdfIndirectObject2.object.isDictionary() && (pdfObject2 = ((PdfDictionary) pdfIndirectObject2.object).get(PdfName.f626PG)) != null && hashSet.contains(new RefKey((PdfIndirectReference) pdfObject2))) {
                            (pdfDictionary = (PdfDictionary) pdfIndirectObject.object).put(PdfName.f626PG, pdfObject2);
                            break;
                        }
                    } else {
                        asArray.remove(i);
                        i--;
                    }
                    i++;
                }
            }
        }
    }

    private void findActives(ArrayList<PdfIndirectReference> arrayList, HashSet<RefKey> hashSet, HashSet<PdfName> hashSet2) {
        for (int i = 0; i < arrayList.size(); i++) {
            PdfIndirectObject pdfIndirectObject = this.indirectObjects.get(new RefKey(arrayList.get(i)));
            if (!(pdfIndirectObject == null || pdfIndirectObject.object == null)) {
                int type = pdfIndirectObject.object.type();
                if (type == 0) {
                    findActivesFromReference((PdfIndirectReference) pdfIndirectObject.object, arrayList, hashSet);
                } else if (type == 5) {
                    findActivesFromArray((PdfArray) pdfIndirectObject.object, arrayList, hashSet, hashSet2);
                } else if (type == 6 || type == 7) {
                    findActivesFromDict((PdfDictionary) pdfIndirectObject.object, arrayList, hashSet, hashSet2);
                }
            }
        }
    }

    private void findActivesFromReference(PdfIndirectReference pdfIndirectReference, ArrayList<PdfIndirectReference> arrayList, HashSet<RefKey> hashSet) {
        RefKey refKey = new RefKey(pdfIndirectReference);
        PdfIndirectObject pdfIndirectObject = this.indirectObjects.get(refKey);
        if ((pdfIndirectObject == null || !pdfIndirectObject.object.isDictionary() || !containsInactivePg((PdfDictionary) pdfIndirectObject.object, hashSet)) && !hashSet.contains(refKey)) {
            hashSet.add(refKey);
            arrayList.add(pdfIndirectReference);
        }
    }

    private void findActivesFromArray(PdfArray pdfArray, ArrayList<PdfIndirectReference> arrayList, HashSet<RefKey> hashSet, HashSet<PdfName> hashSet2) {
        Iterator<PdfObject> it = pdfArray.iterator();
        while (it.hasNext()) {
            PdfObject next = it.next();
            int type = next.type();
            if (type == 0) {
                findActivesFromReference((PdfIndirectReference) next, arrayList, hashSet);
            } else if (type == 5) {
                findActivesFromArray((PdfArray) next, arrayList, hashSet, hashSet2);
            } else if (type == 6 || type == 7) {
                findActivesFromDict((PdfDictionary) next, arrayList, hashSet, hashSet2);
            }
        }
    }

    private void findActivesFromDict(PdfDictionary pdfDictionary, ArrayList<PdfIndirectReference> arrayList, HashSet<RefKey> hashSet, HashSet<PdfName> hashSet2) {
        if (!containsInactivePg(pdfDictionary, hashSet)) {
            for (PdfName next : pdfDictionary.getKeys()) {
                PdfObject pdfObject = pdfDictionary.get(next);
                if (!next.equals(PdfName.f623P)) {
                    if (!next.equals(PdfName.f557C)) {
                        int type = pdfObject.type();
                        if (type == 0) {
                            findActivesFromReference((PdfIndirectReference) pdfObject, arrayList, hashSet);
                        } else if (type == 5) {
                            findActivesFromArray((PdfArray) pdfObject, arrayList, hashSet, hashSet2);
                        } else if (type == 6 || type == 7) {
                            findActivesFromDict((PdfDictionary) pdfObject, arrayList, hashSet, hashSet2);
                        }
                    } else if (pdfObject.isArray()) {
                        Iterator<PdfObject> it = ((PdfArray) pdfObject).iterator();
                        while (it.hasNext()) {
                            PdfObject next2 = it.next();
                            if (next2.isName()) {
                                hashSet2.add((PdfName) next2);
                            }
                        }
                    } else if (pdfObject.isName()) {
                        hashSet2.add((PdfName) pdfObject);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void flushIndirectObjects() throws IOException {
        Iterator<PdfIndirectObject> it = this.savedObjects.iterator();
        while (it.hasNext()) {
            PdfIndirectObject next = it.next();
            this.indirectObjects.remove(new RefKey(next.number, next.generation));
        }
        HashSet hashSet = new HashSet();
        for (Map.Entry next2 : this.indirectObjects.entrySet()) {
            if (next2.getValue() != null) {
                writeObjectToBody((PdfIndirectObject) next2.getValue());
            } else {
                hashSet.add(next2.getKey());
            }
        }
        Iterator it2 = new ArrayList(this.body.xrefs).iterator();
        while (it2.hasNext()) {
            PdfWriter.PdfBody.PdfCrossReference pdfCrossReference = (PdfWriter.PdfBody.PdfCrossReference) it2.next();
            if (hashSet.contains(new RefKey(pdfCrossReference.getRefnum(), 0))) {
                this.body.xrefs.remove(pdfCrossReference);
            }
        }
        this.indirectObjects = null;
    }

    /* JADX WARNING: type inference failed for: r0v6, types: [com.itextpdf.text.pdf.PdfObject] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void writeObjectToBody(com.itextpdf.text.pdf.PdfIndirectObject r8) throws java.io.IOException {
        /*
            r7 = this;
            boolean r0 = r7.mergeFields
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0084
            com.itextpdf.text.pdf.PdfObject r0 = r8.object
            r7.updateAnnotationReferences(r0)
            com.itextpdf.text.pdf.PdfObject r0 = r8.object
            boolean r0 = r0.isDictionary()
            if (r0 != 0) goto L_0x001b
            com.itextpdf.text.pdf.PdfObject r0 = r8.object
            boolean r0 = r0.isStream()
            if (r0 == 0) goto L_0x0084
        L_0x001b:
            com.itextpdf.text.pdf.PdfObject r0 = r8.object
            com.itextpdf.text.pdf.PdfDictionary r0 = (com.itextpdf.text.pdf.PdfDictionary) r0
            java.util.HashMap<com.itextpdf.text.pdf.RefKey, com.itextpdf.text.pdf.PdfIndirectObject> r3 = r7.unmergedIndirectRefsMap
            com.itextpdf.text.pdf.RefKey r4 = new com.itextpdf.text.pdf.RefKey
            int r5 = r8.number
            int r6 = r8.generation
            r4.<init>(r5, r6)
            boolean r3 = r3.containsKey(r4)
            if (r3 == 0) goto L_0x0049
            com.itextpdf.text.pdf.PdfName r3 = annotId
            com.itextpdf.text.pdf.PdfNumber r3 = r0.getAsNumber(r3)
            if (r3 == 0) goto L_0x0049
            java.util.HashMap<java.lang.Integer, com.itextpdf.text.pdf.PdfIndirectObject> r4 = r7.mergedMap
            int r3 = r3.intValue()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            boolean r3 = r4.containsKey(r3)
            if (r3 == 0) goto L_0x0049
            r2 = 1
        L_0x0049:
            java.util.HashSet<com.itextpdf.text.pdf.PdfIndirectObject> r3 = r7.mergedSet
            boolean r3 = r3.contains(r8)
            if (r3 == 0) goto L_0x0084
            com.itextpdf.text.pdf.PdfName r3 = annotId
            com.itextpdf.text.pdf.PdfNumber r3 = r0.getAsNumber(r3)
            if (r3 == 0) goto L_0x0084
            java.util.HashMap<java.lang.Integer, com.itextpdf.text.pdf.PdfIndirectObject> r4 = r7.unmergedMap
            int r3 = r3.intValue()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.lang.Object r3 = r4.get(r3)
            com.itextpdf.text.pdf.PdfIndirectObject r3 = (com.itextpdf.text.pdf.PdfIndirectObject) r3
            if (r3 == 0) goto L_0x0084
            com.itextpdf.text.pdf.PdfObject r4 = r3.object
            boolean r4 = r4.isDictionary()
            if (r4 == 0) goto L_0x0084
            com.itextpdf.text.pdf.PdfObject r3 = r3.object
            com.itextpdf.text.pdf.PdfDictionary r3 = (com.itextpdf.text.pdf.PdfDictionary) r3
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.STRUCTPARENT
            com.itextpdf.text.pdf.PdfNumber r3 = r3.getAsNumber(r4)
            if (r3 == 0) goto L_0x0084
            com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.STRUCTPARENT
            r0.put(r4, r3)
        L_0x0084:
            if (r2 != 0) goto L_0x00b9
            boolean r0 = r7.mergeFields
            r2 = 0
            if (r0 == 0) goto L_0x00a6
            com.itextpdf.text.pdf.PdfObject r0 = r8.object
            boolean r0 = r0.isDictionary()
            if (r0 == 0) goto L_0x00a6
            com.itextpdf.text.pdf.PdfObject r0 = r8.object
            r2 = r0
            com.itextpdf.text.pdf.PdfDictionary r2 = (com.itextpdf.text.pdf.PdfDictionary) r2
            com.itextpdf.text.pdf.PdfName r0 = annotId
            com.itextpdf.text.pdf.PdfNumber r3 = r2.getAsNumber(r0)
            if (r3 == 0) goto L_0x00a3
            r2.remove(r0)
        L_0x00a3:
            r0 = r2
            r2 = r3
            goto L_0x00a7
        L_0x00a6:
            r0 = r2
        L_0x00a7:
            com.itextpdf.text.pdf.PdfWriter$PdfBody r3 = r7.body
            com.itextpdf.text.pdf.PdfObject r4 = r8.object
            int r5 = r8.number
            int r8 = r8.generation
            r3.add(r4, r5, r8, r1)
            if (r2 == 0) goto L_0x00b9
            com.itextpdf.text.pdf.PdfName r8 = annotId
            r0.put(r8, r2)
        L_0x00b9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfCopy.writeObjectToBody(com.itextpdf.text.pdf.PdfIndirectObject):void");
    }

    private void updateAnnotationReferences(PdfObject pdfObject) {
        PdfNumber asNumber;
        PdfIndirectObject pdfIndirectObject;
        PdfNumber asNumber2;
        PdfIndirectObject pdfIndirectObject2;
        if (pdfObject.isArray()) {
            PdfArray pdfArray = (PdfArray) pdfObject;
            for (int i = 0; i < pdfArray.size(); i++) {
                PdfObject pdfObject2 = pdfArray.getPdfObject(i);
                if (pdfObject2 == null || pdfObject2.type() != 0) {
                    updateAnnotationReferences(pdfObject2);
                } else {
                    PdfIndirectObject pdfIndirectObject3 = this.unmergedIndirectRefsMap.get(new RefKey((PdfIndirectReference) pdfObject2));
                    if (!(pdfIndirectObject3 == null || !pdfIndirectObject3.object.isDictionary() || (asNumber2 = ((PdfDictionary) pdfIndirectObject3.object).getAsNumber(annotId)) == null || (pdfIndirectObject2 = this.mergedMap.get(Integer.valueOf(asNumber2.intValue()))) == null)) {
                        pdfArray.set(i, pdfIndirectObject2.getIndirectReference());
                    }
                }
            }
        } else if (pdfObject.isDictionary() || pdfObject.isStream()) {
            PdfDictionary pdfDictionary = (PdfDictionary) pdfObject;
            for (PdfName next : pdfDictionary.getKeys()) {
                PdfObject pdfObject3 = pdfDictionary.get(next);
                if (pdfObject3 == null || pdfObject3.type() != 0) {
                    updateAnnotationReferences(pdfObject3);
                } else {
                    PdfIndirectObject pdfIndirectObject4 = this.unmergedIndirectRefsMap.get(new RefKey((PdfIndirectReference) pdfObject3));
                    if (!(pdfIndirectObject4 == null || !pdfIndirectObject4.object.isDictionary() || (asNumber = ((PdfDictionary) pdfIndirectObject4.object).getAsNumber(annotId)) == null || (pdfIndirectObject = this.mergedMap.get(Integer.valueOf(asNumber.intValue()))) == null)) {
                        pdfDictionary.put(next, pdfIndirectObject.getIndirectReference());
                    }
                }
            }
        }
    }

    private void updateCalculationOrder(PdfReader pdfReader) {
        PdfArray asArray;
        PdfDictionary asDict = pdfReader.getCatalog().getAsDict(PdfName.ACROFORM);
        if (asDict != null && (asArray = asDict.getAsArray(PdfName.f565CO)) != null && asArray.size() != 0) {
            AcroFields acroFields = pdfReader.getAcroFields();
            for (int i = 0; i < asArray.size(); i++) {
                PdfObject pdfObject = asArray.getPdfObject(i);
                if (pdfObject != null && pdfObject.isIndirect()) {
                    String cOName = getCOName(pdfReader, (PRIndirectReference) pdfObject);
                    if (acroFields.getFieldItem(cOName) != null) {
                        String str = "." + cOName;
                        if (!this.calculationOrder.contains(str)) {
                            this.calculationOrder.add(str);
                        }
                    }
                }
            }
        }
    }

    private static String getCOName(PdfReader pdfReader, PRIndirectReference pRIndirectReference) {
        PdfObject pdfObject;
        String str = "";
        while (pRIndirectReference != null && (pdfObject = PdfReader.getPdfObject((PdfObject) pRIndirectReference)) != null && pdfObject.type() == 6) {
            PdfDictionary pdfDictionary = (PdfDictionary) pdfObject;
            PdfString asString = pdfDictionary.getAsString(PdfName.f647T);
            if (asString != null) {
                str = asString.toUnicodeString() + "." + str;
            }
            pRIndirectReference = (PRIndirectReference) pdfDictionary.get(PdfName.PARENT);
        }
        return str.endsWith(".") ? str.substring(0, str.length() - 2) : str;
    }

    private void mergeFields() {
        int i = 0;
        for (int i2 = 0; i2 < this.fields.size(); i2++) {
            AcroFields acroFields = this.fields.get(i2);
            Map<String, AcroFields.Item> fields2 = acroFields.getFields();
            if (i < this.importedPages.size() && this.importedPages.get(i).reader == acroFields.reader) {
                addPageOffsetToField(fields2, i);
                i += acroFields.reader.getNumberOfPages();
            }
            mergeWithMaster(fields2);
        }
    }

    private void addPageOffsetToField(Map<String, AcroFields.Item> map, int i) {
        if (i != 0) {
            for (AcroFields.Item next : map.values()) {
                for (int i2 = 0; i2 < next.size(); i2++) {
                    next.forcePage(i2, next.getPage(i2).intValue() + i);
                }
            }
        }
    }

    private void mergeWithMaster(Map<String, AcroFields.Item> map) {
        for (Map.Entry next : map.entrySet()) {
            mergeField((String) next.getKey(), (AcroFields.Item) next.getValue());
        }
    }

    private void mergeField(String str, AcroFields.Item item) {
        HashMap<String, Object> hashMap = this.fieldTree;
        StringTokenizer stringTokenizer = new StringTokenizer(str, ".");
        if (stringTokenizer.hasMoreTokens()) {
            while (true) {
                String nextToken = stringTokenizer.nextToken();
                Object obj = hashMap.get(nextToken);
                if (stringTokenizer.hasMoreTokens()) {
                    if (obj == null) {
                        LinkedHashMap linkedHashMap = new LinkedHashMap();
                        hashMap.put(nextToken, linkedHashMap);
                        hashMap = linkedHashMap;
                    } else if (obj instanceof HashMap) {
                        hashMap = obj;
                    } else {
                        return;
                    }
                } else if (!(obj instanceof HashMap)) {
                    int i = 0;
                    PdfDictionary merged = item.getMerged(0);
                    if (obj == null) {
                        PdfDictionary pdfDictionary = new PdfDictionary();
                        if (PdfName.SIG.equals(merged.get(PdfName.f588FT))) {
                            this.hasSignature = true;
                        }
                        for (PdfName next : merged.getKeys()) {
                            if (fieldKeys.contains(next)) {
                                pdfDictionary.put(next, merged.get(next));
                            }
                        }
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(pdfDictionary);
                        createWidgets(arrayList, item);
                        hashMap.put(nextToken, arrayList);
                        return;
                    }
                    ArrayList arrayList2 = (ArrayList) obj;
                    PdfDictionary pdfDictionary2 = (PdfDictionary) arrayList2.get(0);
                    PdfName pdfName = (PdfName) pdfDictionary2.get(PdfName.f588FT);
                    PdfName pdfName2 = (PdfName) merged.get(PdfName.f588FT);
                    if (pdfName != null && pdfName.equals(pdfName2)) {
                        PdfObject pdfObject = pdfDictionary2.get(PdfName.f583FF);
                        int intValue = (pdfObject == null || !pdfObject.isNumber()) ? 0 : ((PdfNumber) pdfObject).intValue();
                        PdfObject pdfObject2 = merged.get(PdfName.f583FF);
                        if (pdfObject2 != null && pdfObject2.isNumber()) {
                            i = ((PdfNumber) pdfObject2).intValue();
                        }
                        if (pdfName.equals(PdfName.BTN)) {
                            int i2 = intValue ^ i;
                            if ((i2 & 65536) == 0) {
                                if ((intValue & 65536) == 0 && (32768 & i2) != 0) {
                                    return;
                                }
                            } else {
                                return;
                            }
                        } else if (pdfName.equals(PdfName.f563CH) && ((intValue ^ i) & 131072) != 0) {
                            return;
                        }
                        createWidgets(arrayList2, item);
                        return;
                    }
                    return;
                } else {
                    return;
                }
            }
        }
    }

    private void createWidgets(ArrayList<Object> arrayList, AcroFields.Item item) {
        for (int i = 0; i < item.size(); i++) {
            arrayList.add(item.getPage(i));
            PdfDictionary merged = item.getMerged(i);
            PdfObject pdfObject = merged.get(PdfName.f574DR);
            if (pdfObject != null) {
                PdfFormField.mergeResources(this.resources, (PdfDictionary) PdfReader.getPdfObject(pdfObject));
            }
            PdfDictionary pdfDictionary = new PdfDictionary();
            for (PdfName next : merged.getKeys()) {
                if (widgetKeys.contains(next)) {
                    pdfDictionary.put(next, merged.get(next));
                }
            }
            pdfDictionary.put(iTextTag, new PdfNumber(item.getTabOrder(i).intValue() + 1));
            arrayList.add(pdfDictionary);
        }
    }

    private PdfObject propagate(PdfObject pdfObject) throws IOException {
        if (pdfObject == null) {
            return new PdfNull();
        }
        if (pdfObject.isArray()) {
            PdfArray pdfArray = (PdfArray) pdfObject;
            for (int i = 0; i < pdfArray.size(); i++) {
                pdfArray.set(i, propagate(pdfArray.getPdfObject(i)));
            }
            return pdfArray;
        } else if (!pdfObject.isDictionary() && !pdfObject.isStream()) {
            return pdfObject.isIndirect() ? addToBody(propagate(PdfReader.getPdfObject(pdfObject))).getIndirectReference() : pdfObject;
        } else {
            PdfDictionary pdfDictionary = (PdfDictionary) pdfObject;
            for (PdfName next : pdfDictionary.getKeys()) {
                pdfDictionary.put(next, propagate(pdfDictionary.get(next)));
            }
            return pdfDictionary;
        }
    }

    private void createAcroForms() throws IOException, BadPdfFormatException {
        if (this.fieldTree.isEmpty()) {
            Iterator<ImportedPage> it = this.importedPages.iterator();
            while (it.hasNext()) {
                ImportedPage next = it.next();
                if (next.mergedFields.size() > 0) {
                    addToBody(next.mergedFields, next.annotsIndirectReference);
                }
            }
            return;
        }
        PdfDictionary pdfDictionary = new PdfDictionary();
        pdfDictionary.put(PdfName.f574DR, propagate(this.resources));
        if (this.needAppearances) {
            pdfDictionary.put(PdfName.NEEDAPPEARANCES, PdfBoolean.PDFTRUE);
        }
        pdfDictionary.put(PdfName.f568DA, new PdfString("/Helv 0 Tf 0 g "));
        this.tabOrder = new HashMap<>();
        this.calculationOrderRefs = new ArrayList<>(this.calculationOrder);
        pdfDictionary.put(PdfName.FIELDS, branchForm(this.fieldTree, (PdfIndirectReference) null, ""));
        if (this.hasSignature) {
            pdfDictionary.put(PdfName.SIGFLAGS, new PdfNumber(3));
        }
        PdfArray pdfArray = new PdfArray();
        for (int i = 0; i < this.calculationOrderRefs.size(); i++) {
            Object obj = this.calculationOrderRefs.get(i);
            if (obj instanceof PdfIndirectReference) {
                pdfArray.add((PdfObject) (PdfIndirectReference) obj);
            }
        }
        if (pdfArray.size() > 0) {
            pdfDictionary.put(PdfName.f565CO, pdfArray);
        }
        this.acroForm = addToBody(pdfDictionary).getIndirectReference();
        Iterator<ImportedPage> it2 = this.importedPages.iterator();
        while (it2.hasNext()) {
            ImportedPage next2 = it2.next();
            addToBody(next2.mergedFields, next2.annotsIndirectReference);
        }
    }

    private void updateReferences(PdfObject pdfObject) {
        if (pdfObject.isDictionary() || pdfObject.isStream()) {
            PdfDictionary pdfDictionary = (PdfDictionary) pdfObject;
            for (PdfName next : pdfDictionary.getKeys()) {
                PdfObject pdfObject2 = pdfDictionary.get(next);
                if (pdfObject2.isIndirect()) {
                    PRIndirectReference pRIndirectReference = (PRIndirectReference) pdfObject2;
                    IndirectReferences indirectReferences = (IndirectReferences) this.indirectMap.get(pRIndirectReference.getReader()).get(new RefKey(pRIndirectReference));
                    if (indirectReferences != null) {
                        pdfDictionary.put(next, indirectReferences.getRef());
                    }
                } else {
                    updateReferences(pdfObject2);
                }
            }
        } else if (pdfObject.isArray()) {
            PdfArray pdfArray = (PdfArray) pdfObject;
            for (int i = 0; i < pdfArray.size(); i++) {
                PdfObject pdfObject3 = pdfArray.getPdfObject(i);
                if (pdfObject3.isIndirect()) {
                    PRIndirectReference pRIndirectReference2 = (PRIndirectReference) pdfObject3;
                    IndirectReferences indirectReferences2 = (IndirectReferences) this.indirectMap.get(pRIndirectReference2.getReader()).get(new RefKey(pRIndirectReference2));
                    if (indirectReferences2 != null) {
                        pdfArray.set(i, indirectReferences2.getRef());
                    }
                } else {
                    updateReferences(pdfObject3);
                }
            }
        }
    }

    private PdfArray branchForm(HashMap<String, Object> hashMap, PdfIndirectReference pdfIndirectReference, String str) throws IOException, BadPdfFormatException {
        Iterator<Map.Entry<String, Object>> it;
        boolean z;
        Iterator<Map.Entry<String, Object>> it2;
        PdfIndirectReference pdfIndirectReference2 = pdfIndirectReference;
        PdfArray pdfArray = new PdfArray();
        Iterator<Map.Entry<String, Object>> it3 = hashMap.entrySet().iterator();
        while (it3.hasNext()) {
            Map.Entry next = it3.next();
            String str2 = (String) next.getKey();
            Object value = next.getValue();
            PdfIndirectReference pdfIndirectReference3 = getPdfIndirectReference();
            PdfDictionary pdfDictionary = new PdfDictionary();
            if (pdfIndirectReference2 != null) {
                pdfDictionary.put(PdfName.PARENT, pdfIndirectReference2);
            }
            pdfDictionary.put(PdfName.f647T, new PdfString(str2, PdfObject.TEXT_UNICODE));
            String str3 = str + "." + str2;
            int indexOf = this.calculationOrder.indexOf(str3);
            if (indexOf >= 0) {
                this.calculationOrderRefs.set(indexOf, pdfIndirectReference3);
            }
            int i = 1;
            if (value instanceof HashMap) {
                pdfDictionary.put(PdfName.KIDS, branchForm((HashMap) value, pdfIndirectReference3, str3));
                pdfArray.add((PdfObject) pdfIndirectReference3);
                addToBody(pdfDictionary, pdfIndirectReference3, true);
                it = it3;
            } else {
                ArrayList arrayList = (ArrayList) value;
                pdfDictionary.mergeDifferent((PdfDictionary) arrayList.get(0));
                if (arrayList.size() == 3) {
                    pdfDictionary.mergeDifferent((PdfDictionary) arrayList.get(2));
                    PdfArray pdfArray2 = this.importedPages.get(((Integer) arrayList.get(1)).intValue() - 1).mergedFields;
                    PdfName pdfName = iTextTag;
                    pdfDictionary.remove(pdfName);
                    pdfDictionary.put(PdfName.TYPE, PdfName.ANNOT);
                    adjustTabOrder(pdfArray2, pdfIndirectReference3, (PdfNumber) pdfDictionary.get(pdfName));
                    it = it3;
                    z = true;
                } else {
                    PdfDictionary pdfDictionary2 = (PdfDictionary) arrayList.get(0);
                    PdfArray pdfArray3 = new PdfArray();
                    int i2 = 1;
                    while (i2 < arrayList.size()) {
                        PdfArray pdfArray4 = this.importedPages.get(((Integer) arrayList.get(i2)).intValue() - i).mergedFields;
                        PdfDictionary pdfDictionary3 = new PdfDictionary();
                        pdfDictionary3.merge((PdfDictionary) arrayList.get(i2 + 1));
                        pdfDictionary3.put(PdfName.PARENT, pdfIndirectReference3);
                        PdfName pdfName2 = iTextTag;
                        PdfNumber pdfNumber = (PdfNumber) pdfDictionary3.get(pdfName2);
                        pdfDictionary3.remove(pdfName2);
                        if (isTextField(pdfDictionary2)) {
                            PdfString asString = pdfDictionary2.getAsString(PdfName.f664V);
                            PdfObject directObject = pdfDictionary3.getDirectObject(PdfName.f549AP);
                            if (!(asString == null || directObject == null)) {
                                if (!this.mergedTextFields.containsKey(arrayList)) {
                                    this.mergedTextFields.put(arrayList, asString);
                                } else {
                                    try {
                                        TextField textField = new TextField(this, (Rectangle) null, (String) null);
                                        it2 = it3;
                                        try {
                                            this.fields.get(0).decodeGenericDictionary(pdfDictionary3, textField);
                                            Rectangle normalizedRectangle = PdfReader.getNormalizedRectangle(pdfDictionary3.getAsArray(PdfName.RECT));
                                            if (textField.getRotation() == 90 || textField.getRotation() == 270) {
                                                normalizedRectangle = normalizedRectangle.rotate();
                                            }
                                            textField.setBox(normalizedRectangle);
                                            textField.setText(this.mergedTextFields.get(arrayList).toUnicodeString());
                                            ((PdfDictionary) directObject).put(PdfName.f610N, textField.getAppearance().getIndirectReference());
                                        } catch (DocumentException unused) {
                                        }
                                    } catch (DocumentException unused2) {
                                    }
                                }
                            }
                            it2 = it3;
                        } else {
                            it2 = it3;
                            if (isCheckButton(pdfDictionary2)) {
                                PdfName asName = pdfDictionary2.getAsName(PdfName.f664V);
                                PdfName asName2 = pdfDictionary3.getAsName(PdfName.f550AS);
                                if (!(asName == null || asName2 == null)) {
                                    pdfDictionary3.put(PdfName.f550AS, asName);
                                }
                            } else if (isRadioButton(pdfDictionary2)) {
                                PdfName asName3 = pdfDictionary2.getAsName(PdfName.f664V);
                                PdfName asName4 = pdfDictionary3.getAsName(PdfName.f550AS);
                                if (!(asName3 == null || asName4 == null || asName4.equals(getOffStateName(pdfDictionary3)))) {
                                    if (!this.mergedRadioButtons.contains(arrayList)) {
                                        this.mergedRadioButtons.add(arrayList);
                                        pdfDictionary3.put(PdfName.f550AS, asName3);
                                    } else {
                                        pdfDictionary3.put(PdfName.f550AS, getOffStateName(pdfDictionary3));
                                    }
                                }
                            }
                        }
                        pdfDictionary3.put(PdfName.TYPE, PdfName.ANNOT);
                        PdfIndirectReference indirectReference = addToBody(pdfDictionary3, getPdfIndirectReference(), true).getIndirectReference();
                        adjustTabOrder(pdfArray4, indirectReference, pdfNumber);
                        pdfArray3.add((PdfObject) indirectReference);
                        i2 += 2;
                        PdfIndirectReference pdfIndirectReference4 = pdfIndirectReference;
                        String str4 = str;
                        it3 = it2;
                        i = 1;
                    }
                    it = it3;
                    z = true;
                    pdfDictionary.put(PdfName.KIDS, pdfArray3);
                }
                pdfArray.add((PdfObject) pdfIndirectReference3);
                addToBody(pdfDictionary, pdfIndirectReference3, z);
            }
            pdfIndirectReference2 = pdfIndirectReference;
            it3 = it;
        }
        return pdfArray;
    }

    private void adjustTabOrder(PdfArray pdfArray, PdfIndirectReference pdfIndirectReference, PdfNumber pdfNumber) {
        int intValue = pdfNumber.intValue();
        ArrayList arrayList = this.tabOrder.get(pdfArray);
        if (arrayList == null) {
            ArrayList arrayList2 = new ArrayList();
            int size = pdfArray.size() - 1;
            for (int i = 0; i < size; i++) {
                arrayList2.add(zero);
            }
            arrayList2.add(Integer.valueOf(intValue));
            this.tabOrder.put(pdfArray, arrayList2);
            pdfArray.add((PdfObject) pdfIndirectReference);
            return;
        }
        int size2 = arrayList.size() - 1;
        int i2 = size2;
        while (true) {
            if (i2 < 0) {
                break;
            } else if (((Integer) arrayList.get(i2)).intValue() <= intValue) {
                int i3 = i2 + 1;
                arrayList.add(i3, Integer.valueOf(intValue));
                pdfArray.add(i3, pdfIndirectReference);
                size2 = -2;
                break;
            } else {
                i2--;
            }
        }
        if (size2 != -2) {
            arrayList.add(0, Integer.valueOf(intValue));
            pdfArray.add(0, pdfIndirectReference);
        }
    }

    /* access modifiers changed from: protected */
    public PdfDictionary getCatalog(PdfIndirectReference pdfIndirectReference) {
        try {
            PdfDocument.PdfCatalog catalog = this.pdf.getCatalog(pdfIndirectReference);
            buildStructTreeRootForTagged(catalog);
            if (this.fieldArray != null) {
                addFieldResources(catalog);
            } else if (this.mergeFields && this.acroForm != null) {
                catalog.put(PdfName.ACROFORM, this.acroForm);
            }
            return catalog;
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isStructTreeRootReference(PdfIndirectReference pdfIndirectReference) {
        if (pdfIndirectReference == null || this.structTreeRootReference == null || pdfIndirectReference.number != this.structTreeRootReference.number || pdfIndirectReference.generation != this.structTreeRootReference.generation) {
            return false;
        }
        return true;
    }

    private void addFieldResources(PdfDictionary pdfDictionary) throws IOException {
        if (this.fieldArray != null) {
            PdfDictionary pdfDictionary2 = new PdfDictionary();
            pdfDictionary.put(PdfName.ACROFORM, pdfDictionary2);
            pdfDictionary2.put(PdfName.FIELDS, this.fieldArray);
            pdfDictionary2.put(PdfName.f568DA, new PdfString("/Helv 0 Tf 0 g "));
            if (!this.fieldTemplates.isEmpty()) {
                PdfDictionary pdfDictionary3 = new PdfDictionary();
                pdfDictionary2.put(PdfName.f574DR, pdfDictionary3);
                Iterator<PdfTemplate> it = this.fieldTemplates.iterator();
                while (it.hasNext()) {
                    PdfFormField.mergeResources(pdfDictionary3, (PdfDictionary) it.next().getResources());
                }
                PdfDictionary asDict = pdfDictionary3.getAsDict(PdfName.FONT);
                if (asDict == null) {
                    asDict = new PdfDictionary();
                    pdfDictionary3.put(PdfName.FONT, asDict);
                }
                if (!asDict.contains(PdfName.HELV)) {
                    PdfDictionary pdfDictionary4 = new PdfDictionary(PdfName.FONT);
                    pdfDictionary4.put(PdfName.BASEFONT, PdfName.HELVETICA);
                    pdfDictionary4.put(PdfName.ENCODING, PdfName.WIN_ANSI_ENCODING);
                    pdfDictionary4.put(PdfName.NAME, PdfName.HELV);
                    pdfDictionary4.put(PdfName.SUBTYPE, PdfName.TYPE1);
                    asDict.put(PdfName.HELV, addToBody(pdfDictionary4).getIndirectReference());
                }
                if (!asDict.contains(PdfName.ZADB)) {
                    PdfDictionary pdfDictionary5 = new PdfDictionary(PdfName.FONT);
                    pdfDictionary5.put(PdfName.BASEFONT, PdfName.ZAPFDINGBATS);
                    pdfDictionary5.put(PdfName.NAME, PdfName.ZADB);
                    pdfDictionary5.put(PdfName.SUBTYPE, PdfName.TYPE1);
                    asDict.put(PdfName.ZADB, addToBody(pdfDictionary5).getIndirectReference());
                }
            }
        }
    }

    public void close() {
        if (this.open) {
            this.pdf.close();
            super.close();
        }
    }

    public void freeReader(PdfReader pdfReader) throws IOException {
        if (!this.mergeFields) {
            PdfArray asArray = pdfReader.trailer.getAsArray(PdfName.f600ID);
            if (asArray != null) {
                this.originalFileID = asArray.getAsString(0).getBytes();
            }
            this.indirectMap.remove(pdfReader);
            this.currentPdfReaderInstance = null;
            super.freeReader(pdfReader);
            return;
        }
        throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("it.is.not.possible.to.free.reader.in.merge.fields.mode", new Object[0]));
    }

    /* access modifiers changed from: protected */
    public PdfName getOffStateName(PdfDictionary pdfDictionary) {
        return PdfName.Off;
    }

    static Integer getFlags(PdfDictionary pdfDictionary) {
        PdfNumber asNumber;
        if (PdfName.BTN.equals(pdfDictionary.getAsName(PdfName.f588FT)) && (asNumber = pdfDictionary.getAsNumber(PdfName.f583FF)) != null) {
            return Integer.valueOf(asNumber.intValue());
        }
        return null;
    }

    static boolean isCheckButton(PdfDictionary pdfDictionary) {
        Integer flags = getFlags(pdfDictionary);
        return flags == null || ((flags.intValue() & 65536) == 0 && (flags.intValue() & 32768) == 0);
    }

    static boolean isRadioButton(PdfDictionary pdfDictionary) {
        Integer flags = getFlags(pdfDictionary);
        return (flags == null || (flags.intValue() & 65536) != 0 || (flags.intValue() & 32768) == 0) ? false : true;
    }

    static boolean isTextField(PdfDictionary pdfDictionary) {
        return PdfName.f659TX.equals(pdfDictionary.getAsName(PdfName.f588FT));
    }

    public PageStamp createPageStamp(PdfImportedPage pdfImportedPage) {
        int pageNumber = pdfImportedPage.getPageNumber();
        PdfReader reader2 = pdfImportedPage.getPdfReaderInstance().getReader();
        if (!isTagged()) {
            return new PageStamp(reader2, reader2.getPageN(pageNumber), this);
        }
        throw new RuntimeException(MessageLocalization.getComposedMessage("creating.page.stamp.not.allowed.for.tagged.reader", new Object[0]));
    }

    public static class PageStamp {
        PdfCopy cstp;
        StampContent over;
        PdfDictionary pageN;
        PageResources pageResources;
        PdfReader reader;
        StampContent under;

        PageStamp(PdfReader pdfReader, PdfDictionary pdfDictionary, PdfCopy pdfCopy) {
            this.pageN = pdfDictionary;
            this.reader = pdfReader;
            this.cstp = pdfCopy;
        }

        public PdfContentByte getUnderContent() {
            if (this.under == null) {
                if (this.pageResources == null) {
                    this.pageResources = new PageResources();
                    this.pageResources.setOriginalResources(this.pageN.getAsDict(PdfName.RESOURCES), this.cstp.namePtr);
                }
                this.under = new StampContent(this.cstp, this.pageResources);
            }
            return this.under;
        }

        public PdfContentByte getOverContent() {
            if (this.over == null) {
                if (this.pageResources == null) {
                    this.pageResources = new PageResources();
                    this.pageResources.setOriginalResources(this.pageN.getAsDict(PdfName.RESOURCES), this.cstp.namePtr);
                }
                this.over = new StampContent(this.cstp, this.pageResources);
            }
            return this.over;
        }

        public void alterContents() throws IOException {
            PdfArray pdfArray;
            if (this.over != null || this.under != null) {
                PdfObject pdfObject = PdfReader.getPdfObject(this.pageN.get(PdfName.CONTENTS), this.pageN);
                if (pdfObject == null) {
                    pdfArray = new PdfArray();
                    this.pageN.put(PdfName.CONTENTS, pdfArray);
                } else if (pdfObject.isArray()) {
                    pdfArray = (PdfArray) pdfObject;
                } else if (pdfObject.isStream()) {
                    pdfArray = new PdfArray();
                    pdfArray.add(this.pageN.get(PdfName.CONTENTS));
                    this.pageN.put(PdfName.CONTENTS, pdfArray);
                } else {
                    pdfArray = new PdfArray();
                    this.pageN.put(PdfName.CONTENTS, pdfArray);
                }
                ByteBuffer byteBuffer = new ByteBuffer();
                if (this.under != null) {
                    byteBuffer.append(PdfContents.SAVESTATE);
                    applyRotation(this.pageN, byteBuffer);
                    byteBuffer.append(this.under.getInternalBuffer());
                    byteBuffer.append(PdfContents.RESTORESTATE);
                }
                if (this.over != null) {
                    byteBuffer.append(PdfContents.SAVESTATE);
                }
                PdfStream pdfStream = new PdfStream(byteBuffer.toByteArray());
                pdfStream.flateCompress(this.cstp.getCompressionLevel());
                pdfArray.addFirst(this.cstp.addToBody(pdfStream).getIndirectReference());
                byteBuffer.reset();
                if (this.over != null) {
                    byteBuffer.append(' ');
                    byteBuffer.append(PdfContents.RESTORESTATE);
                    byteBuffer.append(PdfContents.SAVESTATE);
                    applyRotation(this.pageN, byteBuffer);
                    byteBuffer.append(this.over.getInternalBuffer());
                    byteBuffer.append(PdfContents.RESTORESTATE);
                    PdfStream pdfStream2 = new PdfStream(byteBuffer.toByteArray());
                    pdfStream2.flateCompress(this.cstp.getCompressionLevel());
                    pdfArray.add((PdfObject) this.cstp.addToBody(pdfStream2).getIndirectReference());
                }
                this.pageN.put(PdfName.RESOURCES, this.pageResources.getResources());
            }
        }

        /* access modifiers changed from: package-private */
        public void applyRotation(PdfDictionary pdfDictionary, ByteBuffer byteBuffer) {
            if (this.cstp.rotateContents) {
                Rectangle pageSizeWithRotation = this.reader.getPageSizeWithRotation(pdfDictionary);
                int rotation = pageSizeWithRotation.getRotation();
                if (rotation == 90) {
                    byteBuffer.append(PdfContents.ROTATE90);
                    byteBuffer.append(pageSizeWithRotation.getTop());
                    byteBuffer.append(' ').append('0').append(PdfContents.ROTATEFINAL);
                } else if (rotation == 180) {
                    byteBuffer.append(PdfContents.ROTATE180);
                    byteBuffer.append(pageSizeWithRotation.getRight());
                    byteBuffer.append(' ');
                    byteBuffer.append(pageSizeWithRotation.getTop());
                    byteBuffer.append(PdfContents.ROTATEFINAL);
                } else if (rotation == 270) {
                    byteBuffer.append(PdfContents.ROTATE270);
                    byteBuffer.append('0').append(' ');
                    byteBuffer.append(pageSizeWithRotation.getRight());
                    byteBuffer.append(PdfContents.ROTATEFINAL);
                }
            }
        }

        private void addDocumentField(PdfIndirectReference pdfIndirectReference) {
            if (this.cstp.fieldArray == null) {
                this.cstp.fieldArray = new PdfArray();
            }
            this.cstp.fieldArray.add((PdfObject) pdfIndirectReference);
        }

        private void expandFields(PdfFormField pdfFormField, ArrayList<PdfAnnotation> arrayList) {
            arrayList.add(pdfFormField);
            ArrayList<PdfFormField> kids = pdfFormField.getKids();
            if (kids != null) {
                Iterator<PdfFormField> it = kids.iterator();
                while (it.hasNext()) {
                    expandFields(it.next(), arrayList);
                }
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:46:0x00d8 A[Catch:{ IOException -> 0x0170 }] */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x0135 A[Catch:{ IOException -> 0x0170 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void addAnnotation(com.itextpdf.text.pdf.PdfAnnotation r11) {
            /*
                r10 = this;
                java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ IOException -> 0x0170 }
                r0.<init>()     // Catch:{ IOException -> 0x0170 }
                boolean r1 = r11.isForm()     // Catch:{ IOException -> 0x0170 }
                if (r1 == 0) goto L_0x0027
                com.itextpdf.text.pdf.PdfFormField r11 = (com.itextpdf.text.pdf.PdfFormField) r11     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfFormField r1 = r11.getParent()     // Catch:{ IOException -> 0x0170 }
                if (r1 == 0) goto L_0x0014
                return
            L_0x0014:
                r10.expandFields(r11, r0)     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfCopy r11 = r10.cstp     // Catch:{ IOException -> 0x0170 }
                java.util.HashSet<com.itextpdf.text.pdf.PdfTemplate> r11 = r11.fieldTemplates     // Catch:{ IOException -> 0x0170 }
                if (r11 != 0) goto L_0x002a
                com.itextpdf.text.pdf.PdfCopy r11 = r10.cstp     // Catch:{ IOException -> 0x0170 }
                java.util.HashSet r1 = new java.util.HashSet     // Catch:{ IOException -> 0x0170 }
                r1.<init>()     // Catch:{ IOException -> 0x0170 }
                r11.fieldTemplates = r1     // Catch:{ IOException -> 0x0170 }
                goto L_0x002a
            L_0x0027:
                r0.add(r11)     // Catch:{ IOException -> 0x0170 }
            L_0x002a:
                r11 = 0
            L_0x002b:
                int r1 = r0.size()     // Catch:{ IOException -> 0x0170 }
                if (r11 >= r1) goto L_0x016f
                java.lang.Object r1 = r0.get(r11)     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfAnnotation r1 = (com.itextpdf.text.pdf.PdfAnnotation) r1     // Catch:{ IOException -> 0x0170 }
                boolean r2 = r1.isForm()     // Catch:{ IOException -> 0x0170 }
                if (r2 == 0) goto L_0x0060
                boolean r2 = r1.isUsed()     // Catch:{ IOException -> 0x0170 }
                if (r2 != 0) goto L_0x0050
                java.util.HashSet r2 = r1.getTemplates()     // Catch:{ IOException -> 0x0170 }
                if (r2 == 0) goto L_0x0050
                com.itextpdf.text.pdf.PdfCopy r3 = r10.cstp     // Catch:{ IOException -> 0x0170 }
                java.util.HashSet<com.itextpdf.text.pdf.PdfTemplate> r3 = r3.fieldTemplates     // Catch:{ IOException -> 0x0170 }
                r3.addAll(r2)     // Catch:{ IOException -> 0x0170 }
            L_0x0050:
                r2 = r1
                com.itextpdf.text.pdf.PdfFormField r2 = (com.itextpdf.text.pdf.PdfFormField) r2     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfFormField r3 = r2.getParent()     // Catch:{ IOException -> 0x0170 }
                if (r3 != 0) goto L_0x0060
                com.itextpdf.text.pdf.PdfIndirectReference r2 = r2.getIndirectReference()     // Catch:{ IOException -> 0x0170 }
                r10.addDocumentField(r2)     // Catch:{ IOException -> 0x0170 }
            L_0x0060:
                boolean r2 = r1.isAnnotation()     // Catch:{ IOException -> 0x0170 }
                if (r2 == 0) goto L_0x0159
                com.itextpdf.text.pdf.PdfDictionary r2 = r10.pageN     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.ANNOTS     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfObject r2 = r2.get(r3)     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfDictionary r3 = r10.pageN     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfObject r2 = com.itextpdf.text.pdf.PdfReader.getPdfObject(r2, r3)     // Catch:{ IOException -> 0x0170 }
                if (r2 == 0) goto L_0x0080
                boolean r3 = r2.isArray()     // Catch:{ IOException -> 0x0170 }
                if (r3 != 0) goto L_0x007d
                goto L_0x0080
            L_0x007d:
                com.itextpdf.text.pdf.PdfArray r2 = (com.itextpdf.text.pdf.PdfArray) r2     // Catch:{ IOException -> 0x0170 }
                goto L_0x008c
            L_0x0080:
                com.itextpdf.text.pdf.PdfArray r2 = new com.itextpdf.text.pdf.PdfArray     // Catch:{ IOException -> 0x0170 }
                r2.<init>()     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfDictionary r3 = r10.pageN     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfName r4 = com.itextpdf.text.pdf.PdfName.ANNOTS     // Catch:{ IOException -> 0x0170 }
                r3.put(r4, r2)     // Catch:{ IOException -> 0x0170 }
            L_0x008c:
                com.itextpdf.text.pdf.PdfIndirectReference r3 = r1.getIndirectReference()     // Catch:{ IOException -> 0x0170 }
                r2.add((com.itextpdf.text.pdf.PdfObject) r3)     // Catch:{ IOException -> 0x0170 }
                boolean r2 = r1.isUsed()     // Catch:{ IOException -> 0x0170 }
                if (r2 != 0) goto L_0x0159
                com.itextpdf.text.pdf.PdfName r2 = com.itextpdf.text.pdf.PdfName.RECT     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfObject r2 = r1.get(r2)     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfRectangle r2 = (com.itextpdf.text.pdf.PdfRectangle) r2     // Catch:{ IOException -> 0x0170 }
                if (r2 == 0) goto L_0x0159
                float r3 = r2.left()     // Catch:{ IOException -> 0x0170 }
                r4 = 0
                int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
                if (r3 != 0) goto L_0x00c4
                float r3 = r2.right()     // Catch:{ IOException -> 0x0170 }
                int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
                if (r3 != 0) goto L_0x00c4
                float r3 = r2.top()     // Catch:{ IOException -> 0x0170 }
                int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
                if (r3 != 0) goto L_0x00c4
                float r3 = r2.bottom()     // Catch:{ IOException -> 0x0170 }
                int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
                if (r3 == 0) goto L_0x0159
            L_0x00c4:
                com.itextpdf.text.pdf.PdfReader r3 = r10.reader     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfDictionary r4 = r10.pageN     // Catch:{ IOException -> 0x0170 }
                int r3 = r3.getPageRotation((com.itextpdf.text.pdf.PdfDictionary) r4)     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfReader r4 = r10.reader     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfDictionary r5 = r10.pageN     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.Rectangle r4 = r4.getPageSizeWithRotation((com.itextpdf.text.pdf.PdfDictionary) r5)     // Catch:{ IOException -> 0x0170 }
                r5 = 90
                if (r3 == r5) goto L_0x0135
                r5 = 180(0xb4, float:2.52E-43)
                if (r3 == r5) goto L_0x0106
                r5 = 270(0x10e, float:3.78E-43)
                if (r3 == r5) goto L_0x00e1
                goto L_0x0159
            L_0x00e1:
                com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.RECT     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfRectangle r5 = new com.itextpdf.text.pdf.PdfRectangle     // Catch:{ IOException -> 0x0170 }
                float r6 = r2.bottom()     // Catch:{ IOException -> 0x0170 }
                float r7 = r4.getRight()     // Catch:{ IOException -> 0x0170 }
                float r8 = r2.left()     // Catch:{ IOException -> 0x0170 }
                float r7 = r7 - r8
                float r8 = r2.top()     // Catch:{ IOException -> 0x0170 }
                float r4 = r4.getRight()     // Catch:{ IOException -> 0x0170 }
                float r2 = r2.right()     // Catch:{ IOException -> 0x0170 }
                float r4 = r4 - r2
                r5.<init>(r6, r7, r8, r4)     // Catch:{ IOException -> 0x0170 }
                r1.put(r3, r5)     // Catch:{ IOException -> 0x0170 }
                goto L_0x0159
            L_0x0106:
                com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.RECT     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfRectangle r5 = new com.itextpdf.text.pdf.PdfRectangle     // Catch:{ IOException -> 0x0170 }
                float r6 = r4.getRight()     // Catch:{ IOException -> 0x0170 }
                float r7 = r2.left()     // Catch:{ IOException -> 0x0170 }
                float r6 = r6 - r7
                float r7 = r4.getTop()     // Catch:{ IOException -> 0x0170 }
                float r8 = r2.bottom()     // Catch:{ IOException -> 0x0170 }
                float r7 = r7 - r8
                float r8 = r4.getRight()     // Catch:{ IOException -> 0x0170 }
                float r9 = r2.right()     // Catch:{ IOException -> 0x0170 }
                float r8 = r8 - r9
                float r4 = r4.getTop()     // Catch:{ IOException -> 0x0170 }
                float r2 = r2.top()     // Catch:{ IOException -> 0x0170 }
                float r4 = r4 - r2
                r5.<init>(r6, r7, r8, r4)     // Catch:{ IOException -> 0x0170 }
                r1.put(r3, r5)     // Catch:{ IOException -> 0x0170 }
                goto L_0x0159
            L_0x0135:
                com.itextpdf.text.pdf.PdfName r3 = com.itextpdf.text.pdf.PdfName.RECT     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfRectangle r5 = new com.itextpdf.text.pdf.PdfRectangle     // Catch:{ IOException -> 0x0170 }
                float r6 = r4.getTop()     // Catch:{ IOException -> 0x0170 }
                float r7 = r2.bottom()     // Catch:{ IOException -> 0x0170 }
                float r6 = r6 - r7
                float r7 = r2.left()     // Catch:{ IOException -> 0x0170 }
                float r4 = r4.getTop()     // Catch:{ IOException -> 0x0170 }
                float r8 = r2.top()     // Catch:{ IOException -> 0x0170 }
                float r4 = r4 - r8
                float r2 = r2.right()     // Catch:{ IOException -> 0x0170 }
                r5.<init>(r6, r7, r4, r2)     // Catch:{ IOException -> 0x0170 }
                r1.put(r3, r5)     // Catch:{ IOException -> 0x0170 }
            L_0x0159:
                boolean r2 = r1.isUsed()     // Catch:{ IOException -> 0x0170 }
                if (r2 != 0) goto L_0x016b
                r1.setUsed()     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfCopy r2 = r10.cstp     // Catch:{ IOException -> 0x0170 }
                com.itextpdf.text.pdf.PdfIndirectReference r3 = r1.getIndirectReference()     // Catch:{ IOException -> 0x0170 }
                r2.addToBody(r1, r3)     // Catch:{ IOException -> 0x0170 }
            L_0x016b:
                int r11 = r11 + 1
                goto L_0x002b
            L_0x016f:
                return
            L_0x0170:
                r11 = move-exception
                com.itextpdf.text.ExceptionConverter r0 = new com.itextpdf.text.ExceptionConverter
                r0.<init>(r11)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfCopy.PageStamp.addAnnotation(com.itextpdf.text.pdf.PdfAnnotation):void");
        }
    }

    public static class StampContent extends PdfContentByte {
        PageResources pageResources;

        StampContent(PdfWriter pdfWriter, PageResources pageResources2) {
            super(pdfWriter);
            this.pageResources = pageResources2;
        }

        public PdfContentByte getDuplicate() {
            return new StampContent(this.writer, this.pageResources);
        }

        /* access modifiers changed from: package-private */
        public PageResources getPageResources() {
            return this.pageResources;
        }
    }
}
