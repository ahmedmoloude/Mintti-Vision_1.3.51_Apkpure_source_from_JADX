package com.orhanobut.logger;

import androidx.exifinterface.media.ExifInterface;
import com.itextpdf.text.html.HtmlTags;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class LoggerPrinter implements Printer {
    private static final int JSON_INDENT = 2;
    private final ThreadLocal<String> localTag = new ThreadLocal<>();
    private final List<LogAdapter> logAdapters = new ArrayList();

    LoggerPrinter() {
    }

    /* renamed from: t */
    public Printer mo27703t(String str) {
        if (str != null) {
            this.localTag.set(str);
        }
        return this;
    }

    /* renamed from: d */
    public void mo27697d(String str, Object... objArr) {
        log(3, (Throwable) null, str, objArr);
    }

    /* renamed from: d */
    public void mo27696d(Object obj) {
        log(3, (Throwable) null, Utils.toString(obj), new Object[0]);
    }

    /* renamed from: e */
    public void mo27698e(String str, Object... objArr) {
        mo27699e((Throwable) null, str, objArr);
    }

    /* renamed from: e */
    public void mo27699e(Throwable th, String str, Object... objArr) {
        log(6, th, str, objArr);
    }

    /* renamed from: w */
    public void mo27705w(String str, Object... objArr) {
        log(5, (Throwable) null, str, objArr);
    }

    /* renamed from: i */
    public void mo27700i(String str, Object... objArr) {
        log(4, (Throwable) null, str, objArr);
    }

    /* renamed from: v */
    public void mo27704v(String str, Object... objArr) {
        log(2, (Throwable) null, str, objArr);
    }

    public void wtf(String str, Object... objArr) {
        log(7, (Throwable) null, str, objArr);
    }

    public void json(String str) {
        if (Utils.isEmpty(str)) {
            mo27696d("Empty/Null json content");
            return;
        }
        try {
            String trim = str.trim();
            if (trim.startsWith("{")) {
                mo27696d(new JSONObject(trim).toString(2));
            } else if (trim.startsWith("[")) {
                mo27696d(new JSONArray(trim).toString(2));
            } else {
                mo27698e("Invalid Json", new Object[0]);
            }
        } catch (JSONException unused) {
            mo27698e("Invalid Json", new Object[0]);
        }
    }

    public void xml(String str) {
        if (Utils.isEmpty(str)) {
            mo27696d("Empty/Null xml content");
            return;
        }
        try {
            StreamSource streamSource = new StreamSource(new StringReader(str));
            StreamResult streamResult = new StreamResult(new StringWriter());
            Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
            newTransformer.setOutputProperty(HtmlTags.INDENT, "yes");
            newTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", ExifInterface.GPS_MEASUREMENT_2D);
            newTransformer.transform(streamSource, streamResult);
            mo27696d(streamResult.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException unused) {
            mo27698e("Invalid xml", new Object[0]);
        }
    }

    public synchronized void log(int i, String str, String str2, Throwable th) {
        if (!(th == null || str2 == null)) {
            str2 = str2 + " : " + Utils.getStackTraceString(th);
        }
        if (th != null && str2 == null) {
            str2 = Utils.getStackTraceString(th);
        }
        if (Utils.isEmpty(str2)) {
            str2 = "Empty/NULL log message";
        }
        for (LogAdapter next : this.logAdapters) {
            if (next.isLoggable(i, str)) {
                next.log(i, str, str2);
            }
        }
    }

    public void clearLogAdapters() {
        this.logAdapters.clear();
    }

    public void addAdapter(LogAdapter logAdapter) {
        this.logAdapters.add(Utils.checkNotNull(logAdapter));
    }

    private synchronized void log(int i, Throwable th, String str, Object... objArr) {
        Utils.checkNotNull(str);
        log(i, getTag(), createMessage(str, objArr), th);
    }

    private String getTag() {
        String str = this.localTag.get();
        if (str == null) {
            return null;
        }
        this.localTag.remove();
        return str;
    }

    private String createMessage(String str, Object... objArr) {
        return (objArr == null || objArr.length == 0) ? str : String.format(str, objArr);
    }
}
