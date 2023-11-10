package com.itextpdf.testutils;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import java.io.File;
import java.io.IOException;

public abstract class ITextTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ITextTest.class.getName());

    /* access modifiers changed from: protected */
    public void assertPdf(String str) throws Exception {
    }

    /* access modifiers changed from: protected */
    public void comparePdf(String str, String str2) throws Exception {
    }

    /* access modifiers changed from: protected */
    public String getCmpPdf() {
        return "";
    }

    /* access modifiers changed from: protected */
    public abstract String getOutPdf();

    /* access modifiers changed from: protected */
    public abstract void makePdf(String str) throws Exception;

    public void runTest() throws Exception {
        Logger logger = LOGGER;
        logger.info("Starting test.");
        String outPdf = getOutPdf();
        if (outPdf == null || outPdf.length() == 0) {
            throw new IOException("outPdf cannot be empty!");
        }
        makePdf(outPdf);
        assertPdf(outPdf);
        comparePdf(outPdf, getCmpPdf());
        logger.info("Test complete.");
    }

    /* access modifiers changed from: protected */
    public void deleteDirectory(File file) {
        if (file != null && file.exists()) {
            for (File file2 : file.listFiles()) {
                if (file2.isDirectory()) {
                    deleteDirectory(file2);
                    file2.delete();
                } else {
                    file2.delete();
                }
            }
            file.delete();
        }
    }

    /* access modifiers changed from: protected */
    public void deleteFiles(File file) {
        if (file != null && file.exists()) {
            for (File delete : file.listFiles()) {
                delete.delete();
            }
        }
    }
}
