package com.itextpdf.text.pdf.security;

import androidx.exifinterface.media.ExifInterface;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.error_messages.MessageLocalization;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;

public class CertificateInfo {

    public static class X500Name {

        /* renamed from: C */
        public static final ASN1ObjectIdentifier f766C;

        /* renamed from: CN */
        public static final ASN1ObjectIdentifier f767CN;

        /* renamed from: DC */
        public static final ASN1ObjectIdentifier f768DC;
        public static final Map<ASN1ObjectIdentifier, String> DefaultSymbols;

        /* renamed from: E */
        public static final ASN1ObjectIdentifier f769E;
        public static final ASN1ObjectIdentifier EmailAddress;
        public static final ASN1ObjectIdentifier GENERATION;
        public static final ASN1ObjectIdentifier GIVENNAME;
        public static final ASN1ObjectIdentifier INITIALS;

        /* renamed from: L */
        public static final ASN1ObjectIdentifier f770L;

        /* renamed from: O */
        public static final ASN1ObjectIdentifier f771O;

        /* renamed from: OU */
        public static final ASN1ObjectIdentifier f772OU;

        /* renamed from: SN */
        public static final ASN1ObjectIdentifier f773SN;

        /* renamed from: ST */
        public static final ASN1ObjectIdentifier f774ST;
        public static final ASN1ObjectIdentifier SURNAME;

        /* renamed from: T */
        public static final ASN1ObjectIdentifier f775T;
        public static final ASN1ObjectIdentifier UID;
        public static final ASN1ObjectIdentifier UNIQUE_IDENTIFIER = new ASN1ObjectIdentifier("2.5.4.45");
        public Map<String, ArrayList<String>> values = new HashMap();

        static {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("2.5.4.6");
            f766C = aSN1ObjectIdentifier;
            ASN1ObjectIdentifier aSN1ObjectIdentifier2 = new ASN1ObjectIdentifier("2.5.4.10");
            f771O = aSN1ObjectIdentifier2;
            ASN1ObjectIdentifier aSN1ObjectIdentifier3 = new ASN1ObjectIdentifier("2.5.4.11");
            f772OU = aSN1ObjectIdentifier3;
            ASN1ObjectIdentifier aSN1ObjectIdentifier4 = new ASN1ObjectIdentifier("2.5.4.12");
            f775T = aSN1ObjectIdentifier4;
            ASN1ObjectIdentifier aSN1ObjectIdentifier5 = new ASN1ObjectIdentifier("2.5.4.3");
            f767CN = aSN1ObjectIdentifier5;
            ASN1ObjectIdentifier aSN1ObjectIdentifier6 = new ASN1ObjectIdentifier("2.5.4.5");
            f773SN = aSN1ObjectIdentifier6;
            ASN1ObjectIdentifier aSN1ObjectIdentifier7 = new ASN1ObjectIdentifier("2.5.4.7");
            f770L = aSN1ObjectIdentifier7;
            ASN1ObjectIdentifier aSN1ObjectIdentifier8 = new ASN1ObjectIdentifier("2.5.4.8");
            f774ST = aSN1ObjectIdentifier8;
            ASN1ObjectIdentifier aSN1ObjectIdentifier9 = new ASN1ObjectIdentifier("2.5.4.4");
            SURNAME = aSN1ObjectIdentifier9;
            ASN1ObjectIdentifier aSN1ObjectIdentifier10 = new ASN1ObjectIdentifier("2.5.4.42");
            GIVENNAME = aSN1ObjectIdentifier10;
            ASN1ObjectIdentifier aSN1ObjectIdentifier11 = new ASN1ObjectIdentifier("2.5.4.43");
            INITIALS = aSN1ObjectIdentifier11;
            ASN1ObjectIdentifier aSN1ObjectIdentifier12 = new ASN1ObjectIdentifier("2.5.4.44");
            GENERATION = aSN1ObjectIdentifier12;
            ASN1ObjectIdentifier aSN1ObjectIdentifier13 = new ASN1ObjectIdentifier("1.2.840.113549.1.9.1");
            EmailAddress = aSN1ObjectIdentifier13;
            f769E = aSN1ObjectIdentifier13;
            ASN1ObjectIdentifier aSN1ObjectIdentifier14 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.25");
            f768DC = aSN1ObjectIdentifier14;
            ASN1ObjectIdentifier aSN1ObjectIdentifier15 = new ASN1ObjectIdentifier("0.9.2342.19200300.100.1.1");
            UID = aSN1ObjectIdentifier15;
            HashMap hashMap = new HashMap();
            DefaultSymbols = hashMap;
            hashMap.put(aSN1ObjectIdentifier, "C");
            hashMap.put(aSN1ObjectIdentifier2, "O");
            hashMap.put(aSN1ObjectIdentifier4, ExifInterface.GPS_DIRECTION_TRUE);
            hashMap.put(aSN1ObjectIdentifier3, "OU");
            hashMap.put(aSN1ObjectIdentifier5, "CN");
            hashMap.put(aSN1ObjectIdentifier7, "L");
            hashMap.put(aSN1ObjectIdentifier8, "ST");
            hashMap.put(aSN1ObjectIdentifier6, "SN");
            hashMap.put(aSN1ObjectIdentifier13, ExifInterface.LONGITUDE_EAST);
            hashMap.put(aSN1ObjectIdentifier14, "DC");
            hashMap.put(aSN1ObjectIdentifier15, "UID");
            hashMap.put(aSN1ObjectIdentifier9, "SURNAME");
            hashMap.put(aSN1ObjectIdentifier10, "GIVENNAME");
            hashMap.put(aSN1ObjectIdentifier11, "INITIALS");
            hashMap.put(aSN1ObjectIdentifier12, "GENERATION");
        }

