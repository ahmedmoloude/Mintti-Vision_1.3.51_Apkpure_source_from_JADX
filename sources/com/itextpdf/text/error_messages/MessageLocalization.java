package com.itextpdf.text.error_messages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

public final class MessageLocalization {
    private static final String BASE_PATH = "com/itextpdf/text/l10n/error/";
    private static HashMap<String, String> currentLanguage;
    private static HashMap<String, String> defaultLanguage;

    static {
        defaultLanguage = new HashMap<>();
        try {
            defaultLanguage = getLanguageMessages("en", (String) null);
        } catch (Exception unused) {
        }
        if (defaultLanguage == null) {
            defaultLanguage = new HashMap<>();
        }
    }

    private MessageLocalization() {
    }

    public static String getMessage(String str) {
        return getMessage(str, true);
    }

    public static String getMessage(String str, boolean z) {
        String str2;
        String str3;
        HashMap<String, String> hashMap = currentLanguage;
        if (hashMap != null && (str3 = hashMap.get(str)) != null) {
            return str3;
        }
        if (z && (str2 = defaultLanguage.get(str)) != null) {
            return str2;
        }
        return "No message found for " + str;
    }

    public static String getComposedMessage(String str, int i) {
        return getComposedMessage(str, String.valueOf(i), null, null, null);
    }

    public static String getComposedMessage(String str, Object... objArr) {
        String message = getMessage(str);
        if (objArr != null) {
            int i = 1;
            for (Object obj : objArr) {
                if (obj != null) {
                    message = message.replace("{" + i + "}", obj.toString());
                }
                i++;
            }
        }
        return message;
    }

    public static boolean setLanguage(String str, String str2) throws IOException {
        HashMap<String, String> languageMessages = getLanguageMessages(str, str2);
        if (languageMessages == null) {
            return false;
        }
        currentLanguage = languageMessages;
        return true;
    }

