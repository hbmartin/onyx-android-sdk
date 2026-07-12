package com.onyx.android.sdk.data;

import android.graphics.Bitmap;
import com.alibaba.fastjson2.JSONObject;
import com.onyx.android.sdk.utils.DateTimeUtil;
import java.util.Date;
import java.util.List;

public class GObject {
    public static final transient String TAG = "GObject";
    private JSONObject a;
    private transient GObjectCallback b;

    public static abstract class GObjectCallback {
        public void changed(String key, GObject object) {
        }
    }

    public GObject() {
        this.a = new JSONObject();
    }

    public JSONObject getBackend() {
        return this.a;
    }

    public void setBackend(JSONObject object) {
        this.a = object;
    }

    public GObject setDummyObject() {
        this.a = null;
        return this;
    }

    public boolean isDummyObject() {
        return this.a == null;
    }

    public void setCallback(GObjectCallback cb) {
        this.b = cb;
    }

    public void invokeCallback(String key) {
        GObjectCallback gObjectCallback = this.b;
        if (gObjectCallback != null) {
            gObjectCallback.changed(key, this);
        }
    }

    public boolean hasKey(String key) {
        if (isDummyObject()) {
            return false;
        }
        return this.a.containsKey(key);
    }

    public boolean matches(String key, Object pattern) {
        if (hasKey(key)) {
            return pattern.equals(getObject(key));
        }
        return false;
    }

    public String getString(String key) {
        if (!isDummyObject() && this.a.containsKey(key)) {
            return this.a.getString(key);
        }
        return null;
    }

    public boolean putString(String key, String value) {
        if (isDummyObject()) {
            return false;
        }
        this.a.put(key, value);
        invokeCallback(key);
        return true;
    }

    public boolean putDateAsString(String key, Date value) {
        if (isDummyObject()) {
            return false;
        }
        if (value == null) {
            this.a.put(key, (Object) null);
        } else {
            this.a.put(key, DateTimeUtil.formatDate(value));
        }
        invokeCallback(key);
        return true;
    }

    public boolean putLong(String key, long value) {
        if (isDummyObject()) {
            return false;
        }
        this.a.put(key, Long.valueOf(value));
        invokeCallback(key);
        return true;
    }

    public long getLong(String key) {
        if (isDummyObject()) {
            return -1L;
        }
        return this.a.getLong(key).longValue();
    }

    public boolean putGObject(String key, GObject object) {
        if (isDummyObject()) {
            return false;
        }
        this.a.put(key, object);
        invokeCallback(key);
        return true;
    }

    public GObject getGObject(String key) {
        if (isDummyObject()) {
            return null;
        }
        Object obj = this.a.get(key);
        if (obj instanceof GObject) {
            return (GObject) obj;
        }
        return null;
    }

    public int getInt(String key, int defaultValue) {
        if (!isDummyObject() && this.a.containsKey(key)) {
            return this.a.getInteger(key).intValue();
        }
        return defaultValue;
    }

    public boolean putInt(String key, int value) {
        if (isDummyObject()) {
            return false;
        }
        this.a.put(key, Integer.valueOf(value));
        invokeCallback(key);
        return true;
    }

    public float getFloat(String key) {
        if (isDummyObject()) {
            return Float.NEGATIVE_INFINITY;
        }
        return this.a.getFloat(key).floatValue();
    }

    public boolean putFloat(String key, float value) {
        if (isDummyObject()) {
            return false;
        }
        this.a.put(key, Float.valueOf(value));
        invokeCallback(key);
        return true;
    }

    public List getList(String key) {
        if (isDummyObject()) {
            return null;
        }
        Object obj = this.a.get(key);
        if (obj instanceof List) {
            return (List) obj;
        }
        return null;
    }

    public boolean putList(String key, List list) {
        if (isDummyObject()) {
            return false;
        }
        this.a.put(key, list);
        invokeCallback(key);
        return true;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        if (!isDummyObject() && this.a.containsKey(key)) {
            return this.a.getBoolean(key).booleanValue();
        }
        return defaultValue;
    }

    public boolean putBoolean(String key, boolean value) {
        if (isDummyObject()) {
            return false;
        }
        this.a.put(key, Boolean.valueOf(value));
        invokeCallback(key);
        return true;
    }

    public boolean removeObject(String key) {
        if (isDummyObject()) {
            return false;
        }
        this.a.remove(key);
        invokeCallback(key);
        return true;
    }

    public double getDouble(String key) {
        if (isDummyObject()) {
            return -1.0d;
        }
        return this.a.getDouble(key).doubleValue();
    }

    public boolean putDouble(String key, double value) {
        if (isDummyObject()) {
            return false;
        }
        this.a.put(key, Double.valueOf(value));
        invokeCallback(key);
        return true;
    }

    public boolean putObject(String key, Object value) {
        if (isDummyObject()) {
            return false;
        }
        this.a.put(key, value);
        invokeCallback(key);
        return true;
    }

    public boolean putNonNullObject(String key, Object value) {
        if (isDummyObject() || value == null) {
            return false;
        }
        return putObject(key, value);
    }

    public Object getObject(String key) {
        if (isDummyObject()) {
            return null;
        }
        return this.a.get(key);
    }

    public Bitmap getBitmap(String key, Bitmap fallbackBitmap) {
        if (!isDummyObject() && this.a.containsKey(key)) {
            Object object = getObject(key);
            return object instanceof Bitmap ? (Bitmap) object : fallbackBitmap;
        }
        return fallbackBitmap;
    }

    public boolean recycleBitmap(String key) {
        Bitmap bitmap = getBitmap(key, null);
        if (bitmap == null || bitmap.isRecycled()) {
            return false;
        }
        bitmap.recycle();
        this.a.remove(key);
        return true;
    }

    public GObject(JSONObject obj) {
        this.a = new JSONObject();
        this.a = obj;
    }

    public String getString(String key, String defaultValue) {
        if (!isDummyObject() && this.a.containsKey(key)) {
            return this.a.getString(key);
        }
        return defaultValue;
    }

    public int getInt(String key) {
        if (isDummyObject()) {
            return -1;
        }
        return this.a.getInteger(key).intValue();
    }

    public boolean getBoolean(String key) {
        if (isDummyObject()) {
            return false;
        }
        return this.a.getBoolean(key).booleanValue();
    }
}
