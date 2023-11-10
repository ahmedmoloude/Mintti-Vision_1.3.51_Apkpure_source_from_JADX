package p028io.jsonwebtoken.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import p028io.jsonwebtoken.lang.Assert;
import p028io.jsonwebtoken.lang.DateFormats;

/* renamed from: io.jsonwebtoken.impl.JwtMap */
public class JwtMap implements Map<String, Object> {
    private final Map<String, Object> map;

    public JwtMap() {
        this.map = new LinkedHashMap();
    }

    public JwtMap(Map<String, ?> map2) {
        this();
        Assert.notNull(map2, "Map argument cannot be null.");
        putAll(map2);
    }

    /* access modifiers changed from: protected */
    public String getString(String str) {
        Object obj = get(str);
        if (obj != null) {
            return String.valueOf(obj);
        }
        return null;
    }

    protected static Date toDate(Object obj, String str) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Date) {
            return (Date) obj;
        }
        if (obj instanceof Calendar) {
            return ((Calendar) obj).getTime();
        }
        if (obj instanceof Number) {
            return new Date(((Number) obj).longValue());
        }
        if (obj instanceof String) {
            return parseIso8601Date((String) obj, str);
        }
        throw new IllegalStateException("Cannot create Date from '" + str + "' value '" + obj + "'.");
    }

    private static Date parseIso8601Date(String str, String str2) throws IllegalArgumentException {
        try {
            return DateFormats.parseIso8601Date(str);
        } catch (ParseException e) {
            throw new IllegalArgumentException("'" + str2 + "' value does not appear to be ISO-8601-formatted: " + str, e);
        }
    }

    protected static Date toSpecDate(Object obj, String str) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            obj = Long.valueOf(((Number) obj).longValue() * 1000);
        } else if (obj instanceof String) {
            try {
                obj = Long.valueOf(Long.parseLong((String) obj) * 1000);
            } catch (NumberFormatException unused) {
            }
        }
        return toDate(obj, str);
    }

    /* access modifiers changed from: protected */
    public void setValue(String str, Object obj) {
        if (obj == null) {
            this.map.remove(str);
        } else {
            this.map.put(str, obj);
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void setDate(String str, Date date) {
        if (date == null) {
            this.map.remove(str);
        } else {
            this.map.put(str, Long.valueOf(date.getTime() / 1000));
        }
    }

    /* access modifiers changed from: protected */
    public Object setDateAsSeconds(String str, Date date) {
        if (date == null) {
            return this.map.remove(str);
        }
        return this.map.put(str, Long.valueOf(date.getTime() / 1000));
    }

    public int size() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public boolean containsKey(Object obj) {
        return this.map.containsKey(obj);
    }

    public boolean containsValue(Object obj) {
        return this.map.containsValue(obj);
    }

    public Object get(Object obj) {
        return this.map.get(obj);
    }

    public Object put(String str, Object obj) {
        if (obj == null) {
            return this.map.remove(str);
        }
        return this.map.put(str, obj);
    }

    public Object remove(Object obj) {
        return this.map.remove(obj);
    }

    public void putAll(Map<? extends String, ?> map2) {
        if (map2 != null) {
            for (String str : map2.keySet()) {
                put(str, (Object) map2.get(str));
            }
        }
    }

    public void clear() {
        this.map.clear();
    }

    public Set<String> keySet() {
        return this.map.keySet();
    }

    public Collection<Object> values() {
        return this.map.values();
    }

    public Set<Map.Entry<String, Object>> entrySet() {
        return this.map.entrySet();
    }

    public String toString() {
        return this.map.toString();
    }

    public int hashCode() {
        return this.map.hashCode();
    }

    public boolean equals(Object obj) {
        return this.map.equals(obj);
    }
}
