package com.itextpdf.text.pdf.fonts.cmaps;

import java.io.IOException;

public class IdentityToUnicode {
    private static CMapToUnicode identityCNS;
    private static CMapToUnicode identityGB;
    private static CMapToUnicode identityH;
    private static CMapToUnicode identityJapan;
    private static CMapToUnicode identityKorea;

    public static CMapToUnicode GetMapFromOrdering(String str) throws IOException {
        if (str.equals("CNS1")) {
            if (identityCNS == null) {
                CMapUniCid cachedCMapUniCid = CMapCache.getCachedCMapUniCid("UniCNS-UTF16-H");
                if (cachedCMapUniCid == null) {
                    return null;
                }
                identityCNS = cachedCMapUniCid.exportToUnicode();
            }
            return identityCNS;
        } else if (str.equals("Japan1")) {
            if (identityJapan == null) {
                CMapUniCid cachedCMapUniCid2 = CMapCache.getCachedCMapUniCid("UniJIS-UTF16-H");
                if (cachedCMapUniCid2 == null) {
                    return null;
                }
                identityJapan = cachedCMapUniCid2.exportToUnicode();
            }
            return identityJapan;
        } else if (str.equals("Korea1")) {
            if (identityKorea == null) {
                CMapUniCid cachedCMapUniCid3 = CMapCache.getCachedCMapUniCid("UniKS-UTF16-H");
                if (cachedCMapUniCid3 == null) {
                    return null;
                }
                identityKorea = cachedCMapUniCid3.exportToUnicode();
            }
            return identityKorea;
        } else if (str.equals("GB1")) {
            if (identityGB == null) {
                CMapUniCid cachedCMapUniCid4 = CMapCache.getCachedCMapUniCid("UniGB-UTF16-H");
                if (cachedCMapUniCid4 == null) {
                    return null;
                }
                identityGB = cachedCMapUniCid4.exportToUnicode();
            }
            return identityGB;
        } else if (!str.equals("Identity")) {
            return null;
        } else {
            if (identityH == null) {
                identityH = CMapToUnicode.getIdentity();
            }
            return identityH;
        }
    }
}
