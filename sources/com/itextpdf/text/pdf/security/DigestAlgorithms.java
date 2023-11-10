package com.itextpdf.text.pdf.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;

public class DigestAlgorithms {
    public static final String RIPEMD160 = "RIPEMD160";
    public static final String SHA1 = "SHA-1";
    public static final String SHA256 = "SHA-256";
    public static final String SHA384 = "SHA-384";
    public static final String SHA512 = "SHA-512";
    private static final HashMap<String, String> allowedDigests;
    private static final HashMap<String, String> digestNames;
    private static final HashMap<String, String> fixNames;

    static {
        HashMap<String, String> hashMap = new HashMap<>();
        digestNames = hashMap;
        HashMap<String, String> hashMap2 = new HashMap<>();
        fixNames = hashMap2;
        HashMap<String, String> hashMap3 = new HashMap<>();
        allowedDigests = hashMap3;
        hashMap.put("1.2.840.113549.2.5", "MD5");
        hashMap.put("1.2.840.113549.2.2", "MD2");
        hashMap.put("1.3.14.3.2.26", SecurityConstants.SHA1);
        hashMap.put("2.16.840.1.101.3.4.2.4", "SHA224");
        hashMap.put("2.16.840.1.101.3.4.2.1", "SHA256");
        hashMap.put("2.16.840.1.101.3.4.2.2", "SHA384");
        hashMap.put("2.16.840.1.101.3.4.2.3", "SHA512");
        hashMap.put("1.3.36.3.2.2", "RIPEMD128");
        hashMap.put("1.3.36.3.2.1", RIPEMD160);
        hashMap.put("1.3.36.3.2.3", "RIPEMD256");
        hashMap.put("1.2.840.113549.1.1.4", "MD5");
        hashMap.put("1.2.840.113549.1.1.2", "MD2");
        hashMap.put("1.2.840.113549.1.1.5", SecurityConstants.SHA1);
        hashMap.put("1.2.840.113549.1.1.14", "SHA224");
        hashMap.put("1.2.840.113549.1.1.11", "SHA256");
        hashMap.put("1.2.840.113549.1.1.12", "SHA384");
        hashMap.put("1.2.840.113549.1.1.13", "SHA512");
        hashMap.put("1.2.840.113549.2.5", "MD5");
        hashMap.put("1.2.840.113549.2.2", "MD2");
        hashMap.put("1.2.840.10040.4.3", SecurityConstants.SHA1);
        hashMap.put("2.16.840.1.101.3.4.3.1", "SHA224");
        hashMap.put("2.16.840.1.101.3.4.3.2", "SHA256");
        hashMap.put("2.16.840.1.101.3.4.3.3", "SHA384");
        hashMap.put("2.16.840.1.101.3.4.3.4", "SHA512");
        hashMap.put("1.3.36.3.3.1.3", "RIPEMD128");
        hashMap.put("1.3.36.3.3.1.2", RIPEMD160);
        hashMap.put("1.3.36.3.3.1.4", "RIPEMD256");
        hashMap.put("1.2.643.2.2.9", "GOST3411");
        hashMap2.put("SHA256", "SHA-256");
        hashMap2.put("SHA384", SHA384);
        hashMap2.put("SHA512", SHA512);
        hashMap3.put("MD2", "1.2.840.113549.2.2");
        hashMap3.put("MD-2", "1.2.840.113549.2.2");
        hashMap3.put("MD5", "1.2.840.113549.2.5");
        hashMap3.put("MD-5", "1.2.840.113549.2.5");
        Object obj = "1.3.14.3.2.26";
        hashMap3.put(SecurityConstants.SHA1, obj);
        hashMap3.put(SHA1, obj);
        Object obj2 = "2.16.840.1.101.3.4.2.4";
        hashMap3.put("SHA224", obj2);
        hashMap3.put("SHA-224", obj2);
        Object obj3 = "2.16.840.1.101.3.4.2.1";
        hashMap3.put("SHA256", obj3);
        hashMap3.put("SHA-256", obj3);
        Object obj4 = "2.16.840.1.101.3.4.2.2";
        hashMap3.put("SHA384", obj4);
        hashMap3.put(SHA384, obj4);
        Object obj5 = "2.16.840.1.101.3.4.2.3";
        hashMap3.put("SHA512", obj5);
        hashMap3.put(SHA512, obj5);
        Object obj6 = "1.3.36.3.2.2";
        hashMap3.put("RIPEMD128", obj6);
        hashMap3.put("RIPEMD-128", obj6);
        Object obj7 = "1.3.36.3.2.1";
        hashMap3.put(RIPEMD160, obj7);
        hashMap3.put("RIPEMD-160", obj7);
        Object obj8 = "1.3.36.3.2.3";
        hashMap3.put("RIPEMD256", obj8);
        hashMap3.put("RIPEMD-256", obj8);
        hashMap3.put("GOST3411", "1.2.643.2.2.9");
    }

    public static MessageDigest getMessageDigestFromOid(String str, String str2) throws NoSuchAlgorithmException, NoSuchProviderException {
        return getMessageDigest(getDigest(str), str2);
    }

    public static MessageDigest getMessageDigest(String str, String str2) throws NoSuchAlgorithmException, NoSuchProviderException {
        if (str2 == null || str2.startsWith("SunPKCS11") || str2.startsWith("SunMSCAPI")) {
            return MessageDigest.getInstance(normalizeDigestName(str));
        }
        return MessageDigest.getInstance(str, str2);
    }

    public static byte[] digest(InputStream inputStream, String str, String str2) throws GeneralSecurityException, IOException {
        return digest(inputStream, getMessageDigest(str, str2));
    }

    public static byte[] digest(InputStream inputStream, MessageDigest messageDigest) throws GeneralSecurityException, IOException {
        byte[] bArr = new byte[8192];
        while (true) {
            int read = inputStream.read(bArr);
            if (read <= 0) {
                return messageDigest.digest();
            }
            messageDigest.update(bArr, 0, read);
        }
    }

    public static String getDigest(String str) {
        String str2 = digestNames.get(str);
        return str2 == null ? str : str2;
    }

    public static String normalizeDigestName(String str) {
        HashMap<String, String> hashMap = fixNames;
        return hashMap.containsKey(str) ? hashMap.get(str) : str;
    }

    public static String getAllowedDigests(String str) {
        return allowedDigests.get(str.toUpperCase());
    }
}
