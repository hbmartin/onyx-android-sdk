// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import com.onyx.android.sdk.rx.RxUtils;
import io.reactivex.functions.Consumer;
import java.util.concurrent.atomic.AtomicReference;
import androidx.annotation.Nullable;
import android.graphics.Typeface;
import android.util.LruCache;

public class TypeFaceUtils
{
    private static final LruCache<String, Typeface> a;
    private static final Object b;
    
    public static void clear() {
        synchronized (TypeFaceUtils.b) {
            TypeFaceUtils.a.evictAll();
        }
    }
    
    public static void loadTypeface(@Nullable final String filePath, @Nullable final String familyName, final Callback callback) {
        final AtomicReference<Typeface> atomicReference = new AtomicReference<>();
        RxUtils.runInIO(new Runnable() {
            @Override
            public void run() {
                atomicReference.set(TypeFaceUtils.loadTypeface(filePath, familyName));
            }
        }, (Consumer<Object>)new Consumer<Object>() {
            public void accept(final Object o) throws Exception {
                final Callback a;
                if ((a = callback) != null) {
                    a.onTypefaceLoaded(atomicReference.get());
                }
            }
        });
    }
    
    public static Typeface loadTypeface(final String filePath) {
        return loadTypeface(filePath, "");
    }
    
    public static Typeface loadTypeface(@Nullable final String filePath, @Nullable final String familyName) {
        final Typeface create;
        if (StringUtils.isNotBlank(familyName) && (create = Typeface.create(familyName, 0)) != null && create != Typeface.DEFAULT) {
            final Typeface typeface = create;
            Debug.d("loadTypeface from system cache, familyName = " + familyName);
            return typeface;
        }
        if (!FileUtils.fileExist(filePath)) {
            Debug.w("loadTypeface, file does not exits, filePath = " + filePath);
            return Typeface.DEFAULT;
        }
        Typeface typeface2 = null;
        try {
            final Typeface a;
            if ((a = a(filePath)) != null) {
                return a;
            }
            final Benchmark benchmark = new Benchmark();
            typeface2 = Typeface.createFromFile(filePath);
            try {
                if (Debug.getDebug()) {
                    benchmark.report("loadTypeface, Typeface.createFromFile, filePath = " + filePath);
                }
                a(filePath, typeface2);
            }
            catch (final Exception ex) {
                Debug.w((Throwable)ex);
                typeface2 = Typeface.DEFAULT;
            }
        }
        catch (final Exception ex2) {}
        return typeface2;
    }
    
    private static void a(final String key, final Typeface typeface) {
        synchronized (TypeFaceUtils.b) {
            TypeFaceUtils.a.put(key, typeface);
        }
    }
    
    private static Typeface a(final String key) {
        synchronized (TypeFaceUtils.b) {
            final Typeface typeface = TypeFaceUtils.a.get(key);
            return typeface;
        }
    }
    
    static {
        a = new LruCache(8);
        b = new Object();
    }
    
    public interface Callback
    {
        void onTypefaceLoaded(final Typeface p0);
    }
}
