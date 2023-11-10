package com.itextpdf.text.pdf;

import com.itextpdf.text.error_messages.MessageLocalization;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class PdfStructTreeController {
    private PdfIndirectReference nullReference = null;
    private PdfDictionary parentTree;
    protected PdfReader reader;
    private PdfDictionary roleMap = null;
    private PdfDictionary sourceClassMap = null;
    private PdfDictionary sourceRoleMap = null;
    private PdfDictionary structTreeRoot;
    private PdfStructureTreeRoot structureTreeRoot;
    private PdfCopy writer;

    public enum returnType {
        BELOW,
        FOUND,
        ABOVE,
        NOTFOUND
    }

    protected PdfStructTreeController(PdfReader pdfReader, PdfCopy pdfCopy) throws BadPdfFormatException {
        if (pdfCopy.isTagged()) {
            this.writer = pdfCopy;
            PdfStructureTreeRoot structureTreeRoot2 = pdfCopy.getStructureTreeRoot();
            this.structureTreeRoot = structureTreeRoot2;
            structureTreeRoot2.put(PdfName.PARENTTREE, new PdfDictionary(PdfName.STRUCTELEM));
            setReader(pdfReader);
            return;
        }
        throw new BadPdfFormatException(MessageLocalization.getComposedMessage("no.structtreeroot.found", new Object[0]));
    }

    /* access modifiers changed from: protected */
    public void setReader(PdfReader pdfReader) throws BadPdfFormatException {
        this.reader = pdfReader;
        PdfObject directObject = getDirectObject(pdfReader.getCatalog().get(PdfName.STRUCTTREEROOT));
        if (directObject == null || !directObject.isDictionary()) {
            throw new BadPdfFormatException(MessageLocalization.getComposedMessage("no.structtreeroot.found", new Object[0]));
        }
        PdfDictionary pdfDictionary = (PdfDictionary) directObject;
        this.structTreeRoot = pdfDictionary;
        PdfObject directObject2 = getDirectObject(pdfDictionary.get(PdfName.PARENTTREE));
        if (directObject2 == null || !directObject2.isDictionary()) {
            throw new BadPdfFormatException(MessageLocalization.getComposedMessage("the.document.does.not.contain.parenttree", new Object[0]));
        }
        this.parentTree = (PdfDictionary) directObject2;
        this.sourceRoleMap = null;
        this.sourceClassMap = null;
        this.nullReference = null;
    }

    public static boolean checkTagged(PdfReader pdfReader) {
        PdfObject directObject;
        PdfObject directObject2 = getDirectObject(pdfReader.getCatalog().get(PdfName.STRUCTTREEROOT));
        if (directObject2 == null || !directObject2.isDictionary() || (directObject = getDirectObject(((PdfDictionary) directObject2).get(PdfName.PARENTTREE))) == null || !directObject.isDictionary()) {
            return false;
        }
        return true;
    }

    public static PdfObject getDirectObject(PdfObject pdfObject) {
        if (pdfObject == null) {
            return null;
        }
        while (pdfObject.isIndirect()) {
            pdfObject = PdfReader.getPdfObjectRelease(pdfObject);
        }
        return pdfObject;
    }

    public void copyStructTreeForPage(PdfNumber pdfNumber, int i) throws BadPdfFormatException, IOException {
        if (copyPageMarks(this.parentTree, pdfNumber, i) == returnType.NOTFOUND) {
            throw new BadPdfFormatException(MessageLocalization.getComposedMessage("invalid.structparent", new Object[0]));
        }
    }

    private returnType copyPageMarks(PdfDictionary pdfDictionary, PdfNumber pdfNumber, int i) throws BadPdfFormatException, IOException {
        PdfArray pdfArray = (PdfArray) getDirectObject(pdfDictionary.get(PdfName.NUMS));
        if (pdfArray == null) {
            PdfArray pdfArray2 = (PdfArray) getDirectObject(pdfDictionary.get(PdfName.KIDS));
            if (pdfArray2 == null) {
                return returnType.NOTFOUND;
            }
            int size = pdfArray2.size() / 2;
            int i2 = 0;
            while (true) {
                int i3 = size + i2;
                int i4 = C15231.f686x27ed7df1[copyPageMarks((PdfDictionary) getDirectObject(pdfArray2.getPdfObject(i3)), pdfNumber, i).ordinal()];
                int i5 = 1;
                if (i4 == 1) {
                    return returnType.FOUND;
                }
                if (i4 == 2) {
                    int i6 = size / 2;
                    if (i6 != 0) {
                        i5 = i6;
                    }
                    if (i5 + i3 == pdfArray2.size()) {
                        return returnType.ABOVE;
                    }
                    i2 = i3;
                    size = i5;
                } else if (i4 != 3) {
                    return returnType.NOTFOUND;
                } else {
                    if (i3 == 0) {
                        return returnType.BELOW;
                    }
                    if (size == 0) {
                        return returnType.NOTFOUND;
                    }
                    size /= 2;
                }
            }
        } else if (pdfArray.size() == 0) {
            return returnType.NOTFOUND;
        } else {
            return findAndCopyMarks(pdfArray, pdfNumber.intValue(), i);
        }
    }

    /* renamed from: com.itextpdf.text.pdf.PdfStructTreeController$1 */
    static /* synthetic */ class C15231 {

        /* renamed from: $SwitchMap$com$itextpdf$text$pdf$PdfStructTreeController$returnType */
        static final /* synthetic */ int[] f686x27ed7df1;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                com.itextpdf.text.pdf.PdfStructTreeController$returnType[] r0 = com.itextpdf.text.pdf.PdfStructTreeController.returnType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f686x27ed7df1 = r0
                com.itextpdf.text.pdf.PdfStructTreeController$returnType r1 = com.itextpdf.text.pdf.PdfStructTreeController.returnType.FOUND     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = f686x27ed7df1     // Catch:{ NoSuchFieldError -> 0x001d }
                com.itextpdf.text.pdf.PdfStructTreeController$returnType r1 = com.itextpdf.text.pdf.PdfStructTreeController.returnType.ABOVE     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = f686x27ed7df1     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.itextpdf.text.pdf.PdfStructTreeController$returnType r1 = com.itextpdf.text.pdf.PdfStructTreeController.returnType.BELOW     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.PdfStructTreeController.C15231.<clinit>():void");
        }
    }

    private returnType findAndCopyMarks(PdfArray pdfArray, int i, int i2) throws BadPdfFormatException, IOException {
        if (pdfArray.getAsNumber(0).intValue() > i) {
            return returnType.BELOW;
        }
        if (pdfArray.getAsNumber(pdfArray.size() - 2).intValue() < i) {
            return returnType.ABOVE;
        }
        int size = pdfArray.size() / 4;
        int i3 = 0;
        while (true) {
            int i4 = i3 + size;
            int i5 = i4 * 2;
            int intValue = pdfArray.getAsNumber(i5).intValue();
            if (intValue == i) {
                PdfObject pdfObject = pdfArray.getPdfObject(i5 + 1);
                PdfObject pdfObject2 = pdfObject;
                while (pdfObject2.isIndirect()) {
                    pdfObject2 = PdfReader.getPdfObjectRelease(pdfObject2);
                }
                if (pdfObject2.isArray()) {
                    PdfObject pdfObject3 = null;
                    Iterator<PdfObject> it = ((PdfArray) pdfObject2).iterator();
                    while (it.hasNext()) {
                        PdfObject next = it.next();
                        if (next.isNull()) {
                            if (this.nullReference == null) {
                                this.nullReference = this.writer.addToBody(new PdfNull()).getIndirectReference();
                            }
                            this.structureTreeRoot.setPageMark(i2, this.nullReference);
                        } else {
                            PdfObject copyObject = this.writer.copyObject(next, true, false);
                            if (pdfObject3 == null) {
                                pdfObject3 = copyObject;
                            }
                            this.structureTreeRoot.setPageMark(i2, (PdfIndirectReference) copyObject);
                        }
                    }
                    attachStructTreeRootKids(pdfObject3);
                } else if (!pdfObject2.isDictionary()) {
                    return returnType.NOTFOUND;
                } else {
                    if (getKDict((PdfDictionary) pdfObject2) == null) {
                        return returnType.NOTFOUND;
                    }
                    this.structureTreeRoot.setAnnotationMark(i2, (PdfIndirectReference) this.writer.copyObject(pdfObject, true, false));
                }
                return returnType.FOUND;
            } else if (intValue < i) {
                if (size == 0) {
                    return returnType.NOTFOUND;
                }
                if (size != 1) {
                    size /= 2;
                }
                if (size + i4 == pdfArray.size()) {
                    return returnType.NOTFOUND;
                }
                i3 = i4;
            } else if (i4 == 0) {
                return returnType.BELOW;
            } else {
                if (size == 0) {
                    return returnType.NOTFOUND;
                }
                size /= 2;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void attachStructTreeRootKids(PdfObject pdfObject) throws IOException, BadPdfFormatException {
        PdfObject pdfObject2 = this.structTreeRoot.get(PdfName.f605K);
        if (pdfObject2 == null || (!pdfObject2.isArray() && !pdfObject2.isIndirect())) {
            addKid(this.structureTreeRoot, pdfObject);
        } else if (pdfObject2.isIndirect()) {
            addKid(pdfObject2);
        } else {
            Iterator<PdfObject> it = ((PdfArray) pdfObject2).iterator();
            while (it.hasNext()) {
                addKid(it.next());
            }
        }
    }

    static PdfDictionary getKDict(PdfDictionary pdfDictionary) {
        PdfDictionary asDict = pdfDictionary.getAsDict(PdfName.f605K);
        if (asDict == null) {
            PdfArray asArray = pdfDictionary.getAsArray(PdfName.f605K);
            if (asArray == null) {
                return null;
            }
            for (int i = 0; i < asArray.size(); i++) {
                PdfDictionary asDict2 = asArray.getAsDict(i);
                if (asDict2 != null && PdfName.OBJR.equals(asDict2.getAsName(PdfName.TYPE))) {
                    return asDict2;
                }
            }
        } else if (PdfName.OBJR.equals(asDict.getAsName(PdfName.TYPE))) {
            return asDict;
        }
        return null;
    }

    private void addKid(PdfObject pdfObject) throws IOException, BadPdfFormatException {
        if (pdfObject.isIndirect()) {
            PRIndirectReference pRIndirectReference = (PRIndirectReference) pdfObject;
            RefKey refKey = new RefKey(pRIndirectReference);
            if (!this.writer.indirects.containsKey(refKey)) {
                this.writer.copyIndirect(pRIndirectReference, true, false);
            }
            PdfIndirectReference ref = this.writer.indirects.get(refKey).getRef();
            if (this.writer.updateRootKids) {
                addKid(this.structureTreeRoot, ref);
                this.writer.structureTreeRootKidsForReaderImported(this.reader);
            }
        }
    }

    private static PdfArray getDirectArray(PdfArray pdfArray) {
        PdfArray pdfArray2 = new PdfArray();
        for (int i = 0; i < pdfArray.size(); i++) {
            PdfObject directObject = getDirectObject(pdfArray.getPdfObject(i));
            if (directObject != null) {
                if (directObject.isArray()) {
                    pdfArray2.add((PdfObject) getDirectArray((PdfArray) directObject));
                } else if (directObject.isDictionary()) {
                    pdfArray2.add((PdfObject) getDirectDict((PdfDictionary) directObject));
                } else {
                    pdfArray2.add(directObject);
                }
            }
        }
        return pdfArray2;
    }

    private static PdfDictionary getDirectDict(PdfDictionary pdfDictionary) {
        PdfDictionary pdfDictionary2 = new PdfDictionary();
        for (Map.Entry next : pdfDictionary.hashMap.entrySet()) {
            PdfObject directObject = getDirectObject((PdfObject) next.getValue());
            if (directObject != null) {
                if (directObject.isArray()) {
                    pdfDictionary2.put((PdfName) next.getKey(), getDirectArray((PdfArray) directObject));
                } else if (directObject.isDictionary()) {
                    pdfDictionary2.put((PdfName) next.getKey(), getDirectDict((PdfDictionary) directObject));
                } else {
                    pdfDictionary2.put((PdfName) next.getKey(), directObject);
                }
            }
        }
        return pdfDictionary2;
    }

    public static boolean compareObjects(PdfObject pdfObject, PdfObject pdfObject2) {
        PdfObject directObject = getDirectObject(pdfObject2);
        if (directObject == null || pdfObject.type() != directObject.type()) {
            return false;
        }
        if (pdfObject.isBoolean()) {
            if (pdfObject == directObject) {
                return true;
            }
            if (!(directObject instanceof PdfBoolean) || ((PdfBoolean) pdfObject).booleanValue() != ((PdfBoolean) directObject).booleanValue()) {
                return false;
            }
            return true;
        } else if (pdfObject.isName()) {
            return pdfObject.equals(directObject);
        } else {
            if (pdfObject.isNumber()) {
                if (pdfObject == directObject) {
                    return true;
                }
                if (!(directObject instanceof PdfNumber) || ((PdfNumber) pdfObject).doubleValue() != ((PdfNumber) directObject).doubleValue()) {
                    return false;
                }
                return true;
            } else if (pdfObject.isNull()) {
                if (pdfObject != directObject && !(directObject instanceof PdfNull)) {
                    return false;
                }
                return true;
            } else if (pdfObject.isString()) {
                if (pdfObject == directObject) {
                    return true;
                }
                if (!(directObject instanceof PdfString)) {
                    return false;
                }
                PdfString pdfString = (PdfString) directObject;
                if (!(pdfString.value == null && ((PdfString) pdfObject).value == null)) {
                    PdfString pdfString2 = (PdfString) pdfObject;
                    if (pdfString2.value == null || !pdfString2.value.equals(pdfString.value)) {
                        return false;
                    }
                }
                return true;
            } else if (pdfObject.isArray()) {
                PdfArray pdfArray = (PdfArray) pdfObject;
                PdfArray pdfArray2 = (PdfArray) directObject;
                if (pdfArray.size() != pdfArray2.size()) {
                    return false;
                }
                for (int i = 0; i < pdfArray.size(); i++) {
                    if (!compareObjects(pdfArray.getPdfObject(i), pdfArray2.getPdfObject(i))) {
                        return false;
                    }
                }
                return true;
            } else if (!pdfObject.isDictionary()) {
                return false;
            } else {
                PdfDictionary pdfDictionary = (PdfDictionary) pdfObject;
                PdfDictionary pdfDictionary2 = (PdfDictionary) directObject;
                if (pdfDictionary.size() != pdfDictionary2.size()) {
                    return false;
                }
                for (PdfName next : pdfDictionary.hashMap.keySet()) {
                    if (!compareObjects(pdfDictionary.get(next), pdfDictionary2.get(next))) {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void addClass(PdfObject pdfObject) throws BadPdfFormatException {
        PdfObject directObject = getDirectObject(pdfObject);
        if (directObject.isDictionary()) {
            PdfObject pdfObject2 = ((PdfDictionary) directObject).get(PdfName.f557C);
            if (pdfObject2 != null) {
                if (pdfObject2.isArray()) {
                    PdfArray pdfArray = (PdfArray) pdfObject2;
                    for (int i = 0; i < pdfArray.size(); i++) {
                        addClass(pdfArray.getPdfObject(i));
                    }
                } else if (pdfObject2.isName()) {
                    addClass(pdfObject2);
                }
            }
        } else if (directObject.isName()) {
            PdfName pdfName = (PdfName) directObject;
            if (this.sourceClassMap == null) {
                PdfObject directObject2 = getDirectObject(this.structTreeRoot.get(PdfName.CLASSMAP));
                if (directObject2 != null && directObject2.isDictionary()) {
                    this.sourceClassMap = (PdfDictionary) directObject2;
                } else {
                    return;
                }
            }
            PdfObject directObject3 = getDirectObject(this.sourceClassMap.get(pdfName));
            if (directObject3 != null) {
                PdfObject mappedClass = this.structureTreeRoot.getMappedClass(pdfName);
                if (mappedClass != null) {
                    if (!compareObjects(mappedClass, directObject3)) {
                        throw new BadPdfFormatException(MessageLocalization.getComposedMessage("conflict.in.classmap", pdfName));
                    }
                } else if (directObject3.isDictionary()) {
                    this.structureTreeRoot.mapClass(pdfName, getDirectDict((PdfDictionary) directObject3));
                } else if (directObject3.isArray()) {
                    this.structureTreeRoot.mapClass(pdfName, getDirectArray((PdfArray) directObject3));
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void addRole(PdfName pdfName) throws BadPdfFormatException {
        if (pdfName != null) {
            for (PdfName equals : this.writer.getStandardStructElems()) {
                if (equals.equals(pdfName)) {
                    return;
                }
            }
            if (this.sourceRoleMap == null) {
                PdfObject directObject = getDirectObject(this.structTreeRoot.get(PdfName.ROLEMAP));
                if (directObject != null && directObject.isDictionary()) {
                    this.sourceRoleMap = (PdfDictionary) directObject;
                } else {
                    return;
                }
            }
            PdfObject pdfObject = this.sourceRoleMap.get(pdfName);
            if (pdfObject != null && pdfObject.isName()) {
                PdfDictionary pdfDictionary = this.roleMap;
                if (pdfDictionary == null) {
                    this.roleMap = new PdfDictionary();
                    this.structureTreeRoot.put(PdfName.ROLEMAP, this.roleMap);
                    this.roleMap.put(pdfName, pdfObject);
                    return;
                }
                PdfObject pdfObject2 = pdfDictionary.get(pdfName);
                if (pdfObject2 == null) {
                    this.roleMap.put(pdfName, pdfObject);
                } else if (!pdfObject2.equals(pdfObject)) {
                    throw new BadPdfFormatException(MessageLocalization.getComposedMessage("conflict.in.rolemap", pdfObject));
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void addKid(PdfDictionary pdfDictionary, PdfObject pdfObject) {
        PdfArray pdfArray;
        PdfObject pdfObject2 = pdfDictionary.get(PdfName.f605K);
        if (pdfObject2 instanceof PdfArray) {
            pdfArray = (PdfArray) pdfObject2;
        } else {
            PdfArray pdfArray2 = new PdfArray();
            if (pdfObject2 != null) {
                pdfArray2.add(pdfObject2);
            }
            pdfArray = pdfArray2;
        }
        pdfArray.add(pdfObject);
        pdfDictionary.put(PdfName.f605K, pdfArray);
    }
}
