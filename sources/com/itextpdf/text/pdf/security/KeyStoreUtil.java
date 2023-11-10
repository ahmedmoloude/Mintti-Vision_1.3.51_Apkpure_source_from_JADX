package com.itextpdf.text.pdf.security;

import java.security.KeyStore;

public class KeyStoreUtil {
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0046 A[SYNTHETIC, Splitter:B:23:0x0046] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.security.KeyStore loadCacertsKeyStore(java.lang.String r3) {
        /*
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "java.home"
            java.lang.String r1 = java.lang.System.getProperty(r1)
            java.lang.String r2 = "lib"
            r0.<init>(r1, r2)
            java.io.File r1 = new java.io.File
            java.lang.String r2 = "security"
            r1.<init>(r0, r2)
            java.io.File r0 = new java.io.File
            java.lang.String r2 = "cacerts"
            r0.<init>(r1, r2)
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x003d }
            r2.<init>(r0)     // Catch:{ Exception -> 0x003d }
            java.lang.String r0 = "JKS"
            if (r3 != 0) goto L_0x002a
            java.security.KeyStore r3 = java.security.KeyStore.getInstance(r0)     // Catch:{ Exception -> 0x0038, all -> 0x0035 }
            goto L_0x002e
        L_0x002a:
            java.security.KeyStore r3 = java.security.KeyStore.getInstance(r0, r3)     // Catch:{ Exception -> 0x0038, all -> 0x0035 }
        L_0x002e:
            r3.load(r2, r1)     // Catch:{ Exception -> 0x0038, all -> 0x0035 }
            r2.close()     // Catch:{ Exception -> 0x0034 }
        L_0x0034:
            return r3
        L_0x0035:
            r3 = move-exception
            r1 = r2
            goto L_0x0044
        L_0x0038:
            r3 = move-exception
            r1 = r2
            goto L_0x003e
        L_0x003b:
            r3 = move-exception
            goto L_0x0044
        L_0x003d:
            r3 = move-exception
        L_0x003e:
            com.itextpdf.text.ExceptionConverter r0 = new com.itextpdf.text.ExceptionConverter     // Catch:{ all -> 0x003b }
            r0.<init>(r3)     // Catch:{ all -> 0x003b }
            throw r0     // Catch:{ all -> 0x003b }
        L_0x0044:
            if (r1 == 0) goto L_0x0049
            r1.close()     // Catch:{ Exception -> 0x0049 }
        L_0x0049:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.pdf.security.KeyStoreUtil.loadCacertsKeyStore(java.lang.String):java.security.KeyStore");
    }

    public static KeyStore loadCacertsKeyStore() {
        return loadCacertsKeyStore((String) null);
    }
}
