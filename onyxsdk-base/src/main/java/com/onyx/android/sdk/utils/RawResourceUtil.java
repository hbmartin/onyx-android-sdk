
package com.onyx.android.sdk.utils;

import android.content.res.Resources;
import java.io.IOException;
import java.io.Closeable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import com.alibaba.fastjson2.JSONObject;
import com.onyx.android.sdk.data.GObject;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.TypeReference;
import java.util.List;
import java.util.Map;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.content.Context;

public class RawResourceUtil
{
    static final String a = "drawable";
    static final String b = "string";
    
    public static int getDrawableIdByName(final Context context, final String resourceName) {
        return getResourceIdByName(context, "drawable", resourceName);
    }
    
    public static int getStringIdByName(final Context context, final String resourceName) {
        return getResourceIdByName(context, "string", resourceName);
    }
    
    @Nullable
    public static String getStringByResourceName(final Context context, final String resourceName) {
        final int stringIdByName;
        if ((stringIdByName = getStringIdByName(context, resourceName)) <= 0) {
            return null;
        }
        return context.getString(stringIdByName);
    }
    
    @Nullable
    public static Drawable getDrawableByResourceName(final Context context, final String resourceName) {
        final int drawableIdByName;
        if ((drawableIdByName = getDrawableIdByName(context, resourceName)) <= 0) {
            return null;
        }
        return context.getResources().getDrawable(drawableIdByName);
    }
    
    public static int getResourceIdByName(final Context context, final String resourceType, final String resourceName) {
        if (StringUtils.isNotBlank(resourceName)) {
            return context.getResources().getIdentifier(resourceName, resourceType, context.getPackageName());
        }
        return 0;
    }
    
    public static String contentOfRawResource(final Context context, final int rawResourceId) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                context.getResources().openRawResource(rawResourceId)))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        } catch (Exception exception) {
            if (rawResourceId > 0) {
                exception.printStackTrace();
            }
            return null;
        }
    }
    
    public static Map<String, List<Integer>> integerMapFromRawResource(final Context context, final int rawResourceId) {
        return (Map)JSON.parseObject(contentOfRawResource(context, rawResourceId), (TypeReference)new TypeReference<Map<String, List<Integer>>>() {}, new JSONReader.Feature[0]);
    }
    
    public static GObject objectFromRawResource(final Context context, final int rawResourceId) {
        final String contentOfRawResource = contentOfRawResource(context, rawResourceId);
        try {
            final JSONObject object;
            if ((object = JSON.parseObject(contentOfRawResource)) == null) {
                return null;
            }
            final JSONObject jsonObject = object;
            final GObject gObject = new GObject();
            final Iterator<Map.Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
            try {
                while (true) {
                    if (!iterator.hasNext()) {
                        return gObject;
                    }
                    final GObject gObject2 = gObject;
                    final Map.Entry entry = iterator.next();
                    final String key = (String)entry.getKey();
                    try {
                        gObject2.putObject(key, entry.getValue());
                        continue;
                    }
                    catch (final Exception ex) {
                        ex.printStackTrace();
                        return null;
                    }
                }
            }
            catch (final Exception ex2) {
                ex2.printStackTrace();
                return null;
            }
        }
        catch (final Exception ex3) {
            ex3.printStackTrace();
            return null;
        }
    }
    
    public static void closeQuietly(final Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            }
            catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static String contentFromRawResource(final Context context, String name) {
        String contentOfRawResource = "";
        try {
            final Resources resources = context.getResources();
            final String lowerCase = name.toLowerCase();
            name = "raw";
            final String packageName = context.getPackageName();
            try {
                contentOfRawResource = contentOfRawResource(context, resources.getIdentifier(lowerCase, name, packageName));
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
        catch (final Exception ex2) {}
        return contentOfRawResource;
    }
}
