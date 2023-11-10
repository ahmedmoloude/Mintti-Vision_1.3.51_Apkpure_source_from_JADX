package com.itextpdf.text.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.error_messages.MessageLocalization;
import java.io.IOException;
import java.util.ArrayList;

public class PdfPages {
    private int leafSize = 10;
    private ArrayList<PdfIndirectReference> pages = new ArrayList<>();
    private ArrayList<PdfIndirectReference> parents = new ArrayList<>();
    private PdfIndirectReference topParent;
    private PdfWriter writer;

    PdfPages(PdfWriter pdfWriter) {
        this.writer = pdfWriter;
    }

    /* access modifiers changed from: package-private */
    public void addPage(PdfDictionary pdfDictionary) {
        try {
            if (this.pages.size() % this.leafSize == 0) {
                this.parents.add(this.writer.getPdfIndirectReference());
            }
            ArrayList<PdfIndirectReference> arrayList = this.parents;
            pdfDictionary.put(PdfName.PARENT, arrayList.get(arrayList.size() - 1));
            PdfIndirectReference currentPage = this.writer.getCurrentPage();
            this.writer.addToBody((PdfObject) pdfDictionary, currentPage);
            this.pages.add(currentPage);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    /* access modifiers changed from: package-private */
    public PdfIndirectReference addPageRef(PdfIndirectReference pdfIndirectReference) {
        try {
            if (this.pages.size() % this.leafSize == 0) {
                this.parents.add(this.writer.getPdfIndirectReference());
            }
            this.pages.add(pdfIndirectReference);
            ArrayList<PdfIndirectReference> arrayList = this.parents;
            return arrayList.get(arrayList.size() - 1);
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    /* access modifiers changed from: package-private */
    public PdfIndirectReference writePageTree() throws IOException {
        int i;
        int i2;
        if (!this.pages.isEmpty()) {
            ArrayList<PdfIndirectReference> arrayList = this.parents;
            ArrayList<PdfIndirectReference> arrayList2 = this.pages;
            ArrayList<PdfIndirectReference> arrayList3 = new ArrayList<>();
            int i3 = 1;
            while (true) {
                int i4 = this.leafSize;
                i3 *= i4;
                int size = arrayList2.size();
                int i5 = this.leafSize;
                int i6 = size % i5;
                if (i6 != 0) {
                    i5 = i6;
                }
                for (int i7 = 0; i7 < arrayList.size(); i7++) {
                    if (i7 == arrayList.size() - 1) {
                        i2 = this.pages.size() % i3;
                        if (i2 == 0) {
                            i2 = i3;
                        }
                        i = i5;
                    } else {
                        i2 = i3;
                        i = i4;
                    }
                    PdfDictionary pdfDictionary = new PdfDictionary(PdfName.PAGES);
                    pdfDictionary.put(PdfName.COUNT, new PdfNumber(i2));
                    PdfArray pdfArray = new PdfArray();
                    int i8 = i7 * i4;
                    pdfArray.getArrayList().addAll(arrayList2.subList(i8, i + i8));
                    pdfDictionary.put(PdfName.KIDS, pdfArray);
                    if (arrayList.size() > 1) {
                        if (i7 % this.leafSize == 0) {
                            arrayList3.add(this.writer.getPdfIndirectReference());
                        }
                        pdfDictionary.put(PdfName.PARENT, arrayList3.get(i7 / this.leafSize));
                    }
                    this.writer.addToBody((PdfObject) pdfDictionary, arrayList.get(i7));
                }
                if (arrayList.size() == 1) {
                    PdfIndirectReference pdfIndirectReference = arrayList.get(0);
                    this.topParent = pdfIndirectReference;
                    return pdfIndirectReference;
                }
                arrayList2 = arrayList;
                arrayList = arrayList3;
                arrayList3 = new ArrayList<>();
            }
        } else {
            throw new IOException(MessageLocalization.getComposedMessage("the.document.has.no.pages", new Object[0]));
        }
    }

    /* access modifiers changed from: package-private */
    public PdfIndirectReference getTopParent() {
        return this.topParent;
    }

    /* access modifiers changed from: package-private */
    public void setLinearMode(PdfIndirectReference pdfIndirectReference) {
        if (this.parents.size() <= 1) {
            if (pdfIndirectReference != null) {
                this.topParent = pdfIndirectReference;
                this.parents.clear();
                this.parents.add(pdfIndirectReference);
            }
            this.leafSize = 10000000;
            return;
        }
        throw new RuntimeException(MessageLocalization.getComposedMessage("linear.page.mode.can.only.be.called.with.a.single.parent", new Object[0]));
    }

    /* access modifiers changed from: package-private */
    public void addPage(PdfIndirectReference pdfIndirectReference) {
        this.pages.add(pdfIndirectReference);
    }

    /* access modifiers changed from: package-private */
    public int reorderPages(int[] iArr) throws DocumentException {
        if (iArr == null) {
            return this.pages.size();
        }
        if (this.parents.size() > 1) {
            throw new DocumentException(MessageLocalization.getComposedMessage("page.reordering.requires.a.single.parent.in.the.page.tree.call.pdfwriter.setlinearmode.after.open", new Object[0]));
        } else if (iArr.length == this.pages.size()) {
            int size = this.pages.size();
            boolean[] zArr = new boolean[size];
            int i = 0;
            while (i < size) {
                int i2 = iArr[i];
                if (i2 < 1 || i2 > size) {
                    throw new DocumentException(MessageLocalization.getComposedMessage("page.reordering.requires.pages.between.1.and.1.found.2", String.valueOf(size), String.valueOf(i2)));
                }
                int i3 = i2 - 1;
                if (!zArr[i3]) {
                    zArr[i3] = true;
                    i++;
                } else {
                    throw new DocumentException(MessageLocalization.getComposedMessage("page.reordering.requires.no.page.repetition.page.1.is.repeated", i2));
                }
            }
            ArrayList<PdfIndirectReference> arrayList = this.pages;
            PdfIndirectReference[] pdfIndirectReferenceArr = (PdfIndirectReference[]) arrayList.toArray(new PdfIndirectReference[arrayList.size()]);
            for (int i4 = 0; i4 < size; i4++) {
                this.pages.set(i4, pdfIndirectReferenceArr[iArr[i4] - 1]);
            }
            return size;
        } else {
            throw new DocumentException(MessageLocalization.getComposedMessage("page.reordering.requires.an.array.with.the.same.size.as.the.number.of.pages", new Object[0]));
        }
    }
}
