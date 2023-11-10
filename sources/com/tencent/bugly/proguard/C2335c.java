package com.tencent.bugly.proguard;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* renamed from: com.tencent.bugly.proguard.c */
/* compiled from: BUGLY */
class C2335c {

    /* renamed from: a */
    protected HashMap<String, HashMap<String, byte[]>> f1872a = new HashMap<>();

    /* renamed from: b */
    protected HashMap<String, Object> f1873b = new HashMap<>();

    /* renamed from: c */
    protected String f1874c = "GBK";

    /* renamed from: d */
    C2343k f1875d = new C2343k();

    /* renamed from: e */
    private HashMap<String, Object> f1876e = new HashMap<>();

    C2335c() {
    }

    /* renamed from: a */
    public void mo28094a(String str) {
        this.f1874c = str;
    }

    /* renamed from: a */
    public <T> void mo28095a(String str, T t) {
        if (str == null) {
            throw new IllegalArgumentException("put key can not is null");
        } else if (t == null) {
            throw new IllegalArgumentException("put value can not is null");
        } else if (!(t instanceof Set)) {
            C2345l lVar = new C2345l();
            lVar.mo28126a(this.f1874c);
            lVar.mo28131a((Object) t, 0);
            byte[] a = C2347n.m965a(lVar.f1905a);
            HashMap hashMap = new HashMap(1);
            ArrayList arrayList = new ArrayList(1);
            m860a((ArrayList<String>) arrayList, (Object) t);
            hashMap.put(C2230a.m450a(arrayList), a);
            this.f1876e.remove(str);
            this.f1872a.put(str, hashMap);
        } else {
            throw new IllegalArgumentException("can not support Set");
        }
    }

    /* renamed from: a */
    private static void m860a(ArrayList<String> arrayList, Object obj) {
        while (true) {
            if (obj.getClass().isArray()) {
                if (!obj.getClass().getComponentType().toString().equals("byte")) {
                    throw new IllegalArgumentException("only byte[] is supported");
                } else if (Array.getLength(obj) > 0) {
                    arrayList.add("java.util.List");
                    obj = Array.get(obj, 0);
                } else {
                    arrayList.add("Array");
                    arrayList.add("?");
                    return;
                }
            } else if (obj instanceof Array) {
                throw new IllegalArgumentException("can not support Array, please use List");
            } else if (obj instanceof List) {
                arrayList.add("java.util.List");
                List list = (List) obj;
                if (list.size() > 0) {
                    obj = list.get(0);
                } else {
                    arrayList.add("?");
                    return;
                }
            } else if (obj instanceof Map) {
                arrayList.add("java.util.Map");
                Map map = (Map) obj;
                if (map.size() > 0) {
                    Object next = map.keySet().iterator().next();
                    obj = map.get(next);
                    arrayList.add(next.getClass().getName());
                } else {
                    arrayList.add("?");
                    arrayList.add("?");
                    return;
                }
            } else {
                arrayList.add(obj.getClass().getName());
                return;
            }
        }
    }

    /* renamed from: a */
    public byte[] mo28097a() {
        C2345l lVar = new C2345l(0);
        lVar.mo28126a(this.f1874c);
        lVar.mo28134a(this.f1872a, 0);
        return C2347n.m965a(lVar.f1905a);
    }

    /* renamed from: a */
    public void mo28096a(byte[] bArr) {
        this.f1875d.mo28122a(bArr);
        this.f1875d.mo28116a(this.f1874c);
        HashMap hashMap = new HashMap(1);
        HashMap hashMap2 = new HashMap(1);
        hashMap2.put("", new byte[0]);
        hashMap.put("", hashMap2);
        this.f1872a = this.f1875d.mo28120a(hashMap, 0, false);
    }
}
