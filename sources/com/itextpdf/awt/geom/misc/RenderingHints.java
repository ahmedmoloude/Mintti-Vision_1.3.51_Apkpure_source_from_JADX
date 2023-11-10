package com.itextpdf.awt.geom.misc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class RenderingHints implements Map<Object, Object>, Cloneable {
    public static final Key KEY_ALPHA_INTERPOLATION;
    public static final Key KEY_ANTIALIASING;
    public static final Key KEY_COLOR_RENDERING;
    public static final Key KEY_DITHERING;
    public static final Key KEY_FRACTIONALMETRICS;
    public static final Key KEY_INTERPOLATION;
    public static final Key KEY_RENDERING;
    public static final Key KEY_STROKE_CONTROL;
    public static final Key KEY_TEXT_ANTIALIASING;
    public static final Object VALUE_ALPHA_INTERPOLATION_DEFAULT;
    public static final Object VALUE_ALPHA_INTERPOLATION_QUALITY;
    public static final Object VALUE_ALPHA_INTERPOLATION_SPEED;
    public static final Object VALUE_ANTIALIAS_DEFAULT;
    public static final Object VALUE_ANTIALIAS_OFF;
    public static final Object VALUE_ANTIALIAS_ON;
    public static final Object VALUE_COLOR_RENDER_DEFAULT;
    public static final Object VALUE_COLOR_RENDER_QUALITY;
    public static final Object VALUE_COLOR_RENDER_SPEED;
    public static final Object VALUE_DITHER_DEFAULT;
    public static final Object VALUE_DITHER_DISABLE;
    public static final Object VALUE_DITHER_ENABLE;
    public static final Object VALUE_FRACTIONALMETRICS_DEFAULT;
    public static final Object VALUE_FRACTIONALMETRICS_OFF;
    public static final Object VALUE_FRACTIONALMETRICS_ON;
    public static final Object VALUE_INTERPOLATION_BICUBIC;
    public static final Object VALUE_INTERPOLATION_BILINEAR;
    public static final Object VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
    public static final Object VALUE_RENDER_DEFAULT;
    public static final Object VALUE_RENDER_QUALITY;
    public static final Object VALUE_RENDER_SPEED;
    public static final Object VALUE_STROKE_DEFAULT;
    public static final Object VALUE_STROKE_NORMALIZE;
    public static final Object VALUE_STROKE_PURE;
    public static final Object VALUE_TEXT_ANTIALIAS_DEFAULT;
    public static final Object VALUE_TEXT_ANTIALIAS_OFF;
    public static final Object VALUE_TEXT_ANTIALIAS_ON;
    private HashMap<Object, Object> map = new HashMap<>();

    static {
        KeyImpl keyImpl = new KeyImpl(1);
        KEY_ALPHA_INTERPOLATION = keyImpl;
        VALUE_ALPHA_INTERPOLATION_DEFAULT = new KeyValue(keyImpl);
        VALUE_ALPHA_INTERPOLATION_SPEED = new KeyValue(keyImpl);
        VALUE_ALPHA_INTERPOLATION_QUALITY = new KeyValue(keyImpl);
        KeyImpl keyImpl2 = new KeyImpl(2);
        KEY_ANTIALIASING = keyImpl2;
        VALUE_ANTIALIAS_DEFAULT = new KeyValue(keyImpl2);
        VALUE_ANTIALIAS_ON = new KeyValue(keyImpl2);
        VALUE_ANTIALIAS_OFF = new KeyValue(keyImpl2);
        KeyImpl keyImpl3 = new KeyImpl(3);
        KEY_COLOR_RENDERING = keyImpl3;
        VALUE_COLOR_RENDER_DEFAULT = new KeyValue(keyImpl3);
        VALUE_COLOR_RENDER_SPEED = new KeyValue(keyImpl3);
        VALUE_COLOR_RENDER_QUALITY = new KeyValue(keyImpl3);
        KeyImpl keyImpl4 = new KeyImpl(4);
        KEY_DITHERING = keyImpl4;
        VALUE_DITHER_DEFAULT = new KeyValue(keyImpl4);
        VALUE_DITHER_DISABLE = new KeyValue(keyImpl4);
        VALUE_DITHER_ENABLE = new KeyValue(keyImpl4);
        KeyImpl keyImpl5 = new KeyImpl(5);
        KEY_FRACTIONALMETRICS = keyImpl5;
        VALUE_FRACTIONALMETRICS_DEFAULT = new KeyValue(keyImpl5);
        VALUE_FRACTIONALMETRICS_ON = new KeyValue(keyImpl5);
        VALUE_FRACTIONALMETRICS_OFF = new KeyValue(keyImpl5);
        KeyImpl keyImpl6 = new KeyImpl(6);
        KEY_INTERPOLATION = keyImpl6;
        VALUE_INTERPOLATION_BICUBIC = new KeyValue(keyImpl6);
        VALUE_INTERPOLATION_BILINEAR = new KeyValue(keyImpl6);
        VALUE_INTERPOLATION_NEAREST_NEIGHBOR = new KeyValue(keyImpl6);
        KeyImpl keyImpl7 = new KeyImpl(7);
        KEY_RENDERING = keyImpl7;
        VALUE_RENDER_DEFAULT = new KeyValue(keyImpl7);
        VALUE_RENDER_SPEED = new KeyValue(keyImpl7);
        VALUE_RENDER_QUALITY = new KeyValue(keyImpl7);
        KeyImpl keyImpl8 = new KeyImpl(8);
        KEY_STROKE_CONTROL = keyImpl8;
        VALUE_STROKE_DEFAULT = new KeyValue(keyImpl8);
        VALUE_STROKE_NORMALIZE = new KeyValue(keyImpl8);
        VALUE_STROKE_PURE = new KeyValue(keyImpl8);
        KeyImpl keyImpl9 = new KeyImpl(9);
        KEY_TEXT_ANTIALIASING = keyImpl9;
        VALUE_TEXT_ANTIALIAS_DEFAULT = new KeyValue(keyImpl9);
        VALUE_TEXT_ANTIALIAS_ON = new KeyValue(keyImpl9);
        VALUE_TEXT_ANTIALIAS_OFF = new KeyValue(keyImpl9);
    }

    public RenderingHints(Map<Key, ?> map2) {
        if (map2 != null) {
            putAll(map2);
        }
    }

    public RenderingHints(Key key, Object obj) {
        put(key, obj);
    }

    public void add(RenderingHints renderingHints) {
        this.map.putAll(renderingHints.map);
    }

    public Object put(Object obj, Object obj2) {
        if (((Key) obj).isCompatibleValue(obj2)) {
            return this.map.put(obj, obj2);
        }
        throw new IllegalArgumentException();
    }

    public Object remove(Object obj) {
        return this.map.remove(obj);
    }

    public Object get(Object obj) {
        return this.map.get(obj);
    }

    public Set<Object> keySet() {
        return this.map.keySet();
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        return this.map.entrySet();
    }

    public void putAll(Map<?, ?> map2) {
        if (map2 instanceof RenderingHints) {
            this.map.putAll(((RenderingHints) map2).map);
            return;
        }
        Set<Map.Entry<?, ?>> entrySet = map2.entrySet();
        if (entrySet != null) {
            for (Map.Entry next : entrySet) {
                put((Key) next.getKey(), next.getValue());
            }
        }
    }

    public Collection<Object> values() {
        return this.map.values();
    }

    public boolean containsValue(Object obj) {
        return this.map.containsValue(obj);
    }

    public boolean containsKey(Object obj) {
        Objects.requireNonNull(obj);
        return this.map.containsKey(obj);
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public void clear() {
        this.map.clear();
    }

    public int size() {
        return this.map.size();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map2 = (Map) obj;
        Set<Object> keySet = keySet();
        if (!keySet.equals(map2.keySet())) {
            return false;
        }
        Iterator<Object> it = keySet.iterator();
        while (it.hasNext()) {
            Key key = (Key) it.next();
            Object obj2 = get(key);
            Object obj3 = map2.get(key);
            if (obj2 == null) {
                if (obj3 == null) {
                }
            } else if (!obj2.equals(obj3)) {
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.map.hashCode();
    }

    public Object clone() {
        RenderingHints renderingHints = new RenderingHints((Map<Key, ?>) null);
        renderingHints.map = (HashMap) this.map.clone();
        return renderingHints;
    }

    public String toString() {
        return "RenderingHints[" + this.map.toString() + "]";
    }

    public static abstract class Key {
        private final int key;

        public final boolean equals(Object obj) {
            return this == obj;
        }

        public abstract boolean isCompatibleValue(Object obj);

        protected Key(int i) {
            this.key = i;
        }

        public final int hashCode() {
            return System.identityHashCode(this);
        }

        /* access modifiers changed from: protected */
        public final int intKey() {
            return this.key;
        }
    }

    private static class KeyImpl extends Key {
        protected KeyImpl(int i) {
            super(i);
        }

        public boolean isCompatibleValue(Object obj) {
            if ((obj instanceof KeyValue) && ((KeyValue) obj).key == this) {
                return true;
            }
            return false;
        }
    }

    private static class KeyValue {
        /* access modifiers changed from: private */
        public final Key key;

        protected KeyValue(Key key2) {
            this.key = key2;
        }
    }
}
