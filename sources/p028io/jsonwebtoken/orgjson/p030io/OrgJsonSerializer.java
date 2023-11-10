package p028io.jsonwebtoken.orgjson.p030io;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import p028io.jsonwebtoken.lang.Classes;
import p028io.jsonwebtoken.lang.Collections;
import p028io.jsonwebtoken.lang.DateFormats;
import p028io.jsonwebtoken.lang.Objects;
import p028io.jsonwebtoken.lang.Strings;
import p028io.jsonwebtoken.p029io.Encoders;
import p028io.jsonwebtoken.p029io.SerializationException;
import p028io.jsonwebtoken.p029io.Serializer;

/* renamed from: io.jsonwebtoken.orgjson.io.OrgJsonSerializer */
public class OrgJsonSerializer<T> implements Serializer<T> {
    private static final Class JSON_STRING_CLASS;
    private static final String JSON_STRING_CLASS_NAME = "org.json.JSONString";
    private static final String JSON_WRITER_CLASS_NAME = "org.json.JSONWriter";
    private static final Class[] VALUE_TO_STRING_ARG_TYPES = {Object.class};

    static {
        if (Classes.isAvailable(JSON_STRING_CLASS_NAME)) {
            JSON_STRING_CLASS = Classes.forName(JSON_STRING_CLASS_NAME);
        } else {
            JSON_STRING_CLASS = null;
        }
    }

    public byte[] serialize(T t) throws SerializationException {
        try {
            return toBytes(toJSONInstance(t));
        } catch (SerializationException e) {
            throw e;
        } catch (Exception e2) {
            throw new SerializationException("Unable to serialize object of type " + t.getClass().getName() + " to JSON: " + e2.getMessage(), e2);
        }
    }

    private static boolean isJSONString(Object obj) {
        Class cls = JSON_STRING_CLASS;
        if (cls != null) {
            return cls.isInstance(obj);
        }
        return false;
    }

    private Object toJSONInstance(Object obj) {
        if (obj == null) {
            return JSONObject.NULL;
        }
        if ((obj instanceof JSONObject) || (obj instanceof JSONArray) || JSONObject.NULL.equals(obj) || isJSONString(obj) || (obj instanceof Byte) || (obj instanceof Character) || (obj instanceof Short) || (obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Boolean) || (obj instanceof Float) || (obj instanceof Double) || (obj instanceof String) || (obj instanceof BigInteger) || (obj instanceof BigDecimal) || (obj instanceof Enum)) {
            return obj;
        }
        if (obj instanceof Calendar) {
            obj = ((Calendar) obj).getTime();
        }
        if (obj instanceof Date) {
            return DateFormats.formatIso8601((Date) obj);
        }
        if (obj instanceof byte[]) {
            return Encoders.BASE64.encode((byte[]) obj);
        }
        if (obj instanceof char[]) {
            return new String((char[]) obj);
        }
        if (obj instanceof Map) {
            return toJSONObject((Map) obj);
        }
        if (obj instanceof Collection) {
            return toJSONArray((Collection) obj);
        }
        if (Objects.isArray(obj)) {
            return toJSONArray(Collections.arrayToList(obj));
        }
        throw new SerializationException("Unable to serialize object of type " + obj.getClass().getName() + " to JSON using known heuristics.");
    }

    private JSONObject toJSONObject(Map<?, ?> map) {
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry next : map.entrySet()) {
            jSONObject.put(String.valueOf(next.getKey()), toJSONInstance(next.getValue()));
        }
        return jSONObject;
    }

    private JSONArray toJSONArray(Collection collection) {
        JSONArray jSONArray = new JSONArray();
        for (Object jSONInstance : collection) {
            jSONArray.put(toJSONInstance(jSONInstance));
        }
        return jSONArray;
    }

    /* access modifiers changed from: protected */
    public byte[] toBytes(Object obj) {
        String str;
        if (obj instanceof JSONObject) {
            str = obj.toString();
        } else {
            str = (String) Classes.invokeStatic(JSON_WRITER_CLASS_NAME, "valueToString", VALUE_TO_STRING_ARG_TYPES, obj);
        }
        return str.getBytes(Strings.UTF_8);
    }
}
