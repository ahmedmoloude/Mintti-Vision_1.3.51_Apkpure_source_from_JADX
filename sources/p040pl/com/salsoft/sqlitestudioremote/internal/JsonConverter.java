package p040pl.com.salsoft.sqlitestudioremote.internal;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: pl.com.salsoft.sqlitestudioremote.internal.JsonConverter */
public class JsonConverter {
    public static Object fromJsonValue(Object obj) {
        if (obj instanceof JSONObject) {
            return fromJson((JSONObject) obj);
        }
        return obj instanceof JSONArray ? fromJson((JSONArray) obj) : obj;
    }

    public static Object toJsonValue(Object obj) {
        if (obj == null) {
            return JSONObject.NULL;
        }
        if ((obj instanceof JSONArray) || (obj instanceof JSONObject) || obj.equals(JSONObject.NULL)) {
            return obj;
        }
        try {
            if (obj instanceof Collection) {
                return collectionToArray((Collection) obj);
            }
            if (obj instanceof byte[]) {
                JSONArray jSONArray = new JSONArray();
                jSONArray.put(Utils.toBlobString((byte[]) obj));
                return jSONArray;
            } else if (obj.getClass().isArray()) {
                return arrayToArray(obj);
            } else {
                if (obj instanceof Map) {
                    return mapToObject((Map) obj);
                }
                if ((obj instanceof Boolean) || (obj instanceof Character) || (obj instanceof Double) || (obj instanceof Float) || (obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Short) || (obj instanceof Byte)) {
                    return obj;
                }
                if (obj instanceof String) {
                    return obj;
                }
                if (obj.getClass().getPackage().getName().startsWith("java.")) {
                    return obj.toString();
                }
                return null;
            }
        } catch (Exception unused) {
        }
    }

    private static Object arrayToArray(Object obj) {
        JSONArray jSONArray = new JSONArray();
        int i = 0;
        if (obj.getClass().getComponentType().isPrimitive()) {
            int length = Array.getLength(obj);
            while (i < length) {
                jSONArray.put(Array.get(obj, i));
                i++;
            }
        } else {
            Object[] objArr = (Object[]) obj;
            int length2 = objArr.length;
            while (i < length2) {
                jSONArray.put(toJsonValue(objArr[i]));
                i++;
            }
        }
        return jSONArray;
    }

    private static Object collectionToArray(Collection collection) {
        JSONArray jSONArray = new JSONArray();
        for (Object jsonValue : collection) {
            jSONArray.put(toJsonValue(jsonValue));
        }
        return jSONArray;
    }

    private static Object mapToObject(Map map) {
        JSONObject jSONObject = new JSONObject();
        for (Object next : map.keySet()) {
            try {
                jSONObject.put("" + next, toJsonValue(map.get(next)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject;
    }

    private static HashMap<String, Object> fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            hashMap.put(next, fromJsonValue(jSONObject.opt(next)));
        }
        return hashMap;
    }

    private static List<Object> fromJson(JSONArray jSONArray) {
        if (jSONArray == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(fromJsonValue(jSONArray.opt(i)));
        }
        return arrayList;
    }
}