    public static void setMessages(Reader reader) throws IOException {
        currentLanguage = readLanguageStream(reader);
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x00af A[SYNTHETIC, Splitter:B:43:0x00af] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.HashMap<java.lang.String, java.lang.String> getLanguageMessages(java.lang.String r5, java.lang.String r6) throws java.io.IOException {
        /*
            java.lang.String r0 = "com/itextpdf/text/l10n/error/"
            if (r5 == 0) goto L_0x00b3
            java.lang.String r1 = ".lng"
            r2 = 0
            if (r6 == 0) goto L_0x0021
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ac }
            r3.<init>()     // Catch:{ all -> 0x00ac }
            r3.append(r5)     // Catch:{ all -> 0x00ac }
            java.lang.String r4 = "_"
            r3.append(r4)     // Catch:{ all -> 0x00ac }
            r3.append(r6)     // Catch:{ all -> 0x00ac }
            r3.append(r1)     // Catch:{ all -> 0x00ac }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00ac }
            goto L_0x0030
        L_0x0021:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ac }
            r3.<init>()     // Catch:{ all -> 0x00ac }
            r3.append(r5)     // Catch:{ all -> 0x00ac }
            r3.append(r1)     // Catch:{ all -> 0x00ac }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00ac }
        L_0x0030:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ac }
            r4.<init>()     // Catch:{ all -> 0x00ac }
            r4.append(r0)     // Catch:{ all -> 0x00ac }
            r4.append(r3)     // Catch:{ all -> 0x00ac }
            java.lang.String r3 = r4.toString()     // Catch:{ all -> 0x00ac }
            com.itextpdf.text.error_messages.MessageLocalization r4 = new com.itextpdf.text.error_messages.MessageLocalization     // Catch:{ all -> 0x00ac }
            r4.<init>()     // Catch:{ all -> 0x00ac }
            java.lang.Class r4 = r4.getClass()     // Catch:{ all -> 0x00ac }
            java.lang.ClassLoader r4 = r4.getClassLoader()     // Catch:{ all -> 0x00ac }
            java.io.InputStream r3 = com.itextpdf.text.p018io.StreamUtil.getResourceStream(r3, r4)     // Catch:{ all -> 0x00ac }
            if (r3 == 0) goto L_0x005f
            java.util.HashMap r5 = readLanguageStream((java.io.InputStream) r3)     // Catch:{ all -> 0x005c }
            if (r3 == 0) goto L_0x005b
            r3.close()     // Catch:{ Exception -> 0x005b }
        L_0x005b:
            return r5
        L_0x005c:
            r5 = move-exception
            r2 = r3
            goto L_0x00ad
        L_0x005f:
            if (r6 != 0) goto L_0x0067
            if (r3 == 0) goto L_0x0066
            r3.close()     // Catch:{ Exception -> 0x0066 }
        L_0x0066:
            return r2
        L_0x0067:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x005c }
            r6.<init>()     // Catch:{ all -> 0x005c }
            r6.append(r5)     // Catch:{ all -> 0x005c }
            r6.append(r1)     // Catch:{ all -> 0x005c }
            java.lang.String r5 = r6.toString()     // Catch:{ all -> 0x005c }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x005c }
            r6.<init>()     // Catch:{ all -> 0x005c }
            r6.append(r0)     // Catch:{ all -> 0x005c }
            r6.append(r5)     // Catch:{ all -> 0x005c }
            java.lang.String r5 = r6.toString()     // Catch:{ all -> 0x005c }
            com.itextpdf.text.error_messages.MessageLocalization r6 = new com.itextpdf.text.error_messages.MessageLocalization     // Catch:{ all -> 0x005c }
            r6.<init>()     // Catch:{ all -> 0x005c }
            java.lang.Class r6 = r6.getClass()     // Catch:{ all -> 0x005c }
            java.lang.ClassLoader r6 = r6.getClassLoader()     // Catch:{ all -> 0x005c }
            java.io.InputStream r5 = com.itextpdf.text.p018io.StreamUtil.getResourceStream(r5, r6)     // Catch:{ all -> 0x005c }
            if (r5 == 0) goto L_0x00a6
            java.util.HashMap r6 = readLanguageStream((java.io.InputStream) r5)     // Catch:{ all -> 0x00a2 }
            if (r5 == 0) goto L_0x00a1
            r5.close()     // Catch:{ Exception -> 0x00a1 }
        L_0x00a1:
            return r6
        L_0x00a2:
            r6 = move-exception
            r2 = r5
            r5 = r6
            goto L_0x00ad
        L_0x00a6:
            if (r5 == 0) goto L_0x00ab
            r5.close()     // Catch:{ Exception -> 0x00ab }
        L_0x00ab:
            return r2
        L_0x00ac:
            r5 = move-exception
        L_0x00ad:
            if (r2 == 0) goto L_0x00b2
            r2.close()     // Catch:{ Exception -> 0x00b2 }
        L_0x00b2:
            throw r5
        L_0x00b3:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "The language cannot be null."
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.text.error_messages.MessageLocalization.getLanguageMessages(java.lang.String, java.lang.String):java.util.HashMap");
    }

    private static HashMap<String, String> readLanguageStream(InputStream inputStream) throws IOException {
        return readLanguageStream((Reader) new InputStreamReader(inputStream, "UTF-8"));
    }

    private static HashMap<String, String> readLanguageStream(Reader reader) throws IOException {
        HashMap<String, String> hashMap = new HashMap<>();
        BufferedReader bufferedReader = new BufferedReader(reader);
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return hashMap;
            }
            int indexOf = readLine.indexOf(61);
            if (indexOf >= 0) {
                String trim = readLine.substring(0, indexOf).trim();
                if (!trim.startsWith("#")) {
                    hashMap.put(trim, readLine.substring(indexOf + 1));
                }
            }
        }
    }
}
