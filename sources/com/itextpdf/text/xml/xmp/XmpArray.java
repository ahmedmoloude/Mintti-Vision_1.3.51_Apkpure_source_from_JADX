package com.itextpdf.text.xml.xmp;

import com.itextpdf.text.xml.XMLUtil;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.text.Typography;

@Deprecated
public class XmpArray extends ArrayList<String> {
    public static final String ALTERNATIVE = "rdf:Alt";
    public static final String ORDERED = "rdf:Seq";
    public static final String UNORDERED = "rdf:Bag";
    private static final long serialVersionUID = 5722854116328732742L;
    protected String type;

    public XmpArray(String str) {
        this.type = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("<");
        stringBuffer.append(this.type);
        stringBuffer.append(Typography.greater);
        Iterator it = iterator();
        while (it.hasNext()) {
            stringBuffer.append("<rdf:li>");
            stringBuffer.append(XMLUtil.escapeXML((String) it.next(), false));
            stringBuffer.append("</rdf:li>");
        }
        stringBuffer.append("</");
        stringBuffer.append(this.type);
        stringBuffer.append(Typography.greater);
        return stringBuffer.toString();
    }
}
