package com.itextpdf.text.pdf;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ListIterator;

public class PdfLister {
    PrintStream out;

    public PdfLister(PrintStream printStream) {
        this.out = printStream;
    }

    public void listAnyObject(PdfObject pdfObject) {
        int type = pdfObject.type();
        if (type == 3) {
            PrintStream printStream = this.out;
            printStream.println("(" + pdfObject.toString() + ")");
        } else if (type == 5) {
            listArray((PdfArray) pdfObject);
        } else if (type != 6) {
            this.out.println(pdfObject.toString());
        } else {
            listDict((PdfDictionary) pdfObject);
        }
    }

    public void listDict(PdfDictionary pdfDictionary) {
        this.out.println("<<");
        for (PdfName next : pdfDictionary.getKeys()) {
            PdfObject pdfObject = pdfDictionary.get(next);
            this.out.print(next.toString());
            this.out.print(' ');
            listAnyObject(pdfObject);
        }
        this.out.println(">>");
    }

    public void listArray(PdfArray pdfArray) {
        this.out.println('[');
        ListIterator<PdfObject> listIterator = pdfArray.listIterator();
        while (listIterator.hasNext()) {
            listAnyObject(listIterator.next());
        }
        this.out.println(']');
    }

    public void listStream(PRStream pRStream, PdfReaderInstance pdfReaderInstance) {
        try {
            listDict(pRStream);
            this.out.println("startstream");
            byte[] streamBytes = PdfReader.getStreamBytes(pRStream);
            int length = streamBytes.length - 1;
            for (int i = 0; i < length; i++) {
                if (streamBytes[i] == 13 && streamBytes[i + 1] != 10) {
                    streamBytes[i] = 10;
                }
            }
            this.out.println(new String(streamBytes));
            this.out.println("endstream");
        } catch (IOException e) {
            System.err.println("I/O exception: " + e);
        }
    }

    public void listPage(PdfImportedPage pdfImportedPage) {
        int pageNumber = pdfImportedPage.getPageNumber();
        PdfReaderInstance pdfReaderInstance = pdfImportedPage.getPdfReaderInstance();
        PdfDictionary pageN = pdfReaderInstance.getReader().getPageN(pageNumber);
        listDict(pageN);
        PdfObject pdfObject = PdfReader.getPdfObject(pageN.get(PdfName.CONTENTS));
        if (pdfObject != null) {
            int i = pdfObject.type;
            if (i == 5) {
                ListIterator<PdfObject> listIterator = ((PdfArray) pdfObject).listIterator();
                while (listIterator.hasNext()) {
                    listStream((PRStream) PdfReader.getPdfObject(listIterator.next()), pdfReaderInstance);
                    this.out.println("-----------");
                }
            } else if (i == 7) {
                listStream((PRStream) pdfObject, pdfReaderInstance);
            }
        }
    }
}
