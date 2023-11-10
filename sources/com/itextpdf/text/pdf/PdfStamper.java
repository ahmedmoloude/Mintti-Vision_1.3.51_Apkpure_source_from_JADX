package com.itextpdf.text.pdf;

import com.itextpdf.text.DocWriter;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.collection.PdfCollection;
import com.itextpdf.text.pdf.interfaces.PdfEncryptionSettings;
import com.itextpdf.text.pdf.interfaces.PdfViewerPreferences;
import com.itextpdf.text.pdf.security.LtvVerification;
import com.itextpdf.text.xml.xmp.PdfSchema;
import com.itextpdf.text.xml.xmp.XmpWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PdfStamper implements PdfViewerPreferences, PdfEncryptionSettings {
    protected boolean hasSignature;
    private Map<String, String> moreInfo;
    protected PdfSignatureAppearance sigApp;
    protected XmlSignatureAppearance sigXmlApp;
    protected PdfStamperImp stamper;
    private LtvVerification verification;

    public PdfStamper(PdfReader pdfReader, OutputStream outputStream) throws DocumentException, IOException {
        this.stamper = new PdfStamperImp(pdfReader, outputStream, 0, false);
    }

    public PdfStamper(PdfReader pdfReader, OutputStream outputStream, char c) throws DocumentException, IOException {
        this.stamper = new PdfStamperImp(pdfReader, outputStream, c, false);
    }

    public PdfStamper(PdfReader pdfReader, OutputStream outputStream, char c, boolean z) throws DocumentException, IOException {
        this.stamper = new PdfStamperImp(pdfReader, outputStream, c, z);
    }

    public Map<String, String> getMoreInfo() {
        return this.moreInfo;
    }

    public void setMoreInfo(Map<String, String> map) {
        this.moreInfo = map;
    }

    public void replacePage(PdfReader pdfReader, int i, int i2) {
        this.stamper.replacePage(pdfReader, i, i2);
    }

    public void insertPage(int i, Rectangle rectangle) {
        this.stamper.insertPage(i, rectangle);
    }

    public PdfSignatureAppearance getSignatureAppearance() {
        return this.sigApp;
    }

    public XmlSignatureAppearance getXmlSignatureAppearance() {
        return this.sigXmlApp;
    }

    public void flush() {
        try {
            this.stamper.alterContents();
            this.stamper.pagesToContent.clear();
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }

    public void close() throws DocumentException, IOException {
        if (!this.stamper.closed) {
            if (!this.hasSignature) {
                mergeVerification();
                this.stamper.close(this.moreInfo);
                return;
            }
            throw new DocumentException("Signature defined. Must be closed in PdfSignatureAppearance.");
        }
    }

    public PdfContentByte getUnderContent(int i) {
        return this.stamper.getUnderContent(i);
    }

    public PdfContentByte getOverContent(int i) {
        return this.stamper.getOverContent(i);
    }

    public boolean isRotateContents() {
        return this.stamper.isRotateContents();
    }

    public void setRotateContents(boolean z) {
        this.stamper.setRotateContents(z);
    }

    public void setEncryption(byte[] bArr, byte[] bArr2, int i, boolean z) throws DocumentException {
        if (this.stamper.isAppend()) {
            throw new DocumentException(MessageLocalization.getComposedMessage("append.mode.does.not.support.changing.the.encryption.status", new Object[0]));
        } else if (!this.stamper.isContentWritten()) {
            this.stamper.setEncryption(bArr, bArr2, i, z ? 1 : 0);
        } else {
            throw new DocumentException(MessageLocalization.getComposedMessage("content.was.already.written.to.the.output", new Object[0]));
        }
    }

    public void setEncryption(byte[] bArr, byte[] bArr2, int i, int i2) throws DocumentException {
        if (this.stamper.isAppend()) {
            throw new DocumentException(MessageLocalization.getComposedMessage("append.mode.does.not.support.changing.the.encryption.status", new Object[0]));
        } else if (!this.stamper.isContentWritten()) {
            this.stamper.setEncryption(bArr, bArr2, i, i2);
        } else {
            throw new DocumentException(MessageLocalization.getComposedMessage("content.was.already.written.to.the.output", new Object[0]));
        }
    }

    public void setEncryption(boolean z, String str, String str2, int i) throws DocumentException {
        setEncryption(DocWriter.getISOBytes(str), DocWriter.getISOBytes(str2), i, z);
    }

    public void setEncryption(int i, String str, String str2, int i2) throws DocumentException {
        setEncryption(DocWriter.getISOBytes(str), DocWriter.getISOBytes(str2), i2, i);
    }

    public void setEncryption(Certificate[] certificateArr, int[] iArr, int i) throws DocumentException {
        if (this.stamper.isAppend()) {
            throw new DocumentException(MessageLocalization.getComposedMessage("append.mode.does.not.support.changing.the.encryption.status", new Object[0]));
        } else if (!this.stamper.isContentWritten()) {
            this.stamper.setEncryption(certificateArr, iArr, i);
        } else {
            throw new DocumentException(MessageLocalization.getComposedMessage("content.was.already.written.to.the.output", new Object[0]));
        }
    }

    public PdfImportedPage getImportedPage(PdfReader pdfReader, int i) {
        return this.stamper.getImportedPage(pdfReader, i);
    }

    public PdfWriter getWriter() {
        return this.stamper;
    }

    public PdfReader getReader() {
        return this.stamper.reader;
    }

    public AcroFields getAcroFields() {
        return this.stamper.getAcroFields();
    }

    public void setFormFlattening(boolean z) {
        this.stamper.setFormFlattening(z);
    }

    public void setFreeTextFlattening(boolean z) {
        this.stamper.setFreeTextFlattening(z);
    }

    public void setAnnotationFlattening(boolean z) {
        this.stamper.setFlatAnnotations(z);
    }

    public void addAnnotation(PdfAnnotation pdfAnnotation, int i) {
        this.stamper.addAnnotation(pdfAnnotation, i);
    }

    public PdfFormField addSignature(String str, int i, float f, float f2, float f3, float f4) {
        PdfAcroForm acroForm = this.stamper.getAcroForm();
        PdfFormField createSignature = PdfFormField.createSignature(this.stamper);
        PdfAcroForm pdfAcroForm = acroForm;
        PdfFormField pdfFormField = createSignature;
        pdfAcroForm.setSignatureParams(pdfFormField, str, f, f2, f3, f4);
        pdfAcroForm.drawSignatureAppearences(pdfFormField, f, f2, f3, f4);
        addAnnotation(createSignature, i);
        return createSignature;
    }

    public void addComments(FdfReader fdfReader) throws IOException {
        this.stamper.addComments(fdfReader);
    }

    public void setOutlines(List<HashMap<String, Object>> list) {
        this.stamper.setOutlines(list);
    }

    public void setThumbnail(Image image, int i) throws PdfException, DocumentException {
        this.stamper.setThumbnail(image, i);
    }

    public boolean partialFormFlattening(String str) {
        return this.stamper.partialFormFlattening(str);
    }

    public void addJavaScript(String str) {
        this.stamper.addJavaScript(str, !PdfEncodings.isPdfDocEncoding(str));
    }

    public void addJavaScript(String str, String str2) {
        PdfStamperImp pdfStamperImp = this.stamper;
        pdfStamperImp.addJavaScript(str, PdfAction.javaScript(str2, pdfStamperImp, !PdfEncodings.isPdfDocEncoding(str2)));
    }

    public void addFileAttachment(String str, byte[] bArr, String str2, String str3) throws IOException {
        addFileAttachment(str, PdfFileSpecification.fileEmbedded(this.stamper, str2, str3, bArr));
    }

    public void addFileAttachment(String str, PdfFileSpecification pdfFileSpecification) throws IOException {
        this.stamper.addFileAttachment(str, pdfFileSpecification);
    }

    public void makePackage(PdfName pdfName) {
        PdfCollection pdfCollection = new PdfCollection(0);
        pdfCollection.put(PdfName.VIEW, pdfName);
        this.stamper.makePackage(pdfCollection);
    }

    public void makePackage(PdfCollection pdfCollection) {
        this.stamper.makePackage(pdfCollection);
    }

    public void setViewerPreferences(int i) {
        this.stamper.setViewerPreferences(i);
    }

    public void addViewerPreference(PdfName pdfName, PdfObject pdfObject) {
        this.stamper.addViewerPreference(pdfName, pdfObject);
    }

    public void setXmpMetadata(byte[] bArr) {
        this.stamper.setXmpMetadata(bArr);
    }

    public void createXmpMetadata() {
        this.stamper.createXmpMetadata();
    }

    public XmpWriter getXmpWriter() {
        return this.stamper.getXmpWriter();
    }

    public boolean isFullCompression() {
        return this.stamper.isFullCompression();
    }

    public void setFullCompression() throws DocumentException {
        if (!this.stamper.isAppend()) {
            this.stamper.fullCompression = true;
            this.stamper.setAtLeastPdfVersion(PdfWriter.VERSION_1_5);
        }
    }

    public void setPageAction(PdfName pdfName, PdfAction pdfAction, int i) throws PdfException {
        this.stamper.setPageAction(pdfName, pdfAction, i);
    }

    public void setDuration(int i, int i2) {
        this.stamper.setDuration(i, i2);
    }

    public void setTransition(PdfTransition pdfTransition, int i) {
        this.stamper.setTransition(pdfTransition, i);
    }

    public static PdfStamper createSignature(PdfReader pdfReader, OutputStream outputStream, char c, File file, boolean z) throws DocumentException, IOException {
        PdfStamper pdfStamper;
        if (file == null) {
            ByteBuffer byteBuffer = new ByteBuffer();
            pdfStamper = new PdfStamper(pdfReader, byteBuffer, c, z);
            PdfSignatureAppearance pdfSignatureAppearance = new PdfSignatureAppearance(pdfStamper.stamper);
            pdfStamper.sigApp = pdfSignatureAppearance;
            pdfSignatureAppearance.setSigout(byteBuffer);
        } else {
            if (file.isDirectory()) {
                file = File.createTempFile(PdfSchema.DEFAULT_XPATH_ID, ".pdf", file);
            }
            PdfStamper pdfStamper2 = new PdfStamper(pdfReader, new FileOutputStream(file), c, z);
            PdfSignatureAppearance pdfSignatureAppearance2 = new PdfSignatureAppearance(pdfStamper2.stamper);
            pdfStamper2.sigApp = pdfSignatureAppearance2;
            pdfSignatureAppearance2.setTempFile(file);
            pdfStamper = pdfStamper2;
        }
        pdfStamper.sigApp.setOriginalout(outputStream);
        pdfStamper.sigApp.setStamper(pdfStamper);
        pdfStamper.hasSignature = true;
        PdfDictionary catalog = pdfReader.getCatalog();
        PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObject(catalog.get(PdfName.ACROFORM), catalog);
        if (pdfDictionary != null) {
            pdfDictionary.remove(PdfName.NEEDAPPEARANCES);
            pdfStamper.stamper.markUsed((PdfObject) pdfDictionary);
        }
        return pdfStamper;
    }

    public static PdfStamper createSignature(PdfReader pdfReader, OutputStream outputStream, char c) throws DocumentException, IOException {
        return createSignature(pdfReader, outputStream, c, (File) null, false);
    }

    public static PdfStamper createSignature(PdfReader pdfReader, OutputStream outputStream, char c, File file) throws DocumentException, IOException {
        return createSignature(pdfReader, outputStream, c, file, false);
    }

    public static PdfStamper createXmlSignature(PdfReader pdfReader, OutputStream outputStream) throws IOException, DocumentException {
        PdfStamper pdfStamper = new PdfStamper(pdfReader, outputStream);
        XmlSignatureAppearance xmlSignatureAppearance = new XmlSignatureAppearance(pdfStamper.stamper);
        pdfStamper.sigXmlApp = xmlSignatureAppearance;
        xmlSignatureAppearance.setStamper(pdfStamper);
        return pdfStamper;
    }

    public Map<String, PdfLayer> getPdfLayers() {
        return this.stamper.getPdfLayers();
    }

    public void markUsed(PdfObject pdfObject) {
        this.stamper.markUsed(pdfObject);
    }

    public LtvVerification getLtvVerification() {
        if (this.verification == null) {
            this.verification = new LtvVerification(this);
        }
        return this.verification;
    }

    public boolean addNamedDestination(String str, int i, PdfDestination pdfDestination) throws IOException {
        HashMap<Object, PdfObject> namedDestinations = this.stamper.getNamedDestinations();
        if (getReader().getNamedDestination().containsKey(str)) {
            return false;
        }
        PdfDestination pdfDestination2 = new PdfDestination(pdfDestination);
        pdfDestination2.addPage(getReader().getPageOrigRef(i));
        namedDestinations.put(str, new PdfArray((PdfArray) pdfDestination2));
        return true;
    }

    /* access modifiers changed from: package-private */
    public void mergeVerification() throws IOException {
        LtvVerification ltvVerification = this.verification;
        if (ltvVerification != null) {
            ltvVerification.merge();
        }
    }

    protected PdfStamper() {
    }
}