        public X500Name(ASN1Sequence aSN1Sequence) {
            Enumeration objects = aSN1Sequence.getObjects();
            while (objects.hasMoreElements()) {
                ASN1Set aSN1Set = (ASN1Set) objects.nextElement();
                for (int i = 0; i < aSN1Set.size(); i++) {
                    ASN1Sequence objectAt = aSN1Set.getObjectAt(i);
                    String str = DefaultSymbols.get(objectAt.getObjectAt(0));
                    if (str != null) {
                        ArrayList arrayList = this.values.get(str);
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                            this.values.put(str, arrayList);
                        }
                        arrayList.add(objectAt.getObjectAt(1).getString());
                    }
                }
            }
        }

        public X500Name(String str) {
            X509NameTokenizer x509NameTokenizer = new X509NameTokenizer(str);
            while (x509NameTokenizer.hasMoreTokens()) {
                String nextToken = x509NameTokenizer.nextToken();
                int indexOf = nextToken.indexOf(61);
                if (indexOf != -1) {
                    String upperCase = nextToken.substring(0, indexOf).toUpperCase();
                    String substring = nextToken.substring(indexOf + 1);
                    ArrayList arrayList = this.values.get(upperCase);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        this.values.put(upperCase, arrayList);
                    }
                    arrayList.add(substring);
                } else {
                    throw new IllegalArgumentException(MessageLocalization.getComposedMessage("badly.formated.directory.string", new Object[0]));
                }
            }
        }

        public String getField(String str) {
            List list = this.values.get(str);
            if (list == null) {
                return null;
            }
            return (String) list.get(0);
        }

        public List<String> getFieldArray(String str) {
            return this.values.get(str);
        }

        public Map<String, ArrayList<String>> getFields() {
            return this.values;
        }

        public String toString() {
            return this.values.toString();
        }
    }

    public static class X509NameTokenizer {
        private StringBuffer buf = new StringBuffer();
        private int index;
        private String oid;

        public X509NameTokenizer(String str) {
            this.oid = str;
            this.index = -1;
        }

        public boolean hasMoreTokens() {
            return this.index != this.oid.length();
        }

        public String nextToken() {
            if (this.index == this.oid.length()) {
                return null;
            }
            int i = this.index + 1;
            this.buf.setLength(0);
            boolean z = false;
            boolean z2 = false;
            while (i != this.oid.length()) {
                char charAt = this.oid.charAt(i);
                if (charAt == '\"') {
                    if (!z) {
                        z2 = !z2;
                    } else {
                        this.buf.append(charAt);
                    }
                } else if (z || z2) {
                    this.buf.append(charAt);
                } else {
                    if (charAt == '\\') {
                        z = true;
                    } else if (charAt == ',') {
                        break;
                    } else {
                        this.buf.append(charAt);
                    }
                    i++;
                }
                z = false;
                i++;
            }
            this.index = i;
            return this.buf.toString().trim();
        }
    }

    public static X500Name getIssuerFields(X509Certificate x509Certificate) {
        try {
            return new X500Name(getIssuer(x509Certificate.getTBSCertificate()));
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    public static ASN1Primitive getIssuer(byte[] bArr) {
        try {
            ASN1Sequence readObject = new ASN1InputStream(new ByteArrayInputStream(bArr)).readObject();
            return readObject.getObjectAt(readObject.getObjectAt(0) instanceof ASN1TaggedObject ? 3 : 2);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }

    public static X500Name getSubjectFields(X509Certificate x509Certificate) {
        if (x509Certificate == null) {
            return null;
        }
        try {
            return new X500Name(getSubject(x509Certificate.getTBSCertificate()));
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    public static ASN1Primitive getSubject(byte[] bArr) {
        try {
            ASN1Sequence readObject = new ASN1InputStream(new ByteArrayInputStream(bArr)).readObject();
            return readObject.getObjectAt(readObject.getObjectAt(0) instanceof ASN1TaggedObject ? 5 : 4);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }
}
