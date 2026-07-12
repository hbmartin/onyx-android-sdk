// 
// Decompiled by Procyon v0.6.0
// 

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
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aconst_null    
        //     2: astore_0       
        //     3: aconst_null    
        //     4: astore_2       
        //     5: invokevirtual   android/content/Context.getResources:()Landroid/content/res/Resources;
        //     8: iload_1        
        //     9: invokevirtual   android/content/res/Resources.openRawResource:(I)Ljava/io/InputStream;
        //    12: astore_2       
        //    13: new             Ljava/io/BufferedReader;
        //    16: dup            
        //    17: astore_3       
        //    18: new             Ljava/io/InputStreamReader;
        //    21: dup            
        //    22: aload_2        
        //    23: invokespecial   java/io/InputStreamReader.<init>:(Ljava/io/InputStream;)V
        //    26: invokespecial   java/io/BufferedReader.<init>:(Ljava/io/Reader;)V
        //    29: new             Ljava/lang/StringBuilder;
        //    32: dup            
        //    33: astore_0       
        //    34: invokespecial   java/lang/StringBuilder.<init>:()V
        //    37: aload_3        
        //    38: invokevirtual   java/io/BufferedReader.readLine:()Ljava/lang/String;
        //    41: dup            
        //    42: astore          4
        //    44: ifnull          57
        //    47: aload_0        
        //    48: aload           4
        //    50: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    53: pop            
        //    54: goto            37
        //    57: aload_0        
        //    58: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    61: aload_2        
        //    62: aload_3        
        //    63: invokestatic    com/onyx/android/sdk/utils/RawResourceUtil.closeQuietly:(Ljava/io/Closeable;)V
        //    66: invokestatic    com/onyx/android/sdk/utils/RawResourceUtil.closeQuietly:(Ljava/io/Closeable;)V
        //    69: areturn        
        //    70: aload_3        
        //    71: astore_0       
        //    72: goto            98
        //    75: astore_0       
        //    76: aload_0        
        //    77: aload_3        
        //    78: astore_0       
        //    79: astore_3       
        //    80: goto            87
        //    83: goto            98
        //    86: astore_3       
        //    87: iload_1        
        //    88: ifle            107
        //    91: aload_3        
        //    92: invokevirtual   java/lang/Exception.printStackTrace:()V
        //    95: goto            107
        //    98: aload_2        
        //    99: aload_0        
        //   100: invokestatic    com/onyx/android/sdk/utils/RawResourceUtil.closeQuietly:(Ljava/io/Closeable;)V
        //   103: invokestatic    com/onyx/android/sdk/utils/RawResourceUtil.closeQuietly:(Ljava/io/Closeable;)V
        //   106: athrow         
        //   107: aload_2        
        //   108: aload_0        
        //   109: invokestatic    com/onyx/android/sdk/utils/RawResourceUtil.closeQuietly:(Ljava/io/Closeable;)V
        //   112: invokestatic    com/onyx/android/sdk/utils/RawResourceUtil.closeQuietly:(Ljava/io/Closeable;)V
        //   115: aconst_null    
        //   116: areturn        
        //    StackMapTable: 00 09 FF 00 25 00 04 07 00 64 01 07 00 67 07 00 5A 00 00 13 FF 00 0C 00 04 00 00 07 00 67 07 00 5A 00 01 07 00 77 FF 00 04 00 04 00 01 07 00 67 07 00 5A 00 01 07 00 79 FF 00 07 00 03 07 00 5A 00 07 00 67 00 01 07 00 77 FF 00 02 00 03 07 00 5A 01 07 00 67 00 01 07 00 79 FC 00 00 07 00 79 FF 00 0A 00 03 07 00 5A 00 07 00 67 00 01 07 00 77 08
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  5      12     86     87     Ljava/lang/Exception;
        //  5      12     83     86     Any
        //  13     16     86     87     Ljava/lang/Exception;
        //  13     16     83     86     Any
        //  18     29     86     87     Ljava/lang/Exception;
        //  18     29     83     86     Any
        //  29     32     75     83     Ljava/lang/Exception;
        //  29     32     70     75     Any
        //  34     41     75     83     Ljava/lang/Exception;
        //  34     41     70     75     Any
        //  47     53     75     83     Ljava/lang/Exception;
        //  47     53     70     75     Any
        //  57     61     75     83     Ljava/lang/Exception;
        //  57     61     70     75     Any
        //  91     98     83     86     Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0087 (coming from #0072).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2239)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:203)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:93)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:868)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:761)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:638)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:605)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:195)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:162)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:137)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:333)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:254)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:144)
        // 
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
