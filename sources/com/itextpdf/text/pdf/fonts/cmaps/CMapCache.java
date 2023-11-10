package com.itextpdf.text.pdf.fonts.cmaps;

import java.io.IOException;
import java.util.HashMap;

public class CMapCache {
    private static final HashMap<String, CMapByteCid> cacheByteCid = new HashMap<>();
    private static final HashMap<String, CMapCidByte> cacheCidByte = new HashMap<>();
    private static final HashMap<String, CMapCidUni> cacheCidUni = new HashMap<>();
    private static final HashMap<String, CMapUniCid> cacheUniCid = new HashMap<>();

    public static CMapUniCid getCachedCMapUniCid(String str) throws IOException {
        CMapUniCid cMapUniCid;
        HashMap<String, CMapUniCid> hashMap = cacheUniCid;
        synchronized (hashMap) {
            cMapUniCid = hashMap.get(str);
        }
        if (cMapUniCid == null) {
            cMapUniCid = new CMapUniCid();
            CMapParserEx.parseCid(str, cMapUniCid, new CidResource());
            synchronized (hashMap) {
                hashMap.put(str, cMapUniCid);
            }
        }
        return cMapUniCid;
    }

    public static CMapCidUni getCachedCMapCidUni(String str) throws IOException {
        CMapCidUni cMapCidUni;
        HashMap<String, CMapCidUni> hashMap = cacheCidUni;
        synchronized (hashMap) {
            cMapCidUni = hashMap.get(str);
        }
        if (cMapCidUni == null) {
            cMapCidUni = new CMapCidUni();
            CMapParserEx.parseCid(str, cMapCidUni, new CidResource());
            synchronized (hashMap) {
                hashMap.put(str, cMapCidUni);
            }
        }
        return cMapCidUni;
    }

    public static CMapCidByte getCachedCMapCidByte(String str) throws IOException {
        CMapCidByte cMapCidByte;
        HashMap<String, CMapCidByte> hashMap = cacheCidByte;
        synchronized (hashMap) {
            cMapCidByte = hashMap.get(str);
        }
        if (cMapCidByte == null) {
            cMapCidByte = new CMapCidByte();
            CMapParserEx.parseCid(str, cMapCidByte, new CidResource());
            synchronized (hashMap) {
                hashMap.put(str, cMapCidByte);
            }
        }
        return cMapCidByte;
    }

    public static CMapByteCid getCachedCMapByteCid(String str) throws IOException {
        CMapByteCid cMapByteCid;
        HashMap<String, CMapByteCid> hashMap = cacheByteCid;
        synchronized (hashMap) {
            cMapByteCid = hashMap.get(str);
        }
        if (cMapByteCid == null) {
            cMapByteCid = new CMapByteCid();
            CMapParserEx.parseCid(str, cMapByteCid, new CidResource());
            synchronized (hashMap) {
                hashMap.put(str, cMapByteCid);
            }
        }
        return cMapByteCid;
    }
}
