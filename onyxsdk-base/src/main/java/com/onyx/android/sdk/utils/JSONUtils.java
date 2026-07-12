// 
// 

package com.onyx.android.sdk.utils;

import java.util.Iterator;
import androidx.annotation.Nullable;
import com.alibaba.fastjson2.JSONObject;
import androidx.annotation.NonNull;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.alibaba.fastjson2.JSONWriter;
import java.util.Map;
import com.alibaba.fastjson2.TypeReference;
import java.util.List;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;

public class JSONUtils
{
    public static synchronized <T> T parseObject(final String json, final Class<T> cls, final JSONReader.Feature... features) {
        try {
            return (T)JSON.parseObject(json, (Class)cls, features);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static synchronized <T> T parseObjectQuietly(final String json, final Class<T> cls, final JSONReader.Feature... features) {
        try {
            return (T)JSON.parseObject(json, (Class)cls, features);
        }
        catch (final Exception ex) {
            return null;
        }
    }
    
    @Deprecated
    public static <T> List<T> parseObjectList(String json) {
        if (StringUtils.isNullOrEmpty(json)) {
            return null;
        }
        if ((json = json.trim()).startsWith("[") && json.endsWith("]")) {
            return parseListObject(json, new JSONReader.Feature[0]);
        }
        return null;
    }
    
    public static synchronized <T> T parseObject(final String json, final TypeReference<T> typeReference, final JSONReader.Feature... features) {
        try {
            if (StringUtils.isNullOrEmpty(json)) {
                return null;
            }
            return (T)JSON.parseObject(json, (TypeReference)typeReference, features);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    @Deprecated
    public static <T> List<T> parseListObject(final String json, final JSONReader.Feature... features) {
        return parseObject(json, (com.alibaba.fastjson2.TypeReference<List<T>>)new TypeReference<List<T>>() {}, features);
    }
    
    public static <T> Map<String, String> objectToMap(final T t) {
        return parseObject(toJson(t, new JSONWriter.Feature[0]), (com.alibaba.fastjson2.TypeReference<Map<String, String>>)new TypeReference<Map<String, String>>() {}, new JSONReader.Feature[0]);
    }
    
    public static synchronized <T> List<T> parseList(final String json, final Class<T> cls, final JSONReader.Feature... features) {
        if (StringUtils.isBlank(json)) {
            return new ArrayList<T>();
        }
        try {
            return CollectionUtils.ensureList((List<T>)JSON.parseArray(json, (Class)cls, features));
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return new ArrayList<T>();
        }
    }
    
    public static <K, V> Map<K, V> parseObjectToMap(final String json) {
        return parseObject(json, (com.alibaba.fastjson2.TypeReference<Map<K, V>>)new TypeReference<Map<K, V>>() {}, new JSONReader.Feature[0]);
    }
    
    public static synchronized <T> T toBean(final byte[] json, final Class<T> type) {
        Object object = null;
        try {
            try {
                object = JSON.parseObject(new String(json, "UTF-8"), (Class)type);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
        return (T)object;
    }
    
    public static synchronized <T> T toBean(final byte[] json, final Type type) {
        Object object = null;
        try {
            try {
                object = JSON.parseObject(new String(json, "UTF-8"), type);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
        return (T)object;
    }
    
    public static synchronized <T> T toBean(String json, final Type type) {
        try {
            return (T) JSON.parseObject(json, type);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static synchronized String toJson(Object object, final JSONWriter.Feature... features) {
        try {
            return JSON.toJSONString(object, features);
        }
        catch (final Exception ex) {
            Debug.e((Throwable)ex);
            return null;
        }
    }
    
    public static synchronized <T> List<T> toList(final String json, final Type type) {
        return (List)JSON.parseObject(json, type);
    }
    
    @NonNull
    public static synchronized <T> List<T> toList(String json, final Class<T> clazz) {
        try {
            List<T> result = JSON.parseArray(json, clazz);
            return result == null ? new ArrayList<>() : result;
        }
        catch (final Exception ex) {
            Debug.e((Throwable)ex);
            return new ArrayList<>();
        }
    }
    
    @NonNull
    public static final synchronized JSONObject parseObject(final String text) {
        JSONObject object;
        if ((object = JSON.parseObject(text)) == null) {
            object = new JSONObject();
        }
        return object;
    }
    
    @Nullable
    public static JSONObject toJSONObject(Object object) {
        try {
            Object value = JSON.toJSON(object);
            return value instanceof JSONObject ? (JSONObject) value : null;
        }
        catch (final Exception ex) {
            Debug.e((Throwable)ex);
            return null;
        }
    }
    
    @Nullable
    public static <T> T parseObject(final Map<String, Object> data, final Class<T> cls) {
        try {
            return parseObject(new JSONObject(data), cls);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static boolean isJSONString(final String jsonStr) {
        return !StringUtils.isNullOrEmpty(jsonStr) && ((jsonStr.startsWith("{") && jsonStr.endsWith("}")) || (jsonStr.startsWith("[") && jsonStr.endsWith("]")));
    }
    
    public static String appendJsonString(final String origin, final String target) {
        if (isJSONString(origin) && isJSONString(target)) {
            final JSONObject object = parseObject(target);
            final JSONObject object2 = parseObject(origin);
            final Iterator iterator = object.keySet().iterator();
            while (iterator.hasNext()) {
                final JSONObject jsonObject = object2;
                final JSONObject jsonObject2 = object;
                final String s = (String)iterator.next();
                jsonObject.put(s, jsonObject2.get(s));
            }
            return object2.toJSONString(new JSONWriter.Feature[0]);
        }
        return target;
    }
    
    @Nullable
    public static <T> T parseObject(final JSONObject jsonObject, final Class<T> cls) {
        try {
            return (T)jsonObject.toJavaObject((Class)cls, new JSONReader.Feature[0]);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    @Nullable
    public static <T> T clone(final Object object, final Class<T> cls) {
        return parseObject(toJson(object, new JSONWriter.Feature[0]), cls, new JSONReader.Feature[0]);
    }
}
