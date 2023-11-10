package com.itextpdf.testutils;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Meta;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.HtmlTags;
import com.itextpdf.text.p018io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.PRIndirectReference;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PRTokeniser;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfBoolean;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfContentParser;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfIndirectReference;
import com.itextpdf.text.pdf.PdfLiteral;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.RefKey;
import com.itextpdf.text.pdf.parser.ContentByteUtils;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.InlineImageInfo;
import com.itextpdf.text.pdf.parser.InlineImageUtils;
import com.itextpdf.text.pdf.parser.PdfContentStreamProcessor;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TaggedPdfReaderTool;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.xml.XMLUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class CompareTool {
    private static final String cannotOpenTargetDirectory = "Cannot open target directory for <filename>.";
    private static final String differentPages = "File <filename> differs on page <pagenumber>.";
    private static final String gsFailed = "GhostScript failed for <filename>.";
    private static final String ignoredAreasPrefix = "ignored_areas_";
    private static final String undefinedGsPath = "Path to GhostScript is not specified. Please use -DgsExec=<path_to_ghostscript> (e.g. -DgsExec=\"C:/Program Files/gs/gs9.14/bin/gswin32c.exe\")";
    private static final String unexpectedNumberOfPages = "Unexpected number of pages for <filename>.";
    private boolean absoluteError = true;
    private String cmpImage;
    List<PdfDictionary> cmpPages;
    List<RefKey> cmpPagesRef;
    private String cmpPdf;
    /* access modifiers changed from: private */
    public String cmpPdfName;
    private int compareByContentErrorsLimit = 1;
    private String compareExec = System.getProperty("compareExec");
    private final String compareParams = " \"<image1>\" \"<image2>\" \"<difference>\"";
    private double floatComparisonError = 0.0d;
    private boolean generateCompareByContentXmlReport = false;
    private String gsExec = System.getProperty("gsExec");
    private final String gsParams = " -dNOPAUSE -dBATCH -sDEVICE=png16m -r150 -sOutputFile=<outputfile> <inputfile>";
    private String outImage;
    List<PdfDictionary> outPages;
    List<RefKey> outPagesRef;
    private String outPdf;
    /* access modifiers changed from: private */
    public String outPdfName;
    private String xmlReportName = "report";

    private class ObjectPath {
        protected RefKey baseCmpObject;
        protected RefKey baseOutObject;
        protected Stack<Pair<RefKey>> indirects = new Stack<>();
        protected Stack<PathItem> path = new Stack<>();

        public ObjectPath() {
        }

        protected ObjectPath(RefKey refKey, RefKey refKey2) {
            this.baseCmpObject = refKey;
            this.baseOutObject = refKey2;
        }

        private ObjectPath(RefKey refKey, RefKey refKey2, Stack<PathItem> stack) {
            this.baseCmpObject = refKey;
            this.baseOutObject = refKey2;
            this.path = stack;
        }

        private class Pair<T> {
            private T first;
            private T second;

            public Pair(T t, T t2) {
                this.first = t;
                this.second = t2;
            }

            public int hashCode() {
                return (this.first.hashCode() * 31) + this.second.hashCode();
            }

            public boolean equals(Object obj) {
                if (obj instanceof Pair) {
                    Pair pair = (Pair) obj;
                    return this.first.equals(pair.first) && this.second.equals(pair.second);
                }
            }
        }

        private abstract class PathItem {
            /* access modifiers changed from: protected */
            public abstract Node toXmlNode(Document document);

            private PathItem() {
            }
        }

        private class DictPathItem extends PathItem {
            String key;

            public DictPathItem(String str) {
                super();
                this.key = str;
            }

            public String toString() {
                return "Dict key: " + this.key;
            }

            public int hashCode() {
                return this.key.hashCode();
            }

            public boolean equals(Object obj) {
                return (obj instanceof DictPathItem) && this.key.equals(((DictPathItem) obj).key);
            }

            /* access modifiers changed from: protected */
            public Node toXmlNode(Document document) {
                Element createElement = document.createElement("dictKey");
                createElement.appendChild(document.createTextNode(this.key));
                return createElement;
            }
        }

        private class ArrayPathItem extends PathItem {
            int index;

            public ArrayPathItem(int i) {
                super();
                this.index = i;
            }

            public String toString() {
                return "Array index: " + String.valueOf(this.index);
            }

            public int hashCode() {
                return this.index;
            }

            public boolean equals(Object obj) {
                return (obj instanceof ArrayPathItem) && this.index == ((ArrayPathItem) obj).index;
            }

            /* access modifiers changed from: protected */
            public Node toXmlNode(Document document) {
                Element createElement = document.createElement("arrayIndex");
                createElement.appendChild(document.createTextNode(String.valueOf(this.index)));
                return createElement;
            }
        }

        private class OffsetPathItem extends PathItem {
            int offset;

            public OffsetPathItem(int i) {
                super();
                this.offset = i;
            }

            public String toString() {
                return "Offset: " + String.valueOf(this.offset);
            }

            public int hashCode() {
                return this.offset;
            }

            public boolean equals(Object obj) {
                return (obj instanceof OffsetPathItem) && this.offset == ((OffsetPathItem) obj).offset;
            }

            /* access modifiers changed from: protected */
            public Node toXmlNode(Document document) {
                Element createElement = document.createElement(TypedValues.CycleType.S_WAVE_OFFSET);
                createElement.appendChild(document.createTextNode(String.valueOf(this.offset)));
                return createElement;
            }
        }

        public ObjectPath resetDirectPath(RefKey refKey, RefKey refKey2) {
            ObjectPath objectPath = new ObjectPath(refKey, refKey2);
            Stack<Pair<RefKey>> stack = (Stack) this.indirects.clone();
            objectPath.indirects = stack;
            stack.add(new Pair(refKey, refKey2));
            return objectPath;
        }

        public boolean isComparing(RefKey refKey, RefKey refKey2) {
            return this.indirects.contains(new Pair(refKey, refKey2));
        }

        public void pushArrayItemToPath(int i) {
            this.path.add(new ArrayPathItem(i));
        }

        public void pushDictItemToPath(String str) {
            this.path.add(new DictPathItem(str));
        }

        public void pushOffsetToPath(int i) {
            this.path.add(new OffsetPathItem(i));
        }

        public void pop() {
            this.path.pop();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Base cmp object: %s obj. Base out object: %s obj", new Object[]{this.baseCmpObject, this.baseOutObject}));
            Iterator it = this.path.iterator();
            while (it.hasNext()) {
                sb.append("\n");
                sb.append(((PathItem) it.next()).toString());
            }
            return sb.toString();
        }

        public int hashCode() {
            RefKey refKey = this.baseCmpObject;
            int i = 1;
            int hashCode = refKey != null ? refKey.hashCode() : 1;
            RefKey refKey2 = this.baseOutObject;
            if (refKey2 != null) {
                i = refKey2.hashCode();
            }
            int i2 = (hashCode * 31) + i;
            Iterator it = this.path.iterator();
            while (it.hasNext()) {
                i2 = (i2 * 31) + ((PathItem) it.next()).hashCode();
            }
            return i2;
        }

        public boolean equals(Object obj) {
            if (obj instanceof ObjectPath) {
                ObjectPath objectPath = (ObjectPath) obj;
                return this.baseCmpObject.equals(objectPath.baseCmpObject) && this.baseOutObject.equals(objectPath.baseOutObject) && this.path.equals(objectPath.path);
            }
        }

        /* access modifiers changed from: protected */
        public Object clone() {
            return new ObjectPath(this.baseCmpObject, this.baseOutObject, (Stack) this.path.clone());
        }

        public Node toXmlNode(Document document) {
            Element createElement = document.createElement("path");
            Element createElement2 = document.createElement("base");
            createElement2.setAttribute("cmp", this.baseCmpObject.toString() + " obj");
            createElement2.setAttribute("out", this.baseOutObject.toString() + " obj");
            createElement.appendChild(createElement2);
            Iterator it = this.path.iterator();
            while (it.hasNext()) {
                createElement.appendChild(((PathItem) it.next()).toXmlNode(document));
            }
            return createElement;
        }
    }

    protected class CompareResult {
        protected Map<ObjectPath, String> differences = new LinkedHashMap();
        protected int messageLimit = 1;

        public CompareResult(int i) {
            this.messageLimit = i;
        }

        public boolean isOk() {
            return this.differences.size() == 0;
        }

        public int getErrorCount() {
            return this.differences.size();
        }

        /* access modifiers changed from: protected */
        public boolean isMessageLimitReached() {
            return this.differences.size() >= this.messageLimit;
        }

        public String getReport() {
            StringBuilder sb = new StringBuilder();
            boolean z = true;
            for (Map.Entry next : this.differences.entrySet()) {
                if (!z) {
                    sb.append("-----------------------------");
                    sb.append("\n");
                }
                sb.append((String) next.getValue());
                sb.append("\n");
                sb.append(((ObjectPath) next.getKey()).toString());
                sb.append("\n");
                z = false;
            }
            return sb.toString();
        }

        /* access modifiers changed from: protected */
        public void addError(ObjectPath objectPath, String str) {
            if (this.differences.size() < this.messageLimit) {
                this.differences.put((ObjectPath) objectPath.clone(), str);
            }
        }

        public void writeReportToXml(OutputStream outputStream) throws ParserConfigurationException, TransformerException {
            Document newDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element createElement = newDocument.createElement("report");
            Element createElement2 = newDocument.createElement("errors");
            createElement2.setAttribute("count", String.valueOf(this.differences.size()));
            createElement.appendChild(createElement2);
            for (Map.Entry next : this.differences.entrySet()) {
                Element createElement3 = newDocument.createElement("error");
                Element createElement4 = newDocument.createElement("message");
                createElement4.appendChild(newDocument.createTextNode((String) next.getValue()));
                Node xmlNode = ((ObjectPath) next.getKey()).toXmlNode(newDocument);
                createElement3.appendChild(createElement4);
                createElement3.appendChild(xmlNode);
                createElement2.appendChild(createElement3);
            }
            newDocument.appendChild(createElement);
            Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
            newTransformer.setOutputProperty(HtmlTags.INDENT, "yes");
            newTransformer.transform(new DOMSource(newDocument), new StreamResult(outputStream));
        }
    }

    private String compare(String str, String str2, Map<Integer, List<Rectangle>> map) throws IOException, InterruptedException, DocumentException {
        return compare(str, str2, map, (List<Integer>) null);
    }

    private String compare(String str, String str2, Map<Integer, List<Rectangle>> map, List<Integer> list) throws IOException, InterruptedException, DocumentException {
        File[] fileArr;
        int i;
        String str3;
        String str4 = str;
        String str5 = str2;
        List<Integer> list2 = list;
        if (this.gsExec == null) {
            return undefinedGsPath;
        }
        if (!new File(this.gsExec).exists()) {
            return new File(this.gsExec).getAbsolutePath() + " does not exist";
        }
        if (!str4.endsWith("/")) {
            str4 = str4 + "/";
        }
        File file = new File(str4);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            for (File delete : file.listFiles(new PngFileFilter())) {
                delete.delete();
            }
            for (File delete2 : file.listFiles(new CmpPngFileFilter())) {
                delete2.delete();
            }
        }
        File file2 = new File(str4 + str5);
        if (file2.exists()) {
            file2.delete();
        }
        if (map != null && !map.isEmpty()) {
            PdfReader pdfReader = new PdfReader(this.cmpPdf);
            PdfReader pdfReader2 = new PdfReader(this.outPdf);
            PdfStamper pdfStamper = new PdfStamper(pdfReader2, new FileOutputStream(str4 + ignoredAreasPrefix + this.outPdfName));
            PdfStamper pdfStamper2 = new PdfStamper(pdfReader, new FileOutputStream(str4 + ignoredAreasPrefix + this.cmpPdfName));
            for (Map.Entry next : map.entrySet()) {
                int intValue = ((Integer) next.getKey()).intValue();
                List<Rectangle> list3 = (List) next.getValue();
                if (list3 != null && !list3.isEmpty()) {
                    PdfContentByte overContent = pdfStamper.getOverContent(intValue);
                    PdfContentByte overContent2 = pdfStamper2.getOverContent(intValue);
                    for (Rectangle rectangle : list3) {
                        rectangle.setBackgroundColor(BaseColor.BLACK);
                        overContent.rectangle(rectangle);
                        overContent2.rectangle(rectangle);
                    }
                }
            }
            pdfStamper.close();
            pdfStamper2.close();
            pdfReader2.close();
            pdfReader.close();
            init(str4 + ignoredAreasPrefix + this.outPdfName, str4 + ignoredAreasPrefix + this.cmpPdfName);
        }
        if (!file.exists()) {
            return cannotOpenTargetDirectory.replace("<filename>", this.outPdf);
        }
        Process runProcess = runProcess(this.gsExec, " -dNOPAUSE -dBATCH -sDEVICE=png16m -r150 -sOutputFile=<outputfile> <inputfile>".replace("<outputfile>", str4 + this.cmpImage).replace("<inputfile>", this.cmpPdf));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                break;
            }
            System.out.println(readLine);
        }
        bufferedReader.close();
        while (true) {
            String readLine2 = bufferedReader2.readLine();
            if (readLine2 == null) {
                break;
            }
            System.out.println(readLine2);
        }
        bufferedReader2.close();
        if (runProcess.waitFor() != 0) {
            return gsFailed.replace("<filename>", this.cmpPdf);
        }
        Process runProcess2 = runProcess(this.gsExec, " -dNOPAUSE -dBATCH -sDEVICE=png16m -r150 -sOutputFile=<outputfile> <inputfile>".replace("<outputfile>", str4 + this.outImage).replace("<inputfile>", this.outPdf));
        BufferedReader bufferedReader3 = new BufferedReader(new InputStreamReader(runProcess2.getInputStream()));
        BufferedReader bufferedReader4 = new BufferedReader(new InputStreamReader(runProcess2.getErrorStream()));
        while (true) {
            String readLine3 = bufferedReader3.readLine();
            if (readLine3 == null) {
                break;
            }
            System.out.println(readLine3);
        }
        bufferedReader3.close();
        while (true) {
            String readLine4 = bufferedReader4.readLine();
            if (readLine4 == null) {
                break;
            }
            System.out.println(readLine4);
        }
        bufferedReader4.close();
        if (runProcess2.waitFor() != 0) {
            return gsFailed.replace("<filename>", this.outPdf);
        }
        File[] listFiles = file.listFiles(new PngFileFilter());
        File[] listFiles2 = file.listFiles(new CmpPngFileFilter());
        boolean z = listFiles.length != listFiles2.length;
        int min = Math.min(listFiles.length, listFiles2.length);
        if (min < 1) {
            return "No files for comparing!!!\nThe result or sample pdf file is not processed by GhostScript.";
        }
        Arrays.sort(listFiles, new ImageNameComparator());
        Arrays.sort(listFiles2, new ImageNameComparator());
        int i2 = 0;
        String str6 = null;
        while (true) {
            if (i2 >= min) {
                break;
            }
            if (list2 == null || !list2.contains(Integer.valueOf(i2))) {
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("Comparing page ");
                i = i2 + 1;
                sb.append(Integer.toString(i));
                sb.append(" (");
                sb.append(listFiles[i2].getAbsolutePath());
                sb.append(")...");
                printStream.print(sb.toString());
                FileInputStream fileInputStream = new FileInputStream(listFiles[i2]);
                FileInputStream fileInputStream2 = new FileInputStream(listFiles2[i2]);
                boolean compareStreams = compareStreams((InputStream) fileInputStream, (InputStream) fileInputStream2);
                fileInputStream.close();
                fileInputStream2.close();
                if (compareStreams) {
                    fileArr = listFiles2;
                    System.out.println("done.");
                } else if (this.compareExec == null || !new File(this.compareExec).exists()) {
                    str6 = differentPages.replace("<filename>", this.outPdf).replace("<pagenumber>", Integer.toString(i)) + "\nYou can optionally specify path to ImageMagick compare tool (e.g. -DcompareExec=\"C:/Program Files/ImageMagick-6.5.4-2/compare.exe\") to visualize differences.";
                } else {
                    Process runProcess3 = runProcess(this.compareExec, " \"<image1>\" \"<image2>\" \"<difference>\"".replace("<image1>", listFiles[i2].getAbsolutePath()).replace("<image2>", listFiles2[i2].getAbsolutePath()).replace("<difference>", str4 + str5 + Integer.toString(i) + ".png"));
                    fileArr = listFiles2;
                    BufferedReader bufferedReader5 = new BufferedReader(new InputStreamReader(runProcess3.getErrorStream()));
                    while (true) {
                        String readLine5 = bufferedReader5.readLine();
                        if (readLine5 == null) {
                            break;
                        }
                        System.out.println(readLine5);
                    }
                    bufferedReader5.close();
                    if (runProcess3.waitFor() != 0) {
                        str3 = differentPages.replace("<filename>", this.outPdf).replace("<pagenumber>", Integer.toString(i));
                    } else if (str6 == null) {
                        str3 = differentPages.replace("<filename>", this.outPdf).replace("<pagenumber>", Integer.toString(i)) + "\nPlease, examine " + str4 + str5 + Integer.toString(i) + ".png for more details.";
                    } else {
                        str3 = "File " + this.outPdf + " differs.\nPlease, examine difference images for more details.";
                    }
                    str6 = str3;
                    System.out.println(str6);
                }
            } else {
                fileArr = listFiles2;
            }
            i2++;
            listFiles2 = fileArr;
            list2 = list;
        }
        str6 = differentPages.replace("<filename>", this.outPdf).replace("<pagenumber>", Integer.toString(i)) + "\nYou can optionally specify path to ImageMagick compare tool (e.g. -DcompareExec=\"C:/Program Files/ImageMagick-6.5.4-2/compare.exe\") to visualize differences.";
        if (str6 != null) {
            return str6;
        }
        if (z) {
            return unexpectedNumberOfPages.replace("<filename>", this.outPdf);
        }
        return null;
    }

    private Process runProcess(String str, String str2) throws IOException, InterruptedException {
        StringTokenizer stringTokenizer = new StringTokenizer(str2);
        String[] strArr = new String[(stringTokenizer.countTokens() + 1)];
        strArr[0] = str;
        int i = 1;
        while (stringTokenizer.hasMoreTokens()) {
            strArr[i] = stringTokenizer.nextToken();
            i++;
        }
        return Runtime.getRuntime().exec(strArr);
    }

    public String compare(String str, String str2, String str3, String str4, Map<Integer, List<Rectangle>> map) throws IOException, InterruptedException, DocumentException {
        init(str, str2);
        return compare(str3, str4, map);
    }

    public String compare(String str, String str2, String str3, String str4) throws IOException, InterruptedException, DocumentException {
        return compare(str, str2, str3, str4, (Map<Integer, List<Rectangle>>) null);
    }

    public CompareTool setCompareByContentErrorsLimit(int i) {
        this.compareByContentErrorsLimit = i;
        return this;
    }

    public void setGenerateCompareByContentXmlReport(boolean z) {
        this.generateCompareByContentXmlReport = z;
    }

    public CompareTool setFloatAbsoluteError(float f) {
        this.floatComparisonError = (double) f;
        this.absoluteError = true;
        return this;
    }

    public CompareTool setFloatRelativeError(float f) {
        this.floatComparisonError = (double) f;
        this.absoluteError = false;
        return this;
    }

    public String getXmlReportName() {
        return this.xmlReportName;
    }

    public void setXmlReportName(String str) {
        this.xmlReportName = str;
    }

    /* access modifiers changed from: protected */
    public String compareByContent(String str, String str2, Map<Integer, List<Rectangle>> map) throws DocumentException, InterruptedException, IOException {
        RefKey refKey;
        RefKey refKey2;
        System.out.print("[itext] INFO  Comparing by content..........");
        PdfReader pdfReader = new PdfReader(this.outPdf);
        this.outPages = new ArrayList();
        ArrayList arrayList = new ArrayList();
        this.outPagesRef = arrayList;
        loadPagesFromReader(pdfReader, this.outPages, arrayList);
        PdfReader pdfReader2 = new PdfReader(this.cmpPdf);
        this.cmpPages = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        this.cmpPagesRef = arrayList2;
        loadPagesFromReader(pdfReader2, this.cmpPages, arrayList2);
        if (this.outPages.size() != this.cmpPages.size()) {
            return compare(str, str2, map);
        }
        CompareResult compareResult = new CompareResult(this.compareByContentErrorsLimit);
        ArrayList arrayList3 = new ArrayList(this.cmpPages.size());
        for (int i = 0; i < this.cmpPages.size(); i++) {
            if (compareDictionariesExtended(this.outPages.get(i), this.cmpPages.get(i), new ObjectPath(this.cmpPagesRef.get(i), this.outPagesRef.get(i)), compareResult)) {
                arrayList3.add(Integer.valueOf(i));
            }
        }
        PdfObject pdfObject = pdfReader.getCatalog().get(PdfName.STRUCTTREEROOT);
        PdfObject pdfObject2 = pdfReader2.getCatalog().get(PdfName.STRUCTTREEROOT);
        if (pdfObject == null) {
            refKey = null;
        } else {
            refKey = new RefKey((PdfIndirectReference) pdfObject);
        }
        if (pdfObject2 == null) {
            refKey2 = null;
        } else {
            refKey2 = new RefKey((PdfIndirectReference) pdfObject2);
        }
        compareObjects(pdfObject, pdfObject2, new ObjectPath(refKey, refKey2), compareResult);
        PdfObject pdfObject3 = pdfReader.getCatalog().get(PdfName.OCPROPERTIES);
        PdfObject pdfObject4 = pdfReader2.getCatalog().get(PdfName.OCPROPERTIES);
        compareObjects(pdfObject3, pdfObject4, new ObjectPath(pdfObject3 instanceof PdfIndirectReference ? new RefKey((PdfIndirectReference) pdfObject3) : null, pdfObject4 instanceof PdfIndirectReference ? new RefKey((PdfIndirectReference) pdfObject4) : null), compareResult);
        pdfReader.close();
        pdfReader2.close();
        if (this.generateCompareByContentXmlReport) {
            try {
                compareResult.writeReportToXml(new FileOutputStream(str + "/" + this.xmlReportName + ".xml"));
            } catch (Exception unused) {
            }
        }
        if (arrayList3.size() != this.cmpPages.size() || !compareResult.isOk()) {
            System.out.println("Fail");
            System.out.flush();
            System.out.println("Compare by content report:\n" + compareResult.getReport());
            System.out.flush();
            String compare = compare(str, str2, map, (List<Integer>) arrayList3);
            return (compare == null || compare.length() == 0) ? "Compare by content fails. No visual differences" : compare;
        }
        System.out.println("OK");
        System.out.flush();
        return null;
    }

    public String compareByContent(String str, String str2, String str3, String str4, Map<Integer, List<Rectangle>> map) throws DocumentException, InterruptedException, IOException {
        init(str, str2);
        return compareByContent(str3, str4, map);
    }

    public String compareByContent(String str, String str2, String str3, String str4) throws DocumentException, InterruptedException, IOException {
        return compareByContent(str, str2, str3, str4, (Map<Integer, List<Rectangle>>) null);
    }

    private void loadPagesFromReader(PdfReader pdfReader, List<PdfDictionary> list, List<RefKey> list2) {
        addPagesFromDict(pdfReader.getCatalog().get(PdfName.PAGES), list, list2);
    }

    private void addPagesFromDict(PdfObject pdfObject, List<PdfDictionary> list, List<RefKey> list2) {
        PdfDictionary pdfDictionary = (PdfDictionary) PdfReader.getPdfObject(pdfObject);
        if (pdfDictionary.isPages()) {
            PdfArray asArray = pdfDictionary.getAsArray(PdfName.KIDS);
            if (asArray != null) {
                Iterator<PdfObject> it = asArray.iterator();
                while (it.hasNext()) {
                    addPagesFromDict(it.next(), list, list2);
                }
            }
        } else if (pdfDictionary.isPage()) {
            list.add(pdfDictionary);
            list2.add(new RefKey((PdfIndirectReference) (PRIndirectReference) pdfObject));
        }
    }

    private boolean compareObjects(PdfObject pdfObject, PdfObject pdfObject2, ObjectPath objectPath, CompareResult compareResult) throws IOException {
        PdfObject pdfObject3 = PdfReader.getPdfObject(pdfObject);
        PdfObject pdfObject4 = PdfReader.getPdfObject(pdfObject2);
        if (pdfObject4 == null && pdfObject3 == null) {
            return true;
        }
        if (pdfObject3 == null) {
            compareResult.addError(objectPath, "Expected object was not found.");
            return false;
        } else if (pdfObject4 == null) {
            compareResult.addError(objectPath, "Found object which was not expected to be found.");
            return false;
        } else if (pdfObject4.type() != pdfObject3.type()) {
            compareResult.addError(objectPath, String.format("Types do not match. Expected: %s. Found: %s.", new Object[]{pdfObject4.getClass().getSimpleName(), pdfObject3.getClass().getSimpleName()}));
            return false;
        } else {
            if (pdfObject2.isIndirect() && pdfObject.isIndirect()) {
                PdfIndirectReference pdfIndirectReference = (PdfIndirectReference) pdfObject2;
                PdfIndirectReference pdfIndirectReference2 = (PdfIndirectReference) pdfObject;
                if (objectPath.isComparing(new RefKey(pdfIndirectReference), new RefKey(pdfIndirectReference2))) {
                    return true;
                }
                objectPath = objectPath.resetDirectPath(new RefKey(pdfIndirectReference), new RefKey(pdfIndirectReference2));
            }
            if (!pdfObject4.isDictionary() || !((PdfDictionary) pdfObject4).isPage()) {
                if (pdfObject4.isDictionary()) {
                    if (!compareDictionariesExtended((PdfDictionary) pdfObject3, (PdfDictionary) pdfObject4, objectPath, compareResult)) {
                        return false;
                    }
                } else if (pdfObject4.isStream()) {
                    if (!compareStreamsExtended((PRStream) pdfObject3, (PRStream) pdfObject4, objectPath, compareResult)) {
                        return false;
                    }
                } else if (pdfObject4.isArray()) {
                    if (!compareArraysExtended((PdfArray) pdfObject3, (PdfArray) pdfObject4, objectPath, compareResult)) {
                        return false;
                    }
                } else if (pdfObject4.isName()) {
                    if (!compareNamesExtended((PdfName) pdfObject3, (PdfName) pdfObject4, objectPath, compareResult)) {
                        return false;
                    }
                } else if (pdfObject4.isNumber()) {
                    if (!compareNumbersExtended((PdfNumber) pdfObject3, (PdfNumber) pdfObject4, objectPath, compareResult)) {
                        return false;
                    }
                } else if (pdfObject4.isString()) {
                    if (!compareStringsExtended((PdfString) pdfObject3, (PdfString) pdfObject4, objectPath, compareResult)) {
                        return false;
                    }
                } else if (pdfObject4.isBoolean()) {
                    if (!compareBooleansExtended((PdfBoolean) pdfObject3, (PdfBoolean) pdfObject4, objectPath, compareResult)) {
                        return false;
                    }
                } else if (pdfObject4 instanceof PdfLiteral) {
                    if (!compareLiteralsExtended((PdfLiteral) pdfObject3, (PdfLiteral) pdfObject4, objectPath, compareResult)) {
                        return false;
                    }
                } else if (!pdfObject3.isNull() || !pdfObject4.isNull()) {
                    throw new UnsupportedOperationException();
                }
                return true;
            } else if (!pdfObject3.isDictionary() || !((PdfDictionary) pdfObject3).isPage()) {
                if (!(compareResult == null || objectPath == null)) {
                    compareResult.addError(objectPath, "Expected a page. Found not a page.");
                }
                return false;
            } else {
                RefKey refKey = new RefKey((PdfIndirectReference) (PRIndirectReference) pdfObject2);
                RefKey refKey2 = new RefKey((PdfIndirectReference) (PRIndirectReference) pdfObject);
                if (this.cmpPagesRef.contains(refKey) && this.cmpPagesRef.indexOf(refKey) == this.outPagesRef.indexOf(refKey2)) {
                    return true;
                }
                if (!(compareResult == null || objectPath == null)) {
                    compareResult.addError(objectPath, String.format("The dictionaries refer to different pages. Expected page number: %s. Found: %s", new Object[]{Integer.valueOf(this.cmpPagesRef.indexOf(refKey)), Integer.valueOf(this.outPagesRef.indexOf(refKey2))}));
                }
                return false;
            }
        }
    }

    public boolean compareDictionaries(PdfDictionary pdfDictionary, PdfDictionary pdfDictionary2) throws IOException {
        return compareDictionariesExtended(pdfDictionary, pdfDictionary2, (ObjectPath) null, (CompareResult) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0158, code lost:
        if (compareContentStreamsByParsingExtended(r8.getDirectObject(r0), r9.getDirectObject(r0), (com.itextpdf.text.pdf.PdfDictionary) r8.getDirectObject(com.itextpdf.text.pdf.PdfName.RESOURCES), (com.itextpdf.text.pdf.PdfDictionary) r9.getDirectObject(com.itextpdf.text.pdf.PdfName.RESOURCES), r19, r20) == false) goto L_0x015a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean compareDictionariesExtended(com.itextpdf.text.pdf.PdfDictionary r17, com.itextpdf.text.pdf.PdfDictionary r18, com.itextpdf.testutils.CompareTool.ObjectPath r19, com.itextpdf.testutils.CompareTool.CompareResult r20) throws java.io.IOException {
        /*
            r16 = this;
            r7 = r16
            r8 = r17
            r9 = r18
            r10 = r19
            r11 = r20
            r12 = 0
            if (r9 == 0) goto L_0x000f
            if (r8 == 0) goto L_0x0013
        L_0x000f:
            if (r8 == 0) goto L_0x0019
            if (r9 != 0) goto L_0x0019
        L_0x0013:
            java.lang.String r0 = "One of the dictionaries is null, the other is not."
            r11.addError(r10, r0)
            return r12
        L_0x0019:
            java.util.TreeSet r0 = new java.util.TreeSet
            java.util.Set r1 = r18.getKeys()
            r0.<init>(r1)
            java.util.Set r1 = r17.getKeys()
            r0.addAll(r1)
            java.util.Iterator r13 = r0.iterator()
            r14 = 1
            r15 = 1
        L_0x002f:
            boolean r0 = r13.hasNext()
            if (r0 == 0) goto L_0x0196
            java.lang.Object r0 = r13.next()
            com.itextpdf.text.pdf.PdfName r0 = (com.itextpdf.text.pdf.PdfName) r0
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.PARENT
            int r1 = r0.compareTo((com.itextpdf.text.pdf.PdfName) r1)
            if (r1 == 0) goto L_0x0190
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.f623P
            int r1 = r0.compareTo((com.itextpdf.text.pdf.PdfName) r1)
            if (r1 != 0) goto L_0x004d
            goto L_0x0190
        L_0x004d:
            boolean r1 = r17.isStream()
            if (r1 == 0) goto L_0x006b
            boolean r1 = r18.isStream()
            if (r1 == 0) goto L_0x006b
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.FILTER
            boolean r1 = r0.equals(r1)
            if (r1 != 0) goto L_0x0190
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.LENGTH
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x006b
            goto L_0x0190
        L_0x006b:
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.BASEFONT
            int r1 = r0.compareTo((com.itextpdf.text.pdf.PdfName) r1)
            if (r1 == 0) goto L_0x007b
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.FONTNAME
            int r1 = r0.compareTo((com.itextpdf.text.pdf.PdfName) r1)
            if (r1 != 0) goto L_0x0117
        L_0x007b:
            com.itextpdf.text.pdf.PdfObject r1 = r9.getDirectObject(r0)
            boolean r2 = r1.isName()
            if (r2 == 0) goto L_0x0117
            java.lang.String r2 = r1.toString()
            r3 = 43
            int r2 = r2.indexOf(r3)
            if (r2 <= 0) goto L_0x0117
            com.itextpdf.text.pdf.PdfObject r2 = r8.getDirectObject(r0)
            boolean r4 = r2.isName()
            r6 = 3
            java.lang.String r5 = "PdfDictionary %s entry: Expected: %s. Found: %s"
            if (r4 == 0) goto L_0x00a9
            java.lang.String r4 = r2.toString()
            int r4 = r4.indexOf(r3)
            r3 = -1
            if (r4 != r3) goto L_0x00ca
        L_0x00a9:
            if (r11 == 0) goto L_0x00c9
            if (r10 == 0) goto L_0x00c9
            java.lang.Object[] r3 = new java.lang.Object[r6]
            java.lang.String r4 = r0.toString()
            r3[r12] = r4
            java.lang.String r4 = r1.toString()
            r3[r14] = r4
            java.lang.String r4 = r2.toString()
            r15 = 2
            r3[r15] = r4
            java.lang.String r3 = java.lang.String.format(r5, r3)
            r11.addError(r10, r3)
        L_0x00c9:
            r15 = 0
        L_0x00ca:
            java.lang.String r3 = r1.toString()
            java.lang.String r4 = r1.toString()
            r14 = 43
            int r4 = r4.indexOf(r14)
            java.lang.String r3 = r3.substring(r4)
            java.lang.String r4 = r2.toString()
            java.lang.String r12 = r2.toString()
            int r12 = r12.indexOf(r14)
            java.lang.String r4 = r4.substring(r12)
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0192
            if (r11 == 0) goto L_0x0115
            if (r10 == 0) goto L_0x0115
            java.lang.Object[] r3 = new java.lang.Object[r6]
            java.lang.String r0 = r0.toString()
            r4 = 0
            r3[r4] = r0
            java.lang.String r0 = r1.toString()
            r12 = 1
            r3[r12] = r0
            java.lang.String r0 = r2.toString()
            r1 = 2
            r3[r1] = r0
            java.lang.String r0 = java.lang.String.format(r5, r3)
            r11.addError(r10, r0)
            goto L_0x015a
        L_0x0115:
            r12 = 1
            goto L_0x015a
        L_0x0117:
            r12 = 1
            double r1 = r7.floatComparisonError
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 == 0) goto L_0x0161
            boolean r1 = r18.isPage()
            if (r1 == 0) goto L_0x0161
            boolean r1 = r17.isPage()
            if (r1 == 0) goto L_0x0161
            com.itextpdf.text.pdf.PdfName r1 = com.itextpdf.text.pdf.PdfName.CONTENTS
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0161
            com.itextpdf.text.pdf.PdfObject r1 = r8.getDirectObject(r0)
            com.itextpdf.text.pdf.PdfObject r2 = r9.getDirectObject(r0)
            com.itextpdf.text.pdf.PdfName r0 = com.itextpdf.text.pdf.PdfName.RESOURCES
            com.itextpdf.text.pdf.PdfObject r0 = r8.getDirectObject(r0)
            r3 = r0
            com.itextpdf.text.pdf.PdfDictionary r3 = (com.itextpdf.text.pdf.PdfDictionary) r3
            com.itextpdf.text.pdf.PdfName r0 = com.itextpdf.text.pdf.PdfName.RESOURCES
            com.itextpdf.text.pdf.PdfObject r0 = r9.getDirectObject(r0)
            r4 = r0
            com.itextpdf.text.pdf.PdfDictionary r4 = (com.itextpdf.text.pdf.PdfDictionary) r4
            r0 = r16
            r5 = r19
            r6 = r20
            boolean r0 = r0.compareContentStreamsByParsingExtended(r1, r2, r3, r4, r5, r6)
            if (r0 != 0) goto L_0x015f
        L_0x015a:
            r12 = 0
            r14 = 1
            r15 = 0
            goto L_0x002f
        L_0x015f:
            r0 = 0
            goto L_0x0192
        L_0x0161:
            if (r10 == 0) goto L_0x016a
            java.lang.String r1 = r0.toString()
            r10.pushDictItemToPath(r1)
        L_0x016a:
            com.itextpdf.text.pdf.PdfObject r1 = r8.get(r0)
            com.itextpdf.text.pdf.PdfObject r0 = r9.get(r0)
            boolean r0 = r7.compareObjects(r1, r0, r10, r11)
            if (r0 == 0) goto L_0x017c
            if (r15 == 0) goto L_0x017c
            r15 = 1
            goto L_0x017d
        L_0x017c:
            r15 = 0
        L_0x017d:
            if (r10 == 0) goto L_0x0182
            r19.pop()
        L_0x0182:
            if (r15 != 0) goto L_0x015f
            if (r10 == 0) goto L_0x018e
            if (r11 == 0) goto L_0x018e
            boolean r0 = r20.isMessageLimitReached()
            if (r0 == 0) goto L_0x015f
        L_0x018e:
            r0 = 0
            return r0
        L_0x0190:
            r0 = 0
            r12 = 1
        L_0x0192:
            r12 = 0
            r14 = 1
            goto L_0x002f
        L_0x0196:
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.testutils.CompareTool.compareDictionariesExtended(com.itextpdf.text.pdf.PdfDictionary, com.itextpdf.text.pdf.PdfDictionary, com.itextpdf.testutils.CompareTool$ObjectPath, com.itextpdf.testutils.CompareTool$CompareResult):boolean");
    }

    public boolean compareContentStreamsByParsing(PdfObject pdfObject, PdfObject pdfObject2) throws IOException {
        return compareContentStreamsByParsingExtended(pdfObject, pdfObject2, (PdfDictionary) null, (PdfDictionary) null, (ObjectPath) null, (CompareResult) null);
    }

    public boolean compareContentStreamsByParsing(PdfObject pdfObject, PdfObject pdfObject2, PdfDictionary pdfDictionary, PdfDictionary pdfDictionary2) throws IOException {
        return compareContentStreamsByParsingExtended(pdfObject, pdfObject2, pdfDictionary, pdfDictionary2, (ObjectPath) null, (CompareResult) null);
    }

    private boolean compareContentStreamsByParsingExtended(PdfObject pdfObject, PdfObject pdfObject2, PdfDictionary pdfDictionary, PdfDictionary pdfDictionary2, ObjectPath objectPath, CompareResult compareResult) throws IOException {
        ObjectPath objectPath2 = objectPath;
        CompareResult compareResult2 = compareResult;
        int i = 2;
        if (pdfObject.type() != pdfObject.type()) {
            compareResult2.addError(objectPath2, String.format("PdfObject. Types are different. Expected: %s. Found: %s", new Object[]{Integer.valueOf(pdfObject2.type()), Integer.valueOf(pdfObject.type())}));
            return false;
        }
        if (pdfObject.isArray()) {
            PdfArray pdfArray = (PdfArray) pdfObject;
            PdfArray pdfArray2 = (PdfArray) pdfObject2;
            if (pdfArray2.size() != pdfArray.size()) {
                compareResult2.addError(objectPath2, String.format("PdfArray. Sizes are different. Expected: %s. Found: %s", new Object[]{Integer.valueOf(pdfArray2.size()), Integer.valueOf(pdfArray.size())}));
                return false;
            }
            for (int i2 = 0; i2 < pdfArray2.size(); i2++) {
                if (!compareContentStreamsByParsingExtended(pdfArray.getPdfObject(i2), pdfArray2.getPdfObject(i2), pdfDictionary, pdfDictionary2, objectPath, compareResult)) {
                    return false;
                }
            }
        }
        PRTokeniser pRTokeniser = new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(ContentByteUtils.getContentBytesFromContentObject(pdfObject2))));
        PRTokeniser pRTokeniser2 = new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(ContentByteUtils.getContentBytesFromContentObject(pdfObject))));
        PdfContentParser pdfContentParser = new PdfContentParser(pRTokeniser);
        PdfContentParser pdfContentParser2 = new PdfContentParser(pRTokeniser2);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        PdfDictionary pdfDictionary3 = pdfDictionary;
        PdfDictionary pdfDictionary4 = pdfDictionary2;
        while (pdfContentParser.parse(arrayList).size() > 0) {
            pdfContentParser2.parse(arrayList2);
            if (arrayList.size() != arrayList2.size()) {
                Object[] objArr = new Object[i];
                objArr[0] = Integer.valueOf(arrayList.size());
                objArr[1] = Integer.valueOf(arrayList2.size());
                compareResult2.addError(objectPath2, String.format("PdfObject. Different commands lengths. Expected: %s. Found: %s", objArr));
                return false;
            }
            if (arrayList.size() != 1 || !compareLiterals((PdfLiteral) arrayList.get(0), new PdfLiteral("BI")) || !compareLiterals((PdfLiteral) arrayList2.get(0), new PdfLiteral("BI"))) {
                ArrayList arrayList3 = arrayList2;
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    if (!compareObjects((PdfObject) arrayList3.get(i3), (PdfObject) arrayList.get(i3), objectPath2, compareResult2)) {
                        return false;
                    }
                }
                arrayList2 = arrayList3;
            } else {
                PRStream pRStream = (PRStream) pdfObject2;
                PRStream pRStream2 = (PRStream) pdfObject;
                if (!(pRStream2.getDirectObject(PdfName.RESOURCES) == null || pRStream.getDirectObject(PdfName.RESOURCES) == null)) {
                    pdfDictionary3 = (PdfDictionary) pRStream2.getDirectObject(PdfName.RESOURCES);
                    pdfDictionary4 = (PdfDictionary) pRStream.getDirectObject(PdfName.RESOURCES);
                }
                PdfDictionary pdfDictionary5 = pdfDictionary3;
                PdfDictionary pdfDictionary6 = pdfDictionary4;
                ArrayList arrayList4 = arrayList2;
                if (!compareInlineImagesExtended(pdfContentParser2, pdfContentParser, pdfDictionary5, pdfDictionary6, objectPath, compareResult)) {
                    return false;
                }
                arrayList2 = arrayList4;
                pdfDictionary3 = pdfDictionary5;
                pdfDictionary4 = pdfDictionary6;
            }
            i = 2;
        }
        return true;
    }

    private boolean compareInlineImagesExtended(PdfContentParser pdfContentParser, PdfContentParser pdfContentParser2, PdfDictionary pdfDictionary, PdfDictionary pdfDictionary2, ObjectPath objectPath, CompareResult compareResult) throws IOException {
        InlineImageInfo parseInlineImage = InlineImageUtils.parseInlineImage(pdfContentParser2, pdfDictionary2);
        InlineImageInfo parseInlineImage2 = InlineImageUtils.parseInlineImage(pdfContentParser, pdfDictionary);
        return compareObjects(parseInlineImage2.getImageDictionary(), parseInlineImage.getImageDictionary(), objectPath, compareResult) && Arrays.equals(parseInlineImage2.getSamples(), parseInlineImage.getSamples());
    }

    public boolean compareStreams(PRStream pRStream, PRStream pRStream2) throws IOException {
        return compareStreamsExtended(pRStream, pRStream2, (ObjectPath) null, (CompareResult) null);
    }

    private boolean compareStreamsExtended(PRStream pRStream, PRStream pRStream2, ObjectPath objectPath, CompareResult compareResult) throws IOException {
        PRStream pRStream3 = pRStream;
        PRStream pRStream4 = pRStream2;
        ObjectPath objectPath2 = objectPath;
        CompareResult compareResult2 = compareResult;
        boolean equals = PdfName.FLATEDECODE.equals(pRStream3.get(PdfName.FILTER));
        byte[] streamBytesRaw = PdfReader.getStreamBytesRaw(pRStream);
        byte[] streamBytesRaw2 = PdfReader.getStreamBytesRaw(pRStream2);
        if (equals) {
            streamBytesRaw = PdfReader.decodeBytes(streamBytesRaw, pRStream3);
            streamBytesRaw2 = PdfReader.decodeBytes(streamBytesRaw2, pRStream4);
        }
        if (this.floatComparisonError != 0.0d && PdfName.XOBJECT.equals(pRStream4.getDirectObject(PdfName.TYPE)) && PdfName.XOBJECT.equals(pRStream3.getDirectObject(PdfName.TYPE)) && PdfName.FORM.equals(pRStream4.getDirectObject(PdfName.SUBTYPE)) && PdfName.FORM.equals(pRStream3.getDirectObject(PdfName.SUBTYPE))) {
            if (!compareContentStreamsByParsingExtended(pRStream, pRStream2, pRStream3.getAsDict(PdfName.RESOURCES), pRStream4.getAsDict(PdfName.RESOURCES), objectPath, compareResult) || !compareDictionariesExtended(pRStream, pRStream2, objectPath, compareResult)) {
                return false;
            }
            return true;
        } else if (Arrays.equals(streamBytesRaw, streamBytesRaw2)) {
            return compareDictionariesExtended(pRStream, pRStream2, objectPath, compareResult);
        } else {
            char c = 2;
            if (streamBytesRaw2.length == streamBytesRaw.length) {
                int i = 0;
                while (i < streamBytesRaw2.length) {
                    if (streamBytesRaw2[i] != streamBytesRaw[i]) {
                        int max = Math.max(0, i - 10);
                        int min = Math.min(streamBytesRaw2.length, i + 10);
                        if (!(compareResult2 == null || objectPath2 == null)) {
                            objectPath2.pushOffsetToPath(i);
                            Object[] objArr = new Object[5];
                            objArr[0] = Integer.valueOf(i);
                            objArr[1] = new String(new byte[]{streamBytesRaw2[i]});
                            int i2 = min - max;
                            objArr[c] = new String(streamBytesRaw2, max, i2).replaceAll("\\n", "\\\\n");
                            objArr[3] = new String(new byte[]{streamBytesRaw[i]});
                            objArr[4] = new String(streamBytesRaw, max, i2).replaceAll("\\n", "\\\\n");
                            compareResult2.addError(objectPath2, String.format("PRStream. The bytes differ at index %s. Expected: %s (%s). Found: %s (%s)", objArr));
                            objectPath.pop();
                        }
                    }
                    i++;
                    c = 2;
                }
            } else if (!(compareResult2 == null || objectPath2 == null)) {
                compareResult2.addError(objectPath2, String.format("PRStream. Lengths are different. Expected: %s. Found: %s", new Object[]{Integer.valueOf(streamBytesRaw2.length), Integer.valueOf(streamBytesRaw.length)}));
            }
            return false;
        }
    }

    public boolean compareArrays(PdfArray pdfArray, PdfArray pdfArray2) throws IOException {
        return compareArraysExtended(pdfArray, pdfArray2, (ObjectPath) null, (CompareResult) null);
    }

    private boolean compareArraysExtended(PdfArray pdfArray, PdfArray pdfArray2, ObjectPath objectPath, CompareResult compareResult) throws IOException {
        if (pdfArray == null) {
            if (!(compareResult == null || objectPath == null)) {
                compareResult.addError(objectPath, "Found null. Expected PdfArray.");
            }
            return false;
        } else if (pdfArray.size() != pdfArray2.size()) {
            if (!(compareResult == null || objectPath == null)) {
                compareResult.addError(objectPath, String.format("PdfArrays. Lengths are different. Expected: %s. Found: %s.", new Object[]{Integer.valueOf(pdfArray2.size()), Integer.valueOf(pdfArray.size())}));
            }
            return false;
        } else {
            boolean z = true;
            for (int i = 0; i < pdfArray2.size(); i++) {
                if (objectPath != null) {
                    objectPath.pushArrayItemToPath(i);
                }
                z = compareObjects(pdfArray.getPdfObject(i), pdfArray2.getPdfObject(i), objectPath, compareResult) && z;
                if (objectPath != null) {
                    objectPath.pop();
                }
                if (!z && (objectPath == null || compareResult == null || compareResult.isMessageLimitReached())) {
                    return false;
                }
            }
            return z;
        }
    }

    public boolean compareNames(PdfName pdfName, PdfName pdfName2) {
        return pdfName2.compareTo(pdfName) == 0;
    }

    private boolean compareNamesExtended(PdfName pdfName, PdfName pdfName2, ObjectPath objectPath, CompareResult compareResult) {
        if (pdfName2.compareTo(pdfName) == 0) {
            return true;
        }
        if (!(compareResult == null || objectPath == null)) {
            compareResult.addError(objectPath, String.format("PdfName. Expected: %s. Found: %s", new Object[]{pdfName2.toString(), pdfName.toString()}));
        }
        return false;
    }

    public boolean compareNumbers(PdfNumber pdfNumber, PdfNumber pdfNumber2) {
        double abs = Math.abs(pdfNumber.doubleValue() - pdfNumber2.doubleValue());
        if (!this.absoluteError && pdfNumber2.doubleValue() != 0.0d) {
            abs /= pdfNumber2.doubleValue();
        }
        return abs <= this.floatComparisonError;
    }

    private boolean compareNumbersExtended(PdfNumber pdfNumber, PdfNumber pdfNumber2, ObjectPath objectPath, CompareResult compareResult) {
        if (compareNumbers(pdfNumber, pdfNumber2)) {
            return true;
        }
        if (!(compareResult == null || objectPath == null)) {
            compareResult.addError(objectPath, String.format("PdfNumber. Expected: %s. Found: %s", new Object[]{pdfNumber2, pdfNumber}));
        }
        return false;
    }

    public boolean compareStrings(PdfString pdfString, PdfString pdfString2) {
        return Arrays.equals(pdfString2.getBytes(), pdfString.getBytes());
    }

    private boolean compareStringsExtended(PdfString pdfString, PdfString pdfString2, ObjectPath objectPath, CompareResult compareResult) {
        if (Arrays.equals(pdfString2.getBytes(), pdfString.getBytes())) {
            return true;
        }
        String unicodeString = pdfString2.toUnicodeString();
        String unicodeString2 = pdfString.toUnicodeString();
        if (unicodeString.length() == unicodeString2.length()) {
            int i = 0;
            while (true) {
                if (i >= unicodeString.length()) {
                    break;
                } else if (unicodeString.charAt(i) != unicodeString2.charAt(i)) {
                    int max = Math.max(0, i - 10);
                    int min = Math.min(unicodeString.length(), i + 10);
                    if (compareResult != null && objectPath != null) {
                        objectPath.pushOffsetToPath(i);
                        compareResult.addError(objectPath, String.format("PdfString. Characters differ at position %s. Expected: %s (%s). Found: %s (%s).", new Object[]{Integer.valueOf(i), Character.toString(unicodeString.charAt(i)), unicodeString.substring(max, min).replace("\n", "\\n"), Character.toString(unicodeString2.charAt(i)), unicodeString2.substring(max, min).replace("\n", "\\n")}));
                        objectPath.pop();
                    }
                } else {
                    i++;
                }
            }
        } else if (!(compareResult == null || objectPath == null)) {
            compareResult.addError(objectPath, String.format("PdfString. Lengths are different. Expected: %s. Found: %s", new Object[]{Integer.valueOf(unicodeString.length()), Integer.valueOf(unicodeString2.length())}));
        }
        return false;
    }

    public boolean compareLiterals(PdfLiteral pdfLiteral, PdfLiteral pdfLiteral2) {
        return Arrays.equals(pdfLiteral2.getBytes(), pdfLiteral.getBytes());
    }

    private boolean compareLiteralsExtended(PdfLiteral pdfLiteral, PdfLiteral pdfLiteral2, ObjectPath objectPath, CompareResult compareResult) {
        if (compareLiterals(pdfLiteral, pdfLiteral2)) {
            return true;
        }
        if (!(compareResult == null || objectPath == null)) {
            compareResult.addError(objectPath, String.format("PdfLiteral. Expected: %s. Found: %s", new Object[]{pdfLiteral2, pdfLiteral}));
        }
        return false;
    }

    public boolean compareBooleans(PdfBoolean pdfBoolean, PdfBoolean pdfBoolean2) {
        return Arrays.equals(pdfBoolean2.getBytes(), pdfBoolean.getBytes());
    }

    private boolean compareBooleansExtended(PdfBoolean pdfBoolean, PdfBoolean pdfBoolean2, ObjectPath objectPath, CompareResult compareResult) {
        if (pdfBoolean2.booleanValue() == pdfBoolean.booleanValue()) {
            return true;
        }
        if (!(compareResult == null || objectPath == null)) {
            compareResult.addError(objectPath, String.format("PdfBoolean. Expected: %s. Found: %s.", new Object[]{Boolean.valueOf(pdfBoolean2.booleanValue()), Boolean.valueOf(pdfBoolean.booleanValue())}));
        }
        return false;
    }

    public String compareXmp(String str, String str2) {
        return compareXmp(str, str2, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0093  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00ab  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00b2  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00b7  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00be  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00c3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String compareXmp(java.lang.String r13, java.lang.String r14, boolean r15) {
        /*
            r12 = this;
            java.lang.String r0 = "Producer"
            java.lang.String r1 = "http://ns.adobe.com/pdf/1.3/"
            java.lang.String r2 = "MetadataDate"
            java.lang.String r3 = "ModifyDate"
            java.lang.String r4 = "CreateDate"
            java.lang.String r5 = "XMP parsing failure!"
            java.lang.String r6 = "http://ns.adobe.com/xap/1.0/"
            r12.init(r13, r14)
            r13 = 0
            com.itextpdf.text.pdf.PdfReader r14 = new com.itextpdf.text.pdf.PdfReader     // Catch:{ XMPException -> 0x00bb, IOException -> 0x00af, ParserConfigurationException -> 0x00a3, SAXException -> 0x0097, all -> 0x0088 }
            java.lang.String r7 = r12.cmpPdf     // Catch:{ XMPException -> 0x00bb, IOException -> 0x00af, ParserConfigurationException -> 0x00a3, SAXException -> 0x0097, all -> 0x0088 }
            r14.<init>((java.lang.String) r7)     // Catch:{ XMPException -> 0x00bb, IOException -> 0x00af, ParserConfigurationException -> 0x00a3, SAXException -> 0x0097, all -> 0x0088 }
            com.itextpdf.text.pdf.PdfReader r7 = new com.itextpdf.text.pdf.PdfReader     // Catch:{ XMPException -> 0x0085, IOException -> 0x0082, ParserConfigurationException -> 0x007f, SAXException -> 0x007c, all -> 0x0078 }
            java.lang.String r8 = r12.outPdf     // Catch:{ XMPException -> 0x0085, IOException -> 0x0082, ParserConfigurationException -> 0x007f, SAXException -> 0x007c, all -> 0x0078 }
            r7.<init>((java.lang.String) r8)     // Catch:{ XMPException -> 0x0085, IOException -> 0x0082, ParserConfigurationException -> 0x007f, SAXException -> 0x007c, all -> 0x0078 }
            byte[] r8 = r14.getMetadata()     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            byte[] r9 = r7.getMetadata()     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            if (r15 == 0) goto L_0x0060
            com.itextpdf.xmp.XMPMeta r15 = com.itextpdf.xmp.XMPMetaFactory.parseFromBuffer(r8)     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            r8 = 1
            com.itextpdf.xmp.XMPUtils.removeProperties(r15, r6, r4, r8, r8)     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            com.itextpdf.xmp.XMPUtils.removeProperties(r15, r6, r3, r8, r8)     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            com.itextpdf.xmp.XMPUtils.removeProperties(r15, r6, r2, r8, r8)     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            com.itextpdf.xmp.XMPUtils.removeProperties(r15, r1, r0, r8, r8)     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            com.itextpdf.xmp.options.SerializeOptions r10 = new com.itextpdf.xmp.options.SerializeOptions     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            r11 = 8192(0x2000, float:1.14794E-41)
            r10.<init>(r11)     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            byte[] r15 = com.itextpdf.xmp.XMPMetaFactory.serializeToBuffer(r15, r10)     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            com.itextpdf.xmp.XMPMeta r9 = com.itextpdf.xmp.XMPMetaFactory.parseFromBuffer(r9)     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            com.itextpdf.xmp.XMPUtils.removeProperties(r9, r6, r4, r8, r8)     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            com.itextpdf.xmp.XMPUtils.removeProperties(r9, r6, r3, r8, r8)     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            com.itextpdf.xmp.XMPUtils.removeProperties(r9, r6, r2, r8, r8)     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            com.itextpdf.xmp.XMPUtils.removeProperties(r9, r1, r0, r8, r8)     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            com.itextpdf.xmp.options.SerializeOptions r0 = new com.itextpdf.xmp.options.SerializeOptions     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            r0.<init>(r11)     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            byte[] r9 = com.itextpdf.xmp.XMPMetaFactory.serializeToBuffer(r9, r0)     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            r8 = r15
        L_0x0060:
            boolean r15 = r12.compareXmls((byte[]) r8, (byte[]) r9)     // Catch:{ XMPException -> 0x0086, IOException -> 0x0083, ParserConfigurationException -> 0x0080, SAXException -> 0x007d, all -> 0x0076 }
            if (r15 != 0) goto L_0x006f
            java.lang.String r13 = "The XMP packages different!"
            r14.close()
            r7.close()
            return r13
        L_0x006f:
            r14.close()
            r7.close()
            return r13
        L_0x0076:
            r13 = move-exception
            goto L_0x008c
        L_0x0078:
            r15 = move-exception
            r7 = r13
            r13 = r15
            goto L_0x008c
        L_0x007c:
            r7 = r13
        L_0x007d:
            r13 = r14
            goto L_0x0098
        L_0x007f:
            r7 = r13
        L_0x0080:
            r13 = r14
            goto L_0x00a4
        L_0x0082:
            r7 = r13
        L_0x0083:
            r13 = r14
            goto L_0x00b0
        L_0x0085:
            r7 = r13
        L_0x0086:
            r13 = r14
            goto L_0x00bc
        L_0x0088:
            r14 = move-exception
            r7 = r13
            r13 = r14
            r14 = r7
        L_0x008c:
            if (r14 == 0) goto L_0x0091
            r14.close()
        L_0x0091:
            if (r7 == 0) goto L_0x0096
            r7.close()
        L_0x0096:
            throw r13
        L_0x0097:
            r7 = r13
        L_0x0098:
            if (r13 == 0) goto L_0x009d
            r13.close()
        L_0x009d:
            if (r7 == 0) goto L_0x00a2
            r7.close()
        L_0x00a2:
            return r5
        L_0x00a3:
            r7 = r13
        L_0x00a4:
            if (r13 == 0) goto L_0x00a9
            r13.close()
        L_0x00a9:
            if (r7 == 0) goto L_0x00ae
            r7.close()
        L_0x00ae:
            return r5
        L_0x00af:
            r7 = r13
        L_0x00b0:
            if (r13 == 0) goto L_0x00b5
            r13.close()
        L_0x00b5:
            if (r7 == 0) goto L_0x00ba
            r7.close()
        L_0x00ba:
            return r5
        L_0x00bb:
            r7 = r13
        L_0x00bc:
            if (r13 == 0) goto L_0x00c1
            r13.close()
        L_0x00c1:
            if (r7 == 0) goto L_0x00c6
            r7.close()
        L_0x00c6:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.testutils.CompareTool.compareXmp(java.lang.String, java.lang.String, boolean):java.lang.String");
    }

    public boolean compareXmls(byte[] bArr, byte[] bArr2) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
        newInstance.setNamespaceAware(true);
        newInstance.setCoalescing(true);
        newInstance.setIgnoringElementContentWhitespace(true);
        newInstance.setIgnoringComments(true);
        DocumentBuilder newDocumentBuilder = newInstance.newDocumentBuilder();
        Document parse = newDocumentBuilder.parse(new ByteArrayInputStream(bArr));
        parse.normalizeDocument();
        Document parse2 = newDocumentBuilder.parse(new ByteArrayInputStream(bArr2));
        parse2.normalizeDocument();
        return parse2.isEqualNode(parse);
    }

    public String compareDocumentInfo(String str, String str2) throws IOException {
        String str3;
        System.out.print("[itext] INFO  Comparing document info.......");
        PdfReader pdfReader = new PdfReader(str);
        PdfReader pdfReader2 = new PdfReader(str2);
        String[] convertInfo = convertInfo(pdfReader2.getInfo());
        String[] convertInfo2 = convertInfo(pdfReader.getInfo());
        int i = 0;
        while (true) {
            if (i >= convertInfo.length) {
                str3 = null;
                break;
            } else if (!convertInfo[i].equals(convertInfo2[i])) {
                str3 = "Document info fail";
                break;
            } else {
                i++;
            }
        }
        pdfReader.close();
        pdfReader2.close();
        if (str3 == null) {
            System.out.println("OK");
        } else {
            System.out.println("Fail");
        }
        System.out.flush();
        return str3;
    }

    private boolean linksAreSame(PdfAnnotation.PdfImportedLink pdfImportedLink, PdfAnnotation.PdfImportedLink pdfImportedLink2) {
        if (pdfImportedLink.getDestinationPage() != pdfImportedLink2.getDestinationPage() || !pdfImportedLink.getRect().toString().equals(pdfImportedLink2.getRect().toString())) {
            return false;
        }
        Map<PdfName, PdfObject> parameters = pdfImportedLink.getParameters();
        Map<PdfName, PdfObject> parameters2 = pdfImportedLink2.getParameters();
        if (parameters.size() != parameters2.size()) {
            return false;
        }
        for (Map.Entry next : parameters.entrySet()) {
            PdfObject pdfObject = (PdfObject) next.getValue();
            if (!parameters2.containsKey(next.getKey())) {
                return false;
            }
            PdfObject pdfObject2 = parameters2.get(next.getKey());
            if (pdfObject.type() != pdfObject2.type()) {
                return false;
            }
            int type = pdfObject.type();
            if ((type == 1 || type == 2 || type == 3 || type == 4 || type == 8) && !pdfObject.toString().equals(pdfObject2.toString())) {
                return false;
            }
        }
        return true;
    }

    public String compareLinks(String str, String str2) throws IOException {
        System.out.print("[itext] INFO  Comparing link annotations....");
        PdfReader pdfReader = new PdfReader(str);
        PdfReader pdfReader2 = new PdfReader(str2);
        String str3 = null;
        int i = 0;
        while (true) {
            if (i >= pdfReader.getNumberOfPages() || i >= pdfReader2.getNumberOfPages()) {
                break;
            }
            i++;
            ArrayList<PdfAnnotation.PdfImportedLink> links = pdfReader.getLinks(i);
            ArrayList<PdfAnnotation.PdfImportedLink> links2 = pdfReader2.getLinks(i);
            if (links2.size() != links.size()) {
                str3 = String.format("Different number of links on page %d.", new Object[]{Integer.valueOf(i)});
                break;
            }
            int i2 = 0;
            while (true) {
                if (i2 >= links2.size()) {
                    break;
                } else if (!linksAreSame(links2.get(i2), links.get(i2))) {
                    str3 = String.format("Different links on page %d.\n%s\n%s", new Object[]{Integer.valueOf(i), links2.get(i2).toString(), links.get(i2).toString()});
                    break;
                } else {
                    i2++;
                }
            }
        }
        pdfReader.close();
        pdfReader2.close();
        if (str3 == null) {
            System.out.println("OK");
        } else {
            System.out.println("Fail");
        }
        System.out.flush();
        return str3;
    }

    public String compareTagStructures(String str, String str2) throws IOException, ParserConfigurationException, SAXException {
        System.out.print("[itext] INFO  Comparing tag structures......");
        String replace = str.replace(".pdf", ".xml");
        String replace2 = str.replace(".pdf", ".cmp.xml");
        PdfReader pdfReader = new PdfReader(str);
        FileOutputStream fileOutputStream = new FileOutputStream(replace);
        new CmpTaggedPdfReaderTool().convertToXml(pdfReader, fileOutputStream);
        pdfReader.close();
        PdfReader pdfReader2 = new PdfReader(str2);
        FileOutputStream fileOutputStream2 = new FileOutputStream(replace2);
        new CmpTaggedPdfReaderTool().convertToXml(pdfReader2, fileOutputStream2);
        pdfReader2.close();
        String str3 = !compareXmls(replace, replace2) ? "The tag structures are different." : null;
        fileOutputStream.close();
        fileOutputStream2.close();
        if (str3 == null) {
            System.out.println("OK");
        } else {
            System.out.println("Fail");
        }
        System.out.flush();
        return str3;
    }

    private String[] convertInfo(HashMap<String, String> hashMap) {
        String[] strArr = {"", "", "", ""};
        for (Map.Entry next : hashMap.entrySet()) {
            if ("title".equalsIgnoreCase((String) next.getKey())) {
                strArr[0] = (String) next.getValue();
            } else if (Meta.AUTHOR.equalsIgnoreCase((String) next.getKey())) {
                strArr[1] = (String) next.getValue();
            } else if ("subject".equalsIgnoreCase((String) next.getKey())) {
                strArr[2] = (String) next.getValue();
            } else if (Meta.KEYWORDS.equalsIgnoreCase((String) next.getKey())) {
                strArr[3] = (String) next.getValue();
            }
        }
        return strArr;
    }

    public boolean compareXmls(String str, String str2) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
        newInstance.setNamespaceAware(true);
        newInstance.setCoalescing(true);
        newInstance.setIgnoringElementContentWhitespace(true);
        newInstance.setIgnoringComments(true);
        DocumentBuilder newDocumentBuilder = newInstance.newDocumentBuilder();
        Document parse = newDocumentBuilder.parse(new File(str));
        parse.normalizeDocument();
        Document parse2 = newDocumentBuilder.parse(new File(str2));
        parse2.normalizeDocument();
        return parse2.isEqualNode(parse);
    }

    private void init(String str, String str2) {
        this.outPdf = str;
        this.cmpPdf = str2;
        this.outPdfName = new File(str).getName();
        this.cmpPdfName = new File(str2).getName();
        this.outImage = this.outPdfName + "-%03d.png";
        if (this.cmpPdfName.startsWith("cmp_")) {
            this.cmpImage = this.cmpPdfName + "-%03d.png";
            return;
        }
        this.cmpImage = "cmp_" + this.cmpPdfName + "-%03d.png";
    }

    private boolean compareStreams(InputStream inputStream, InputStream inputStream2) throws IOException {
        int read;
        byte[] bArr = new byte[65536];
        byte[] bArr2 = new byte[65536];
        do {
            read = inputStream.read(bArr);
            if (read != inputStream2.read(bArr2) || !Arrays.equals(bArr, bArr2)) {
                return false;
            }
        } while (read != -1);
        return true;
    }

    class PngFileFilter implements FileFilter {
        PngFileFilter() {
        }

        public boolean accept(File file) {
            String absolutePath = file.getAbsolutePath();
            return absolutePath.endsWith(".png") && !absolutePath.contains("cmp_") && absolutePath.contains(CompareTool.this.outPdfName);
        }
    }

    class CmpPngFileFilter implements FileFilter {
        CmpPngFileFilter() {
        }

        public boolean accept(File file) {
            String absolutePath = file.getAbsolutePath();
            return absolutePath.endsWith(".png") && absolutePath.contains("cmp_") && absolutePath.contains(CompareTool.this.cmpPdfName);
        }
    }

    class ImageNameComparator implements Comparator<File> {
        ImageNameComparator() {
        }

        public int compare(File file, File file2) {
            return file.getAbsolutePath().compareTo(file2.getAbsolutePath());
        }
    }

    class CmpTaggedPdfReaderTool extends TaggedPdfReaderTool {
        Map<PdfDictionary, Map<Integer, String>> parsedTags = new HashMap();

        CmpTaggedPdfReaderTool() {
        }

        public void parseTag(String str, PdfObject pdfObject, PdfDictionary pdfDictionary) throws IOException {
            if (pdfObject instanceof PdfNumber) {
                if (!this.parsedTags.containsKey(pdfDictionary)) {
                    CmpMarkedContentRenderFilter cmpMarkedContentRenderFilter = new CmpMarkedContentRenderFilter();
                    new PdfContentStreamProcessor(cmpMarkedContentRenderFilter).processContent(PdfReader.getPageContent(pdfDictionary), pdfDictionary.getAsDict(PdfName.RESOURCES));
                    this.parsedTags.put(pdfDictionary, cmpMarkedContentRenderFilter.getParsedTagContent());
                }
                PdfNumber pdfNumber = (PdfNumber) pdfObject;
                this.out.print(XMLUtil.escapeXML(this.parsedTags.get(pdfDictionary).containsKey(Integer.valueOf(pdfNumber.intValue())) ? (String) this.parsedTags.get(pdfDictionary).get(Integer.valueOf(pdfNumber.intValue())) : "", true));
                return;
            }
            super.parseTag(str, pdfObject, pdfDictionary);
        }

        public void inspectChildDictionary(PdfDictionary pdfDictionary) throws IOException {
            inspectChildDictionary(pdfDictionary, true);
        }
    }

    class CmpMarkedContentRenderFilter implements RenderListener {
        Map<Integer, TextExtractionStrategy> tagsByMcid = new HashMap();

        public void renderImage(ImageRenderInfo imageRenderInfo) {
        }

        CmpMarkedContentRenderFilter() {
        }

        public Map<Integer, String> getParsedTagContent() {
            HashMap hashMap = new HashMap();
            for (Integer intValue : this.tagsByMcid.keySet()) {
                int intValue2 = intValue.intValue();
                hashMap.put(Integer.valueOf(intValue2), this.tagsByMcid.get(Integer.valueOf(intValue2)).getResultantText());
            }
            return hashMap;
        }

        public void beginTextBlock() {
            for (Integer intValue : this.tagsByMcid.keySet()) {
                this.tagsByMcid.get(Integer.valueOf(intValue.intValue())).beginTextBlock();
            }
        }

        public void renderText(TextRenderInfo textRenderInfo) {
            Integer mcid = textRenderInfo.getMcid();
            if (mcid != null && this.tagsByMcid.containsKey(mcid)) {
                this.tagsByMcid.get(mcid).renderText(textRenderInfo);
            } else if (mcid != null) {
                this.tagsByMcid.put(mcid, new SimpleTextExtractionStrategy());
                this.tagsByMcid.get(mcid).renderText(textRenderInfo);
            }
        }

        public void endTextBlock() {
            for (Integer intValue : this.tagsByMcid.keySet()) {
                this.tagsByMcid.get(Integer.valueOf(intValue.intValue())).endTextBlock();
            }
        }
    }
}
