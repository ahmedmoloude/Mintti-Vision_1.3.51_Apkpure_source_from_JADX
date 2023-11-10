package com.itextpdf.text.pdf.security;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.error_messages.MessageLocalization;
import com.itextpdf.text.pdf.XmlSignatureAppearance;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.crypto.dsig.spec.XPathFilter2ParameterSpec;
import javax.xml.crypto.dsig.spec.XPathType;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.jcp.xml.dsig.internal.dom.DOMKeyInfoFactory;
import org.apache.jcp.xml.dsig.internal.dom.DOMReference;
import org.apache.jcp.xml.dsig.internal.dom.DOMSignedInfo;
import org.apache.jcp.xml.dsig.internal.dom.DOMUtils;
import org.apache.jcp.xml.dsig.internal.dom.DOMXMLSignature;
import org.apache.jcp.xml.dsig.internal.dom.XMLDSigRI;
import org.apache.xml.security.utils.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MakeXmlSignature {

    private static class EmptyKey implements Key {
        private static EmptyKey instance = new EmptyKey();

        public String getAlgorithm() {
            return null;
        }

        public byte[] getEncoded() {
            return new byte[0];
        }

        public String getFormat() {
            return null;
        }

        private EmptyKey() {
        }

        public static EmptyKey getInstance() {
            return instance;
        }
    }

    public static void signXmlDSig(XmlSignatureAppearance xmlSignatureAppearance, ExternalSignature externalSignature, KeyInfo keyInfo) throws GeneralSecurityException, IOException, DocumentException {
        String str;
        verifyArguments(xmlSignatureAppearance, externalSignature);
        XMLSignatureFactory createSignatureFactory = createSignatureFactory();
        Reference generateContentReference = generateContentReference(createSignatureFactory, xmlSignatureAppearance, (String) null);
        if (externalSignature.getEncryptionAlgorithm().equals(SecurityConstants.RSA)) {
            str = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
        } else {
            str = externalSignature.getEncryptionAlgorithm().equals(SecurityConstants.DSA) ? "http://www.w3.org/2000/09/xmldsig#dsa-sha1" : null;
        }
        sign(createSignatureFactory, externalSignature, xmlSignatureAppearance.getXmlLocator(), createSignatureFactory.newSignedInfo(createSignatureFactory.newCanonicalizationMethod("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", (C14NMethodParameterSpec) null), createSignatureFactory.newSignatureMethod(str, (SignatureMethodParameterSpec) null), Collections.singletonList(generateContentReference)), (XMLObject) null, keyInfo, (String) null);
        xmlSignatureAppearance.close();
    }

    public static void signXmlDSig(XmlSignatureAppearance xmlSignatureAppearance, ExternalSignature externalSignature, Certificate[] certificateArr) throws DocumentException, GeneralSecurityException, IOException {
        signXmlDSig(xmlSignatureAppearance, externalSignature, generateKeyInfo(certificateArr, xmlSignatureAppearance));
    }

    public static void signXmlDSig(XmlSignatureAppearance xmlSignatureAppearance, ExternalSignature externalSignature, PublicKey publicKey) throws GeneralSecurityException, DocumentException, IOException {
        signXmlDSig(xmlSignatureAppearance, externalSignature, generateKeyInfo(publicKey));
    }

    public static void signXades(XmlSignatureAppearance xmlSignatureAppearance, ExternalSignature externalSignature, Certificate[] certificateArr, boolean z) throws GeneralSecurityException, DocumentException, IOException {
        String str;
        String[] strArr;
        XmlSignatureAppearance xmlSignatureAppearance2 = xmlSignatureAppearance;
        verifyArguments(xmlSignatureAppearance, externalSignature);
        if (externalSignature.getEncryptionAlgorithm().equals(SecurityConstants.RSA)) {
            str = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
        } else {
            str = externalSignature.getEncryptionAlgorithm().equals(SecurityConstants.DSA) ? "http://www.w3.org/2000/09/xmldsig#dsa-sha1" : null;
        }
        String str2 = SecurityConstants.Reference_ + getRandomId();
        String str3 = SecurityConstants.SignedProperties_ + getRandomId();
        String str4 = SecurityConstants.Signature_ + getRandomId();
        XMLSignatureFactory createSignatureFactory = createSignatureFactory();
        KeyInfo generateKeyInfo = generateKeyInfo(certificateArr, xmlSignatureAppearance2);
        if (z) {
            String[] strArr2 = new String[2];
            if (str.equals("http://www.w3.org/2000/09/xmldsig#rsa-sha1")) {
                strArr2[0] = SecurityConstants.OID_RSA_SHA1;
                strArr2[1] = SecurityConstants.OID_RSA_SHA1_DESC;
            } else {
                strArr2[0] = SecurityConstants.OID_DSA_SHA1;
                strArr2[1] = SecurityConstants.OID_DSA_SHA1_DESC;
            }
            strArr = strArr2;
        } else {
            strArr = null;
        }
        ExternalSignature externalSignature2 = externalSignature;
        sign(createSignatureFactory, externalSignature2, xmlSignatureAppearance.getXmlLocator(), createSignatureFactory.newSignedInfo(createSignatureFactory.newCanonicalizationMethod("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", (C14NMethodParameterSpec) null), createSignatureFactory.newSignatureMethod(str, (SignatureMethodParameterSpec) null), Arrays.asList(new Reference[]{generateCustomReference(createSignatureFactory, "#" + str3, SecurityConstants.SignedProperties_Type, (String) null), generateContentReference(createSignatureFactory, xmlSignatureAppearance2, str2)}), (String) null), generateXadesObject(createSignatureFactory, xmlSignatureAppearance, str4, str2, str3, strArr), generateKeyInfo, str4);
        xmlSignatureAppearance.close();
    }

    public static void signXadesBes(XmlSignatureAppearance xmlSignatureAppearance, ExternalSignature externalSignature, Certificate[] certificateArr) throws GeneralSecurityException, DocumentException, IOException {
        signXades(xmlSignatureAppearance, externalSignature, certificateArr, false);
    }

    public static void signXadesEpes(XmlSignatureAppearance xmlSignatureAppearance, ExternalSignature externalSignature, Certificate[] certificateArr) throws GeneralSecurityException, DocumentException, IOException {
        signXades(xmlSignatureAppearance, externalSignature, certificateArr, true);
    }

    private static void verifyArguments(XmlSignatureAppearance xmlSignatureAppearance, ExternalSignature externalSignature) throws DocumentException {
        if (xmlSignatureAppearance.getXmlLocator() == null) {
            throw new DocumentException(MessageLocalization.getComposedMessage("xmllocator.cannot.be.null", new Object[0]));
        } else if (!externalSignature.getHashAlgorithm().equals(SecurityConstants.SHA1)) {
            throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("support.only.sha1.hash.algorithm", new Object[0]));
        } else if (!externalSignature.getEncryptionAlgorithm().equals(SecurityConstants.RSA) && !externalSignature.getEncryptionAlgorithm().equals(SecurityConstants.DSA)) {
            throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("support.only.rsa.and.dsa.algorithms", new Object[0]));
        }
    }

    private static Element findElement(NodeList nodeList, String str) {
        for (int length = nodeList.getLength() - 1; length >= 0; length--) {
            Node item = nodeList.item(length);
            if (item.getNodeType() == 1 && item.getLocalName().equals(str)) {
                return (Element) item;
            }
        }
        return null;
    }

    private static KeyInfo generateKeyInfo(Certificate[] certificateArr, XmlSignatureAppearance xmlSignatureAppearance) {
        Certificate certificate = certificateArr[0];
        xmlSignatureAppearance.setCertificate(certificate);
        DOMKeyInfoFactory dOMKeyInfoFactory = new DOMKeyInfoFactory();
        return dOMKeyInfoFactory.newKeyInfo(Collections.singletonList(dOMKeyInfoFactory.newX509Data(Collections.singletonList(certificate))));
    }

    private static KeyInfo generateKeyInfo(PublicKey publicKey) throws GeneralSecurityException {
        DOMKeyInfoFactory dOMKeyInfoFactory = new DOMKeyInfoFactory();
        return dOMKeyInfoFactory.newKeyInfo(Collections.singletonList(dOMKeyInfoFactory.newKeyValue(publicKey)));
    }

    private static String getRandomId() {
        return UUID.randomUUID().toString().substring(24);
    }

    private static XMLSignatureFactory createSignatureFactory() {
        return XMLSignatureFactory.getInstance("DOM", new XMLDSigRI());
    }

    private static XMLObject generateXadesObject(XMLSignatureFactory xMLSignatureFactory, XmlSignatureAppearance xmlSignatureAppearance, String str, String str2, String str3, String[] strArr) throws GeneralSecurityException {
        Element element;
        MessageDigest instance = MessageDigest.getInstance(SecurityConstants.SHA1);
        Certificate certificate = xmlSignatureAppearance.getCertificate();
        Document document = xmlSignatureAppearance.getXmlLocator().getDocument();
        Element createElementNS = document.createElementNS(SecurityConstants.XADES_132_URI, SecurityConstants.XADES_QualifyingProperties);
        createElementNS.setAttribute(SecurityConstants.Target, "#" + str);
        Element createElementNS2 = document.createElementNS(SecurityConstants.XADES_132_URI, SecurityConstants.XADES_SignedProperties);
        createElementNS2.setAttribute(SecurityConstants.f778Id, str3);
        createElementNS2.setIdAttribute(SecurityConstants.f778Id, true);
        Element createElementNS3 = document.createElementNS(SecurityConstants.XADES_132_URI, SecurityConstants.XADES_SignedSignatureProperties);
        Element createElementNS4 = document.createElementNS(SecurityConstants.XADES_132_URI, SecurityConstants.XADES_SigningTime);
        String format = new SimpleDateFormat(SecurityConstants.SigningTimeFormat).format(xmlSignatureAppearance.getSignDate().getTime());
        createElementNS4.appendChild(document.createTextNode(format.substring(0, format.length() - 2).concat(":").concat(format.substring(format.length() - 2))));
        createElementNS3.appendChild(createElementNS4);
        Element createElementNS5 = document.createElementNS(SecurityConstants.XADES_132_URI, SecurityConstants.XADES_SigningCertificate);
        Element createElementNS6 = document.createElementNS(SecurityConstants.XADES_132_URI, SecurityConstants.XADES_Cert);
        Element createElementNS7 = document.createElementNS(SecurityConstants.XADES_132_URI, SecurityConstants.XADES_CertDigest);
        Element createElementNS8 = document.createElementNS(SecurityConstants.XMLDSIG_URI, SecurityConstants.DigestMethod);
        createElementNS8.setAttribute(SecurityConstants.Algorithm, SecurityConstants.SHA1_URI);
        createElementNS7.appendChild(createElementNS8);
        Element element2 = createElementNS;
        Element createElementNS9 = document.createElementNS(SecurityConstants.XMLDSIG_URI, SecurityConstants.DigestValue);
        String str4 = "#";
        createElementNS9.appendChild(document.createTextNode(Base64.encode(instance.digest(certificate.getEncoded()))));
        createElementNS7.appendChild(createElementNS9);
        createElementNS6.appendChild(createElementNS7);
        if (certificate instanceof X509Certificate) {
            Element createElementNS10 = document.createElementNS(SecurityConstants.XADES_132_URI, SecurityConstants.XADES_IssuerSerial);
            Element createElementNS11 = document.createElementNS(SecurityConstants.XMLDSIG_URI, SecurityConstants.X509IssuerName);
            X509Certificate x509Certificate = (X509Certificate) certificate;
            createElementNS11.appendChild(document.createTextNode(getX509IssuerName(x509Certificate)));
            createElementNS10.appendChild(createElementNS11);
            Element createElementNS12 = document.createElementNS(SecurityConstants.XMLDSIG_URI, SecurityConstants.X509SerialNumber);
            createElementNS12.appendChild(document.createTextNode(getX509SerialNumber(x509Certificate)));
            createElementNS10.appendChild(createElementNS12);
            createElementNS6.appendChild(createElementNS10);
        }
        createElementNS5.appendChild(createElementNS6);
        createElementNS3.appendChild(createElementNS5);
        if (strArr != null) {
            Element createElementNS13 = document.createElementNS(SecurityConstants.XADES_132_URI, SecurityConstants.XADES_SignaturePolicyIdentifier);
            Element createElementNS14 = document.createElementNS(SecurityConstants.XADES_132_URI, SecurityConstants.XADES_SignaturePolicyId);
            Element createElementNS15 = document.createElementNS(SecurityConstants.XADES_132_URI, SecurityConstants.XADES_SigPolicyId);
            Element createElementNS16 = document.createElementNS(SecurityConstants.XADES_132_URI, SecurityConstants.XADES_Identifier);
            createElementNS16.appendChild(document.createTextNode(strArr[0]));
            Element element3 = createElementNS2;
            createElementNS16.setAttribute(SecurityConstants.Qualifier, SecurityConstants.OIDAsURN);
            createElementNS15.appendChild(createElementNS16);
            Element createElementNS17 = document.createElementNS(SecurityConstants.XADES_132_URI, SecurityConstants.XADES_Description);
            createElementNS17.appendChild(document.createTextNode(strArr[1]));
            createElementNS15.appendChild(createElementNS17);
            createElementNS14.appendChild(createElementNS15);
            Element createElementNS18 = document.createElementNS(SecurityConstants.XADES_132_URI, SecurityConstants.XADES_SigPolicyHash);
            Element createElementNS19 = document.createElementNS(SecurityConstants.XMLDSIG_URI, SecurityConstants.DigestMethod);
            createElementNS19.setAttribute(SecurityConstants.Algorithm, SecurityConstants.SHA1_URI);
            createElementNS18.appendChild(createElementNS19);
            Element createElementNS20 = document.createElementNS(SecurityConstants.XMLDSIG_URI, SecurityConstants.DigestValue);
            createElementNS20.appendChild(document.createTextNode(Base64.encode(instance.digest(getByteArrayOfNode(createElementNS15)))));
            createElementNS18.appendChild(createElementNS20);
            createElementNS14.appendChild(createElementNS18);
            createElementNS13.appendChild(createElementNS14);
            createElementNS3.appendChild(createElementNS13);
            element = element3;
        } else {
            element = createElementNS2;
        }
        element.appendChild(createElementNS3);
        Element createElement = document.createElement(SecurityConstants.XADES_SignedDataObjectProperties);
        Element createElement2 = document.createElement(SecurityConstants.XADES_DataObjectFormat);
        createElement2.setAttribute(SecurityConstants.ObjectReference, str4 + str2);
        String description = xmlSignatureAppearance.getDescription();
        if (description != null) {
            Element createElement3 = document.createElement(SecurityConstants.XADES_Description);
            createElement3.appendChild(document.createTextNode(description));
            createElement2.appendChild(createElement3);
        }
        Element createElement4 = document.createElement(SecurityConstants.XADES_MimeType);
        createElement4.appendChild(document.createTextNode(xmlSignatureAppearance.getMimeType()));
        createElement2.appendChild(createElement4);
        String encoding = xmlSignatureAppearance.getXmlLocator().getEncoding();
        if (encoding != null) {
            Element createElement5 = document.createElement(SecurityConstants.XADES_Encoding);
            createElement5.appendChild(document.createTextNode(encoding));
            createElement2.appendChild(createElement5);
        }
        createElement.appendChild(createElement2);
        element.appendChild(createElement);
        Element element4 = element2;
        element4.appendChild(element);
        return xMLSignatureFactory.newXMLObject(Collections.singletonList(new DOMStructure(element4)), (String) null, (String) null, (String) null);
    }

    private static String getX509IssuerName(X509Certificate x509Certificate) {
        return x509Certificate.getIssuerX500Principal().toString();
    }

    private static String getX509SerialNumber(X509Certificate x509Certificate) {
        return x509Certificate.getSerialNumber().toString();
    }

    private static Reference generateContentReference(XMLSignatureFactory xMLSignatureFactory, XmlSignatureAppearance xmlSignatureAppearance, String str) throws GeneralSecurityException {
        DigestMethod newDigestMethod = xMLSignatureFactory.newDigestMethod(SecurityConstants.SHA1_URI, (DigestMethodParameterSpec) null);
        ArrayList arrayList = new ArrayList();
        arrayList.add(xMLSignatureFactory.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature", (TransformParameterSpec) null));
        XpathConstructor xpathConstructor = xmlSignatureAppearance.getXpathConstructor();
        if (xpathConstructor != null && xpathConstructor.getXpathExpression().length() > 0) {
            arrayList.add(xMLSignatureFactory.newTransform("http://www.w3.org/2002/06/xmldsig-filter2", new XPathFilter2ParameterSpec(Collections.singletonList(new XPathType(xpathConstructor.getXpathExpression(), XPathType.Filter.INTERSECT)))));
        }
        return xMLSignatureFactory.newReference("", newDigestMethod, arrayList, (String) null, str);
    }

    private static Reference generateCustomReference(XMLSignatureFactory xMLSignatureFactory, String str, String str2, String str3) throws GeneralSecurityException {
        return xMLSignatureFactory.newReference(str, xMLSignatureFactory.newDigestMethod(SecurityConstants.SHA1_URI, (DigestMethodParameterSpec) null), (List) null, str2, str3);
    }

    private static void sign(XMLSignatureFactory xMLSignatureFactory, ExternalSignature externalSignature, XmlLocator xmlLocator, DOMSignedInfo dOMSignedInfo, XMLObject xMLObject, KeyInfo keyInfo, String str) throws DocumentException {
        Document document = xmlLocator.getDocument();
        DOMSignContext dOMSignContext = new DOMSignContext(EmptyKey.getInstance(), document.getDocumentElement());
        DOMXMLSignature newXMLSignature = xMLSignatureFactory.newXMLSignature(dOMSignedInfo, keyInfo, xMLObject != null ? Collections.singletonList(xMLObject) : null, str, (String) null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            newXMLSignature.marshal(dOMSignContext.getParent(), dOMSignContext.getNextSibling(), DOMUtils.getSignaturePrefix(dOMSignContext), dOMSignContext);
            Element findElement = findElement(document.getDocumentElement().getChildNodes(), SecurityConstants.Signature);
            if (str != null) {
                findElement.setAttributeNS(SecurityConstants.XMLNS_URI, SecurityConstants.XMLNS_XADES, SecurityConstants.XADES_132_URI);
            }
            List references = dOMSignedInfo.getReferences();
            for (int i = 0; i < references.size(); i++) {
                ((DOMReference) references.get(i)).digest(dOMSignContext);
            }
            dOMSignedInfo.canonicalize(dOMSignContext, byteArrayOutputStream);
            findElement(findElement.getChildNodes(), SecurityConstants.SignatureValue).appendChild(document.createTextNode(Base64.encode(externalSignature.sign(byteArrayOutputStream.toByteArray()))));
            xmlLocator.setDocument(document);
        } catch (Exception e) {
            throw new DocumentException(e);
        }
    }

    private static byte[] getByteArrayOfNode(Node node) {
        try {
            StreamResult streamResult = new StreamResult(new StringWriter());
            Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
            newTransformer.setOutputProperty("omit-xml-declaration", "yes");
            newTransformer.transform(new DOMSource(node), streamResult);
            return streamResult.getWriter().toString().getBytes();
        } catch (Exception unused) {
            return new ByteArrayOutputStream().toByteArray();
        }
    }
}
