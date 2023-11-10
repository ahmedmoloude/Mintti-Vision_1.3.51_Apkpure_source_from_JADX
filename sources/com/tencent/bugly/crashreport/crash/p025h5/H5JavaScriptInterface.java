package com.tencent.bugly.crashreport.crash.p025h5;

import android.webkit.JavascriptInterface;
import com.itextpdf.text.Annotation;
import com.itextpdf.text.xml.xmp.DublinCoreProperties;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.inner.InnerApi;
import com.tencent.bugly.proguard.C2265al;
import com.tencent.bugly.proguard.C2273ap;
import com.tencent.bugly.proguard.C2313bb;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: com.tencent.bugly.crashreport.crash.h5.H5JavaScriptInterface */
/* compiled from: BUGLY */
public class H5JavaScriptInterface {

    /* renamed from: a */
    private static HashSet<Integer> f1398a = new HashSet<>();

    /* renamed from: b */
    private String f1399b = null;

    /* renamed from: c */
    private Thread f1400c = null;

    /* renamed from: d */
    private String f1401d = null;

    /* renamed from: e */
    private Map<String, String> f1402e = null;

    private H5JavaScriptInterface() {
    }

    public static H5JavaScriptInterface getInstance(CrashReport.C2220a aVar) {
        String str = null;
        if (aVar == null || f1398a.contains(Integer.valueOf(aVar.hashCode()))) {
            return null;
        }
        H5JavaScriptInterface h5JavaScriptInterface = new H5JavaScriptInterface();
        f1398a.add(Integer.valueOf(aVar.hashCode()));
        Thread currentThread = Thread.currentThread();
        h5JavaScriptInterface.f1400c = currentThread;
        if (currentThread != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            for (int i = 2; i < currentThread.getStackTrace().length; i++) {
                StackTraceElement stackTraceElement = currentThread.getStackTrace()[i];
                if (!stackTraceElement.toString().contains("crashreport")) {
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
            }
            str = sb.toString();
        }
        h5JavaScriptInterface.f1401d = str;
        HashMap hashMap = new HashMap();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(aVar.mo27891c());
        hashMap.put("[WebView] ContentDescription", sb2.toString());
        h5JavaScriptInterface.f1402e = hashMap;
        return h5JavaScriptInterface;
    }

    /* renamed from: a */
    private static C2313bb m436a(String str) {
        if (str != null && str.length() > 0) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                C2313bb bbVar = new C2313bb();
                bbVar.f1726a = jSONObject.getString("projectRoot");
                if (bbVar.f1726a == null) {
                    return null;
                }
                bbVar.f1727b = jSONObject.getString("context");
                if (bbVar.f1727b == null) {
                    return null;
                }
                bbVar.f1728c = jSONObject.getString(Annotation.URL);
                if (bbVar.f1728c == null) {
                    return null;
                }
                bbVar.f1729d = jSONObject.getString("userAgent");
                if (bbVar.f1729d == null) {
                    return null;
                }
                bbVar.f1730e = jSONObject.getString(DublinCoreProperties.LANGUAGE);
                if (bbVar.f1730e == null) {
                    return null;
                }
                bbVar.f1731f = jSONObject.getString("name");
                if (bbVar.f1731f != null) {
                    if (!bbVar.f1731f.equals("null")) {
                        String string = jSONObject.getString("stacktrace");
                        if (string == null) {
                            return null;
                        }
                        int indexOf = string.indexOf("\n");
                        if (indexOf < 0) {
                            C2265al.m610d("H5 crash stack's format is wrong!", new Object[0]);
                            return null;
                        }
                        bbVar.f1733h = string.substring(indexOf + 1);
                        bbVar.f1732g = string.substring(0, indexOf);
                        int indexOf2 = bbVar.f1732g.indexOf(":");
                        if (indexOf2 > 0) {
                            bbVar.f1732g = bbVar.f1732g.substring(indexOf2 + 1);
                        }
                        bbVar.f1734i = jSONObject.getString(Annotation.FILE);
                        if (bbVar.f1731f == null) {
                            return null;
                        }
                        bbVar.f1735j = jSONObject.getLong("lineNumber");
                        if (bbVar.f1735j < 0) {
                            return null;
                        }
                        bbVar.f1736k = jSONObject.getLong("columnNumber");
                        if (bbVar.f1736k < 0) {
                            return null;
                        }
                        C2265al.m604a("H5 crash information is following: ", new Object[0]);
                        C2265al.m604a("[projectRoot]: " + bbVar.f1726a, new Object[0]);
                        C2265al.m604a("[context]: " + bbVar.f1727b, new Object[0]);
                        C2265al.m604a("[url]: " + bbVar.f1728c, new Object[0]);
                        C2265al.m604a("[userAgent]: " + bbVar.f1729d, new Object[0]);
                        C2265al.m604a("[language]: " + bbVar.f1730e, new Object[0]);
                        C2265al.m604a("[name]: " + bbVar.f1731f, new Object[0]);
                        C2265al.m604a("[message]: " + bbVar.f1732g, new Object[0]);
                        C2265al.m604a("[stacktrace]: \n" + bbVar.f1733h, new Object[0]);
                        C2265al.m604a("[file]: " + bbVar.f1734i, new Object[0]);
                        C2265al.m604a("[lineNumber]: " + bbVar.f1735j, new Object[0]);
                        C2265al.m604a("[columnNumber]: " + bbVar.f1736k, new Object[0]);
                        return bbVar;
                    }
                }
                return null;
            } catch (Throwable th) {
                if (!C2265al.m605a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    @JavascriptInterface
    public void printLog(String str) {
        C2265al.m610d("Log from js: %s", str);
    }

    @JavascriptInterface
    public void reportJSException(String str) {
        if (str == null) {
            C2265al.m610d("Payload from JS is null.", new Object[0]);
            return;
        }
        String c = C2273ap.m671c(str.getBytes());
        String str2 = this.f1399b;
        if (str2 == null || !str2.equals(c)) {
            this.f1399b = c;
            C2265al.m610d("Handling JS exception ...", new Object[0]);
            C2313bb a = m436a(str);
            if (a == null) {
                C2265al.m610d("Failed to parse payload.", new Object[0]);
                return;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            if (a.f1726a != null) {
                linkedHashMap2.put("[JS] projectRoot", a.f1726a);
            }
            if (a.f1727b != null) {
                linkedHashMap2.put("[JS] context", a.f1727b);
            }
            if (a.f1728c != null) {
                linkedHashMap2.put("[JS] url", a.f1728c);
            }
            if (a.f1729d != null) {
                linkedHashMap2.put("[JS] userAgent", a.f1729d);
            }
            if (a.f1734i != null) {
                linkedHashMap2.put("[JS] file", a.f1734i);
            }
            if (a.f1735j != 0) {
                linkedHashMap2.put("[JS] lineNumber", Long.toString(a.f1735j));
            }
            linkedHashMap.putAll(linkedHashMap2);
            linkedHashMap.putAll(this.f1402e);
            linkedHashMap.put("Java Stack", this.f1401d);
            Thread thread = this.f1400c;
            if (a != null) {
                InnerApi.postH5CrashAsync(thread, a.f1731f, a.f1732g, a.f1733h, linkedHashMap);
                return;
            }
            return;
        }
        C2265al.m610d("Same payload from js. Please check whether you've injected bugly.js more than one times.", new Object[0]);
    }
}
